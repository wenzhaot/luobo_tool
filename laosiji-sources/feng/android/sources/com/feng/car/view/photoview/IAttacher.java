package com.feng.car.view.photoview;

import android.view.GestureDetector.OnDoubleTapListener;
import android.view.View.OnLongClickListener;

public interface IAttacher {
    public static final float DEFAULT_MAX_SCALE = 3.0f;
    public static final float DEFAULT_MID_SCALE = 2.0f;
    public static final float DEFAULT_MIN_SCALE = 1.0f;
    public static final long ZOOM_DURATION = 200;

    float getMaximumScale();

    float getMediumScale();

    float getMinimumScale();

    OnPhotoTapListener getOnPhotoTapListener();

    OnViewTapListener getOnViewTapListener();

    float getScale();

    void setAllowParentInterceptOnEdge(boolean z);

    void setMaximumScale(float f);

    void setMediumScale(float f);

    void setMinimumScale(float f);

    void setOnDoubleTapListener(OnDoubleTapListener onDoubleTapListener);

    void setOnLongClickListener(OnLongClickListener onLongClickListener);

    void setOnPhotoTapListener(OnPhotoTapListener onPhotoTapListener);

    void setOnScaleChangeListener(OnScaleChangeListener onScaleChangeListener);

    void setOnViewTapListener(OnViewTapListener onViewTapListener);

    void setScale(float f);

    void setScale(float f, float f2, float f3, boolean z);

    void setScale(float f, boolean z);

    void setZoomTransitionDuration(long j);

    void update(int i, int i2);
}
