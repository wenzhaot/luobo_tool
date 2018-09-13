package com.tencent.liteav.beauty.b.b;

import android.util.Log;
import com.tencent.liteav.beauty.b.b;
import com.tencent.liteav.beauty.b.z;

/* compiled from: TXCBeauty3Filter */
public class a extends b {
    private b r = null;
    private z s = null;
    private String t = "TXCBeauty3Filter";
    private float u = 0.0f;
    private float v = 0.0f;
    private float w = 0.0f;
    private float x = 0.0f;

    public boolean c(int i, int i2) {
        return d(i, i2);
    }

    public void a(int i, int i2) {
        if (this.e != i || this.f != i2) {
            this.e = i;
            this.f = i2;
            d(i, i2);
        }
    }

    private boolean d(int i, int i2) {
        if (this.r == null) {
            this.r = new b();
            this.r.a(true);
            if (!this.r.c()) {
                Log.e(this.t, "m_verticalFilter init failed!!, break init");
                return false;
            }
        }
        this.r.a(i, i2);
        if (this.s == null) {
            this.s = new z();
            this.s.a(true);
            if (!this.s.c()) {
                Log.e(this.t, "mSharpnessFilter init failed!!, break init");
                return false;
            }
        }
        this.s.a(i, i2);
        return true;
    }

    public int a(int i) {
        if (this.u > 0.0f || this.v > 0.0f || this.w > 0.0f) {
            i = this.r.a(i);
        }
        if (this.x > 0.0f) {
            return this.s.a(i);
        }
        return i;
    }

    public void c(int i) {
        this.u = ((float) i) / 10.0f;
        if (this.r != null) {
            this.r.a(this.u);
        }
    }

    public void d(int i) {
        this.v = ((float) i) / 10.0f;
        if (this.r != null) {
            this.r.b(this.v);
        }
    }

    public void e(int i) {
        this.w = ((float) i) / 10.0f;
        if (this.r != null) {
            this.r.c(this.w);
        }
    }

    public void f(int i) {
        this.x = ((float) i) / 20.0f;
        if (this.s != null) {
            this.s.a(this.x);
        }
    }

    void r() {
        if (this.r != null) {
            this.r.b();
            this.r = null;
        }
        if (this.s != null) {
            this.s.b();
            this.s = null;
        }
    }

    public void b() {
        super.b();
        r();
    }
}
