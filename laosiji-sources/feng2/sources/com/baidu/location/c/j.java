package com.baidu.location.c;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import java.util.Timer;

public class j {
    private double A;
    private double B;
    private double C;
    private double D;
    private double E;
    private double F;
    private double G;
    private int H;
    private float I;
    private int J;
    private int K;
    private double[] L;
    private boolean M;
    Timer a;
    public SensorEventListener b;
    private a c;
    private SensorManager d;
    private boolean e;
    private int f;
    private Sensor g;
    private Sensor h;
    private final long i;
    private volatile int j;
    private int k;
    private float[] l;
    private float[] m;
    private double[] n;
    private int o;
    private double[] p;
    private int q;
    private int r;
    private int s;
    private double[] t;
    private int u;
    private double v;
    private int w;
    private long x;
    private int y;
    private int z;

    public interface a {
        void a(double d, double d2);
    }

    private j(Context context, int i) {
        this.i = 30;
        this.j = 1;
        this.k = 1;
        this.l = new float[3];
        this.m = new float[]{0.0f, 0.0f, 0.0f};
        this.n = new double[]{0.0d, 0.0d, 0.0d};
        this.o = 31;
        this.p = new double[this.o];
        this.q = 0;
        this.t = new double[6];
        this.u = 0;
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.A = 0.0d;
        this.B = 0.0d;
        this.C = 100.0d;
        this.D = 0.5d;
        this.E = this.D;
        this.F = 0.85d;
        this.G = 0.42d;
        this.H = -1;
        this.I = 0.0f;
        this.J = 20;
        this.K = 0;
        this.L = new double[this.J];
        this.M = false;
        this.b = new k(this);
        this.v = 1.6d;
        this.w = 440;
        try {
            this.d = (SensorManager) context.getSystemService("sensor");
            this.f = i;
            this.g = this.d.getDefaultSensor(1);
            this.h = this.d.getDefaultSensor(3);
        } catch (Exception e) {
        }
    }

    public j(Context context, a aVar) {
        this(context, 1);
        this.c = aVar;
    }

    private double a(double d, double d2, double d3) {
        double d4 = d2 - d;
        if (d4 < -180.0d) {
            d4 += 360.0d;
        } else if (d4 > 180.0d) {
            d4 -= 360.0d;
        }
        return (d4 * d3) + d;
    }

    private double a(double[] dArr) {
        int i = 0;
        double d = 0.0d;
        double d2 = 0.0d;
        for (double d3 : dArr) {
            d2 += d3;
        }
        d2 /= (double) r6;
        while (i < r6) {
            d += (dArr[i] - d2) * (dArr[i] - d2);
            i++;
        }
        return d / ((double) (r6 - 1));
    }

    private void a(double d) {
        this.t[this.u % 6] = d;
        this.u++;
        this.u %= 6;
    }

    private synchronized void a(int i) {
        this.k |= i;
    }

    private float[] a(float f, float f2, float f3) {
        float[] fArr = new float[]{(this.l[0] * 0.8f) + (0.19999999f * f), (this.l[1] * 0.8f) + (0.19999999f * f2), (this.l[2] * 0.8f) + (0.19999999f * f3)};
        fArr[0] = f - this.l[0];
        fArr[1] = f2 - this.l[1];
        fArr[2] = f3 - this.l[2];
        return fArr;
    }

    private boolean b(double d) {
        for (int i = 1; i <= 5; i++) {
            if (this.t[((((this.u - 1) - i) + 6) + 6) % 6] - this.t[((this.u - 1) + 6) % 6] > d) {
                return true;
            }
        }
        return false;
    }

    private boolean f() {
        for (int i = 0; i < this.J; i++) {
            if (this.L[i] > 1.0E-7d) {
                return true;
            }
        }
        return false;
    }

    static /* synthetic */ int g(j jVar) {
        int i = jVar.s + 1;
        jVar.s = i;
        return i;
    }

    private void g() {
        if (this.r >= 20) {
            long currentTimeMillis = System.currentTimeMillis();
            Object obj = new float[3];
            System.arraycopy(this.m, 0, obj, 0, 3);
            Object obj2 = new double[3];
            System.arraycopy(this.n, 0, obj2, 0, 3);
            double sqrt = Math.sqrt((double) ((obj[2] * obj[2]) + ((obj[0] * obj[0]) + (obj[1] * obj[1]))));
            this.p[this.q] = sqrt;
            a(sqrt);
            this.z++;
            if (sqrt > this.B) {
                this.B = sqrt;
            } else if (sqrt < this.C) {
                this.C = sqrt;
            }
            this.q++;
            if (this.q == this.o) {
                this.q = 0;
                double a = a(this.p);
                if (this.j != 0 || a >= 0.3d) {
                    a(1);
                    this.j = 1;
                } else {
                    a(0);
                    this.j = 0;
                }
            }
            if (currentTimeMillis - this.x > ((long) this.w) && b(this.v)) {
                this.y++;
                this.x = currentTimeMillis;
                double d = obj2[0];
                if (this.z >= 40 || this.z <= 0) {
                    this.E = this.D;
                } else {
                    this.E = Math.sqrt(Math.sqrt(this.B - this.C)) * this.G;
                    if (this.E > this.F) {
                        this.E = this.F;
                    } else if (this.E < this.D) {
                        this.E = this.D;
                    }
                }
                d += (double) this.I;
                if (d > 360.0d) {
                    d -= 360.0d;
                }
                if (d < 0.0d) {
                    d += 360.0d;
                }
                this.z = 1;
                this.B = sqrt;
                this.C = sqrt;
                if (this.M) {
                    this.c.a(this.E, d);
                }
            }
        }
    }

    public void a() {
        if (!this.e) {
            if (this.g != null) {
                try {
                    this.d.registerListener(this.b, this.g, this.f);
                } catch (Exception e) {
                }
                this.a = new Timer("UpdateData", false);
                this.a.schedule(new l(this), 500, 30);
                this.e = true;
            }
            if (this.h != null) {
                try {
                    this.d.registerListener(this.b, this.h, this.f);
                } catch (Exception e2) {
                }
            }
        }
    }

    public void b() {
        if (this.e) {
            try {
                this.d.unregisterListener(this.b);
            } catch (Exception e) {
            }
            this.a.cancel();
            this.a.purge();
            this.a = null;
            this.e = false;
        }
    }

    public synchronized int c() {
        return this.r < 20 ? 1 : this.k;
    }

    public synchronized int d() {
        return this.r < 20 ? -1 : this.y;
    }

    public synchronized void e() {
        this.k = 0;
    }
}
