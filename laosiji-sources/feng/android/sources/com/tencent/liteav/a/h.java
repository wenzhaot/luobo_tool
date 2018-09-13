package com.tencent.liteav.a;

import android.annotation.TargetApi;
import android.media.MediaFormat;
import android.util.LongSparseArray;
import android.view.Surface;
import com.tencent.liteav.a.a.a;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.c.e;
import com.tencent.liteav.f.a.g;
import com.tencent.liteav.f.c;
import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

@TargetApi(16)
/* compiled from: TXReaderLone */
public class h implements g {
    private LongSparseArray<e> a = new LongSparseArray();
    private LongSparseArray<e> b = new LongSparseArray();
    private AtomicBoolean c = new AtomicBoolean(false);
    private AtomicBoolean d = new AtomicBoolean(false);
    private LinkedList<e> e = new LinkedList();
    private Surface f;
    private a g;
    private c h = new c();
    private g i;
    private com.tencent.liteav.f.e j;
    private e k;
    private e l;
    private ArrayBlockingQueue<e> m;
    private ArrayBlockingQueue<e> n;

    public h() {
        this.e.clear();
    }

    public int a(String str) {
        int a;
        try {
            a = this.h.a(str);
        } catch (IOException e) {
            e.printStackTrace();
            a = 0;
        }
        if (a < 0) {
            return a;
        }
        return 0;
    }

    public void a(Surface surface) {
        this.f = surface;
    }

    public MediaFormat a() {
        return this.h.k();
    }

    public int b() {
        return this.h.b();
    }

    public int c() {
        return this.h.c();
    }

    public int d() {
        return this.h.d();
    }

    public int f() {
        MediaFormat k = this.h.k();
        if (k.containsKey("sample-rate")) {
            return k.getInteger("sample-rate");
        }
        return 0;
    }

    public int g() {
        MediaFormat k = this.h.k();
        if (k.containsKey("max-input-size")) {
            return k.getInteger("max-input-size");
        }
        return 0;
    }

    public void a(a aVar) {
        this.g = aVar;
    }

    public void h() {
        this.i = new g();
        this.j = new com.tencent.liteav.f.e();
        MediaFormat k = this.h.k();
        this.j.a(k);
        this.j.a(k, null);
        this.j.a();
        this.i.a(this.h.j());
        this.i.a(this.h.j(), this.f);
        this.i.a();
    }

    public void i() {
        if (this.j != null) {
            this.j.b();
        }
        if (this.i != null) {
            this.i.b();
        }
        if (this.e != null) {
            this.e.clear();
        }
        if (this.b != null) {
            this.b.clear();
        }
        if (this.a != null) {
            this.a.clear();
        }
        this.h.m();
        this.c.compareAndSet(true, false);
        this.d.compareAndSet(true, false);
    }

    public void j() throws InterruptedException {
        k();
        l();
        m();
        n();
    }

    private void k() throws InterruptedException {
        if (this.c.get()) {
            TXCLog.d("TXReaderLone", "mReadVideoEOF, ignore");
            return;
        }
        e c = this.i.c();
        if (c != null) {
            c = this.h.a(c);
            if (this.h.c(c)) {
                this.c.compareAndSet(false, true);
                TXCLog.i("TXReaderLone", "==TXReaderLone Read Video End===");
            }
            this.b.put(c.e(), c);
            this.i.a(c);
        }
    }

    private void l() throws InterruptedException {
        if (!this.d.get()) {
            e c = this.j.c();
            if (c != null) {
                c = this.h.b(c);
                if (this.h.d(c)) {
                    this.d.compareAndSet(false, true);
                    TXCLog.i("TXReaderLone", "==TXReaderLone Read Audio End===");
                }
                this.a.put(c.e(), c);
                this.j.a(c);
            }
        }
    }

    private void m() {
        e eVar;
        if (this.e.size() == 0) {
            if (this.m == null || this.m.size() <= 0) {
                e d = this.i.d();
                if (d != null && d.o() != null) {
                    if (this.k == null) {
                        this.k = d;
                    }
                    eVar = (e) this.b.get(d.e());
                    if (eVar != null) {
                        eVar = this.i.a(eVar, d);
                    } else {
                        eVar = d;
                    }
                    if ((eVar.o().flags & 4) != 0) {
                        TXCLog.i("TXReaderLone", "==TXReaderLone Decode Video End===");
                    }
                    this.e.add(eVar);
                } else {
                    return;
                }
            }
            TXCLog.d("TXReaderLone", "decodeVideoFrame, ignore because mVideoBlockingQueue.size() = " + this.m.size());
            return;
        }
        if (this.e.size() > 0) {
            eVar = (e) this.e.get(0);
            if (this.k == null) {
                this.k = eVar;
            }
            if (this.g != null) {
                this.g.b(eVar);
            }
            if (!this.e.isEmpty() && this.e.size() > 0) {
                this.e.remove(0);
            }
            this.k = eVar;
        }
    }

    private void n() {
        if (this.n == null || this.n.size() <= 9) {
            e d = this.j.d();
            if (d != null && d.o() != null) {
                e eVar = (e) this.a.get(d.e());
                if (eVar != null) {
                    eVar = this.j.a(eVar, d);
                } else {
                    eVar = d;
                }
                if (eVar == null) {
                    TXCLog.e("TXReaderLone", "decodeAudioFrame, fixFrame is null, sampleTime = " + d.e());
                    return;
                }
                if ((eVar.o().flags & 4) != 0) {
                    TXCLog.i("TXReaderLone", "==TXReaderLone Decode Audio End===");
                }
                if (this.l == null) {
                    this.l = d;
                }
                if (this.g != null) {
                    this.g.a(eVar);
                }
                this.l = eVar;
                return;
            }
            return;
        }
        TXCLog.d("TXReaderLone", "decodeAudioFrame, ignore because mAudioBlockingQueue size = " + this.n.size());
    }

    public void e() {
        if (this.i != null) {
            this.i.e();
        }
    }

    public void a(ArrayBlockingQueue<e> arrayBlockingQueue) {
        this.m = arrayBlockingQueue;
    }

    public void b(ArrayBlockingQueue<e> arrayBlockingQueue) {
        this.n = arrayBlockingQueue;
    }
}
