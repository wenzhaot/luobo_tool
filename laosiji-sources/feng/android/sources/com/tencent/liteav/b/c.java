package com.tencent.liteav.b;

import com.tencent.liteav.basic.log.TXCLog;

/* compiled from: CutTimeConfig */
public class c {
    private static c b;
    private final String a = "CutTimeConfig";
    private long c = -1;
    private long d = -1;

    public static c a() {
        if (b == null) {
            synchronized (c.class) {
                if (b == null) {
                    b = new c();
                }
            }
        }
        return b;
    }

    public void a(long j, long j2) {
        if (j < 0 || j2 < 0) {
            TXCLog.e("CutTimeConfig", "setCutTimeUs, startTimeUs or endTimeUs < 0");
        } else if (j >= j2) {
            TXCLog.e("CutTimeConfig", "setCutTimeUs, start time >= end time, ignore");
        } else {
            this.c = j;
            this.d = j2;
        }
    }

    public long b() {
        return this.c;
    }

    public long c() {
        return this.d;
    }

    public long d() {
        if (this.c < 0) {
            this.c = 0;
        }
        return this.c;
    }

    public long e() {
        if (this.d < 0) {
            this.d = 0;
        }
        return this.d;
    }

    public void f() {
        this.c = -1;
        this.d = -1;
    }
}
