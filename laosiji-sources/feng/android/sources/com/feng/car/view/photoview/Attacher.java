package com.feng.car.view.photoview;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.ScrollerCompat;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewParent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import com.facebook.drawee.drawable.ScalingUtils.ScaleType;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.DraweeView;
import java.lang.ref.WeakReference;

public class Attacher implements IAttacher, OnTouchListener, OnScaleDragGestureListener {
    private static final int EDGE_BOTH = 2;
    private static final int EDGE_LEFT = 0;
    private static final int EDGE_NONE = -1;
    private static final int EDGE_RIGHT = 1;
    private boolean isDragable = true;
    private boolean mAllowParentInterceptOnEdge = true;
    private boolean mBlockParentIntercept = false;
    private FlingRunnable mCurrentFlingRunnable;
    private final RectF mDisplayRect = new RectF();
    private WeakReference<DraweeView<GenericDraweeHierarchy>> mDraweeView;
    private GestureDetectorCompat mGestureDetector;
    private int mImageInfoHeight = -1;
    private int mImageInfoWidth = -1;
    private OnLongClickListener mLongClickListener;
    private final Matrix mMatrix = new Matrix();
    private final float[] mMatrixValues = new float[9];
    private float mMaxScale = 3.0f;
    private float mMidScale = 2.0f;
    private float mMinScale = 1.0f;
    private OnPhotoTapListener mPhotoTapListener;
    private OnScaleChangeListener mScaleChangeListener;
    private ScaleDragDetector mScaleDragDetector;
    private int mScrollEdge = 2;
    private OnViewTapListener mViewTapListener;
    private long mZoomDuration = 200;
    private final Interpolator mZoomInterpolator = new AccelerateDecelerateInterpolator();

    private class AnimatedZoomRunnable implements Runnable {
        private final float mFocalX;
        private final float mFocalY;
        private final long mStartTime = System.currentTimeMillis();
        private final float mZoomEnd;
        private final float mZoomStart;

        public AnimatedZoomRunnable(float currentZoom, float targetZoom, float focalX, float focalY) {
            this.mFocalX = focalX;
            this.mFocalY = focalY;
            this.mZoomStart = currentZoom;
            this.mZoomEnd = targetZoom;
        }

        public void run() {
            DraweeView<GenericDraweeHierarchy> draweeView = Attacher.this.getDraweeView();
            if (draweeView != null) {
                float t = interpolate();
                Attacher.this.onScale((this.mZoomStart + ((this.mZoomEnd - this.mZoomStart) * t)) / Attacher.this.getScale(), this.mFocalX, this.mFocalY);
                if (t < 1.0f) {
                    Attacher.this.postOnAnimation(draweeView, this);
                }
            }
        }

        private float interpolate() {
            return Attacher.this.mZoomInterpolator.getInterpolation(Math.min(1.0f, (((float) (System.currentTimeMillis() - this.mStartTime)) * 1.0f) / ((float) Attacher.this.mZoomDuration)));
        }
    }

    private class FlingRunnable implements Runnable {
        private int mCurrentX;
        private int mCurrentY;
        private final ScrollerCompat mScroller;

        public FlingRunnable(Context context) {
            this.mScroller = ScrollerCompat.create(context);
        }

        public void cancelFling() {
            this.mScroller.abortAnimation();
        }

        public void fling(int viewWidth, int viewHeight, int velocityX, int velocityY) {
            RectF rect = Attacher.this.getDisplayRect();
            if (rect != null) {
                int minX;
                int maxX;
                int minY;
                int maxY;
                int startX = Math.round(-rect.left);
                if (((float) viewWidth) < rect.width()) {
                    minX = 0;
                    maxX = Math.round(rect.width() - ((float) viewWidth));
                } else {
                    maxX = startX;
                    minX = startX;
                }
                int startY = Math.round(-rect.top);
                if (((float) viewHeight) < rect.height()) {
                    minY = 0;
                    maxY = Math.round(rect.height() - ((float) viewHeight));
                } else {
                    maxY = startY;
                    minY = startY;
                }
                this.mCurrentX = startX;
                this.mCurrentY = startY;
                if (startX != maxX || startY != maxY) {
                    this.mScroller.fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY, 0, 0);
                }
            }
        }

        public void run() {
            if (!this.mScroller.isFinished()) {
                DraweeView<GenericDraweeHierarchy> draweeView = Attacher.this.getDraweeView();
                if (draweeView != null && this.mScroller.computeScrollOffset()) {
                    int newX = this.mScroller.getCurrX();
                    int newY = this.mScroller.getCurrY();
                    Attacher.this.mMatrix.postTranslate((float) (this.mCurrentX - newX), (float) (this.mCurrentY - newY));
                    draweeView.invalidate();
                    this.mCurrentX = newX;
                    this.mCurrentY = newY;
                    Attacher.this.postOnAnimation(draweeView, this);
                }
            }
        }
    }

    public void setDragable(boolean value) {
        this.isDragable = value;
    }

    public Attacher(DraweeView<GenericDraweeHierarchy> draweeView) {
        this.mDraweeView = new WeakReference(draweeView);
        ((GenericDraweeHierarchy) draweeView.getHierarchy()).setActualImageScaleType(ScaleType.FIT_CENTER);
        draweeView.setOnTouchListener(this);
        this.mScaleDragDetector = new ScaleDragDetector(draweeView.getContext(), this);
        this.mGestureDetector = new GestureDetectorCompat(draweeView.getContext(), new SimpleOnGestureListener() {
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                if (Attacher.this.mLongClickListener != null) {
                    Attacher.this.mLongClickListener.onLongClick(Attacher.this.getDraweeView());
                }
            }
        });
        this.mGestureDetector.setOnDoubleTapListener(new DefaultOnDoubleTapListener(this));
    }

    public void setOnDoubleTapListener(OnDoubleTapListener newOnDoubleTapListener) {
        if (newOnDoubleTapListener != null) {
            this.mGestureDetector.setOnDoubleTapListener(newOnDoubleTapListener);
        } else {
            this.mGestureDetector.setOnDoubleTapListener(new DefaultOnDoubleTapListener(this));
        }
    }

    @Nullable
    public DraweeView<GenericDraweeHierarchy> getDraweeView() {
        return (DraweeView) this.mDraweeView.get();
    }

    public float getMinimumScale() {
        return this.mMinScale;
    }

    public float getMediumScale() {
        return this.mMidScale;
    }

    public float getMaximumScale() {
        return this.mMaxScale;
    }

    public void setMaximumScale(float maximumScale) {
        checkZoomLevels(this.mMinScale, this.mMidScale, maximumScale);
        this.mMaxScale = maximumScale;
    }

    public void setMediumScale(float mediumScale) {
        checkZoomLevels(this.mMinScale, mediumScale, this.mMaxScale);
        this.mMidScale = mediumScale;
    }

    public void setMinimumScale(float minimumScale) {
        checkZoomLevels(minimumScale, this.mMidScale, this.mMaxScale);
        this.mMinScale = minimumScale;
    }

    public float getScale() {
        return (float) Math.sqrt((double) (((float) Math.pow((double) getMatrixValue(this.mMatrix, 0), 2.0d)) + ((float) Math.pow((double) getMatrixValue(this.mMatrix, 3), 2.0d))));
    }

    public void setScale(float scale) {
        setScale(scale, false);
    }

    public void setScale(float scale, boolean animate) {
        DraweeView<GenericDraweeHierarchy> draweeView = getDraweeView();
        if (draweeView != null) {
            setScale(scale, (float) (draweeView.getRight() / 2), (float) (draweeView.getBottom() / 2), false);
        }
    }

    public void setScale(float scale, float focalX, float focalY, boolean animate) {
        DraweeView<GenericDraweeHierarchy> draweeView = getDraweeView();
        if (draweeView != null && scale >= this.mMinScale && scale <= this.mMaxScale) {
            if (animate) {
                draweeView.post(new AnimatedZoomRunnable(getScale(), scale, focalX, focalY));
                return;
            }
            this.mMatrix.setScale(scale, scale, focalX, focalY);
            checkMatrixAndInvalidate();
        }
    }

    public void setZoomTransitionDuration(long duration) {
        if (duration < 0) {
            duration = 200;
        }
        this.mZoomDuration = duration;
    }

    public void setAllowParentInterceptOnEdge(boolean allow) {
        this.mAllowParentInterceptOnEdge = allow;
    }

    public void setOnScaleChangeListener(OnScaleChangeListener listener) {
        this.mScaleChangeListener = listener;
    }

    public void setOnLongClickListener(OnLongClickListener listener) {
        this.mLongClickListener = listener;
    }

    public void setOnPhotoTapListener(OnPhotoTapListener listener) {
        this.mPhotoTapListener = listener;
    }

    public void setOnViewTapListener(OnViewTapListener listener) {
        this.mViewTapListener = listener;
    }

    public OnPhotoTapListener getOnPhotoTapListener() {
        return this.mPhotoTapListener;
    }

    public OnViewTapListener getOnViewTapListener() {
        return this.mViewTapListener;
    }

    public void update(int imageInfoWidth, int imageInfoHeight) {
        this.mImageInfoWidth = imageInfoWidth;
        this.mImageInfoHeight = imageInfoHeight;
        updateBaseMatrix();
    }

    private static void checkZoomLevels(float minZoom, float midZoom, float maxZoom) {
        if (minZoom >= midZoom) {
            throw new IllegalArgumentException("MinZoom has to be less than MidZoom");
        } else if (midZoom >= maxZoom) {
            throw new IllegalArgumentException("MidZoom has to be less than MaxZoom");
        }
    }

    private int getViewWidth() {
        DraweeView<GenericDraweeHierarchy> draweeView = getDraweeView();
        if (draweeView != null) {
            return (draweeView.getWidth() - draweeView.getPaddingLeft()) - draweeView.getPaddingRight();
        }
        return 0;
    }

    private int getViewHeight() {
        DraweeView<GenericDraweeHierarchy> draweeView = getDraweeView();
        if (draweeView != null) {
            return (draweeView.getHeight() - draweeView.getPaddingTop()) - draweeView.getPaddingBottom();
        }
        return 0;
    }

    private float getMatrixValue(Matrix matrix, int whichValue) {
        matrix.getValues(this.mMatrixValues);
        return this.mMatrixValues[whichValue];
    }

    public Matrix getDrawMatrix() {
        return this.mMatrix;
    }

    public RectF getDisplayRect() {
        checkMatrixBounds();
        return getDisplayRect(getDrawMatrix());
    }

    public void checkMatrixAndInvalidate() {
        DraweeView<GenericDraweeHierarchy> draweeView = getDraweeView();
        if (draweeView != null && checkMatrixBounds()) {
            draweeView.invalidate();
        }
    }

    public boolean checkMatrixBounds() {
        RectF rect = getDisplayRect(getDrawMatrix());
        if (rect == null) {
            return false;
        }
        float height = rect.height();
        float width = rect.width();
        float deltaX = 0.0f;
        float deltaY = 0.0f;
        int viewHeight = getViewHeight();
        if (height <= ((float) viewHeight)) {
            deltaY = ((((float) viewHeight) - height) / 2.0f) - rect.top;
        } else if (rect.top > 0.0f) {
            deltaY = -rect.top;
        } else if (rect.bottom < ((float) viewHeight)) {
            deltaY = ((float) viewHeight) - rect.bottom;
        }
        int viewWidth = getViewWidth();
        if (1.0f + width <= ((float) viewWidth)) {
            deltaX = ((((float) viewWidth) - width) / 2.0f) - rect.left;
            this.mScrollEdge = 2;
        } else if (rect.left > 0.0f) {
            deltaX = -rect.left;
            this.mScrollEdge = 0;
        } else if (rect.right < ((float) viewWidth)) {
            deltaX = ((float) viewWidth) - rect.right;
            this.mScrollEdge = 1;
        } else {
            this.mScrollEdge = -1;
        }
        this.mMatrix.postTranslate(deltaX, deltaY);
        return true;
    }

    private RectF getDisplayRect(Matrix matrix) {
        DraweeView<GenericDraweeHierarchy> draweeView = getDraweeView();
        if (draweeView == null || (this.mImageInfoWidth == -1 && this.mImageInfoHeight == -1)) {
            return null;
        }
        this.mDisplayRect.set(0.0f, 0.0f, (float) this.mImageInfoWidth, (float) this.mImageInfoHeight);
        ((GenericDraweeHierarchy) draweeView.getHierarchy()).getActualImageBounds(this.mDisplayRect);
        matrix.mapRect(this.mDisplayRect);
        return this.mDisplayRect;
    }

    private void updateBaseMatrix() {
        if (this.mImageInfoWidth != -1 || this.mImageInfoHeight != -1) {
            resetMatrix();
        }
    }

    private void resetMatrix() {
        this.mMatrix.reset();
        checkMatrixBounds();
        DraweeView<GenericDraweeHierarchy> draweeView = getDraweeView();
        if (draweeView != null) {
            draweeView.invalidate();
        }
    }

    private void checkMinScale() {
        DraweeView<GenericDraweeHierarchy> draweeView = getDraweeView();
        if (draweeView != null && getScale() < this.mMinScale) {
            RectF rect = getDisplayRect();
            if (rect != null) {
                draweeView.post(new AnimatedZoomRunnable(getScale(), this.mMinScale, rect.centerX(), rect.centerY()));
            }
        }
    }

    public void onScale(float scaleFactor, float focusX, float focusY) {
        if (getScale() < this.mMaxScale || scaleFactor < 1.0f) {
            if (this.mScaleChangeListener != null) {
                this.mScaleChangeListener.onScaleChange(getScale(), focusX, focusY);
            }
            this.mMatrix.postScale(scaleFactor, scaleFactor, focusX, focusY);
            checkMatrixAndInvalidate();
        }
    }

    public void onScaleEnd() {
        checkMinScale();
    }

    public void onDrag(float dx, float dy) {
        if (this.isDragable) {
            DraweeView<GenericDraweeHierarchy> draweeView = getDraweeView();
            if (draweeView != null && !this.mScaleDragDetector.isScaling()) {
                this.mMatrix.postTranslate(dx, dy);
                checkMatrixAndInvalidate();
                ViewParent parent = draweeView.getParent();
                if (parent == null) {
                    return;
                }
                if (!this.mAllowParentInterceptOnEdge || this.mScaleDragDetector.isScaling() || this.mBlockParentIntercept) {
                    parent.requestDisallowInterceptTouchEvent(true);
                } else if (this.mScrollEdge == 2 || ((this.mScrollEdge == 0 && dx >= 1.0f && Math.abs(dx) - Math.abs(dy) > 0.0f) || (this.mScrollEdge == 1 && dx <= -1.0f && Math.abs(dx) - Math.abs(dy) > 0.0f))) {
                    parent.requestDisallowInterceptTouchEvent(false);
                }
            }
        }
    }

    public void onFling(float startX, float startY, float velocityX, float velocityY) {
        DraweeView<GenericDraweeHierarchy> draweeView = getDraweeView();
        if (draweeView != null) {
            this.mCurrentFlingRunnable = new FlingRunnable(draweeView.getContext());
            this.mCurrentFlingRunnable.fling(getViewWidth(), getViewHeight(), (int) velocityX, (int) velocityY);
            draweeView.post(this.mCurrentFlingRunnable);
        }
    }

    public boolean onTouch(View v, MotionEvent event) {
        boolean noScale;
        boolean z = true;
        ViewParent parent;
        switch (MotionEventCompat.getActionMasked(event)) {
            case 0:
                parent = v.getParent();
                if (parent != null) {
                    parent.requestDisallowInterceptTouchEvent(true);
                }
                cancelFling();
                break;
            case 1:
            case 3:
                parent = v.getParent();
                if (parent != null) {
                    parent.requestDisallowInterceptTouchEvent(false);
                    break;
                }
                break;
        }
        boolean wasScaling = this.mScaleDragDetector.isScaling();
        boolean wasDragging = this.mScaleDragDetector.isDragging();
        boolean handled = this.mScaleDragDetector.onTouchEvent(event);
        if (wasScaling || this.mScaleDragDetector.isScaling()) {
            noScale = false;
        } else {
            noScale = true;
        }
        boolean noDrag;
        if (wasDragging || this.mScaleDragDetector.isDragging()) {
            noDrag = false;
        } else {
            noDrag = true;
        }
        if (!(noScale && noDrag)) {
            z = false;
        }
        this.mBlockParentIntercept = z;
        if (this.mGestureDetector.onTouchEvent(event)) {
            return true;
        }
        return handled;
    }

    private void cancelFling() {
        if (this.mCurrentFlingRunnable != null) {
            this.mCurrentFlingRunnable.cancelFling();
            this.mCurrentFlingRunnable = null;
        }
    }

    private void postOnAnimation(View view, Runnable runnable) {
        if (VERSION.SDK_INT >= 16) {
            view.postOnAnimation(runnable);
        } else {
            view.postDelayed(runnable, 16);
        }
    }

    protected void onDetachedFromWindow() {
        cancelFling();
    }
}
