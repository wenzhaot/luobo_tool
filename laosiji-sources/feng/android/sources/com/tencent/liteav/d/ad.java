package com.tencent.liteav.d;

import android.content.Context;
import android.graphics.Bitmap;
import com.tencent.liteav.b.h;
import com.tencent.liteav.b.k;
import com.tencent.liteav.i.b.a;
import java.util.ArrayList;
import java.util.List;

/* compiled from: VideoTimelistThumbnailGenerate */
public class ad extends e {
    private a n;
    private ArrayList<Long> o;
    private p p;

    public ad(Context context) {
        super(context);
        this.p = new p() {
            public void a(int i, long j, Bitmap bitmap) {
                if (ad.this.n != null) {
                    ad.this.n.a(i, j / 1000, bitmap);
                }
            }
        };
        this.o = new ArrayList();
        this.c = new ac();
        this.f.a(this.p);
    }

    public void a(a aVar) {
        this.n = aVar;
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
        h.a().a(this.o);
        this.c.a(h.a().b());
    }

    protected int a(int i, int i2, int i3, long j) {
        return i;
    }

    protected void e() {
    }

    public void a(int i) {
        h.a().a(i);
    }

    public void b(int i) {
        h.a().b(i);
    }

    public void a(List<Long> list) {
        this.o.clear();
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < list.size()) {
                this.o.add(Long.valueOf(((Long) list.get(i2)).longValue() * 1000));
                i = i2 + 1;
            } else {
                return;
            }
        }
    }

    public void c() {
        super.c();
        this.o.clear();
        this.p = null;
    }

    public void b(boolean z) {
        if (this.c != null) {
            this.c.a(z);
        }
    }
}
