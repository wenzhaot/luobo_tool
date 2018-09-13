package com.tencent.liteav.d;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import com.tencent.liteav.b.h;
import com.tencent.liteav.b.i;
import com.tencent.liteav.b.k;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.i.b.a;
import com.tencent.liteav.i.b.e;
import com.tencent.liteav.muxer.c;
import com.tencent.liteav.videoencoder.b;

/* compiled from: VideoProcessGenerate */
public class aa extends e {
    private e n;
    private a o;
    private Handler p;
    private p q;

    public aa(Context context) {
        super(context);
        this.p = new Handler(Looper.getMainLooper());
        this.q = new p() {
            public void a(int i, long j, Bitmap bitmap) {
                if (aa.this.o != null) {
                    aa.this.o.a(i, j / 1000, bitmap);
                }
                if (i.a().r) {
                    int c = h.a().c();
                    if (c == 0) {
                        aa.this.b();
                        if (aa.this.n != null) {
                            aa.this.g();
                            return;
                        }
                        return;
                    }
                    final float f = (((float) (i + 1)) * 1.0f) / ((float) c);
                    TXCLog.i("VideoProcessGenerate", "index:" + i + ",count= " + c + ",progress:" + f);
                    aa.this.p.post(new Runnable() {
                        public void run() {
                            if (aa.this.n != null) {
                                aa.this.n.a(f);
                                if (f >= 1.0f) {
                                    aa.this.g();
                                    aa.this.b();
                                }
                            }
                        }
                    });
                }
            }
        };
        this.c = new s();
        this.f.a(this.q);
    }

    public void a(e eVar) {
        this.n = eVar;
    }

    public void a(a aVar) {
        this.o = aVar;
    }

    public void a() {
        int i = 1;
        int i2 = 0;
        a(k.a().a);
        f();
        super.a();
        boolean z = this.l.h.a < 1280 && this.l.h.b < 1280;
        this.b = z;
        if (this.b) {
            i = 2;
        }
        this.h = new b(i);
        Context context = this.a;
        if (!this.b) {
            i2 = 2;
        }
        this.i = new c(context, i2);
        if (!this.l.r) {
            this.l.f();
            this.i.a(this.l.o);
        }
    }

    protected void d() {
        k.a().a = i.a().o;
        this.p.post(new Runnable() {
            public void run() {
                if (aa.this.n != null) {
                    com.tencent.liteav.i.a.c cVar = new com.tencent.liteav.i.a.c();
                    cVar.a = 0;
                    cVar.b = "Generate Complete";
                    aa.this.n.a(cVar);
                }
            }
        });
    }

    protected void a(final long j) {
        this.p.post(new Runnable() {
            public void run() {
                if (aa.this.n != null) {
                    long j = aa.this.l.k;
                    if (j > 0) {
                        aa.this.n.a((((float) (j - com.tencent.liteav.b.c.a().d())) * 1.0f) / ((float) j));
                    }
                }
            }
        });
    }

    protected void f() {
        h.a().a(this.c.c());
    }

    private void g() {
        com.tencent.liteav.i.a.c cVar = new com.tencent.liteav.i.a.c();
        cVar.a = 0;
        cVar.b = "Generate Complete";
        this.n.a(cVar);
        TXCLog.i("VideoProcessGenerate", "===onProcessComplete===");
    }

    protected int a(int i, int i2, int i3, long j) {
        return i;
    }

    protected void e() {
    }

    public void c() {
        super.c();
        this.q = null;
    }
}
