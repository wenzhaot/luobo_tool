package com.feng.car.view.cropimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView.ScaleType;
import com.feng.car.utils.FengUtil;

public class ClipZoomImageView extends AppCompatImageView implements OnScaleGestureListener, OnTouchListener, OnGlobalLayoutListener {
    public static float SCALE_MAX = 4.0f;
    private static float SCALE_MID = 1.0f;
    private float initScale;
    private boolean isAutoScale;
    private boolean isCanDrag;
    private int lastPointerCount;
    private Context mContext;
    private GestureDetector mGestureDetector;
    private int mHorizontalPadding;
    private float mLastX;
    private float mLastY;
    private ScaleGestureDetector mScaleGestureDetector;
    private final Matrix mScaleMatrix;
    private int mTouchSlop;
    private int mType;
    private final float[] matrixValues;
    private boolean once;

    private class AutoScaleRunnable implements Runnable {
        static final float BIGGER = 1.07f;
        static final float SMALLER = 0.93f;
        private float mTargetScale;
        private float tmpScale;
        private float x;
        private float y;

        public AutoScaleRunnable(float targetScale, float x, float y) {
            this.mTargetScale = targetScale;
            this.x = x;
            this.y = y;
            if (ClipZoomImageView.this.getScale() < this.mTargetScale) {
                this.tmpScale = BIGGER;
            } else {
                this.tmpScale = SMALLER;
            }
        }

        public void run() {
            ClipZoomImageView.this.mScaleMatrix.postScale(this.tmpScale, this.tmpScale, this.x, this.y);
            ClipZoomImageView.this.checkBorder();
            ClipZoomImageView.this.setImageMatrix(ClipZoomImageView.this.mScaleMatrix);
            float currentScale = ClipZoomImageView.this.getScale();
            if ((this.tmpScale <= 1.0f || currentScale >= this.mTargetScale) && (this.tmpScale >= 1.0f || this.mTargetScale >= currentScale)) {
                float deltaScale = this.mTargetScale / currentScale;
                ClipZoomImageView.this.mScaleMatrix.postScale(deltaScale, deltaScale, this.x, this.y);
                ClipZoomImageView.this.checkBorder();
                ClipZoomImageView.this.setImageMatrix(ClipZoomImageView.this.mScaleMatrix);
                ClipZoomImageView.this.isAutoScale = false;
                return;
            }
            ClipZoomImageView.this.postDelayed(this, 16);
        }
    }

    public void setCropType(int type) {
        this.mType = type;
        if (this.mType != 2) {
            this.mHorizontalPadding = 0;
        }
        invalidate();
    }

    public ClipZoomImageView(Context context) {
        this(context, null);
    }

    public ClipZoomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mType = 1;
        this.initScale = 1.0f;
        this.once = true;
        this.matrixValues = new float[9];
        this.mScaleGestureDetector = null;
        this.mScaleMatrix = new Matrix();
        this.mContext = context;
        setScaleType(ScaleType.MATRIX);
        this.mGestureDetector = new GestureDetector(context, new SimpleOnGestureListener() {
            public boolean onDoubleTap(MotionEvent e) {
                if (!ClipZoomImageView.this.isAutoScale) {
                    float x = e.getX();
                    float y = e.getY();
                    if (ClipZoomImageView.this.getScale() < ClipZoomImageView.SCALE_MID) {
                        ClipZoomImageView.this.postDelayed(new AutoScaleRunnable(ClipZoomImageView.SCALE_MID, x, y), 16);
                        ClipZoomImageView.this.isAutoScale = true;
                    } else {
                        ClipZoomImageView.this.postDelayed(new AutoScaleRunnable(ClipZoomImageView.this.initScale, x, y), 16);
                        ClipZoomImageView.this.isAutoScale = true;
                    }
                }
                return true;
            }
        });
        this.mScaleGestureDetector = new ScaleGestureDetector(context, this);
        setOnTouchListener(this);
    }

    public boolean onScale(ScaleGestureDetector detector) {
        float scale = getScale();
        float scaleFactor = detector.getScaleFactor();
        if (getDrawable() != null && ((scale < SCALE_MAX && scaleFactor > 1.0f) || (scale > this.initScale && scaleFactor < 1.0f))) {
            if (scaleFactor * scale < this.initScale) {
                scaleFactor = this.initScale / scale;
            }
            if (scaleFactor * scale > SCALE_MAX) {
                scaleFactor = SCALE_MAX / scale;
            }
            this.mScaleMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
            checkBorder();
            setImageMatrix(this.mScaleMatrix);
        }
        return true;
    }

    private RectF getMatrixRectF() {
        Matrix matrix = this.mScaleMatrix;
        RectF rect = new RectF();
        Drawable d = getDrawable();
        if (d != null) {
            rect.set(0.0f, 0.0f, (float) d.getIntrinsicWidth(), (float) d.getIntrinsicHeight());
            matrix.mapRect(rect);
        }
        return rect;
    }

    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    public void onScaleEnd(ScaleGestureDetector detector) {
    }

    public boolean onTouch(View v, MotionEvent event) {
        if (!this.mGestureDetector.onTouchEvent(event)) {
            this.mScaleGestureDetector.onTouchEvent(event);
            float x = 0.0f;
            float y = 0.0f;
            int pointerCount = event.getPointerCount();
            for (int i = 0; i < pointerCount; i++) {
                x += event.getX(i);
                y += event.getY(i);
            }
            x /= (float) pointerCount;
            y /= (float) pointerCount;
            if (pointerCount != this.lastPointerCount) {
                this.isCanDrag = false;
                this.mLastX = x;
                this.mLastY = y;
            }
            this.lastPointerCount = pointerCount;
            switch (event.getAction()) {
                case 1:
                case 3:
                    this.lastPointerCount = 0;
                    break;
                case 2:
                    float dx = x - this.mLastX;
                    float dy = y - this.mLastY;
                    if (!this.isCanDrag) {
                        this.isCanDrag = isCanDrag(dx, dy);
                    }
                    if (this.isCanDrag && getDrawable() != null) {
                        RectF rectF = getMatrixRectF();
                        if (rectF.width() <= ((float) (getWidth() - (this.mHorizontalPadding * 2)))) {
                            dx = 0.0f;
                        }
                        if (rectF.height() <= ((float) (getHeight() - (getHVerticalPadding() * 2)))) {
                            dy = 0.0f;
                        }
                        this.mScaleMatrix.postTranslate(dx, dy);
                        checkBorder();
                        setImageMatrix(this.mScaleMatrix);
                    }
                    this.mLastX = x;
                    this.mLastY = y;
                    break;
            }
        }
        return true;
    }

    public final float getScale() {
        this.mScaleMatrix.getValues(this.matrixValues);
        return this.matrixValues[0];
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeGlobalOnLayoutListener(this);
    }

    public void onGlobalLayout() {
        if (this.once) {
            Drawable d = getDrawable();
            if (d != null) {
                int width = getWidth();
                int height = getHeight();
                int drawableW = d.getIntrinsicWidth();
                int drawableH = d.getIntrinsicHeight();
                float scale = 1.0f;
                int frameSize = getWidth() - (this.mHorizontalPadding * 2);
                if (drawableW > frameSize && drawableH < frameSize) {
                    scale = (((float) frameSize) * 1.0f) / ((float) drawableH);
                } else if (drawableH > frameSize && drawableW < frameSize) {
                    scale = (((float) frameSize) * 1.0f) / ((float) drawableW);
                } else if (drawableW > frameSize && drawableH > frameSize) {
                    scale = (((float) frameSize) * 1.0f) / ((float) drawableW);
                }
                if (drawableW < frameSize && drawableH > frameSize) {
                    scale = (((float) frameSize) * 1.0f) / ((float) drawableW);
                } else if (drawableH < frameSize && drawableW > frameSize) {
                    scale = (((float) frameSize) * 1.0f) / ((float) drawableH);
                } else if (drawableW < frameSize && drawableH < frameSize) {
                    scale = (((float) frameSize) * 1.0f) / ((float) drawableW);
                }
                this.initScale = scale;
                SCALE_MID = this.initScale * 2.0f;
                SCALE_MAX = this.initScale * 4.0f;
                this.mScaleMatrix.postTranslate((float) ((width - drawableW) / 2), (float) ((height - drawableH) / 2));
                this.mScaleMatrix.postScale(scale, scale, (float) (getWidth() / 2), (float) (getHeight() / 2));
                setImageMatrix(this.mScaleMatrix);
                this.once = false;
            }
        }
    }

    public Bitmap clip() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Config.ARGB_8888);
        draw(new Canvas(bitmap));
        if (this.mType == 2) {
            return Bitmap.createBitmap(bitmap, this.mHorizontalPadding, getHVerticalPadding(), getWidth() - (this.mHorizontalPadding * 2), getWidth() - (this.mHorizontalPadding * 2));
        }
        int width;
        int height;
        if (this.mType == 1) {
            width = FengUtil.getScreenWidth(this.mContext);
            height = (width * 9) / 16;
            return Bitmap.createBitmap(bitmap, 0, ((FengUtil.getScreenHeight(this.mContext) - this.mContext.getResources().getDimensionPixelSize(2131296859)) - height) / 2, width, height);
        }
        width = FengUtil.getScreenWidth(this.mContext);
        height = (width * 3) / 4;
        return Bitmap.createBitmap(bitmap, 0, ((FengUtil.getScreenHeight(this.mContext) - this.mContext.getResources().getDimensionPixelSize(2131296859)) - height) / 2, width, height);
    }

    private void checkBorder() {
        RectF rect = getMatrixRectF();
        float deltaX = 0.0f;
        float deltaY = 0.0f;
        int width = getWidth();
        int height = getHeight();
        if (((double) rect.width()) + 0.01d >= ((double) (width - (this.mHorizontalPadding * 2)))) {
            if (rect.left > ((float) this.mHorizontalPadding)) {
                deltaX = (-rect.left) + ((float) this.mHorizontalPadding);
            }
            if (rect.right < ((float) (width - this.mHorizontalPadding))) {
                deltaX = ((float) (width - this.mHorizontalPadding)) - rect.right;
            }
        }
        if (((double) rect.height()) + 0.01d >= ((double) (height - (getHVerticalPadding() * 2)))) {
            if (rect.top > ((float) getHVerticalPadding())) {
                deltaY = (-rect.top) + ((float) getHVerticalPadding());
            }
            if (rect.bottom < ((float) (height - getHVerticalPadding()))) {
                deltaY = ((float) (height - getHVerticalPadding())) - rect.bottom;
            }
        }
        this.mScaleMatrix.postTranslate(deltaX, deltaY);
    }

    private boolean isCanDrag(float dx, float dy) {
        return Math.sqrt((double) ((dx * dx) + (dy * dy))) >= ((double) this.mTouchSlop);
    }

    public void setHorizontalPadding(int mHorizontalPadding) {
        this.mHorizontalPadding = mHorizontalPadding;
    }

    private int getHVerticalPadding() {
        if (this.mType == 2) {
            return (getHeight() - (getWidth() - (this.mHorizontalPadding * 2))) / 2;
        }
        if (this.mType == 1) {
            return ((FengUtil.getScreenHeight(this.mContext) - this.mContext.getResources().getDimensionPixelSize(2131296859)) - ((FengUtil.getScreenWidth(this.mContext) * 9) / 16)) / 2;
        }
        return ((FengUtil.getScreenHeight(this.mContext) - this.mContext.getResources().getDimensionPixelSize(2131296859)) - ((FengUtil.getScreenWidth(this.mContext) * 3) / 4)) / 2;
    }
}
