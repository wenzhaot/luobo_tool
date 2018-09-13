package com.tencent.liteav.beauty.b;

import android.opengl.GLES20;
import com.tencent.liteav.basic.log.TXCLog;

/* compiled from: TXCGPUDissolveBlendFilter */
public class h extends ac {
    private static String r = "precision mediump float; \nvarying vec2 textureCoordinate;\nvarying vec2 textureCoordinate2;\n\nuniform sampler2D inputImageTexture;\nuniform sampler2D inputImageTexture2;\nuniform float mixturePercent;\n\nvoid main()\n{\n   vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n   vec4 textureColor2 = texture2D(inputImageTexture2, textureCoordinate2);\n   \n   gl_FragColor = mix(textureColor, textureColor2, mixturePercent);\n}\n";
    private static String s = "DissolveBlend";
    private int t = -1;

    public h() {
        super(r);
    }

    public boolean a() {
        if (super.a()) {
            this.t = GLES20.glGetUniformLocation(this.a, "mixturePercent");
            a(0.5f);
            return true;
        }
        TXCLog.e(s, "onInit failed");
        return false;
    }

    public void a(float f) {
        a(this.t, f);
    }
}
