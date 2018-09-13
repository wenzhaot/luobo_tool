package com.github.jdsjlzx.progressindicator.indicator;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.animation.LinearInterpolator;
import java.util.ArrayList;
import java.util.List;

public class SquareSpinIndicator extends BaseIndicatorController {
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawRect(new RectF((float) (getWidth() / 5), (float) (getHeight() / 5), (float) ((getWidth() * 4) / 5), (float) ((getHeight() * 4) / 5)), paint);
    }

    public List<Animator> createAnimation() {
        List<Animator> animators = new ArrayList();
        PropertyValuesHolder rotation5 = PropertyValuesHolder.ofFloat("rotationX", new float[]{0.0f, 180.0f, 180.0f, 0.0f, 0.0f});
        PropertyValuesHolder rotation6 = PropertyValuesHolder.ofFloat("rotationY", new float[]{0.0f, 0.0f, 180.0f, 180.0f, 0.0f});
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(getTarget(), new PropertyValuesHolder[]{rotation6, rotation5});
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(-1);
        animator.setDuration(2500);
        animator.start();
        animators.add(animator);
        return animators;
    }
}
