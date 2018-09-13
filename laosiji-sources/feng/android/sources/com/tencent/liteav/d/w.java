package com.tencent.liteav.d;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaFormat;
import android.os.Handler;
import android.os.Looper;
import android.view.Surface;
import com.tencent.liteav.b.i;
import com.tencent.liteav.b.k;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.c.e;
import com.tencent.liteav.c.g;
import com.tencent.liteav.e.b;
import com.tencent.liteav.i.a.f;
import com.tencent.liteav.i.b.d;
import com.tencent.liteav.j.a;
import java.io.IOException;
import java.util.List;

/* compiled from: VideoEditerPreview */
public class w {
    private final String a = "VideoEditerPreview";
    private Context b;
    private u c;
    private y d;
    private b e;
    private k f;
    private i g;
    private com.tencent.liteav.e.k h;
    private b i;
    private Surface j;
    private boolean k;
    private d l;
    private com.tencent.liteav.i.b.b m;
    private m n;
    private f o;
    private j p = new j() {
        public void a(Surface surface) {
            TXCLog.i("VideoEditerPreview", "onSurfaceTextureAvailable surface:" + surface + ", mStartPlay = " + w.this.k);
            synchronized (this) {
                w.this.j = surface;
            }
            if (w.this.h != null) {
                w.this.h.a();
                w.this.h.b();
                w.this.h.a(w.this.q);
            }
            if (w.this.k) {
                w.this.g();
            }
        }

        public void a(int i, int i2) {
            if (w.this.h != null) {
                g gVar = new g();
                gVar.a = i;
                gVar.b = i2;
                w.this.h.a(gVar);
            }
        }

        public void b(Surface surface) {
            TXCLog.i("VideoEditerPreview", "onSurfaceTextureDestroy surface:" + surface);
            synchronized (this) {
                if (w.this.j == surface) {
                    w.this.j = null;
                }
            }
            if (w.this.h != null) {
                w.this.h.c();
                w.this.h.d();
                w.this.h.a(null);
            }
            if (w.this.o != null) {
                w.this.o.a();
            }
        }

        public int a(int i, float[] fArr, e eVar) {
            if (eVar.p()) {
                w.this.h();
            } else {
                if (w.this.o != null) {
                    i = w.this.o.a(eVar, com.tencent.liteav.b.e.a().b(), false);
                    eVar.l(i);
                    eVar.m(0);
                }
                if (w.this.h != null) {
                    w.this.h.a(fArr);
                    w.this.h.a(i, eVar);
                    w.this.c(eVar.e());
                }
            }
            return 0;
        }

        public void a(boolean z) {
            if (w.this.h != null) {
                w.this.h.a(z);
            }
        }
    };
    private i q = new i() {
        public void a(int i, e eVar) {
            a.c();
            if (w.this.d != null) {
                w.this.d.a(i, w.this.d.a(), w.this.d.b());
            }
        }

        public int b(int i, e eVar) {
            return w.this.a(i, eVar.m(), eVar.n(), eVar.e());
        }
    };
    private h r = new h() {
        public void a(e eVar) {
            if (eVar != null && eVar.b() != null) {
                a.d();
                if (!((i.a().l() && k.a().d() == 2) || w.this.c.h())) {
                }
                if (eVar.p() && ((k.a().d() == 2 && w.this.n.c()) || (k.a().d() == 1 && w.this.c.q()))) {
                    w.this.h();
                    return;
                }
                if (w.this.e != null) {
                    w.this.e.a(eVar);
                }
                if (w.this.i != null) {
                    w.this.i.i();
                }
            }
        }
    };
    private com.tencent.liteav.f.a.b s = new com.tencent.liteav.f.a.b() {
        public void a(e eVar) {
            a.b();
            if (w.this.i != null) {
                w.this.i.a(eVar);
            }
        }

        public void b(e eVar) {
            a.a();
            if (w.this.d != null) {
                w.this.d.a(eVar);
            }
        }
    };
    private com.tencent.liteav.f.a.i t = new com.tencent.liteav.f.a.i() {
        public void a(e eVar) {
            if (w.this.d != null) {
                w.this.d.b(eVar);
            }
        }
    };
    private Handler u = new Handler(Looper.getMainLooper());
    private b.a v = new b.a() {
        public void a(int i) {
            boolean z = true;
            if (k.a().d() == 1 && w.this.c.h()) {
                u i2 = w.this.c;
                if (i > 5) {
                    z = false;
                }
                i2.a(z);
            } else if (w.this.i != null) {
                b l = w.this.i;
                if (i > 5) {
                    z = false;
                }
                l.c(z);
            }
        }
    };

    public w(Context context) {
        this.b = context;
        this.d = new y(context);
        this.d.a(this.p);
        this.e = new b();
        this.g = i.a();
        this.h = new com.tencent.liteav.e.k(context);
        this.g = i.a();
        this.f = k.a();
    }

    public void a(String str) {
        TXCLog.i("VideoEditerPreview", "setVideoPath videoPath:" + str);
        if (this.c == null) {
            this.c = new u();
        }
        try {
            this.c.a(str);
            if (this.c.h()) {
                this.g.a(this.c.f());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void a(List<Bitmap> list, int i) {
        this.n = new m();
        this.n.a(true);
        this.n.a((List) list, i);
        this.o = new f(this.b, this.n.a(), this.n.b());
    }

    public long a(int i) {
        com.tencent.liteav.b.e.a().a(i);
        if (this.n != null) {
            return this.n.a(i);
        }
        return 0;
    }

    public void b(String str) {
        if (this.i == null) {
            this.i = new b();
            this.i.a();
        }
        this.i.a(str);
        this.g.c(this.i.h());
        this.i.a(this.g.m());
        boolean z = false;
        if (k.a().d() == 1) {
            z = this.c.h();
        }
        if (!z) {
            this.i.b(z);
            this.i.c();
        }
    }

    public void a(long j, long j2) {
        TXCLog.i("VideoEditerPreview", "setBGMStartTime startTime:" + j + ",endTime:" + j2);
        if (this.i != null) {
            this.i.a(j, j2);
        }
    }

    public void a(float f) {
        TXCLog.i("VideoEditerPreview", "setVideoVolume volume:" + f);
        if (this.i != null) {
            this.i.a(f);
        }
    }

    public void b(float f) {
        TXCLog.i("VideoEditerPreview", "setBGMVolume volume:" + f);
        if (this.i != null) {
            this.i.b(f);
        }
    }

    public void a(boolean z) {
        TXCLog.i("VideoEditerPreview", "setBGMLoop looping:" + z);
        if (this.i != null) {
            this.i.a(z);
        }
    }

    public void a(long j) {
        TXCLog.i("VideoEditerPreview", "setBGMAtVideoTime videoStartTime:" + j);
        if (this.i != null) {
            this.i.a(j);
        }
    }

    public void a() {
        if (this.d != null) {
            this.d.a(true);
        }
    }

    public void b(long j, long j2) {
        if (this.f.d() == 1 && this.c != null) {
            this.c.a(j * 1000, 1000 * j2);
        } else if (this.f.d() == 2 && this.n != null) {
            this.n.a(j, j2);
        }
    }

    public void c(long j, long j2) {
        if (this.f.d() == 2) {
            TXCLog.e("VideoEditerPreview", "setRepeateFromTimeToTime, source is picture, not support yet!");
        } else {
            this.c.b(j, j2);
        }
    }

    public void a(d dVar) {
        this.l = dVar;
    }

    public void a(com.tencent.liteav.i.b.b bVar) {
        this.m = bVar;
    }

    public void a(f fVar) {
        synchronized (this) {
            this.j = null;
        }
        if (this.f.d() == 1) {
            a(this.f.a);
            if (k.a().e() != 0) {
                TXCLog.e("VideoEditerPreview", "initWithPreview Video Source illegal : " + this.f.a);
                return;
            }
        }
        this.d.a(fVar);
    }

    public void b(long j) {
        if (this.f.d() == 1 && this.c != null) {
            this.c.a(j);
        } else if (this.f.d() == 2 && this.n != null) {
            this.n.a(j);
        }
    }

    public void b() {
        this.k = true;
        TXCLog.i("VideoEditerPreview", "startPlay mStartPlay true,mSurface:" + this.j);
        if (this.j != null) {
            g();
        }
    }

    public void c() {
        this.k = false;
        TXCLog.i("VideoEditerPreview", "stopPlay mStartPlay false");
        if (this.f.d() == 1 && this.c != null) {
            this.c.a(null);
            this.c.m();
        } else if (this.f.d() == 2 && this.n != null) {
            this.n.e();
            this.n.a(null);
        }
        if (this.e != null) {
            this.e.a(null);
            this.e.d();
        }
        if (this.i != null) {
            this.i.d();
            this.i.a(null);
            this.i.b();
            this.i = null;
        }
        if (this.d != null) {
            this.d.d();
        }
    }

    /* JADX WARNING: Missing block: B:11:0x0024, code:
            if (r3.f.d() != 1) goto L_0x0051;
     */
    /* JADX WARNING: Missing block: B:13:0x0028, code:
            if (r3.c == null) goto L_0x0051;
     */
    /* JADX WARNING: Missing block: B:14:0x002a, code:
            r3.c.o();
     */
    /* JADX WARNING: Missing block: B:16:0x0031, code:
            if (r3.e == null) goto L_0x0038;
     */
    /* JADX WARNING: Missing block: B:17:0x0033, code:
            r3.e.b();
     */
    /* JADX WARNING: Missing block: B:19:0x003a, code:
            if (r3.c == null) goto L_?;
     */
    /* JADX WARNING: Missing block: B:21:0x0042, code:
            if (r3.c.h() != false) goto L_?;
     */
    /* JADX WARNING: Missing block: B:23:0x0046, code:
            if (r3.i == null) goto L_?;
     */
    /* JADX WARNING: Missing block: B:24:0x0048, code:
            r3.i.g();
     */
    /* JADX WARNING: Missing block: B:30:0x0058, code:
            if (r3.f.d() != 2) goto L_0x002f;
     */
    /* JADX WARNING: Missing block: B:32:0x005c, code:
            if (r3.n == null) goto L_0x002f;
     */
    /* JADX WARNING: Missing block: B:33:0x005e, code:
            r3.n.g();
     */
    /* JADX WARNING: Missing block: B:38:?, code:
            return;
     */
    /* JADX WARNING: Missing block: B:39:?, code:
            return;
     */
    /* JADX WARNING: Missing block: B:40:?, code:
            return;
     */
    /* JADX WARNING: Missing block: B:41:?, code:
            return;
     */
    public void d() {
        /*
        r3 = this;
        r2 = 1;
        r3.k = r2;
        r0 = r3.d;
        if (r0 == 0) goto L_0x000d;
    L_0x0007:
        r0 = r3.d;
        r1 = 0;
        r0.a(r1);
    L_0x000d:
        monitor-enter(r3);
        r0 = r3.j;	 Catch:{ all -> 0x004e }
        if (r0 != 0) goto L_0x001d;
    L_0x0012:
        r0 = "VideoEditerPreview";
        r1 = "resumePlay, mSurface is null, ignore!";
        com.tencent.liteav.basic.log.TXCLog.i(r0, r1);	 Catch:{ all -> 0x004e }
        monitor-exit(r3);	 Catch:{ all -> 0x004e }
    L_0x001c:
        return;
    L_0x001d:
        monitor-exit(r3);	 Catch:{ all -> 0x004e }
        r0 = r3.f;
        r0 = r0.d();
        if (r0 != r2) goto L_0x0051;
    L_0x0026:
        r0 = r3.c;
        if (r0 == 0) goto L_0x0051;
    L_0x002a:
        r0 = r3.c;
        r0.o();
    L_0x002f:
        r0 = r3.e;
        if (r0 == 0) goto L_0x0038;
    L_0x0033:
        r0 = r3.e;
        r0.b();
    L_0x0038:
        r0 = r3.c;
        if (r0 == 0) goto L_0x001c;
    L_0x003c:
        r0 = r3.c;
        r0 = r0.h();
        if (r0 != 0) goto L_0x001c;
    L_0x0044:
        r0 = r3.i;
        if (r0 == 0) goto L_0x001c;
    L_0x0048:
        r0 = r3.i;
        r0.g();
        goto L_0x001c;
    L_0x004e:
        r0 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x004e }
        throw r0;
    L_0x0051:
        r0 = r3.f;
        r0 = r0.d();
        r1 = 2;
        if (r0 != r1) goto L_0x002f;
    L_0x005a:
        r0 = r3.n;
        if (r0 == 0) goto L_0x002f;
    L_0x005e:
        r0 = r3.n;
        r0.g();
        goto L_0x002f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.d.w.d():void");
    }

    public void e() {
        this.k = false;
        if (this.f.d() == 1 && this.c != null) {
            this.c.n();
        } else if (this.f.d() == 2 && this.n != null) {
            this.n.f();
        }
        if (this.e != null) {
            this.e.a();
        }
        if (this.c != null && !this.c.h() && this.i != null) {
            this.i.f();
        }
    }

    public void f() {
        TXCLog.i("VideoEditerPreview", "release");
        if (this.c != null) {
            this.c.k();
        }
        if (this.n != null) {
            this.n.i();
        }
        if (this.d != null) {
            this.d.a(null);
            this.d.e();
        }
        this.d = null;
        this.h = null;
        this.p = null;
        this.q = null;
        this.r = null;
        this.s = null;
        this.v = null;
        this.j = null;
    }

    private void g() {
        TXCLog.i("VideoEditerPreview", "startPlayInternal");
        if (this.i == null) {
            this.i = new b();
            this.i.a();
        }
        this.i.a(this.r);
        if (this.g.l()) {
            MediaFormat m = this.g.m();
            this.i.a(m);
            if (this.f.d() == 1) {
                this.i.b(this.c.h());
            } else {
                this.i.b(false);
            }
            this.i.c();
            this.i.e();
            this.e.a(m);
        }
        g gVar = new g();
        gVar.a = this.d.a();
        gVar.b = this.d.b();
        this.h.a(gVar);
        if (this.f.d() == 1 && this.c != null) {
            synchronized (this) {
                this.c.a(this.j);
            }
            this.c.a(this.s);
            this.c.l();
        } else if (this.f.d() == 2 && this.n != null) {
            this.n.a(this.t);
            this.n.d();
        }
        this.e.a(this.v);
        this.e.c();
        this.d.a(false);
        this.d.c();
        a.h();
    }

    private int a(int i, int i2, int i3, long j) {
        if (this.m != null) {
            return this.m.a(i, i2, i3, j);
        }
        return i;
    }

    private void h() {
        this.u.post(new Runnable() {
            public void run() {
                if (w.this.l != null) {
                    w.this.l.a();
                }
            }
        });
    }

    private void c(final long j) {
        this.u.post(new Runnable() {
            public void run() {
                if (w.this.l != null) {
                    w.this.l.a((int) j);
                }
            }
        });
    }
}
