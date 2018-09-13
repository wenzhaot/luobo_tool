package com.feng.car.view.largeimage;

import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

class ScaleHelper {
    private int mDuration;
    private boolean mFinished = true;
    private Interpolator mInterpolator;
    private float mScale;
    private long mStartTime;
    private int mStartX;
    private int mStartY;
    private float mToScale;

    ScaleHelper() {
    }

    public void startScale(float scale, float toScale, int x, int y, Interpolator interpolator) {
        float d;
        this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
        this.mInterpolator = interpolator;
        this.mScale = scale;
        this.mToScale = toScale;
        this.mStartX = x;
        this.mStartY = y;
        if (toScale > scale) {
            d = toScale / scale;
        } else {
            d = scale / toScale;
        }
        if (d > 4.0f) {
            d = 4.0f;
        }
        this.mDuration = (int) (220.0d + Math.sqrt((double) (3600.0f * d)));
        this.mFinished = false;
    }

    public boolean computeScrollOffset() {
        if (isFinished()) {
            return false;
        }
        long elapsedTime = AnimationUtils.currentAnimationTimeMillis() - this.mStartTime;
        int duration = this.mDuration;
        if (elapsedTime < ((long) duration)) {
            this.mScale += (this.mToScale - this.mScale) * this.mInterpolator.getInterpolation(((float) elapsedTime) / ((float) duration));
            return true;
        }
        this.mScale = this.mToScale;
        this.mFinished = true;
        return true;
    }

    private boolean isFinished() {
        return this.mFinished;
    }

    public float getCurScale() {
        return this.mScale;
    }

    public int getStartX() {
        return this.mStartX;
    }

    public int getStartY() {
        return this.mStartY;
    }
}
