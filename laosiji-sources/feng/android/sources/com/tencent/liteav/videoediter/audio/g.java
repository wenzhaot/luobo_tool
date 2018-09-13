package com.tencent.liteav.videoediter.audio;

/* compiled from: TXMixerHelper */
public class g {
    private volatile float a = 1.0f;
    private volatile float b = 1.0f;

    public void a(float f) {
        this.a = f;
    }

    public void b(float f) {
        this.b = f;
    }

    public short[] a(short[] sArr, short[] sArr2) {
        for (int i = 0; i < sArr.length; i++) {
            short s;
            int i2 = (int) ((((float) sArr[i]) * this.b) + (((float) sArr2[i]) * this.a));
            if (i2 > 32767) {
                s = Short.MAX_VALUE;
            } else if (i2 < -32768) {
                s = Short.MIN_VALUE;
            } else {
                s = (short) i2;
            }
            sArr[i] = s;
        }
        return sArr;
    }
}
