package com.tencent.liteav.beauty.b;

import android.opengl.GLES20;
import com.tencent.liteav.basic.c.d;
import com.tencent.liteav.basic.c.g;
import com.tencent.liteav.basic.c.h;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/* compiled from: TXCGPUThreeInputFilter */
public class ab extends d {
    public int r;
    public int s;
    public int t;
    public int u;
    public int v;
    public int w;
    private ByteBuffer x;
    private ByteBuffer y;

    public ab(String str) {
        this("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\nattribute vec4 inputTextureCoordinate2;\nattribute vec4 inputTextureCoordinate3;\n \nvarying vec2 textureCoordinate;\nvarying vec2 textureCoordinate2;\nvarying vec2 textureCoordinate3;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n    textureCoordinate2 = inputTextureCoordinate2.xy;\n    textureCoordinate3 = inputTextureCoordinate3.xy;\n}", str);
        a(g.NORMAL, false, true);
    }

    public ab(String str, String str2) {
        super(str, str2);
        this.t = -1;
        this.w = -1;
        a(g.NORMAL, false, true);
    }

    public boolean a() {
        boolean a = super.a();
        GLES20.glUseProgram(q());
        this.r = GLES20.glGetAttribLocation(q(), "inputTextureCoordinate2");
        this.u = GLES20.glGetAttribLocation(q(), "inputTextureCoordinate3");
        this.s = GLES20.glGetUniformLocation(q(), "inputImageTexture2");
        this.v = GLES20.glGetUniformLocation(q(), "inputImageTexture3");
        GLES20.glEnableVertexAttribArray(this.r);
        GLES20.glEnableVertexAttribArray(this.u);
        return a;
    }

    public int a(int i, int i2, int i3) {
        return a(i, i2, i3, this.m, this.n);
    }

    public int a(int i, int i2, int i3, int i4, int i5) {
        this.t = i2;
        this.w = i3;
        return super.a(i, i4, i5);
    }

    public int b(int i, int i2, int i3) {
        this.t = i2;
        this.w = i3;
        return b(i);
    }

    public void a(int i, int i2) {
        super.a(i, i2);
    }

    protected void i() {
        GLES20.glEnableVertexAttribArray(this.r);
        GLES20.glActiveTexture(33987);
        GLES20.glBindTexture(3553, this.t);
        GLES20.glUniform1i(this.s, 3);
        this.x.position(0);
        GLES20.glVertexAttribPointer(this.r, 2, 5126, false, 0, this.x);
        GLES20.glEnableVertexAttribArray(this.u);
        GLES20.glActiveTexture(33988);
        GLES20.glBindTexture(3553, this.w);
        GLES20.glUniform1i(this.v, 4);
        this.y.position(0);
        GLES20.glVertexAttribPointer(this.u, 2, 5126, false, 0, this.y);
    }

    public void a(g gVar, boolean z, boolean z2) {
        float[] a = h.a(gVar, z, z2);
        ByteBuffer order = ByteBuffer.allocateDirect(32).order(ByteOrder.nativeOrder());
        FloatBuffer asFloatBuffer = order.asFloatBuffer();
        asFloatBuffer.put(a);
        asFloatBuffer.flip();
        this.x = order;
        order = ByteBuffer.allocateDirect(32).order(ByteOrder.nativeOrder());
        asFloatBuffer = order.asFloatBuffer();
        asFloatBuffer.put(a);
        asFloatBuffer.flip();
        this.y = order;
    }
}
