package com.tencent.liteav.b;

import com.tencent.liteav.c.c;
import com.tencent.liteav.c.d;
import com.tencent.liteav.c.h;

/* compiled from: VideoPreProcessConfig */
public class j {
    private static j b;
    public float a;
    private com.tencent.liteav.c.j c;
    private h d;
    private c e;
    private d f;

    public static j a() {
        if (b == null) {
            b = new j();
        }
        return b;
    }

    private j() {
        f();
    }

    private void f() {
        e();
    }

    public void a(com.tencent.liteav.c.j jVar) {
        this.c = jVar;
    }

    public com.tencent.liteav.c.j b() {
        return this.c;
    }

    public void a(c cVar) {
        this.e = cVar;
    }

    public c c() {
        return this.e;
    }

    public d d() {
        return this.f;
    }

    public void a(d dVar) {
        this.f = dVar;
    }

    public void e() {
        this.a = 1.0f;
        if (this.c != null) {
            this.c.b();
        }
        this.c = null;
        if (this.d != null) {
            this.d.a();
        }
        if (this.f != null) {
            this.f.a();
        }
        this.d = null;
        this.e = null;
    }
}
