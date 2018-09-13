package com.umeng.analytics.game;

import android.content.Context;
import com.stub.StubApp;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.pro.h;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.commonsdk.statistics.common.MLog;

public class UMGameAgent extends MobclickAgent {
    private static final String a = "Input string is null or empty";
    private static final String b = "Input string must be less than 64 chars";
    private static final String c = "Input value type is negative";
    private static final String d = "The int value for 'Pay Channels' ranges between 1 ~ 99 ";
    private static final b e = new b();
    private static Context f;

    public static void init(Context context) {
        try {
            if (f == null && context != null) {
                f = StubApp.getOrigApplicationContext(context.getApplicationContext());
            }
            e.a(f);
        } catch (Throwable th) {
            MLog.e("please check Context!");
        }
    }

    public static void setTraceSleepTime(boolean z) {
        e.a(z);
    }

    public static void setPlayerLevel(int i) {
        e.a(String.valueOf(i));
    }

    public static void startLevel(String str) {
        UMLog uMLog;
        if (a(str)) {
            MLog.e(a);
            uMLog = UMConfigure.umDebugLog;
            UMLog.aq(h.aB, 0, "\\|");
        } else if (str.length() > 64) {
            MLog.e(b);
            uMLog = UMConfigure.umDebugLog;
            UMLog.aq(h.aC, 0, "\\|");
        } else {
            e.b(str);
        }
    }

    public static void finishLevel(String str) {
        UMLog uMLog;
        if (a(str)) {
            MLog.e(a);
            uMLog = UMConfigure.umDebugLog;
            UMLog.aq(h.aD, 0, "\\|");
        } else if (str.length() > 64) {
            MLog.e(b);
            uMLog = UMConfigure.umDebugLog;
            UMLog.aq(h.aE, 0, "\\|");
        } else {
            e.c(str);
        }
    }

    public static void failLevel(String str) {
        UMLog uMLog;
        if (a(str)) {
            MLog.e(a);
            uMLog = UMConfigure.umDebugLog;
            UMLog.aq(h.aF, 0, "\\|");
        } else if (str.length() > 64) {
            MLog.e(b);
            uMLog = UMConfigure.umDebugLog;
            UMLog.aq(h.aG, 0, "\\|");
        } else {
            e.d(str);
        }
    }

    public static void pay(double d, double d2, int i) {
        UMLog uMLog;
        if (i <= 0 || i >= 100) {
            MLog.e(d);
            uMLog = UMConfigure.umDebugLog;
            UMLog.aq(h.aI, 0, "\\|");
        } else if (d < 0.0d || d2 < 0.0d) {
            MLog.e(c);
            uMLog = UMConfigure.umDebugLog;
            UMLog.aq(h.aH, 0, "\\|");
        } else {
            e.a(d, d2, i);
        }
    }

    public static void pay(double d, String str, int i, double d2, int i2) {
        UMLog uMLog;
        if (i2 <= 0 || i2 >= 100) {
            MLog.e(d);
            uMLog = UMConfigure.umDebugLog;
            UMLog.aq(h.aJ, 0, "\\|");
        } else if (d < 0.0d || i < 0 || d2 < 0.0d) {
            MLog.e(c);
            uMLog = UMConfigure.umDebugLog;
            UMLog.aq(h.aK, 0, "\\|");
        } else if (a(str)) {
            MLog.e(a);
            uMLog = UMConfigure.umDebugLog;
            UMLog.aq(h.aL, 0, "\\|");
        } else {
            e.a(d, str, i, d2, i2);
        }
    }

    public static void exchange(double d, String str, double d2, int i, String str2) {
        UMLog uMLog;
        if (d < 0.0d || d2 < 0.0d) {
            MLog.e(c);
            uMLog = UMConfigure.umDebugLog;
            UMLog.aq(h.aM, 0, "\\|");
        } else if (i <= 0 || i >= 100) {
            MLog.e(d);
            uMLog = UMConfigure.umDebugLog;
            UMLog.aq(h.aN, 0, "\\|");
        } else {
            e.a(d, str, d2, i, str2);
        }
    }

    public static void buy(String str, int i, double d) {
        UMLog uMLog;
        if (a(str)) {
            MLog.e(a);
            uMLog = UMConfigure.umDebugLog;
            UMLog.aq(h.aO, 0, "\\|");
        } else if (i < 0 || d < 0.0d) {
            MLog.e(c);
            uMLog = UMConfigure.umDebugLog;
            UMLog.aq(h.aP, 0, "\\|");
        } else {
            e.a(str, i, d);
        }
    }

    public static void use(String str, int i, double d) {
        UMLog uMLog;
        if (a(str)) {
            MLog.e(a);
            uMLog = UMConfigure.umDebugLog;
            UMLog.aq(h.aV, 0, "\\|");
        } else if (i < 0 || d < 0.0d) {
            MLog.e(c);
            uMLog = UMConfigure.umDebugLog;
            UMLog.aq(h.aW, 0, "\\|");
        } else {
            e.b(str, i, d);
        }
    }

    public static void bonus(double d, int i) {
        UMLog uMLog;
        if (d < 0.0d) {
            MLog.e(c);
            uMLog = UMConfigure.umDebugLog;
            UMLog.aq(h.aQ, 0, "\\|");
        } else if (i <= 0 || i >= 100) {
            MLog.e(d);
            uMLog = UMConfigure.umDebugLog;
            UMLog.aq(h.aR, 0, "\\|");
        } else {
            e.a(d, i);
        }
    }

    public static void bonus(String str, int i, double d, int i2) {
        UMLog uMLog;
        if (a(str)) {
            MLog.e(a);
            uMLog = UMConfigure.umDebugLog;
            UMLog.aq(h.aS, 0, "\\|");
        } else if (i < 0 || d < 0.0d) {
            MLog.e(c);
            uMLog = UMConfigure.umDebugLog;
            UMLog.aq(h.aT, 0, "\\|");
        } else if (i2 <= 0 || i2 >= 100) {
            MLog.e(d);
            uMLog = UMConfigure.umDebugLog;
            UMLog.aq(h.aU, 0, "\\|");
        } else {
            e.a(str, i, d, i2);
        }
    }

    private static boolean a(String str) {
        if (str != null && str.trim().length() > 0) {
            return false;
        }
        return true;
    }
}
