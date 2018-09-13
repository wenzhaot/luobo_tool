package com.tencent.liteav.d;

import android.graphics.SurfaceTexture;
import android.graphics.SurfaceTexture.OnFrameAvailableListener;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.Surface;
import com.tencent.liteav.basic.c.c;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.c.e;
import com.tencent.liteav.c.g;
import com.tencent.liteav.renderer.d;

/* compiled from: VideoGLGenerate */
public class x {
    private final String a = "VideoGLGenerate";
    private float[] b = new float[16];
    private Handler c;
    private HandlerThread d = new HandlerThread("VideoGLGenerate");
    private int e;
    private int f;
    private c g;
    private d h;
    private d i;
    private l j;
    private j k;
    private SurfaceTexture l;
    private Surface m;
    private boolean n;
    private boolean o;
    private e p;
    private OnFrameAvailableListener q = new OnFrameAvailableListener() {
        public void onFrameAvailable(SurfaceTexture surfaceTexture) {
            x.this.n = true;
            if (x.this.p != null) {
                x.this.c(x.this.p);
                x.this.p = null;
            }
        }
    };

    public x() {
        this.d.start();
        this.c = new Handler(this.d.getLooper());
    }

    public void a(g gVar) {
        this.e = gVar.a;
        this.f = gVar.b;
    }

    public void a(j jVar) {
        this.k = jVar;
    }

    public void a(l lVar) {
        this.j = lVar;
    }

    public void a() {
        TXCLog.d("VideoGLGenerate", "start");
        if (this.c != null) {
            this.c.post(new Runnable() {
                public void run() {
                    x.this.f();
                    x.this.d();
                }
            });
        }
    }

    public void b() {
        TXCLog.d("VideoGLGenerate", "stop");
        if (this.c != null) {
            this.c.post(new Runnable() {
                public void run() {
                    x.this.e();
                    x.this.g();
                }
            });
        }
    }

    public void c() {
        if (this.c != null) {
            if (this.d != null) {
                if (VERSION.SDK_INT >= 18) {
                    this.d.quitSafely();
                } else {
                    this.d.quit();
                }
                this.d = null;
            }
            this.k = null;
            this.j = null;
            this.q = null;
            this.c = null;
        }
    }

    private void d() {
        TXCLog.d("VideoGLGenerate", "initTextureRender");
        this.h = new d(true);
        this.h.b();
        this.i = new d(false);
        this.i.b();
        this.l = new SurfaceTexture(this.h.a());
        this.m = new Surface(this.l);
        this.l.setOnFrameAvailableListener(this.q);
        this.o = true;
        if (this.k != null) {
            this.k.a(this.m);
        }
        if (this.j != null) {
            this.j.a(this.g.d());
        }
    }

    private void e() {
        TXCLog.d("VideoGLGenerate", "destroyTextureRender");
        this.o = false;
        if (this.h != null) {
            this.h.c();
        }
        this.h = null;
        if (this.i != null) {
            this.i.c();
        }
        this.i = null;
    }

    private void f() {
        TXCLog.d("VideoGLGenerate", "initEGL");
        this.g = c.a(null, null, null, this.e, this.f);
    }

    private void g() {
        TXCLog.d("VideoGLGenerate", "destroyEGL");
        if (this.k != null) {
            this.k.b(this.m);
        }
        if (this.g != null) {
            this.g.b();
            this.g = null;
        }
    }

    public synchronized void a(final e eVar) {
        if (this.c != null) {
            this.c.post(new Runnable() {
                public void run() {
                    x.this.c(eVar);
                }
            });
        }
    }

    public void b(final e eVar) {
        if (this.c != null) {
            this.c.post(new Runnable() {
                public void run() {
                    x.this.n = true;
                    x.this.c(eVar);
                }
            });
        }
    }

    /* JADX WARNING: Missing block: B:18:0x0041, code:
            android.opengl.GLES20.glViewport(0, 0, r4.e, r4.f);
     */
    /* JADX WARNING: Missing block: B:19:0x0048, code:
            if (r1 == false) goto L_0x006f;
     */
    /* JADX WARNING: Missing block: B:22:0x004c, code:
            if (r4.l == null) goto L_0x005a;
     */
    /* JADX WARNING: Missing block: B:23:0x004e, code:
            r4.l.updateTexImage();
            r4.l.getTransformMatrix(r4.b);
     */
    private boolean c(com.tencent.liteav.c.e r5) {
        /*
        r4 = this;
        r0 = 0;
        r1 = r4.o;
        if (r1 != 0) goto L_0x0006;
    L_0x0005:
        return r0;
    L_0x0006:
        r1 = r5.p();
        if (r1 != 0) goto L_0x0012;
    L_0x000c:
        r1 = r5.r();
        if (r1 == 0) goto L_0x0036;
    L_0x0012:
        r1 = r4.k;
        if (r1 == 0) goto L_0x0005;
    L_0x0016:
        r1 = r5.y();
        if (r1 != 0) goto L_0x0028;
    L_0x001c:
        r1 = r4.k;
        r2 = r5.x();
        r3 = r4.b;
        r1.a(r2, r3, r5);
        goto L_0x0005;
    L_0x0028:
        r1 = r4.k;
        r2 = r4.h;
        r2 = r2.a();
        r3 = r4.b;
        r1.a(r2, r3, r5);
        goto L_0x0005;
    L_0x0036:
        monitor-enter(r4);
        r1 = r4.n;	 Catch:{ all -> 0x0075 }
        if (r1 == 0) goto L_0x0071;
    L_0x003b:
        r1 = r4.n;	 Catch:{ all -> 0x0075 }
        r2 = 0;
        r4.n = r2;	 Catch:{ all -> 0x0075 }
        monitor-exit(r4);	 Catch:{ all -> 0x0075 }
        r2 = r4.e;
        r3 = r4.f;
        android.opengl.GLES20.glViewport(r0, r0, r2, r3);
        if (r1 == 0) goto L_0x006f;
    L_0x004a:
        r0 = r4.l;	 Catch:{ Exception -> 0x0092 }
        if (r0 == 0) goto L_0x005a;
    L_0x004e:
        r0 = r4.l;	 Catch:{ Exception -> 0x0092 }
        r0.updateTexImage();	 Catch:{ Exception -> 0x0092 }
        r0 = r4.l;	 Catch:{ Exception -> 0x0092 }
        r1 = r4.b;	 Catch:{ Exception -> 0x0092 }
        r0.getTransformMatrix(r1);	 Catch:{ Exception -> 0x0092 }
    L_0x005a:
        r0 = r4.k;
        if (r0 == 0) goto L_0x0086;
    L_0x005e:
        r0 = r5.y();
        if (r0 != 0) goto L_0x0078;
    L_0x0064:
        r0 = r4.k;
        r1 = r5.x();
        r2 = r4.b;
        r0.a(r1, r2, r5);
    L_0x006f:
        r0 = 1;
        goto L_0x0005;
    L_0x0071:
        r4.p = r5;	 Catch:{ all -> 0x0075 }
        monitor-exit(r4);	 Catch:{ all -> 0x0075 }
        goto L_0x0005;
    L_0x0075:
        r0 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x0075 }
        throw r0;
    L_0x0078:
        r0 = r4.k;
        r1 = r4.h;
        r1 = r1.a();
        r2 = r4.b;
        r0.a(r1, r2, r5);
        goto L_0x006f;
    L_0x0086:
        r0 = r4.i;
        if (r0 == 0) goto L_0x006f;
    L_0x008a:
        r0 = r4.i;
        r1 = r4.l;
        r0.a(r1);
        goto L_0x006f;
    L_0x0092:
        r0 = move-exception;
        goto L_0x005a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.d.x.c(com.tencent.liteav.c.e):boolean");
    }
}
