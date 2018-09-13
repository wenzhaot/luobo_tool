package com.feng.car.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Scroller;
import java.util.LinkedList;
import java.util.List;

public class SwipeBackLayout extends FrameLayout {
    private boolean canScroll;
    private int downX;
    private int downY;
    private boolean isFinish;
    private boolean isSilding;
    private Activity mActivity;
    private BackLayoutFinishCallBack mCallBack;
    private View mContentView;
    private boolean mIsVehicle;
    private Scroller mScroller;
    private int mTouchSlop;
    private List<ViewPager> mViewPagers;
    private int tempX;
    private int viewWidth;

    public void setCanScroll(boolean canScroll) {
        this.canScroll = canScroll;
    }

    public void setIsVehicle(boolean isVehicle) {
        this.mIsVehicle = isVehicle;
    }

    public void setmCallBack(BackLayoutFinishCallBack callBack) {
        this.mCallBack = callBack;
    }

    public SwipeBackLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.canScroll = true;
        this.mIsVehicle = false;
        this.mViewPagers = new LinkedList();
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop() * 2;
        this.mScroller = new Scroller(context);
    }

    public void attachToActivity(Activity activity) {
        this.mActivity = activity;
        TypedArray a = activity.getTheme().obtainStyledAttributes(new int[]{16842836});
        int background = a.getResourceId(0, 0);
        a.recycle();
        ViewGroup decor = (ViewGroup) activity.getWindow().getDecorView();
        ViewGroup decorChild = (ViewGroup) decor.getChildAt(0);
        decorChild.setBackgroundResource(background);
        decor.removeView(decorChild);
        addView(decorChild);
        setContentView(decorChild);
        decor.addView(this);
    }

    private void setContentView(View decorChild) {
        this.mContentView = (View) decorChild.getParent();
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        ViewPager mViewPager = getTouchViewPager(this.mViewPagers, ev);
        if (mViewPager != null && mViewPager.getCurrentItem() != 0) {
            return super.onInterceptTouchEvent(ev);
        }
        switch (ev.getAction()) {
            case 0:
                if (this.canScroll) {
                    int rawX = (int) ev.getRawX();
                    this.tempX = rawX;
                    this.downX = rawX;
                    this.downY = (int) ev.getRawY();
                    break;
                }
                return false;
            case 2:
                if (!this.canScroll) {
                    return false;
                }
                if (((int) ev.getRawX()) - this.downX > this.mTouchSlop && Math.abs(((int) ev.getRawY()) - this.downY) < this.mTouchSlop) {
                    return true;
                }
        }
        return super.onInterceptTouchEvent(ev);
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case 1:
                if (this.canScroll) {
                    this.isSilding = false;
                    scrollRightOrOrigin();
                    break;
                }
                return false;
            case 2:
                if (this.canScroll) {
                    int moveX = (int) event.getRawX();
                    int deltaX = this.tempX - moveX;
                    this.tempX = moveX;
                    if (moveX - this.downX > this.mTouchSlop && Math.abs(((int) event.getRawY()) - this.downY) < this.mTouchSlop) {
                        this.isSilding = true;
                    }
                    if (moveX - this.downX >= 0 && this.isSilding) {
                        this.mContentView.scrollBy(deltaX, 0);
                        break;
                    }
                }
                return false;
        }
        return true;
    }

    public void scrollRightOrOrigin() {
        if (!this.canScroll) {
            return;
        }
        if (this.mContentView == null || this.mContentView.getScrollX() > (-this.viewWidth) / 4) {
            scrollOrigin();
            this.isFinish = false;
            return;
        }
        this.isFinish = true;
        scrollRight();
    }

    private void getAlLViewPager(List<ViewPager> mViewPagers, ViewGroup parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            if (child instanceof ViewPager) {
                mViewPagers.add((ViewPager) child);
            } else if (child instanceof ViewGroup) {
                getAlLViewPager(mViewPagers, (ViewGroup) child);
            }
        }
    }

    private ViewPager getTouchViewPager(List<ViewPager> mViewPagers, MotionEvent ev) {
        if (mViewPagers == null || mViewPagers.size() == 0) {
            return null;
        }
        Rect mRect = new Rect();
        for (ViewPager v : mViewPagers) {
            if (this.mIsVehicle) {
                v.getGlobalVisibleRect(mRect);
            } else {
                v.getHitRect(mRect);
            }
            if (mRect.contains((int) ev.getX(), (int) ev.getY())) {
                return v;
            }
        }
        return null;
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        try {
            super.onLayout(changed, l, t, r, b);
            if (changed) {
                this.viewWidth = getWidth();
                getAlLViewPager(this.mViewPagers, this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean getFinish() {
        return this.isFinish;
    }

    public void scrollRight() {
        if (this.isFinish) {
            if (this.mCallBack != null) {
                this.mCallBack.callBack();
            }
            this.mActivity.finish();
        }
    }

    public void scrollOrigin() {
        if (this.mContentView != null) {
            int delta = this.mContentView.getScrollX();
            this.mScroller.startScroll(this.mContentView.getScrollX(), 0, -delta, 0, Math.abs(delta));
            postInvalidate();
        }
    }

    public void computeScroll() {
        if (this.mScroller.computeScrollOffset()) {
            this.mContentView.scrollTo(this.mScroller.getCurrX(), this.mScroller.getCurrY());
            postInvalidate();
        }
    }
}
