package com.tencent.bugly.imsdk.crashreport.common.info;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import com.feng.car.utils.HttpConstant;
import com.netease.LDNetDiagnoUtils.LDNetUtil;
import com.taobao.accs.utl.UtilityImpl;
import com.tencent.bugly.imsdk.proguard.w;
import com.tencent.bugly.imsdk.proguard.y;
import com.umeng.message.MsgConstant;
import com.umeng.message.proguard.l;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

/* compiled from: BUGLY */
public final class b {
    private static String a = null;
    private static String b = null;

    public static String a() {
        try {
            return Build.MODEL;
        } catch (Throwable th) {
            if (!w.a(th)) {
                th.printStackTrace();
            }
            return "fail";
        }
    }

    public static String b() {
        try {
            return VERSION.RELEASE;
        } catch (Throwable th) {
            if (!w.a(th)) {
                th.printStackTrace();
            }
            return "fail";
        }
    }

    public static int c() {
        try {
            return VERSION.SDK_INT;
        } catch (Throwable th) {
            if (!w.a(th)) {
                th.printStackTrace();
            }
            return -1;
        }
    }

    public static String a(Context context) {
        String deviceId;
        if (context == null) {
            return null;
        }
        if (AppInfo.a(context, MsgConstant.PERMISSION_READ_PHONE_STATE)) {
            try {
                deviceId = ((TelephonyManager) context.getSystemService(HttpConstant.PHONE)).getDeviceId();
                if (deviceId == null) {
                    return deviceId;
                }
                try {
                    return deviceId.toLowerCase();
                } catch (Throwable th) {
                }
            } catch (Throwable th2) {
                deviceId = null;
            }
        } else {
            w.d("no READ_PHONE_STATE permission to get IMEI", new Object[0]);
            return null;
        }
        w.a("Failed to get IMEI.", new Object[0]);
        return deviceId;
    }

    public static String b(Context context) {
        String subscriberId;
        if (context == null) {
            return null;
        }
        if (AppInfo.a(context, MsgConstant.PERMISSION_READ_PHONE_STATE)) {
            try {
                subscriberId = ((TelephonyManager) context.getSystemService(HttpConstant.PHONE)).getSubscriberId();
                if (subscriberId == null) {
                    return subscriberId;
                }
                try {
                    return subscriberId.toLowerCase();
                } catch (Throwable th) {
                }
            } catch (Throwable th2) {
                subscriberId = null;
            }
        } else {
            w.d("no READ_PHONE_STATE permission to get IMSI", new Object[0]);
            return null;
        }
        w.a("Failed to get IMSI.", new Object[0]);
        return subscriberId;
    }

    public static String c(Context context) {
        Throwable th;
        String str = "fail";
        if (context == null) {
            return str;
        }
        try {
            String string = Secure.getString(context.getContentResolver(), SocializeProtocolConstants.PROTOCOL_KEY_ANDROID_ID);
            if (string != null) {
                return string.toLowerCase();
            }
            try {
                return "null";
            } catch (Throwable th2) {
                Throwable th3 = th2;
                str = string;
                th = th3;
                if (w.a(th)) {
                    return str;
                }
                w.a("Failed to get Android ID.", new Object[0]);
                return str;
            }
        } catch (Throwable th4) {
            th = th4;
        }
    }

    public static String d(Context context) {
        String str = "fail";
        if (context == null) {
            return str;
        }
        String toLowerCase;
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(UtilityImpl.NET_TYPE_WIFI);
            if (wifiManager != null) {
                WifiInfo connectionInfo = wifiManager.getConnectionInfo();
                if (connectionInfo != null) {
                    str = connectionInfo.getMacAddress();
                    toLowerCase = str == null ? "null" : str.toLowerCase();
                    return toLowerCase;
                }
            }
            toLowerCase = str;
        } catch (Throwable th) {
            Throwable th2 = th;
            toLowerCase = str;
            Throwable th3 = th2;
            if (!w.a(th3)) {
                th3.printStackTrace();
            }
        }
        return toLowerCase;
    }

    private static boolean o() {
        try {
            if (Environment.getExternalStorageState().equals("mounted")) {
                return true;
            }
        } catch (Throwable th) {
            if (!w.a(th)) {
                th.printStackTrace();
            }
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:37:0x0073 A:{Catch:{ all -> 0x00bf }} */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0078 A:{SYNTHETIC, Splitter: B:39:0x0078} */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x007d A:{SYNTHETIC, Splitter: B:42:0x007d} */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x009d A:{SYNTHETIC, Splitter: B:56:0x009d} */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00a2 A:{SYNTHETIC, Splitter: B:59:0x00a2} */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0073 A:{Catch:{ all -> 0x00bf }} */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0078 A:{SYNTHETIC, Splitter: B:39:0x0078} */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x007d A:{SYNTHETIC, Splitter: B:42:0x007d} */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x009d A:{SYNTHETIC, Splitter: B:56:0x009d} */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00a2 A:{SYNTHETIC, Splitter: B:59:0x00a2} */
    private static java.lang.String p() {
        /*
        r6 = 2;
        r1 = 0;
        r0 = "/system/build.prop";
        r3 = new java.io.FileReader;	 Catch:{ Throwable -> 0x006a, all -> 0x0098 }
        r3.<init>(r0);	 Catch:{ Throwable -> 0x006a, all -> 0x0098 }
        r2 = new java.io.BufferedReader;	 Catch:{ Throwable -> 0x00c1, all -> 0x00bc }
        r0 = 2048; // 0x800 float:2.87E-42 double:1.0118E-320;
        r2.<init>(r3, r0);	 Catch:{ Throwable -> 0x00c1, all -> 0x00bc }
    L_0x0011:
        r0 = r2.readLine();	 Catch:{ Throwable -> 0x00c4 }
        if (r0 == 0) goto L_0x00c6;
    L_0x0017:
        r4 = "=";
        r5 = 2;
        r0 = r0.split(r4, r5);	 Catch:{ Throwable -> 0x00c4 }
        r4 = r0.length;	 Catch:{ Throwable -> 0x00c4 }
        if (r4 != r6) goto L_0x0011;
    L_0x0022:
        r4 = 0;
        r4 = r0[r4];	 Catch:{ Throwable -> 0x00c4 }
        r5 = "ro.product.cpu.abilist";
        r4 = r4.equals(r5);	 Catch:{ Throwable -> 0x00c4 }
        if (r4 == 0) goto L_0x0044;
    L_0x002e:
        r4 = 1;
        r0 = r0[r4];	 Catch:{ Throwable -> 0x00c4 }
    L_0x0031:
        if (r0 == 0) goto L_0x003d;
    L_0x0033:
        r4 = ",";
        r0 = r0.split(r4);	 Catch:{ Throwable -> 0x00c4 }
        r4 = 0;
        r0 = r0[r4];	 Catch:{ Throwable -> 0x00c4 }
    L_0x003d:
        r2.close();	 Catch:{ IOException -> 0x0054 }
    L_0x0040:
        r3.close();	 Catch:{ IOException -> 0x005f }
    L_0x0043:
        return r0;
    L_0x0044:
        r4 = 0;
        r4 = r0[r4];	 Catch:{ Throwable -> 0x00c4 }
        r5 = "ro.product.cpu.abi";
        r4 = r4.equals(r5);	 Catch:{ Throwable -> 0x00c4 }
        if (r4 == 0) goto L_0x0011;
    L_0x0050:
        r4 = 1;
        r0 = r0[r4];	 Catch:{ Throwable -> 0x00c4 }
        goto L_0x0031;
    L_0x0054:
        r1 = move-exception;
        r2 = com.tencent.bugly.imsdk.proguard.w.a(r1);
        if (r2 != 0) goto L_0x0040;
    L_0x005b:
        r1.printStackTrace();
        goto L_0x0040;
    L_0x005f:
        r1 = move-exception;
        r2 = com.tencent.bugly.imsdk.proguard.w.a(r1);
        if (r2 != 0) goto L_0x0043;
    L_0x0066:
        r1.printStackTrace();
        goto L_0x0043;
    L_0x006a:
        r0 = move-exception;
        r2 = r1;
        r3 = r1;
    L_0x006d:
        r4 = com.tencent.bugly.imsdk.proguard.w.a(r0);	 Catch:{ all -> 0x00bf }
        if (r4 != 0) goto L_0x0076;
    L_0x0073:
        r0.printStackTrace();	 Catch:{ all -> 0x00bf }
    L_0x0076:
        if (r2 == 0) goto L_0x007b;
    L_0x0078:
        r2.close();	 Catch:{ IOException -> 0x0082 }
    L_0x007b:
        if (r3 == 0) goto L_0x0080;
    L_0x007d:
        r3.close();	 Catch:{ IOException -> 0x008d }
    L_0x0080:
        r0 = r1;
        goto L_0x0043;
    L_0x0082:
        r0 = move-exception;
        r2 = com.tencent.bugly.imsdk.proguard.w.a(r0);
        if (r2 != 0) goto L_0x007b;
    L_0x0089:
        r0.printStackTrace();
        goto L_0x007b;
    L_0x008d:
        r0 = move-exception;
        r2 = com.tencent.bugly.imsdk.proguard.w.a(r0);
        if (r2 != 0) goto L_0x0080;
    L_0x0094:
        r0.printStackTrace();
        goto L_0x0080;
    L_0x0098:
        r0 = move-exception;
        r2 = r1;
        r3 = r1;
    L_0x009b:
        if (r2 == 0) goto L_0x00a0;
    L_0x009d:
        r2.close();	 Catch:{ IOException -> 0x00a6 }
    L_0x00a0:
        if (r3 == 0) goto L_0x00a5;
    L_0x00a2:
        r3.close();	 Catch:{ IOException -> 0x00b1 }
    L_0x00a5:
        throw r0;
    L_0x00a6:
        r1 = move-exception;
        r2 = com.tencent.bugly.imsdk.proguard.w.a(r1);
        if (r2 != 0) goto L_0x00a0;
    L_0x00ad:
        r1.printStackTrace();
        goto L_0x00a0;
    L_0x00b1:
        r1 = move-exception;
        r2 = com.tencent.bugly.imsdk.proguard.w.a(r1);
        if (r2 != 0) goto L_0x00a5;
    L_0x00b8:
        r1.printStackTrace();
        goto L_0x00a5;
    L_0x00bc:
        r0 = move-exception;
        r2 = r1;
        goto L_0x009b;
    L_0x00bf:
        r0 = move-exception;
        goto L_0x009b;
    L_0x00c1:
        r0 = move-exception;
        r2 = r1;
        goto L_0x006d;
    L_0x00c4:
        r0 = move-exception;
        goto L_0x006d;
    L_0x00c6:
        r0 = r1;
        goto L_0x0031;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.imsdk.crashreport.common.info.b.p():java.lang.String");
    }

    public static String a(boolean z) {
        String str = null;
        if (z) {
            try {
                str = p();
            } catch (Throwable th) {
                if (!w.a(th)) {
                    th.printStackTrace();
                }
                return "fail";
            }
        }
        if (str == null) {
            str = System.getProperty("os.arch");
        }
        return str;
    }

    public static long d() {
        long j = -1;
        try {
            StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
            return ((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize());
        } catch (Throwable th) {
            if (w.a(th)) {
                return j;
            }
            th.printStackTrace();
            return j;
        }
    }

    public static long e() {
        long j = -1;
        try {
            StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
            return ((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize());
        } catch (Throwable th) {
            if (w.a(th)) {
                return j;
            }
            th.printStackTrace();
            return j;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:60:0x00a9 A:{SYNTHETIC, Splitter: B:60:0x00a9} */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x00ae A:{SYNTHETIC, Splitter: B:63:0x00ae} */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x007e A:{Catch:{ all -> 0x00cd }} */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0083 A:{SYNTHETIC, Splitter: B:43:0x0083} */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x0088 A:{SYNTHETIC, Splitter: B:46:0x0088} */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x00a9 A:{SYNTHETIC, Splitter: B:60:0x00a9} */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x00ae A:{SYNTHETIC, Splitter: B:63:0x00ae} */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x00a9 A:{SYNTHETIC, Splitter: B:60:0x00a9} */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x00ae A:{SYNTHETIC, Splitter: B:63:0x00ae} */
    public static long f() {
        /*
        r1 = 0;
        r0 = "/proc/meminfo";
        r3 = new java.io.FileReader;	 Catch:{ Throwable -> 0x0076, all -> 0x00a4 }
        r3.<init>(r0);	 Catch:{ Throwable -> 0x0076, all -> 0x00a4 }
        r2 = new java.io.BufferedReader;	 Catch:{ Throwable -> 0x00d1, all -> 0x00c8 }
        r0 = 2048; // 0x800 float:2.87E-42 double:1.0118E-320;
        r2.<init>(r3, r0);	 Catch:{ Throwable -> 0x00d1, all -> 0x00c8 }
        r0 = r2.readLine();	 Catch:{ Throwable -> 0x00d4, all -> 0x00cb }
        if (r0 != 0) goto L_0x0035;
    L_0x0016:
        r2.close();	 Catch:{ IOException -> 0x001f }
    L_0x0019:
        r3.close();	 Catch:{ IOException -> 0x002a }
    L_0x001c:
        r0 = -1;
    L_0x001e:
        return r0;
    L_0x001f:
        r0 = move-exception;
        r1 = com.tencent.bugly.imsdk.proguard.w.a(r0);
        if (r1 != 0) goto L_0x0019;
    L_0x0026:
        r0.printStackTrace();
        goto L_0x0019;
    L_0x002a:
        r0 = move-exception;
        r1 = com.tencent.bugly.imsdk.proguard.w.a(r0);
        if (r1 != 0) goto L_0x001c;
    L_0x0031:
        r0.printStackTrace();
        goto L_0x001c;
    L_0x0035:
        r1 = ":\\s+";
        r4 = 2;
        r0 = r0.split(r1, r4);	 Catch:{ Throwable -> 0x00d4, all -> 0x00cb }
        r1 = 1;
        r0 = r0[r1];	 Catch:{ Throwable -> 0x00d4, all -> 0x00cb }
        r0 = r0.toLowerCase();	 Catch:{ Throwable -> 0x00d4, all -> 0x00cb }
        r1 = "kb";
        r4 = "";
        r0 = r0.replace(r1, r4);	 Catch:{ Throwable -> 0x00d4, all -> 0x00cb }
        r0 = r0.trim();	 Catch:{ Throwable -> 0x00d4, all -> 0x00cb }
        r0 = java.lang.Long.parseLong(r0);	 Catch:{ Throwable -> 0x00d4, all -> 0x00cb }
        r4 = 10;
        r0 = r0 << r4;
        r2.close();	 Catch:{ IOException -> 0x006b }
    L_0x005c:
        r3.close();	 Catch:{ IOException -> 0x0060 }
        goto L_0x001e;
    L_0x0060:
        r2 = move-exception;
        r3 = com.tencent.bugly.imsdk.proguard.w.a(r2);
        if (r3 != 0) goto L_0x001e;
    L_0x0067:
        r2.printStackTrace();
        goto L_0x001e;
    L_0x006b:
        r2 = move-exception;
        r4 = com.tencent.bugly.imsdk.proguard.w.a(r2);
        if (r4 != 0) goto L_0x005c;
    L_0x0072:
        r2.printStackTrace();
        goto L_0x005c;
    L_0x0076:
        r0 = move-exception;
        r2 = r1;
    L_0x0078:
        r3 = com.tencent.bugly.imsdk.proguard.w.a(r0);	 Catch:{ all -> 0x00cd }
        if (r3 != 0) goto L_0x0081;
    L_0x007e:
        r0.printStackTrace();	 Catch:{ all -> 0x00cd }
    L_0x0081:
        if (r1 == 0) goto L_0x0086;
    L_0x0083:
        r1.close();	 Catch:{ IOException -> 0x008e }
    L_0x0086:
        if (r2 == 0) goto L_0x008b;
    L_0x0088:
        r2.close();	 Catch:{ IOException -> 0x0099 }
    L_0x008b:
        r0 = -2;
        goto L_0x001e;
    L_0x008e:
        r0 = move-exception;
        r1 = com.tencent.bugly.imsdk.proguard.w.a(r0);
        if (r1 != 0) goto L_0x0086;
    L_0x0095:
        r0.printStackTrace();
        goto L_0x0086;
    L_0x0099:
        r0 = move-exception;
        r1 = com.tencent.bugly.imsdk.proguard.w.a(r0);
        if (r1 != 0) goto L_0x008b;
    L_0x00a0:
        r0.printStackTrace();
        goto L_0x008b;
    L_0x00a4:
        r0 = move-exception;
        r2 = r1;
        r3 = r1;
    L_0x00a7:
        if (r2 == 0) goto L_0x00ac;
    L_0x00a9:
        r2.close();	 Catch:{ IOException -> 0x00b2 }
    L_0x00ac:
        if (r3 == 0) goto L_0x00b1;
    L_0x00ae:
        r3.close();	 Catch:{ IOException -> 0x00bd }
    L_0x00b1:
        throw r0;
    L_0x00b2:
        r1 = move-exception;
        r2 = com.tencent.bugly.imsdk.proguard.w.a(r1);
        if (r2 != 0) goto L_0x00ac;
    L_0x00b9:
        r1.printStackTrace();
        goto L_0x00ac;
    L_0x00bd:
        r1 = move-exception;
        r2 = com.tencent.bugly.imsdk.proguard.w.a(r1);
        if (r2 != 0) goto L_0x00b1;
    L_0x00c4:
        r1.printStackTrace();
        goto L_0x00b1;
    L_0x00c8:
        r0 = move-exception;
        r2 = r1;
        goto L_0x00a7;
    L_0x00cb:
        r0 = move-exception;
        goto L_0x00a7;
    L_0x00cd:
        r0 = move-exception;
        r3 = r2;
        r2 = r1;
        goto L_0x00a7;
    L_0x00d1:
        r0 = move-exception;
        r2 = r3;
        goto L_0x0078;
    L_0x00d4:
        r0 = move-exception;
        r1 = r2;
        r2 = r3;
        goto L_0x0078;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.imsdk.crashreport.common.info.b.f():long");
    }

    /* JADX WARNING: Removed duplicated region for block: B:89:0x0140 A:{SYNTHETIC, Splitter: B:89:0x0140} */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x0145 A:{SYNTHETIC, Splitter: B:92:0x0145} */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x0140 A:{SYNTHETIC, Splitter: B:89:0x0140} */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x0145 A:{SYNTHETIC, Splitter: B:92:0x0145} */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x0114 A:{Catch:{ all -> 0x0164 }} */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x0119 A:{SYNTHETIC, Splitter: B:72:0x0119} */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x011e A:{SYNTHETIC, Splitter: B:75:0x011e} */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x0140 A:{SYNTHETIC, Splitter: B:89:0x0140} */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x0145 A:{SYNTHETIC, Splitter: B:92:0x0145} */
    public static long g() {
        /*
        r3 = 0;
        r0 = -1;
        r10 = 10;
        r2 = "/proc/meminfo";
        r4 = new java.io.FileReader;	 Catch:{ Throwable -> 0x010c, all -> 0x013b }
        r4.<init>(r2);	 Catch:{ Throwable -> 0x010c, all -> 0x013b }
        r2 = new java.io.BufferedReader;	 Catch:{ Throwable -> 0x0168, all -> 0x015f }
        r5 = 2048; // 0x800 float:2.87E-42 double:1.0118E-320;
        r2.<init>(r4, r5);	 Catch:{ Throwable -> 0x0168, all -> 0x015f }
        r2.readLine();	 Catch:{ Throwable -> 0x016c, all -> 0x0162 }
        r3 = r2.readLine();	 Catch:{ Throwable -> 0x016c, all -> 0x0162 }
        if (r3 != 0) goto L_0x003a;
    L_0x001d:
        r2.close();	 Catch:{ IOException -> 0x0024 }
    L_0x0020:
        r4.close();	 Catch:{ IOException -> 0x002f }
    L_0x0023:
        return r0;
    L_0x0024:
        r2 = move-exception;
        r3 = com.tencent.bugly.imsdk.proguard.w.a(r2);
        if (r3 != 0) goto L_0x0020;
    L_0x002b:
        r2.printStackTrace();
        goto L_0x0020;
    L_0x002f:
        r2 = move-exception;
        r3 = com.tencent.bugly.imsdk.proguard.w.a(r2);
        if (r3 != 0) goto L_0x0023;
    L_0x0036:
        r2.printStackTrace();
        goto L_0x0023;
    L_0x003a:
        r5 = ":\\s+";
        r6 = 2;
        r3 = r3.split(r5, r6);	 Catch:{ Throwable -> 0x016c, all -> 0x0162 }
        r5 = 1;
        r3 = r3[r5];	 Catch:{ Throwable -> 0x016c, all -> 0x0162 }
        r3 = r3.toLowerCase();	 Catch:{ Throwable -> 0x016c, all -> 0x0162 }
        r5 = "kb";
        r6 = "";
        r3 = r3.replace(r5, r6);	 Catch:{ Throwable -> 0x016c, all -> 0x0162 }
        r3 = r3.trim();	 Catch:{ Throwable -> 0x016c, all -> 0x0162 }
        r6 = 0;
        r8 = java.lang.Long.parseLong(r3);	 Catch:{ Throwable -> 0x016c, all -> 0x0162 }
        r8 = r8 << r10;
        r6 = r6 + r8;
        r3 = r2.readLine();	 Catch:{ Throwable -> 0x016c, all -> 0x0162 }
        if (r3 != 0) goto L_0x0082;
    L_0x0065:
        r2.close();	 Catch:{ IOException -> 0x0077 }
    L_0x0068:
        r4.close();	 Catch:{ IOException -> 0x006c }
        goto L_0x0023;
    L_0x006c:
        r2 = move-exception;
        r3 = com.tencent.bugly.imsdk.proguard.w.a(r2);
        if (r3 != 0) goto L_0x0023;
    L_0x0073:
        r2.printStackTrace();
        goto L_0x0023;
    L_0x0077:
        r2 = move-exception;
        r3 = com.tencent.bugly.imsdk.proguard.w.a(r2);
        if (r3 != 0) goto L_0x0068;
    L_0x007e:
        r2.printStackTrace();
        goto L_0x0068;
    L_0x0082:
        r5 = ":\\s+";
        r8 = 2;
        r3 = r3.split(r5, r8);	 Catch:{ Throwable -> 0x016c, all -> 0x0162 }
        r5 = 1;
        r3 = r3[r5];	 Catch:{ Throwable -> 0x016c, all -> 0x0162 }
        r3 = r3.toLowerCase();	 Catch:{ Throwable -> 0x016c, all -> 0x0162 }
        r5 = "kb";
        r8 = "";
        r3 = r3.replace(r5, r8);	 Catch:{ Throwable -> 0x016c, all -> 0x0162 }
        r3 = r3.trim();	 Catch:{ Throwable -> 0x016c, all -> 0x0162 }
        r8 = java.lang.Long.parseLong(r3);	 Catch:{ Throwable -> 0x016c, all -> 0x0162 }
        r8 = r8 << r10;
        r6 = r6 + r8;
        r3 = r2.readLine();	 Catch:{ Throwable -> 0x016c, all -> 0x0162 }
        if (r3 != 0) goto L_0x00ca;
    L_0x00ab:
        r2.close();	 Catch:{ IOException -> 0x00bf }
    L_0x00ae:
        r4.close();	 Catch:{ IOException -> 0x00b3 }
        goto L_0x0023;
    L_0x00b3:
        r2 = move-exception;
        r3 = com.tencent.bugly.imsdk.proguard.w.a(r2);
        if (r3 != 0) goto L_0x0023;
    L_0x00ba:
        r2.printStackTrace();
        goto L_0x0023;
    L_0x00bf:
        r2 = move-exception;
        r3 = com.tencent.bugly.imsdk.proguard.w.a(r2);
        if (r3 != 0) goto L_0x00ae;
    L_0x00c6:
        r2.printStackTrace();
        goto L_0x00ae;
    L_0x00ca:
        r0 = ":\\s+";
        r1 = 2;
        r0 = r3.split(r0, r1);	 Catch:{ Throwable -> 0x016c, all -> 0x0162 }
        r1 = 1;
        r0 = r0[r1];	 Catch:{ Throwable -> 0x016c, all -> 0x0162 }
        r0 = r0.toLowerCase();	 Catch:{ Throwable -> 0x016c, all -> 0x0162 }
        r1 = "kb";
        r3 = "";
        r0 = r0.replace(r1, r3);	 Catch:{ Throwable -> 0x016c, all -> 0x0162 }
        r0 = r0.trim();	 Catch:{ Throwable -> 0x016c, all -> 0x0162 }
        r0 = java.lang.Long.parseLong(r0);	 Catch:{ Throwable -> 0x016c, all -> 0x0162 }
        r0 = r0 << r10;
        r0 = r0 + r6;
        r2.close();	 Catch:{ IOException -> 0x0101 }
    L_0x00f0:
        r4.close();	 Catch:{ IOException -> 0x00f5 }
        goto L_0x0023;
    L_0x00f5:
        r2 = move-exception;
        r3 = com.tencent.bugly.imsdk.proguard.w.a(r2);
        if (r3 != 0) goto L_0x0023;
    L_0x00fc:
        r2.printStackTrace();
        goto L_0x0023;
    L_0x0101:
        r2 = move-exception;
        r3 = com.tencent.bugly.imsdk.proguard.w.a(r2);
        if (r3 != 0) goto L_0x00f0;
    L_0x0108:
        r2.printStackTrace();
        goto L_0x00f0;
    L_0x010c:
        r0 = move-exception;
        r1 = r3;
    L_0x010e:
        r2 = com.tencent.bugly.imsdk.proguard.w.a(r0);	 Catch:{ all -> 0x0164 }
        if (r2 != 0) goto L_0x0117;
    L_0x0114:
        r0.printStackTrace();	 Catch:{ all -> 0x0164 }
    L_0x0117:
        if (r1 == 0) goto L_0x011c;
    L_0x0119:
        r1.close();	 Catch:{ IOException -> 0x0125 }
    L_0x011c:
        if (r3 == 0) goto L_0x0121;
    L_0x011e:
        r3.close();	 Catch:{ IOException -> 0x0130 }
    L_0x0121:
        r0 = -2;
        goto L_0x0023;
    L_0x0125:
        r0 = move-exception;
        r1 = com.tencent.bugly.imsdk.proguard.w.a(r0);
        if (r1 != 0) goto L_0x011c;
    L_0x012c:
        r0.printStackTrace();
        goto L_0x011c;
    L_0x0130:
        r0 = move-exception;
        r1 = com.tencent.bugly.imsdk.proguard.w.a(r0);
        if (r1 != 0) goto L_0x0121;
    L_0x0137:
        r0.printStackTrace();
        goto L_0x0121;
    L_0x013b:
        r0 = move-exception;
        r2 = r3;
        r4 = r3;
    L_0x013e:
        if (r2 == 0) goto L_0x0143;
    L_0x0140:
        r2.close();	 Catch:{ IOException -> 0x0149 }
    L_0x0143:
        if (r4 == 0) goto L_0x0148;
    L_0x0145:
        r4.close();	 Catch:{ IOException -> 0x0154 }
    L_0x0148:
        throw r0;
    L_0x0149:
        r1 = move-exception;
        r2 = com.tencent.bugly.imsdk.proguard.w.a(r1);
        if (r2 != 0) goto L_0x0143;
    L_0x0150:
        r1.printStackTrace();
        goto L_0x0143;
    L_0x0154:
        r1 = move-exception;
        r2 = com.tencent.bugly.imsdk.proguard.w.a(r1);
        if (r2 != 0) goto L_0x0148;
    L_0x015b:
        r1.printStackTrace();
        goto L_0x0148;
    L_0x015f:
        r0 = move-exception;
        r2 = r3;
        goto L_0x013e;
    L_0x0162:
        r0 = move-exception;
        goto L_0x013e;
    L_0x0164:
        r0 = move-exception;
        r2 = r1;
        r4 = r3;
        goto L_0x013e;
    L_0x0168:
        r0 = move-exception;
        r1 = r3;
        r3 = r4;
        goto L_0x010e;
    L_0x016c:
        r0 = move-exception;
        r1 = r2;
        r3 = r4;
        goto L_0x010e;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.imsdk.crashreport.common.info.b.g():long");
    }

    public static long h() {
        if (!o()) {
            return 0;
        }
        try {
            StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
            return ((long) statFs.getBlockSize()) * ((long) statFs.getBlockCount());
        } catch (Throwable th) {
            if (!w.a(th)) {
                th.printStackTrace();
            }
            return -2;
        }
    }

    public static long i() {
        if (!o()) {
            return 0;
        }
        try {
            StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
            return ((long) statFs.getBlockSize()) * ((long) statFs.getAvailableBlocks());
        } catch (Throwable th) {
            if (!w.a(th)) {
                th.printStackTrace();
            }
            return -2;
        }
    }

    public static String j() {
        String str = "fail";
        try {
            return Locale.getDefault().getCountry();
        } catch (Throwable th) {
            if (w.a(th)) {
                return str;
            }
            th.printStackTrace();
            return str;
        }
    }

    public static String k() {
        String str = "fail";
        try {
            return Build.BRAND;
        } catch (Throwable th) {
            if (w.a(th)) {
                return str;
            }
            th.printStackTrace();
            return str;
        }
    }

    public static String e(Context context) {
        String str = "unknown";
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                return null;
            }
            if (activeNetworkInfo.getType() == 1) {
                return LDNetUtil.NETWORKTYPE_WIFI;
            }
            String str2;
            if (activeNetworkInfo.getType() == 0) {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(HttpConstant.PHONE);
                if (telephonyManager != null) {
                    int networkType = telephonyManager.getNetworkType();
                    switch (networkType) {
                        case 1:
                            return "GPRS";
                        case 2:
                            return "EDGE";
                        case 3:
                            return "UMTS";
                        case 4:
                            return "CDMA";
                        case 5:
                            return "EVDO_0";
                        case 6:
                            return "EVDO_A";
                        case 7:
                            return "1xRTT";
                        case 8:
                            return "HSDPA";
                        case 9:
                            return "HSUPA";
                        case 10:
                            return "HSPA";
                        case 11:
                            return "iDen";
                        case 12:
                            return "EVDO_B";
                        case 13:
                            return "LTE";
                        case 14:
                            return "eHRPD";
                        case 15:
                            return "HSPA+";
                        default:
                            str2 = "MOBILE(" + networkType + l.t;
                            break;
                    }
                    return str2;
                }
            }
            str2 = str;
            return str2;
        } catch (Throwable e) {
            if (w.a(e)) {
                return str;
            }
            e.printStackTrace();
            return str;
        }
    }

    public static String f(Context context) {
        String a = y.a(context, "ro.miui.ui.version.name");
        if (!y.a(a) && !a.equals("fail")) {
            return "XiaoMi/MIUI/" + a;
        }
        a = y.a(context, "ro.build.version.emui");
        if (!y.a(a) && !a.equals("fail")) {
            return "HuaWei/EMOTION/" + a;
        }
        a = y.a(context, "ro.lenovo.series");
        if (y.a(a) || a.equals("fail")) {
            a = y.a(context, "ro.build.nubia.rom.name");
            if (!y.a(a) && !a.equals("fail")) {
                return "Zte/NUBIA/" + a + "_" + y.a(context, "ro.build.nubia.rom.code");
            }
            a = y.a(context, "ro.meizu.product.model");
            if (!y.a(a) && !a.equals("fail")) {
                return "Meizu/FLYME/" + y.a(context, "ro.build.display.id");
            }
            a = y.a(context, "ro.build.version.opporom");
            if (!y.a(a) && !a.equals("fail")) {
                return "Oppo/COLOROS/" + a;
            }
            a = y.a(context, "ro.vivo.os.build.display.id");
            if (!y.a(a) && !a.equals("fail")) {
                return "vivo/FUNTOUCH/" + a;
            }
            a = y.a(context, "ro.aa.romver");
            if (!y.a(a) && !a.equals("fail")) {
                return "htc/" + a + "/" + y.a(context, "ro.build.description");
            }
            a = y.a(context, "ro.lewa.version");
            if (!y.a(a) && !a.equals("fail")) {
                return "tcl/" + a + "/" + y.a(context, "ro.build.display.id");
            }
            a = y.a(context, "ro.gn.gnromvernumber");
            if (!y.a(a) && !a.equals("fail")) {
                return "amigo/" + a + "/" + y.a(context, "ro.build.display.id");
            }
            a = y.a(context, "ro.build.tyd.kbstyle_version");
            if (y.a(a) || a.equals("fail")) {
                return y.a(context, "ro.build.fingerprint") + "/" + y.a(context, "ro.build.rom.id");
            }
            return "dido/" + a;
        }
        return "Lenovo/VIBE/" + y.a(context, "ro.build.version.incremental");
    }

    public static String g(Context context) {
        return y.a(context, "ro.board.platform");
    }

    public static boolean h(Context context) {
        boolean z;
        boolean exists;
        try {
            exists = new File("/system/app/Superuser.apk").exists();
        } catch (Throwable th) {
            if (!w.b(th)) {
                th.printStackTrace();
            }
            exists = false;
        }
        Boolean bool = null;
        ArrayList a = y.a(context, new String[]{"/system/bin/sh", "-c", "type su"});
        if (a != null && a.size() > 0) {
            Iterator it = a.iterator();
            while (it.hasNext()) {
                Boolean valueOf;
                String str = (String) it.next();
                w.c(str, new Object[0]);
                if (str.contains("not found")) {
                    valueOf = Boolean.valueOf(false);
                } else {
                    valueOf = bool;
                }
                bool = valueOf;
            }
            if (bool == null) {
                bool = Boolean.valueOf(true);
            }
        }
        bool = Boolean.valueOf(bool == null ? false : bool.booleanValue());
        if (Build.TAGS == null || !Build.TAGS.contains("test-keys")) {
            z = false;
        } else {
            z = true;
        }
        if (z || exists || bool.booleanValue()) {
            return true;
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:47:0x00a7 A:{SYNTHETIC, Splitter: B:47:0x00a7} */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x00be A:{Splitter: B:5:0x0021, ExcHandler: all (th java.lang.Throwable), PHI: r1 } */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00a7 A:{SYNTHETIC, Splitter: B:47:0x00a7} */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x0099 A:{SYNTHETIC, Splitter: B:40:0x0099} */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00a7 A:{SYNTHETIC, Splitter: B:47:0x00a7} */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x0099 A:{SYNTHETIC, Splitter: B:40:0x0099} */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00b3 A:{Splitter: B:11:0x002e, ExcHandler: all (th java.lang.Throwable), PHI: r2 } */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x00be A:{Splitter: B:5:0x0021, ExcHandler: all (th java.lang.Throwable), PHI: r1 } */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00a7 A:{SYNTHETIC, Splitter: B:47:0x00a7} */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x00be A:{Splitter: B:5:0x0021, ExcHandler: all (th java.lang.Throwable), PHI: r1 } */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00a7 A:{SYNTHETIC, Splitter: B:47:0x00a7} */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:52:0x00b0, code:
            r0 = th;
     */
    /* JADX WARNING: Missing block: B:53:0x00b1, code:
            r2 = r1;
     */
    /* JADX WARNING: Missing block: B:54:0x00b3, code:
            r0 = th;
     */
    /* JADX WARNING: Missing block: B:57:0x00b8, code:
            r0 = th;
     */
    /* JADX WARNING: Missing block: B:58:0x00b9, code:
            r2 = r1;
     */
    /* JADX WARNING: Missing block: B:59:0x00bb, code:
            r0 = th;
     */
    /* JADX WARNING: Missing block: B:60:0x00bc, code:
            r2 = r1;
     */
    /* JADX WARNING: Missing block: B:65:0x00c4, code:
            r1 = r2;
     */
    public static java.lang.String l() {
        /*
        r0 = 0;
        r3 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x0095, all -> 0x00a2 }
        r3.<init>();	 Catch:{ Throwable -> 0x0095, all -> 0x00a2 }
        r1 = new java.io.File;	 Catch:{ Throwable -> 0x0095, all -> 0x00a2 }
        r2 = "/sys/block/mmcblk0/device/type";
        r1.<init>(r2);	 Catch:{ Throwable -> 0x0095, all -> 0x00a2 }
        r1 = r1.exists();	 Catch:{ Throwable -> 0x0095, all -> 0x00a2 }
        if (r1 == 0) goto L_0x00c8;
    L_0x0014:
        r1 = new java.io.BufferedReader;	 Catch:{ Throwable -> 0x0095, all -> 0x00a2 }
        r2 = new java.io.FileReader;	 Catch:{ Throwable -> 0x0095, all -> 0x00a2 }
        r4 = "/sys/block/mmcblk0/device/type";
        r2.<init>(r4);	 Catch:{ Throwable -> 0x0095, all -> 0x00a2 }
        r1.<init>(r2);	 Catch:{ Throwable -> 0x0095, all -> 0x00a2 }
        r2 = r1.readLine();	 Catch:{ Throwable -> 0x00be, all -> 0x00b0 }
        if (r2 == 0) goto L_0x002a;
    L_0x0027:
        r3.append(r2);	 Catch:{ Throwable -> 0x00be, all -> 0x00b0 }
    L_0x002a:
        r1.close();	 Catch:{ Throwable -> 0x00be, all -> 0x00b0 }
        r2 = r1;
    L_0x002e:
        r1 = ",";
        r3.append(r1);	 Catch:{ Throwable -> 0x00c0, all -> 0x00b3 }
        r1 = new java.io.File;	 Catch:{ Throwable -> 0x00c0, all -> 0x00b3 }
        r4 = "/sys/block/mmcblk0/device/name";
        r1.<init>(r4);	 Catch:{ Throwable -> 0x00c0, all -> 0x00b3 }
        r1 = r1.exists();	 Catch:{ Throwable -> 0x00c0, all -> 0x00b3 }
        if (r1 == 0) goto L_0x005c;
    L_0x0042:
        r1 = new java.io.BufferedReader;	 Catch:{ Throwable -> 0x00c0, all -> 0x00b3 }
        r4 = new java.io.FileReader;	 Catch:{ Throwable -> 0x00c0, all -> 0x00b3 }
        r5 = "/sys/block/mmcblk0/device/name";
        r4.<init>(r5);	 Catch:{ Throwable -> 0x00c0, all -> 0x00b3 }
        r1.<init>(r4);	 Catch:{ Throwable -> 0x00c0, all -> 0x00b3 }
        r2 = r1.readLine();	 Catch:{ Throwable -> 0x00be, all -> 0x00b5 }
        if (r2 == 0) goto L_0x0058;
    L_0x0055:
        r3.append(r2);	 Catch:{ Throwable -> 0x00be, all -> 0x00b5 }
    L_0x0058:
        r1.close();	 Catch:{ Throwable -> 0x00be, all -> 0x00b5 }
        r2 = r1;
    L_0x005c:
        r1 = ",";
        r3.append(r1);	 Catch:{ Throwable -> 0x00c3, all -> 0x00b3 }
        r1 = new java.io.File;	 Catch:{ Throwable -> 0x00c3, all -> 0x00b3 }
        r4 = "/sys/block/mmcblk0/device/cid";
        r1.<init>(r4);	 Catch:{ Throwable -> 0x00c3, all -> 0x00b3 }
        r1 = r1.exists();	 Catch:{ Throwable -> 0x00c3, all -> 0x00b3 }
        if (r1 == 0) goto L_0x00c6;
    L_0x0070:
        r1 = new java.io.BufferedReader;	 Catch:{ Throwable -> 0x00c3, all -> 0x00b3 }
        r4 = new java.io.FileReader;	 Catch:{ Throwable -> 0x00c3, all -> 0x00b3 }
        r5 = "/sys/block/mmcblk0/device/cid";
        r4.<init>(r5);	 Catch:{ Throwable -> 0x00c3, all -> 0x00b3 }
        r1.<init>(r4);	 Catch:{ Throwable -> 0x00c3, all -> 0x00b3 }
        r2 = r1.readLine();	 Catch:{ Throwable -> 0x00be, all -> 0x00b8 }
        if (r2 == 0) goto L_0x0086;
    L_0x0083:
        r3.append(r2);	 Catch:{ Throwable -> 0x00be, all -> 0x00b8 }
    L_0x0086:
        r0 = r3.toString();	 Catch:{ Throwable -> 0x00be, all -> 0x00bb }
        if (r1 == 0) goto L_0x008f;
    L_0x008c:
        r1.close();	 Catch:{ IOException -> 0x0090 }
    L_0x008f:
        return r0;
    L_0x0090:
        r1 = move-exception;
        com.tencent.bugly.imsdk.proguard.w.a(r1);
        goto L_0x008f;
    L_0x0095:
        r1 = move-exception;
        r1 = r0;
    L_0x0097:
        if (r1 == 0) goto L_0x008f;
    L_0x0099:
        r1.close();	 Catch:{ IOException -> 0x009d }
        goto L_0x008f;
    L_0x009d:
        r1 = move-exception;
        com.tencent.bugly.imsdk.proguard.w.a(r1);
        goto L_0x008f;
    L_0x00a2:
        r1 = move-exception;
        r2 = r0;
        r0 = r1;
    L_0x00a5:
        if (r2 == 0) goto L_0x00aa;
    L_0x00a7:
        r2.close();	 Catch:{ IOException -> 0x00ab }
    L_0x00aa:
        throw r0;
    L_0x00ab:
        r1 = move-exception;
        com.tencent.bugly.imsdk.proguard.w.a(r1);
        goto L_0x00aa;
    L_0x00b0:
        r0 = move-exception;
        r2 = r1;
        goto L_0x00a5;
    L_0x00b3:
        r0 = move-exception;
        goto L_0x00a5;
    L_0x00b5:
        r0 = move-exception;
        r2 = r1;
        goto L_0x00a5;
    L_0x00b8:
        r0 = move-exception;
        r2 = r1;
        goto L_0x00a5;
    L_0x00bb:
        r0 = move-exception;
        r2 = r1;
        goto L_0x00a5;
    L_0x00be:
        r2 = move-exception;
        goto L_0x0097;
    L_0x00c0:
        r1 = move-exception;
        r1 = r2;
        goto L_0x0097;
    L_0x00c3:
        r1 = move-exception;
        r1 = r2;
        goto L_0x0097;
    L_0x00c6:
        r1 = r2;
        goto L_0x0086;
    L_0x00c8:
        r2 = r0;
        goto L_0x002e;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.imsdk.crashreport.common.info.b.l():java.lang.String");
    }

    public static String i(Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        String a = y.a(context, "ro.genymotion.version");
        if (a != null) {
            stringBuilder.append("ro.genymotion.version");
            stringBuilder.append("|");
            stringBuilder.append(a);
            stringBuilder.append("\n");
        }
        a = y.a(context, "androVM.vbox_dpi");
        if (a != null) {
            stringBuilder.append("androVM.vbox_dpi");
            stringBuilder.append("|");
            stringBuilder.append(a);
            stringBuilder.append("\n");
        }
        a = y.a(context, "qemu.sf.fake_camera");
        if (a != null) {
            stringBuilder.append("qemu.sf.fake_camera");
            stringBuilder.append("|");
            stringBuilder.append(a);
        }
        return stringBuilder.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x00a1 A:{SYNTHETIC, Splitter: B:32:0x00a1} */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00b2 A:{SYNTHETIC, Splitter: B:40:0x00b2} */
    public static java.lang.String j(android.content.Context r5) {
        /*
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r0 = a;
        if (r0 != 0) goto L_0x0012;
    L_0x0009:
        r0 = "ro.secure";
        r0 = com.tencent.bugly.imsdk.proguard.y.a(r5, r0);
        a = r0;
    L_0x0012:
        r0 = a;
        if (r0 == 0) goto L_0x002d;
    L_0x0016:
        r0 = "ro.secure";
        r3.append(r0);
        r0 = "|";
        r3.append(r0);
        r0 = a;
        r3.append(r0);
        r0 = "\n";
        r3.append(r0);
    L_0x002d:
        r0 = b;
        if (r0 != 0) goto L_0x003a;
    L_0x0031:
        r0 = "ro.debuggable";
        r0 = com.tencent.bugly.imsdk.proguard.y.a(r5, r0);
        b = r0;
    L_0x003a:
        r0 = b;
        if (r0 == 0) goto L_0x0055;
    L_0x003e:
        r0 = "ro.debuggable";
        r3.append(r0);
        r0 = "|";
        r3.append(r0);
        r0 = b;
        r3.append(r0);
        r0 = "\n";
        r3.append(r0);
    L_0x0055:
        r2 = 0;
        r1 = new java.io.BufferedReader;	 Catch:{ Throwable -> 0x009a, all -> 0x00ae }
        r0 = new java.io.FileReader;	 Catch:{ Throwable -> 0x009a, all -> 0x00ae }
        r4 = "/proc/self/status";
        r0.<init>(r4);	 Catch:{ Throwable -> 0x009a, all -> 0x00ae }
        r1.<init>(r0);	 Catch:{ Throwable -> 0x009a, all -> 0x00ae }
    L_0x0063:
        r0 = r1.readLine();	 Catch:{ Throwable -> 0x00bd }
        if (r0 == 0) goto L_0x0072;
    L_0x0069:
        r2 = "TracerPid:";
        r2 = r0.startsWith(r2);	 Catch:{ Throwable -> 0x00bd }
        if (r2 == 0) goto L_0x0063;
    L_0x0072:
        if (r0 == 0) goto L_0x008d;
    L_0x0074:
        r2 = 10;
        r0 = r0.substring(r2);	 Catch:{ Throwable -> 0x00bd }
        r0 = r0.trim();	 Catch:{ Throwable -> 0x00bd }
        r2 = "tracer_pid";
        r3.append(r2);	 Catch:{ Throwable -> 0x00bd }
        r2 = "|";
        r3.append(r2);	 Catch:{ Throwable -> 0x00bd }
        r3.append(r0);	 Catch:{ Throwable -> 0x00bd }
    L_0x008d:
        r0 = r3.toString();	 Catch:{ Throwable -> 0x00bd }
        r1.close();	 Catch:{ IOException -> 0x0095 }
    L_0x0094:
        return r0;
    L_0x0095:
        r1 = move-exception;
        com.tencent.bugly.imsdk.proguard.w.a(r1);
        goto L_0x0094;
    L_0x009a:
        r0 = move-exception;
        r1 = r2;
    L_0x009c:
        com.tencent.bugly.imsdk.proguard.w.a(r0);	 Catch:{ all -> 0x00bb }
        if (r1 == 0) goto L_0x00a4;
    L_0x00a1:
        r1.close();	 Catch:{ IOException -> 0x00a9 }
    L_0x00a4:
        r0 = r3.toString();
        goto L_0x0094;
    L_0x00a9:
        r0 = move-exception;
        com.tencent.bugly.imsdk.proguard.w.a(r0);
        goto L_0x00a4;
    L_0x00ae:
        r0 = move-exception;
        r1 = r2;
    L_0x00b0:
        if (r1 == 0) goto L_0x00b5;
    L_0x00b2:
        r1.close();	 Catch:{ IOException -> 0x00b6 }
    L_0x00b5:
        throw r0;
    L_0x00b6:
        r1 = move-exception;
        com.tencent.bugly.imsdk.proguard.w.a(r1);
        goto L_0x00b5;
    L_0x00bb:
        r0 = move-exception;
        goto L_0x00b0;
    L_0x00bd:
        r0 = move-exception;
        goto L_0x009c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.imsdk.crashreport.common.info.b.j(android.content.Context):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:40:0x00c0 A:{SYNTHETIC, Splitter: B:40:0x00c0} */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00cc A:{SYNTHETIC, Splitter: B:46:0x00cc} */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00e4 A:{Splitter: B:5:0x0021, ExcHandler: all (th java.lang.Throwable), PHI: r0 } */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00cc A:{SYNTHETIC, Splitter: B:46:0x00cc} */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00c0 A:{SYNTHETIC, Splitter: B:40:0x00c0} */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00c9 A:{Splitter: B:11:0x003a, ExcHandler: all (th java.lang.Throwable), PHI: r1 } */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00e4 A:{Splitter: B:5:0x0021, ExcHandler: all (th java.lang.Throwable), PHI: r0 } */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00cc A:{SYNTHETIC, Splitter: B:46:0x00cc} */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00c0 A:{SYNTHETIC, Splitter: B:40:0x00c0} */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00c9 A:{Splitter: B:11:0x003a, ExcHandler: all (th java.lang.Throwable), PHI: r1 } */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:44:0x00c9, code:
            r0 = th;
     */
    /* JADX WARNING: Missing block: B:47:?, code:
            r1.close();
     */
    /* JADX WARNING: Missing block: B:49:0x00d0, code:
            r1 = move-exception;
     */
    /* JADX WARNING: Missing block: B:50:0x00d1, code:
            com.tencent.bugly.imsdk.proguard.w.a(r1);
     */
    /* JADX WARNING: Missing block: B:51:0x00d5, code:
            r1 = move-exception;
     */
    /* JADX WARNING: Missing block: B:52:0x00d6, code:
            r5 = r1;
            r1 = r0;
            r0 = r5;
     */
    /* JADX WARNING: Missing block: B:55:0x00df, code:
            r1 = move-exception;
     */
    /* JADX WARNING: Missing block: B:56:0x00e0, code:
            r5 = r1;
            r1 = r0;
            r0 = r5;
     */
    /* JADX WARNING: Missing block: B:61:0x00ea, code:
            r0 = r1;
     */
    public static java.lang.String m() {
        /*
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r1 = 0;
        r0 = new java.io.File;	 Catch:{ Throwable -> 0x00bc, all -> 0x00c9 }
        r3 = "/sys/class/power_supply/ac/online";
        r0.<init>(r3);	 Catch:{ Throwable -> 0x00bc, all -> 0x00c9 }
        r0 = r0.exists();	 Catch:{ Throwable -> 0x00bc, all -> 0x00c9 }
        if (r0 == 0) goto L_0x003a;
    L_0x0014:
        r0 = new java.io.BufferedReader;	 Catch:{ Throwable -> 0x00bc, all -> 0x00c9 }
        r3 = new java.io.FileReader;	 Catch:{ Throwable -> 0x00bc, all -> 0x00c9 }
        r4 = "/sys/class/power_supply/ac/online";
        r3.<init>(r4);	 Catch:{ Throwable -> 0x00bc, all -> 0x00c9 }
        r0.<init>(r3);	 Catch:{ Throwable -> 0x00bc, all -> 0x00c9 }
        r1 = r0.readLine();	 Catch:{ Throwable -> 0x00e4, all -> 0x00d5 }
        if (r1 == 0) goto L_0x0036;
    L_0x0027:
        r3 = "ac_online";
        r2.append(r3);	 Catch:{ Throwable -> 0x00e4, all -> 0x00d5 }
        r3 = "|";
        r2.append(r3);	 Catch:{ Throwable -> 0x00e4, all -> 0x00d5 }
        r2.append(r1);	 Catch:{ Throwable -> 0x00e4, all -> 0x00d5 }
    L_0x0036:
        r0.close();	 Catch:{ Throwable -> 0x00e4, all -> 0x00d5 }
        r1 = r0;
    L_0x003a:
        r0 = "\n";
        r2.append(r0);	 Catch:{ Throwable -> 0x00e6, all -> 0x00c9 }
        r0 = new java.io.File;	 Catch:{ Throwable -> 0x00e6, all -> 0x00c9 }
        r3 = "/sys/class/power_supply/usb/online";
        r0.<init>(r3);	 Catch:{ Throwable -> 0x00e6, all -> 0x00c9 }
        r0 = r0.exists();	 Catch:{ Throwable -> 0x00e6, all -> 0x00c9 }
        if (r0 == 0) goto L_0x0074;
    L_0x004e:
        r0 = new java.io.BufferedReader;	 Catch:{ Throwable -> 0x00e6, all -> 0x00c9 }
        r3 = new java.io.FileReader;	 Catch:{ Throwable -> 0x00e6, all -> 0x00c9 }
        r4 = "/sys/class/power_supply/usb/online";
        r3.<init>(r4);	 Catch:{ Throwable -> 0x00e6, all -> 0x00c9 }
        r0.<init>(r3);	 Catch:{ Throwable -> 0x00e6, all -> 0x00c9 }
        r1 = r0.readLine();	 Catch:{ Throwable -> 0x00e4, all -> 0x00da }
        if (r1 == 0) goto L_0x0070;
    L_0x0061:
        r3 = "usb_online";
        r2.append(r3);	 Catch:{ Throwable -> 0x00e4, all -> 0x00da }
        r3 = "|";
        r2.append(r3);	 Catch:{ Throwable -> 0x00e4, all -> 0x00da }
        r2.append(r1);	 Catch:{ Throwable -> 0x00e4, all -> 0x00da }
    L_0x0070:
        r0.close();	 Catch:{ Throwable -> 0x00e4, all -> 0x00da }
        r1 = r0;
    L_0x0074:
        r0 = "\n";
        r2.append(r0);	 Catch:{ Throwable -> 0x00e9, all -> 0x00c9 }
        r0 = new java.io.File;	 Catch:{ Throwable -> 0x00e9, all -> 0x00c9 }
        r3 = "/sys/class/power_supply/battery/capacity";
        r0.<init>(r3);	 Catch:{ Throwable -> 0x00e9, all -> 0x00c9 }
        r0 = r0.exists();	 Catch:{ Throwable -> 0x00e9, all -> 0x00c9 }
        if (r0 == 0) goto L_0x00ec;
    L_0x0088:
        r0 = new java.io.BufferedReader;	 Catch:{ Throwable -> 0x00e9, all -> 0x00c9 }
        r3 = new java.io.FileReader;	 Catch:{ Throwable -> 0x00e9, all -> 0x00c9 }
        r4 = "/sys/class/power_supply/battery/capacity";
        r3.<init>(r4);	 Catch:{ Throwable -> 0x00e9, all -> 0x00c9 }
        r0.<init>(r3);	 Catch:{ Throwable -> 0x00e9, all -> 0x00c9 }
        r1 = r0.readLine();	 Catch:{ Throwable -> 0x00e4, all -> 0x00df }
        if (r1 == 0) goto L_0x00aa;
    L_0x009b:
        r3 = "battery_capacity";
        r2.append(r3);	 Catch:{ Throwable -> 0x00e4, all -> 0x00df }
        r3 = "|";
        r2.append(r3);	 Catch:{ Throwable -> 0x00e4, all -> 0x00df }
        r2.append(r1);	 Catch:{ Throwable -> 0x00e4, all -> 0x00df }
    L_0x00aa:
        r0.close();	 Catch:{ Throwable -> 0x00e4, all -> 0x00df }
    L_0x00ad:
        if (r0 == 0) goto L_0x00b2;
    L_0x00af:
        r0.close();	 Catch:{ IOException -> 0x00b7 }
    L_0x00b2:
        r0 = r2.toString();
        return r0;
    L_0x00b7:
        r0 = move-exception;
        com.tencent.bugly.imsdk.proguard.w.a(r0);
        goto L_0x00b2;
    L_0x00bc:
        r0 = move-exception;
        r0 = r1;
    L_0x00be:
        if (r0 == 0) goto L_0x00b2;
    L_0x00c0:
        r0.close();	 Catch:{ IOException -> 0x00c4 }
        goto L_0x00b2;
    L_0x00c4:
        r0 = move-exception;
        com.tencent.bugly.imsdk.proguard.w.a(r0);
        goto L_0x00b2;
    L_0x00c9:
        r0 = move-exception;
    L_0x00ca:
        if (r1 == 0) goto L_0x00cf;
    L_0x00cc:
        r1.close();	 Catch:{ IOException -> 0x00d0 }
    L_0x00cf:
        throw r0;
    L_0x00d0:
        r1 = move-exception;
        com.tencent.bugly.imsdk.proguard.w.a(r1);
        goto L_0x00cf;
    L_0x00d5:
        r1 = move-exception;
        r5 = r1;
        r1 = r0;
        r0 = r5;
        goto L_0x00ca;
    L_0x00da:
        r1 = move-exception;
        r5 = r1;
        r1 = r0;
        r0 = r5;
        goto L_0x00ca;
    L_0x00df:
        r1 = move-exception;
        r5 = r1;
        r1 = r0;
        r0 = r5;
        goto L_0x00ca;
    L_0x00e4:
        r1 = move-exception;
        goto L_0x00be;
    L_0x00e6:
        r0 = move-exception;
        r0 = r1;
        goto L_0x00be;
    L_0x00e9:
        r0 = move-exception;
        r0 = r1;
        goto L_0x00be;
    L_0x00ec:
        r0 = r1;
        goto L_0x00ad;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.imsdk.crashreport.common.info.b.m():java.lang.String");
    }

    public static String k(Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        String a = y.a(context, "gsm.sim.state");
        if (a != null) {
            stringBuilder.append("gsm.sim.state");
            stringBuilder.append("|");
            stringBuilder.append(a);
        }
        stringBuilder.append("\n");
        a = y.a(context, "gsm.sim.state2");
        if (a != null) {
            stringBuilder.append("gsm.sim.state2");
            stringBuilder.append("|");
            stringBuilder.append(a);
        }
        return stringBuilder.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x004b A:{SYNTHETIC, Splitter: B:26:0x004b} */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x003e A:{SYNTHETIC, Splitter: B:19:0x003e} */
    public static long n() {
        /*
        r0 = 0;
        r3 = 0;
        r2 = new java.io.BufferedReader;	 Catch:{ Throwable -> 0x0037, all -> 0x0047 }
        r1 = new java.io.FileReader;	 Catch:{ Throwable -> 0x0037, all -> 0x0047 }
        r4 = "/proc/uptime";
        r1.<init>(r4);	 Catch:{ Throwable -> 0x0037, all -> 0x0047 }
        r2.<init>(r1);	 Catch:{ Throwable -> 0x0037, all -> 0x0047 }
        r1 = r2.readLine();	 Catch:{ Throwable -> 0x0056 }
        if (r1 == 0) goto L_0x002d;
    L_0x0015:
        r3 = " ";
        r1 = r1.split(r3);	 Catch:{ Throwable -> 0x0056 }
        r3 = 0;
        r1 = r1[r3];	 Catch:{ Throwable -> 0x0056 }
        r4 = java.lang.System.currentTimeMillis();	 Catch:{ Throwable -> 0x0056 }
        r6 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r4 = r4 / r6;
        r3 = (float) r4;	 Catch:{ Throwable -> 0x0056 }
        r0 = java.lang.Float.parseFloat(r1);	 Catch:{ Throwable -> 0x0056 }
        r0 = r3 - r0;
    L_0x002d:
        r2.close();	 Catch:{ IOException -> 0x0032 }
    L_0x0030:
        r0 = (long) r0;
        return r0;
    L_0x0032:
        r1 = move-exception;
        com.tencent.bugly.imsdk.proguard.w.a(r1);
        goto L_0x0030;
    L_0x0037:
        r1 = move-exception;
        r2 = r3;
    L_0x0039:
        com.tencent.bugly.imsdk.proguard.w.a(r1);	 Catch:{ all -> 0x0054 }
        if (r2 == 0) goto L_0x0030;
    L_0x003e:
        r2.close();	 Catch:{ IOException -> 0x0042 }
        goto L_0x0030;
    L_0x0042:
        r1 = move-exception;
        com.tencent.bugly.imsdk.proguard.w.a(r1);
        goto L_0x0030;
    L_0x0047:
        r0 = move-exception;
        r2 = r3;
    L_0x0049:
        if (r2 == 0) goto L_0x004e;
    L_0x004b:
        r2.close();	 Catch:{ IOException -> 0x004f }
    L_0x004e:
        throw r0;
    L_0x004f:
        r1 = move-exception;
        com.tencent.bugly.imsdk.proguard.w.a(r1);
        goto L_0x004e;
    L_0x0054:
        r0 = move-exception;
        goto L_0x0049;
    L_0x0056:
        r1 = move-exception;
        goto L_0x0039;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.imsdk.crashreport.common.info.b.n():long");
    }
}
