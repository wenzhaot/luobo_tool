package com.tencent.liteav.e;

import android.graphics.Bitmap;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.c.e;
import com.tencent.liteav.c.i;
import com.tencent.liteav.d.q;
import com.tencent.liteav.i.a.g;
import com.tencent.liteav.j.f;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/* compiled from: TailWaterMarkChain */
public class j {
    private static j c;
    public e a;
    public e b;
    private i d;
    private float e;
    private List<com.tencent.liteav.i.a.j> f;
    private q g;
    private int h;
    private int i;
    private int j;
    private int k;
    private int l;
    private boolean m;
    private boolean n;
    private boolean o;

    public static j a() {
        if (c == null) {
            c = new j();
        }
        return c;
    }

    private j() {
        i();
    }

    public void a(i iVar) {
        this.d = iVar;
    }

    public boolean b() {
        return this.d != null;
    }

    public void a(q qVar) {
        this.g = qVar;
    }

    public long c() {
        return (long) ((this.d.a() * 1000) * 1000);
    }

    public void d() {
        int i = 0;
        this.o = com.tencent.liteav.b.i.a().l();
        if (this.d != null && this.a != null) {
            int a = this.d.a();
            if (a != 0) {
                this.k = this.a.i() * a;
                this.l = 0;
                this.e = 0.0f;
                e();
                if (this.o) {
                    if (this.b != null) {
                        this.i = (this.b.g() * 1000) / ((this.b.k() * 2) * this.b.j());
                        this.h = (a * 1000) / this.i;
                        this.j = 0;
                        while (i < this.h) {
                            g();
                            i++;
                        }
                    } else {
                        return;
                    }
                }
                f();
            }
        }
    }

    public void e() {
        Bitmap c = this.d.c();
        g d = this.d.d();
        int a = this.d.a();
        if (c != null && !c.isRecycled() && d != null && a != 0) {
            List arrayList = new ArrayList();
            int i = a * this.a.i();
            long a2 = f.a(this.a) / 1000;
            int i2 = 255 / i;
            a = 100;
            for (int i3 = 0; i3 < i; i3++) {
                a += i2;
                if (a >= 255) {
                    a = 255;
                }
                Bitmap a3 = com.tencent.liteav.j.g.a(c, a);
                com.tencent.liteav.i.a.j jVar = new com.tencent.liteav.i.a.j();
                jVar.b = d;
                jVar.a = a3;
                jVar.c = a2;
                jVar.d = a2 + ((long) (1000 / this.a.i()));
                arrayList.add(jVar);
                a2 = jVar.d;
            }
            this.f = arrayList;
        }
    }

    public void f() {
        if (!this.n) {
            if (this.l >= this.k - 1) {
                this.n = true;
                TXCLog.d("TailWaterMarkChain", "===insertTailVideoFrame===mEndAudio:" + this.m + ",mHasAudioTrack:" + this.o);
                if (!this.o) {
                    l();
                    return;
                } else if (this.m) {
                    k();
                    return;
                } else {
                    return;
                }
            }
            long u;
            e eVar = new e(this.a.a(), this.a.b(), this.a.o());
            eVar.a(this.a.c());
            eVar.b(this.a.d());
            eVar.e(this.a.h());
            eVar.f(this.a.i());
            eVar.g(this.a.j());
            if (eVar.h() == 90 || eVar.h() == 270) {
                eVar.j(this.a.n());
                eVar.k(this.a.m());
            } else {
                eVar.j(this.a.m());
                eVar.k(this.a.n());
            }
            if (com.tencent.liteav.b.g.a().b()) {
                u = this.a.u() + ((long) ((((this.l + 1) * 1000) / this.a.i()) * 1000));
            } else {
                u = this.a.t() + ((long) ((((this.l + 1) * 1000) / this.a.i()) * 1000));
            }
            eVar.a(u);
            eVar.b(u);
            eVar.c(u);
            eVar.a(true);
            this.e += 10.0f / ((float) this.k);
            eVar.a(this.e);
            eVar.c(this.a.f());
            eVar.m(this.a.y());
            eVar.a(this.a.w());
            this.l++;
            TXCLog.d("TailWaterMarkChain", "===insertTailVideoFrame===mVideoIndex:" + this.l + ",time:" + eVar.t());
            if (this.g != null) {
                this.g.b(eVar);
            }
        }
    }

    public void g() {
        if (!this.m) {
            if (this.j >= this.h - 1) {
                this.m = true;
                if (this.n) {
                    k();
                    return;
                }
                return;
            }
            this.b.a(ByteBuffer.allocate(this.b.g()));
            e eVar = new e(this.b.a(), this.b.b(), this.b.o());
            eVar.a(this.b.c());
            eVar.b(this.b.d());
            eVar.g(this.b.j());
            long e = this.b.e() + ((long) ((this.i * (this.j + 1)) * 1000));
            eVar.a(e);
            eVar.b(e);
            eVar.c(e);
            eVar.a(true);
            eVar.c(this.b.f());
            this.j++;
            TXCLog.d("TailWaterMarkChain", "===insertTailAudioFrame===mAudioIndex:" + this.j + ",time:" + eVar.e());
            if (this.g != null) {
                this.g.a(eVar);
            }
        }
    }

    private void k() {
        TXCLog.d("TailWaterMarkChain", "===insertAudioTailFrame===");
        this.b.a(ByteBuffer.allocate(this.b.g()));
        e eVar = new e(this.b.a(), this.b.b(), this.b.o());
        eVar.a(this.b.c());
        eVar.b(this.b.d());
        eVar.g(this.b.j());
        long e = this.b.e() + ((long) ((this.i * (this.j + 1)) * 1000));
        eVar.a(e);
        eVar.b(e);
        eVar.c(e);
        eVar.a(true);
        eVar.c(4);
        this.j++;
        if (this.g != null) {
            this.g.a(eVar);
        }
    }

    private void l() {
        TXCLog.d("TailWaterMarkChain", "===insertVideoTailVFrame===, lastVideoFrame = " + this.a);
        if (this.a != null) {
            long u;
            e eVar = new e(this.a.a(), this.a.b(), this.a.o());
            eVar.a(this.a.c());
            eVar.b(this.a.d());
            eVar.e(this.a.h());
            eVar.f(this.a.i());
            eVar.g(this.a.j());
            if (eVar.h() == 90 || eVar.h() == 270) {
                eVar.j(this.a.n());
                eVar.k(this.a.m());
            } else {
                eVar.j(this.a.m());
                eVar.k(this.a.n());
            }
            if (com.tencent.liteav.b.g.a().b()) {
                u = this.a.u() + ((long) ((((this.l + 1) * 1000) / this.a.i()) * 1000));
            } else {
                u = this.a.t() + ((long) ((((this.l + 1) * 1000) / this.a.i()) * 1000));
            }
            eVar.a(u);
            eVar.b(u);
            eVar.c(u);
            eVar.a(true);
            eVar.c(4);
            eVar.m(this.a.y());
            this.e += 10.0f / ((float) this.k);
            eVar.a(this.e);
            this.l++;
            TXCLog.d("TailWaterMarkChain", "===insertVideoTailVFrame===mVideoIndex:" + this.l + ",time:" + eVar.t() + ",flag:" + eVar.f());
            if (this.g != null) {
                this.g.b(eVar);
            }
        }
    }

    public List<com.tencent.liteav.i.a.j> h() {
        return this.f;
    }

    public void i() {
        if (this.f != null) {
            for (com.tencent.liteav.i.a.j jVar : this.f) {
                if (!(jVar == null || jVar.a == null || jVar.a.isRecycled())) {
                    jVar.a.recycle();
                    jVar.a = null;
                }
            }
            this.f.clear();
        }
        this.f = null;
        if (this.d != null) {
            this.d.b();
        }
        this.d = null;
        this.a = null;
        this.b = null;
        this.e = 0.0f;
        this.j = 0;
        this.l = 0;
        this.h = 0;
        this.k = 0;
        this.m = false;
        this.n = false;
    }

    public boolean j() {
        if (this.o) {
            return this.n && this.m;
        } else {
            return this.n;
        }
    }
}
