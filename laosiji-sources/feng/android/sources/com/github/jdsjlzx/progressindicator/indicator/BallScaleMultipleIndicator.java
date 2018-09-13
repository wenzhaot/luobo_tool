package com.github.jdsjlzx.progressindicator.indicator;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.animation.LinearInterpolator;
import java.util.ArrayList;
import java.util.List;

public class BallScaleMultipleIndicator extends BaseIndicatorController {
    int[] alphaInts = new int[]{255, 255, 255};
    float[] scaleFloats = new float[]{1.0f, 1.0f, 1.0f};

    public void draw(Canvas canvas, Paint paint) {
        for (int i = 0; i < 3; i++) {
            paint.setAlpha(this.alphaInts[i]);
            canvas.scale(this.scaleFloats[i], this.scaleFloats[i], (float) (getWidth() / 2), (float) (getHeight() / 2));
            canvas.drawCircle((float) (getWidth() / 2), (float) (getHeight() / 2), ((float) (getWidth() / 2)) - 4.0f, paint);
        }
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
                    BallScaleMultipleIndicator.this.scaleFloats[index] = ((Float) animation.getAnimatedValue()).floatValue();
                    BallScaleMultipleIndicator.this.postInvalidate();
                }
            });
            scaleAnim.setStartDelay(delays[i]);
            scaleAnim.start();
            ValueAnimator alphaAnim = ValueAnimator.ofInt(new int[]{255, 0});
            alphaAnim.setInterpolator(new LinearInterpolator());
            alphaAnim.setDuration(1000);
            alphaAnim.setRepeatCount(-1);
            alphaAnim.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    BallScaleMultipleIndicator.this.alphaInts[index] = ((Integer) animation.getAnimatedValue()).intValue();
                    BallScaleMultipleIndicator.this.postInvalidate();
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
