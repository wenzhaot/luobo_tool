package com.tencent.liteav.f;

import android.annotation.TargetApi;
import android.media.MediaFormat;
import android.util.LongSparseArray;
import android.view.Surface;
import com.tencent.ijk.media.player.IjkMediaMeta;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.c.e;
import com.tencent.liteav.f.a.b;
import com.tencent.liteav.f.a.f;
import com.tencent.liteav.f.a.g;
import com.tencent.liteav.f.a.h;
import com.tencent.liteav.i.c;
import com.tencent.ugc.TXRecordCommon;
import com.tencent.ugc.TXVideoEditConstants;
import com.umeng.message.proguard.l;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@TargetApi(16)
/* compiled from: VideoSourceReader */
public class q implements g {
    private boolean A;
    private long B = -1;
    private long C = -1;
    private int D;
    private String E;
    private String F;
    private AtomicBoolean G = new AtomicBoolean(false);
    private h H;
    private volatile boolean I = true;
    private volatile boolean J;
    private long K = -1;
    private long L = -1;
    private long M;
    private long N;
    private long O;
    private long P = 0;
    private long Q = 0;
    private int R;
    private boolean S = false;
    private long T;
    private boolean U;
    private final String a = q.class.getSimpleName();
    private int b;
    private LinkedList<e> c = new LinkedList();
    private LongSparseArray<e> d = new LongSparseArray();
    private LongSparseArray<e> e = new LongSparseArray();
    private c f;
    private m g;
    private e h;
    private Surface i;
    private b j;
    private a k;
    private AtomicLong l = new AtomicLong(0);
    private AtomicLong m = new AtomicLong(0);
    private AtomicBoolean n = new AtomicBoolean(false);
    private AtomicBoolean o = new AtomicBoolean(false);
    private AtomicBoolean p = new AtomicBoolean(false);
    private AtomicBoolean q = new AtomicBoolean(false);
    private AtomicInteger r = new AtomicInteger(0);
    private AtomicInteger s = new AtomicInteger(0);
    private e t;
    private e u;
    private LinkedHashMap<String, MediaFormat> v = new LinkedHashMap();
    private LinkedHashMap<String, MediaFormat> w = new LinkedHashMap();
    private c.b x;
    private f y;
    private a.e z;

    /* compiled from: VideoSourceReader */
    class a extends Thread {
        a() {
        }

        public void run() {
            setName("DecodeThread");
            try {
                q.this.m();
                q.this.q();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            q.this.p();
            if (q.this.p.get() && q.this.q.get()) {
                q.this.o();
            }
        }
    }

    public void a(h hVar) {
        this.H = hVar;
    }

    public void a(f fVar) {
        this.y = fVar;
    }

    public void a(a.e eVar) {
        this.z = eVar;
    }

    public synchronized void a(long j, long j2) {
        this.l.getAndSet(j);
        this.m.getAndSet(j2);
    }

    public MediaFormat a() {
        MediaFormat mediaFormat = null;
        if (this.w != null && this.w.size() > 0) {
            int i = 0;
            for (MediaFormat mediaFormat2 : this.w.values()) {
                MediaFormat mediaFormat22;
                if (mediaFormat22 != null) {
                    int integer;
                    int i2;
                    try {
                        integer = mediaFormat22.getInteger("sample-rate");
                    } catch (NullPointerException e) {
                        integer = TXRecordCommon.AUDIO_SAMPLERATE_48000;
                    }
                    if (integer > i) {
                        i2 = integer;
                    } else {
                        mediaFormat22 = mediaFormat;
                        i2 = i;
                    }
                    i = i2;
                    mediaFormat = mediaFormat22;
                }
            }
        }
        return mediaFormat;
    }

    public synchronized int a(String str) throws IOException {
        int i;
        this.f = new c();
        int a = this.f.a(str);
        if (a == TXVideoEditConstants.ERR_UNSUPPORT_LARGE_RESOLUTION || a == 0) {
            long j;
            if (!this.w.containsKey(str)) {
                this.w.put(str, this.f.k());
            }
            if (!this.v.containsKey(str)) {
                this.v.put(str, this.f.j());
            }
            if (this.E == null) {
                this.E = str;
            }
            this.F = str;
            long j2 = 0;
            long j3 = 0;
            for (String str2 : this.w.keySet()) {
                if (str2.equals(this.F)) {
                    break;
                }
                long j4;
                try {
                    j4 = ((MediaFormat) this.w.get(str2)).getLong("durationUs");
                } catch (NullPointerException e) {
                    TXCLog.d(this.a, "fixJoinDuration NullPointerException KEY_DURATION");
                    j4 = 0;
                }
                j3 += j4;
                try {
                    j = ((MediaFormat) this.v.get(str2)).getLong("durationUs");
                } catch (NullPointerException e2) {
                    TXCLog.d(this.a, "fixJoinDuration NullPointerException KEY_DURATION");
                    j = 0;
                }
                j2 += j;
            }
            if (j3 > j2) {
                j = j3;
            } else {
                j = j2;
            }
            TXCLog.d(this.a, "TotalSeekDuration:" + j);
            i = a;
        } else {
            i = a;
        }
        return i;
    }

    public int b() {
        MediaFormat j = this.f.j();
        if (j == null) {
            return 0;
        }
        try {
            return j.getInteger("frame-rate");
        } catch (NullPointerException e) {
            return 20;
        }
    }

    public boolean c() {
        if (this.f == null) {
            return false;
        }
        boolean z = true;
        for (MediaFormat mediaFormat : this.w.values()) {
            if (mediaFormat != null) {
                int integer;
                boolean z2;
                try {
                    integer = mediaFormat.getInteger("channel-count");
                } catch (NullPointerException e) {
                    integer = 0;
                }
                if (integer < 1 || integer > 2) {
                    z2 = false;
                } else {
                    z2 = true;
                }
                if (!z2) {
                    return z2;
                }
                z = z2;
            }
        }
        return z;
    }

    public int d() {
        if (this.v.size() <= 0) {
            return 0;
        }
        for (MediaFormat mediaFormat : this.v.values()) {
            if (mediaFormat != null) {
                int integer;
                try {
                    integer = mediaFormat.getInteger("frame-rate");
                } catch (NullPointerException e) {
                    integer = 20;
                }
                if (this.D == 0) {
                    this.D = integer;
                }
                if (integer < this.D) {
                    this.D = integer;
                }
            }
        }
        return this.D;
    }

    public synchronized void f() {
        int i = 0;
        synchronized (this) {
            long currentTimeMillis = System.currentTimeMillis();
            this.r.getAndSet(4);
            TXCLog.d(this.a, "================== CANCEL ======================" + this.r);
            while (true) {
                int i2 = i;
                if (i2 >= 3) {
                    break;
                } else if (this.k != null && this.k.isAlive()) {
                    try {
                        this.k.join(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    i = i2 + 1;
                }
            }
            if (this.H != null) {
                this.H.a();
            }
            long currentTimeMillis2 = System.currentTimeMillis();
            this.O = 0;
            this.M = 0;
            this.N = 0;
            this.K = -1;
            this.L = -1;
            this.S = false;
            TXCLog.d(this.a, "cancel :" + String.valueOf(currentTimeMillis2 - currentTimeMillis));
        }
    }

    public synchronized void g() {
        if (this.r.get() == 2) {
            this.r.getAndSet(3);
        }
        TXCLog.d(this.a, "pause current state : " + this.r);
    }

    public synchronized void h() {
        int i = this.r.get();
        if (i == 3) {
            this.r.getAndSet(2);
            TXCLog.d(this.a, "resume current state : " + this.r);
        } else if (i == 4) {
            TXCLog.d(this.a, "resume start");
            k();
        }
    }

    public synchronized void a(int i) {
        TXCLog.d(this.a, "======setCurrentType Render MODE currentType :" + i);
        this.s.getAndSet(i);
    }

    public synchronized void a(Surface surface) {
        this.i = surface;
    }

    public synchronized void a(boolean z) {
        TXCLog.d(this.a, "setLastFileFlag :" + z);
        this.A = z;
    }

    public int i() {
        return this.r.get();
    }

    public void b(boolean z) {
        this.J = z;
        if (this.g != null) {
            this.g.a(this.J);
        }
    }

    public void c(boolean z) {
        this.I = z;
    }

    public MediaFormat j() {
        MediaFormat j = this.f.j();
        if (j == null) {
            return null;
        }
        int e = this.f.e();
        int b = this.f.b();
        int c = this.f.c();
        if (e == 90 || e == 270) {
            b = this.f.c();
            c = this.f.b();
        }
        MediaFormat mediaFormat = new MediaFormat();
        mediaFormat.setInteger("rotation-degrees", e);
        mediaFormat.setInteger("width", b);
        mediaFormat.setInteger("height", c);
        if (j.containsKey("frame-rate")) {
            mediaFormat.setInteger("frame-rate", j.getInteger("frame-rate"));
        }
        if (j.containsKey("video-framerate")) {
            mediaFormat.setInteger("frame-rate", j.getInteger("video-framerate"));
        }
        if (j.containsKey("i-frame-interval")) {
            mediaFormat.setInteger("i-frame-interval", j.getInteger("i-frame-interval"));
        }
        if (j.containsKey(IjkMediaMeta.IJKM_KEY_BITRATE)) {
            mediaFormat.setInteger(IjkMediaMeta.IJKM_KEY_BITRATE, j.getInteger(IjkMediaMeta.IJKM_KEY_BITRATE));
        }
        return mediaFormat;
    }

    public void a(b bVar) {
        this.j = bVar;
    }

    public synchronized void k() {
        long currentTimeMillis = System.currentTimeMillis();
        switch (this.r.get()) {
            case 0:
            case 4:
                l();
                this.r.getAndSet(2);
                this.k = new a();
                this.k.start();
                break;
            case 3:
                this.r.getAndSet(2);
                break;
        }
        TXCLog.d(this.a, "start :" + String.valueOf(System.currentTimeMillis() - currentTimeMillis));
    }

    private void m() {
        this.S = false;
        this.O = this.N + 21333;
        this.n.getAndSet(false);
        this.o.getAndSet(false);
        this.p.getAndSet(false);
        this.q.getAndSet(false);
        this.c.clear();
        a(this.l.get());
        n();
    }

    private void n() {
        boolean z = true;
        if (this.Q != 0 || this.P != 0) {
            if (this.P <= this.l.get() || this.Q >= this.m.get()) {
                if (this.P >= this.l.get() || this.Q <= this.l.get() || this.Q >= this.m.get()) {
                    z = false;
                } else {
                    this.P = this.l.get();
                }
            }
            if (z) {
                this.R = 3;
                return;
            }
            this.R = 0;
            this.S = false;
        }
    }

    private void a(long j) {
        long n;
        long currentTimeMillis = System.currentTimeMillis();
        TXCLog.d(this.a, "======================准备开始定位video和audio起点=====================开始时间mStartTime = " + this.l);
        int i = 1;
        this.f.c(j);
        long o = this.f.o();
        while (true) {
            this.f.a(j);
            n = this.f.n();
            TXCLog.d(this.a, String.format("第%s定位video和audio时间 vdts = %s , adts = %s", new Object[]{Integer.valueOf(i), Long.valueOf(n), Long.valueOf(o)}));
            if (j != 0) {
                if (n <= j) {
                    TXCLog.d(this.a, "======================定位提前结束起点=====================");
                    break;
                }
                TXCLog.w(this.a, "seek time is larger than require. seekTime = " + n + ", require time = " + j);
                if (i == 3) {
                    TXCLog.d(this.a, "======================定位强制结束=====================");
                    break;
                }
                j -= 5000000;
                if (j < 0) {
                    j = 0;
                }
                i++;
            } else {
                break;
            }
        }
        TXCLog.d(this.a, "======================定位结束=====================");
        TXCLog.d(this.a, "==============seekTime==========" + this.l);
        TXCLog.d(this.a, "==============startVdts==========" + n);
        TXCLog.d(this.a, "==============startAdts==========" + o);
        TXCLog.d(this.a, "start :" + String.valueOf(System.currentTimeMillis() - currentTimeMillis));
    }

    public void l() {
        long currentTimeMillis = System.currentTimeMillis();
        this.g = new m();
        this.g.a(this.J);
        this.h = new e();
        MediaFormat k = this.f.k();
        this.h.a(k);
        this.h.a(k, null);
        this.h.a();
        this.g.a(this.f.j());
        this.g.a(this.f.j(), this.i);
        this.g.a();
        TXCLog.d(this.a, "==================Decoder start==========================");
        TXCLog.d(this.a, "start :" + String.valueOf(System.currentTimeMillis() - currentTimeMillis));
    }

    private void o() {
        TXCLog.d(this.a, "onCallback  mCurrentType : " + this.s.get());
        switch (this.s.get()) {
            case 0:
            case 1:
                if (this.x != null) {
                    this.x.a();
                }
                if (this.y != null) {
                    this.y.onPreviewCompletion();
                    return;
                }
                return;
            case 3:
                if (this.z != null) {
                    this.z.onJoinDecodeCompletion();
                    return;
                }
                return;
            default:
                return;
        }
    }

    private void p() {
        if (this.r.get() == 4) {
            long currentTimeMillis = System.currentTimeMillis();
            this.h.b();
            this.g.b();
            TXCLog.d(this.a, "onStop :" + String.valueOf(System.currentTimeMillis() - currentTimeMillis));
            this.c.clear();
        }
    }

    private void q() throws InterruptedException {
        TXCLog.d(this.a, "=================thread start===================");
        this.G.set(false);
        while (this.r.get() != 4) {
            if (this.r.get() == 3) {
                Thread.sleep(30);
            } else {
                if (this.G.get() && this.I) {
                    u();
                    r();
                }
                v();
                s();
            }
        }
        TXCLog.d(this.a, "=================thread exit===================");
    }

    private void r() {
        if (this.r.get() != 4 && this.r.get() != 3 && this.j != null) {
            e d = this.h.d();
            if (d != null && d.o() != null) {
                e eVar = (e) this.e.get(d.e());
                if (eVar != null) {
                    eVar = this.h.a(eVar, d);
                } else {
                    eVar = d;
                }
                if (eVar == null) {
                    return;
                }
                if (eVar.e() >= this.l.get() || (eVar.o().flags & 4) != 0) {
                    if (!this.U && eVar.e() > this.m.get()) {
                        TXCLog.d(this.a, "AudioFrame pts :" + eVar.e() + " after  duration (" + this.m + l.t);
                        eVar = this.h.b(eVar);
                    }
                    if ((eVar.o().flags & 4) != 0) {
                        this.q.getAndSet(true);
                        TXCLog.d(this.a, "==================generate decode Audio END==========================");
                        if (this.p.get()) {
                            this.r.getAndSet(4);
                            if (this.A) {
                                TXCLog.d(this.a, "================== AUDIO SEND END OF FILE ==========================" + eVar.toString());
                            } else {
                                TXCLog.d(this.a, "--------------AUDIO NOT LAST FILE ----------------");
                                return;
                            }
                        }
                        TXCLog.d(this.a, "-------------- generate VIDEO NOT END ----------------");
                        return;
                    }
                    if (this.t == null) {
                        this.t = d;
                        TXCLog.d(this.a, "first AUDIO pts:" + this.t.e());
                    }
                    if (this.C == -1) {
                        this.C = System.currentTimeMillis();
                    }
                    d = c(eVar);
                    this.N = d.e();
                    this.j.a(d);
                    this.t = d;
                    this.C = System.currentTimeMillis();
                    if (this.x == null) {
                        return;
                    }
                    if (eVar.f() != 4 || eVar.e() != 0) {
                        this.x.a((int) eVar.e());
                        return;
                    }
                    return;
                }
                TXCLog.d(this.a, "AudioFrame pts :" + eVar.e() + " before  startTime (" + this.l + l.t);
            }
        }
    }

    private void s() {
        if (this.j != null && !t() && this.c.size() > 0) {
            e eVar = (e) this.c.get(0);
            if (this.u == null) {
                this.u = eVar;
            }
            if (eVar.e() <= (this.t != null ? this.t.e() : -1) || this.q.get() || this.t == null) {
                a(eVar);
            }
        }
    }

    private void a(e eVar) {
        e b = b(eVar);
        long e = b.e();
        if (e <= this.M) {
            e = this.M + 1000;
            b.a(e);
        }
        this.M = e;
        this.j.b(b);
        if (!this.c.isEmpty() && this.c.size() > 0) {
            this.c.remove(0);
        }
        this.u = b;
        this.B = System.currentTimeMillis();
        if (this.x == null) {
            return;
        }
        if (b.f() != 4 || b.e() != 0) {
            this.x.a((int) b.e());
        }
    }

    private e b(e eVar) {
        if (this.s.get() == 3 && !this.F.equals(this.E) && this.v.containsKey(this.E)) {
            long e = eVar.e();
            if (this.K == -1) {
                this.K = e;
            }
            eVar.a((e - this.K) + this.O);
        }
        return eVar;
    }

    private e c(e eVar) {
        if (this.s.get() == 3 && !this.F.equals(this.E) && this.w.containsKey(this.E)) {
            long e = eVar.e();
            if (this.L == -1) {
                this.L = e;
            }
            eVar.a((e - this.L) + this.O);
        }
        return eVar;
    }

    private boolean t() {
        if (this.r.get() != 2) {
            return true;
        }
        if (this.c.size() == 0) {
            e d = this.g.d();
            if (d == null) {
                return true;
            }
            if (d.o() == null) {
                return true;
            }
            if (this.u == null) {
                this.u = d;
                TXCLog.d(this.a, "first VIDEO pts:" + this.u.e());
            }
            e eVar = (e) this.d.get(d.e());
            if (eVar != null) {
                eVar = this.g.a(eVar, d);
            } else {
                eVar = d;
            }
            if (eVar.e() >= this.l.get() || (eVar.o().flags & 4) != 0) {
                this.G.getAndSet(true);
                if (!this.U && eVar.e() > this.m.get()) {
                    TXCLog.d(this.a, "VideoFrame pts :" + eVar.e() + " after  duration (" + this.m + l.t);
                    eVar = this.g.b(eVar);
                }
                if ((eVar.o().flags & 4) != 0) {
                    this.p.getAndSet(true);
                    TXCLog.d(this.a, "==================generate decode Video END==========================");
                    if (this.q.get()) {
                        this.r.getAndSet(4);
                        if (this.A) {
                            TXCLog.d(this.a, "================== VIDEO SEND END OF FILE ==========================" + eVar.toString());
                        } else {
                            TXCLog.d(this.a, "--------------VIDEO NOT LAST FILE ----------------");
                            return true;
                        }
                    }
                    TXCLog.d(this.a, "-------------- generate Audio NOT END ----------------");
                    return true;
                }
                if (this.B == -1) {
                    this.B = System.currentTimeMillis();
                }
                if (this.s.get() == 2 || this.s.get() == 0) {
                    this.c.add(eVar);
                } else {
                    this.c.add(eVar);
                }
            } else {
                TXCLog.d(this.a, "VideoFrame pts :" + eVar.e() + " before  startTime (" + this.l + l.t);
                e();
                return true;
            }
        }
        return false;
    }

    private void u() throws InterruptedException {
        if (!this.o.get() && this.r.get() == 2) {
            e c = this.h.c();
            if (c != null) {
                c = this.f.b(c);
                if (this.f.d(c)) {
                    this.o.getAndSet(true);
                    TXCLog.d(this.a, "audio endOfFile:" + this.o.get());
                    TXCLog.d(this.a, "read audio end");
                }
                this.e.put(c.e(), c);
                this.h.a(c);
            }
        }
    }

    private void v() throws InterruptedException {
        if (!this.n.get() && this.r.get() == 2) {
            e c = this.g.c();
            if (c != null) {
                if ((this.R == 3 || this.R == 2) && this.f.p() >= this.Q) {
                    this.f.a(this.P);
                    this.R--;
                    this.S = true;
                }
                c = this.f.a(c);
                if (this.u == null) {
                    this.D = b();
                    if (this.D != 0) {
                        this.b = (1000 / this.D) * 1000;
                    }
                }
                if (this.S) {
                    c.a(this.T + ((long) this.b));
                }
                this.T = c.e();
                if (this.f.c(c)) {
                    this.n.getAndSet(true);
                    TXCLog.d(this.a, "video endOfFile:" + this.n.get());
                    TXCLog.d(this.a, "read video end");
                }
                this.d.put(c.e(), c);
                this.g.a(c);
            }
        }
    }

    public void e() {
        if (this.g != null) {
            this.g.e();
        }
    }
}
