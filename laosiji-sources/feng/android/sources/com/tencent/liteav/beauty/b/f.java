package com.tencent.liteav.beauty.b;

import android.opengl.GLES20;

/* compiled from: TXCGPUColorBrushFilter */
public class f extends ac {
    private static String r = "precision highp float;\nvarying mediump vec2 textureCoordinate;\nuniform sampler2D inputImageTexture;\n\nvarying mediump vec2 textureCoordinate2;\nuniform sampler2D inputImageTexture2;\n\nuniform mediump vec4 brushColor;\nuniform mediump vec4 fillColor;\n\nvoid main()\n{\n    // 第一个纹理 网络\n    vec4 texture1Color = texture2D(inputImageTexture, textureCoordinate);\n    // 第二个纹理 上一张纹理\n    vec4 texture2Color = texture2D(inputImageTexture2, textureCoordinate2);\n\n    if (brushColor.a == texture1Color.a || brushColor.a == texture2Color.a){\n        gl_FragColor = brushColor;\n    }else{\n        gl_FragColor = fillColor;\n    }\n}\n";
    private int s = -1;
    private int t = -1;

    public f() {
        super(r);
    }

    public boolean a() {
        boolean a = super.a();
        this.s = GLES20.glGetUniformLocation(q(), "brushColor");
        this.t = GLES20.glGetUniformLocation(q(), "fillColor");
        b(new float[]{1.0f, 1.0f, 1.0f, 1.0f});
        c(new float[]{0.0f, 0.0f, 0.0f, 0.0f});
        return a;
    }

    public void b(float[] fArr) {
        c(this.s, fArr);
    }

    public void c(float[] fArr) {
        c(this.t, fArr);
    }
}
