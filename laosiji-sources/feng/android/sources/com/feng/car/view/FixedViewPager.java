package com.feng.car.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

public class FixedViewPager extends ViewPager {
    private boolean isPointerDown = false;
    private float mDisplacementX;
    private float mDisplacementY;
    private boolean mEnableScroll = false;
    private float mInitialTx;
    private float mInitialTy;
    private boolean mIsLongImage = false;
    private boolean mIsLongImageScrollToBottom = false;
    private boolean mIsLongImageScrollToTop = false;
    private boolean mTracking;
    private OnSwipeListener onSwipeListener;

    public FixedViewPager(Context context) {
        super(context);
    }

    public FixedViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setEnableScroll(boolean enableScroll) {
        this.mEnableScroll = enableScroll;
    }

    public void setIsLongImage(boolean isLongImage) {
        this.mIsLongImage = isLongImage;
    }

    public void setIsLongImageScrollToBottom(boolean isBottom) {
        this.mIsLongImageScrollToBottom = isBottom;
    }

    public void setIsLongImageScrollToTop(boolean isTop) {
        this.mIsLongImageScrollToTop = isTop;
    }

    public void resetLongImageState() {
        this.mIsLongImage = false;
        this.mIsLongImageScrollToBottom = false;
        this.mIsLongImageScrollToTop = false;
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        boolean z = false;
        if (!this.mEnableScroll) {
            try {
                return super.dispatchTouchEvent(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (this.onSwipeListener != null) {
            switch (event.getActionMasked()) {
                case 0:
                    this.mDisplacementX = event.getRawX();
                    this.mDisplacementY = event.getRawY();
                    this.mInitialTy = getTranslationY();
                    this.mInitialTx = getTranslationX();
                    break;
                case 1:
                case 3:
                    this.isPointerDown = z;
                    if (this.mTracking) {
                        this.mTracking = z;
                        if (Math.abs(getTranslationY()) > ((float) (getHeight() / 6))) {
                            this.onSwipeListener.downSwipe();
                            break;
                        }
                        this.onSwipeListener.onReduction();
                        setViewDefault();
                    }
                    this.onSwipeListener.overSwipe();
                    break;
                case 2:
                    if (!this.isPointerDown) {
                        float deltaX = event.getRawX() - this.mDisplacementX;
                        float deltaY = event.getRawY() - this.mDisplacementY;
                        if ((Math.abs(deltaY) > ((float) ViewConfiguration.get(getContext()).getScaledTouchSlop()) && Math.abs(deltaY) > Math.abs(deltaX)) || this.mTracking) {
                            float mScale;
                            if (!this.mIsLongImage) {
                                if (Math.abs(deltaY) > 10.0f) {
                                    this.onSwipeListener.onSwiping(Math.abs(deltaY));
                                    this.mTracking = true;
                                    setTranslationY(this.mInitialTy + deltaY);
                                    setTranslationX(this.mInitialTx + deltaX);
                                    mScale = 1.0f - (Math.abs(deltaY) / ((float) getHeight()));
                                    if (((double) mScale) < 0.3d) {
                                        mScale = 0.3f;
                                    }
                                    setScaleX(mScale);
                                    setScaleY(mScale);
                                    break;
                                }
                            } else if (deltaY <= 0.0f) {
                                if (this.mIsLongImageScrollToBottom) {
                                    this.onSwipeListener.onSwiping(Math.abs(deltaY));
                                    this.mTracking = true;
                                    setTranslationY(this.mInitialTy + deltaY);
                                    setTranslationX(this.mInitialTx + deltaX);
                                    mScale = 1.0f - (Math.abs(deltaY) / ((float) getHeight()));
                                    if (((double) mScale) < 0.3d) {
                                        mScale = 0.3f;
                                    }
                                    setScaleX(mScale);
                                    setScaleY(mScale);
                                    break;
                                }
                            } else if (this.mIsLongImageScrollToTop) {
                                this.onSwipeListener.onSwiping(Math.abs(deltaY));
                                this.mTracking = true;
                                setTranslationY(this.mInitialTy + deltaY);
                                setTranslationX(this.mInitialTx + deltaX);
                                mScale = 1.0f - (Math.abs(deltaY) / ((float) getHeight()));
                                if (((double) mScale) < 0.3d) {
                                    mScale = 0.3f;
                                }
                                setScaleX(mScale);
                                setScaleY(mScale);
                                break;
                            }
                        }
                    }
                    break;
                case 5:
                    this.isPointerDown = true;
                    break;
            }
        }
        try {
            return super.dispatchTouchEvent(event);
        } catch (Exception e2) {
            e2.printStackTrace();
            return z;
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean z = false;
        if (this.mTracking) {
            return z;
        }
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (Exception e) {
            e.printStackTrace();
            return z;
        }
    }

    private void setViewDefault() {
        setTranslationX(0.0f);
        setTranslationY(0.0f);
        setScaleX(1.0f);
        setScaleY(1.0f);
    }

    public void setOnSwipeListener(OnSwipeListener onSwipeListener) {
        this.onSwipeListener = onSwipeListener;
    }
}
