package com.liulishuo.filedownloader;

import com.liulishuo.filedownloader.BaseDownloadTask.FinishListener;
import com.liulishuo.filedownloader.BaseDownloadTask.IRunningTask;
import com.liulishuo.filedownloader.BaseDownloadTask.LifeCycleCallback;
import com.liulishuo.filedownloader.IDownloadSpeed.Lookup;
import com.liulishuo.filedownloader.IDownloadSpeed.Monitor;
import com.liulishuo.filedownloader.ITaskHunter.IMessageHandler;
import com.liulishuo.filedownloader.ITaskHunter.IStarter;
import com.liulishuo.filedownloader.message.MessageSnapshot;
import com.liulishuo.filedownloader.message.MessageSnapshot.IWarnMessageSnapshot;
import com.liulishuo.filedownloader.message.MessageSnapshotTaker;
import com.liulishuo.filedownloader.model.FileDownloadHeader;
import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.liulishuo.filedownloader.util.FileDownloadLog;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;

public class DownloadTaskHunter implements ITaskHunter, IStarter, IMessageHandler, LifeCycleCallback {
    private String mEtag;
    private boolean mIsLargeFile;
    private boolean mIsResuming;
    private boolean mIsReusedOldFile = false;
    private IFileDownloadMessenger mMessenger;
    private final Object mPauseLock;
    private int mRetryingTimes;
    private long mSoFarBytes;
    private final Lookup mSpeedLookup;
    private final Monitor mSpeedMonitor;
    private volatile byte mStatus = (byte) 0;
    private final ICaptureTask mTask;
    private Throwable mThrowable = null;
    private long mTotalBytes;

    interface ICaptureTask {
        ArrayList<FinishListener> getFinishListenerList();

        FileDownloadHeader getHeader();

        IRunningTask getRunningTask();

        void setFileName(String str);
    }

    public boolean updateKeepAhead(MessageSnapshot snapshot) {
        if (FileDownloadStatus.isKeepAhead(getStatus(), snapshot.getStatus())) {
            update(snapshot);
            return true;
        } else if (!FileDownloadLog.NEED_LOG) {
            return false;
        } else {
            FileDownloadLog.d(this, "can't update mStatus change by keep ahead, %d, but the current mStatus is %d, %d", Byte.valueOf(this.mStatus), Byte.valueOf(getStatus()), Integer.valueOf(getId()));
            return false;
        }
    }

    public boolean updateKeepFlow(MessageSnapshot snapshot) {
        int currentStatus = getStatus();
        int nextStatus = snapshot.getStatus();
        if (-2 == currentStatus && FileDownloadStatus.isIng(nextStatus)) {
            if (!FileDownloadLog.NEED_LOG) {
                return true;
            }
            FileDownloadLog.d(this, "High concurrent cause, callback pending, but has already be paused %d", Integer.valueOf(getId()));
            return true;
        } else if (FileDownloadStatus.isKeepFlow(currentStatus, nextStatus)) {
            update(snapshot);
            return true;
        } else {
            if (FileDownloadLog.NEED_LOG) {
                FileDownloadLog.d(this, "can't update mStatus change by keep flow, %d, but the current mStatus is %d, %d", Byte.valueOf(this.mStatus), Byte.valueOf(getStatus()), Integer.valueOf(getId()));
            }
            return false;
        }
    }

    public boolean updateMoreLikelyCompleted(MessageSnapshot snapshot) {
        if (!FileDownloadStatus.isMoreLikelyCompleted(this.mTask.getRunningTask().getOrigin())) {
            return false;
        }
        update(snapshot);
        return true;
    }

    public boolean updateSameFilePathTaskRunning(MessageSnapshot snapshot) {
        if (!this.mTask.getRunningTask().getOrigin().isPathAsDirectory() || snapshot.getStatus() != (byte) -4 || getStatus() != (byte) 2) {
            return false;
        }
        update(snapshot);
        return true;
    }

    public IFileDownloadMessenger getMessenger() {
        return this.mMessenger;
    }

    public MessageSnapshot prepareErrorMessage(Throwable cause) {
        this.mStatus = (byte) -1;
        this.mThrowable = cause;
        return MessageSnapshotTaker.catchException(getId(), getSofarBytes(), cause);
    }

    private void update(MessageSnapshot snapshot) {
        BaseDownloadTask task = this.mTask.getRunningTask().getOrigin();
        byte status = snapshot.getStatus();
        this.mStatus = status;
        this.mIsLargeFile = snapshot.isLargeFile();
        switch (status) {
            case (byte) -4:
                int sameStoreTaskCount;
                this.mSpeedMonitor.reset();
                int sameIdTaskCount = FileDownloadList.getImpl().count(task.getId());
                if (sameIdTaskCount > 1 || !task.isPathAsDirectory()) {
                    sameStoreTaskCount = 0;
                } else {
                    sameStoreTaskCount = FileDownloadList.getImpl().count(FileDownloadUtils.generateId(task.getUrl(), task.getTargetFilePath()));
                }
                if (sameIdTaskCount + sameStoreTaskCount <= 1) {
                    FileDownloadLog.w(this, "warn, but no mListener to receive, switch to pending %d %d", Integer.valueOf(task.getId()), Integer.valueOf(FileDownloadServiceProxy.getImpl().getStatus(task.getId())));
                    if (FileDownloadStatus.isIng(FileDownloadServiceProxy.getImpl().getStatus(task.getId()))) {
                        this.mStatus = (byte) 1;
                        this.mTotalBytes = snapshot.getLargeTotalBytes();
                        this.mSoFarBytes = snapshot.getLargeSofarBytes();
                        this.mSpeedMonitor.start(this.mSoFarBytes);
                        this.mMessenger.notifyPending(((IWarnMessageSnapshot) snapshot).turnToPending());
                        return;
                    }
                }
                FileDownloadList.getImpl().remove(this.mTask.getRunningTask(), snapshot);
                return;
            case (byte) -3:
                this.mIsReusedOldFile = snapshot.isReusedDownloadedFile();
                this.mSoFarBytes = snapshot.getLargeTotalBytes();
                this.mTotalBytes = snapshot.getLargeTotalBytes();
                FileDownloadList.getImpl().remove(this.mTask.getRunningTask(), snapshot);
                return;
            case (byte) -1:
                this.mThrowable = snapshot.getThrowable();
                this.mSoFarBytes = snapshot.getLargeSofarBytes();
                FileDownloadList.getImpl().remove(this.mTask.getRunningTask(), snapshot);
                return;
            case (byte) 1:
                this.mSoFarBytes = snapshot.getLargeSofarBytes();
                this.mTotalBytes = snapshot.getLargeTotalBytes();
                this.mMessenger.notifyPending(snapshot);
                return;
            case (byte) 2:
                this.mTotalBytes = snapshot.getLargeTotalBytes();
                this.mIsResuming = snapshot.isResuming();
                this.mEtag = snapshot.getEtag();
                String filename = snapshot.getFileName();
                if (filename != null) {
                    if (task.getFilename() != null) {
                        FileDownloadLog.w(this, "already has mFilename[%s], but assign mFilename[%s] again", task.getFilename(), filename);
                    }
                    this.mTask.setFileName(filename);
                }
                this.mSpeedMonitor.start(this.mSoFarBytes);
                this.mMessenger.notifyConnected(snapshot);
                return;
            case (byte) 3:
                this.mSoFarBytes = snapshot.getLargeSofarBytes();
                this.mSpeedMonitor.update(snapshot.getLargeSofarBytes());
                this.mMessenger.notifyProgress(snapshot);
                return;
            case (byte) 5:
                this.mSoFarBytes = snapshot.getLargeSofarBytes();
                this.mThrowable = snapshot.getThrowable();
                this.mRetryingTimes = snapshot.getRetryingTimes();
                this.mSpeedMonitor.reset();
                this.mMessenger.notifyRetry(snapshot);
                return;
            case (byte) 6:
                this.mMessenger.notifyStarted(snapshot);
                return;
            default:
                return;
        }
    }

    public void onBegin() {
        if (FileDownloadMonitor.isValid()) {
            FileDownloadMonitor.getMonitor().onTaskBegin(this.mTask.getRunningTask().getOrigin());
        }
        if (FileDownloadLog.NEED_LOG) {
            FileDownloadLog.v(this, "filedownloader:lifecycle:start %s by %d ", toString(), Byte.valueOf(getStatus()));
        }
    }

    public void onIng() {
        if (FileDownloadMonitor.isValid() && getStatus() == (byte) 6) {
            FileDownloadMonitor.getMonitor().onTaskStarted(this.mTask.getRunningTask().getOrigin());
        }
    }

    public void onOver() {
        BaseDownloadTask origin = this.mTask.getRunningTask().getOrigin();
        if (FileDownloadMonitor.isValid()) {
            FileDownloadMonitor.getMonitor().onTaskOver(origin);
        }
        if (FileDownloadLog.NEED_LOG) {
            FileDownloadLog.v(this, "filedownloader:lifecycle:over %s by %d ", toString(), Byte.valueOf(getStatus()));
        }
        this.mSpeedMonitor.end(this.mSoFarBytes);
        if (this.mTask.getFinishListenerList() != null) {
            ArrayList<FinishListener> listenersCopy = (ArrayList) this.mTask.getFinishListenerList().clone();
            int numListeners = listenersCopy.size();
            for (int i = 0; i < numListeners; i++) {
                ((FinishListener) listenersCopy.get(i)).over(origin);
            }
        }
        FileDownloader.getImpl().getLostConnectedHandler().taskWorkFine(this.mTask.getRunningTask());
    }

    DownloadTaskHunter(ICaptureTask task, Object pauseLock) {
        this.mPauseLock = pauseLock;
        this.mTask = task;
        DownloadSpeedMonitor monitor = new DownloadSpeedMonitor();
        this.mSpeedMonitor = monitor;
        this.mSpeedLookup = monitor;
        this.mMessenger = new FileDownloadMessenger(task.getRunningTask(), this);
    }

    /* JADX WARNING: Missing block: B:9:0x002e, code:
            r3 = r10.mTask.getRunningTask();
            r1 = r3.getOrigin();
     */
    /* JADX WARNING: Missing block: B:10:0x003c, code:
            if (com.liulishuo.filedownloader.FileDownloadMonitor.isValid() == false) goto L_0x0045;
     */
    /* JADX WARNING: Missing block: B:11:0x003e, code:
            com.liulishuo.filedownloader.FileDownloadMonitor.getMonitor().onRequestStart(r1);
     */
    /* JADX WARNING: Missing block: B:13:0x0047, code:
            if (com.liulishuo.filedownloader.util.FileDownloadLog.NEED_LOG == false) goto L_0x006b;
     */
    /* JADX WARNING: Missing block: B:14:0x0049, code:
            com.liulishuo.filedownloader.util.FileDownloadLog.v(r10, "call start Url[%s], Path[%s] Listener[%s], Tag[%s]", r1.getUrl(), r1.getPath(), r1.getListener(), r1.getTag());
     */
    /* JADX WARNING: Missing block: B:15:0x006b, code:
            r2 = true;
     */
    /* JADX WARNING: Missing block: B:17:?, code:
            prepare();
     */
    /* JADX WARNING: Missing block: B:27:0x0092, code:
            r0 = move-exception;
     */
    /* JADX WARNING: Missing block: B:28:0x0093, code:
            r2 = false;
            com.liulishuo.filedownloader.FileDownloadList.getImpl().add(r3);
            com.liulishuo.filedownloader.FileDownloadList.getImpl().remove(r3, prepareErrorMessage(r0));
     */
    public void intoLaunchPool() {
        /*
        r10 = this;
        r7 = 2;
        r9 = 1;
        r8 = 0;
        r5 = r10.mPauseLock;
        monitor-enter(r5);
        r4 = r10.mStatus;	 Catch:{ all -> 0x008f }
        if (r4 == 0) goto L_0x0029;
    L_0x000a:
        r4 = "High concurrent cause, this task %d will not input to launch pool, because of the status isn't idle : %d";
        r6 = 2;
        r6 = new java.lang.Object[r6];	 Catch:{ all -> 0x008f }
        r7 = 0;
        r8 = r10.getId();	 Catch:{ all -> 0x008f }
        r8 = java.lang.Integer.valueOf(r8);	 Catch:{ all -> 0x008f }
        r6[r7] = r8;	 Catch:{ all -> 0x008f }
        r7 = 1;
        r8 = r10.mStatus;	 Catch:{ all -> 0x008f }
        r8 = java.lang.Byte.valueOf(r8);	 Catch:{ all -> 0x008f }
        r6[r7] = r8;	 Catch:{ all -> 0x008f }
        com.liulishuo.filedownloader.util.FileDownloadLog.w(r10, r4, r6);	 Catch:{ all -> 0x008f }
        monitor-exit(r5);	 Catch:{ all -> 0x008f }
    L_0x0028:
        return;
    L_0x0029:
        r4 = 10;
        r10.mStatus = r4;	 Catch:{ all -> 0x008f }
        monitor-exit(r5);	 Catch:{ all -> 0x008f }
        r4 = r10.mTask;
        r3 = r4.getRunningTask();
        r1 = r3.getOrigin();
        r4 = com.liulishuo.filedownloader.FileDownloadMonitor.isValid();
        if (r4 == 0) goto L_0x0045;
    L_0x003e:
        r4 = com.liulishuo.filedownloader.FileDownloadMonitor.getMonitor();
        r4.onRequestStart(r1);
    L_0x0045:
        r4 = com.liulishuo.filedownloader.util.FileDownloadLog.NEED_LOG;
        if (r4 == 0) goto L_0x006b;
    L_0x0049:
        r4 = "call start Url[%s], Path[%s] Listener[%s], Tag[%s]";
        r5 = 4;
        r5 = new java.lang.Object[r5];
        r6 = r1.getUrl();
        r5[r8] = r6;
        r6 = r1.getPath();
        r5[r9] = r6;
        r6 = r1.getListener();
        r5[r7] = r6;
        r6 = 3;
        r7 = r1.getTag();
        r5[r6] = r7;
        com.liulishuo.filedownloader.util.FileDownloadLog.v(r10, r4, r5);
    L_0x006b:
        r2 = 1;
        r10.prepare();	 Catch:{ Throwable -> 0x0092 }
    L_0x006f:
        if (r2 == 0) goto L_0x0078;
    L_0x0071:
        r4 = com.liulishuo.filedownloader.FileDownloadTaskLauncher.getImpl();
        r4.launch(r10);
    L_0x0078:
        r4 = com.liulishuo.filedownloader.util.FileDownloadLog.NEED_LOG;
        if (r4 == 0) goto L_0x0028;
    L_0x007c:
        r4 = "the task[%d] has been into the launch pool.";
        r5 = new java.lang.Object[r9];
        r6 = r10.getId();
        r6 = java.lang.Integer.valueOf(r6);
        r5[r8] = r6;
        com.liulishuo.filedownloader.util.FileDownloadLog.v(r10, r4, r5);
        goto L_0x0028;
    L_0x008f:
        r4 = move-exception;
        monitor-exit(r5);	 Catch:{ all -> 0x008f }
        throw r4;
    L_0x0092:
        r0 = move-exception;
        r2 = 0;
        r4 = com.liulishuo.filedownloader.FileDownloadList.getImpl();
        r4.add(r3);
        r4 = com.liulishuo.filedownloader.FileDownloadList.getImpl();
        r5 = r10.prepareErrorMessage(r0);
        r4.remove(r3, r5);
        goto L_0x006f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.liulishuo.filedownloader.DownloadTaskHunter.intoLaunchPool():void");
    }

    public boolean pause() {
        if (!FileDownloadStatus.isOver(getStatus())) {
            this.mStatus = (byte) -2;
            IRunningTask runningTask = this.mTask.getRunningTask();
            BaseDownloadTask origin = runningTask.getOrigin();
            FileDownloadTaskLauncher.getImpl().expire((IStarter) this);
            if (FileDownloadLog.NEED_LOG) {
                FileDownloadLog.v(this, "the task[%d] has been expired from the launch pool.", Integer.valueOf(getId()));
            }
            if (FileDownloader.getImpl().isServiceConnected()) {
                FileDownloadServiceProxy.getImpl().pause(origin.getId());
            } else if (FileDownloadLog.NEED_LOG) {
                FileDownloadLog.d(this, "request pause the task[%d] to the download service, but the download service isn't connected yet.", Integer.valueOf(origin.getId()));
            }
            FileDownloadList.getImpl().add(runningTask);
            FileDownloadList.getImpl().remove(runningTask, MessageSnapshotTaker.catchPause(origin));
            FileDownloader.getImpl().getLostConnectedHandler().taskWorkFine(runningTask);
            return true;
        } else if (!FileDownloadLog.NEED_LOG) {
            return false;
        } else {
            FileDownloadLog.d(this, "High concurrent cause, Already is over, can't pause again, %d %d", Byte.valueOf(getStatus()), Integer.valueOf(this.mTask.getRunningTask().getOrigin().getId()));
            return false;
        }
    }

    public byte getStatus() {
        return this.mStatus;
    }

    public void reset() {
        this.mThrowable = null;
        this.mEtag = null;
        this.mIsResuming = false;
        this.mRetryingTimes = 0;
        this.mIsReusedOldFile = false;
        this.mIsLargeFile = false;
        this.mSoFarBytes = 0;
        this.mTotalBytes = 0;
        this.mSpeedMonitor.reset();
        if (FileDownloadStatus.isOver(this.mStatus)) {
            this.mMessenger.discard();
            this.mMessenger = new FileDownloadMessenger(this.mTask.getRunningTask(), this);
        } else {
            this.mMessenger.reAppointment(this.mTask.getRunningTask(), this);
        }
        this.mStatus = (byte) 0;
    }

    public void setMinIntervalUpdateSpeed(int minIntervalUpdateSpeed) {
        this.mSpeedLookup.setMinIntervalUpdateSpeed(minIntervalUpdateSpeed);
    }

    public int getSpeed() {
        return this.mSpeedLookup.getSpeed();
    }

    public long getSofarBytes() {
        return this.mSoFarBytes;
    }

    public long getTotalBytes() {
        return this.mTotalBytes;
    }

    public Throwable getErrorCause() {
        return this.mThrowable;
    }

    public int getRetryingTimes() {
        return this.mRetryingTimes;
    }

    public boolean isReusedOldFile() {
        return this.mIsReusedOldFile;
    }

    public boolean isResuming() {
        return this.mIsResuming;
    }

    public String getEtag() {
        return this.mEtag;
    }

    public boolean isLargeFile() {
        return this.mIsLargeFile;
    }

    public void free() {
        if (FileDownloadLog.NEED_LOG) {
            FileDownloadLog.d(this, "free the task %d, when the status is %d", Integer.valueOf(getId()), Byte.valueOf(this.mStatus));
        }
        this.mStatus = (byte) 0;
    }

    private void prepare() throws IOException {
        File dir;
        BaseDownloadTask origin = this.mTask.getRunningTask().getOrigin();
        if (origin.getPath() == null) {
            origin.setPath(FileDownloadUtils.getDefaultSaveFilePath(origin.getUrl()));
            if (FileDownloadLog.NEED_LOG) {
                FileDownloadLog.d(this, "save Path is null to %s", origin.getPath());
            }
        }
        if (origin.isPathAsDirectory()) {
            dir = new File(origin.getPath());
        } else {
            String dirString = FileDownloadUtils.getParent(origin.getPath());
            if (dirString == null) {
                throw new InvalidParameterException(FileDownloadUtils.formatString("the provided mPath[%s] is invalid, can't find its directory", origin.getPath()));
            }
            dir = new File(dirString);
        }
        if (!dir.exists() && !dir.mkdirs() && !dir.exists()) {
            throw new IOException(FileDownloadUtils.formatString("Create parent directory failed, please make sure you have permission to create file or directory on the path: %s", dir.getAbsolutePath()));
        }
    }

    private int getId() {
        return this.mTask.getRunningTask().getOrigin().getId();
    }

    /* JADX WARNING: Missing block: B:25:?, code:
            com.liulishuo.filedownloader.FileDownloadList.getImpl().add(r14);
     */
    /* JADX WARNING: Missing block: B:26:0x00a8, code:
            if (com.liulishuo.filedownloader.util.FileDownloadHelper.inspectAndInflowDownloaded(r13.getId(), r13.getTargetFilePath(), r13.isForceReDownload(), true) != false) goto L_?;
     */
    /* JADX WARNING: Missing block: B:27:0x00aa, code:
            r16 = com.liulishuo.filedownloader.FileDownloadServiceProxy.getImpl().start(r13.getUrl(), r13.getPath(), r13.isPathAsDirectory(), r13.getCallbackProgressTimes(), r13.getCallbackProgressMinInterval(), r13.getAutoRetryTimes(), r13.isForceReDownload(), r17.mTask.getHeader(), r13.isWifiRequired());
     */
    /* JADX WARNING: Missing block: B:28:0x00df, code:
            if (r17.mStatus != (byte) -2) goto L_0x0106;
     */
    /* JADX WARNING: Missing block: B:29:0x00e1, code:
            r0 = r17;
            com.liulishuo.filedownloader.util.FileDownloadLog.w(r0, "High concurrent cause, this task %d will be paused,because of the status is paused, so the pause action must be applied", java.lang.Integer.valueOf(getId()));
     */
    /* JADX WARNING: Missing block: B:30:0x00f7, code:
            if (r16 == false) goto L_?;
     */
    /* JADX WARNING: Missing block: B:31:0x00f9, code:
            com.liulishuo.filedownloader.FileDownloadServiceProxy.getImpl().pause(getId());
     */
    /* JADX WARNING: Missing block: B:32:0x0106, code:
            if (r16 != false) goto L_0x0139;
     */
    /* JADX WARNING: Missing block: B:34:0x010c, code:
            if (r12.dispatchTaskStart(r14) != false) goto L_?;
     */
    /* JADX WARNING: Missing block: B:35:0x010e, code:
            r15 = prepareErrorMessage(new java.lang.RuntimeException("Occur Unknown Error, when request to start maybe some problem in binder, maybe the process was killed in unexpected."));
     */
    /* JADX WARNING: Missing block: B:36:0x0124, code:
            if (com.liulishuo.filedownloader.FileDownloadList.getImpl().isNotContains(r14) == false) goto L_0x0130;
     */
    /* JADX WARNING: Missing block: B:37:0x0126, code:
            r12.taskWorkFine(r14);
            com.liulishuo.filedownloader.FileDownloadList.getImpl().add(r14);
     */
    /* JADX WARNING: Missing block: B:38:0x0130, code:
            com.liulishuo.filedownloader.FileDownloadList.getImpl().remove(r14, r15);
     */
    /* JADX WARNING: Missing block: B:39:0x0139, code:
            r12.taskWorkFine(r14);
     */
    /* JADX WARNING: Missing block: B:43:?, code:
            return;
     */
    /* JADX WARNING: Missing block: B:44:?, code:
            return;
     */
    /* JADX WARNING: Missing block: B:45:?, code:
            return;
     */
    /* JADX WARNING: Missing block: B:46:?, code:
            return;
     */
    /* JADX WARNING: Missing block: B:47:?, code:
            return;
     */
    /* JADX WARNING: Missing block: B:49:?, code:
            return;
     */
    public void start() {
        /*
        r17 = this;
        r0 = r17;
        r1 = r0.mStatus;
        r2 = 10;
        if (r1 == r2) goto L_0x002a;
    L_0x0008:
        r1 = "High concurrent cause, this task %d will not start, because the of status isn't toLaunchPool: %d";
        r2 = 2;
        r2 = new java.lang.Object[r2];
        r3 = 0;
        r4 = r17.getId();
        r4 = java.lang.Integer.valueOf(r4);
        r2[r3] = r4;
        r3 = 1;
        r0 = r17;
        r4 = r0.mStatus;
        r4 = java.lang.Byte.valueOf(r4);
        r2[r3] = r4;
        r0 = r17;
        com.liulishuo.filedownloader.util.FileDownloadLog.w(r0, r1, r2);
    L_0x0029:
        return;
    L_0x002a:
        r0 = r17;
        r1 = r0.mTask;
        r14 = r1.getRunningTask();
        r13 = r14.getOrigin();
        r1 = com.liulishuo.filedownloader.FileDownloader.getImpl();
        r12 = r1.getLostConnectedHandler();
        r1 = r12.dispatchTaskStart(r14);	 Catch:{ Throwable -> 0x0077 }
        if (r1 != 0) goto L_0x0029;
    L_0x0044:
        r0 = r17;
        r2 = r0.mPauseLock;	 Catch:{ Throwable -> 0x0077 }
        monitor-enter(r2);	 Catch:{ Throwable -> 0x0077 }
        r0 = r17;
        r1 = r0.mStatus;	 Catch:{ all -> 0x0074 }
        r3 = 10;
        if (r1 == r3) goto L_0x0089;
    L_0x0051:
        r1 = "High concurrent cause, this task %d will not start, the status can't assign to toFileDownloadService, because the status isn't toLaunchPool: %d";
        r3 = 2;
        r3 = new java.lang.Object[r3];	 Catch:{ all -> 0x0074 }
        r4 = 0;
        r5 = r17.getId();	 Catch:{ all -> 0x0074 }
        r5 = java.lang.Integer.valueOf(r5);	 Catch:{ all -> 0x0074 }
        r3[r4] = r5;	 Catch:{ all -> 0x0074 }
        r4 = 1;
        r0 = r17;
        r5 = r0.mStatus;	 Catch:{ all -> 0x0074 }
        r5 = java.lang.Byte.valueOf(r5);	 Catch:{ all -> 0x0074 }
        r3[r4] = r5;	 Catch:{ all -> 0x0074 }
        r0 = r17;
        com.liulishuo.filedownloader.util.FileDownloadLog.w(r0, r1, r3);	 Catch:{ all -> 0x0074 }
        monitor-exit(r2);	 Catch:{ all -> 0x0074 }
        goto L_0x0029;
    L_0x0074:
        r1 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x0074 }
        throw r1;	 Catch:{ Throwable -> 0x0077 }
    L_0x0077:
        r11 = move-exception;
        r11.printStackTrace();
        r1 = com.liulishuo.filedownloader.FileDownloadList.getImpl();
        r0 = r17;
        r2 = r0.prepareErrorMessage(r11);
        r1.remove(r14, r2);
        goto L_0x0029;
    L_0x0089:
        r1 = 11;
        r0 = r17;
        r0.mStatus = r1;	 Catch:{ all -> 0x0074 }
        monitor-exit(r2);	 Catch:{ all -> 0x0074 }
        r1 = com.liulishuo.filedownloader.FileDownloadList.getImpl();	 Catch:{ Throwable -> 0x0077 }
        r1.add(r14);	 Catch:{ Throwable -> 0x0077 }
        r1 = r13.getId();	 Catch:{ Throwable -> 0x0077 }
        r2 = r13.getTargetFilePath();	 Catch:{ Throwable -> 0x0077 }
        r3 = r13.isForceReDownload();	 Catch:{ Throwable -> 0x0077 }
        r4 = 1;
        r1 = com.liulishuo.filedownloader.util.FileDownloadHelper.inspectAndInflowDownloaded(r1, r2, r3, r4);	 Catch:{ Throwable -> 0x0077 }
        if (r1 != 0) goto L_0x0029;
    L_0x00aa:
        r1 = com.liulishuo.filedownloader.FileDownloadServiceProxy.getImpl();	 Catch:{ Throwable -> 0x0077 }
        r2 = r13.getUrl();	 Catch:{ Throwable -> 0x0077 }
        r3 = r13.getPath();	 Catch:{ Throwable -> 0x0077 }
        r4 = r13.isPathAsDirectory();	 Catch:{ Throwable -> 0x0077 }
        r5 = r13.getCallbackProgressTimes();	 Catch:{ Throwable -> 0x0077 }
        r6 = r13.getCallbackProgressMinInterval();	 Catch:{ Throwable -> 0x0077 }
        r7 = r13.getAutoRetryTimes();	 Catch:{ Throwable -> 0x0077 }
        r8 = r13.isForceReDownload();	 Catch:{ Throwable -> 0x0077 }
        r0 = r17;
        r9 = r0.mTask;	 Catch:{ Throwable -> 0x0077 }
        r9 = r9.getHeader();	 Catch:{ Throwable -> 0x0077 }
        r10 = r13.isWifiRequired();	 Catch:{ Throwable -> 0x0077 }
        r16 = r1.start(r2, r3, r4, r5, r6, r7, r8, r9, r10);	 Catch:{ Throwable -> 0x0077 }
        r0 = r17;
        r1 = r0.mStatus;	 Catch:{ Throwable -> 0x0077 }
        r2 = -2;
        if (r1 != r2) goto L_0x0106;
    L_0x00e1:
        r1 = "High concurrent cause, this task %d will be paused,because of the status is paused, so the pause action must be applied";
        r2 = 1;
        r2 = new java.lang.Object[r2];	 Catch:{ Throwable -> 0x0077 }
        r3 = 0;
        r4 = r17.getId();	 Catch:{ Throwable -> 0x0077 }
        r4 = java.lang.Integer.valueOf(r4);	 Catch:{ Throwable -> 0x0077 }
        r2[r3] = r4;	 Catch:{ Throwable -> 0x0077 }
        r0 = r17;
        com.liulishuo.filedownloader.util.FileDownloadLog.w(r0, r1, r2);	 Catch:{ Throwable -> 0x0077 }
        if (r16 == 0) goto L_0x0029;
    L_0x00f9:
        r1 = com.liulishuo.filedownloader.FileDownloadServiceProxy.getImpl();	 Catch:{ Throwable -> 0x0077 }
        r2 = r17.getId();	 Catch:{ Throwable -> 0x0077 }
        r1.pause(r2);	 Catch:{ Throwable -> 0x0077 }
        goto L_0x0029;
    L_0x0106:
        if (r16 != 0) goto L_0x0139;
    L_0x0108:
        r1 = r12.dispatchTaskStart(r14);	 Catch:{ Throwable -> 0x0077 }
        if (r1 != 0) goto L_0x0029;
    L_0x010e:
        r1 = new java.lang.RuntimeException;	 Catch:{ Throwable -> 0x0077 }
        r2 = "Occur Unknown Error, when request to start maybe some problem in binder, maybe the process was killed in unexpected.";
        r1.<init>(r2);	 Catch:{ Throwable -> 0x0077 }
        r0 = r17;
        r15 = r0.prepareErrorMessage(r1);	 Catch:{ Throwable -> 0x0077 }
        r1 = com.liulishuo.filedownloader.FileDownloadList.getImpl();	 Catch:{ Throwable -> 0x0077 }
        r1 = r1.isNotContains(r14);	 Catch:{ Throwable -> 0x0077 }
        if (r1 == 0) goto L_0x0130;
    L_0x0126:
        r12.taskWorkFine(r14);	 Catch:{ Throwable -> 0x0077 }
        r1 = com.liulishuo.filedownloader.FileDownloadList.getImpl();	 Catch:{ Throwable -> 0x0077 }
        r1.add(r14);	 Catch:{ Throwable -> 0x0077 }
    L_0x0130:
        r1 = com.liulishuo.filedownloader.FileDownloadList.getImpl();	 Catch:{ Throwable -> 0x0077 }
        r1.remove(r14, r15);	 Catch:{ Throwable -> 0x0077 }
        goto L_0x0029;
    L_0x0139:
        r12.taskWorkFine(r14);	 Catch:{ Throwable -> 0x0077 }
        goto L_0x0029;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.liulishuo.filedownloader.DownloadTaskHunter.start():void");
    }

    public boolean equalListener(FileDownloadListener listener) {
        return this.mTask.getRunningTask().getOrigin().getListener() == listener;
    }
}
