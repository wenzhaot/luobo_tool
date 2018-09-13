package com.qiniu.android.collect;

import com.qiniu.android.http.UserAgent;
import com.qiniu.android.storage.UpToken;
import com.umeng.message.util.HttpRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public final class UploadInfoCollector {
    private static OkHttpClient httpClient = null;
    private static UploadInfoCollector httpCollector;
    private static ExecutorService singleServer = null;
    private long lastUpload;
    private File recordFile = null;
    private final String recordFileName;
    private final String serverURL;

    public static abstract class RecordMsg {
        public abstract String toRecordMsg();
    }

    private UploadInfoCollector(String recordFileName, String serverURL) {
        this.recordFileName = recordFileName;
        this.serverURL = serverURL;
        try {
            reset0();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static UploadInfoCollector getHttpCollector() {
        if (httpCollector == null) {
            httpCollector = new UploadInfoCollector("_qiniu_record_file_hs5z9lo7anx03", Config.serverURL);
        }
        return httpCollector;
    }

    public static void clean() {
        try {
            if (singleServer != null) {
                singleServer.shutdown();
            }
        } catch (Exception e) {
        }
        singleServer = null;
        httpClient = null;
        try {
            getHttpCollector().clean0();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        httpCollector = null;
    }

    public static void reset() {
        try {
            getHttpCollector().reset0();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void handleHttp(UpToken upToken, RecordMsg record) {
        try {
            if (Config.isRecord) {
                getHttpCollector().handle0(upToken, record);
            }
        } catch (Throwable th) {
        }
    }

    public static void handleUpload(UpToken upToken, RecordMsg record) {
        handleHttp(upToken, record);
    }

    /* JADX WARNING: Removed duplicated region for block: B:40:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0027 A:{SYNTHETIC, Splitter: B:15:0x0027} */
    /* JADX WARNING: Removed duplicated region for block: B:41:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0033 A:{SYNTHETIC, Splitter: B:22:0x0033} */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x003c A:{SYNTHETIC, Splitter: B:27:0x003c} */
    private static void writeToFile(java.io.File r5, java.lang.String r6, boolean r7) {
        /*
        r1 = 0;
        r2 = new java.io.FileOutputStream;	 Catch:{ FileNotFoundException -> 0x0021, IOException -> 0x002d }
        r2.<init>(r5, r7);	 Catch:{ FileNotFoundException -> 0x0021, IOException -> 0x002d }
        r3 = "UTF-8";
        r3 = java.nio.charset.Charset.forName(r3);	 Catch:{ FileNotFoundException -> 0x0048, IOException -> 0x0045, all -> 0x0042 }
        r3 = r6.getBytes(r3);	 Catch:{ FileNotFoundException -> 0x0048, IOException -> 0x0045, all -> 0x0042 }
        r2.write(r3);	 Catch:{ FileNotFoundException -> 0x0048, IOException -> 0x0045, all -> 0x0042 }
        r2.flush();	 Catch:{ FileNotFoundException -> 0x0048, IOException -> 0x0045, all -> 0x0042 }
        if (r2 == 0) goto L_0x004b;
    L_0x0019:
        r2.close();	 Catch:{ IOException -> 0x001e }
        r1 = r2;
    L_0x001d:
        return;
    L_0x001e:
        r3 = move-exception;
        r1 = r2;
        goto L_0x001d;
    L_0x0021:
        r0 = move-exception;
    L_0x0022:
        r0.printStackTrace();	 Catch:{ all -> 0x0039 }
        if (r1 == 0) goto L_0x001d;
    L_0x0027:
        r1.close();	 Catch:{ IOException -> 0x002b }
        goto L_0x001d;
    L_0x002b:
        r3 = move-exception;
        goto L_0x001d;
    L_0x002d:
        r0 = move-exception;
    L_0x002e:
        r0.printStackTrace();	 Catch:{ all -> 0x0039 }
        if (r1 == 0) goto L_0x001d;
    L_0x0033:
        r1.close();	 Catch:{ IOException -> 0x0037 }
        goto L_0x001d;
    L_0x0037:
        r3 = move-exception;
        goto L_0x001d;
    L_0x0039:
        r3 = move-exception;
    L_0x003a:
        if (r1 == 0) goto L_0x003f;
    L_0x003c:
        r1.close();	 Catch:{ IOException -> 0x0040 }
    L_0x003f:
        throw r3;
    L_0x0040:
        r4 = move-exception;
        goto L_0x003f;
    L_0x0042:
        r3 = move-exception;
        r1 = r2;
        goto L_0x003a;
    L_0x0045:
        r0 = move-exception;
        r1 = r2;
        goto L_0x002e;
    L_0x0048:
        r0 = move-exception;
        r1 = r2;
        goto L_0x0022;
    L_0x004b:
        r1 = r2;
        goto L_0x001d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.qiniu.android.collect.UploadInfoCollector.writeToFile(java.io.File, java.lang.String, boolean):void");
    }

    private static OkHttpClient getHttpClient() {
        if (httpClient == null) {
            Builder builder = new Builder();
            builder.connectTimeout(10, TimeUnit.SECONDS);
            builder.readTimeout(15, TimeUnit.SECONDS);
            builder.writeTimeout((long) ((((Config.interval / 2) + 1) * 60) - 10), TimeUnit.SECONDS);
            httpClient = builder.build();
        }
        return httpClient;
    }

    private void clean0() {
        try {
            if (this.recordFile != null) {
                this.recordFile.delete();
            } else {
                new File(getRecordDir(Config.recordDir), this.recordFileName).delete();
            }
        } catch (Exception e) {
        }
        this.recordFile = null;
    }

    private void reset0() throws IOException {
        if (Config.isRecord) {
            initRecordFile(getRecordDir(Config.recordDir));
        }
        if (!(Config.isRecord || singleServer == null)) {
            singleServer.shutdown();
        }
        if (!Config.isRecord) {
            return;
        }
        if (singleServer == null || singleServer.isShutdown()) {
            singleServer = Executors.newSingleThreadExecutor();
        }
    }

    private File getRecordDir(String recordDir) {
        return new File(recordDir);
    }

    private void initRecordFile(File recordDir) throws IOException {
        if (recordDir == null) {
            throw new IOException("record's dir is not setted");
        } else if (recordDir.exists()) {
            if (recordDir.isDirectory()) {
                this.recordFile = new File(recordDir, this.recordFileName);
                return;
            }
            throw new IOException(recordDir.getAbsolutePath() + " is not a dir");
        } else if (!recordDir.mkdirs()) {
            throw new IOException("mkdir failed: " + recordDir.getAbsolutePath());
        }
    }

    private void handle0(final UpToken upToken, final RecordMsg record) {
        if (singleServer != null && !singleServer.isShutdown()) {
            singleServer.submit(new Runnable() {
                public void run() {
                    if (Config.isRecord) {
                        try {
                            UploadInfoCollector.this.tryRecode(record.toRecordMsg(), UploadInfoCollector.this.recordFile);
                        } catch (Throwable th) {
                        }
                    }
                }
            });
            if (Config.isUpload && upToken != UpToken.NULL) {
                singleServer.submit(new Runnable() {
                    public void run() {
                        if (Config.isRecord && Config.isUpload) {
                            try {
                                UploadInfoCollector.this.tryUploadAndClean(upToken, UploadInfoCollector.this.recordFile);
                            } catch (Throwable th) {
                            }
                        }
                    }
                });
            }
        }
    }

    private void tryRecode(String msg, File recordFile) {
        if (Config.isRecord && recordFile.length() < ((long) Config.maxRecordFileSize)) {
            writeToFile(recordFile, msg + "\n", true);
        }
    }

    private void tryUploadAndClean(UpToken upToken, File recordFile) {
        if (Config.isUpload && recordFile.length() > ((long) Config.uploadThreshold)) {
            long now = new Date().getTime();
            if (now > this.lastUpload + ((long) ((Config.interval * 60) * 1000))) {
                this.lastUpload = now;
                if (upload(upToken, recordFile)) {
                    writeToFile(recordFile, "", false);
                    writeToFile(recordFile, "", false);
                }
            }
        }
    }

    private boolean upload(UpToken upToken, File recordFile) {
        Response res;
        try {
            res = getHttpClient().newCall(new Request.Builder().url(this.serverURL).addHeader(HttpRequest.HEADER_AUTHORIZATION, "UpToken " + upToken.token).addHeader("User-Agent", UserAgent.instance().getUa(upToken.accessKey)).post(RequestBody.create(MediaType.parse("text/plain"), recordFile)).build()).execute();
            boolean isOk = isOk(res);
            try {
                res.body().close();
                return isOk;
            } catch (Exception e) {
                return isOk;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        } catch (Throwable th) {
            try {
                res.body().close();
            } catch (Exception e3) {
            }
        }
    }

    private boolean isOk(Response res) {
        return res.isSuccessful() && res.header("X-Reqid") != null;
    }
}
