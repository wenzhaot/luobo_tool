package com.tencent.liteav.beauty;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import com.tencent.liteav.basic.c.d;
import com.tencent.liteav.basic.c.e;
import com.tencent.liteav.basic.c.f;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.tencent.liteav.beauty.b.ah;
import com.tencent.liteav.beauty.b.c;
import com.tencent.liteav.beauty.b.l;
import com.tencent.liteav.beauty.b.m;
import com.tencent.liteav.beauty.b.p;
import com.tencent.liteav.beauty.b.r;
import com.tencent.liteav.beauty.b.t;
import com.tencent.liteav.beauty.b.v;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/* compiled from: TXCFilterDrawer */
class b extends HandlerThread {
    private int A = 0;
    private int B = 0;
    private boolean C = false;
    private float[] D;
    private int E = 0;
    private int F = 0;
    private com.tencent.liteav.basic.c.a G = null;
    private Bitmap H = null;
    private p I = null;
    private v J = null;
    private com.tencent.liteav.beauty.b.b K = null;
    private com.tencent.liteav.beauty.b.a.a L = null;
    private com.tencent.liteav.beauty.b.b.a M = null;
    private c N = null;
    private Bitmap O;
    private Bitmap P;
    private float Q;
    private float R;
    private float S;
    private r T;
    private t U = null;
    private ah V = null;
    private m W = null;
    private l X = null;
    private d Y = null;
    private d Z = null;
    boolean a = false;
    private byte[] aA = null;
    private int aB = -1;
    private int aC = 0;
    private int aD = 1;
    private int aE = this.aB;
    private e aF = null;
    private WeakReference<com.tencent.liteav.basic.b.a> aG = new WeakReference(null);
    private com.tencent.liteav.basic.c.f.b aH = new com.tencent.liteav.basic.c.f.b() {
    };
    private e aa = null;
    private d ab = null;
    private final Queue<Runnable> ac = new LinkedList();
    private boolean ad;
    private Object ae = new Object();
    private Object af = new Object();
    private Handler ag;
    private a ah;
    private float ai = 0.5f;
    private int aj = 0;
    private int ak = 0;
    private int al = 0;
    private int am = 0;
    private int an = 0;
    private boolean ao = false;
    private com.tencent.liteav.beauty.a.a.c ap = null;
    private com.tencent.liteav.beauty.a.a.a aq = null;
    private Bitmap ar = null;
    private List<d.d> as = null;
    private long at = 0;
    private int au = 0;
    private final int av = 100;
    private final float aw = 1000.0f;
    private byte[] ax = null;
    private int[] ay = null;
    private boolean az = false;
    protected int[] b = null;
    protected int[] c = null;
    com.tencent.liteav.beauty.b.a d = new com.tencent.liteav.beauty.b.a();
    com.tencent.liteav.beauty.b.a e = new com.tencent.liteav.beauty.b.a();
    com.tencent.liteav.beauty.b.a f = new com.tencent.liteav.beauty.b.a();
    private int g = 0;
    private int h = 0;
    private int i = 0;
    private int j = 0;
    private int k = 0;
    private int l = 0;
    private Context m = null;
    private boolean n = true;
    private d.d o = null;
    private int p = -1;
    private int q = -1;
    private int r = -1;
    private int s = -1;
    private int t = -1;
    private int u = -1;
    private int v = -1;
    private int w = -1;
    private float x = 1.0f;
    private int y = -1;
    private int z = -1;

    /* compiled from: TXCFilterDrawer */
    private class a extends Handler {
        private String b = "EGLDrawThreadHandler";

        a(Looper looper, Context context) {
            super(looper);
        }

        private void a(Object obj) {
            TXCLog.i(this.b, "come into InitEGL");
            b bVar = (b) obj;
            a();
            b.this.aq = new com.tencent.liteav.beauty.a.a.a();
            b.this.ap = new com.tencent.liteav.beauty.a.a.c(b.this.aq, bVar.g, bVar.f, false);
            b.this.ap.b();
            if (b.this.c(bVar)) {
                TXCLog.i(this.b, "come out InitEGL");
            } else {
                TXCLog.e(this.b, "initInternal failed!");
            }
        }

        public void a() {
            TXCLog.i(this.b, "come into releaseEGL");
            if (b.this.ay != null && b.this.ay[0] > 0) {
                GLES20.glDeleteBuffers(1, b.this.ay, 0);
                b.this.ay = null;
            }
            b.this.b();
            if (b.this.ap != null) {
                b.this.ap.c();
                b.this.ap = null;
            }
            if (b.this.aq != null) {
                b.this.aq.a();
                b.this.aq = null;
            }
            b.this.ao = false;
            NativeLoad.getInstance();
            NativeLoad.nativeDeleteYuv2Yuv();
            TXCLog.i(this.b, "come out releaseEGL");
        }

        public void handleMessage(android.os.Message r10) {
            /*
            r9 = this;
            r7 = 1;
            super.handleMessage(r10);
            r8 = 0;
            r0 = r10.what;
            switch(r0) {
                case 0: goto L_0x0013;
                case 1: goto L_0x001f;
                case 2: goto L_0x002b;
                case 3: goto L_0x0038;
                case 4: goto L_0x0041;
                case 5: goto L_0x0066;
                case 6: goto L_0x000a;
                case 7: goto L_0x0071;
                default: goto L_0x000a;
            };
        L_0x000a:
            r0 = r8;
        L_0x000b:
            monitor-enter(r9);
            if (r7 != r0) goto L_0x0011;
        L_0x000e:
            r9.notify();	 Catch:{ all -> 0x009e }
        L_0x0011:
            monitor-exit(r9);	 Catch:{ all -> 0x009e }
            return;
        L_0x0013:
            r0 = r10.obj;
            r9.a(r0);
            r0 = com.tencent.liteav.beauty.b.this;
            r0.ao = r7;
            r0 = r7;
            goto L_0x000b;
        L_0x001f:
            r9.a();
            r0 = com.tencent.liteav.beauty.b.this;
            r0 = r0.d;
            r0.a();
            r0 = r8;
            goto L_0x000b;
        L_0x002b:
            r1 = com.tencent.liteav.beauty.b.this;
            r0 = r10.obj;
            r0 = (byte[]) r0;
            r0 = (byte[]) r0;
            r1.a(r0);
            r0 = r8;
            goto L_0x000b;
        L_0x0038:
            r0 = com.tencent.liteav.beauty.b.this;
            r1 = r10.arg1;
            r0.h(r1);
            r0 = r7;
            goto L_0x000b;
        L_0x0041:
            r0 = com.tencent.liteav.beauty.b.this;
            r1 = r10.arg1;
            r2 = (double) r1;
            r4 = 4636737291354636288; // 0x4059000000000000 float:0.0 double:100.0;
            r2 = r2 / r4;
            r1 = (float) r2;
            r0.ai = r1;
            r0 = com.tencent.liteav.beauty.b.this;
            r0 = r0.T;
            if (r0 == 0) goto L_0x000a;
        L_0x0055:
            r0 = com.tencent.liteav.beauty.b.this;
            r0 = r0.T;
            r1 = com.tencent.liteav.beauty.b.this;
            r1 = r1.ai;
            r0.a(r1);
            r0 = r8;
            goto L_0x000b;
        L_0x0066:
            r0 = r10.obj;
            r0 = (com.tencent.liteav.beauty.d.b) r0;
            r1 = com.tencent.liteav.beauty.b.this;
            r1.d(r0);
            r0 = r8;
            goto L_0x000b;
        L_0x0071:
            r0 = com.tencent.liteav.beauty.b.this;
            r1 = com.tencent.liteav.beauty.b.this;
            r1 = r1.t;
            r2 = com.tencent.liteav.beauty.b.this;
            r2 = r2.u;
            r3 = com.tencent.liteav.beauty.b.this;
            r3 = r3.A;
            r4 = r10.arg1;
            r5 = r10.arg2;
            r6 = r10.obj;
            r6 = (java.lang.Integer) r6;
            r6 = r6.intValue();
            r0.a(r1, r2, r3, r4, r5, r6);
            r0 = com.tencent.liteav.beauty.b.this;
            r0 = r0.f;
            r0.a();
            r0 = r8;
            goto L_0x000b;
        L_0x009e:
            r0 = move-exception;
            monitor-exit(r9);	 Catch:{ all -> 0x009e }
            throw r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.beauty.b.a.handleMessage(android.os.Message):void");
        }

        void b() {
            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    b(Context context, boolean z) {
        super("TXCFilterDrawer");
        this.m = context;
        this.ag = new Handler(this.m.getMainLooper());
        this.ad = z;
    }

    public synchronized boolean a(b bVar) {
        boolean z;
        z = true;
        if (bVar.j) {
            z = c(bVar);
        } else {
            if (this.ah == null) {
                start();
                this.ah = new a(getLooper(), this.m);
            }
            this.ah.obtainMessage(0, bVar).sendToTarget();
            this.ah.b();
        }
        return z;
    }

    public int a(int i, int i2) {
        int i3;
        a(this.ac);
        int i4 = this.x != 1.0f ? 1 : 0;
        GLES20.glViewport(0, 0, this.v, this.w);
        if (this.Z != null) {
            if (4 == i2) {
                this.Z.a(this.D);
            }
            i = this.Z.a(i);
        }
        if (this.K != null && (this.ak > 0 || this.al > 0 || this.an > 0)) {
            i = this.K.a(i);
        }
        if (this.T != null) {
            i = this.T.a(i);
        }
        GLES20.glViewport(0, 0, this.t, this.u);
        if (this.W != null) {
            i = this.W.a(i);
            i4 = 0;
        }
        if (this.X != null) {
            i4 = this.X.a(i);
            i3 = 0;
        } else {
            i3 = i4;
            i4 = i;
        }
        if (i3 != 0) {
            c(this.t, this.u);
            if (this.ab != null) {
                GLES20.glViewport(0, 0, this.t, this.u);
                i4 = this.ab.a(i4);
            }
        }
        if (this.aF != null) {
            i3 = this.aF.willAddWatermark(i4, this.t, this.u);
            if (i3 > 0) {
                i4 = i3;
            }
        }
        GLES20.glViewport(0, 0, this.t, this.u);
        if (this.V != null) {
            i4 = this.V.a(i4);
        }
        if (this.Y != null) {
            GLES20.glViewport(0, 0, this.y, this.z);
            i4 = this.Y.a(i4);
        }
        g(i4);
        return i4;
    }

    public void a(final float f) {
        this.ai = f;
        a(new Runnable() {
            public void run() {
                if (b.this.T != null) {
                    b.this.T.a(f);
                }
            }
        });
    }

    public void a(final float[] fArr) {
        a(new Runnable() {
            public void run() {
                b.this.D = fArr;
            }
        });
    }

    private void a(com.tencent.liteav.basic.c.a aVar, int i, int i2, int i3, int i4, boolean z, int i5, int i6) {
        int i7;
        if (this.Z == null) {
            TXCLog.i("TXCFilterDrawer", "Create CropFilter");
            if (4 == i6) {
                this.Z = new d("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nuniform mat4 textureTransform;\nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = (textureTransform * inputTextureCoordinate).xy;\n}", "#extension GL_OES_EGL_image_external : require\n\nvarying lowp vec2 textureCoordinate;\n \nuniform samplerExternalOES inputImageTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}", true);
            } else {
                this.Z = new d();
            }
            if (true == this.Z.c()) {
                this.Z.a(true);
            } else {
                TXCLog.e("TXCFilterDrawer", "mInputCropFilter init failed!");
            }
        }
        this.Z.a(i3, i4);
        float[] a = this.Z.a(this.p, this.q, null, aVar, i6);
        int i8 = (720 - i5) % 360;
        if (i8 == 90 || i8 == 270) {
            i7 = i4;
        } else {
            i7 = i3;
        }
        if (i8 == 90 || i8 == 270) {
            i4 = i3;
        }
        this.Z.a(i, i2, i8, a, ((float) i7) / ((float) i4), z, false);
    }

    private void a(int i, int i2, int i3, int i4, int i5, int i6) {
        synchronized (this.af) {
            int i7 = ((i6 - i3) + 360) % 360;
            TXCLog.i("TXCFilterDrawer", "real outputAngle " + i7);
            if (this.Y == null) {
                if (i == i4 && i2 == i5 && i7 == 0) {
                    TXCLog.i("TXCFilterDrawer", "Don't need change output Image, don't create out filter!");
                    return;
                }
                this.Y = new d();
                if (true == this.Y.c()) {
                    this.Y.a(true);
                } else {
                    TXCLog.e("TXCFilterDrawer", "mOutputZoomFilter init failed!");
                }
            }
            this.Y.a(i4, i5);
            this.Y.a((720 - i7) % 360, null);
        }
    }

    public void a(Bitmap bitmap, float f, float f2, float f3) {
        if (this.o == null) {
            this.o = new d.d();
        }
        if (this.o.a != null && bitmap != null && true == this.o.a.equals(bitmap) && f == this.o.b && f2 == this.o.c && f3 == this.o.d) {
            Log.d("TXCFilterDrawer", "Same Water Mark; don't set again");
            return;
        }
        this.o.a = bitmap;
        this.o.b = f;
        this.o.c = f2;
        this.o.d = f3;
        final Bitmap bitmap2 = bitmap;
        final float f4 = f;
        final float f5 = f2;
        final float f6 = f3;
        a(new Runnable() {
            public void run() {
                if (bitmap2 != null) {
                    a.a().e();
                }
                if (bitmap2 != null) {
                    if (b.this.V == null) {
                        if (b.this.t <= 0 || b.this.u <= 0) {
                            TXCLog.e("TXCFilterDrawer", "output Width and Height is error!");
                            return;
                        }
                        b.this.V = new ah();
                        b.this.V.a(true);
                        if (b.this.V.c()) {
                            b.this.V.a(b.this.t, b.this.u);
                        } else {
                            TXCLog.e("TXCFilterDrawer", "mWatermarkFilter.init failed!");
                            b.this.V.e();
                            b.this.V = null;
                            return;
                        }
                    }
                    b.this.V.d(true);
                    b.this.V.a(bitmap2, f4, f5, f6);
                } else if (b.this.V != null) {
                    b.this.V.e();
                    b.this.V = null;
                }
            }
        });
    }

    public void a(final List<d.d> list) {
        a(new Runnable() {
            public void run() {
                b.this.as = list;
                if ((list == null || list.size() == 0) && b.this.ar == null && b.this.V != null) {
                    b.this.V.e();
                    b.this.V = null;
                } else if (list != null && list.size() != 0) {
                    if (b.this.V == null) {
                        if (b.this.t <= 0 || b.this.u <= 0) {
                            Log.e("TXCFilterDrawer", "output Width and Height is error!");
                            return;
                        }
                        b.this.V = new ah();
                        b.this.V.a(true);
                        if (b.this.V.c()) {
                            b.this.V.a(b.this.t, b.this.u);
                        } else {
                            Log.e("TXCFilterDrawer", "mWatermarkFilter.init failed!");
                            b.this.V.e();
                            b.this.V = null;
                            return;
                        }
                    }
                    b.this.V.d(true);
                    b.this.V.a(list);
                }
            }
        });
    }

    void a(e eVar) {
        TXCLog.i("TXCFilterDrawer", "set listener");
        this.aF = eVar;
    }

    private int g(int i) {
        if (this.F == 0) {
            if (this.aF == null) {
                return i;
            }
            this.aF.didProcessFrame(i, this.y, this.z, TXCTimeUtil.getTimeTick());
            return i;
        } else if (1 == this.F || 3 == this.F || 2 == this.F) {
            GLES20.glViewport(0, 0, this.y, this.z);
            if (this.J == null) {
                TXCLog.e("TXCFilterDrawer", "mRGBA2I420Filter is null!");
                return i;
            }
            GLES20.glBindFramebuffer(36160, this.b[0]);
            this.J.b(i);
            if (2 == this.F) {
                b(this.y, this.z);
            } else {
                b(this.y, (this.z * 3) / 8);
            }
            GLES20.glBindFramebuffer(36160, 0);
            return i;
        } else {
            TXCLog.e("TXCFilterDrawer", "Don't support format!");
            return -1;
        }
    }

    private int b(int i, int i2) {
        ByteBuffer byteBuffer = null;
        if (true == this.ad) {
            if (this.aF != null) {
                NativeLoad.getInstance();
                NativeLoad.nativeGlReadPixs(i, i2, this.ax);
                this.aF.didProcessFrame(this.ax, this.y, this.z, this.F, TXCTimeUtil.getTimeTick());
            } else if (this.aA != null) {
                NativeLoad.getInstance();
                NativeLoad.nativeGlReadPixs(i, i2, this.aA);
            }
        } else if (3 == f.a()) {
            if (0 == this.at) {
                this.at = TXCTimeUtil.getTimeTick();
            }
            int i3 = this.au + 1;
            this.au = i3;
            if (i3 >= 100) {
                TXCLog.i("TXCFilterDrawer", "Real fps " + (100.0f / (((float) (TXCTimeUtil.getTimeTick() - this.at)) / 1000.0f)));
                this.au = 0;
                this.at = TXCTimeUtil.getTimeTick();
            }
            GLES30.glPixelStorei(3333, 1);
            if (VERSION.SDK_INT >= 18) {
                GLES30.glReadBuffer(1029);
            }
            GLES30.glBindBuffer(35051, this.ay[0]);
            NativeLoad.getInstance();
            NativeLoad.nativeGlReadPixs(i, i2, null);
            if (VERSION.SDK_INT >= 18) {
                byteBuffer = (ByteBuffer) GLES30.glMapBufferRange(35051, 0, (i * i2) * 4, 1);
                if (byteBuffer == null) {
                    TXCLog.e("TXCFilterDrawer", "glMapBufferRange is null");
                    return -1;
                }
            }
            NativeLoad.getInstance();
            NativeLoad.nativeGlMapBufferToQueue(i, i2, byteBuffer);
            if (VERSION.SDK_INT >= 18) {
                GLES30.glUnmapBuffer(35051);
            }
            GLES30.glBindBuffer(35051, 0);
        } else {
            NativeLoad.getInstance();
            NativeLoad.nativeGlReadPixsToQueue(i, i2);
        }
        return 0;
    }

    public void a(final int i) {
        a(new Runnable() {
            public void run() {
                b.this.F = i;
            }
        });
    }

    private int h(int i) {
        GLES20.glViewport(0, 0, this.p, this.q);
        return a(this.I.r(), i);
    }

    private void a(byte[] bArr) {
        if (this.I == null) {
            TXCLog.e("TXCFilterDrawer", "mI4202RGBAFilter is null!");
        } else {
            this.I.a(bArr);
        }
    }

    public void a() {
        if (this.ad) {
            b();
        } else if (this.ah != null) {
            this.ah.obtainMessage(1).sendToTarget();
            try {
                this.d.b();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void b() {
        TXCLog.i("TXCFilterDrawer", "come into releaseInternal");
        this.az = false;
        if (this.I != null) {
            this.I.e();
            this.I = null;
        }
        if (this.J != null) {
            this.J.e();
            this.J = null;
        }
        c();
        if (this.T != null) {
            this.T.e();
            this.T = null;
        }
        if (this.U != null) {
            this.U.a();
            this.U = null;
        }
        if (this.aa != null) {
            this.aa.e();
            this.aa = null;
        }
        if (this.Z != null) {
            this.Z.e();
            this.Z = null;
        }
        if (this.Y != null) {
            this.Y.e();
            this.Y = null;
        }
        if (this.V != null) {
            this.V.e();
            this.V = null;
        }
        if (this.W != null) {
            this.W.a();
            this.W = null;
        }
        if (this.X != null) {
            this.X.e();
            this.X = null;
        }
        if (this.ab != null) {
            this.ab.e();
            this.ab = null;
        }
        if (this.b != null) {
            GLES20.glDeleteFramebuffers(1, this.b, 0);
            this.b = null;
        }
        if (this.c != null) {
            GLES20.glDeleteTextures(1, this.c, 0);
            this.c = null;
        }
        this.o = null;
        TXCLog.i("TXCFilterDrawer", "come out releaseInternal");
    }

    private boolean c(b bVar) {
        TXCLog.i("TXCFilterDrawer", "come into initInternal");
        b();
        this.ad = bVar.j;
        this.p = bVar.d;
        this.q = bVar.e;
        this.G = bVar.m;
        this.r = bVar.g;
        this.s = bVar.f;
        this.A = bVar.h;
        this.C = bVar.i;
        this.y = bVar.b;
        this.z = bVar.c;
        this.B = bVar.a;
        this.t = bVar.g;
        this.u = bVar.f;
        if (this.A == 90 || this.A == 270) {
            this.t = bVar.f;
            this.u = bVar.g;
        }
        this.F = bVar.l;
        this.E = bVar.k;
        this.ax = new byte[((this.y * this.z) * 4)];
        TXCLog.i("TXCFilterDrawer", "processWidth mPituScaleRatio is " + this.x);
        if (this.x != 1.0f) {
            int i = this.t < this.u ? this.t : this.u;
            if (i > 368) {
                this.x = 432.0f / ((float) i);
            }
            if (this.x > 1.0f) {
                this.x = 1.0f;
            }
        }
        this.v = (int) (((float) this.t) * this.x);
        this.w = (int) (((float) this.u) * this.x);
        a(this.v, this.w, this.aj);
        if (!(this.o == null || this.o.a == null || this.V != null)) {
            TXCLog.i("TXCFilterDrawer", "reset water mark!");
            a(this.o.a, this.o.b, this.o.c, this.o.d);
        }
        if (!(this.O == null && this.P == null) && this.T == null) {
            a(this.v, this.w, this.Q, this.O, this.R, this.P, this.S);
        }
        a(this.G, this.r, this.s, this.v, this.w, this.C, this.A, this.E);
        a(this.t, this.u, this.A, this.y, this.z, this.B);
        if (this.b == null) {
            this.b = new int[1];
        } else {
            GLES20.glDeleteFramebuffers(1, this.b, 0);
        }
        if (this.c == null) {
            this.c = new int[1];
        } else {
            GLES20.glDeleteTextures(1, this.c, 0);
        }
        a(this.b, this.c, this.y, this.z);
        if (3 == f.a()) {
            if (this.ay == null) {
                this.ay = new int[1];
            } else {
                TXCLog.i("TXCFilterDrawer", "m_pbo0 is not null, delete Buffers, and recreate");
                GLES30.glDeleteBuffers(1, this.ay, 0);
            }
            TXCLog.i("TXCFilterDrawer", "opengl es 3.0, use PBO");
            f.a(this.r, this.s, this.ay);
        }
        TXCLog.i("TXCFilterDrawer", "come out initInternal");
        return true;
    }

    public boolean b(b bVar) {
        if (this.ad) {
            d(bVar);
        } else if (this.ah == null) {
            TXCLog.e("TXCFilterDrawer", "mThreadHandler is null!");
            return false;
        } else {
            this.ah.obtainMessage(5, 0, 0, bVar).sendToTarget();
        }
        return true;
    }

    private void a(int[] iArr, int[] iArr2, int i, int i2) {
        GLES20.glGenFramebuffers(1, iArr, 0);
        iArr2[0] = f.a(i, i2, 6408, 6408, iArr2);
        GLES20.glBindFramebuffer(36160, iArr[0]);
        GLES20.glFramebufferTexture2D(36160, 36064, 3553, iArr2[0], 0);
        GLES20.glBindFramebuffer(36160, 0);
    }

    private boolean d(b bVar) {
        if ((1 == bVar.k || 3 == bVar.k || 2 == bVar.k) && this.I == null) {
            this.I = new p(bVar.k);
            this.I.a(true);
            if (this.I.c()) {
                this.I.a(bVar.d, bVar.e);
            } else {
                TXCLog.e("TXCFilterDrawer", "mI4202RGBAFilter init failed!!, break init");
                return false;
            }
        }
        if ((1 == bVar.l || 3 == bVar.l || 2 == bVar.l) && this.J == null) {
            this.J = new v(bVar.l);
            if (this.J.c()) {
                this.J.a(bVar.b, bVar.c);
            } else {
                TXCLog.e("TXCFilterDrawer", "mRGBA2I420Filter init failed!!, break init");
                return false;
            }
        }
        return true;
    }

    public void b(final int i) {
        this.ak = i;
        a(new Runnable() {
            public void run() {
                if (i > 0) {
                    a.a().b();
                }
                if (b.this.K != null && i >= 0) {
                    b.this.K.c(i);
                }
            }
        });
    }

    public void c(final int i) {
        if (this.aj != i && i <= 2 && i >= 0) {
            this.aj = i;
            a(new Runnable() {
                public void run() {
                    b.this.a(b.this.v, b.this.w, i);
                }
            });
        }
    }

    public void d(final int i) {
        this.al = i;
        a(new Runnable() {
            public void run() {
                if (i > 0) {
                    a.a().c();
                }
                if (b.this.K != null && i >= 0) {
                    b.this.K.d(i);
                }
            }
        });
    }

    public void e(final int i) {
        this.am = i;
        a(new Runnable() {
            public void run() {
                if (i > 0) {
                    a.a().c();
                }
                if (b.this.K != null && i >= 0) {
                    b.this.K.f(i);
                }
            }
        });
    }

    public void f(final int i) {
        this.an = i;
        a(new Runnable() {
            public void run() {
                if (i > 0) {
                    a.a().c();
                }
                if (b.this.K != null && i >= 0) {
                    b.this.K.e(i);
                }
            }
        });
    }

    public void a(String str) {
    }

    public void a(boolean z) {
    }

    public void a(final float f, Bitmap bitmap, final float f2, Bitmap bitmap2, final float f3) {
        if (this.O != bitmap || this.P != bitmap2) {
            this.O = bitmap;
            this.P = bitmap2;
            this.Q = f;
            this.R = f2;
            this.S = f3;
            final float f4 = f;
            final Bitmap bitmap3 = bitmap;
            final float f5 = f2;
            final Bitmap bitmap4 = bitmap2;
            final float f6 = f3;
            a(new Runnable() {
                public void run() {
                    if (b.this.T != null) {
                        a.a().d();
                    }
                    if (b.this.O == null && b.this.P == null) {
                        if (b.this.T != null) {
                            b.this.T.e();
                            b.this.T = null;
                        }
                    } else if (b.this.T == null) {
                        b.this.a(b.this.v, b.this.w, b.this.Q, b.this.O, b.this.R, b.this.P, b.this.S);
                    } else {
                        b.this.T.a(f4, bitmap3, f5, bitmap4, f6);
                    }
                }
            });
        } else if (this.T == null) {
        } else {
            if (this.Q != f || this.R != f2 || this.S != f3) {
                this.Q = f;
                this.R = f2;
                this.S = f3;
                a(new Runnable() {
                    public void run() {
                        b.this.T.a(f, f2, f3);
                    }
                });
            }
        }
    }

    public void b(final float f) {
        a(new Runnable() {
            public void run() {
                if (f <= 0.0f) {
                    if (b.this.X != null) {
                        b.this.X.e();
                        b.this.X = null;
                        return;
                    }
                } else if (b.this.X == null) {
                    b.this.X = new l();
                    b.this.X.a(true);
                    if (b.this.X.c()) {
                        b.this.X.a(b.this.t, b.this.u);
                    } else {
                        TXCLog.e("TXCFilterDrawer", "Gaussian Filter init failed!");
                        return;
                    }
                }
                if (b.this.X != null) {
                    b.this.X.a(f / 4.0f);
                }
            }
        });
    }

    private void a(int i, int i2, int i3) {
        TXCLog.i("TXCFilterDrawer", "create Beauty Filter!");
        if (i3 == 0) {
            if (this.L == null) {
                this.L = new com.tencent.liteav.beauty.b.a.a();
            }
            this.K = this.L;
            Log.i("TXCFilterDrawer", "0 BeautyFilter");
        } else if (1 == i3) {
            if (this.M == null) {
                this.M = new com.tencent.liteav.beauty.b.b.a();
            }
            this.K = this.M;
            Log.i("TXCFilterDrawer", "1 BeautyFilter");
        } else if (2 == i3) {
            if (this.N == null) {
                this.N = new c();
            }
            this.K = this.N;
            Log.i("TXCFilterDrawer", "2 BeautyFilter");
        }
        if (this.K == null) {
            TXCLog.e("TXCFilterDrawer", "mBeautyFilter set error!");
            return;
        }
        this.K.a(true);
        if (true == this.K.c(i, i2)) {
            if (this.ak > 0) {
                this.K.c(this.ak);
            }
            if (this.al > 0) {
                this.K.d(this.al);
            }
            if (this.an > 0) {
                this.K.e(this.an);
            }
            if (this.am > 0) {
                this.K.f(this.am);
                return;
            }
            return;
        }
        TXCLog.e("TXCFilterDrawer", "mBeautyFilter init failed!");
    }

    private void c() {
        if (this.L != null) {
            this.L.e();
            this.L = null;
        }
        if (this.M != null) {
            this.M.e();
            this.M = null;
        }
        if (this.N != null) {
            this.N.e();
            this.N = null;
        }
        this.K = null;
    }

    private void a(int i, int i2, float f, Bitmap bitmap, float f2, Bitmap bitmap2, float f3) {
        if (this.T == null) {
            TXCLog.i("TXCFilterDrawer", "createComLooKupFilter");
            this.T = new r(f, bitmap, f2, bitmap2, f3);
            if (true == this.T.c()) {
                this.T.a(true);
                this.T.a(i, i2);
                return;
            }
            TXCLog.e("TXCFilterDrawer", "mLookupFilterGroup init failed!");
        }
    }

    private void c(int i, int i2) {
        if (this.ab == null) {
            TXCLog.i("TXCFilterDrawer", "createRecoverScaleFilter");
            this.ab = new d();
            if (true == this.ab.c()) {
                this.ab.a(true);
            } else {
                TXCLog.e("TXCFilterDrawer", "mRecoverScaleFilter init failed!");
            }
        }
        if (this.ab != null) {
            this.ab.a(i, i2);
        }
    }

    private void a(Runnable runnable) {
        synchronized (this.ac) {
            this.ac.add(runnable);
        }
    }

    private void a(Queue<Runnable> queue) {
        while (true) {
            Runnable runnable = null;
            synchronized (queue) {
                if (!queue.isEmpty()) {
                    runnable = (Runnable) queue.poll();
                }
            }
            if (runnable != null) {
                runnable.run();
            } else {
                return;
            }
        }
        while (true) {
        }
    }
}
