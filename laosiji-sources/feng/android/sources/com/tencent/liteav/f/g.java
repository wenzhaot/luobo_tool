package com.tencent.liteav.f;

import android.content.Context;
import android.graphics.Bitmap;
import com.tencent.liteav.beauty.d;
import com.tencent.liteav.c.e;
import com.tencent.liteav.i.a.j;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/* compiled from: TXFilterContainer */
public class g {
    private d a;
    private List<j> b = new ArrayList();

    public g(Context context) {
        this.a = new d(context, true);
    }

    public void a(e eVar, boolean z) {
        List linkedList = new LinkedList();
        if (z) {
            this.a.a(linkedList);
            return;
        }
        long e = eVar.e() / 1000;
        if (!(this.b == null || this.b.size() == 0)) {
            for (j jVar : this.b) {
                if (e > jVar.c && e < jVar.d) {
                    linkedList.add(a(jVar.a, jVar.b));
                }
            }
        }
        this.a.a(linkedList);
    }

    public void a(int i) {
        this.a.a(i);
    }

    public void a(float[] fArr) {
        this.a.a(fArr);
    }

    public int a(int i, int i2, int i3, int i4, int i5, int i6) {
        return this.a.a(i, i2, i3, i4, i5, i6);
    }

    public void a() {
        if (this.a != null) {
            this.a.a();
            this.a = null;
        }
    }

    private d.d a(Bitmap bitmap, com.tencent.liteav.i.a.g gVar) {
        d.d dVar = new d.d();
        dVar.a = bitmap;
        dVar.b = gVar.a;
        dVar.c = gVar.b;
        dVar.d = gVar.c;
        return dVar;
    }

    public void a(int i, int i2) {
        this.a.c(i);
        this.a.d(i2);
    }
}
