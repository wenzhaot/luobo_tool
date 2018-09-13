package com.github.jdsjlzx.progressindicator.indicator;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.Canvas;
import android.graphics.Paint;
import java.util.ArrayList;
import java.util.List;

public class BallPulseIndicator extends BaseIndicatorController {
    public static final float SCALE = 1.0f;
    private float[] scaleFloats = new float[]{1.0f, 1.0f, 1.0f};

    public void draw(Canvas canvas, Paint paint) {
        float radius = (((float) Math.min(getWidth(), getHeight())) - (4.0f * 2.0f)) / 6.0f;
        float x = ((float) (getWidth() / 2)) - ((radius * 2.0f) + 4.0f);
        float y = (float) (getHeight() / 2);
        for (int i = 0; i < 3; i++) {
            canvas.save();
            canvas.translate((((radius * 2.0f) * ((float) i)) + x) + (((float) i) * 4.0f), y);
            canvas.scale(this.scaleFloats[i], this.scaleFloats[i]);
            canvas.drawCircle(0.0f, 0.0f, radius, paint);
            canvas.restore();
        }
    }

    public List<Animator> createAnimation() {
        List<Animator> animators = new ArrayList();
        int[] delays = new int[]{120, 240, 360};
        for (int i = 0; i < 3; i++) {
            final int index = i;
            ValueAnimator scaleAnim = ValueAnimator.ofFloat(new float[]{1.0f, 0.3f, 1.0f});
            scaleAnim.setDuration(750);
            scaleAnim.setRepeatCount(-1);
            scaleAnim.setStartDelay((long) delays[i]);
            scaleAnim.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    BallPulseIndicator.this.scaleFloats[index] = ((Float) animation.getAnimatedValue()).floatValue();
                    BallPulseIndicator.this.postInvalidate();
                }
            });
            scaleAnim.start();
            animators.add(scaleAnim);
        }
        return animators;
    }
}
