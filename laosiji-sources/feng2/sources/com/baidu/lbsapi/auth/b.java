package com.baidu.lbsapi.auth;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;
import android.util.Base64;
import java.io.ByteArrayInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Locale;

class b {

    static class a {
        public static String a(byte[] bArr) {
            char[] cArr = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
            StringBuilder stringBuilder = new StringBuilder(bArr.length * 2);
            for (int i = 0; i < bArr.length; i++) {
                stringBuilder.append(cArr[(bArr[i] & 240) >> 4]);
                stringBuilder.append(cArr[bArr[i] & 15]);
            }
            return stringBuilder.toString();
        }
    }

    static String a() {
        return Locale.getDefault().getLanguage();
    }

    protected static String a(Context context) {
        String packageName = context.getPackageName();
        return a(context, packageName) + ";" + packageName;
    }

    private static String a(Context context, String str) {
        String a;
        String str2 = "";
        try {
            a = a((X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(context.getPackageManager().getPackageInfo(str, 64).signatures[0].toByteArray())));
        } catch (NameNotFoundException e) {
            a = str2;
        } catch (CertificateException e2) {
            a = str2;
        }
        StringBuffer stringBuffer = new StringBuffer();
        int i = 0;
        while (i < a.length()) {
            stringBuffer.append(a.charAt(i));
            if (i > 0 && i % 2 == 1 && i < a.length() - 1) {
                stringBuffer.append(":");
            }
            i++;
        }
        return stringBuffer.toString();
    }

    static String a(X509Certificate x509Certificate) {
        try {
            return a.a(a(x509Certificate.getEncoded()));
        } catch (CertificateEncodingException e) {
            return null;
        }
    }

    static byte[] a(byte[] bArr) {
        try {
            return MessageDigest.getInstance("SHA1").digest(bArr);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    protected static String[] b(Context context) {
        String packageName = context.getPackageName();
        String[] b = b(context, packageName);
        if (b == null || b.length <= 0) {
            return null;
        }
        String[] strArr = new String[b.length];
        for (int i = 0; i < strArr.length; i++) {
            strArr[i] = b[i] + ";" + packageName;
            if (a.a) {
                a.a("mcode" + strArr[i]);
            }
        }
        return strArr;
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x004a  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x004a  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x004a  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x004a  */
    private static java.lang.String[] b(android.content.Context r8, java.lang.String r9) {
        /*
        r2 = 0;
        r3 = 0;
        r0 = r8.getPackageManager();	 Catch:{ NameNotFoundException -> 0x007b, CertificateException -> 0x007f }
        r1 = 64;
        r0 = r0.getPackageInfo(r9, r1);	 Catch:{ NameNotFoundException -> 0x007b, CertificateException -> 0x007f }
        r5 = r0.signatures;	 Catch:{ NameNotFoundException -> 0x007b, CertificateException -> 0x007f }
        if (r5 == 0) goto L_0x0091;
    L_0x0010:
        r0 = r5.length;	 Catch:{ NameNotFoundException -> 0x007b, CertificateException -> 0x007f }
        if (r0 <= 0) goto L_0x0091;
    L_0x0013:
        r0 = r5.length;	 Catch:{ NameNotFoundException -> 0x007b, CertificateException -> 0x007f }
        r1 = new java.lang.String[r0];	 Catch:{ NameNotFoundException -> 0x007b, CertificateException -> 0x007f }
        r4 = r3;
    L_0x0017:
        r0 = r5.length;	 Catch:{ NameNotFoundException -> 0x008f, CertificateException -> 0x008d }
        if (r4 >= r0) goto L_0x003c;
    L_0x001a:
        r0 = "X.509";
        r0 = java.security.cert.CertificateFactory.getInstance(r0);	 Catch:{ NameNotFoundException -> 0x008f, CertificateException -> 0x008d }
        r6 = new java.io.ByteArrayInputStream;	 Catch:{ NameNotFoundException -> 0x008f, CertificateException -> 0x008d }
        r7 = r5[r4];	 Catch:{ NameNotFoundException -> 0x008f, CertificateException -> 0x008d }
        r7 = r7.toByteArray();	 Catch:{ NameNotFoundException -> 0x008f, CertificateException -> 0x008d }
        r6.<init>(r7);	 Catch:{ NameNotFoundException -> 0x008f, CertificateException -> 0x008d }
        r0 = r0.generateCertificate(r6);	 Catch:{ NameNotFoundException -> 0x008f, CertificateException -> 0x008d }
        r0 = (java.security.cert.X509Certificate) r0;	 Catch:{ NameNotFoundException -> 0x008f, CertificateException -> 0x008d }
        r0 = a(r0);	 Catch:{ NameNotFoundException -> 0x008f, CertificateException -> 0x008d }
        r1[r4] = r0;	 Catch:{ NameNotFoundException -> 0x008f, CertificateException -> 0x008d }
        r0 = r4 + 1;
        r4 = r0;
        goto L_0x0017;
    L_0x003c:
        r0 = r1;
    L_0x003d:
        r4 = r0;
    L_0x003e:
        if (r4 == 0) goto L_0x008c;
    L_0x0040:
        r0 = r4.length;
        if (r0 <= 0) goto L_0x008c;
    L_0x0043:
        r0 = r4.length;
        r2 = new java.lang.String[r0];
        r0 = r3;
    L_0x0047:
        r1 = r4.length;
        if (r0 >= r1) goto L_0x008c;
    L_0x004a:
        r5 = new java.lang.StringBuffer;
        r5.<init>();
        r1 = r3;
    L_0x0050:
        r6 = r4[r0];
        r6 = r6.length();
        if (r1 >= r6) goto L_0x0083;
    L_0x0058:
        r6 = r4[r0];
        r6 = r6.charAt(r1);
        r5.append(r6);
        if (r1 <= 0) goto L_0x0078;
    L_0x0063:
        r6 = r1 % 2;
        r7 = 1;
        if (r6 != r7) goto L_0x0078;
    L_0x0068:
        r6 = r4[r0];
        r6 = r6.length();
        r6 = r6 + -1;
        if (r1 >= r6) goto L_0x0078;
    L_0x0072:
        r6 = ":";
        r5.append(r6);
    L_0x0078:
        r1 = r1 + 1;
        goto L_0x0050;
    L_0x007b:
        r0 = move-exception;
        r1 = r2;
    L_0x007d:
        r4 = r1;
        goto L_0x003e;
    L_0x007f:
        r0 = move-exception;
        r1 = r2;
    L_0x0081:
        r4 = r1;
        goto L_0x003e;
    L_0x0083:
        r1 = r5.toString();
        r2[r0] = r1;
        r0 = r0 + 1;
        goto L_0x0047;
    L_0x008c:
        return r2;
    L_0x008d:
        r0 = move-exception;
        goto L_0x0081;
    L_0x008f:
        r0 = move-exception;
        goto L_0x007d;
    L_0x0091:
        r0 = r2;
        goto L_0x003d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.lbsapi.auth.b.b(android.content.Context, java.lang.String):java.lang.String[]");
    }

    static String c(Context context) {
        String str = null;
        if (null == null || "".equals(null)) {
            str = context.getSharedPreferences("mac", 0).getString("macaddr", null);
            if (str == null) {
                str = d(context);
                if (str != null) {
                    str = Base64.encodeToString(str.getBytes(), 0);
                    if (!TextUtils.isEmpty(str)) {
                        context.getSharedPreferences("mac", 0).edit().putString("macaddr", str).commit();
                    }
                } else {
                    str = "";
                }
            }
        }
        if (a.a) {
            a.a("getMacID mac_adress: " + str);
        }
        return str;
    }

    private static boolean c(Context context, String str) {
        boolean z = context.checkCallingOrSelfPermission(str) != -1;
        if (a.a) {
            a.a("hasPermission " + z + " | " + str);
        }
        return z;
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x005d  */
    static java.lang.String d(android.content.Context r7) {
        /*
        r1 = 0;
        r0 = "android.permission.ACCESS_WIFI_STATE";
        r0 = c(r7, r0);	 Catch:{ Exception -> 0x0055 }
        if (r0 == 0) goto L_0x0049;
    L_0x000a:
        r0 = "wifi";
        r0 = r7.getSystemService(r0);	 Catch:{ Exception -> 0x0055 }
        r0 = (android.net.wifi.WifiManager) r0;	 Catch:{ Exception -> 0x0055 }
        r2 = r0.getConnectionInfo();	 Catch:{ Exception -> 0x0055 }
        r0 = r2.getMacAddress();	 Catch:{ Exception -> 0x0055 }
        r1 = android.text.TextUtils.isEmpty(r0);	 Catch:{ Exception -> 0x0065 }
        if (r1 != 0) goto L_0x0029;
    L_0x0021:
        r1 = r0.getBytes();	 Catch:{ Exception -> 0x0065 }
        r3 = 0;
        android.util.Base64.encode(r1, r3);	 Catch:{ Exception -> 0x0065 }
    L_0x0029:
        r1 = com.baidu.lbsapi.auth.a.a;	 Catch:{ Exception -> 0x0065 }
        if (r1 == 0) goto L_0x0048;
    L_0x002d:
        r1 = "ssid=%s mac=%s";
        r3 = 2;
        r3 = new java.lang.Object[r3];	 Catch:{ Exception -> 0x0065 }
        r4 = 0;
        r5 = r2.getSSID();	 Catch:{ Exception -> 0x0065 }
        r3[r4] = r5;	 Catch:{ Exception -> 0x0065 }
        r4 = 1;
        r2 = r2.getMacAddress();	 Catch:{ Exception -> 0x0065 }
        r3[r4] = r2;	 Catch:{ Exception -> 0x0065 }
        r1 = java.lang.String.format(r1, r3);	 Catch:{ Exception -> 0x0065 }
        com.baidu.lbsapi.auth.a.a(r1);	 Catch:{ Exception -> 0x0065 }
    L_0x0048:
        return r0;
    L_0x0049:
        r0 = com.baidu.lbsapi.auth.a.a;	 Catch:{ Exception -> 0x0055 }
        if (r0 == 0) goto L_0x0053;
    L_0x004d:
        r0 = "You need the android.Manifest.permission.ACCESS_WIFI_STATE permission. Open AndroidManifest.xml and just before the final </manifest> tag add:android.permission.ACCESS_WIFI_STATE";
        com.baidu.lbsapi.auth.a.a(r0);	 Catch:{ Exception -> 0x0055 }
    L_0x0053:
        r0 = r1;
        goto L_0x0048;
    L_0x0055:
        r0 = move-exception;
        r6 = r0;
        r0 = r1;
        r1 = r6;
    L_0x0059:
        r2 = com.baidu.lbsapi.auth.a.a;
        if (r2 == 0) goto L_0x0048;
    L_0x005d:
        r1 = r1.toString();
        com.baidu.lbsapi.auth.a.a(r1);
        goto L_0x0048;
    L_0x0065:
        r1 = move-exception;
        goto L_0x0059;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.lbsapi.auth.b.d(android.content.Context):java.lang.String");
    }
}
