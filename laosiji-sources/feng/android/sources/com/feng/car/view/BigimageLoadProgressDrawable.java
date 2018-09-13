package com.feng.car.view;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import com.facebook.drawee.drawable.DrawableUtils;

public class BigimageLoadProgressDrawable extends Drawable {
    private boolean mHideWhenZero = false;
    private int mLevel = 0;
    private final Paint mPaint = new Paint(1);
    private OnValueChangedListener onValueChangedListener;

    public void setHideWhenZero(boolean hideWhenZero) {
        this.mHideWhenZero = hideWhenZero;
    }

    public boolean getHideWhenZero() {
        return this.mHideWhenZero;
    }

    protected boolean onLevelChange(int level) {
        this.mLevel = level;
        if (!(this.mHideWhenZero && this.mLevel == 0)) {
            this.onValueChangedListener.valueChanged(getBounds(), this.mLevel);
        }
        return true;
    }

    public void setAlpha(int alpha) {
        this.mPaint.setAlpha(alpha);
    }

    public void setColorFilter(ColorFilter cf) {
        this.mPaint.setColorFilter(cf);
    }

    public int getOpacity() {
        return DrawableUtils.getOpacityFromColor(this.mPaint.getColor());
    }

    public void draw(Canvas canvas) {
    }

    public BigimageLoadProgressDrawable(OnValueChangedListener onValueChangedListener) {
        this.onValueChangedListener = onValueChangedListener;
    }

    public void setOnValueChangedListener(OnValueChangedListener onValueChangedListener) {
        this.onValueChangedListener = onValueChangedListener;
    }
}
