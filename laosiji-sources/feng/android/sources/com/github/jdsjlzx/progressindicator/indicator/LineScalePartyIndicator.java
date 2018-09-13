package com.github.jdsjlzx.progressindicator.indicator;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import java.util.ArrayList;
import java.util.List;

public class LineScalePartyIndicator extends BaseIndicatorController {
    public static final float SCALE = 1.0f;
    float[] scaleFloats = new float[]{1.0f, 1.0f, 1.0f, 1.0f, 1.0f};

    public void draw(Canvas canvas, Paint paint) {
        float translateX = (float) (getWidth() / 9);
        float translateY = (float) (getHeight() / 2);
        for (int i = 0; i < 4; i++) {
            canvas.save();
            canvas.translate((((float) ((i * 2) + 2)) * translateX) - (translateX / 2.0f), translateY);
            canvas.scale(this.scaleFloats[i], this.scaleFloats[i]);
            canvas.drawRoundRect(new RectF((-translateX) / 2.0f, ((float) (-getHeight())) / 2.5f, translateX / 2.0f, ((float) getHeight()) / 2.5f), 5.0f, 5.0f, paint);
            canvas.restore();
        }
    }

    public List<Animator> createAnimation() {
        List<Animator> animators = new ArrayList();
        long[] durations = new long[]{1260, 430, 1010, 730};
        long[] delays = new long[]{770, 290, 280, 740};
        for (int i = 0; i < 4; i++) {
            final int index = i;
            ValueAnimator scaleAnim = ValueAnimator.ofFloat(new float[]{1.0f, 0.4f, 1.0f});
            scaleAnim.setDuration(durations[i]);
            scaleAnim.setRepeatCount(-1);
            scaleAnim.setStartDelay(delays[i]);
            scaleAnim.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    LineScalePartyIndicator.this.scaleFloats[index] = ((Float) animation.getAnimatedValue()).floatValue();
                    LineScalePartyIndicator.this.postInvalidate();
                }
            });
            scaleAnim.start();
            animators.add(scaleAnim);
        }
        return animators;
    }
}
