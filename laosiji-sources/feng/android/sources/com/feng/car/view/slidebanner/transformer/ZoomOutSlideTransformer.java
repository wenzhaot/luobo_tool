package com.feng.car.view.slidebanner.transformer;

import android.view.View;

public class ZoomOutSlideTransformer extends ABaseTransformer {
    private static final float MIN_ALPHA = 0.5f;
    private static final float MIN_SCALE = 0.85f;

    protected void onTransform(View view, float position) {
        if (position >= -1.0f || position <= 1.0f) {
            float height = (float) view.getHeight();
            float width = (float) view.getWidth();
            float scaleFactor = Math.max(MIN_SCALE, 1.0f - Math.abs(position));
            float vertMargin = ((1.0f - scaleFactor) * height) / 2.0f;
            float horzMargin = ((1.0f - scaleFactor) * width) / 2.0f;
            view.setPivotY(MIN_ALPHA * height);
            view.setPivotX(MIN_ALPHA * width);
            if (position < 0.0f) {
                view.setTranslationX(horzMargin - (vertMargin / 2.0f));
            } else {
                view.setTranslationX((-horzMargin) + (vertMargin / 2.0f));
            }
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
            view.setAlpha((((scaleFactor - MIN_SCALE) / 0.14999998f) * MIN_ALPHA) + MIN_ALPHA);
        }
    }
}
