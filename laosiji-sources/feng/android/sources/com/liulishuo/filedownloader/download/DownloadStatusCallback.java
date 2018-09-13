package com.liulishuo.filedownloader.download;

import android.database.sqlite.SQLiteFullException;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import com.liulishuo.filedownloader.database.FileDownloadDatabase;
import com.liulishuo.filedownloader.exception.FileDownloadGiveUpRetryException;
import com.liulishuo.filedownloader.exception.FileDownloadOutOfSpaceException;
import com.liulishuo.filedownloader.message.MessageSnapshotFlow;
import com.liulishuo.filedownloader.message.MessageSnapshotTaker;
import com.liulishuo.filedownloader.model.FileDownloadModel;
import com.liulishuo.filedownloader.services.FileDownloadBroadcastHandler;
import com.liulishuo.filedownloader.util.FileDownloadLog;
import com.liulishuo.filedownloader.util.FileDownloadProperties;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.LockSupport;

public class DownloadStatusCallback implements Callback {
    private static final String ALREADY_DEAD_MESSAGE = "require callback %d but the host thread of the flow has already dead, what is occurred because of there are several reason can final this flow on different thread.";
    private static final int CALLBACK_SAFE_MIN_INTERVAL_BYTES = 1;
    private static final int CALLBACK_SAFE_MIN_INTERVAL_MILLIS = 5;
    private static final int NO_ANY_PROGRESS_CALLBACK = -1;
    private final AtomicLong callbackIncreaseBuffer = new AtomicLong();
    private long callbackMinIntervalBytes;
    private final int callbackProgressMaxCount;
    private final int callbackProgressMinInterval;
    private final FileDownloadDatabase database;
    private Handler handler;
    private HandlerThread handlerThread;
    private volatile boolean handlingMessage = false;
    private final AtomicBoolean isFirstCallback = new AtomicBoolean(true);
    private volatile long lastCallbackTimestamp = 0;
    private final int maxRetryTimes;
    private final FileDownloadModel model;
    private final AtomicBoolean needCallbackProgressToUser = new AtomicBoolean(false);
    private final AtomicBoolean needSetProcess = new AtomicBoolean(false);
    private volatile Thread parkThread;
    private final ProcessParams processParams;

    public static class ProcessParams {
        private Exception exception;
        private boolean isResuming;
        private int retryingTimes;

        void setResuming(boolean isResuming) {
            this.isResuming = isResuming;
        }

        public boolean isResuming() {
            return this.isResuming;
        }

        void setException(Exception exception) {
            this.exception = exception;
        }

        void setRetryingTimes(int retryingTimes) {
            this.retryingTimes = retryingTimes;
        }

        public Exception getException() {
            return this.exception;
        }

        public int getRetryingTimes() {
            return this.retryingTimes;
        }
    }

    DownloadStatusCallback(FileDownloadModel model, int maxRetryTimes, int minIntervalMillis, int callbackProgressMaxCount) {
        this.model = model;
        this.database = CustomComponentHolder.getImpl().getDatabaseInstance();
        if (minIntervalMillis < 5) {
            minIntervalMillis = 5;
        }
        this.callbackProgressMinInterval = minIntervalMillis;
        this.callbackProgressMaxCount = callbackProgressMaxCount;
        this.processParams = new ProcessParams();
        this.maxRetryTimes = maxRetryTimes;
    }

    public boolean isAlive() {
        return this.handlerThread != null && this.handlerThread.isAlive();
    }

    void discardAllMessage() {
        if (this.handler != null) {
            this.handler.removeCallbacksAndMessages(null);
            this.handlerThread.quit();
            this.parkThread = Thread.currentThread();
            while (this.handlingMessage) {
                LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(100));
            }
            this.parkThread = null;
        }
    }

    public void onPending() {
        this.model.setStatus((byte) 1);
        this.database.updatePending(this.model.getId());
        onStatusChanged((byte) 1);
    }

    void onStartThread() {
        this.model.setStatus((byte) 6);
        onStatusChanged((byte) 6);
        this.database.onTaskStart(this.model.getId());
    }

    void onConnected(boolean isResume, long totalLength, String etag, String fileName) throws IllegalArgumentException {
        String oldEtag = this.model.getETag();
        if (oldEtag == null || oldEtag.equals(etag)) {
            this.processParams.setResuming(isResume);
            this.model.setStatus((byte) 2);
            this.model.setTotal(totalLength);
            this.model.setETag(etag);
            this.model.setFilename(fileName);
            this.database.updateConnected(this.model.getId(), totalLength, etag, fileName);
            onStatusChanged((byte) 2);
            this.callbackMinIntervalBytes = calculateCallbackMinIntervalBytes(totalLength, (long) this.callbackProgressMaxCount);
            this.needSetProcess.compareAndSet(false, true);
            return;
        }
        throw new IllegalArgumentException(FileDownloadUtils.formatString("callback onConnected must with precondition succeed, but the etag is changes(%s != %s)", etag, oldEtag));
    }

    void onMultiConnection() {
        this.handlerThread = new HandlerThread("source-status-callback");
        this.handlerThread.start();
        this.handler = new Handler(this.handlerThread.getLooper(), this);
    }

    void onProgress(long increaseBytes) {
        this.callbackIncreaseBuffer.addAndGet(increaseBytes);
        this.model.increaseSoFar(increaseBytes);
        inspectNeedCallbackToUser(SystemClock.elapsedRealtime());
        if (this.handler == null) {
            handleProgress();
        } else if (this.needCallbackProgressToUser.get()) {
            sendMessage(this.handler.obtainMessage(3));
        }
    }

    void onRetry(Exception exception, int remainRetryTimes) {
        this.callbackIncreaseBuffer.set(0);
        if (this.handler == null) {
            handleRetry(exception, remainRetryTimes);
        } else {
            sendMessage(this.handler.obtainMessage(5, remainRetryTimes, 0, exception));
        }
    }

    void onPausedDirectly() {
        handlePaused();
    }

    void onErrorDirectly(Exception exception) {
        handleError(exception);
    }

    void onCompletedDirectly() throws IOException {
        if (!interceptBeforeCompleted()) {
            handleCompleted();
        }
    }

    private synchronized void sendMessage(Message message) {
        if (this.handlerThread.isAlive()) {
            try {
                this.handler.sendMessage(message);
            } catch (IllegalStateException e) {
                if (this.handlerThread.isAlive()) {
                    throw e;
                } else if (FileDownloadLog.NEED_LOG) {
                    FileDownloadLog.d(this, ALREADY_DEAD_MESSAGE, Integer.valueOf(message.what));
                }
            }
        } else if (FileDownloadLog.NEED_LOG) {
            FileDownloadLog.d(this, ALREADY_DEAD_MESSAGE, Integer.valueOf(message.what));
        }
    }

    private static long calculateCallbackMinIntervalBytes(long contentLength, long callbackProgressMaxCount) {
        if (callbackProgressMaxCount <= 0) {
            return -1;
        }
        if (contentLength == -1) {
            return 1;
        }
        long minIntervalBytes = contentLength / callbackProgressMaxCount;
        if (minIntervalBytes <= 0) {
            minIntervalBytes = 1;
        }
        return minIntervalBytes;
    }

    private Exception exFiltrate(Exception ex) {
        String tempPath = this.model.getTempFilePath();
        if ((!this.model.isChunked() && !FileDownloadProperties.getImpl().fileNonPreAllocation) || !(ex instanceof IOException) || !new File(tempPath).exists()) {
            return ex;
        }
        long freeSpaceBytes = FileDownloadUtils.getFreeSpaceBytes(tempPath);
        if (freeSpaceBytes > 4096) {
            return ex;
        }
        long downloadedSize = 0;
        File file = new File(tempPath);
        if (file.exists()) {
            downloadedSize = file.length();
        } else {
            FileDownloadLog.e(this, ex, "Exception with: free space isn't enough, and the target file not exist.", new Object[0]);
        }
        if (VERSION.SDK_INT >= 9) {
            return new FileDownloadOutOfSpaceException(freeSpaceBytes, 4096, downloadedSize, ex);
        }
        return new FileDownloadOutOfSpaceException(freeSpaceBytes, 4096, downloadedSize);
    }

    private void handleSQLiteFullException(SQLiteFullException sqLiteFullException) {
        int id = this.model.getId();
        if (FileDownloadLog.NEED_LOG) {
            FileDownloadLog.d(this, "the data of the task[%d] is dirty, because the SQLite full exception[%s], so remove it from the database directly.", Integer.valueOf(id), sqLiteFullException.toString());
        }
        this.model.setErrMsg(sqLiteFullException.toString());
        this.model.setStatus((byte) -1);
        this.database.remove(id);
        this.database.removeConnections(id);
    }

    private void renameTempFile() throws IOException {
        String tempPath = this.model.getTempFilePath();
        String targetPath = this.model.getTargetFilePath();
        File tempFile = new File(tempPath);
        try {
            boolean renameFailed;
            File targetFile = new File(targetPath);
            if (targetFile.exists()) {
                long oldTargetFileLength = targetFile.length();
                if (targetFile.delete()) {
                    FileDownloadLog.w(this, "The target file([%s], [%d]) will be replaced with the new downloaded file[%d]", targetPath, Long.valueOf(oldTargetFileLength), Long.valueOf(tempFile.length()));
                } else {
                    throw new IOException(FileDownloadUtils.formatString("Can't delete the old file([%s], [%d]), so can't replace it with the new downloaded one.", targetPath, Long.valueOf(oldTargetFileLength)));
                }
            }
            if (tempFile.renameTo(targetFile)) {
                renameFailed = false;
            } else {
                renameFailed = true;
            }
            if (renameFailed) {
                throw new IOException(FileDownloadUtils.formatString("Can't rename the  temp downloaded file(%s) to the target file(%s)", tempPath, targetPath));
            } else if (renameFailed && tempFile.exists() && !tempFile.delete()) {
                FileDownloadLog.w(this, "delete the temp file(%s) failed, on completed downloading.", tempPath);
            }
        } catch (Throwable th) {
            if (true && tempFile.exists() && !tempFile.delete()) {
                FileDownloadLog.w(this, "delete the temp file(%s) failed, on completed downloading.", tempPath);
            }
        }
    }

    public boolean handleMessage(Message msg) {
        this.handlingMessage = true;
        switch (msg.what) {
            case 3:
                try {
                    handleProgress();
                    break;
                } catch (Throwable th) {
                    this.handlingMessage = false;
                    if (this.parkThread != null) {
                        LockSupport.unpark(this.parkThread);
                    }
                }
            case 5:
                handleRetry((Exception) msg.obj, msg.arg1);
                break;
        }
        this.handlingMessage = false;
        if (this.parkThread != null) {
            LockSupport.unpark(this.parkThread);
        }
        return true;
    }

    private void handleProgress() {
        if (this.model.getSoFar() == this.model.getTotal()) {
            this.database.updateProgress(this.model.getId(), this.model.getSoFar());
            return;
        }
        if (this.needSetProcess.compareAndSet(true, false)) {
            if (FileDownloadLog.NEED_LOG) {
                FileDownloadLog.i(this, "handleProgress update model's status with progress", new Object[0]);
            }
            this.model.setStatus((byte) 3);
        }
        if (this.needCallbackProgressToUser.compareAndSet(true, false)) {
            if (FileDownloadLog.NEED_LOG) {
                FileDownloadLog.i(this, "handleProgress notify user progress status", new Object[0]);
            }
            onStatusChanged((byte) 3);
        }
    }

    private void handleCompleted() throws IOException {
        renameTempFile();
        this.model.setStatus((byte) -3);
        this.database.updateCompleted(this.model.getId(), this.model.getTotal());
        this.database.removeConnections(this.model.getId());
        onStatusChanged((byte) -3);
        if (FileDownloadProperties.getImpl().broadcastCompleted) {
            FileDownloadBroadcastHandler.sendCompletedBroadcast(this.model);
        }
    }

    private boolean interceptBeforeCompleted() {
        if (this.model.isChunked()) {
            this.model.setTotal(this.model.getSoFar());
        } else if (this.model.getSoFar() != this.model.getTotal()) {
            onErrorDirectly(new FileDownloadGiveUpRetryException(FileDownloadUtils.formatString("sofar[%d] not equal total[%d]", Long.valueOf(this.model.getSoFar()), Long.valueOf(this.model.getTotal()))));
            return true;
        }
        return false;
    }

    private void handleRetry(Exception exception, int remainRetryTimes) {
        Exception processEx = exFiltrate(exception);
        this.processParams.setException(processEx);
        this.processParams.setRetryingTimes(this.maxRetryTimes - remainRetryTimes);
        this.model.setStatus((byte) 5);
        this.model.setErrMsg(processEx.toString());
        this.database.updateRetry(this.model.getId(), processEx);
        onStatusChanged((byte) 5);
    }

    private void handlePaused() {
        this.model.setStatus((byte) -2);
        this.database.updatePause(this.model.getId(), this.model.getSoFar());
        onStatusChanged((byte) -2);
    }

    private void handleError(Exception exception) {
        Exception errProcessEx = exFiltrate(exception);
        if (errProcessEx instanceof SQLiteFullException) {
            handleSQLiteFullException((SQLiteFullException) errProcessEx);
        } else {
            try {
                this.model.setStatus((byte) -1);
                this.model.setErrMsg(exception.toString());
                this.database.updateError(this.model.getId(), errProcessEx, this.model.getSoFar());
            } catch (Exception fullException) {
                errProcessEx = fullException;
                handleSQLiteFullException((SQLiteFullException) errProcessEx);
            }
        }
        this.processParams.setException(errProcessEx);
        onStatusChanged((byte) -1);
    }

    private void inspectNeedCallbackToUser(long now) {
        boolean needCallback;
        if (this.isFirstCallback.compareAndSet(true, false)) {
            needCallback = true;
        } else {
            needCallback = this.callbackMinIntervalBytes != -1 && this.callbackIncreaseBuffer.get() >= this.callbackMinIntervalBytes && now - this.lastCallbackTimestamp >= ((long) this.callbackProgressMinInterval);
        }
        if (needCallback && this.needCallbackProgressToUser.compareAndSet(false, true)) {
            if (FileDownloadLog.NEED_LOG) {
                FileDownloadLog.i(this, "inspectNeedCallbackToUser need callback to user", new Object[0]);
            }
            this.lastCallbackTimestamp = now;
            this.callbackIncreaseBuffer.set(0);
        }
    }

    private void onStatusChanged(byte status) {
        if (status != (byte) -2) {
            MessageSnapshotFlow.getImpl().inflow(MessageSnapshotTaker.take(status, this.model, this.processParams));
        } else if (FileDownloadLog.NEED_LOG) {
            FileDownloadLog.d(this, "High concurrent cause, Already paused and we don't need to call-back to Task in here, %d", Integer.valueOf(this.model.getId()));
        }
    }
}
