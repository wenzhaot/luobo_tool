package com.tencent.liteav.d;

import android.annotation.TargetApi;
import android.media.MediaFormat;
import android.view.Surface;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.f.a.b;
import com.tencent.liteav.f.e;
import com.tencent.ugc.TXVideoEditConstants;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

@TargetApi(16)
/* compiled from: BasicVideoDecDemux */
public class c {
    protected com.tencent.liteav.f.c a;
    protected z b;
    protected e c;
    protected Surface d;
    protected b e;
    protected AtomicLong f;
    protected AtomicLong g;
    protected int h;
    protected String i;
    private final String j = "BasicVideoDecDemux";
    private int k;

    public int a(String str) throws IOException {
        this.i = str;
        this.a = new com.tencent.liteav.f.c();
        int a = this.a.a(str);
        if (a == TXVideoEditConstants.ERR_UNSUPPORT_LARGE_RESOLUTION || a == 0) {
            this.k = 0;
        }
        return a;
    }

    public void a(b bVar) {
        this.e = bVar;
    }

    protected void a() {
        TXCLog.i("BasicVideoDecDemux", "configureVideo");
        this.b = new z();
        this.b.a(this.a.j());
        this.b.a(this.a.j(), this.d);
        this.b.a();
    }

    protected void b() {
        TXCLog.i("BasicVideoDecDemux", "configureAudio");
        this.c = new e();
        MediaFormat k = this.a.k();
        this.c.a(k);
        this.c.a(k, null);
        this.c.a();
    }

    public synchronized void a(Surface surface) {
        this.d = surface;
    }

    public long c() {
        return this.a.a();
    }

    public int d() {
        return this.a.b();
    }

    public int e() {
        return this.a.c();
    }

    public MediaFormat f() {
        return this.a.k();
    }

    public MediaFormat g() {
        return this.a.j();
    }

    public boolean h() {
        if (this.a == null || this.a.k() == null) {
            return false;
        }
        return true;
    }

    public boolean i() {
        return this.a.j() != null;
    }

    protected long a(com.tencent.liteav.c.e eVar) {
        TXCLog.e("BasicVideoDecDemux", "seekFinalVideo, read is end frame, try to find final video frame(not end frame)");
        int i = 1;
        long j = (long) ((1000 / j()) * 1000);
        long i2 = this.a.i();
        if (i2 <= 0) {
            i2 = this.g.get();
        }
        while (true) {
            int i3 = i;
            if (i3 > 3) {
                return 0;
            }
            long j2 = i2 - (((long) i3) * j);
            if (j2 < 0) {
                j2 = i2;
            }
            this.a.a(j2);
            this.a.a(eVar);
            TXCLog.i("BasicVideoDecDemux", "seekReversePTS, seek End PTS = " + j2 + ", flags = " + eVar.f() + ", seekEndCount = " + i3);
            if (!eVar.p()) {
                return eVar.e();
            }
            i = i3 + 1;
        }
    }

    protected boolean a(long j, long j2, com.tencent.liteav.c.e eVar) {
        if (j <= this.f.get()) {
            TXCLog.i("BasicVideoDecDemux", "seekReversePTS, lastReadPTS <= mStartTime");
            this.a.a(j);
            this.h++;
            if (this.h < 2) {
                return false;
            }
            this.b.b(eVar);
            return true;
        }
        long j3 = j - 1000;
        this.a.a(j3);
        long n = this.a.n();
        if (n < j) {
            TXCLog.i("BasicVideoDecDemux", "seekReversePTS, seekPTS = " + j3 + ", find previous pts = " + n);
            return false;
        }
        int i = 1;
        while (true) {
            int i2 = i;
            j3 = j - ((((long) i2) * j2) + 1000);
            if (j3 < 0) {
                j3 = 0;
            }
            this.a.a(j3);
            long n2 = this.a.n();
            TXCLog.i("BasicVideoDecDemux", "seekReversePTS, may be SEEK_TO_PREVIOUS_SYNC same to NEXT_SYNC, seekPTS = " + j3 + ", find previous pts = " + n2 + ", count = " + i2);
            if (n2 < j) {
                return false;
            }
            i = i2 + 1;
            if (i > 10) {
                this.b.b(eVar);
                return true;
            }
        }
    }

    public int j() {
        if (this.k != 0) {
            return this.k;
        }
        MediaFormat j = this.a.j();
        if (j != null) {
            try {
                this.k = j.getInteger("frame-rate");
            } catch (NullPointerException e) {
                this.k = 20;
            }
        }
        return this.k;
    }

    public void k() {
        if (this.a != null) {
            this.a.m();
        }
    }
}
