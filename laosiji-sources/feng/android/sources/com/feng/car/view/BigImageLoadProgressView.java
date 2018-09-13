package com.feng.car.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.feng.car.utils.FengUtil;

public class BigImageLoadProgressView extends ImageView {
    private Rect mBounds;
    private int mLevel = 0;
    private final Paint mPaint = new Paint(1);
    private int mScreenOrientation = 1;
    private int mTextColor = Color.parseColor("#66000000");

    public BigImageLoadProgressView(Context context) {
        super(context);
        this.mBounds = new Rect(0, 0, FengUtil.getScreenWidth(context), FengUtil.getScreenHeight(context));
    }

    public BigImageLoadProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mBounds = new Rect(0, 0, FengUtil.getScreenWidth(context), FengUtil.getScreenHeight(context));
    }

    public BigImageLoadProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mBounds = new Rect(0, 0, FengUtil.getScreenWidth(context), FengUtil.getScreenHeight(context));
    }

    public void setValue(Rect bounds, int level) {
        this.mBounds = bounds;
        this.mLevel = level;
        invalidate();
    }

    public void setScreenOrientation(int mScreenOrientation) {
        this.mScreenOrientation = mScreenOrientation;
    }

    public void setScreenOrientation(Context context, int mScreenOrientation) {
        this.mScreenOrientation = mScreenOrientation;
        this.mBounds = new Rect(0, 0, FengUtil.getScreenWidth(context), FengUtil.getScreenHeight(context));
        invalidate();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mBounds != null && this.mLevel >= 0) {
            int ringRadius;
            int circleRadius;
            int strokeWidth;
            int textSize;
            if (this.mScreenOrientation == 1) {
                ringRadius = (int) (((float) this.mBounds.right) / 11.0f);
                circleRadius = (int) (((float) this.mBounds.right) / 11.5f);
                strokeWidth = (int) (((float) this.mBounds.right) / 150.0f);
                textSize = (int) (((float) this.mBounds.bottom) / 30.0f);
            } else {
                ringRadius = (int) (((float) this.mBounds.bottom) / 11.0f);
                circleRadius = (int) (((float) this.mBounds.bottom) / 11.5f);
                strokeWidth = (int) (((float) this.mBounds.bottom) / 150.0f);
                textSize = (int) (((float) this.mBounds.right) / 30.0f);
            }
            this.mPaint.setColor(-1);
            this.mPaint.setStyle(Style.STROKE);
            this.mPaint.setStrokeWidth((float) strokeWidth);
            canvas.drawCircle((float) this.mBounds.centerX(), (float) this.mBounds.centerY(), (float) ringRadius, this.mPaint);
            this.mPaint.setColor(this.mTextColor);
            this.mPaint.setStyle(Style.FILL);
            canvas.drawCircle((float) this.mBounds.centerX(), (float) this.mBounds.centerY(), (float) circleRadius, this.mPaint);
            String textStr = ((int) (((float) this.mLevel) / 100.0f)) + "%";
            this.mPaint.setColor(-1);
            this.mPaint.setTextSize((float) textSize);
            FontMetricsInt fontMetrics = this.mPaint.getFontMetricsInt();
            int baseline = (((this.mBounds.bottom + this.mBounds.top) - fontMetrics.bottom) - fontMetrics.top) / 2;
            this.mPaint.setTextAlign(Align.CENTER);
            canvas.drawText(textStr, (float) this.mBounds.centerX(), (float) baseline, this.mPaint);
        }
    }
}
