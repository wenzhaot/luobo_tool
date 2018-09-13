package com.tencent.liteav.k;

import android.opengl.GLES20;
import android.util.Log;
import com.tencent.liteav.basic.c.d;
import com.tencent.liteav.k.n.c;

/* compiled from: TXCGPUGridGeneralFilter */
public class f extends d {
    private static String r = "attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n\nvarying vec2 textureCoordinate;\nvarying vec2 textureNoRotate; // 保证以中心点旋转\n\n// 旋转逻辑\nuniform mat4 textureTransform;\n\nvoid main() \n{ \n  gl_Position = position;\n  textureCoordinate = (textureTransform * inputTextureCoordinate).xy;\n  textureNoRotate = inputTextureCoordinate.xy;\n}\n";
    private static String s = "precision mediump float; \nvarying highp vec2 textureCoordinate;\nuniform sampler2D inputImageTexture; \n \n// x 轴竖条\nuniform float xOffset;\nuniform float xWidth;\nuniform float xStride;\n\n// y 轴竖条\nuniform float yOffset;\nuniform float yWidth;\nuniform float yStride;\n\n// 中心点\nuniform vec2 center;\n// 网格半径\nuniform float radius;\n// 宽高比\nuniform float aspectRatio;\n// 放大 或 缩小\nuniform int zoomModel;  // 0 放大，1缩小\n\nuniform float maxRadius;\n\nvoid drawGrid(){\n    // 第一步：画黑色矩形框\n    // 黑色竖条\n    float mx = mod(textureCoordinate.x + xOffset, xStride); \n    float my = mod(textureCoordinate.y + yOffset, yStride);\n\n    if(mx <= xWidth || my <= yWidth){ \n        gl_FragColor = vec4(1.0, 1.0, 1.0, 1.0); \n    }else{\n        gl_FragColor = vec4(0.0, 0.0, 0.0, 0.0);\n    }\n\n}\n\nvoid main()\n{ \n    highp vec2 textureCoordinateToUse = vec2(textureCoordinate.x, (textureCoordinate.y * aspectRatio + 0.5 - 0.5 * aspectRatio));\n    highp float cRadius = distance(center, textureCoordinateToUse);\n\n    // 如果是缩小模式\n    if (1 == zoomModel){\n        if (cRadius < maxRadius && cRadius > radius){\n            drawGrid();\n        }else{\n            gl_FragColor = vec4(0.0, 0.0, 0.0, 0.0);\n        }\n    }else{\n        if (cRadius < radius){\n            drawGrid();\n        }else{\n            gl_FragColor = vec4(0.0, 0.0, 0.0, 0.0);\n        }\n    }\n}\n";
    private static String t = "Diffuse";
    private int A = -1;
    private int B = -1;
    private int C = -1;
    private int D = -1;
    private int E = -1;
    private int F = -1;
    private final float[] G = new float[]{1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f};
    private float[] H = ((float[]) this.G.clone());
    private int u = -1;
    private int v = -1;
    private int w = -1;
    private int x = -1;
    private int y = -1;
    private int z = -1;

    public f() {
        super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", s);
    }

    public boolean a() {
        if (super.a()) {
            this.u = GLES20.glGetUniformLocation(q(), "xOffset");
            this.v = GLES20.glGetUniformLocation(q(), "xWidth");
            this.w = GLES20.glGetUniformLocation(q(), "xStride");
            this.x = GLES20.glGetUniformLocation(q(), "yOffset");
            this.y = GLES20.glGetUniformLocation(q(), "yWidth");
            this.z = GLES20.glGetUniformLocation(q(), "yStride");
            this.A = GLES20.glGetUniformLocation(q(), "textureTransform");
            this.B = GLES20.glGetUniformLocation(q(), "radius");
            this.C = GLES20.glGetUniformLocation(q(), "center");
            this.D = GLES20.glGetUniformLocation(q(), "aspectRatio");
            this.E = GLES20.glGetUniformLocation(q(), "zoomModel");
            this.F = GLES20.glGetUniformLocation(q(), "maxRadius");
            a(this.C, new float[]{0.5f, 0.5f});
            return true;
        }
        Log.e(t, "super.onInit failed");
        return false;
    }

    public void a(c cVar) {
        a(this.u, cVar.a);
        a(this.v, cVar.b);
        a(this.w, cVar.c);
        a(this.x, cVar.a);
        a(this.y, cVar.b);
        a(this.z, cVar.c);
        a(this.B, cVar.f);
        b(this.E, cVar.g.value);
        a(this.F, cVar.e);
    }

    public void a(int i, int i2) {
        if (this.f != i2 || this.e != i) {
            super.a(i, i2);
            a(this.D, (((float) i2) * 1.0f) / ((float) i));
        }
    }
}
