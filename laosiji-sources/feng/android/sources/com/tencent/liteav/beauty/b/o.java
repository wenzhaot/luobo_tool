package com.tencent.liteav.beauty.b;

import android.util.Log;
import com.tencent.liteav.k.f;
import com.tencent.liteav.k.n.c;

/* compiled from: TXCGPUGridShapeFilter */
public class o {
    private static String e = "GridShape";
    private f a = null;
    private e b = null;
    private x c = null;
    private aa d = null;
    private int f = 0;
    private int g = 0;
    private c h = null;

    public boolean a(int i, int i2) {
        return c(i, i2);
    }

    private boolean c(int i, int i2) {
        if (this.a == null) {
            this.a = new f();
            this.a.a(true);
            if (!this.a.c()) {
                Log.e(e, "mDissolveBlendFilter init Failed!");
                return false;
            }
        }
        if (this.a != null) {
            this.a.a(i, i2);
        }
        if (this.b == null) {
            this.b = new e();
            this.b.a(true);
            if (!this.b.c()) {
                Log.e(e, "mDissolveBlendFilter init Failed!");
                return false;
            }
        }
        if (this.b != null) {
            this.b.a(i, i2);
        }
        if (this.c == null) {
            this.c = new x();
            this.c.a(true);
            if (!this.c.c()) {
                Log.e(e, "mRotateScaleFilter init Failed!");
                return false;
            }
        }
        if (this.c != null) {
            this.c.a(i, i2);
        }
        return true;
    }

    private void b() {
        if (this.a != null) {
            this.a.e();
            this.a = null;
        }
        if (this.b != null) {
            this.b.e();
            this.b = null;
        }
        if (this.c != null) {
            this.c.e();
            this.c = null;
        }
    }

    public void a(c cVar) {
        this.h = cVar;
        if (this.a != null) {
            this.a.a(this.h);
        }
        if (this.c != null) {
            this.c.a(this.h.d);
        }
        if (this.b != null) {
            this.b.a(this.h.i);
            this.b.b(this.h.h);
        }
    }

    public void b(int i, int i2) {
        if (i != this.f || i2 != this.g) {
            c(i, i2);
        }
    }

    public int a(int i) {
        if (this.h == null) {
            return i;
        }
        if (this.a != null) {
            i = this.a.a(i);
        }
        if (this.c != null) {
            i = this.c.a(i);
        }
        if (this.b != null) {
            return this.b.a(i);
        }
        return i;
    }

    public void a() {
        b();
    }
}
