package com.tencent.liteav.d;

import android.annotation.TargetApi;
import android.media.MediaFormat;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.LongSparseArray;
import com.tencent.liteav.b.g;
import com.tencent.liteav.b.i;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.c.e;
import com.umeng.message.proguard.l;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@TargetApi(16)
/* compiled from: VideoDecAndDemuxPreview */
public class u extends c {
    private AtomicBoolean A;
    private AtomicBoolean B;
    private AtomicBoolean C;
    private AtomicBoolean D;
    private AtomicInteger E;
    private e F;
    private e G;
    private volatile boolean H;
    private long I;
    private int J;
    private long K;
    private long L;
    private int M;
    private boolean N;
    private long O;
    private int P;
    private long Q;
    private long R;
    private long S;
    private long T;
    private long U;
    private AtomicBoolean V;
    private e W;
    private long X;
    private AtomicBoolean Y;
    private long Z;
    private AtomicBoolean aa;
    private final String j;
    private final int k;
    private final int l;
    private final int m;
    private final int n;
    private final int o;
    private final int p;
    private final int q;
    private final int r;
    private final int s;
    private final int t;
    private LongSparseArray<e> u;
    private LongSparseArray<e> v;
    private b w;
    private HandlerThread x;
    private a y;
    private HandlerThread z;

    /* compiled from: VideoDecAndDemuxPreview */
    class a extends Handler {
        public a(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case 101:
                    u.this.b();
                    u.this.y.sendEmptyMessage(102);
                    return;
                case 102:
                    TXCLog.i("VideoDecAndDemuxPreview", "avsync audio frame start AudioDecodeHandler, mCurrentState = " + u.this.E + ", mAudioDecodeEOF = " + u.this.D);
                    while (u.this.E.get() != 1 && !u.this.D.get()) {
                        try {
                            if (u.this.E.get() == 3) {
                                u.this.F = null;
                                u.this.X = -1;
                                Thread.sleep(10);
                            } else if (u.this.H) {
                                u.this.v();
                                u.this.w();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            if (u.this.E.get() == 1) {
                            }
                        }
                    }
                    u.this.F = null;
                    u.this.X = -1;
                    if (u.this.E.get() == 1) {
                        TXCLog.d("VideoDecAndDemuxPreview", "AudioDecodeHandler, loop decode end state is init, ignore to stop");
                        return;
                    }
                    TXCLog.i("VideoDecAndDemuxPreview", "AudioDecodeHandler, in MSG_AUDIO_DECODE_START, send MSG_AUDIO_DECODE_STOP");
                    u.this.y.sendEmptyMessage(103);
                    return;
                case 103:
                    TXCLog.i("VideoDecAndDemuxPreview", "AudioDecodeHandler, audio decode stop!");
                    u.this.y.removeMessages(102);
                    if (u.this.c != null) {
                        u.this.c.b();
                        u.this.c = null;
                        return;
                    }
                    return;
                case 104:
                    u.this.F = null;
                    u.this.X = -1;
                    u.this.y.removeMessages(102);
                    return;
                default:
                    return;
            }
        }
    }

    /* compiled from: VideoDecAndDemuxPreview */
    class b extends Handler {
        public b(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            e g;
            switch (message.what) {
                case 1:
                    TXCLog.i("VideoDecAndDemuxPreview", "normal : configureVideo()");
                    u.this.a();
                    if (g.a().b()) {
                        u.this.a.a(u.this.g.get());
                        TXCLog.i("VideoDecAndDemuxPreview", "VideoDecodeHandler, reverse, seekVideo time = " + u.this.g);
                    }
                    u.this.w.sendEmptyMessage(2);
                    return;
                case 2:
                    try {
                        if (u.this.T >= 0) {
                            if (u.this.W.p()) {
                                TXCLog.i("VideoDecAndDemuxPreview", "is end video frame, to stop decode");
                                u.this.c(u.this.W);
                                u.this.w.sendEmptyMessage(3);
                                return;
                            } else if (u.this.V.get()) {
                                u.this.c(u.this.W);
                            } else if (u.this.t()) {
                                u.this.c(u.this.W);
                            } else {
                                u.this.w.sendEmptyMessageDelayed(2, 5);
                                return;
                            }
                        }
                        u.this.u();
                        g = u.this.s();
                        if (g == null) {
                            u.this.w.sendEmptyMessage(2);
                            return;
                        }
                        u.this.W = u.this.b(g);
                        if (u.this.R < 0) {
                            u.this.R = u.this.T;
                            if (u.this.X > 0) {
                                u.this.S = u.this.X;
                            } else {
                                u.this.S = System.currentTimeMillis();
                            }
                            TXCLog.d("VideoDecAndDemuxPreview", "avsync first video frame ts : " + u.this.R + ", first systime : " + u.this.S + ", current systime " + System.currentTimeMillis());
                            u.this.V.set(true);
                            u.this.w.sendEmptyMessage(2);
                            return;
                        }
                        u.this.V.compareAndSet(true, false);
                        u.this.w.sendEmptyMessageDelayed(2, 5);
                        return;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
                case 3:
                    TXCLog.i("VideoDecAndDemuxPreview", "VideoDecodeHandler, video decode stop!");
                    u.this.w.removeMessages(2);
                    synchronized (u.this) {
                        u.this.r();
                    }
                    if (u.this.b != null) {
                        u.this.b.b();
                        u.this.b = null;
                        return;
                    }
                    return;
                case 4:
                    TXCLog.i("VideoDecAndDemuxPreview", "video decode pause");
                    u.this.w.removeMessages(2);
                    synchronized (u.this) {
                        u.this.r();
                    }
                    return;
                case 5:
                    try {
                        u.this.a.a(u.this.Z);
                        g = u.this.b.c();
                        if (g != null) {
                            u.this.b.a(u.this.a.a(g));
                            g = u.this.b.d();
                            if (g == null) {
                                TXCLog.e("VideoDecAndDemuxPreview", "VideoDecodeHandler, preview at time, frame is null");
                                return;
                            }
                            g.j(u.this.d());
                            g.k(u.this.e());
                            g.e(u.this.p());
                            if (g.a().b() && g.e() <= u.this.f.get()) {
                                u.this.b.b(g);
                            }
                            u.this.c(g);
                            return;
                        }
                        return;
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        return;
                    }
                case 6:
                    TXCLog.i("VideoDecAndDemuxPreview", "preview at time : configureVideo()");
                    u.this.a();
                    u.this.a.a(u.this.Z);
                    u.this.w.sendEmptyMessage(5);
                    return;
                default:
                    return;
            }
        }
    }

    public u() {
        this.j = "VideoDecAndDemuxPreview";
        this.k = 1;
        this.l = 2;
        this.m = 3;
        this.n = 4;
        this.o = 5;
        this.p = 6;
        this.q = 101;
        this.r = 102;
        this.s = 103;
        this.t = 104;
        this.H = true;
        this.I = -1;
        this.K = 0;
        this.L = 0;
        this.N = false;
        this.Q = -1;
        this.R = -1;
        this.S = -1;
        this.T = -1;
        this.U = -1;
        this.X = -1;
        this.v = new LongSparseArray();
        this.u = new LongSparseArray();
        this.f = new AtomicLong(0);
        this.g = new AtomicLong(0);
        this.E = new AtomicInteger(1);
        this.A = new AtomicBoolean(false);
        this.B = new AtomicBoolean(false);
        this.aa = new AtomicBoolean(false);
        this.C = new AtomicBoolean(false);
        this.D = new AtomicBoolean(false);
        this.V = new AtomicBoolean(false);
        this.Y = new AtomicBoolean(false);
        this.x = new HandlerThread("video_handler_thread");
        this.x.start();
        this.w = new b(this.x.getLooper());
        this.z = new HandlerThread("audio_handler_thread");
        this.z.start();
        this.y = new a(this.z.getLooper());
    }

    public synchronized void l() {
        TXCLog.i("VideoDecAndDemuxPreview", "start(), mCurrentState = " + this.E);
        if (this.E.get() == 2) {
            TXCLog.e("VideoDecAndDemuxPreview", "start ignore, mCurrentState = " + this.E.get());
        } else {
            this.u.clear();
            this.V.set(false);
            this.aa.set(true);
            this.Y.getAndSet(false);
            this.A.getAndSet(false);
            this.B.getAndSet(false);
            this.C.getAndSet(false);
            this.D.getAndSet(false);
            this.W = null;
            this.H = true;
            this.Q = -1;
            if (this.E.get() == 3) {
                TXCLog.i("VideoDecAndDemuxPreview", "start(), state pause, seek then send MSG_VIDEO_DECODE_START and MSG_AUDIO_DECODE_START");
                this.E.set(2);
                b(this.f.get());
                this.w.sendEmptyMessage(2);
                if (h()) {
                    this.y.sendEmptyMessage(102);
                }
            } else if (this.E.get() == 4) {
                TXCLog.i("VideoDecAndDemuxPreview", "start(), state preview at time, stop then start");
                m();
                l();
            } else {
                TXCLog.i("VideoDecAndDemuxPreview", "start(), state init, seek then send MSG_VIDEO_DECODE_CONFIG and MSG_AUDIO_DECODE_CONFIG");
                this.E.set(2);
                b(this.f.get());
                this.w.sendEmptyMessage(1);
                if (h()) {
                    this.y.sendEmptyMessage(101);
                }
            }
        }
    }

    public void m() {
        if (this.E.get() == 1) {
            TXCLog.e("VideoDecAndDemuxPreview", "stop(), mCurrentState in stop, ignore");
            return;
        }
        this.E.set(1);
        TXCLog.i("VideoDecAndDemuxPreview", "stop(), send MSG_VIDEO_DECODE_STOP");
        this.w.sendEmptyMessage(3);
        if (h()) {
            TXCLog.i("VideoDecAndDemuxPreview", "stop(), send MSG_AUDIO_DECODE_STOP");
            this.y.sendEmptyMessage(103);
        }
    }

    public synchronized void n() {
        int i = this.E.get();
        if (i == 3 || i == 1) {
            TXCLog.e("VideoDecAndDemuxPreview", "pause ignore, current state = " + i);
        } else {
            this.E.set(3);
            TXCLog.i("VideoDecAndDemuxPreview", "pause(), send MSG_VIDEO_DECODE_PAUSE");
            this.w.sendEmptyMessage(4);
            if (h()) {
                TXCLog.i("VideoDecAndDemuxPreview", "pause(), send MSG_AUDIO_DECODE_PAUSE");
                this.y.sendEmptyMessage(104);
            }
        }
    }

    public synchronized void o() {
        int i = this.E.get();
        if (i == 1 || i == 2 || i == 4) {
            TXCLog.e("VideoDecAndDemuxPreview", "resume ignore, state = " + i);
        } else {
            this.E.set(2);
            TXCLog.i("VideoDecAndDemuxPreview", "resume(), send MSG_VIDEO_DECODE_START");
            this.w.sendEmptyMessage(2);
            if (h()) {
                TXCLog.i("VideoDecAndDemuxPreview", "resume(), send MSG_AUDIO_DECODE_START");
                this.y.sendEmptyMessage(102);
            }
        }
    }

    public void a(long j) {
        this.Z = 1000 * j;
        if (this.E.get() == 3 || this.E.get() == 4) {
            TXCLog.i("VideoDecAndDemuxPreview", "previewAtTime, state = " + this.E.get() + ", send MSG_VIDEO_DECODE_PREVIEW_START");
            this.E.set(4);
            this.w.sendEmptyMessage(5);
            return;
        }
        TXCLog.i("VideoDecAndDemuxPreview", "previewAtTime, state = " + this.E.get() + ", send MSG_VIDEO_DECODE_PREVIEW_CONFIG");
        this.E.set(4);
        synchronized (this) {
            r();
        }
        this.w.sendEmptyMessage(6);
    }

    public void a(boolean z) {
        this.H = z;
    }

    public synchronized void a(long j, long j2) {
        this.f.getAndSet(j);
        this.g.getAndSet(j2);
        r();
    }

    private void r() {
        this.W = null;
        this.G = null;
        this.T = -1;
        this.U = -1;
        this.R = -1;
        this.S = -1;
        this.V.set(false);
        TXCLog.d("VideoDecAndDemuxPreview", "avsync video frame reset first systime " + this.S);
        b(this.K, this.L);
    }

    public void b(long j, long j2) {
        if (j == 0 && j2 == 0) {
            this.M = 0;
        } else {
            this.M = 3;
        }
        this.K = j;
        this.L = j2;
        this.N = false;
    }

    public int p() {
        return this.a.e();
    }

    @TargetApi(18)
    public void k() {
        if (this.x != null) {
            this.x.quitSafely();
        }
        if (this.z != null) {
            this.z.quitSafely();
        }
        if (this.u != null) {
            this.u.clear();
        }
        if (this.v != null) {
            this.v.clear();
        }
        this.F = null;
        this.G = null;
    }

    private e b(e eVar) {
        if (!com.tencent.liteav.e.g.a().c()) {
            eVar.b(eVar.e());
        } else if (this.G == null) {
            TXCLog.i("VideoDecAndDemuxPreview", "processSpeedFrame, mLastVideoFrame is null, this is first frame, not to speed");
        } else if (eVar.p()) {
            TXCLog.i("VideoDecAndDemuxPreview", "processSpeedFrame, this frame is end frame, not to speed");
        } else {
            float a = com.tencent.liteav.e.g.a().a(eVar.e());
            long e = eVar.e() - this.G.e();
            long t = ((long) (((float) e) / a)) + this.G.t();
            eVar.b(t);
            this.T = t / 1000;
        }
        return eVar;
    }

    private synchronized void b(long j) {
        if (this.Y.get()) {
            TXCLog.e("VideoDecAndDemuxPreview", "seekPosition, had seeked");
        } else {
            TXCLog.d("VideoDecAndDemuxPreview", "======================准备开始定位video和audio起点=====================开始时间mStartTime = " + this.f);
            this.a.a(j);
            long n = this.a.n();
            this.a.c(n);
            long o = this.a.o();
            TXCLog.d("VideoDecAndDemuxPreview", "======================定位结束=====================");
            TXCLog.d("VideoDecAndDemuxPreview", "==============seekTime==========" + this.f);
            TXCLog.d("VideoDecAndDemuxPreview", "==============startVdts==========" + n);
            TXCLog.d("VideoDecAndDemuxPreview", "==============startAdts==========" + o);
            this.Y.getAndSet(true);
        }
    }

    private void c(e eVar) {
        if (this.e != null) {
            this.e.b(eVar);
        }
        this.G = eVar;
    }

    @Nullable
    private e s() {
        e d = this.b.d();
        if (d == null) {
            return null;
        }
        if (d.o() == null) {
            return null;
        }
        e eVar = (e) this.u.get(d.e());
        if (eVar != null) {
            d = this.b.a(eVar, d);
            if (g.a().b()) {
                d.a(eVar.v());
                eVar = d;
            }
            eVar = d;
        } else {
            d.j(d());
            d.k(e());
            eVar = d;
        }
        if (eVar.e() >= this.f.get() || eVar.p()) {
            if (eVar.e() > this.g.get()) {
                TXCLog.d("VideoDecAndDemuxPreview", "VideoFrame pts :" + eVar.e() + " after  duration (" + this.g + l.t);
                if (g.a().b()) {
                    return null;
                }
                eVar = this.b.b(eVar);
            }
            if (eVar.p()) {
                this.C.getAndSet(true);
                TXCLog.d("VideoDecAndDemuxPreview", "==================generate decode Video END==========================");
                if (this.D.get()) {
                    TXCLog.d("VideoDecAndDemuxPreview", "================== VIDEO SEND END OF FILE ==========================" + eVar.toString());
                } else {
                    TXCLog.d("VideoDecAndDemuxPreview", "-------------- generate Audio NOT END ----------------");
                    return eVar;
                }
            }
            this.W = eVar;
            this.T = this.W.e() / 1000;
            return eVar;
        }
        TXCLog.d("VideoDecAndDemuxPreview", "VideoFrame pts :" + eVar.e() + " before  startTime (" + this.f + l.t);
        return null;
    }

    private boolean t() {
        this.U = System.currentTimeMillis();
        this.T = this.W.t() / 1000;
        if (Math.abs(this.T - this.R) < this.U - this.S) {
            return true;
        }
        return false;
    }

    private synchronized void u() throws InterruptedException {
        if (this.A.get()) {
            TXCLog.e("VideoDecAndDemuxPreview", "readVideoFrame, read video end of file, ignore");
        } else {
            e c = this.b.c();
            if (c != null) {
                boolean a;
                if ((this.M == 3 || this.M == 2) && this.f.get() <= this.K && this.a.p() >= this.L) {
                    this.a.a(this.K);
                    this.M--;
                    this.N = true;
                }
                e a2 = this.a.a(c);
                if (this.J <= 0) {
                    this.J = j();
                    if (this.J != 0) {
                        this.P = (1000 / this.J) * 1000;
                    }
                }
                if (this.N) {
                    a2.a(this.O + ((long) this.P));
                }
                this.O = a2.e();
                if (this.Q < 0) {
                    this.Q = this.O;
                }
                if (g.a().b()) {
                    if (a2.p()) {
                        this.O = a(a2);
                        this.Q = this.O;
                    }
                    a = a(this.O, (long) this.P, a2);
                    if (!a) {
                        long abs = Math.abs(this.Q - this.O);
                        TXCLog.i("VideoDecAndDemuxPreview", "reverse newVPts = " + abs + ", mFirstVideoReadPTS = " + this.Q + ", curFixFrame.getSampleTime() = " + this.O);
                        a2.a(abs);
                        a2.c(abs);
                        a2.d(this.O);
                    }
                } else {
                    a = this.a.c(a2);
                }
                if (a) {
                    this.A.set(true);
                    TXCLog.d("VideoDecAndDemuxPreview", "read video end");
                }
                this.u.put(a2.e(), a2);
                this.b.a(a2);
            }
        }
    }

    private synchronized void v() {
        if (!this.B.get()) {
            e c = this.c.c();
            if (c != null) {
                c = this.a.b(c);
                if (this.a.d(c)) {
                    this.B.set(true);
                    TXCLog.d("VideoDecAndDemuxPreview", "audio endOfFile:" + this.B.get());
                    TXCLog.d("VideoDecAndDemuxPreview", "read audio end");
                }
                this.v.put(c.e(), c);
                this.c.a(c);
            }
        }
    }

    private synchronized void w() {
        e d = this.c.d();
        if (d != null) {
            if (this.c.e() && this.aa.get()) {
                MediaFormat f = f();
                f.setInteger("sample-rate", d.j());
                i.a().a(f);
                this.aa.set(false);
            }
            if (d.o() != null) {
                e eVar = (e) this.v.get(d.e());
                if (eVar != null) {
                    eVar = this.c.a(eVar, d);
                } else {
                    eVar = d;
                }
                if (eVar != null) {
                    if (eVar.e() >= this.f.get() || eVar.p()) {
                        if (eVar.e() > this.g.get()) {
                            TXCLog.d("VideoDecAndDemuxPreview", "AudioFrame pts :" + eVar.e() + " after  duration (" + this.g + l.t);
                            eVar = this.c.b(eVar);
                        }
                        if (eVar.p()) {
                            this.D.set(true);
                            TXCLog.d("VideoDecAndDemuxPreview", "==================generate decode Audio END==========================");
                            if (this.C.get()) {
                                TXCLog.d("VideoDecAndDemuxPreview", "================== AUDIO SEND END OF FILE ==========================" + eVar.toString());
                            } else {
                                TXCLog.d("VideoDecAndDemuxPreview", "-------------- generate VIDEO NOT END ----------------");
                            }
                        }
                        if (this.F == null) {
                            this.F = d;
                            this.X = System.currentTimeMillis();
                            TXCLog.d("VideoDecAndDemuxPreview", "avsync first audio frame ts : " + this.F.e() + ", first systime : " + this.X);
                        }
                        if (this.I == -1) {
                            this.I = System.currentTimeMillis();
                        }
                        if (this.e != null) {
                            this.e.a(eVar);
                        }
                        this.F = eVar;
                        this.I = System.currentTimeMillis();
                    } else {
                        TXCLog.d("VideoDecAndDemuxPreview", "AudioFrame pts :" + eVar.e() + " before  startTime (" + this.f + l.t);
                    }
                }
            }
        }
    }

    public boolean q() {
        return this.C.get();
    }
}
