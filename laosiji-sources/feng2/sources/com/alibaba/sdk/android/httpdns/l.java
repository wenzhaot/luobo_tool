package com.alibaba.sdk.android.httpdns;

import android.content.Context;
import java.util.concurrent.Callable;

class l implements Callable<String[]> {
    private static Context a;
    private static b hostManager = b.a();
    /* renamed from: a */
    private n f0a;
    private String b;
    private int d = 1;
    private String[] e = d.d;
    private String g = null;

    l(String str, n nVar) {
        this.b = str;
        this.a = nVar;
    }

    static void setContext(Context context) {
        a = context;
    }

    public void a(int i) {
        if (i >= 0) {
            this.d = i;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:61:0x01dc  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x01e1 A:{SYNTHETIC, Splitter: B:63:0x01e1} */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x01e6 A:{Catch:{ IOException -> 0x0280 }} */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x01dc  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x01e1 A:{SYNTHETIC, Splitter: B:63:0x01e1} */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x01e6 A:{Catch:{ IOException -> 0x0280 }} */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x01dc  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x01e1 A:{SYNTHETIC, Splitter: B:63:0x01e1} */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x01e6 A:{Catch:{ IOException -> 0x0280 }} */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0125 A:{Catch:{ all -> 0x0296 }} */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0130  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0135 A:{SYNTHETIC, Splitter: B:47:0x0135} */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x013a A:{Catch:{ IOException -> 0x013f }} */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x01dc  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x01e1 A:{SYNTHETIC, Splitter: B:63:0x01e1} */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x01e6 A:{Catch:{ IOException -> 0x0280 }} */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0125 A:{Catch:{ all -> 0x0296 }} */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0130  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0135 A:{SYNTHETIC, Splitter: B:47:0x0135} */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x013a A:{Catch:{ IOException -> 0x013f }} */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x01dc  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x01e1 A:{SYNTHETIC, Splitter: B:63:0x01e1} */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x01e6 A:{Catch:{ IOException -> 0x0280 }} */
    /* renamed from: b */
    public java.lang.String[] call() {
        /*
        r8 = this;
        r3 = 0;
        r1 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r2 = 14;
        if (r1 < r2) goto L_0x000d;
    L_0x0007:
        r1 = 40965; // 0xa005 float:5.7404E-41 double:2.02394E-319;
        android.net.TrafficStats.setThreadStatsTag(r1);	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
    L_0x000d:
        r1 = hostManager;	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r2 = r8.b;	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r1.b(r2);	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r1 = r8.a;	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r1 = com.alibaba.sdk.android.httpdns.s.a(r1);	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r8.g = r1;	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r1 = r8.g;	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        if (r1 != 0) goto L_0x0054;
    L_0x0020:
        r1 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r1.<init>();	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r2 = "serverIp is null, give up query for hostname:";
        r1 = r1.append(r2);	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r2 = r8.b;	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r1 = r1.append(r2);	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r1 = r1.toString();	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        com.alibaba.sdk.android.httpdns.g.e(r1);	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r5 = r3;
        r1 = r3;
    L_0x003b:
        if (r1 == 0) goto L_0x0040;
    L_0x003d:
        r1.disconnect();
    L_0x0040:
        if (r5 == 0) goto L_0x0045;
    L_0x0042:
        r5.close();	 Catch:{ IOException -> 0x027a }
    L_0x0045:
        if (r3 == 0) goto L_0x004a;
    L_0x0047:
        r3.close();	 Catch:{ IOException -> 0x027a }
    L_0x004a:
        r1 = hostManager;
        r2 = r8.b;
        r1.c(r2);
        r1 = r8.e;
        return r1;
    L_0x0054:
        r1 = com.alibaba.sdk.android.httpdns.a.a();	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        if (r1 == 0) goto L_0x0145;
    L_0x005a:
        r1 = com.alibaba.sdk.android.httpdns.a.getTimestamp();	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r2 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r2.<init>();	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r4 = com.alibaba.sdk.android.httpdns.d.PROTOCOL;	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r2 = r2.append(r4);	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r4 = r8.g;	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r2 = r2.append(r4);	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r4 = ":";
        r2 = r2.append(r4);	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r4 = com.alibaba.sdk.android.httpdns.d.d;	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r2 = r2.append(r4);	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r4 = "/";
        r2 = r2.append(r4);	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r4 = com.alibaba.sdk.android.httpdns.d.c;	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r2 = r2.append(r4);	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r4 = "/sign_d?host=";
        r2 = r2.append(r4);	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r4 = r8.b;	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r2 = r2.append(r4);	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r4 = "&sdk=android_";
        r2 = r2.append(r4);	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r4 = "1.1.3.1";
        r2 = r2.append(r4);	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r4 = "&t=";
        r2 = r2.append(r4);	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r2 = r2.append(r1);	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r4 = "&s=";
        r2 = r2.append(r4);	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r4 = r8.b;	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r1 = com.alibaba.sdk.android.httpdns.a.a(r4, r1);	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r1 = r2.append(r1);	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r1 = r1.toString();	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
    L_0x00c4:
        r2 = new java.net.URL;	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r2.<init>(r1);	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r1 = r2.openConnection();	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r1 = (java.net.HttpURLConnection) r1;	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r2 = com.alibaba.sdk.android.httpdns.d.a;	 Catch:{ Throwable -> 0x02a0, all -> 0x028b }
        r1.setConnectTimeout(r2);	 Catch:{ Throwable -> 0x02a0, all -> 0x028b }
        r2 = com.alibaba.sdk.android.httpdns.d.a;	 Catch:{ Throwable -> 0x02a0, all -> 0x028b }
        r1.setReadTimeout(r2);	 Catch:{ Throwable -> 0x02a0, all -> 0x028b }
        r2 = r1 instanceof javax.net.ssl.HttpsURLConnection;	 Catch:{ Throwable -> 0x02a0, all -> 0x028b }
        if (r2 == 0) goto L_0x00e9;
    L_0x00dd:
        r0 = r1;
        r0 = (javax.net.ssl.HttpsURLConnection) r0;	 Catch:{ Throwable -> 0x02a0, all -> 0x028b }
        r2 = r0;
        r4 = new com.alibaba.sdk.android.httpdns.l$1;	 Catch:{ Throwable -> 0x02a0, all -> 0x028b }
        r4.<init>();	 Catch:{ Throwable -> 0x02a0, all -> 0x028b }
        r2.setHostnameVerifier(r4);	 Catch:{ Throwable -> 0x02a0, all -> 0x028b }
    L_0x00e9:
        r2 = r1.getResponseCode();	 Catch:{ Throwable -> 0x02a0, all -> 0x028b }
        r4 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        if (r2 == r4) goto L_0x01ea;
    L_0x00f1:
        r5 = r1.getErrorStream();	 Catch:{ Throwable -> 0x02a0, all -> 0x028b }
        r4 = new java.io.BufferedReader;	 Catch:{ Throwable -> 0x02a6, all -> 0x0291 }
        r2 = new java.io.InputStreamReader;	 Catch:{ Throwable -> 0x02a6, all -> 0x0291 }
        r6 = "UTF-8";
        r2.<init>(r5, r6);	 Catch:{ Throwable -> 0x02a6, all -> 0x0291 }
        r4.<init>(r2);	 Catch:{ Throwable -> 0x02a6, all -> 0x0291 }
        r2 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x0111, all -> 0x01d6 }
        r2.<init>();	 Catch:{ Throwable -> 0x0111, all -> 0x01d6 }
    L_0x0107:
        r3 = r4.readLine();	 Catch:{ Throwable -> 0x0111, all -> 0x01d6 }
        if (r3 == 0) goto L_0x0191;
    L_0x010d:
        r2.append(r3);	 Catch:{ Throwable -> 0x0111, all -> 0x01d6 }
        goto L_0x0107;
    L_0x0111:
        r2 = move-exception;
        r3 = r5;
        r7 = r4;
        r4 = r1;
        r1 = r2;
        r2 = r7;
    L_0x0117:
        com.alibaba.sdk.android.httpdns.g.a(r1);	 Catch:{ all -> 0x0296 }
        r5 = r8.b;	 Catch:{ all -> 0x0296 }
        r6 = r8.g;	 Catch:{ all -> 0x0296 }
        com.alibaba.sdk.android.httpdns.s.a(r5, r6, r1);	 Catch:{ all -> 0x0296 }
        r1 = r8.d;	 Catch:{ all -> 0x0296 }
        if (r1 <= 0) goto L_0x012e;
    L_0x0125:
        r1 = r8.d;	 Catch:{ all -> 0x0296 }
        r1 = r1 + -1;
        r8.d = r1;	 Catch:{ all -> 0x0296 }
        r8.call();	 Catch:{ all -> 0x0296 }
    L_0x012e:
        if (r4 == 0) goto L_0x0133;
    L_0x0130:
        r4.disconnect();
    L_0x0133:
        if (r3 == 0) goto L_0x0138;
    L_0x0135:
        r3.close();	 Catch:{ IOException -> 0x013f }
    L_0x0138:
        if (r2 == 0) goto L_0x004a;
    L_0x013a:
        r2.close();	 Catch:{ IOException -> 0x013f }
        goto L_0x004a;
    L_0x013f:
        r1 = move-exception;
        com.alibaba.sdk.android.httpdns.g.a(r1);
        goto L_0x004a;
    L_0x0145:
        r1 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r1.<init>();	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r2 = com.alibaba.sdk.android.httpdns.d.PROTOCOL;	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r1 = r1.append(r2);	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r2 = r8.g;	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r1 = r1.append(r2);	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r2 = ":";
        r1 = r1.append(r2);	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r2 = com.alibaba.sdk.android.httpdns.d.d;	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r1 = r1.append(r2);	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r2 = "/";
        r1 = r1.append(r2);	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r2 = com.alibaba.sdk.android.httpdns.d.c;	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r1 = r1.append(r2);	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r2 = "/d?host=";
        r1 = r1.append(r2);	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r2 = r8.b;	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r1 = r1.append(r2);	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r2 = "&sdk=android_";
        r1 = r1.append(r2);	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r2 = "1.1.3.1";
        r1 = r1.append(r2);	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        r1 = r1.toString();	 Catch:{ Throwable -> 0x029b, all -> 0x0286 }
        goto L_0x00c4;
    L_0x0191:
        r3 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x0111, all -> 0x01d6 }
        r3.<init>();	 Catch:{ Throwable -> 0x0111, all -> 0x01d6 }
        r6 = "response code is ";
        r3 = r3.append(r6);	 Catch:{ Throwable -> 0x0111, all -> 0x01d6 }
        r6 = r1.getResponseCode();	 Catch:{ Throwable -> 0x0111, all -> 0x01d6 }
        r3 = r3.append(r6);	 Catch:{ Throwable -> 0x0111, all -> 0x01d6 }
        r6 = " expect 200. response body is ";
        r3 = r3.append(r6);	 Catch:{ Throwable -> 0x0111, all -> 0x01d6 }
        r6 = r2.toString();	 Catch:{ Throwable -> 0x0111, all -> 0x01d6 }
        r3 = r3.append(r6);	 Catch:{ Throwable -> 0x0111, all -> 0x01d6 }
        r3 = r3.toString();	 Catch:{ Throwable -> 0x0111, all -> 0x01d6 }
        com.alibaba.sdk.android.httpdns.g.f(r3);	 Catch:{ Throwable -> 0x0111, all -> 0x01d6 }
        r3 = new com.alibaba.sdk.android.httpdns.e;	 Catch:{ Throwable -> 0x0111, all -> 0x01d6 }
        r6 = r1.getResponseCode();	 Catch:{ Throwable -> 0x0111, all -> 0x01d6 }
        r2 = r2.toString();	 Catch:{ Throwable -> 0x0111, all -> 0x01d6 }
        r3.<init>(r6, r2);	 Catch:{ Throwable -> 0x0111, all -> 0x01d6 }
        r2 = new com.alibaba.sdk.android.httpdns.f;	 Catch:{ Throwable -> 0x0111, all -> 0x01d6 }
        r6 = r1.getResponseCode();	 Catch:{ Throwable -> 0x0111, all -> 0x01d6 }
        r3 = r3.a();	 Catch:{ Throwable -> 0x0111, all -> 0x01d6 }
        r2.<init>(r6, r3);	 Catch:{ Throwable -> 0x0111, all -> 0x01d6 }
        throw r2;	 Catch:{ Throwable -> 0x0111, all -> 0x01d6 }
    L_0x01d6:
        r2 = move-exception;
        r3 = r4;
        r4 = r1;
        r1 = r2;
    L_0x01da:
        if (r4 == 0) goto L_0x01df;
    L_0x01dc:
        r4.disconnect();
    L_0x01df:
        if (r5 == 0) goto L_0x01e4;
    L_0x01e1:
        r5.close();	 Catch:{ IOException -> 0x0280 }
    L_0x01e4:
        if (r3 == 0) goto L_0x01e9;
    L_0x01e6:
        r3.close();	 Catch:{ IOException -> 0x0280 }
    L_0x01e9:
        throw r1;
    L_0x01ea:
        r5 = r1.getInputStream();	 Catch:{ Throwable -> 0x02a0, all -> 0x028b }
        r4 = new java.io.BufferedReader;	 Catch:{ Throwable -> 0x02a6, all -> 0x0291 }
        r2 = new java.io.InputStreamReader;	 Catch:{ Throwable -> 0x02a6, all -> 0x0291 }
        r6 = "UTF-8";
        r2.<init>(r5, r6);	 Catch:{ Throwable -> 0x02a6, all -> 0x0291 }
        r4.<init>(r2);	 Catch:{ Throwable -> 0x02a6, all -> 0x0291 }
        r2 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x020a, all -> 0x0274 }
        r2.<init>();	 Catch:{ Throwable -> 0x020a, all -> 0x0274 }
    L_0x0200:
        r3 = r4.readLine();	 Catch:{ Throwable -> 0x020a, all -> 0x0274 }
        if (r3 == 0) goto L_0x0212;
    L_0x0206:
        r2.append(r3);	 Catch:{ Throwable -> 0x020a, all -> 0x0274 }
        goto L_0x0200;
    L_0x020a:
        r2 = move-exception;
        r3 = r5;
        r7 = r4;
        r4 = r1;
        r1 = r2;
        r2 = r7;
        goto L_0x0117;
    L_0x0212:
        r3 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x020a, all -> 0x0274 }
        r3.<init>();	 Catch:{ Throwable -> 0x020a, all -> 0x0274 }
        r6 = "resolve host: ";
        r3 = r3.append(r6);	 Catch:{ Throwable -> 0x020a, all -> 0x0274 }
        r6 = r8.b;	 Catch:{ Throwable -> 0x020a, all -> 0x0274 }
        r3 = r3.append(r6);	 Catch:{ Throwable -> 0x020a, all -> 0x0274 }
        r6 = ", return: ";
        r3 = r3.append(r6);	 Catch:{ Throwable -> 0x020a, all -> 0x0274 }
        r6 = r2.toString();	 Catch:{ Throwable -> 0x020a, all -> 0x0274 }
        r3 = r3.append(r6);	 Catch:{ Throwable -> 0x020a, all -> 0x0274 }
        r3 = r3.toString();	 Catch:{ Throwable -> 0x020a, all -> 0x0274 }
        com.alibaba.sdk.android.httpdns.g.e(r3);	 Catch:{ Throwable -> 0x020a, all -> 0x0274 }
        r3 = new com.alibaba.sdk.android.httpdns.c;	 Catch:{ Throwable -> 0x020a, all -> 0x0274 }
        r2 = r2.toString();	 Catch:{ Throwable -> 0x020a, all -> 0x0274 }
        r3.<init>(r2);	 Catch:{ Throwable -> 0x020a, all -> 0x0274 }
        r2 = hostManager;	 Catch:{ Throwable -> 0x020a, all -> 0x0274 }
        r2 = r2.a();	 Catch:{ Throwable -> 0x020a, all -> 0x0274 }
        r6 = 100;
        if (r2 >= r6) goto L_0x026b;
    L_0x024d:
        r2 = hostManager;	 Catch:{ Throwable -> 0x020a, all -> 0x0274 }
        r6 = r8.b;	 Catch:{ Throwable -> 0x020a, all -> 0x0274 }
        r2.a(r6, r3);	 Catch:{ Throwable -> 0x020a, all -> 0x0274 }
        r2 = r8.b;	 Catch:{ Throwable -> 0x020a, all -> 0x0274 }
        r6 = r8.g;	 Catch:{ Throwable -> 0x020a, all -> 0x0274 }
        com.alibaba.sdk.android.httpdns.s.a(r2, r6);	 Catch:{ Throwable -> 0x020a, all -> 0x0274 }
        r2 = hostManager;	 Catch:{ Throwable -> 0x020a, all -> 0x0274 }
        r6 = r8.b;	 Catch:{ Throwable -> 0x020a, all -> 0x0274 }
        r2.c(r6);	 Catch:{ Throwable -> 0x020a, all -> 0x0274 }
        r2 = r3.a();	 Catch:{ Throwable -> 0x020a, all -> 0x0274 }
        r8.e = r2;	 Catch:{ Throwable -> 0x020a, all -> 0x0274 }
        r3 = r4;
        goto L_0x003b;
    L_0x026b:
        r2 = new java.lang.Exception;	 Catch:{ Throwable -> 0x020a, all -> 0x0274 }
        r3 = "the total number of hosts is exceed 100";
        r2.<init>(r3);	 Catch:{ Throwable -> 0x020a, all -> 0x0274 }
        throw r2;	 Catch:{ Throwable -> 0x020a, all -> 0x0274 }
    L_0x0274:
        r2 = move-exception;
        r3 = r4;
        r4 = r1;
        r1 = r2;
        goto L_0x01da;
    L_0x027a:
        r1 = move-exception;
        com.alibaba.sdk.android.httpdns.g.a(r1);
        goto L_0x004a;
    L_0x0280:
        r2 = move-exception;
        com.alibaba.sdk.android.httpdns.g.a(r2);
        goto L_0x01e9;
    L_0x0286:
        r1 = move-exception;
        r5 = r3;
        r4 = r3;
        goto L_0x01da;
    L_0x028b:
        r2 = move-exception;
        r5 = r3;
        r4 = r1;
        r1 = r2;
        goto L_0x01da;
    L_0x0291:
        r2 = move-exception;
        r4 = r1;
        r1 = r2;
        goto L_0x01da;
    L_0x0296:
        r1 = move-exception;
        r5 = r3;
        r3 = r2;
        goto L_0x01da;
    L_0x029b:
        r1 = move-exception;
        r2 = r3;
        r4 = r3;
        goto L_0x0117;
    L_0x02a0:
        r2 = move-exception;
        r4 = r1;
        r1 = r2;
        r2 = r3;
        goto L_0x0117;
    L_0x02a6:
        r2 = move-exception;
        r4 = r1;
        r1 = r2;
        r2 = r3;
        r3 = r5;
        goto L_0x0117;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.httpdns.l.b():java.lang.String[]");
    }
}
