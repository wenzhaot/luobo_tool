package com.github.jdsjlzx.progressindicator;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import com.feng.car.utils.FengConstant;
import com.github.jdsjlzx.R;
import com.github.jdsjlzx.progressindicator.indicator.BallBeatIndicator;
import com.github.jdsjlzx.progressindicator.indicator.BallClipRotateIndicator;
import com.github.jdsjlzx.progressindicator.indicator.BallClipRotateMultipleIndicator;
import com.github.jdsjlzx.progressindicator.indicator.BallClipRotatePulseIndicator;
import com.github.jdsjlzx.progressindicator.indicator.BallGridBeatIndicator;
import com.github.jdsjlzx.progressindicator.indicator.BallGridPulseIndicator;
import com.github.jdsjlzx.progressindicator.indicator.BallPulseIndicator;
import com.github.jdsjlzx.progressindicator.indicator.BallPulseRiseIndicator;
import com.github.jdsjlzx.progressindicator.indicator.BallPulseSyncIndicator;
import com.github.jdsjlzx.progressindicator.indicator.BallRotateIndicator;
import com.github.jdsjlzx.progressindicator.indicator.BallScaleIndicator;
import com.github.jdsjlzx.progressindicator.indicator.BallScaleMultipleIndicator;
import com.github.jdsjlzx.progressindicator.indicator.BallScaleRippleIndicator;
import com.github.jdsjlzx.progressindicator.indicator.BallScaleRippleMultipleIndicator;
import com.github.jdsjlzx.progressindicator.indicator.BallSpinFadeLoaderIndicator;
import com.github.jdsjlzx.progressindicator.indicator.BallTrianglePathIndicator;
import com.github.jdsjlzx.progressindicator.indicator.BallZigZagDeflectIndicator;
import com.github.jdsjlzx.progressindicator.indicator.BallZigZagIndicator;
import com.github.jdsjlzx.progressindicator.indicator.BaseIndicatorController;
import com.github.jdsjlzx.progressindicator.indicator.BaseIndicatorController.AnimStatus;
import com.github.jdsjlzx.progressindicator.indicator.CubeTransitionIndicator;
import com.github.jdsjlzx.progressindicator.indicator.LineScaleIndicator;
import com.github.jdsjlzx.progressindicator.indicator.LineScalePartyIndicator;
import com.github.jdsjlzx.progressindicator.indicator.LineScalePulseOutIndicator;
import com.github.jdsjlzx.progressindicator.indicator.LineScalePulseOutRapidIndicator;
import com.github.jdsjlzx.progressindicator.indicator.LineSpinFadeLoaderIndicator;
import com.github.jdsjlzx.progressindicator.indicator.PacmanIndicator;
import com.github.jdsjlzx.progressindicator.indicator.SemiCircleSpinIndicator;
import com.github.jdsjlzx.progressindicator.indicator.SquareSpinIndicator;
import com.github.jdsjlzx.progressindicator.indicator.TriangleSkewSpinIndicator;

public class AVLoadingIndicatorView extends View {
    public static final int BallBeat = 17;
    public static final int BallClipRotate = 2;
    public static final int BallClipRotateMultiple = 5;
    public static final int BallClipRotatePulse = 3;
    public static final int BallGridBeat = 26;
    public static final int BallGridPulse = 1;
    public static final int BallPulse = 0;
    public static final int BallPulseRise = 6;
    public static final int BallPulseSync = 16;
    public static final int BallRotate = 7;
    public static final int BallScale = 12;
    public static final int BallScaleMultiple = 15;
    public static final int BallScaleRipple = 20;
    public static final int BallScaleRippleMultiple = 21;
    public static final int BallSpinFadeLoader = 22;
    public static final int BallTrianglePath = 11;
    public static final int BallZigZag = 9;
    public static final int BallZigZagDeflect = 10;
    public static final int CubeTransition = 8;
    public static final int DEFAULT_SIZE = 30;
    public static final int LineScale = 13;
    public static final int LineScaleParty = 14;
    public static final int LineScalePulseOut = 18;
    public static final int LineScalePulseOutRapid = 19;
    public static final int LineSpinFadeLoader = 23;
    public static final int Pacman = 25;
    public static final int SemiCircleSpin = 27;
    public static final int SquareSpin = 4;
    public static final int TriangleSkewSpin = 24;
    private boolean mHasAnimation;
    int mIndicatorColor;
    BaseIndicatorController mIndicatorController;
    int mIndicatorId;
    Paint mPaint;

    public @interface Indicator {
    }

    public AVLoadingIndicatorView(Context context) {
        super(context);
        init(null, 0);
    }

    public AVLoadingIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public AVLoadingIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    @TargetApi(21)
    public AVLoadingIndicatorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyle) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.AVLoadingIndicatorView);
        this.mIndicatorId = a.getInt(R.styleable.AVLoadingIndicatorView_indicator, 2);
        this.mIndicatorColor = a.getColor(R.styleable.AVLoadingIndicatorView_indicator_color, -1);
        a.recycle();
        this.mPaint = new Paint();
        this.mPaint.setColor(this.mIndicatorColor);
        this.mPaint.setStyle(Style.FILL);
        this.mPaint.setAntiAlias(true);
        applyIndicator();
    }

    public void setIndicatorId(int indicatorId) {
        this.mIndicatorId = indicatorId;
        applyIndicator();
    }

    public void setIndicatorColor(int color) {
        this.mIndicatorColor = color;
        this.mPaint.setColor(this.mIndicatorColor);
        invalidate();
    }

    private void applyIndicator() {
        switch (this.mIndicatorId) {
            case 0:
                this.mIndicatorController = new BallPulseIndicator();
                break;
            case 1:
                this.mIndicatorController = new BallGridPulseIndicator();
                break;
            case 2:
                this.mIndicatorController = new BallClipRotateIndicator();
                break;
            case 3:
                this.mIndicatorController = new BallClipRotatePulseIndicator();
                break;
            case 4:
                this.mIndicatorController = new SquareSpinIndicator();
                break;
            case 5:
                this.mIndicatorController = new BallClipRotateMultipleIndicator();
                break;
            case 6:
                this.mIndicatorController = new BallPulseRiseIndicator();
                break;
            case 7:
                this.mIndicatorController = new BallRotateIndicator();
                break;
            case 8:
                this.mIndicatorController = new CubeTransitionIndicator();
                break;
            case 9:
                this.mIndicatorController = new BallZigZagIndicator();
                break;
            case 10:
                this.mIndicatorController = new BallZigZagDeflectIndicator();
                break;
            case 11:
                this.mIndicatorController = new BallTrianglePathIndicator();
                break;
            case 12:
                this.mIndicatorController = new BallScaleIndicator();
                break;
            case 13:
                this.mIndicatorController = new LineScaleIndicator();
                break;
            case 14:
                this.mIndicatorController = new LineScalePartyIndicator();
                break;
            case 15:
                this.mIndicatorController = new BallScaleMultipleIndicator();
                break;
            case 16:
                this.mIndicatorController = new BallPulseSyncIndicator();
                break;
            case 17:
                this.mIndicatorController = new BallBeatIndicator();
                break;
            case 18:
                this.mIndicatorController = new LineScalePulseOutIndicator();
                break;
            case 19:
                this.mIndicatorController = new LineScalePulseOutRapidIndicator();
                break;
            case 20:
                this.mIndicatorController = new BallScaleRippleIndicator();
                break;
            case 21:
                this.mIndicatorController = new BallScaleRippleMultipleIndicator();
                break;
            case 22:
                this.mIndicatorController = new BallSpinFadeLoaderIndicator();
                break;
            case 23:
                this.mIndicatorController = new LineSpinFadeLoaderIndicator();
                break;
            case 24:
                this.mIndicatorController = new TriangleSkewSpinIndicator();
                break;
            case 25:
                this.mIndicatorController = new PacmanIndicator();
                break;
            case 26:
                this.mIndicatorController = new BallGridBeatIndicator();
                break;
            case 27:
                this.mIndicatorController = new SemiCircleSpinIndicator();
                break;
        }
        this.mIndicatorController.setTarget(this);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureDimension(dp2px(30), widthMeasureSpec), measureDimension(dp2px(30), heightMeasureSpec));
    }

    private int measureDimension(int defaultSize, int measureSpec) {
        int result = defaultSize;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == FengConstant.MAXVIDEOSIZE) {
            return specSize;
        }
        if (specMode == Integer.MIN_VALUE) {
            return Math.min(defaultSize, specSize);
        }
        return defaultSize;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawIndicator(canvas);
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (!this.mHasAnimation) {
            this.mHasAnimation = true;
            applyAnimation();
        }
    }

    public void setVisibility(int v) {
        if (getVisibility() != v) {
            super.setVisibility(v);
            if (v == 8 || v == 4) {
                this.mIndicatorController.setAnimationStatus(AnimStatus.END);
            } else {
                this.mIndicatorController.setAnimationStatus(AnimStatus.START);
            }
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mIndicatorController.setAnimationStatus(AnimStatus.CANCEL);
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mIndicatorController.setAnimationStatus(AnimStatus.START);
    }

    void drawIndicator(Canvas canvas) {
        this.mIndicatorController.draw(canvas, this.mPaint);
    }

    void applyAnimation() {
        this.mIndicatorController.initAnimation();
    }

    private int dp2px(int dpValue) {
        return ((int) getContext().getResources().getDisplayMetrics().density) * dpValue;
    }
}
