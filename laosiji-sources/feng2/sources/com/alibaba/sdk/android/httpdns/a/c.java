package com.alibaba.sdk.android.httpdns.a;

import android.text.TextUtils;
import java.text.SimpleDateFormat;

public class c {
    private static final SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static long a(String str) {
        long j = 0;
        if (TextUtils.isEmpty(str)) {
            return j;
        }
        try {
            return Long.valueOf(str).longValue();
        } catch (Exception e) {
            return j;
        }
    }

    static String c(String str) {
        try {
            return a.format(Long.valueOf(a(str) * 1000));
        } catch (Exception e) {
            return a.format(Long.valueOf(System.currentTimeMillis()));
        }
    }

    static String d(String str) {
        try {
            return String.valueOf(a.parse(str).getTime() / 1000);
        } catch (Exception e) {
            return String.valueOf(System.currentTimeMillis() / 1000);
        }
    }
}
