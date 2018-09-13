package com.talkingdata.sdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.feng.car.utils.HttpConstant;
import com.taobao.accs.utl.UtilityImpl;
import com.umeng.commonsdk.proguard.g;
import com.umeng.message.MsgConstant;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.io.File;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import org.json.JSONArray;

/* compiled from: td */
public class au {
    static TelephonyManager a = null;
    static String b = null;
    private static final String c = "pref.deviceid.key";
    private static final String d = "00:00:00:00:00:00";
    private static final Pattern e = Pattern.compile("^([0-9A-F]{2}:){5}([0-9A-F]{2})$");
    private static final Pattern f = Pattern.compile("[0-3][0-9a-f]{24,32}");
    private static final Pattern g = Pattern.compile("[0-3][0-9a-f]{32}");
    private static final String h = ".tcookieid";
    private static String i = null;
    private static boolean j = false;
    private static String k = null;

    public static void init(Context context) {
        try {
            a = (TelephonyManager) context.getSystemService(HttpConstant.PHONE);
        } catch (Throwable th) {
        }
    }

    public static synchronized String a(Context context) {
        String str;
        synchronized (au.class) {
            if (b == null) {
                b = l(context);
            }
            str = b;
        }
        return str;
    }

    public static String b(Context context) {
        try {
            return Secure.getString(context.getContentResolver(), SocializeProtocolConstants.PROTOCOL_KEY_ANDROID_ID);
        } catch (Throwable th) {
            return null;
        }
    }

    public static String c(Context context) {
        try {
            if (bo.a(23) && context.checkSelfPermission(MsgConstant.PERMISSION_READ_PHONE_STATE) != 0) {
                return null;
            }
            if (bo.b(context, MsgConstant.PERMISSION_READ_PHONE_STATE)) {
                String str;
                if (a == null) {
                    init(context);
                }
                JSONArray z = bd.z(context);
                if (z == null || z.length() != 2) {
                    str = null;
                } else {
                    try {
                        str = z.getJSONObject(1).getString("imei");
                    } catch (Exception e) {
                        str = null;
                    }
                }
                if (str == null) {
                    return a.getDeviceId();
                }
                return str;
            }
            return null;
        } catch (Throwable th) {
        }
    }

    public static String d(Context context) {
        try {
            if ((bo.a(23) && context.checkSelfPermission(MsgConstant.PERMISSION_READ_PHONE_STATE) != 0) || !bo.b(context, MsgConstant.PERMISSION_READ_PHONE_STATE)) {
                return null;
            }
            if (a == null) {
                init(context);
            }
            return a.getSimSerialNumber();
        } catch (Throwable th) {
            return null;
        }
    }

    public static String e(Context context) {
        try {
            if ((bo.a(23) && context.checkSelfPermission(MsgConstant.PERMISSION_READ_PHONE_STATE) != 0) || !bo.b(context, MsgConstant.PERMISSION_READ_PHONE_STATE)) {
                return null;
            }
            if (a == null) {
                init(context);
            }
            return a.getSubscriberId();
        } catch (Throwable th) {
            return null;
        }
    }

    public static String f(Context context) {
        String str;
        Throwable th;
        String str2 = null;
        if (!bo.b) {
            return null;
        }
        String str3 = "02:00:00:00:00:00";
        try {
            if (bo.a(23)) {
                try {
                    List<NetworkInterface> list = Collections.list(NetworkInterface.getNetworkInterfaces());
                    if (list == null || list.size() <= 0) {
                        return str3;
                    }
                    for (NetworkInterface networkInterface : list) {
                        if (networkInterface.getName().equalsIgnoreCase("wlan0")) {
                            byte[] hardwareAddress = networkInterface.getHardwareAddress();
                            if (hardwareAddress == null) {
                                return "";
                            }
                            StringBuilder stringBuilder = new StringBuilder();
                            int length = hardwareAddress.length;
                            for (int i = 0; i < length; i++) {
                                stringBuilder.append(String.format("%02X:", new Object[]{Byte.valueOf(hardwareAddress[i])}));
                            }
                            if (stringBuilder.length() > 0) {
                                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                            }
                            str2 = stringBuilder.toString().toUpperCase().trim();
                        }
                    }
                    str = str2;
                    try {
                        if (bo.b(str)) {
                            return str3;
                        }
                        return str;
                    } catch (Throwable th2) {
                        th = th2;
                    }
                } catch (Throwable th3) {
                    str = null;
                }
            } else {
                if (bo.b(context, MsgConstant.PERMISSION_ACCESS_WIFI_STATE)) {
                    WifiManager wifiManager = (WifiManager) context.getSystemService(UtilityImpl.NET_TYPE_WIFI);
                    if (wifiManager.isWifiEnabled()) {
                        WifiInfo connectionInfo = wifiManager.getConnectionInfo();
                        if (connectionInfo != null) {
                            str = connectionInfo.getMacAddress();
                            if (str == null) {
                                return str;
                            }
                            str = str.toUpperCase().trim();
                            if (d.equals(str) || !e.matcher(str).matches()) {
                                return null;
                            }
                            return str;
                        }
                    }
                }
                return null;
            }
        } catch (Throwable th4) {
            th = th4;
            str = null;
        }
        cs.postSDKError(th);
        return str;
    }

    public static final String g(Context context) {
        try {
            if (!j) {
                bl.a.execute(new av(context));
            }
            return i;
        } catch (Throwable th) {
            cs.postSDKError(th);
            return null;
        }
    }

    public static final String h(Context context) {
        try {
            if (bo.a(9) && bo.b(26)) {
                return Build.SERIAL;
            }
            if (bo.a(26) && bo.b(context, MsgConstant.PERMISSION_READ_PHONE_STATE)) {
                return Build.getSerial();
            }
            return null;
        } catch (Throwable th) {
        }
    }

    public static final String i(Context context) {
        try {
            String f = f(context);
            if (!TextUtils.isEmpty(f)) {
                f = String.valueOf(Long.parseLong(f.replaceAll(":", ""), 16));
            }
            String b = b(context);
            String c = c(context);
            String e = e(context);
            String d = d(context);
            String a = a(context);
            String g = g(context);
            return 2 + "|" + f + "|" + b + "|" + c + "|" + e + "|" + d + "|" + a + "|" + g + "|" + h(context);
        } catch (Throwable th) {
            return null;
        }
    }

    private static String l(Context context) {
        int length;
        int i = 0;
        String j = j(context);
        String a = a();
        boolean b = b();
        String[] strArr = new String[]{j, a, a(context, b)};
        for (String str : strArr) {
            if (!bo.b(str) && g.matcher(str).matches()) {
                break;
            }
        }
        String str2 = null;
        if (bo.b(str2) && !bo.b(j) && Math.random() < 0.99d) {
            length = strArr.length;
            while (i < length) {
                String str3 = strArr[i];
                if (!bo.b(str3) && f.matcher(str3).matches()) {
                    str2 = str3;
                    break;
                }
                i++;
            }
        }
        if (bo.b(str2)) {
            str2 = m(context);
        }
        if (!str2.equals(j)) {
            b(context, str2);
        }
        if (!str2.equals(r7)) {
            a(context, str2, b);
        }
        if (!str2.equals(a)) {
            a(context, str2);
        }
        return str2;
    }

    static String a(Context context, boolean z) {
        if (bo.a(23) && context.checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") != 0) {
            return null;
        }
        if (!"mounted".equals(Environment.getExternalStorageState())) {
            return "";
        }
        String str;
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        if (z) {
            str = h;
        } else {
            str = h + k(context);
        }
        str = a(new File(externalStorageDirectory, str));
        if (bo.b(str)) {
            return a(new File(Environment.getExternalStorageDirectory(), ".tid" + k(context)));
        }
        return str;
    }

    static String a() {
        String str = null;
        try {
            File[] listFiles = new File("/").listFiles();
            if (listFiles != null && listFiles.length != 0) {
                loop0:
                for (File file : listFiles) {
                    if (file.isDirectory() && !"/sdcard".equals(file.getAbsolutePath())) {
                        if (file.canWrite()) {
                            str = a(new File(file, h));
                            if (!bo.b(str)) {
                                break;
                            }
                        }
                        if (file.listFiles() != null) {
                            for (File file2 : file.listFiles()) {
                                if (file2.isDirectory()) {
                                    str = a(new File(file2, h));
                                    if (!bo.b(str)) {
                                        break loop0;
                                    }
                                }
                            }
                            continue;
                        } else {
                            continue;
                        }
                    }
                }
            }
        } catch (Throwable th) {
        }
        return str;
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x002a A:{SYNTHETIC, Splitter: B:17:0x002a} */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0033 A:{SYNTHETIC, Splitter: B:23:0x0033} */
    private static java.lang.String a(java.io.File r6) {
        /*
        r1 = 0;
        r0 = r6.exists();	 Catch:{ Throwable -> 0x0038 }
        if (r0 == 0) goto L_0x0039;
    L_0x0007:
        r0 = r6.canRead();	 Catch:{ Throwable -> 0x0038 }
        if (r0 == 0) goto L_0x0039;
    L_0x000d:
        r2 = new java.io.FileInputStream;	 Catch:{ Throwable -> 0x0026, all -> 0x002f }
        r2.<init>(r6);	 Catch:{ Throwable -> 0x0026, all -> 0x002f }
        r0 = 128; // 0x80 float:1.794E-43 double:6.32E-322;
        r3 = new byte[r0];	 Catch:{ Throwable -> 0x0043, all -> 0x0041 }
        r4 = r2.read(r3);	 Catch:{ Throwable -> 0x0043, all -> 0x0041 }
        r0 = new java.lang.String;	 Catch:{ Throwable -> 0x0043, all -> 0x0041 }
        r5 = 0;
        r0.<init>(r3, r5, r4);	 Catch:{ Throwable -> 0x0043, all -> 0x0041 }
        if (r2 == 0) goto L_0x0025;
    L_0x0022:
        r2.close();	 Catch:{ Throwable -> 0x003b }
    L_0x0025:
        return r0;
    L_0x0026:
        r0 = move-exception;
        r0 = r1;
    L_0x0028:
        if (r0 == 0) goto L_0x002d;
    L_0x002a:
        r0.close();	 Catch:{ Throwable -> 0x003d }
    L_0x002d:
        r0 = r1;
        goto L_0x0025;
    L_0x002f:
        r0 = move-exception;
        r2 = r1;
    L_0x0031:
        if (r2 == 0) goto L_0x0036;
    L_0x0033:
        r2.close();	 Catch:{ Throwable -> 0x003f }
    L_0x0036:
        r0 = r1;
        goto L_0x0025;
    L_0x0038:
        r0 = move-exception;
    L_0x0039:
        r0 = r1;
        goto L_0x0025;
    L_0x003b:
        r1 = move-exception;
        goto L_0x0025;
    L_0x003d:
        r0 = move-exception;
        goto L_0x002d;
    L_0x003f:
        r0 = move-exception;
        goto L_0x0036;
    L_0x0041:
        r0 = move-exception;
        goto L_0x0031;
    L_0x0043:
        r0 = move-exception;
        r0 = r2;
        goto L_0x0028;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.talkingdata.sdk.au.a(java.io.File):java.lang.String");
    }

    static String j(Context context) {
        try {
            String b = bi.b(context, "tdid", c, null);
            if (bo.b(b)) {
                return PreferenceManager.getDefaultSharedPreferences(context).getString(c, null);
            }
            return b;
        } catch (Throwable th) {
            return "";
        }
    }

    private static void a(Context context, String str, boolean z) {
        try {
            String str2;
            File externalStorageDirectory = Environment.getExternalStorageDirectory();
            if (z) {
                str2 = h;
            } else {
                str2 = h + k(context);
            }
            a(new File(externalStorageDirectory, str2), str);
        } catch (Throwable th) {
        }
    }

    private static void a(Context context, String str) {
        try {
            File[] listFiles = new File("/").listFiles();
            if (listFiles != null && listFiles.length != 0) {
                for (File file : listFiles) {
                    if (file.isDirectory() && !"/sdcard".equals(file.getAbsolutePath())) {
                        if (file.canWrite() && !new File(file, h + k(context)).exists()) {
                            a(new File(file, h), str);
                        }
                        if (file.listFiles() != null) {
                            for (File file2 : file.listFiles()) {
                                if (file2.isDirectory() && file2.canWrite() && !new File(file2, h + k(context)).exists()) {
                                    a(new File(file2, h), str);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Throwable th) {
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0075 A:{SYNTHETIC, Splitter: B:19:0x0075} */
    private static void a(java.io.File r7, java.lang.String r8) {
        /*
        r1 = 0;
        r0 = new java.io.FileOutputStream;	 Catch:{ Throwable -> 0x0082, all -> 0x0072 }
        r0.<init>(r7);	 Catch:{ Throwable -> 0x0082, all -> 0x0072 }
        r1 = r8.getBytes();	 Catch:{ Throwable -> 0x0069, all -> 0x007d }
        r0.write(r1);	 Catch:{ Throwable -> 0x0069, all -> 0x007d }
        r1 = 9;
        r1 = com.talkingdata.sdk.bo.a(r1);	 Catch:{ Throwable -> 0x0069, all -> 0x007d }
        if (r1 == 0) goto L_0x0049;
    L_0x0015:
        r1 = r7.getClass();	 Catch:{ Throwable -> 0x0069, all -> 0x007d }
        r2 = "setReadable";
        r3 = 2;
        r3 = new java.lang.Class[r3];	 Catch:{ Throwable -> 0x0069, all -> 0x007d }
        r4 = 0;
        r5 = java.lang.Boolean.TYPE;	 Catch:{ Throwable -> 0x0069, all -> 0x007d }
        r3[r4] = r5;	 Catch:{ Throwable -> 0x0069, all -> 0x007d }
        r4 = 1;
        r5 = java.lang.Boolean.TYPE;	 Catch:{ Throwable -> 0x0069, all -> 0x007d }
        r3[r4] = r5;	 Catch:{ Throwable -> 0x0069, all -> 0x007d }
        r1 = r1.getMethod(r2, r3);	 Catch:{ Throwable -> 0x0069, all -> 0x007d }
        r2 = 2;
        r2 = new java.lang.Object[r2];	 Catch:{ Throwable -> 0x0069, all -> 0x007d }
        r3 = 0;
        r4 = 1;
        r4 = java.lang.Boolean.valueOf(r4);	 Catch:{ Throwable -> 0x0069, all -> 0x007d }
        r2[r3] = r4;	 Catch:{ Throwable -> 0x0069, all -> 0x007d }
        r3 = 1;
        r4 = 0;
        r4 = java.lang.Boolean.valueOf(r4);	 Catch:{ Throwable -> 0x0069, all -> 0x007d }
        r2[r3] = r4;	 Catch:{ Throwable -> 0x0069, all -> 0x007d }
        r1.invoke(r7, r2);	 Catch:{ Throwable -> 0x0069, all -> 0x007d }
    L_0x0043:
        if (r0 == 0) goto L_0x0048;
    L_0x0045:
        r0.close();	 Catch:{ Throwable -> 0x0079 }
    L_0x0048:
        return;
    L_0x0049:
        r1 = java.lang.Runtime.getRuntime();	 Catch:{ Throwable -> 0x0069, all -> 0x007d }
        r2 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x0069, all -> 0x007d }
        r2.<init>();	 Catch:{ Throwable -> 0x0069, all -> 0x007d }
        r3 = "chmod 444 ";
        r2 = r2.append(r3);	 Catch:{ Throwable -> 0x0069, all -> 0x007d }
        r3 = r7.getAbsolutePath();	 Catch:{ Throwable -> 0x0069, all -> 0x007d }
        r2 = r2.append(r3);	 Catch:{ Throwable -> 0x0069, all -> 0x007d }
        r2 = r2.toString();	 Catch:{ Throwable -> 0x0069, all -> 0x007d }
        r1.exec(r2);	 Catch:{ Throwable -> 0x0069, all -> 0x007d }
        goto L_0x0043;
    L_0x0069:
        r1 = move-exception;
    L_0x006a:
        if (r0 == 0) goto L_0x0048;
    L_0x006c:
        r0.close();	 Catch:{ Throwable -> 0x0070 }
        goto L_0x0048;
    L_0x0070:
        r0 = move-exception;
        goto L_0x0048;
    L_0x0072:
        r0 = move-exception;
    L_0x0073:
        if (r1 == 0) goto L_0x0078;
    L_0x0075:
        r1.close();	 Catch:{ Throwable -> 0x007b }
    L_0x0078:
        throw r0;
    L_0x0079:
        r0 = move-exception;
        goto L_0x0048;
    L_0x007b:
        r1 = move-exception;
        goto L_0x0078;
    L_0x007d:
        r1 = move-exception;
        r6 = r1;
        r1 = r0;
        r0 = r6;
        goto L_0x0073;
    L_0x0082:
        r0 = move-exception;
        r0 = r1;
        goto L_0x006a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.talkingdata.sdk.au.a(java.io.File, java.lang.String):void");
    }

    private static void b(Context context, String str) {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences("tdid", 0);
            if (sharedPreferences != null) {
                Editor edit = sharedPreferences.edit();
                edit.putString(c, str);
                edit.commit();
            }
        } catch (Throwable th) {
        }
    }

    private static String m(Context context) {
        return "3" + bo.c(n(context));
    }

    private static String n(Context context) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(c(context)).append('-').append(f(context)).append('-').append(b(context));
            return stringBuilder.toString();
        } catch (Throwable th) {
            return "";
        }
    }

    static boolean b() {
        boolean booleanValue;
        try {
            if (bo.a(9)) {
                booleanValue = ((Boolean) Environment.class.getMethod("isExternalStorageRemovable", new Class[0]).invoke(null, new Object[0])).booleanValue();
            } else {
                booleanValue = true;
            }
        } catch (Throwable th) {
            booleanValue = true;
        }
        if (booleanValue) {
            return false;
        }
        return true;
    }

    static String k(Context context) {
        if (k == null) {
            try {
                Sensor[] sensorArr = new Sensor[64];
                for (Sensor sensor : ((SensorManager) context.getSystemService(g.aa)).getSensorList(-1)) {
                    if (sensor.getType() < sensorArr.length && sensor.getType() >= 0) {
                        sensorArr[sensor.getType()] = sensor;
                    }
                }
                StringBuffer stringBuffer = new StringBuffer();
                for (int i = 0; i < sensorArr.length; i++) {
                    if (sensorArr[i] != null) {
                        stringBuffer.append(i).append('.').append(sensorArr[i].getVendor()).append('-').append(sensorArr[i].getName()).append('-').append(sensorArr[i].getVersion()).append(10);
                    }
                }
                k = String.valueOf(stringBuffer.toString().hashCode());
            } catch (Throwable th) {
            }
        }
        return k;
    }
}
