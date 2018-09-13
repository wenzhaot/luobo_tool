package com.tencent.liteav.e;

import com.tencent.liteav.b.i;
import com.tencent.liteav.c.e;
import com.tencent.liteav.c.g;
import com.tencent.liteav.i.a;

/* compiled from: BasicFilterChain */
public class c {
    protected int a;
    protected int b;
    protected e c;

    public void a(g gVar) {
        this.a = gVar.a;
        this.b = gVar.b;
    }

    protected g b(e eVar) {
        g gVar = new g();
        float m = (((float) this.a) * 1.0f) / ((float) eVar.m());
        float n = (((float) this.b) * 1.0f) / ((float) eVar.n());
        if (i.a().s == 2) {
            if (m <= n) {
                n = m;
            }
        } else if (m >= n) {
            n = m;
        }
        gVar.a = (int) (((float) eVar.m()) * n);
        gVar.b = (int) (n * ((float) eVar.n()));
        return gVar;
    }

    protected a.g a(a.g gVar, g gVar2) {
        a.g gVar3 = new a.g();
        gVar3.a = (gVar.a - ((float) ((this.a - gVar2.a) / 2))) / ((float) gVar2.a);
        gVar3.b = (gVar.b - ((float) ((this.b - gVar2.b) / 2))) / ((float) gVar2.b);
        gVar3.c = gVar.c / ((float) gVar2.a);
        return gVar3;
    }

    public void c(e eVar) {
        this.c = eVar;
    }
}
