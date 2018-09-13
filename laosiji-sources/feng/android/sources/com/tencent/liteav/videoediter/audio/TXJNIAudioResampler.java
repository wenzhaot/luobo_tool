package com.tencent.liteav.videoediter.audio;

import com.tencent.liteav.basic.log.TXCLog;

public class TXJNIAudioResampler {
    private static final String TAG = "TXJNIAudioResampler";
    private long handle = ((long) init());
    private volatile boolean isInitSuccess;

    private native short[] flushBuffer(long j);

    private native int init();

    private native short[] resample(long j, short[] sArr);

    private native void setChannelCount(long j, int i);

    private native void setSampleRate(long j, int i, int i2);

    private native void setSpeed(long j, float f);

    private native void uninit(long j);

    public TXJNIAudioResampler() {
        if (this.handle != -1) {
            this.isInitSuccess = true;
        }
    }

    public synchronized void destroy() {
        if (this.isInitSuccess) {
            this.isInitSuccess = false;
            uninit(this.handle);
            this.handle = -1;
        }
    }

    public synchronized void setSpeed(float f) {
        if (this.isInitSuccess) {
            setSpeed(this.handle, f);
        } else {
            TXCLog.e(TAG, "you must init first!!!");
        }
    }

    public synchronized void setSampleRate(int i, int i2) {
        if (this.isInitSuccess) {
            setSampleRate(this.handle, i, i2);
        } else {
            TXCLog.e(TAG, "you must init first!!!");
        }
    }

    public synchronized void setChannelCount(int i) {
        if (this.isInitSuccess) {
            setChannelCount(this.handle, i);
        } else {
            TXCLog.e(TAG, "you must init first!!!");
        }
    }

    public synchronized short[] resample(short[] sArr) {
        if (this.isInitSuccess) {
            sArr = resample(this.handle, sArr);
        } else {
            TXCLog.e(TAG, "you must init first!!!");
        }
        return sArr;
    }

    public synchronized short[] flushBuffer() {
        short[] flushBuffer;
        if (this.isInitSuccess) {
            flushBuffer = flushBuffer(this.handle);
        } else {
            TXCLog.e(TAG, "you must init first!!!");
            flushBuffer = null;
        }
        return flushBuffer;
    }
}
