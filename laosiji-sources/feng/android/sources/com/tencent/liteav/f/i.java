package com.tencent.liteav.f;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.MediaFormat;
import android.view.Surface;
import com.tencent.ijk.media.player.IjkMediaMeta;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.d.b.a;
import com.tencent.liteav.f.a.b;
import com.tencent.liteav.f.a.c;
import com.tencent.liteav.f.a.d;
import com.tencent.liteav.f.a.e;
import com.tencent.ugc.TXVideoEditConstants;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

@TargetApi(16)
/* compiled from: TXJoinerGenerater */
public class i implements a, b, d {
    private d a;
    private MediaFormat b;
    private MediaFormat c;
    private MediaFormat d;
    private float e = -1.0f;
    private c f;
    private Context g;
    private com.tencent.liteav.videoediter.audio.d h;
    private q i;
    private r j;
    private p k;
    private Surface l;
    private AtomicInteger m;
    private AtomicInteger n;
    private AtomicInteger o;
    private Object p = new Object();
    private boolean q = false;
    private boolean r = false;
    private float s = 1.0f;

    static {
        com.tencent.liteav.basic.util.a.d();
    }

    public void a(e eVar) {
        this.i.a(eVar);
    }

    public void a(boolean z) {
        this.i.a(z);
    }

    public boolean a() {
        return this.i.c();
    }

    public i(Context context) {
        this.g = context;
        this.i = new q();
        this.i.a((b) this);
        this.k = new p(context);
        this.j = new r(context);
        this.m = new AtomicInteger(0);
        this.n = new AtomicInteger(0);
        this.o = new AtomicInteger(0);
        this.a = d.a();
    }

    public void a(c cVar) {
        this.f = cVar;
    }

    public void a(long j) {
        TXCLog.d("TXJoinerGenerater", "setDuration: " + j);
        this.j.a(j);
    }

    public int a(String str) throws IOException {
        TXCLog.d("TXJoinerGenerater", "setVideoPath: " + str);
        int a = this.i.a(str);
        if (a == TXVideoEditConstants.ERR_UNSUPPORT_LARGE_RESOLUTION || a == 0) {
            MediaFormat j = this.i.j();
            if (j != null) {
                this.j.c(j);
            }
            this.c = this.i.a();
            d();
        }
        return a;
    }

    private void d() {
        int i = 0;
        if (this.c != null) {
            int integer;
            int i2;
            if (this.d == null) {
                integer = this.c.getInteger("sample-rate");
                i2 = 1;
            } else {
                i2 = this.c.getInteger("sample-rate");
                integer = this.d.getInteger("sample-rate");
                if (i2 <= integer) {
                    i2 = integer;
                }
                integer = this.b.getInteger("sample-rate");
                if (i2 != integer) {
                    TXCLog.i("TXJoinerGenerater", "initTargetAudioFormat: sample rate change : from " + integer + " to " + i2);
                    integer = i2;
                    i2 = 0;
                    i = 1;
                } else {
                    integer = i2;
                    i2 = 0;
                }
            }
            if (i2 != 0 || i != 0) {
                if (this.i != null && this.i.i() == 2) {
                    this.i.g();
                }
                this.b = MediaFormat.createAudioFormat("audio/mp4a-latm", integer, 1);
                if (this.c.containsKey(IjkMediaMeta.IJKM_KEY_BITRATE)) {
                    this.b.setInteger(IjkMediaMeta.IJKM_KEY_BITRATE, this.c.getInteger(IjkMediaMeta.IJKM_KEY_BITRATE));
                }
                this.j.b(this.b);
                if (this.h == null) {
                    e();
                } else {
                    this.h.a(this.b);
                }
                if (this.i != null && this.i.i() == 3) {
                    this.i.h();
                }
            }
        }
    }

    public void b(String str) throws IOException {
        TXCLog.d("TXJoinerGenerater", "addSourcePath: " + str);
        this.i.a(str);
    }

    public void b(int i) {
        TXCLog.d("TXJoinerGenerater", "setVideoCompressed: " + i);
        this.j.b(i);
    }

    public void c(int i) {
        TXCLog.d("TXJoinerGenerater", "setCurrentType: " + i);
        this.i.a(i);
    }

    public synchronized void b() {
        TXCLog.d("TXJoinerGenerater", "start");
        this.i.k();
    }

    public synchronized void c() {
        boolean z = true;
        synchronized (this) {
            TXCLog.d("TXJoinerGenerater", "init");
            this.j.b(this.a.b());
            e();
            this.m.set(0);
            this.n.set(0);
            this.o.set(0);
            this.q = false;
            this.j.a(this.i.d());
            this.j.a(this.f);
            this.j.c();
            this.l = this.j.d();
            this.k.a((d) this);
            this.k.a(this.l);
            this.k.a(this.j.a(), this.j.b());
            this.k.a(this.i);
            this.k.a(false);
            this.k.a();
            int i = this.i.i();
            TXCLog.d("TXJoinerGenerater", "init status : " + i);
            q qVar = this.i;
            p pVar = this.k;
            if (i != 4) {
                z = false;
            }
            qVar.a(pVar.c(z));
            this.i.b(false);
            this.i.c(true);
            this.i.k();
        }
    }

    private void e() {
        if (this.h == null) {
            this.h = new com.tencent.liteav.videoediter.audio.d();
            if (this.b != null) {
                this.h.a(this.b);
            }
            if (this.e != -1.0f) {
                this.h.c(this.e);
            }
            this.h.a(this.s);
        }
    }

    public synchronized void b(boolean z) {
        TXCLog.d("TXJoinerGenerater", "MediaComposer stop");
        this.j.a(null);
        this.j.a(z);
        if (this.r) {
            this.q = false;
        }
        this.i.f();
        this.k.a(null);
        this.k.b();
        if (this.h != null) {
            this.h.b();
            this.h = null;
        }
        this.k = new p(this.g);
    }

    public void a(int i) {
        this.i.c(i <= 5);
    }

    public void a(com.tencent.liteav.c.e eVar) {
        com.tencent.liteav.c.e eVar2;
        if (this.h == null) {
            eVar2 = eVar;
        } else {
            try {
                eVar2 = this.h.a(eVar);
            } catch (IllegalArgumentException e) {
                this.h = null;
                e();
                eVar2 = this.h.a(eVar);
            }
            if (eVar2 == null) {
                return;
            }
        }
        if (this.s != 1.0f) {
            do {
                a(eVar, eVar2);
                eVar2 = this.h.a();
            } while (eVar2 != null);
            return;
        }
        a(eVar, eVar2);
    }

    private void a(com.tencent.liteav.c.e eVar, com.tencent.liteav.c.e eVar2) {
        if ((eVar.f() & 4) != 0) {
            synchronized (this.p) {
                TXCLog.i("TXJoinerGenerater", "newAudioFrameAvailable, lock end frame, pre process video frame count = " + this.o.get());
                this.q = true;
                if (this.o.get() >= this.m.get()) {
                    this.q = false;
                    this.j.a(false);
                }
            }
            return;
        }
        this.n.incrementAndGet();
        this.j.b(eVar2);
    }

    public void b(com.tencent.liteav.c.e eVar) {
        d(eVar);
    }

    private void d(com.tencent.liteav.c.e eVar) {
        if ((eVar.f() & 4) != 0) {
            synchronized (this.p) {
                TXCLog.i("TXJoinerGenerater", "newVideoFrameAvailable, lock end frame, pre process video frame count = " + this.o.get());
                this.q = true;
                if (this.o.get() >= this.m.get()) {
                    this.q = false;
                    this.j.a(false);
                }
            }
            return;
        }
        this.m.incrementAndGet();
        this.k.a(eVar);
    }

    public void a(long j, long j2) {
        TXCLog.d("TXJoinerGenerater", "setPlayFromTime startTime=" + (j * 1000) + ",duration=" + (j2 * 1000));
        this.i.a(j * 1000, 1000 * j2);
    }

    public void c(com.tencent.liteav.c.e eVar) {
        this.j.a(eVar);
        this.o.incrementAndGet();
        synchronized (this.p) {
            if (this.q && this.o.get() >= this.m.get()) {
                TXCLog.i("TXJoinerGenerater", "onFrameProcessed, pre process frame count  = " + this.o.get() + ", stop video writer");
                this.q = false;
                this.j.a(false);
            }
        }
    }
}
