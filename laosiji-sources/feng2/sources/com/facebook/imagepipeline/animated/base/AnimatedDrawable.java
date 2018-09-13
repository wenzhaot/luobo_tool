package com.facebook.imagepipeline.animated.base;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;
import android.view.animation.LinearInterpolator;
import com.facebook.common.time.MonotonicClock;
import java.util.concurrent.ScheduledExecutorService;

@TargetApi(11)
public class AnimatedDrawable extends AbstractAnimatedDrawable implements AnimatableDrawable {
    public AnimatedDrawable(ScheduledExecutorService scheduledExecutorServiceForUiThread, AnimatedDrawableCachingBackend animatedDrawableBackend, AnimatedDrawableDiagnostics animatedDrawableDiagnostics, MonotonicClock monotonicClock) {
        super(scheduledExecutorServiceForUiThread, animatedDrawableBackend, animatedDrawableDiagnostics, monotonicClock);
    }

    public ValueAnimator createValueAnimator(int maxDurationMs) {
        ValueAnimator animator = createValueAnimator();
        animator.setRepeatCount(Math.max(maxDurationMs / getAnimatedDrawableBackend().getDurationMs(), 1));
        return animator;
    }

    public ValueAnimator createValueAnimator() {
        int loopCount = getAnimatedDrawableBackend().getLoopCount();
        ValueAnimator animator = new ValueAnimator();
        animator.setIntValues(new int[]{0, getDuration()});
        animator.setDuration((long) getDuration());
        if (loopCount == 0) {
            loopCount = -1;
        }
        animator.setRepeatCount(loopCount);
        animator.setRepeatMode(1);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(createAnimatorUpdateListener());
        return animator;
    }

    public AnimatorUpdateListener createAnimatorUpdateListener() {
        return new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                AnimatedDrawable.this.setLevel(((Integer) animation.getAnimatedValue()).intValue());
            }
        };
    }
}
