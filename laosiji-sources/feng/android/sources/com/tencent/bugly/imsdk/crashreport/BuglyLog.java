package com.tencent.bugly.imsdk.crashreport;

import android.util.Log;
import com.tencent.bugly.imsdk.b;
import com.tencent.bugly.imsdk.proguard.x;

/* compiled from: BUGLY */
public class BuglyLog {
    public static void v(String str, String str2) {
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = "null";
        }
        if (b.c) {
            Log.v(str, str2);
        }
        x.a("V", str, str2);
    }

    public static void d(String str, String str2) {
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = "null";
        }
        if (b.c) {
            Log.d(str, str2);
        }
        x.a("D", str, str2);
    }

    public static void i(String str, String str2) {
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = "null";
        }
        if (b.c) {
            Log.i(str, str2);
        }
        x.a("I", str, str2);
    }

    public static void w(String str, String str2) {
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = "null";
        }
        if (b.c) {
            Log.w(str, str2);
        }
        x.a("W", str, str2);
    }

    public static void e(String str, String str2) {
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = "null";
        }
        if (b.c) {
            Log.e(str, str2);
        }
        x.a("E", str, str2);
    }

    public static void e(String str, String str2, Throwable th) {
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = "null";
        }
        if (b.c) {
            Log.e(str, str2, th);
        }
        x.a("E", str, th);
    }

    public static void setCache(int i) {
        x.a(i);
    }
}
