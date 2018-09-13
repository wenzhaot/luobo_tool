package com.tencent.liteav.k;

import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.beauty.b.aj;
import com.tencent.liteav.beauty.d.d;
import com.tencent.liteav.k.n.l;

/* compiled from: TXCGPUSpiritOutFilter */
public class j {
    private static String e = "SpiritOut";
    protected aj a = null;
    protected l b = null;
    d[] c = null;
    private l d = null;
    private int f = -1;
    private int g = -1;

    public boolean a(int i, int i2) {
        return c(i, i2);
    }

    private boolean c(int i, int i2) {
        if (i == this.f && i2 == this.g) {
            return true;
        }
        this.f = i;
        this.g = i2;
        if (this.a == null) {
            this.a = new aj();
            this.a.a(true);
            if (!this.a.c()) {
                TXCLog.e(e, "mZoomInOutFilter init error!");
                return false;
            }
        }
        this.a.a(i, i2);
        if (this.d == null) {
            this.d = new l();
            this.d.a(true);
            if (!this.d.c()) {
                TXCLog.e(e, "mTextureWaterMarkFilter init error!");
                return false;
            }
        }
        this.d.a(i, i2);
        return true;
    }

    private void b() {
        if (this.a != null) {
            this.a.e();
            this.a = null;
        }
        if (this.d != null) {
            this.d.e();
            this.d = null;
        }
    }

    public void a(l lVar) {
        this.b = lVar;
    }

    public int a(int i) {
        if (this.b == null || this.a == null) {
            return i;
        }
        this.a.a(0.96f, this.b.g);
        this.a.a(this.b.h);
        int i2 = i;
        for (int i3 = 0; i3 < this.b.f; i3++) {
            if (i3 >= 1) {
                this.a.a(0.9f, this.b.g + i3);
            }
            d[] dVarArr = new d[]{new d()};
            dVarArr[0].e = this.a.a(i);
            dVarArr[0].f = this.f;
            dVarArr[0].g = this.g;
            dVarArr[0].b = 0.0f;
            dVarArr[0].c = 0.0f;
            dVarArr[0].d = 1.0f;
            if (this.d != null) {
                this.d.a(dVarArr);
                i2 = this.d.a(i2);
            }
        }
        return i2;
    }

    public void b(int i, int i2) {
        c(i, i2);
    }

    public void a() {
        b();
    }
}
