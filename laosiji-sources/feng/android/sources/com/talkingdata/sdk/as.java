package com.talkingdata.sdk;

import android.content.Context;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import com.feng.car.utils.PermissionsConstant;
import com.feng.car.video.shortvideo.FileUtils;
import com.taobao.accs.AccsClientConfig;
import com.umeng.message.util.HttpRequest;
import com.umeng.qq.handler.QQConstant;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.GZIPInputStream;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/* compiled from: td */
public class as {
    public static int a = PermissionsConstant.CODE_FOR_DOWN_WRITE_PERMISSION_BASE;
    public static int b = PermissionsConstant.CODE_FOR_DOWN_WRITE_PERMISSION_BASE;
    public static volatile HashMap c = new HashMap();
    private static final int d = 600;
    private static final int e = 60000;
    private static final int f = 60000;
    private static Context g = null;
    private static volatile HashMap h = new HashMap();

    /* compiled from: td */
    static class a {
        String host = null;
        String resolveIp = null;
        String savedIp = null;
        String staticIp = null;
        String successIp = null;

        a() {
        }
    }

    /* compiled from: td */
    static class b {
        static final int resolvedIp = 1;
        static final int savedIp = 3;
        static final int staticIp = 4;
        static final int successIp = 2;

        b() {
        }
    }

    /* compiled from: td */
    public class c {
        public static final String EMPTY = "";
        public static final String FORM = "application/x-www-form-urlencoded";
        public static final String JSON = "application/json";
        public static final String UNIVERSAL_STREAM = "application/octet-stream";
    }

    /* compiled from: td */
    static class d implements X509TrustManager {
        X509Certificate cert;

        d(X509Certificate x509Certificate) {
            this.cert = x509Certificate;
        }

        public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) {
        }

        public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) {
            if (x509CertificateArr.length == 0) {
            }
            if (!x509CertificateArr[0].getIssuerDN().equals(this.cert.getSubjectDN())) {
            }
            try {
                String str2;
                String name = x509CertificateArr[0].getSubjectDN().getName();
                int indexOf = name.indexOf("CN=");
                if (indexOf >= 0) {
                    name = name.substring(indexOf + 3);
                    indexOf = name.indexOf(MiPushClient.ACCEPT_TIME_SEPARATOR);
                    if (indexOf >= 0) {
                        name = name.substring(0, indexOf);
                    }
                }
                String[] split = name.split("\\.");
                if (split.length >= 2) {
                    str2 = split[split.length - 2] + FileUtils.FILE_EXTENSION_SEPARATOR + split[split.length - 1];
                } else {
                    str2 = name;
                }
                if (!as.c.containsKey(Long.valueOf(Thread.currentThread().getId()))) {
                    throw new CertificateException("No valid host provided!");
                } else if (((String) as.c.get(Long.valueOf(Thread.currentThread().getId()))).endsWith(str2)) {
                    x509CertificateArr[0].verify(this.cert.getPublicKey());
                    x509CertificateArr[0].checkValidity();
                } else {
                    throw new CertificateException("Server certificate has incorrect host name!");
                }
            } catch (Throwable th) {
                cs.postSDKError(th);
                if (th instanceof CertificateException) {
                }
            }
        }

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }

    /* compiled from: td */
    public static class e {
        public String responseMsg;
        public int statusCode;

        e(int i, String str) {
            this.statusCode = i;
            this.responseMsg = str;
        }

        e(int i) {
            this(i, "");
        }

        public int getStatusCode() {
            return this.statusCode;
        }

        public String getResponseMsg() {
            return this.responseMsg;
        }
    }

    public static e a(Context context, String str, String str2, String str3, String str4, byte[] bArr, String str5) {
        return a(context, str, str2, str3, str4, bArr, AccsClientConfig.DEFAULT_CONFIGTAG, str5);
    }

    public static e a(Context context, a aVar, String str, byte[] bArr, String str2) {
        g = context;
        b(aVar.getHost(), "");
        return a(aVar.getHost(), "", str, aVar.getCert(), bArr, AccsClientConfig.DEFAULT_CONFIGTAG, aVar, str2, null);
    }

    public static e a(Context context, String str, String str2, String str3, String str4, byte[] bArr, String str5, String str6) {
        return a(context, str, str2, str3, str4, bArr, str5, str6, null);
    }

    public static e a(Context context, String str, String str2, String str3, String str4, byte[] bArr, String str5, String str6, String str7) {
        g = context;
        b(str, str2);
        return a(str, str2, str3, str4, bArr, str5, null, str6, str7);
    }

    public static String a(String str, boolean z) {
        return b(str, null, z);
    }

    public static String a(String str, String str2, boolean z) {
        return b(str, str2, z);
    }

    public static SSLSocketFactory a(boolean z, X509Certificate x509Certificate) {
        try {
            SSLContext instance = SSLContext.getInstance("TLS");
            if (!z || x509Certificate == null) {
                instance.init(null, null, null);
            } else {
                instance.init(null, new TrustManager[]{new d(x509Certificate)}, null);
            }
            return instance.getSocketFactory();
        } catch (Throwable th) {
            cs.postSDKError(th);
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:56:0x00c0 A:{SYNTHETIC, Splitter: B:56:0x00c0} */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00c5 A:{SYNTHETIC, Splitter: B:59:0x00c5} */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x009c A:{SYNTHETIC, Splitter: B:37:0x009c} */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00a1 A:{SYNTHETIC, Splitter: B:40:0x00a1} */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00c0 A:{SYNTHETIC, Splitter: B:56:0x00c0} */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00c5 A:{SYNTHETIC, Splitter: B:59:0x00c5} */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x009c A:{SYNTHETIC, Splitter: B:37:0x009c} */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00a1 A:{SYNTHETIC, Splitter: B:40:0x00a1} */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00c0 A:{SYNTHETIC, Splitter: B:56:0x00c0} */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00c5 A:{SYNTHETIC, Splitter: B:59:0x00c5} */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00c0 A:{SYNTHETIC, Splitter: B:56:0x00c0} */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00c5 A:{SYNTHETIC, Splitter: B:59:0x00c5} */
    private static java.lang.String b(java.lang.String r9, java.lang.String r10, boolean r11) {
        /*
        r1 = 0;
        r0 = com.talkingdata.sdk.bo.b(r9);
        if (r0 == 0) goto L_0x0009;
    L_0x0007:
        r0 = r1;
    L_0x0008:
        return r0;
    L_0x0009:
        r2 = 0;
        r4 = new java.lang.StringBuffer;
        r4.<init>();
        r3 = new java.net.URL;	 Catch:{ Throwable -> 0x00e1, all -> 0x00bc }
        r3.<init>(r9);	 Catch:{ Throwable -> 0x00e1, all -> 0x00bc }
        r0 = 0;
        r5 = 0;
        r6 = 0;
        r0 = a(r3, r0, r5, r6);	 Catch:{ Throwable -> 0x00e1, all -> 0x00bc }
        r0 = (java.net.HttpURLConnection) r0;	 Catch:{ Throwable -> 0x00e1, all -> 0x00bc }
        r5 = r9.toLowerCase();	 Catch:{ Throwable -> 0x00e4, all -> 0x00d5 }
        r6 = "https";
        r5 = r5.startsWith(r6);	 Catch:{ Throwable -> 0x00e4, all -> 0x00d5 }
        if (r5 == 0) goto L_0x00ec;
    L_0x002a:
        r5 = com.talkingdata.sdk.bo.b(r10);	 Catch:{ Throwable -> 0x00e4, all -> 0x00d5 }
        if (r5 != 0) goto L_0x00ec;
    L_0x0030:
        r5 = c;	 Catch:{ Throwable -> 0x00e4, all -> 0x00d5 }
        r6 = java.lang.Thread.currentThread();	 Catch:{ Throwable -> 0x00e4, all -> 0x00d5 }
        r6 = r6.getId();	 Catch:{ Throwable -> 0x00e4, all -> 0x00d5 }
        r6 = java.lang.Long.valueOf(r6);	 Catch:{ Throwable -> 0x00e4, all -> 0x00d5 }
        r3 = r3.getHost();	 Catch:{ Throwable -> 0x00e4, all -> 0x00d5 }
        r5.put(r6, r3);	 Catch:{ Throwable -> 0x00e4, all -> 0x00d5 }
        r0 = a(r0, r10);	 Catch:{ Throwable -> 0x00e4, all -> 0x00d5 }
        r3 = r0;
    L_0x004a:
        r0 = "GET";
        r3.setRequestMethod(r0);	 Catch:{ Throwable -> 0x00e9, all -> 0x00d9 }
        r0 = r3.getResponseCode();	 Catch:{ Throwable -> 0x00e9, all -> 0x00d9 }
        r5 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        if (r0 != r5) goto L_0x00a9;
    L_0x0058:
        if (r11 == 0) goto L_0x007c;
    L_0x005a:
        r0 = new java.lang.String;	 Catch:{ Throwable -> 0x00e9, all -> 0x00d9 }
        r2 = r3.getInputStream();	 Catch:{ Throwable -> 0x00e9, all -> 0x00d9 }
        r2 = a(r2);	 Catch:{ Throwable -> 0x00e9, all -> 0x00d9 }
        r5 = "utf-8";
        r0.<init>(r2, r5);	 Catch:{ Throwable -> 0x00e9, all -> 0x00d9 }
        r4.append(r0);	 Catch:{ Throwable -> 0x00e9, all -> 0x00d9 }
    L_0x006d:
        if (r1 == 0) goto L_0x0072;
    L_0x006f:
        r1.close();	 Catch:{ Throwable -> 0x00cb }
    L_0x0072:
        if (r3 == 0) goto L_0x0077;
    L_0x0074:
        r3.disconnect();	 Catch:{ Throwable -> 0x00cd }
    L_0x0077:
        r0 = r4.toString();
        goto L_0x0008;
    L_0x007c:
        r2 = new java.io.BufferedReader;	 Catch:{ Throwable -> 0x00e9, all -> 0x00d9 }
        r0 = new java.io.InputStreamReader;	 Catch:{ Throwable -> 0x00e9, all -> 0x00d9 }
        r5 = r3.getInputStream();	 Catch:{ Throwable -> 0x00e9, all -> 0x00d9 }
        r0.<init>(r5);	 Catch:{ Throwable -> 0x00e9, all -> 0x00d9 }
        r2.<init>(r0);	 Catch:{ Throwable -> 0x00e9, all -> 0x00d9 }
    L_0x008a:
        r0 = r2.readLine();	 Catch:{ Throwable -> 0x0094, all -> 0x00db }
        if (r0 == 0) goto L_0x00a7;
    L_0x0090:
        r4.append(r0);	 Catch:{ Throwable -> 0x0094, all -> 0x00db }
        goto L_0x008a;
    L_0x0094:
        r0 = move-exception;
        r1 = r2;
        r2 = r3;
    L_0x0097:
        com.talkingdata.sdk.cs.postSDKError(r0);	 Catch:{ all -> 0x00de }
        if (r1 == 0) goto L_0x009f;
    L_0x009c:
        r1.close();	 Catch:{ Throwable -> 0x00cf }
    L_0x009f:
        if (r2 == 0) goto L_0x0077;
    L_0x00a1:
        r2.disconnect();	 Catch:{ Throwable -> 0x00a5 }
        goto L_0x0077;
    L_0x00a5:
        r0 = move-exception;
        goto L_0x0077;
    L_0x00a7:
        r1 = r2;
        goto L_0x006d;
    L_0x00a9:
        r0 = java.lang.String.valueOf(r0);	 Catch:{ Throwable -> 0x00e9, all -> 0x00d9 }
        if (r1 == 0) goto L_0x00b2;
    L_0x00af:
        r2.close();	 Catch:{ Throwable -> 0x00c9 }
    L_0x00b2:
        if (r3 == 0) goto L_0x0008;
    L_0x00b4:
        r3.disconnect();	 Catch:{ Throwable -> 0x00b9 }
        goto L_0x0008;
    L_0x00b9:
        r1 = move-exception;
        goto L_0x0008;
    L_0x00bc:
        r0 = move-exception;
        r3 = r1;
    L_0x00be:
        if (r1 == 0) goto L_0x00c3;
    L_0x00c0:
        r1.close();	 Catch:{ Throwable -> 0x00d1 }
    L_0x00c3:
        if (r3 == 0) goto L_0x00c8;
    L_0x00c5:
        r3.disconnect();	 Catch:{ Throwable -> 0x00d3 }
    L_0x00c8:
        throw r0;
    L_0x00c9:
        r1 = move-exception;
        goto L_0x00b2;
    L_0x00cb:
        r0 = move-exception;
        goto L_0x0072;
    L_0x00cd:
        r0 = move-exception;
        goto L_0x0077;
    L_0x00cf:
        r0 = move-exception;
        goto L_0x009f;
    L_0x00d1:
        r1 = move-exception;
        goto L_0x00c3;
    L_0x00d3:
        r1 = move-exception;
        goto L_0x00c8;
    L_0x00d5:
        r2 = move-exception;
        r3 = r0;
        r0 = r2;
        goto L_0x00be;
    L_0x00d9:
        r0 = move-exception;
        goto L_0x00be;
    L_0x00db:
        r0 = move-exception;
        r1 = r2;
        goto L_0x00be;
    L_0x00de:
        r0 = move-exception;
        r3 = r2;
        goto L_0x00be;
    L_0x00e1:
        r0 = move-exception;
        r2 = r1;
        goto L_0x0097;
    L_0x00e4:
        r2 = move-exception;
        r8 = r2;
        r2 = r0;
        r0 = r8;
        goto L_0x0097;
    L_0x00e9:
        r0 = move-exception;
        r2 = r3;
        goto L_0x0097;
    L_0x00ec:
        r3 = r0;
        goto L_0x004a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.talkingdata.sdk.as.b(java.lang.String, java.lang.String, boolean):java.lang.String");
    }

    private static byte[] a(InputStream inputStream) {
        byte[] bArr;
        Throwable th;
        try {
            GZIPInputStream gZIPInputStream = new GZIPInputStream(inputStream);
            bArr = new byte[1024];
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while (true) {
                int read = gZIPInputStream.read(bArr, 0, bArr.length);
                if (read == -1) {
                    break;
                }
                byteArrayOutputStream.write(bArr, 0, read);
            }
            bArr = byteArrayOutputStream.toByteArray();
            try {
                byteArrayOutputStream.flush();
                byteArrayOutputStream.close();
                gZIPInputStream.close();
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Throwable th3) {
            Throwable th4 = th3;
            bArr = null;
            th = th4;
        }
        return bArr;
        cs.postSDKError(th);
        return bArr;
    }

    public static String a(String str, File file) {
        return b(str, null, file);
    }

    public static String a(String str, String str2, File file) {
        return b(str, str2, file);
    }

    public static e a(String str, String str2, File file, String[] strArr) {
        return b(str, str2, file, strArr);
    }

    /* JADX WARNING: Removed duplicated region for block: B:47:0x00af A:{SYNTHETIC, Splitter: B:47:0x00af} */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0092 A:{SYNTHETIC, Splitter: B:32:0x0092} */
    private static com.talkingdata.sdk.as.e b(java.lang.String r9, java.lang.String r10, java.io.File r11, java.lang.String[] r12) {
        /*
        r2 = 0;
        r3 = 0;
        r1 = new com.talkingdata.sdk.as$e;
        r0 = 600; // 0x258 float:8.41E-43 double:2.964E-321;
        r1.<init>(r0);
        r4 = new java.net.URL;	 Catch:{ Throwable -> 0x008c }
        r4.<init>(r9);	 Catch:{ Throwable -> 0x008c }
        r0 = 0;
        r5 = 0;
        r6 = 0;
        r0 = a(r4, r0, r5, r6);	 Catch:{ Throwable -> 0x008c }
        r0 = (java.net.HttpURLConnection) r0;	 Catch:{ Throwable -> 0x008c }
        r2 = r9.toLowerCase();	 Catch:{ Throwable -> 0x00bd, all -> 0x00b9 }
        r5 = "https";
        r2 = r2.startsWith(r5);	 Catch:{ Throwable -> 0x00bd, all -> 0x00b9 }
        if (r2 == 0) goto L_0x00c2;
    L_0x0024:
        r2 = com.talkingdata.sdk.bo.b(r10);	 Catch:{ Throwable -> 0x00bd, all -> 0x00b9 }
        if (r2 != 0) goto L_0x00c2;
    L_0x002a:
        r2 = c;	 Catch:{ Throwable -> 0x00bd, all -> 0x00b9 }
        r5 = java.lang.Thread.currentThread();	 Catch:{ Throwable -> 0x00bd, all -> 0x00b9 }
        r6 = r5.getId();	 Catch:{ Throwable -> 0x00bd, all -> 0x00b9 }
        r5 = java.lang.Long.valueOf(r6);	 Catch:{ Throwable -> 0x00bd, all -> 0x00b9 }
        r4 = r4.getHost();	 Catch:{ Throwable -> 0x00bd, all -> 0x00b9 }
        r2.put(r5, r4);	 Catch:{ Throwable -> 0x00bd, all -> 0x00b9 }
        r0 = a(r0, r10);	 Catch:{ Throwable -> 0x00bd, all -> 0x00b9 }
        r2 = r0;
    L_0x0044:
        r0 = r2.getResponseCode();	 Catch:{ Throwable -> 0x008c }
        r1.statusCode = r0;	 Catch:{ Throwable -> 0x008c }
        r0 = r1.statusCode;	 Catch:{ Throwable -> 0x008c }
        r4 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        if (r0 != r4) goto L_0x00a4;
    L_0x0050:
        r4 = new org.json.JSONObject;	 Catch:{ Throwable -> 0x008c }
        r4.<init>();	 Catch:{ Throwable -> 0x008c }
        r5 = r12.length;	 Catch:{ Throwable -> 0x008c }
        r0 = r3;
    L_0x0057:
        if (r0 >= r5) goto L_0x0065;
    L_0x0059:
        r3 = r12[r0];	 Catch:{ Throwable -> 0x008c }
        r6 = r2.getHeaderField(r3);	 Catch:{ Throwable -> 0x008c }
        r4.put(r3, r6);	 Catch:{ Throwable -> 0x008c }
        r0 = r0 + 1;
        goto L_0x0057;
    L_0x0065:
        r0 = r4.toString();	 Catch:{ Throwable -> 0x008c }
        r1.responseMsg = r0;	 Catch:{ Throwable -> 0x008c }
        r3 = r2.getInputStream();	 Catch:{ Throwable -> 0x008c }
        r4 = new java.io.FileOutputStream;	 Catch:{ Throwable -> 0x008c }
        r4.<init>(r11);	 Catch:{ Throwable -> 0x008c }
        r0 = 4096; // 0x1000 float:5.74E-42 double:2.0237E-320;
        r0 = new byte[r0];	 Catch:{ all -> 0x0084 }
    L_0x0078:
        r5 = r3.read(r0);	 Catch:{ all -> 0x0084 }
        r6 = -1;
        if (r5 == r6) goto L_0x0097;
    L_0x007f:
        r6 = 0;
        r4.write(r0, r6, r5);	 Catch:{ all -> 0x0084 }
        goto L_0x0078;
    L_0x0084:
        r0 = move-exception;
        r4.close();	 Catch:{ Throwable -> 0x008c }
        r3.close();	 Catch:{ Throwable -> 0x008c }
        throw r0;	 Catch:{ Throwable -> 0x008c }
    L_0x008c:
        r0 = move-exception;
    L_0x008d:
        com.talkingdata.sdk.cs.postSDKError(r0);	 Catch:{ all -> 0x00ac }
        if (r2 == 0) goto L_0x0095;
    L_0x0092:
        r2.disconnect();	 Catch:{ Throwable -> 0x00b5 }
    L_0x0095:
        r0 = r1;
    L_0x0096:
        return r0;
    L_0x0097:
        r4.close();	 Catch:{ Throwable -> 0x008c }
        r3.close();	 Catch:{ Throwable -> 0x008c }
        if (r2 == 0) goto L_0x00a2;
    L_0x009f:
        r2.disconnect();	 Catch:{ Throwable -> 0x00b3 }
    L_0x00a2:
        r0 = r1;
        goto L_0x0096;
    L_0x00a4:
        if (r2 == 0) goto L_0x0095;
    L_0x00a6:
        r2.disconnect();	 Catch:{ Throwable -> 0x00aa }
        goto L_0x0095;
    L_0x00aa:
        r0 = move-exception;
        goto L_0x0095;
    L_0x00ac:
        r0 = move-exception;
    L_0x00ad:
        if (r2 == 0) goto L_0x00b2;
    L_0x00af:
        r2.disconnect();	 Catch:{ Throwable -> 0x00b7 }
    L_0x00b2:
        throw r0;
    L_0x00b3:
        r0 = move-exception;
        goto L_0x00a2;
    L_0x00b5:
        r0 = move-exception;
        goto L_0x0095;
    L_0x00b7:
        r1 = move-exception;
        goto L_0x00b2;
    L_0x00b9:
        r1 = move-exception;
        r2 = r0;
        r0 = r1;
        goto L_0x00ad;
    L_0x00bd:
        r2 = move-exception;
        r8 = r2;
        r2 = r0;
        r0 = r8;
        goto L_0x008d;
    L_0x00c2:
        r2 = r0;
        goto L_0x0044;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.talkingdata.sdk.as.b(java.lang.String, java.lang.String, java.io.File, java.lang.String[]):com.talkingdata.sdk.as$e");
    }

    /* JADX WARNING: Removed duplicated region for block: B:72:0x00ba A:{Splitter: B:10:0x003c, ExcHandler: all (th java.lang.Throwable)} */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x00ba A:{Splitter: B:10:0x003c, ExcHandler: all (th java.lang.Throwable)} */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x00ba A:{Splitter: B:10:0x003c, ExcHandler: all (th java.lang.Throwable)} */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x00a6 A:{SYNTHETIC, Splitter: B:61:0x00a6} */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x006b A:{SYNTHETIC, Splitter: B:27:0x006b} */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0070 A:{SYNTHETIC, Splitter: B:30:0x0070} */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x00ba A:{Splitter: B:10:0x003c, ExcHandler: all (th java.lang.Throwable)} */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x007b A:{SYNTHETIC, Splitter: B:39:0x007b} */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x00a6 A:{SYNTHETIC, Splitter: B:61:0x00a6} */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x00a6 A:{SYNTHETIC, Splitter: B:61:0x00a6} */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x00ba A:{Splitter: B:10:0x003c, ExcHandler: all (th java.lang.Throwable)} */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:34:0x0074, code:
            r0 = th;
     */
    /* JADX WARNING: Missing block: B:35:0x0075, code:
            r2 = r4;
     */
    /* JADX WARNING: Missing block: B:62:?, code:
            r4.disconnect();
     */
    /* JADX WARNING: Missing block: B:72:0x00ba, code:
            r0 = th;
     */
    private static java.lang.String b(java.lang.String r9, java.lang.String r10, java.io.File r11) {
        /*
        r1 = 0;
        r2 = new java.net.URL;	 Catch:{ Throwable -> 0x00bf, all -> 0x00a2 }
        r2.<init>(r9);	 Catch:{ Throwable -> 0x00bf, all -> 0x00a2 }
        r0 = 0;
        r3 = 0;
        r4 = 0;
        r0 = a(r2, r0, r3, r4);	 Catch:{ Throwable -> 0x00bf, all -> 0x00a2 }
        r0 = (java.net.HttpURLConnection) r0;	 Catch:{ Throwable -> 0x00bf, all -> 0x00a2 }
        r3 = r9.toLowerCase();	 Catch:{ Throwable -> 0x00c2, all -> 0x00b6 }
        r4 = "https";
        r3 = r3.startsWith(r4);	 Catch:{ Throwable -> 0x00c2, all -> 0x00b6 }
        if (r3 == 0) goto L_0x00ce;
    L_0x001c:
        r3 = com.talkingdata.sdk.bo.b(r10);	 Catch:{ Throwable -> 0x00c2, all -> 0x00b6 }
        if (r3 != 0) goto L_0x00ce;
    L_0x0022:
        r3 = c;	 Catch:{ Throwable -> 0x00c2, all -> 0x00b6 }
        r4 = java.lang.Thread.currentThread();	 Catch:{ Throwable -> 0x00c2, all -> 0x00b6 }
        r4 = r4.getId();	 Catch:{ Throwable -> 0x00c2, all -> 0x00b6 }
        r4 = java.lang.Long.valueOf(r4);	 Catch:{ Throwable -> 0x00c2, all -> 0x00b6 }
        r2 = r2.getHost();	 Catch:{ Throwable -> 0x00c2, all -> 0x00b6 }
        r3.put(r4, r2);	 Catch:{ Throwable -> 0x00c2, all -> 0x00b6 }
        r0 = a(r0, r10);	 Catch:{ Throwable -> 0x00c2, all -> 0x00b6 }
        r4 = r0;
    L_0x003c:
        r0 = r4.getResponseCode();	 Catch:{ Throwable -> 0x0074, all -> 0x00ba }
        r2 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        if (r0 != r2) goto L_0x009a;
    L_0x0044:
        r0 = "MD5";
        r0 = java.security.MessageDigest.getInstance(r0);	 Catch:{ Throwable -> 0x0074, all -> 0x00ba }
        r3 = r4.getInputStream();	 Catch:{ all -> 0x00c7 }
        r2 = new java.io.FileOutputStream;	 Catch:{ all -> 0x00cb }
        r2.<init>(r11);	 Catch:{ all -> 0x00cb }
        r5 = 4096; // 0x1000 float:5.74E-42 double:2.0237E-320;
        r5 = new byte[r5];	 Catch:{ all -> 0x0068 }
    L_0x0058:
        r6 = r3.read(r5);	 Catch:{ all -> 0x0068 }
        r7 = -1;
        if (r6 == r7) goto L_0x0080;
    L_0x005f:
        r7 = 0;
        r2.write(r5, r7, r6);	 Catch:{ all -> 0x0068 }
        r7 = 0;
        r0.update(r5, r7, r6);	 Catch:{ all -> 0x0068 }
        goto L_0x0058;
    L_0x0068:
        r0 = move-exception;
    L_0x0069:
        if (r2 == 0) goto L_0x006e;
    L_0x006b:
        r2.close();	 Catch:{ Throwable -> 0x00ae, all -> 0x00ba }
    L_0x006e:
        if (r3 == 0) goto L_0x0073;
    L_0x0070:
        r3.close();	 Catch:{ Throwable -> 0x00b0, all -> 0x00ba }
    L_0x0073:
        throw r0;	 Catch:{ Throwable -> 0x0074, all -> 0x00ba }
    L_0x0074:
        r0 = move-exception;
        r2 = r4;
    L_0x0076:
        com.talkingdata.sdk.cs.postSDKError(r0);	 Catch:{ all -> 0x00bc }
        if (r2 == 0) goto L_0x007e;
    L_0x007b:
        r2.disconnect();	 Catch:{ Throwable -> 0x00b2 }
    L_0x007e:
        r0 = r1;
    L_0x007f:
        return r0;
    L_0x0080:
        if (r2 == 0) goto L_0x0085;
    L_0x0082:
        r2.close();	 Catch:{ Throwable -> 0x00aa, all -> 0x00ba }
    L_0x0085:
        if (r3 == 0) goto L_0x008a;
    L_0x0087:
        r3.close();	 Catch:{ Throwable -> 0x00ac, all -> 0x00ba }
    L_0x008a:
        r0 = r0.digest();	 Catch:{ Throwable -> 0x0074, all -> 0x00ba }
        r0 = com.talkingdata.sdk.bo.a(r0);	 Catch:{ Throwable -> 0x0074, all -> 0x00ba }
        if (r4 == 0) goto L_0x007f;
    L_0x0094:
        r4.disconnect();	 Catch:{ Throwable -> 0x0098 }
        goto L_0x007f;
    L_0x0098:
        r1 = move-exception;
        goto L_0x007f;
    L_0x009a:
        if (r4 == 0) goto L_0x007e;
    L_0x009c:
        r4.disconnect();	 Catch:{ Throwable -> 0x00a0 }
        goto L_0x007e;
    L_0x00a0:
        r0 = move-exception;
        goto L_0x007e;
    L_0x00a2:
        r0 = move-exception;
        r4 = r1;
    L_0x00a4:
        if (r4 == 0) goto L_0x00a9;
    L_0x00a6:
        r4.disconnect();	 Catch:{ Throwable -> 0x00b4 }
    L_0x00a9:
        throw r0;
    L_0x00aa:
        r2 = move-exception;
        goto L_0x0085;
    L_0x00ac:
        r2 = move-exception;
        goto L_0x008a;
    L_0x00ae:
        r2 = move-exception;
        goto L_0x006e;
    L_0x00b0:
        r2 = move-exception;
        goto L_0x0073;
    L_0x00b2:
        r0 = move-exception;
        goto L_0x007e;
    L_0x00b4:
        r1 = move-exception;
        goto L_0x00a9;
    L_0x00b6:
        r1 = move-exception;
        r4 = r0;
        r0 = r1;
        goto L_0x00a4;
    L_0x00ba:
        r0 = move-exception;
        goto L_0x00a4;
    L_0x00bc:
        r0 = move-exception;
        r4 = r2;
        goto L_0x00a4;
    L_0x00bf:
        r0 = move-exception;
        r2 = r1;
        goto L_0x0076;
    L_0x00c2:
        r2 = move-exception;
        r8 = r2;
        r2 = r0;
        r0 = r8;
        goto L_0x0076;
    L_0x00c7:
        r0 = move-exception;
        r2 = r1;
        r3 = r1;
        goto L_0x0069;
    L_0x00cb:
        r0 = move-exception;
        r2 = r1;
        goto L_0x0069;
    L_0x00ce:
        r4 = r0;
        goto L_0x003c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.talkingdata.sdk.as.b(java.lang.String, java.lang.String, java.io.File):java.lang.String");
    }

    private static URL a(String str, String str2) {
        URL url = new URL(str);
        if (bd.a()) {
            return url;
        }
        return new URL(url.getProtocol(), str2, url.getPort(), url.getFile());
    }

    private static void a(String str) {
        String a = a(str, 1);
        if (a != null && !a.equalsIgnoreCase(a(str, 3)) && g != null) {
            PreferenceManager.getDefaultSharedPreferences(g).edit().putString(bo.d(str), a(str, 1)).apply();
            a(str, a(str, 1), 3);
        }
    }

    private static e a(String str, String str2, String str3, String str4, byte[] bArr, String str5, a aVar, String str6, String str7) {
        Throwable th;
        e eVar = new e(600);
        e a;
        try {
            if (a(str, 2) != null) {
                a = a(a(str, 2), str3, str4, bArr, str, str5, aVar, str6, str7);
                try {
                    if (a.statusCode != 600) {
                        return a;
                    }
                    a(str, null, 2);
                    return a;
                } catch (Throwable th2) {
                    th = th2;
                    cs.postSDKError(th);
                    return a;
                }
            }
            if (a(str, 1) != null) {
                eVar = a(a(str, 1), str3, str4, bArr, str, str5, aVar, str6, str7);
                try {
                    if (eVar.statusCode != 600) {
                        a(str, a(str, 1), 2);
                        a(str);
                    }
                } catch (Throwable th3) {
                    th = th3;
                    a = eVar;
                    cs.postSDKError(th);
                    return a;
                }
            }
            try {
                if (eVar.statusCode == 600 && a(str, 3) != null) {
                    eVar = a(a(str, 3), str3, str4, bArr, str, str5, aVar, str6, str7);
                    if (eVar.statusCode != 600) {
                        a(str, a(str, 3), 2);
                    }
                }
                if (eVar.statusCode != 600 || a(str, 4) == null) {
                    return eVar;
                }
                a = a(a(str, 4), str3, str4, bArr, str, str5, aVar, str6, str7);
                if (a.statusCode == 600) {
                    return a;
                }
                a(str, a(str, 4), 2);
                return a;
            } catch (Throwable th32) {
                th = th32;
                a = eVar;
                cs.postSDKError(th);
                return a;
            }
        } catch (Throwable th322) {
            th = th322;
            a = eVar;
            cs.postSDKError(th);
            return a;
        }
    }

    private static e a(String str, String str2, String str3, byte[] bArr, String str4, String str5, a aVar, String str6, String str7) {
        try {
            URLConnection a;
            if (str2.startsWith("https://")) {
                c.put(Long.valueOf(Thread.currentThread().getId()), str4);
            }
            if (str2.toLowerCase().startsWith("https") && str3.trim().isEmpty()) {
                a = a(new URL(str2), str4, true, aVar, str5, str6, str7);
            } else {
                a = a(a(str2, str), str4, true, aVar, str5, str6, str7);
            }
            if (a == null) {
                return new e(600, "");
            }
            if (str2.toLowerCase().startsWith("https") && !str3.trim().isEmpty()) {
                a = a(a, str3);
            }
            return a(bArr, a, str5);
        } catch (Throwable th) {
            cs.postSDKError(th);
            return new e(600, "");
        }
    }

    private static URLConnection a(URL url, String str, boolean z, String str2) {
        return b(url, str, z, null, null, "", str2);
    }

    private static URLConnection a(URL url, String str, boolean z, a aVar, String str2, String str3, String str4) {
        return b(url, str, z, aVar, str2, str3, str4);
    }

    private static URLConnection b(URL url, String str, boolean z, a aVar, String str2, String str3, String str4) {
        try {
            URLConnection openConnection = url.openConnection();
            openConnection.setRequestProperty(HttpRequest.HEADER_ACCEPT_ENCODING, "");
            openConnection.setRequestProperty("User-Agent", "");
            if (str != null) {
                openConnection.setRequestProperty("Host", str);
                openConnection.setRequestProperty("Content-Type", "");
            }
            if (bo.a(14) && bo.b(19)) {
                openConnection.setRequestProperty("Connection", "close");
            }
            openConnection.setDoInput(true);
            if (z) {
                openConnection.setDoOutput(true);
            }
            openConnection.setConnectTimeout(a);
            openConnection.setReadTimeout(b);
            return openConnection;
        } catch (Throwable th) {
            cs.postSDKError(th);
            return null;
        }
    }

    private static HttpsURLConnection a(URLConnection uRLConnection, String str) {
        try {
            SSLContext instance;
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) uRLConnection;
            if (bo.a(16)) {
                instance = SSLContext.getInstance("TLSv1.2");
            } else {
                instance = SSLContext.getInstance("TLSv1");
            }
            instance.init(null, new TrustManager[]{new d(b(str))}, null);
            httpsURLConnection.setHostnameVerifier(new at());
            httpsURLConnection.setSSLSocketFactory(instance.getSocketFactory());
            return httpsURLConnection;
        } catch (Throwable th) {
            cs.postSDKError(th);
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:48:0x0097 A:{SYNTHETIC, Splitter: B:48:0x0097} */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x009c A:{SYNTHETIC, Splitter: B:51:0x009c} */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00a1 A:{SYNTHETIC, Splitter: B:54:0x00a1} */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x00bf A:{Splitter: B:9:0x002c, ExcHandler: all (th java.lang.Throwable)} */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x007c A:{SYNTHETIC, Splitter: B:37:0x007c} */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x0081 A:{SYNTHETIC, Splitter: B:40:0x0081} */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x0081 A:{SYNTHETIC, Splitter: B:40:0x0081} */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:49:?, code:
            r3.close();
     */
    /* JADX WARNING: Missing block: B:52:?, code:
            r7.close();
     */
    /* JADX WARNING: Missing block: B:55:?, code:
            r1.disconnect();
     */
    /* JADX WARNING: Missing block: B:58:0x00ae, code:
            r2 = move-exception;
     */
    /* JADX WARNING: Missing block: B:59:0x00af, code:
            com.talkingdata.sdk.cs.postSDKError(r2);
     */
    /* JADX WARNING: Missing block: B:66:0x00bf, code:
            r0 = th;
     */
    /* JADX WARNING: Missing block: B:68:0x00c2, code:
            r0 = 600;
            r2 = r3;
     */
    private static com.talkingdata.sdk.as.e a(byte[] r9, java.net.URLConnection r10, java.lang.String r11) {
        /*
        r7 = 0;
        r2 = 600; // 0x258 float:8.41E-43 double:2.964E-321;
        r8 = 60000; // 0xea60 float:8.4078E-41 double:2.9644E-319;
        if (r9 == 0) goto L_0x000d;
    L_0x0008:
        r0 = r9.length;
        if (r0 == 0) goto L_0x000d;
    L_0x000b:
        if (r10 != 0) goto L_0x0016;
    L_0x000d:
        r0 = new com.talkingdata.sdk.as$e;
        r1 = "";
        r0.<init>(r2, r1);
    L_0x0015:
        return r0;
    L_0x0016:
        r4 = android.os.SystemClock.elapsedRealtime();
        r1 = r10;
        r1 = (java.net.HttpURLConnection) r1;
        r6 = new java.lang.StringBuffer;
        r6.<init>();
        r0 = "POST";
        r1.setRequestMethod(r0);	 Catch:{ Throwable -> 0x0072, all -> 0x0093 }
        r3 = r1.getOutputStream();	 Catch:{ Throwable -> 0x0072, all -> 0x0093 }
        r3.write(r9);	 Catch:{ Throwable -> 0x00c1, all -> 0x00bf }
        r3.close();	 Catch:{ Throwable -> 0x00c1, all -> 0x00bf }
        r0 = r1.getResponseCode();	 Catch:{ Throwable -> 0x00c1, all -> 0x00bf }
        r2 = 400; // 0x190 float:5.6E-43 double:1.976E-321;
        if (r0 <= r2) goto L_0x0068;
    L_0x003a:
        r7 = r1.getErrorStream();	 Catch:{ Throwable -> 0x00c5, all -> 0x00bf }
    L_0x003e:
        r2 = a(r11, r7);	 Catch:{ Throwable -> 0x00c5, all -> 0x00bf }
        r6.append(r2);	 Catch:{ Throwable -> 0x00c5, all -> 0x00bf }
        if (r3 == 0) goto L_0x004a;
    L_0x0047:
        r3.close();	 Catch:{ Throwable -> 0x00b3 }
    L_0x004a:
        if (r7 == 0) goto L_0x004f;
    L_0x004c:
        r7.close();	 Catch:{ Throwable -> 0x00b5 }
    L_0x004f:
        if (r1 == 0) goto L_0x0054;
    L_0x0051:
        r1.disconnect();	 Catch:{ Throwable -> 0x006d }
    L_0x0054:
        a = r8;
        b = r8;
        r2 = r9.length;
        r2 = (long) r2;
        a(r1, r2, r4, r6);
    L_0x005d:
        r1 = new com.talkingdata.sdk.as$e;
        r2 = r6.toString();
        r1.<init>(r0, r2);
        r0 = r1;
        goto L_0x0015;
    L_0x0068:
        r7 = r1.getInputStream();	 Catch:{ Throwable -> 0x00c5, all -> 0x00bf }
        goto L_0x003e;
    L_0x006d:
        r2 = move-exception;
        com.talkingdata.sdk.cs.postSDKError(r2);
        goto L_0x0054;
    L_0x0072:
        r0 = move-exception;
        r0 = r2;
        r2 = r7;
    L_0x0075:
        if (r2 == 0) goto L_0x007a;
    L_0x0077:
        r2.close();	 Catch:{ Throwable -> 0x00b7 }
    L_0x007a:
        if (r7 == 0) goto L_0x007f;
    L_0x007c:
        r7.close();	 Catch:{ Throwable -> 0x00b9 }
    L_0x007f:
        if (r1 == 0) goto L_0x0084;
    L_0x0081:
        r1.disconnect();	 Catch:{ Throwable -> 0x008e }
    L_0x0084:
        a = r8;
        b = r8;
        r2 = r9.length;
        r2 = (long) r2;
        a(r1, r2, r4, r6);
        goto L_0x005d;
    L_0x008e:
        r2 = move-exception;
        com.talkingdata.sdk.cs.postSDKError(r2);
        goto L_0x0084;
    L_0x0093:
        r0 = move-exception;
        r3 = r7;
    L_0x0095:
        if (r3 == 0) goto L_0x009a;
    L_0x0097:
        r3.close();	 Catch:{ Throwable -> 0x00bb }
    L_0x009a:
        if (r7 == 0) goto L_0x009f;
    L_0x009c:
        r7.close();	 Catch:{ Throwable -> 0x00bd }
    L_0x009f:
        if (r1 == 0) goto L_0x00a4;
    L_0x00a1:
        r1.disconnect();	 Catch:{ Throwable -> 0x00ae }
    L_0x00a4:
        a = r8;
        b = r8;
        r2 = r9.length;
        r2 = (long) r2;
        a(r1, r2, r4, r6);
        throw r0;
    L_0x00ae:
        r2 = move-exception;
        com.talkingdata.sdk.cs.postSDKError(r2);
        goto L_0x00a4;
    L_0x00b3:
        r2 = move-exception;
        goto L_0x004a;
    L_0x00b5:
        r2 = move-exception;
        goto L_0x004f;
    L_0x00b7:
        r2 = move-exception;
        goto L_0x007a;
    L_0x00b9:
        r2 = move-exception;
        goto L_0x007f;
    L_0x00bb:
        r2 = move-exception;
        goto L_0x009a;
    L_0x00bd:
        r2 = move-exception;
        goto L_0x009f;
    L_0x00bf:
        r0 = move-exception;
        goto L_0x0095;
    L_0x00c1:
        r0 = move-exception;
        r0 = r2;
        r2 = r3;
        goto L_0x0075;
    L_0x00c5:
        r2 = move-exception;
        r2 = r3;
        goto L_0x0075;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.talkingdata.sdk.as.a(byte[], java.net.URLConnection, java.lang.String):com.talkingdata.sdk.as$e");
    }

    /* JADX WARNING: Removed duplicated region for block: B:33:0x0061 A:{SYNTHETIC, Splitter: B:33:0x0061} */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0037 A:{SYNTHETIC, Splitter: B:18:0x0037} */
    private static java.lang.String a(java.lang.String r4, java.io.InputStream r5) {
        /*
        r3 = new java.lang.StringBuffer;
        r3.<init>();
        r1 = 0;
        r0 = com.talkingdata.sdk.bo.b(r4);	 Catch:{ Throwable -> 0x006c }
        if (r0 != 0) goto L_0x0056;
    L_0x000c:
        r0 = -1;
        r2 = r4.hashCode();	 Catch:{ Throwable -> 0x006c }
        switch(r2) {
            case 3189082: goto L_0x003f;
            default: goto L_0x0014;
        };	 Catch:{ Throwable -> 0x006c }
    L_0x0014:
        switch(r0) {
            case 0: goto L_0x004a;
            default: goto L_0x0017;
        };	 Catch:{ Throwable -> 0x006c }
    L_0x0017:
        r2 = new java.io.BufferedReader;	 Catch:{ Throwable -> 0x006c }
        r0 = new java.io.InputStreamReader;	 Catch:{ Throwable -> 0x006c }
        r0.<init>(r5);	 Catch:{ Throwable -> 0x006c }
        r2.<init>(r0);	 Catch:{ Throwable -> 0x006c }
    L_0x0021:
        r0 = r2.readLine();	 Catch:{ Throwable -> 0x0030, all -> 0x0069 }
        if (r0 == 0) goto L_0x006e;
    L_0x0027:
        r3.append(r0);	 Catch:{ Throwable -> 0x0030, all -> 0x0069 }
        r0 = 10;
        r3.append(r0);	 Catch:{ Throwable -> 0x0030, all -> 0x0069 }
        goto L_0x0021;
    L_0x0030:
        r0 = move-exception;
        r1 = r2;
    L_0x0032:
        com.talkingdata.sdk.cs.postSDKError(r0);	 Catch:{ all -> 0x005e }
        if (r1 == 0) goto L_0x003a;
    L_0x0037:
        r1.close();	 Catch:{ Throwable -> 0x0065 }
    L_0x003a:
        r0 = r3.toString();
        return r0;
    L_0x003f:
        r2 = "gzip";
        r2 = r4.equals(r2);	 Catch:{ Throwable -> 0x006c }
        if (r2 == 0) goto L_0x0014;
    L_0x0048:
        r0 = 0;
        goto L_0x0014;
    L_0x004a:
        r0 = new java.lang.String;	 Catch:{ Throwable -> 0x006c }
        r2 = a(r5);	 Catch:{ Throwable -> 0x006c }
        r0.<init>(r2);	 Catch:{ Throwable -> 0x006c }
        r3.append(r0);	 Catch:{ Throwable -> 0x006c }
    L_0x0056:
        if (r1 == 0) goto L_0x003a;
    L_0x0058:
        r1.close();	 Catch:{ Throwable -> 0x005c }
        goto L_0x003a;
    L_0x005c:
        r0 = move-exception;
        goto L_0x003a;
    L_0x005e:
        r0 = move-exception;
    L_0x005f:
        if (r1 == 0) goto L_0x0064;
    L_0x0061:
        r1.close();	 Catch:{ Throwable -> 0x0067 }
    L_0x0064:
        throw r0;
    L_0x0065:
        r0 = move-exception;
        goto L_0x003a;
    L_0x0067:
        r1 = move-exception;
        goto L_0x0064;
    L_0x0069:
        r0 = move-exception;
        r1 = r2;
        goto L_0x005f;
    L_0x006c:
        r0 = move-exception;
        goto L_0x0032;
    L_0x006e:
        r1 = r2;
        goto L_0x0056;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.talkingdata.sdk.as.a(java.lang.String, java.io.InputStream):java.lang.String");
    }

    private static void a(HttpURLConnection httpURLConnection, long j, long j2, StringBuffer stringBuffer) {
        if (httpURLConnection != null) {
            try {
                boolean z;
                Map treeMap = new TreeMap();
                URL url = httpURLConnection.getURL();
                treeMap.put(QQConstant.SHARE_TO_QQ_TARGET_URL, url.toString());
                treeMap.put("targetIP", InetAddress.getByName(url.getHost()).getHostAddress());
                if (httpURLConnection.getResponseCode() == 200) {
                    treeMap.put("reqSize", Long.valueOf(j));
                    treeMap.put("respTime", Long.valueOf(SystemClock.elapsedRealtime() - j2));
                    z = true;
                } else {
                    treeMap.put("errorMsg", stringBuffer.toString());
                    z = false;
                }
                cs.a(z, treeMap);
            } catch (Throwable th) {
                cs.postSDKError(th);
            }
        }
    }

    private static X509Certificate b(String str) {
        if (str == null || str.trim().isEmpty()) {
            return null;
        }
        InputStream byteArrayInputStream = new ByteArrayInputStream(str.getBytes());
        try {
            X509Certificate x509Certificate = (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(byteArrayInputStream);
            if (byteArrayInputStream == null) {
                return x509Certificate;
            }
            try {
                byteArrayInputStream.close();
                return x509Certificate;
            } catch (Throwable th) {
                return x509Certificate;
            }
        } catch (Exception e) {
            if (byteArrayInputStream != null) {
                byteArrayInputStream.close();
            }
            return null;
        } catch (Throwable th2) {
        }
    }

    private static synchronized void a(String str, String str2, int i) {
        synchronized (as.class) {
            if (!bo.b(str) && h.containsKey(str)) {
                if (h != null) {
                    a aVar = (a) h.get(str);
                    switch (i) {
                        case 1:
                            aVar.resolveIp = str2;
                            break;
                        case 2:
                            aVar.successIp = str2;
                            break;
                        case 3:
                            aVar.savedIp = str2;
                            break;
                        case 4:
                            aVar.staticIp = str2;
                            break;
                    }
                }
            }
        }
    }

    private static synchronized void b(String str, String str2) {
        synchronized (as.class) {
            if (!(bo.b(str) || h.containsKey(str))) {
                if (h != null) {
                    try {
                        a aVar = new a();
                        aVar.host = str;
                        aVar.staticIp = str2;
                        aVar.savedIp = PreferenceManager.getDefaultSharedPreferences(g).getString(bo.d(str), null);
                        aVar.resolveIp = InetAddress.getByName(aVar.host).getHostAddress();
                        h.put(aVar.host, aVar);
                    } catch (Throwable th) {
                    }
                }
            }
        }
    }

    private static synchronized String a(String str, int i) {
        String str2;
        synchronized (as.class) {
            if (!bo.b(str) && h.containsKey(str)) {
                if (h != null) {
                    a aVar = (a) h.get(str);
                    if (aVar != null) {
                        switch (i) {
                            case 1:
                                str2 = aVar.resolveIp;
                                break;
                            case 2:
                                str2 = aVar.successIp;
                                break;
                            case 3:
                                str2 = aVar.savedIp;
                                break;
                            case 4:
                                str2 = aVar.staticIp;
                                break;
                            default:
                                str2 = null;
                                break;
                        }
                    }
                    str2 = null;
                } else {
                    str2 = null;
                }
            } else {
                str2 = null;
            }
        }
        return str2;
    }

    private static void a(HttpsURLConnection httpsURLConnection) {
        if (httpsURLConnection != null) {
        }
    }
}
