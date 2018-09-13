package com.baidu.location.a;

import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import com.baidu.location.f;
import com.baidu.location.g.a;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class k implements SensorEventListener {
    private static k d;
    private float[] a;
    private float[] b;
    private SensorManager c;
    private float e;
    private double f = Double.MIN_VALUE;
    private boolean g = false;
    private boolean h = false;
    private boolean i = false;
    private boolean j = false;
    private float k = 0.0f;
    private long l = 0;
    private Map<Integer, List<Float>> m = new ConcurrentHashMap();
    private boolean n = false;
    private long o = 0;
    private boolean p = false;

    private k() {
        try {
            if (this.c == null) {
                this.c = (SensorManager) f.getServiceContext().getSystemService("sensor");
            }
            if (this.c.getDefaultSensor(6) != null) {
                this.j = true;
            }
        } catch (Exception e) {
            this.j = false;
        }
    }

    public static synchronized k a() {
        k kVar;
        synchronized (k.class) {
            if (d == null) {
                d = new k();
            }
            kVar = d;
        }
        return kVar;
    }

    private void l() {
        if (this.c != null) {
            Sensor defaultSensor = this.c.getDefaultSensor(6);
            if (!(defaultSensor == null || this.p)) {
                this.c.registerListener(d, defaultSensor, 3);
                this.p = true;
            }
            if (!this.h) {
                a.a().postDelayed(new l(this), 2000);
            }
        }
    }

    public void a(boolean z) {
        this.g = z;
    }

    public synchronized void b() {
        if (!this.n) {
            if (this.g || this.i) {
                if (this.c == null) {
                    this.c = (SensorManager) f.getServiceContext().getSystemService("sensor");
                }
                if (this.c != null) {
                    Sensor defaultSensor = this.c.getDefaultSensor(11);
                    if (defaultSensor != null && this.g) {
                        this.c.registerListener(this, defaultSensor, 3);
                    }
                    defaultSensor = this.c.getDefaultSensor(6);
                    if (defaultSensor != null && this.i) {
                        this.c.registerListener(this, defaultSensor, 3);
                        this.p = true;
                    }
                }
                this.n = true;
            }
        }
    }

    public void b(boolean z) {
    }

    public synchronized void c() {
        if (this.n) {
            if (this.c != null) {
                this.c.unregisterListener(this);
                this.c = null;
                this.p = false;
            }
            this.n = false;
            this.k = 0.0f;
            this.m.clear();
        }
    }

    public void c(boolean z) {
        this.h = z;
        if (!z) {
            if (this.c != null) {
                this.c.unregisterListener(d, this.c.getDefaultSensor(6));
                this.p = false;
            }
            this.m.clear();
        }
    }

    public void d() {
        if (!this.i && this.j) {
            if (this.h || System.currentTimeMillis() - this.o > 60000) {
                this.o = System.currentTimeMillis();
                l();
            }
        }
    }

    public float e() {
        return (!this.j || this.l <= 0) ? 0.0f : this.h ? f() : this.k > 0.0f ? this.k : 0.0f;
    }

    /* JADX WARNING: Missing block: B:49:?, code:
            return r1;
     */
    public float f() {
        /*
        r7 = this;
        r2 = 0;
        monitor-enter(r7);	 Catch:{ Exception -> 0x007f }
        r0 = r7.j;	 Catch:{ all -> 0x0078 }
        if (r0 == 0) goto L_0x0089;
    L_0x0006:
        r0 = r7.l;	 Catch:{ all -> 0x0078 }
        r4 = 0;
        r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1));
        if (r0 <= 0) goto L_0x0089;
    L_0x000e:
        r0 = r7.m;	 Catch:{ all -> 0x0078 }
        r0 = r0.size();	 Catch:{ all -> 0x0078 }
        if (r0 <= 0) goto L_0x0089;
    L_0x0016:
        r1 = 0;
        r0 = r7.m;	 Catch:{ all -> 0x0078 }
        r0 = r0.keySet();	 Catch:{ all -> 0x0078 }
        r3 = r0.iterator();	 Catch:{ all -> 0x0078 }
    L_0x0021:
        r0 = r3.hasNext();	 Catch:{ all -> 0x0078 }
        if (r0 == 0) goto L_0x0039;
    L_0x0027:
        r0 = r3.next();	 Catch:{ all -> 0x0078 }
        r0 = (java.lang.Integer) r0;	 Catch:{ all -> 0x0078 }
        r4 = r0.intValue();	 Catch:{ all -> 0x0078 }
        if (r4 <= r1) goto L_0x008b;
    L_0x0033:
        r0 = r0.intValue();	 Catch:{ all -> 0x0078 }
    L_0x0037:
        r1 = r0;
        goto L_0x0021;
    L_0x0039:
        r0 = r7.m;	 Catch:{ all -> 0x0078 }
        r3 = java.lang.Integer.valueOf(r1);	 Catch:{ all -> 0x0078 }
        r0 = r0.get(r3);	 Catch:{ all -> 0x0078 }
        if (r0 == 0) goto L_0x0089;
    L_0x0045:
        r0 = r7.m;	 Catch:{ all -> 0x0078 }
        r1 = java.lang.Integer.valueOf(r1);	 Catch:{ all -> 0x0078 }
        r0 = r0.get(r1);	 Catch:{ all -> 0x0078 }
        r0 = (java.util.List) r0;	 Catch:{ all -> 0x0078 }
        r4 = r0.iterator();	 Catch:{ all -> 0x0078 }
        r3 = r2;
    L_0x0056:
        r1 = r4.hasNext();	 Catch:{ all -> 0x0078 }
        if (r1 == 0) goto L_0x0069;
    L_0x005c:
        r1 = r4.next();	 Catch:{ all -> 0x0078 }
        r1 = (java.lang.Float) r1;	 Catch:{ all -> 0x0078 }
        r1 = r1.floatValue();	 Catch:{ all -> 0x0078 }
        r1 = r1 + r3;
        r3 = r1;
        goto L_0x0056;
    L_0x0069:
        r0 = r0.size();	 Catch:{ all -> 0x0078 }
        r0 = (float) r0;
        r2 = r3 / r0;
        r0 = r2;
    L_0x0071:
        r1 = r7.m;	 Catch:{ all -> 0x0082 }
        r1.clear();	 Catch:{ all -> 0x0082 }
        monitor-exit(r7);	 Catch:{ all -> 0x0082 }
    L_0x0077:
        return r0;
    L_0x0078:
        r0 = move-exception;
        r1 = r2;
    L_0x007a:
        monitor-exit(r7);	 Catch:{ all -> 0x0087 }
        throw r0;	 Catch:{ Exception -> 0x007c }
    L_0x007c:
        r0 = move-exception;
        r0 = r1;
        goto L_0x0077;
    L_0x007f:
        r0 = move-exception;
        r0 = r2;
        goto L_0x0077;
    L_0x0082:
        r1 = move-exception;
        r6 = r1;
        r1 = r0;
        r0 = r6;
        goto L_0x007a;
    L_0x0087:
        r0 = move-exception;
        goto L_0x007a;
    L_0x0089:
        r0 = r2;
        goto L_0x0071;
    L_0x008b:
        r0 = r1;
        goto L_0x0037;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.location.a.k.f():float");
    }

    public boolean g() {
        return this.g;
    }

    public boolean h() {
        return this.i;
    }

    public float i() {
        return this.e;
    }

    public double j() {
        return this.f;
    }

    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    @SuppressLint({"NewApi"})
    public void onSensorChanged(SensorEvent sensorEvent) {
        switch (sensorEvent.sensor.getType()) {
            case 6:
                try {
                    this.b = (float[]) sensorEvent.values.clone();
                    this.k = this.b[0];
                    this.l = System.currentTimeMillis();
                    if (this.h) {
                        int i = (int) (this.l / 1000);
                        if (this.m.get(Integer.valueOf(i)) == null) {
                            this.m.put(Integer.valueOf(i), new ArrayList());
                        }
                        ((List) this.m.get(Integer.valueOf(i))).add(Float.valueOf(this.k));
                    }
                    this.f = (double) SensorManager.getAltitude(1013.25f, this.b[0]);
                    return;
                } catch (Exception e) {
                    return;
                }
            case 11:
                this.a = (float[]) sensorEvent.values.clone();
                if (this.a != null) {
                    float[] fArr = new float[9];
                    try {
                        SensorManager.getRotationMatrixFromVector(fArr, this.a);
                        float[] fArr2 = new float[3];
                        SensorManager.getOrientation(fArr, fArr2);
                        this.e = (float) Math.toDegrees((double) fArr2[0]);
                        this.e = (float) Math.floor(this.e >= 0.0f ? (double) this.e : (double) (this.e + 360.0f));
                        return;
                    } catch (Exception e2) {
                        this.e = 0.0f;
                        return;
                    }
                }
                return;
            default:
                return;
        }
    }
}
