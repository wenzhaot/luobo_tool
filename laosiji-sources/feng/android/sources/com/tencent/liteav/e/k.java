package com.tencent.liteav.e;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.tencent.liteav.b.h;
import com.tencent.liteav.b.j;
import com.tencent.liteav.beauty.d;
import com.tencent.liteav.c.c;
import com.tencent.liteav.c.e;
import com.tencent.liteav.c.g;
import com.tencent.liteav.d.i;
import com.tencent.liteav.d.p;
import com.tencent.liteav.i.a;
import com.tencent.liteav.j.f;
import java.util.ArrayList;
import java.util.List;

/* compiled from: VideoPreprocessChain */
public class k {
    private Context a;
    private j b;
    private d c;
    private p d;
    private i e;
    private g f;
    private i g;
    private l h;
    private e i;
    private h j;
    private f k;
    private a l;
    private j m;
    private ArrayList<d.d> n;
    private e o;
    private boolean p;
    private int q;
    private l r;
    private boolean s = false;

    public k(Context context) {
        this.a = context;
    }

    public void a() {
        this.b = j.a();
        this.c = new d(this.a, true);
        this.i = new e(this.a);
        this.j = h.a();
        this.k = f.a();
        this.l = a.a();
        this.m = j.a();
    }

    public void b() {
        if (!h.a().e()) {
            this.g = new i(Boolean.valueOf(false));
            this.g.a();
        }
        this.h = new l(Boolean.valueOf(false));
        this.h.a();
        this.r = new l(Boolean.valueOf(true));
        this.r.a();
    }

    public void c() {
        if (this.g != null) {
            this.g.b();
            this.g = null;
        }
        if (this.h != null) {
            this.h.b();
            this.h = null;
        }
        if (this.r != null) {
            this.r.b();
            this.r = null;
        }
    }

    public void d() {
        if (this.i != null) {
            this.i.a();
        }
        if (this.c != null) {
            this.c.a();
            this.c = null;
        }
        if (this.n != null) {
            this.n.clear();
        }
        this.o = null;
    }

    public void a(g gVar) {
        this.f = gVar;
    }

    public void a(i iVar) {
        this.e = iVar;
    }

    public void a(p pVar) {
        this.d = pVar;
    }

    public void a(float[] fArr) {
        if (this.c != null) {
            this.c.a(fArr);
        }
    }

    public void a(boolean z) {
        this.p = z;
        if (z) {
            a(this.q, this.o);
        }
    }

    public void a(int i, e eVar) {
        if (this.c != null && eVar != null) {
            int c;
            if (this.s) {
                c = c(i, eVar);
                e b = b(eVar);
                e(c, b);
                this.o = b;
                this.q = i;
                return;
            }
            int c2;
            this.n = new ArrayList();
            if (com.tencent.liteav.b.k.a().d() == 1) {
                c2 = c(i, eVar);
                eVar = b(eVar);
            } else {
                c2 = i;
            }
            this.l.c(eVar);
            this.k.c(eVar);
            this.j.c(eVar);
            e();
            g();
            f();
            if (!this.p) {
                e(eVar);
                d(eVar);
                c(eVar);
            }
            a(eVar);
            this.c.a(0);
            this.c.a(this.n);
            this.c.b(eVar.s());
            c = d(this.c.a(c2, eVar.m(), eVar.n(), 0, 0, 0), eVar);
            if (this.e != null) {
                c = this.e.b(c, eVar);
            }
            c = b(c, eVar);
            if (this.e != null) {
                this.e.a(c, eVar);
            }
            f(c, eVar);
            this.o = eVar;
            this.q = i;
        }
    }

    private void a(e eVar) {
        if (this.m.b()) {
            List<a.j> h = this.m.h();
            if (h != null && h.size() != 0) {
                long a = f.a(eVar) / 1000;
                for (a.j jVar : h) {
                    if (a <= jVar.c) {
                        return;
                    }
                    if (a > jVar.c && a <= jVar.d) {
                        this.n.add(a(jVar.a, jVar.b));
                    }
                }
            }
        }
    }

    private e b(e eVar) {
        if (eVar.h() == 90 || eVar.h() == 270) {
            int n = eVar.n();
            eVar.k(eVar.m());
            eVar.j(n);
            eVar.e(0);
        }
        return eVar;
    }

    private int b(int i, e eVar) {
        if (this.h == null || eVar.m() == 0 || eVar.n() == 0) {
            return i;
        }
        this.h.a(com.tencent.liteav.b.i.a().s);
        this.h.b(eVar.m(), eVar.n());
        this.h.a(this.f.a, this.f.b);
        return this.h.d(i);
    }

    private int c(int i, e eVar) {
        if (this.r == null || eVar.m() == 0 || eVar.n() == 0) {
            return i;
        }
        this.r.a(com.tencent.liteav.b.i.a().s);
        int h = eVar.h();
        this.r.b(360 - h);
        if (h == 90 || h == 270) {
            this.r.b(eVar.m(), eVar.n());
            this.r.a(eVar.n(), eVar.m());
        } else {
            this.r.b(eVar.m(), eVar.n());
            this.r.a(eVar.m(), eVar.n());
        }
        return this.r.d(i);
    }

    private int d(int i, e eVar) {
        if (this.i == null) {
            return i;
        }
        this.i.a(eVar);
        return this.i.a(eVar, i);
    }

    private void c(e eVar) {
        List b = this.l.b();
        if (b == null || b.size() == 0) {
            this.l.a(this.f);
            this.l.a(eVar);
            b = this.l.b();
        }
        for (com.tencent.liteav.c.a aVar : b) {
            long e = eVar.e() / 1000;
            if (e > aVar.c && e <= aVar.d) {
                Bitmap decodeFile = BitmapFactory.decodeFile(aVar.a);
                if (decodeFile != null) {
                    if (aVar.e == 0.0f) {
                        this.n.add(a(decodeFile, aVar.b));
                    } else {
                        this.n.add(a(com.tencent.liteav.j.g.a(aVar.e, decodeFile), aVar.b));
                    }
                }
            }
        }
    }

    private void d(e eVar) {
        List b = this.k.b();
        if (b == null || b.size() == 0) {
            this.k.a(this.f);
            this.k.a(eVar);
            b = this.k.b();
        }
        for (a.e eVar2 : b) {
            long e = eVar.e() / 1000;
            if (e >= eVar2.c && e <= eVar2.d) {
                this.n.add(a(eVar2.a, eVar2.b));
            }
        }
    }

    private void e(e eVar) {
        List b = this.j.b();
        if (b == null || b.size() == 0) {
            this.j.a(this.f);
            this.j.a(eVar);
            b = this.j.b();
        }
        for (a.j jVar : b) {
            long e = eVar.e() / 1000;
            if (e >= jVar.c && e <= jVar.d) {
                this.n.add(a(jVar.a, jVar.b));
            }
        }
    }

    private void e(int i, e eVar) {
        if (this.d != null) {
            h a = h.a();
            if (!a.e()) {
                int h;
                long e;
                if (eVar.p()) {
                    do {
                        h = a.h();
                        if (this.o != null) {
                            e = this.o.e();
                            g d = a.d();
                            if (this.g != null) {
                                this.g.b(this.o.m(), this.o.n());
                                this.g.a(d.a, d.b);
                                Bitmap a2 = com.tencent.liteav.j.e.a(this.g.b(i), d.a, d.b);
                                if (this.d != null) {
                                    this.d.a(h, e, a2);
                                }
                            }
                        }
                    } while (!a.e());
                    return;
                }
                h = a.h();
                e = a.g();
                g d2 = a.d();
                if (this.g != null) {
                    this.g.b(eVar.m(), eVar.n());
                    this.g.a(d2.a, d2.b);
                    this.d.a(h, e, com.tencent.liteav.j.e.a(this.g.b(i), d2.a, d2.b));
                }
            }
        }
    }

    private void f(int i, e eVar) {
        if (this.d != null) {
            h a = h.a();
            if (!a.e()) {
                int h;
                long e;
                if (eVar.p()) {
                    do {
                        h = a.h();
                        a.g();
                        if (this.o != null) {
                            e = this.o.e();
                            g d = a.d();
                            if (this.g != null) {
                                this.g.b(this.o.m(), this.o.n());
                                this.g.a(d.a, d.b);
                                Bitmap a2 = com.tencent.liteav.j.e.a(this.g.b(i), d.a, d.b);
                                if (this.d != null) {
                                    this.d.a(h, e, a2);
                                }
                            }
                        }
                    } while (!a.e());
                    return;
                }
                e = eVar.e();
                if (com.tencent.liteav.b.i.a().r || e >= a.f()) {
                    h = a.h();
                    e = a.g();
                    g d2 = a.d();
                    if (this.g != null) {
                        this.g.b(eVar.m(), eVar.n());
                        this.g.a(d2.a, d2.b);
                        this.d.a(h, e, com.tencent.liteav.j.e.a(this.g.b(i), d2.a, d2.b));
                    }
                }
            }
        }
    }

    private void e() {
        c c = this.b.c();
        if (c != null && c.a()) {
            this.c.c(c.a);
            this.c.d(c.b);
        }
    }

    private void f() {
        com.tencent.liteav.c.d d = this.b.d();
        if (d != null) {
            float d2 = d.d();
            Bitmap e = d.e();
            Bitmap f = d.f();
            this.c.a(d2, e, d.b(), f, d.c());
        }
    }

    private void g() {
        com.tencent.liteav.c.j b = this.b.b();
        if (b != null) {
            this.n.add(a(b.c(), b.d()));
        }
    }

    private d.d a(Bitmap bitmap, a.g gVar) {
        d.d dVar = new d.d();
        dVar.a = bitmap;
        dVar.b = gVar.a;
        dVar.c = gVar.b;
        dVar.d = gVar.c;
        return dVar;
    }

    public void b(boolean z) {
        this.s = z;
    }
}
