package com.tencent.liteav.b;

import android.text.TextUtils;

/* compiled from: BgmConfig */
public class b {
    private static b i;
    public String a;
    public long b;
    public long c;
    public long d;
    public boolean e;
    public float f;
    public float g;
    public boolean h;

    public static b a() {
        if (i == null) {
            i = new b();
        }
        return i;
    }

    private b() {
        b();
    }

    public void a(String str) {
        if (TextUtils.isEmpty(str)) {
            b();
        } else if (this.a == null || !this.a.equals(str)) {
            b(str);
        }
    }

    private void b(String str) {
        this.a = str;
    }

    public void b() {
        this.a = null;
        this.b = -1;
        this.c = -1;
        this.h = false;
        this.f = 1.0f;
    }
}
