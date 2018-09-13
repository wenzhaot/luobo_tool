package com.tencent.liteav.c;

import android.graphics.Bitmap;
import com.tencent.liteav.i.a.g;

/* compiled from: WaterMark */
public class j {
    private Bitmap a;
    private g b;

    public j(Bitmap bitmap, g gVar) {
        this.a = bitmap;
        this.b = gVar;
    }

    public Bitmap c() {
        return this.a;
    }

    public g d() {
        return this.b;
    }

    public void b() {
        if (!(this.a == null || this.a.isRecycled())) {
            this.a.recycle();
            this.a = null;
        }
        this.b = null;
    }
}
