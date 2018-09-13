package com.feng.car.view.slidebanner;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

public class BannerScroller extends Scroller {
    private int mDuration = 800;

    public BannerScroller(Context context) {
        super(context);
    }

    public BannerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public BannerScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, this.mDuration);
    }

    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, this.mDuration);
    }

    public void setDuration(int time) {
        this.mDuration = time;
    }
}
