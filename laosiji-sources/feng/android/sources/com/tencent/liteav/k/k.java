package com.tencent.liteav.k;

import android.opengl.GLES20;
import com.tencent.liteav.basic.c.d;
import com.tencent.liteav.k.n.m;
import java.nio.FloatBuffer;

/* compiled from: TXCGPUSplitScreenFilter */
public class k extends d {
    private int r = 0;
    private int[] s = new int[]{1, 4, 9};
    private a[] t = null;

    /* compiled from: TXCGPUSplitScreenFilter */
    private static class a {
        int a;
        public int b;
        public int c;
        public int d;

        private a() {
            this.a = 0;
            this.b = 0;
            this.c = 0;
            this.d = 0;
        }
    }

    public void a(m mVar) {
        int i = 0;
        if (mVar.a == this.r) {
            return;
        }
        if (mVar.a == this.s[0] || mVar.a == this.s[1] || mVar.a == this.s[2]) {
            this.r = mVar.a;
            this.t = new a[this.r];
            for (int i2 = 0; i2 < this.r; i2++) {
                this.t[i2] = new a();
            }
            if (mVar.a == this.s[0]) {
                this.t[0].a = 0;
                this.t[0].b = 0;
                this.t[0].c = this.e;
                this.t[0].d = this.f;
            } else if (mVar.a == this.s[1]) {
                while (i < this.s[1]) {
                    this.t[i].a = ((i % 2) * this.e) / 2;
                    this.t[i].b = ((i / 2) * this.f) / 2;
                    this.t[i].c = this.e / 2;
                    this.t[i].d = this.f / 2;
                    i++;
                }
            } else if (mVar.a == this.s[2]) {
                while (i < this.s[2]) {
                    this.t[i].a = ((i % 3) * this.e) / 3;
                    this.t[i].b = ((i / 3) * this.f) / 3;
                    this.t[i].c = this.e / 3;
                    this.t[i].d = this.f / 3;
                    i++;
                }
            }
        }
    }

    public void a(int i, FloatBuffer floatBuffer, FloatBuffer floatBuffer2) {
        if (this.t != null) {
            for (int i2 = 0; i2 < this.t.length; i2++) {
                if (this.t[i2] != null) {
                    GLES20.glViewport(this.t[i2].a, this.t[i2].b, this.t[i2].c, this.t[i2].d);
                }
                super.a(i, floatBuffer, floatBuffer2);
            }
            return;
        }
        super.a(i, floatBuffer, floatBuffer2);
    }
}
