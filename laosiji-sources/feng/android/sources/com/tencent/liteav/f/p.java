package com.tencent.liteav.f;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.graphics.SurfaceTexture.OnFrameAvailableListener;
import android.opengl.GLUtils;
import android.util.Log;
import android.view.Surface;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.c.e;
import com.tencent.liteav.f.a.d;
import com.tencent.liteav.f.a.g;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;

@TargetApi(17)
/* compiled from: VideoSourceProcessor */
public class p {
    float[] a = new float[16];
    private o b;
    private int c = 2;
    private volatile Surface d;
    private SurfaceTexture e;
    private Surface f;
    private volatile e g;
    private Object h = new Object();
    private d i;
    private g j;
    private int k = 0;
    private int l = 0;
    private int m = 0;
    private int n = 0;
    private int o = 0;
    private com.tencent.liteav.renderer.d p;
    private g q;
    private l r;
    private b s;
    private final BlockingQueue<a> t = new LinkedBlockingDeque();
    private boolean u;
    private volatile boolean v = true;
    private volatile boolean w;
    private volatile Object x;
    private ArrayList<Long> y;
    private boolean z;

    /* compiled from: VideoSourceProcessor */
    private interface a {
        e a();
    }

    /* compiled from: VideoSourceProcessor */
    private static class b extends Thread {
        private WeakReference<p> a;
        private EGL10 b;
        private EGLContext c;
        private EGLDisplay d;
        private EGLSurface e;
        private EGLConfig f;
        private WeakReference<Surface> g;
        private volatile boolean h = false;

        b(p pVar) {
            this.a = new WeakReference(pVar);
        }

        public void run() {
            setName("TXVideoRenderThread for ThumbnailProcessor" + getId());
            TXCLog.d("TXVideoRenderThread", "TXVideoRenderThread init");
            try {
                this.h = true;
                f();
                b();
                c();
                while (this.h) {
                    a e = e();
                    if (e != null) {
                        e a = e.a();
                        if (a != null) {
                            if (!(this.b == null || this.d == null || this.e == null)) {
                                this.b.eglSwapBuffers(this.d, this.e);
                            }
                            a(a);
                        }
                    }
                }
                d();
                g();
            } catch (Exception e2) {
                e2.printStackTrace();
            } finally {
                TXCLog.d("TXVideoRenderThread", "TXVideoRenderThread cancel");
            }
        }

        private void a() {
            this.h = false;
        }

        private void b() {
            try {
                p pVar = (p) this.a.get();
                if (pVar != null) {
                    pVar.g();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void c() {
            try {
                p pVar = (p) this.a.get();
                if (pVar != null) {
                    pVar.d();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void d() {
            try {
                p pVar = (p) this.a.get();
                if (pVar != null) {
                    pVar.e();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private a e() {
            if (!(this.a == null || this.a.get() == null)) {
                try {
                    return (a) ((p) this.a.get()).t.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        private void a(e eVar) {
            if (this.a != null) {
                ((p) this.a.get()).c(eVar);
            }
        }

        private void f() {
            p pVar = (p) this.a.get();
            if (pVar != null) {
                this.b = (EGL10) EGLContext.getEGL();
                this.d = this.b.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
                this.b.eglInitialize(this.d, new int[2]);
                this.f = i();
                this.c = a(this.b, this.d, this.f, EGL10.EGL_NO_CONTEXT);
                Surface j = pVar.f();
                if (j == null || !j.isValid()) {
                    TXCLog.w("TXVideoRenderThread", "no output Surface found! surface:" + j);
                    return;
                }
                this.g = new WeakReference(j);
                this.e = this.b.eglCreateWindowSurface(this.d, this.f, j, null);
                TXCLog.w("TXVideoRenderThread", "vrender: init egl @context=" + this.c + ",surface=" + this.e);
                try {
                    if (this.e == null || this.e == EGL10.EGL_NO_SURFACE) {
                        throw new RuntimeException("GL error:" + GLUtils.getEGLErrorString(this.b.eglGetError()));
                    } else if (!this.b.eglMakeCurrent(this.d, this.e, this.e, this.c)) {
                        throw new RuntimeException("GL Make current Error" + GLUtils.getEGLErrorString(this.b.eglGetError()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        private void g() {
            this.b.eglMakeCurrent(this.d, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
            this.b.eglDestroyContext(this.d, this.c);
            if (this.e != null) {
                this.b.eglDestroySurface(this.d, this.e);
            }
            this.b.eglTerminate(this.d);
            this.g = null;
            TXCLog.w("TXVideoRenderThread", "vrender: uninit egl @context=" + this.c + ",surface=" + this.e);
        }

        private void a(Surface surface) {
            try {
                if (this.g == null || ((Surface) this.g.get()) != surface) {
                    h();
                    this.g = new WeakReference(surface);
                    this.e = this.b.eglCreateWindowSurface(this.d, this.f, surface, null);
                    if (this.e == null || this.e == EGL10.EGL_NO_SURFACE) {
                        throw new RuntimeException("GL error:" + GLUtils.getEGLErrorString(this.b.eglGetError()));
                    } else if (this.b.eglMakeCurrent(this.d, this.e, this.e, this.c)) {
                        TXCLog.w("TXVideoRenderThread", "vrender: init surface sucess @context=" + this.c + ",surface=" + this.e);
                        return;
                    } else {
                        throw new RuntimeException("GL Make current Error" + GLUtils.getEGLErrorString(this.b.eglGetError()));
                    }
                }
                TXCLog.w("TXVideoRenderThread", "vrender: ignore initSurface @" + surface);
            } catch (Exception e) {
                TXCLog.w("TXVideoRenderThread", "vrender: init surface fail @context=" + this.c + ",surface=" + this.e);
                e.printStackTrace();
            }
        }

        private void h() {
            try {
                this.b.eglMakeCurrent(this.d, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
                if (!(this.e == null || this.e == EGL10.EGL_NO_SURFACE)) {
                    this.b.eglDestroySurface(this.d, this.e);
                    this.e = EGL10.EGL_NO_SURFACE;
                }
                TXCLog.w("TXVideoRenderThread", "vrender: destroy surface sucess @context=" + this.c + ",surface=" + this.e);
            } catch (Exception e) {
                TXCLog.w("TXVideoRenderThread", "vrender: destroy surface fail @context=" + this.c + ",surface=" + this.e);
                e.printStackTrace();
            }
        }

        private EGLContext a(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig, EGLContext eGLContext) {
            return egl10.eglCreateContext(eGLDisplay, eGLConfig, eGLContext, new int[]{12440, 2, 12344});
        }

        private EGLConfig i() {
            int[] iArr = new int[1];
            EGLConfig[] eGLConfigArr = new EGLConfig[1];
            if (!this.b.eglChooseConfig(this.d, j(), eGLConfigArr, 1, iArr)) {
                throw new IllegalArgumentException("Failed to choose config:" + GLUtils.getEGLErrorString(this.b.eglGetError()));
            } else if (iArr[0] > 0) {
                return eGLConfigArr[0];
            } else {
                return null;
            }
        }

        private int[] j() {
            return new int[]{12352, 4, 12324, 8, 12323, 8, 12322, 8, 12321, 8, 12325, 0, 12326, 0, 12344};
        }
    }

    public void a(g gVar) {
        this.j = gVar;
    }

    private void c() {
        if (this.j != null) {
            this.j.e();
        }
    }

    public void a(boolean z) {
        this.v = z;
    }

    public void b(boolean z) {
        this.z = z;
    }

    public p(Context context) {
        if (this.r == null) {
            this.r = new l(Boolean.valueOf(false));
        }
        this.q = new g(context);
        this.b = o.a();
        this.y = new ArrayList();
    }

    public void a(d dVar) {
        this.i = dVar;
    }

    public void a() {
        this.w = true;
        this.x = new Object();
        TXCLog.d("VideoSourceProcessor", "start");
        if (this.s == null || !this.s.isAlive()) {
            this.s = new b(this);
            this.s.start();
            synchronized (this.h) {
                if (this.p == null || this.p.a() == -12345) {
                    try {
                        this.h.wait(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        TXCLog.e("VideoSourceProcessor", "Object wait exception:" + e);
                    }
                }
            }
            return;
        }
        TXCLog.e("VideoSourceProcessor", "pre render thread must be stoped first before create another!");
        return;
    }

    public void a(int i, int i2) {
        this.k = i;
        this.l = i2;
        this.r.a(i, i2);
        TXCLog.d("VideoSourceProcessor", "setRenderResolution: " + i + "*" + i2);
    }

    public void a(int i) {
        TXCLog.d("VideoSourceProcessor", "setRenderMode: " + i);
        this.r.a(i);
    }

    public Surface c(boolean z) {
        if (this.p == null) {
            TXCLog.e("VideoSourceProcessor", "getSurface() must be called after start() !");
            return null;
        }
        TXCLog.d("VideoSourceProcessor", "getSurface: textureId = " + this.p.a() + ", createNew = " + z);
        if (z || this.e == null) {
            if (this.e != null) {
                this.e.setOnFrameAvailableListener(null);
                this.e.release();
            }
            if (this.f != null) {
                this.f.release();
            }
            this.e = new SurfaceTexture(this.p.a());
            this.f = new Surface(this.e);
            this.e.setOnFrameAvailableListener(new OnFrameAvailableListener() {
                public void onFrameAvailable(SurfaceTexture surfaceTexture) {
                    if (p.this.w) {
                        if (p.this.x != null) {
                            synchronized (p.this.x) {
                                p.this.x.notify();
                            }
                        }
                        p.this.w = false;
                    }
                }
            });
        }
        return this.f;
    }

    public void b() {
        Log.d("VideoSourceProcessor", "stop:" + toString());
        if (this.s != null) {
            if (this.s.isAlive()) {
                this.s.a();
                a(new a() {
                    public e a() {
                        return null;
                    }
                });
                try {
                    this.s.join(1000);
                } catch (InterruptedException e) {
                    TXCLog.e("VideoSourceProcessor", "render thread join exception:" + e);
                    e.printStackTrace();
                }
            }
            this.s = null;
        }
        this.u = false;
        this.t.clear();
        this.e = null;
        this.f = null;
        this.p = null;
    }

    public void a(Surface surface) {
        if (surface != null) {
            if (this.d == surface) {
                TXCLog.w("VideoSourceProcessor", "output SurfaceTexture is the same");
                return;
            }
            this.d = surface;
            a(new a() {
                public e a() {
                    if (p.this.s != null) {
                        p.this.s.a(p.this.d);
                    }
                    return null;
                }
            });
        }
    }

    public void a(final e eVar) {
        if (this.w && this.x != null) {
            synchronized (this.x) {
                try {
                    this.x.wait(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if (eVar != null) {
            if (!this.u || eVar == this.g) {
                this.g = eVar;
                a(new a() {
                    public e a() {
                        if ((eVar.f() & 4) == 0) {
                            return p.this.b(eVar);
                        }
                        if (!(eVar == null || p.this.i == null)) {
                            TXCLog.i("VideoSourceProcessor", "process last VideoFrame!!!");
                            p.this.i.c(eVar);
                        }
                        return null;
                    }
                });
            }
        }
    }

    private void d() {
        this.r.a();
    }

    private void e() {
        if (this.r != null) {
            this.r.b();
            this.r = null;
        }
        if (this.q != null) {
            this.q.a();
            this.q = null;
        }
    }

    private void c(e eVar) {
        if (eVar != null && this.i != null) {
            this.i.c(eVar);
        }
    }

    private Surface f() {
        return this.d;
    }

    private void g() {
        synchronized (this.h) {
            this.p = new com.tencent.liteav.renderer.d(true);
            this.p.b();
            this.h.notify();
        }
    }

    private boolean a(a aVar) {
        if (this.s == null || !this.s.isAlive()) {
            TXCLog.w("VideoSourceProcessor", "render thread is not alive");
            return false;
        }
        this.t.add(aVar);
        return true;
    }

    protected e b(e eVar) {
        if (eVar != null && (!(this.m == eVar.m() && this.n == eVar.n() && this.o == eVar.h()) && eVar.m() > 0 && eVar.n() > 0)) {
            TXCLog.d("VideoSourceProcessor", "scale. old size = " + this.m + "*" + this.n + ", rotation = " + this.o + ", new size = " + eVar.m() + "*" + eVar.n() + ", rotation = " + eVar.h());
            this.m = eVar.m();
            this.n = eVar.n();
            this.o = eVar.h();
            if (this.o == 90 || this.o == 270) {
                this.m = eVar.n();
                this.n = eVar.m();
            }
            this.r.b(this.m, this.n);
        }
        if (this.p == null || this.e == null) {
            return null;
        }
        int a = this.p.a();
        this.e.updateTexImage();
        if (!this.v) {
            c();
        } else if (this.g.e() != eVar.e() && this.t.size() > 0) {
            TXCLog.i("VideoSourceProcessor", "onDrawFrame: drop frame!!!! task size = " + this.t.size());
            eVar = this.g;
            this.t.clear();
        }
        this.e.getTransformMatrix(this.a);
        if (!(this.a == null || eVar == null)) {
            this.q.a(eVar, this.u);
            if (this.u) {
                this.u = false;
            }
            a = a(this.o, this.a, a, eVar);
        }
        this.r.b(a);
        return eVar;
    }

    public int a(int i, float[] fArr, int i2, e eVar) {
        int c = this.b.c();
        int d = this.b.d();
        if (c > 0 || d > 0) {
            this.q.a(c, d);
        }
        this.q.a(i);
        this.q.a(fArr);
        return this.q.a(i2, eVar.m(), eVar.n(), i, 4, 0);
    }
}
