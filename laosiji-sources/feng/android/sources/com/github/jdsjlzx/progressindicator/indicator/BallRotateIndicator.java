package com.github.jdsjlzx.progressindicator.indicator;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.Canvas;
import android.graphics.Paint;
import java.util.ArrayList;
import java.util.List;

public class BallRotateIndicator extends BaseIndicatorController {
    float scaleFloat = 0.5f;

    public void draw(Canvas canvas, Paint paint) {
        float radius = (float) (getWidth() / 10);
        float x = (float) (getWidth() / 2);
        float y = (float) (getHeight() / 2);
        canvas.save();
        canvas.translate((x - (radius * 2.0f)) - radius, y);
        canvas.scale(this.scaleFloat, this.scaleFloat);
        canvas.drawCircle(0.0f, 0.0f, radius, paint);
        canvas.restore();
        canvas.save();
        canvas.translate(x, y);
        canvas.scale(this.scaleFloat, this.scaleFloat);
        canvas.drawCircle(0.0f, 0.0f, radius, paint);
        canvas.restore();
        canvas.save();
        canvas.translate(((radius * 2.0f) + x) + radius, y);
        canvas.scale(this.scaleFloat, this.scaleFloat);
        canvas.drawCircle(0.0f, 0.0f, radius, paint);
        canvas.restore();
    }

    public List<Animator> createAnimation() {
        List<Animator> animators = new ArrayList();
        ValueAnimator scaleAnim = ValueAnimator.ofFloat(new float[]{0.5f, 1.0f, 0.5f});
        scaleAnim.setDuration(1000);
        scaleAnim.setRepeatCount(-1);
        scaleAnim.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                BallRotateIndicator.this.scaleFloat = ((Float) animation.getAnimatedValue()).floatValue();
                BallRotateIndicator.this.postInvalidate();
            }
        });
        scaleAnim.start();
        ObjectAnimator rotateAnim = ObjectAnimator.ofFloat(getTarget(), "rotation", new float[]{0.0f, 180.0f, 360.0f});
        rotateAnim.setDuration(1000);
        rotateAnim.setRepeatCount(-1);
        rotateAnim.start();
        animators.add(scaleAnim);
        animators.add(rotateAnim);
        return animators;
    }
}
