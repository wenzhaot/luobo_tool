package com.github.jdsjlzx.progressindicator.indicator;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.view.animation.LinearInterpolator;
import java.util.ArrayList;
import java.util.List;

public class BallZigZagDeflectIndicator extends BallZigZagIndicator {
    public List<Animator> createAnimation() {
        List<Animator> animators = new ArrayList();
        float startX = (float) (getWidth() / 6);
        float startY = (float) (getWidth() / 6);
        for (int i = 0; i < 2; i++) {
            final int index = i;
            ValueAnimator translateXAnim = ValueAnimator.ofFloat(new float[]{startX, ((float) getWidth()) - startX, startX, ((float) getWidth()) - startX, startX});
            if (i == 1) {
                translateXAnim = ValueAnimator.ofFloat(new float[]{((float) getWidth()) - startX, startX, ((float) getWidth()) - startX, startX, ((float) getWidth()) - startX});
            }
            ValueAnimator translateYAnim = ValueAnimator.ofFloat(new float[]{startY, startY, ((float) getHeight()) - startY, ((float) getHeight()) - startY, startY});
            if (i == 1) {
                translateYAnim = ValueAnimator.ofFloat(new float[]{((float) getHeight()) - startY, ((float) getHeight()) - startY, startY, startY, ((float) getHeight()) - startY});
            }
            translateXAnim.setDuration(2000);
            translateXAnim.setInterpolator(new LinearInterpolator());
            translateXAnim.setRepeatCount(-1);
            translateXAnim.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    BallZigZagDeflectIndicator.this.translateX[index] = ((Float) animation.getAnimatedValue()).floatValue();
                    BallZigZagDeflectIndicator.this.postInvalidate();
                }
            });
            translateXAnim.start();
            translateYAnim.setDuration(2000);
            translateYAnim.setInterpolator(new LinearInterpolator());
            translateYAnim.setRepeatCount(-1);
            translateYAnim.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    BallZigZagDeflectIndicator.this.translateY[index] = ((Float) animation.getAnimatedValue()).floatValue();
                    BallZigZagDeflectIndicator.this.postInvalidate();
                }
            });
            translateYAnim.start();
            animators.add(translateXAnim);
            animators.add(translateYAnim);
        }
        return animators;
    }
}
