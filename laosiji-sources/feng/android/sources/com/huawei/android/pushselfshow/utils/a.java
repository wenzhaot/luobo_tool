package com.huawei.android.pushselfshow.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.widget.TextView;
import com.feng.car.utils.FengConstant;
import com.feng.car.utils.HttpConstant;
import com.feng.library.okhttp.utils.OkHttpUtils;
import com.huawei.android.pushagent.a.a.a.d;
import com.huawei.android.pushagent.a.a.c;
import com.umeng.message.proguard.l;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class a {
    private static final char[] a = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static Typeface b = null;

    public static int a(int i, int i2) {
        c.a("PushSelfShowLog", "enter ctrlSockets(cmd:" + i + " param:" + i2 + l.t);
        try {
            return ((Integer) Class.forName("dalvik.system.Zygote").getMethod("ctrlSockets", new Class[]{Integer.TYPE, Integer.TYPE}).invoke(null, new Object[]{Integer.valueOf(i), Integer.valueOf(i2)})).intValue();
        } catch (NoSuchMethodException e) {
            c.d("PushSelfShowLog", "NoSuchMethodException:" + e);
            return -2;
        } catch (ClassNotFoundException e2) {
            c.d("PushSelfShowLog", "ClassNotFoundException:" + e2);
            return -2;
        } catch (IllegalAccessException e3) {
            c.d("PushSelfShowLog", "IllegalAccessException:" + e3);
            return -2;
        } catch (InvocationTargetException e4) {
            c.d("PushSelfShowLog", "InvocationTargetException:" + e4);
            return -2;
        } catch (RuntimeException e5) {
            c.d("PushSelfShowLog", "RuntimeException:" + e5);
            return -2;
        } catch (Exception e6) {
            c.d("PushSelfShowLog", "Exception:" + e6);
            return -2;
        }
    }

    public static int a(Context context, float f) {
        return (int) ((context.getResources().getDisplayMetrics().density * f) + 0.5f);
    }

    public static long a() {
        return System.currentTimeMillis();
    }

    public static long a(Context context) {
        c.a("PushSelfShowLog", "enter getVersion()");
        long a;
        try {
            List queryBroadcastReceivers = context.getPackageManager().queryBroadcastReceivers(new Intent("com.huawei.android.push.intent.REGISTER").setPackage(context.getPackageName()), FengConstant.IMAGEMIDDLEWIDTH);
            if (queryBroadcastReceivers == null || queryBroadcastReceivers.size() == 0) {
                return -1000;
            }
            a = a((ResolveInfo) queryBroadcastReceivers.get(0), "CS_cloud_version");
            c.a("PushSelfShowLog", "get the version is :" + a);
            return a;
        } catch (Throwable e) {
            c.c("PushSelfShowLog", e.toString(), e);
            a = -1000;
        }
    }

    public static long a(ResolveInfo resolveInfo, String str) {
        long j = -1;
        if (resolveInfo == null) {
            return j;
        }
        try {
            return Long.parseLong(b(resolveInfo, str));
        } catch (NumberFormatException e) {
            c.b("PushSelfShowLog", str + " is not set in " + a(resolveInfo));
            return j;
        }
    }

    public static Intent a(Context context, String str) {
        Intent intent = null;
        try {
            return context.getPackageManager().getLaunchIntentForPackage(str);
        } catch (Throwable e) {
            c.b("PushSelfShowLog", e.toString(), e);
            return intent;
        }
    }

    public static Boolean a(Context context, String str, Intent intent) {
        try {
            List queryIntentActivities = context.getPackageManager().queryIntentActivities(intent, 0);
            if (queryIntentActivities != null && queryIntentActivities.size() > 0) {
                int size = queryIntentActivities.size();
                int i = 0;
                while (i < size) {
                    if (((ResolveInfo) queryIntentActivities.get(i)).activityInfo != null && str.equals(((ResolveInfo) queryIntentActivities.get(i)).activityInfo.applicationInfo.packageName)) {
                        return Boolean.valueOf(true);
                    }
                    i++;
                }
            }
        } catch (Throwable e) {
            c.c("PushSelfShowLog", e.toString(), e);
        }
        return Boolean.valueOf(false);
    }

    public static String a(Context context, String str, String str2) {
        try {
            if (context.getResources().getConfiguration().locale.getLanguage().endsWith("zh")) {
                return str;
            }
        } catch (Throwable e) {
            c.c("PushSelfShowLog", "getStringByLanguage failed ", e);
        }
        return str2;
    }

    public static String a(ResolveInfo resolveInfo) {
        return resolveInfo.serviceInfo != null ? resolveInfo.serviceInfo.packageName : resolveInfo.activityInfo.packageName;
    }

    public static String a(String str) {
        String str2 = "";
        String str3 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDf5raDExuuXbsVNCWl48yuB89W\rfNOuuhPuS2Mptii/0UorpzypBkNTTGt11E7aorCc1lFwlB+4KDMIpFyQsdChSk+A\rt9UfhFKa95uiDpMe5rMfU+DAhoXGER6WQ2qGtrHmBWVv33i3lc76u9IgEfYuLwC6\r1mhQDHzAKPiViY6oeQIDAQAB\r";
        try {
            return a(d.a(str.getBytes("UTF-8"), "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDf5raDExuuXbsVNCWl48yuB89W\rfNOuuhPuS2Mptii/0UorpzypBkNTTGt11E7aorCc1lFwlB+4KDMIpFyQsdChSk+A\rt9UfhFKa95uiDpMe5rMfU+DAhoXGER6WQ2qGtrHmBWVv33i3lc76u9IgEfYuLwC6\r1mhQDHzAKPiViY6oeQIDAQAB\r"));
        } catch (Throwable e) {
            c.d("PushSelfShowLog", "encrypter error ", e);
            return str2;
        }
    }

    public static String a(byte[] bArr) {
        StringBuilder stringBuilder = new StringBuilder(bArr.length);
        for (int i = 0; i < bArr.length; i++) {
            stringBuilder.append(a[(bArr[i] >>> 4) & 15]);
            stringBuilder.append(a[bArr[i] & 15]);
        }
        return stringBuilder.toString();
    }

    public static void a(Context context, Intent intent, long j) {
        try {
            c.a("PushSelfShowLog", "enter setAPDelayAlarm(intent:" + intent.toURI() + " interval:" + j + "ms, context:" + context);
            ((AlarmManager) context.getSystemService("alarm")).set(0, System.currentTimeMillis() + j, PendingIntent.getBroadcast(context, new SecureRandom().nextInt(), intent, 0));
        } catch (Throwable e) {
            c.a("PushSelfShowLog", "set DelayAlarm error", e);
        }
    }

    public static synchronized void a(Context context, TextView textView) {
        synchronized (a.class) {
            if (context == null || textView == null) {
                c.b("PushSelfShowLog", "context is null or textView is null");
            } else if (com.huawei.android.pushagent.a.a.a.a() >= 10 && e()) {
                String str = "chnfzxh";
                if (com.huawei.android.pushagent.a.a.a.a() >= 11) {
                    str = "HwChinese-medium";
                }
                if (b == null) {
                    try {
                        b = Typeface.create(str, 0);
                    } catch (Exception e) {
                        c.d("PushSelfShowLog", e.toString());
                    }
                }
                if (b != null) {
                    c.a("PushSelfShowLog", "setTypeFaceEx success");
                    textView.setTypeface(b);
                }
            }
        }
        return;
    }

    public static void a(Context context, String str, com.huawei.android.pushselfshow.b.a aVar) {
        if (aVar != null) {
            a(context, str, aVar.m, aVar.p);
        }
    }

    public static void a(Context context, String str, String str2, String str3) {
        new Thread(new b(context, str2, str, str3)).start();
    }

    public static void a(File file) {
        if (file != null) {
            c.a("PushSelfShowLog", "delete file " + file.getAbsolutePath());
            File file2 = new File(file.getAbsolutePath() + System.currentTimeMillis());
            if (!file.renameTo(file2)) {
                return;
            }
            if (!(file2.isFile() && file2.delete()) && file2.isDirectory()) {
                File[] listFiles = file2.listFiles();
                if (listFiles != null && listFiles.length != 0) {
                    for (File a : listFiles) {
                        a(a);
                    }
                    if (!file2.delete()) {
                        c.a("PushSelfShowLog", "delete file unsuccess");
                    }
                } else if (!file2.delete()) {
                    c.a("PushSelfShowLog", "delete file failed");
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0034 A:{SYNTHETIC, Splitter: B:21:0x0034} */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0039 A:{SYNTHETIC, Splitter: B:24:0x0039} */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0041 A:{SYNTHETIC, Splitter: B:29:0x0041} */
    /* JADX WARNING: Removed duplicated region for block: B:114:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0046 A:{SYNTHETIC, Splitter: B:32:0x0046} */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x00dc A:{SYNTHETIC, Splitter: B:71:0x00dc} */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x00e1 A:{SYNTHETIC, Splitter: B:74:0x00e1} */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x00e9 A:{SYNTHETIC, Splitter: B:79:0x00e9} */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x00ee A:{SYNTHETIC, Splitter: B:82:0x00ee} */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0034 A:{SYNTHETIC, Splitter: B:21:0x0034} */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0039 A:{SYNTHETIC, Splitter: B:24:0x0039} */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0041 A:{SYNTHETIC, Splitter: B:29:0x0041} */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0046 A:{SYNTHETIC, Splitter: B:32:0x0046} */
    /* JADX WARNING: Removed duplicated region for block: B:114:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x00dc A:{SYNTHETIC, Splitter: B:71:0x00dc} */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x00e1 A:{SYNTHETIC, Splitter: B:74:0x00e1} */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x00e9 A:{SYNTHETIC, Splitter: B:79:0x00e9} */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x00ee A:{SYNTHETIC, Splitter: B:82:0x00ee} */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0034 A:{SYNTHETIC, Splitter: B:21:0x0034} */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0039 A:{SYNTHETIC, Splitter: B:24:0x0039} */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0041 A:{SYNTHETIC, Splitter: B:29:0x0041} */
    /* JADX WARNING: Removed duplicated region for block: B:114:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0046 A:{SYNTHETIC, Splitter: B:32:0x0046} */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x00dc A:{SYNTHETIC, Splitter: B:71:0x00dc} */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x00e1 A:{SYNTHETIC, Splitter: B:74:0x00e1} */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x00e9 A:{SYNTHETIC, Splitter: B:79:0x00e9} */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x00ee A:{SYNTHETIC, Splitter: B:82:0x00ee} */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x00dc A:{SYNTHETIC, Splitter: B:71:0x00dc} */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x00e1 A:{SYNTHETIC, Splitter: B:74:0x00e1} */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x00e9 A:{SYNTHETIC, Splitter: B:79:0x00e9} */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x00ee A:{SYNTHETIC, Splitter: B:82:0x00ee} */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0034 A:{SYNTHETIC, Splitter: B:21:0x0034} */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0039 A:{SYNTHETIC, Splitter: B:24:0x0039} */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0041 A:{SYNTHETIC, Splitter: B:29:0x0041} */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0046 A:{SYNTHETIC, Splitter: B:32:0x0046} */
    /* JADX WARNING: Removed duplicated region for block: B:114:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x00dc A:{SYNTHETIC, Splitter: B:71:0x00dc} */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x00e1 A:{SYNTHETIC, Splitter: B:74:0x00e1} */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x00e9 A:{SYNTHETIC, Splitter: B:79:0x00e9} */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x00ee A:{SYNTHETIC, Splitter: B:82:0x00ee} */
    public static void a(java.io.File r7, java.io.File r8) {
        /*
        r2 = 0;
        r5 = new java.io.FileInputStream;	 Catch:{ IOException -> 0x013d, all -> 0x00d6 }
        r5.<init>(r7);	 Catch:{ IOException -> 0x013d, all -> 0x00d6 }
        r4 = new java.io.BufferedInputStream;	 Catch:{ IOException -> 0x0143, all -> 0x012b }
        r4.<init>(r5);	 Catch:{ IOException -> 0x0143, all -> 0x012b }
        r3 = new java.io.FileOutputStream;	 Catch:{ IOException -> 0x0149, all -> 0x012f }
        r3.<init>(r8);	 Catch:{ IOException -> 0x0149, all -> 0x012f }
        r1 = new java.io.BufferedOutputStream;	 Catch:{ IOException -> 0x014f, all -> 0x0132 }
        r1.<init>(r3);	 Catch:{ IOException -> 0x014f, all -> 0x0132 }
        r0 = 5120; // 0x1400 float:7.175E-42 double:2.5296E-320;
        r0 = new byte[r0];	 Catch:{ IOException -> 0x0025, all -> 0x0134 }
    L_0x0019:
        r2 = r4.read(r0);	 Catch:{ IOException -> 0x0025, all -> 0x0134 }
        r6 = -1;
        if (r2 == r6) goto L_0x004a;
    L_0x0020:
        r6 = 0;
        r1.write(r0, r6, r2);	 Catch:{ IOException -> 0x0025, all -> 0x0134 }
        goto L_0x0019;
    L_0x0025:
        r0 = move-exception;
        r2 = r3;
        r3 = r4;
        r4 = r5;
    L_0x0029:
        r5 = "PushSelfShowLog";
        r6 = "copyFile ";
        com.huawei.android.pushagent.a.a.c.d(r5, r6, r0);	 Catch:{ all -> 0x0137 }
        if (r3 == 0) goto L_0x0037;
    L_0x0034:
        r3.close();	 Catch:{ Exception -> 0x009b }
    L_0x0037:
        if (r1 == 0) goto L_0x003f;
    L_0x0039:
        r1.flush();	 Catch:{ Exception -> 0x00a6 }
    L_0x003c:
        r1.close();	 Catch:{ Exception -> 0x00b2 }
    L_0x003f:
        if (r2 == 0) goto L_0x0044;
    L_0x0041:
        r2.close();	 Catch:{ Exception -> 0x00be }
    L_0x0044:
        if (r4 == 0) goto L_0x0049;
    L_0x0046:
        r4.close();	 Catch:{ Exception -> 0x00ca }
    L_0x0049:
        return;
    L_0x004a:
        if (r4 == 0) goto L_0x004f;
    L_0x004c:
        r4.close();	 Catch:{ Exception -> 0x006d }
    L_0x004f:
        if (r1 == 0) goto L_0x0057;
    L_0x0051:
        r1.flush();	 Catch:{ Exception -> 0x0078 }
    L_0x0054:
        r1.close();	 Catch:{ Exception -> 0x0084 }
    L_0x0057:
        if (r3 == 0) goto L_0x005c;
    L_0x0059:
        r3.close();	 Catch:{ Exception -> 0x0090 }
    L_0x005c:
        if (r5 == 0) goto L_0x0049;
    L_0x005e:
        r5.close();	 Catch:{ Exception -> 0x0062 }
        goto L_0x0049;
    L_0x0062:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r2 = "input.close() ";
        com.huawei.android.pushagent.a.a.c.d(r1, r2, r0);
        goto L_0x0049;
    L_0x006d:
        r0 = move-exception;
        r2 = "PushSelfShowLog";
        r4 = "inBuff.close() ";
        com.huawei.android.pushagent.a.a.c.d(r2, r4, r0);
        goto L_0x004f;
    L_0x0078:
        r0 = move-exception;
        r2 = "PushSelfShowLog";
        r4 = r0.toString();
        com.huawei.android.pushagent.a.a.c.c(r2, r4, r0);
        goto L_0x0054;
    L_0x0084:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r2 = r0.toString();
        com.huawei.android.pushagent.a.a.c.c(r1, r2, r0);
        goto L_0x0057;
    L_0x0090:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r2 = "output.close() ";
        com.huawei.android.pushagent.a.a.c.d(r1, r2, r0);
        goto L_0x005c;
    L_0x009b:
        r0 = move-exception;
        r3 = "PushSelfShowLog";
        r5 = "inBuff.close() ";
        com.huawei.android.pushagent.a.a.c.d(r3, r5, r0);
        goto L_0x0037;
    L_0x00a6:
        r0 = move-exception;
        r3 = "PushSelfShowLog";
        r5 = r0.toString();
        com.huawei.android.pushagent.a.a.c.c(r3, r5, r0);
        goto L_0x003c;
    L_0x00b2:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r3 = r0.toString();
        com.huawei.android.pushagent.a.a.c.c(r1, r3, r0);
        goto L_0x003f;
    L_0x00be:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r2 = "output.close() ";
        com.huawei.android.pushagent.a.a.c.d(r1, r2, r0);
        goto L_0x0044;
    L_0x00ca:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r2 = "input.close() ";
        com.huawei.android.pushagent.a.a.c.d(r1, r2, r0);
        goto L_0x0049;
    L_0x00d6:
        r0 = move-exception;
        r3 = r2;
        r4 = r2;
        r5 = r2;
    L_0x00da:
        if (r4 == 0) goto L_0x00df;
    L_0x00dc:
        r4.close();	 Catch:{ Exception -> 0x00f2 }
    L_0x00df:
        if (r2 == 0) goto L_0x00e7;
    L_0x00e1:
        r2.flush();	 Catch:{ Exception -> 0x00fd }
    L_0x00e4:
        r2.close();	 Catch:{ Exception -> 0x0109 }
    L_0x00e7:
        if (r3 == 0) goto L_0x00ec;
    L_0x00e9:
        r3.close();	 Catch:{ Exception -> 0x0115 }
    L_0x00ec:
        if (r5 == 0) goto L_0x00f1;
    L_0x00ee:
        r5.close();	 Catch:{ Exception -> 0x0120 }
    L_0x00f1:
        throw r0;
    L_0x00f2:
        r1 = move-exception;
        r4 = "PushSelfShowLog";
        r6 = "inBuff.close() ";
        com.huawei.android.pushagent.a.a.c.d(r4, r6, r1);
        goto L_0x00df;
    L_0x00fd:
        r1 = move-exception;
        r4 = "PushSelfShowLog";
        r6 = r1.toString();
        com.huawei.android.pushagent.a.a.c.c(r4, r6, r1);
        goto L_0x00e4;
    L_0x0109:
        r1 = move-exception;
        r2 = "PushSelfShowLog";
        r4 = r1.toString();
        com.huawei.android.pushagent.a.a.c.c(r2, r4, r1);
        goto L_0x00e7;
    L_0x0115:
        r1 = move-exception;
        r2 = "PushSelfShowLog";
        r3 = "output.close() ";
        com.huawei.android.pushagent.a.a.c.d(r2, r3, r1);
        goto L_0x00ec;
    L_0x0120:
        r1 = move-exception;
        r2 = "PushSelfShowLog";
        r3 = "input.close() ";
        com.huawei.android.pushagent.a.a.c.d(r2, r3, r1);
        goto L_0x00f1;
    L_0x012b:
        r0 = move-exception;
        r3 = r2;
        r4 = r2;
        goto L_0x00da;
    L_0x012f:
        r0 = move-exception;
        r3 = r2;
        goto L_0x00da;
    L_0x0132:
        r0 = move-exception;
        goto L_0x00da;
    L_0x0134:
        r0 = move-exception;
        r2 = r1;
        goto L_0x00da;
    L_0x0137:
        r0 = move-exception;
        r5 = r4;
        r4 = r3;
        r3 = r2;
        r2 = r1;
        goto L_0x00da;
    L_0x013d:
        r0 = move-exception;
        r1 = r2;
        r3 = r2;
        r4 = r2;
        goto L_0x0029;
    L_0x0143:
        r0 = move-exception;
        r1 = r2;
        r3 = r2;
        r4 = r5;
        goto L_0x0029;
    L_0x0149:
        r0 = move-exception;
        r1 = r2;
        r3 = r4;
        r4 = r5;
        goto L_0x0029;
    L_0x014f:
        r0 = move-exception;
        r1 = r2;
        r2 = r3;
        r3 = r4;
        r4 = r5;
        goto L_0x0029;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.android.pushselfshow.utils.a.a(java.io.File, java.io.File):void");
    }

    public static boolean a(Context context, Intent intent) {
        if (context == null) {
            c.b("PushSelfShowLog", "context is null");
            return false;
        } else if (intent == null) {
            c.b("PushSelfShowLog", "intent is null");
            return false;
        } else {
            List queryIntentActivities = context.getPackageManager().queryIntentActivities(intent, FengConstant.IMAGEMIDDLEWIDTH);
            if (queryIntentActivities == null || queryIntentActivities.size() == 0) {
                c.d("PushSelfShowLog", "no activity exist, may be system Err!! pkgName:");
                return false;
            }
            boolean z = ((ResolveInfo) queryIntentActivities.get(0)).activityInfo.exported;
            c.b("PushSelfShowLog", "exportedFlag:" + z);
            CharSequence charSequence = ((ResolveInfo) queryIntentActivities.get(0)).activityInfo.permission;
            c.b("PushSelfShowLog", "need permission:" + charSequence);
            return !z ? false : TextUtils.isEmpty(charSequence) ? true : "com.huawei.pushagent.permission.LAUNCH_ACTIVITY".equals(charSequence);
        }
    }

    public static boolean a(String str, String str2) {
        try {
            boolean mkdirs = new File(str2).mkdirs();
            c.a("PushSelfShowLog", "urlSrc is %s ,urlDest is %s,urlDest is already exist?%s ", str, str2, Boolean.valueOf(mkdirs));
            File[] listFiles = new File(str).listFiles();
            if (listFiles == null) {
                return true;
            }
            for (int i = 0; i < listFiles.length; i++) {
                if (listFiles[i].isFile()) {
                    a(listFiles[i], new File(str2 + File.separator + listFiles[i].getName()));
                }
                if (listFiles[i].isDirectory()) {
                    b(str + File.separator + listFiles[i].getName(), str2 + File.separator + listFiles[i].getName());
                }
            }
            return true;
        } catch (Throwable e) {
            c.d("PushSelfShowLog", "fileCopy error ", e);
            return false;
        }
    }

    public static long b(String str) {
        if (str == null) {
            str = "";
        }
        try {
            Date date = new Date();
            int hours = (date.getHours() * 2) + (date.getMinutes() / 30);
            String concat = str.concat(str);
            c.a("PushSelfShowLog", "startIndex is %s ,and ap is %s ,length is %s", Integer.valueOf(hours), concat, Integer.valueOf(concat.length()));
            int i = hours;
            while (i < concat.length()) {
                if (concat.charAt(i) != '0') {
                    long minutes = ((long) (((i - hours) * 30) - (date.getMinutes() % 30))) * OkHttpUtils.DEFAULT_MILLISECONDS;
                    c.a("PushSelfShowLog", "startIndex is %s i is %s delay %s", Integer.valueOf(hours), Integer.valueOf(i), Long.valueOf(minutes));
                    return minutes >= 0 ? minutes : 0;
                } else {
                    i++;
                }
            }
        } catch (Throwable e) {
            c.c("PushSelfShowLog", "error ", e);
        }
        return 0;
    }

    public static String b(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(HttpConstant.PHONE);
        return telephonyManager != null ? telephonyManager.getDeviceId() : "";
    }

    private static String b(ResolveInfo resolveInfo, String str) {
        Bundle bundle = resolveInfo.serviceInfo != null ? resolveInfo.serviceInfo.metaData : resolveInfo.activityInfo.metaData;
        return bundle == null ? null : bundle.getString(str);
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0035 A:{SYNTHETIC, Splitter: B:19:0x0035} */
    /* JADX WARNING: Removed duplicated region for block: B:63:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x003a A:{SYNTHETIC, Splitter: B:22:0x003a} */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x007b A:{SYNTHETIC, Splitter: B:42:0x007b} */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0080 A:{SYNTHETIC, Splitter: B:45:0x0080} */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0035 A:{SYNTHETIC, Splitter: B:19:0x0035} */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x003a A:{SYNTHETIC, Splitter: B:22:0x003a} */
    /* JADX WARNING: Removed duplicated region for block: B:63:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x007b A:{SYNTHETIC, Splitter: B:42:0x007b} */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0080 A:{SYNTHETIC, Splitter: B:45:0x0080} */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x007b A:{SYNTHETIC, Splitter: B:42:0x007b} */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0080 A:{SYNTHETIC, Splitter: B:45:0x0080} */
    public static void b(android.content.Context r5, java.lang.String r6, java.lang.String r7) {
        /*
        r2 = 0;
        r0 = new java.io.File;	 Catch:{ IOException -> 0x00a3, all -> 0x0077 }
        r0.<init>(r7);	 Catch:{ IOException -> 0x00a3, all -> 0x0077 }
        r0 = r0.exists();	 Catch:{ IOException -> 0x00a3, all -> 0x0077 }
        if (r0 != 0) goto L_0x003e;
    L_0x000c:
        r0 = r5.getAssets();	 Catch:{ IOException -> 0x00a3, all -> 0x0077 }
        r3 = r0.open(r6);	 Catch:{ IOException -> 0x00a3, all -> 0x0077 }
        r1 = new java.io.FileOutputStream;	 Catch:{ IOException -> 0x00a6, all -> 0x009a }
        r1.<init>(r7);	 Catch:{ IOException -> 0x00a6, all -> 0x009a }
        r0 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r0 = new byte[r0];	 Catch:{ IOException -> 0x0028, all -> 0x009c }
    L_0x001d:
        r2 = r3.read(r0);	 Catch:{ IOException -> 0x0028, all -> 0x009c }
        if (r2 <= 0) goto L_0x0040;
    L_0x0023:
        r4 = 0;
        r1.write(r0, r4, r2);	 Catch:{ IOException -> 0x0028, all -> 0x009c }
        goto L_0x001d;
    L_0x0028:
        r0 = move-exception;
        r2 = r3;
    L_0x002a:
        r3 = "PushSelfShowLog";
        r4 = "copyAsset ";
        com.huawei.android.pushagent.a.a.c.d(r3, r4, r0);	 Catch:{ all -> 0x009f }
        if (r1 == 0) goto L_0x0038;
    L_0x0035:
        r1.close();	 Catch:{ Exception -> 0x0061 }
    L_0x0038:
        if (r2 == 0) goto L_0x003d;
    L_0x003a:
        r2.close();	 Catch:{ Exception -> 0x006c }
    L_0x003d:
        return;
    L_0x003e:
        r1 = r2;
        r3 = r2;
    L_0x0040:
        if (r1 == 0) goto L_0x0045;
    L_0x0042:
        r1.close();	 Catch:{ Exception -> 0x0056 }
    L_0x0045:
        if (r3 == 0) goto L_0x003d;
    L_0x0047:
        r3.close();	 Catch:{ Exception -> 0x004b }
        goto L_0x003d;
    L_0x004b:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r2 = "is.close() ";
        com.huawei.android.pushagent.a.a.c.d(r1, r2, r0);
        goto L_0x003d;
    L_0x0056:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r2 = "fos.close() ";
        com.huawei.android.pushagent.a.a.c.d(r1, r2, r0);
        goto L_0x0045;
    L_0x0061:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r3 = "fos.close() ";
        com.huawei.android.pushagent.a.a.c.d(r1, r3, r0);
        goto L_0x0038;
    L_0x006c:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r2 = "is.close() ";
        com.huawei.android.pushagent.a.a.c.d(r1, r2, r0);
        goto L_0x003d;
    L_0x0077:
        r0 = move-exception;
        r3 = r2;
    L_0x0079:
        if (r2 == 0) goto L_0x007e;
    L_0x007b:
        r2.close();	 Catch:{ Exception -> 0x0084 }
    L_0x007e:
        if (r3 == 0) goto L_0x0083;
    L_0x0080:
        r3.close();	 Catch:{ Exception -> 0x008f }
    L_0x0083:
        throw r0;
    L_0x0084:
        r1 = move-exception;
        r2 = "PushSelfShowLog";
        r4 = "fos.close() ";
        com.huawei.android.pushagent.a.a.c.d(r2, r4, r1);
        goto L_0x007e;
    L_0x008f:
        r1 = move-exception;
        r2 = "PushSelfShowLog";
        r3 = "is.close() ";
        com.huawei.android.pushagent.a.a.c.d(r2, r3, r1);
        goto L_0x0083;
    L_0x009a:
        r0 = move-exception;
        goto L_0x0079;
    L_0x009c:
        r0 = move-exception;
        r2 = r1;
        goto L_0x0079;
    L_0x009f:
        r0 = move-exception;
        r3 = r2;
        r2 = r1;
        goto L_0x0079;
    L_0x00a3:
        r0 = move-exception;
        r1 = r2;
        goto L_0x002a;
    L_0x00a6:
        r0 = move-exception;
        r1 = r2;
        r2 = r3;
        goto L_0x002a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.android.pushselfshow.utils.a.b(android.content.Context, java.lang.String, java.lang.String):void");
    }

    public static void b(File file) {
        c.a("PushSelfShowLog", "delete file before ");
        if (file != null && file.exists()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null && listFiles.length != 0) {
                long currentTimeMillis = System.currentTimeMillis();
                for (File file2 : listFiles) {
                    try {
                        if (currentTimeMillis - file2.lastModified() > 86400000) {
                            c.e("PushSelfShowLog", "delete file before " + file2.getAbsolutePath());
                            a(file2);
                        }
                    } catch (Exception e) {
                        c.e("PushSelfShowLog", e.toString());
                    }
                }
            }
        }
    }

    private static void b(String str, String str2) {
        if (new File(str2).mkdirs()) {
            c.e("PushSelfShowLog", "mkdir");
        }
        File[] listFiles = new File(str).listFiles();
        if (listFiles != null) {
            for (int i = 0; i < listFiles.length; i++) {
                if (listFiles[i].isFile()) {
                    a(listFiles[i], new File(new File(str2).getAbsolutePath() + File.separator + listFiles[i].getName()));
                }
                if (listFiles[i].isDirectory()) {
                    b(str + "/" + listFiles[i].getName(), str2 + "/" + listFiles[i].getName());
                }
            }
        }
    }

    public static boolean b() {
        return VERSION.SDK_INT >= 11;
    }

    public static boolean b(Context context, String str) {
        if (context == null || str == null || "".equals(str)) {
            return false;
        }
        try {
            if (context.getPackageManager().getApplicationInfo(str, 8192) == null) {
                return false;
            }
            c.a("PushSelfShowLog", str + " is installed");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String c(Context context, String str) {
        String str2;
        Throwable e;
        String str3 = "";
        try {
            str2 = "";
            str2 = ((!Environment.getExternalStorageState().equals("mounted") ? context.getFilesDir().getPath() : Environment.getExternalStorageDirectory().getPath()) + File.separator + "PushService") + File.separator + str;
            try {
                c.a("PushSelfShowLog", "dbPath is " + str2);
            } catch (Exception e2) {
                e = e2;
                c.d("PushSelfShowLog", "getDbPath error", e);
                return str2;
            }
        } catch (Throwable e3) {
            Throwable th = e3;
            str2 = str3;
            e = th;
        }
        return str2;
    }

    public static ArrayList c(Context context) {
        ArrayList arrayList = new ArrayList();
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse("market://details?id="));
        List queryIntentActivities = context.getPackageManager().queryIntentActivities(intent, 0);
        if (!(queryIntentActivities == null || queryIntentActivities.size() == 0)) {
            int size = queryIntentActivities.size();
            for (int i = 0; i < size; i++) {
                if (((ResolveInfo) queryIntentActivities.get(i)).activityInfo != null) {
                    arrayList.add(((ResolveInfo) queryIntentActivities.get(i)).activityInfo.applicationInfo.packageName);
                }
            }
        }
        return arrayList;
    }

    public static boolean c() {
        return VERSION.SDK_INT >= 16;
    }

    public static boolean d() {
        return com.huawei.android.pushagent.a.a.a.a() >= 9;
    }

    public static boolean d(Context context) {
        Intent intent = new Intent("android.intent.action.SENDTO");
        intent.setPackage("com.android.email");
        intent.setData(Uri.fromParts("mailto", "xxxx@xxxx.com", null));
        List queryIntentActivities = context.getPackageManager().queryIntentActivities(intent, 0);
        return (queryIntentActivities == null || queryIntentActivities.size() == 0) ? false : true;
    }

    private static boolean e() {
        return "zh".equals(Locale.getDefault().getLanguage());
    }

    public static boolean e(Context context) {
        return "com.huawei.android.pushagent".equals(context.getPackageName());
    }

    public static boolean f(Context context) {
        try {
            return context.getPackageManager().getApplicationInfo("com.huawei.android.pushagent", 128) != null;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x0066  */
    public static boolean g(android.content.Context r9) {
        /*
        r6 = 1;
        r7 = 0;
        r8 = 0;
        r0 = r9.getContentResolver();
        r1 = com.huawei.android.pushselfshow.richpush.provider.RichMediaProvider.a.a;	 Catch:{ Exception -> 0x0050, all -> 0x0062 }
        r2 = 0;
        r3 = 0;
        r4 = 0;
        r5 = 0;
        r1 = r0.query(r1, r2, r3, r4, r5);	 Catch:{ Exception -> 0x0050, all -> 0x0062 }
        if (r1 == 0) goto L_0x0049;
    L_0x0013:
        r0 = r1.moveToFirst();	 Catch:{ Exception -> 0x006c }
        if (r0 == 0) goto L_0x0049;
    L_0x0019:
        r0 = "isSupport";
        r0 = r1.getColumnIndex(r0);	 Catch:{ Exception -> 0x006c }
        r0 = r1.getInt(r0);	 Catch:{ Exception -> 0x006c }
        r2 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x006c }
        r3.<init>();	 Catch:{ Exception -> 0x006c }
        r4 = "isExistProvider:";
        r3 = r3.append(r4);	 Catch:{ Exception -> 0x006c }
        r3 = r3.append(r0);	 Catch:{ Exception -> 0x006c }
        r3 = r3.toString();	 Catch:{ Exception -> 0x006c }
        com.huawei.android.pushagent.a.a.c.a(r2, r3);	 Catch:{ Exception -> 0x006c }
        if (r6 != r0) goto L_0x0047;
    L_0x0040:
        r0 = r6;
    L_0x0041:
        if (r1 == 0) goto L_0x0046;
    L_0x0043:
        r1.close();
    L_0x0046:
        return r0;
    L_0x0047:
        r0 = r7;
        goto L_0x0041;
    L_0x0049:
        if (r1 == 0) goto L_0x004e;
    L_0x004b:
        r1.close();
    L_0x004e:
        r0 = r7;
        goto L_0x0046;
    L_0x0050:
        r0 = move-exception;
        r1 = r8;
    L_0x0052:
        r2 = "PushSelfShowLog";
        r3 = r0.toString();	 Catch:{ all -> 0x006a }
        com.huawei.android.pushagent.a.a.c.a(r2, r3, r0);	 Catch:{ all -> 0x006a }
        if (r1 == 0) goto L_0x004e;
    L_0x005e:
        r1.close();
        goto L_0x004e;
    L_0x0062:
        r0 = move-exception;
        r1 = r8;
    L_0x0064:
        if (r1 == 0) goto L_0x0069;
    L_0x0066:
        r1.close();
    L_0x0069:
        throw r0;
    L_0x006a:
        r0 = move-exception;
        goto L_0x0064;
    L_0x006c:
        r0 = move-exception;
        goto L_0x0052;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.android.pushselfshow.utils.a.g(android.content.Context):boolean");
    }

    public static int h(Context context) {
        if (context == null) {
            return 3;
        }
        return (VERSION.SDK_INT < 16 || context.getResources().getIdentifier("androidhwext:style/Theme.Emui", null, null) == 0) ? 3 : 0;
    }

    public static int i(Context context) {
        try {
            Class cls = Class.forName("com.huawei.android.immersion.ImmersionStyle");
            int intValue = ((Integer) cls.getDeclaredMethod("getPrimaryColor", new Class[]{Context.class}).invoke(cls, new Object[]{context})).intValue();
            c.b("PushSelfShowLog", "colorPrimary:" + intValue);
            return intValue;
        } catch (ClassNotFoundException e) {
            c.d("PushSelfShowLog", "ImmersionStyle ClassNotFoundException");
        } catch (Throwable e2) {
            c.c("PushSelfShowLog", e2.toString(), e2);
        } catch (Throwable e22) {
            c.c("PushSelfShowLog", e22.toString(), e22);
        } catch (Throwable e222) {
            c.c("PushSelfShowLog", e222.toString(), e222);
        } catch (Throwable e2222) {
            c.c("PushSelfShowLog", e2222.toString(), e2222);
        } catch (Throwable e22222) {
            c.c("PushSelfShowLog", e22222.toString(), e22222);
        }
        return 0;
    }

    public static int j(Context context) {
        int intValue;
        Throwable e;
        Throwable th;
        try {
            Class cls = Class.forName("com.huawei.android.immersion.ImmersionStyle");
            intValue = ((Integer) cls.getDeclaredMethod("getPrimaryColor", new Class[]{Context.class}).invoke(cls, new Object[]{context})).intValue();
            intValue = ((Integer) cls.getDeclaredMethod("getSuggestionForgroundColorStyle", new Class[]{Integer.TYPE}).invoke(cls, new Object[]{Integer.valueOf(intValue)})).intValue();
            try {
                c.b("PushSelfShowLog", "getSuggestionForgroundColorStyle:" + intValue);
            } catch (ClassNotFoundException e2) {
                c.d("PushSelfShowLog", "ImmersionStyle ClassNotFoundException");
                return intValue;
            } catch (SecurityException e3) {
                e = e3;
                c.c("PushSelfShowLog", e.toString(), e);
                return intValue;
            } catch (NoSuchMethodException e4) {
                e = e4;
                c.c("PushSelfShowLog", e.toString(), e);
                return intValue;
            } catch (IllegalArgumentException e5) {
                e = e5;
                c.c("PushSelfShowLog", e.toString(), e);
                return intValue;
            } catch (IllegalAccessException e6) {
                e = e6;
                c.c("PushSelfShowLog", e.toString(), e);
                return intValue;
            } catch (InvocationTargetException e7) {
                e = e7;
                c.c("PushSelfShowLog", e.toString(), e);
                return intValue;
            }
        } catch (ClassNotFoundException e8) {
            intValue = -1;
        } catch (Throwable e9) {
            th = e9;
            intValue = -1;
            e = th;
            c.c("PushSelfShowLog", e.toString(), e);
            return intValue;
        } catch (Throwable e92) {
            th = e92;
            intValue = -1;
            e = th;
            c.c("PushSelfShowLog", e.toString(), e);
            return intValue;
        } catch (Throwable e922) {
            th = e922;
            intValue = -1;
            e = th;
            c.c("PushSelfShowLog", e.toString(), e);
            return intValue;
        } catch (Throwable e9222) {
            th = e9222;
            intValue = -1;
            e = th;
            c.c("PushSelfShowLog", e.toString(), e);
            return intValue;
        } catch (Throwable e92222) {
            th = e92222;
            intValue = -1;
            e = th;
            c.c("PushSelfShowLog", e.toString(), e);
            return intValue;
        }
        return intValue;
    }

    public static String k(Context context) {
        File externalCacheDir = context.getExternalCacheDir();
        if (externalCacheDir != null) {
            return externalCacheDir.getPath();
        }
        return Environment.getExternalStorageDirectory().getPath() + ("/Android/data/" + context.getPackageName() + "/cache");
    }

    private static boolean m(Context context) {
        boolean z = true;
        int i = -1;
        if (context == null) {
            return false;
        }
        try {
            i = Secure.getInt(context.getContentResolver(), "user_experience_involved", -1);
            c.a("PushSelfShowLog", "settingMainSwitch:" + i);
        } catch (Throwable e) {
            c.c("PushSelfShowLog", e.toString(), e);
        }
        if (i != 1) {
            z = false;
        }
        return z;
    }
}
