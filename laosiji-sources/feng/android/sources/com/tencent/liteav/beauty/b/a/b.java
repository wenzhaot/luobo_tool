package com.tencent.liteav.beauty.b.a;

import android.opengl.GLES20;
import android.util.Log;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.beauty.NativeLoad;
import com.tencent.liteav.beauty.b.ac;

/* compiled from: TXCBeautyBlend */
public class b extends ac {
    private int r = -1;
    private int s = -1;
    private int t = -1;
    private final String x = "BeautyBlend";

    public b() {
        super("varying lowp vec2 textureCoordinate;\n \nuniform sampler2D inputImageTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}");
    }

    public boolean c() {
        NativeLoad.getInstance();
        this.a = NativeLoad.nativeLoadGLProgram(12);
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
        r();
        return true;
    }

    public void a(float f) {
        TXCLog.i("BeautyBlend", "setBrightLevel " + f);
        a(this.s, f);
    }

    public void b(float f) {
        Log.i("BeautyBlend", "setRuddyLevel level " + f);
        a(this.t, f / 2.0f);
    }

    private void r() {
        this.s = GLES20.glGetUniformLocation(q(), "whiteDegree");
        this.r = GLES20.glGetUniformLocation(q(), "contrast");
        this.t = GLES20.glGetUniformLocation(q(), "ruddyDegree");
    }
}
