package anet.channel.util;

import android.text.TextUtils;
import android.util.Log;
import com.taobao.tlog.adapter.AdapterForTLog;

/* compiled from: Taobao */
public class ALog {
    private static Object LOG_BREAK = "|";
    private static boolean isPrintLog = true;
    private static boolean isUseTlog = true;

    /* compiled from: Taobao */
    public static class a {
        public static final int D = 1;
        public static final int E = 4;
        public static final int I = 2;
        public static final int L = 5;
        public static final int V = 0;
        public static final int W = 3;

        public static int a(String str) {
            switch (str.charAt(0)) {
                case 'D':
                    return 1;
                case 'E':
                    return 4;
                case 'I':
                    return 2;
                case 'V':
                    return 0;
                case 'W':
                    return 3;
                default:
                    return 5;
            }
        }
    }

    public static void setPrintLog(boolean z) {
        isPrintLog = z;
    }

    public static void setUseTlog(boolean z) {
        isUseTlog = z;
    }

    @Deprecated
    public static void setEnableTLog(boolean z) {
        isUseTlog = z;
    }

    @Deprecated
    public static boolean isPrintLog() {
        return false;
    }

    public static boolean isPrintLog(int i) {
        if (!isPrintLog) {
            return false;
        }
        if (!isUseTlog || i >= a.a(AdapterForTLog.getLogLevel())) {
            return true;
        }
        return false;
    }

    public static void d(String str, String str2, String str3, Object... objArr) {
        if (!isPrintLog(1)) {
            return;
        }
        if (isUseTlog) {
            AdapterForTLog.logd(buildLogTag(str), buildLogMsg(str2, str3, objArr));
        } else {
            Log.d(buildLogTag(str), buildLogMsg(str2, str3, objArr));
        }
    }

    public static void i(String str, String str2, String str3, Object... objArr) {
        if (!isPrintLog(2)) {
            return;
        }
        if (isUseTlog) {
            AdapterForTLog.logi(buildLogTag(str), buildLogMsg(str2, str3, objArr));
        } else {
            Log.i(buildLogTag(str), buildLogMsg(str2, str3, objArr));
        }
    }

    public static void w(String str, String str2, String str3, Object... objArr) {
        if (!isPrintLog(3)) {
            return;
        }
        if (isUseTlog) {
            AdapterForTLog.logw(buildLogTag(str), buildLogMsg(str2, str3, objArr));
        } else {
            Log.w(buildLogTag(str), buildLogMsg(str2, str3, objArr));
        }
    }

    public static void w(String str, String str2, String str3, Throwable th, Object... objArr) {
        if (!isPrintLog(3)) {
            return;
        }
        if (isUseTlog) {
            AdapterForTLog.logw(buildLogTag(str), buildLogMsg(str2, str3, objArr), th);
        } else {
            Log.w(buildLogTag(str), buildLogMsg(str2, str3, objArr), th);
        }
    }

    public static void e(String str, String str2, String str3, Object... objArr) {
        if (!isPrintLog(4)) {
            return;
        }
        if (isUseTlog) {
            AdapterForTLog.loge(buildLogTag(str), buildLogMsg(str2, str3, objArr));
        } else {
            Log.e(buildLogTag(str), buildLogMsg(str2, str3, objArr));
        }
    }

    public static void e(String str, String str2, String str3, Throwable th, Object... objArr) {
        if (!isPrintLog(4)) {
            return;
        }
        if (isUseTlog) {
            AdapterForTLog.loge(buildLogTag(str), buildLogMsg(str2, str3, objArr), th);
        } else {
            Log.e(buildLogTag(str), buildLogMsg(str2, str3, objArr), th);
        }
    }

    private static String buildLogTag(String str) {
        return str;
    }

    static String buildLogMsg(String str, String str2, Object... objArr) {
        if (str == null && str2 == null && objArr == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (!TextUtils.isEmpty(str2)) {
            stringBuilder.append(LOG_BREAK).append("[seq:").append(str2).append("]");
        }
        if (str != null) {
            stringBuilder.append(" ").append(str);
        }
        if (objArr != null) {
            int i = 0;
            while (i + 1 < objArr.length) {
                stringBuilder.append(" ").append(objArr[i] != null ? objArr[i] : "").append(":").append(objArr[i + 1] != null ? objArr[i + 1] : "");
                i += 2;
            }
            if (i < objArr.length) {
                stringBuilder.append(" ");
                stringBuilder.append(objArr[i]);
            }
        }
        return stringBuilder.toString();
    }
}
