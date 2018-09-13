package com.feng.car.view.cropimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;

public class ClipImageLayout extends RelativeLayout {
    private ClipImageBorderView mClipImageView;
    private int mHorizontalPadding = 20;
    private ClipZoomImageView mZoomImageView;

    public void setIsCropHead(int type) {
        if (this.mZoomImageView != null) {
            this.mZoomImageView.setCropType(type);
        }
        if (this.mClipImageView != null) {
            this.mClipImageView.setIsCropHead(type);
        }
    }

    public ClipImageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mZoomImageView = new ClipZoomImageView(context);
        this.mClipImageView = new ClipImageBorderView(context);
        LayoutParams lp = new RelativeLayout.LayoutParams(-1, -1);
        addView(this.mZoomImageView, lp);
        addView(this.mClipImageView, lp);
        this.mHorizontalPadding = (int) TypedValue.applyDimension(1, (float) this.mHorizontalPadding, getResources().getDisplayMetrics());
        this.mZoomImageView.setHorizontalPadding(this.mHorizontalPadding);
        this.mClipImageView.setHorizontalPadding(this.mHorizontalPadding);
    }

    public void setImageDrawable(Drawable drawable) {
        this.mZoomImageView.setImageDrawable(drawable);
    }

    public void setImageBitmap(Bitmap bitmap) {
        this.mZoomImageView.setImageBitmap(bitmap);
    }

    public void setHorizontalPadding(int mHorizontalPadding) {
        this.mHorizontalPadding = mHorizontalPadding;
    }

    public Bitmap clip() {
        return this.mZoomImageView.clip();
    }
}
