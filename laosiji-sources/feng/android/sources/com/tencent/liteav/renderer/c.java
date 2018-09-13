package com.tencent.liteav.renderer;

import android.content.Context;
import android.opengl.GLDebugHelper;
import android.opengl.GLSurfaceView.Renderer;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import com.tencent.liteav.basic.log.TXCLog;
import java.io.Writer;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

/* compiled from: TXCGLSurfaceViewBase */
public class c extends SurfaceView implements Callback {
    private static final j a = new j();
    private i b;
    private Renderer c;
    protected boolean d = false;
    protected boolean e = false;
    protected final WeakReference<c> f = new WeakReference(this);
    protected boolean g;
    protected boolean h;
    private boolean i;
    private e j;
    private f k;
    private g l;
    private k m;
    private int n;
    private int o;
    private boolean p;

    /* compiled from: TXCGLSurfaceViewBase */
    public interface e {
        EGLConfig a(EGL10 egl10, EGLDisplay eGLDisplay);
    }

    /* compiled from: TXCGLSurfaceViewBase */
    private abstract class a implements e {
        protected int[] a;

        abstract EGLConfig a(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig[] eGLConfigArr);

        public a(int[] iArr) {
            this.a = a(iArr);
        }

        public EGLConfig a(EGL10 egl10, EGLDisplay eGLDisplay) {
            int[] iArr = new int[1];
            if (egl10.eglChooseConfig(eGLDisplay, this.a, null, 0, iArr)) {
                int i = iArr[0];
                if (i <= 0) {
                    throw new IllegalArgumentException("No configs match configSpec");
                }
                EGLConfig[] eGLConfigArr = new EGLConfig[i];
                if (egl10.eglChooseConfig(eGLDisplay, this.a, eGLConfigArr, i, iArr)) {
                    EGLConfig a = a(egl10, eGLDisplay, eGLConfigArr);
                    if (a != null) {
                        return a;
                    }
                    throw new IllegalArgumentException("No config chosen");
                }
                throw new IllegalArgumentException("eglChooseConfig#2 failed");
            }
            throw new IllegalArgumentException("eglChooseConfig failed");
        }

        private int[] a(int[] iArr) {
            if (c.this.o != 2) {
                return iArr;
            }
            int length = iArr.length;
            Object obj = new int[(length + 2)];
            System.arraycopy(iArr, 0, obj, 0, length - 1);
            obj[length - 1] = 12352;
            obj[length] = 4;
            obj[length + 1] = 12344;
            return obj;
        }
    }

    /* compiled from: TXCGLSurfaceViewBase */
    private class b extends a {
        protected int c;
        protected int d;
        protected int e;
        protected int f;
        protected int g;
        protected int h;
        private int[] j = new int[1];

        public b(int i, int i2, int i3, int i4, int i5, int i6) {
            super(new int[]{12324, i, 12323, i2, 12322, i3, 12321, i4, 12325, i5, 12326, i6, 12344});
            this.c = i;
            this.d = i2;
            this.e = i3;
            this.f = i4;
            this.g = i5;
            this.h = i6;
        }

        public EGLConfig a(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig[] eGLConfigArr) {
            for (EGLConfig eGLConfig : eGLConfigArr) {
                int a = a(egl10, eGLDisplay, eGLConfig, 12325, 0);
                int a2 = a(egl10, eGLDisplay, eGLConfig, 12326, 0);
                if (a >= this.g && a2 >= this.h) {
                    a = a(egl10, eGLDisplay, eGLConfig, 12324, 0);
                    int a3 = a(egl10, eGLDisplay, eGLConfig, 12323, 0);
                    int a4 = a(egl10, eGLDisplay, eGLConfig, 12322, 0);
                    a2 = a(egl10, eGLDisplay, eGLConfig, 12321, 0);
                    if (a == this.c && a3 == this.d && a4 == this.e && a2 == this.f) {
                        return eGLConfig;
                    }
                }
            }
            return null;
        }

        private int a(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig, int i, int i2) {
            if (egl10.eglGetConfigAttrib(eGLDisplay, eGLConfig, i, this.j)) {
                return this.j[0];
            }
            return i2;
        }
    }

    /* compiled from: TXCGLSurfaceViewBase */
    public interface f {
        EGLContext a(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig);

        void a(EGL10 egl10, EGLDisplay eGLDisplay, EGLContext eGLContext);
    }

    /* compiled from: TXCGLSurfaceViewBase */
    private class c implements f {
        private int b;

        private c() {
            this.b = 12440;
        }

        /* synthetic */ c(c cVar, AnonymousClass1 anonymousClass1) {
            this();
        }

        public EGLContext a(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig) {
            int[] iArr = new int[]{this.b, c.this.o, 12344};
            EGLContext eGLContext = EGL10.EGL_NO_CONTEXT;
            if (c.this.o == 0) {
                iArr = null;
            }
            return egl10.eglCreateContext(eGLDisplay, eGLConfig, eGLContext, iArr);
        }

        public void a(EGL10 egl10, EGLDisplay eGLDisplay, EGLContext eGLContext) {
            if (!egl10.eglDestroyContext(eGLDisplay, eGLContext)) {
                TXCLog.e("DefaultContextFactory", "display:" + eGLDisplay + " context: " + eGLContext);
                h.a("eglDestroyContex", egl10.eglGetError());
            }
        }
    }

    /* compiled from: TXCGLSurfaceViewBase */
    public interface g {
        EGLSurface a(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig, Object obj);

        void a(EGL10 egl10, EGLDisplay eGLDisplay, EGLSurface eGLSurface);
    }

    /* compiled from: TXCGLSurfaceViewBase */
    private static class d implements g {
        private d() {
        }

        /* synthetic */ d(AnonymousClass1 anonymousClass1) {
            this();
        }

        public EGLSurface a(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig, Object obj) {
            EGLSurface eGLSurface = null;
            try {
                return egl10.eglCreateWindowSurface(eGLDisplay, eGLConfig, obj, null);
            } catch (IllegalArgumentException e) {
                TXCLog.e("TXCGLSurfaceViewBase", "eglCreateWindowSurface");
                TXCLog.e("TXCGLSurfaceViewBase", e.toString());
                return eGLSurface;
            }
        }

        public void a(EGL10 egl10, EGLDisplay eGLDisplay, EGLSurface eGLSurface) {
            egl10.eglDestroySurface(eGLDisplay, eGLSurface);
        }
    }

    /* compiled from: TXCGLSurfaceViewBase */
    public static class h {
        EGL10 a;
        EGLDisplay b;
        EGLSurface c;
        EGLConfig d;
        EGLContext e;
        private WeakReference<c> f;

        public h(WeakReference<c> weakReference) {
            this.f = weakReference;
        }

        public void a() {
            this.a = (EGL10) EGLContext.getEGL();
            this.b = this.a.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
            if (this.b == EGL10.EGL_NO_DISPLAY) {
                throw new RuntimeException("eglGetDisplay failed");
            }
            if (this.a.eglInitialize(this.b, new int[2])) {
                c cVar = (c) this.f.get();
                if (cVar == null) {
                    this.d = null;
                    this.e = null;
                } else {
                    this.d = cVar.j.a(this.a, this.b);
                    this.e = cVar.k.a(this.a, this.b, this.d);
                }
                if (this.e == null || this.e == EGL10.EGL_NO_CONTEXT) {
                    this.e = null;
                    a("createContext");
                }
                if (cVar != null) {
                    cVar.h = true;
                }
                this.c = null;
                return;
            }
            throw new RuntimeException("eglInitialize failed");
        }

        public boolean b() {
            if (this.a == null) {
                throw new RuntimeException("egl not initialized");
            } else if (this.b == null) {
                throw new RuntimeException("eglDisplay not initialized");
            } else if (this.d == null) {
                throw new RuntimeException("mEglConfig not initialized");
            } else {
                h();
                c cVar = (c) this.f.get();
                if (cVar != null) {
                    this.c = cVar.l.a(this.a, this.b, this.d, cVar.getHolder());
                } else {
                    this.c = null;
                }
                if (this.c == null || this.c == EGL10.EGL_NO_SURFACE) {
                    if (this.a.eglGetError() == 12299) {
                        TXCLog.e("EglHelper", "createWindowSurface returned EGL_BAD_NATIVE_WINDOW.");
                    }
                    return false;
                } else if (this.a.eglMakeCurrent(this.b, this.c, this.c, this.e)) {
                    if (cVar != null) {
                        cVar.g = true;
                    }
                    return true;
                } else {
                    a("EGLHelper", "eglMakeCurrent", this.a.eglGetError());
                    return false;
                }
            }
        }

        public int c() {
            return e();
        }

        GL d() {
            GL gl = this.e.getGL();
            c cVar = (c) this.f.get();
            if (cVar == null) {
                return gl;
            }
            if (cVar.m != null) {
                gl = cVar.m.a(gl);
            }
            if ((cVar.n & 3) == 0) {
                return gl;
            }
            Writer lVar;
            int i = 0;
            if ((cVar.n & 1) != 0) {
                i = 1;
            }
            if ((cVar.n & 2) != 0) {
                lVar = new l();
            } else {
                lVar = null;
            }
            return GLDebugHelper.wrap(gl, i, lVar);
        }

        public int e() {
            if (this.a.eglSwapBuffers(this.b, this.c)) {
                return 12288;
            }
            return this.a.eglGetError();
        }

        public void f() {
            h();
        }

        private void h() {
            if (this.c != null && this.c != EGL10.EGL_NO_SURFACE) {
                this.a.eglMakeCurrent(this.b, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
                c cVar = (c) this.f.get();
                if (cVar != null) {
                    cVar.l.a(this.a, this.b, this.c);
                    cVar.g = false;
                }
                this.c = null;
            }
        }

        public void g() {
            if (this.e != null) {
                c cVar = (c) this.f.get();
                if (cVar != null) {
                    cVar.k.a(this.a, this.b, this.e);
                }
                this.e = null;
            }
            if (this.b != null) {
                this.a.eglTerminate(this.b);
                this.b = null;
            }
        }

        private void a(String str) {
            a(str, this.a.eglGetError());
        }

        public static void a(String str, int i) {
            throw new RuntimeException(b(str, i));
        }

        public static void a(String str, String str2, int i) {
            TXCLog.w(str, b(str2, i));
        }

        public static String b(String str, int i) {
            return str + " failed: " + i;
        }
    }

    /* compiled from: TXCGLSurfaceViewBase */
    static class i extends Thread {
        private boolean a;
        private boolean b;
        private boolean c;
        private boolean d;
        private boolean e;
        private boolean f;
        private boolean g;
        private boolean h;
        private boolean i;
        private boolean j;
        private boolean k;
        private int l = 0;
        private int m = 0;
        private int n = 1;
        private boolean o = true;
        private boolean p;
        private ArrayList<Runnable> q = new ArrayList();
        private boolean r = true;
        private h s;
        private WeakReference<c> t;

        i(WeakReference<c> weakReference) {
            this.t = weakReference;
        }

        public void run() {
            setName("GLThread " + getId());
            try {
                i();
            } catch (InterruptedException e) {
            } finally {
                c.a.a(this);
            }
        }

        public int a() {
            return this.s.c();
        }

        public h b() {
            return this.s;
        }

        /* JADX WARNING: Missing block: B:22:0x006d, code:
            if (r4 == null) goto L_0x01f9;
     */
        /* JADX WARNING: Missing block: B:24:?, code:
            r4.run();
     */
        /* JADX WARNING: Missing block: B:25:0x0072, code:
            r3 = r5;
            r5 = r7;
            r7 = r8;
            r8 = r9;
            r9 = r10;
            r10 = r11;
            r11 = r1;
            r17 = r2;
            r2 = null;
            r4 = r6;
     */
        /* JADX WARNING: Missing block: B:112:0x01f9, code:
            if (r1 == null) goto L_0x02e1;
     */
        /* JADX WARNING: Missing block: B:115:0x0203, code:
            if (r18.s.b() == false) goto L_0x02ad;
     */
        /* JADX WARNING: Missing block: B:116:0x0205, code:
            r3 = com.tencent.liteav.renderer.c.e();
     */
        /* JADX WARNING: Missing block: B:117:0x0209, code:
            monitor-enter(r3);
     */
        /* JADX WARNING: Missing block: B:120:?, code:
            r18.j = true;
            com.tencent.liteav.renderer.c.e().notifyAll();
     */
        /* JADX WARNING: Missing block: B:121:0x0216, code:
            monitor-exit(r3);
     */
        /* JADX WARNING: Missing block: B:122:0x0217, code:
            r3 = null;
     */
        /* JADX WARNING: Missing block: B:123:0x0219, code:
            if (r11 == null) goto L_0x02de;
     */
        /* JADX WARNING: Missing block: B:125:?, code:
            r1 = (javax.microedition.khronos.opengles.GL10) r18.s.d();
            com.tencent.liteav.renderer.c.e().a(r1);
            r11 = null;
            r13 = r1;
     */
        /* JADX WARNING: Missing block: B:126:0x022e, code:
            if (r12 == null) goto L_0x024a;
     */
        /* JADX WARNING: Missing block: B:127:0x0230, code:
            r1 = (com.tencent.liteav.renderer.c) r18.t.get();
     */
        /* JADX WARNING: Missing block: B:128:0x023a, code:
            if (r1 == null) goto L_0x0249;
     */
        /* JADX WARNING: Missing block: B:129:0x023c, code:
            com.tencent.liteav.renderer.c.b(r1).onSurfaceCreated(r13, r18.s.d);
     */
        /* JADX WARNING: Missing block: B:130:0x0249, code:
            r12 = null;
     */
        /* JADX WARNING: Missing block: B:131:0x024a, code:
            if (r9 == null) goto L_0x0260;
     */
        /* JADX WARNING: Missing block: B:132:0x024c, code:
            r1 = (com.tencent.liteav.renderer.c) r18.t.get();
     */
        /* JADX WARNING: Missing block: B:133:0x0256, code:
            if (r1 == null) goto L_0x025f;
     */
        /* JADX WARNING: Missing block: B:134:0x0258, code:
            com.tencent.liteav.renderer.c.b(r1).onSurfaceChanged(r13, r6, r5);
     */
        /* JADX WARNING: Missing block: B:135:0x025f, code:
            r9 = null;
     */
        /* JADX WARNING: Missing block: B:136:0x0260, code:
            r1 = (com.tencent.liteav.renderer.c) r18.t.get();
     */
        /* JADX WARNING: Missing block: B:137:0x026c, code:
            if (r1 == null) goto L_0x02f7;
     */
        /* JADX WARNING: Missing block: B:138:0x026e, code:
            com.tencent.liteav.renderer.c.b(r1).onDrawFrame(r13);
            r1 = r1.c();
     */
        /* JADX WARNING: Missing block: B:139:0x0279, code:
            switch(r1) {
                case 12288: goto L_0x0297;
                case 12302: goto L_0x02d6;
                default: goto L_0x027c;
            };
     */
        /* JADX WARNING: Missing block: B:140:0x027c, code:
            com.tencent.liteav.renderer.c.h.a("GLThread", "eglSwapBuffers", r1);
            r14 = com.tencent.liteav.renderer.c.e();
     */
        /* JADX WARNING: Missing block: B:141:0x0289, code:
            monitor-enter(r14);
     */
        /* JADX WARNING: Missing block: B:144:?, code:
            r18.f = true;
            com.tencent.liteav.renderer.c.e().notifyAll();
     */
        /* JADX WARNING: Missing block: B:145:0x0296, code:
            monitor-exit(r14);
     */
        /* JADX WARNING: Missing block: B:146:0x0297, code:
            if (r8 == null) goto L_0x02f5;
     */
        /* JADX WARNING: Missing block: B:147:0x0299, code:
            r1 = 1;
     */
        /* JADX WARNING: Missing block: B:148:0x029a, code:
            r2 = r4;
            r14 = r13;
            r4 = r6;
            r6 = r1;
            r17 = r7;
            r7 = r8;
            r8 = r9;
            r9 = r10;
            r10 = r11;
            r11 = r3;
            r3 = r5;
     */
        /* JADX WARNING: Missing block: B:154:0x02ad, code:
            r3 = com.tencent.liteav.renderer.c.e();
     */
        /* JADX WARNING: Missing block: B:155:0x02b1, code:
            monitor-enter(r3);
     */
        /* JADX WARNING: Missing block: B:158:?, code:
            r18.j = true;
            r18.f = true;
            com.tencent.liteav.renderer.c.e().notifyAll();
     */
        /* JADX WARNING: Missing block: B:159:0x02c3, code:
            monitor-exit(r3);
     */
        /* JADX WARNING: Missing block: B:160:0x02c4, code:
            r3 = r5;
            r5 = r7;
            r7 = r8;
            r8 = r9;
            r9 = r10;
            r10 = r11;
            r11 = r1;
            r17 = r2;
            r2 = r4;
            r4 = r6;
     */
        /* JADX WARNING: Missing block: B:165:0x02d6, code:
            r10 = 1;
     */
        /* JADX WARNING: Missing block: B:175:0x02de, code:
            r13 = r14;
     */
        /* JADX WARNING: Missing block: B:176:0x02e1, code:
            r3 = r1;
     */
        /* JADX WARNING: Missing block: B:180:0x02f5, code:
            r1 = r2;
     */
        /* JADX WARNING: Missing block: B:181:0x02f7, code:
            r1 = 12288;
     */
        private void i() throws java.lang.InterruptedException {
            /*
            r18 = this;
            r1 = new com.tencent.liteav.renderer.c$h;
            r0 = r18;
            r2 = r0.t;
            r1.<init>(r2);
            r0 = r18;
            r0.s = r1;
            r1 = 0;
            r0 = r18;
            r0.h = r1;
            r1 = 0;
            r0 = r18;
            r0.i = r1;
            r3 = 0;
            r12 = 0;
            r1 = 0;
            r11 = 0;
            r10 = 0;
            r9 = 0;
            r8 = 0;
            r2 = 0;
            r7 = 0;
            r6 = 0;
            r5 = 0;
            r4 = 0;
            r14 = r3;
            r3 = r5;
            r5 = r7;
            r7 = r8;
            r8 = r9;
            r9 = r10;
            r10 = r11;
            r11 = r1;
            r17 = r2;
            r2 = r4;
            r4 = r6;
            r6 = r17;
        L_0x0031:
            r15 = com.tencent.liteav.renderer.c.a;	 Catch:{ all -> 0x01d5 }
            monitor-enter(r15);	 Catch:{ all -> 0x01d5 }
        L_0x0036:
            r0 = r18;
            r1 = r0.a;	 Catch:{ all -> 0x01d2 }
            if (r1 == 0) goto L_0x004d;
        L_0x003c:
            monitor-exit(r15);	 Catch:{ all -> 0x01d2 }
            r2 = com.tencent.liteav.renderer.c.a;
            monitor-enter(r2);
            r18.j();	 Catch:{ all -> 0x004a }
            r18.k();	 Catch:{ all -> 0x004a }
            monitor-exit(r2);	 Catch:{ all -> 0x004a }
            return;
        L_0x004a:
            r1 = move-exception;
            monitor-exit(r2);	 Catch:{ all -> 0x004a }
            throw r1;
        L_0x004d:
            r0 = r18;
            r1 = r0.q;	 Catch:{ all -> 0x01d2 }
            r1 = r1.isEmpty();	 Catch:{ all -> 0x01d2 }
            if (r1 != 0) goto L_0x0081;
        L_0x0057:
            r0 = r18;
            r1 = r0.q;	 Catch:{ all -> 0x01d2 }
            r2 = 0;
            r1 = r1.remove(r2);	 Catch:{ all -> 0x01d2 }
            r1 = (java.lang.Runnable) r1;	 Catch:{ all -> 0x01d2 }
            r2 = r6;
            r6 = r4;
            r4 = r1;
            r1 = r11;
            r11 = r10;
            r10 = r9;
            r9 = r8;
            r8 = r7;
            r7 = r5;
            r5 = r3;
        L_0x006c:
            monitor-exit(r15);	 Catch:{ all -> 0x01d2 }
            if (r4 == 0) goto L_0x01f9;
        L_0x006f:
            r4.run();	 Catch:{ all -> 0x01d5 }
            r4 = 0;
            r3 = r5;
            r5 = r7;
            r7 = r8;
            r8 = r9;
            r9 = r10;
            r10 = r11;
            r11 = r1;
            r17 = r2;
            r2 = r4;
            r4 = r6;
            r6 = r17;
            goto L_0x0031;
        L_0x0081:
            r1 = 0;
            r0 = r18;
            r13 = r0.d;	 Catch:{ all -> 0x01d2 }
            r0 = r18;
            r0 = r0.c;	 Catch:{ all -> 0x01d2 }
            r16 = r0;
            r0 = r16;
            if (r13 == r0) goto L_0x02f2;
        L_0x0090:
            r0 = r18;
            r1 = r0.c;	 Catch:{ all -> 0x01d2 }
            r0 = r18;
            r13 = r0.c;	 Catch:{ all -> 0x01d2 }
            r0 = r18;
            r0.d = r13;	 Catch:{ all -> 0x01d2 }
            r13 = com.tencent.liteav.renderer.c.a;	 Catch:{ all -> 0x01d2 }
            r13.notifyAll();	 Catch:{ all -> 0x01d2 }
            r13 = r1;
        L_0x00a4:
            r0 = r18;
            r1 = r0.k;	 Catch:{ all -> 0x01d2 }
            if (r1 == 0) goto L_0x00b6;
        L_0x00aa:
            r18.j();	 Catch:{ all -> 0x01d2 }
            r18.k();	 Catch:{ all -> 0x01d2 }
            r1 = 0;
            r0 = r18;
            r0.k = r1;	 Catch:{ all -> 0x01d2 }
            r5 = 1;
        L_0x00b6:
            if (r9 == 0) goto L_0x00bf;
        L_0x00b8:
            r18.j();	 Catch:{ all -> 0x01d2 }
            r18.k();	 Catch:{ all -> 0x01d2 }
            r9 = 0;
        L_0x00bf:
            if (r13 == 0) goto L_0x00ca;
        L_0x00c1:
            r0 = r18;
            r1 = r0.i;	 Catch:{ all -> 0x01d2 }
            if (r1 == 0) goto L_0x00ca;
        L_0x00c7:
            r18.j();	 Catch:{ all -> 0x01d2 }
        L_0x00ca:
            if (r13 == 0) goto L_0x00ee;
        L_0x00cc:
            r0 = r18;
            r1 = r0.h;	 Catch:{ all -> 0x01d2 }
            if (r1 == 0) goto L_0x00ee;
        L_0x00d2:
            r0 = r18;
            r1 = r0.t;	 Catch:{ all -> 0x01d2 }
            r1 = r1.get();	 Catch:{ all -> 0x01d2 }
            r1 = (com.tencent.liteav.renderer.c) r1;	 Catch:{ all -> 0x01d2 }
            if (r1 != 0) goto L_0x01ab;
        L_0x00de:
            r1 = 0;
        L_0x00df:
            if (r1 == 0) goto L_0x00eb;
        L_0x00e1:
            r1 = com.tencent.liteav.renderer.c.a;	 Catch:{ all -> 0x01d2 }
            r1 = r1.a();	 Catch:{ all -> 0x01d2 }
            if (r1 == 0) goto L_0x00ee;
        L_0x00eb:
            r18.k();	 Catch:{ all -> 0x01d2 }
        L_0x00ee:
            if (r13 == 0) goto L_0x0101;
        L_0x00f0:
            r1 = com.tencent.liteav.renderer.c.a;	 Catch:{ all -> 0x01d2 }
            r1 = r1.b();	 Catch:{ all -> 0x01d2 }
            if (r1 == 0) goto L_0x0101;
        L_0x00fa:
            r0 = r18;
            r1 = r0.s;	 Catch:{ all -> 0x01d2 }
            r1.g();	 Catch:{ all -> 0x01d2 }
        L_0x0101:
            r0 = r18;
            r1 = r0.e;	 Catch:{ all -> 0x01d2 }
            if (r1 != 0) goto L_0x0127;
        L_0x0107:
            r0 = r18;
            r1 = r0.g;	 Catch:{ all -> 0x01d2 }
            if (r1 != 0) goto L_0x0127;
        L_0x010d:
            r0 = r18;
            r1 = r0.i;	 Catch:{ all -> 0x01d2 }
            if (r1 == 0) goto L_0x0116;
        L_0x0113:
            r18.j();	 Catch:{ all -> 0x01d2 }
        L_0x0116:
            r1 = 1;
            r0 = r18;
            r0.g = r1;	 Catch:{ all -> 0x01d2 }
            r1 = 0;
            r0 = r18;
            r0.f = r1;	 Catch:{ all -> 0x01d2 }
            r1 = com.tencent.liteav.renderer.c.a;	 Catch:{ all -> 0x01d2 }
            r1.notifyAll();	 Catch:{ all -> 0x01d2 }
        L_0x0127:
            r0 = r18;
            r1 = r0.e;	 Catch:{ all -> 0x01d2 }
            if (r1 == 0) goto L_0x013f;
        L_0x012d:
            r0 = r18;
            r1 = r0.g;	 Catch:{ all -> 0x01d2 }
            if (r1 == 0) goto L_0x013f;
        L_0x0133:
            r1 = 0;
            r0 = r18;
            r0.g = r1;	 Catch:{ all -> 0x01d2 }
            r1 = com.tencent.liteav.renderer.c.a;	 Catch:{ all -> 0x01d2 }
            r1.notifyAll();	 Catch:{ all -> 0x01d2 }
        L_0x013f:
            if (r6 == 0) goto L_0x014f;
        L_0x0141:
            r7 = 0;
            r6 = 0;
            r1 = 1;
            r0 = r18;
            r0.p = r1;	 Catch:{ all -> 0x01d2 }
            r1 = com.tencent.liteav.renderer.c.a;	 Catch:{ all -> 0x01d2 }
            r1.notifyAll();	 Catch:{ all -> 0x01d2 }
        L_0x014f:
            r1 = r18.l();	 Catch:{ all -> 0x01d2 }
            if (r1 == 0) goto L_0x01f0;
        L_0x0155:
            r0 = r18;
            r1 = r0.h;	 Catch:{ all -> 0x01d2 }
            if (r1 != 0) goto L_0x015e;
        L_0x015b:
            if (r5 == 0) goto L_0x01b1;
        L_0x015d:
            r5 = 0;
        L_0x015e:
            r0 = r18;
            r1 = r0.h;	 Catch:{ all -> 0x01d2 }
            if (r1 == 0) goto L_0x02ee;
        L_0x0164:
            r0 = r18;
            r1 = r0.i;	 Catch:{ all -> 0x01d2 }
            if (r1 != 0) goto L_0x02ee;
        L_0x016a:
            r1 = 1;
            r0 = r18;
            r0.i = r1;	 Catch:{ all -> 0x01d2 }
            r11 = 1;
            r10 = 1;
            r8 = 1;
            r1 = r8;
            r8 = r10;
        L_0x0174:
            r0 = r18;
            r10 = r0.i;	 Catch:{ all -> 0x01d2 }
            if (r10 == 0) goto L_0x01ee;
        L_0x017a:
            r0 = r18;
            r10 = r0.r;	 Catch:{ all -> 0x01d2 }
            if (r10 == 0) goto L_0x02e4;
        L_0x0180:
            r7 = 1;
            r0 = r18;
            r3 = r0.l;	 Catch:{ all -> 0x01d2 }
            r0 = r18;
            r1 = r0.m;	 Catch:{ all -> 0x01d2 }
            r4 = 1;
            r10 = 1;
            r11 = 0;
            r0 = r18;
            r0.r = r11;	 Catch:{ all -> 0x01d2 }
        L_0x0190:
            r11 = 0;
            r0 = r18;
            r0.o = r11;	 Catch:{ all -> 0x01d2 }
            r11 = com.tencent.liteav.renderer.c.a;	 Catch:{ all -> 0x01d2 }
            r11.notifyAll();	 Catch:{ all -> 0x01d2 }
            r11 = r8;
            r8 = r4;
            r4 = r2;
            r2 = r6;
            r6 = r3;
            r17 = r1;
            r1 = r10;
            r10 = r9;
            r9 = r7;
            r7 = r5;
            r5 = r17;
            goto L_0x006c;
        L_0x01ab:
            r1 = r1.p;	 Catch:{ all -> 0x01d2 }
            goto L_0x00df;
        L_0x01b1:
            r1 = com.tencent.liteav.renderer.c.a;	 Catch:{ all -> 0x01d2 }
            r0 = r18;
            r1 = r1.b(r0);	 Catch:{ all -> 0x01d2 }
            if (r1 == 0) goto L_0x015e;
        L_0x01bd:
            r0 = r18;
            r1 = r0.s;	 Catch:{ RuntimeException -> 0x01e3 }
            r1.a();	 Catch:{ RuntimeException -> 0x01e3 }
            r1 = 1;
            r0 = r18;
            r0.h = r1;	 Catch:{ all -> 0x01d2 }
            r12 = 1;
            r1 = com.tencent.liteav.renderer.c.a;	 Catch:{ all -> 0x01d2 }
            r1.notifyAll();	 Catch:{ all -> 0x01d2 }
            goto L_0x015e;
        L_0x01d2:
            r1 = move-exception;
            monitor-exit(r15);	 Catch:{ all -> 0x01d2 }
            throw r1;	 Catch:{ all -> 0x01d5 }
        L_0x01d5:
            r1 = move-exception;
            r2 = com.tencent.liteav.renderer.c.a;
            monitor-enter(r2);
            r18.j();	 Catch:{ all -> 0x02db }
            r18.k();	 Catch:{ all -> 0x02db }
            monitor-exit(r2);	 Catch:{ all -> 0x02db }
            throw r1;
        L_0x01e3:
            r1 = move-exception;
            r2 = com.tencent.liteav.renderer.c.a;	 Catch:{ all -> 0x01d2 }
            r0 = r18;
            r2.c(r0);	 Catch:{ all -> 0x01d2 }
            throw r1;	 Catch:{ all -> 0x01d2 }
        L_0x01ee:
            r10 = r8;
            r8 = r1;
        L_0x01f0:
            r1 = com.tencent.liteav.renderer.c.a;	 Catch:{ all -> 0x01d2 }
            r1.wait();	 Catch:{ all -> 0x01d2 }
            goto L_0x0036;
        L_0x01f9:
            if (r1 == 0) goto L_0x02e1;
        L_0x01fb:
            r0 = r18;
            r3 = r0.s;	 Catch:{ all -> 0x01d5 }
            r3 = r3.b();	 Catch:{ all -> 0x01d5 }
            if (r3 == 0) goto L_0x02ad;
        L_0x0205:
            r3 = com.tencent.liteav.renderer.c.a;	 Catch:{ all -> 0x01d5 }
            monitor-enter(r3);	 Catch:{ all -> 0x01d5 }
            r1 = 1;
            r0 = r18;
            r0.j = r1;	 Catch:{ all -> 0x02aa }
            r1 = com.tencent.liteav.renderer.c.a;	 Catch:{ all -> 0x02aa }
            r1.notifyAll();	 Catch:{ all -> 0x02aa }
            monitor-exit(r3);	 Catch:{ all -> 0x02aa }
            r1 = 0;
            r3 = r1;
        L_0x0219:
            if (r11 == 0) goto L_0x02de;
        L_0x021b:
            r0 = r18;
            r1 = r0.s;	 Catch:{ all -> 0x01d5 }
            r1 = r1.d();	 Catch:{ all -> 0x01d5 }
            r1 = (javax.microedition.khronos.opengles.GL10) r1;	 Catch:{ all -> 0x01d5 }
            r11 = com.tencent.liteav.renderer.c.a;	 Catch:{ all -> 0x01d5 }
            r11.a(r1);	 Catch:{ all -> 0x01d5 }
            r11 = 0;
            r13 = r1;
        L_0x022e:
            if (r12 == 0) goto L_0x024a;
        L_0x0230:
            r0 = r18;
            r1 = r0.t;	 Catch:{ all -> 0x01d5 }
            r1 = r1.get();	 Catch:{ all -> 0x01d5 }
            r1 = (com.tencent.liteav.renderer.c) r1;	 Catch:{ all -> 0x01d5 }
            if (r1 == 0) goto L_0x0249;
        L_0x023c:
            r1 = r1.c;	 Catch:{ all -> 0x01d5 }
            r0 = r18;
            r12 = r0.s;	 Catch:{ all -> 0x01d5 }
            r12 = r12.d;	 Catch:{ all -> 0x01d5 }
            r1.onSurfaceCreated(r13, r12);	 Catch:{ all -> 0x01d5 }
        L_0x0249:
            r12 = 0;
        L_0x024a:
            if (r9 == 0) goto L_0x0260;
        L_0x024c:
            r0 = r18;
            r1 = r0.t;	 Catch:{ all -> 0x01d5 }
            r1 = r1.get();	 Catch:{ all -> 0x01d5 }
            r1 = (com.tencent.liteav.renderer.c) r1;	 Catch:{ all -> 0x01d5 }
            if (r1 == 0) goto L_0x025f;
        L_0x0258:
            r1 = r1.c;	 Catch:{ all -> 0x01d5 }
            r1.onSurfaceChanged(r13, r6, r5);	 Catch:{ all -> 0x01d5 }
        L_0x025f:
            r9 = 0;
        L_0x0260:
            r14 = 12288; // 0x3000 float:1.7219E-41 double:6.071E-320;
            r0 = r18;
            r1 = r0.t;	 Catch:{ all -> 0x01d5 }
            r1 = r1.get();	 Catch:{ all -> 0x01d5 }
            r1 = (com.tencent.liteav.renderer.c) r1;	 Catch:{ all -> 0x01d5 }
            if (r1 == 0) goto L_0x02f7;
        L_0x026e:
            r14 = r1.c;	 Catch:{ all -> 0x01d5 }
            r14.onDrawFrame(r13);	 Catch:{ all -> 0x01d5 }
            r1 = r1.c();	 Catch:{ all -> 0x01d5 }
        L_0x0279:
            switch(r1) {
                case 12288: goto L_0x0297;
                case 12302: goto L_0x02d6;
                default: goto L_0x027c;
            };	 Catch:{ all -> 0x01d5 }
        L_0x027c:
            r14 = "GLThread";
            r15 = "eglSwapBuffers";
            com.tencent.liteav.renderer.c.h.a(r14, r15, r1);	 Catch:{ all -> 0x01d5 }
            r14 = com.tencent.liteav.renderer.c.a;	 Catch:{ all -> 0x01d5 }
            monitor-enter(r14);	 Catch:{ all -> 0x01d5 }
            r1 = 1;
            r0 = r18;
            r0.f = r1;	 Catch:{ all -> 0x02d8 }
            r1 = com.tencent.liteav.renderer.c.a;	 Catch:{ all -> 0x02d8 }
            r1.notifyAll();	 Catch:{ all -> 0x02d8 }
            monitor-exit(r14);	 Catch:{ all -> 0x02d8 }
        L_0x0297:
            if (r8 == 0) goto L_0x02f5;
        L_0x0299:
            r1 = 1;
        L_0x029a:
            r2 = r4;
            r14 = r13;
            r4 = r6;
            r6 = r1;
            r17 = r7;
            r7 = r8;
            r8 = r9;
            r9 = r10;
            r10 = r11;
            r11 = r3;
            r3 = r5;
            r5 = r17;
            goto L_0x0031;
        L_0x02aa:
            r1 = move-exception;
            monitor-exit(r3);	 Catch:{ all -> 0x02aa }
            throw r1;	 Catch:{ all -> 0x01d5 }
        L_0x02ad:
            r3 = com.tencent.liteav.renderer.c.a;	 Catch:{ all -> 0x01d5 }
            monitor-enter(r3);	 Catch:{ all -> 0x01d5 }
            r13 = 1;
            r0 = r18;
            r0.j = r13;	 Catch:{ all -> 0x02d3 }
            r13 = 1;
            r0 = r18;
            r0.f = r13;	 Catch:{ all -> 0x02d3 }
            r13 = com.tencent.liteav.renderer.c.a;	 Catch:{ all -> 0x02d3 }
            r13.notifyAll();	 Catch:{ all -> 0x02d3 }
            monitor-exit(r3);	 Catch:{ all -> 0x02d3 }
            r3 = r5;
            r5 = r7;
            r7 = r8;
            r8 = r9;
            r9 = r10;
            r10 = r11;
            r11 = r1;
            r17 = r2;
            r2 = r4;
            r4 = r6;
            r6 = r17;
            goto L_0x0031;
        L_0x02d3:
            r1 = move-exception;
            monitor-exit(r3);	 Catch:{ all -> 0x02d3 }
            throw r1;	 Catch:{ all -> 0x01d5 }
        L_0x02d6:
            r10 = 1;
            goto L_0x0297;
        L_0x02d8:
            r1 = move-exception;
            monitor-exit(r14);	 Catch:{ all -> 0x02d8 }
            throw r1;	 Catch:{ all -> 0x01d5 }
        L_0x02db:
            r1 = move-exception;
            monitor-exit(r2);	 Catch:{ all -> 0x02db }
            throw r1;
        L_0x02de:
            r13 = r14;
            goto L_0x022e;
        L_0x02e1:
            r3 = r1;
            goto L_0x0219;
        L_0x02e4:
            r10 = r11;
            r17 = r4;
            r4 = r7;
            r7 = r1;
            r1 = r3;
            r3 = r17;
            goto L_0x0190;
        L_0x02ee:
            r1 = r8;
            r8 = r10;
            goto L_0x0174;
        L_0x02f2:
            r13 = r1;
            goto L_0x00a4;
        L_0x02f5:
            r1 = r2;
            goto L_0x029a;
        L_0x02f7:
            r1 = r14;
            goto L_0x0279;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.renderer.c.i.i():void");
        }

        private void j() {
            if (this.i) {
                this.i = false;
                this.s.f();
            }
        }

        private void k() {
            if (this.h) {
                this.s.g();
                this.h = false;
                c cVar = (c) this.t.get();
                if (cVar != null) {
                    cVar.h = false;
                }
                c.a.c(this);
            }
        }

        public boolean c() {
            return this.h && this.i && l();
        }

        private boolean l() {
            return !this.d && this.e && !this.f && this.l > 0 && this.m > 0 && (this.o || this.n == 1);
        }

        public void a(int i) {
            if (i < 0 || i > 1) {
                throw new IllegalArgumentException("renderMode");
            }
            synchronized (c.a) {
                this.n = i;
                c.a.notifyAll();
            }
        }

        public int d() {
            int i;
            synchronized (c.a) {
                i = this.n;
            }
            return i;
        }

        public void e() {
            synchronized (c.a) {
                this.e = true;
                this.j = false;
                c.a.notifyAll();
                while (this.g && !this.j && !this.b) {
                    try {
                        c.a.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void f() {
            synchronized (c.a) {
                this.e = false;
                c.a.notifyAll();
                while (!this.g && !this.b) {
                    try {
                        c.a.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void a(int i, int i2) {
            synchronized (c.a) {
                this.l = i;
                this.m = i2;
                this.r = true;
                this.o = true;
                this.p = false;
                c.a.notifyAll();
                while (!this.b && !this.d && !this.p && c()) {
                    try {
                        c.a.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void g() {
            synchronized (c.a) {
                this.a = true;
                c.a.notifyAll();
                while (!this.b) {
                    try {
                        c.a.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void h() {
            this.k = true;
            c.a.notifyAll();
        }

        public void a(Runnable runnable) {
            if (runnable == null) {
                throw new IllegalArgumentException("r must not be null");
            }
            synchronized (c.a) {
                this.q.add(runnable);
                c.a.notifyAll();
            }
        }
    }

    /* compiled from: TXCGLSurfaceViewBase */
    private static class j {
        private static String a = "GLThreadManager";
        private boolean b;
        private int c;
        private boolean d;
        private boolean e;
        private boolean f;
        private i g;

        private j() {
        }

        /* synthetic */ j(AnonymousClass1 anonymousClass1) {
            this();
        }

        public synchronized void a(i iVar) {
            iVar.b = true;
            if (this.g == iVar) {
                this.g = null;
            }
            notifyAll();
        }

        public boolean b(i iVar) {
            if (this.g == iVar || this.g == null) {
                this.g = iVar;
                notifyAll();
                return true;
            }
            c();
            if (this.e) {
                return true;
            }
            if (this.g != null) {
                this.g.h();
            }
            return false;
        }

        public void c(i iVar) {
            if (this.g == iVar) {
                this.g = null;
            }
            notifyAll();
        }

        public synchronized boolean a() {
            return this.f;
        }

        public synchronized boolean b() {
            c();
            return !this.e;
        }

        public synchronized void a(GL10 gl10) {
            boolean z = true;
            synchronized (this) {
                if (!this.d) {
                    c();
                    String glGetString = gl10.glGetString(7937);
                    if (this.c < 131072) {
                        this.e = !glGetString.startsWith("Q3Dimension MSM7500 ");
                        notifyAll();
                    }
                    if (this.e) {
                        z = false;
                    }
                    this.f = z;
                    this.d = true;
                }
            }
        }

        private void c() {
            this.c = 131072;
            this.e = true;
            this.b = true;
        }
    }

    /* compiled from: TXCGLSurfaceViewBase */
    public interface k {
        GL a(GL gl);
    }

    /* compiled from: TXCGLSurfaceViewBase */
    static class l extends Writer {
        private StringBuilder a = new StringBuilder();

        l() {
        }

        public void close() {
            a();
        }

        public void flush() {
            a();
        }

        public void write(char[] cArr, int i, int i2) {
            for (int i3 = 0; i3 < i2; i3++) {
                char c = cArr[i + i3];
                if (c == 10) {
                    a();
                } else {
                    this.a.append(c);
                }
            }
        }

        private void a() {
            if (this.a.length() > 0) {
                TXCLog.v("TXCGLSurfaceViewBase", this.a.toString());
                this.a.delete(0, this.a.length());
            }
        }
    }

    /* compiled from: TXCGLSurfaceViewBase */
    private class m extends b {
        public m(boolean z) {
            int i;
            if (z) {
                i = 16;
            } else {
                i = 0;
            }
            super(8, 8, 8, 0, i, 0);
        }
    }

    public c(Context context) {
        super(context);
        b();
    }

    protected int c() {
        return 0;
    }

    protected void finalize() throws Throwable {
        try {
            if (this.b != null) {
                this.b.g();
            }
            super.finalize();
        } catch (Throwable th) {
            super.finalize();
        }
    }

    private void b() {
        getHolder().addCallback(this);
    }

    public void setGLWrapper(k kVar) {
        this.m = kVar;
    }

    public void setDebugFlags(int i) {
        this.n = i;
    }

    public int getDebugFlags() {
        return this.n;
    }

    public void setPreserveEGLContextOnPause(boolean z) {
        this.p = z;
    }

    public boolean getPreserveEGLContextOnPause() {
        return this.p;
    }

    public void setRenderer(Renderer renderer) {
        f();
        if (this.j == null) {
            this.j = new m(true);
        }
        if (this.k == null) {
            this.k = new c(this, null);
        }
        if (this.l == null) {
            this.l = new d();
        }
        this.c = renderer;
        this.b = new i(this.f);
        this.b.start();
    }

    public void setEGLContextFactory(f fVar) {
        f();
        this.k = fVar;
    }

    public void setEGLWindowSurfaceFactory(g gVar) {
        f();
        this.l = gVar;
    }

    public void setEGLConfigChooser(e eVar) {
        f();
        this.j = eVar;
    }

    public void setEGLConfigChooser(boolean z) {
        setEGLConfigChooser(new m(z));
    }

    public void a(int i, int i2, int i3, int i4, int i5, int i6) {
        setEGLConfigChooser(new b(i, i2, i3, i4, i5, i6));
    }

    public void setEGLContextClientVersion(int i) {
        f();
        this.o = i;
    }

    public void setRenderMode(int i) {
        this.b.a(i);
    }

    public int getRenderMode() {
        return this.b.d();
    }

    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        this.b.e();
        setRunInBackground(false);
    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        setRunInBackground(true);
        if (!this.d) {
            this.b.a(new Runnable() {
                public void run() {
                    c.this.a();
                }
            });
            this.b.f();
        }
    }

    protected void a() {
    }

    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        this.b.a(i2, i3);
    }

    public void c(boolean z) {
        this.d = z;
        if (!this.d && this.e && this.b != null) {
            TXCLog.w("TXCGLSurfaceViewBase", "background capture destroy surface when not enable background run");
            this.b.a(new Runnable() {
                public void run() {
                    c.this.a();
                }
            });
            this.b.f();
        }
    }

    protected void setRunInBackground(boolean z) {
        this.e = z;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.i && this.c != null) {
            int d;
            if (this.b != null) {
                d = this.b.d();
            } else {
                d = 1;
            }
            this.b = new i(this.f);
            if (d != 1) {
                this.b.a(d);
            }
            this.b.start();
        }
        this.i = false;
    }

    protected void onDetachedFromWindow() {
        if (this.b != null) {
            this.b.g();
        }
        this.i = true;
        super.onDetachedFromWindow();
    }

    private void f() {
        if (this.b != null) {
            throw new IllegalStateException("setRenderer has already been called for this instance.");
        }
    }

    public int d() {
        return this.b.a();
    }

    public h getEGLHelper() {
        return this.b.b();
    }
}
