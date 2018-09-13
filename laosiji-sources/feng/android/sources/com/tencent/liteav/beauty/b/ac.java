package com.tencent.liteav.beauty.b;

import android.opengl.GLES20;
import com.tencent.liteav.basic.c.d;
import com.tencent.liteav.basic.c.g;
import com.tencent.liteav.basic.c.h;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/* compiled from: TXCGPUTwoInputFilter */
public class ac extends d {
    private ByteBuffer r;
    public int u;
    public int v;
    public int w;

    public ac(String str) {
        this("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\nattribute vec4 inputTextureCoordinate2;\n \nvarying vec2 textureCoordinate;\nvarying vec2 textureCoordinate2;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n    textureCoordinate2 = inputTextureCoordinate2.xy;\n}", str);
    }

    public ac(String str, String str2) {
        super(str, str2);
        this.u = -1;
        this.w = -1;
        a(g.NORMAL, false, true);
    }

    public boolean a() {
        boolean a = super.a();
        if (a) {
            this.u = GLES20.glGetAttribLocation(q(), "inputTextureCoordinate2");
            this.v = GLES20.glGetUniformLocation(q(), "inputImageTexture2");
            GLES20.glEnableVertexAttribArray(this.u);
        }
        return a;
    }

    public void b() {
        super.b();
    }

    protected void i() {
        GLES20.glActiveTexture(33987);
        GLES20.glBindTexture(3553, this.w);
        GLES20.glUniform1i(this.v, 3);
        if (this.u != -1) {
            GLES20.glEnableVertexAttribArray(this.u);
            this.r.position(0);
            GLES20.glVertexAttribPointer(this.u, 2, 5126, false, 0, this.r);
        }
    }

    public void a(g gVar, boolean z, boolean z2) {
        float[] a = h.a(gVar, z, z2);
        ByteBuffer order = ByteBuffer.allocateDirect(32).order(ByteOrder.nativeOrder());
        FloatBuffer asFloatBuffer = order.asFloatBuffer();
        asFloatBuffer.put(a);
        asFloatBuffer.flip();
        this.r = order;
    }

    public int c(int i, int i2) {
        this.w = i2;
        return a(i, this.m, this.n);
    }

    public int a(int i, int i2, int i3, int i4) {
        this.w = i2;
        return a(i, i3, i4);
    }

    public int d(int i, int i2) {
        this.w = i2;
        return b(i);
    }
}
