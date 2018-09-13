package com.meizu.cloud.pushinternal;

import android.content.Context;
import android.os.Environment;
import com.meizu.cloud.pushsdk.base.Logger;

public class DebugLogger {
    public static boolean debug = false;

    public static void initDebugLogger(Context context) {
        Logger.get().init(context);
        Logger.get().setFilePath(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/pushSdk/" + context.getPackageName());
    }

    public static void switchDebug(boolean flag) {
        Logger.get().setDebugMode(flag);
    }

    public static void flush() {
        Logger.get().flush(false);
    }

    public static boolean isDebuggable() {
        return Logger.get().isDebugMode();
    }

    public static void i(String tag, String message) {
        Logger.get().i(tag, message);
    }

    public static void d(String tag, String message) {
        Logger.get().d(tag, message);
    }

    public static void w(String tag, String message) {
        Logger.get().w(tag, message);
    }

    public static void e(String tag, String message) {
        Logger.get().e(tag, message);
    }
}
