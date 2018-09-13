package com.tencent.liteav.f;

import android.opengl.GLES20;
import android.opengl.Matrix;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.rtmp.TXLiveConstants;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/* compiled from: TXTweenFilter */
public class l {
    private int a = 0;
    private int b = 0;
    private int c = 0;
    private int d = 0;
    private int e = 2;
    private int f = 0;
    private boolean g = false;
    private float[] h = new float[16];
    private float[] i = new float[16];
    private float j = 1.0f;
    private float k = 1.0f;
    private boolean l = false;
    private boolean m = true;
    private final float[] n = new float[]{-1.0f, -1.0f, 0.0f, 0.0f, 1.0f, 1.0f, -1.0f, 0.0f, 1.0f, 1.0f, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f};
    private FloatBuffer o;
    private float[] p = new float[16];
    private float[] q = new float[16];
    private int r;
    private int s = -12345;
    private int t = -12345;
    private int u;
    private int v;
    private int w;
    private int x;

    public void a(int i, int i2) {
        if (i != this.a || i2 != this.b) {
            TXCLog.d("TXTweenFilter", "Output resolution change: " + this.a + "*" + this.b + " -> " + i + "*" + i2);
            this.a = i;
            this.b = i2;
            if (i > i2) {
                Matrix.orthoM(this.h, 0, -1065353216, 1.0f, -1.0f, 1.0f, -1.0f, 1.0f);
                this.j = 1.0f;
                this.k = 1.0f;
            } else {
                Matrix.orthoM(this.h, 0, -1.0f, 1.0f, -1065353216, 1.0f, -1.0f, 1.0f);
                this.j = 1.0f;
                this.k = 1.0f;
            }
            this.l = true;
        }
    }

    public void b(int i, int i2) {
        if (i != this.c || i2 != this.d) {
            TXCLog.d("TXTweenFilter", "Input resolution change: " + this.c + "*" + this.d + " -> " + i + "*" + i2);
            this.c = i;
            this.d = i2;
        }
    }

    public void a(int i) {
        this.e = i;
    }

    private void a(float[] fArr) {
        if (this.b != 0 && this.a != 0) {
            int i = this.c;
            int i2 = this.d;
            if (this.f == 270 || this.f == 90) {
                i = this.d;
                i2 = this.c;
            }
            float f = (((float) this.a) * 1.0f) / ((float) i);
            float f2 = (((float) this.b) * 1.0f) / ((float) i2);
            if (this.e == 1) {
                if (((float) i2) * f <= ((float) this.b)) {
                    f = f2;
                }
            } else if (((float) i2) * f > ((float) this.b)) {
                f = f2;
            }
            Matrix.setIdentityM(this.i, 0);
            if (this.g) {
                if (this.f % TXLiveConstants.RENDER_ROTATION_180 == 0) {
                    Matrix.scaleM(this.i, 0, -1.0f, 1.0f, 1.0f);
                } else {
                    Matrix.scaleM(this.i, 0, 1.0f, -1.0f, 1.0f);
                }
            }
            Matrix.scaleM(this.i, 0, ((((float) i) * f) / ((float) this.a)) * 1.0f, ((((float) i2) * f) / ((float) this.b)) * 1.0f, 1.0f);
            Matrix.rotateM(this.i, 0, (float) this.f, 0.0f, 0.0f, -1.0f);
            Matrix.multiplyMM(fArr, 0, this.h, 0, this.i, 0);
        }
    }

    public l(Boolean bool) {
        this.m = bool.booleanValue();
        this.o = ByteBuffer.allocateDirect(this.n.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.o.put(this.n).position(0);
        Matrix.setIdentityM(this.q, 0);
    }

    public void b(int i) {
        GLES20.glViewport(0, 0, this.a, this.b);
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glClear(16640);
        GLES20.glUseProgram(this.r);
        a("glUseProgram");
        if (this.m) {
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(36197, i);
        } else {
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(3553, i);
        }
        this.o.position(0);
        GLES20.glVertexAttribPointer(this.w, 3, 5126, false, 20, this.o);
        a("glVertexAttribPointer maPosition");
        GLES20.glEnableVertexAttribArray(this.w);
        a("glEnableVertexAttribArray maPositionHandle");
        this.o.position(3);
        GLES20.glVertexAttribPointer(this.x, 2, 5126, false, 20, this.o);
        a("glVertexAttribPointer maTextureHandle");
        GLES20.glEnableVertexAttribArray(this.x);
        a("glEnableVertexAttribArray maTextureHandle");
        Matrix.setIdentityM(this.p, 0);
        a(this.p);
        GLES20.glUniformMatrix4fv(this.u, 1, false, this.p, 0);
        GLES20.glUniformMatrix4fv(this.v, 1, false, this.q, 0);
        a("glDrawArrays");
        GLES20.glDrawArrays(5, 0, 4);
        a("glDrawArrays");
        if (this.m) {
            GLES20.glBindTexture(36197, 0);
        } else {
            GLES20.glBindTexture(3553, 0);
        }
    }

    public void a() {
        if (this.m) {
            this.r = a("uniform mat4 uMVPMatrix;\nuniform mat4 uSTMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n  gl_Position = uMVPMatrix * aPosition;\n  vTextureCoord = (uSTMatrix * aTextureCoord).xy;\n}\n", "#extension GL_OES_EGL_image_external : require\nprecision mediump float;\nvarying vec2 vTextureCoord;\nuniform samplerExternalOES sTexture;\nvoid main() {\n  gl_FragColor = texture2D(sTexture, vTextureCoord);\n}\n");
        } else {
            this.r = a("uniform mat4 uMVPMatrix;\nuniform mat4 uSTMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n  gl_Position = uMVPMatrix * aPosition;\n  vTextureCoord = (uSTMatrix * aTextureCoord).xy;\n}\n", "varying highp vec2 vTextureCoord;\n \nuniform sampler2D sTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(sTexture, vTextureCoord);\n}");
        }
        if (this.r == 0) {
            throw new RuntimeException("failed creating program");
        }
        this.w = GLES20.glGetAttribLocation(this.r, "aPosition");
        a("glGetAttribLocation aPosition");
        if (this.w == -1) {
            throw new RuntimeException("Could not get attrib location for aPosition");
        }
        this.x = GLES20.glGetAttribLocation(this.r, "aTextureCoord");
        a("glGetAttribLocation aTextureCoord");
        if (this.x == -1) {
            throw new RuntimeException("Could not get attrib location for aTextureCoord");
        }
        this.u = GLES20.glGetUniformLocation(this.r, "uMVPMatrix");
        a("glGetUniformLocation uMVPMatrix");
        if (this.u == -1) {
            throw new RuntimeException("Could not get attrib location for uMVPMatrix");
        }
        this.v = GLES20.glGetUniformLocation(this.r, "uSTMatrix");
        a("glGetUniformLocation uSTMatrix");
        if (this.v == -1) {
            throw new RuntimeException("Could not get attrib location for uSTMatrix");
        }
    }

    public void b() {
        GLES20.glDeleteProgram(this.r);
        c();
    }

    private void c() {
        if (this.t != -12345) {
            GLES20.glDeleteFramebuffers(1, new int[]{this.t}, 0);
            this.t = -12345;
        }
        if (this.s != -12345) {
            GLES20.glDeleteTextures(1, new int[]{this.s}, 0);
            this.s = -12345;
        }
    }

    private int a(int i, String str) {
        int glCreateShader = GLES20.glCreateShader(i);
        a("glCreateShader type=" + i);
        GLES20.glShaderSource(glCreateShader, str);
        GLES20.glCompileShader(glCreateShader);
        int[] iArr = new int[1];
        GLES20.glGetShaderiv(glCreateShader, 35713, iArr, 0);
        if (iArr[0] != 0) {
            return glCreateShader;
        }
        TXCLog.e("TXTweenFilter", "Could not compile shader " + i + ":");
        TXCLog.e("TXTweenFilter", " " + GLES20.glGetShaderInfoLog(glCreateShader));
        GLES20.glDeleteShader(glCreateShader);
        return 0;
    }

    private int a(String str, String str2) {
        int a = a(35633, str);
        if (a == 0) {
            return 0;
        }
        int a2 = a(35632, str2);
        if (a2 == 0) {
            return 0;
        }
        int glCreateProgram = GLES20.glCreateProgram();
        a("glCreateProgram");
        if (glCreateProgram == 0) {
            TXCLog.e("TXTweenFilter", "Could not create program");
        }
        GLES20.glAttachShader(glCreateProgram, a);
        a("glAttachShader");
        GLES20.glAttachShader(glCreateProgram, a2);
        a("glAttachShader");
        GLES20.glLinkProgram(glCreateProgram);
        int[] iArr = new int[1];
        GLES20.glGetProgramiv(glCreateProgram, 35714, iArr, 0);
        if (iArr[0] == 1) {
            return glCreateProgram;
        }
        TXCLog.e("TXTweenFilter", "Could not link program: ");
        TXCLog.e("TXTweenFilter", GLES20.glGetProgramInfoLog(glCreateProgram));
        GLES20.glDeleteProgram(glCreateProgram);
        return 0;
    }

    private void a(String str) {
        int glGetError = GLES20.glGetError();
        if (glGetError != 0) {
            TXCLog.e("TXTweenFilter", str + ": glError " + glGetError);
            throw new RuntimeException(str + ": glError " + glGetError);
        }
    }
}
