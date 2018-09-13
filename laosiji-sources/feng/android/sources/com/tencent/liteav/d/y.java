package com.tencent.liteav.d;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.graphics.SurfaceTexture.OnFrameAvailableListener;
import android.opengl.GLES20;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.Surface;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import android.widget.FrameLayout;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.c.e;
import com.tencent.liteav.e.d;
import com.tencent.liteav.i.a.f;

/* compiled from: VideoGLRender */
public class y {
    private final Context a;
    private Object b = new Object();
    private float[] c;
    private d d;
    private j e;
    private FrameLayout f;
    private TextureView g;
    private int h;
    private int i;
    private Handler j;
    private HandlerThread k;
    private com.tencent.liteav.renderer.d l;
    private com.tencent.liteav.renderer.d m;
    private SurfaceTexture n;
    private SurfaceTexture o;
    private Surface p;
    private boolean q;
    private e r;
    private boolean s;
    private boolean t;
    private SurfaceTextureListener u = new SurfaceTextureListener() {
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
            TXCLog.d("VideoGLRender", "onSurfaceTextureAvailable surface:" + surfaceTexture + ",width:" + i + ",height:" + i2 + ", mSaveSurfaceTexture = " + y.this.o);
            y.this.h = i;
            y.this.i = i2;
            if (y.this.o == null) {
                y.this.o = surfaceTexture;
                y.this.a(surfaceTexture);
            } else if (VERSION.SDK_INT >= 16) {
                y.this.g.setSurfaceTexture(y.this.o);
            }
        }

        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
            TXCLog.d("VideoGLRender", "wonSurfaceTextureSizeChanged surface:" + surfaceTexture + ",width:" + i + ",height:" + i2);
            y.this.h = i;
            y.this.i = i2;
            if (y.this.e != null) {
                y.this.e.a(i, i2);
            }
        }

        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            TXCLog.d("VideoGLRender", "onSurfaceTextureDestroyed surface:" + surfaceTexture);
            if (!y.this.s) {
                y.this.o = null;
                y.this.b(false);
            }
            return false;
        }

        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        }
    };
    private OnFrameAvailableListener v = new OnFrameAvailableListener() {
        public void onFrameAvailable(SurfaceTexture surfaceTexture) {
            synchronized (this) {
                y.this.q = true;
            }
        }
    };

    public y(Context context) {
        this.a = context;
        this.c = new float[16];
        this.d = new d();
        this.k = new HandlerThread("VideoGLRender");
        this.k.start();
        this.j = new Handler(this.k.getLooper());
    }

    public void a(j jVar) {
        this.e = jVar;
    }

    public void a(f fVar) {
        if (this.f != null) {
            this.f.removeAllViews();
        }
        FrameLayout frameLayout = fVar.a;
        if (frameLayout == null) {
            TXCLog.w("VideoGLRender", "initWithPreview param.videoView is NULL!!!");
            return;
        }
        if (this.f == null || !frameLayout.equals(this.f)) {
            this.g = new TextureView(this.a);
            this.g.setSurfaceTextureListener(this.u);
        }
        this.f = frameLayout;
        this.f.addView(this.g);
    }

    public int a() {
        return this.h;
    }

    public int b() {
        return this.i;
    }

    public void c() {
        this.s = true;
    }

    public void d() {
        this.s = false;
    }

    public void e() {
        this.s = false;
        b(true);
        if (this.g != null) {
            this.g.setSurfaceTextureListener(null);
            this.g = null;
        }
        if (this.f != null) {
            this.f.removeAllViews();
            this.f = null;
        }
        if (this.e != null) {
            this.e = null;
        }
        this.u = null;
        this.v = null;
    }

    private void a(final SurfaceTexture surfaceTexture) {
        if (this.j != null) {
            this.j.post(new Runnable() {
                public void run() {
                    y.this.d.a(surfaceTexture);
                    y.this.f();
                    if (y.this.e != null) {
                        y.this.e.a(y.this.p);
                    }
                }
            });
        }
    }

    private void b(final boolean z) {
        if (this.j != null) {
            this.j.post(new Runnable() {
                public void run() {
                    try {
                        if (y.this.j != null) {
                            if (y.this.e != null) {
                                y.this.e.b(y.this.p);
                            }
                            y.this.g();
                            y.this.d.a();
                            if (z) {
                                y.this.j = null;
                                if (y.this.k != null) {
                                    y.this.k.quit();
                                    y.this.k = null;
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void f() {
        this.l = new com.tencent.liteav.renderer.d(true);
        this.l.b();
        this.m = new com.tencent.liteav.renderer.d(false);
        this.m.b();
        this.n = new SurfaceTexture(this.l.a());
        this.p = new Surface(this.n);
        this.n.setOnFrameAvailableListener(this.v);
        this.t = true;
    }

    private void g() {
        this.t = false;
        if (this.l != null) {
            this.l.c();
        }
        this.l = null;
        if (this.m != null) {
            this.m.c();
        }
        this.m = null;
        if (this.n != null) {
            this.n.setOnFrameAvailableListener(null);
            this.n.release();
            this.n = null;
        }
        if (this.p != null) {
            this.p.release();
            this.p = null;
        }
    }

    public void a(int i, int i2, int i3) {
        GLES20.glViewport(0, 0, i2, i3);
        if (this.m != null) {
            this.m.a(i, false, 0);
        }
    }

    public void a(final e eVar) {
        if (this.j != null) {
            this.j.post(new Runnable() {
                public void run() {
                    if (y.this.c(eVar)) {
                        y.this.d.b();
                    }
                }
            });
        }
    }

    public void a(final boolean z) {
        if (this.j != null) {
            this.j.post(new Runnable() {
                public void run() {
                    if (y.this.e != null) {
                        y.this.e.a(z);
                    }
                }
            });
        }
    }

    public void b(final e eVar) {
        if (this.j != null) {
            this.j.post(new Runnable() {
                public void run() {
                    y.this.q = true;
                    y.this.c(eVar);
                    y.this.d.b();
                }
            });
        }
    }

    /* JADX WARNING: Missing block: B:17:0x0046, code:
            android.opengl.GLES20.glViewport(0, 0, r4.h, r4.i);
     */
    /* JADX WARNING: Missing block: B:18:0x004d, code:
            if (r1 == false) goto L_0x0074;
     */
    /* JADX WARNING: Missing block: B:20:0x0051, code:
            if (r4.n == null) goto L_0x005f;
     */
    /* JADX WARNING: Missing block: B:21:0x0053, code:
            r4.n.updateTexImage();
            r4.n.getTransformMatrix(r4.c);
     */
    /* JADX WARNING: Missing block: B:23:0x0061, code:
            if (r4.e == null) goto L_0x0089;
     */
    /* JADX WARNING: Missing block: B:25:0x0067, code:
            if (r5.y() != 0) goto L_0x007b;
     */
    /* JADX WARNING: Missing block: B:26:0x0069, code:
            r4.e.a(r5.x(), r4.c, r5);
     */
    /* JADX WARNING: Missing block: B:33:0x007b, code:
            r4.e.a(r4.l.a(), r4.c, r5);
     */
    /* JADX WARNING: Missing block: B:35:0x008b, code:
            if (r4.m == null) goto L_0x0074;
     */
    /* JADX WARNING: Missing block: B:36:0x008d, code:
            r4.m.a(r4.n);
     */
    /* JADX WARNING: Missing block: B:41:?, code:
            return true;
     */
    private boolean c(com.tencent.liteav.c.e r5) {
        /*
        r4 = this;
        r0 = 0;
        r1 = r4.t;
        if (r1 != 0) goto L_0x0006;
    L_0x0005:
        return r0;
    L_0x0006:
        r1 = r5.p();
        if (r1 == 0) goto L_0x0039;
    L_0x000c:
        r1 = "VideoGLRender";
        r2 = "onDrawFrame, frame isEndFrame";
        com.tencent.liteav.basic.log.TXCLog.d(r1, r2);
        r1 = r4.e;
        if (r1 == 0) goto L_0x0005;
    L_0x0019:
        r1 = r5.y();
        if (r1 != 0) goto L_0x002b;
    L_0x001f:
        r1 = r4.e;
        r2 = r5.x();
        r3 = r4.c;
        r1.a(r2, r3, r5);
        goto L_0x0005;
    L_0x002b:
        r1 = r4.e;
        r2 = r4.l;
        r2 = r2.a();
        r3 = r4.c;
        r1.a(r2, r3, r5);
        goto L_0x0005;
    L_0x0039:
        r4.r = r5;
        monitor-enter(r4);
        r1 = r4.q;	 Catch:{ all -> 0x0078 }
        if (r1 == 0) goto L_0x0076;
    L_0x0040:
        r1 = r4.q;	 Catch:{ all -> 0x0078 }
        r2 = 0;
        r4.q = r2;	 Catch:{ all -> 0x0078 }
        monitor-exit(r4);	 Catch:{ all -> 0x0078 }
        r2 = r4.h;
        r3 = r4.i;
        android.opengl.GLES20.glViewport(r0, r0, r2, r3);
        if (r1 == 0) goto L_0x0074;
    L_0x004f:
        r0 = r4.n;
        if (r0 == 0) goto L_0x005f;
    L_0x0053:
        r0 = r4.n;
        r0.updateTexImage();
        r0 = r4.n;
        r1 = r4.c;
        r0.getTransformMatrix(r1);
    L_0x005f:
        r0 = r4.e;
        if (r0 == 0) goto L_0x0089;
    L_0x0063:
        r0 = r5.y();
        if (r0 != 0) goto L_0x007b;
    L_0x0069:
        r0 = r4.e;
        r1 = r5.x();
        r2 = r4.c;
        r0.a(r1, r2, r5);
    L_0x0074:
        r0 = 1;
        goto L_0x0005;
    L_0x0076:
        monitor-exit(r4);	 Catch:{ all -> 0x0078 }
        goto L_0x0005;
    L_0x0078:
        r0 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x0078 }
        throw r0;
    L_0x007b:
        r0 = r4.e;
        r1 = r4.l;
        r1 = r1.a();
        r2 = r4.c;
        r0.a(r1, r2, r5);
        goto L_0x0074;
    L_0x0089:
        r0 = r4.m;
        if (r0 == 0) goto L_0x0074;
    L_0x008d:
        r0 = r4.m;
        r1 = r4.n;
        r0.a(r1);
        goto L_0x0074;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.d.y.c(com.tencent.liteav.c.e):boolean");
    }
}
