package com.tencent.liteav.k;

import android.content.Context;
import com.tencent.liteav.basic.log.TXCLog;
import java.util.LinkedList;
import java.util.Queue;

/* compiled from: TXCVideoEffect */
public class n {
    private Context A = null;
    private j a = null;
    private k b = null;
    private c c = null;
    private a d = null;
    private h e;
    private e f = null;
    private i g = null;
    private d h = null;
    private g i = null;
    private b j = null;
    private com.tencent.liteav.a k = null;
    private com.tencent.liteav.b l = null;
    private l m = null;
    private m n = null;
    private d o = null;
    private a p = null;
    private i q = null;
    private f r = null;
    private k s = null;
    private e t = null;
    private h u = null;
    private c v = null;
    private g w = null;
    private j x = null;
    private final Queue<Runnable> y = new LinkedList();
    private final String z = "VideoEffect";

    /* compiled from: TXCVideoEffect */
    public static class n {
    }

    /* compiled from: TXCVideoEffect */
    public static class a extends n {
    }

    /* compiled from: TXCVideoEffect */
    public static class b {
        public int a;
        public int b;
        public int c;
    }

    /* compiled from: TXCVideoEffect */
    public static class c extends n {
        public float a = 0.01f;
        public float b = 0.02f;
        public float c = 0.05f;
        public float d = 30.0f;
        public float e = 0.6f;
        public float f = 0.0f;
        public a g = a.MODE_ZOOM_IN;
        public float h = 0.3f;
        public float i = 0.5f;
        public float j = 1.5f;
        public boolean k = false;

        /* compiled from: TXCVideoEffect */
        public enum a {
            MODE_NONE(-1),
            MODE_ZOOM_IN(0),
            MODE_ZOOM_OUT(1);
            
            public int value;

            private a(int i) {
                this.value = i;
            }
        }
    }

    /* compiled from: TXCVideoEffect */
    public static class d extends n {
        public float a = 0.0f;
        public float b = 0.4f;
        public float[] c = new float[]{0.5f, 0.5f};
        public float d = 0.0f;
        public float e = 10.0f;
        public float[] f = new float[]{0.0f, 0.0f};
        public float[] g = new float[]{0.0f, 0.0f};
        public float[] h = new float[]{0.0f, 0.0f};
    }

    /* compiled from: TXCVideoEffect */
    public static class e extends n {
        public float a = 0.0f;
        public float b = 0.0f;
        public float c = 0.0f;
    }

    /* compiled from: TXCVideoEffect */
    public static class f extends n {
        public int a = 5;
        public int b = 1;
        public float c = 0.5f;
    }

    /* compiled from: TXCVideoEffect */
    public static class g extends n {
    }

    /* compiled from: TXCVideoEffect */
    public static class h extends n {
        public float a = 0.0f;
    }

    /* compiled from: TXCVideoEffect */
    public static class i extends n {
        public float a = 0.0f;
        public float b = 0.0f;
        public float c = 0.0f;
        public float d = 0.0f;
        public float e = 0.05f;
    }

    /* compiled from: TXCVideoEffect */
    public static class j extends n {
        public float a = 0.0f;
    }

    /* compiled from: TXCVideoEffect */
    public static class l extends n {
        public float d = 0.5f;
        public float e = 0.5f;
        public int f = 1;
        public int g = 1;
        public float h = 0.5f;
    }

    /* compiled from: TXCVideoEffect */
    public static class k extends l {
        public float[] a = new float[]{0.0f, 0.0f};
        public float[] b = new float[]{0.0f, 0.1f};
        public float[] c = new float[]{0.0f, 0.0f};
    }

    /* compiled from: TXCVideoEffect */
    public static class m extends n {
        public int a;
    }

    public n(Context context) {
        this.A = context;
    }

    public void a(final int i, final n nVar) {
        a(new Runnable() {
            public void run() {
                switch (i) {
                    case 0:
                        n.this.p = (a) nVar;
                        return;
                    case 1:
                        n.this.o = (d) nVar;
                        return;
                    case 2:
                        n.this.m = (l) nVar;
                        return;
                    case 3:
                        n.this.n = (m) nVar;
                        return;
                    case 4:
                        n.this.q = (i) nVar;
                        return;
                    case 5:
                        n.this.r = (f) nVar;
                        return;
                    case 6:
                        n.this.s = (k) nVar;
                        return;
                    case 7:
                        n.this.t = (e) nVar;
                        return;
                    case 8:
                        n.this.u = (h) nVar;
                        return;
                    case 9:
                        n.this.v = (c) nVar;
                        return;
                    case 10:
                        n.this.w = (g) nVar;
                        return;
                    case 11:
                        n.this.x = (j) nVar;
                        return;
                    default:
                        return;
                }
            }
        });
    }

    public int a(b bVar) {
        a(this.y);
        int i = bVar.a;
        if (this.p != null) {
            d(bVar.b, bVar.c);
            if (this.d != null) {
                this.d.a(this.p);
                i = this.d.a(i);
            }
        }
        if (this.o != null) {
            c(bVar.b, bVar.c);
            if (this.c != null) {
                this.c.a(this.o);
                i = this.c.a(i);
            }
        }
        if (this.m != null) {
            a(bVar.b, bVar.c);
            if (this.a != null) {
                this.a.a(this.m);
                i = this.a.a(i);
            }
        }
        if (this.n != null) {
            b(bVar.b, bVar.c);
            if (this.b != null) {
                this.b.a(this.n);
                i = this.b.a(i);
            }
        }
        if (this.q != null) {
            e(bVar.b, bVar.c);
            if (this.e != null) {
                this.e.a(this.q);
                i = this.e.a(i);
            }
        }
        if (this.r != null) {
            f(bVar.b, bVar.c);
            if (this.f != null) {
                this.f.a(this.r);
                i = this.f.a(i);
            }
        }
        if (this.s != null) {
            g(bVar.b, bVar.c);
            if (this.g != null) {
                this.g.a(this.s);
                i = this.g.a(i);
            }
        }
        if (this.t != null) {
            h(bVar.b, bVar.c);
            if (this.h != null) {
                this.h.a(this.t);
                i = this.h.a(i);
            }
        }
        if (this.u != null) {
            i(bVar.b, bVar.c);
            if (this.i != null) {
                this.i.a(this.u);
                i = this.i.a(i);
            }
        }
        if (this.v != null) {
            j(bVar.b, bVar.c);
            if (this.j != null) {
                this.j.a(this.v);
                i = this.j.a(i);
            }
        }
        if (this.w != null) {
            k(bVar.b, bVar.c);
            if (this.k != null) {
                i = this.k.a(i);
            }
        }
        if (this.x != null) {
            l(bVar.b, bVar.c);
            if (this.l != null) {
                this.l.a(this.x);
                i = this.l.a(i);
            }
        }
        b();
        return i;
    }

    private void a(int i, int i2) {
        if (this.a == null) {
            this.a = new j();
            if (!this.a.a(i, i2)) {
                TXCLog.e("VideoEffect", "mSpiritOutFilter.init failed");
                return;
            }
        }
        this.a.b(i, i2);
    }

    private void b(int i, int i2) {
        if (this.b == null) {
            this.b = new k();
            this.b.a(true);
            if (!this.b.c()) {
                TXCLog.e("VideoEffect", "mSpiritOutFilter.init failed");
                return;
            }
        }
        this.b.a(i, i2);
    }

    private void c(int i, int i2) {
        if (this.c == null) {
            this.c = new c();
            this.c.a(true);
            if (!this.c.c()) {
                TXCLog.e("VideoEffect", "mSpiritOutFilter.init failed");
                return;
            }
        }
        this.c.a(i, i2);
    }

    private void d(int i, int i2) {
        if (this.d == null) {
            this.d = new a();
            this.d.a(true);
            if (!this.d.c()) {
                TXCLog.e("VideoEffect", "mAnHeiFilter.init failed");
                return;
            }
        }
        this.d.a(i, i2);
    }

    private void e(int i, int i2) {
        if (this.e == null) {
            this.e = new h();
            this.e.a(true);
            if (!this.e.c()) {
                TXCLog.e("VideoEffect", "mShadowFilter.init failed");
                return;
            }
        }
        this.e.a(i, i2);
    }

    private void f(int i, int i2) {
        if (this.f == null) {
            this.f = new e();
            if (!this.f.a(i, i2)) {
                TXCLog.e("VideoEffect", "mGhostShadowFilter.init failed");
                return;
            }
        }
        this.f.b(i, i2);
    }

    private void g(int i, int i2) {
        if (this.g == null) {
            this.g = new i();
            if (!this.g.a(i, i2)) {
                TXCLog.e("VideoEffect", "createPhontomFilter.init failed");
                return;
            }
        }
        this.g.b(i, i2);
    }

    private void h(int i, int i2) {
        if (this.h == null) {
            this.h = new d();
            this.h.a(true);
            if (!this.h.c()) {
                TXCLog.e("VideoEffect", "createGhostFilter.init failed");
                return;
            }
        }
        this.h.a(i, i2);
    }

    private void i(int i, int i2) {
        if (this.i == null) {
            this.i = new g(this.A);
            if (!this.i.a(i, i2)) {
                TXCLog.e("VideoEffect", "createGhostFilter.init failed");
                return;
            }
        }
        this.i.b(i, i2);
    }

    private void j(int i, int i2) {
        if (this.j == null) {
            this.j = new b();
            if (!this.j.a(i, i2)) {
                TXCLog.e("VideoEffect", "mDiffuseFilter.init failed");
                return;
            }
        }
        this.j.b(i, i2);
    }

    private void k(int i, int i2) {
        if (this.k == null) {
            this.k = new com.tencent.liteav.a();
            this.k.a(true);
            if (!this.k.c()) {
                TXCLog.e("VideoEffect", "mDiffuseFilter.init failed");
                return;
            }
        }
        this.k.a(i, i2);
    }

    private void l(int i, int i2) {
        if (this.l == null) {
            this.l = new com.tencent.liteav.b();
            this.l.a(true);
            if (!this.l.c()) {
                TXCLog.e("VideoEffect", "mDiffuseFilter.init failed");
                return;
            }
        }
        this.l.a(i, i2);
    }

    private void b() {
        this.p = null;
        this.o = null;
        this.m = null;
        this.n = null;
        this.q = null;
        this.r = null;
        this.s = null;
        this.t = null;
        this.u = null;
        this.v = null;
        this.w = null;
        this.x = null;
    }

    private void a(Runnable runnable) {
        synchronized (this.y) {
            this.y.add(runnable);
        }
    }

    private void a(Queue<Runnable> queue) {
        while (true) {
            Runnable runnable = null;
            synchronized (queue) {
                if (!queue.isEmpty()) {
                    runnable = (Runnable) queue.poll();
                }
            }
            if (runnable != null) {
                runnable.run();
            } else {
                return;
            }
        }
    }

    public void a() {
        c();
        b();
    }

    private void c() {
        if (this.a != null) {
            this.a.a();
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
        if (this.d != null) {
            this.d.e();
            this.d = null;
        }
        if (this.e != null) {
            this.e.e();
            this.e = null;
        }
        if (this.f != null) {
            this.f.a();
            this.f = null;
        }
        if (this.g != null) {
            this.g.a();
            this.g = null;
        }
        if (this.h != null) {
            this.h.e();
            this.h = null;
        }
        if (this.i != null) {
            this.i.b();
            this.i = null;
        }
        if (this.j != null) {
            this.j.a();
            this.j = null;
        }
        if (this.k != null) {
            this.k.e();
            this.k = null;
        }
        if (this.l != null) {
            this.l.e();
            this.l = null;
        }
    }
}
