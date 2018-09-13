package com.tencent.liteav.k;

import android.util.Log;
import com.tencent.liteav.beauty.b.h;
import com.tencent.liteav.beauty.c;
import com.tencent.liteav.k.n.f;

/* compiled from: TXCGPUGhostShadowFilter */
public class e {
    private static String c = "GhostShadow";
    f a = null;
    private h b = null;
    private c d = null;
    private int e = 0;
    private int f = 0;

    public boolean a(int i, int i2) {
        this.e = i;
        this.f = i2;
        return c(i, i2);
    }

    private boolean c(int i, int i2) {
        if (this.b == null) {
            this.b = new h();
            this.b.a(true);
            if (!this.b.c()) {
                Log.e(c, "mDissolveBlendFilter init Failed!");
                return false;
            }
        }
        if (this.b != null) {
            this.b.a(i, i2);
        }
        if (this.d == null) {
            this.d = new c();
            if (!this.d.a(i, i2)) {
                Log.e(c, "mDissolveBlendFilter init Failed!");
                return false;
            }
        }
        if (this.d != null) {
            this.d.b(i, i2);
        }
        return true;
    }

    private void b() {
        if (this.b != null) {
            this.b.e();
            this.b = null;
        }
        if (this.d != null) {
            this.d.b();
            this.d = null;
        }
    }

    public void a(f fVar) {
        this.a = fVar;
        if (fVar == null) {
            Log.i(c, "GhostShadowParam is null, reset list");
            if (this.d != null) {
                this.d.a();
            }
        }
    }

    /* JADX WARNING: Missing block: B:5:0x0017, code:
            if (r0 > 0) goto L_0x0019;
     */
    public int a(int r4) {
        /*
        r3 = this;
        r0 = r3.a;
        r1 = r3.e;
        r2 = r3.f;
        r0 = r3.a(r0, r1, r2);
        if (r0 != 0) goto L_0x000d;
    L_0x000c:
        return r4;
    L_0x000d:
        r0 = r3.d;
        if (r0 == 0) goto L_0x0024;
    L_0x0011:
        r0 = r3.d;
        r0 = r0.a(r4);
        if (r0 <= 0) goto L_0x0024;
    L_0x0019:
        r1 = r3.b;
        if (r1 == 0) goto L_0x000c;
    L_0x001d:
        r1 = r3.b;
        r4 = r1.c(r4, r0);
        goto L_0x000c;
    L_0x0024:
        r0 = r4;
        goto L_0x0019;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.k.e.a(int):int");
    }

    private boolean a(f fVar, int i, int i2) {
        if (fVar == null) {
            return false;
        }
        if (this.d != null) {
            this.d.b(fVar.a);
        }
        return true;
    }

    public void b(int i, int i2) {
        c(i, i2);
    }

    public void a() {
        b();
    }
}
