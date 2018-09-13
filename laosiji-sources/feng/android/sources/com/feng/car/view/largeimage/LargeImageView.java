package com.feng.car.view.largeimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ScrollerCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import com.feng.car.view.largeimage.BlockImageLoader.DrawData;
import com.feng.car.view.largeimage.BlockImageLoader.OnImageLoadListener;
import com.feng.car.view.largeimage.factory.BitmapDecoderFactory;
import java.util.List;

public class LargeImageView extends View implements OnImageLoadListener, ILargeImageView {
    private AccelerateInterpolator accelerateInterpolator;
    private DecelerateInterpolator decelerateInterpolator;
    private float fitScale;
    private final GestureDetector gestureDetector;
    private final BlockImageLoader imageBlockLoader;
    private Rect imageRect;
    private boolean isEnableZoom;
    private Drawable mDrawable;
    private int mDrawableHeight;
    private int mDrawableWidth;
    private BitmapDecoderFactory mFactory;
    private LargeImageClickListener mLargeImageClickListener;
    private LargeImageOperationListener mLargeImageOperationListener;
    private int mLevel;
    private final int mMaximumVelocity;
    private final int mMinimumVelocity;
    private OnImageLoadListener mOnImageLoadListener;
    private float mScale;
    private final ScrollerCompat mScroller;
    private float maxScale;
    private float minScale;
    private OnScaleGestureListener onScaleGestureListener;
    private final ScaleGestureDetector scaleGestureDetector;
    private ScaleHelper scaleHelper;
    private SimpleOnGestureListener simpleOnGestureListener;

    public void setEnableZoom(boolean enableZoom) {
        this.isEnableZoom = enableZoom;
    }

    public LargeImageView(Context context) {
        this(context, null);
    }

    public LargeImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LargeImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mScale = 1.0f;
        this.isEnableZoom = true;
        this.imageRect = new Rect();
        this.simpleOnGestureListener = new SimpleOnGestureListener() {
            public boolean onDown(MotionEvent e) {
                if (!LargeImageView.this.mScroller.isFinished()) {
                    LargeImageView.this.mScroller.abortAnimation();
                }
                return true;
            }

            public void onShowPress(MotionEvent e) {
            }

            public boolean onSingleTapConfirmed(MotionEvent e) {
                if (LargeImageView.this.mLargeImageClickListener != null) {
                    LargeImageView.this.mLargeImageClickListener.onClick();
                }
                return super.onSingleTapConfirmed(e);
            }

            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                LargeImageView.this.overScrollByCompat((int) distanceX, (int) distanceY, LargeImageView.this.getScrollX(), LargeImageView.this.getScrollY(), LargeImageView.this.getScrollRangeX(), LargeImageView.this.getScrollRangeY(), 0, 0, false);
                return true;
            }

            public void onLongPress(MotionEvent e) {
            }

            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                LargeImageView.this.fling((int) (-velocityX), (int) (-velocityY));
                return true;
            }

            public boolean onDoubleTap(MotionEvent e) {
                if (!LargeImageView.this.hasLoad()) {
                    return false;
                }
                if (LargeImageView.this.isEnableZoom) {
                    float newScale;
                    if (LargeImageView.this.mScale < 1.0f) {
                        newScale = 1.0f;
                    } else if (LargeImageView.this.mScale < LargeImageView.this.fitScale && LargeImageView.this.fitScale < LargeImageView.this.maxScale) {
                        newScale = LargeImageView.this.fitScale;
                    } else if (LargeImageView.this.mScale >= LargeImageView.this.maxScale / 2.0f || LargeImageView.this.mScale >= 1.5f) {
                        if (LargeImageView.this.mScale < LargeImageView.this.maxScale) {
                            newScale = LargeImageView.this.maxScale;
                        } else {
                            newScale = 1.0f;
                        }
                    } else if (LargeImageView.this.maxScale / 2.0f < 1.5f) {
                        newScale = 1.5f;
                    } else {
                        newScale = LargeImageView.this.maxScale / 2.0f;
                    }
                    LargeImageView.this.smoothScale(newScale, (int) e.getX(), (int) e.getY());
                }
                return true;
            }
        };
        this.onScaleGestureListener = new OnScaleGestureListener() {
            public boolean onScale(ScaleGestureDetector detector) {
                if (!LargeImageView.this.hasLoad()) {
                    return false;
                }
                if (LargeImageView.this.isEnableZoom) {
                    float newScale = LargeImageView.this.mScale * detector.getScaleFactor();
                    if (newScale > LargeImageView.this.maxScale) {
                        newScale = LargeImageView.this.maxScale;
                    } else if (newScale < LargeImageView.this.minScale) {
                        newScale = LargeImageView.this.minScale;
                    }
                    LargeImageView.this.setScale(newScale, (int) detector.getFocusX(), (int) detector.getFocusY());
                }
                return true;
            }

            public boolean onScaleBegin(ScaleGestureDetector detector) {
                return true;
            }

            public void onScaleEnd(ScaleGestureDetector detector) {
            }
        };
        this.mScroller = ScrollerCompat.create(getContext(), null);
        this.scaleHelper = new ScaleHelper();
        setFocusable(true);
        setWillNotDraw(false);
        this.gestureDetector = new GestureDetector(context, this.simpleOnGestureListener);
        this.scaleGestureDetector = new ScaleGestureDetector(context, this.onScaleGestureListener);
        this.imageBlockLoader = new BlockImageLoader(context);
        this.imageBlockLoader.setOnImageLoadListener(this);
        ViewConfiguration configuration = ViewConfiguration.get(getContext());
        this.mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        this.mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
    }

    public boolean onTouchEvent(MotionEvent event) {
        this.scaleGestureDetector.onTouchEvent(event);
        this.gestureDetector.onTouchEvent(event);
        return true;
    }

    public void computeScroll() {
        super.computeScroll();
        if (this.scaleHelper.computeScrollOffset()) {
            setScale(this.scaleHelper.getCurScale(), this.scaleHelper.getStartX(), this.scaleHelper.getStartY());
        }
        if (this.mScroller.computeScrollOffset()) {
            int oldX = getScrollX();
            int oldY = getScrollY();
            int x = this.mScroller.getCurrX();
            int y = this.mScroller.getCurrY();
            if (!(oldX == x && oldY == y)) {
                int i = x - oldX;
                int i2 = y - oldY;
                overScrollByCompat(i, i2, oldX, oldY, getScrollRangeX(), getScrollRangeY(), 0, 0, false);
            }
            if (!this.mScroller.isFinished()) {
                notifyInvalidate();
            }
        }
    }

    public boolean canScrollHorizontally(int direction) {
        if (direction > 0) {
            if (getScrollX() < getScrollRangeX()) {
                return true;
            }
            return false;
        } else if (getScrollX() <= 0 || getScrollRangeX() <= 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean canScrollVertically(int direction) {
        if (direction > 0) {
            if (getScrollY() < getScrollRangeY()) {
                return true;
            }
            return false;
        } else if (getScrollY() <= 0 || getScrollRangeY() <= 0) {
            return false;
        } else {
            return true;
        }
    }

    public void setOnImageLoadListener(OnImageLoadListener onImageLoadListener) {
        this.mOnImageLoadListener = onImageLoadListener;
    }

    public OnImageLoadListener getOnImageLoadListener() {
        return this.mOnImageLoadListener;
    }

    public void setImage(Bitmap bm) {
        setImageDrawable(new BitmapDrawable(getResources(), bm));
    }

    public void setImage(@DrawableRes int resId) {
        setImageDrawable(ContextCompat.getDrawable(getContext(), resId));
    }

    public void setImageDrawable(Drawable drawable) {
        this.mFactory = null;
        this.mScale = 1.0f;
        scrollTo(0, 0);
        if (this.mDrawable != drawable) {
            int oldWidth = this.mDrawableWidth;
            int oldHeight = this.mDrawableHeight;
            updateDrawable(drawable);
            onLoadImageSize(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            if (!(oldWidth == this.mDrawableWidth && oldHeight == this.mDrawableHeight)) {
                requestLayout();
            }
            notifyInvalidate();
        }
    }

    public void setImage(BitmapDecoderFactory factory) {
        setImage(factory, null);
    }

    public void setImage(BitmapDecoderFactory factory, Drawable defaultDrawable) {
        this.mScale = 1.0f;
        this.mFactory = factory;
        scrollTo(0, 0);
        if (defaultDrawable != null) {
            onLoadImageSize(defaultDrawable.getIntrinsicWidth(), defaultDrawable.getIntrinsicHeight());
        }
        this.imageBlockLoader.load(factory);
    }

    private void updateDrawable(Drawable d) {
        if (this.mDrawable != null) {
            this.mDrawable.setCallback(null);
            unscheduleDrawable(this.mDrawable);
        }
        this.mDrawable = d;
        if (d != null) {
            d.setCallback(this);
            DrawableCompat.setLayoutDirection(d, DrawableCompat.getLayoutDirection(d));
            if (d.isStateful()) {
                d.setState(getDrawableState());
            }
            d.setVisible(getVisibility() == 0, true);
            d.setLevel(this.mLevel);
            this.mDrawableWidth = d.getIntrinsicWidth();
            this.mDrawableHeight = d.getIntrinsicHeight();
            return;
        }
        this.mDrawableHeight = -1;
        this.mDrawableWidth = -1;
    }

    public boolean hasLoad() {
        if (this.mDrawable != null) {
            return true;
        }
        if (this.mFactory != null) {
            return this.imageBlockLoader.hasLoad();
        }
        return false;
    }

    public int computeVerticalScrollRange() {
        int contentHeight = (getHeight() - getPaddingBottom()) - getPaddingTop();
        int scrollRange = getContentHeight();
        int scrollY = getScrollY();
        int overscrollBottom = Math.max(0, scrollRange - contentHeight);
        if (scrollY < 0) {
            return scrollRange - scrollY;
        }
        if (scrollY > overscrollBottom) {
            return scrollRange + (scrollY - overscrollBottom);
        }
        return scrollRange;
    }

    public int getImageWidth() {
        if (this.mDrawable != null) {
            return this.mDrawableWidth;
        }
        if (this.mFactory == null || !hasLoad()) {
            return 0;
        }
        return this.mDrawableWidth;
    }

    public int getImageHeight() {
        if (this.mDrawable != null) {
            return this.mDrawableHeight;
        }
        if (this.mFactory == null || !hasLoad()) {
            return 0;
        }
        return this.mDrawableHeight;
    }

    private int getScrollRangeY() {
        return getContentHeight() - ((getHeight() - getPaddingBottom()) - getPaddingTop());
    }

    public int computeVerticalScrollOffset() {
        return Math.max(0, super.computeVerticalScrollOffset());
    }

    public int computeVerticalScrollExtent() {
        return super.computeVerticalScrollExtent();
    }

    public int computeHorizontalScrollRange() {
        int contentWidth = (getWidth() - getPaddingRight()) - getPaddingLeft();
        int scrollRange = getContentWidth();
        int scrollX = getScrollX();
        int overscrollRight = Math.max(0, scrollRange - contentWidth);
        if (scrollX < 0) {
            return scrollRange - scrollX;
        }
        if (scrollX > overscrollRight) {
            return scrollRange + (scrollX - overscrollRight);
        }
        return scrollRange;
    }

    private int getScrollRangeX() {
        return getContentWidth() - ((getWidth() - getPaddingRight()) - getPaddingLeft());
    }

    private int getContentWidth() {
        if (hasLoad()) {
            return (int) (((float) getMeasuredWidth()) * this.mScale);
        }
        return 0;
    }

    private int getContentHeight() {
        if (!hasLoad() || getImageWidth() == 0) {
            return 0;
        }
        return (int) ((((1.0f * ((float) getMeasuredWidth())) * ((float) getImageHeight())) / ((float) getImageWidth())) * this.mScale);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getMeasuredWidth() != 0 && getMeasuredHeight() != 0 && hasLoad()) {
            int drawOffsetX = 0;
            int drawOffsetY = 0;
            int contentWidth = getContentWidth();
            int contentHeight = getContentHeight();
            int layoutWidth = getMeasuredWidth();
            int layoutHeight = getMeasuredHeight();
            if (layoutWidth > contentWidth) {
                drawOffsetX = (layoutWidth - contentWidth) / 2;
            }
            if (layoutHeight > contentHeight) {
                drawOffsetY = (layoutHeight - contentHeight) / 2;
            }
            if (this.mDrawable != null) {
                this.mDrawable.setBounds(drawOffsetX, drawOffsetY, drawOffsetX + contentWidth, drawOffsetY + contentHeight);
                this.mDrawable.draw(canvas);
            } else if (this.mFactory != null) {
                int left = getScrollX();
                int right = left + getMeasuredWidth();
                int top = getScrollY();
                int bottom = top + getMeasuredHeight();
                float imageScale = ((float) this.imageBlockLoader.getWidth()) / (this.mScale * ((float) getWidth()));
                this.imageRect.left = (int) Math.ceil((double) (((float) (left - 0)) * imageScale));
                this.imageRect.top = (int) Math.ceil((double) (((float) (top - 0)) * imageScale));
                this.imageRect.right = (int) Math.ceil((double) (((float) (right - 0)) * imageScale));
                this.imageRect.bottom = (int) Math.ceil((double) (((float) (bottom - 0)) * imageScale));
                try {
                    List<DrawData> drawData = this.imageBlockLoader.getDrawData(imageScale, this.imageRect);
                    int saveCount = canvas.save();
                    for (DrawData data : drawData) {
                        Rect drawRect = data.imageRect;
                        drawRect.left = ((int) (Math.ceil((double) (((float) drawRect.left) / imageScale)) + ((double) 0))) + drawOffsetX;
                        drawRect.top = ((int) (Math.ceil((double) (((float) drawRect.top) / imageScale)) + ((double) 0))) + drawOffsetY;
                        drawRect.right = ((int) (Math.ceil((double) (((float) drawRect.right) / imageScale)) + ((double) 0))) + drawOffsetX;
                        drawRect.bottom = ((int) (Math.ceil((double) (((float) drawRect.bottom) / imageScale)) + ((double) 0))) + drawOffsetY;
                        canvas.drawBitmap(data.bitmap, data.srcRect, drawRect, null);
                    }
                    canvas.restoreToCount(saveCount);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void onBlockImageLoadFinished() {
        notifyInvalidate();
        if (this.mOnImageLoadListener != null) {
            this.mOnImageLoadListener.onBlockImageLoadFinished();
        }
    }

    public void onLoadImageSize(final int imageWidth, final int imageHeight) {
        this.mDrawableWidth = imageWidth;
        this.mDrawableHeight = imageHeight;
        int layoutWidth = getMeasuredWidth();
        int layoutHeight = getMeasuredHeight();
        if (layoutWidth == 0 || layoutHeight == 0) {
            post(new Runnable() {
                public void run() {
                    LargeImageView.this.initFitImageScale(imageWidth, imageHeight);
                }
            });
        } else {
            initFitImageScale(imageWidth, imageHeight);
        }
        notifyInvalidate();
        if (this.mOnImageLoadListener != null) {
            this.mOnImageLoadListener.onLoadImageSize(imageWidth, imageHeight);
        }
    }

    public void onLoadFail(Exception e) {
        if (this.mOnImageLoadListener != null) {
            this.mOnImageLoadListener.onLoadFail(e);
        }
    }

    private void initFitImageScale(int imageWidth, int imageHeight) {
        int layoutWidth = getMeasuredWidth();
        int layoutHeight = getMeasuredHeight();
        if (imageWidth > imageHeight) {
            this.fitScale = (((((float) imageWidth) * 1.0f) / ((float) layoutWidth)) * ((float) layoutHeight)) / ((float) imageHeight);
            this.maxScale = ((((float) imageWidth) * 1.0f) / ((float) layoutWidth)) * 4.0f;
            this.minScale = ((((float) imageWidth) * 1.0f) / ((float) layoutWidth)) / 4.0f;
            if (this.minScale > 1.0f) {
                this.minScale = 1.0f;
            }
        } else {
            this.fitScale = 1.0f;
            this.minScale = 0.25f;
            this.maxScale = (((float) imageWidth) * 1.0f) / ((float) layoutWidth);
            float a = (((((float) imageWidth) * 1.0f) / ((float) layoutWidth)) * ((float) layoutHeight)) / ((float) imageHeight);
            this.maxScale *= getContext().getResources().getDisplayMetrics().density;
            if (this.maxScale < 4.0f) {
                this.maxScale = 4.0f;
            }
            if (this.minScale > a) {
                this.minScale = a;
            }
        }
        this.minScale = 1.0f;
    }

    private void notifyInvalidate() {
        ViewCompat.postInvalidateOnAnimation(this);
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mDrawable != null) {
            boolean z;
            Drawable drawable = this.mDrawable;
            if (getVisibility() == 0) {
                z = true;
            } else {
                z = false;
            }
            drawable.setVisible(z, false);
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.imageBlockLoader.destroy();
        if (this.mDrawable != null) {
            this.mDrawable.setVisible(false, false);
        }
    }

    private boolean overScrollByCompat(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        int oldScrollX = getScrollX();
        int oldScrollY = getScrollY();
        int newScrollX = scrollX + deltaX;
        int newScrollY = scrollY + deltaY;
        int left = -maxOverScrollX;
        int right = maxOverScrollX + scrollRangeX;
        int top = -maxOverScrollY;
        int bottom = maxOverScrollY + scrollRangeY;
        boolean clampedX = false;
        if (newScrollX > right) {
            newScrollX = right;
            clampedX = true;
        } else if (newScrollX < left) {
            newScrollX = left;
            clampedX = true;
        }
        boolean clampedY = false;
        if (this.mLargeImageOperationListener != null) {
            this.mLargeImageOperationListener.onScroll();
        }
        if (newScrollY > bottom) {
            newScrollY = bottom;
            clampedY = true;
            if (this.mLargeImageOperationListener != null) {
                this.mLargeImageOperationListener.onScrollBottom();
            }
        } else if (newScrollY < top) {
            newScrollY = top;
            clampedY = true;
            if (this.mLargeImageOperationListener != null) {
                this.mLargeImageOperationListener.onScrollTop();
            }
        }
        if (newScrollX < 0) {
            newScrollX = 0;
        }
        if (newScrollY < 0) {
            newScrollY = 0;
        }
        onOverScrolled(newScrollX, newScrollY, clampedX, clampedY);
        if (getScrollX() - oldScrollX == deltaX || getScrollY() - oldScrollY == deltaY) {
            return true;
        }
        return false;
    }

    private boolean fling(int velocityX, int velocityY) {
        if (Math.abs(velocityX) < this.mMinimumVelocity) {
            velocityX = 0;
        }
        if (Math.abs(velocityY) < this.mMinimumVelocity) {
            velocityY = 0;
        }
        int scrollY = getScrollY();
        int scrollX = getScrollX();
        boolean canFlingX = (scrollX > 0 || velocityX > 0) && (scrollX < getScrollRangeX() || velocityX < 0);
        boolean canFlingY = (scrollY > 0 || velocityY > 0) && (scrollY < getScrollRangeY() || velocityY < 0);
        boolean canFling = canFlingY || canFlingX;
        if (!canFling) {
            return false;
        }
        int height = (getHeight() - getPaddingBottom()) - getPaddingTop();
        int width = (getWidth() - getPaddingRight()) - getPaddingLeft();
        this.mScroller.fling(getScrollX(), getScrollY(), Math.max(-this.mMaximumVelocity, Math.min(velocityX, this.mMaximumVelocity)), Math.max(-this.mMaximumVelocity, Math.min(velocityY, this.mMaximumVelocity)), 0, Math.max(0, getContentWidth() - width), 0, Math.max(0, getContentHeight() - height), width / 2, height / 2);
        notifyInvalidate();
        return true;
    }

    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.scrollTo(scrollX, scrollY);
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (hasLoad()) {
            initFitImageScale(this.mDrawableWidth, this.mDrawableHeight);
        }
    }

    public void smoothScale(float newScale, int centerX, int centerY) {
        if (this.mScale > newScale) {
            if (this.accelerateInterpolator == null) {
                this.accelerateInterpolator = new AccelerateInterpolator();
            }
            this.scaleHelper.startScale(this.mScale, newScale, centerX, centerY, this.accelerateInterpolator);
        } else {
            if (this.decelerateInterpolator == null) {
                this.decelerateInterpolator = new DecelerateInterpolator();
            }
            this.scaleHelper.startScale(this.mScale, newScale, centerX, centerY, this.decelerateInterpolator);
        }
        notifyInvalidate();
    }

    public void setScale(float scale) {
        setScale(scale, getMeasuredWidth() >> 1, getMeasuredHeight() >> 1);
    }

    public float getScale() {
        return this.mScale;
    }

    public void setScale(float scale, int centerX, int centerY) {
        if (hasLoad()) {
            float preScale = this.mScale;
            this.mScale = scale;
            int sX = getScrollX();
            int sY = getScrollY();
            overScrollByCompat((int) (((float) (sX + centerX)) * ((scale / preScale) - 1.0f)), (int) (((float) (sY + centerY)) * ((scale / preScale) - 1.0f)), sX, sY, getScrollRangeX(), getScrollRangeY(), 0, 0, false);
            notifyInvalidate();
            if (this.mLargeImageOperationListener != null) {
                this.mLargeImageOperationListener.onScale(this.mScale);
            }
        }
    }

    public void setLargeImageClickListener(LargeImageClickListener mLargeImageClickListener) {
        this.mLargeImageClickListener = mLargeImageClickListener;
    }

    public void setLargeImageOperationListener(LargeImageOperationListener l) {
        this.mLargeImageOperationListener = l;
    }
}
