package com.talkingdata.sdk;

import android.os.Handler;
import android.os.HandlerThread;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: td */
public class cs {
    private static List a = new ArrayList();
    private static Handler b;
    private static HandlerThread c;

    static {
        try {
            c = new HandlerThread("excHandlerThread");
            c.start();
            b = new Handler(c.getLooper());
        } catch (Throwable th) {
        }
    }

    public static void postSDKError(Throwable th) {
        try {
            b.post(new ct(th));
        } catch (Throwable th2) {
        }
    }

    public static void a(boolean z, Map map) {
        try {
            b.post(new cu(map, z));
        } catch (Throwable th) {
        }
    }

    private static final String b(Throwable th) {
        int i = 50;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(th.toString());
        stringBuilder.append("\r\n");
        try {
            StackTraceElement[] stackTrace = th.getStackTrace();
            if (stackTrace.length <= 50) {
                i = stackTrace.length;
            }
            for (int i2 = 0; i2 < i; i2++) {
                stringBuilder.append("\t");
                stringBuilder.append(stackTrace[i2]);
                stringBuilder.append("\r\n");
            }
            Throwable cause = th.getCause();
            if (cause != null) {
                a(stringBuilder, stackTrace, cause, 1);
            }
        } catch (Throwable th2) {
        }
        return stringBuilder.toString();
    }

    private static final void a(StringBuilder stringBuilder, StackTraceElement[] stackTraceElementArr, Throwable th, int i) {
        try {
            StackTraceElement[] stackTrace = th.getStackTrace();
            int length = stackTrace.length - 1;
            int length2 = stackTraceElementArr.length - 1;
            while (length >= 0 && length2 >= 0 && stackTrace[length].equals(stackTraceElementArr[length2])) {
                length2--;
                length--;
            }
            if (length > 50) {
                length = 50;
            }
            stringBuilder.append("Caused by : ");
            stringBuilder.append(th);
            stringBuilder.append("\r\n");
            for (int i2 = 0; i2 <= length; i2++) {
                stringBuilder.append("\t");
                stringBuilder.append(stackTrace[i2]);
                stringBuilder.append("\r\n");
            }
            if (i < 5 && th.getCause() != null) {
                a(stringBuilder, stackTrace, th, i + 1);
            }
        } catch (Throwable th2) {
        }
    }
}
