package com.tencent.liteav.l;

import android.opengl.GLES20;
import android.util.Log;
import com.tencent.liteav.basic.c.d;
import com.tencent.liteav.basic.c.f;
import com.tencent.liteav.basic.c.f.a;
import com.tencent.liteav.basic.c.h;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.beauty.b.ag;
import com.tencent.liteav.beauty.b.u;
import com.tencent.liteav.beauty.e;

/* compiled from: TXCCombineVideoFilter */
public class b {
    d a = null;
    d b = null;
    protected a[] c = null;
    protected a d = null;
    protected int[] e = null;
    private ag f = null;
    private u g = null;
    private final int h = 2;
    private final int i = 3;
    private int j = 0;
    private int k = 0;
    private int l = 0;
    private int m = 0;
    private com.tencent.liteav.basic.c.a n = null;
    private float[] o = new float[]{0.0f, 0.0f, 0.0f, 0.0f};
    private e p = null;
    private String q = "CombineVideoFilter";

    public void a(int i, int i2) {
        if (i > 0 && i2 > 0 && !(i == this.j && i2 == this.k)) {
            f.a(this.c);
            this.c = null;
        }
        this.j = i;
        this.k = i2;
    }

    public void b(int i, int i2) {
        if (i > 0 && i2 > 0 && !(i == this.l && i2 == this.m)) {
            b();
        }
        this.l = i;
        this.m = i2;
    }

    public void a(com.tencent.liteav.basic.c.a aVar) {
        this.n = aVar;
    }

    public int a(com.tencent.liteav.basic.f.a[] aVarArr, int i) {
        if (aVarArr == null || this.j <= 0 || this.k <= 0) {
            Log.e(this.q, "frames or canvaceSize if null!");
            return -1;
        }
        int i2;
        int i3;
        int a;
        a(aVarArr);
        if (this.f != null) {
            i2 = 0;
            for (i3 = 0; i3 < aVarArr.length; i3++) {
                GLES20.glBindFramebuffer(36160, this.c[i3].a[0]);
                GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                GLES20.glClear(16640);
                com.tencent.liteav.beauty.d.d[] dVarArr = new com.tencent.liteav.beauty.d.d[]{new com.tencent.liteav.beauty.d.d()};
                dVarArr[0].e = aVarArr[i3].a;
                dVarArr[0].f = aVarArr[i3].g.c;
                dVarArr[0].g = aVarArr[i3].g.d;
                dVarArr[0].b = (((float) aVarArr[i3].g.a) * 1.0f) / ((float) this.j);
                dVarArr[0].c = (((float) aVarArr[i3].g.b) * 1.0f) / ((float) this.k);
                dVarArr[0].d = (((float) aVarArr[i3].g.c) * 1.0f) / ((float) this.j);
                if (aVarArr[i3].e != null) {
                    this.f.a(aVarArr[i3].e.c);
                    this.f.c(aVarArr[i3].e.d);
                }
                this.f.a(dVarArr);
                GLES20.glViewport(0, 0, this.j, this.k);
                if (i3 == 0) {
                    this.f.b(this.d.b[0]);
                } else {
                    this.f.b(this.c[i3 - 1].b[0]);
                }
                GLES20.glBindFramebuffer(36160, 0);
                i2 = i3;
            }
        } else {
            i2 = 0;
        }
        i3 = this.c[i2].b[0];
        i2 = this.j;
        int i4 = this.k;
        if (!(this.b == null || this.n == null)) {
            GLES20.glViewport(0, 0, this.n.c, this.n.d);
            i3 = this.b.a(i3);
        }
        if (this.a != null) {
            GLES20.glViewport(0, 0, this.l, this.m);
            a = this.a.a(i3);
            i2 = this.l;
            i4 = this.m;
        } else {
            a = i3;
        }
        if (this.p == null) {
            return a;
        }
        this.p.didProcessFrame(a, i2, i4, (long) i);
        return a;
    }

    public void a() {
        f.a(this.c);
        this.c = null;
        d();
        if (this.d != null) {
            f.a(this.d);
            this.d = null;
        }
    }

    private void a(int i, int i2, int i3) {
        if (i > 0 && i2 > 0) {
            if (this.c == null || i3 != this.c.length) {
                f.a(this.c);
                this.c = null;
                this.c = f.a(this.c, i3, i, i2);
                if (this.e == null) {
                    this.e = new int[1];
                    this.e[0] = f.a(i, i2, 6408, 6408, this.e);
                }
                if (this.d != null) {
                    f.a(this.d);
                    this.d = null;
                }
                if (this.d == null) {
                    this.d = f.a(this.d, i, i2);
                }
                if (this.g != null) {
                    GLES20.glBindFramebuffer(36160, this.d.a[0]);
                    GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                    GLES20.glClear(16640);
                    this.g.b(this.o);
                    this.g.b(-1);
                    GLES20.glBindFramebuffer(36160, 0);
                }
            }
        }
    }

    private void c(int i, int i2) {
        if (this.a == null) {
            this.a = new d();
            this.a.a(true);
            if (!this.a.c()) {
                TXCLog.e(this.q, "mOutputFilter.init failed!");
                return;
            }
        }
        if (this.a != null) {
            this.a.a(i, i2);
        }
    }

    private void d(int i, int i2) {
        if (this.f == null) {
            this.f = new ag();
            this.f.a(true);
            if (!this.f.c()) {
                TXCLog.e(this.q, "TXCGPUWatermarkTextureFilter.init failed!");
                return;
            }
        }
        if (this.f != null) {
            this.f.a(i, i2);
        }
    }

    private boolean e(int i, int i2) {
        if (this.b == null) {
            this.b = new d();
            this.b.a(true);
            if (!this.b.c()) {
                TXCLog.e(this.q, "mCropFilter.init failed!");
                return false;
            }
        }
        if (this.b == null) {
            return true;
        }
        this.b.a(i, i2);
        return true;
    }

    private void b() {
        if (this.a != null) {
            this.a.e();
            this.a = null;
        }
    }

    private void c() {
        if (this.b != null) {
            this.b.e();
            this.b = null;
        }
    }

    private void a(com.tencent.liteav.basic.f.a[] aVarArr) {
        d(this.j, this.k);
        if (this.g == null) {
            this.g = new u();
            if (!this.g.c()) {
                TXCLog.e(this.q, "mCropFilter.init failed!");
                return;
            }
        }
        if (this.g != null) {
            this.g.a(this.j, this.k);
        }
        a(this.j, this.k, aVarArr.length);
        if (this.n != null) {
            e(this.n.c, this.n.d);
            if (this.b != null) {
                this.b.a(h.e, this.b.a(this.j, this.k, null, this.n, 0));
            }
        } else {
            c();
        }
        if (this.l > 0 && this.m > 0) {
            c(this.l, this.m);
        }
    }

    private void d() {
        if (this.f != null) {
            this.f.e();
            this.f = null;
        }
        b();
        if (this.b != null) {
            this.b.e();
            this.b = null;
        }
    }
}
