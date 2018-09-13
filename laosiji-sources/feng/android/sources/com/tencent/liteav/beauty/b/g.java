package com.tencent.liteav.beauty.b;

import android.opengl.GLES20;

/* compiled from: TXCGPUColorScreenFilter */
public class g extends ab {
    private int A;
    private float[] B;
    private int x;
    private int y;
    private int z;

    public void d() {
        super.d();
        this.x = GLES20.glGetUniformLocation(q(), "screenMode");
        this.y = GLES20.glGetUniformLocation(q(), "screenReplaceColor");
        this.z = GLES20.glGetUniformLocation(q(), "screenMirrorX");
        this.A = GLES20.glGetUniformLocation(q(), "screenMirrorY");
        b(this.B);
    }

    public void b(float[] fArr) {
        b(this.y, new float[]{(float) (((0.2989d * ((double) fArr[0])) + (0.5866d * ((double) fArr[1]))) + (0.1145d * ((double) fArr[2]))), (float) (0.7132d * ((double) (fArr[0] - r0[0]))), (float) (0.5647d * ((double) (fArr[2] - r0[0])))});
    }
}
