package com.umeng.commonsdk.debug;

import android.util.Log;
import com.meizu.cloud.pushsdk.constants.PushConstants;

public class UMRTLog {
    private static final String RTLOG_ENABLE = "1";
    private static final String RTLOG_PROP = "debug.umeng.rtlog";
    public static final String RTLOG_TAG = "MobclickRT";

    private UMRTLog() {
    }

    private static String getSystemProperty(String str, String str2) {
        try {
            return (String) Class.forName("android.os.SystemProperties").getMethod("get", new Class[]{String.class, String.class}).invoke(null, new Object[]{str, str2});
        } catch (Throwable th) {
            return str2;
        }
    }

    private static boolean shouldOutput() {
        if ("1".equals(getSystemProperty(RTLOG_PROP, PushConstants.PUSH_TYPE_NOTIFY))) {
            return true;
        }
        return false;
    }

    public static void e(String str, String str2) {
        if (shouldOutput()) {
            Log.e(str, warpperMsg(str2, false));
        }
    }

    public static void w(String str, String str2) {
        if (shouldOutput()) {
            Log.w(str, warpperMsg(str2, false));
        }
    }

    public static void i(String str, String str2) {
        if (shouldOutput()) {
            Log.i(str, warpperMsg(str2, false));
        }
    }

    public static void d(String str, String str2) {
        if (shouldOutput()) {
            Log.d(str, warpperMsg(str2, false));
        }
    }

    public static void v(String str, String str2) {
        if (shouldOutput()) {
            Log.v(str, warpperMsg(str2, false));
        }
    }

    public static void se(String str, String str2) {
        if (shouldOutput()) {
            Log.e(str, warpperMsg(str2, true));
        }
    }

    public static void sw(String str, String str2) {
        if (shouldOutput()) {
            Log.w(str, warpperMsg(str2, true));
        }
    }

    public static void si(String str, String str2) {
        if (shouldOutput()) {
            Log.i(str, warpperMsg(str2, true));
        }
    }

    public static void sd(String str, String str2) {
        if (shouldOutput()) {
            Log.d(str, warpperMsg(str2, true));
        }
    }

    public static void sv(String str, String str2) {
        if (shouldOutput()) {
            Log.v(str, warpperMsg(str2, true));
        }
    }

    private static String warpperMsg(String str, boolean z) {
        if (!z) {
            return str;
        }
        StringBuffer stringBuffer = null;
        try {
            StackTraceElement[] stackTrace = new Throwable().getStackTrace();
            if (stackTrace.length >= 3) {
                String fileName = stackTrace[2].getFileName();
                String methodName = stackTrace[2].getMethodName();
                int lineNumber = stackTrace[2].getLineNumber();
                stringBuffer = new StringBuffer();
                stringBuffer.append("<");
                stringBuffer.append(fileName);
                stringBuffer.append(":");
                stringBuffer.append(methodName);
                stringBuffer.append(":");
                stringBuffer.append(lineNumber);
                stringBuffer.append("> ");
                stringBuffer.append(str);
            }
            return stringBuffer.toString();
        } catch (Throwable th) {
            return str;
        }
    }
}
