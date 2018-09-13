package com.talkingdata.sdk;

import android.util.Log;
import com.feng.car.video.shortvideo.FileUtils;

/* compiled from: td */
public class aq {
    public static boolean a = true;
    private static final boolean b = false;

    public static void iForInternal(String... strArr) {
    }

    public static void dForInternal(String... strArr) {
    }

    public static void eForInternal(String... strArr) {
    }

    public static void eForInternal(Throwable th) {
    }

    public static void json(String str) {
    }

    public static void iForDeveloper(String str) {
        if (a) {
            a(str, 4);
        }
    }

    public static void dForDeveloper(String str) {
        if (a) {
            a(str, 3);
        }
    }

    public static void eForDeveloper(String str) {
        if (a) {
            a(str, 6);
        }
    }

    public static void a(String str, Throwable th) {
        if (a) {
            a(str, 4);
            th.printStackTrace();
        }
    }

    private static void a(String str, int i) {
        int i2 = 0;
        if (str != null) {
            try {
                int length = str.length();
                int i3 = 2000;
                int i4 = 0;
                while (i2 < 100) {
                    if (length > i3) {
                        b(str.substring(i4, i3), i);
                        i2++;
                        i4 = i3;
                        i3 += 2000;
                    } else {
                        b(str.substring(i4, length), i);
                        return;
                    }
                }
            } catch (Throwable th) {
                cs.postSDKError(th);
            }
        }
    }

    private static void b(String str, int i) {
        String a = a();
        switch (i) {
            case 2:
                Log.v(a, str);
                return;
            case 3:
                Log.d(a, str);
                return;
            case 4:
                Log.i(a, str);
                return;
            case 5:
                Log.w(a, str);
                return;
            case 6:
                Log.e(a, str);
                return;
            default:
                return;
        }
    }

    private static synchronized String a() {
        String str;
        synchronized (aq.class) {
            try {
                new Exception().getStackTrace()[4].getClassName().lastIndexOf(FileUtils.FILE_EXTENSION_SEPARATOR);
                str = ab.s;
            } catch (Throwable th) {
                cs.postSDKError(th);
                str = ab.s;
            }
        }
        return str;
    }
}
