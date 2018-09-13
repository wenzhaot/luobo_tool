package com.tencent.liteav.beauty.b;

import android.opengl.GLES20;
import com.tencent.liteav.basic.c.d;
import com.tencent.liteav.basic.c.g;
import com.tencent.liteav.basic.c.h;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* compiled from: TXCGPUFilterGroup */
public class j extends d {
    protected List<d> r;
    protected List<d> s;
    private int[] t;
    private int[] u;
    private d v;
    private final FloatBuffer w;
    private final FloatBuffer x;
    private final FloatBuffer y;

    public j() {
        this(null);
        this.o = true;
    }

    public j(List<d> list) {
        this.s = new ArrayList();
        this.v = new d();
        this.o = true;
        this.r = list;
        if (this.r == null) {
            this.r = new ArrayList();
        } else {
            s();
        }
        this.w = ByteBuffer.allocateDirect(w.a.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.w.put(w.a).position(0);
        this.x = ByteBuffer.allocateDirect(h.a.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.x.put(h.a).position(0);
        float[] a = h.a(g.NORMAL, false, true);
        this.y = ByteBuffer.allocateDirect(a.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.y.put(a).position(0);
    }

    public void a(d dVar) {
        if (dVar != null) {
            this.r.add(dVar);
            s();
        }
    }

    public boolean a() {
        boolean a = super.a();
        if (a) {
            for (d dVar : this.r) {
                dVar.c();
                if (!dVar.n()) {
                    break;
                }
            }
            a = this.v.c();
        }
        if (a && GLES20.glGetError() == 0) {
            return true;
        }
        return false;
    }

    public void b() {
        super.b();
        for (d e : this.r) {
            e.e();
        }
    }

    public void f() {
        super.f();
        if (this.u != null) {
            GLES20.glDeleteTextures(2, this.u, 0);
            this.u = null;
        }
        if (this.t != null) {
            GLES20.glDeleteFramebuffers(2, this.t, 0);
            this.t = null;
        }
    }

    public void a(int i, int i2) {
        if (this.e != i || this.f != i2) {
            if (this.t != null) {
                f();
            }
            super.a(i, i2);
            int size = this.s.size();
            for (int i3 = 0; i3 < size; i3++) {
                ((d) this.s.get(i3)).a(i, i2);
            }
            this.v.a(i, i2);
            if (this.s != null && this.s.size() > 0) {
                this.s.size();
                this.t = new int[2];
                this.u = new int[2];
                for (int i4 = 0; i4 < 2; i4++) {
                    GLES20.glGenFramebuffers(1, this.t, i4);
                    GLES20.glGenTextures(1, this.u, i4);
                    GLES20.glBindTexture(3553, this.u[i4]);
                    GLES20.glTexImage2D(3553, 0, 6408, i, i2, 0, 6408, 5121, null);
                    GLES20.glTexParameterf(3553, 10240, 9729.0f);
                    GLES20.glTexParameterf(3553, 10241, 9729.0f);
                    GLES20.glTexParameterf(3553, 10242, 33071.0f);
                    GLES20.glTexParameterf(3553, 10243, 33071.0f);
                    GLES20.glBindFramebuffer(36160, this.t[i4]);
                    GLES20.glFramebufferTexture2D(36160, 36064, 3553, this.u[i4], 0);
                    GLES20.glBindTexture(3553, 0);
                    GLES20.glBindFramebuffer(36160, 0);
                }
            }
        }
    }

    public int a(int i, int i2, int i3) {
        int size = this.s.size();
        k();
        int i4 = 0;
        int i5 = 0;
        while (i4 < size) {
            int a;
            int i6;
            d dVar = (d) this.s.get(i4);
            if (i5 != 0) {
                a = dVar.a(i, i2, i3);
            } else {
                a = dVar.a(i, this.t[0], this.u[0]);
            }
            if (i5 != 0) {
                i6 = 0;
            } else {
                i6 = 1;
            }
            i4++;
            i5 = i6;
            i = a;
        }
        if (i5 != 0) {
            this.v.a(i, i2, i3);
        }
        return i3;
    }

    public List<d> r() {
        return this.s;
    }

    public void s() {
        if (this.r != null) {
            this.s.clear();
            for (d dVar : this.r) {
                if (dVar instanceof j) {
                    ((j) dVar).s();
                    Collection r = ((j) dVar).r();
                    if (!(r == null || r.isEmpty())) {
                        this.s.addAll(r);
                    }
                } else {
                    this.s.add(dVar);
                }
            }
        }
    }
}
