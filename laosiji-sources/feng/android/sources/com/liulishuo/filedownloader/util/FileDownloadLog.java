package com.liulishuo.filedownloader.util;

import android.util.Log;

public class FileDownloadLog {
    public static boolean NEED_LOG = false;
    private static final String TAG = "FileDownloader.";

    public static void e(Object o, Throwable e, String msg, Object... args) {
        log(6, o, e, msg, args);
    }

    public static void e(Object o, String msg, Object... args) {
        log(6, o, msg, args);
    }

    public static void i(Object o, String msg, Object... args) {
        log(4, o, msg, args);
    }

    public static void d(Object o, String msg, Object... args) {
        log(3, o, msg, args);
    }

    public static void w(Object o, String msg, Object... args) {
        log(5, o, msg, args);
    }

    public static void v(Object o, String msg, Object... args) {
        log(2, o, msg, args);
    }

    private static void log(int priority, Object o, String message, Object... args) {
        log(priority, o, null, message, args);
    }

    private static void log(int priority, Object o, Throwable throwable, String message, Object... args) {
        if ((priority >= 5) || NEED_LOG) {
            Log.println(priority, getTag(o), FileDownloadUtils.formatString(message, args));
            if (throwable != null) {
                throwable.printStackTrace();
            }
        }
    }

    private static String getTag(Object o) {
        return TAG + (o instanceof Class ? ((Class) o).getSimpleName() : o.getClass().getSimpleName());
    }
}
