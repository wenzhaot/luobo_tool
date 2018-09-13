package com.feng.car.view.photoview;

import android.graphics.RectF;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.MotionEvent;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.DraweeView;

public class DefaultOnDoubleTapListener implements OnDoubleTapListener {
    private Attacher mAttacher;

    public DefaultOnDoubleTapListener(Attacher attacher) {
        setPhotoDraweeViewAttacher(attacher);
    }

    public boolean onSingleTapConfirmed(MotionEvent e) {
        if (this.mAttacher == null) {
            return false;
        }
        DraweeView<GenericDraweeHierarchy> draweeView = this.mAttacher.getDraweeView();
        if (draweeView == null) {
            return false;
        }
        if (this.mAttacher.getOnPhotoTapListener() != null) {
            RectF displayRect = this.mAttacher.getDisplayRect();
            if (displayRect != null) {
                float x = e.getX();
                float y = e.getY();
                if (displayRect.contains(x, y)) {
                    this.mAttacher.getOnPhotoTapListener().onPhotoTap(draweeView, (x - displayRect.left) / displayRect.width(), (y - displayRect.top) / displayRect.height());
                    return true;
                }
            }
        }
        if (this.mAttacher.getOnViewTapListener() == null) {
            return false;
        }
        this.mAttacher.getOnViewTapListener().onViewTap(draweeView, e.getX(), e.getY());
        return true;
    }

    public boolean onDoubleTap(MotionEvent event) {
        if (this.mAttacher == null) {
            return false;
        }
        try {
            float scale = this.mAttacher.getScale();
            float x = event.getX();
            float y = event.getY();
            if (scale < this.mAttacher.getMediumScale()) {
                this.mAttacher.setScale(this.mAttacher.getMediumScale(), x, y, true);
                return true;
            } else if (scale < this.mAttacher.getMediumScale() || scale >= this.mAttacher.getMaximumScale()) {
                this.mAttacher.setScale(this.mAttacher.getMinimumScale(), x, y, true);
                return true;
            } else {
                this.mAttacher.setScale(this.mAttacher.getMaximumScale(), x, y, true);
                return true;
            }
        } catch (Exception e) {
            return true;
        }
    }

    public boolean onDoubleTapEvent(MotionEvent event) {
        return false;
    }

    public void setPhotoDraweeViewAttacher(Attacher attacher) {
        this.mAttacher = attacher;
    }
}
