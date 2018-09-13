package anet.channel.monitor;

import anet.channel.util.ALog;

/* compiled from: Taobao */
class d implements Runnable {
    final /* synthetic */ long a;
    final /* synthetic */ long b;
    final /* synthetic */ long c;
    final /* synthetic */ b d;

    d(b bVar, long j, long j2, long j3) {
        this.d = bVar;
        this.a = j;
        this.b = j2;
        this.c = j3;
    }

    public void run() {
        int i = 5;
        if (ALog.isPrintLog(1)) {
            ALog.d("awcn.BandWidthSampler", "onDataReceived", null, "mRequestStartTime", Long.valueOf(this.a), "mRequestFinishedTime", Long.valueOf(this.b), "mRequestDataSize", Long.valueOf(this.c));
        }
        if (b.k && this.c > 3000 && this.a < this.b) {
            b.a++;
            b.e += this.c;
            if (b.a == 1) {
                b.d = this.b - this.a;
            }
            if (b.a >= 2 && b.a <= 3) {
                if (this.a >= b.c) {
                    b.d += this.b - this.a;
                } else if (this.a < b.c && this.b >= b.c) {
                    b.d += this.b - this.a;
                    b.d -= b.c - this.a;
                }
            }
            b.b = this.a;
            b.c = this.b;
            if (b.a == 3) {
                b.i = (double) ((long) this.d.n.a((double) b.e, (double) b.d));
                b.f++;
                this.d.m = this.d.m + 1;
                if (b.f > 30) {
                    this.d.n.a();
                    b.f = 3;
                }
                double d = ((b.i * 0.68d) + (b.h * 0.27d)) + (b.g * 0.05d);
                b.g = b.h;
                b.h = b.i;
                if (b.i < 0.65d * b.g || b.i > 2.0d * b.g) {
                    b.i = d;
                }
                if (ALog.isPrintLog(1)) {
                    ALog.d("awcn.BandWidthSampler", "NetworkSpeed", null, "mKalmanDataSize", Long.valueOf(b.e), "mKalmanTimeUsed", Long.valueOf(b.d), "speed", Double.valueOf(b.i), "mSpeedKalmanCount", Long.valueOf(b.f));
                }
                if (this.d.m > 5 || b.f == 2) {
                    a.a().a(b.i);
                    this.d.m = 0;
                    b bVar = this.d;
                    if (b.i < b.j) {
                        i = 1;
                    }
                    bVar.l = i;
                    ALog.i("awcn.BandWidthSampler", "NetworkSpeed notification!", null, "Send Network quality notification.");
                }
                b.d = 0;
                b.e = 0;
                b.a = 0;
            }
        }
    }
}
