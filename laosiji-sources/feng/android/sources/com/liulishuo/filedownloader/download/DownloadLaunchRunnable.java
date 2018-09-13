package com.liulishuo.filedownloader.download;

import com.liulishuo.filedownloader.IThreadPoolMonitor;
import com.liulishuo.filedownloader.connection.FileDownloadConnection;
import com.liulishuo.filedownloader.database.FileDownloadDatabase;
import com.liulishuo.filedownloader.download.ConnectionProfile.ConnectionProfileBuild;
import com.liulishuo.filedownloader.exception.FileDownloadGiveUpRetryException;
import com.liulishuo.filedownloader.exception.FileDownloadHttpException;
import com.liulishuo.filedownloader.exception.FileDownloadNetworkPolicyException;
import com.liulishuo.filedownloader.exception.FileDownloadOutOfSpaceException;
import com.liulishuo.filedownloader.exception.FileDownloadSecurityException;
import com.liulishuo.filedownloader.model.ConnectionModel;
import com.liulishuo.filedownloader.model.FileDownloadHeader;
import com.liulishuo.filedownloader.model.FileDownloadModel;
import com.liulishuo.filedownloader.stream.FileDownloadOutputStream;
import com.liulishuo.filedownloader.util.FileDownloadExecutors;
import com.liulishuo.filedownloader.util.FileDownloadHelper;
import com.liulishuo.filedownloader.util.FileDownloadLog;
import com.liulishuo.filedownloader.util.FileDownloadProperties;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import com.taobao.accs.common.Constants;
import com.umeng.message.MsgConstant;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;

public class DownloadLaunchRunnable implements Runnable, ProcessCallback {
    private static final ThreadPoolExecutor DOWNLOAD_EXECUTOR = FileDownloadExecutors.newFixedThreadPool("ConnectionBlock");
    private static final int HTTP_REQUESTED_RANGE_NOT_SATISFIABLE = 416;
    private static final int TOTAL_VALUE_IN_CHUNKED_RESOURCE = -1;
    private boolean acceptPartial;
    private final AtomicBoolean alive;
    private final FileDownloadDatabase database;
    private final int defaultConnectionCount;
    private final ArrayList<DownloadRunnable> downloadRunnableList;
    private volatile boolean error;
    private volatile Exception errorException;
    private boolean isChunked;
    private final boolean isForceReDownload;
    private boolean isNeedForceDiscardRange;
    private boolean isResumeAvailableOnDB;
    private boolean isSingleConnection;
    private boolean isTriedFixRangeNotSatisfiable;
    private final boolean isWifiRequired;
    private long lastCallbackBytes;
    private long lastCallbackTimestamp;
    private long lastUpdateBytes;
    private long lastUpdateTimestamp;
    private final FileDownloadModel model;
    private volatile boolean paused;
    private String redirectedUrl;
    private DownloadRunnable singleDownloadRunnable;
    private final DownloadStatusCallback statusCallback;
    private final boolean supportSeek;
    private final IThreadPoolMonitor threadPoolMonitor;
    private final FileDownloadHeader userRequestHeader;
    int validRetryTimes;

    public static class Builder {
        private Integer callbackProgressMaxCount;
        private FileDownloadHeader header;
        private Boolean isForceReDownload;
        private Boolean isWifiRequired;
        private Integer maxRetryTimes;
        private Integer minIntervalMillis;
        private FileDownloadModel model;
        private IThreadPoolMonitor threadPoolMonitor;

        public Builder setModel(FileDownloadModel model) {
            this.model = model;
            return this;
        }

        public Builder setHeader(FileDownloadHeader header) {
            this.header = header;
            return this;
        }

        public Builder setThreadPoolMonitor(IThreadPoolMonitor threadPoolMonitor) {
            this.threadPoolMonitor = threadPoolMonitor;
            return this;
        }

        public Builder setMinIntervalMillis(Integer minIntervalMillis) {
            this.minIntervalMillis = minIntervalMillis;
            return this;
        }

        public Builder setCallbackProgressMaxCount(Integer callbackProgressMaxCount) {
            this.callbackProgressMaxCount = callbackProgressMaxCount;
            return this;
        }

        public Builder setForceReDownload(Boolean forceReDownload) {
            this.isForceReDownload = forceReDownload;
            return this;
        }

        public Builder setWifiRequired(Boolean wifiRequired) {
            this.isWifiRequired = wifiRequired;
            return this;
        }

        public Builder setMaxRetryTimes(Integer maxRetryTimes) {
            this.maxRetryTimes = maxRetryTimes;
            return this;
        }

        public DownloadLaunchRunnable build() {
            if (this.model != null && this.threadPoolMonitor != null && this.minIntervalMillis != null && this.callbackProgressMaxCount != null && this.isForceReDownload != null && this.isWifiRequired != null && this.maxRetryTimes != null) {
                return new DownloadLaunchRunnable(this.model, this.header, this.threadPoolMonitor, this.minIntervalMillis.intValue(), this.callbackProgressMaxCount.intValue(), this.isForceReDownload.booleanValue(), this.isWifiRequired.booleanValue(), this.maxRetryTimes.intValue(), null);
            }
            throw new IllegalArgumentException();
        }
    }

    class DiscardSafely extends Throwable {
        DiscardSafely() {
        }
    }

    class RetryDirectly extends Throwable {
        RetryDirectly() {
        }
    }

    private DownloadLaunchRunnable(FileDownloadModel model, FileDownloadHeader header, IThreadPoolMonitor threadPoolMonitor, int minIntervalMillis, int callbackProgressMaxCount, boolean isForceReDownload, boolean isWifiRequired, int maxRetryTimes) {
        this.defaultConnectionCount = 5;
        this.isNeedForceDiscardRange = false;
        this.downloadRunnableList = new ArrayList(5);
        this.lastCallbackBytes = 0;
        this.lastCallbackTimestamp = 0;
        this.lastUpdateBytes = 0;
        this.lastUpdateTimestamp = 0;
        this.alive = new AtomicBoolean(true);
        this.paused = false;
        this.isTriedFixRangeNotSatisfiable = false;
        this.model = model;
        this.userRequestHeader = header;
        this.isForceReDownload = isForceReDownload;
        this.isWifiRequired = isWifiRequired;
        this.database = CustomComponentHolder.getImpl().getDatabaseInstance();
        this.supportSeek = CustomComponentHolder.getImpl().isSupportSeek();
        this.threadPoolMonitor = threadPoolMonitor;
        this.validRetryTimes = maxRetryTimes;
        this.statusCallback = new DownloadStatusCallback(model, maxRetryTimes, minIntervalMillis, callbackProgressMaxCount);
    }

    private DownloadLaunchRunnable(DownloadStatusCallback callback, FileDownloadModel model, FileDownloadHeader header, IThreadPoolMonitor threadPoolMonitor, int minIntervalMillis, int callbackProgressMaxCount, boolean isForceReDownload, boolean isWifiRequired, int maxRetryTimes) {
        this.defaultConnectionCount = 5;
        this.isNeedForceDiscardRange = false;
        this.downloadRunnableList = new ArrayList(5);
        this.lastCallbackBytes = 0;
        this.lastCallbackTimestamp = 0;
        this.lastUpdateBytes = 0;
        this.lastUpdateTimestamp = 0;
        this.alive = new AtomicBoolean(true);
        this.paused = false;
        this.isTriedFixRangeNotSatisfiable = false;
        this.model = model;
        this.userRequestHeader = header;
        this.isForceReDownload = isForceReDownload;
        this.isWifiRequired = isWifiRequired;
        this.database = CustomComponentHolder.getImpl().getDatabaseInstance();
        this.supportSeek = CustomComponentHolder.getImpl().isSupportSeek();
        this.threadPoolMonitor = threadPoolMonitor;
        this.validRetryTimes = maxRetryTimes;
        this.statusCallback = callback;
    }

    static DownloadLaunchRunnable createForTest(DownloadStatusCallback callback, FileDownloadModel model, FileDownloadHeader header, IThreadPoolMonitor threadPoolMonitor, int minIntervalMillis, int callbackProgressMaxCount, boolean isForceReDownload, boolean isWifiRequired, int maxRetryTimes) {
        return new DownloadLaunchRunnable(callback, model, header, threadPoolMonitor, minIntervalMillis, callbackProgressMaxCount, isForceReDownload, isWifiRequired, maxRetryTimes);
    }

    public void pause() {
        this.paused = true;
        if (this.singleDownloadRunnable != null) {
            this.singleDownloadRunnable.pause();
        }
        Iterator it = ((ArrayList) this.downloadRunnableList.clone()).iterator();
        while (it.hasNext()) {
            DownloadRunnable runnable = (DownloadRunnable) it.next();
            if (runnable != null) {
                runnable.pause();
            }
        }
    }

    public void pending() {
        inspectTaskModelResumeAvailableOnDB(this.database.findConnectionModel(this.model.getId()));
        this.statusCallback.onPending();
    }

    /* JADX WARNING: Removed duplicated region for block: B:141:0x026b  */
    /* JADX WARNING: Removed duplicated region for block: B:111:0x0208  */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x022f A:{SYNTHETIC, Splitter: B:123:0x022f} */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x0182 A:{Catch:{ IOException -> 0x017a, IllegalAccessException -> 0x0223, InterruptedException -> 0x022b, IllegalArgumentException -> 0x02a0, FileDownloadSecurityException -> 0x02a4, FileDownloadGiveUpRetryException -> 0x02a8, DiscardSafely -> 0x0233, RetryDirectly -> 0x0262, all -> 0x007b }} */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x0182 A:{Catch:{ IOException -> 0x017a, IllegalAccessException -> 0x0223, InterruptedException -> 0x022b, IllegalArgumentException -> 0x02a0, FileDownloadSecurityException -> 0x02a4, FileDownloadGiveUpRetryException -> 0x02a8, DiscardSafely -> 0x0233, RetryDirectly -> 0x0262, all -> 0x007b }} */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x022f A:{SYNTHETIC, Splitter: B:123:0x022f} */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x022f A:{SYNTHETIC, Splitter: B:123:0x022f} */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x0182 A:{Catch:{ IOException -> 0x017a, IllegalAccessException -> 0x0223, InterruptedException -> 0x022b, IllegalArgumentException -> 0x02a0, FileDownloadSecurityException -> 0x02a4, FileDownloadGiveUpRetryException -> 0x02a8, DiscardSafely -> 0x0233, RetryDirectly -> 0x0262, all -> 0x007b }} */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x0182 A:{Catch:{ IOException -> 0x017a, IllegalAccessException -> 0x0223, InterruptedException -> 0x022b, IllegalArgumentException -> 0x02a0, FileDownloadSecurityException -> 0x02a4, FileDownloadGiveUpRetryException -> 0x02a8, DiscardSafely -> 0x0233, RetryDirectly -> 0x0262, all -> 0x007b }} */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x022f A:{SYNTHETIC, Splitter: B:123:0x022f} */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x022f A:{SYNTHETIC, Splitter: B:123:0x022f} */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x0182 A:{Catch:{ IOException -> 0x017a, IllegalAccessException -> 0x0223, InterruptedException -> 0x022b, IllegalArgumentException -> 0x02a0, FileDownloadSecurityException -> 0x02a4, FileDownloadGiveUpRetryException -> 0x02a8, DiscardSafely -> 0x0233, RetryDirectly -> 0x0262, all -> 0x007b }} */
    public void run() {
        /*
        r14 = this;
        r10 = -2;
        r8 = 1;
        r9 = 0;
        r5 = 10;
        android.os.Process.setThreadPriority(r5);	 Catch:{ all -> 0x007b }
        r5 = r14.model;	 Catch:{ all -> 0x007b }
        r5 = r5.getStatus();	 Catch:{ all -> 0x007b }
        if (r5 == r8) goto L_0x00a9;
    L_0x0010:
        r5 = r14.model;	 Catch:{ all -> 0x007b }
        r5 = r5.getStatus();	 Catch:{ all -> 0x007b }
        if (r5 != r10) goto L_0x0046;
    L_0x0018:
        r5 = com.liulishuo.filedownloader.util.FileDownloadLog.NEED_LOG;	 Catch:{ all -> 0x007b }
        if (r5 == 0) goto L_0x0032;
    L_0x001c:
        r5 = "High concurrent cause, start runnable but already paused %d";
        r8 = 1;
        r8 = new java.lang.Object[r8];	 Catch:{ all -> 0x007b }
        r10 = 0;
        r11 = r14.model;	 Catch:{ all -> 0x007b }
        r11 = r11.getId();	 Catch:{ all -> 0x007b }
        r11 = java.lang.Integer.valueOf(r11);	 Catch:{ all -> 0x007b }
        r8[r10] = r11;	 Catch:{ all -> 0x007b }
        com.liulishuo.filedownloader.util.FileDownloadLog.d(r14, r5, r8);	 Catch:{ all -> 0x007b }
    L_0x0032:
        r5 = r14.statusCallback;
        r5.discardAllMessage();
        r5 = r14.paused;
        if (r5 == 0) goto L_0x0090;
    L_0x003b:
        r5 = r14.statusCallback;
        r5.onPausedDirectly();
    L_0x0040:
        r5 = r14.alive;
        r5.set(r9);
    L_0x0045:
        return;
    L_0x0046:
        r5 = new java.lang.RuntimeException;	 Catch:{ all -> 0x007b }
        r8 = "Task[%d] can't start the download runnable, because its status is %d not %d";
        r10 = 3;
        r10 = new java.lang.Object[r10];	 Catch:{ all -> 0x007b }
        r11 = 0;
        r12 = r14.model;	 Catch:{ all -> 0x007b }
        r12 = r12.getId();	 Catch:{ all -> 0x007b }
        r12 = java.lang.Integer.valueOf(r12);	 Catch:{ all -> 0x007b }
        r10[r11] = r12;	 Catch:{ all -> 0x007b }
        r11 = 1;
        r12 = r14.model;	 Catch:{ all -> 0x007b }
        r12 = r12.getStatus();	 Catch:{ all -> 0x007b }
        r12 = java.lang.Byte.valueOf(r12);	 Catch:{ all -> 0x007b }
        r10[r11] = r12;	 Catch:{ all -> 0x007b }
        r11 = 2;
        r12 = 1;
        r12 = java.lang.Byte.valueOf(r12);	 Catch:{ all -> 0x007b }
        r10[r11] = r12;	 Catch:{ all -> 0x007b }
        r8 = com.liulishuo.filedownloader.util.FileDownloadUtils.formatString(r8, r10);	 Catch:{ all -> 0x007b }
        r5.<init>(r8);	 Catch:{ all -> 0x007b }
        r14.onError(r5);	 Catch:{ all -> 0x007b }
        goto L_0x0032;
    L_0x007b:
        r5 = move-exception;
        r8 = r14.statusCallback;
        r8.discardAllMessage();
        r8 = r14.paused;
        if (r8 == 0) goto L_0x0284;
    L_0x0085:
        r8 = r14.statusCallback;
        r8.onPausedDirectly();
    L_0x008a:
        r8 = r14.alive;
        r8.set(r9);
        throw r5;
    L_0x0090:
        r5 = r14.error;
        if (r5 == 0) goto L_0x009c;
    L_0x0094:
        r5 = r14.statusCallback;
        r8 = r14.errorException;
        r5.onErrorDirectly(r8);
        goto L_0x0040;
    L_0x009c:
        r5 = r14.statusCallback;	 Catch:{ IOException -> 0x00a2 }
        r5.onCompletedDirectly();	 Catch:{ IOException -> 0x00a2 }
        goto L_0x0040;
    L_0x00a2:
        r3 = move-exception;
        r5 = r14.statusCallback;
        r5.onErrorDirectly(r3);
        goto L_0x0040;
    L_0x00a9:
        r5 = r14.paused;	 Catch:{ all -> 0x007b }
        if (r5 != 0) goto L_0x00b2;
    L_0x00ad:
        r5 = r14.statusCallback;	 Catch:{ all -> 0x007b }
        r5.onStartThread();	 Catch:{ all -> 0x007b }
    L_0x00b2:
        r5 = r14.paused;	 Catch:{ all -> 0x007b }
        if (r5 == 0) goto L_0x00fe;
    L_0x00b6:
        r5 = com.liulishuo.filedownloader.util.FileDownloadLog.NEED_LOG;	 Catch:{ all -> 0x007b }
        if (r5 == 0) goto L_0x00d0;
    L_0x00ba:
        r5 = "High concurrent cause, start runnable but already paused %d";
        r8 = 1;
        r8 = new java.lang.Object[r8];	 Catch:{ all -> 0x007b }
        r10 = 0;
        r11 = r14.model;	 Catch:{ all -> 0x007b }
        r11 = r11.getId();	 Catch:{ all -> 0x007b }
        r11 = java.lang.Integer.valueOf(r11);	 Catch:{ all -> 0x007b }
        r8[r10] = r11;	 Catch:{ all -> 0x007b }
        com.liulishuo.filedownloader.util.FileDownloadLog.d(r14, r5, r8);	 Catch:{ all -> 0x007b }
    L_0x00d0:
        r5 = r14.statusCallback;
        r5.discardAllMessage();
        r5 = r14.paused;
        if (r5 == 0) goto L_0x00e5;
    L_0x00d9:
        r5 = r14.statusCallback;
        r5.onPausedDirectly();
    L_0x00de:
        r5 = r14.alive;
        r5.set(r9);
        goto L_0x0045;
    L_0x00e5:
        r5 = r14.error;
        if (r5 == 0) goto L_0x00f1;
    L_0x00e9:
        r5 = r14.statusCallback;
        r8 = r14.errorException;
        r5.onErrorDirectly(r8);
        goto L_0x00de;
    L_0x00f1:
        r5 = r14.statusCallback;	 Catch:{ IOException -> 0x00f7 }
        r5.onCompletedDirectly();	 Catch:{ IOException -> 0x00f7 }
        goto L_0x00de;
    L_0x00f7:
        r3 = move-exception;
        r5 = r14.statusCallback;
        r5.onErrorDirectly(r3);
        goto L_0x00de;
    L_0x00fe:
        r14.checkupBeforeConnect();	 Catch:{ IOException -> 0x017a, IllegalAccessException -> 0x0223, InterruptedException -> 0x022b, IllegalArgumentException -> 0x02a0, FileDownloadSecurityException -> 0x02a4, FileDownloadGiveUpRetryException -> 0x02a8, DiscardSafely -> 0x0233, RetryDirectly -> 0x0262 }
        r14.trialConnect();	 Catch:{ IOException -> 0x017a, IllegalAccessException -> 0x0223, InterruptedException -> 0x022b, IllegalArgumentException -> 0x02a0, FileDownloadSecurityException -> 0x02a4, FileDownloadGiveUpRetryException -> 0x02a8, DiscardSafely -> 0x0233, RetryDirectly -> 0x0262 }
        r14.checkupAfterGetFilename();	 Catch:{ IOException -> 0x017a, IllegalAccessException -> 0x0223, InterruptedException -> 0x022b, IllegalArgumentException -> 0x02a0, FileDownloadSecurityException -> 0x02a4, FileDownloadGiveUpRetryException -> 0x02a8, DiscardSafely -> 0x0233, RetryDirectly -> 0x0262 }
        r5 = r14.database;	 Catch:{ IOException -> 0x017a, IllegalAccessException -> 0x0223, InterruptedException -> 0x022b, IllegalArgumentException -> 0x02a0, FileDownloadSecurityException -> 0x02a4, FileDownloadGiveUpRetryException -> 0x02a8, DiscardSafely -> 0x0233, RetryDirectly -> 0x0262 }
        r10 = r14.model;	 Catch:{ IOException -> 0x017a, IllegalAccessException -> 0x0223, InterruptedException -> 0x022b, IllegalArgumentException -> 0x02a0, FileDownloadSecurityException -> 0x02a4, FileDownloadGiveUpRetryException -> 0x02a8, DiscardSafely -> 0x0233, RetryDirectly -> 0x0262 }
        r10 = r10.getId();	 Catch:{ IOException -> 0x017a, IllegalAccessException -> 0x0223, InterruptedException -> 0x022b, IllegalArgumentException -> 0x02a0, FileDownloadSecurityException -> 0x02a4, FileDownloadGiveUpRetryException -> 0x02a8, DiscardSafely -> 0x0233, RetryDirectly -> 0x0262 }
        r1 = r5.findConnectionModel(r10);	 Catch:{ IOException -> 0x017a, IllegalAccessException -> 0x0223, InterruptedException -> 0x022b, IllegalArgumentException -> 0x02a0, FileDownloadSecurityException -> 0x02a4, FileDownloadGiveUpRetryException -> 0x02a8, DiscardSafely -> 0x0233, RetryDirectly -> 0x0262 }
        r14.inspectTaskModelResumeAvailableOnDB(r1);	 Catch:{ IOException -> 0x017a, IllegalAccessException -> 0x0223, InterruptedException -> 0x022b, IllegalArgumentException -> 0x02a0, FileDownloadSecurityException -> 0x02a4, FileDownloadGiveUpRetryException -> 0x02a8, DiscardSafely -> 0x0233, RetryDirectly -> 0x0262 }
        r5 = r14.paused;	 Catch:{ IOException -> 0x017a, IllegalAccessException -> 0x0223, InterruptedException -> 0x022b, IllegalArgumentException -> 0x02a0, FileDownloadSecurityException -> 0x02a4, FileDownloadGiveUpRetryException -> 0x02a8, DiscardSafely -> 0x0233, RetryDirectly -> 0x0262 }
        if (r5 == 0) goto L_0x014e;
    L_0x011a:
        r5 = r14.model;	 Catch:{ IOException -> 0x017a, IllegalAccessException -> 0x0223, InterruptedException -> 0x022b, IllegalArgumentException -> 0x02a0, FileDownloadSecurityException -> 0x02a4, FileDownloadGiveUpRetryException -> 0x02a8, DiscardSafely -> 0x0233, RetryDirectly -> 0x0262 }
        r10 = -2;
        r5.setStatus(r10);	 Catch:{ IOException -> 0x017a, IllegalAccessException -> 0x0223, InterruptedException -> 0x022b, IllegalArgumentException -> 0x02a0, FileDownloadSecurityException -> 0x02a4, FileDownloadGiveUpRetryException -> 0x02a8, DiscardSafely -> 0x0233, RetryDirectly -> 0x0262 }
        r5 = r14.statusCallback;
        r5.discardAllMessage();
        r5 = r14.paused;
        if (r5 == 0) goto L_0x0135;
    L_0x0129:
        r5 = r14.statusCallback;
        r5.onPausedDirectly();
    L_0x012e:
        r5 = r14.alive;
        r5.set(r9);
        goto L_0x0045;
    L_0x0135:
        r5 = r14.error;
        if (r5 == 0) goto L_0x0141;
    L_0x0139:
        r5 = r14.statusCallback;
        r8 = r14.errorException;
        r5.onErrorDirectly(r8);
        goto L_0x012e;
    L_0x0141:
        r5 = r14.statusCallback;	 Catch:{ IOException -> 0x0147 }
        r5.onCompletedDirectly();	 Catch:{ IOException -> 0x0147 }
        goto L_0x012e;
    L_0x0147:
        r3 = move-exception;
        r5 = r14.statusCallback;
        r5.onErrorDirectly(r3);
        goto L_0x012e;
    L_0x014e:
        r5 = r14.model;	 Catch:{ IOException -> 0x017a, IllegalAccessException -> 0x0223, InterruptedException -> 0x022b, IllegalArgumentException -> 0x02a0, FileDownloadSecurityException -> 0x02a4, FileDownloadGiveUpRetryException -> 0x02a8, DiscardSafely -> 0x0233, RetryDirectly -> 0x0262 }
        r6 = r5.getTotal();	 Catch:{ IOException -> 0x017a, IllegalAccessException -> 0x0223, InterruptedException -> 0x022b, IllegalArgumentException -> 0x02a0, FileDownloadSecurityException -> 0x02a4, FileDownloadGiveUpRetryException -> 0x02a8, DiscardSafely -> 0x0233, RetryDirectly -> 0x0262 }
        r5 = r14.model;	 Catch:{ IOException -> 0x017a, IllegalAccessException -> 0x0223, InterruptedException -> 0x022b, IllegalArgumentException -> 0x02a0, FileDownloadSecurityException -> 0x02a4, FileDownloadGiveUpRetryException -> 0x02a8, DiscardSafely -> 0x0233, RetryDirectly -> 0x0262 }
        r5 = r5.getTempFilePath();	 Catch:{ IOException -> 0x017a, IllegalAccessException -> 0x0223, InterruptedException -> 0x022b, IllegalArgumentException -> 0x02a0, FileDownloadSecurityException -> 0x02a4, FileDownloadGiveUpRetryException -> 0x02a8, DiscardSafely -> 0x0233, RetryDirectly -> 0x0262 }
        r14.handlePreAllocate(r6, r5);	 Catch:{ IOException -> 0x017a, IllegalAccessException -> 0x0223, InterruptedException -> 0x022b, IllegalArgumentException -> 0x02a0, FileDownloadSecurityException -> 0x02a4, FileDownloadGiveUpRetryException -> 0x02a8, DiscardSafely -> 0x0233, RetryDirectly -> 0x0262 }
        r0 = r14.calcConnectionCount(r6);	 Catch:{ IOException -> 0x017a, IllegalAccessException -> 0x0223, InterruptedException -> 0x022b, IllegalArgumentException -> 0x02a0, FileDownloadSecurityException -> 0x02a4, FileDownloadGiveUpRetryException -> 0x02a8, DiscardSafely -> 0x0233, RetryDirectly -> 0x0262 }
        if (r0 > 0) goto L_0x0187;
    L_0x0163:
        r5 = new java.lang.IllegalAccessException;	 Catch:{ IOException -> 0x017a, IllegalAccessException -> 0x0223, InterruptedException -> 0x022b, IllegalArgumentException -> 0x02a0, FileDownloadSecurityException -> 0x02a4, FileDownloadGiveUpRetryException -> 0x02a8, DiscardSafely -> 0x0233, RetryDirectly -> 0x0262 }
        r10 = "invalid connection count %d, the connection count must be larger than 0";
        r11 = 1;
        r11 = new java.lang.Object[r11];	 Catch:{ IOException -> 0x017a, IllegalAccessException -> 0x0223, InterruptedException -> 0x022b, IllegalArgumentException -> 0x02a0, FileDownloadSecurityException -> 0x02a4, FileDownloadGiveUpRetryException -> 0x02a8, DiscardSafely -> 0x0233, RetryDirectly -> 0x0262 }
        r12 = 0;
        r13 = java.lang.Integer.valueOf(r0);	 Catch:{ IOException -> 0x017a, IllegalAccessException -> 0x0223, InterruptedException -> 0x022b, IllegalArgumentException -> 0x02a0, FileDownloadSecurityException -> 0x02a4, FileDownloadGiveUpRetryException -> 0x02a8, DiscardSafely -> 0x0233, RetryDirectly -> 0x0262 }
        r11[r12] = r13;	 Catch:{ IOException -> 0x017a, IllegalAccessException -> 0x0223, InterruptedException -> 0x022b, IllegalArgumentException -> 0x02a0, FileDownloadSecurityException -> 0x02a4, FileDownloadGiveUpRetryException -> 0x02a8, DiscardSafely -> 0x0233, RetryDirectly -> 0x0262 }
        r10 = com.liulishuo.filedownloader.util.FileDownloadUtils.formatString(r10, r11);	 Catch:{ IOException -> 0x017a, IllegalAccessException -> 0x0223, InterruptedException -> 0x022b, IllegalArgumentException -> 0x02a0, FileDownloadSecurityException -> 0x02a4, FileDownloadGiveUpRetryException -> 0x02a8, DiscardSafely -> 0x0233, RetryDirectly -> 0x0262 }
        r5.<init>(r10);	 Catch:{ IOException -> 0x017a, IllegalAccessException -> 0x0223, InterruptedException -> 0x022b, IllegalArgumentException -> 0x02a0, FileDownloadSecurityException -> 0x02a4, FileDownloadGiveUpRetryException -> 0x02a8, DiscardSafely -> 0x0233, RetryDirectly -> 0x0262 }
        throw r5;	 Catch:{ IOException -> 0x017a, IllegalAccessException -> 0x0223, InterruptedException -> 0x022b, IllegalArgumentException -> 0x02a0, FileDownloadSecurityException -> 0x02a4, FileDownloadGiveUpRetryException -> 0x02a8, DiscardSafely -> 0x0233, RetryDirectly -> 0x0262 }
    L_0x017a:
        r5 = move-exception;
        r3 = r5;
    L_0x017c:
        r5 = r14.isRetry(r3);	 Catch:{ all -> 0x007b }
        if (r5 == 0) goto L_0x022f;
    L_0x0182:
        r14.onRetry(r3);	 Catch:{ all -> 0x007b }
        goto L_0x00b2;
    L_0x0187:
        r10 = 0;
        r5 = (r6 > r10 ? 1 : (r6 == r10 ? 0 : -1));
        if (r5 != 0) goto L_0x01bb;
    L_0x018d:
        r5 = r14.statusCallback;
        r5.discardAllMessage();
        r5 = r14.paused;
        if (r5 == 0) goto L_0x01a2;
    L_0x0196:
        r5 = r14.statusCallback;
        r5.onPausedDirectly();
    L_0x019b:
        r5 = r14.alive;
        r5.set(r9);
        goto L_0x0045;
    L_0x01a2:
        r5 = r14.error;
        if (r5 == 0) goto L_0x01ae;
    L_0x01a6:
        r5 = r14.statusCallback;
        r8 = r14.errorException;
        r5.onErrorDirectly(r8);
        goto L_0x019b;
    L_0x01ae:
        r5 = r14.statusCallback;	 Catch:{ IOException -> 0x01b4 }
        r5.onCompletedDirectly();	 Catch:{ IOException -> 0x01b4 }
        goto L_0x019b;
    L_0x01b4:
        r3 = move-exception;
        r5 = r14.statusCallback;
        r5.onErrorDirectly(r3);
        goto L_0x019b;
    L_0x01bb:
        r5 = r14.paused;	 Catch:{ IOException -> 0x017a, IllegalAccessException -> 0x0223, InterruptedException -> 0x022b, IllegalArgumentException -> 0x02a0, FileDownloadSecurityException -> 0x02a4, FileDownloadGiveUpRetryException -> 0x02a8, DiscardSafely -> 0x0233, RetryDirectly -> 0x0262 }
        if (r5 == 0) goto L_0x01f3;
    L_0x01bf:
        r5 = r14.model;	 Catch:{ IOException -> 0x017a, IllegalAccessException -> 0x0223, InterruptedException -> 0x022b, IllegalArgumentException -> 0x02a0, FileDownloadSecurityException -> 0x02a4, FileDownloadGiveUpRetryException -> 0x02a8, DiscardSafely -> 0x0233, RetryDirectly -> 0x0262 }
        r10 = -2;
        r5.setStatus(r10);	 Catch:{ IOException -> 0x017a, IllegalAccessException -> 0x0223, InterruptedException -> 0x022b, IllegalArgumentException -> 0x02a0, FileDownloadSecurityException -> 0x02a4, FileDownloadGiveUpRetryException -> 0x02a8, DiscardSafely -> 0x0233, RetryDirectly -> 0x0262 }
        r5 = r14.statusCallback;
        r5.discardAllMessage();
        r5 = r14.paused;
        if (r5 == 0) goto L_0x01da;
    L_0x01ce:
        r5 = r14.statusCallback;
        r5.onPausedDirectly();
    L_0x01d3:
        r5 = r14.alive;
        r5.set(r9);
        goto L_0x0045;
    L_0x01da:
        r5 = r14.error;
        if (r5 == 0) goto L_0x01e6;
    L_0x01de:
        r5 = r14.statusCallback;
        r8 = r14.errorException;
        r5.onErrorDirectly(r8);
        goto L_0x01d3;
    L_0x01e6:
        r5 = r14.statusCallback;	 Catch:{ IOException -> 0x01ec }
        r5.onCompletedDirectly();	 Catch:{ IOException -> 0x01ec }
        goto L_0x01d3;
    L_0x01ec:
        r3 = move-exception;
        r5 = r14.statusCallback;
        r5.onErrorDirectly(r3);
        goto L_0x01d3;
    L_0x01f3:
        if (r0 != r8) goto L_0x0214;
    L_0x01f5:
        r5 = r8;
    L_0x01f6:
        r14.isSingleConnection = r5;	 Catch:{ IOException -> 0x017a, IllegalAccessException -> 0x0223, InterruptedException -> 0x022b, IllegalArgumentException -> 0x02a0, FileDownloadSecurityException -> 0x02a4, FileDownloadGiveUpRetryException -> 0x02a8, DiscardSafely -> 0x0233, RetryDirectly -> 0x0262 }
        r5 = r14.isSingleConnection;	 Catch:{ IOException -> 0x017a, IllegalAccessException -> 0x0223, InterruptedException -> 0x022b, IllegalArgumentException -> 0x02a0, FileDownloadSecurityException -> 0x02a4, FileDownloadGiveUpRetryException -> 0x02a8, DiscardSafely -> 0x0233, RetryDirectly -> 0x0262 }
        if (r5 == 0) goto L_0x0216;
    L_0x01fc:
        r14.realDownloadWithSingleConnection(r6);	 Catch:{ IOException -> 0x017a, IllegalAccessException -> 0x0223, InterruptedException -> 0x022b, IllegalArgumentException -> 0x02a0, FileDownloadSecurityException -> 0x02a4, FileDownloadGiveUpRetryException -> 0x02a8, DiscardSafely -> 0x0233, RetryDirectly -> 0x0262 }
    L_0x01ff:
        r5 = r14.statusCallback;
        r5.discardAllMessage();
        r5 = r14.paused;
        if (r5 == 0) goto L_0x026b;
    L_0x0208:
        r5 = r14.statusCallback;
        r5.onPausedDirectly();
    L_0x020d:
        r5 = r14.alive;
        r5.set(r9);
        goto L_0x0045;
    L_0x0214:
        r5 = r9;
        goto L_0x01f6;
    L_0x0216:
        r5 = r14.statusCallback;	 Catch:{ IOException -> 0x017a, IllegalAccessException -> 0x0223, InterruptedException -> 0x022b, IllegalArgumentException -> 0x02a0, FileDownloadSecurityException -> 0x02a4, FileDownloadGiveUpRetryException -> 0x02a8, DiscardSafely -> 0x0233, RetryDirectly -> 0x0262 }
        r5.onMultiConnection();	 Catch:{ IOException -> 0x017a, IllegalAccessException -> 0x0223, InterruptedException -> 0x022b, IllegalArgumentException -> 0x02a0, FileDownloadSecurityException -> 0x02a4, FileDownloadGiveUpRetryException -> 0x02a8, DiscardSafely -> 0x0233, RetryDirectly -> 0x0262 }
        r5 = r14.isResumeAvailableOnDB;	 Catch:{ IOException -> 0x017a, IllegalAccessException -> 0x0223, InterruptedException -> 0x022b, IllegalArgumentException -> 0x02a0, FileDownloadSecurityException -> 0x02a4, FileDownloadGiveUpRetryException -> 0x02a8, DiscardSafely -> 0x0233, RetryDirectly -> 0x0262 }
        if (r5 == 0) goto L_0x0227;
    L_0x021f:
        r14.realDownloadWithMultiConnectionFromResume(r0, r1);	 Catch:{ IOException -> 0x017a, IllegalAccessException -> 0x0223, InterruptedException -> 0x022b, IllegalArgumentException -> 0x02a0, FileDownloadSecurityException -> 0x02a4, FileDownloadGiveUpRetryException -> 0x02a8, DiscardSafely -> 0x0233, RetryDirectly -> 0x0262 }
        goto L_0x01ff;
    L_0x0223:
        r5 = move-exception;
        r3 = r5;
        goto L_0x017c;
    L_0x0227:
        r14.realDownloadWithMultiConnectionFromBeginning(r6, r0);	 Catch:{ IOException -> 0x017a, IllegalAccessException -> 0x0223, InterruptedException -> 0x022b, IllegalArgumentException -> 0x02a0, FileDownloadSecurityException -> 0x02a4, FileDownloadGiveUpRetryException -> 0x02a8, DiscardSafely -> 0x0233, RetryDirectly -> 0x0262 }
        goto L_0x01ff;
    L_0x022b:
        r5 = move-exception;
        r3 = r5;
        goto L_0x017c;
    L_0x022f:
        r14.onError(r3);	 Catch:{ all -> 0x007b }
        goto L_0x01ff;
    L_0x0233:
        r2 = move-exception;
        r5 = r14.statusCallback;
        r5.discardAllMessage();
        r5 = r14.paused;
        if (r5 == 0) goto L_0x0249;
    L_0x023d:
        r5 = r14.statusCallback;
        r5.onPausedDirectly();
    L_0x0242:
        r5 = r14.alive;
        r5.set(r9);
        goto L_0x0045;
    L_0x0249:
        r5 = r14.error;
        if (r5 == 0) goto L_0x0255;
    L_0x024d:
        r5 = r14.statusCallback;
        r8 = r14.errorException;
        r5.onErrorDirectly(r8);
        goto L_0x0242;
    L_0x0255:
        r5 = r14.statusCallback;	 Catch:{ IOException -> 0x025b }
        r5.onCompletedDirectly();	 Catch:{ IOException -> 0x025b }
        goto L_0x0242;
    L_0x025b:
        r3 = move-exception;
        r5 = r14.statusCallback;
        r5.onErrorDirectly(r3);
        goto L_0x0242;
    L_0x0262:
        r4 = move-exception;
        r5 = r14.model;	 Catch:{ all -> 0x007b }
        r10 = 5;
        r5.setStatus(r10);	 Catch:{ all -> 0x007b }
        goto L_0x00b2;
    L_0x026b:
        r5 = r14.error;
        if (r5 == 0) goto L_0x0277;
    L_0x026f:
        r5 = r14.statusCallback;
        r8 = r14.errorException;
        r5.onErrorDirectly(r8);
        goto L_0x020d;
    L_0x0277:
        r5 = r14.statusCallback;	 Catch:{ IOException -> 0x027d }
        r5.onCompletedDirectly();	 Catch:{ IOException -> 0x027d }
        goto L_0x020d;
    L_0x027d:
        r3 = move-exception;
        r5 = r14.statusCallback;
        r5.onErrorDirectly(r3);
        goto L_0x020d;
    L_0x0284:
        r8 = r14.error;
        if (r8 == 0) goto L_0x0291;
    L_0x0288:
        r8 = r14.statusCallback;
        r10 = r14.errorException;
        r8.onErrorDirectly(r10);
        goto L_0x008a;
    L_0x0291:
        r8 = r14.statusCallback;	 Catch:{ IOException -> 0x0298 }
        r8.onCompletedDirectly();	 Catch:{ IOException -> 0x0298 }
        goto L_0x008a;
    L_0x0298:
        r3 = move-exception;
        r8 = r14.statusCallback;
        r8.onErrorDirectly(r3);
        goto L_0x008a;
    L_0x02a0:
        r5 = move-exception;
        r3 = r5;
        goto L_0x017c;
    L_0x02a4:
        r5 = move-exception;
        r3 = r5;
        goto L_0x017c;
    L_0x02a8:
        r5 = move-exception;
        r3 = r5;
        goto L_0x017c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.liulishuo.filedownloader.download.DownloadLaunchRunnable.run():void");
    }

    private int calcConnectionCount(long totalLength) {
        if (!isMultiConnectionAvailable()) {
            return 1;
        }
        if (this.isResumeAvailableOnDB) {
            return this.model.getConnectionCount();
        }
        return CustomComponentHolder.getImpl().determineConnectionCount(this.model.getId(), this.model.getUrl(), this.model.getPath(), totalLength);
    }

    private void trialConnect() throws IOException, RetryDirectly, IllegalAccessException, FileDownloadSecurityException {
        FileDownloadConnection trialConnection = null;
        try {
            ConnectionProfile trialConnectionProfile;
            if (this.isNeedForceDiscardRange) {
                trialConnectionProfile = ConnectionProfileBuild.buildTrialConnectionProfileNoRange();
            } else {
                trialConnectionProfile = ConnectionProfileBuild.buildTrialConnectionProfile();
            }
            ConnectTask trialConnectTask = new Builder().setDownloadId(this.model.getId()).setUrl(this.model.getUrl()).setEtag(this.model.getETag()).setHeader(this.userRequestHeader).setConnectionProfile(trialConnectionProfile).build();
            trialConnection = trialConnectTask.connect();
            handleTrialConnectResult(trialConnectTask.getRequestHeader(), trialConnectTask, trialConnection);
        } finally {
            if (trialConnection != null) {
                trialConnection.ending();
            }
        }
    }

    private boolean isMultiConnectionAvailable() {
        boolean z = true;
        if (this.isResumeAvailableOnDB && this.model.getConnectionCount() <= 1) {
            return false;
        }
        if (!(this.acceptPartial && this.supportSeek && !this.isChunked)) {
            z = false;
        }
        return z;
    }

    private int determineConnectionCount() {
        return 5;
    }

    void inspectTaskModelResumeAvailableOnDB(List<ConnectionModel> connectionOnDBList) {
        boolean isMultiConnection;
        long offset;
        boolean z = true;
        int connectionCount = this.model.getConnectionCount();
        String tempFilePath = this.model.getTempFilePath();
        String targetFilePath = this.model.getTargetFilePath();
        if (connectionCount > 1) {
            isMultiConnection = true;
        } else {
            isMultiConnection = false;
        }
        if (this.isNeedForceDiscardRange) {
            offset = 0;
        } else if (isMultiConnection && !this.supportSeek) {
            offset = 0;
        } else if (!FileDownloadUtils.isBreakpointAvailable(this.model.getId(), this.model)) {
            offset = 0;
        } else if (!this.supportSeek) {
            offset = new File(tempFilePath).length();
        } else if (!isMultiConnection) {
            offset = this.model.getSoFar();
        } else if (connectionCount != connectionOnDBList.size()) {
            offset = 0;
        } else {
            offset = ConnectionModel.getTotalOffset(connectionOnDBList);
        }
        this.model.setSoFar(offset);
        if (offset <= 0) {
            z = false;
        }
        this.isResumeAvailableOnDB = z;
        if (!this.isResumeAvailableOnDB) {
            this.database.removeConnections(this.model.getId());
            FileDownloadUtils.deleteTaskFiles(targetFilePath, tempFilePath);
        }
    }

    private void handleTrialConnectResult(Map<String, List<String>> requestHeader, ConnectTask connectTask, FileDownloadConnection connection) throws IOException, RetryDirectly, IllegalArgumentException, FileDownloadSecurityException {
        int id = this.model.getId();
        int code = connection.getResponseCode();
        this.acceptPartial = FileDownloadUtils.isAcceptRange(code, connection);
        boolean onlyFromBeginning = code == 200 || code == Constants.COMMAND_PING || code == 0;
        String oldEtag = this.model.getETag();
        String newEtag = FileDownloadUtils.findEtag(id, connection);
        boolean isPreconditionFailed = false;
        if (code == 412) {
            isPreconditionFailed = true;
        } else if (oldEtag != null && !oldEtag.equals(newEtag) && (onlyFromBeginning || this.acceptPartial)) {
            isPreconditionFailed = true;
        } else if (code == Constants.COMMAND_PING && connectTask.isRangeNotFromBeginning()) {
            isPreconditionFailed = true;
        } else if (code == HTTP_REQUESTED_RANGE_NOT_SATISFIABLE) {
            if (this.model.getSoFar() > 0) {
                isPreconditionFailed = true;
            } else if (!this.isNeedForceDiscardRange) {
                this.isNeedForceDiscardRange = true;
                isPreconditionFailed = true;
            }
        }
        if (isPreconditionFailed) {
            if (this.isResumeAvailableOnDB) {
                FileDownloadLog.w(this, "there is precondition failed on this request[%d] with old etag[%s]、new etag[%s]、response code is %d", Integer.valueOf(id), oldEtag, newEtag, Integer.valueOf(code));
            }
            this.database.removeConnections(this.model.getId());
            FileDownloadUtils.deleteTaskFiles(this.model.getTargetFilePath(), this.model.getTempFilePath());
            this.isResumeAvailableOnDB = false;
            if (oldEtag != null && oldEtag.equals(newEtag)) {
                FileDownloadLog.w(this, "the old etag[%s] is the same to the new etag[%s], but the response status code is %d not Partial(206), so wo have to start this task from very beginning for task[%d]!", oldEtag, newEtag, Integer.valueOf(code), Integer.valueOf(id));
                newEtag = null;
            }
            this.model.setSoFar(0);
            this.model.setTotal(0);
            this.model.setETag(newEtag);
            this.model.resetConnectionCount();
            this.database.updateOldEtagOverdue(id, this.model.getETag(), this.model.getSoFar(), this.model.getTotal(), this.model.getConnectionCount());
            throw new RetryDirectly();
        }
        this.redirectedUrl = connectTask.getFinalRedirectedUrl();
        if (this.acceptPartial || onlyFromBeginning) {
            long totalLength = FileDownloadUtils.findInstanceLengthForTrial(connection);
            String fileName = null;
            if (this.model.isPathAsDirectory()) {
                fileName = FileDownloadUtils.findFilename(connection, this.model.getUrl());
            }
            this.isChunked = totalLength == -1;
            DownloadStatusCallback downloadStatusCallback = this.statusCallback;
            boolean z = this.isResumeAvailableOnDB && this.acceptPartial;
            downloadStatusCallback.onConnected(z, totalLength, newEtag, fileName);
            return;
        }
        throw new FileDownloadHttpException(code, requestHeader, connection.getResponseHeaderFields());
    }

    private void realDownloadWithSingleConnection(long totalLength) throws IOException, IllegalAccessException {
        ConnectionProfile profile;
        if (this.acceptPartial) {
            profile = ConnectionProfileBuild.buildToEndConnectionProfile(this.model.getSoFar(), this.model.getSoFar(), totalLength - this.model.getSoFar());
        } else {
            this.model.setSoFar(0);
            profile = ConnectionProfileBuild.buildBeginToEndConnectionProfile(totalLength);
        }
        this.singleDownloadRunnable = new com.liulishuo.filedownloader.download.DownloadRunnable.Builder().setId(this.model.getId()).setConnectionIndex(Integer.valueOf(-1)).setCallback(this).setUrl(this.model.getUrl()).setEtag(this.model.getETag()).setHeader(this.userRequestHeader).setWifiRequired(this.isWifiRequired).setConnectionModel(profile).setPath(this.model.getTempFilePath()).build();
        this.model.setConnectionCount(1);
        this.database.updateConnectionCount(this.model.getId(), 1);
        if (this.paused) {
            this.model.setStatus((byte) -2);
            this.singleDownloadRunnable.pause();
            return;
        }
        this.singleDownloadRunnable.run();
    }

    private void realDownloadWithMultiConnectionFromResume(int connectionCount, List<ConnectionModel> modelList) throws InterruptedException {
        if (connectionCount <= 1 || modelList.size() != connectionCount) {
            throw new IllegalArgumentException();
        }
        fetchWithMultipleConnection(modelList, this.model.getTotal());
    }

    private void realDownloadWithMultiConnectionFromBeginning(long totalLength, int connectionCount) throws InterruptedException {
        long startOffset = 0;
        long eachRegion = totalLength / ((long) connectionCount);
        int id = this.model.getId();
        List<ConnectionModel> connectionModelList = new ArrayList();
        for (int i = 0; i < connectionCount; i++) {
            long endOffset;
            if (i == connectionCount - 1) {
                endOffset = -1;
            } else {
                endOffset = (startOffset + eachRegion) - 1;
            }
            ConnectionModel connectionModel = new ConnectionModel();
            connectionModel.setId(id);
            connectionModel.setIndex(i);
            connectionModel.setStartOffset(startOffset);
            connectionModel.setCurrentOffset(startOffset);
            connectionModel.setEndOffset(endOffset);
            connectionModelList.add(connectionModel);
            this.database.insertConnectionModel(connectionModel);
            startOffset += eachRegion;
        }
        this.model.setConnectionCount(connectionCount);
        this.database.updateConnectionCount(id, connectionCount);
        fetchWithMultipleConnection(connectionModelList, totalLength);
    }

    private void fetchWithMultipleConnection(List<ConnectionModel> connectionModelList, long totalLength) throws InterruptedException {
        String url;
        DownloadRunnable runnable;
        int id = this.model.getId();
        String etag = this.model.getETag();
        if (this.redirectedUrl != null) {
            url = this.redirectedUrl;
        } else {
            url = this.model.getUrl();
        }
        String path = this.model.getTempFilePath();
        if (FileDownloadLog.NEED_LOG) {
            FileDownloadLog.d(this, "fetch data with multiple connection(count: [%d]) for task[%d] totalLength[%d]", Integer.valueOf(connectionModelList.size()), Integer.valueOf(id), Long.valueOf(totalLength));
        }
        long totalOffset = 0;
        boolean withEtag = this.isResumeAvailableOnDB;
        for (ConnectionModel connectionModel : connectionModelList) {
            long contentLength;
            if (connectionModel.getEndOffset() == -1) {
                contentLength = totalLength - connectionModel.getCurrentOffset();
            } else {
                contentLength = (connectionModel.getEndOffset() - connectionModel.getCurrentOffset()) + 1;
            }
            totalOffset += connectionModel.getCurrentOffset() - connectionModel.getStartOffset();
            if (contentLength != 0) {
                runnable = new com.liulishuo.filedownloader.download.DownloadRunnable.Builder().setId(id).setConnectionIndex(Integer.valueOf(connectionModel.getIndex())).setCallback(this).setUrl(url).setEtag(withEtag ? etag : null).setHeader(this.userRequestHeader).setWifiRequired(this.isWifiRequired).setConnectionModel(ConnectionProfileBuild.buildConnectionProfile(connectionModel.getStartOffset(), connectionModel.getCurrentOffset(), connectionModel.getEndOffset(), contentLength)).setPath(path).build();
                if (FileDownloadLog.NEED_LOG) {
                    FileDownloadLog.d(this, "enable multiple connection: %s", connectionModel);
                }
                if (runnable == null) {
                    throw new IllegalArgumentException("the download runnable must not be null!");
                }
                this.downloadRunnableList.add(runnable);
            } else if (FileDownloadLog.NEED_LOG) {
                FileDownloadLog.d(this, "pass connection[%d-%d], because it has been completed", Integer.valueOf(connectionModel.getId()), Integer.valueOf(connectionModel.getIndex()));
            }
        }
        if (totalOffset != this.model.getSoFar()) {
            FileDownloadLog.w(this, "correct the sofar[%d] from connection table[%d]", Long.valueOf(this.model.getSoFar()), Long.valueOf(totalOffset));
            this.model.setSoFar(totalOffset);
        }
        List<Callable<Object>> arrayList = new ArrayList(this.downloadRunnableList.size());
        Iterator it = this.downloadRunnableList.iterator();
        while (it.hasNext()) {
            runnable = (DownloadRunnable) it.next();
            if (this.paused) {
                runnable.pause();
            } else {
                arrayList.add(Executors.callable(runnable));
            }
        }
        if (this.paused) {
            this.model.setStatus((byte) -2);
            return;
        }
        List<Future<Object>> subTaskFutures = DOWNLOAD_EXECUTOR.invokeAll(arrayList);
        if (FileDownloadLog.NEED_LOG) {
            for (Future<Object> future : subTaskFutures) {
                FileDownloadLog.d(this, "finish sub-task for [%d] %B %B", Integer.valueOf(id), Boolean.valueOf(future.isDone()), Boolean.valueOf(future.isCancelled()));
            }
        }
    }

    private void handlePreAllocate(long totalLength, String path) throws IOException, IllegalAccessException {
        FileDownloadOutputStream outputStream = null;
        if (totalLength != -1) {
            try {
                outputStream = FileDownloadUtils.createOutputStream(this.model.getTempFilePath());
                long breakpointBytes = new File(path).length();
                long requiredSpaceBytes = totalLength - breakpointBytes;
                long freeSpaceBytes = FileDownloadUtils.getFreeSpaceBytes(path);
                if (freeSpaceBytes < requiredSpaceBytes) {
                    throw new FileDownloadOutOfSpaceException(freeSpaceBytes, requiredSpaceBytes, breakpointBytes);
                } else if (!FileDownloadProperties.getImpl().fileNonPreAllocation) {
                    outputStream.setLength(totalLength);
                }
            } catch (Throwable th) {
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        }
        if (outputStream != null) {
            outputStream.close();
        }
    }

    public void onProgress(long increaseBytes) {
        if (!this.paused) {
            this.statusCallback.onProgress(increaseBytes);
        }
    }

    public void onCompleted(DownloadRunnable doneRunnable, long startOffset, long endOffset) {
        if (!this.paused) {
            int doneConnectionIndex = doneRunnable.connectionIndex;
            if (FileDownloadLog.NEED_LOG) {
                FileDownloadLog.d(this, "the connection has been completed(%d): [%d, %d)  %d", Integer.valueOf(doneConnectionIndex), Long.valueOf(startOffset), Long.valueOf(endOffset), Long.valueOf(this.model.getTotal()));
            }
            if (!this.isSingleConnection) {
                synchronized (this.downloadRunnableList) {
                    this.downloadRunnableList.remove(doneRunnable);
                }
            } else if (startOffset != 0 && endOffset != this.model.getTotal()) {
                FileDownloadLog.e(this, "the single task not completed corrected(%d, %d != %d) for task(%d)", Long.valueOf(startOffset), Long.valueOf(endOffset), Long.valueOf(this.model.getTotal()), Integer.valueOf(this.model.getId()));
            }
        } else if (FileDownloadLog.NEED_LOG) {
            FileDownloadLog.d(this, "the task[%d] has already been paused, so pass the completed callback", Integer.valueOf(this.model.getId()));
        }
    }

    public boolean isRetry(Exception exception) {
        if (exception instanceof FileDownloadHttpException) {
            int code = ((FileDownloadHttpException) exception).getCode();
            if (this.isSingleConnection && code == HTTP_REQUESTED_RANGE_NOT_SATISFIABLE && !this.isTriedFixRangeNotSatisfiable) {
                FileDownloadUtils.deleteTaskFiles(this.model.getTargetFilePath(), this.model.getTempFilePath());
                this.isTriedFixRangeNotSatisfiable = true;
                return true;
            }
        }
        if (this.validRetryTimes <= 0 || (exception instanceof FileDownloadGiveUpRetryException)) {
            return false;
        }
        return true;
    }

    public void onError(Exception exception) {
        this.error = true;
        this.errorException = exception;
        if (!this.paused) {
            Iterator it = ((ArrayList) this.downloadRunnableList.clone()).iterator();
            while (it.hasNext()) {
                DownloadRunnable runnable = (DownloadRunnable) it.next();
                if (runnable != null) {
                    runnable.discard();
                }
            }
        } else if (FileDownloadLog.NEED_LOG) {
            FileDownloadLog.d(this, "the task[%d] has already been paused, so pass the error callback", Integer.valueOf(this.model.getId()));
        }
    }

    public void onRetry(Exception exception) {
        if (!this.paused) {
            int i = this.validRetryTimes;
            this.validRetryTimes = i - 1;
            if (i < 0) {
                FileDownloadLog.e(this, "valid retry times is less than 0(%d) for download task(%d)", Integer.valueOf(this.validRetryTimes), Integer.valueOf(this.model.getId()));
            }
            this.statusCallback.onRetry(exception, this.validRetryTimes);
        } else if (FileDownloadLog.NEED_LOG) {
            FileDownloadLog.d(this, "the task[%d] has already been paused, so pass the retry callback", Integer.valueOf(this.model.getId()));
        }
    }

    public void syncProgressFromCache() {
        this.database.updateProgress(this.model.getId(), this.model.getSoFar());
    }

    private void checkupBeforeConnect() throws FileDownloadGiveUpRetryException {
        if (this.isWifiRequired && !FileDownloadUtils.checkPermission(MsgConstant.PERMISSION_ACCESS_NETWORK_STATE)) {
            throw new FileDownloadGiveUpRetryException(FileDownloadUtils.formatString("Task[%d] can't start the download runnable, because this task require wifi, but user application nor current process has %s, so we can't check whether the network type connection.", Integer.valueOf(this.model.getId()), MsgConstant.PERMISSION_ACCESS_NETWORK_STATE));
        } else if (this.isWifiRequired && FileDownloadUtils.isNetworkNotOnWifiType()) {
            throw new FileDownloadNetworkPolicyException();
        }
    }

    private void checkupAfterGetFilename() throws RetryDirectly, DiscardSafely {
        int id = this.model.getId();
        if (this.model.isPathAsDirectory()) {
            String targetFilePath = this.model.getTargetFilePath();
            int fileCaseId = FileDownloadUtils.generateId(this.model.getUrl(), targetFilePath);
            if (FileDownloadHelper.inspectAndInflowDownloaded(id, targetFilePath, this.isForceReDownload, false)) {
                this.database.remove(id);
                this.database.removeConnections(id);
                throw new DiscardSafely();
            }
            FileDownloadModel fileCaseModel = this.database.find(fileCaseId);
            if (fileCaseModel != null) {
                if (FileDownloadHelper.inspectAndInflowDownloading(id, fileCaseModel, this.threadPoolMonitor, false)) {
                    this.database.remove(id);
                    this.database.removeConnections(id);
                    throw new DiscardSafely();
                }
                List<ConnectionModel> connectionModelList = this.database.findConnectionModel(fileCaseId);
                this.database.remove(fileCaseId);
                this.database.removeConnections(fileCaseId);
                FileDownloadUtils.deleteTargetFile(this.model.getTargetFilePath());
                if (FileDownloadUtils.isBreakpointAvailable(fileCaseId, fileCaseModel)) {
                    this.model.setSoFar(fileCaseModel.getSoFar());
                    this.model.setTotal(fileCaseModel.getTotal());
                    this.model.setETag(fileCaseModel.getETag());
                    this.model.setConnectionCount(fileCaseModel.getConnectionCount());
                    this.database.update(this.model);
                    if (connectionModelList != null) {
                        for (ConnectionModel connectionModel : connectionModelList) {
                            connectionModel.setId(id);
                            this.database.insertConnectionModel(connectionModel);
                        }
                    }
                    throw new RetryDirectly();
                }
            }
            if (FileDownloadHelper.inspectAndInflowConflictPath(id, this.model.getSoFar(), this.model.getTempFilePath(), targetFilePath, this.threadPoolMonitor)) {
                this.database.remove(id);
                this.database.removeConnections(id);
                throw new DiscardSafely();
            }
        }
    }

    public int getId() {
        return this.model.getId();
    }

    public boolean isAlive() {
        return this.alive.get() || this.statusCallback.isAlive();
    }

    public String getTempFilePath() {
        return this.model.getTempFilePath();
    }
}
