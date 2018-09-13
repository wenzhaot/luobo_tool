package com.github.jdsjlzx.progressindicator.indicator;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import java.util.ArrayList;
import java.util.List;

public class LineScalePulseOutIndicator extends LineScaleIndicator {
    public List<Animator> createAnimation() {
        List<Animator> animators = new ArrayList();
        long[] delays = new long[]{500, 250, 0, 250, 500};
        for (int i = 0; i < 5; i++) {
            final int index = i;
            ValueAnimator scaleAnim = ValueAnimator.ofFloat(new float[]{1.0f, 0.3f, 1.0f});
            scaleAnim.setDuration(900);
            scaleAnim.setRepeatCount(-1);
            scaleAnim.setStartDelay(delays[i]);
            scaleAnim.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    LineScalePulseOutIndicator.this.scaleYFloats[index] = ((Float) animation.getAnimatedValue()).floatValue();
                    LineScalePulseOutIndicator.this.postInvalidate();
                }
            });
            scaleAnim.start();
            animators.add(scaleAnim);
        }
        return animators;
    }
}
