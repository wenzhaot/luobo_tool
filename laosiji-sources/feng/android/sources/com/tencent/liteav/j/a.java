package com.tencent.liteav.j;

import android.util.Log;

/* compiled from: FrameCounter */
public class a {
    private static boolean a = false;
    private static int b;
    private static int c;
    private static int d;
    private static int e;
    private static int f;
    private static int g;
    private static int h;
    private static boolean i = false;

    public static void a() {
        b++;
        if (a) {
            Log.w("FrameCounter", "decodeVideoCount:" + b);
        }
    }

    public static void b() {
        c++;
        if (a) {
            Log.w("FrameCounter", "decodeAudioCount:" + c);
        }
    }

    public static void c() {
        d++;
        if (a) {
            Log.w("FrameCounter", "processVideoCount:" + d);
        }
    }

    public static void d() {
        e++;
        if (a) {
            Log.w("FrameCounter", "processAudioCount:" + e);
        }
    }

    public static void e() {
        f++;
        if (a) {
            Log.w("FrameCounter", "renderVideoCount:" + f);
        }
    }

    public static void f() {
        g++;
        if (a) {
            Log.w("FrameCounter", "encodeVideoCount:" + g);
        }
    }

    public static void g() {
        h++;
        if (a) {
            Log.w("FrameCounter", "encodeAudioCount:" + h);
        }
    }

    public static void h() {
        i = true;
        b = 0;
        c = 0;
        d = 0;
        e = 0;
        f = 0;
        g = 0;
        h = 0;
    }
}
