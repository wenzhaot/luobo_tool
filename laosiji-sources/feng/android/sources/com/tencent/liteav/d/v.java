package com.tencent.liteav.d;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.tencent.liteav.b.k;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.i.a;
import com.tencent.liteav.i.b.b;
import com.tencent.liteav.i.b.c;

/* compiled from: VideoEditGenerate */
public class v extends e {
    private c n;
    private b o;
    private Handler p = new Handler(Looper.getMainLooper());

    public v(Context context) {
        super(context);
    }

    public void a(c cVar) {
        this.n = cVar;
    }

    public void a(b bVar) {
        this.o = bVar;
    }

    public void a() {
        int i = 1;
        int i2 = 0;
        if (k.a().d() == 1) {
            a(k.a().a);
            if (k.a().e() != 0) {
                if (this.n != null) {
                    a.c cVar = new a.c();
                    cVar.a = 0;
                    cVar.b = "Generate Fail,Cause: Video Source Path illegal : " + k.a().a;
                    TXCLog.d("VideoEditGenerate", "onGenerateComplete");
                    this.n.a(cVar);
                    return;
                }
                return;
            }
        } else if (k.a().d() == 2) {
            a(k.a().b(), k.a().c());
        }
        if (!this.l.b()) {
            boolean z;
            this.l.g();
            super.a();
            if (this.l.h.a >= 1280 || this.l.h.b >= 1280) {
                z = false;
            } else {
                z = true;
            }
            this.b = z;
            if (this.b) {
                i = 2;
            }
            this.h = new com.tencent.liteav.videoencoder.b(i);
            Context context = this.a;
            if (!this.b) {
                i2 = 2;
            }
            this.i = new com.tencent.liteav.muxer.c(context, i2);
            this.i.a(this.l.i);
            this.k.a(this.m);
        }
    }

    public void b() {
        super.b();
        this.k.a(null);
    }

    protected void d() {
        k.a().b();
        this.p.post(new Runnable() {
            public void run() {
                if (v.this.n != null) {
                    a.c cVar = new a.c();
                    cVar.a = 0;
                    cVar.b = "Generate Complete";
                    TXCLog.d("VideoEditGenerate", "onGenerateComplete");
                    TXCLog.d("VideoEditGenerate", "===onGenerateComplete===");
                    v.this.n.a(cVar);
                }
            }
        });
    }

    protected void a(final long j) {
        this.p.post(new Runnable() {
            public void run() {
                if (v.this.n != null) {
                    long j = v.this.l.k;
                    if (j > 0) {
                        float d = (((float) (j - com.tencent.liteav.b.c.a().d())) * 1.0f) / ((float) j);
                        TXCLog.d("VideoEditGenerate", "onGenerateProgress timestamp:" + j + ",progress:" + d + ",duration:" + j);
                        v.this.n.a(d);
                    }
                }
            }
        });
    }

    protected int a(int i, int i2, int i3, long j) {
        if (this.o != null) {
            return this.o.a(i, i2, i3, j);
        }
        return i;
    }

    protected void e() {
        if (this.o != null) {
            this.o.a();
        }
    }
}
