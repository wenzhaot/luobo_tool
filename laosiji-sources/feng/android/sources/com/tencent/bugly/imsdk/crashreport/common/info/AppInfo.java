package com.tencent.bugly.imsdk.crashreport.common.info;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.bugly.imsdk.proguard.w;
import com.tencent.bugly.imsdk.proguard.y;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.io.ByteArrayInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.Principal;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: BUGLY */
public class AppInfo {
    private static ActivityManager a;

    static {
        "@buglyAllChannel@".split(MiPushClient.ACCEPT_TIME_SEPARATOR);
        "@buglyAllChannelPriority@".split(MiPushClient.ACCEPT_TIME_SEPARATOR);
    }

    public static String a(Context context) {
        if (context == null) {
            return null;
        }
        try {
            return context.getPackageName();
        } catch (Throwable th) {
            if (!w.a(th)) {
                th.printStackTrace();
            }
            return "fail";
        }
    }

    public static PackageInfo b(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(a(context), 0);
        } catch (Throwable th) {
            if (!w.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    public static boolean a(Context context, String str) {
        if (context == null || str == null || str.trim().length() <= 0) {
            return false;
        }
        try {
            String[] strArr = context.getPackageManager().getPackageInfo(context.getPackageName(), 4096).requestedPermissions;
            if (strArr == null) {
                return false;
            }
            for (Object equals : strArr) {
                if (str.equals(equals)) {
                    return true;
                }
            }
            return false;
        } catch (Throwable th) {
            if (w.a(th)) {
                return false;
            }
            th.printStackTrace();
            return false;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0045 A:{Catch:{ all -> 0x0060 }} */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x004e A:{SYNTHETIC, Splitter: B:23:0x004e} */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0058 A:{SYNTHETIC, Splitter: B:29:0x0058} */
    public static java.lang.String a(int r5) {
        /*
        r0 = 0;
        r2 = 0;
        r1 = new java.io.FileReader;	 Catch:{ Throwable -> 0x003d, all -> 0x0054 }
        r3 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x003d, all -> 0x0054 }
        r4 = "/proc/";
        r3.<init>(r4);	 Catch:{ Throwable -> 0x003d, all -> 0x0054 }
        r3 = r3.append(r5);	 Catch:{ Throwable -> 0x003d, all -> 0x0054 }
        r4 = "/cmdline";
        r3 = r3.append(r4);	 Catch:{ Throwable -> 0x003d, all -> 0x0054 }
        r3 = r3.toString();	 Catch:{ Throwable -> 0x003d, all -> 0x0054 }
        r1.<init>(r3);	 Catch:{ Throwable -> 0x003d, all -> 0x0054 }
        r2 = 512; // 0x200 float:7.175E-43 double:2.53E-321;
        r2 = new char[r2];	 Catch:{ Throwable -> 0x0062 }
        r1.read(r2);	 Catch:{ Throwable -> 0x0062 }
    L_0x0025:
        r3 = r2.length;	 Catch:{ Throwable -> 0x0062 }
        if (r0 >= r3) goto L_0x002f;
    L_0x0028:
        r3 = r2[r0];	 Catch:{ Throwable -> 0x0062 }
        if (r3 == 0) goto L_0x002f;
    L_0x002c:
        r0 = r0 + 1;
        goto L_0x0025;
    L_0x002f:
        r3 = new java.lang.String;	 Catch:{ Throwable -> 0x0062 }
        r3.<init>(r2);	 Catch:{ Throwable -> 0x0062 }
        r2 = 0;
        r0 = r3.substring(r2, r0);	 Catch:{ Throwable -> 0x0062 }
        r1.close();	 Catch:{ Throwable -> 0x005c }
    L_0x003c:
        return r0;
    L_0x003d:
        r0 = move-exception;
        r1 = r2;
    L_0x003f:
        r2 = com.tencent.bugly.imsdk.proguard.w.a(r0);	 Catch:{ all -> 0x0060 }
        if (r2 != 0) goto L_0x0048;
    L_0x0045:
        r0.printStackTrace();	 Catch:{ all -> 0x0060 }
    L_0x0048:
        r0 = java.lang.String.valueOf(r5);	 Catch:{ all -> 0x0060 }
        if (r1 == 0) goto L_0x003c;
    L_0x004e:
        r1.close();	 Catch:{ Throwable -> 0x0052 }
        goto L_0x003c;
    L_0x0052:
        r1 = move-exception;
        goto L_0x003c;
    L_0x0054:
        r0 = move-exception;
        r1 = r2;
    L_0x0056:
        if (r1 == 0) goto L_0x005b;
    L_0x0058:
        r1.close();	 Catch:{ Throwable -> 0x005e }
    L_0x005b:
        throw r0;
    L_0x005c:
        r1 = move-exception;
        goto L_0x003c;
    L_0x005e:
        r1 = move-exception;
        goto L_0x005b;
    L_0x0060:
        r0 = move-exception;
        goto L_0x0056;
    L_0x0062:
        r0 = move-exception;
        goto L_0x003f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.imsdk.crashreport.common.info.AppInfo.a(int):java.lang.String");
    }

    public static String c(Context context) {
        if (context == null) {
            return null;
        }
        try {
            PackageManager packageManager = context.getPackageManager();
            ApplicationInfo applicationInfo = context.getApplicationInfo();
            if (packageManager == null || applicationInfo == null) {
                return null;
            }
            return packageManager.getApplicationLabel(applicationInfo).toString();
        } catch (Throwable th) {
            if (w.a(th)) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }

    public static Map<String, String> d(Context context) {
        if (context == null) {
            return null;
        }
        try {
            HashMap hashMap;
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo.metaData != null) {
                hashMap = new HashMap();
                Object obj = applicationInfo.metaData.get("BUGLY_DISABLE");
                if (obj != null) {
                    hashMap.put("BUGLY_DISABLE", obj.toString());
                }
                obj = applicationInfo.metaData.get("BUGLY_APPID");
                if (obj != null) {
                    hashMap.put("BUGLY_APPID", obj.toString());
                }
                obj = applicationInfo.metaData.get("BUGLY_APP_CHANNEL");
                if (obj != null) {
                    hashMap.put("BUGLY_APP_CHANNEL", obj.toString());
                }
                obj = applicationInfo.metaData.get("BUGLY_APP_VERSION");
                if (obj != null) {
                    hashMap.put("BUGLY_APP_VERSION", obj.toString());
                }
                obj = applicationInfo.metaData.get("BUGLY_ENABLE_DEBUG");
                if (obj != null) {
                    hashMap.put("BUGLY_ENABLE_DEBUG", obj.toString());
                }
                Object obj2 = applicationInfo.metaData.get("com.tencent.rdm.uuid");
                if (obj2 != null) {
                    hashMap.put("com.tencent.rdm.uuid", obj2.toString());
                }
            } else {
                hashMap = null;
            }
            return hashMap;
        } catch (Throwable th) {
            if (w.a(th)) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }

    public static List<String> a(Map<String, String> map) {
        if (map == null) {
            return null;
        }
        try {
            String str = (String) map.get("BUGLY_DISABLE");
            if (str == null || str.length() == 0) {
                return null;
            }
            String[] split = str.split(MiPushClient.ACCEPT_TIME_SEPARATOR);
            for (int i = 0; i < split.length; i++) {
                split[i] = split[i].trim();
            }
            return Arrays.asList(split);
        } catch (Throwable th) {
            if (!w.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    private static String a(byte[] bArr) {
        StringBuilder stringBuilder = new StringBuilder();
        if (bArr != null && bArr.length > 0) {
            try {
                CertificateFactory instance = CertificateFactory.getInstance("X.509");
                if (instance == null) {
                    return null;
                }
                X509Certificate x509Certificate = (X509Certificate) instance.generateCertificate(new ByteArrayInputStream(bArr));
                if (x509Certificate == null) {
                    return null;
                }
                stringBuilder.append("Issuer|");
                Principal issuerDN = x509Certificate.getIssuerDN();
                if (issuerDN != null) {
                    stringBuilder.append(issuerDN.toString());
                } else {
                    stringBuilder.append("unknown");
                }
                stringBuilder.append("\n");
                stringBuilder.append("SerialNumber|");
                BigInteger serialNumber = x509Certificate.getSerialNumber();
                if (issuerDN != null) {
                    stringBuilder.append(serialNumber.toString(16));
                } else {
                    stringBuilder.append("unknown");
                }
                stringBuilder.append("\n");
                stringBuilder.append("NotBefore|");
                Date notBefore = x509Certificate.getNotBefore();
                if (issuerDN != null) {
                    stringBuilder.append(notBefore.toString());
                } else {
                    stringBuilder.append("unknown");
                }
                stringBuilder.append("\n");
                stringBuilder.append("NotAfter|");
                notBefore = x509Certificate.getNotAfter();
                if (issuerDN != null) {
                    stringBuilder.append(notBefore.toString());
                } else {
                    stringBuilder.append("unknown");
                }
                stringBuilder.append("\n");
                stringBuilder.append("SHA1|");
                String a = y.a(MessageDigest.getInstance("SHA1").digest(x509Certificate.getEncoded()));
                if (a == null || a.length() <= 0) {
                    stringBuilder.append("unknown");
                } else {
                    stringBuilder.append(a.toString());
                }
                stringBuilder.append("\n");
                stringBuilder.append("MD5|");
                String a2 = y.a(MessageDigest.getInstance("MD5").digest(x509Certificate.getEncoded()));
                if (a2 == null || a2.length() <= 0) {
                    stringBuilder.append("unknown");
                } else {
                    stringBuilder.append(a2.toString());
                }
            } catch (Throwable e) {
                if (!w.a(e)) {
                    e.printStackTrace();
                }
            } catch (Throwable e2) {
                if (!w.a(e2)) {
                    e2.printStackTrace();
                }
            }
        }
        if (stringBuilder.length() == 0) {
            return "unknown";
        }
        return stringBuilder.toString();
    }

    public static String e(Context context) {
        String a = a(context);
        if (a == null) {
            return null;
        }
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(a, 64);
            if (packageInfo == null) {
                return null;
            }
            Signature[] signatureArr = packageInfo.signatures;
            if (signatureArr == null || signatureArr.length == 0) {
                return null;
            }
            return a(packageInfo.signatures[0].toByteArray());
        } catch (NameNotFoundException e) {
            return null;
        }
    }

    public static boolean f(Context context) {
        if (context == null) {
            return false;
        }
        if (a == null) {
            a = (ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME);
        }
        try {
            MemoryInfo memoryInfo = new MemoryInfo();
            a.getMemoryInfo(memoryInfo);
            if (!memoryInfo.lowMemory) {
                return false;
            }
            w.c("Memory is low.", new Object[0]);
            return true;
        } catch (Throwable th) {
            if (!w.a(th)) {
                th.printStackTrace();
            }
            return false;
        }
    }
}
