package com.meizu.cloud.pushsdk.util;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Process;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.base.BuildExt;
import com.meizu.cloud.pushsdk.base.DeviceUtils;
import com.meizu.cloud.pushsdk.base.SystemProperties;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.stub.StubApp;
import java.util.List;
import org.android.agoo.common.AgooConstants;

public class MzSystemUtils {
    private static final String TAG = "MzSystemUtils";

    private static String getServicesByPackageName(Context context, String packageName) {
        ServiceInfo[] serviceInfos = null;
        try {
            serviceInfos = context.getPackageManager().getPackageInfo(packageName, 4).services;
        } catch (NameNotFoundException e) {
        }
        if (serviceInfos == null) {
            return null;
        }
        for (int i = 0; i < serviceInfos.length; i++) {
            if ("com.meizu.cloud.pushsdk.pushservice.MzPushService".equals(serviceInfos[i].name)) {
                return serviceInfos[i].processName;
            }
        }
        return null;
    }

    public static String getMzPushServicePackageName(Context context) {
        String packageName = context.getPackageName();
        try {
            String mzPushserviceProcessName = getServicesByPackageName(context, "com.meizu.cloud");
            if (!TextUtils.isEmpty(mzPushserviceProcessName) && mzPushserviceProcessName.contains("mzservice_v1")) {
                return "com.meizu.cloud";
            }
        } catch (Exception var8) {
            var8.printStackTrace();
        }
        DebugLogger.i("SystemUtils", "startservice package name " + packageName);
        return packageName;
    }

    public static String getAppVersionName(Context context, String packageName) {
        String versionName = "";
        try {
            versionName = context.getPackageManager().getPackageInfo(packageName, 0).versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
            return versionName;
        } catch (Exception e) {
            DebugLogger.e("VersionInfo", "Exception message " + e.getMessage());
            return "";
        }
    }

    public static boolean compareVersion(String version1, String version2) {
        String[] versionArray1 = version1.split("\\.");
        String[] versionArray2 = version2.split("\\.");
        int minLength = Math.min(versionArray1.length, versionArray2.length);
        int diff = 0;
        for (int idx = 0; idx < minLength; idx++) {
            diff = versionArray1[idx].length() - versionArray2[idx].length();
            if (diff != 0) {
                break;
            }
            diff = versionArray1[idx].compareTo(versionArray2[idx]);
            if (diff != 0) {
                break;
            }
        }
        if (diff == 0) {
            diff = versionArray1.length - versionArray2.length;
        }
        return diff >= 0;
    }

    public static String findReceiver(Context paramContext, String action, String packageName) {
        if (TextUtils.isEmpty(action) || TextUtils.isEmpty(packageName)) {
            return null;
        }
        Intent localIntent = new Intent(action);
        localIntent.setPackage(packageName);
        List localList = paramContext.getPackageManager().queryBroadcastReceivers(localIntent, 0);
        if (localList == null || localList.size() <= 0) {
            return null;
        }
        return ((ResolveInfo) localList.get(0)).activityInfo.name;
    }

    public static String getDeviceId(Context context) {
        return DeviceUtils.getDeviceId(context);
    }

    public static boolean isBrandMeizu() {
        return !TextUtils.isEmpty(SystemProperties.get("ro.meizu.product.model")) || AgooConstants.MESSAGE_SYSTEM_SOURCE_MEIZU.equalsIgnoreCase(Build.BRAND) || "22c4185e".equalsIgnoreCase(Build.BRAND);
    }

    public static boolean isInternational() {
        return BuildExt.isInternational().ok ? ((Boolean) BuildExt.isInternational().value).booleanValue() : false;
    }

    public static boolean isIndiaLocal() {
        return "india".equals(SystemProperties.get("ro.meizu.locale.region"));
    }

    public static boolean isApplicationDebug(Context context) {
        try {
            return (context.getApplicationInfo().flags & 2) != 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getAppVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            return "";
        }
    }

    public static boolean compatApi(int apiLevel) {
        return VERSION.SDK_INT >= apiLevel;
    }

    public static String getSn() {
        String result = SystemProperties.get("ro.serialno");
        return !TextUtils.isEmpty(result) ? result : Build.SERIAL;
    }

    public static String getProcessName(Context context) {
        int pid = Process.myPid();
        String processName = "";
        for (RunningAppProcessInfo process : ((ActivityManager) StubApp.getOrigApplicationContext(context.getApplicationContext()).getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningAppProcesses()) {
            DebugLogger.i(TAG, "processName " + process.processName);
            if (process.pid == pid) {
                return process.processName;
            }
        }
        return processName;
    }

    public static boolean isPackageInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    public static boolean isRunningProcess(Context context, String packageName) {
        boolean running = false;
        try {
            for (RunningAppProcessInfo info : ((ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningAppProcesses()) {
                running = info.processName.contains(packageName);
                if (running) {
                    break;
                }
            }
            DebugLogger.i(TAG, packageName + " is running " + running);
            return running;
        } catch (Exception e) {
            DebugLogger.e(TAG, "can not get running process info so set running true");
            return true;
        }
    }
}
