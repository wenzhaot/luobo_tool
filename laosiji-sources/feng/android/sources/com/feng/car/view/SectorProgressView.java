package com.feng.car.view;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import com.feng.car.R;

public class SectorProgressView extends View {
    private ObjectAnimator animator;
    private int bgColor;
    private Paint bgPaint;
    private int fgColor;
    private Paint fgPaint;
    private RectF oval;
    private float percent;
    private float startAngle;

    public SectorProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SectorProgressView, 0, 0);
        try {
            this.bgColor = a.getColor(0, -1710619);
            this.fgColor = a.getColor(1, -35236);
            this.percent = a.getFloat(2, 0.0f);
            this.startAngle = a.getFloat(3, 0.0f) + 270.0f;
            init();
        } finally {
            a.recycle();
        }
    }

    private void init() {
        this.bgPaint = new Paint();
        this.bgPaint.setColor(this.bgColor);
        this.bgPaint.setAntiAlias(true);
        this.fgPaint = new Paint();
        this.fgPaint.setColor(this.fgColor);
        this.fgPaint.setAntiAlias(true);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.oval = new RectF((float) getPaddingLeft(), (float) getPaddingTop(), ((float) getPaddingLeft()) + (((float) w) - ((float) (getPaddingLeft() + getPaddingRight()))), ((float) getPaddingTop()) + (((float) h) - ((float) (getPaddingBottom() + getPaddingTop()))));
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(this.oval, 0.0f, 360.0f, true, this.bgPaint);
        canvas.drawArc(this.oval, this.startAngle, 3.6f * this.percent, true, this.fgPaint);
    }

    public int getBgColor() {
        return this.bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
        refreshTheLayout();
    }

    public int getFgColor() {
        return this.fgColor;
    }

    public void setFgColor(int fgColor) {
        this.fgColor = fgColor;
        refreshTheLayout();
    }

    private void refreshTheLayout() {
        invalidate();
        requestLayout();
    }

    public float getStartAngle() {
        return this.startAngle;
    }

    public void setStartAngle(float startAngle) {
        this.startAngle = 270.0f + startAngle;
        invalidate();
        requestLayout();
    }

    public float getPercent() {
        return this.percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
        invalidate();
        requestLayout();
    }

    public void animateIndeterminate() {
        animateIndeterminate(800, new AccelerateDecelerateInterpolator());
    }

    public void animateIndeterminate(int durationOneCircle, TimeInterpolator interpolator) {
        this.animator = ObjectAnimator.ofFloat(this, "startAngle", new float[]{getStartAngle(), getStartAngle() + 360.0f});
        if (interpolator != null) {
            this.animator.setInterpolator(interpolator);
        }
        this.animator.setDuration((long) durationOneCircle);
        this.animator.setRepeatCount(-1);
        this.animator.setRepeatMode(1);
        this.animator.start();
    }

    public void stopAnimateIndeterminate() {
        if (this.animator != null) {
            this.animator.cancel();
            this.animator = null;
        }
    }
}
