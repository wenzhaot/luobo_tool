package com.umeng.commonsdk.internal.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.StatFs;
import android.os.SystemClock;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import com.feng.car.utils.HttpConstant;
import com.feng.car.video.shortvideo.FileUtils;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.pushtracer.constant.Parameters;
import com.taobao.accs.utl.UtilityImpl;
import com.umeng.commonsdk.framework.UMWorkDispatch;
import com.umeng.commonsdk.statistics.common.DeviceConfig;
import com.umeng.commonsdk.statistics.common.HelperUtils;
import com.umeng.commonsdk.statistics.common.e;
import com.umeng.message.MsgConstant;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: ApplicationLayerUtil */
public class a {

    /* compiled from: ApplicationLayerUtil */
    public static class a {
        public String a;
        public String b;
    }

    /* compiled from: ApplicationLayerUtil */
    public static class b {
        public String a = null;
        public int b = -1;
        public String c = null;
    }

    /* compiled from: ApplicationLayerUtil */
    public static class c {
        public int a;
        public String b;
        public String c;
        public int d;
        public int e;
        public int f;
        public int g;
        public String h;
        public int i;
        public int j;
        public int k;
        public long l;
    }

    public static long a(Context context, String str) {
        long j = 0;
        if (context == null) {
            return j;
        }
        try {
            return context.getPackageManager().getPackageInfo(str, 0).firstInstallTime;
        } catch (Throwable e) {
            com.umeng.commonsdk.proguard.b.a(context, e);
            if (e == null) {
                return j;
            }
            e.e("getAppFirstInstallTime" + e.getMessage());
            return j;
        }
    }

    public static long b(Context context, String str) {
        long j = 0;
        if (context == null) {
            return j;
        }
        try {
            return context.getPackageManager().getPackageInfo(str, 0).lastUpdateTime;
        } catch (Throwable e) {
            com.umeng.commonsdk.proguard.b.a(context, e);
            if (e == null) {
                return j;
            }
            e.e("getAppLastUpdateTime:" + e.getMessage());
            return j;
        }
    }

    public static String c(Context context, String str) {
        String str2 = null;
        try {
            return context.getPackageManager().getInstallerPackageName(str);
        } catch (Throwable e) {
            com.umeng.commonsdk.proguard.b.a(context, e);
            if (e == null) {
                return str2;
            }
            e.e("getAppInstaller:" + e.getMessage());
            return str2;
        }
    }

    public static int d(Context context, String str) {
        if (context == null) {
            return 0;
        }
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getPackageInfo(str, 0).applicationInfo;
            if (applicationInfo != null) {
                return applicationInfo.uid;
            }
            return 0;
        } catch (Throwable e) {
            com.umeng.commonsdk.proguard.b.a(context, e);
            if (e == null) {
                return 0;
            }
            e.e("getAppUid:" + e.getMessage());
            return 0;
        }
    }

    public static boolean a() {
        return h.a();
    }

    public static String b() {
        return new SimpleDateFormat().format(new Date());
    }

    public static float a(Context context) {
        if (context == null) {
            return 0.0f;
        }
        Configuration configuration = new Configuration();
        try {
            configuration.updateFrom(context.getResources().getConfiguration());
            if (configuration != null) {
                return configuration.fontScale;
            }
            return 0.0f;
        } catch (Exception e) {
            if (e == null) {
                return 0.0f;
            }
            e.e("getFontSize:" + e.getMessage());
            return 0.0f;
        }
    }

    public static List<ScanResult> b(Context context) {
        if (context == null) {
            return null;
        }
        WifiManager wifiManager = (WifiManager) context.getSystemService(UtilityImpl.NET_TYPE_WIFI);
        if (wifiManager == null) {
            return null;
        }
        List<ScanResult> scanResults;
        if (DeviceConfig.checkPermission(context, MsgConstant.PERMISSION_ACCESS_WIFI_STATE)) {
            scanResults = wifiManager.getScanResults();
            if (scanResults == null || scanResults.size() == 0) {
                return scanResults;
            }
        }
        scanResults = null;
        return scanResults;
    }

    public static WifiInfo c(Context context) {
        if (context == null) {
            return null;
        }
        WifiInfo connectionInfo;
        if (DeviceConfig.checkPermission(context, MsgConstant.PERMISSION_ACCESS_WIFI_STATE)) {
            WifiManager wifiManager = (WifiManager) context.getSystemService(UtilityImpl.NET_TYPE_WIFI);
            if (wifiManager != null) {
                connectionInfo = wifiManager.getConnectionInfo();
                return connectionInfo;
            }
        }
        connectionInfo = null;
        return connectionInfo;
    }

    public static void d(Context context) {
        int i = 1;
        if (context != null) {
            WifiInfo c = c(context);
            if (c != null) {
                c cVar = new c();
                cVar.a = c.describeContents();
                cVar.b = c.getBSSID();
                cVar.c = c.getSSID();
                if (VERSION.SDK_INT >= 21) {
                    cVar.d = c.getFrequency();
                } else {
                    cVar.d = -1;
                }
                if (c.getHiddenSSID()) {
                    cVar.e = 1;
                } else {
                    cVar.e = 0;
                }
                cVar.f = c.getIpAddress();
                cVar.g = c.getLinkSpeed();
                cVar.h = DeviceConfig.getMac(context);
                cVar.i = c.getNetworkId();
                cVar.j = c.getRssi();
                cVar.k = g(context);
                cVar.l = System.currentTimeMillis();
                if (c != null) {
                    try {
                        JSONArray b = f.b(context);
                        if (b != null && b.length() > 0) {
                            for (int i2 = 0; i2 < b.length(); i2++) {
                                String optString = b.optJSONObject(i2).optString("ssid", null);
                                if (optString != null && optString.equals(cVar.c)) {
                                    break;
                                }
                            }
                        }
                        i = 0;
                        if (i == 0) {
                            f.a(context, cVar);
                        }
                    } catch (Exception e) {
                        if (e != null) {
                            e.e("wifiChange:" + e.getMessage());
                        }
                    }
                }
            }
        }
    }

    public static JSONArray e(Context context) {
        if (context == null) {
            return null;
        }
        return f.b(context);
    }

    public static void f(Context context) {
        if (context != null) {
            f.c(context);
        }
    }

    public static int g(Context context) {
        if (context == null) {
            return -1;
        }
        int wifiState;
        if (DeviceConfig.checkPermission(context, MsgConstant.PERMISSION_ACCESS_WIFI_STATE)) {
            WifiManager wifiManager = (WifiManager) context.getSystemService(UtilityImpl.NET_TYPE_WIFI);
            if (wifiManager != null) {
                wifiState = wifiManager.getWifiState();
                return wifiState;
            }
        }
        wifiState = -1;
        return wifiState;
    }

    public static int h(Context context) {
        if (context == null) {
            return 0;
        }
        Resources resources = context.getResources();
        return resources.getDimensionPixelSize(resources.getIdentifier("status_bar_height", "dimen", "android"));
    }

    public static int i(Context context) {
        if (context == null) {
            return 0;
        }
        Resources resources = context.getResources();
        return resources.getDimensionPixelSize(resources.getIdentifier("navigation_bar_height", "dimen", "android"));
    }

    public static DisplayMetrics j(Context context) {
        if (context == null) {
            return null;
        }
        return context.getResources().getDisplayMetrics();
    }

    private static boolean j() {
        String externalStorageState = Environment.getExternalStorageState();
        if ("mounted".equals(externalStorageState) || "mounted_ro".equals(externalStorageState)) {
            return true;
        }
        return false;
    }

    public static long c() {
        if (!j() || VERSION.SDK_INT < 9) {
            return 0;
        }
        return Environment.getExternalStorageDirectory().getFreeSpace();
    }

    public static long d() {
        if (!j() || VERSION.SDK_INT < 9) {
            return 0;
        }
        return Environment.getExternalStorageDirectory().getTotalSpace();
    }

    public static String e() {
        return g.a(Parameters.DEVICE_MANUFACTURER);
    }

    public static String k(Context context) {
        if (context == null) {
            return null;
        }
        String subscriberId;
        if (DeviceConfig.checkPermission(context, MsgConstant.PERMISSION_READ_PHONE_STATE)) {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(HttpConstant.PHONE);
            if (telephonyManager != null) {
                subscriberId = telephonyManager.getSubscriberId();
                return subscriberId;
            }
        }
        subscriberId = null;
        return subscriberId;
    }

    public static String l(Context context) {
        String str = null;
        if (context == null) {
            return str;
        }
        String str2;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(HttpConstant.PHONE);
        if (!DeviceConfig.checkPermission(context, MsgConstant.PERMISSION_READ_PHONE_STATE) || telephonyManager == null) {
            str2 = str;
        } else if (VERSION.SDK_INT < 26) {
            str2 = telephonyManager.getDeviceId();
        } else {
            try {
                CharSequence t = t(context);
                CharSequence str22;
                try {
                    if (TextUtils.isEmpty(t)) {
                        str22 = telephonyManager.getDeviceId();
                    } else {
                        str22 = t;
                    }
                } catch (Throwable th) {
                    str22 = t;
                }
            } catch (Throwable th2) {
                str22 = str;
            }
        }
        return str22;
    }

    private static String t(Context context) {
        if (context == null) {
            return null;
        }
        try {
            String str;
            Object invoke = Class.forName("android.telephony.TelephonyManager").getMethod("getMeid", new Class[0]).invoke(null, new Object[0]);
            if (invoke == null || !(invoke instanceof String)) {
                str = null;
            } else {
                str = (String) invoke;
            }
            return str;
        } catch (Exception e) {
            if (e == null) {
                return null;
            }
            e.e("meid:" + e.getMessage());
            return null;
        }
    }

    public static List<InputMethodInfo> m(Context context) {
        if (context == null) {
            return null;
        }
        return ((InputMethodManager) context.getSystemService("input_method")).getInputMethodList();
    }

    public static void n(Context context) {
        if (context != null) {
            try {
                BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
                b bVar = new b();
                if (DeviceConfig.checkPermission(context, "android.permission.BLUETOOTH") && defaultAdapter.isEnabled()) {
                    bVar.b = defaultAdapter.getState();
                    if (VERSION.SDK_INT < 23) {
                        bVar.a = defaultAdapter.getAddress();
                    } else {
                        bVar.a = a(defaultAdapter);
                    }
                    bVar.c = defaultAdapter.getName();
                    UMWorkDispatch.sendEvent(context, com.umeng.commonsdk.internal.a.i, com.umeng.commonsdk.internal.b.a(context).a(), bVar);
                }
            } catch (Exception e) {
                if (e != null) {
                    e.e("startBluethInfo:" + e.getMessage());
                }
            }
        }
    }

    public static JSONObject o(Context context) {
        if (context == null) {
            return null;
        }
        return f.a(context);
    }

    private static String a(BluetoothAdapter bluetoothAdapter) {
        if (bluetoothAdapter == null) {
            return null;
        }
        Class cls = bluetoothAdapter.getClass();
        try {
            Class cls2 = Class.forName("android.bluetooth.IBluetooth");
            Field declaredField = cls.getDeclaredField("mService");
            declaredField.setAccessible(true);
            Method method = cls2.getMethod("getAddress", new Class[0]);
            method.setAccessible(true);
            return (String) method.invoke(declaredField.get(bluetoothAdapter), new Object[0]);
        } catch (Exception e) {
            return bluetoothAdapter.getAddress();
        }
    }

    public static List<a> p(Context context) {
        if (context == null) {
            return null;
        }
        List<a> arrayList = new ArrayList();
        try {
            File file = new File(Environment.getExternalStorageDirectory() + "/Android/data/");
            if (file == null || !file.isDirectory()) {
                return arrayList;
            }
            String[] list = file.list();
            if (list == null || list.length <= 0) {
                return arrayList;
            }
            for (String str : list) {
                if (!(str == null || str.startsWith(FileUtils.FILE_EXTENSION_SEPARATOR))) {
                    a aVar = new a();
                    aVar.a = str;
                    aVar.b = e(context, str);
                    arrayList.add(aVar);
                }
            }
            return arrayList;
        } catch (Exception e) {
            if (e == null) {
                return arrayList;
            }
            e.e("getAppList:" + e.getMessage());
            return arrayList;
        }
    }

    private static String e(Context context, String str) {
        if (context == null) {
            return null;
        }
        try {
            String str2;
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(str, 128);
            if (applicationInfo != null) {
                str2 = (String) applicationInfo.loadLabel(context.getPackageManager());
            } else {
                str2 = null;
            }
            return str2;
        } catch (Exception e) {
            if (e == null) {
                return null;
            }
            e.e("getLabel:" + e.getMessage());
            return null;
        }
    }

    public static MemoryInfo q(Context context) {
        if (context == null) {
            return null;
        }
        ActivityManager activityManager = (ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME);
        MemoryInfo memoryInfo = new MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        return memoryInfo;
    }

    public static long f() {
        return System.currentTimeMillis() - SystemClock.elapsedRealtime();
    }

    public static String r(Context context) {
        if (context == null) {
        }
        return null;
    }

    public static String s(Context context) {
        return null;
    }

    public static String g() {
        Exception e;
        String obj;
        try {
            Method declaredMethod = Build.class.getDeclaredMethod("getString", new Class[]{String.class});
            declaredMethod.setAccessible(true);
            obj = declaredMethod.invoke(null, new Object[]{"net.hostname"}).toString();
            if (obj == null) {
                return obj;
            }
            try {
                if (obj.equalsIgnoreCase("")) {
                    return obj;
                }
                return HelperUtils.getUmengMD5(obj);
            } catch (Exception e2) {
                e = e2;
                if (e == null) {
                    return obj;
                }
                e.e("getHostName:" + e.getMessage());
                return obj;
            }
        } catch (Exception e3) {
            Exception exception = e3;
            obj = null;
            e = exception;
        }
    }

    public static long h() {
        long j = 0;
        try {
            StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
            return ((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize());
        } catch (Exception e) {
            return j;
        }
    }

    public static long i() {
        long j = 0;
        try {
            StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
            return ((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize());
        } catch (Exception e) {
            return j;
        }
    }
}
