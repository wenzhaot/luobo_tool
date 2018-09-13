package com.huawei.android.pushselfshow.utils.b;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import com.huawei.android.pushagent.a.a.c;
import com.huawei.android.pushselfshow.utils.a;
import java.io.File;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;

public class b {
    public Handler a;
    public Context b;
    public String c;
    public String d;
    public boolean e;
    private boolean f;
    private Runnable g;

    public b() {
        this.a = null;
        this.e = false;
        this.g = new c(this);
        this.f = false;
    }

    public b(Handler handler, Context context, String str, String str2) {
        this.a = null;
        this.e = false;
        this.g = new c(this);
        this.a = handler;
        this.b = context;
        this.c = str;
        this.d = str2;
        this.f = false;
    }

    public static String a(Context context) {
        return b(context) + File.separator + "richpush";
    }

    public static void a(HttpClient httpClient) {
        if (httpClient != null) {
            try {
                httpClient.getConnectionManager().shutdown();
            } catch (Throwable e) {
                c.c("PushSelfShowLog", "close input stream failed." + e.getMessage(), e);
            }
        }
    }

    public static void a(HttpRequestBase httpRequestBase) {
        if (httpRequestBase != null) {
            try {
                httpRequestBase.abort();
            } catch (Throwable e) {
                c.c("PushSelfShowLog", "close input stream failed." + e.getMessage(), e);
            }
        }
    }

    public static String b(Context context) {
        String str = "";
        try {
            str = !Environment.getExternalStorageState().equals("mounted") ? context.getFilesDir().getPath() : a.k(context);
        } catch (Throwable e) {
            c.c("PushSelfShowLog", "getLocalFileHeader failed ", e);
        }
        str = str + File.separator + "PushService";
        c.a("PushSelfShowLog", "getFileHeader:" + str);
        return str;
    }

    public static String c(Context context) {
        String str = "";
        try {
            if (!Environment.getExternalStorageState().equals("mounted")) {
                return null;
            }
            str = a.k(context);
            return str + File.separator + "PushService";
        } catch (Throwable e) {
            c.c("PushSelfShowLog", "getLocalFileHeader failed ", e);
        }
    }

    public String a(Context context, String str, String str2) {
        try {
            String str3 = "" + System.currentTimeMillis();
            String str4 = str3 + str2;
            String str5 = a(context) + File.separator + str3;
            str3 = str5 + File.separator + str4;
            File file = new File(str5);
            if (file.exists()) {
                a.a(file);
            } else {
                c.a("PushSelfShowLog", "dir is not exists()," + file.getAbsolutePath());
            }
            if (file.mkdirs()) {
                c.a("PushSelfShowLog", "dir.mkdirs() success  ");
            }
            c.a("PushSelfShowLog", "begin to download zip file, path is " + str3 + ",dir is " + file.getAbsolutePath());
            if (b(context, str, str3)) {
                return str3;
            }
            c.a("PushSelfShowLog", "download richpush file failed，clear temp file");
            if (file.exists()) {
                a.a(file);
            }
            return null;
        } catch (Exception e) {
            c.a("PushSelfShowLog", "download err" + e.toString());
        }
    }

    public void a() {
        this.f = true;
    }

    public void a(String str) {
        Message message = new Message();
        message.what = 1;
        message.obj = str;
        c.a("PushSelfShowLog", "mDownloadHandler = " + this.a);
        if (this.a != null) {
            this.a.sendMessageDelayed(message, 1);
        }
    }

    public void b() {
        new Thread(this.g).start();
    }

    /* JADX WARNING: Removed duplicated region for block: B:124:0x0360 A:{SYNTHETIC, Splitter: B:124:0x0360} */
    /* JADX WARNING: Removed duplicated region for block: B:127:0x0365 A:{SYNTHETIC, Splitter: B:127:0x0365} */
    /* JADX WARNING: Removed duplicated region for block: B:130:0x036a A:{SYNTHETIC, Splitter: B:130:0x036a} */
    /* JADX WARNING: Removed duplicated region for block: B:106:0x02e3 A:{SYNTHETIC, Splitter: B:106:0x02e3} */
    /* JADX WARNING: Removed duplicated region for block: B:109:0x02e8 A:{SYNTHETIC, Splitter: B:109:0x02e8} */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x02ed A:{SYNTHETIC, Splitter: B:112:0x02ed} */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x0360 A:{SYNTHETIC, Splitter: B:124:0x0360} */
    /* JADX WARNING: Removed duplicated region for block: B:127:0x0365 A:{SYNTHETIC, Splitter: B:127:0x0365} */
    /* JADX WARNING: Removed duplicated region for block: B:130:0x036a A:{SYNTHETIC, Splitter: B:130:0x036a} */
    /* JADX WARNING: Removed duplicated region for block: B:106:0x02e3 A:{SYNTHETIC, Splitter: B:106:0x02e3} */
    /* JADX WARNING: Removed duplicated region for block: B:109:0x02e8 A:{SYNTHETIC, Splitter: B:109:0x02e8} */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x02ed A:{SYNTHETIC, Splitter: B:112:0x02ed} */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x0360 A:{SYNTHETIC, Splitter: B:124:0x0360} */
    /* JADX WARNING: Removed duplicated region for block: B:127:0x0365 A:{SYNTHETIC, Splitter: B:127:0x0365} */
    /* JADX WARNING: Removed duplicated region for block: B:130:0x036a A:{SYNTHETIC, Splitter: B:130:0x036a} */
    /* JADX WARNING: Removed duplicated region for block: B:106:0x02e3 A:{SYNTHETIC, Splitter: B:106:0x02e3} */
    /* JADX WARNING: Removed duplicated region for block: B:109:0x02e8 A:{SYNTHETIC, Splitter: B:109:0x02e8} */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x02ed A:{SYNTHETIC, Splitter: B:112:0x02ed} */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x0360 A:{SYNTHETIC, Splitter: B:124:0x0360} */
    /* JADX WARNING: Removed duplicated region for block: B:127:0x0365 A:{SYNTHETIC, Splitter: B:127:0x0365} */
    /* JADX WARNING: Removed duplicated region for block: B:130:0x036a A:{SYNTHETIC, Splitter: B:130:0x036a} */
    /* JADX WARNING: Removed duplicated region for block: B:106:0x02e3 A:{SYNTHETIC, Splitter: B:106:0x02e3} */
    /* JADX WARNING: Removed duplicated region for block: B:109:0x02e8 A:{SYNTHETIC, Splitter: B:109:0x02e8} */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x02ed A:{SYNTHETIC, Splitter: B:112:0x02ed} */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x0360 A:{SYNTHETIC, Splitter: B:124:0x0360} */
    /* JADX WARNING: Removed duplicated region for block: B:127:0x0365 A:{SYNTHETIC, Splitter: B:127:0x0365} */
    /* JADX WARNING: Removed duplicated region for block: B:130:0x036a A:{SYNTHETIC, Splitter: B:130:0x036a} */
    public boolean b(android.content.Context r13, java.lang.String r14, java.lang.String r15) {
        /*
        r12 = this;
        r1 = 1;
        r3 = 0;
        r0 = 0;
        r4 = 0;
        r6 = 0;
        r7 = 0;
        r5 = new org.apache.http.impl.client.DefaultHttpClient;	 Catch:{ IOException -> 0x02b8, all -> 0x0353 }
        r5.<init>();	 Catch:{ IOException -> 0x02b8, all -> 0x0353 }
        r2 = new org.apache.http.client.methods.HttpGet;	 Catch:{ IOException -> 0x03ea, all -> 0x03ce }
        r2.<init>(r14);	 Catch:{ IOException -> 0x03ea, all -> 0x03ce }
        r8 = new com.huawei.android.pushselfshow.utils.b.d;	 Catch:{ IOException -> 0x03f1, all -> 0x03d3 }
        r8.<init>(r13);	 Catch:{ IOException -> 0x03f1, all -> 0x03d3 }
        r8 = r8.a(r14, r5, r2);	 Catch:{ IOException -> 0x03f1, all -> 0x03d3 }
        if (r8 != 0) goto L_0x009a;
    L_0x001b:
        r1 = "PushSelfShowLog";
        r8 = "fail, httprespone  is null";
        com.huawei.android.pushagent.a.a.c.a(r1, r8);	 Catch:{ IOException -> 0x03f1, all -> 0x03d3 }
        a(r2);
        a(r5);
        if (r3 == 0) goto L_0x002f;
    L_0x002c:
        r4.close();	 Catch:{ IOException -> 0x003a }
    L_0x002f:
        if (r3 == 0) goto L_0x0034;
    L_0x0031:
        r6.close();	 Catch:{ IOException -> 0x005a }
    L_0x0034:
        if (r3 == 0) goto L_0x0039;
    L_0x0036:
        r7.close();	 Catch:{ Exception -> 0x007a }
    L_0x0039:
        return r0;
    L_0x003a:
        r1 = move-exception;
        r2 = "PushSelfShowLog";
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = " bos download  error";
        r4 = r4.append(r5);
        r5 = r1.toString();
        r4 = r4.append(r5);
        r4 = r4.toString();
        com.huawei.android.pushagent.a.a.c.c(r2, r4, r1);
        goto L_0x002f;
    L_0x005a:
        r1 = move-exception;
        r2 = "PushSelfShowLog";
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = " bis download  error";
        r4 = r4.append(r5);
        r5 = r1.toString();
        r4 = r4.append(r5);
        r4 = r4.toString();
        com.huawei.android.pushagent.a.a.c.c(r2, r4, r1);
        goto L_0x0034;
    L_0x007a:
        r1 = move-exception;
        r2 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "out download  error";
        r3 = r3.append(r4);
        r4 = r1.toString();
        r3 = r3.append(r4);
        r3 = r3.toString();
        com.huawei.android.pushagent.a.a.c.c(r2, r3, r1);
        goto L_0x0039;
    L_0x009a:
        r9 = r8.getStatusLine();	 Catch:{ IOException -> 0x03f1, all -> 0x03d3 }
        if (r9 == 0) goto L_0x0146;
    L_0x00a0:
        r9 = r8.getStatusLine();	 Catch:{ IOException -> 0x03f1, all -> 0x03d3 }
        r9 = r9.getStatusCode();	 Catch:{ IOException -> 0x03f1, all -> 0x03d3 }
        r10 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        if (r9 == r10) goto L_0x0146;
    L_0x00ac:
        r1 = "PushSelfShowLog";
        r9 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x03f1, all -> 0x03d3 }
        r9.<init>();	 Catch:{ IOException -> 0x03f1, all -> 0x03d3 }
        r10 = "fail, httprespone status is ";
        r9 = r9.append(r10);	 Catch:{ IOException -> 0x03f1, all -> 0x03d3 }
        r8 = r8.getStatusLine();	 Catch:{ IOException -> 0x03f1, all -> 0x03d3 }
        r8 = r8.getStatusCode();	 Catch:{ IOException -> 0x03f1, all -> 0x03d3 }
        r8 = r9.append(r8);	 Catch:{ IOException -> 0x03f1, all -> 0x03d3 }
        r8 = r8.toString();	 Catch:{ IOException -> 0x03f1, all -> 0x03d3 }
        com.huawei.android.pushagent.a.a.c.a(r1, r8);	 Catch:{ IOException -> 0x03f1, all -> 0x03d3 }
        a(r2);
        a(r5);
        if (r3 == 0) goto L_0x00d9;
    L_0x00d6:
        r4.close();	 Catch:{ IOException -> 0x0106 }
    L_0x00d9:
        if (r3 == 0) goto L_0x00de;
    L_0x00db:
        r6.close();	 Catch:{ IOException -> 0x0126 }
    L_0x00de:
        if (r3 == 0) goto L_0x0039;
    L_0x00e0:
        r7.close();	 Catch:{ Exception -> 0x00e5 }
        goto L_0x0039;
    L_0x00e5:
        r1 = move-exception;
        r2 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "out download  error";
        r3 = r3.append(r4);
        r4 = r1.toString();
        r3 = r3.append(r4);
        r3 = r3.toString();
        com.huawei.android.pushagent.a.a.c.c(r2, r3, r1);
        goto L_0x0039;
    L_0x0106:
        r1 = move-exception;
        r2 = "PushSelfShowLog";
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = " bos download  error";
        r4 = r4.append(r5);
        r5 = r1.toString();
        r4 = r4.append(r5);
        r4 = r4.toString();
        com.huawei.android.pushagent.a.a.c.c(r2, r4, r1);
        goto L_0x00d9;
    L_0x0126:
        r1 = move-exception;
        r2 = "PushSelfShowLog";
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = " bis download  error";
        r4 = r4.append(r5);
        r5 = r1.toString();
        r4 = r4.append(r5);
        r4 = r4.toString();
        com.huawei.android.pushagent.a.a.c.c(r2, r4, r1);
        goto L_0x00de;
    L_0x0146:
        r6 = new java.io.BufferedInputStream;	 Catch:{ IOException -> 0x03f1, all -> 0x03d3 }
        r4 = r8.getEntity();	 Catch:{ IOException -> 0x03f1, all -> 0x03d3 }
        r4 = r4.getContent();	 Catch:{ IOException -> 0x03f1, all -> 0x03d3 }
        r6.<init>(r4);	 Catch:{ IOException -> 0x03f1, all -> 0x03d3 }
        r4 = "PushSelfShowLog";
        r7 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x03f7, all -> 0x03d7 }
        r7.<init>();	 Catch:{ IOException -> 0x03f7, all -> 0x03d7 }
        r8 = "begin to write content to ";
        r7 = r7.append(r8);	 Catch:{ IOException -> 0x03f7, all -> 0x03d7 }
        r7 = r7.append(r15);	 Catch:{ IOException -> 0x03f7, all -> 0x03d7 }
        r7 = r7.toString();	 Catch:{ IOException -> 0x03f7, all -> 0x03d7 }
        com.huawei.android.pushagent.a.a.c.a(r4, r7);	 Catch:{ IOException -> 0x03f7, all -> 0x03d7 }
        r4 = new java.io.File;	 Catch:{ IOException -> 0x03f7, all -> 0x03d7 }
        r4.<init>(r15);	 Catch:{ IOException -> 0x03f7, all -> 0x03d7 }
        r4 = r4.exists();	 Catch:{ IOException -> 0x03f7, all -> 0x03d7 }
        if (r4 == 0) goto L_0x019b;
    L_0x0178:
        r4 = "PushSelfShowLog";
        r7 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x03f7, all -> 0x03d7 }
        r7.<init>();	 Catch:{ IOException -> 0x03f7, all -> 0x03d7 }
        r8 = "file delete ";
        r7 = r7.append(r8);	 Catch:{ IOException -> 0x03f7, all -> 0x03d7 }
        r8 = new java.io.File;	 Catch:{ IOException -> 0x03f7, all -> 0x03d7 }
        r8.<init>(r15);	 Catch:{ IOException -> 0x03f7, all -> 0x03d7 }
        r8 = r8.delete();	 Catch:{ IOException -> 0x03f7, all -> 0x03d7 }
        r7 = r7.append(r8);	 Catch:{ IOException -> 0x03f7, all -> 0x03d7 }
        r7 = r7.toString();	 Catch:{ IOException -> 0x03f7, all -> 0x03d7 }
        com.huawei.android.pushagent.a.a.c.a(r4, r7);	 Catch:{ IOException -> 0x03f7, all -> 0x03d7 }
    L_0x019b:
        r4 = new java.io.FileOutputStream;	 Catch:{ IOException -> 0x03f7, all -> 0x03d7 }
        r4.<init>(r15);	 Catch:{ IOException -> 0x03f7, all -> 0x03d7 }
        r7 = new java.io.BufferedOutputStream;	 Catch:{ IOException -> 0x03fd, all -> 0x03db }
        r7.<init>(r4);	 Catch:{ IOException -> 0x03fd, all -> 0x03db }
        r3 = 32768; // 0x8000 float:4.5918E-41 double:1.61895E-319;
        r3 = new byte[r3];	 Catch:{ IOException -> 0x0405, all -> 0x03e0 }
    L_0x01aa:
        r8 = r6.read(r3);	 Catch:{ IOException -> 0x0405, all -> 0x03e0 }
        if (r8 >= 0) goto L_0x0234;
    L_0x01b0:
        r3 = "PushSelfShowLog";
        r8 = "downLoad success ";
        com.huawei.android.pushagent.a.a.c.a(r3, r8);	 Catch:{ IOException -> 0x0405, all -> 0x03e0 }
        r3 = 0;
        r12.e = r3;	 Catch:{ IOException -> 0x0405, all -> 0x03e0 }
        a(r2);
        a(r5);
        if (r7 == 0) goto L_0x01c7;
    L_0x01c4:
        r7.close();	 Catch:{ IOException -> 0x01d4 }
    L_0x01c7:
        if (r6 == 0) goto L_0x01cc;
    L_0x01c9:
        r6.close();	 Catch:{ IOException -> 0x01f4 }
    L_0x01cc:
        if (r4 == 0) goto L_0x01d1;
    L_0x01ce:
        r4.close();	 Catch:{ Exception -> 0x0214 }
    L_0x01d1:
        r0 = r1;
        goto L_0x0039;
    L_0x01d4:
        r0 = move-exception;
        r2 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r5 = " bos download  error";
        r3 = r3.append(r5);
        r5 = r0.toString();
        r3 = r3.append(r5);
        r3 = r3.toString();
        com.huawei.android.pushagent.a.a.c.c(r2, r3, r0);
        goto L_0x01c7;
    L_0x01f4:
        r0 = move-exception;
        r2 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r5 = " bis download  error";
        r3 = r3.append(r5);
        r5 = r0.toString();
        r3 = r3.append(r5);
        r3 = r3.toString();
        com.huawei.android.pushagent.a.a.c.c(r2, r3, r0);
        goto L_0x01cc;
    L_0x0214:
        r0 = move-exception;
        r2 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "out download  error";
        r3 = r3.append(r4);
        r4 = r0.toString();
        r3 = r3.append(r4);
        r3 = r3.toString();
        com.huawei.android.pushagent.a.a.c.c(r2, r3, r0);
        goto L_0x01d1;
    L_0x0234:
        r9 = 1;
        r12.e = r9;	 Catch:{ IOException -> 0x0405, all -> 0x03e0 }
        r9 = 0;
        r7.write(r3, r9, r8);	 Catch:{ IOException -> 0x0405, all -> 0x03e0 }
        r8 = r12.f;	 Catch:{ IOException -> 0x0405, all -> 0x03e0 }
        if (r8 == 0) goto L_0x01aa;
    L_0x023f:
        a(r2);
        a(r5);
        if (r7 == 0) goto L_0x024a;
    L_0x0247:
        r7.close();	 Catch:{ IOException -> 0x0258 }
    L_0x024a:
        if (r6 == 0) goto L_0x024f;
    L_0x024c:
        r6.close();	 Catch:{ IOException -> 0x0278 }
    L_0x024f:
        if (r4 == 0) goto L_0x0254;
    L_0x0251:
        r4.close();	 Catch:{ Exception -> 0x0298 }
    L_0x0254:
        r12.e = r0;
        goto L_0x0039;
    L_0x0258:
        r1 = move-exception;
        r2 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r5 = " bos download  error";
        r3 = r3.append(r5);
        r5 = r1.toString();
        r3 = r3.append(r5);
        r3 = r3.toString();
        com.huawei.android.pushagent.a.a.c.c(r2, r3, r1);
        goto L_0x024a;
    L_0x0278:
        r1 = move-exception;
        r2 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r5 = " bis download  error";
        r3 = r3.append(r5);
        r5 = r1.toString();
        r3 = r3.append(r5);
        r3 = r3.toString();
        com.huawei.android.pushagent.a.a.c.c(r2, r3, r1);
        goto L_0x024f;
    L_0x0298:
        r1 = move-exception;
        r2 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "out download  error";
        r3 = r3.append(r4);
        r4 = r1.toString();
        r3 = r3.append(r4);
        r3 = r3.toString();
        com.huawei.android.pushagent.a.a.c.c(r2, r3, r1);
        goto L_0x0254;
    L_0x02b8:
        r1 = move-exception;
        r2 = r3;
        r4 = r3;
        r5 = r3;
        r6 = r3;
    L_0x02bd:
        r7 = "PushSelfShowLog";
        r8 = new java.lang.StringBuilder;	 Catch:{ all -> 0x03e4 }
        r8.<init>();	 Catch:{ all -> 0x03e4 }
        r9 = "downLoadSgThread download  error";
        r8 = r8.append(r9);	 Catch:{ all -> 0x03e4 }
        r9 = r1.toString();	 Catch:{ all -> 0x03e4 }
        r8 = r8.append(r9);	 Catch:{ all -> 0x03e4 }
        r8 = r8.toString();	 Catch:{ all -> 0x03e4 }
        com.huawei.android.pushagent.a.a.c.c(r7, r8, r1);	 Catch:{ all -> 0x03e4 }
        a(r2);
        a(r4);
        if (r6 == 0) goto L_0x02e6;
    L_0x02e3:
        r6.close();	 Catch:{ IOException -> 0x0313 }
    L_0x02e6:
        if (r5 == 0) goto L_0x02eb;
    L_0x02e8:
        r5.close();	 Catch:{ IOException -> 0x0333 }
    L_0x02eb:
        if (r3 == 0) goto L_0x0254;
    L_0x02ed:
        r3.close();	 Catch:{ Exception -> 0x02f2 }
        goto L_0x0254;
    L_0x02f2:
        r1 = move-exception;
        r2 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "out download  error";
        r3 = r3.append(r4);
        r4 = r1.toString();
        r3 = r3.append(r4);
        r3 = r3.toString();
        com.huawei.android.pushagent.a.a.c.c(r2, r3, r1);
        goto L_0x0254;
    L_0x0313:
        r1 = move-exception;
        r2 = "PushSelfShowLog";
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r6 = " bos download  error";
        r4 = r4.append(r6);
        r6 = r1.toString();
        r4 = r4.append(r6);
        r4 = r4.toString();
        com.huawei.android.pushagent.a.a.c.c(r2, r4, r1);
        goto L_0x02e6;
    L_0x0333:
        r1 = move-exception;
        r2 = "PushSelfShowLog";
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = " bis download  error";
        r4 = r4.append(r5);
        r5 = r1.toString();
        r4 = r4.append(r5);
        r4 = r4.toString();
        com.huawei.android.pushagent.a.a.c.c(r2, r4, r1);
        goto L_0x02eb;
    L_0x0353:
        r0 = move-exception;
        r2 = r3;
        r5 = r3;
        r6 = r3;
        r7 = r3;
    L_0x0358:
        a(r2);
        a(r5);
        if (r7 == 0) goto L_0x0363;
    L_0x0360:
        r7.close();	 Catch:{ IOException -> 0x036e }
    L_0x0363:
        if (r6 == 0) goto L_0x0368;
    L_0x0365:
        r6.close();	 Catch:{ IOException -> 0x038e }
    L_0x0368:
        if (r3 == 0) goto L_0x036d;
    L_0x036a:
        r3.close();	 Catch:{ Exception -> 0x03ae }
    L_0x036d:
        throw r0;
    L_0x036e:
        r1 = move-exception;
        r2 = "PushSelfShowLog";
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = " bos download  error";
        r4 = r4.append(r5);
        r5 = r1.toString();
        r4 = r4.append(r5);
        r4 = r4.toString();
        com.huawei.android.pushagent.a.a.c.c(r2, r4, r1);
        goto L_0x0363;
    L_0x038e:
        r1 = move-exception;
        r2 = "PushSelfShowLog";
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = " bis download  error";
        r4 = r4.append(r5);
        r5 = r1.toString();
        r4 = r4.append(r5);
        r4 = r4.toString();
        com.huawei.android.pushagent.a.a.c.c(r2, r4, r1);
        goto L_0x0368;
    L_0x03ae:
        r1 = move-exception;
        r2 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "out download  error";
        r3 = r3.append(r4);
        r4 = r1.toString();
        r3 = r3.append(r4);
        r3 = r3.toString();
        com.huawei.android.pushagent.a.a.c.c(r2, r3, r1);
        goto L_0x036d;
    L_0x03ce:
        r0 = move-exception;
        r2 = r3;
        r6 = r3;
        r7 = r3;
        goto L_0x0358;
    L_0x03d3:
        r0 = move-exception;
        r6 = r3;
        r7 = r3;
        goto L_0x0358;
    L_0x03d7:
        r0 = move-exception;
        r7 = r3;
        goto L_0x0358;
    L_0x03db:
        r0 = move-exception;
        r7 = r3;
        r3 = r4;
        goto L_0x0358;
    L_0x03e0:
        r0 = move-exception;
        r3 = r4;
        goto L_0x0358;
    L_0x03e4:
        r0 = move-exception;
        r7 = r6;
        r6 = r5;
        r5 = r4;
        goto L_0x0358;
    L_0x03ea:
        r1 = move-exception;
        r2 = r3;
        r4 = r5;
        r6 = r3;
        r5 = r3;
        goto L_0x02bd;
    L_0x03f1:
        r1 = move-exception;
        r4 = r5;
        r6 = r3;
        r5 = r3;
        goto L_0x02bd;
    L_0x03f7:
        r1 = move-exception;
        r4 = r5;
        r5 = r6;
        r6 = r3;
        goto L_0x02bd;
    L_0x03fd:
        r1 = move-exception;
        r11 = r4;
        r4 = r5;
        r5 = r6;
        r6 = r3;
        r3 = r11;
        goto L_0x02bd;
    L_0x0405:
        r1 = move-exception;
        r3 = r4;
        r4 = r5;
        r5 = r6;
        r6 = r7;
        goto L_0x02bd;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.android.pushselfshow.utils.b.b.b(android.content.Context, java.lang.String, java.lang.String):boolean");
    }

    public void c() {
        Message message = new Message();
        message.what = 2;
        c.a("PushSelfShowLog", "mDownloadHandler = " + this.a);
        if (this.a != null) {
            this.a.sendMessageDelayed(message, 1);
        }
    }
}
