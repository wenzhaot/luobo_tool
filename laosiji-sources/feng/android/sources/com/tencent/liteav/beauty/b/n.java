package com.tencent.liteav.beauty.b;

import android.opengl.GLES20;

/* compiled from: TXCGPUGridBlendFilter */
public class n extends ab {
    private static String x = "precision highp float;\nvarying mediump vec2 textureCoordinate;\nuniform sampler2D inputImageTexture;\n\nvarying mediump vec2 textureCoordinate2;\nuniform sampler2D inputImageTexture2;\n\nvarying mediump vec2 textureCoordinate3;\nuniform sampler2D inputImageTexture3;\n\nuniform mediump float scale1;\n\nmediump vec4 textureScale(sampler2D texture, mediump vec2 coor, mediump float scale){\n\t vec2 rCoor = textureCoordinate - (1.0 - scale) * (vec2(0.5, 0.5) - textureCoordinate);\n     float x = rCoor.x;\n     float y = rCoor.y;\n\n    vec4 scaleColor = texture2D(texture, coor);\n     if (x < 0.0 || x > 1.0 || y < 0.0 || y > 1.0) { \n         scaleColor = vec4(1.0, 1.0, 1.0, 1.0); \n     } else { \n         scaleColor = texture2D(texture, vec2(x, y)); \n     } \n\n     return scaleColor;\n}\n\nvoid main()\n{\n    // 第一个纹理 网络(需要放大)\n    vec4 gridColor = texture2D(inputImageTexture, textureCoordinate);\n    if (1.0 != scale1){\n    \tgridColor = textureScale(inputImageTexture, textureCoordinate, scale1);\n    }\n\n    // 第二个纹理 上一张纹理\n    vec4 prevColor = texture2D(inputImageTexture2, textureCoordinate2);\n    // 第三个纹理 当前纹理\n    vec4 currentColor = texture2D(inputImageTexture3, textureCoordinate3);\n\n    // 如果 alpha 为1.0，则显示当前放大 或 缩小的图片\n    if (0.0 == gridColor.a){\n        gl_FragColor = prevColor;\n    }else{\n        gl_FragColor = currentColor;\n    }\n}\n";
    private int y = -1;

    public n() {
        super(x);
    }

    public boolean a() {
        boolean a = super.a();
        this.y = GLES20.glGetUniformLocation(q(), "scale1");
        a(1.5f);
        return a;
    }

    public void a(float f) {
        a(this.y, f);
    }
}
