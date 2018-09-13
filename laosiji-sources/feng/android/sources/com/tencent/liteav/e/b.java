package com.tencent.liteav.e;

import android.media.MediaFormat;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.tencent.liteav.b.i;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.c.e;
import com.tencent.liteav.d.h;
import com.tencent.liteav.videoediter.audio.TXJNIAudioResampler;
import com.tencent.liteav.videoediter.audio.c;
import com.tencent.liteav.videoediter.audio.f;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: AudioPreprecessChain */
public class b {
    public e a;
    private final String b = "AudioPreprecessChain";
    private com.tencent.liteav.videoediter.audio.e c;
    private f d;
    private TXJNIAudioResampler e;
    private c f;
    private h g;
    private int h;
    private int i;
    private LinkedList<Long> j = new LinkedList();
    private long k = -1;
    private long l = -1;
    private int m = 0;
    private com.tencent.liteav.b.b n = com.tencent.liteav.b.b.a();
    private com.tencent.liteav.c.b o;
    private float p;
    private g q = g.a();
    private boolean r = true;
    private HandlerThread s;
    private a t;
    private AtomicBoolean u = new AtomicBoolean(false);
    private final AtomicBoolean v = new AtomicBoolean(false);
    private Object w = new Object();

    /* compiled from: AudioPreprecessChain */
    class a extends Handler {
        public a(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case 10000:
                    a();
                    return;
                default:
                    return;
            }
        }

        private void a() {
            if (!b.this.v.get()) {
                if (b.this.u.get()) {
                    e c;
                    e b;
                    synchronized (b.this.w) {
                        c = b.this.f.c();
                        boolean b2 = b.this.f.b();
                    }
                    if (c == null && b2) {
                        b.this.v.set(true);
                        b = b();
                        if (b.this.g != null) {
                            b.this.g.a(b);
                            return;
                        }
                    }
                    if (!(c == null || c.b() == null)) {
                        long j;
                        if (b.this.n.g != 1.0f) {
                            b.this.c.a(b.this.n.g);
                            c = b.this.a(c.b(), b.this.c.a(com.tencent.liteav.videoediter.audio.b.a(c.b(), c.g())), c.e());
                        }
                        long a = b.this.a(c.g());
                        if (a == -1) {
                            a = 0;
                        }
                        c.a(a);
                        long j2 = i.a().l;
                        TXCLog.i("AudioPreprecessChain", "BgmHandler pts:" + a + ",duration:" + j2);
                        if (j2 != 0 || b.this.a == null) {
                            j = j2;
                        } else {
                            j = b.this.a.e();
                        }
                        if (a >= j) {
                            b.this.v.set(true);
                            b = b();
                            if (b.this.g != null) {
                                b.this.g.a(b);
                                return;
                            }
                        }
                        c.a(a + com.tencent.liteav.b.c.a().d());
                        if (b.this.g != null) {
                            b.this.g.a(c);
                        }
                    }
                    sendEmptyMessageDelayed(10000, 10);
                    return;
                }
                sendEmptyMessageDelayed(10000, 10);
            }
        }

        private e b() {
            e eVar = new e();
            eVar.d(0);
            eVar.a(0);
            eVar.c(4);
            return eVar;
        }
    }

    public void a() {
        TXCLog.d("AudioPreprecessChain", "initFilter");
        this.e = new TXJNIAudioResampler();
        this.d = new f();
        synchronized (this.w) {
            this.f = new c();
        }
        this.c = new com.tencent.liteav.videoediter.audio.e();
        this.p = 1.0f;
        this.e.setSpeed(this.p);
    }

    public void b() {
        TXCLog.d("AudioPreprecessChain", "destroyFilter");
        this.k = -1;
        this.l = -1;
        this.m = 0;
        if (this.e != null) {
            this.e.destroy();
            this.e = null;
        }
        synchronized (this.w) {
            if (this.f != null) {
                this.f.d();
                this.f = null;
            }
        }
        if (this.d != null) {
            this.d = null;
        }
        if (this.j != null) {
            this.j.clear();
        }
    }

    public void a(MediaFormat mediaFormat) {
        if (mediaFormat == null) {
            TXCLog.e("AudioPreprecessChain", "setAudioFormat audioFormat is null");
            return;
        }
        this.o = new com.tencent.liteav.c.b();
        if (VERSION.SDK_INT >= 16) {
            this.o.b = mediaFormat.getInteger("sample-rate");
            this.o.a = mediaFormat.getInteger("channel-count");
            TXCLog.i("AudioPreprecessChain", "setAudioFormat sampleRate:" + this.o.b);
            TXCLog.i("AudioPreprecessChain", "setAudioFormat channelCount:" + this.o.a);
        }
        if (!(this.h == 0 || this.i == 0)) {
            this.e.setChannelCount(this.o.b);
            this.d.a(this.h, this.o.a);
            this.e.setSampleRate(this.i, this.o.b);
        }
        if (this.f != null) {
            this.f.a(mediaFormat);
        }
    }

    public void c() {
        TXCLog.i("AudioPreprecessChain", "start");
        if (TextUtils.isEmpty(this.n.a)) {
            this.n.h = false;
            return;
        }
        this.n.h = true;
        this.u.set(true);
        a(this.n.a);
        if (!(this.n.b == -1 || this.n.c == -1)) {
            a(this.n.b, this.n.c);
        }
        a(this.n.e);
        a(this.n.f);
        b(this.n.g);
        a(this.n.d);
    }

    public void d() {
        TXCLog.i("AudioPreprecessChain", "stop");
        if (!this.r) {
            if (this.t != null) {
                this.t.removeCallbacksAndMessages(null);
                this.s.quit();
            }
            this.v.set(true);
            this.s = null;
            this.t = null;
        }
    }

    public int e() {
        if (this.r) {
            TXCLog.w("AudioPreprecessChain", "tryStartAddBgmForNoAudioTrack, this has audio track, ignore!");
            return -1;
        }
        if (this.s == null) {
            this.s = new HandlerThread("bgm_handler_thread");
            this.s.start();
            this.t = new a(this.s.getLooper());
        }
        this.v.set(false);
        this.t.sendEmptyMessage(10000);
        return 0;
    }

    public void f() {
        TXCLog.i("AudioPreprecessChain", "pause");
        this.u.set(false);
    }

    public void g() {
        TXCLog.i("AudioPreprecessChain", "resume");
        this.u.set(true);
    }

    public int a(String str) {
        int a;
        try {
            a = this.f.a(str);
        } catch (IOException e) {
            e.printStackTrace();
            a = -1;
        }
        this.f.a();
        if (TextUtils.isEmpty(str)) {
            this.n.h = false;
        } else {
            this.n.h = true;
        }
        return a;
    }

    public void a(long j, long j2) {
        if (this.f != null) {
            this.f.a(j, j2);
        }
    }

    public void a(boolean z) {
        if (this.f != null) {
            this.f.a(z);
        }
    }

    public void a(long j) {
        this.n.d = j;
    }

    public void a(float f) {
        if (this.f != null) {
            this.f.b(f);
        }
    }

    public void b(float f) {
        if (this.f != null) {
            this.f.a(f);
        }
    }

    public MediaFormat h() {
        return this.f.e();
    }

    public void a(h hVar) {
        this.g = hVar;
    }

    public void a(e eVar) {
        if (eVar == null) {
            TXCLog.e("AudioPreprecessChain", "processFrame, frame is null");
        } else if (eVar.q() || eVar.r()) {
            TXCLog.i("AudioPreprecessChain", "processFrame, frame is isUnNormallFrame");
            if (this.g != null) {
                this.g.a(eVar);
            }
        } else if (eVar.p()) {
            TXCLog.e("AudioPreprecessChain", "processFrame, frame is end");
            if (this.g != null) {
                this.g.a(eVar);
            }
        } else {
            if (this.q.c() || this.i != this.o.b) {
                this.p = this.q.a(eVar.e());
                this.e.setSpeed(this.p);
                if (this.k == -1) {
                    this.k = eVar.e();
                }
                this.j.add(k());
            } else {
                this.j.add(Long.valueOf(eVar.e()));
            }
            e a = a(eVar.b(), b(eVar));
            if (this.g != null) {
                this.g.a(a);
            }
        }
    }

    public void i() {
        e eVar = null;
        if (this.e != null) {
            short[] flushBuffer = this.e.flushBuffer();
            if (!(this.i == this.o.b || this.o.a != 2 || flushBuffer == null)) {
                this.d.a(1, 2);
                flushBuffer = this.d.a(flushBuffer);
            }
            if (flushBuffer != null && flushBuffer.length > 0) {
                this.j.add(Long.valueOf(k().longValue()));
                eVar = a(null, flushBuffer);
            }
            if (eVar != null && this.g != null) {
                this.g.a(eVar);
            }
        }
    }

    private Long k() {
        long j;
        if (this.m == 0) {
            j = this.k;
        } else {
            j = this.k + ((1024000000 * ((long) this.m)) / ((long) this.o.b));
        }
        this.m++;
        return Long.valueOf(j);
    }

    private long a(int i) {
        long j;
        if (this.l == -1) {
            j = this.k;
        } else {
            j = this.l;
        }
        this.l = ((1000000 * ((long) i)) / ((long) ((this.o.b * this.o.a) * 2))) + j;
        return j;
    }

    private e a(ByteBuffer byteBuffer, short[] sArr) {
        if (sArr == null || sArr.length == 0) {
            return null;
        }
        if (this.j == null || this.j.size() == 0) {
            TXCLog.e("AudioPreprecessChain", "doMixer mTimeQueue:" + this.j);
            return null;
        }
        long longValue = ((Long) this.j.pollFirst()).longValue();
        if (this.n.h) {
            if (longValue >= this.n.d) {
                sArr = this.f.a(sArr);
            }
            return a(byteBuffer, sArr, longValue);
        } else if (this.n.f == 1.0f) {
            return a(byteBuffer, sArr, longValue);
        } else {
            this.c.a(this.n.f);
            return a(byteBuffer, this.c.a(sArr), longValue);
        }
    }

    private e a(ByteBuffer byteBuffer, short[] sArr, long j) {
        int length = sArr.length * 2;
        ByteBuffer a = com.tencent.liteav.videoediter.audio.b.a(byteBuffer, sArr);
        e eVar = new e();
        eVar.d(length);
        eVar.a(a);
        eVar.h(this.o.a);
        eVar.g(this.o.b);
        eVar.b(j);
        eVar.a(j);
        return eVar;
    }

    private short[] b(e eVar) {
        c(eVar);
        short[] a = com.tencent.liteav.videoediter.audio.b.a(eVar.b(), eVar.g());
        if (this.i == this.o.b || this.o.a != 2) {
            if (this.h != this.o.a) {
                a = this.d.a(a);
            }
            if (this.p == 1.0f && this.i == this.o.b) {
                return a;
            }
            return this.e.resample(a);
        }
        if (this.h == 2) {
            this.d.a(2, 1);
            if (a != null) {
                a = this.d.a(a);
            }
        }
        if (!(this.p == 1.0f && this.i == this.o.b)) {
            a = this.e.resample(a);
        }
        if (a == null) {
            return a;
        }
        this.d.a(1, 2);
        return this.d.a(a);
    }

    private void c(e eVar) {
        if (this.h != eVar.k()) {
            this.h = eVar.k();
            TXCLog.i("AudioPreprecessChain", "setAudioFormat initResampler setChannelCount");
            this.e.setChannelCount(this.o.a);
            this.d.a(this.h, this.o.a);
        }
        if (this.i != eVar.j()) {
            this.i = eVar.j();
            TXCLog.i("AudioPreprecessChain", "setAudioFormat initResampler setSampleRate");
            this.e.setSampleRate(this.i, this.o.b);
        }
    }

    public void b(boolean z) {
        this.r = z;
    }

    public void c(boolean z) {
        this.u.set(z);
    }

    public boolean j() {
        return this.v.get();
    }
}
