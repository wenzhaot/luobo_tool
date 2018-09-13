package com.github.jdsjlzx.progressindicator.indicator;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import java.util.ArrayList;
import java.util.List;

public class BallClipRotatePulseIndicator extends BaseIndicatorController {
    float degrees;
    float scaleFloat1;
    float scaleFloat2;

    public void draw(Canvas canvas, Paint paint) {
        float x = (float) (getWidth() / 2);
        float y = (float) (getHeight() / 2);
        canvas.save();
        canvas.translate(x, y);
        canvas.scale(this.scaleFloat1, this.scaleFloat1);
        paint.setStyle(Style.FILL);
        canvas.drawCircle(0.0f, 0.0f, x / 2.5f, paint);
        canvas.restore();
        canvas.translate(x, y);
        canvas.scale(this.scaleFloat2, this.scaleFloat2);
        canvas.rotate(this.degrees);
        paint.setStrokeWidth(3.0f);
        paint.setStyle(Style.STROKE);
        float[] startAngles = new float[]{225.0f, 45.0f};
        for (int i = 0; i < 2; i++) {
            canvas.drawArc(new RectF((-x) + 12.0f, (-y) + 12.0f, x - 12.0f, y - 12.0f), startAngles[i], 90.0f, false, paint);
        }
    }

    public List<Animator> createAnimation() {
        ValueAnimator scaleAnim = ValueAnimator.ofFloat(new float[]{1.0f, 0.3f, 1.0f});
        scaleAnim.setDuration(1000);
        scaleAnim.setRepeatCount(-1);
        scaleAnim.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                BallClipRotatePulseIndicator.this.scaleFloat1 = ((Float) animation.getAnimatedValue()).floatValue();
                BallClipRotatePulseIndicator.this.postInvalidate();
            }
        });
        scaleAnim.start();
        ValueAnimator scaleAnim2 = ValueAnimator.ofFloat(new float[]{1.0f, 0.6f, 1.0f});
        scaleAnim2.setDuration(1000);
        scaleAnim2.setRepeatCount(-1);
        scaleAnim2.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                BallClipRotatePulseIndicator.this.scaleFloat2 = ((Float) animation.getAnimatedValue()).floatValue();
                BallClipRotatePulseIndicator.this.postInvalidate();
            }
        });
        scaleAnim2.start();
        ValueAnimator rotateAnim = ValueAnimator.ofFloat(new float[]{0.0f, 180.0f, 360.0f});
        rotateAnim.setDuration(1000);
        rotateAnim.setRepeatCount(-1);
        rotateAnim.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                BallClipRotatePulseIndicator.this.degrees = ((Float) animation.getAnimatedValue()).floatValue();
                BallClipRotatePulseIndicator.this.postInvalidate();
            }
        });
        rotateAnim.start();
        List<Animator> animators = new ArrayList();
        animators.add(scaleAnim);
        animators.add(scaleAnim2);
        animators.add(rotateAnim);
        return animators;
    }
}
