package com.tencent.liteav.c;

import android.graphics.Bitmap;

/* compiled from: StaticFilter */
public class h {
    private Bitmap a;

    public void a() {
        if (this.a != null && !this.a.isRecycled()) {
            this.a.recycle();
            this.a = null;
        }
    }
}
