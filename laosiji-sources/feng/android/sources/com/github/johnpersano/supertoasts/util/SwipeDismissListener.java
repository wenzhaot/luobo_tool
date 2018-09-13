package com.github.johnpersano.supertoasts.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewPropertyAnimator;

@SuppressLint({"NewApi"})
public class SwipeDismissListener implements OnTouchListener {
    private boolean isSwiping;
    private float mActionDownXCoordinate;
    private final long mAnimationTime;
    private final OnDismissCallback mCallback;
    private final int mMaxFlingVelocity;
    private final int mMinFlingVelocity;
    private final int mScaledTouchSlop;
    private float mTranslationX;
    private VelocityTracker mVelocityTracker;
    private final View mView;

    public interface OnDismissCallback {
        void onDismiss(View view);
    }

    public SwipeDismissListener(View view, OnDismissCallback callback) {
        ViewConfiguration mViewConfiguration = ViewConfiguration.get(view.getContext());
        this.mScaledTouchSlop = mViewConfiguration.getScaledTouchSlop();
        this.mMinFlingVelocity = mViewConfiguration.getScaledMinimumFlingVelocity();
        this.mMaxFlingVelocity = mViewConfiguration.getScaledMaximumFlingVelocity();
        this.mAnimationTime = (long) view.getContext().getResources().getInteger(17694720);
        this.mView = view;
        this.mCallback = callback;
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        motionEvent.offsetLocation(this.mTranslationX, 0.0f);
        int mViewWidth = this.mView.getWidth();
        switch (motionEvent.getActionMasked()) {
            case 0:
                this.mActionDownXCoordinate = motionEvent.getRawX();
                this.mVelocityTracker = VelocityTracker.obtain();
                this.mVelocityTracker.addMovement(motionEvent);
                view.onTouchEvent(motionEvent);
                return true;
            case 1:
                if (this.mVelocityTracker != null) {
                    float deltaXActionUp = motionEvent.getRawX() - this.mActionDownXCoordinate;
                    this.mVelocityTracker.addMovement(motionEvent);
                    this.mVelocityTracker.computeCurrentVelocity(1000);
                    float velocityX = Math.abs(this.mVelocityTracker.getXVelocity());
                    float velocityY = Math.abs(this.mVelocityTracker.getYVelocity());
                    boolean dismiss = false;
                    boolean dismissRight = false;
                    if (Math.abs(deltaXActionUp) > ((float) (mViewWidth / 2))) {
                        dismiss = true;
                        dismissRight = deltaXActionUp > 0.0f;
                    } else if (((float) this.mMinFlingVelocity) <= velocityX && velocityX <= ((float) this.mMaxFlingVelocity) && velocityY < velocityX) {
                        dismiss = true;
                        dismissRight = this.mVelocityTracker.getXVelocity() > 0.0f;
                    }
                    if (dismiss) {
                        float f;
                        ViewPropertyAnimator animate = this.mView.animate();
                        if (dismissRight) {
                            f = (float) mViewWidth;
                        } else {
                            f = (float) (-mViewWidth);
                        }
                        animate.translationX(f).alpha(0.0f).setDuration(this.mAnimationTime).setListener(new AnimatorListenerAdapter() {
                            public void onAnimationEnd(Animator animation) {
                                SwipeDismissListener.this.performDismiss();
                            }
                        });
                    } else {
                        this.mView.animate().translationX(0.0f).alpha(1.0f).setDuration(this.mAnimationTime).setListener(null);
                    }
                    this.mVelocityTracker.recycle();
                    this.mTranslationX = 0.0f;
                    this.mActionDownXCoordinate = 0.0f;
                    this.isSwiping = false;
                    break;
                }
                break;
            case 2:
                if (this.mVelocityTracker != null) {
                    this.mVelocityTracker.addMovement(motionEvent);
                    float deltaXActionMove = motionEvent.getRawX() - this.mActionDownXCoordinate;
                    if (Math.abs(deltaXActionMove) > ((float) this.mScaledTouchSlop)) {
                        this.isSwiping = true;
                        this.mView.getParent().requestDisallowInterceptTouchEvent(true);
                        MotionEvent cancelEvent = MotionEvent.obtain(motionEvent);
                        cancelEvent.setAction((motionEvent.getActionIndex() << 8) | 3);
                        this.mView.onTouchEvent(cancelEvent);
                        cancelEvent.recycle();
                    }
                    if (this.isSwiping) {
                        this.mTranslationX = deltaXActionMove;
                        this.mView.setTranslationX(deltaXActionMove);
                        this.mView.setAlpha(Math.max(0.0f, Math.min(1.0f, 1.0f - ((2.0f * Math.abs(deltaXActionMove)) / ((float) mViewWidth)))));
                        return true;
                    }
                }
                break;
        }
        return false;
    }

    private void performDismiss() {
        final LayoutParams lp = this.mView.getLayoutParams();
        int originalHeight = this.mView.getHeight();
        ValueAnimator animator = ValueAnimator.ofInt(new int[]{originalHeight, 1}).setDuration(this.mAnimationTime);
        animator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                SwipeDismissListener.this.mCallback.onDismiss(SwipeDismissListener.this.mView);
            }
        });
        animator.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                lp.height = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                SwipeDismissListener.this.mView.setLayoutParams(lp);
            }
        });
        animator.start();
    }
}
