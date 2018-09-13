package com.tencent.liteav.videoediter.audio;

import com.tencent.liteav.basic.log.TXCLog;

public class TXSkpResample {
    private static final String TAG = "TXSkpResample";
    private long handle;
    private volatile boolean isInitSuccess;

    private final native int nativeInit(int i, int i2);

    private final native short[] resample(long j, short[] sArr);

    private final native void uninit(long j);

    public synchronized void init(int i, int i2) {
        if (this.isInitSuccess) {
            destroy();
        }
        this.handle = (long) nativeInit(i, i2);
        this.isInitSuccess = this.handle != -1;
    }

    public synchronized short[] doResample(short[] sArr) {
        if (this.isInitSuccess) {
            sArr = resample(this.handle, sArr);
        } else {
            TXCLog.e(TAG, " you should nativeInit this object first");
        }
        return sArr;
    }

    public synchronized void destroy() {
        if (this.isInitSuccess) {
            this.isInitSuccess = false;
            uninit(this.handle);
        }
    }
}
