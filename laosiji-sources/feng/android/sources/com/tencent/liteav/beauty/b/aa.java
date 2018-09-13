package com.tencent.liteav.beauty.b;

import android.graphics.PointF;
import android.opengl.GLES20;
import com.tencent.liteav.basic.c.d;

/* compiled from: TXCGPUSwirlFilter */
public class aa extends d {
    private float r;
    private int s;
    private float t;
    private int u;
    private PointF v;
    private int w;

    public boolean a() {
        boolean a = super.a();
        this.s = GLES20.glGetUniformLocation(q(), "angle");
        this.u = GLES20.glGetUniformLocation(q(), "radius");
        this.w = GLES20.glGetUniformLocation(q(), "center");
        return a;
    }

    public void d() {
        super.d();
        a(this.t);
        b(this.r);
        a(this.v);
    }

    public void a(float f) {
        this.t = f;
        a(this.u, f);
    }

    public void b(float f) {
        this.r = f;
        a(this.s, f);
    }

    public void a(PointF pointF) {
        this.v = pointF;
        a(this.w, pointF);
    }
}
