package com.tencent.liteav.beauty.b;

import android.opengl.GLES20;
import android.opengl.Matrix;
import com.tencent.liteav.basic.c.d;
import com.tencent.liteav.basic.log.TXCLog;
import java.nio.FloatBuffer;

/* compiled from: TXCZoomInOutFilter */
public class aj extends d {
    private static String x = "ZoomInOut";
    private int r = -1;
    private int s = -1;
    private int t = -1;
    private int u = -1;
    private int v = -1;
    private float w = 0.3f;
    private final float[] y = new float[]{1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f};
    private float[] z = ((float[]) this.y.clone());

    public aj() {
        super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nuniform mat4 textureTransform;\nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = (textureTransform * inputTextureCoordinate).xy;\n}", "precision highp float;\nvarying lowp vec2 textureCoordinate;\n \nuniform sampler2D inputImageTexture;\nuniform lowp float alphaLevel;\nuniform vec2 offsetR; \nuniform vec2 offsetG;\nuniform vec2 offsetB;\n\nvoid main()\n{\n\tmediump vec4 fout;\n\tfout.r = texture2D(inputImageTexture, textureCoordinate + offsetR).r; \n\tfout.g = texture2D(inputImageTexture, textureCoordinate + offsetG).g; \n\tfout.b = texture2D(inputImageTexture, textureCoordinate + offsetB).b; \n\tfout.a = alphaLevel;\n\n    gl_FragColor = fout;\n}\n\n");
    }

    public boolean a() {
        if (super.a()) {
            this.r = GLES20.glGetUniformLocation(this.a, "textureTransform");
            this.v = GLES20.glGetUniformLocation(this.a, "alphaLevel");
            this.s = GLES20.glGetUniformLocation(this.a, "offsetR");
            this.t = GLES20.glGetUniformLocation(this.a, "offsetG");
            this.u = GLES20.glGetUniformLocation(this.a, "offsetB");
            a(this.w);
            return true;
        }
        TXCLog.e(x, "onInit failed");
        return false;
    }

    public void a(float[] fArr, float[] fArr2, float[] fArr3) {
        a(this.s, fArr);
        a(this.t, fArr2);
        a(this.u, fArr3);
    }

    public void a(float f) {
        this.w = f;
        a(this.v, this.w);
    }

    public void a(float f, int i) {
        if (f <= 0.0f) {
            this.z = (float[]) this.y.clone();
            return;
        }
        this.z = (float[]) this.y.clone();
        for (int i2 = 0; i2 < i; i2++) {
            float[] fArr = new float[16];
            Matrix.setIdentityM(fArr, 0);
            Matrix.scaleM(fArr, 0, f, f, 1.0f);
            Matrix.multiplyMM(this.z, 0, fArr, 0, this.z, 0);
            Matrix.setIdentityM(fArr, 0);
            Matrix.translateM(fArr, 0, 0.02f, 0.02f, 1.0f);
            Matrix.multiplyMM(this.z, 0, fArr, 0, this.z, 0);
        }
    }

    public int a(int i) {
        return a(i, this.m, this.n);
    }

    public void a(int i, FloatBuffer floatBuffer, FloatBuffer floatBuffer2) {
        GLES20.glUseProgram(this.a);
        k();
        if (n() && this.z != null) {
            floatBuffer.position(0);
            GLES20.glVertexAttribPointer(this.b, 2, 5126, false, 0, floatBuffer);
            GLES20.glEnableVertexAttribArray(this.b);
            floatBuffer2.position(0);
            GLES20.glVertexAttribPointer(this.d, 2, 5126, false, 0, floatBuffer2);
            GLES20.glEnableVertexAttribArray(this.d);
            GLES20.glUniformMatrix4fv(this.r, 1, false, this.z, 0);
            if (i != -1) {
                GLES20.glActiveTexture(33984);
                GLES20.glBindTexture(3553, i);
                GLES20.glUniform1i(this.c, 0);
            }
            GLES20.glDrawArrays(5, 0, 4);
            GLES20.glDisableVertexAttribArray(this.b);
            GLES20.glDisableVertexAttribArray(this.d);
            GLES20.glBindTexture(3553, 0);
        }
    }
}
