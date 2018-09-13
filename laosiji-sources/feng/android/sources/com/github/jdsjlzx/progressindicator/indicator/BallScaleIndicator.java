package com.github.jdsjlzx.progressindicator.indicator;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.animation.LinearInterpolator;
import java.util.ArrayList;
import java.util.List;

public class BallScaleIndicator extends BaseIndicatorController {
    int alpha = 255;
    float scale = 1.0f;

    public void draw(Canvas canvas, Paint paint) {
        paint.setAlpha(this.alpha);
        canvas.scale(this.scale, this.scale, (float) (getWidth() / 2), (float) (getHeight() / 2));
        paint.setAlpha(this.alpha);
        canvas.drawCircle((float) (getWidth() / 2), (float) (getHeight() / 2), ((float) (getWidth() / 2)) - 4.0f, paint);
    }

    public List<Animator> createAnimation() {
        List<Animator> animators = new ArrayList();
        ValueAnimator scaleAnim = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        scaleAnim.setInterpolator(new LinearInterpolator());
        scaleAnim.setDuration(1000);
        scaleAnim.setRepeatCount(-1);
        scaleAnim.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                BallScaleIndicator.this.scale = ((Float) animation.getAnimatedValue()).floatValue();
                BallScaleIndicator.this.postInvalidate();
            }
        });
        scaleAnim.start();
        ValueAnimator alphaAnim = ValueAnimator.ofInt(new int[]{255, 0});
        alphaAnim.setInterpolator(new LinearInterpolator());
        alphaAnim.setDuration(1000);
        alphaAnim.setRepeatCount(-1);
        alphaAnim.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                BallScaleIndicator.this.alpha = ((Integer) animation.getAnimatedValue()).intValue();
                BallScaleIndicator.this.postInvalidate();
            }
        });
        alphaAnim.start();
        animators.add(scaleAnim);
        animators.add(alphaAnim);
        return animators;
    }
}
