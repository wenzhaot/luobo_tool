package com.umeng.social.tool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

class UMWaterMark {
    static final int RELATIVE_POSITION_HORIZONTAL_LEFT = 4;
    static final int RELATIVE_POSITION_HORIZONTAL_RIGHT = 3;
    static final int RELATIVE_POSITION_VERTICAL_BOTTOM = 1;
    static final int RELATIVE_POSITION_VERTICAL_TOP = 2;
    private static final String TAG = UMWaterMark.class.getSimpleName();
    private float mAlpha = -1.0f;
    private Rect mAnchorMarkRect = new Rect();
    private int mBottomMargin;
    private Context mContext;
    private int mDegree = -1;
    private int mGravity = 51;
    private int mHorizontalRelativePosition = -1;
    private boolean mIsBringToFront = false;
    private boolean mIsTransparent = false;
    private int mLeftMargin;
    private Rect mMeasureRect = new Rect();
    private int mRightMargin;
    private float mScale = 0.3f;
    private int mTopMargin;
    private int mVerticalRelativePosition = -1;

    UMWaterMark() {
    }

    public void setMargins(int left, int top, int right, int bottom) {
        this.mLeftMargin = left;
        this.mTopMargin = top;
        this.mRightMargin = right;
        this.mBottomMargin = bottom;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public void setGravity(int gravity) {
        if (gravity > 0 && this.mGravity != gravity) {
            this.mGravity = gravity;
        }
    }

    public void setScale(float scale) {
        if (scale >= 0.0f && scale <= 1.0f) {
            this.mScale = scale;
        }
    }

    public void setRotate(int degree) {
        if (degree >= 0 && degree <= 360) {
            this.mDegree = degree;
        }
    }

    public void bringToFront() {
        this.mIsBringToFront = true;
    }

    public void setAlpha(float alpha) {
        if (alpha >= 0.0f && alpha <= 1.0f) {
            this.mAlpha = alpha;
        }
    }

    public void setTransparent() {
        this.mIsTransparent = true;
    }

    public Bitmap compound(Bitmap src) {
        if (src == null) {
            try {
                Log.e(TAG, "scr bitmap is null");
                return null;
            } catch (Exception e) {
                return null;
            }
        }
        Bitmap markBitmap = getMarkBitmap();
        if (markBitmap == null) {
            Log.e(TAG, "mark bitmap is null");
            return src;
        }
        int srcWidth = src.getWidth();
        int srcHeight = src.getHeight();
        if (srcWidth <= 0 || srcHeight <= 0) {
            Log.e(TAG, "mark bitmap is error, markWidth:" + srcWidth + ", markHeight:" + srcHeight);
            return src;
        }
        int markWidth = getMarkWidth();
        int markHeight = getMarkHeight();
        if (markWidth <= 0 || markHeight <= 0) {
            Log.e(TAG, "mark bitmap is error, markWidth:" + markWidth + ", markHeight:" + markHeight);
            return src;
        }
        Bitmap bitmap;
        Canvas canvas;
        if (this.mIsTransparent) {
            bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Config.ARGB_8888);
            canvas = new Canvas(bitmap);
            canvas.drawColor(0);
        } else {
            bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Config.RGB_565);
            canvas = new Canvas(bitmap);
        }
        canvas.drawBitmap(src, 0.0f, 0.0f, null);
        Matrix matrix = new Matrix();
        float scaleFactor = (this.mScale * ((float) Math.min(src.getWidth(), src.getHeight()))) / ((float) Math.max(markWidth, markHeight));
        matrix.postScale(scaleFactor, scaleFactor, getScaleAnchorX(markWidth), getScaleAnchorY(markHeight));
        if (this.mDegree != -1) {
            matrix.postRotate((float) this.mDegree, (float) (markWidth / 2), (float) (markHeight / 2));
        }
        matrix.postTranslate(isHorizontalRelativePosition() ? getRelativeDx(srcWidth) : getDx(srcWidth), isVerticalRelativePosition() ? getRelativeDy(srcHeight) : getDy(srcHeight));
        if (this.mAlpha != -1.0f) {
            Paint paint = new Paint();
            paint.setAlpha((int) (255.0f * this.mAlpha));
            canvas.drawBitmap(markBitmap, matrix, paint);
        } else {
            canvas.drawBitmap(markBitmap, matrix, null);
        }
        canvas.save(31);
        canvas.restore();
        safelyRecycleBitmap(src);
        safelyRecycleBitmap(markBitmap);
        releaseResource();
        return bitmap;
    }

    private float getScaleAnchorY(int height) {
        switch (this.mGravity & 112) {
            case 16:
                return (float) (height / 2);
            case 80:
                return (float) height;
            default:
                return 0.0f;
        }
    }

    private float getScaleAnchorX(int width) {
        switch (this.mGravity & 7) {
            case 1:
                return (float) (width / 2);
            case 5:
                return (float) width;
            default:
                return 0.0f;
        }
    }

    private void safelyRecycleBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            try {
                if (!bitmap.isRecycled()) {
                    bitmap.recycle();
                }
            } catch (Exception e) {
            }
        }
    }

    Bitmap getMarkBitmap() {
        return null;
    }

    private float getRelativeDy(int srcHeight) {
        float anchorMarkTop = (float) this.mAnchorMarkRect.top;
        float anchorMarkBottom = (float) this.mAnchorMarkRect.bottom;
        switch (this.mVerticalRelativePosition) {
            case 1:
                return anchorMarkBottom + ((float) dip2px((float) this.mTopMargin));
            case 2:
                return (anchorMarkTop - ((float) getMarkHeight())) + ((float) (-dip2px((float) this.mBottomMargin)));
            default:
                return getDy(srcHeight);
        }
    }

    private float getRelativeDx(int srcWidth) {
        float anchorMarkLeft = (float) this.mAnchorMarkRect.left;
        float anchorMarkRight = (float) this.mAnchorMarkRect.right;
        switch (this.mHorizontalRelativePosition) {
            case 3:
                return anchorMarkRight + ((float) dip2px((float) this.mLeftMargin));
            case 4:
                return (anchorMarkLeft - ((float) getMarkWidth())) + ((float) (-dip2px((float) this.mRightMargin)));
            default:
                return getDx(srcWidth);
        }
    }

    private float getDy(int srcHeight) {
        int bottomMargin = -dip2px((float) this.mBottomMargin);
        int topMargin = dip2px((float) this.mTopMargin);
        switch (this.mGravity & 112) {
            case 16:
                int offset;
                if (topMargin != 0) {
                    offset = topMargin;
                } else {
                    offset = bottomMargin;
                }
                return ((((float) (srcHeight - getMarkHeight())) * 1.0f) / 2.0f) + ((float) offset);
            case 80:
                return (float) ((srcHeight - getMarkHeight()) + bottomMargin);
            default:
                return (float) topMargin;
        }
    }

    private float getDx(int srcWidth) {
        int leftMargin = dip2px((float) this.mLeftMargin);
        int rightMargin = -dip2px((float) this.mRightMargin);
        switch (this.mGravity & 7) {
            case 1:
                int offset;
                if (leftMargin != 0) {
                    offset = leftMargin;
                } else {
                    offset = rightMargin;
                }
                return ((((float) (srcWidth - getMarkWidth())) * 1.0f) / 2.0f) + ((float) offset);
            case 5:
                return (float) ((srcWidth - getMarkWidth()) + rightMargin);
            default:
                return (float) leftMargin;
        }
    }

    private int getMarkWidth() {
        if (getMarkBitmap() == null) {
            return -1;
        }
        return getMarkBitmap().getWidth();
    }

    private int getMarkHeight() {
        if (getMarkBitmap() == null) {
            return -1;
        }
        return getMarkBitmap().getHeight();
    }

    void setAnchorMarkHorizontalRect(Rect rect) {
        this.mAnchorMarkRect.set(rect.left, this.mAnchorMarkRect.top, rect.right, this.mAnchorMarkRect.bottom);
    }

    void setAnchorMarkVerticalRect(Rect rect) {
        this.mAnchorMarkRect = rect;
        this.mAnchorMarkRect.set(this.mAnchorMarkRect.left, rect.top, this.mAnchorMarkRect.right, rect.bottom);
    }

    Rect onMeasure(int srcWidth, int srcHeight) {
        int left = 0;
        int right = 0;
        int top = 0;
        int bottom = 0;
        if (!isHorizontalRelativePosition()) {
            left = (int) getDx(srcWidth);
            right = left + getMarkWidth();
        }
        if (!isVerticalRelativePosition()) {
            top = (int) getDy(srcHeight);
            bottom = top + getMarkHeight();
        }
        this.mMeasureRect.set(left, top, right, bottom);
        return this.mMeasureRect;
    }

    void setHorizontalRelativePosition(int position) {
        this.mHorizontalRelativePosition = position;
    }

    void setVerticalRelativePosition(int position) {
        this.mVerticalRelativePosition = position;
    }

    void clearRelativePosition() {
        this.mHorizontalRelativePosition = -1;
        this.mVerticalRelativePosition = -1;
    }

    boolean isVerticalRelativePosition() {
        return this.mVerticalRelativePosition != -1;
    }

    boolean isHorizontalRelativePosition() {
        return this.mHorizontalRelativePosition != -1;
    }

    boolean isBringToFront() {
        return this.mIsBringToFront;
    }

    int dip2px(float dpValue) {
        return (int) ((dpValue * this.mContext.getResources().getDisplayMetrics().density) + 0.5f);
    }

    void releaseResource() {
    }
}
