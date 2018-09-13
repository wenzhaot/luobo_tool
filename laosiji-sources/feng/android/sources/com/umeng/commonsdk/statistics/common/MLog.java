package com.umeng.commonsdk.statistics.common;

import android.text.TextUtils;
import android.util.Log;
import com.umeng.commonsdk.statistics.AnalyticsConstants;
import java.util.Formatter;
import java.util.Locale;

public class MLog {
    public static boolean DEBUG = false;
    private static final int LEVEL_DEBUG = 2;
    private static final int LEVEL_ERROR = 5;
    private static final int LEVEL_INFO = 3;
    private static final int LEVEL_VERBOSE = 1;
    private static final int LEVEL_WARN = 4;
    private static int LOG_MAXLENGTH = 2000;
    private static String TAG = AnalyticsConstants.LOG_TAG;

    private MLog() {
    }

    public static void i(Locale locale, String str, Object... objArr) {
        try {
            i(TAG, new Formatter(locale).format(str, objArr).toString(), null);
        } catch (Throwable th) {
            e(th);
        }
    }

    public static void d(Locale locale, String str, Object... objArr) {
        try {
            d(TAG, new Formatter(locale).format(str, objArr).toString(), null);
        } catch (Throwable th) {
            e(th);
        }
    }

    public static void e(Locale locale, String str, Object... objArr) {
        try {
            e(TAG, new Formatter(locale).format(str, objArr).toString(), null);
        } catch (Throwable th) {
            e(th);
        }
    }

    public static void v(Locale locale, String str, Object... objArr) {
        try {
            v(TAG, new Formatter(locale).format(str, objArr).toString(), null);
        } catch (Throwable th) {
            e(th);
        }
    }

    public static void w(Locale locale, String str, Object... objArr) {
        try {
            w(TAG, new Formatter(locale).format(str, objArr).toString(), null);
        } catch (Throwable th) {
            e(th);
        }
    }

    public static void i(String str, Object... objArr) {
        try {
            String str2 = "";
            if (str.contains("%")) {
                i(TAG, new Formatter().format(str, objArr).toString(), null);
                return;
            }
            if (objArr != null) {
                str2 = (String) objArr[0];
            }
            i(str, str2, null);
        } catch (Throwable th) {
            e(th);
        }
    }

    public static void d(String str, Object... objArr) {
        try {
            String str2 = "";
            if (str.contains("%")) {
                d(TAG, new Formatter().format(str, objArr).toString(), null);
                return;
            }
            if (objArr != null) {
                str2 = (String) objArr[0];
            }
            d(str, str2, null);
        } catch (Throwable th) {
            e(th);
        }
    }

    public static void e(String str, Object... objArr) {
        try {
            String str2 = "";
            if (str.contains("%")) {
                e(TAG, new Formatter().format(str, objArr).toString(), null);
                return;
            }
            if (objArr != null) {
                str2 = (String) objArr[0];
            }
            e(str, str2, null);
        } catch (Throwable th) {
            e(th);
        }
    }

    public static void v(String str, Object... objArr) {
        try {
            String str2 = "";
            if (str.contains("%")) {
                v(TAG, new Formatter().format(str, objArr).toString(), null);
                return;
            }
            if (objArr != null) {
                str2 = (String) objArr[0];
            }
            v(str, str2, null);
        } catch (Throwable th) {
            e(th);
        }
    }

    public static void w(String str, Object... objArr) {
        try {
            String str2 = "";
            if (str.contains("%")) {
                w(TAG, new Formatter().format(str, objArr).toString(), null);
                return;
            }
            if (objArr != null) {
                str2 = (String) objArr[0];
            }
            w(str, str2, null);
        } catch (Throwable th) {
            e(th);
        }
    }

    public static void i(Throwable th) {
        i(TAG, null, th);
    }

    public static void v(Throwable th) {
        v(TAG, null, th);
    }

    public static void w(Throwable th) {
        w(TAG, null, th);
    }

    public static void d(Throwable th) {
        d(TAG, null, th);
    }

    public static void e(Throwable th) {
        e(TAG, null, th);
    }

    public static void i(String str, Throwable th) {
        i(TAG, str, th);
    }

    public static void v(String str, Throwable th) {
        v(TAG, str, th);
    }

    public static void w(String str, Throwable th) {
        w(TAG, str, th);
    }

    public static void d(String str, Throwable th) {
        d(TAG, str, th);
    }

    public static void e(String str, Throwable th) {
        e(TAG, str, th);
    }

    public static void v(String str) {
        v(TAG, str, null);
    }

    public static void d(String str) {
        d(TAG, str, null);
    }

    public static void i(String str) {
        i(TAG, str, null);
    }

    public static void w(String str) {
        w(TAG, str, null);
    }

    public static void e(String str) {
        e(TAG, str, null);
    }

    public static void v(String str, String str2, Throwable th) {
        if (DEBUG) {
            print(1, str, str2, th);
        }
    }

    public static void d(String str, String str2, Throwable th) {
        if (DEBUG) {
            print(2, str, str2, th);
        }
    }

    public static void i(String str, String str2, Throwable th) {
        if (DEBUG) {
            print(3, str, str2, th);
        }
    }

    public static void w(String str, String str2, Throwable th) {
        if (DEBUG) {
            print(4, str, str2, th);
        }
    }

    public static void e(String str, String str2, Throwable th) {
        if (DEBUG) {
            print(5, str, str2, th);
        }
    }

    private static void print(int i, String str, String str2, Throwable th) {
        int i2 = 0;
        if (!TextUtils.isEmpty(str2)) {
            int length = str2.length();
            int i3 = LOG_MAXLENGTH;
            int i4 = 0;
            while (i2 < 100) {
                if (length <= i3) {
                    switch (i) {
                        case 1:
                            Log.v(str, str2.substring(i4, length));
                            break;
                        case 2:
                            Log.d(str, str2.substring(i4, length));
                            break;
                        case 3:
                            Log.i(str, str2.substring(i4, length));
                            break;
                        case 4:
                            Log.w(str, str2.substring(i4, length));
                            break;
                        case 5:
                            Log.e(str, str2.substring(i4, length));
                            break;
                    }
                }
                switch (i) {
                    case 1:
                        Log.v(str, str2.substring(i4, i3));
                        break;
                    case 2:
                        Log.d(str, str2.substring(i4, i3));
                        break;
                    case 3:
                        Log.i(str, str2.substring(i4, i3));
                        break;
                    case 4:
                        Log.w(str, str2.substring(i4, i3));
                        break;
                    case 5:
                        Log.e(str, str2.substring(i4, i3));
                        break;
                    default:
                        break;
                }
                i2++;
                i4 = i3;
                i3 = LOG_MAXLENGTH + i3;
            }
        }
        if (th != null) {
            String stackTrace = getStackTrace(th);
            if (!TextUtils.isEmpty(stackTrace)) {
                switch (i) {
                    case 1:
                        Log.v(str, stackTrace);
                        return;
                    case 2:
                        Log.d(str, stackTrace);
                        return;
                    case 3:
                        Log.i(str, stackTrace);
                        return;
                    case 4:
                        Log.w(str, stackTrace);
                        return;
                    case 5:
                        Log.e(str, stackTrace);
                        return;
                    default:
                        return;
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x002a A:{SYNTHETIC, Splitter: B:16:0x002a} */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x002f  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0038 A:{SYNTHETIC, Splitter: B:23:0x0038} */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x003d  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0038 A:{SYNTHETIC, Splitter: B:23:0x0038} */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x003d  */
    public static java.lang.String getStackTrace(java.lang.Throwable r4) {
        /*
        r2 = 0;
        r0 = "";
        r3 = new java.io.StringWriter;	 Catch:{ Throwable -> 0x0026, all -> 0x0033 }
        r3.<init>();	 Catch:{ Throwable -> 0x0026, all -> 0x0033 }
        r1 = new java.io.PrintWriter;	 Catch:{ Throwable -> 0x004c, all -> 0x0047 }
        r1.<init>(r3);	 Catch:{ Throwable -> 0x004c, all -> 0x0047 }
        r4.printStackTrace(r1);	 Catch:{ Throwable -> 0x0050, all -> 0x004a }
        r1.flush();	 Catch:{ Throwable -> 0x0050, all -> 0x004a }
        r3.flush();	 Catch:{ Throwable -> 0x0050, all -> 0x004a }
        r0 = r3.toString();	 Catch:{ Throwable -> 0x0050, all -> 0x004a }
        if (r3 == 0) goto L_0x0020;
    L_0x001d:
        r3.close();	 Catch:{ Throwable -> 0x0041 }
    L_0x0020:
        if (r1 == 0) goto L_0x0025;
    L_0x0022:
        r1.close();
    L_0x0025:
        return r0;
    L_0x0026:
        r1 = move-exception;
        r1 = r2;
    L_0x0028:
        if (r2 == 0) goto L_0x002d;
    L_0x002a:
        r2.close();	 Catch:{ Throwable -> 0x0043 }
    L_0x002d:
        if (r1 == 0) goto L_0x0025;
    L_0x002f:
        r1.close();
        goto L_0x0025;
    L_0x0033:
        r0 = move-exception;
        r1 = r2;
        r3 = r2;
    L_0x0036:
        if (r3 == 0) goto L_0x003b;
    L_0x0038:
        r3.close();	 Catch:{ Throwable -> 0x0045 }
    L_0x003b:
        if (r1 == 0) goto L_0x0040;
    L_0x003d:
        r1.close();
    L_0x0040:
        throw r0;
    L_0x0041:
        r2 = move-exception;
        goto L_0x0020;
    L_0x0043:
        r2 = move-exception;
        goto L_0x002d;
    L_0x0045:
        r2 = move-exception;
        goto L_0x003b;
    L_0x0047:
        r0 = move-exception;
        r1 = r2;
        goto L_0x0036;
    L_0x004a:
        r0 = move-exception;
        goto L_0x0036;
    L_0x004c:
        r1 = move-exception;
        r1 = r2;
        r2 = r3;
        goto L_0x0028;
    L_0x0050:
        r2 = move-exception;
        r2 = r3;
        goto L_0x0028;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.commonsdk.statistics.common.MLog.getStackTrace(java.lang.Throwable):java.lang.String");
    }
}
