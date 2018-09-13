package com.umeng.analytics;

import android.content.Context;
import android.text.TextUtils;
import com.umeng.analytics.pro.u;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.commonsdk.statistics.SdkVersion;
import com.umeng.commonsdk.utils.UMUtils;

public class AnalyticsConfig {
    public static boolean ACTIVITY_DURATION_OPEN = true;
    public static boolean CATCH_EXCEPTION = true;
    public static boolean FLAG_DPLUS = false;
    public static String GPU_RENDERER = "";
    public static String GPU_VENDER = "";
    public static final String[] UM_COMMON_VERSION_LIMIT = new String[]{SdkVersion.SDK_VERSION, "1.5.3+000"};
    static double[] a = null;
    private static String b = null;
    private static String c = null;
    private static String d = null;
    private static int e = 0;
    public static long kContinueSessionMillis = 30000;
    public static String mWrapperType = null;
    public static String mWrapperVersion = null;

    static void a(String str) {
        c = str;
    }

    public static String getAppkey(Context context) {
        return UMUtils.getAppkey(context);
    }

    public static String getChannel(Context context) {
        return UMUtils.getChannel(context);
    }

    public static double[] getLocation() {
        return a;
    }

    static void a(Context context, String str) {
        if (TextUtils.isEmpty(str)) {
            UMLog uMLog = UMConfigure.umDebugLog;
            UMLog.aq("MobclickAgent.setSecret方法参数secretkey不能为null，也不能为空字符串。|secretkey参数必须是非空 字符串。", 0, "\\|");
            return;
        }
        d = str;
        u.a(context).a(d);
    }

    public static String getSecretKey(Context context) {
        if (TextUtils.isEmpty(d)) {
            d = u.a(context).c();
        }
        return d;
    }

    static void a(Context context, int i) {
        e = i;
        u.a(context).a(e);
    }

    public static int getVerticalType(Context context) {
        if (e == 0) {
            e = u.a(context).d();
        }
        return e;
    }
}
