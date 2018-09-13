package com.umeng.message.util;

import android.os.Build;
import java.io.IOException;

/* compiled from: OSUtils */
public class d {
    private static final String a = "ro.build.version.emui";
    private static final String b = "ro.miui.ui.version.code";
    private static final String c = "ro.miui.ui.version.name";
    private static final String d = "ro.miui.internal.storage";

    private static boolean a(String... strArr) {
        try {
            a g = a.g();
            for (String a : strArr) {
                if (g.a(a) == null) {
                    return false;
                }
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean a() {
        return a(a);
    }

    public static boolean b() {
        return a(b, c, d);
    }

    public static boolean c() {
        try {
            if (Build.class.getMethod("hasSmartBar", new Class[0]) != null) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
