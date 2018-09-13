package com.feng.car.view;

import android.content.Context;
import android.util.AttributeSet;
import com.github.jdsjlzx.recyclerview.LRecyclerView;

public class NewRecyclerView extends LRecyclerView {
    boolean isNestedEnable = false;

    public NewRecyclerView(Context context) {
        super(context);
    }

    public NewRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NewRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public boolean isNestedEnable() {
        return this.isNestedEnable;
    }

    public void setNestedEnable(boolean nestedEnable) {
        this.isNestedEnable = nestedEnable;
    }

    public boolean startNestedScroll(int axes, int type) {
        if (this.isNestedEnable) {
            return super.startNestedScroll(axes, type);
        }
        return false;
    }

    public void stopNestedScroll(int type) {
        if (this.isNestedEnable) {
            super.stopNestedScroll(type);
        }
    }

    public boolean hasNestedScrollingParent(int type) {
        if (this.isNestedEnable) {
            return super.hasNestedScrollingParent(type);
        }
        return false;
    }

    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow, int type) {
        if (this.isNestedEnable) {
            return super.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow, type);
        }
        return false;
    }

    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow, int type) {
        if (this.isNestedEnable) {
            return super.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, type);
        }
        return false;
    }
}
