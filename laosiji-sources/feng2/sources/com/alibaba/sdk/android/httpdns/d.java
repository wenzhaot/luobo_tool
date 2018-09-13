package com.alibaba.sdk.android.httpdns;

class d {
    static String PROTOCOL = "http://";
    static int a = 15000;
    static String[] b = new String[]{"203.107.1.1"};
    static String c;
    /* renamed from: c */
    static final String[] f2c = new String[]{"203.107.1.97", "203.107.1.100", "httpdns-sc.aliyuncs.com"};
    static String d = "80";
    /* renamed from: d */
    static final String[] f3d = new String[0];

    static synchronized boolean a(String[] strArr) {
        boolean z;
        synchronized (d.class) {
            if (strArr != null) {
                if (strArr.length != 0) {
                    b = strArr;
                    z = true;
                }
            }
            z = false;
        }
        return z;
    }

    static synchronized void d(String str) {
        synchronized (d.class) {
            c = str;
        }
    }

    static synchronized void setHTTPSRequestEnabled(boolean z) {
        synchronized (d.class) {
            if (z) {
                PROTOCOL = "https://";
                d = "443";
            } else {
                PROTOCOL = "http://";
                d = "80";
            }
        }
    }

    static synchronized void setTimeoutInterval(int i) {
        synchronized (d.class) {
            if (i > 0) {
                a = i;
            }
        }
    }
}
