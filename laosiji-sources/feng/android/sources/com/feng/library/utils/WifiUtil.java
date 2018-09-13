package com.feng.library.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.telephony.TelephonyManager;
import com.feng.car.utils.HttpConstant;

public class WifiUtil {
    public static boolean isConnectivity(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(HttpConstant.PHONE);
        if ((connectivityManager.getActiveNetworkInfo() == null || connectivityManager.getActiveNetworkInfo().getState() == null || connectivityManager.getActiveNetworkInfo().getState() != State.CONNECTED) && telephonyManager.getNetworkType() != 3) {
            return false;
        }
        return true;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivity == null) {
            return false;
        }
        NetworkInfo[] info = connectivity.getAllNetworkInfo();
        if (info == null) {
            return false;
        }
        for (NetworkInfo state : info) {
            if (state.getState() == State.CONNECTED) {
                return true;
            }
        }
        return false;
    }

    public static boolean isWifiConnectivity(Context context) {
        NetworkInfo activeNetInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetInfo == null || activeNetInfo.getType() != 1) {
            return false;
        }
        return true;
    }

    public static boolean isMobile(Context context) {
        NetworkInfo activeNetInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetInfo == null || activeNetInfo.getType() != 0) {
            return false;
        }
        return true;
    }
}
