package com.tencent.liteav.basic.c;

import android.opengl.GLES20;
import java.nio.FloatBuffer;

/* compiled from: TXCGPUOESTextureFilter */
public class e extends d {
    private float[] r;
    private int s;

    public boolean a() {
        boolean a = super.a();
        this.s = GLES20.glGetUniformLocation(this.a, "textureTransform");
        if (a && GLES20.glGetError() == 0) {
            return true;
        }
        return false;
    }

    public void a(float[] fArr) {
        this.r = fArr;
    }

    public void a(int i, FloatBuffer floatBuffer, FloatBuffer floatBuffer2) {
        GLES20.glUseProgram(this.a);
        k();
        if (n() && this.r != null) {
            floatBuffer.position(0);
            GLES20.glVertexAttribPointer(this.b, 2, 5126, false, 0, floatBuffer);
            GLES20.glEnableVertexAttribArray(this.b);
            floatBuffer2.position(0);
            GLES20.glVertexAttribPointer(this.d, 2, 5126, false, 0, floatBuffer2);
            GLES20.glEnableVertexAttribArray(this.d);
            GLES20.glUniformMatrix4fv(this.s, 1, false, this.r, 0);
            if (i != -1) {
                GLES20.glActiveTexture(33984);
                GLES20.glBindTexture(36197, i);
                GLES20.glUniform1i(this.c, 0);
            }
            GLES20.glDrawArrays(5, 0, 4);
            GLES20.glDisableVertexAttribArray(this.b);
            GLES20.glDisableVertexAttribArray(this.d);
            GLES20.glBindTexture(36197, 0);
        }
    }
}
