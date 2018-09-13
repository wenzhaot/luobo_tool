package com.tencent.liteav.d;

import android.annotation.TargetApi;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.LongSparseArray;
import com.tencent.liteav.b.g;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.c.e;
import com.tencent.liteav.f.c;
import com.umeng.message.proguard.l;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@TargetApi(16)
/* compiled from: BasicVideoDecDemuxGenerater */
public abstract class d extends c {
    protected AtomicBoolean A;
    protected AtomicInteger B;
    protected b C;
    protected HandlerThread D;
    protected a E;
    protected HandlerThread F;
    protected volatile boolean G;
    protected e H;
    protected e I;
    protected int J;
    protected long K;
    protected long L;
    protected int M;
    protected boolean N;
    protected long O;
    protected int P;
    protected long Q;
    protected long R;
    protected long S;
    protected AtomicBoolean T;
    protected AtomicBoolean U;
    protected List<Long> V;
    protected int W;
    protected int X;
    protected AtomicBoolean Y;
    private final String Z;
    private long aa;
    private c ab;
    private long ac;
    private long ad;
    protected final int j;
    protected final int k;
    protected final int l;
    protected final int m;
    protected final int n;
    protected final int o;
    protected final int p;
    protected final int q;
    protected final int r;
    protected final int s;
    protected final int t;
    protected final int u;
    protected LongSparseArray<e> v;
    protected LongSparseArray<e> w;
    protected AtomicBoolean x;
    protected AtomicBoolean y;
    protected AtomicBoolean z;

    /* compiled from: BasicVideoDecDemuxGenerater */
    class a extends Handler {
        public a(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case 101:
                    d.this.b();
                    d.this.E.sendEmptyMessage(102);
                    return;
                case 102:
                    break;
                case 103:
                    TXCLog.i("BasicVideoDecDemuxGenerater", "AudioDecodeHandler, audio decode stop!");
                    d.this.E.removeMessages(102);
                    if (d.this.c != null) {
                        d.this.c.b();
                        d.this.c = null;
                        return;
                    }
                    return;
                default:
                    return;
            }
            while (d.this.B.get() != 1 && !d.this.A.get()) {
                if (d.this.G) {
                    d.this.t();
                    e i = d.this.u();
                    if (i != null) {
                        i = d.this.b(i);
                        if (i.p()) {
                            TXCLog.i("BasicVideoDecDemuxGenerater", "is end audio frame, to stop decode, mVideoDecodeEOF = " + d.this.z);
                            if (d.this.z.get()) {
                                d.this.d(i);
                            }
                            d.this.E.sendEmptyMessage(103);
                            return;
                        }
                        d.this.d(i);
                    } else {
                        continue;
                    }
                }
            }
        }
    }

    /* compiled from: BasicVideoDecDemuxGenerater */
    protected class b extends Handler {
        public b(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            e b;
            d dVar;
            switch (message.what) {
                case 1:
                    d.this.a();
                    if (g.a().b()) {
                        d.this.a.a(d.this.g.get());
                        TXCLog.i("BasicVideoDecDemuxGenerater", "VideoDecodeHandler, reverse, seekVideo time = " + d.this.g);
                    }
                    d.this.C.sendEmptyMessage(2);
                    return;
                case 2:
                    try {
                        if (d.this.b != null) {
                            d.this.s();
                            b = d.this.r();
                            if (b == null) {
                                d.this.C.sendEmptyMessage(2);
                                return;
                            }
                            b = d.this.e(b);
                            if (b.p()) {
                                TXCLog.i("BasicVideoDecDemuxGenerater", "is end video frame, to stop decode, mAudioDecodeEOF = " + d.this.A);
                                if (!d.this.h()) {
                                    d.this.c(b);
                                } else if (d.this.A.get()) {
                                    d.this.c(b);
                                }
                                d.this.C.sendEmptyMessage(3);
                                return;
                            }
                            d.this.c(b);
                            return;
                        }
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                case 3:
                    TXCLog.i("BasicVideoDecDemuxGenerater", "VideoDecodeHandler, video decode stop!");
                    d.this.C.removeMessages(2);
                    d.this.I = null;
                    d.this.R = -1;
                    d.this.T.set(false);
                    if (d.this.b != null) {
                        d.this.b.b();
                        d.this.b = null;
                        return;
                    }
                    return;
                case 5:
                    if (d.this.V != null && d.this.V.size() != 0) {
                        d.this.a();
                        d.this.W = 0;
                        d.this.X = 0;
                        d.this.C.sendEmptyMessage(6);
                        return;
                    }
                    return;
                case 6:
                    if (d.this.X < d.this.V.size()) {
                        if (d.this.aa < 0) {
                            if (d.this.W < d.this.V.size()) {
                                d.this.aa = ((Long) d.this.V.get(d.this.W)).longValue();
                                d.this.a.a(d.this.aa);
                                TXCLog.i("BasicVideoDecDemuxGenerater", "VideoDecodeHandler, get pts = " + d.this.aa + ", mVideoGivenPtsInputIndex = " + d.this.W);
                            } else {
                                d.this.aa = 0;
                            }
                        }
                        if (d.this.aa >= 0) {
                            b = d.this.b.c();
                            if (b != null) {
                                if (d.this.W < d.this.V.size()) {
                                    d dVar2 = d.this;
                                    dVar2.W++;
                                    d.this.b.a(d.this.a.a(b));
                                    TXCLog.i("BasicVideoDecDemuxGenerater", "VideoDecodeHandler, freeFrame exist");
                                } else {
                                    TXCLog.i("BasicVideoDecDemuxGenerater", "VideoDecodeHandler, isReadGivenTimeEnd, set end flag");
                                    b.a(0);
                                    b.c(4);
                                    d.this.b.a(b);
                                }
                                d.this.aa = -1;
                            }
                        }
                        if (d.this.Y.get()) {
                            b = d.this.b.d();
                            if (b == null) {
                                d.this.C.sendEmptyMessageDelayed(6, 5);
                                return;
                            }
                            b.j(d.this.d());
                            b.k(d.this.e());
                            d.this.c(b);
                            dVar = d.this;
                            dVar.X++;
                            d.this.Y.set(false);
                            if (d.this.X >= d.this.V.size()) {
                                TXCLog.i("BasicVideoDecDemuxGenerater", "VideoDecodeHandler, decode end");
                                d.this.C.sendEmptyMessage(7);
                                return;
                            }
                            d.this.C.sendEmptyMessageDelayed(6, 5);
                            return;
                        }
                        d.this.C.sendEmptyMessageDelayed(6, 5);
                        return;
                    }
                    return;
                case 7:
                    d.this.C.removeMessages(6);
                    if (d.this.b != null) {
                        d.this.b.b();
                        d.this.b = null;
                    }
                    d.this.B.set(1);
                    return;
                case 8:
                    if (d.this.V != null && d.this.V.size() != 0) {
                        d.this.q();
                        d.this.a();
                        d.this.W = 0;
                        d.this.X = 0;
                        sendEmptyMessage(9);
                        return;
                    }
                    return;
                case 9:
                    if (d.this.X < d.this.V.size() && d.this.W < d.this.V.size()) {
                        d.this.aa = ((Long) d.this.V.get(d.this.W)).longValue();
                        if (d.this.ac >= d.this.aa) {
                            TXCLog.i("BasicVideoDecDemuxGenerater", "seek lastSyncTime:" + d.this.ad + ",index:" + d.this.W);
                            d.this.a.b(d.this.ad);
                            sendEmptyMessage(10);
                            d.this.ac = d.this.ad;
                            return;
                        }
                        d.this.ad = d.this.ac;
                        d.this.ab.b(d.this.ad + 1);
                        d.this.ac = d.this.ab.n();
                        TXCLog.i("BasicVideoDecDemuxGenerater", "nextSyncTime:" + d.this.ac + ",lastSyncTime" + d.this.ad + ",mGivenPts:" + d.this.aa);
                        if (d.this.ac == -1 || d.this.ac == d.this.ad) {
                            d.this.ac = d.this.ad;
                            TXCLog.i("BasicVideoDecDemuxGenerater", "seek lastSyncTime:" + d.this.ad + ",index:" + d.this.W);
                            sendEmptyMessage(10);
                            return;
                        }
                        sendEmptyMessage(9);
                        return;
                    }
                    return;
                case 10:
                    try {
                        if (d.this.b != null) {
                            d.this.s();
                            b = d.this.r();
                            if (b == null) {
                                sendEmptyMessageDelayed(10, 5);
                                return;
                            }
                            b.j(d.this.d());
                            b.k(d.this.e());
                            if (b.p()) {
                                d.this.c(b);
                                sendEmptyMessage(3);
                                return;
                            } else if (b.e() >= d.this.aa) {
                                d.this.c(b);
                                dVar = d.this;
                                dVar.W++;
                                sendEmptyMessage(9);
                                return;
                            } else {
                                sendEmptyMessage(10);
                                return;
                            }
                        }
                        return;
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        return;
                    }
                default:
                    return;
            }
        }
    }

    public abstract void a(boolean z);

    protected abstract void l();

    protected abstract void m();

    public d() {
        this.Z = "BasicVideoDecDemuxGenerater";
        this.j = 1;
        this.k = 2;
        this.l = 3;
        this.m = 5;
        this.n = 6;
        this.o = 7;
        this.p = 8;
        this.q = 9;
        this.r = 10;
        this.s = 101;
        this.t = 102;
        this.u = 103;
        this.G = true;
        this.K = 0;
        this.L = 0;
        this.N = false;
        this.Q = -1;
        this.R = -1;
        this.S = -1;
        this.aa = -1;
        this.w = new LongSparseArray();
        this.v = new LongSparseArray();
        this.f = new AtomicLong(0);
        this.g = new AtomicLong(0);
        this.B = new AtomicInteger(1);
        this.x = new AtomicBoolean(false);
        this.y = new AtomicBoolean(false);
        this.z = new AtomicBoolean(false);
        this.A = new AtomicBoolean(false);
        this.T = new AtomicBoolean(false);
        this.U = new AtomicBoolean(false);
    }

    public int n() {
        return this.a.e();
    }

    private void q() {
        this.ab = new c();
        try {
            this.ab.a(this.i);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private e b(e eVar) {
        if (g.a().b()) {
            if (this.S < 0) {
                this.S = eVar.e();
            }
            this.Q = eVar.e();
            long j = this.Q - this.S;
            TXCLog.i("BasicVideoDecDemuxGenerater", "processReverseAudioFrame newVPts = " + j + ", mFirstAudioFramePTS = " + this.S + ", curAudioFrame pts = " + this.Q);
            eVar.a(j);
        }
        return eVar;
    }

    private void c(e eVar) {
        if (this.e != null) {
            this.e.b(eVar);
        }
        this.I = eVar;
    }

    private void d(e eVar) {
        if (this.e != null) {
            this.e.a(eVar);
        }
    }

    private e e(e eVar) {
        if (this.I == null) {
            TXCLog.i("BasicVideoDecDemuxGenerater", "processSpeedFrame, mLastVideoFrame is null");
        } else if (!eVar.p() && com.tencent.liteav.e.g.a().c()) {
            float a = com.tencent.liteav.e.g.a().a(eVar.e());
            long e = eVar.e() - this.I.e();
            eVar.b(((long) (((float) e) / a)) + this.I.t());
        }
        return eVar;
    }

    protected synchronized void a(long j) {
        if (this.U.get()) {
            TXCLog.e("BasicVideoDecDemuxGenerater", "seekPosition, had seeked");
        } else {
            TXCLog.d("BasicVideoDecDemuxGenerater", "======================准备开始定位video和audio起点=====================开始时间mStartTime = " + this.f);
            this.a.a(j);
            long n = this.a.n();
            this.a.c(n);
            long o = this.a.o();
            TXCLog.d("BasicVideoDecDemuxGenerater", "==============startVdts==========" + n);
            TXCLog.d("BasicVideoDecDemuxGenerater", "==============startAdts==========" + o);
            this.U.getAndSet(true);
        }
    }

    @Nullable
    private e r() {
        e d = this.b.d();
        if (d == null) {
            return null;
        }
        if (d.o() == null) {
            return null;
        }
        if (d.p()) {
            TXCLog.i("BasicVideoDecDemuxGenerater", "getDecodeVideoFrame, is end frame");
            d.j(d());
            d.k(e());
            this.z.getAndSet(true);
            return d;
        }
        long j = 0;
        e eVar = (e) this.v.get(d.e());
        if (eVar != null) {
            d = this.b.a(eVar, d);
            if (g.a().b()) {
                d.a(eVar.v());
                d.c(eVar.u());
                j = eVar.v();
                eVar = d;
            } else {
                j = d.e();
                eVar = d;
            }
        } else {
            eVar = d;
        }
        if (j < this.f.get()) {
            TXCLog.d("BasicVideoDecDemuxGenerater", "VideoFrame pts :" + j + " before  startTime (" + this.f + l.t);
            return null;
        }
        if (j > this.g.get()) {
            TXCLog.d("BasicVideoDecDemuxGenerater", "VideoFrame pts :" + j + " after  duration (" + this.g + l.t);
            if (g.a().b()) {
                return null;
            }
            eVar = this.b.b(eVar);
        }
        if (!eVar.p()) {
            return eVar;
        }
        this.z.getAndSet(true);
        TXCLog.d("BasicVideoDecDemuxGenerater", "==================generate decode Video END==========================");
        if (this.A.get()) {
            TXCLog.d("BasicVideoDecDemuxGenerater", "================== VIDEO SEND END OF FILE ==========================" + eVar.toString());
            return eVar;
        }
        TXCLog.d("BasicVideoDecDemuxGenerater", "-------------- generate Audio NOT END ----------------");
        return eVar;
    }

    private synchronized void s() {
        if (this.x.get()) {
            TXCLog.e("BasicVideoDecDemuxGenerater", "readVideoFrame, read video end of file, ignore");
        } else {
            e c = this.b.c();
            if (c != null) {
                boolean a;
                if ((this.M == 3 || this.M == 2) && this.a.p() >= this.L) {
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
                if (this.R < 0) {
                    this.R = this.O;
                }
                if (g.a().b()) {
                    if (a2.p()) {
                        this.O = a(a2);
                        this.R = this.O;
                    }
                    a = a(this.O, (long) this.P, a2);
                    if (!a) {
                        long abs = Math.abs(this.R - this.O);
                        TXCLog.i("BasicVideoDecDemuxGenerater", "reverse newVPts = " + abs + ", mFirstVideoFramePTS = " + this.R + ", curFixFrame.getSampleTime() = " + this.O);
                        a2.a(abs);
                        a2.c(abs);
                        a2.d(this.O);
                    }
                } else {
                    a = this.a.c(a2);
                }
                if (a) {
                    this.x.set(true);
                    TXCLog.d("BasicVideoDecDemuxGenerater", "read video end");
                }
                this.v.put(a2.e(), a2);
                this.b.a(a2);
            }
        }
    }

    private synchronized void t() {
        if (!this.y.get()) {
            e c = this.c.c();
            if (c != null) {
                c = this.a.b(c);
                if (this.a.d(c)) {
                    this.y.set(true);
                    TXCLog.d("BasicVideoDecDemuxGenerater", "audio endOfFile:" + this.y.get());
                    TXCLog.d("BasicVideoDecDemuxGenerater", "read audio end");
                }
                this.w.put(c.e(), c);
                this.c.a(c);
            }
        }
    }

    private synchronized e u() {
        e eVar;
        e d = this.c.d();
        if (d == null) {
            eVar = null;
        } else if (d.o() == null) {
            eVar = null;
        } else {
            eVar = (e) this.w.get(d.e());
            if (eVar != null) {
                eVar = this.c.a(eVar, d);
            } else {
                eVar = d;
            }
            if (eVar == null) {
                eVar = null;
            } else if (eVar.e() >= this.f.get() || eVar.p()) {
                if (eVar.e() > this.g.get()) {
                    TXCLog.d("BasicVideoDecDemuxGenerater", "AudioFrame pts :" + eVar.e() + " after  duration (" + this.g + l.t);
                    eVar = this.c.b(eVar);
                }
                if (eVar.p()) {
                    this.A.set(true);
                    TXCLog.d("BasicVideoDecDemuxGenerater", "==================generate decode Audio END==========================");
                    if (this.z.get()) {
                        TXCLog.d("BasicVideoDecDemuxGenerater", "================== AUDIO SEND END OF FILE ==========================" + eVar.toString());
                    } else {
                        TXCLog.d("BasicVideoDecDemuxGenerater", "-------------- generate VIDEO NOT END ----------------");
                    }
                }
                if (this.H == null) {
                    this.H = d;
                    TXCLog.d("BasicVideoDecDemuxGenerater", "first AUDIO pts:" + this.H.e());
                }
                this.H = eVar;
            } else {
                TXCLog.d("BasicVideoDecDemuxGenerater", "AudioFrame pts :" + eVar.e() + " before  startTime (" + this.f + l.t);
                eVar = null;
            }
        }
        return eVar;
    }

    public synchronized void a(long j, long j2) {
        this.f.getAndSet(j);
        this.g.getAndSet(j2);
    }

    public void b(long j, long j2) {
        if (j == 0 && j2 == 0) {
            this.M = 0;
            this.N = false;
        } else {
            this.M = 3;
        }
        this.K = j;
        this.L = j2;
        this.O = 0;
    }

    public void a(List<Long> list) {
        Log.e("thumbnail", "setVideoGivenPtsList :" + list.size());
        this.V.clear();
        this.V.addAll(list);
    }

    public boolean o() {
        return this.z.get();
    }

    public synchronized void p() {
        if (this.B.get() == 1) {
            TXCLog.e("BasicVideoDecDemuxGenerater", "getNextVideoFrame, current state is init, ignore");
        } else {
            this.C.sendEmptyMessage(2);
        }
    }

    public void b(boolean z) {
        this.G = z;
    }
}
