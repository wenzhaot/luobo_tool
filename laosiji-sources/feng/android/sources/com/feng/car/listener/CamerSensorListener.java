package com.feng.car.listener;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class CamerSensorListener implements SensorEventListener {
    private Context mContext;
    private SensorListener mSensorListener;

    public CamerSensorListener(Context mContext, SensorListener mSensorListener) {
        this.mContext = mContext;
        this.mSensorListener = mSensorListener;
    }

    public void onSensorChanged(SensorEvent event) {
        if (this.mSensorListener != null) {
            this.mSensorListener.onChanged(event);
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
