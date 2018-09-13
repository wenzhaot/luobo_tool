package com.github.jdsjlzx.progressindicator.indicator;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.animation.LinearInterpolator;
import java.util.ArrayList;
import java.util.List;

public class BallScaleRippleMultipleIndicator extends BallScaleMultipleIndicator {
    public void draw(Canvas canvas, Paint paint) {
        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth(3.0f);
        super.draw(canvas, paint);
    }

    public List<Animator> createAnimation() {
        List<Animator> animators = new ArrayList();
        long[] delays = new long[]{0, 200, 400};
        for (int i = 0; i < 3; i++) {
            final int index = i;
            ValueAnimator scaleAnim = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
            scaleAnim.setInterpolator(new LinearInterpolator());
            scaleAnim.setDuration(1000);
            scaleAnim.setRepeatCount(-1);
            scaleAnim.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    BallScaleRippleMultipleIndicator.this.scaleFloats[index] = ((Float) animation.getAnimatedValue()).floatValue();
                    BallScaleRippleMultipleIndicator.this.postInvalidate();
                }
            });
            scaleAnim.setStartDelay(delays[i]);
            scaleAnim.start();
            ValueAnimator alphaAnim = ValueAnimator.ofInt(new int[]{0, 255});
            scaleAnim.setInterpolator(new LinearInterpolator());
            alphaAnim.setDuration(1000);
            alphaAnim.setRepeatCount(-1);
            alphaAnim.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    BallScaleRippleMultipleIndicator.this.alphaInts[index] = ((Integer) animation.getAnimatedValue()).intValue();
                    BallScaleRippleMultipleIndicator.this.postInvalidate();
                }
            });
            scaleAnim.setStartDelay(delays[i]);
            alphaAnim.start();
            animators.add(scaleAnim);
            animators.add(alphaAnim);
        }
        return animators;
    }
}
