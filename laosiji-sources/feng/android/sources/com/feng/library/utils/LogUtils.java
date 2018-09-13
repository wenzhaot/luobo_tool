package com.feng.library.utils;

import android.util.Log;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LogUtils {
    private static final char A = 'A';
    private static final String BOTTOM_BORDER = "╚════════════════════════════════════════════════════════════════════════════════════════";
    private static final char BOTTOM_LEFT_CORNER = '╚';
    private static final char D = 'D';
    private static final String DOUBLE_DIVIDER = "════════════════════════════════════════════";
    private static final char E = 'E';
    private static final char HORIZONTAL_DOUBLE_LINE = '║';
    private static final char I = 'I';
    static int JSON_INDENT = 4;
    static String LINE_SEPARATOR = System.getProperty("line.separator");
    private static final char M = 'M';
    private static final String MIDDLE_BORDER = "╟────────────────────────────────────────────────────────────────────────────────────────";
    private static final char MIDDLE_CORNER = '╟';
    private static final String SINGLE_DIVIDER = "────────────────────────────────────────────";
    private static final String TAG = "lyy";
    private static final String TOP_BORDER = "╔════════════════════════════════════════════════════════════════════════════════════════";
    private static final char TOP_LEFT_CORNER = '╔';
    private static final char V = 'V';
    private static final char W = 'W';
    public static boolean isDebug = true;

    private LogUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static String m2s(Map map) {
        if (!isDebug) {
            return "";
        }
        if (map == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Entry entry : map.entrySet()) {
            sb.append(entry.getValue());
        }
        return sb.toString();
    }

    public static void m(Map map) {
        if (isDebug) {
            Set<Entry> set = map.entrySet();
            if (set.size() < 1) {
                printLog(D, "[]");
                return;
            }
            int i = 0;
            String[] s = new String[set.size()];
            for (Entry entry : set) {
                s[i] = entry.getKey() + " = " + entry.getValue() + ",\n";
                i++;
            }
            printLog(V, s);
        }
    }

    public static void j(String jsonStr) {
        if (isDebug) {
            String message;
            try {
                if (jsonStr.startsWith("{")) {
                    message = new JSONObject(jsonStr).toString(JSON_INDENT);
                } else if (jsonStr.startsWith("[")) {
                    message = new JSONArray(jsonStr).toString(JSON_INDENT);
                } else {
                    message = jsonStr;
                }
            } catch (JSONException e) {
                message = jsonStr;
            }
            printLog(D, (LINE_SEPARATOR + message).split(LINE_SEPARATOR));
        }
    }

    public static void i(String... msg) {
        if (isDebug) {
            printLog(I, msg);
        }
    }

    public static void d(String... msg) {
        if (isDebug) {
            printLog(D, msg);
        }
    }

    public static void w(String... msg) {
        if (isDebug) {
            printLog(W, msg);
        }
    }

    public static void e(String... msg) {
        if (isDebug) {
            printLog(E, msg);
        }
    }

    public static void v(String... msg) {
        if (isDebug) {
            printLog(V, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (isDebug) {
            Log.i(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (isDebug) {
            Log.d(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (isDebug) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (isDebug) {
            Log.e(tag, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (isDebug) {
            Log.v(tag, msg);
        }
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (isDebug) {
            Log.i(tag, msg, tr);
        }
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (isDebug) {
            Log.d(tag, msg, tr);
        }
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (isDebug) {
            Log.w(tag, msg, tr);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (isDebug) {
            Log.e(tag, msg, tr);
        }
    }

    public static void v(String tag, String msg, Throwable tr) {
        if (isDebug) {
            Log.v(tag, msg, tr);
        }
    }

    private static void printHunk(char type, String str) {
        switch (type) {
            case 'A':
                Log.wtf(TAG, str);
                return;
            case 'D':
                Log.d(TAG, str);
                return;
            case 'E':
                Log.e(TAG, str);
                return;
            case 'I':
                Log.i(TAG, str);
                return;
            case 'V':
                Log.v(TAG, str);
                return;
            case 'W':
                Log.w(TAG, str);
                return;
            default:
                return;
        }
    }

    private static void printHead(char type) {
        printHunk(type, TOP_BORDER);
        printHunk(type, "║   Thread:");
        printHunk(type, "║   " + Thread.currentThread().getName());
        printHunk(type, MIDDLE_BORDER);
    }

    private static void printLocation(char type, String... msg) {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        int i = 0;
        int length = stack.length;
        int i2 = 0;
        while (i2 < length && !stack[i2].getClassName().equals(LogUtils.class.getName())) {
            i++;
            i2++;
        }
        i += 3;
        String className = stack[i].getFileName();
        String methodName = stack[i].getMethodName();
        int lineNumber = stack[i].getLineNumber();
        StringBuilder sb = new StringBuilder();
        printHunk(type, "║   Location:");
        sb.append(HORIZONTAL_DOUBLE_LINE).append("   (").append(className).append(":").append(lineNumber).append(")# ").append(methodName);
        printHunk(type, sb.toString());
        String str = (msg == null || msg.length == 0) ? BOTTOM_BORDER : MIDDLE_BORDER;
        printHunk(type, str);
    }

    private static void printMsg(char type, String... msg) {
        printHunk(type, "║   msg:");
        for (String str : msg) {
            printHunk(type, "║   " + str);
        }
        printHunk(type, BOTTOM_BORDER);
    }

    private static void printLog(char type, String... msg) {
        printHead(type);
        printLocation(type, msg);
        if (msg != null && msg.length != 0) {
            printMsg(type, msg);
        }
    }
}
