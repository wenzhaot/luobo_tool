package com.tencent.liteav.e;

import android.content.Context;
import com.feng.car.utils.FengConstant;
import com.feng.car.utils.PermissionsConstant;
import com.talkingdata.sdk.ab;
import com.tencent.liteav.b.d;
import com.tencent.liteav.b.g;
import com.tencent.liteav.c.f;
import com.tencent.liteav.k.n;
import com.tencent.liteav.k.n.a;
import com.tencent.liteav.k.n.b;
import com.tencent.liteav.k.n.h;
import com.tencent.liteav.k.n.i;
import com.tencent.liteav.k.n.j;
import com.tencent.liteav.k.n.k;
import com.tencent.liteav.k.n.l;
import com.tencent.liteav.k.n.m;
import java.util.List;

/* compiled from: MotionFilterChain */
public class e {
    private final int A = 274000;
    private final int B = 318000;
    private final int C = 362000;
    private final int D = 406000;
    private final int E = 450000;
    private final int F = 494000;
    private final int G = 538000;
    private final int H = 582000;
    private final int I = 560000;
    private final int J = 1120000;
    private final int K = 1000000;
    private final int L = 120000;
    private final int M = 70000;
    private final int N = FengConstant.VIDEO_MAX_DURATION;
    private final int O = 350000;
    private final int P = 400000;
    private final int Q = 500000;
    private final int R = 600000;
    private final int S = 650000;
    private final int T = 700000;
    private final int U = 800000;
    private final int V = 900000;
    private final int W = 1000000;
    private final int X = 1050000;
    private final int Y = 1100000;
    private final int Z = 1200000;
    private g a;
    private final int aA = PermissionsConstant.CODE_FOR_WRITE_PERMISSION_BASE;
    private final int aB = 150000;
    private final int aC = 250000;
    private final int aD = FengConstant.VIDEO_MAX_DURATION;
    private final int aE = 400000;
    private final int aF = 580000;
    private final int aG = 1000000;
    private final int aH = 2000000;
    private final int aa = 1500000;
    private final int ab = 2500000;
    private final int ac = 120000;
    private final int ad = 240000;
    private final int ae = 360000;
    private final int af = 480000;
    private final int ag = 600000;
    private final int ah = 720000;
    private final int ai = 840000;
    private final int aj = 960000;
    private final int ak = 1080000;
    private final int al = 1200000;
    private final int am = 1320000;
    private final int an = 1440000;
    private final int ao = 1560000;
    private final int ap = 1680000;
    private final int aq = ab.I;
    private final int ar = 100000;
    private final int as = 200000;
    private final int at = FengConstant.VIDEO_MAX_DURATION;
    private final int au = 400000;
    private final int av = 500000;
    private final int aw = 600000;
    private final int ax = 700000;
    private final int ay = 800000;
    private final int az = 850000;
    private d b = d.a();
    private n c;
    private f d;
    private boolean e;
    private long f = -1;
    private long g = -1;
    private long h = -1;
    private long i = -1;
    private long j = -1;
    private long k = -1;
    private long l = -1;
    private long m = -1;
    private l n;
    private m o;
    private a p;
    private n.d q;
    private i r;
    private n.f s;
    private k t;
    private com.tencent.liteav.k.n.e u;
    private h v;
    private n.g w;
    private j x;
    private final int y = 120000;
    private final int z = 230000;

    public e(Context context) {
        this.c = new n(context);
        this.a = g.a();
    }

    public void a(com.tencent.liteav.c.e eVar) {
        long e = eVar.e();
        this.d = null;
        if (b()) {
            int a = a(e);
            if (a != -1 && this.d != null && b(e)) {
                a(a, e);
            }
        }
    }

    public int a(com.tencent.liteav.c.e eVar, int i) {
        if (this.d == null) {
            return i;
        }
        b bVar = new b();
        bVar.a = i;
        if (eVar.h() == 90 || eVar.h() == 270) {
            bVar.b = eVar.n();
            bVar.c = eVar.m();
        } else {
            bVar.b = eVar.m();
            bVar.c = eVar.n();
        }
        switch (this.d.a) {
            case 0:
                this.c.a(0, this.p);
                break;
            case 1:
                this.c.a(1, this.q);
                break;
            case 2:
                this.c.a(2, this.n);
                break;
            case 3:
                this.c.a(3, this.o);
                break;
            case 4:
                this.c.a(4, this.r);
                break;
            case 5:
                this.c.a(5, this.s);
                break;
            case 6:
                this.c.a(6, this.t);
                break;
            case 7:
                this.c.a(7, this.u);
                break;
            case 8:
                this.c.a(8, this.v);
                break;
            case 10:
                this.c.a(10, this.w);
                break;
            case 11:
                this.c.a(11, this.x);
                break;
        }
        return this.c.a(bVar);
    }

    private boolean b() {
        List d = this.b.d();
        if (d == null || d.size() == 0) {
            return false;
        }
        return true;
    }

    private int a(long j) {
        List d = this.b.d();
        if (d == null || d.size() == 0) {
            return -1;
        }
        int i;
        for (int size = d.size() - 1; size >= 0; size--) {
            f fVar = (f) d.get(size);
            if (j >= fVar.b && j <= fVar.c) {
                int i2 = fVar.a;
                this.d = fVar;
                i = i2;
                break;
            }
        }
        i = -1;
        f b = this.b.b();
        if (b.c != -1 && b.c != b.b) {
            return i;
        }
        i = b.a;
        this.d = b;
        return i;
    }

    private boolean b(long j) {
        boolean z = false;
        long j2 = this.d.b;
        long j3 = this.d.c;
        if (j2 != -1 && j3 != -1 && j > j2 && j < j3) {
            z = true;
        }
        if (j2 != -1 && j > j2) {
            z = true;
        }
        if (j3 == -1 || j >= j3) {
            return z;
        }
        return true;
    }

    private void a(int i, long j) {
        switch (i) {
            case 0:
                if (this.p == null) {
                    this.p = new a();
                }
                c();
                return;
            case 1:
                j(j);
                return;
            case 2:
                l(j);
                return;
            case 3:
                k(j);
                return;
            case 4:
                i(j);
                return;
            case 5:
                h(j);
                return;
            case 6:
                g(j);
                return;
            case 7:
                f(j);
                return;
            case 8:
                e(j);
                return;
            case 10:
                d(j);
                return;
            case 11:
                c(j);
                return;
            default:
                return;
        }
    }

    private void c(long j) {
        if (this.x == null) {
            this.x = new j();
        }
        if (this.m == -1) {
            this.m = j;
        }
        long abs = Math.abs(j - this.m);
        if (abs < 1000000) {
            this.x.a = 0.0f;
        } else if (abs < 2000000) {
            this.x.a = 1.0f;
        } else {
            this.m = -1;
        }
    }

    private void d(long j) {
        if (this.w == null) {
            this.w = new n.g();
        }
    }

    private void e(long j) {
        if (this.l == -1) {
            this.l = j;
        }
        if (this.v == null) {
            this.v = new h();
            this.v.a = 0.0f;
        }
        long abs = Math.abs(j - this.l);
        if (abs < 50000) {
            this.v.a = 0.7f;
        } else if (abs < 150000) {
            this.v.a = 0.5f;
        } else if (abs < 250000) {
            this.v.a = 0.4f;
        } else if (abs < 300000) {
            this.v.a = 1.0f;
        } else if (abs < 400000) {
            this.v.a = 0.3f;
        } else if (abs < 580000) {
            this.v.a = 0.0f;
        } else {
            this.l = -1;
        }
    }

    private void f(long j) {
        if (this.k == -1) {
            this.k = j;
        }
        if (this.u == null) {
            this.u = new com.tencent.liteav.k.n.e();
            this.u.b = 0.0f;
            this.u.a = 0.0f;
            this.u.c = 0.0f;
        }
        long abs = Math.abs(j - this.k);
        if (abs < 100000) {
            this.u.b = 10.0f;
            this.u.a = 0.01f;
            this.u.c = 0.0f;
        } else if (abs < 200000) {
            this.u.b = 20.0f;
            this.u.a = -0.02f;
            this.u.c = 0.0f;
        } else if (abs < 300000) {
            this.u.b = 30.0f;
            this.u.a = 0.02f;
            this.u.c = 0.0f;
        } else if (abs < 400000) {
            this.u.b = 20.0f;
            this.u.a = -0.03f;
            this.u.c = 0.0f;
        } else if (abs < 500000) {
            this.u.b = 10.0f;
            this.u.a = 0.01f;
            this.u.c = 0.0f;
        } else if (abs < 600000) {
            this.u.b = 20.0f;
            this.u.a = -0.02f;
            this.u.c = 0.0f;
        } else if (abs < 700000) {
            this.u.b = 30.0f;
            this.u.a = -0.03f;
            this.u.c = 0.0f;
        } else if (abs < 800000) {
            this.u.b = 20.0f;
            this.u.a = 0.02f;
            this.u.c = 0.0f;
        } else if (abs < 850000) {
            this.u.b = 0.0f;
            this.u.a = 0.0f;
            this.u.c = 0.0f;
        } else {
            this.k = -1;
        }
    }

    private void g(long j) {
        if (this.j == -1) {
            this.j = j;
        }
        if (this.t == null) {
            this.t = new k();
            this.t.f = 1;
            this.t.h = 0.3f;
        }
        this.t.a = new float[]{0.0f, 0.0f};
        this.t.b = new float[]{0.0f, 0.1f};
        long abs = Math.abs(j - this.j);
        if (abs < 120000) {
            this.t.g = 0;
            this.t.a = new float[]{0.0f, 0.0f};
            this.t.b = new float[]{0.0f, 0.0f};
        } else if (abs < 120000) {
            this.t.g = 1;
        } else if (abs < 240000) {
            this.t.g = 2;
        } else if (abs < 360000) {
            this.t.g = 3;
        } else if (abs < 480000) {
            this.t.g = 4;
        } else if (abs < 600000) {
            this.t.g = 5;
        } else if (abs < 720000) {
            this.t.g = 6;
        } else if (abs < 840000) {
            this.t.g = 7;
        } else if (abs < 960000) {
            this.t.g = 8;
        } else if (abs < 1080000) {
            this.t.g = 9;
        } else if (abs < 1200000) {
            this.t.g = 10;
        } else if (abs < 1320000) {
            this.t.g = 11;
        } else if (abs < 1440000) {
            this.t.g = 12;
        } else if (abs < 1560000) {
            this.t.g = 13;
        } else if (abs < 1680000) {
            this.t.g = 14;
        } else if (abs < 1800000) {
            this.t.g = 15;
        } else {
            this.j = -1;
        }
    }

    private void h(long j) {
        if (this.s == null) {
            this.s = new n.f();
        }
        this.s.a = 5;
        this.s.b = 1;
        this.s.c = 0.5f;
    }

    private void i(long j) {
        if (this.i == -1) {
            if (this.e) {
                this.i = this.d.b;
            } else {
                this.i = j;
            }
        }
        if (this.r == null) {
            this.r = new i();
        }
        long abs = Math.abs(j - this.i);
        if (abs < 300000) {
            this.r.b = 0.0f;
            this.r.e = 0.03f;
            this.r.d = 0.003f;
        } else if (abs < 350000) {
            this.r.b = 0.0f;
            this.r.e = 0.03f;
            this.r.d = 0.015f;
        } else if (abs < 400000) {
            this.r.b = 0.0f;
            this.r.e = 0.03f;
            this.r.d = 0.024f;
        } else if (abs < 500000) {
            this.r.b = 0.0f;
            this.r.e = 0.03f;
            this.r.d = 0.015f;
        } else if (abs < 600000) {
            this.r.b = 0.0f;
            this.r.e = 0.03f;
            this.r.d = 0.003f;
        } else if (abs < 650000) {
            this.r.b = 0.0f;
            this.r.e = 0.03f;
            this.r.d = 0.03f;
        } else if (abs < 700000) {
            this.r.b = 0.0f;
            this.r.e = 0.03f;
            this.r.d = 0.015f;
        } else if (abs < 800000) {
            this.r.b = 0.0f;
            this.r.e = 0.03f;
            this.r.d = 0.024f;
        } else if (abs < 900000) {
            this.r.b = 1.0f;
        } else if (abs < 1000000) {
            this.r.b = 0.0f;
            this.r.e = 0.03f;
            this.r.d = 0.015f;
        } else if (abs < 1050000) {
            this.r.b = 0.0f;
            this.r.e = 0.03f;
            this.r.d = 0.024f;
        } else if (abs < 1100000) {
            this.r.b = 0.0f;
            this.r.e = 0.03f;
            this.r.d = 0.015f;
        } else if (abs < 1200000) {
            this.r.b = 0.0f;
            this.r.e = 0.03f;
            this.r.d = 0.009f;
        } else if (abs < 1500000) {
            this.r.b = 0.0f;
            this.r.e = 0.03f;
            this.r.d = 0.003f;
        } else if (abs < 2500000) {
            this.r.b = 1.0f;
        } else {
            this.i = -1;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x00c2  */
    private void j(long r14) {
        /*
        r13 = this;
        r10 = -1;
        r8 = 1090519040; // 0x41000000 float:8.0 double:5.38787994E-315;
        r7 = 0;
        r6 = 2;
        r0 = r13.h;
        r0 = (r0 > r10 ? 1 : (r0 == r10 ? 0 : -1));
        if (r0 != 0) goto L_0x0016;
    L_0x000c:
        r0 = r13.e;
        if (r0 == 0) goto L_0x0074;
    L_0x0010:
        r0 = r13.d;
        r0 = r0.b;
        r13.h = r0;
    L_0x0016:
        r0 = r13.q;
        if (r0 != 0) goto L_0x0039;
    L_0x001a:
        r0 = new com.tencent.liteav.k.n$d;
        r0.<init>();
        r13.q = r0;
        r0 = r13.q;
        r0.e = r8;
        r0 = r13.q;
        r1 = new float[r6];
        r1 = {1056964608, 1056964608};
        r0.c = r1;
        r0 = r13.q;
        r0.a = r7;
        r0 = r13.q;
        r1 = 1045220557; // 0x3e4ccccd float:0.2 double:5.164075695E-315;
        r0.b = r1;
    L_0x0039:
        r0 = r13.h;
        r0 = r14 - r0;
        r2 = java.lang.Math.abs(r0);
        r0 = 120000; // 0x1d4c0 float:1.68156E-40 double:5.9288E-319;
        r0 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1));
        if (r0 >= 0) goto L_0x0077;
    L_0x0048:
        r0 = r13.q;
        r0.d = r7;
        r0 = r13.q;
        r0.e = r8;
        r0 = r13.q;
        r1 = new float[r6];
        r1 = {0, 0};
        r0.c = r1;
        r0 = r13.q;
        r0.a = r7;
        r0 = r13.q;
        r0.b = r7;
        r0 = r13.q;
        r1 = new float[r6];
        r1 = {0, 0};
        r0.f = r1;
        r0 = r13.q;
        r1 = new float[r6];
        r1 = {0, 0};
        r0.g = r1;
    L_0x0073:
        return;
    L_0x0074:
        r13.h = r14;
        goto L_0x0016;
    L_0x0077:
        r0 = 1;
    L_0x0078:
        r1 = 8;
        if (r0 > r1) goto L_0x00bb;
    L_0x007c:
        r1 = 120000; // 0x1d4c0 float:1.68156E-40 double:5.9288E-319;
        r4 = 70000; // 0x11170 float:9.8091E-41 double:3.45846E-319;
        r4 = r4 * r0;
        r1 = r1 + r4;
        r4 = (long) r1;
        r1 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r1 >= 0) goto L_0x0109;
    L_0x0089:
        r1 = r13.q;
        r4 = (float) r0;
        r1.d = r4;
        r1 = r13.q;
        r1.e = r8;
        r1 = r13.q;
        r4 = new float[r6];
        r4 = {1056964608, 1056964608};
        r1.c = r4;
        r1 = r13.q;
        r1.a = r7;
        r1 = r13.q;
        r4 = 1050253722; // 0x3e99999a float:0.3 double:5.188942835E-315;
        r1.b = r4;
        r1 = 3;
        if (r0 < r1) goto L_0x00f6;
    L_0x00a9:
        r0 = r13.q;
        r1 = new float[r6];
        r1 = {-1110651699, 0};
        r0.f = r1;
        r0 = r13.q;
        r1 = new float[r6];
        r1 = {0, 1036831949};
        r0.g = r1;
    L_0x00bb:
        r0 = 680000; // 0xa6040 float:9.52883E-40 double:3.359646E-318;
        r0 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1));
        if (r0 <= 0) goto L_0x0073;
    L_0x00c2:
        r0 = 1080000; // 0x107ac0 float:1.513402E-39 double:5.33591E-318;
        r0 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1));
        if (r0 > 0) goto L_0x010d;
    L_0x00c9:
        r0 = r13.q;
        r0.d = r7;
        r0 = r13.q;
        r0.e = r8;
        r0 = r13.q;
        r1 = new float[r6];
        r1 = {0, 0};
        r0.c = r1;
        r0 = r13.q;
        r0.a = r7;
        r0 = r13.q;
        r0.b = r7;
        r0 = r13.q;
        r1 = new float[r6];
        r1 = {0, 0};
        r0.f = r1;
        r0 = r13.q;
        r1 = new float[r6];
        r1 = {0, 0};
        r0.g = r1;
        goto L_0x0073;
    L_0x00f6:
        r0 = r13.q;
        r1 = new float[r6];
        r1 = {0, 0};
        r0.f = r1;
        r0 = r13.q;
        r1 = new float[r6];
        r1 = {0, 0};
        r0.g = r1;
        goto L_0x00bb;
    L_0x0109:
        r0 = r0 + 1;
        goto L_0x0078;
    L_0x010d:
        r13.h = r10;
        goto L_0x0073;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.e.e.j(long):void");
    }

    private void c() {
        if (this.p == null) {
            this.p = new a();
        }
    }

    private void k(long j) {
        if (this.g == -1) {
            if (this.e) {
                this.g = this.d.b;
            } else {
                this.g = j;
            }
        }
        if (this.o == null) {
            this.o = new m();
        }
        long abs = Math.abs(j - this.g);
        if (abs <= 1000000) {
            this.o.a = 4;
        } else if (abs <= 2000000) {
            this.o.a = 9;
        } else {
            this.g = -1;
        }
    }

    private void l(long j) {
        if (this.f == -1) {
            if (this.e) {
                this.f = this.d.b;
            } else {
                this.f = j;
            }
        }
        if (this.n == null) {
            this.n = new l();
            this.n.f = 1;
            this.n.h = 0.3f;
        }
        long abs = Math.abs(j - this.f);
        if (abs < 120000) {
            this.n.g = 0;
        } else if (abs < 230000) {
            this.n.g = 1;
        } else if (abs < 274000) {
            this.n.g = 2;
        } else if (abs < 318000) {
            this.n.g = 3;
        } else if (abs < 362000) {
            this.n.g = 4;
        } else if (abs < 406000) {
            this.n.g = 5;
        } else if (abs < 450000) {
            this.n.g = 6;
        } else if (abs < 494000) {
            this.n.g = 7;
        } else if (abs < 538000) {
            this.n.g = 8;
        } else if (abs < 582000) {
            this.n.g = 9;
        } else if (abs < 1120000) {
            this.n.g = 0;
        } else {
            this.f = -1;
        }
    }

    public void a() {
        if (this.c != null) {
            this.c.a();
        }
    }
}
