package com.tencent.liteav.beauty.b;

import android.opengl.GLES20;
import com.tencent.liteav.basic.c.d;

/* compiled from: TXCGPUPurlColorFilter */
public class u extends d {
    private int r = -1;

    public boolean a() {
        boolean a = super.a();
        this.r = GLES20.glGetUniformLocation(q(), "purlColor");
        c(this.r, new float[]{0.0f, 0.0f, 0.0f, 1.0f});
        return a;
    }

    public void b(float[] fArr) {
        c(this.r, fArr);
    }
}
