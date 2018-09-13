package com.tencent.liteav.c;

import android.graphics.Bitmap;

/* compiled from: ComStaticFilter */
public class d {
    private float a;
    private Bitmap b;
    private Bitmap c;
    private float d;
    private float e;

    public d(float f, Bitmap bitmap, float f2, Bitmap bitmap2, float f3) {
        this.a = f;
        this.b = bitmap;
        this.c = bitmap2;
        this.d = f2;
        this.e = f3;
    }

    public void a() {
        if (!(this.b == null || this.b.isRecycled())) {
            this.b.recycle();
            this.b = null;
        }
        if (this.c != null && !this.c.isRecycled()) {
            this.c.recycle();
            this.c = null;
        }
    }

    public float b() {
        return this.d;
    }

    public float c() {
        return this.e;
    }

    public float d() {
        return this.a;
    }

    public Bitmap e() {
        return this.b;
    }

    public Bitmap f() {
        return this.c;
    }

    public void a(float f) {
        this.d = f;
    }

    public void b(float f) {
        this.e = f;
    }
}
