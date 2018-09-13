package com.feng.car.view.vhtableview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

public class HListViewScrollView extends HorizontalScrollView {
    private ScrollChangedListener listener;

    public interface ScrollChangedListener {
        HListViewScrollView getCurrentTouchView();

        void onUIScrollChanged(int i, int i2, int i3, int i4);

        void setCurrentTouchView(HListViewScrollView hListViewScrollView);
    }

    public HListViewScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public HListViewScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HListViewScrollView(Context context) {
        super(context);
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (this.listener != null) {
            this.listener.setCurrentTouchView(this);
        }
        return super.onTouchEvent(ev);
    }

    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (this.listener == null || this.listener.getCurrentTouchView() == null) {
            super.onScrollChanged(l, t, oldl, oldt);
        } else {
            this.listener.onUIScrollChanged(l, t, oldl, oldt);
        }
    }

    public void setScrollChangedListener(ScrollChangedListener listener) {
        this.listener = listener;
    }

    public ScrollChangedListener getListener() {
        return this.listener;
    }
}
