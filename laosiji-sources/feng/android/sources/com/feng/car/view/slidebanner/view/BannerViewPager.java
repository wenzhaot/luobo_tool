package com.feng.car.view.slidebanner.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class BannerViewPager extends ViewPager {
    private boolean scrollable = true;

    public BannerViewPager(Context context) {
        super(context);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return this.scrollable && super.onTouchEvent(ev);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return this.scrollable && super.onInterceptTouchEvent(ev);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void setScrollable(boolean scrollable) {
        this.scrollable = scrollable;
    }
}
