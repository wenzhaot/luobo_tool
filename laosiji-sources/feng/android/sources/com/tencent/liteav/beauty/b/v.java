package com.tencent.liteav.beauty.b;

import android.opengl.GLES20;
import com.tencent.liteav.basic.c.d;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.beauty.NativeLoad;

/* compiled from: TXCGPURGBA2I420Filter */
public class v extends d {
    private static float[] C = new float[]{0.1826f, 0.6142f, 0.062f, -0.1006f, -0.3386f, 0.4392f, 0.4392f, -0.3989f, -0.0403f};
    private static float[] D = new float[]{0.256816f, 0.504154f, 0.0979137f, -0.148246f, -0.29102f, 0.439266f, 0.439271f, -0.367833f, -0.071438f};
    private static float[] E = new float[]{0.0625f, 0.5f, 0.5f};
    private String A = "RGBA2I420Filter";
    private int B = 1;
    private int r = -1;
    private int s = -1;
    private int t = -1;
    private int u = -1;
    private int v = -1;
    private int w = -1;
    private int x = -1;
    private int y = -1;
    private int z = -1;

    public v(int i) {
        super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", "varying lowp vec2 textureCoordinate;\n \nuniform sampler2D inputImageTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}");
        this.B = i;
    }

    public boolean c() {
        if (1 == this.B) {
            NativeLoad.getInstance();
            this.a = NativeLoad.nativeLoadGLProgram(8);
            TXCLog.i(this.A, "RGB-->I420 init!");
        } else if (3 == this.B) {
            TXCLog.i(this.A, "RGB-->NV21 init!");
            NativeLoad.getInstance();
            this.a = NativeLoad.nativeLoadGLProgram(11);
        } else if (2 == this.B) {
            TXCLog.i(this.A, "RGBA Format init!");
            return super.c();
        } else {
            TXCLog.i(this.A, "don't support format " + this.B + " use default I420");
            NativeLoad.getInstance();
            this.a = NativeLoad.nativeLoadGLProgram(8);
        }
        if (this.a == 0 || !a()) {
            this.g = false;
        } else {
            this.g = true;
        }
        d();
        return this.g;
    }

    public boolean a() {
        super.a();
        this.r = GLES20.glGetUniformLocation(this.a, "width");
        this.s = GLES20.glGetUniformLocation(this.a, "height");
        return true;
    }

    public void a(int i, int i2) {
        if (i <= 0 || i2 <= 0) {
            TXCLog.e(this.A, "width or height is error!");
            return;
        }
        super.a(i, i2);
        TXCLog.i(this.A, "RGBA2I420Filter width " + i + " height " + i2);
        a(this.r, (float) i);
        a(this.s, (float) i2);
    }

    public void d() {
        super.d();
    }
}
