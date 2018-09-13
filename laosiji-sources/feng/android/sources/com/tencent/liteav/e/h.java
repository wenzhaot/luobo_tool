package com.tencent.liteav.e;

import com.tencent.liteav.c.e;
import com.tencent.liteav.c.g;
import com.tencent.liteav.i.a;
import com.tencent.liteav.i.a.j;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: SubtitleFilterChain */
public class h extends c {
    private static h d;
    private List<j> e;
    private CopyOnWriteArrayList<j> f = new CopyOnWriteArrayList();

    public static h a() {
        if (d == null) {
            d = new h();
        }
        return d;
    }

    private h() {
    }

    public void a(List<j> list) {
        this.e = list;
        this.f.clear();
        if (this.c != null) {
            a(this.c);
        }
    }

    public List<j> b() {
        return this.f;
    }

    public void a(e eVar) {
        if (this.a != 0 && this.b != 0 && this.e != null && this.e.size() != 0) {
            g b = b(eVar);
            for (j jVar : this.e) {
                if (jVar != null) {
                    this.f.add(a(jVar, a(jVar.b, b)));
                }
            }
        }
    }

    private j a(j jVar, a.g gVar) {
        j jVar2 = new j();
        jVar2.b = gVar;
        jVar2.a = jVar.a;
        jVar2.c = jVar.c;
        jVar2.d = jVar.d;
        return jVar2;
    }

    public void c() {
        b(this.f);
        b(this.e);
        this.e = null;
    }

    protected void b(List<j> list) {
        if (list != null) {
            for (j jVar : list) {
                if (!(jVar == null || jVar.a == null || jVar.a.isRecycled())) {
                    jVar.a.recycle();
                    jVar.a = null;
                }
            }
            list.clear();
        }
    }
}
