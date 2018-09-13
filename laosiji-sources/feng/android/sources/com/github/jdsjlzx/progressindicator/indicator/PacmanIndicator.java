package com.github.jdsjlzx.progressindicator.indicator;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.animation.LinearInterpolator;
import com.tencent.ijk.media.player.IjkMediaMeta;
import java.util.ArrayList;
import java.util.List;

public class PacmanIndicator extends BaseIndicatorController {
    private int alpha;
    private float degrees1;
    private float degrees2;
    private float translateX;

    public void draw(Canvas canvas, Paint paint) {
        drawPacman(canvas, paint);
        drawCircle(canvas, paint);
    }

    private void drawPacman(Canvas canvas, Paint paint) {
        float x = (float) (getWidth() / 2);
        float y = (float) (getHeight() / 2);
        canvas.save();
        canvas.translate(x, y);
        canvas.rotate(this.degrees1);
        paint.setAlpha(255);
        canvas.drawArc(new RectF((-x) / 1.7f, (-y) / 1.7f, x / 1.7f, y / 1.7f), 0.0f, 270.0f, true, paint);
        canvas.restore();
        canvas.save();
        canvas.translate(x, y);
        canvas.rotate(this.degrees2);
        paint.setAlpha(255);
        canvas.drawArc(new RectF((-x) / 1.7f, (-y) / 1.7f, x / 1.7f, y / 1.7f), 90.0f, 270.0f, true, paint);
        canvas.restore();
    }

    private void drawCircle(Canvas canvas, Paint paint) {
        float radius = (float) (getWidth() / 11);
        paint.setAlpha(this.alpha);
        canvas.drawCircle(this.translateX, (float) (getHeight() / 2), radius, paint);
    }

    public List<Animator> createAnimation() {
        List<Animator> animators = new ArrayList();
        float startT = (float) (getWidth() / 11);
        ValueAnimator translationAnim = ValueAnimator.ofFloat(new float[]{((float) getWidth()) - startT, (float) (getWidth() / 2)});
        translationAnim.setDuration(650);
        translationAnim.setInterpolator(new LinearInterpolator());
        translationAnim.setRepeatCount(-1);
        translationAnim.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                PacmanIndicator.this.translateX = ((Float) animation.getAnimatedValue()).floatValue();
                PacmanIndicator.this.postInvalidate();
            }
        });
        translationAnim.start();
        ValueAnimator alphaAnim = ValueAnimator.ofInt(new int[]{255, IjkMediaMeta.FF_PROFILE_H264_HIGH_422});
        alphaAnim.setDuration(650);
        alphaAnim.setRepeatCount(-1);
        alphaAnim.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                PacmanIndicator.this.alpha = ((Integer) animation.getAnimatedValue()).intValue();
                PacmanIndicator.this.postInvalidate();
            }
        });
        alphaAnim.start();
        ValueAnimator rotateAnim1 = ValueAnimator.ofFloat(new float[]{0.0f, 45.0f, 0.0f});
        rotateAnim1.setDuration(650);
        rotateAnim1.setRepeatCount(-1);
        rotateAnim1.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                PacmanIndicator.this.degrees1 = ((Float) animation.getAnimatedValue()).floatValue();
                PacmanIndicator.this.postInvalidate();
            }
        });
        rotateAnim1.start();
        ValueAnimator rotateAnim2 = ValueAnimator.ofFloat(new float[]{0.0f, -45.0f, 0.0f});
        rotateAnim2.setDuration(650);
        rotateAnim2.setRepeatCount(-1);
        rotateAnim2.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                PacmanIndicator.this.degrees2 = ((Float) animation.getAnimatedValue()).floatValue();
                PacmanIndicator.this.postInvalidate();
            }
        });
        rotateAnim2.start();
        animators.add(translationAnim);
        animators.add(alphaAnim);
        animators.add(rotateAnim1);
        animators.add(rotateAnim2);
        return animators;
    }
}
