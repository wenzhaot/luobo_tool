package com.tencent.liteav.beauty.b;

import android.opengl.GLES20;
import android.opengl.Matrix;
import com.tencent.liteav.basic.c.d;
import com.tencent.liteav.basic.c.d.a;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/* compiled from: TXCGPURotateScaleFilter */
public class x extends d {
    private static String r = "precision mediump float;\nvarying mediump vec2 textureCoordinate;\nuniform sampler2D inputImageTexture;\nuniform float scale;\n uniform mediump float alpha;\n\nvoid main(void) {\n    gl_FragColor = vec4(texture2D(inputImageTexture, textureCoordinate).rgb, alpha); \n}\n";
    private float A = 1.0f;
    private boolean B;
    private int s;
    private int t;
    private float[] u = new float[16];
    private int v = -1;
    private int w = -1;
    private int x = -1;
    private float[] y;
    private float z = 1.0f;

    public x() {
        super("attribute vec4 position;\n attribute vec4 inputTextureCoordinate;\n \n uniform mat4 transformMatrix;\n uniform mat4 orthographicMatrix;\n \n varying vec2 textureCoordinate;\n void main()\n {\n     gl_Position = transformMatrix * vec4(position.xyz, 1.0) * orthographicMatrix;\n     textureCoordinate = inputTextureCoordinate.xy;\n }", r);
        Matrix.orthoM(this.u, 0, -1.0f, 1.0f, -1.0f, 1.0f, -1.0f, 1.0f);
        this.y = new float[16];
        Matrix.setIdentityM(this.y, 0);
    }

    public boolean a() {
        boolean a = super.a();
        this.s = GLES20.glGetUniformLocation(q(), "transformMatrix");
        this.t = GLES20.glGetUniformLocation(q(), "orthographicMatrix");
        this.v = GLES20.glGetUniformLocation(q(), "scale");
        this.w = GLES20.glGetUniformLocation(q(), "center");
        this.x = GLES20.glGetUniformLocation(q(), "alpha");
        d(this.s, this.y);
        d(this.t, this.u);
        a(this.v, this.z);
        b(this.A);
        a(this.w, new float[]{0.5f, 0.5f});
        return a;
    }

    public void d() {
        super.d();
    }

    public void a(int i, int i2) {
        if (this.f != i2 || this.e != i) {
            super.a(i, i2);
            if (!this.B) {
                Matrix.orthoM(this.u, 0, -1.0f, 1.0f, (((float) i2) * -1.0f) / ((float) i), (((float) i2) * 1.0f) / ((float) i), -1.0f, 1.0f);
                d(this.t, this.u);
            }
        }
    }

    public int a(int i, int i2, int i3) {
        if (!this.g) {
            return -1;
        }
        GLES20.glBindFramebuffer(36160, i2);
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES20.glClear(16640);
        a(i, this.h, this.i);
        if (this.l instanceof a) {
            this.l.a(i3);
        }
        GLES20.glBindFramebuffer(36160, 0);
        return i3;
    }

    public void a(int i, FloatBuffer floatBuffer, FloatBuffer floatBuffer2) {
        if (!this.B) {
            float[] fArr = new float[8];
            floatBuffer.position(0);
            floatBuffer.get(fArr);
            float p = ((float) p()) / ((float) o());
            fArr[1] = fArr[1] * p;
            fArr[3] = fArr[3] * p;
            fArr[5] = fArr[5] * p;
            fArr[7] = p * fArr[7];
            floatBuffer = ByteBuffer.allocateDirect(fArr.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
            floatBuffer.put(fArr).position(0);
        }
        super.a(i, floatBuffer, floatBuffer2);
    }

    public void a(float f) {
        a(null, f);
    }

    private float[] a(float[] fArr, float f) {
        float[] fArr2;
        if (fArr == null) {
            fArr2 = new float[16];
            Matrix.setIdentityM(fArr2, 0);
        } else {
            fArr2 = fArr;
        }
        Matrix.setRotateM(fArr2, 0, f, 0.0f, 0.0f, 1.0f);
        this.y = fArr2;
        d(this.s, this.y);
        return fArr2;
    }

    private float[] b(float[] fArr, float f) {
        if (fArr == null) {
            fArr = new float[16];
            Matrix.setIdentityM(fArr, 0);
        }
        Matrix.scaleM(fArr, 0, f, f, 1.0f);
        this.y = fArr;
        d(this.s, this.y);
        return fArr;
    }

    public float[] a(float f, float f2) {
        return b(a(null, f), f2);
    }

    public void b(float f) {
        a(this.x, f);
    }
}
