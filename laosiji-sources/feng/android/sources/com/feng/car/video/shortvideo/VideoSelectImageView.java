package com.feng.car.video.shortvideo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class VideoSelectImageView extends View {
    private BoxChangedListener mBoxChangedListener;
    private Paint mBoxPaint;
    private int mBoxWidth;
    private float mCenterX;
    private Paint mCoverPaint;

    public interface BoxChangedListener {
        void onBoxChanged(float f, int i);
    }

    public VideoSelectImageView(Context context) {
        super(context);
        init(context);
    }

    public VideoSelectImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public VideoSelectImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Canvas canvas2 = canvas;
        canvas2.drawRect(this.mCenterX - ((float) (this.mBoxWidth / 2)), 0.0f, ((float) (this.mBoxWidth / 2)) + this.mCenterX, (float) getHeight(), this.mBoxPaint);
        canvas.drawRect(0.0f, 0.0f, this.mCenterX - ((float) (this.mBoxWidth / 2)), (float) getHeight(), this.mCoverPaint);
        canvas2 = canvas;
        canvas2.drawRect(((float) (this.mBoxWidth / 2)) + this.mCenterX, 0.0f, (float) getWidth(), (float) getHeight(), this.mCoverPaint);
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                float x = event.getX();
                if (x < ((float) (this.mBoxWidth / 2))) {
                    this.mCenterX = (float) (this.mBoxWidth / 2);
                } else if (x > ((float) (getWidth() - (this.mBoxWidth / 2)))) {
                    this.mCenterX = (float) (getWidth() - (this.mBoxWidth / 2));
                } else {
                    this.mCenterX = x;
                }
                if (this.mBoxChangedListener != null) {
                    this.mBoxChangedListener.onBoxChanged(this.mCenterX - ((float) (this.mBoxWidth / 2)), getWidth() - this.mBoxWidth);
                }
                invalidate();
                break;
            case 2:
                float x1 = event.getX();
                if (x1 < ((float) (this.mBoxWidth / 2))) {
                    this.mCenterX = (float) (this.mBoxWidth / 2);
                } else if (x1 > ((float) (getWidth() - (this.mBoxWidth / 2)))) {
                    this.mCenterX = (float) (getWidth() - (this.mBoxWidth / 2));
                } else {
                    this.mCenterX = x1;
                }
                if (this.mBoxChangedListener != null) {
                    this.mBoxChangedListener.onBoxChanged(this.mCenterX - ((float) (this.mBoxWidth / 2)), getWidth() - this.mBoxWidth);
                }
                invalidate();
                break;
        }
        return true;
    }

    private void init(Context context) {
        this.mBoxPaint = new Paint();
        this.mBoxPaint.setAntiAlias(true);
        this.mBoxPaint.setColor(context.getResources().getColor(2131558537));
        this.mBoxPaint.setStyle(Style.STROKE);
        this.mBoxPaint.setStrokeWidth((float) context.getResources().getDimensionPixelSize(2131296368));
        this.mCoverPaint = new Paint();
        this.mCoverPaint.setAntiAlias(true);
        this.mCoverPaint.setColor(context.getResources().getColor(2131558448));
        this.mBoxWidth = context.getResources().getDimensionPixelSize(2131296284);
        this.mCenterX = (float) (this.mBoxWidth / 2);
    }

    public void setBoxChangedListener(BoxChangedListener l) {
        this.mBoxChangedListener = l;
    }
}
