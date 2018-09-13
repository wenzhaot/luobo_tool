package com.tencent.liteav.videoediter.audio;

import com.tencent.liteav.basic.log.TXCLog;

/* compiled from: TXChannelResample */
public class f {
    private int a;
    private int b;
    private volatile boolean c;

    public void a(int i, int i2) {
        if (this.a != i || this.b != i2) {
            this.c = true;
            this.a = i;
            this.b = i2;
            a();
        }
    }

    public short[] a(short[] sArr) {
        if (sArr == null || a() || this.a == this.b) {
            return sArr;
        }
        if (this.a == 2 && this.b == 1) {
            return c(sArr);
        }
        if (this.a == 1 && this.b == 2) {
            return b(sArr);
        }
        return sArr;
    }

    private boolean a() {
        if (!this.c) {
            TXCLog.e("FaceDetect", "you must set target channel count first");
            return true;
        } else if (this.a >= 1 && this.a <= 2 && this.b >= 1 && this.b <= 2) {
            return false;
        } else {
            TXCLog.e("FaceDetect", "channel count must between 1 and 2");
            return true;
        }
    }

    private short[] b(short[] sArr) {
        short[] sArr2 = new short[(sArr.length * 2)];
        for (int i = 0; i < sArr.length; i++) {
            sArr2[i * 2] = sArr[i];
            sArr2[(i * 2) + 1] = sArr[i];
        }
        return sArr2;
    }

    private short[] c(short[] sArr) {
        int i = 0;
        int length = sArr.length / 2;
        short[] sArr2 = new short[length];
        int i2 = 0;
        while (i2 < length) {
            sArr2[i2] = sArr[i];
            i2++;
            i = (i + 1) + 1;
        }
        return sArr2;
    }
}
