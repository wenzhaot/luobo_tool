package com.baidu.mapapi.common;

import com.baidu.platform.comapi.util.e;

public class SysOSUtil {
    public static float getDensity() {
        return e.y;
    }

    public static int getDensityDpi() {
        return e.l();
    }

    public static String getDeviceID() {
        return e.o();
    }

    public static String getModuleFileName() {
        return e.n();
    }

    public static String getPhoneType() {
        return e.g();
    }

    public static int getScreenSizeX() {
        return e.h();
    }

    public static int getScreenSizeY() {
        return e.j();
    }
}
