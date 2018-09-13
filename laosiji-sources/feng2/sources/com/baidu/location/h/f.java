package com.baidu.location.h;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import java.util.Map;

public abstract class f {
    private static String a = "10.0.0.172";
    private static int b = 80;
    public static int g = a.g;
    protected static int o = 0;
    public String h = null;
    public int i = 3;
    public String j = null;
    public Map<String, Object> k = null;
    public String l = null;
    public byte[] m = null;
    public String n = null;

    private static int a(Context context, NetworkInfo networkInfo) {
        String toLowerCase;
        if (!(networkInfo == null || networkInfo.getExtraInfo() == null)) {
            toLowerCase = networkInfo.getExtraInfo().toLowerCase();
            if (toLowerCase != null) {
                if (toLowerCase.startsWith("cmwap") || toLowerCase.startsWith("uniwap") || toLowerCase.startsWith("3gwap")) {
                    toLowerCase = Proxy.getDefaultHost();
                    if (toLowerCase == null || toLowerCase.equals("") || toLowerCase.equals("null")) {
                        toLowerCase = "10.0.0.172";
                    }
                    a = toLowerCase;
                    return a.d;
                } else if (toLowerCase.startsWith("ctwap")) {
                    toLowerCase = Proxy.getDefaultHost();
                    if (toLowerCase == null || toLowerCase.equals("") || toLowerCase.equals("null")) {
                        toLowerCase = "10.0.0.200";
                    }
                    a = toLowerCase;
                    return a.d;
                } else if (toLowerCase.startsWith("cmnet") || toLowerCase.startsWith("uninet") || toLowerCase.startsWith("ctnet") || toLowerCase.startsWith("3gnet")) {
                    return a.e;
                }
            }
        }
        toLowerCase = Proxy.getDefaultHost();
        if (toLowerCase != null && toLowerCase.length() > 0) {
            if ("10.0.0.172".equals(toLowerCase.trim())) {
                a = "10.0.0.172";
                return a.d;
            } else if ("10.0.0.200".equals(toLowerCase.trim())) {
                a = "10.0.0.200";
                return a.d;
            }
        }
        return a.e;
    }

    private void b() {
        g = c();
    }

    private int c() {
        Context serviceContext = com.baidu.location.f.getServiceContext();
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) serviceContext.getSystemService("connectivity");
            if (connectivityManager == null) {
                return a.g;
            }
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo == null || !activeNetworkInfo.isAvailable()) {
                return a.g;
            }
            if (activeNetworkInfo.getType() != 1) {
                return a(serviceContext, activeNetworkInfo);
            }
            String defaultHost = Proxy.getDefaultHost();
            return (defaultHost == null || defaultHost.length() <= 0) ? a.f : a.h;
        } catch (Exception e) {
            return a.g;
        }
    }

    public abstract void a();

    public abstract void a(boolean z);

    public void a(boolean z, String str) {
        new h(this, str, z).start();
    }

    public void c(String str) {
        new i(this, str).start();
    }

    public void d() {
        new g(this).start();
    }

    public void e() {
        a(false, "loc.map.baidu.com");
    }
}
