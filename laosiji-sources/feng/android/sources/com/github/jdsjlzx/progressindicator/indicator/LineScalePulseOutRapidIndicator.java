package com.github.jdsjlzx.progressindicator.indicator;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import java.util.ArrayList;
import java.util.List;

public class LineScalePulseOutRapidIndicator extends LineScaleIndicator {
    public List<Animator> createAnimation() {
        List<Animator> animators = new ArrayList();
        long[] delays = new long[]{400, 200, 0, 200, 400};
        for (int i = 0; i < 5; i++) {
            final int index = i;
            ValueAnimator scaleAnim = ValueAnimator.ofFloat(new float[]{1.0f, 0.4f, 1.0f});
            scaleAnim.setDuration(1000);
            scaleAnim.setRepeatCount(-1);
            scaleAnim.setStartDelay(delays[i]);
            scaleAnim.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    LineScalePulseOutRapidIndicator.this.scaleYFloats[index] = ((Float) animation.getAnimatedValue()).floatValue();
                    LineScalePulseOutRapidIndicator.this.postInvalidate();
                }
            });
            scaleAnim.start();
            animators.add(scaleAnim);
        }
        return animators;
    }
}
