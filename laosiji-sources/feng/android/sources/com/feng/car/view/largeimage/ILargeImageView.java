package com.feng.car.view.largeimage;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import com.feng.car.view.largeimage.BlockImageLoader.OnImageLoadListener;
import com.feng.car.view.largeimage.factory.BitmapDecoderFactory;

interface ILargeImageView {
    int getImageHeight();

    int getImageWidth();

    OnImageLoadListener getOnImageLoadListener();

    float getScale();

    boolean hasLoad();

    void setImage(@DrawableRes int i);

    void setImage(Bitmap bitmap);

    void setImage(BitmapDecoderFactory bitmapDecoderFactory);

    void setImage(BitmapDecoderFactory bitmapDecoderFactory, Drawable drawable);

    void setImageDrawable(Drawable drawable);

    void setOnImageLoadListener(OnImageLoadListener onImageLoadListener);

    void setScale(float f);
}
