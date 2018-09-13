package com.feng.library.utils;

import android.os.Build.VERSION;

public class AndroidVersionUtil {
    private AndroidVersionUtil() {
    }

    public static boolean hasDonut() {
        return VERSION.SDK_INT >= 4;
    }

    public static boolean hasEclair() {
        return VERSION.SDK_INT >= 5;
    }

    public static boolean hasFroyo() {
        return VERSION.SDK_INT >= 8;
    }

    public static boolean hasGingerbread() {
        return VERSION.SDK_INT >= 9;
    }

    public static boolean hasHoneycomb() {
        return VERSION.SDK_INT >= 11;
    }

    public static boolean hasHoneycombMR1() {
        return VERSION.SDK_INT >= 12;
    }

    public static boolean hasIcecreamsandwich() {
        return VERSION.SDK_INT >= 14;
    }

    public static boolean hasJellyBean() {
        return VERSION.SDK_INT >= 16;
    }

    public static boolean hasKitKat() {
        return VERSION.SDK_INT >= 19;
    }

    public static boolean hasLollipop() {
        return VERSION.SDK_INT >= 21;
    }

    public static boolean hasM() {
        return VERSION.SDK_INT >= 23;
    }
}
