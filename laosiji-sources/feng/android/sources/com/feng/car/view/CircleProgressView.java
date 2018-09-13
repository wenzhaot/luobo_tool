package com.feng.car.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

public class CircleProgressView extends View {
    private final int mCircleLineStrokeWidth;
    private final Context mContext;
    private int mMaxProgress;
    private final Paint mPaint;
    private int mProgress;
    private final RectF mRectF;

    public CircleProgressView(Context context) {
        this(context, null);
    }

    public CircleProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mMaxProgress = 100;
        this.mProgress = 0;
        this.mCircleLineStrokeWidth = 8;
        this.mContext = context;
        this.mRectF = new RectF();
        this.mPaint = new Paint();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        if (width != height) {
            int min = Math.min(width, height);
            width = min;
            height = min;
        }
        this.mPaint.setAntiAlias(true);
        canvas.drawColor(0);
        this.mPaint.setStyle(Style.FILL);
        this.mPaint.setColor(ContextCompat.getColor(this.mContext, 2131558558));
        canvas.drawCircle((float) (width / 2), (float) (width / 2), (float) (((width - 8) - 4) / 2), this.mPaint);
        this.mPaint.setColor(ContextCompat.getColor(this.mContext, 2131558512));
        this.mPaint.setStrokeWidth(4.0f);
        this.mPaint.setStyle(Style.STROKE);
        this.mRectF.left = 4.0f;
        this.mRectF.top = 4.0f;
        this.mRectF.right = (float) (width - 4);
        this.mRectF.bottom = (float) (height - 4);
        canvas.drawArc(this.mRectF, -90.0f, 360.0f, false, this.mPaint);
        this.mPaint.setStrokeWidth(8.0f);
        this.mPaint.setColor(ContextCompat.getColor(this.mContext, 2131558549));
        canvas.drawArc(this.mRectF, -90.0f, 360.0f * (((float) this.mProgress) / ((float) this.mMaxProgress)), false, this.mPaint);
    }

    public int getMaxProgress() {
        return this.mMaxProgress;
    }

    public void setMaxProgress(int maxProgress) {
        this.mMaxProgress = maxProgress;
    }

    public void setProgress(int progress) {
        this.mProgress = progress;
        invalidate();
    }

    public void setProgressNotInUiThread(int progress) {
        this.mProgress = progress;
        postInvalidate();
    }
}
