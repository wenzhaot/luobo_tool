package com.tencent.liteav.b;

import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.c.g;
import com.tencent.liteav.i.a.k;
import java.util.ArrayList;
import java.util.List;

/* compiled from: ThumbnailConfig */
public class h {
    private static h a;
    private ArrayList<Long> b;
    private int c;
    private k d;
    private int e;
    private int f;

    public static h a() {
        if (a == null) {
            a = new h();
        }
        return a;
    }

    public void a(k kVar) {
        this.d = kVar;
    }

    public void a(ArrayList<Long> arrayList) {
        this.b = arrayList;
    }

    public void a(long j) {
        if (j >= 0) {
            this.b = new ArrayList();
            if (this.d != null && this.d.a > 0) {
                long j2;
                c a = c.a();
                long d = a.d();
                long e = a.e();
                TXCLog.i("ThumbnailConfig", "calculateThumbnailList startTimeUs : " + d + ", endTimeUs : " + e);
                if (e - d > 0) {
                    j2 = e - d;
                } else {
                    j2 = j;
                }
                long j3 = j2 / ((long) this.d.a);
                for (int i = 0; i < this.d.a; i++) {
                    long j4 = (((long) i) * j3) + d;
                    if (e <= 0 || e >= j) {
                        if (j4 > j) {
                            j4 = j;
                        }
                    } else if (j4 > e) {
                        j4 = e;
                    }
                    TXCLog.i("ThumbnailConfig", "calculateThumbnailList frameTime : " + j4);
                    this.b.add(Long.valueOf(j4));
                }
            }
        }
    }

    public List<Long> b() {
        return this.b;
    }

    public int c() {
        if (this.d == null) {
            return 0;
        }
        return this.d.a;
    }

    public g d() {
        g gVar = new g();
        if (this.d != null) {
            gVar.a = this.d.b;
            gVar.b = this.d.c;
        } else if (!(this.f == 0 || this.e == 0)) {
            gVar.b = this.f;
            gVar.a = this.e;
        }
        return gVar;
    }

    public boolean e() {
        return this.b == null || this.b.size() <= 0;
    }

    public long f() {
        return ((Long) this.b.get(0)).longValue();
    }

    public long g() {
        this.c++;
        return ((Long) this.b.remove(0)).longValue();
    }

    public int h() {
        return this.c;
    }

    public void i() {
        this.c = 0;
        this.b = null;
    }

    public void j() {
        i();
        this.d = null;
    }

    public void a(int i) {
        this.e = i;
    }

    public void b(int i) {
        this.f = i;
    }
}
