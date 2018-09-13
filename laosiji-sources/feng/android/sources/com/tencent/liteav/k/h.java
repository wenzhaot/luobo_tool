package com.tencent.liteav.k;

import android.opengl.GLES20;
import com.taobao.accs.common.Constants;
import com.tencent.liteav.basic.c.d;
import com.tencent.liteav.k.n.i;

/* compiled from: TXCGPULinearShadowFilter */
public class h extends d {
    private int r = -1;
    private int s = -1;
    private int t = -1;
    private int u = -1;
    private int v = -1;
    private int w = -1;
    private int x = -1;
    private int y = -1;

    public h() {
        super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", "precision mediump float; \nvarying highp vec2 textureCoordinate; \nuniform sampler2D inputImageTexture; \n \nuniform float a; \nuniform float b; \nuniform float c; \nuniform float d; \nuniform float mode; \nuniform float width; \nuniform float stride; \nuniform float alpha; \n \nvoid main() \n{ \n\tgl_FragColor = texture2D(inputImageTexture, textureCoordinate); \n    if(b == 0.0){ \n\t\t//float mx = textureCoordinate.x > (stride - c) ? mod(textureCoordinate.x + c, stride) - c : textureCoordinate.x; \n\t\t//if((mode < 0.5 && mx > -1.0*c && mx <= width - c) || (mode > 0.5 && (mx > width - c || mx < -1.0 *c))){ \n\t\tfloat mx = mod(textureCoordinate.x + c, stride); \n\t\tif((mode < 0.5 && mx <= width) || (mode > 0.5 && (mx > width))){ \n\t\t\tgl_FragColor.rgb = gl_FragColor.rgb*alpha; \n\t\t} \n\t} \n} \n");
    }

    public void a(int i, int i2) {
        super.a(i, i2);
    }

    public boolean a() {
        if (!super.a()) {
            return false;
        }
        this.s = GLES20.glGetUniformLocation(this.a, "b");
        this.t = GLES20.glGetUniformLocation(this.a, "c");
        this.v = GLES20.glGetUniformLocation(this.a, Constants.KEY_MODE);
        this.w = GLES20.glGetUniformLocation(this.a, "width");
        this.x = GLES20.glGetUniformLocation(this.a, "stride");
        this.y = GLES20.glGetUniformLocation(this.a, "alpha");
        return true;
    }

    public void a(i iVar) {
        a(iVar.a, iVar.b, iVar.c, iVar.d, iVar.e);
    }

    private void a(float f, float f2, float f3, float f4, float f5) {
        a(this.v, f);
        a(this.y, f2);
        a(this.t, -1.0f * f3);
        a(this.w, f4);
        a(this.x, f5);
    }
}
