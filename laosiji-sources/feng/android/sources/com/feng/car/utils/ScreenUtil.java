package com.feng.car.utils;

import android.os.Build;
import com.feng.car.FengApplication;

public class ScreenUtil {
    public static final int VIVO_FILLET = 8;
    public static final int VIVO_NOTCH = 32;

    public static boolean hasNotch() {
        if (getInt("ro.miui.notch") == 1 || hasNotchAtHuawei() || hasNotchAtOPPO() || hasNotchAtVivo()) {
            return true;
        }
        return false;
    }

    public static int getInt(String key) {
        int result = 0;
        if (!Build.MANUFACTURER.equals("Xiaomi")) {
            return result;
        }
        try {
            Class SystemProperties = FengApplication.getInstance().getClassLoader().loadClass("android.os.SystemProperties");
            return ((Integer) SystemProperties.getMethod("getInt", new Class[]{String.class, Integer.TYPE}).invoke(SystemProperties, new Object[]{new String(key), new Integer(0)})).intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
    }

    public static boolean hasNotchAtHuawei() {
        boolean ret = false;
        try {
            Class HwNotchSizeUtil = FengApplication.getInstance().getClassLoader().loadClass("com.huawei.android.util.HwNotchSizeUtil");
            return ((Boolean) HwNotchSizeUtil.getMethod("hasNotchInScreen", new Class[0]).invoke(HwNotchSizeUtil, new Object[0])).booleanValue();
        } catch (Exception e) {
            e.printStackTrace();
            return ret;
        }
    }

    public static boolean hasNotchAtVivo() {
        boolean ret = false;
        try {
            Class FtFeature = FengApplication.getInstance().getClassLoader().loadClass("android.util.FtFeature");
            return ((Boolean) FtFeature.getMethod("isFeatureSupport", new Class[]{Integer.TYPE}).invoke(FtFeature, new Object[]{Integer.valueOf(32)})).booleanValue();
        } catch (Exception e) {
            e.printStackTrace();
            return ret;
        }
    }

    public static boolean hasNotchAtOPPO() {
        return FengApplication.getInstance().getPackageManager().hasSystemFeature("com.oppo.feature.screen.heteromorphism");
    }
}
