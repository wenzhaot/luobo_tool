package com.tencent.liteav.k;

import com.tencent.liteav.beauty.b.ah;
import com.tencent.liteav.beauty.b.ah.a;
import com.tencent.liteav.beauty.d.d;

/* compiled from: TXCGPUWatermarkTextureFilter */
public class l extends ah {
    private String x;

    public l(String str, String str2) {
        super(str, str2);
        this.x = "WatermarkTexture";
        this.t = true;
        this.u = 770;
    }

    public l() {
        this("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", "varying lowp vec2 textureCoordinate;\n \nuniform sampler2D inputImageTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}");
    }

    public void a(d[] dVarArr) {
        if (this.r == null) {
            this.r = new a[dVarArr.length];
        }
        for (int i = 0; i < dVarArr.length; i++) {
            if (this.r[i] == null) {
                this.r[i] = new a();
            }
            if (this.r[i].d == null) {
                this.r[i].d = new int[1];
            }
            a(dVarArr[i].f, dVarArr[i].g, dVarArr[i].b, dVarArr[i].c, dVarArr[i].d, i);
            this.r[i].d[0] = dVarArr[i].e;
        }
    }
}
