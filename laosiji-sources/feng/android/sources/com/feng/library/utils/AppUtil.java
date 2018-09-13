package com.feng.library.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.view.inputmethod.InputMethodManager;

public class AppUtil {
    public static boolean isMobile(Context context) {
        NetworkInfo activeNetInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetInfo == null || activeNetInfo.getType() != 0) {
            return false;
        }
        return true;
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        Resources mResources;
        if (context == null) {
            mResources = Resources.getSystem();
        } else {
            mResources = context.getResources();
        }
        return mResources.getDisplayMetrics();
    }

    public static void showSoftInput(Context context) {
        ((InputMethodManager) context.getSystemService("input_method")).toggleSoftInput(0, 2);
    }

    public static PackageInfo getPackageInfo(Context context) {
        PackageInfo info = null;
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 1);
        } catch (Exception e) {
            e.printStackTrace();
            return info;
        }
    }
}
