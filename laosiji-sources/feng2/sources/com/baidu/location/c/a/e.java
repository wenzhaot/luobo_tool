package com.baidu.location.c.a;

final class e {
    double a;
    double b;
    int c = Integer.MAX_VALUE;
    int d = Integer.MAX_VALUE;
    int e = Integer.MAX_VALUE;

    e() {
    }

    private double a(double d, double d2) {
        return Math.sqrt((d * d) + (d2 * d2));
    }

    double a() {
        return a(this.a, this.b);
    }

    double a(e eVar) {
        return Math.sqrt(((this.a - eVar.a) * (this.a - eVar.a)) + ((this.b - eVar.b) * (this.b - eVar.b)));
    }
}
