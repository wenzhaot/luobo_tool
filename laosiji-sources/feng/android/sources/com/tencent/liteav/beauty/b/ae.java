package com.tencent.liteav.beauty.b;

import android.opengl.GLES20;
import com.tencent.liteav.basic.c.d;

/* compiled from: TXCGPUTwoPassTextureSamplingFilter */
public class ae extends ad {
    protected float u = 4.0f;

    public ae(String str, String str2, String str3, String str4) {
        super(str, str2, str3, str4);
    }

    public boolean a() {
        if (super.a() && GLES20.glGetError() == 0) {
            return true;
        }
        return false;
    }

    protected void v() {
        float u = u();
        d dVar = (d) this.r.get(0);
        int glGetUniformLocation = GLES20.glGetUniformLocation(dVar.q(), "texelWidthOffset");
        int glGetUniformLocation2 = GLES20.glGetUniformLocation(dVar.q(), "texelHeightOffset");
        dVar.a(glGetUniformLocation, u / ((float) this.e));
        dVar.a(glGetUniformLocation2, 0.0f);
        u = t();
        dVar = (d) this.r.get(1);
        glGetUniformLocation = GLES20.glGetUniformLocation(dVar.q(), "texelWidthOffset");
        glGetUniformLocation2 = GLES20.glGetUniformLocation(dVar.q(), "texelHeightOffset");
        dVar.a(glGetUniformLocation, 0.0f);
        dVar.a(glGetUniformLocation2, u / ((float) this.f));
    }

    public void a(int i, int i2) {
        super.a(i, i2);
        v();
    }

    public float t() {
        return this.u;
    }

    public float u() {
        return this.u;
    }
}
