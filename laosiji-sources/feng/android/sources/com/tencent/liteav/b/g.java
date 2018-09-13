package com.tencent.liteav.b;

/* compiled from: ReverseConfig */
public class g {
    private static g a;
    private boolean b = false;

    public static g a() {
        if (a == null) {
            synchronized (g.class) {
                if (a == null) {
                    a = new g();
                }
            }
        }
        return a;
    }

    public void a(boolean z) {
        this.b = z;
    }

    public boolean b() {
        return this.b;
    }

    public void c() {
        this.b = false;
    }
}
