package com.baidu.location.c;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

class k implements SensorEventListener {
    final /* synthetic */ j a;

    k(j jVar) {
        this.a = jVar;
    }

    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        float[] fArr;
        switch (sensorEvent.sensor.getType()) {
            case 1:
                fArr = (float[]) sensorEvent.values.clone();
                this.a.m = (float[]) fArr.clone();
                fArr = this.a.a(fArr[0], fArr[1], fArr[2]);
                if (j.a(this.a) >= 20) {
                    double d = (double) ((fArr[2] * fArr[2]) + ((fArr[0] * fArr[0]) + (fArr[1] * fArr[1])));
                    if (this.a.j == 0) {
                        if (d > 4.0d) {
                            this.a.j = 1;
                            return;
                        }
                        return;
                    } else if (d < 0.009999999776482582d) {
                        this.a.j = 0;
                        return;
                    } else {
                        return;
                    }
                }
                return;
            case 3:
                fArr = (float[]) sensorEvent.values.clone();
                this.a.L[this.a.K] = (double) fArr[0];
                this.a.K = this.a.K + 1;
                if (this.a.K == this.a.J) {
                    this.a.K = 0;
                }
                if (j.g(this.a) >= 20) {
                    this.a.M = this.a.f();
                    if (!this.a.M) {
                        this.a.d.unregisterListener(this.a.b, this.a.h);
                    }
                    this.a.n[0] = this.a.a(this.a.n[0], (double) fArr[0], 0.7d);
                    this.a.n[1] = (double) fArr[1];
                    this.a.n[2] = (double) fArr[2];
                    return;
                }
                return;
            default:
                return;
        }
    }
}
