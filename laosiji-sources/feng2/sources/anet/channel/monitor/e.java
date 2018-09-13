package anet.channel.monitor;

/* compiled from: Taobao */
class e {
    private long a = 0;
    private double b;
    private double c;
    private double d;
    private double e;
    private double f;
    private double g;
    private double h;
    private double i = 0.0d;
    private double j = 0.0d;
    private double k = 0.0d;

    e() {
    }

    public double a(double d, double d2) {
        double d3 = d / d2;
        if (d3 >= 8.0d) {
            if (this.a == 0) {
                this.i = d3;
                this.h = this.i;
                this.d = this.h * 0.1d;
                this.c = this.h * 0.02d;
                this.e = (0.1d * this.h) * this.h;
            } else if (this.a == 1) {
                this.j = d3;
                this.h = this.j;
            } else {
                double d4 = d3 - this.j;
                this.i = this.j;
                this.j = d3;
                this.b = d3 / 0.95d;
                this.g = this.b - (this.h * 0.95d);
                int i = 0;
                double sqrt = Math.sqrt(this.d);
                if (this.g >= 4.0d * sqrt) {
                    i = 1;
                    this.g = (sqrt * 2.0d) + (0.75d * this.g);
                } else if (this.g <= -4.0d * sqrt) {
                    i = 2;
                    this.g = (sqrt * -1.0d) + (0.75d * this.g);
                }
                this.d = Math.min(Math.max(Math.abs((1.05d * this.d) - ((0.0025d * this.g) * this.g)), 0.8d * this.d), 1.25d * this.d);
                this.f = this.e / (((0.95d * 0.95d) * this.e) + this.d);
                this.h = ((d4 * (1.0d / 0.95d)) + this.h) + (this.f * this.g);
                if (i == 1) {
                    this.h = Math.min(this.h, this.b);
                } else if (i == 2) {
                    this.h = Math.max(this.h, this.b);
                }
                this.e = (1.0d - (0.95d * this.f)) * (this.e + this.c);
            }
            if (this.h < 0.0d) {
                this.k = this.j * 0.7d;
                this.h = this.k;
            } else {
                this.k = this.h;
            }
            return this.k;
        } else if (this.a != 0) {
            return this.k;
        } else {
            this.k = d3;
            return this.k;
        }
    }

    public void a() {
        this.a = 0;
        this.k = 0.0d;
    }
}
