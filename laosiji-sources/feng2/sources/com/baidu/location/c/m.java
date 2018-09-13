package com.baidu.location.c;

import android.os.Handler;
import com.baidu.location.BDLocation;

public class m {
    private a a;
    private long b = 450;
    private BDLocation c;
    private b d = null;
    private b e = null;
    private b f = new b();
    private b g = new b();
    private b h = new b();
    private b i = new b();
    private long j = -1;
    private boolean k = false;
    private Handler l = new Handler();
    private Runnable m = new n(this);

    public interface a {
        void a(BDLocation bDLocation);
    }

    private class b {
        public double a;
        public double b;

        public b() {
            this.a = 0.0d;
            this.b = 0.0d;
        }

        public b(double d, double d2) {
            this.a = d;
            this.b = d2;
        }

        public b(b bVar) {
            this.a = bVar.a;
            this.b = bVar.b;
        }

        public b a(double d) {
            return new b(this.a * d, this.b * d);
        }

        public b a(b bVar) {
            return new b(this.a - bVar.a, this.b - bVar.b);
        }

        public b b(b bVar) {
            return new b(this.a + bVar.a, this.b + bVar.b);
        }

        public boolean b(double d) {
            double abs = Math.abs(this.a);
            double abs2 = Math.abs(this.b);
            return abs > 0.0d && abs < d && abs2 > 0.0d && abs2 < d;
        }
    }

    private b a(b bVar) {
        if (this.d == null || bVar == null) {
            return null;
        }
        b a = this.d.a(bVar);
        this.i = this.i.b(a);
        b a2 = this.h.a(this.f);
        this.f = new b(this.h);
        this.h = new b(a);
        a = a.a(0.2d);
        b a3 = this.i.a(0.01d);
        return a.b(a3).b(a2.a(-0.02d));
    }

    public void a() {
        if (this.k) {
            this.k = false;
            this.l.removeCallbacks(this.m);
            b();
        }
    }

    public void a(long j) {
        this.b = j;
    }

    public void b() {
        this.j = -1;
        this.e = null;
        this.d = null;
        this.f = new b();
        this.g = new b();
        this.h = new b();
        this.i = new b();
    }

    public boolean c() {
        return this.k;
    }
}
