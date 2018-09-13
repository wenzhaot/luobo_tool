package com.tencent.liteav.d;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import com.tencent.liteav.b.h;
import com.tencent.liteav.b.i;
import com.tencent.liteav.b.k;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.i.a.c;
import com.tencent.liteav.i.b.a;
import com.tencent.liteav.i.b.e;

/* compiled from: VideoAverageThumbnailGenerate */
public class r extends e {
    private e n;
    private a o;
    private Handler p;
    private p q;

    public r(Context context) {
        super(context);
        this.p = new Handler(Looper.getMainLooper());
        this.q = new p() {
            public void a(int i, long j, Bitmap bitmap) {
                r.this.c.p();
                if (r.this.o != null) {
                    r.this.o.a(i, j / 1000, bitmap);
                }
                int c = h.a().c();
                if (c == 0) {
                    r.this.b();
                    if (r.this.n != null) {
                        r.this.g();
                        return;
                    }
                    return;
                }
                final float f = (((float) (i + 1)) * 1.0f) / ((float) c);
                TXCLog.i("VideoAverageThumbnailGenerate", "index:" + i + ",count= " + c + ",progress:" + f);
                r.this.p.post(new Runnable() {
                    public void run() {
                        if (f >= 1.0f) {
                            r.this.b();
                        }
                        if (r.this.n != null) {
                            r.this.n.a(f);
                            if (f >= 1.0f) {
                                r.this.g();
                                TXCLog.i("VideoAverageThumbnailGenerate", "===onProcessComplete===");
                            }
                        }
                    }
                });
            }
        };
        this.c = new ac();
        this.f.a(this.q);
    }

    public void a(e eVar) {
        this.n = eVar;
    }

    public void a(a aVar) {
        this.o = aVar;
    }

    public void a() {
        a(k.a().a);
        f();
        super.a();
    }

    protected void d() {
    }

    protected void a(long j) {
    }

    protected void f() {
        h.a().a(this.c.c());
        this.c.a(h.a().b());
    }

    private void g() {
        i.a().n = true;
        c cVar = new c();
        cVar.a = 0;
        cVar.b = "Generate Complete";
        this.n.a(cVar);
    }

    protected int a(int i, int i2, int i3, long j) {
        return i;
    }

    protected void e() {
    }

    public void c() {
        if (this.f != null) {
            this.f.a(null);
        }
        this.q = null;
        super.c();
    }

    public void b(boolean z) {
        if (this.c != null) {
            this.c.a(z);
        }
    }
}
