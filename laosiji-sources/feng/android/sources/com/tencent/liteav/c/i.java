package com.tencent.liteav.c;

import android.graphics.Bitmap;
import com.tencent.liteav.i.a.g;

/* compiled from: TailWaterMark */
public class i extends j {
    private final int a = 3000;
    private int b;

    public i(Bitmap bitmap, g gVar, int i) {
        super(bitmap, gVar);
        this.b = i;
    }

    public int a() {
        return this.b;
    }

    public void b() {
        super.b();
        this.b = 0;
    }
}
