package com.github.jdsjlzx.progressindicator.indicator;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.Canvas;
import android.graphics.Paint;
import java.util.ArrayList;
import java.util.List;

public class BallSpinFadeLoaderIndicator extends BaseIndicatorController {
    public static final int ALPHA = 255;
    public static final float SCALE = 1.0f;
    int[] alphas = new int[]{255, 255, 255, 255, 255, 255, 255, 255};
    float[] scaleFloats = new float[]{1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f};

    final class Point {
        public float x;
        public float y;

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }

    public void draw(Canvas canvas, Paint paint) {
        float radius = (float) (getWidth() / 10);
        for (int i = 0; i < 8; i++) {
            canvas.save();
            Point point = circleAt(getWidth(), getHeight(), ((float) (getWidth() / 2)) - radius, ((double) i) * 0.7853981633974483d);
            canvas.translate(point.x, point.y);
            canvas.scale(this.scaleFloats[i], this.scaleFloats[i]);
            paint.setAlpha(this.alphas[i]);
            canvas.drawCircle(0.0f, 0.0f, radius, paint);
            canvas.restore();
        }
    }

    Point circleAt(int width, int height, float radius, double angle) {
        return new Point((float) (((double) (width / 2)) + (((double) radius) * Math.cos(angle))), (float) (((double) (height / 2)) + (((double) radius) * Math.sin(angle))));
    }

    public List<Animator> createAnimation() {
        List<Animator> animators = new ArrayList();
        int[] delays = new int[]{0, 120, 240, 360, 480, 600, 720, 780, 840};
        for (int i = 0; i < 8; i++) {
            final int index = i;
            ValueAnimator scaleAnim = ValueAnimator.ofFloat(new float[]{1.0f, 0.4f, 1.0f});
            scaleAnim.setDuration(1000);
            scaleAnim.setRepeatCount(-1);
            scaleAnim.setStartDelay((long) delays[i]);
            scaleAnim.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    BallSpinFadeLoaderIndicator.this.scaleFloats[index] = ((Float) animation.getAnimatedValue()).floatValue();
                    BallSpinFadeLoaderIndicator.this.postInvalidate();
                }
            });
            scaleAnim.start();
            ValueAnimator alphaAnim = ValueAnimator.ofInt(new int[]{255, 77, 255});
            alphaAnim.setDuration(1000);
            alphaAnim.setRepeatCount(-1);
            alphaAnim.setStartDelay((long) delays[i]);
            alphaAnim.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    BallSpinFadeLoaderIndicator.this.alphas[index] = ((Integer) animation.getAnimatedValue()).intValue();
                    BallSpinFadeLoaderIndicator.this.postInvalidate();
                }
            });
            alphaAnim.start();
            animators.add(scaleAnim);
            animators.add(alphaAnim);
        }
        return animators;
    }
}
