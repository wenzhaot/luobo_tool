package com.tencent.liteav.e;

import com.tencent.liteav.c.g;
import com.tencent.liteav.i.a;
import com.tencent.liteav.i.a.e;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: PasterFilterChain */
public class f extends c {
    private static f d;
    private List<e> e;
    private CopyOnWriteArrayList<e> f = new CopyOnWriteArrayList();

    public static f a() {
        if (d == null) {
            d = new f();
        }
        return d;
    }

    private f() {
    }

    public void a(List<e> list) {
        this.e = list;
        b(this.f);
        if (this.c != null) {
            a(this.c);
        }
    }

    public List<e> b() {
        return this.f;
    }

    public void a(com.tencent.liteav.c.e eVar) {
        if (this.a != 0 && this.b != 0 && this.e != null && this.e.size() != 0) {
            g b = b(eVar);
            for (e eVar2 : this.e) {
                if (eVar2 != null) {
                    this.f.add(a(eVar2, a(eVar2.b, b)));
                }
            }
        }
    }

    private e a(e eVar, a.g gVar) {
        e eVar2 = new e();
        eVar2.b = gVar;
        eVar2.a = eVar.a;
        eVar2.c = eVar.c;
        eVar2.d = eVar.d;
        return eVar2;
    }

    public void c() {
        b(this.f);
        b(this.e);
        this.e = null;
    }

    protected void b(List<e> list) {
        if (list != null) {
            for (e eVar : list) {
                if (!(eVar == null || eVar.a == null || eVar.a.isRecycled())) {
                    eVar.a.recycle();
                    eVar.a = null;
                }
            }
            list.clear();
        }
    }
}
