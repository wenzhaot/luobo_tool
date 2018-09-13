package com.feng.car.view;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class MyLinearLayout extends LinearLayout {
    private boolean block;

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void enableBlock(boolean isBlock) {
        this.block = isBlock;
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return this.block || super.onInterceptTouchEvent(ev);
    }

    @BindingAdapter({"blockTouch"})
    public static void setBlock(MyLinearLayout ml, boolean block) {
        ml.enableBlock(block);
    }
}
