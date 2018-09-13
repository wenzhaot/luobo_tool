package com.feng.car.video.shortvideo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.feng.car.FengApplication;

public class VideoCropSelcetView extends View {
    private Bitmap endImageBitmap;
    private float endImageCenterX = -1.0f;
    private float endImageCenterY = -1.0f;
    private RectF endImageRectF;
    private boolean isTouchEndImage = false;
    private boolean isTouchStartImage = false;
    private CursorChangedListener mCursorChangedListener;
    private float mFirstMovePx = 0.0f;
    private Paint mImagePaint;
    private float mMinDistance;
    private Paint mWhiteRectPaint;
    private float progressCenterX = -1.0f;
    private Bitmap startImageBitmap;
    private float startImageCenterX = -1.0f;
    private float startImageCenterY = -1.0f;
    private RectF startImageRectF;

    public interface CursorChangedListener {
        void onEndCursorChanged(float f, float f2);

        void onStartCursorChanged(float f, float f2);
    }

    public VideoCropSelcetView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public VideoCropSelcetView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public VideoCropSelcetView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mMinDistance != (((float) getWidth()) - (((float) getBitmapWidth()) * 2.0f)) / 12.0f) {
            this.mMinDistance = (((float) getWidth()) - (((float) getBitmapWidth()) * 2.0f)) / 12.0f;
        }
        if (this.startImageCenterX == -1.0f) {
            this.startImageCenterX = (float) (this.startImageBitmap.getWidth() / 2);
        }
        if (this.startImageCenterY == -1.0f) {
            this.startImageCenterY = (float) (getHeight() / 2);
        }
        this.startImageRectF.left = this.startImageCenterX - ((float) (this.startImageBitmap.getWidth() / 2));
        this.startImageRectF.top = 0.0f;
        this.startImageRectF.right = this.startImageCenterX + ((float) (this.startImageBitmap.getWidth() / 2));
        this.startImageRectF.bottom = (float) getHeight();
        canvas.drawBitmap(this.startImageBitmap, null, this.startImageRectF, this.mImagePaint);
        if (this.endImageCenterX >= ((float) getWidth()) || this.endImageCenterX == -1.0f) {
            this.endImageCenterX = (float) (getWidth() - (this.endImageBitmap.getWidth() / 2));
        }
        if (this.endImageCenterY == -1.0f) {
            this.endImageCenterY = (float) (getHeight() / 2);
        }
        this.endImageRectF.left = this.endImageCenterX - ((float) (this.endImageBitmap.getWidth() / 2));
        this.endImageRectF.top = 0.0f;
        this.endImageRectF.right = this.endImageCenterX + ((float) (this.endImageBitmap.getWidth() / 2));
        this.endImageRectF.bottom = (float) getHeight();
        canvas.drawBitmap(this.endImageBitmap, null, this.endImageRectF, this.mImagePaint);
        canvas.drawRect((this.startImageCenterX + ((float) (this.startImageBitmap.getWidth() / 2))) - 1.0f, 0.0f, (this.endImageCenterX - ((float) (this.endImageBitmap.getWidth() / 2))) + 1.0f, (float) FengApplication.getInstance().getResources().getDimensionPixelSize(2131296701), this.mWhiteRectPaint);
        canvas.drawRect((this.startImageCenterX + ((float) (this.startImageBitmap.getWidth() / 2))) - 1.0f, (float) (getHeight() - FengApplication.getInstance().getResources().getDimensionPixelSize(2131296701)), (this.endImageCenterX - ((float) (this.endImageBitmap.getWidth() / 2))) + 1.0f, (float) getHeight(), this.mWhiteRectPaint);
        if (this.progressCenterX == -1.0f) {
            this.progressCenterX = this.startImageCenterX + ((float) (this.startImageBitmap.getWidth() / 2));
        }
        canvas.drawRect(this.progressCenterX - 1.0f, 0.0f, this.progressCenterX + 1.0f, (float) getHeight(), this.mWhiteRectPaint);
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                float x = event.getX();
                if (x < (this.startImageCenterX - ((float) (this.startImageBitmap.getWidth() / 2))) - 10.0f || x > (this.startImageCenterX + ((float) (this.startImageBitmap.getWidth() / 2))) + 10.0f) {
                    if (x >= (this.endImageCenterX - ((float) (this.endImageBitmap.getWidth() / 2))) - 10.0f && x <= (this.endImageCenterX + ((float) (this.endImageBitmap.getWidth() / 2))) + 10.0f) {
                        this.isTouchEndImage = true;
                        break;
                    }
                    return false;
                }
                this.isTouchStartImage = true;
                break;
                break;
            case 1:
            case 3:
                this.isTouchStartImage = false;
                this.isTouchEndImage = false;
                break;
            case 2:
                float currentX = event.getX();
                if (this.isTouchStartImage) {
                    if (currentX <= ((float) (this.startImageBitmap.getWidth() / 2))) {
                        currentX = (float) (this.startImageBitmap.getWidth() / 2);
                    }
                    if (currentX >= (this.endImageCenterX - ((float) (this.endImageBitmap.getWidth() / 2))) - this.mMinDistance) {
                        currentX = (this.endImageCenterX - ((float) (this.endImageBitmap.getWidth() / 2))) - this.mMinDistance;
                    }
                    this.startImageCenterX = currentX;
                    this.progressCenterX = this.startImageCenterX + ((float) (this.startImageBitmap.getWidth() / 2));
                    this.mFirstMovePx = 0.0f;
                    invalidate();
                    if (this.mCursorChangedListener != null) {
                        this.mCursorChangedListener.onStartCursorChanged(this.startImageCenterX - ((float) (this.startImageBitmap.getWidth() / 2)), (this.endImageCenterX - ((float) (this.endImageBitmap.getWidth() / 2))) - (this.startImageCenterX + ((float) (this.startImageBitmap.getWidth() / 2))));
                        break;
                    }
                } else if (this.isTouchEndImage) {
                    if (currentX >= ((float) (getWidth() - (this.startImageBitmap.getWidth() / 2)))) {
                        currentX = (float) (getWidth() - (this.startImageBitmap.getWidth() / 2));
                    }
                    if (currentX <= (this.startImageCenterX + ((float) (this.startImageBitmap.getWidth() / 2))) + this.mMinDistance) {
                        currentX = (this.startImageCenterX + ((float) (this.startImageBitmap.getWidth() / 2))) + this.mMinDistance;
                    }
                    this.endImageCenterX = currentX;
                    this.progressCenterX = this.startImageCenterX + ((float) (this.startImageBitmap.getWidth() / 2));
                    this.mFirstMovePx = 0.0f;
                    invalidate();
                    if (this.mCursorChangedListener != null) {
                        this.mCursorChangedListener.onEndCursorChanged(this.startImageCenterX - ((float) (this.startImageBitmap.getWidth() / 2)), (this.endImageCenterX - ((float) (this.endImageBitmap.getWidth() / 2))) - (this.startImageCenterX + ((float) (this.startImageBitmap.getWidth() / 2))));
                        break;
                    }
                } else {
                    return false;
                }
                break;
        }
        return true;
    }

    private void init(Context context) {
        this.mImagePaint = new Paint();
        this.mImagePaint.setAntiAlias(true);
        this.mWhiteRectPaint = new Paint();
        this.mWhiteRectPaint.setAntiAlias(true);
        this.mWhiteRectPaint.setColor(context.getResources().getColor(2131558558));
        this.startImageBitmap = BitmapFactory.decodeResource(context.getResources(), 2130837923);
        this.endImageBitmap = BitmapFactory.decodeResource(context.getResources(), 2130837924);
        this.startImageRectF = new RectF();
        this.endImageRectF = new RectF();
    }

    public void updateProgressCenterX(float movePx) {
        this.progressCenterX += Math.abs(movePx - this.mFirstMovePx);
        this.mFirstMovePx = movePx;
        invalidate();
    }

    public void resetProgressCenterX() {
        this.progressCenterX = this.startImageCenterX + ((float) (this.startImageBitmap.getWidth() / 2));
        this.mFirstMovePx = 0.0f;
        invalidate();
    }

    public int getBitmapWidth() {
        if (this.startImageBitmap != null) {
            return this.startImageBitmap.getWidth();
        }
        return 0;
    }

    public void setCursorChangedListener(CursorChangedListener l) {
        this.mCursorChangedListener = l;
    }
}
