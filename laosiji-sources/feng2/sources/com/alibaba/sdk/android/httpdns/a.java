package com.alibaba.sdk.android.httpdns;

import android.text.TextUtils;
import java.security.NoSuchAlgorithmException;

class a {
    private static long a;
    /* renamed from: a */
    private static String f4a;

    static String a(String str, String str2) {
        String str3 = "";
        if (!j.b(str)) {
            return str3;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str);
        stringBuilder.append("-");
        stringBuilder.append(a);
        stringBuilder.append("-");
        stringBuilder.append(str2);
        try {
            return j.a(stringBuilder.toString());
        } catch (NoSuchAlgorithmException e) {
            return str3;
        }
    }

    static void a(String str) {
        a = str;
    }

    static boolean a() {
        return !TextUtils.isEmpty(a);
    }

    static String getTimestamp() {
        return String.valueOf(((System.currentTimeMillis() / 1000) + a) + 600);
    }

    static void setAuthCurrentTime(long j) {
        a = j - (System.currentTimeMillis() / 1000);
    }
}
