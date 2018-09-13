package com.feng.car.view.behavior;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.AppBarLayout.Behavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class AppBarLayoutOverScrollViewBehavior extends Behavior {
    private static final String TAG = "overScroll";
    private static final String TAG_MIDDLE = "middle";
    private static final String TAG_TOOLBAR = "toolbar";
    private static final float TARGET_HEIGHT = 1500.0f;
    private final float MAX_REFRESH_LIMIT = 0.3f;
    private boolean isAnimate;
    private boolean isRecovering = false;
    private boolean mIsFling = false;
    private int mLastBottom;
    private float mLastScale;
    private int mMiddleHeight;
    private int mParentHeight;
    private boolean mResetFling = false;
    private View mTargetView;
    private int mTargetViewHeight;
    private Toolbar mToolBar;
    private float mTotalDy;
    private ViewGroup middleLayout;
    onProgressChangeListener onProgressChangeListener;

    public AppBarLayoutOverScrollViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean onLayoutChild(CoordinatorLayout parent, AppBarLayout abl, int layoutDirection) {
        boolean handled = super.onLayoutChild(parent, abl, layoutDirection);
        if (this.mToolBar == null) {
            this.mToolBar = (Toolbar) parent.findViewWithTag(TAG_TOOLBAR);
        }
        if (this.middleLayout == null) {
            this.middleLayout = (ViewGroup) parent.findViewWithTag(TAG_MIDDLE);
        }
        if (this.mTargetView == null) {
            this.mTargetView = parent.findViewWithTag(TAG);
            if (this.mTargetView != null) {
                initial(abl);
            }
        }
        return handled;
    }

    public boolean onStartNestedScroll(CoordinatorLayout parent, AppBarLayout child, View directTargetChild, View target, int nestedScrollAxes, int type) {
        this.isAnimate = true;
        if (target instanceof DisInterceptNestedScrollView) {
            return true;
        }
        return super.onStartNestedScroll(parent, child, directTargetChild, target, nestedScrollAxes, type);
    }

    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dx, int dy, int[] consumed, int type) {
        if (this.isRecovering || this.mTargetView == null || ((dy >= 0 || child.getBottom() < this.mParentHeight) && (dy <= 0 || child.getBottom() <= this.mParentHeight))) {
            super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        } else if (!this.mIsFling) {
            scale(child, target, dy);
        } else if (!this.mResetFling) {
            resetFling();
        }
    }

    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, float velocityX, float velocityY) {
        if (velocityY > 100.0f) {
            this.isAnimate = false;
        }
        this.mIsFling = true;
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
    }

    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout abl, View target, int type) {
        recovery(abl);
        super.onStopNestedScroll(coordinatorLayout, abl, target, type);
    }

    private void initial(AppBarLayout abl) {
        abl.setClipChildren(false);
        this.mParentHeight = abl.getHeight();
        this.mTargetViewHeight = this.mTargetView.getHeight();
        this.mMiddleHeight = this.middleLayout.getHeight();
    }

    public void setParentHeight(int height) {
        this.mParentHeight = height;
        this.mMiddleHeight = this.middleLayout.getHeight();
    }

    private void scale(AppBarLayout abl, View target, int dy) {
        this.mTotalDy += (float) (-dy);
        this.mTotalDy = Math.min(this.mTotalDy, TARGET_HEIGHT);
        this.mLastScale = Math.max(1.0f, (this.mTotalDy / TARGET_HEIGHT) + 1.0f);
        ViewCompat.setScaleX(this.mTargetView, this.mLastScale);
        ViewCompat.setScaleY(this.mTargetView, this.mLastScale);
        this.mLastBottom = this.mParentHeight + ((int) (((float) (this.mTargetViewHeight / 2)) * (this.mLastScale - 1.0f)));
        abl.setBottom(this.mLastBottom);
        target.setScrollY(0);
        this.middleLayout.setTop(this.mLastBottom - this.mMiddleHeight);
        this.middleLayout.setBottom(this.mLastBottom);
        if (this.onProgressChangeListener != null) {
            this.onProgressChangeListener.onProgressChange(Math.min((this.mLastScale - 1.0f) / 0.3f, 1.0f), false);
        }
    }

    public void setOnProgressChangeListener(onProgressChangeListener onProgressChangeListener) {
        this.onProgressChangeListener = onProgressChangeListener;
    }

    private void recovery(final AppBarLayout abl) {
        if (!this.isRecovering && this.mTotalDy > 0.0f) {
            this.isRecovering = true;
            this.mTotalDy = 0.0f;
            if (this.isAnimate) {
                ValueAnimator anim = ValueAnimator.ofFloat(new float[]{this.mLastScale, 1.0f}).setDuration(200);
                anim.addUpdateListener(new AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float value = ((Float) animation.getAnimatedValue()).floatValue();
                        ViewCompat.setScaleX(AppBarLayoutOverScrollViewBehavior.this.mTargetView, value);
                        ViewCompat.setScaleY(AppBarLayoutOverScrollViewBehavior.this.mTargetView, value);
                        abl.setBottom((int) (((float) AppBarLayoutOverScrollViewBehavior.this.mLastBottom) - (((float) (AppBarLayoutOverScrollViewBehavior.this.mLastBottom - AppBarLayoutOverScrollViewBehavior.this.mParentHeight)) * animation.getAnimatedFraction())));
                        AppBarLayoutOverScrollViewBehavior.this.middleLayout.setTop((int) ((((float) AppBarLayoutOverScrollViewBehavior.this.mLastBottom) - (((float) (AppBarLayoutOverScrollViewBehavior.this.mLastBottom - AppBarLayoutOverScrollViewBehavior.this.mParentHeight)) * animation.getAnimatedFraction())) - ((float) AppBarLayoutOverScrollViewBehavior.this.mMiddleHeight)));
                        if (AppBarLayoutOverScrollViewBehavior.this.onProgressChangeListener != null) {
                            AppBarLayoutOverScrollViewBehavior.this.onProgressChangeListener.onProgressChange(Math.min((value - 1.0f) / 0.3f, 1.0f), true);
                        }
                    }
                });
                anim.addListener(new AnimatorListener() {
                    public void onAnimationStart(Animator animation) {
                    }

                    public void onAnimationEnd(Animator animation) {
                        AppBarLayoutOverScrollViewBehavior.this.reset();
                    }

                    public void onAnimationCancel(Animator animation) {
                    }

                    public void onAnimationRepeat(Animator animation) {
                    }
                });
                anim.start();
                return;
            }
            ViewCompat.setScaleX(this.mTargetView, 1.0f);
            ViewCompat.setScaleY(this.mTargetView, 1.0f);
            abl.setBottom(this.mParentHeight);
            this.middleLayout.setTop(this.mParentHeight - this.mMiddleHeight);
            reset();
            if (this.onProgressChangeListener != null) {
                this.onProgressChangeListener.onProgressChange(0.0f, true);
            }
        }
    }

    private void reset() {
        this.middleLayout.postDelayed(new Runnable() {
            public void run() {
                AppBarLayoutOverScrollViewBehavior.this.isRecovering = false;
            }
        }, 1000);
    }

    private void resetFling() {
        this.mResetFling = true;
        this.middleLayout.postDelayed(new Runnable() {
            public void run() {
                AppBarLayoutOverScrollViewBehavior.this.mIsFling = false;
                AppBarLayoutOverScrollViewBehavior.this.mResetFling = false;
            }
        }, 2000);
    }

    public boolean onInterceptTouchEvent(CoordinatorLayout parent, AppBarLayout child, MotionEvent ev) {
        return super.onInterceptTouchEvent(parent, child, ev);
    }
}
