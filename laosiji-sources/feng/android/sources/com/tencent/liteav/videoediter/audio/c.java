package com.tencent.liteav.videoediter.audio;

import android.annotation.TargetApi;
import android.media.MediaFormat;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.ijk.media.player.misc.IMediaFormat;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.c.e;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@TargetApi(18)
/* compiled from: TXAudioMixer */
public class c {
    public static String[] a = new String[]{"audio/mp4a-latm", "audio/mpeg"};
    private static final String b = c.class.getSimpleName();
    private boolean A = true;
    private AtomicInteger c = new AtomicInteger(-1);
    private MediaFormat d;
    private com.tencent.liteav.f.c e;
    private MediaFormat f;
    private String g;
    private volatile long h = -1;
    private volatile long i = -1;
    private int j;
    private int k;
    private int l;
    private int m;
    private List<e> n;
    private a o;
    private com.tencent.liteav.f.e p;
    private AtomicBoolean q = new AtomicBoolean(false);
    private AtomicBoolean r = new AtomicBoolean(false);
    private ReentrantLock s = new ReentrantLock();
    private Condition t = this.s.newCondition();
    private Condition u = this.s.newCondition();
    private f v;
    private TXSkpResample w;
    private g x = new g();
    private e y;
    private Handler z = new Handler(Looper.getMainLooper());

    /* compiled from: TXAudioMixer */
    private class a extends Thread {
        public a() {
            super("Mixer-BGM-Decoder-Thread");
        }

        public void run() {
            super.run();
            TXCLog.d(c.b, "================= start thread===================");
            try {
                c.this.l();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (c.this.A && c.this.c.get() == 1) {
                c.this.z.post(new Runnable() {
                    public void run() {
                        c.this.i();
                    }
                });
            }
            TXCLog.d(c.b, "================= finish thread===================");
        }
    }

    public c() {
        this.x.a(1.0f);
    }

    public void a(float f) {
        this.x.a(f);
    }

    public int a(String str) throws IOException {
        if (!(this.g == null || this.g.equals(str))) {
            this.h = -1;
            this.i = -1;
        }
        if (TextUtils.isEmpty(str)) {
            d();
            this.f = null;
            return 0;
        }
        if (this.c.get() == 0 || this.c.get() == 1) {
            d();
        }
        this.g = str;
        return g();
    }

    public void a(long j, long j2) {
        this.h = j * 1000;
        this.i = j2 * 1000;
        if (this.e != null) {
            this.e.c(this.h);
        }
        Log.d(b, "bgm startTime :" + this.h + ",bgm endTime:" + this.i);
    }

    public void b(float f) {
        this.x.b(f);
    }

    public void a(MediaFormat mediaFormat) {
        if (mediaFormat == null) {
            TXCLog.e(b, "target media format can't be null");
            return;
        }
        this.d = mediaFormat;
        this.k = this.d.getInteger("channel-count");
        this.j = this.d.getInteger("sample-rate");
        o();
    }

    private int g() throws IOException {
        int i;
        this.c.getAndSet(0);
        p();
        String string = this.f.getString(IMediaFormat.KEY_MIME);
        for (Object obj : a) {
            if (string != null && string.equals(obj)) {
                i = 1;
                break;
            }
        }
        i = 0;
        if (i == 0) {
            this.c.getAndSet(2);
            return -1;
        }
        h();
        q();
        o();
        return 0;
    }

    private void h() throws IOException {
        this.p = new com.tencent.liteav.f.e();
        this.p.a(this.e.k());
        this.p.a(this.e.k(), null);
        this.p.a();
    }

    private void i() {
        try {
            a(this.g);
            a();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void a() {
        if (this.c.get() == -1 || this.c.get() == 2) {
            TXCLog.e(b, "you should set bgm info first");
        } else if (this.c.get() == 1) {
            TXCLog.e(b, "decode have been started");
        } else {
            this.c.getAndSet(1);
            j();
        }
    }

    public short[] a(short[] sArr) {
        if (this.c.get() != 1) {
            TXCLog.e(b, "you should start first");
            return sArr;
        }
        short[] a = a(sArr.length);
        return (a == null || a.length == 0) ? sArr : this.x.a(sArr, a);
    }

    private short[] a(int i) {
        e c = c();
        if (c == null) {
            return null;
        }
        short[] a = a(c);
        if (a == null) {
            return null;
        }
        short[] copyOf = Arrays.copyOf(a, i);
        int length = a.length;
        if (length < i) {
            while (length < i) {
                c = c();
                if (c == null) {
                    return null;
                }
                short[] a2 = a(c);
                if (a2.length + length > i) {
                    a = a(copyOf, length, a2);
                    if (a != null) {
                        length += a2.length - a.length;
                        this.y = b(a);
                    }
                } else {
                    a(copyOf, length, a2);
                    length += a2.length;
                    this.y = null;
                }
            }
        } else if (length > i) {
            this.y = b(Arrays.copyOfRange(a, i, a.length));
            return copyOf;
        } else if (length == i) {
            short[] a3 = a(c);
            this.y = null;
            return a3;
        }
        return copyOf;
    }

    private short[] a(e eVar) {
        if (eVar instanceof a) {
            return ((a) eVar).z();
        }
        return b.a(eVar.b(), eVar.g());
    }

    private short[] a(short[] sArr, int i, short[] sArr2) {
        int i2 = 0;
        while (i2 < sArr2.length && i < sArr.length) {
            sArr[i] = sArr2[i2];
            i++;
            i2++;
        }
        if ((sArr2.length - i2) + 1 > 0) {
            return Arrays.copyOfRange(sArr2, i2, sArr2.length);
        }
        return null;
    }

    public boolean b() {
        return !this.A && this.r.get();
    }

    public e c() {
        e eVar = null;
        e eVar2;
        if (this.y != null) {
            eVar2 = this.y;
            this.y = null;
            return eVar2;
        } else if (!this.A && this.r.get()) {
            return null;
        } else {
            while (this.n != null && this.n.size() == 0) {
                this.s.lock();
                try {
                    this.u.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    eVar = this.s;
                    eVar.unlock();
                }
            }
            if (this.n != null && this.n.size() <= 10) {
                this.s.lock();
                this.t.signal();
                this.s.unlock();
            }
            eVar2 = eVar;
            while (true) {
                if ((eVar2 != null && eVar2.g() != 0) || this.n == null || this.n.size() == 0) {
                    return eVar2;
                }
                eVar2 = (e) this.n.remove(0);
            }
        }
    }

    public void d() {
        if (this.c.get() != -1) {
            this.c.getAndSet(2);
            TXCLog.i(b, "============================start cancel mix task=============================");
            u();
            s();
            r();
            t();
            this.z.removeCallbacksAndMessages(null);
            TXCLog.i(b, "============================cancel finish =============================");
        }
    }

    private void j() {
        k();
        this.o = new a();
        this.o.start();
    }

    private void k() {
        if (!(this.o == null || !this.o.isAlive() || this.o.isInterrupted())) {
            this.o.interrupt();
            this.o = null;
        }
        r();
        q();
        this.q.getAndSet(false);
        this.r.getAndSet(false);
    }

    public MediaFormat e() {
        return this.f;
    }

    public void a(boolean z) {
        this.A = z;
    }

    private void l() throws Exception {
        TXCLog.d(b, "================= start decode===================");
        loop0:
        while (this.c.get() == 1 && !Thread.currentThread().isInterrupted()) {
            if (this.r.get()) {
                TXCLog.d(b, "=================解码完毕===================");
                break loop0;
            }
            e n;
            try {
                m();
                n = n();
            } catch (Exception e) {
                n = null;
            }
            if (n != null) {
                Object b = b(n);
                if (b != null) {
                    if (this.n != null && this.n.size() == 20) {
                        this.s.lock();
                        try {
                            this.t.await();
                        } catch (InterruptedException e2) {
                            e2.printStackTrace();
                        } finally {
                            b = this.s;
                            b.unlock();
                        }
                    }
                    if (this.n != null && this.n.size() == 0) {
                        if (b != null) {
                            this.n.add(b);
                        }
                        this.s.lock();
                        this.u.signal();
                        this.s.unlock();
                    } else if (!(this.n == null || b == null)) {
                        this.n.add(b);
                    }
                }
            }
        }
        TXCLog.d(b, "=================decode finish===================");
    }

    private void m() throws InterruptedException {
        if (!this.q.get()) {
            e c = this.p.c();
            if (c != null) {
                c = this.e.b(c);
                if (this.e.d(c)) {
                    this.q.getAndSet(true);
                    TXCLog.d(b, "audio endOfFile:" + this.q.get());
                    TXCLog.d(b, "read audio end");
                }
                this.p.a(c);
            }
        }
    }

    private e n() {
        if (this.c.get() != 1) {
            return null;
        }
        e d = this.p.d();
        if (d == null || d.o() == null) {
            return null;
        }
        if (d.e() < this.h && (d.o().flags & 4) == 0) {
            return null;
        }
        if (d.e() > this.i) {
            this.r.getAndSet(true);
            return null;
        } else if ((d.o().flags & 4) == 0) {
            return d;
        } else {
            TXCLog.d(b, "==================generate decode Audio END==========================");
            this.r.getAndSet(true);
            return d;
        }
    }

    private e b(e eVar) throws InterruptedException {
        if (eVar.o().flags == 2) {
            return eVar;
        }
        if (this.k == this.m && this.j == this.l) {
            return eVar;
        }
        short[] a = b.a(eVar.b(), eVar.g());
        if (a == null || a.length == 0 || this.v == null || this.w == null) {
            return eVar;
        }
        if (this.k != this.m) {
            a = this.v.a(a);
        }
        if (this.j != this.l) {
            a = this.w.doResample(a);
            if (a == null || a.length == 0) {
                return null;
            }
        }
        return b(a);
    }

    private e b(short[] sArr) {
        if (sArr == null || sArr.length == 0) {
            return null;
        }
        e aVar = new a();
        aVar.a(sArr);
        aVar.d(sArr.length * 2);
        aVar.h(this.k);
        aVar.g(this.j);
        return aVar;
    }

    @TargetApi(16)
    private void o() {
        if (this.f != null && this.d != null) {
            if (this.v == null) {
                this.v = new f();
            }
            this.v.a(this.m, this.k);
            if (this.w == null) {
                this.w = new TXSkpResample();
            }
            this.w.init(this.l, this.j);
            TXCLog.i(b, "TXChannelResample and TXSkpResample have been created!!!");
        }
    }

    private void p() throws IOException {
        this.e = new com.tencent.liteav.f.c(true);
        this.e.a(this.g);
        this.f = this.e.k();
        this.m = this.f.getInteger("channel-count");
        this.l = this.f.getInteger("sample-rate");
        if (this.h == -1 && this.i == -1) {
            this.h = 0;
            this.i = this.f.getLong("durationUs") * 1000;
        }
        this.e.c(this.h);
    }

    private void q() {
        this.n = new LinkedList();
        this.n = Collections.synchronizedList(this.n);
    }

    private void r() {
        if (this.n != null) {
            TXCLog.i(b, "clean audio frame queue");
            this.n.clear();
            this.n = null;
        }
    }

    private void s() {
        if (this.e != null) {
            TXCLog.i(b, "release media extractor");
            this.e.m();
            this.e = null;
        }
    }

    private void t() {
        if (this.v != null) {
            this.v = null;
            TXCLog.i(b, "release chanel resample ");
        }
        if (this.w != null) {
            TXCLog.i(b, "release skp resample ");
            this.w.destroy();
            this.w = null;
        }
    }

    private void u() {
        if (!(this.o == null || !this.o.isAlive() || this.o.isInterrupted())) {
            TXCLog.i(b, "interrupt the decode thread");
            this.o.interrupt();
            this.o = null;
        }
        if (this.p != null) {
            TXCLog.i(b, "stop audio decode");
            this.p.b();
            this.p = null;
        }
    }
}
