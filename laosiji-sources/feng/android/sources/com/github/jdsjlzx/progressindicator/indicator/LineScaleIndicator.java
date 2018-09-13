package com.github.jdsjlzx.progressindicator.indicator;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import java.util.ArrayList;
import java.util.List;

public class LineScaleIndicator extends BaseIndicatorController {
    public static final float SCALE = 1.0f;
    float[] scaleYFloats = new float[]{1.0f, 1.0f, 1.0f, 1.0f, 1.0f};

    public void draw(Canvas canvas, Paint paint) {
        float translateX = (float) (getWidth() / 11);
        float translateY = (float) (getHeight() / 2);
        for (int i = 0; i < 5; i++) {
            canvas.save();
            canvas.translate((((float) ((i * 2) + 2)) * translateX) - (translateX / 2.0f), translateY);
            canvas.scale(1.0f, this.scaleYFloats[i]);
            canvas.drawRoundRect(new RectF((-translateX) / 2.0f, ((float) (-getHeight())) / 2.5f, translateX / 2.0f, ((float) getHeight()) / 2.5f), 5.0f, 5.0f, paint);
            canvas.restore();
        }
    }

    public List<Animator> createAnimation() {
        List<Animator> animators = new ArrayList();
        long[] delays = new long[]{100, 200, 300, 400, 500};
        for (int i = 0; i < 5; i++) {
            final int index = i;
            ValueAnimator scaleAnim = ValueAnimator.ofFloat(new float[]{1.0f, 0.4f, 1.0f});
            scaleAnim.setDuration(1000);
            scaleAnim.setRepeatCount(-1);
            scaleAnim.setStartDelay(delays[i]);
            scaleAnim.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    LineScaleIndicator.this.scaleYFloats[index] = ((Float) animation.getAnimatedValue()).floatValue();
                    LineScaleIndicator.this.postInvalidate();
                }
            });
            scaleAnim.start();
            animators.add(scaleAnim);
        }
        return animators;
    }
}
