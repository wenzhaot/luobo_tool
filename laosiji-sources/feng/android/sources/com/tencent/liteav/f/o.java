package com.tencent.liteav.f;

import com.tencent.liteav.i.a.i;
import java.util.List;

/* compiled from: VideoConfig */
public class o {
    private static o a;
    private long b;
    private float c;
    private long d;
    private long e;
    private float f;
    private float g;
    private List<i> h;
    private int i;
    private int j;

    public static o a() {
        if (a == null) {
            a = new o();
        }
        return a;
    }

    private o() {
        this.d = -1;
        this.e = -1;
        this.f = 1.0f;
        this.g = 1.0f;
        this.b = 0;
        this.c = 1.0f;
        this.d = -1;
        this.e = -1;
        this.f = 1.0f;
        this.g = 1.0f;
    }

    public List<i> b() {
        return this.h;
    }

    public int c() {
        return this.i;
    }

    public int d() {
        return this.j;
    }
}
