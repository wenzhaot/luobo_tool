package com.feng.car.view.largeimage;

import android.annotation.SuppressLint;
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
import android.util.AttributeSet;
import com.feng.car.view.largeimage.BlockImageLoader.DrawData;
import com.feng.car.view.largeimage.BlockImageLoader.OnImageLoadListener;
import com.feng.car.view.largeimage.factory.BitmapDecoderFactory;
import java.util.List;

public class UpdateImageView extends UpdateView implements OnImageLoadListener, ILargeImageView {
    private Drawable defaultDrawable;
    private BlockImageLoader imageBlockLoader;
    private Drawable mDrawable;
    private int mDrawableHeight;
    private int mDrawableWidth;
    private BitmapDecoderFactory mFactory;
    private int mLevel;
    private float mOffsetX;
    private float mOffsetY;
    private float mScale;
    private OnImageLoadListener onImageLoadListener;
    private Rect tempRect;

    public UpdateImageView(Context context) {
        this(context, null);
    }

    public UpdateImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UpdateImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.tempRect = new Rect();
        this.imageBlockLoader = new BlockImageLoader(context);
        this.imageBlockLoader.setOnImageLoadListener(this);
    }

    public void setScale(float scale) {
        this.mScale = scale;
        notifyInvalidate();
    }

    public float getScale() {
        return this.mScale;
    }

    public OnImageLoadListener getOnImageLoadListener() {
        return this.onImageLoadListener;
    }

    public void setScale(float scale, float offsetX, float offsetY) {
        this.mScale = scale;
        this.mOffsetX = offsetX;
        this.mOffsetY = offsetY;
        notifyInvalidate();
    }

    public int getImageWidth() {
        if (this.mDrawable != null) {
            return this.mDrawable.getIntrinsicWidth();
        }
        return this.imageBlockLoader.getWidth();
    }

    public int getImageHeight() {
        if (this.mDrawable != null) {
            return this.mDrawable.getIntrinsicHeight();
        }
        return this.imageBlockLoader.getHeight();
    }

    public void setSelected(boolean selected) {
        super.setSelected(selected);
        resizeFromDrawable();
    }

    private void resizeFromDrawable() {
        Drawable d = this.mDrawable;
        if (d != null) {
            int w = d.getIntrinsicWidth();
            if (w < 0) {
                w = this.mDrawableWidth;
            }
            int h = d.getIntrinsicHeight();
            if (h < 0) {
                h = this.mDrawableHeight;
            }
            if (w != this.mDrawableWidth || h != this.mDrawableHeight) {
                this.mDrawableWidth = w;
                this.mDrawableHeight = h;
                requestLayout();
            }
        }
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

    public boolean hasLoad() {
        if (this.mDrawable != null) {
            return true;
        }
        if (this.mFactory == null) {
            return false;
        }
        if (this.defaultDrawable == null) {
            return this.imageBlockLoader.hasLoad();
        }
        return true;
    }

    public void setOnImageLoadListener(OnImageLoadListener onImageLoadListener) {
        this.onImageLoadListener = onImageLoadListener;
    }

    public void setImage(BitmapDecoderFactory factory) {
        setImage(factory, null);
    }

    public void setImage(BitmapDecoderFactory factory, Drawable defaultDrawable) {
        this.mScale = 1.0f;
        this.mOffsetX = 0.0f;
        this.mOffsetY = 0.0f;
        this.mDrawable = null;
        this.mFactory = factory;
        this.defaultDrawable = defaultDrawable;
        if (defaultDrawable != null) {
            onLoadImageSize(defaultDrawable.getIntrinsicWidth(), defaultDrawable.getIntrinsicHeight());
        }
        this.imageBlockLoader.load(factory);
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
        this.mOffsetX = 0.0f;
        this.mOffsetY = 0.0f;
        if (this.mDrawable != drawable) {
            int oldWidth = this.mDrawableWidth;
            int oldHeight = this.mDrawableHeight;
            updateDrawable(drawable);
            onLoadImageSize(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            if (!(oldWidth == this.mDrawableWidth && oldHeight == this.mDrawableHeight)) {
                requestLayout();
            }
            invalidate();
        }
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

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable d = this.mDrawable;
        if (d != null && d.isStateful()) {
            d.setState(getDrawableState());
        }
    }

    public void drawableHotspotChanged(float x, float y) {
        super.drawableHotspotChanged(x, y);
        if (this.mDrawable != null) {
            DrawableCompat.setHotspot(this.mDrawable, x, y);
        }
    }

    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (this.mDrawable != null) {
            boolean z;
            Drawable drawable = this.mDrawable;
            if (visibility == 0) {
                z = true;
            } else {
                z = false;
            }
            drawable.setVisible(z, false);
        }
    }

    protected void onUpdateWindow(Rect visibilityRect) {
        if (this.mFactory != null && hasLoad()) {
            notifyInvalidate();
        }
    }

    private void notifyInvalidate() {
        ViewCompat.postInvalidateOnAnimation(this);
    }

    @SuppressLint({"DrawAllocation"})
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getMeasuredWidth() != 0) {
            if (this.mDrawable != null) {
                this.mDrawable.setBounds((int) this.mOffsetX, (int) this.mOffsetY, (int) (((float) getMeasuredWidth()) * this.mScale), (int) (((float) getMeasuredHeight()) * this.mScale));
                this.mDrawable.draw(canvas);
            } else if (this.mFactory != null) {
                Rect visibilityRect = this.tempRect;
                getVisibilityRect(visibilityRect);
                float imageScale = ((float) this.imageBlockLoader.getWidth()) / (this.mScale * ((float) getWidth()));
                Rect imageRect = new Rect();
                imageRect.left = (int) Math.ceil((double) ((((float) visibilityRect.left) - this.mOffsetX) * imageScale));
                imageRect.top = (int) Math.ceil((double) ((((float) visibilityRect.top) - this.mOffsetY) * imageScale));
                imageRect.right = (int) Math.ceil((double) ((((float) visibilityRect.right) - this.mOffsetX) * imageScale));
                imageRect.bottom = (int) Math.ceil((double) ((((float) visibilityRect.bottom) - this.mOffsetY) * imageScale));
                List<DrawData> drawData = this.imageBlockLoader.getDrawData(imageScale, imageRect);
                if (!drawData.isEmpty()) {
                    int saveCount = canvas.save();
                    for (DrawData data : drawData) {
                        Rect drawRect = data.imageRect;
                        drawRect.left = (int) (Math.ceil((double) (((float) drawRect.left) / imageScale)) + ((double) this.mOffsetX));
                        drawRect.top = (int) (Math.ceil((double) (((float) drawRect.top) / imageScale)) + ((double) this.mOffsetY));
                        drawRect.right = (int) (Math.ceil((double) (((float) drawRect.right) / imageScale)) + ((double) this.mOffsetX));
                        drawRect.bottom = (int) (Math.ceil((double) (((float) drawRect.bottom) / imageScale)) + ((double) this.mOffsetY));
                        canvas.drawBitmap(data.bitmap, data.srcRect, drawRect, null);
                    }
                    canvas.restoreToCount(saveCount);
                } else if (this.defaultDrawable != null) {
                    int height = (int) (((1.0f * ((float) this.defaultDrawable.getIntrinsicHeight())) / ((float) this.defaultDrawable.getIntrinsicWidth())) * ((float) getMeasuredWidth()));
                    this.defaultDrawable.setBounds((int) this.mOffsetX, ((int) this.mOffsetY) + ((getMeasuredHeight() - height) / 2), (int) (((float) getMeasuredWidth()) * this.mScale), (int) (((float) height) * this.mScale));
                    this.defaultDrawable.draw(canvas);
                }
            }
        }
    }

    public void onBlockImageLoadFinished() {
        notifyInvalidate();
        if (this.onImageLoadListener != null) {
            this.onImageLoadListener.onBlockImageLoadFinished();
        }
    }

    public void onLoadImageSize(int imageWidth, int imageHeight) {
        this.mDrawableWidth = imageWidth;
        this.mDrawableHeight = imageHeight;
        notifyInvalidate();
        if (this.onImageLoadListener != null) {
            this.onImageLoadListener.onLoadImageSize(imageWidth, imageHeight);
        }
    }

    public void onLoadFail(Exception e) {
        if (this.onImageLoadListener != null) {
            this.onImageLoadListener.onLoadFail(e);
        }
    }
}
