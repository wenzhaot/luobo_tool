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

public class BallScaleRippleIndicator extends BallScaleIndicator {
    public void draw(Canvas canvas, Paint paint) {
        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth(3.0f);
        super.draw(canvas, paint);
    }

    public List<Animator> createAnimation() {
        List<Animator> animators = new ArrayList();
        ValueAnimator scaleAnim = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        scaleAnim.setInterpolator(new LinearInterpolator());
        scaleAnim.setDuration(1000);
        scaleAnim.setRepeatCount(-1);
        scaleAnim.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                BallScaleRippleIndicator.this.scale = ((Float) animation.getAnimatedValue()).floatValue();
                BallScaleRippleIndicator.this.postInvalidate();
            }
        });
        scaleAnim.start();
        ValueAnimator alphaAnim = ValueAnimator.ofInt(new int[]{0, 255});
        alphaAnim.setInterpolator(new LinearInterpolator());
        alphaAnim.setDuration(1000);
        alphaAnim.setRepeatCount(-1);
        alphaAnim.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                BallScaleRippleIndicator.this.alpha = ((Integer) animation.getAnimatedValue()).intValue();
                BallScaleRippleIndicator.this.postInvalidate();
            }
        });
        alphaAnim.start();
        animators.add(scaleAnim);
        animators.add(alphaAnim);
        return animators;
    }
}
