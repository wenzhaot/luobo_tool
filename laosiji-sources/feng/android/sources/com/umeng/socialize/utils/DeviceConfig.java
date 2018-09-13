package com.umeng.socialize.utils;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build.VERSION;
import android.os.Environment;
import android.provider.Settings.Secure;
import com.umeng.message.MsgConstant;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;

public class DeviceConfig {
    protected static final String LOG_TAG = "DeviceConfig";
    private static final String MOBILE_NETWORK = "2G/3G";
    protected static final String UNKNOW = "Unknown";
    private static final String WIFI = "Wi-Fi";
    public static Context context;
    private static Object object = new Object();

    public static boolean isAppInstalled(String str, Context context) {
        boolean z = false;
        if (context != null) {
            synchronized (object) {
                try {
                    context.getPackageManager().getPackageInfo(str, 1);
                    z = true;
                } catch (NameNotFoundException e) {
                } catch (RuntimeException e2) {
                }
            }
        }
        return z;
    }

    public static String getAppVersion(String str, Context context) {
        if (context == null) {
            return "";
        }
        try {
            return context.getPackageManager().getPackageInfo(str, 0).versionName;
        } catch (Exception e) {
            return "";
        }
    }

    public static boolean checkPermission(Context context, String str) {
        return com.umeng.commonsdk.statistics.common.DeviceConfig.checkPermission(context, str);
    }

    public static String getDeviceId(Context context) {
        String str = "";
        return com.umeng.commonsdk.statistics.common.DeviceConfig.getDeviceId(context);
    }

    public static String getDeviceSN() {
        try {
            Class cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getMethod("get", new Class[]{String.class, String.class}).invoke(cls, new Object[]{"ro.serialno", "unknown"});
        } catch (Exception e) {
            return null;
        }
    }

    public static String[] getNetworkAccessMode(Context context) {
        return com.umeng.commonsdk.statistics.common.DeviceConfig.getNetworkAccessMode(context);
    }

    public static boolean isOnline(Context context) {
        return com.umeng.commonsdk.statistics.common.DeviceConfig.isOnline(context);
    }

    public static boolean isNetworkAvailable(Context context) {
        if (context != null && checkPermission(context, MsgConstant.PERMISSION_ACCESS_NETWORK_STATE) && isOnline(context)) {
            return true;
        }
        return false;
    }

    public static boolean isSdCardWrittenable() {
        if (Environment.getExternalStorageState().equals("mounted")) {
            return true;
        }
        return false;
    }

    public static String getAndroidID(Context context) {
        if (context == null) {
            return "";
        }
        return Secure.getString(context.getContentResolver(), SocializeProtocolConstants.PROTOCOL_KEY_ANDROID_ID);
    }

    public static String getOsVersion() {
        return VERSION.RELEASE;
    }

    public static String getMac(Context context) {
        return com.umeng.commonsdk.statistics.common.DeviceConfig.getMac(context);
    }

    public static String getPackageName(Context context) {
        if (context == null) {
            return "";
        }
        return context.getPackageName();
    }
}
