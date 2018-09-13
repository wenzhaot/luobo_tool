package com.huawei.android.pushagent.a.a;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.umeng.message.proguard.l;

public class c {
    private static String a = "";
    private static String b = "hwpush";
    private static String c = "PushLog";
    private static c d = null;

    private c() {
    }

    public static synchronized c a() {
        c cVar;
        synchronized (c.class) {
            if (d == null) {
                d = new c();
            }
            cVar = d;
        }
        return cVar;
    }

    public static String a(Throwable th) {
        return Log.getStackTraceString(th);
    }

    private synchronized void a(int i, String str, String str2, Throwable th, int i2) {
        try {
            if (a(i)) {
                String str3 = "[" + Thread.currentThread().getName() + "-" + Thread.currentThread().getId() + "]" + str2;
                StackTraceElement[] stackTrace = new Throwable().getStackTrace();
                str3 = stackTrace.length > i2 ? str3 + l.s + a + "/" + stackTrace[i2].getFileName() + ":" + stackTrace[i2].getLineNumber() + l.t : str3 + l.s + a + "/unknown source)";
                if (th != null) {
                    str3 = str3 + 10 + a(th);
                }
                Log.println(i, c, str3);
            }
        } catch (Throwable e) {
            Log.e("PushLogSC2816", "call writeLog cause:" + e.toString(), e);
        }
        return;
    }

    public static synchronized void a(Context context) {
        synchronized (c.class) {
            if (d == null) {
                a();
            }
            if (TextUtils.isEmpty(a)) {
                String packageName = context.getPackageName();
                if (packageName != null) {
                    String[] split = packageName.split("\\.");
                    if (split.length > 0) {
                        a = split[split.length - 1];
                    }
                }
                c = b(context);
            }
        }
    }

    public static void a(String str, String str2) {
        a().a(3, str, str2, null, 2);
    }

    public static void a(String str, String str2, Throwable th) {
        a().a(3, str, str2, th, 2);
    }

    public static void a(String str, String str2, Object... objArr) {
        try {
            a().a(3, str, String.format(str2, objArr), null, 2);
        } catch (Throwable e) {
            Log.e("PushLogSC2816", "call writeLog cause:" + e.toString(), e);
        }
    }

    private static boolean a(int i) {
        return Log.isLoggable(b, i);
    }

    public static String b(Context context) {
        String str = "PushLogSC2816";
        return context == null ? str : "com.huawei.android.pushagent".equals(context.getPackageName()) ? str.replace("SC", "AC") : "android".equals(context.getPackageName()) ? str.replace("SC", "") : !TextUtils.isEmpty(a) ? str + "_" + a : str;
    }

    public static void b(String str, String str2) {
        a().a(4, str, str2, null, 2);
    }

    public static void b(String str, String str2, Throwable th) {
        a().a(4, str, str2, th, 2);
    }

    public static void b(String str, String str2, Object... objArr) {
        try {
            a().a(2, str, String.format(str2, objArr), null, 2);
        } catch (Throwable e) {
            Log.e("PushLogSC2816", "call writeLog cause:" + e.toString(), e);
        }
    }

    public static void c(String str, String str2) {
        a().a(5, str, str2, null, 2);
    }

    public static void c(String str, String str2, Throwable th) {
        a().a(6, str, str2, th, 2);
    }

    public static void d(String str, String str2) {
        a().a(6, str, str2, null, 2);
    }

    public static void d(String str, String str2, Throwable th) {
        a().a(2, str, str2, th, 2);
    }

    public static void e(String str, String str2) {
        a().a(2, str, str2, null, 2);
    }
}
