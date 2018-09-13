package com.github.jdsjlzx.progressindicator.indicator;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.animation.LinearInterpolator;
import java.util.ArrayList;
import java.util.List;

public class CubeTransitionIndicator extends BaseIndicatorController {
    float degrees;
    float scaleFloat = 1.0f;
    float[] translateX = new float[2];
    float[] translateY = new float[2];

    public void draw(Canvas canvas, Paint paint) {
        float rWidth = (float) (getWidth() / 5);
        float rHeight = (float) (getHeight() / 5);
        for (int i = 0; i < 2; i++) {
            canvas.save();
            canvas.translate(this.translateX[i], this.translateY[i]);
            canvas.rotate(this.degrees);
            canvas.scale(this.scaleFloat, this.scaleFloat);
            canvas.drawRect(new RectF((-rWidth) / 2.0f, (-rHeight) / 2.0f, rWidth / 2.0f, rHeight / 2.0f), paint);
            canvas.restore();
        }
    }

    public List<Animator> createAnimation() {
        List<Animator> animators = new ArrayList();
        float startX = (float) (getWidth() / 5);
        float startY = (float) (getHeight() / 5);
        for (int i = 0; i < 2; i++) {
            final int index = i;
            this.translateX[index] = startX;
            ValueAnimator translationXAnim = ValueAnimator.ofFloat(new float[]{startX, ((float) getWidth()) - startX, ((float) getWidth()) - startX, startX, startX});
            if (i == 1) {
                translationXAnim = ValueAnimator.ofFloat(new float[]{((float) getWidth()) - startX, startX, startX, ((float) getWidth()) - startX, ((float) getWidth()) - startX});
            }
            translationXAnim.setInterpolator(new LinearInterpolator());
            translationXAnim.setDuration(1600);
            translationXAnim.setRepeatCount(-1);
            translationXAnim.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    CubeTransitionIndicator.this.translateX[index] = ((Float) animation.getAnimatedValue()).floatValue();
                    CubeTransitionIndicator.this.postInvalidate();
                }
            });
            translationXAnim.start();
            this.translateY[index] = startY;
            ValueAnimator translationYAnim = ValueAnimator.ofFloat(new float[]{startY, startY, ((float) getHeight()) - startY, ((float) getHeight()) - startY, startY});
            if (i == 1) {
                translationYAnim = ValueAnimator.ofFloat(new float[]{((float) getHeight()) - startY, ((float) getHeight()) - startY, startY, startY, ((float) getHeight()) - startY});
            }
            translationYAnim.setDuration(1600);
            translationYAnim.setInterpolator(new LinearInterpolator());
            translationYAnim.setRepeatCount(-1);
            translationYAnim.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    CubeTransitionIndicator.this.translateY[index] = ((Float) animation.getAnimatedValue()).floatValue();
                    CubeTransitionIndicator.this.postInvalidate();
                }
            });
            translationYAnim.start();
            animators.add(translationXAnim);
            animators.add(translationYAnim);
        }
        ValueAnimator scaleAnim = ValueAnimator.ofFloat(new float[]{1.0f, 0.5f, 1.0f, 0.5f, 1.0f});
        scaleAnim.setDuration(1600);
        scaleAnim.setInterpolator(new LinearInterpolator());
        scaleAnim.setRepeatCount(-1);
        scaleAnim.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                CubeTransitionIndicator.this.scaleFloat = ((Float) animation.getAnimatedValue()).floatValue();
                CubeTransitionIndicator.this.postInvalidate();
            }
        });
        scaleAnim.start();
        ValueAnimator rotateAnim = ValueAnimator.ofFloat(new float[]{0.0f, 180.0f, 360.0f, 540.0f, 720.0f});
        rotateAnim.setDuration(1600);
        rotateAnim.setInterpolator(new LinearInterpolator());
        rotateAnim.setRepeatCount(-1);
        rotateAnim.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                CubeTransitionIndicator.this.degrees = ((Float) animation.getAnimatedValue()).floatValue();
                CubeTransitionIndicator.this.postInvalidate();
            }
        });
        rotateAnim.start();
        animators.add(scaleAnim);
        animators.add(rotateAnim);
        return animators;
    }
}
