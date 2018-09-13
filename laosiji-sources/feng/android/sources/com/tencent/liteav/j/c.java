package com.tencent.liteav.j;

import com.feng.car.utils.FengConstant;
import com.tencent.liteav.basic.log.TXCLog;

/* compiled from: TXResolutionUtils */
public class c {
    public static int[] a(int i, int i2, int i3) {
        int i4 = FengConstant.IMAGEMIDDLEWIDTH;
        if (i2 <= 0 || i3 <= 0) {
            TXCLog.w("GlUtil", "no input size. " + i2 + "*" + i3);
            return new int[]{i2, i3};
        }
        int i5;
        float f = (((float) i2) * 1.0f) / ((float) i3);
        if (i == 0) {
            if ((i2 <= FengConstant.IMAGEMIDDLEWIDTH && i3 <= 360) || (i2 <= 360 && i3 <= FengConstant.IMAGEMIDDLEWIDTH)) {
                TXCLog.d("GlUtil", "target size: " + i2 + "*" + i3);
                return new int[]{i2, i3};
            } else if (i2 >= i3) {
                i5 = (int) (360.0f * f);
                if (i5 < FengConstant.IMAGEMIDDLEWIDTH) {
                    i4 = i5;
                }
                i3 = (int) (((float) i4) / f);
            } else {
                i4 = (int) (640.0f * f);
                if (i4 >= 360) {
                    i4 = 360;
                }
                i3 = (int) (((float) i4) / f);
            }
        } else if (i == 1) {
            if ((i2 <= FengConstant.IMAGEMIDDLEWIDTH && i3 <= 480) || (i2 <= 480 && i3 <= FengConstant.IMAGEMIDDLEWIDTH)) {
                TXCLog.d("GlUtil", "target size: " + i2 + "*" + i3);
                return new int[]{i2, i3};
            } else if (i2 >= i3) {
                i5 = (int) (480.0f * f);
                if (i5 < FengConstant.IMAGEMIDDLEWIDTH) {
                    i4 = i5;
                }
                i3 = (int) (((float) i4) / f);
            } else {
                i4 = (int) (640.0f * f);
                if (i4 >= 480) {
                    i4 = 480;
                }
                i3 = (int) (((float) i4) / f);
            }
        } else if (i == 2) {
            if ((i2 <= 960 && i3 <= 544) || (i2 <= 544 && i3 <= 960)) {
                TXCLog.d("GlUtil", "target size: " + i2 + "*" + i3);
                return new int[]{i2, i3};
            } else if (i2 >= i3) {
                i4 = (int) (544.0f * f);
                if (i4 >= 960) {
                    i4 = 960;
                }
                i3 = (int) (((float) i4) / f);
            } else {
                i4 = (int) (960.0f * f);
                if (i4 >= 544) {
                    i4 = 544;
                }
                i3 = (int) (((float) i4) / f);
            }
        } else if (i != 3) {
            i4 = i2;
        } else if ((i2 <= 1280 && i3 <= 720) || (i2 <= 720 && i3 <= 1280)) {
            TXCLog.d("GlUtil", "target size: " + i2 + "*" + i3);
            return new int[]{i2, i3};
        } else if (i2 >= i3) {
            i4 = (int) (720.0f * f);
            if (i4 >= 1280) {
                i4 = 1280;
            }
            i3 = (int) (((float) i4) / f);
        } else {
            i4 = (int) (1280.0f * f);
            if (i4 >= 720) {
                i4 = 720;
            }
            i3 = (int) (((float) i4) / f);
        }
        i5 = ((i4 + 1) >> 1) << 1;
        int i6 = ((i3 + 1) >> 1) << 1;
        return new int[]{i5, i6};
    }
}
