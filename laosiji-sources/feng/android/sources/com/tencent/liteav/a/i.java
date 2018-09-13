package com.tencent.liteav.a;

import android.graphics.SurfaceTexture;
import android.graphics.SurfaceTexture.OnFrameAvailableListener;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.Surface;
import com.tencent.liteav.basic.c.c;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.c.e;
import com.tencent.liteav.c.g;
import com.tencent.liteav.d.j;
import com.tencent.liteav.d.l;
import com.tencent.liteav.renderer.d;
import java.util.ArrayList;
import java.util.List;

/* compiled from: VideoGLMultiGenerate */
public class i {
    public List<a> a = new ArrayList();
    private final String b = "VideoGLMultiGenerate";
    private Handler c;
    private HandlerThread d;
    private int e;
    private int f;
    private c g;
    private d h;
    private l i;
    private boolean j;

    /* compiled from: VideoGLMultiGenerate */
    public class a {
        private int b;
        private float[] c;
        private int d;
        private int e;
        private d f;
        private j g;
        private SurfaceTexture h;
        private Surface i;
        private boolean j;
        private e k;
        private OnFrameAvailableListener l = new OnFrameAvailableListener() {
            public void onFrameAvailable(SurfaceTexture surfaceTexture) {
                TXCLog.d("VideoGLMultiGenerate", "onFrameAvailable, index = " + a.this.b + ", mFrame = " + a.this.k);
                a.this.j = true;
                if (a.this.k != null) {
                    i.this.b(a.this.k, a.this.b);
                    a.this.k = null;
                }
            }
        };
    }

    public i(int i) {
        for (int i2 = 0; i2 < i; i2++) {
            a aVar = new a();
            aVar.b = i2;
            aVar.c = new float[16];
            this.a.add(aVar);
        }
        this.d = new HandlerThread("VideoGLMultiGenerate");
        this.d.start();
        this.c = new Handler(this.d.getLooper());
    }

    public void a(g gVar, int i) {
        if (this.a == null || this.a.size() == 0 || i >= this.a.size()) {
            TXCLog.e("VideoGLMultiGenerate", "setRenderResolution, mVideoGLInfoList is empty or mIndex is larger than size");
            return;
        }
        a aVar = (a) this.a.get(i);
        aVar.d = gVar.a;
        aVar.e = gVar.b;
        this.e = gVar.a > this.e ? gVar.a : this.e;
        this.f = gVar.b > this.f ? gVar.b : this.f;
        TXCLog.i("VideoGLMultiGenerate", "setRenderResolution, mSurfaceWidth = " + this.e + ", mSurfaceHeight = " + this.f);
    }

    public void a(j jVar, int i) {
        if (this.a == null || this.a.size() == 0 || i >= this.a.size()) {
            TXCLog.e("VideoGLMultiGenerate", "setListener, mVideoGLInfoList is empty or mIndex is larger than size");
        } else {
            ((a) this.a.get(i)).g = jVar;
        }
    }

    public void a(l lVar) {
        this.i = lVar;
    }

    public void a() {
        TXCLog.d("VideoGLMultiGenerate", "start");
        if (this.c != null) {
            this.c.post(new Runnable() {
                public void run() {
                    i.this.e();
                    i.this.c();
                }
            });
        }
    }

    public void b() {
        TXCLog.d("VideoGLMultiGenerate", "stop");
        if (this.c != null) {
            this.c.post(new Runnable() {
                public void run() {
                    i.this.d();
                    i.this.f();
                }
            });
        }
    }

    private void c() {
        boolean z = false;
        TXCLog.d("VideoGLMultiGenerate", "initTextureRender");
        this.h = new d(false);
        this.h.b();
        while (true) {
            boolean z2 = z;
            if (z2 >= this.a.size()) {
                break;
            }
            a aVar = (a) this.a.get(z2);
            aVar.f = new d(true);
            aVar.f.b();
            aVar.h = new SurfaceTexture(aVar.f.a());
            aVar.i = new Surface(aVar.h);
            aVar.h.setOnFrameAvailableListener(aVar.l);
            if (aVar.g != null) {
                aVar.g.a(aVar.i);
            }
            if (z2 == this.a.size() - 1) {
                this.j = true;
            }
            z = z2 + 1;
        }
        if (this.i != null) {
            this.i.a(this.g.d());
        }
    }

    private void d() {
        TXCLog.d("VideoGLMultiGenerate", "destroyTextureRender");
        this.j = false;
        for (int i = 0; i < this.a.size(); i++) {
            a aVar = (a) this.a.get(i);
            if (aVar.f != null) {
                aVar.f.c();
                aVar.f = null;
                if (aVar.h != null) {
                    aVar.h.setOnFrameAvailableListener(null);
                    aVar.h.release();
                    aVar.h = null;
                }
                if (aVar.i != null) {
                    aVar.i.release();
                    aVar.i = null;
                }
                aVar.h = null;
                aVar.k = null;
                aVar.j = false;
                aVar.c = new float[16];
            }
        }
        if (this.h != null) {
            this.h.c();
        }
        this.h = null;
    }

    private void e() {
        TXCLog.d("VideoGLMultiGenerate", "initEGL");
        this.g = c.a(null, null, null, this.e, this.f);
    }

    private void f() {
        TXCLog.d("VideoGLMultiGenerate", "destroyEGL");
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= this.a.size()) {
                break;
            }
            a aVar = (a) this.a.get(i2);
            if (aVar.g != null) {
                aVar.g.b(aVar.i);
            }
            i = i2 + 1;
        }
        if (this.g != null) {
            this.g.b();
            this.g = null;
        }
    }

    public synchronized void a(final e eVar, final int i) {
        if (this.a == null || this.a.size() == 0 || i >= this.a.size()) {
            TXCLog.e("VideoGLMultiGenerate", "setListener, mVideoGLInfoList is empty or mIndex is larger than size");
        } else if (this.c != null) {
            this.c.post(new Runnable() {
                public void run() {
                    i.this.b(eVar, i);
                }
            });
        }
    }

    /* JADX WARNING: Missing block: B:20:0x0085, code:
            android.opengl.GLES20.glViewport(0, 0, com.tencent.liteav.a.i.a.j(r0), com.tencent.liteav.a.i.a.k(r0));
     */
    /* JADX WARNING: Missing block: B:21:0x0090, code:
            if (r2 == false) goto L_0x00c5;
     */
    /* JADX WARNING: Missing block: B:24:0x0096, code:
            if (com.tencent.liteav.a.i.a.d(r0) == null) goto L_0x00aa;
     */
    /* JADX WARNING: Missing block: B:25:0x0098, code:
            com.tencent.liteav.a.i.a.d(r0).updateTexImage();
            com.tencent.liteav.a.i.a.d(r0).getTransformMatrix(com.tencent.liteav.a.i.a.i(r0));
     */
    private boolean b(com.tencent.liteav.c.e r6, int r7) {
        /*
        r5 = this;
        r1 = 0;
        r0 = r5.j;
        if (r0 != 0) goto L_0x0007;
    L_0x0005:
        r0 = r1;
    L_0x0006:
        return r0;
    L_0x0007:
        r0 = r5.a;
        r0 = r0.get(r7);
        r0 = (com.tencent.liteav.a.i.a) r0;
        r2 = "VideoGLMultiGenerate";
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "onDrawFrame, mTextureAvailable = ";
        r3 = r3.append(r4);
        r4 = r0.j;
        r3 = r3.append(r4);
        r4 = ", mIndex = ";
        r3 = r3.append(r4);
        r3 = r3.append(r7);
        r3 = r3.toString();
        com.tencent.liteav.basic.log.TXCLog.d(r2, r3);
        r2 = r6.p();
        if (r2 != 0) goto L_0x0044;
    L_0x003e:
        r2 = r6.r();
        if (r2 == 0) goto L_0x0075;
    L_0x0044:
        r2 = r0.g;
        if (r2 == 0) goto L_0x005f;
    L_0x004a:
        r2 = r6.y();
        if (r2 != 0) goto L_0x0061;
    L_0x0050:
        r2 = r0.g;
        r3 = r6.x();
        r0 = r0.c;
        r2.a(r3, r0, r6);
    L_0x005f:
        r0 = r1;
        goto L_0x0006;
    L_0x0061:
        r2 = r0.g;
        r3 = r0.f;
        r3 = r3.a();
        r0 = r0.c;
        r2.a(r3, r0, r6);
        goto L_0x005f;
    L_0x0075:
        monitor-enter(r5);
        r2 = r0.j;	 Catch:{ all -> 0x00cf }
        if (r2 == 0) goto L_0x00c8;
    L_0x007c:
        r2 = r0.j;	 Catch:{ all -> 0x00cf }
        r3 = 0;
        r0.j = r3;	 Catch:{ all -> 0x00cf }
        monitor-exit(r5);	 Catch:{ all -> 0x00cf }
        r3 = r0.d;
        r4 = r0.e;
        android.opengl.GLES20.glViewport(r1, r1, r3, r4);
        if (r2 == 0) goto L_0x00c5;
    L_0x0092:
        r1 = r0.h;	 Catch:{ Exception -> 0x00f4 }
        if (r1 == 0) goto L_0x00aa;
    L_0x0098:
        r1 = r0.h;	 Catch:{ Exception -> 0x00f4 }
        r1.updateTexImage();	 Catch:{ Exception -> 0x00f4 }
        r1 = r0.h;	 Catch:{ Exception -> 0x00f4 }
        r2 = r0.c;	 Catch:{ Exception -> 0x00f4 }
        r1.getTransformMatrix(r2);	 Catch:{ Exception -> 0x00f4 }
    L_0x00aa:
        r1 = r0.g;
        if (r1 == 0) goto L_0x00e6;
    L_0x00b0:
        r1 = r6.y();
        if (r1 != 0) goto L_0x00d2;
    L_0x00b6:
        r1 = r0.g;
        r2 = r6.x();
        r0 = r0.c;
        r1.a(r2, r0, r6);
    L_0x00c5:
        r0 = 1;
        goto L_0x0006;
    L_0x00c8:
        r0.k = r6;	 Catch:{ all -> 0x00cf }
        monitor-exit(r5);	 Catch:{ all -> 0x00cf }
        r0 = r1;
        goto L_0x0006;
    L_0x00cf:
        r0 = move-exception;
        monitor-exit(r5);	 Catch:{ all -> 0x00cf }
        throw r0;
    L_0x00d2:
        r1 = r0.g;
        r2 = r0.f;
        r2 = r2.a();
        r0 = r0.c;
        r1.a(r2, r0, r6);
        goto L_0x00c5;
    L_0x00e6:
        r1 = r5.h;
        if (r1 == 0) goto L_0x00c5;
    L_0x00ea:
        r1 = r5.h;
        r0 = r0.h;
        r1.a(r0);
        goto L_0x00c5;
    L_0x00f4:
        r1 = move-exception;
        goto L_0x00aa;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.a.i.b(com.tencent.liteav.c.e, int):boolean");
    }
}
