package com.feng.car.view.cropimage;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import com.feng.car.utils.FengUtil;

public class ClipImageBorderView extends View {
    private int height;
    private int mBorderColor;
    private int mBorderWidth;
    private Context mContext;
    private int mCoverColor;
    private int mHorizontalPadding;
    private Paint mPaint;
    private int mType;
    private int mVerticalPadding;
    private int mWidth;
    private int screenHeight;
    private int width;

    public void setIsCropHead(int type) {
        this.mType = type;
        if (this.mType == 2) {
            this.mBorderColor = Color.parseColor("#FFFFFF");
            this.mCoverColor = Color.parseColor("#aa000000");
        } else {
            this.mBorderColor = Color.parseColor("#dfdfdf");
            this.mCoverColor = Color.parseColor("#B3ffffff");
        }
        if (this.mType == 3 || this.mType == 4) {
            this.height = (this.width * 3) / 4;
        } else {
            this.height = (this.width * 9) / 16;
        }
        invalidate();
    }

    public ClipImageBorderView(Context context) {
        this(context, null);
    }

    public ClipImageBorderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClipImageBorderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mBorderColor = Color.parseColor("#FFFFFF");
        this.mBorderWidth = 1;
        this.mCoverColor = Color.parseColor("#aa000000");
        this.width = 0;
        this.screenHeight = 0;
        this.height = 0;
        this.mType = 2;
        this.mContext = context;
        this.mBorderWidth = (int) TypedValue.applyDimension(1, (float) this.mBorderWidth, getResources().getDisplayMetrics());
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.width = FengUtil.getScreenWidth(this.mContext);
        this.screenHeight = FengUtil.getScreenHeight(this.mContext) - this.mContext.getResources().getDimensionPixelSize(2131296859);
        if (this.mType == 3 || this.mType == 4) {
            this.height = (this.width * 3) / 4;
        } else {
            this.height = (this.width * 9) / 16;
        }
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mType == 2) {
            this.mWidth = getWidth() - (this.mHorizontalPadding * 2);
            this.mVerticalPadding = (getHeight() - this.mWidth) / 2;
            this.mPaint.setColor(this.mCoverColor);
            this.mPaint.setStyle(Style.FILL);
            canvas.drawRect(0.0f, 0.0f, (float) this.mHorizontalPadding, (float) getHeight(), this.mPaint);
            canvas.drawRect((float) (getWidth() - this.mHorizontalPadding), 0.0f, (float) getWidth(), (float) getHeight(), this.mPaint);
            canvas.drawRect((float) this.mHorizontalPadding, 0.0f, (float) (getWidth() - this.mHorizontalPadding), (float) this.mVerticalPadding, this.mPaint);
            canvas.drawRect((float) this.mHorizontalPadding, (float) (getHeight() - this.mVerticalPadding), (float) (getWidth() - this.mHorizontalPadding), (float) getHeight(), this.mPaint);
            this.mPaint.setColor(this.mBorderColor);
            this.mPaint.setStrokeWidth((float) this.mBorderWidth);
            this.mPaint.setStyle(Style.STROKE);
            canvas.drawRect((float) this.mHorizontalPadding, (float) this.mVerticalPadding, (float) (getWidth() - this.mHorizontalPadding), (float) (getHeight() - this.mVerticalPadding), this.mPaint);
            return;
        }
        this.mPaint.setColor(Color.parseColor("#B3ffffff"));
        this.mPaint.setStyle(Style.FILL);
        canvas.drawRect(0.0f, 0.0f, (float) this.width, (float) ((this.screenHeight - this.height) / 2), this.mPaint);
        canvas.drawRect(0.0f, (float) ((this.screenHeight + this.height) / 2), (float) this.width, (float) this.screenHeight, this.mPaint);
        this.mPaint.setColor(this.mBorderColor);
        this.mPaint.setStrokeWidth((float) this.mBorderWidth);
        this.mPaint.setStyle(Style.STROKE);
        canvas.drawRect(0.0f, (float) ((this.screenHeight - this.height) / 2), (float) this.width, (float) (((this.screenHeight - this.height) / 2) + 1), this.mPaint);
        canvas.drawRect(0.0f, (float) ((this.screenHeight + this.height) / 2), (float) this.width, (float) (((this.screenHeight + this.height) / 2) + 1), this.mPaint);
    }

    public void setHorizontalPadding(int mHorizontalPadding) {
        this.mHorizontalPadding = mHorizontalPadding;
    }
}
