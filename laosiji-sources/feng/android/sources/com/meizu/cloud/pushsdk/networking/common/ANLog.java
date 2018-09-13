package com.meizu.cloud.pushsdk.networking.common;

import android.util.Log;
import com.meizu.cloud.pushinternal.DebugLogger;

public class ANLog {
    private static boolean IS_LOGGING_ENABLED = false;
    private static String TAG = ANConstants.ANDROID_NETWORKING;

    private ANLog() {
    }

    public static void enableLogging() {
        IS_LOGGING_ENABLED = true;
    }

    public static void disableLogging() {
        IS_LOGGING_ENABLED = false;
    }

    public static void setTag(String tag) {
        if (tag != null) {
            TAG = tag;
        }
    }

    public static void d(String message) {
        if (IS_LOGGING_ENABLED) {
            DebugLogger.d(TAG, message);
        }
    }

    public static void e(String message) {
        if (IS_LOGGING_ENABLED) {
            DebugLogger.e(TAG, message);
        }
    }

    public static void i(String message) {
        if (IS_LOGGING_ENABLED) {
            DebugLogger.i(TAG, message);
        }
    }

    public static void w(String message) {
        if (IS_LOGGING_ENABLED) {
            DebugLogger.w(TAG, message);
        }
    }

    public static void wtf(String message) {
        if (IS_LOGGING_ENABLED) {
            Log.wtf(TAG, message);
        }
    }
}
