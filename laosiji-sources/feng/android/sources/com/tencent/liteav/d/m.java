package com.tencent.liteav.d;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.c.e;
import com.tencent.liteav.f.a.i;
import com.tencent.liteav.j.g;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: PicDec */
public class m {
    private int A;
    private int B = -1;
    private final String a = "PicDec";
    private int b = 1;
    private Handler c;
    private HandlerThread d = new HandlerThread("picDec_handler_thread");
    private List<Bitmap> e;
    private i f;
    private int g = 20;
    private long h;
    private List<Long> i;
    private long j = 1000;
    private long k = 500;
    private long l;
    private long m;
    private boolean n;
    private long o;
    private long p;
    private long q = -1;
    private long r = -1;
    private long s = -1;
    private long t = -1;
    private e u;
    private AtomicBoolean v;
    private AtomicBoolean w;
    private long x;
    private int y = 0;
    private int z;

    /* compiled from: PicDec */
    class a extends Handler {
        public a(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    m.this.o();
                    m.this.c(m.this.o);
                    m.this.c.sendEmptyMessage(2);
                    return;
                case 2:
                    m.this.m();
                    return;
                case 3:
                    m.this.k();
                    return;
                case 4:
                    TXCLog.i("PicDec", "stopDecode");
                    m.this.l();
                    return;
                case 5:
                    m.this.b(m.this.x);
                    return;
                default:
                    return;
            }
        }
    }

    public m() {
        this.d.start();
        this.c = new a(this.d.getLooper());
        this.e = new ArrayList();
        this.i = new ArrayList();
        this.v = new AtomicBoolean(false);
        this.w = new AtomicBoolean(false);
    }

    public void a(boolean z) {
        this.n = z;
    }

    public void a(List<Bitmap> list, int i) {
        if (list == null || list.size() == 0) {
            TXCLog.e("PicDec", "setBitmapList, bitmapList is empty");
            return;
        }
        if (i <= 0) {
            this.g = 20;
        } else {
            this.g = i;
        }
        a((List) list);
        this.h = (long) (1000 / this.g);
    }

    public long a(int i) {
        synchronized (this) {
            if (this.B != i) {
                this.i.clear();
            }
        }
        this.B = i;
        this.j = g.b(i);
        this.k = g.c(i);
        if (i == 5 || i == 4) {
            this.l = ((long) this.e.size()) * (this.j + this.k);
        } else {
            this.l = (((long) this.e.size()) * (this.j + this.k)) - this.k;
        }
        this.m = ((long) (((int) ((this.l / 1000) * ((long) this.g))) - 1)) * this.h;
        return this.m;
    }

    public void a(i iVar) {
        this.f = iVar;
    }

    private void j() {
        this.q = -1;
        this.r = -1;
        this.s = -1;
        this.t = -1;
        this.v.compareAndSet(true, false);
    }

    public void a(long j, long j2) {
        this.o = j;
        this.p = j2;
    }

    public int a() {
        if (this.e.size() == 0) {
            return 0;
        }
        this.z = 720;
        return this.z;
    }

    public int b() {
        if (this.e.size() == 0) {
            return 0;
        }
        this.A = 1280;
        return this.A;
    }

    public boolean c() {
        return this.w.get();
    }

    public synchronized void d() {
        if (this.b == 2) {
            TXCLog.e("PicDec", "start(), mState is play, ignore");
        } else {
            this.b = 2;
            this.w.compareAndSet(true, false);
            j();
            this.c.sendEmptyMessage(1);
        }
    }

    public synchronized void e() {
        if (this.b == 1) {
            TXCLog.e("PicDec", "stop(), mState is init, ignore");
        } else {
            this.b = 1;
            this.c.sendEmptyMessage(4);
        }
    }

    public void f() {
        if (this.b == 1 || this.b == 3) {
            TXCLog.e("PicDec", "pause(), mState = " + this.b + ", ignore");
            return;
        }
        this.b = 3;
        this.c.sendEmptyMessage(3);
    }

    public void g() {
        if (this.b == 1 || this.b == 2) {
            TXCLog.e("PicDec", "resume(), mState = " + this.b + ", ignore");
            return;
        }
        this.b = 2;
        this.c.sendEmptyMessage(2);
    }

    public void a(long j) {
        this.b = 4;
        this.x = j;
        this.c.sendEmptyMessage(5);
    }

    public synchronized void h() {
        if (this.b == 1) {
            TXCLog.e("PicDec", "getNextBitmapFrame, current state is init, ignore");
        } else {
            this.c.sendEmptyMessage(2);
        }
    }

    private void k() {
        this.c.removeMessages(2);
        j();
    }

    private void l() {
        this.c.removeMessages(2);
        j();
    }

    /* JADX WARNING: Missing block: B:23:0x0061, code:
            if (r0 != -1) goto L_0x0097;
     */
    /* JADX WARNING: Missing block: B:24:0x0063, code:
            r12.u.c(4);
            r12.u.a(0);
            r12.u.j(a());
            r12.u.k(b());
            r12.u.m(0);
            b(r12.u);
            r12.c.sendEmptyMessage(4);
            r12.w.set(true);
     */
    /* JADX WARNING: Missing block: B:29:0x0097, code:
            monitor-enter(r12);
     */
    /* JADX WARNING: Missing block: B:31:?, code:
            r12.y++;
     */
    /* JADX WARNING: Missing block: B:32:0x009e, code:
            monitor-exit(r12);
     */
    /* JADX WARNING: Missing block: B:33:0x009f, code:
            r12.s = r0 / 1000;
            r12.u.a(r0);
            r12.u.b(r0);
            r12.u.f(r12.g);
            r12.u.m(0);
            a(r12.u);
     */
    /* JADX WARNING: Missing block: B:34:0x00c2, code:
            if (r12.n != false) goto L_0x00ce;
     */
    /* JADX WARNING: Missing block: B:35:0x00c4, code:
            b(r12.u);
     */
    /* JADX WARNING: Missing block: B:41:0x00d2, code:
            if (r12.q >= 0) goto L_0x00ec;
     */
    /* JADX WARNING: Missing block: B:42:0x00d4, code:
            r12.q = r12.s;
            r12.v.set(true);
            r12.s = r0;
            r12.r = java.lang.System.currentTimeMillis();
            r12.c.sendEmptyMessage(2);
     */
    /* JADX WARNING: Missing block: B:43:0x00ec, code:
            r12.v.compareAndSet(true, false);
            r12.c.sendEmptyMessageDelayed(2, 5);
     */
    /* JADX WARNING: Missing block: B:52:?, code:
            return;
     */
    /* JADX WARNING: Missing block: B:53:?, code:
            return;
     */
    /* JADX WARNING: Missing block: B:54:?, code:
            return;
     */
    /* JADX WARNING: Missing block: B:55:?, code:
            return;
     */
    private void m() {
        /*
        r12 = this;
        r9 = 4;
        r10 = 0;
        r8 = 2;
        r7 = 1;
        r6 = 0;
        r0 = r12.n;
        if (r0 == 0) goto L_0x001d;
    L_0x000a:
        r0 = r12.s;
        r0 = (r0 > r10 ? 1 : (r0 == r10 ? 0 : -1));
        if (r0 < 0) goto L_0x001d;
    L_0x0010:
        r0 = r12.v;
        r0 = r0.get();
        if (r0 == 0) goto L_0x002f;
    L_0x0018:
        r0 = r12.u;
        r12.b(r0);
    L_0x001d:
        r0 = new com.tencent.liteav.c.e;
        r0.<init>();
        r12.u = r0;
        monitor-enter(r12);
        r0 = r12.i;	 Catch:{ all -> 0x0094 }
        r0 = r0.size();	 Catch:{ all -> 0x0094 }
        if (r0 > 0) goto L_0x0043;
    L_0x002d:
        monitor-exit(r12);	 Catch:{ all -> 0x0094 }
    L_0x002e:
        return;
    L_0x002f:
        r0 = r12.n();
        if (r0 != 0) goto L_0x003d;
    L_0x0035:
        r0 = r12.c;
        r2 = 5;
        r0.sendEmptyMessageDelayed(r8, r2);
        goto L_0x002e;
    L_0x003d:
        r0 = r12.u;
        r12.b(r0);
        goto L_0x001d;
    L_0x0043:
        r0 = r12.i;	 Catch:{ all -> 0x0094 }
        r1 = r12.y;	 Catch:{ all -> 0x0094 }
        r0 = r0.get(r1);	 Catch:{ all -> 0x0094 }
        r0 = (java.lang.Long) r0;	 Catch:{ all -> 0x0094 }
        r0 = r0.longValue();	 Catch:{ all -> 0x0094 }
        r2 = r12.p;	 Catch:{ all -> 0x0094 }
        r4 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r2 = r2 * r4;
        r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        if (r2 <= 0) goto L_0x005c;
    L_0x005a:
        r0 = -1;
    L_0x005c:
        monitor-exit(r12);	 Catch:{ all -> 0x0094 }
        r2 = -1;
        r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        if (r2 != 0) goto L_0x0097;
    L_0x0063:
        r0 = r12.u;
        r0.c(r9);
        r0 = r12.u;
        r0.a(r10);
        r0 = r12.u;
        r1 = r12.a();
        r0.j(r1);
        r0 = r12.u;
        r1 = r12.b();
        r0.k(r1);
        r0 = r12.u;
        r0.m(r6);
        r0 = r12.u;
        r12.b(r0);
        r0 = r12.c;
        r0.sendEmptyMessage(r9);
        r0 = r12.w;
        r0.set(r7);
        goto L_0x002e;
    L_0x0094:
        r0 = move-exception;
        monitor-exit(r12);	 Catch:{ all -> 0x0094 }
        throw r0;
    L_0x0097:
        monitor-enter(r12);
        r2 = r12.y;	 Catch:{ all -> 0x00cb }
        r2 = r2 + 1;
        r12.y = r2;	 Catch:{ all -> 0x00cb }
        monitor-exit(r12);	 Catch:{ all -> 0x00cb }
        r2 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r2 = r0 / r2;
        r12.s = r2;
        r2 = r12.u;
        r2.a(r0);
        r2 = r12.u;
        r2.b(r0);
        r2 = r12.u;
        r3 = r12.g;
        r2.f(r3);
        r2 = r12.u;
        r2.m(r6);
        r2 = r12.u;
        r12.a(r2);
        r2 = r12.n;
        if (r2 != 0) goto L_0x00ce;
    L_0x00c4:
        r0 = r12.u;
        r12.b(r0);
        goto L_0x002e;
    L_0x00cb:
        r0 = move-exception;
        monitor-exit(r12);	 Catch:{ all -> 0x00cb }
        throw r0;
    L_0x00ce:
        r2 = r12.q;
        r2 = (r2 > r10 ? 1 : (r2 == r10 ? 0 : -1));
        if (r2 >= 0) goto L_0x00ec;
    L_0x00d4:
        r2 = r12.s;
        r12.q = r2;
        r2 = r12.v;
        r2.set(r7);
        r12.s = r0;
        r0 = java.lang.System.currentTimeMillis();
        r12.r = r0;
        r0 = r12.c;
        r0.sendEmptyMessage(r8);
        goto L_0x002e;
    L_0x00ec:
        r0 = r12.v;
        r0.compareAndSet(r7, r6);
        r0 = r12.c;
        r2 = 5;
        r0.sendEmptyMessageDelayed(r8, r2);
        goto L_0x002e;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.d.m.m():void");
    }

    private void b(long j) {
        c(j);
        this.u = new e();
        synchronized (this) {
            if (this.i.size() <= 0) {
                return;
            }
            long longValue = ((Long) this.i.get(this.y)).longValue();
            this.u.a(longValue);
            this.u.m(0);
            a(this.u);
            b(this.u);
        }
    }

    private void a(e eVar) {
        Object obj;
        long e = eVar.e() / 1000;
        int i = (int) (e / (this.j + this.k));
        TXCLog.i("PicDec", "setBitmapsAndDisplayRatio, frameTimeMs = " + e + ", picIndex = " + i + ", loopTime = " + (this.j + this.k));
        if (i >= this.e.size()) {
            obj = (Bitmap) this.e.get(this.e.size() - 1);
        } else {
            Bitmap obj2 = (Bitmap) this.e.get(i);
        }
        Object obj3 = null;
        if (i < this.e.size() - 1) {
            obj3 = (Bitmap) this.e.get(i + 1);
        }
        List arrayList = new ArrayList();
        arrayList.add(obj2);
        if (obj3 != null) {
            arrayList.add(obj3);
        }
        eVar.a(arrayList);
        eVar.j(a());
        eVar.k(b());
    }

    private boolean n() {
        this.t = System.currentTimeMillis();
        this.s = this.u.e() / 1000;
        if (Math.abs(this.s - this.q) < this.t - this.r) {
            return true;
        }
        return false;
    }

    private void b(e eVar) {
        if (this.f != null) {
            this.f.a(eVar);
        }
    }

    private synchronized void c(long j) {
        if (this.i.size() > 0) {
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 >= this.i.size()) {
                    break;
                } else if (((Long) this.i.get(i2)).longValue() / 1000 >= j) {
                    this.y = i2;
                    break;
                } else {
                    i = i2 + 1;
                }
            }
        }
    }

    private void a(List<Bitmap> list) {
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < list.size()) {
                this.e.add(a((Bitmap) list.get(i2), 720, 1280));
                i = i2 + 1;
            } else {
                return;
            }
        }
    }

    public static Bitmap a(Bitmap bitmap, int i, int i2) {
        float f;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (((float) width) / ((float) height) >= ((float) i) / ((float) i2)) {
            f = ((float) i) / ((float) width);
        } else {
            f = ((float) i2) / ((float) height);
        }
        Matrix matrix = new Matrix();
        matrix.postScale(f, f);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    private synchronized void o() {
        if (this.i.size() <= 0) {
            int i = (int) ((this.l / 1000) * ((long) this.g));
            for (int i2 = 0; i2 < i; i2++) {
                if (i2 == i - 1) {
                    this.i.add(Long.valueOf((((long) i2) * this.h) * 1000));
                    this.i.add(Long.valueOf(-1));
                } else {
                    this.i.add(Long.valueOf((((long) i2) * this.h) * 1000));
                }
            }
        }
    }

    public void i() {
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= this.e.size()) {
                break;
            }
            ((Bitmap) this.e.get(i2)).recycle();
            i = i2 + 1;
        }
        this.e.clear();
        if (this.d != null) {
            this.d.quit();
        }
    }
}
