package com.facebook.imagepipeline.animated.base;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;
import android.graphics.drawable.Animatable;

@TargetApi(11)
public interface AnimatableDrawable extends Animatable {
    AnimatorUpdateListener createAnimatorUpdateListener();

    ValueAnimator createValueAnimator();

    ValueAnimator createValueAnimator(int i);
}
