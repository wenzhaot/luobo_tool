package com.umeng.social.tool;

import android.graphics.Bitmap;

public class UMImageMark extends UMWaterMark {
    private Bitmap mMarkBitmap;

    public void setMarkBitmap(Bitmap markBitmap) {
        this.mMarkBitmap = markBitmap;
    }

    Bitmap getMarkBitmap() {
        return this.mMarkBitmap;
    }
}
