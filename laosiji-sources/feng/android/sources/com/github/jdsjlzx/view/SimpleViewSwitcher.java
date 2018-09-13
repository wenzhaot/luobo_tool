package com.github.jdsjlzx.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class SimpleViewSwitcher extends ViewGroup {
    public SimpleViewSwitcher(Context context) {
        super(context);
    }

    public SimpleViewSwitcher(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleViewSwitcher(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childCount = getChildCount();
        int maxHeight = 0;
        int maxWidth = 0;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            int cw = child.getMeasuredWidth();
            maxWidth = child.getMeasuredWidth();
            maxHeight = child.getMeasuredHeight();
        }
        setMeasuredDimension(maxWidth, maxHeight);
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != 8) {
                child.layout(0, 0, r - l, b - t);
            }
        }
    }

    public void setView(View view) {
        if (getChildCount() != 0) {
            removeViewAt(0);
        }
        addView(view, 0);
    }
}
