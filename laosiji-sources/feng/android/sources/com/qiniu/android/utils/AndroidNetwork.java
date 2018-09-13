package com.qiniu.android.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public final class AndroidNetwork {
    public static boolean isNetWorkReady() {
        Context c = ContextGetter.applicationContext();
        if (c == null) {
            return true;
        }
        try {
            NetworkInfo info = ((ConnectivityManager) c.getSystemService("connectivity")).getActiveNetworkInfo();
            if (info == null || !info.isConnected()) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return true;
        }
    }
}
