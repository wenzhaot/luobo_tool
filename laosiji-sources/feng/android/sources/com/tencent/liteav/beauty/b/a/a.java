package com.tencent.liteav.beauty.b.a;

import android.opengl.GLES20;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.beauty.b.b;
import com.tencent.liteav.beauty.b.k;
import com.tencent.liteav.beauty.b.y;

/* compiled from: TXCBeauty2Filter */
public class a extends b {
    private int A = 0;
    private float B = 1.0f;
    private final float C = 0.7f;
    private float D = 0.8f;
    private float E = 2.0f;
    private int F = 0;
    private int G = 0;
    private int H = 0;
    private int I = 0;
    private c r = null;
    private d s = null;
    private e t = null;
    private k u = null;
    private y v = null;
    private b w = null;
    private String x = "TXCBeauty2Filter";
    private int y = 0;
    private int z = 0;

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

    public void c(int i) {
        if (this.t != null) {
            this.t.a(((float) i) / 10.0f);
        }
        this.y = i;
        g(i);
    }

    public void d(int i) {
        if (this.w != null) {
            this.w.a(((float) i) / 10.0f);
        }
        this.z = i;
    }

    public void e(int i) {
        if (this.w != null) {
            this.w.b(((float) i) / 10.0f);
        }
        this.A = i;
    }

    private void g(int i) {
        this.B = 1.0f - (((float) i) / 50.0f);
        if (this.u != null) {
            this.u.a(this.B);
        }
    }

    public void f(int i) {
        this.D = 0.7f + (((float) i) / 12.0f);
        TXCLog.i(this.x, "set mSharpenLevel " + i);
        if (this.v != null) {
            this.v.a(this.D);
        }
    }

    public int a(int i) {
        if (1.0f != this.E) {
            GLES20.glViewport(0, 0, this.H, this.I);
        }
        int c = this.t.c(this.s.a(i), i);
        if (1.0f != this.E) {
            GLES20.glViewport(0, 0, this.F, this.G);
        }
        if (this.D > 0.7f) {
            c = this.v.a(c);
        }
        return this.w.c(c, i);
    }

    private boolean d(int i, int i2) {
        this.F = i;
        this.G = i2;
        this.H = i;
        this.I = i2;
        if (1.0f != this.E) {
            this.H = (int) (((float) this.H) / this.E);
            this.I = (int) (((float) this.I) / this.E);
        }
        TXCLog.i(this.x, "mResampleRatio " + this.E + " mResampleWidth " + this.H + " mResampleHeight " + this.I);
        if (this.w == null) {
            this.w = new b();
            this.w.a(true);
            if (!this.w.c()) {
                TXCLog.e(this.x, "mBeautyBlendFilter init failed!!, break init");
                return false;
            }
        }
        this.w.a(i, i2);
        if (this.s == null) {
            this.s = new d();
            this.s.a(true);
            if (!this.s.c()) {
                TXCLog.e(this.x, "m_horizontalFilter init failed!!, break init");
                return false;
            }
        }
        this.s.a(this.H, this.I);
        if (this.t == null) {
            boolean z;
            this.t = new e();
            this.t.a(true);
            if (1.0f == this.E) {
                z = false;
            } else {
                z = true;
            }
            this.t.b(z);
            if (!this.t.c()) {
                TXCLog.e(this.x, "m_verticalFilter init failed!!, break init");
                return false;
            }
        }
        this.t.a(this.H, this.I);
        if (this.u == null) {
            this.u = new k(1.0f);
            this.u.a(true);
            if (!this.u.c()) {
                TXCLog.e(this.x, "m_gammaFilter init failed!!, break init");
                return false;
            }
        }
        this.u.a(this.H, this.I);
        if (this.v == null) {
            this.v = new y();
            this.v.a(true);
            if (!this.v.c()) {
                TXCLog.e(this.x, "mSharpenFilter init failed!!, break init");
                return false;
            }
        }
        this.v.a(i, i2);
        return true;
    }

    void r() {
        if (this.w != null) {
            this.w.e();
            this.w = null;
        }
        if (this.s != null) {
            this.s.e();
            this.s = null;
        }
        if (this.t != null) {
            this.t.e();
            this.t = null;
        }
        if (this.u != null) {
            this.u.e();
            this.u = null;
        }
        if (this.v != null) {
            this.v.e();
            this.v = null;
        }
    }

    public void b() {
        super.b();
        r();
    }
}
