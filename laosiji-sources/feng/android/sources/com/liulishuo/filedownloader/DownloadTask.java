package com.liulishuo.filedownloader;

import android.text.TextUtils;
import android.util.SparseArray;
import com.liulishuo.filedownloader.BaseDownloadTask.FinishListener;
import com.liulishuo.filedownloader.BaseDownloadTask.IRunningTask;
import com.liulishuo.filedownloader.BaseDownloadTask.InQueueTask;
import com.liulishuo.filedownloader.ITaskHunter.IMessageHandler;
import com.liulishuo.filedownloader.model.FileDownloadHeader;
import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.liulishuo.filedownloader.util.FileDownloadLog;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import java.io.File;
import java.util.ArrayList;

public class DownloadTask implements BaseDownloadTask, IRunningTask, ICaptureTask {
    public static final int DEFAULT_CALLBACK_PROGRESS_MIN_INTERVAL_MILLIS = 10;
    private final Object headerCreateLock = new Object();
    volatile int mAttachKey = 0;
    private int mAutoRetryTimes = 0;
    private int mCallbackProgressMinIntervalMillis = 10;
    private int mCallbackProgressTimes = 100;
    private String mFilename;
    private ArrayList<FinishListener> mFinishListenerList;
    private FileDownloadHeader mHeader;
    private final ITaskHunter mHunter;
    private int mId;
    private boolean mIsForceReDownload = false;
    private boolean mIsInQueueTask = false;
    private volatile boolean mIsMarkedAdded2List = false;
    private boolean mIsWifiRequired = false;
    private SparseArray<Object> mKeyedTags;
    private FileDownloadListener mListener;
    private final IMessageHandler mMessageHandler;
    private String mPath;
    private boolean mPathAsDirectory;
    private final Object mPauseLock;
    private boolean mSyncCallback = false;
    private Object mTag;
    private final String mUrl;

    private static final class InQueueTaskImpl implements InQueueTask {
        private final DownloadTask mTask;

        private InQueueTaskImpl(DownloadTask task) {
            this.mTask = task;
            this.mTask.mIsInQueueTask = true;
        }

        public int enqueue() {
            int id = this.mTask.getId();
            if (FileDownloadLog.NEED_LOG) {
                FileDownloadLog.d(this, "add the task[%d] to the queue", Integer.valueOf(id));
            }
            FileDownloadList.getImpl().addUnchecked(this.mTask);
            return id;
        }
    }

    DownloadTask(String url) {
        this.mUrl = url;
        this.mPauseLock = new Object();
        DownloadTaskHunter hunter = new DownloadTaskHunter(this, this.mPauseLock);
        this.mHunter = hunter;
        this.mMessageHandler = hunter;
    }

    public BaseDownloadTask setMinIntervalUpdateSpeed(int minIntervalUpdateSpeedMs) {
        this.mHunter.setMinIntervalUpdateSpeed(minIntervalUpdateSpeedMs);
        return this;
    }

    public BaseDownloadTask setPath(String path) {
        return setPath(path, false);
    }

    public BaseDownloadTask setPath(String path, boolean pathAsDirectory) {
        this.mPath = path;
        if (FileDownloadLog.NEED_LOG) {
            FileDownloadLog.d(this, "setPath %s", path);
        }
        this.mPathAsDirectory = pathAsDirectory;
        if (pathAsDirectory) {
            this.mFilename = null;
        } else {
            this.mFilename = new File(path).getName();
        }
        return this;
    }

    public BaseDownloadTask setListener(FileDownloadListener listener) {
        this.mListener = listener;
        if (FileDownloadLog.NEED_LOG) {
            FileDownloadLog.d(this, "setListener %s", listener);
        }
        return this;
    }

    public BaseDownloadTask setCallbackProgressTimes(int callbackProgressCount) {
        this.mCallbackProgressTimes = callbackProgressCount;
        return this;
    }

    public BaseDownloadTask setCallbackProgressMinInterval(int minIntervalMillis) {
        this.mCallbackProgressMinIntervalMillis = minIntervalMillis;
        return this;
    }

    public BaseDownloadTask setCallbackProgressIgnored() {
        return setCallbackProgressTimes(-1);
    }

    public BaseDownloadTask setTag(Object tag) {
        this.mTag = tag;
        if (FileDownloadLog.NEED_LOG) {
            FileDownloadLog.d(this, "setTag %s", tag);
        }
        return this;
    }

    public BaseDownloadTask setTag(int key, Object tag) {
        if (this.mKeyedTags == null) {
            this.mKeyedTags = new SparseArray(2);
        }
        this.mKeyedTags.put(key, tag);
        return this;
    }

    public BaseDownloadTask setForceReDownload(boolean isForceReDownload) {
        this.mIsForceReDownload = isForceReDownload;
        return this;
    }

    public BaseDownloadTask setFinishListener(FinishListener finishListener) {
        addFinishListener(finishListener);
        return this;
    }

    public BaseDownloadTask addFinishListener(FinishListener finishListener) {
        if (this.mFinishListenerList == null) {
            this.mFinishListenerList = new ArrayList();
        }
        if (!this.mFinishListenerList.contains(finishListener)) {
            this.mFinishListenerList.add(finishListener);
        }
        return this;
    }

    public boolean removeFinishListener(FinishListener finishListener) {
        return this.mFinishListenerList != null && this.mFinishListenerList.remove(finishListener);
    }

    public BaseDownloadTask setAutoRetryTimes(int autoRetryTimes) {
        this.mAutoRetryTimes = autoRetryTimes;
        return this;
    }

    public BaseDownloadTask addHeader(String name, String value) {
        checkAndCreateHeader();
        this.mHeader.add(name, value);
        return this;
    }

    public BaseDownloadTask addHeader(String line) {
        checkAndCreateHeader();
        this.mHeader.add(line);
        return this;
    }

    public BaseDownloadTask removeAllHeaders(String name) {
        if (this.mHeader == null) {
            synchronized (this.headerCreateLock) {
                if (this.mHeader == null) {
                }
            }
            return this;
        }
        this.mHeader.removeAll(name);
        return this;
    }

    public BaseDownloadTask setSyncCallback(boolean syncCallback) {
        this.mSyncCallback = syncCallback;
        return this;
    }

    public BaseDownloadTask setWifiRequired(boolean isWifiRequired) {
        this.mIsWifiRequired = isWifiRequired;
        return this;
    }

    public int ready() {
        return asInQueueTask().enqueue();
    }

    public InQueueTask asInQueueTask() {
        return new InQueueTaskImpl();
    }

    public boolean reuse() {
        if (isRunning()) {
            FileDownloadLog.w(this, "This task[%d] is running, if you want start the same task, please create a new one by FileDownloader#create", Integer.valueOf(getId()));
            return false;
        }
        this.mAttachKey = 0;
        this.mIsInQueueTask = false;
        this.mIsMarkedAdded2List = false;
        this.mHunter.reset();
        return true;
    }

    public boolean isUsing() {
        return this.mHunter.getStatus() != (byte) 0;
    }

    public boolean isRunning() {
        if (FileDownloader.getImpl().getLostConnectedHandler().isInWaitingList(this)) {
            return true;
        }
        return FileDownloadStatus.isIng(getStatus());
    }

    public boolean isAttached() {
        return this.mAttachKey != 0;
    }

    public int start() {
        if (!this.mIsInQueueTask) {
            return startTaskUnchecked();
        }
        throw new IllegalStateException("If you start the task manually, it means this task doesn't belong to a queue, so you must not invoke BaseDownloadTask#ready() or InQueueTask#enqueue() before you start() this method. For detail: If this task doesn't belong to a queue, what is just an isolated task, you just need to invoke BaseDownloadTask#start() to start this task, that's all. In other words, If this task doesn't belong to a queue, you must not invoke BaseDownloadTask#ready() method or InQueueTask#enqueue() method before invoke BaseDownloadTask#start(), If you do that and if there is the same listener object to start a queue in another thread, this task may be assembled by the queue, in that case, when you invoke BaseDownloadTask#start() manually to start this task or this task is started by the queue, there is an exception buried in there, because this task object is started two times without declare BaseDownloadTask#reuse() : 1. you invoke BaseDownloadTask#start() manually;  2. the queue start this task automatically.");
    }

    private int startTaskUnchecked() {
        if (!isUsing()) {
            if (!isAttached()) {
                setAttachKeyDefault();
            }
            this.mHunter.intoLaunchPool();
            return getId();
        } else if (isRunning()) {
            throw new IllegalStateException(FileDownloadUtils.formatString("This task is running %d, if you want to start the same task, please create a new one by FileDownloader.create", Integer.valueOf(getId())));
        } else {
            throw new IllegalStateException("This task is dirty to restart, If you want to reuse this task, please invoke #reuse method manually and retry to restart again." + this.mHunter.toString());
        }
    }

    public boolean pause() {
        boolean pause;
        synchronized (this.mPauseLock) {
            pause = this.mHunter.pause();
        }
        return pause;
    }

    public boolean cancel() {
        return pause();
    }

    public int getId() {
        if (this.mId != 0) {
            return this.mId;
        }
        if (TextUtils.isEmpty(this.mPath) || TextUtils.isEmpty(this.mUrl)) {
            return 0;
        }
        int generateId = FileDownloadUtils.generateId(this.mUrl, this.mPath, this.mPathAsDirectory);
        this.mId = generateId;
        return generateId;
    }

    public int getDownloadId() {
        return getId();
    }

    public String getUrl() {
        return this.mUrl;
    }

    public int getCallbackProgressTimes() {
        return this.mCallbackProgressTimes;
    }

    public int getCallbackProgressMinInterval() {
        return this.mCallbackProgressMinIntervalMillis;
    }

    public String getPath() {
        return this.mPath;
    }

    public boolean isPathAsDirectory() {
        return this.mPathAsDirectory;
    }

    public String getFilename() {
        return this.mFilename;
    }

    public String getTargetFilePath() {
        return FileDownloadUtils.getTargetFilePath(getPath(), isPathAsDirectory(), getFilename());
    }

    public FileDownloadListener getListener() {
        return this.mListener;
    }

    public int getSoFarBytes() {
        return getSmallFileSoFarBytes();
    }

    public int getSmallFileSoFarBytes() {
        if (this.mHunter.getSofarBytes() > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) this.mHunter.getSofarBytes();
    }

    public long getLargeFileSoFarBytes() {
        return this.mHunter.getSofarBytes();
    }

    public int getTotalBytes() {
        return getSmallFileTotalBytes();
    }

    public int getSmallFileTotalBytes() {
        if (this.mHunter.getTotalBytes() > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) this.mHunter.getTotalBytes();
    }

    public long getLargeFileTotalBytes() {
        return this.mHunter.getTotalBytes();
    }

    public int getSpeed() {
        return this.mHunter.getSpeed();
    }

    public byte getStatus() {
        return this.mHunter.getStatus();
    }

    public boolean isForceReDownload() {
        return this.mIsForceReDownload;
    }

    public Throwable getEx() {
        return getErrorCause();
    }

    public Throwable getErrorCause() {
        return this.mHunter.getErrorCause();
    }

    public boolean isReusedOldFile() {
        return this.mHunter.isReusedOldFile();
    }

    public Object getTag() {
        return this.mTag;
    }

    public Object getTag(int key) {
        return this.mKeyedTags == null ? null : this.mKeyedTags.get(key);
    }

    public boolean isContinue() {
        return isResuming();
    }

    public boolean isResuming() {
        return this.mHunter.isResuming();
    }

    public String getEtag() {
        return this.mHunter.getEtag();
    }

    public int getAutoRetryTimes() {
        return this.mAutoRetryTimes;
    }

    public int getRetryingTimes() {
        return this.mHunter.getRetryingTimes();
    }

    public boolean isSyncCallback() {
        return this.mSyncCallback;
    }

    public boolean isLargeFile() {
        return this.mHunter.isLargeFile();
    }

    public boolean isWifiRequired() {
        return this.mIsWifiRequired;
    }

    private void checkAndCreateHeader() {
        if (this.mHeader == null) {
            synchronized (this.headerCreateLock) {
                if (this.mHeader == null) {
                    this.mHeader = new FileDownloadHeader();
                }
            }
        }
    }

    public FileDownloadHeader getHeader() {
        return this.mHeader;
    }

    public void markAdded2List() {
        this.mIsMarkedAdded2List = true;
    }

    public void free() {
        this.mHunter.free();
        if (FileDownloadList.getImpl().isNotContains(this)) {
            this.mIsMarkedAdded2List = false;
        }
    }

    public void startTaskByQueue() {
        startTaskUnchecked();
    }

    public void startTaskByRescue() {
        startTaskUnchecked();
    }

    public Object getPauseLock() {
        return this.mPauseLock;
    }

    public boolean isContainFinishListener() {
        return this.mFinishListenerList != null && this.mFinishListenerList.size() > 0;
    }

    public boolean isMarkedAdded2List() {
        return this.mIsMarkedAdded2List;
    }

    public IRunningTask getRunningTask() {
        return this;
    }

    public void setFileName(String fileName) {
        this.mFilename = fileName;
    }

    public ArrayList<FinishListener> getFinishListenerList() {
        return this.mFinishListenerList;
    }

    public BaseDownloadTask getOrigin() {
        return this;
    }

    public IMessageHandler getMessageHandler() {
        return this.mMessageHandler;
    }

    public boolean is(int id) {
        return getId() == id;
    }

    public boolean is(FileDownloadListener listener) {
        return getListener() == listener;
    }

    public boolean isOver() {
        return FileDownloadStatus.isOver(getStatus());
    }

    public int getAttachKey() {
        return this.mAttachKey;
    }

    public void setAttachKeyByQueue(int key) {
        this.mAttachKey = key;
    }

    public void setAttachKeyDefault() {
        int attachKey;
        if (getListener() != null) {
            attachKey = getListener().hashCode();
        } else {
            attachKey = hashCode();
        }
        this.mAttachKey = attachKey;
    }

    public String toString() {
        return FileDownloadUtils.formatString("%d@%s", Integer.valueOf(getId()), super.toString());
    }
}
