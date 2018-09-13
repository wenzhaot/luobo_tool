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

public class BallClipRotateMultipleIndicator extends BaseIndicatorController {
    float degrees;
    float scaleFloat = 1.0f;

    public void draw(Canvas canvas, Paint paint) {
        int i;
        paint.setStrokeWidth(3.0f);
        paint.setStyle(Style.STROKE);
        float x = (float) (getWidth() / 2);
        float y = (float) (getHeight() / 2);
        canvas.save();
        canvas.translate(x, y);
        canvas.scale(this.scaleFloat, this.scaleFloat);
        canvas.rotate(this.degrees);
        float[] bStartAngles = new float[]{135.0f, -45.0f};
        for (i = 0; i < 2; i++) {
            canvas.drawArc(new RectF((-x) + 12.0f, (-y) + 12.0f, x - 12.0f, y - 12.0f), bStartAngles[i], 90.0f, false, paint);
        }
        canvas.restore();
        canvas.translate(x, y);
        canvas.scale(this.scaleFloat, this.scaleFloat);
        canvas.rotate(-this.degrees);
        float[] sStartAngles = new float[]{225.0f, 45.0f};
        for (i = 0; i < 2; i++) {
            canvas.drawArc(new RectF(((-x) / 1.8f) + 12.0f, ((-y) / 1.8f) + 12.0f, (x / 1.8f) - 12.0f, (y / 1.8f) - 12.0f), sStartAngles[i], 90.0f, false, paint);
        }
    }

    public List<Animator> createAnimation() {
        List<Animator> animators = new ArrayList();
        ValueAnimator scaleAnim = ValueAnimator.ofFloat(new float[]{1.0f, 0.6f, 1.0f});
        scaleAnim.setDuration(1000);
        scaleAnim.setRepeatCount(-1);
        scaleAnim.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                BallClipRotateMultipleIndicator.this.scaleFloat = ((Float) animation.getAnimatedValue()).floatValue();
                BallClipRotateMultipleIndicator.this.postInvalidate();
            }
        });
        scaleAnim.start();
        ValueAnimator rotateAnim = ValueAnimator.ofFloat(new float[]{0.0f, 180.0f, 360.0f});
        rotateAnim.setDuration(1000);
        rotateAnim.setRepeatCount(-1);
        rotateAnim.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                BallClipRotateMultipleIndicator.this.degrees = ((Float) animation.getAnimatedValue()).floatValue();
                BallClipRotateMultipleIndicator.this.postInvalidate();
            }
        });
        rotateAnim.start();
        animators.add(scaleAnim);
        animators.add(rotateAnim);
        return animators;
    }
}
