package com.feng.car.view.photoview;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.MotionEvent;
import android.view.View.OnLongClickListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;

public class PhotoDraweeView extends SimpleDraweeView implements IAttacher {
    private Attacher mAttacher;

    public PhotoDraweeView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
        init();
    }

    public PhotoDraweeView(Context context) {
        super(context);
        init();
    }

    public PhotoDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PhotoDraweeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    protected void init() {
        if (this.mAttacher == null || this.mAttacher.getDraweeView() == null) {
            this.mAttacher = new Attacher(this);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    protected void onDraw(@NonNull Canvas canvas) {
        int saveCount = canvas.save();
        canvas.concat(this.mAttacher.getDrawMatrix());
        super.onDraw(canvas);
        canvas.restoreToCount(saveCount);
    }

    protected void onAttachedToWindow() {
        init();
        super.onAttachedToWindow();
    }

    protected void onDetachedFromWindow() {
        this.mAttacher.onDetachedFromWindow();
        super.onDetachedFromWindow();
    }

    public float getMinimumScale() {
        return this.mAttacher.getMinimumScale();
    }

    public float getMediumScale() {
        return this.mAttacher.getMediumScale();
    }

    public float getMaximumScale() {
        return this.mAttacher.getMaximumScale();
    }

    public void setMinimumScale(float minimumScale) {
        this.mAttacher.setMinimumScale(minimumScale);
    }

    public void setMediumScale(float mediumScale) {
        this.mAttacher.setMediumScale(mediumScale);
    }

    public void setMaximumScale(float maximumScale) {
        this.mAttacher.setMaximumScale(maximumScale);
    }

    public float getScale() {
        return this.mAttacher.getScale();
    }

    public void setScale(float scale) {
        this.mAttacher.setScale(scale);
    }

    public void setScale(float scale, boolean animate) {
        this.mAttacher.setScale(scale, animate);
    }

    public void setScale(float scale, float focalX, float focalY, boolean animate) {
        this.mAttacher.setScale(scale, focalX, focalY, animate);
    }

    public void setZoomTransitionDuration(long duration) {
        this.mAttacher.setZoomTransitionDuration(duration);
    }

    public void setAllowParentInterceptOnEdge(boolean allow) {
        this.mAttacher.setAllowParentInterceptOnEdge(allow);
    }

    public void setOnDoubleTapListener(OnDoubleTapListener listener) {
        this.mAttacher.setOnDoubleTapListener(listener);
    }

    public void setOnScaleChangeListener(OnScaleChangeListener listener) {
        this.mAttacher.setOnScaleChangeListener(listener);
    }

    public void setOnLongClickListener(OnLongClickListener listener) {
        this.mAttacher.setOnLongClickListener(listener);
    }

    public void setOnPhotoTapListener(OnPhotoTapListener listener) {
        this.mAttacher.setOnPhotoTapListener(listener);
    }

    public void setOnViewTapListener(OnViewTapListener listener) {
        this.mAttacher.setOnViewTapListener(listener);
    }

    public OnPhotoTapListener getOnPhotoTapListener() {
        return this.mAttacher.getOnPhotoTapListener();
    }

    public OnViewTapListener getOnViewTapListener() {
        return this.mAttacher.getOnViewTapListener();
    }

    public void update(int imageInfoWidth, int imageInfoHeight) {
        this.mAttacher.update(imageInfoWidth, imageInfoHeight);
    }

    public void resetDragable(boolean value) {
        this.mAttacher.setDragable(value);
    }
}
