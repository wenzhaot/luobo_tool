package com.umeng.socialize.shareboard;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import com.feng.car.utils.FengConstant;

public class IndicatorView extends View {
    private int mIndicatorMargin;
    private int mIndicatorWidth;
    private float mLeftPosition;
    private Paint mNormalPaint;
    private int mPageCount;
    private Paint mSelectPaint;
    private int mSelectPosition;

    public IndicatorView(Context context) {
        super(context);
    }

    public IndicatorView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public IndicatorView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @TargetApi(21)
    public IndicatorView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    protected void onMeasure(int i, int i2) {
        setMeasuredDimension(measureWidth(i), measureHeight(i2));
    }

    private int measureWidth(int i) {
        int mode = MeasureSpec.getMode(i);
        int size = MeasureSpec.getSize(i);
        int paddingLeft = ((getPaddingLeft() + getPaddingRight()) + ((this.mIndicatorWidth * this.mPageCount) * 2)) + (this.mIndicatorMargin * (this.mPageCount - 1));
        this.mLeftPosition = (((float) (getMeasuredWidth() - paddingLeft)) / 2.0f) + ((float) getPaddingLeft());
        if (mode == FengConstant.MAXVIDEOSIZE) {
            return Math.max(paddingLeft, size);
        }
        if (mode == Integer.MIN_VALUE) {
            return Math.min(paddingLeft, size);
        }
        return paddingLeft;
    }

    private int measureHeight(int i) {
        int mode = MeasureSpec.getMode(i);
        int size = MeasureSpec.getSize(i);
        if (mode == FengConstant.MAXVIDEOSIZE) {
            return size;
        }
        int paddingTop = (getPaddingTop() + getPaddingBottom()) + (this.mIndicatorWidth * 2);
        return mode == Integer.MIN_VALUE ? Math.min(paddingTop, size) : paddingTop;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mSelectPaint != null && this.mNormalPaint != null) {
            float f = ((float) this.mIndicatorWidth) + this.mLeftPosition;
            int i = 0;
            while (i < this.mPageCount) {
                canvas.drawCircle(f, (float) this.mIndicatorWidth, (float) this.mIndicatorWidth, i == this.mSelectPosition ? this.mSelectPaint : this.mNormalPaint);
                f += (float) (this.mIndicatorMargin + (this.mIndicatorWidth * 2));
                i++;
            }
        }
    }

    public void setSelectedPosition(int i) {
        this.mSelectPosition = i;
        invalidate();
    }

    public void setPageCount(int i) {
        this.mPageCount = i;
        invalidate();
    }

    public void setIndicator(int i, int i2) {
        this.mIndicatorMargin = dip2px((float) i2);
        this.mIndicatorWidth = dip2px((float) i);
    }

    public void setIndicatorColor(int i, int i2) {
        this.mSelectPaint = new Paint();
        this.mSelectPaint.setStyle(Style.FILL);
        this.mSelectPaint.setAntiAlias(true);
        this.mSelectPaint.setColor(i2);
        this.mNormalPaint = new Paint();
        this.mNormalPaint.setStyle(Style.FILL);
        this.mNormalPaint.setAntiAlias(true);
        this.mNormalPaint.setColor(i);
    }

    protected int dip2px(float f) {
        return (int) ((getContext().getResources().getDisplayMetrics().density * f) + 0.5f);
    }
}
