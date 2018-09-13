package com.tencent.liteav.beauty.b.b;

import android.opengl.GLES20;
import com.tencent.liteav.basic.c.d;
import com.tencent.liteav.beauty.NativeLoad;

/* compiled from: TXCGChannelBeautyFilter */
public class b extends d {
    private int r = -1;
    private int s = -1;
    private float[] t = new float[4];
    private String u = "Beauty3Filter";

    public b() {
        super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", "varying lowp vec2 textureCoordinate;\n \nuniform sampler2D inputImageTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}");
    }

    public boolean c() {
        NativeLoad.getInstance();
        this.a = NativeLoad.nativeLoadGLProgram(14);
        if (this.a == 0 || !a()) {
            this.g = false;
        } else {
            this.g = true;
        }
        d();
        return this.g;
    }

    public boolean a() {
        boolean a = super.a();
        this.r = GLES20.glGetUniformLocation(q(), "singleStepOffset");
        this.s = GLES20.glGetUniformLocation(q(), "beautyParams");
        a(5.0f);
        return a;
    }

    public void c(int i, int i2) {
        a(this.r, new float[]{2.0f / ((float) i), 2.0f / ((float) i2)});
    }

    public void a(int i, int i2) {
        super.a(i, i2);
        c(i, i2);
    }

    public void a(float f) {
        this.t[0] = f;
        b(this.t);
    }

    public void b(float f) {
        this.t[1] = f;
        b(this.t);
    }

    public void c(float f) {
        this.t[2] = f;
        b(this.t);
    }

    private void b(float[] fArr) {
        c(this.s, fArr);
    }
}
