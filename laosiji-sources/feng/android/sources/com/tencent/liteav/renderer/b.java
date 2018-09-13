package com.tencent.liteav.renderer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.graphics.SurfaceTexture.OnFrameAvailableListener;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.HandlerThread;
import com.tencent.liteav.basic.c.d;
import com.tencent.liteav.basic.c.f;
import com.tencent.liteav.basic.c.g;
import com.tencent.liteav.basic.c.h;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.rtmp.TXLiveConstants;
import java.lang.ref.WeakReference;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Queue;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.opengles.GL10;

/* compiled from: TXCGLSurfaceView */
public class b extends c implements OnFrameAvailableListener, Renderer {
    private int A = 0;
    private int B = 0;
    private boolean C = true;
    private a D = null;
    private int E = 0;
    private int F;
    private int G;
    private final Queue<Runnable> H = new LinkedList();
    e a;
    f b;
    WeakReference<com.tencent.liteav.basic.b.a> c;
    private SurfaceTexture i;
    private EGLContext j;
    private d k;
    private boolean l = false;
    private int[] m;
    private float[] n = new float[16];
    private int o = 0;
    private boolean p = false;
    private float q = 1.0f;
    private float r = 1.0f;
    private int s = 20;
    private long t = 0;
    private long u = 0;
    private int v = 12288;
    private boolean w = true;
    private boolean x = false;
    private Object y = new Object();
    private Handler z;

    /* compiled from: TXCGLSurfaceView */
    public interface a {
        void a(Bitmap bitmap);
    }

    public b(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        a(8, 8, 8, 8, 16, 0);
        setRenderer(this);
    }

    public void setFPS(final int i) {
        a(new Runnable() {
            public void run() {
                b.this.s = i;
                if (b.this.s <= 0) {
                    b.this.s = 1;
                } else if (b.this.s > 60) {
                    b.this.s = 60;
                }
                b.this.u = 0;
                b.this.t = 0;
            }
        });
    }

    public void setRendMode(final int i) {
        a(new Runnable() {
            public void run() {
                b.this.E = i;
                GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
                GLES20.glClear(16640);
            }
        });
    }

    protected void a() {
        if (this.a != null) {
            this.a.onSurfaceTextureDestroy(null);
        }
    }

    protected void setRunInBackground(boolean z) {
        if (z) {
            synchronized (this) {
                TXCLog.d("TXCGLSurfaceView", "background capture enter background");
                this.e = true;
            }
            return;
        }
        a(new Runnable() {
            public void run() {
                synchronized (this) {
                    TXCLog.d("TXCGLSurfaceView", "background capture exit background");
                    b.this.e = false;
                }
            }
        });
    }

    public void setListener(e eVar) {
        this.a = eVar;
    }

    public void setTextureListener(f fVar) {
        this.b = fVar;
    }

    public void setNotifyListener(com.tencent.liteav.basic.b.a aVar) {
        this.c = new WeakReference(aVar);
    }

    public void a(a aVar) {
        this.D = aVar;
        this.C = true;
    }

    public void b() {
        this.l = false;
        this.D = null;
        this.C = false;
    }

    /* JADX WARNING: Missing block: B:13:0x0012, code:
            if (r11.E != 0) goto L_0x00a0;
     */
    /* JADX WARNING: Missing block: B:14:0x0014, code:
            r11.A = r13;
            r11.B = r14;
            r11.F = 0;
            r11.G = 0;
            android.opengl.GLES20.glViewport(0, 0, r13, r14);
            r3 = getWidth();
            r4 = getHeight();
     */
    /* JADX WARNING: Missing block: B:15:0x002b, code:
            if (r4 == 0) goto L_0x00ca;
     */
    /* JADX WARNING: Missing block: B:16:0x002d, code:
            r5 = ((float) r3) / ((float) r4);
     */
    /* JADX WARNING: Missing block: B:17:0x0031, code:
            if (r18 == 0) goto L_0x00cf;
     */
    /* JADX WARNING: Missing block: B:18:0x0033, code:
            r2 = ((float) r17) / ((float) r18);
     */
    /* JADX WARNING: Missing block: B:20:0x003c, code:
            if (r11.p != r15) goto L_0x0050;
     */
    /* JADX WARNING: Missing block: B:22:0x0042, code:
            if (r11.o != r16) goto L_0x0050;
     */
    /* JADX WARNING: Missing block: B:24:0x0048, code:
            if (r11.q != r5) goto L_0x0050;
     */
    /* JADX WARNING: Missing block: B:26:0x004e, code:
            if (r11.r == r2) goto L_0x0092;
     */
    /* JADX WARNING: Missing block: B:27:0x0050, code:
            r11.p = r15;
            r11.o = r16;
            r11.q = r5;
            r11.r = r2;
            r5 = (720 - r11.o) % 360;
     */
    /* JADX WARNING: Missing block: B:28:0x0062, code:
            if (r5 == 90) goto L_0x0068;
     */
    /* JADX WARNING: Missing block: B:30:0x0066, code:
            if (r5 != 270) goto L_0x00d3;
     */
    /* JADX WARNING: Missing block: B:31:0x0068, code:
            r10 = 1;
     */
    /* JADX WARNING: Missing block: B:32:0x006a, code:
            if (r10 == null) goto L_0x00d6;
     */
    /* JADX WARNING: Missing block: B:33:0x006c, code:
            r7 = r4;
     */
    /* JADX WARNING: Missing block: B:34:0x006d, code:
            if (r10 == null) goto L_0x00d8;
     */
    /* JADX WARNING: Missing block: B:35:0x006f, code:
            r2 = r11.k;
            r6 = com.tencent.liteav.basic.c.h.a(com.tencent.liteav.basic.c.g.NORMAL, false, true);
            r7 = ((float) r7) / ((float) r3);
     */
    /* JADX WARNING: Missing block: B:36:0x007d, code:
            if (r10 == null) goto L_0x00da;
     */
    /* JADX WARNING: Missing block: B:37:0x007f, code:
            r8 = false;
     */
    /* JADX WARNING: Missing block: B:38:0x0080, code:
            if (r10 == null) goto L_0x00dd;
     */
    /* JADX WARNING: Missing block: B:39:0x0082, code:
            r9 = r11.p;
     */
    /* JADX WARNING: Missing block: B:40:0x0084, code:
            r2.a(r17, r18, r5, r6, r7, r8, r9);
     */
    /* JADX WARNING: Missing block: B:41:0x008b, code:
            if (r10 == null) goto L_0x00df;
     */
    /* JADX WARNING: Missing block: B:42:0x008d, code:
            r11.k.g();
     */
    /* JADX WARNING: Missing block: B:43:0x0092, code:
            android.opengl.GLES20.glBindFramebuffer(36160, 0);
            r11.k.b(r12);
     */
    /* JADX WARNING: Missing block: B:45:0x00a3, code:
            if (r11.E != 1) goto L_0x00e5;
     */
    /* JADX WARNING: Missing block: B:46:0x00a5, code:
            r2 = a(r13, r14, r17, r18);
            r3 = r2[0];
            r4 = r2[1];
            r11.F = r2[2];
            r11.G = r2[3];
            r11.A = r3;
            r11.B = r4;
            android.opengl.GLES20.glViewport(r11.F, r11.G, r3, r4);
     */
    /* JADX WARNING: Missing block: B:47:0x00ca, code:
            r5 = 1.0f;
     */
    /* JADX WARNING: Missing block: B:48:0x00cf, code:
            r2 = 1.0f;
     */
    /* JADX WARNING: Missing block: B:49:0x00d3, code:
            r10 = null;
     */
    /* JADX WARNING: Missing block: B:50:0x00d6, code:
            r7 = r3;
     */
    /* JADX WARNING: Missing block: B:51:0x00d8, code:
            r3 = r4;
     */
    /* JADX WARNING: Missing block: B:52:0x00da, code:
            r8 = r11.p;
     */
    /* JADX WARNING: Missing block: B:53:0x00dd, code:
            r9 = false;
     */
    /* JADX WARNING: Missing block: B:54:0x00df, code:
            r11.k.h();
     */
    /* JADX WARNING: Missing block: B:55:0x00e5, code:
            r4 = r14;
            r3 = r13;
     */
    /* JADX WARNING: Missing block: B:57:?, code:
            return;
     */
    public void a(int r12, int r13, int r14, boolean r15, int r16, int r17, int r18) {
        /*
        r11 = this;
        r2 = r11.k;
        if (r2 != 0) goto L_0x0005;
    L_0x0004:
        return;
    L_0x0005:
        monitor-enter(r11);
        r2 = r11.e;	 Catch:{ all -> 0x000c }
        if (r2 == 0) goto L_0x000f;
    L_0x000a:
        monitor-exit(r11);	 Catch:{ all -> 0x000c }
        goto L_0x0004;
    L_0x000c:
        r2 = move-exception;
        monitor-exit(r11);	 Catch:{ all -> 0x000c }
        throw r2;
    L_0x000f:
        monitor-exit(r11);	 Catch:{ all -> 0x000c }
        r2 = r11.E;
        if (r2 != 0) goto L_0x00a0;
    L_0x0014:
        r11.A = r13;
        r11.B = r14;
        r2 = 0;
        r11.F = r2;
        r2 = 0;
        r11.G = r2;
        r2 = 0;
        r3 = 0;
        android.opengl.GLES20.glViewport(r2, r3, r13, r14);
        r3 = r11.getWidth();
        r4 = r11.getHeight();
    L_0x002b:
        if (r4 == 0) goto L_0x00ca;
    L_0x002d:
        r2 = (float) r3;
        r5 = (float) r4;
        r2 = r2 / r5;
        r5 = r2;
    L_0x0031:
        if (r18 == 0) goto L_0x00cf;
    L_0x0033:
        r0 = r17;
        r2 = (float) r0;
        r0 = r18;
        r6 = (float) r0;
        r2 = r2 / r6;
    L_0x003a:
        r6 = r11.p;
        if (r6 != r15) goto L_0x0050;
    L_0x003e:
        r6 = r11.o;
        r0 = r16;
        if (r6 != r0) goto L_0x0050;
    L_0x0044:
        r6 = r11.q;
        r6 = (r6 > r5 ? 1 : (r6 == r5 ? 0 : -1));
        if (r6 != 0) goto L_0x0050;
    L_0x004a:
        r6 = r11.r;
        r6 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1));
        if (r6 == 0) goto L_0x0092;
    L_0x0050:
        r11.p = r15;
        r0 = r16;
        r11.o = r0;
        r11.q = r5;
        r11.r = r2;
        r2 = r11.o;
        r2 = 720 - r2;
        r5 = r2 % 360;
        r2 = 90;
        if (r5 == r2) goto L_0x0068;
    L_0x0064:
        r2 = 270; // 0x10e float:3.78E-43 double:1.334E-321;
        if (r5 != r2) goto L_0x00d3;
    L_0x0068:
        r2 = 1;
        r10 = r2;
    L_0x006a:
        if (r10 == 0) goto L_0x00d6;
    L_0x006c:
        r7 = r4;
    L_0x006d:
        if (r10 == 0) goto L_0x00d8;
    L_0x006f:
        r2 = r11.k;
        r4 = com.tencent.liteav.basic.c.g.NORMAL;
        r6 = 0;
        r8 = 1;
        r6 = com.tencent.liteav.basic.c.h.a(r4, r6, r8);
        r4 = (float) r7;
        r3 = (float) r3;
        r7 = r4 / r3;
        if (r10 == 0) goto L_0x00da;
    L_0x007f:
        r8 = 0;
    L_0x0080:
        if (r10 == 0) goto L_0x00dd;
    L_0x0082:
        r9 = r11.p;
    L_0x0084:
        r3 = r17;
        r4 = r18;
        r2.a(r3, r4, r5, r6, r7, r8, r9);
        if (r10 == 0) goto L_0x00df;
    L_0x008d:
        r2 = r11.k;
        r2.g();
    L_0x0092:
        r2 = 36160; // 0x8d40 float:5.0671E-41 double:1.78654E-319;
        r3 = 0;
        android.opengl.GLES20.glBindFramebuffer(r2, r3);
        r2 = r11.k;
        r2.b(r12);
        goto L_0x0004;
    L_0x00a0:
        r2 = r11.E;
        r3 = 1;
        if (r2 != r3) goto L_0x00e5;
    L_0x00a5:
        r0 = r17;
        r1 = r18;
        r2 = r11.a(r13, r14, r0, r1);
        r3 = 0;
        r3 = r2[r3];
        r4 = 1;
        r4 = r2[r4];
        r5 = 2;
        r5 = r2[r5];
        r11.F = r5;
        r5 = 3;
        r2 = r2[r5];
        r11.G = r2;
        r11.A = r3;
        r11.B = r4;
        r2 = r11.F;
        r5 = r11.G;
        android.opengl.GLES20.glViewport(r2, r5, r3, r4);
        goto L_0x002b;
    L_0x00ca:
        r2 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r5 = r2;
        goto L_0x0031;
    L_0x00cf:
        r2 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        goto L_0x003a;
    L_0x00d3:
        r2 = 0;
        r10 = r2;
        goto L_0x006a;
    L_0x00d6:
        r7 = r3;
        goto L_0x006d;
    L_0x00d8:
        r3 = r4;
        goto L_0x006f;
    L_0x00da:
        r8 = r11.p;
        goto L_0x0080;
    L_0x00dd:
        r9 = 0;
        goto L_0x0084;
    L_0x00df:
        r2 = r11.k;
        r2.h();
        goto L_0x0092;
    L_0x00e5:
        r4 = r14;
        r3 = r13;
        goto L_0x002b;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.renderer.b.a(int, int, int, boolean, int, int, int):void");
    }

    private int[] a(int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int[] iArr = new int[4];
        float f = ((float) i4) / ((float) i3);
        if (((float) i2) / ((float) i) > f) {
            i5 = (int) (((float) i) * f);
            i6 = (i2 - i5) / 2;
            i2 = i5;
            i5 = 0;
        } else {
            i5 = (int) (((float) i2) / f);
            i = i5;
            i5 = (i - i5) / 2;
            i6 = 0;
        }
        iArr[0] = i;
        iArr[1] = i2;
        iArr[2] = i5;
        iArr[3] = i6;
        return iArr;
    }

    public SurfaceTexture getSurfaceTexture() {
        return this.i;
    }

    public EGLContext getGLContext() {
        return this.j;
    }

    protected int c() {
        if (this.v != 12288) {
            TXCLog.e("TXCGLSurfaceView", "background capture swapbuffer error : " + this.v);
        }
        return this.v;
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (VERSION.SDK_INT >= 21 && this.z != null) {
            this.z.getLooper().quitSafely();
        }
    }

    public void a(Runnable runnable) {
        synchronized (this.H) {
            this.H.add(runnable);
        }
    }

    /* JADX WARNING: Missing block: B:9:0x0012, code:
            if (r0 != null) goto L_0x0019;
     */
    /* JADX WARNING: Missing block: B:14:0x0019, code:
            r0.run();
     */
    /* JADX WARNING: Missing block: B:16:?, code:
            return false;
     */
    /* JADX WARNING: Missing block: B:17:?, code:
            return true;
     */
    private boolean a(java.util.Queue<java.lang.Runnable> r3) {
        /*
        r2 = this;
        r1 = 0;
        monitor-enter(r3);
        r0 = r3.isEmpty();	 Catch:{ all -> 0x0016 }
        if (r0 == 0) goto L_0x000b;
    L_0x0008:
        monitor-exit(r3);	 Catch:{ all -> 0x0016 }
        r0 = r1;
    L_0x000a:
        return r0;
    L_0x000b:
        r0 = r3.poll();	 Catch:{ all -> 0x0016 }
        r0 = (java.lang.Runnable) r0;	 Catch:{ all -> 0x0016 }
        monitor-exit(r3);	 Catch:{ all -> 0x0016 }
        if (r0 != 0) goto L_0x0019;
    L_0x0014:
        r0 = r1;
        goto L_0x000a;
    L_0x0016:
        r0 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x0016 }
        throw r0;
    L_0x0019:
        r0.run();
        r0 = 1;
        goto L_0x000a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.renderer.b.a(java.util.Queue):boolean");
    }

    public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
        this.j = ((EGL10) EGLContext.getEGL()).eglGetCurrentContext();
        this.m = new int[1];
        this.m[0] = f.b();
        if (this.m[0] <= 0) {
            this.m = null;
            TXCLog.e("TXCGLSurfaceView", "create oes texture error!! at glsurfaceview");
            return;
        }
        this.i = new SurfaceTexture(this.m[0]);
        if (VERSION.SDK_INT >= 21) {
            if (this.z != null) {
                this.z.getLooper().quitSafely();
            }
            HandlerThread handlerThread = new HandlerThread("VideoCaptureThread");
            handlerThread.start();
            this.z = new Handler(handlerThread.getLooper());
            this.i.setOnFrameAvailableListener(this, this.z);
        } else {
            this.i.setOnFrameAvailableListener(this);
        }
        this.k = new d();
        if (this.k.c()) {
            this.k.a(h.e, h.a(g.NORMAL, false, false));
            if (this.a != null) {
                this.a.onSurfaceTextureAvailable(this.i);
            }
        }
    }

    public void onSurfaceChanged(GL10 gl10, int i, int i2) {
    }

    /* JADX WARNING: Missing block: B:32:0x006b, code:
            if (r12.b == null) goto L_0x0079;
     */
    /* JADX WARNING: Missing block: B:33:0x006d, code:
            r12.b.onTextureProcess(r12.m[0], r12.n);
     */
    /* JADX WARNING: Missing block: B:34:0x0079, code:
            f();
     */
    /* JADX WARNING: Missing block: B:35:0x007c, code:
            monitor-enter(r12);
     */
    /* JADX WARNING: Missing block: B:38:0x007f, code:
            if (r12.e != false) goto L_0x0082;
     */
    /* JADX WARNING: Missing block: B:39:0x0081, code:
            r0 = 1;
     */
    /* JADX WARNING: Missing block: B:40:0x0082, code:
            monitor-exit(r12);
     */
    /* JADX WARNING: Missing block: B:41:0x0083, code:
            if (r0 == null) goto L_?;
     */
    /* JADX WARNING: Missing block: B:43:?, code:
            r12.v = d();
     */
    /* JADX WARNING: Missing block: B:54:?, code:
            return;
     */
    /* JADX WARNING: Missing block: B:56:?, code:
            return;
     */
    public void onDrawFrame(javax.microedition.khronos.opengles.GL10 r13) {
        /*
        r12 = this;
        r10 = 1;
        r0 = 0;
        r1 = r12.H;
        r12.a(r1);
    L_0x0008:
        r2 = java.lang.System.currentTimeMillis();
        r4 = r12.u;
        r6 = 0;
        r1 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r1 != 0) goto L_0x0016;
    L_0x0014:
        r12.u = r2;
    L_0x0016:
        r4 = r12.u;
        r4 = r2 - r4;
        r6 = r12.t;
        r8 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r6 = r6 * r8;
        r1 = r12.s;
        r8 = (long) r1;
        r6 = r6 / r8;
        r1 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r1 >= 0) goto L_0x002b;
    L_0x0027:
        r12.g();
        goto L_0x0008;
    L_0x002b:
        r4 = r12.t;
        r4 = r4 + r10;
        r12.t = r4;
        r4 = r12.u;
        r2 = r2 - r4;
        r4 = 2000; // 0x7d0 float:2.803E-42 double:9.88E-321;
        r1 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r1 <= 0) goto L_0x0041;
    L_0x0039:
        r12.t = r10;
        r2 = java.lang.System.currentTimeMillis();
        r12.u = r2;
    L_0x0041:
        r1 = r12.w;
        if (r1 == 0) goto L_0x0046;
    L_0x0045:
        return;
    L_0x0046:
        monitor-enter(r12);	 Catch:{ Exception -> 0x0050 }
        r1 = r12.x;	 Catch:{ all -> 0x004d }
        if (r1 != 0) goto L_0x0055;
    L_0x004b:
        monitor-exit(r12);	 Catch:{ all -> 0x004d }
        goto L_0x0045;
    L_0x004d:
        r0 = move-exception;
        monitor-exit(r12);	 Catch:{ all -> 0x004d }
        throw r0;	 Catch:{ Exception -> 0x0050 }
    L_0x0050:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x0045;
    L_0x0055:
        r1 = r12.i;	 Catch:{ all -> 0x004d }
        if (r1 == 0) goto L_0x0065;
    L_0x0059:
        r1 = r12.i;	 Catch:{ all -> 0x004d }
        r1.updateTexImage();	 Catch:{ all -> 0x004d }
        r1 = r12.i;	 Catch:{ all -> 0x004d }
        r2 = r12.n;	 Catch:{ all -> 0x004d }
        r1.getTransformMatrix(r2);	 Catch:{ all -> 0x004d }
    L_0x0065:
        r1 = 0;
        r12.x = r1;	 Catch:{ all -> 0x004d }
        monitor-exit(r12);	 Catch:{ all -> 0x004d }
        r1 = r12.b;	 Catch:{ Exception -> 0x0050 }
        if (r1 == 0) goto L_0x0079;
    L_0x006d:
        r1 = r12.b;	 Catch:{ Exception -> 0x0050 }
        r2 = r12.m;	 Catch:{ Exception -> 0x0050 }
        r3 = 0;
        r2 = r2[r3];	 Catch:{ Exception -> 0x0050 }
        r3 = r12.n;	 Catch:{ Exception -> 0x0050 }
        r1.onTextureProcess(r2, r3);	 Catch:{ Exception -> 0x0050 }
    L_0x0079:
        r12.f();	 Catch:{ Exception -> 0x0050 }
        monitor-enter(r12);	 Catch:{ Exception -> 0x0050 }
        r1 = r12.e;	 Catch:{ all -> 0x008c }
        if (r1 != 0) goto L_0x0082;
    L_0x0081:
        r0 = 1;
    L_0x0082:
        monitor-exit(r12);	 Catch:{ all -> 0x008c }
        if (r0 == 0) goto L_0x0045;
    L_0x0085:
        r0 = r12.d();	 Catch:{ Exception -> 0x0050 }
        r12.v = r0;	 Catch:{ Exception -> 0x0050 }
        goto L_0x0045;
    L_0x008c:
        r0 = move-exception;
        monitor-exit(r12);	 Catch:{ all -> 0x008c }
        throw r0;	 Catch:{ Exception -> 0x0050 }
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.renderer.b.onDrawFrame(javax.microedition.khronos.opengles.GL10):void");
    }

    private void f() {
        if (this.C) {
            if (!(this.A == 0 || this.B == 0)) {
                int i;
                boolean z = getWidth() <= getHeight();
                int i2 = this.B >= this.A ? this.B : this.A;
                int i3 = this.B >= this.A ? this.A : this.B;
                if (z) {
                    i = i3;
                    i3 = i2;
                } else {
                    i = i2;
                }
                final Buffer allocate = ByteBuffer.allocate((i * i3) * 4);
                final Bitmap createBitmap = Bitmap.createBitmap(i, i3, Config.ARGB_8888);
                allocate.position(0);
                GLES20.glReadPixels(this.F, this.G, i, i3, 6408, 5121, allocate);
                final int i4 = i;
                final int i5 = i3;
                new Thread(new Runnable() {
                    public void run() {
                        allocate.position(0);
                        createBitmap.copyPixelsFromBuffer(allocate);
                        Matrix matrix = new Matrix();
                        matrix.setScale(1.0f, -1.0f);
                        Bitmap createBitmap = Bitmap.createBitmap(createBitmap, 0, 0, i4, i5, matrix, false);
                        if (b.this.D != null) {
                            b.this.D.a(createBitmap);
                            b.this.D = null;
                        }
                        createBitmap.recycle();
                    }
                }).start();
            }
            this.C = false;
        }
    }

    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        if (!this.l) {
            com.tencent.liteav.basic.util.a.a(this.c, (int) TXLiveConstants.PUSH_EVT_FIRST_FRAME_AVAILABLE, "首帧画面采集完成");
            this.l = true;
        }
        this.w = false;
        synchronized (this) {
            this.x = true;
        }
    }

    public void a(boolean z) {
        this.w = true;
        if (z) {
            GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            GLES20.glClear(16384);
            this.v = d();
        }
        synchronized (this) {
            if (this.x) {
                this.x = false;
                if (this.i != null) {
                    this.i.updateTexImage();
                }
            }
        }
    }

    public void b(final boolean z) {
        synchronized (this.y) {
            a(new Runnable() {
                public void run() {
                    synchronized (b.this.y) {
                        b.this.a(z);
                        b.this.y.notifyAll();
                    }
                }
            });
            try {
                this.y.wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void g() {
        try {
            Thread.sleep(15);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
