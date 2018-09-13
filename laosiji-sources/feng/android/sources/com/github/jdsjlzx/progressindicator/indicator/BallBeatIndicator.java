package com.github.jdsjlzx.progressindicator.indicator;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.Canvas;
import android.graphics.Paint;
import com.feng.car.utils.FengConstant;
import java.util.ArrayList;
import java.util.List;

public class BallBeatIndicator extends BaseIndicatorController {
    public static final int ALPHA = 255;
    public static final float SCALE = 1.0f;
    int[] alphas = new int[]{255, 255, 255};
    private float[] scaleFloats = new float[]{1.0f, 1.0f, 1.0f};

    public void draw(Canvas canvas, Paint paint) {
        float radius = (((float) getWidth()) - (4.0f * 2.0f)) / 6.0f;
        float x = ((float) (getWidth() / 2)) - ((radius * 2.0f) + 4.0f);
        float y = (float) (getHeight() / 2);
        for (int i = 0; i < 3; i++) {
            canvas.save();
            canvas.translate((((radius * 2.0f) * ((float) i)) + x) + (((float) i) * 4.0f), y);
            canvas.scale(this.scaleFloats[i], this.scaleFloats[i]);
            paint.setAlpha(this.alphas[i]);
            canvas.drawCircle(0.0f, 0.0f, radius, paint);
            canvas.restore();
        }
    }

    public List<Animator> createAnimation() {
        List<Animator> animators = new ArrayList();
        int[] delays = new int[]{FengConstant.IMAGEMIDDLEWIDTH_350, 0, FengConstant.IMAGEMIDDLEWIDTH_350};
        for (int i = 0; i < 3; i++) {
            final int index = i;
            ValueAnimator scaleAnim = ValueAnimator.ofFloat(new float[]{1.0f, 0.75f, 1.0f});
            scaleAnim.setDuration(700);
            scaleAnim.setRepeatCount(-1);
            scaleAnim.setStartDelay((long) delays[i]);
            scaleAnim.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    BallBeatIndicator.this.scaleFloats[index] = ((Float) animation.getAnimatedValue()).floatValue();
                    BallBeatIndicator.this.postInvalidate();
                }
            });
            scaleAnim.start();
            ValueAnimator alphaAnim = ValueAnimator.ofInt(new int[]{255, 51, 255});
            alphaAnim.setDuration(700);
            alphaAnim.setRepeatCount(-1);
            alphaAnim.setStartDelay((long) delays[i]);
            alphaAnim.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    BallBeatIndicator.this.alphas[index] = ((Integer) animation.getAnimatedValue()).intValue();
                    BallBeatIndicator.this.postInvalidate();
                }
            });
            alphaAnim.start();
            animators.add(scaleAnim);
            animators.add(alphaAnim);
        }
        return animators;
    }
}
