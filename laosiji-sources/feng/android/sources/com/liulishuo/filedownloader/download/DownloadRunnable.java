package com.liulishuo.filedownloader.download;

import com.liulishuo.filedownloader.database.FileDownloadDatabase;
import com.liulishuo.filedownloader.model.ConnectionModel;
import com.liulishuo.filedownloader.model.FileDownloadHeader;
import com.liulishuo.filedownloader.util.FileDownloadUtils;

public class DownloadRunnable implements Runnable {
    private final ProcessCallback callback;
    private final ConnectTask connectTask;
    final int connectionIndex;
    private final int downloadId;
    private FetchDataTask fetchDataTask;
    private final boolean isWifiRequired;
    private final String path;
    private volatile boolean paused;

    public static class Builder {
        private ProcessCallback callback;
        private final Builder connectTaskBuilder = new Builder();
        private Integer connectionIndex;
        private Boolean isWifiRequired;
        private String path;

        public Builder setCallback(ProcessCallback callback) {
            this.callback = callback;
            return this;
        }

        public Builder setId(int id) {
            this.connectTaskBuilder.setDownloadId(id);
            return this;
        }

        public Builder setUrl(String url) {
            this.connectTaskBuilder.setUrl(url);
            return this;
        }

        public Builder setEtag(String etag) {
            this.connectTaskBuilder.setEtag(etag);
            return this;
        }

        public Builder setHeader(FileDownloadHeader header) {
            this.connectTaskBuilder.setHeader(header);
            return this;
        }

        public Builder setConnectionModel(ConnectionProfile model) {
            this.connectTaskBuilder.setConnectionProfile(model);
            return this;
        }

        public Builder setPath(String path) {
            this.path = path;
            return this;
        }

        public Builder setWifiRequired(boolean wifiRequired) {
            this.isWifiRequired = Boolean.valueOf(wifiRequired);
            return this;
        }

        public Builder setConnectionIndex(Integer connectionIndex) {
            this.connectionIndex = connectionIndex;
            return this;
        }

        public DownloadRunnable build() {
            if (this.callback == null || this.path == null || this.isWifiRequired == null || this.connectionIndex == null) {
                throw new IllegalArgumentException(FileDownloadUtils.formatString("%s %s %B", this.callback, this.path, this.isWifiRequired));
            }
            ConnectTask connectTask = this.connectTaskBuilder.build();
            return new DownloadRunnable(connectTask.downloadId, this.connectionIndex.intValue(), connectTask, this.callback, this.isWifiRequired.booleanValue(), this.path);
        }

        DownloadRunnable buildForTest(ConnectTask connectTask) {
            return new DownloadRunnable(connectTask.downloadId, 0, connectTask, this.callback, false, "");
        }
    }

    private DownloadRunnable(int id, int connectionIndex, ConnectTask connectTask, ProcessCallback callback, boolean isWifiRequired, String path) {
        this.downloadId = id;
        this.connectionIndex = connectionIndex;
        this.paused = false;
        this.callback = callback;
        this.path = path;
        this.connectTask = connectTask;
        this.isWifiRequired = isWifiRequired;
    }

    public void pause() {
        this.paused = true;
        if (this.fetchDataTask != null) {
            this.fetchDataTask.pause();
        }
    }

    public void discard() {
        pause();
    }

    /* JADX WARNING: Failed to extract finally block: empty outs */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0137 A:{SYNTHETIC, Splitter: B:49:0x0137} */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x009e A:{Catch:{ all -> 0x0143 }} */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x009e A:{Catch:{ all -> 0x0143 }} */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0137 A:{SYNTHETIC, Splitter: B:49:0x0137} */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0137 A:{SYNTHETIC, Splitter: B:49:0x0137} */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x009e A:{Catch:{ all -> 0x0143 }} */
    /* JADX WARNING: Missing block: B:73:?, code:
            return;
     */
    public void run() {
        /*
        r14 = this;
        r9 = 10;
        android.os.Process.setThreadPriority(r9);
        r4 = 0;
        r9 = r14.connectTask;
        r9 = r9.getProfile();
        r0 = r9.currentOffset;
        r8 = 0;
    L_0x000f:
        r9 = r14.paused;	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        if (r9 == 0) goto L_0x0019;
    L_0x0013:
        if (r4 == 0) goto L_0x0018;
    L_0x0015:
        r4.ending();
    L_0x0018:
        return;
    L_0x0019:
        r8 = 0;
        r9 = r14.connectTask;	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        r4 = r9.connect();	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        r3 = r4.getResponseCode();	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        r9 = com.liulishuo.filedownloader.util.FileDownloadLog.NEED_LOG;	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        if (r9 == 0) goto L_0x0053;
    L_0x0028:
        r9 = "the connection[%d] for %d, is connected %s with code[%d]";
        r10 = 4;
        r10 = new java.lang.Object[r10];	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        r11 = 0;
        r12 = r14.connectionIndex;	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        r12 = java.lang.Integer.valueOf(r12);	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        r10[r11] = r12;	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        r11 = 1;
        r12 = r14.downloadId;	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        r12 = java.lang.Integer.valueOf(r12);	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        r10[r11] = r12;	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        r11 = 2;
        r12 = r14.connectTask;	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        r12 = r12.getProfile();	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        r10[r11] = r12;	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        r11 = 3;
        r12 = java.lang.Integer.valueOf(r3);	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        r10[r11] = r12;	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        com.liulishuo.filedownloader.util.FileDownloadLog.d(r14, r9, r10);	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
    L_0x0053:
        r9 = 206; // 0xce float:2.89E-43 double:1.02E-321;
        if (r3 == r9) goto L_0x00bc;
    L_0x0057:
        r9 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        if (r3 == r9) goto L_0x00bc;
    L_0x005b:
        r9 = new java.net.SocketException;	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        r10 = "Connection failed with request[%s] response[%s] http-state[%d] on task[%d-%d], which is changed after verify connection, so please try again.";
        r11 = 5;
        r11 = new java.lang.Object[r11];	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        r12 = 0;
        r13 = r14.connectTask;	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        r13 = r13.getRequestHeader();	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        r11[r12] = r13;	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        r12 = 1;
        r13 = r4.getResponseHeaderFields();	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        r11[r12] = r13;	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        r12 = 2;
        r13 = java.lang.Integer.valueOf(r3);	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        r11[r12] = r13;	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        r12 = 3;
        r13 = r14.downloadId;	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        r13 = java.lang.Integer.valueOf(r13);	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        r11[r12] = r13;	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        r12 = 4;
        r13 = r14.connectionIndex;	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        r13 = java.lang.Integer.valueOf(r13);	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        r11[r12] = r13;	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        r10 = com.liulishuo.filedownloader.util.FileDownloadUtils.formatString(r10, r11);	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        r9.<init>(r10);	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        throw r9;	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
    L_0x0094:
        r9 = move-exception;
        r5 = r9;
    L_0x0096:
        r9 = r14.callback;	 Catch:{ all -> 0x0143 }
        r9 = r9.isRetry(r5);	 Catch:{ all -> 0x0143 }
        if (r9 == 0) goto L_0x0137;
    L_0x009e:
        if (r8 == 0) goto L_0x0118;
    L_0x00a0:
        r9 = r14.fetchDataTask;	 Catch:{ all -> 0x0143 }
        if (r9 != 0) goto L_0x0118;
    L_0x00a4:
        r9 = "it is valid to retry and connection is valid but create fetch-data-task failed, so give up directly with %s";
        r10 = 1;
        r10 = new java.lang.Object[r10];	 Catch:{ all -> 0x0143 }
        r11 = 0;
        r10[r11] = r5;	 Catch:{ all -> 0x0143 }
        com.liulishuo.filedownloader.util.FileDownloadLog.w(r14, r9, r10);	 Catch:{ all -> 0x0143 }
        r9 = r14.callback;	 Catch:{ all -> 0x0143 }
        r9.onError(r5);	 Catch:{ all -> 0x0143 }
        if (r4 == 0) goto L_0x0018;
    L_0x00b7:
        r4.ending();
        goto L_0x0018;
    L_0x00bc:
        r8 = 1;
        r2 = new com.liulishuo.filedownloader.download.FetchDataTask$Builder;	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        r2.<init>();	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        r9 = r14.paused;	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        if (r9 == 0) goto L_0x00cd;
    L_0x00c6:
        if (r4 == 0) goto L_0x0018;
    L_0x00c8:
        r4.ending();
        goto L_0x0018;
    L_0x00cd:
        r9 = r14.downloadId;	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        r9 = r2.setDownloadId(r9);	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        r10 = r14.connectionIndex;	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        r9 = r9.setConnectionIndex(r10);	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        r10 = r14.callback;	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        r9 = r9.setCallback(r10);	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        r9 = r9.setHost(r14);	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        r10 = r14.isWifiRequired;	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        r9 = r9.setWifiRequired(r10);	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        r9 = r9.setConnection(r4);	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        r10 = r14.connectTask;	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        r10 = r10.getProfile();	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        r9 = r9.setConnectionProfile(r10);	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        r10 = r14.path;	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        r9 = r9.setPath(r10);	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        r9 = r9.build();	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        r14.fetchDataTask = r9;	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        r9 = r14.fetchDataTask;	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        r9.run();	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        r9 = r14.paused;	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        if (r9 == 0) goto L_0x0111;
    L_0x010c:
        r9 = r14.fetchDataTask;	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
        r9.pause();	 Catch:{ IllegalAccessException -> 0x0094, IOException -> 0x014a, FileDownloadGiveUpRetryException -> 0x014e, IllegalArgumentException -> 0x0152 }
    L_0x0111:
        if (r4 == 0) goto L_0x0018;
    L_0x0113:
        r4.ending();
        goto L_0x0018;
    L_0x0118:
        r9 = r14.fetchDataTask;	 Catch:{ all -> 0x0143 }
        if (r9 == 0) goto L_0x012b;
    L_0x011c:
        r6 = r14.getDownloadedOffset();	 Catch:{ all -> 0x0143 }
        r10 = 0;
        r9 = (r6 > r10 ? 1 : (r6 == r10 ? 0 : -1));
        if (r9 <= 0) goto L_0x012b;
    L_0x0126:
        r9 = r14.connectTask;	 Catch:{ all -> 0x0143 }
        r9.updateConnectionProfile(r6);	 Catch:{ all -> 0x0143 }
    L_0x012b:
        r9 = r14.callback;	 Catch:{ all -> 0x0143 }
        r9.onRetry(r5);	 Catch:{ all -> 0x0143 }
        if (r4 == 0) goto L_0x000f;
    L_0x0132:
        r4.ending();
        goto L_0x000f;
    L_0x0137:
        r9 = r14.callback;	 Catch:{ all -> 0x0143 }
        r9.onError(r5);	 Catch:{ all -> 0x0143 }
        if (r4 == 0) goto L_0x0018;
    L_0x013e:
        r4.ending();
        goto L_0x0018;
    L_0x0143:
        r9 = move-exception;
        if (r4 == 0) goto L_0x0149;
    L_0x0146:
        r4.ending();
    L_0x0149:
        throw r9;
    L_0x014a:
        r9 = move-exception;
        r5 = r9;
        goto L_0x0096;
    L_0x014e:
        r9 = move-exception;
        r5 = r9;
        goto L_0x0096;
    L_0x0152:
        r9 = move-exception;
        r5 = r9;
        goto L_0x0096;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.liulishuo.filedownloader.download.DownloadRunnable.run():void");
    }

    private long getDownloadedOffset() {
        FileDownloadDatabase database = CustomComponentHolder.getImpl().getDatabaseInstance();
        if (this.connectionIndex < 0) {
            return database.find(this.downloadId).getSoFar();
        }
        for (ConnectionModel connectionModel : database.findConnectionModel(this.downloadId)) {
            if (connectionModel.getIndex() == this.connectionIndex) {
                return connectionModel.getCurrentOffset();
            }
        }
        return 0;
    }
}
