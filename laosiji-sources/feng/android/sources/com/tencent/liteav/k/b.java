package com.tencent.liteav.k;

import android.opengl.GLES20;
import android.util.Log;
import com.tencent.liteav.basic.c.d;
import com.tencent.liteav.beauty.b.f;
import com.tencent.liteav.beauty.b.n;
import com.tencent.liteav.beauty.b.o;
import com.tencent.liteav.beauty.c;
import com.tencent.liteav.k.n.c.a;

/* compiled from: TXCGPUDiffuseFilter */
public class b {
    private static String j = "Diffuse";
    private c a = null;
    private o b = null;
    private m c = null;
    private m d = null;
    private n e = null;
    private d f = null;
    private f g = null;
    private int h = 0;
    private int i = 0;
    private int k = 1;
    private n.c l = null;
    private a m = a.MODE_NONE;
    private a n = a.MODE_NONE;
    private int[] o = null;
    private com.tencent.liteav.basic.c.f.a p = null;
    private com.tencent.liteav.basic.c.f.a q = null;
    private float r = 0.0f;

    public boolean a(int i, int i2) {
        this.h = i;
        this.i = i2;
        if (!(this.p != null && i == this.p.c && i2 == this.p.d)) {
            this.p = com.tencent.liteav.basic.c.f.a(this.p);
            this.p = com.tencent.liteav.basic.c.f.a(this.p, i, i2);
        }
        if (!(this.q != null && i == this.q.c && i2 == this.q.d)) {
            this.q = com.tencent.liteav.basic.c.f.a(this.q);
            this.q = com.tencent.liteav.basic.c.f.a(this.q, i, i2);
        }
        return c(i, i2);
    }

    private boolean c(int i, int i2) {
        if (this.a == null) {
            this.a = new c();
            if (!this.a.a(i, i2)) {
                Log.e(j, "mDissolveBlendFilter init Failed!");
                return false;
            }
        }
        if (this.a != null) {
            this.a.b(this.k);
            this.a.b(i, i2);
        }
        if (this.b == null) {
            this.b = new o();
            if (!this.b.a(i, i2)) {
                Log.e(j, "mGridShapeFilter init Failed!");
                return false;
            }
        }
        if (this.b != null) {
            this.b.b(i, i2);
        }
        if (this.c == null) {
            this.c = new m();
            this.c.a(true);
            if (!this.c.c()) {
                Log.e(j, "mScaleFilter init Failed!");
                return false;
            }
        }
        if (this.c != null) {
            this.c.a(i, i2);
        }
        if (this.d == null) {
            this.d = new m();
            this.d.a(true);
            if (!this.d.c()) {
                Log.e(j, "mScaleFilter2 init Failed!");
                return false;
            }
        }
        if (this.d != null) {
            this.d.a(i, i2);
        }
        if (this.e == null) {
            this.e = new n();
            this.e.a(true);
            if (!this.e.c()) {
                Log.e(j, "mGridShapeFilter init Failed!");
                return false;
            }
        }
        if (this.e != null) {
            this.e.a(i, i2);
        }
        if (this.f == null) {
            this.f = new d();
            if (!this.f.c()) {
                Log.e(j, "mDrawFilter init Failed!");
                return false;
            }
        }
        if (this.f != null) {
            this.f.a(i, i2);
        }
        if (this.g == null) {
            this.g = new f();
            if (!this.g.c()) {
                Log.e(j, "mColorBrushFilter init Failed!");
                return false;
            }
        }
        if (this.g != null) {
            this.g.a(i, i2);
        }
        return true;
    }

    public void b(int i, int i2) {
        if (i != this.h || i2 != this.i) {
            a(i, i2);
        }
    }

    private void b() {
        if (this.a != null) {
            this.a.b();
            this.a = null;
        }
        if (this.b != null) {
            this.b.a();
            this.b = null;
        }
        if (this.c != null) {
            this.c.e();
            this.c = null;
        }
        if (this.e != null) {
            this.e.e();
            this.e = null;
        }
        if (this.f != null) {
            this.f.e();
            this.f = null;
        }
        if (this.g != null) {
            this.g.e();
            this.g = null;
        }
    }

    private void c() {
        if (this.q != null) {
            com.tencent.liteav.basic.c.f.a(this.q);
            this.q = null;
        }
        if (this.p != null) {
            com.tencent.liteav.basic.c.f.a(this.p);
            this.p = null;
        }
        if (this.o != null) {
            GLES20.glDeleteTextures(1, this.o, 0);
            this.o = null;
        }
    }

    public void a(n.c cVar) {
        this.l = cVar;
        if (this.l != null) {
            if (this.b != null) {
                this.b.a(this.l);
            }
            if (this.c != null) {
                this.c.a(this.l.j);
            }
            if (a.MODE_ZOOM_IN == cVar.g) {
                this.r = cVar.j;
            }
        }
    }

    public int a(int i) {
        if (this.l == null) {
            return i;
        }
        int a;
        int a2;
        if (this.o == null) {
            this.o = new int[1];
            this.o[0] = com.tencent.liteav.basic.c.f.a(this.h, this.i, 6408, 6408, this.o);
        }
        if (this.b != null) {
            a = this.b.a(i);
        } else {
            a = i;
        }
        if (this.c != null) {
            a2 = this.c.a(i);
        } else {
            a2 = i;
        }
        if (this.a != null) {
            int a3;
            if (a.MODE_ZOOM_OUT == this.l.g) {
                this.d.a(this.r);
                int a4 = this.d.a(i);
                a3 = this.a.a(a4);
                if (a3 > 0) {
                    i = a3;
                }
                if (a.MODE_ZOOM_IN == this.m) {
                    i = a4;
                }
            } else {
                a3 = this.a.a(i);
                if (a3 <= 0) {
                    a3 = i;
                }
                if (a.MODE_ZOOM_OUT != this.m) {
                    i = a3;
                }
            }
            this.m = this.l.g;
        }
        if (this.q != null) {
            GLES20.glBindFramebuffer(36160, this.q.a[0]);
            if (true == this.l.k) {
                this.g.d(this.o[0], a);
            } else {
                this.g.d(this.q.b[0], a);
            }
            GLES20.glBindFramebuffer(36160, 0);
        }
        GLES20.glBindFramebuffer(36160, this.p.a[0]);
        if (!(this.e == null || this.q == null)) {
            this.e.b(this.q.b[0], i, a2);
        }
        GLES20.glBindFramebuffer(36160, 0);
        return this.p.b[0];
    }

    public void a() {
        b();
        c();
    }
}
