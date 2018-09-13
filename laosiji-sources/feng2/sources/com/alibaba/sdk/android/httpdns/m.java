package com.alibaba.sdk.android.httpdns;

import java.util.concurrent.Callable;

public class m implements Callable<String[]> {
    private int d;

    public m(int i) {
        this.d = i;
    }

    /* JADX WARNING: Removed duplicated region for block: B:56:0x00ef  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x00f4 A:{SYNTHETIC, Splitter: B:58:0x00f4} */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x00f9 A:{Catch:{ IOException -> 0x00fd }} */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00b5 A:{Catch:{ all -> 0x0110 }} */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00c0  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00c5 A:{SYNTHETIC, Splitter: B:42:0x00c5} */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00ca A:{Catch:{ IOException -> 0x00ce }} */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00ef  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x00f4 A:{SYNTHETIC, Splitter: B:58:0x00f4} */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x00f9 A:{Catch:{ IOException -> 0x00fd }} */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00b5 A:{Catch:{ all -> 0x0110 }} */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00c0  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00c5 A:{SYNTHETIC, Splitter: B:42:0x00c5} */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00ca A:{Catch:{ IOException -> 0x00ce }} */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00ef  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x00f4 A:{SYNTHETIC, Splitter: B:58:0x00f4} */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x00f9 A:{Catch:{ IOException -> 0x00fd }} */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00ef  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x00f4 A:{SYNTHETIC, Splitter: B:58:0x00f4} */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x00f9 A:{Catch:{ IOException -> 0x00fd }} */
    /* renamed from: b */
    public java.lang.String[] call() {
        /*
        r8 = this;
        r3 = 0;
        r1 = com.alibaba.sdk.android.httpdns.o.a();	 Catch:{ Exception -> 0x0114, all -> 0x00ea }
        r1 = r1.f();	 Catch:{ Exception -> 0x0114, all -> 0x00ea }
        if (r1 == 0) goto L_0x0123;
    L_0x000b:
        r2 = new java.net.URL;	 Catch:{ Exception -> 0x0114, all -> 0x00ea }
        r2.<init>(r1);	 Catch:{ Exception -> 0x0114, all -> 0x00ea }
        r1 = r2.openConnection();	 Catch:{ Exception -> 0x0114, all -> 0x00ea }
        r1 = (java.net.HttpURLConnection) r1;	 Catch:{ Exception -> 0x0114, all -> 0x00ea }
        r2 = 15000; // 0x3a98 float:2.102E-41 double:7.411E-320;
        r1.setConnectTimeout(r2);	 Catch:{ Exception -> 0x0118, all -> 0x0102 }
        r2 = 15000; // 0x3a98 float:2.102E-41 double:7.411E-320;
        r1.setReadTimeout(r2);	 Catch:{ Exception -> 0x0118, all -> 0x0102 }
        r2 = r1 instanceof javax.net.ssl.HttpsURLConnection;	 Catch:{ Exception -> 0x0118, all -> 0x0102 }
        if (r2 == 0) goto L_0x0030;
    L_0x0024:
        r0 = r1;
        r0 = (javax.net.ssl.HttpsURLConnection) r0;	 Catch:{ Exception -> 0x0118, all -> 0x0102 }
        r2 = r0;
        r4 = new com.alibaba.sdk.android.httpdns.m$1;	 Catch:{ Exception -> 0x0118, all -> 0x0102 }
        r4.<init>();	 Catch:{ Exception -> 0x0118, all -> 0x0102 }
        r2.setHostnameVerifier(r4);	 Catch:{ Exception -> 0x0118, all -> 0x0102 }
    L_0x0030:
        r2 = r1.getResponseCode();	 Catch:{ Exception -> 0x0118, all -> 0x0102 }
        r4 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        if (r2 == r4) goto L_0x0081;
    L_0x0038:
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0118, all -> 0x0102 }
        r2.<init>();	 Catch:{ Exception -> 0x0118, all -> 0x0102 }
        r4 = "response code is ";
        r2 = r2.append(r4);	 Catch:{ Exception -> 0x0118, all -> 0x0102 }
        r4 = r1.getResponseCode();	 Catch:{ Exception -> 0x0118, all -> 0x0102 }
        r2 = r2.append(r4);	 Catch:{ Exception -> 0x0118, all -> 0x0102 }
        r4 = " expect 200";
        r2 = r2.append(r4);	 Catch:{ Exception -> 0x0118, all -> 0x0102 }
        r2 = r2.toString();	 Catch:{ Exception -> 0x0118, all -> 0x0102 }
        com.alibaba.sdk.android.httpdns.g.f(r2);	 Catch:{ Exception -> 0x0118, all -> 0x0102 }
        r2 = com.alibaba.sdk.android.httpdns.o.a();	 Catch:{ Exception -> 0x0118, all -> 0x0102 }
        r4 = new com.alibaba.sdk.android.httpdns.f;	 Catch:{ Exception -> 0x0118, all -> 0x0102 }
        r5 = r1.getResponseCode();	 Catch:{ Exception -> 0x0118, all -> 0x0102 }
        r6 = "";
        r4.<init>(r5, r6);	 Catch:{ Exception -> 0x0118, all -> 0x0102 }
        r2.b(r4);	 Catch:{ Exception -> 0x0118, all -> 0x0102 }
        r5 = r3;
    L_0x006e:
        if (r1 == 0) goto L_0x0073;
    L_0x0070:
        r1.disconnect();
    L_0x0073:
        if (r5 == 0) goto L_0x0078;
    L_0x0075:
        r5.close();	 Catch:{ IOException -> 0x00e5 }
    L_0x0078:
        if (r3 == 0) goto L_0x007d;
    L_0x007a:
        r3.close();	 Catch:{ IOException -> 0x00e5 }
    L_0x007d:
        r1 = 0;
        r1 = new java.lang.String[r1];
        return r1;
    L_0x0081:
        r5 = r1.getInputStream();	 Catch:{ Exception -> 0x0118, all -> 0x0102 }
        r4 = new java.io.BufferedReader;	 Catch:{ Exception -> 0x011d, all -> 0x0107 }
        r2 = new java.io.InputStreamReader;	 Catch:{ Exception -> 0x011d, all -> 0x0107 }
        r6 = "UTF-8";
        r2.<init>(r5, r6);	 Catch:{ Exception -> 0x011d, all -> 0x0107 }
        r4.<init>(r2);	 Catch:{ Exception -> 0x011d, all -> 0x0107 }
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00a1, all -> 0x010b }
        r2.<init>();	 Catch:{ Exception -> 0x00a1, all -> 0x010b }
    L_0x0097:
        r3 = r4.readLine();	 Catch:{ Exception -> 0x00a1, all -> 0x010b }
        if (r3 == 0) goto L_0x00d3;
    L_0x009d:
        r2.append(r3);	 Catch:{ Exception -> 0x00a1, all -> 0x010b }
        goto L_0x0097;
    L_0x00a1:
        r2 = move-exception;
        r3 = r5;
        r7 = r4;
        r4 = r1;
        r1 = r2;
        r2 = r7;
    L_0x00a7:
        com.alibaba.sdk.android.httpdns.g.a(r1);	 Catch:{ all -> 0x0110 }
        r5 = com.alibaba.sdk.android.httpdns.o.a();	 Catch:{ all -> 0x0110 }
        r5.b(r1);	 Catch:{ all -> 0x0110 }
        r1 = r8.d;	 Catch:{ all -> 0x0110 }
        if (r1 <= 0) goto L_0x00be;
    L_0x00b5:
        r1 = r8.d;	 Catch:{ all -> 0x0110 }
        r1 = r1 + -1;
        r8.d = r1;	 Catch:{ all -> 0x0110 }
        r8.call();	 Catch:{ all -> 0x0110 }
    L_0x00be:
        if (r4 == 0) goto L_0x00c3;
    L_0x00c0:
        r4.disconnect();
    L_0x00c3:
        if (r3 == 0) goto L_0x00c8;
    L_0x00c5:
        r3.close();	 Catch:{ IOException -> 0x00ce }
    L_0x00c8:
        if (r2 == 0) goto L_0x007d;
    L_0x00ca:
        r2.close();	 Catch:{ IOException -> 0x00ce }
        goto L_0x007d;
    L_0x00ce:
        r1 = move-exception;
        com.alibaba.sdk.android.httpdns.g.a(r1);
        goto L_0x007d;
    L_0x00d3:
        r3 = new com.alibaba.sdk.android.httpdns.p;	 Catch:{ Exception -> 0x00a1, all -> 0x010b }
        r2 = r2.toString();	 Catch:{ Exception -> 0x00a1, all -> 0x010b }
        r3.<init>(r2);	 Catch:{ Exception -> 0x00a1, all -> 0x010b }
        r2 = com.alibaba.sdk.android.httpdns.o.a();	 Catch:{ Exception -> 0x00a1, all -> 0x010b }
        r2.a(r3);	 Catch:{ Exception -> 0x00a1, all -> 0x010b }
        r3 = r4;
        goto L_0x006e;
    L_0x00e5:
        r1 = move-exception;
        com.alibaba.sdk.android.httpdns.g.a(r1);
        goto L_0x007d;
    L_0x00ea:
        r1 = move-exception;
        r5 = r3;
        r4 = r3;
    L_0x00ed:
        if (r4 == 0) goto L_0x00f2;
    L_0x00ef:
        r4.disconnect();
    L_0x00f2:
        if (r5 == 0) goto L_0x00f7;
    L_0x00f4:
        r5.close();	 Catch:{ IOException -> 0x00fd }
    L_0x00f7:
        if (r3 == 0) goto L_0x00fc;
    L_0x00f9:
        r3.close();	 Catch:{ IOException -> 0x00fd }
    L_0x00fc:
        throw r1;
    L_0x00fd:
        r2 = move-exception;
        com.alibaba.sdk.android.httpdns.g.a(r2);
        goto L_0x00fc;
    L_0x0102:
        r2 = move-exception;
        r5 = r3;
        r4 = r1;
        r1 = r2;
        goto L_0x00ed;
    L_0x0107:
        r2 = move-exception;
        r4 = r1;
        r1 = r2;
        goto L_0x00ed;
    L_0x010b:
        r2 = move-exception;
        r3 = r4;
        r4 = r1;
        r1 = r2;
        goto L_0x00ed;
    L_0x0110:
        r1 = move-exception;
        r5 = r3;
        r3 = r2;
        goto L_0x00ed;
    L_0x0114:
        r1 = move-exception;
        r2 = r3;
        r4 = r3;
        goto L_0x00a7;
    L_0x0118:
        r2 = move-exception;
        r4 = r1;
        r1 = r2;
        r2 = r3;
        goto L_0x00a7;
    L_0x011d:
        r2 = move-exception;
        r4 = r1;
        r1 = r2;
        r2 = r3;
        r3 = r5;
        goto L_0x00a7;
    L_0x0123:
        r5 = r3;
        r1 = r3;
        goto L_0x006e;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.httpdns.m.b():java.lang.String[]");
    }
}
