package com.tencent.liteav.videoediter.audio;

import android.annotation.TargetApi;
import android.media.MediaFormat;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.c.e;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.LinkedList;

@TargetApi(16)
/* compiled from: TXAudioProcessor */
public class d {
    private static final String e = d.class.getSimpleName();
    private volatile boolean a;
    private volatile boolean b;
    private volatile boolean c;
    private volatile boolean d;
    private float f = 1.0f;
    private TXJNIAudioResampler g = new TXJNIAudioResampler();
    private f h = new f();
    private c i;
    private e j = new e();
    private MediaFormat k;
    private int l;
    private int m;
    private int n;
    private int o;
    private float p = 1.0f;
    private LinkedList<Long> q = new LinkedList();
    private int r;
    private long s = -1;
    private long t = 0;

    public void a(@NonNull MediaFormat mediaFormat) {
        this.k = mediaFormat;
        this.l = this.k.getInteger("channel-count");
        this.m = this.k.getInteger("sample-rate");
        if (!(!this.a || this.n == 0 || this.o == 0)) {
            this.g.setChannelCount(this.l);
            this.h.a(this.n, this.l);
            this.g.setSampleRate(this.o, this.m);
        }
        this.a = true;
        if (this.i != null) {
            this.i.a(mediaFormat);
        }
    }

    public void a(float f) {
        if (!d() && f >= 0.0f) {
            this.f = f;
            this.g.setSpeed(f);
        }
    }

    public int a(String str) {
        int i = -1;
        if (!d()) {
            c();
            if (this.i != null) {
                try {
                    i = this.i.a(str);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                this.i.a();
            } else {
                i = 0;
            }
            if (TextUtils.isEmpty(str)) {
                this.c = false;
                this.d = false;
            } else {
                this.c = true;
                this.d = true;
            }
        }
        return i;
    }

    public void a(long j, long j2) {
        if (!d()) {
            c();
            if (this.i != null) {
                this.i.a(j, j2);
            }
        }
    }

    public void b(float f) {
        if (this.i == null) {
            this.i = new c();
            this.i.a(this.k);
        }
        this.i.a(f);
    }

    public void c(float f) {
        this.p = f;
        if (this.i == null) {
            this.i = new c();
            this.i.a(this.k);
        }
        this.i.b(f);
    }

    public e a(e eVar) {
        if (d() || eVar == null || eVar.g() == 0 || eVar.f() == 2 || eVar.f() == 4) {
            return eVar;
        }
        if (this.s == -1) {
            this.s = eVar.e();
        }
        int k = eVar.k();
        int j = eVar.j();
        int i = this.l;
        int i2 = this.m;
        if (k != this.n) {
            this.n = k;
            this.g.setChannelCount(this.l);
            if (this.h != null) {
                this.h.a(k, this.l);
            }
        }
        if (j != this.o) {
            this.o = j;
            this.g.setSampleRate(j, this.m);
        }
        this.q.add(Long.valueOf(e()));
        short[] a = b.a(eVar.b(), eVar.g());
        if (!(k == i || this.h == null)) {
            a = this.h.a(a);
        }
        if (this.f != 1.0f || j < i2) {
            a = this.g.resample(a);
        }
        return a(eVar.b(), a);
    }

    public e a() {
        short[] flushBuffer = this.g.flushBuffer();
        if (flushBuffer == null || flushBuffer.length <= 0) {
            return null;
        }
        this.q.add(Long.valueOf(e()));
        return a(null, flushBuffer);
    }

    private e a(ByteBuffer byteBuffer, short[] sArr) {
        Object obj = 1;
        if (sArr == null || sArr.length == 0) {
            return null;
        }
        Object obj2 = (this.d && this.c) ? 1 : null;
        long j = 0;
        if (this.q != null) {
            j = ((Long) this.q.pollFirst()).longValue();
        }
        if (obj2 != null) {
            short[] a = this.i.a(sArr);
            TXCLog.i(e, "---mix---");
            return a(byteBuffer, a, j);
        }
        if (this.p == 1.0f) {
            obj = null;
        }
        if (obj == null) {
            return a(byteBuffer, sArr, j);
        }
        this.j.a(this.p);
        return a(byteBuffer, this.j.a(sArr), j);
    }

    public void b() {
        this.s = -1;
        this.r = 0;
        this.t = 0;
        if (this.g != null) {
            this.g.destroy();
            this.f = 1.0f;
        }
        if (this.i != null) {
            this.i.d();
            this.i = null;
        }
        if (this.h != null) {
            this.h = null;
        }
        if (this.q != null) {
            this.q.clear();
        }
        this.b = true;
    }

    private void c() {
        if (this.i == null) {
            this.i = new c();
            this.i.a(this.k);
        }
    }

    private boolean d() {
        if (this.b) {
            TXCLog.e(e, "this object have been destroy");
            return true;
        } else if (this.a) {
            return false;
        } else {
            TXCLog.e(e, "you must set target MediaFormat first");
            return true;
        }
    }

    private e a(ByteBuffer byteBuffer, short[] sArr, long j) {
        if (sArr == null || sArr.length == 0) {
            return null;
        }
        int length = sArr.length * 2;
        ByteBuffer a = b.a(byteBuffer, sArr);
        e eVar = new e();
        eVar.d(length);
        eVar.a(a);
        eVar.h(this.l);
        eVar.g(this.m);
        eVar.a(j);
        return eVar;
    }

    private long e() {
        long j;
        if (this.r == 0) {
            j = this.s;
        } else {
            j = this.s + ((1024000000 * ((long) this.r)) / ((long) this.m));
        }
        this.r++;
        return j;
    }
}
