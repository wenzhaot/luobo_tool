package com.tencent.liteav;

import android.opengl.GLES20;
import com.taobao.accs.common.Constants;
import com.tencent.liteav.basic.c.d;
import com.tencent.liteav.k.n.j;

/* compiled from: TXCGPUMirrorFilter */
public class b extends d {
    private int r = -1;

    public b() {
        super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", "varying highp vec2 textureCoordinate; \nuniform sampler2D inputImageTexture; \nuniform lowp float mode; \n \nvoid main() \n{ \n    highp vec2 position = textureCoordinate; \n     \n    if (mode <= 0.5) \n    { \n        if (position.x > 0.5) \n        { \n            position.x = 1.0 - position.x; \n        } \n    } \n    else \n    { \n        if (position.x > 0.5) \n        { \n            position.x = position.x - 0.5; \n        } \n        else \n        { \n            position.x = 0.5 - position.x; \n        } \n    } \n     \n    gl_FragColor = texture2D(inputImageTexture, position); \n} \n");
    }

    public boolean a() {
        boolean a = super.a();
        this.r = GLES20.glGetUniformLocation(this.a, Constants.KEY_MODE);
        return a;
    }

    public void a(j jVar) {
        a(this.r, jVar.a);
    }
}
