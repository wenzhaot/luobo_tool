package com.alibaba.sdk.android.httpdns;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.alibaba.sdk.android.httpdns.a.b;
import java.util.ArrayList;

public class k {
    private static Context a;
    static boolean b = false;
    private static String f;

    private static String c() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) a.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isAvailable()) {
            return "None_Network";
        }
        String typeName = activeNetworkInfo.getTypeName();
        g.e("[detectCurrentNetwork] - Network name:" + typeName + " subType name: " + activeNetworkInfo.getSubtypeName());
        return typeName == null ? "None_Network" : typeName;
    }

    public static void setContext(Context context) {
        if (context == null) {
            throw new IllegalStateException("Context can't be null");
        }
        a = context;
        BroadcastReceiver anonymousClass1 = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                try {
                    if (!isInitialStickyBroadcast() && intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                        b.b(context);
                        String d = k.c();
                        if (!(d == "None_Network" || d.equalsIgnoreCase(k.f))) {
                            g.e("[BroadcastReceiver.onReceive] - Network state changed");
                            ArrayList a = b.a().a();
                            b.a().clear();
                            b.a().a();
                            if (k.b && HttpDns.instance != null) {
                                g.e("[BroadcastReceiver.onReceive] - refresh host");
                                HttpDns.instance.setPreResolveHosts(a);
                            }
                        }
                        k.f = d;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        a.registerReceiver(anonymousClass1, intentFilter);
    }
}
