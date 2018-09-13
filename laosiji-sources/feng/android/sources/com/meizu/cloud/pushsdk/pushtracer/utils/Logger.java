package com.meizu.cloud.pushsdk.pushtracer.utils;

import com.meizu.cloud.pushinternal.DebugLogger;

public class Logger {
    private static int level = 0;

    public static void e(String tag, String msg, Object... args) {
        if (level >= 1) {
            DebugLogger.e(getTag(tag), getMessage(msg, args));
        }
    }

    public static void d(String tag, String msg, Object... args) {
        if (level >= 2) {
            DebugLogger.d(getTag(tag), getMessage(msg, args));
        }
    }

    public static void i(String tag, String msg, Object... args) {
        if (level >= 3) {
            DebugLogger.i(getTag(tag), getMessage(msg, args));
        }
    }

    private static String getMessage(String msg, Object... args) {
        return getThread() + "|" + String.format(msg, args);
    }

    private static String getTag(String tag) {
        return "PushTracker->" + tag;
    }

    private static String getThread() {
        return Thread.currentThread().getName();
    }

    public static void updateLogLevel(LogLevel newLevel) {
        level = newLevel.getLevel();
    }
}
