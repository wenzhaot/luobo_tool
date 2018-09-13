package com.tencent.liteav.beauty.b;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;
import com.tencent.liteav.basic.c.d;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.List;

/* compiled from: TXCGPUWatermarkFilter */
public class ah extends d {
    private static final float[] A = new float[]{0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f};
    protected static final short[] v = new short[]{(short) 1, (short) 2, (short) 0, (short) 2, (short) 0, (short) 3};
    private static final float[] z = new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
    private String B;
    protected a[] r;
    protected List<com.tencent.liteav.beauty.d.d> s;
    protected boolean t;
    protected int u;
    protected ShortBuffer w;
    private a x;
    private int y;

    /* compiled from: TXCGPUWatermarkFilter */
    public class a {
        public FloatBuffer a = null;
        public FloatBuffer b = null;
        public Bitmap c;
        public int[] d = null;
    }

    public ah(String str, String str2) {
        super(str, str2);
        this.r = null;
        this.x = null;
        this.s = null;
        this.t = false;
        this.u = 1;
        this.y = 1;
        this.w = null;
        this.B = "GPUWatermark";
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(v.length * 2);
        allocateDirect.order(ByteOrder.nativeOrder());
        this.w = allocateDirect.asShortBuffer();
        this.w.put(v);
        this.w.position(0);
        this.o = true;
    }

    public ah() {
        this("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", "varying lowp vec2 textureCoordinate;\n \nuniform sampler2D inputImageTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}");
    }

    public void e() {
        super.e();
        this.t = false;
        r();
    }

    public void d(boolean z) {
        this.t = z;
    }

    public void a(Bitmap bitmap, float f, float f2, float f3, int i) {
        if (bitmap == null) {
            if (this.r != null && this.r[i] != null) {
                Log.i(this.B, "release " + i + " water mark!");
                if (this.r[i].d != null) {
                    GLES20.glDeleteTextures(1, this.r[i].d, 0);
                }
                this.r[i].d = null;
                this.r[i].c = null;
                this.r[i] = null;
            }
        } else if (this.r[i] == null || i >= this.r.length) {
            Log.e(this.B, "index is too large for mSzWaterMark!");
        } else {
            a(bitmap.getWidth(), bitmap.getHeight(), f, f2, f3, i);
            if (this.r[i].d == null) {
                this.r[i].d = new int[1];
                GLES20.glGenTextures(1, this.r[i].d, 0);
                GLES20.glBindTexture(3553, this.r[i].d[0]);
                GLES20.glTexParameterf(3553, 10240, 9729.0f);
                GLES20.glTexParameterf(3553, 10241, 9729.0f);
                GLES20.glTexParameterf(3553, 10242, 33071.0f);
                GLES20.glTexParameterf(3553, 10243, 33071.0f);
            }
            if (this.r[i].c == null || !this.r[i].c.equals(bitmap)) {
                GLES20.glBindTexture(3553, this.r[i].d[0]);
                GLUtils.texImage2D(3553, 0, bitmap, 0);
            }
            this.r[i].c = bitmap;
        }
    }

    protected void a(int i, int i2, float f, float f2, float f3, int i3) {
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(z.length * 4);
        allocateDirect.order(ByteOrder.nativeOrder());
        this.r[i3].a = allocateDirect.asFloatBuffer();
        float[] fArr = new float[z.length];
        float f4 = (((((float) i2) / ((float) i)) * f3) * ((float) this.e)) / ((float) this.f);
        if (f < 0.0f) {
            f = 0.0f;
        } else if (f > 1.0f) {
            f = 1.0f;
        }
        if (f2 < 0.0f) {
            f2 = 0.0f;
        } else if (f2 > 1.0f) {
            f2 = 1.0f;
        }
        fArr[0] = (2.0f * f) - 1.0f;
        fArr[1] = 1.0f - (2.0f * f2);
        fArr[2] = fArr[0];
        fArr[3] = fArr[1] - (f4 * 2.0f);
        fArr[4] = fArr[0] + (2.0f * f3);
        fArr[5] = fArr[3];
        fArr[6] = fArr[4];
        fArr[7] = fArr[1];
        for (int i4 = 1; i4 <= 7; i4 += 2) {
            fArr[i4] = fArr[i4] * -1.0f;
        }
        this.r[i3].a.put(fArr);
        this.r[i3].a.position(0);
        ByteBuffer allocateDirect2 = ByteBuffer.allocateDirect(A.length * 4);
        allocateDirect2.order(ByteOrder.nativeOrder());
        this.r[i3].b = allocateDirect2.asFloatBuffer();
        this.r[i3].b.put(A);
        this.r[i3].b.position(0);
    }

    public void a(Bitmap bitmap, float f, float f2, float f3) {
        if (this.r == null) {
            this.r = new a[1];
        }
        if (this.r[0] == null) {
            this.r[0] = new a();
        }
        a(bitmap, f, f2, f3, 0);
        this.x = this.r[0];
    }

    public void a(List<com.tencent.liteav.beauty.d.d> list) {
        if (this.s == null || !a(this.s, list)) {
            this.s = list;
            if (this.r != null && this.r.length > 1) {
                for (int i = this.y; i < this.r.length; i++) {
                    if (this.r[i].d != null) {
                        GLES20.glDeleteTextures(1, this.r[i].d, 0);
                    }
                }
            }
            this.r = new a[(list.size() + this.y)];
            this.r[0] = this.x;
            for (int i2 = 0; i2 < list.size(); i2++) {
                com.tencent.liteav.beauty.d.d dVar = (com.tencent.liteav.beauty.d.d) list.get(i2);
                if (dVar != null) {
                    this.r[this.y + i2] = new a();
                    a(dVar.a, dVar.b, dVar.c, dVar.d, i2 + this.y);
                }
            }
            return;
        }
        Log.i(this.B, "Same markList");
    }

    private boolean a(List<com.tencent.liteav.beauty.d.d> list, List<com.tencent.liteav.beauty.d.d> list2) {
        if (list.size() != list2.size()) {
            return false;
        }
        for (int i = 0; i < list.size(); i++) {
            com.tencent.liteav.beauty.d.d dVar = (com.tencent.liteav.beauty.d.d) list.get(i);
            com.tencent.liteav.beauty.d.d dVar2 = (com.tencent.liteav.beauty.d.d) list2.get(i);
            if (!dVar.a.equals(dVar2.a) || dVar.b != dVar2.b || dVar.c != dVar2.c || dVar.d != dVar2.d) {
                return false;
            }
        }
        return true;
    }

    private void r() {
        if (this.r != null) {
            for (int i = 0; i < this.r.length; i++) {
                if (this.r[i] != null) {
                    if (this.r[i].d != null) {
                        GLES20.glDeleteTextures(1, this.r[i].d, 0);
                    }
                    this.r[i].d = null;
                    this.r[i].c = null;
                    this.r[i] = null;
                }
            }
        }
        this.r = null;
    }

    protected void j() {
        super.j();
        if (this.t) {
            GLES20.glEnable(3042);
            GLES20.glBlendFunc(this.u, 771);
            GLES20.glActiveTexture(33984);
            for (int i = 0; i < this.r.length; i++) {
                if (this.r[i] != null) {
                    GLES20.glBindTexture(3553, this.r[i].d[0]);
                    GLES20.glUniform1i(this.c, 0);
                    GLES20.glVertexAttribPointer(this.b, 2, 5126, false, 8, this.r[i].a);
                    GLES20.glEnableVertexAttribArray(this.b);
                    GLES20.glVertexAttribPointer(this.d, 4, 5126, false, 16, this.r[i].b);
                    GLES20.glEnableVertexAttribArray(this.d);
                    GLES20.glDrawElements(4, v.length, 5123, this.w);
                    GLES20.glDisableVertexAttribArray(this.b);
                    GLES20.glDisableVertexAttribArray(this.d);
                }
            }
            GLES20.glDisable(3042);
        }
    }
}
