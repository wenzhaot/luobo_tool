package com.tencent.bugly.proguard;

import android.content.Context;
import com.qiniu.android.common.Constants;
import com.umeng.message.util.HttpRequest;
import com.umeng.socialize.common.SocializeConstants;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: BUGLY */
public final class s {
    private static s b;
    public Map<String, String> a = null;
    private Context c;

    private s(Context context) {
        this.c = context;
    }

    public static s a(Context context) {
        if (b == null) {
            b = new s(context);
        }
        return b;
    }

    /* JADX WARNING: Removed duplicated region for block: B:80:0x017f A:{Catch:{ all -> 0x0191, Throwable -> 0x0196 }} */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x017f A:{Catch:{ all -> 0x0191, Throwable -> 0x0196 }} */
    public final byte[] a(java.lang.String r19, byte[] r20, com.tencent.bugly.proguard.v r21, java.util.Map<java.lang.String, java.lang.String> r22) {
        /*
        r18 = this;
        if (r19 != 0) goto L_0x000d;
    L_0x0002:
        r4 = "Failed for no URL.";
        r5 = 0;
        r5 = new java.lang.Object[r5];
        com.tencent.bugly.proguard.x.e(r4, r5);
        r4 = 0;
    L_0x000c:
        return r4;
    L_0x000d:
        r7 = 0;
        r8 = 0;
        if (r20 != 0) goto L_0x005a;
    L_0x0011:
        r4 = 0;
    L_0x0013:
        r6 = "request: %s, send: %d (pid=%d | tid=%d)";
        r9 = 4;
        r9 = new java.lang.Object[r9];
        r10 = 0;
        r9[r10] = r19;
        r10 = 1;
        r11 = java.lang.Long.valueOf(r4);
        r9[r10] = r11;
        r10 = 2;
        r11 = android.os.Process.myPid();
        r11 = java.lang.Integer.valueOf(r11);
        r9[r10] = r11;
        r10 = 3;
        r11 = android.os.Process.myTid();
        r11 = java.lang.Integer.valueOf(r11);
        r9[r10] = r11;
        com.tencent.bugly.proguard.x.c(r6, r9);
        r6 = 0;
        r9 = r19;
    L_0x003f:
        if (r7 > 0) goto L_0x01b7;
    L_0x0041:
        if (r8 > 0) goto L_0x01b7;
    L_0x0043:
        if (r6 == 0) goto L_0x005f;
    L_0x0045:
        r6 = 0;
    L_0x0046:
        r0 = r18;
        r10 = r0.c;
        r10 = com.tencent.bugly.crashreport.common.info.b.f(r10);
        if (r10 != 0) goto L_0x0091;
    L_0x0050:
        r10 = "Failed to request for network not avail";
        r11 = 0;
        r11 = new java.lang.Object[r11];
        com.tencent.bugly.proguard.x.d(r10, r11);
        goto L_0x003f;
    L_0x005a:
        r0 = r20;
        r4 = r0.length;
        r4 = (long) r4;
        goto L_0x0013;
    L_0x005f:
        r7 = r7 + 1;
        r10 = 1;
        if (r7 <= r10) goto L_0x0046;
    L_0x0064:
        r10 = new java.lang.StringBuilder;
        r11 = "try time: ";
        r10.<init>(r11);
        r10 = r10.append(r7);
        r10 = r10.toString();
        r11 = 0;
        r11 = new java.lang.Object[r11];
        com.tencent.bugly.proguard.x.c(r10, r11);
        r10 = new java.util.Random;
        r12 = java.lang.System.currentTimeMillis();
        r10.<init>(r12);
        r11 = 10000; // 0x2710 float:1.4013E-41 double:4.9407E-320;
        r10 = r10.nextInt(r11);
        r10 = (long) r10;
        r12 = 10000; // 0x2710 float:1.4013E-41 double:4.9407E-320;
        r10 = r10 + r12;
        android.os.SystemClock.sleep(r10);
        goto L_0x0046;
    L_0x0091:
        r0 = r21;
        r0.a(r4);
        r0 = r18;
        r1 = r20;
        r2 = r22;
        r14 = r0.a(r9, r1, r10, r2);
        if (r14 == 0) goto L_0x01a1;
    L_0x00a2:
        r12 = r14.getResponseCode();	 Catch:{ IOException -> 0x0173 }
        r10 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        if (r12 != r10) goto L_0x00d3;
    L_0x00aa:
        r10 = a(r14);	 Catch:{ IOException -> 0x0173 }
        r0 = r18;
        r0.a = r10;	 Catch:{ IOException -> 0x0173 }
        r10 = b(r14);	 Catch:{ IOException -> 0x0173 }
        if (r10 != 0) goto L_0x00c5;
    L_0x00b8:
        r12 = 0;
    L_0x00ba:
        r0 = r21;
        r0.b(r12);	 Catch:{ IOException -> 0x0173 }
        r14.disconnect();	 Catch:{ Throwable -> 0x00c8 }
    L_0x00c2:
        r4 = r10;
        goto L_0x000c;
    L_0x00c5:
        r11 = r10.length;	 Catch:{ IOException -> 0x0173 }
        r12 = (long) r11;
        goto L_0x00ba;
    L_0x00c8:
        r4 = move-exception;
        r5 = com.tencent.bugly.proguard.x.a(r4);
        if (r5 != 0) goto L_0x00c2;
    L_0x00cf:
        r4.printStackTrace();
        goto L_0x00c2;
    L_0x00d3:
        r10 = 301; // 0x12d float:4.22E-43 double:1.487E-321;
        if (r12 == r10) goto L_0x00e3;
    L_0x00d7:
        r10 = 302; // 0x12e float:4.23E-43 double:1.49E-321;
        if (r12 == r10) goto L_0x00e3;
    L_0x00db:
        r10 = 303; // 0x12f float:4.25E-43 double:1.497E-321;
        if (r12 == r10) goto L_0x00e3;
    L_0x00df:
        r10 = 307; // 0x133 float:4.3E-43 double:1.517E-321;
        if (r12 != r10) goto L_0x010c;
    L_0x00e3:
        r10 = 1;
    L_0x00e4:
        if (r10 == 0) goto L_0x01d5;
    L_0x00e6:
        r10 = 1;
        r6 = "Location";
        r11 = r14.getHeaderField(r6);	 Catch:{ IOException -> 0x01ba }
        if (r11 != 0) goto L_0x0119;
    L_0x00f0:
        r6 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x01ba }
        r11 = "Failed to redirect: %d";
        r6.<init>(r11);	 Catch:{ IOException -> 0x01ba }
        r6 = r6.append(r12);	 Catch:{ IOException -> 0x01ba }
        r6 = r6.toString();	 Catch:{ IOException -> 0x01ba }
        r11 = 0;
        r11 = new java.lang.Object[r11];	 Catch:{ IOException -> 0x01ba }
        com.tencent.bugly.proguard.x.e(r6, r11);	 Catch:{ IOException -> 0x01ba }
        r14.disconnect();	 Catch:{ Throwable -> 0x010e }
    L_0x0109:
        r4 = 0;
        goto L_0x000c;
    L_0x010c:
        r10 = 0;
        goto L_0x00e4;
    L_0x010e:
        r4 = move-exception;
        r5 = com.tencent.bugly.proguard.x.a(r4);
        if (r5 != 0) goto L_0x0109;
    L_0x0115:
        r4.printStackTrace();
        goto L_0x0109;
    L_0x0119:
        r8 = r8 + 1;
        r7 = 0;
        r6 = "redirect code: %d ,to:%s";
        r9 = 2;
        r9 = new java.lang.Object[r9];	 Catch:{ IOException -> 0x01c6 }
        r13 = 0;
        r15 = java.lang.Integer.valueOf(r12);	 Catch:{ IOException -> 0x01c6 }
        r9[r13] = r15;	 Catch:{ IOException -> 0x01c6 }
        r13 = 1;
        r9[r13] = r11;	 Catch:{ IOException -> 0x01c6 }
        com.tencent.bugly.proguard.x.c(r6, r9);	 Catch:{ IOException -> 0x01c6 }
        r6 = r10;
        r9 = r11;
        r16 = r7;
        r7 = r8;
        r8 = r16;
    L_0x0136:
        r10 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x01d3 }
        r11 = "response code ";
        r10.<init>(r11);	 Catch:{ IOException -> 0x01d3 }
        r10 = r10.append(r12);	 Catch:{ IOException -> 0x01d3 }
        r10 = r10.toString();	 Catch:{ IOException -> 0x01d3 }
        r11 = 0;
        r11 = new java.lang.Object[r11];	 Catch:{ IOException -> 0x01d3 }
        com.tencent.bugly.proguard.x.d(r10, r11);	 Catch:{ IOException -> 0x01d3 }
        r10 = r14.getContentLength();	 Catch:{ IOException -> 0x01d3 }
        r10 = (long) r10;	 Catch:{ IOException -> 0x01d3 }
        r12 = 0;
        r12 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1));
        if (r12 >= 0) goto L_0x0159;
    L_0x0157:
        r10 = 0;
    L_0x0159:
        r0 = r21;
        r0.b(r10);	 Catch:{ IOException -> 0x01d3 }
        r14.disconnect();	 Catch:{ Throwable -> 0x0168 }
    L_0x0161:
        r16 = r7;
        r7 = r8;
        r8 = r16;
        goto L_0x003f;
    L_0x0168:
        r10 = move-exception;
        r11 = com.tencent.bugly.proguard.x.a(r10);
        if (r11 != 0) goto L_0x0161;
    L_0x016f:
        r10.printStackTrace();
        goto L_0x0161;
    L_0x0173:
        r10 = move-exception;
        r16 = r8;
        r8 = r7;
        r7 = r16;
    L_0x0179:
        r11 = com.tencent.bugly.proguard.x.a(r10);	 Catch:{ all -> 0x0191 }
        if (r11 != 0) goto L_0x0182;
    L_0x017f:
        r10.printStackTrace();	 Catch:{ all -> 0x0191 }
    L_0x0182:
        r14.disconnect();	 Catch:{ Throwable -> 0x0186 }
        goto L_0x0161;
    L_0x0186:
        r10 = move-exception;
        r11 = com.tencent.bugly.proguard.x.a(r10);
        if (r11 != 0) goto L_0x0161;
    L_0x018d:
        r10.printStackTrace();
        goto L_0x0161;
    L_0x0191:
        r4 = move-exception;
        r14.disconnect();	 Catch:{ Throwable -> 0x0196 }
    L_0x0195:
        throw r4;
    L_0x0196:
        r5 = move-exception;
        r6 = com.tencent.bugly.proguard.x.a(r5);
        if (r6 != 0) goto L_0x0195;
    L_0x019d:
        r5.printStackTrace();
        goto L_0x0195;
    L_0x01a1:
        r10 = "Failed to execute post.";
        r11 = 0;
        r11 = new java.lang.Object[r11];
        com.tencent.bugly.proguard.x.c(r10, r11);
        r10 = 0;
        r0 = r21;
        r0.b(r10);
        r16 = r8;
        r8 = r7;
        r7 = r16;
        goto L_0x0161;
    L_0x01b7:
        r4 = 0;
        goto L_0x000c;
    L_0x01ba:
        r6 = move-exception;
        r16 = r6;
        r6 = r10;
        r10 = r16;
        r17 = r8;
        r8 = r7;
        r7 = r17;
        goto L_0x0179;
    L_0x01c6:
        r6 = move-exception;
        r9 = r11;
        r16 = r10;
        r10 = r6;
        r6 = r16;
        r17 = r7;
        r7 = r8;
        r8 = r17;
        goto L_0x0179;
    L_0x01d3:
        r10 = move-exception;
        goto L_0x0179;
    L_0x01d5:
        r16 = r8;
        r8 = r7;
        r7 = r16;
        goto L_0x0136;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.s.a(java.lang.String, byte[], com.tencent.bugly.proguard.v, java.util.Map):byte[]");
    }

    private static Map<String, String> a(HttpURLConnection httpURLConnection) {
        HashMap hashMap = new HashMap();
        Map headerFields = httpURLConnection.getHeaderFields();
        if (headerFields == null || headerFields.size() == 0) {
            return null;
        }
        for (String str : headerFields.keySet()) {
            List list = (List) headerFields.get(str);
            if (list.size() > 0) {
                hashMap.put(str, list.get(0));
            }
        }
        return hashMap;
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x004b A:{SYNTHETIC, Splitter: B:30:0x004b} */
    private static byte[] b(java.net.HttpURLConnection r6) {
        /*
        r0 = 0;
        if (r6 != 0) goto L_0x0004;
    L_0x0003:
        return r0;
    L_0x0004:
        r2 = new java.io.BufferedInputStream;	 Catch:{ Throwable -> 0x0056, all -> 0x0046 }
        r1 = r6.getInputStream();	 Catch:{ Throwable -> 0x0056, all -> 0x0046 }
        r2.<init>(r1);	 Catch:{ Throwable -> 0x0056, all -> 0x0046 }
        r1 = new java.io.ByteArrayOutputStream;	 Catch:{ Throwable -> 0x0021 }
        r1.<init>();	 Catch:{ Throwable -> 0x0021 }
        r3 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r3 = new byte[r3];	 Catch:{ Throwable -> 0x0021 }
    L_0x0016:
        r4 = r2.read(r3);	 Catch:{ Throwable -> 0x0021 }
        if (r4 <= 0) goto L_0x0036;
    L_0x001c:
        r5 = 0;
        r1.write(r3, r5, r4);	 Catch:{ Throwable -> 0x0021 }
        goto L_0x0016;
    L_0x0021:
        r1 = move-exception;
    L_0x0022:
        r3 = com.tencent.bugly.proguard.x.a(r1);	 Catch:{ all -> 0x0054 }
        if (r3 != 0) goto L_0x002b;
    L_0x0028:
        r1.printStackTrace();	 Catch:{ all -> 0x0054 }
    L_0x002b:
        if (r2 == 0) goto L_0x0003;
    L_0x002d:
        r2.close();	 Catch:{ Throwable -> 0x0031 }
        goto L_0x0003;
    L_0x0031:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x0003;
    L_0x0036:
        r1.flush();	 Catch:{ Throwable -> 0x0021 }
        r0 = r1.toByteArray();	 Catch:{ Throwable -> 0x0021 }
        r2.close();	 Catch:{ Throwable -> 0x0041 }
        goto L_0x0003;
    L_0x0041:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x0003;
    L_0x0046:
        r1 = move-exception;
        r2 = r0;
        r0 = r1;
    L_0x0049:
        if (r2 == 0) goto L_0x004e;
    L_0x004b:
        r2.close();	 Catch:{ Throwable -> 0x004f }
    L_0x004e:
        throw r0;
    L_0x004f:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x004e;
    L_0x0054:
        r0 = move-exception;
        goto L_0x0049;
    L_0x0056:
        r1 = move-exception;
        r2 = r0;
        goto L_0x0022;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.s.b(java.net.HttpURLConnection):byte[]");
    }

    private HttpURLConnection a(String str, byte[] bArr, String str2, Map<String, String> map) {
        if (str == null) {
            x.e("destUrl is null.", new Object[0]);
            return null;
        }
        HttpURLConnection a = a(str2, str);
        if (a == null) {
            x.e("Failed to get HttpURLConnection object.", new Object[0]);
            return null;
        }
        try {
            a.setRequestProperty("wup_version", SocializeConstants.PROTOCOL_VERSON);
            if (map != null && map.size() > 0) {
                for (Entry entry : map.entrySet()) {
                    a.setRequestProperty((String) entry.getKey(), URLEncoder.encode((String) entry.getValue(), Constants.UTF_8));
                }
            }
            a.setRequestProperty("A37", URLEncoder.encode(str2, Constants.UTF_8));
            a.setRequestProperty("A38", URLEncoder.encode(str2, Constants.UTF_8));
            OutputStream outputStream = a.getOutputStream();
            if (bArr == null) {
                outputStream.write(0);
            } else {
                outputStream.write(bArr);
            }
            return a;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            x.e("Failed to upload, please check your network.", new Object[0]);
            return null;
        }
    }

    private static HttpURLConnection a(String str, String str2) {
        try {
            HttpURLConnection httpURLConnection;
            URL url = new URL(str2);
            if (str == null || !str.toLowerCase(Locale.US).contains("wap")) {
                httpURLConnection = (HttpURLConnection) url.openConnection();
            } else {
                httpURLConnection = (HttpURLConnection) url.openConnection(new Proxy(Type.HTTP, new InetSocketAddress(System.getProperty("http.proxyHost"), Integer.parseInt(System.getProperty("http.proxyPort")))));
            }
            httpURLConnection.setConnectTimeout(30000);
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestMethod(HttpRequest.METHOD_POST);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setInstanceFollowRedirects(false);
            return httpURLConnection;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }
}
