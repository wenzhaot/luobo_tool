package com.alibaba.sdk.android.httpdns.a;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

public class h {
    private final Handler b;
    private String o = "UNKNOWN";

    private class a extends Handler {
        a(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            super.handleMessage(message);
            switch (message.what) {
                case 0:
                    h.this.d((Context) message.obj);
                    return;
                default:
                    return;
            }
        }
    }

    h() {
        HandlerThread handlerThread = new HandlerThread("SpStatus daemon");
        handlerThread.start();
        this.b = new a(handlerThread.getLooper());
    }

    private static int a(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null) {
                return 0;
            }
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                return 255;
            }
            if (!activeNetworkInfo.isAvailable() || !activeNetworkInfo.isConnected()) {
                return 255;
            }
            if (activeNetworkInfo.getType() == 1) {
                return 1;
            }
            if (activeNetworkInfo.getType() != 0) {
                return 0;
            }
            switch (activeNetworkInfo.getSubtype()) {
                case 1:
                case 2:
                case 4:
                case 7:
                case 11:
                    return 2;
                case 3:
                case 5:
                case 6:
                case 8:
                case 9:
                case 10:
                case 15:
                    return 3;
                case 13:
                    return 4;
                default:
                    return 0;
            }
        } catch (Exception e) {
            return 255;
        }
    }

    /* renamed from: a */
    private static String m11a(Context context) {
        try {
            Object simOperator = ((TelephonyManager) context.getSystemService("phone")).getSimOperator();
            if (!TextUtils.isEmpty(simOperator)) {
                return simOperator;
            }
        } catch (Throwable th) {
        }
        return String.valueOf(0);
    }

    private static String b(Context context) {
        try {
            WifiInfo connectionInfo = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo();
            return connectionInfo != null ? connectionInfo.getSSID() : null;
        } catch (Throwable th) {
            return null;
        }
    }

    private void d(Context context) {
        switch (a(context)) {
            case 1:
                this.o = b(context);
                break;
            case 2:
            case 3:
            case 4:
                this.o = a(context);
                break;
        }
        if (TextUtils.isEmpty(this.o)) {
            this.o = "UNKNOWN";
        }
    }

    void c(Context context) {
        if (context != null) {
            Message obtain = Message.obtain();
            obtain.obj = context;
            obtain.what = 0;
            this.b.sendMessage(obtain);
        }
    }

    String g() {
        return this.o;
    }
}
