package com.tencent.liteav.b;

import com.tencent.liteav.c.f;
import java.util.LinkedList;
import java.util.List;

/* compiled from: MotionFilterConfig */
public class d {
    private static d a;
    private final LinkedList<f> b = new LinkedList();
    private f c;

    public static d a() {
        if (a == null) {
            a = new d();
        }
        return a;
    }

    private d() {
    }

    public void a(f fVar) {
        this.c = fVar;
        this.b.add(fVar);
    }

    public f b() {
        return this.c;
    }

    public void c() {
        if (this.b.size() != 0) {
            this.b.removeLast();
        }
    }

    public List<f> d() {
        return this.b;
    }

    public void e() {
        this.b.clear();
    }
}
