package com.tencent.liteav.b;

/* compiled from: PicConfig */
public class e {
    private static e b;
    private final String a = "PicConfig";
    private int c;

    public static e a() {
        if (b == null) {
            synchronized (e.class) {
                if (b == null) {
                    b = new e();
                }
            }
        }
        return b;
    }

    public void a(int i) {
        this.c = i;
    }

    public int b() {
        return this.c;
    }
}
