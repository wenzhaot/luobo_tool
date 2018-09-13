package com.feng.car.view.slidebanner.transformer;

import android.view.View;

public class FlipVerticalTransformer extends ABaseTransformer {
    protected void onTransform(View view, float position) {
        float rotation = -180.0f * position;
        float f = (rotation > 90.0f || rotation < -90.0f) ? 0.0f : 1.0f;
        view.setAlpha(f);
        view.setPivotX(((float) view.getWidth()) * 0.5f);
        view.setPivotY(((float) view.getHeight()) * 0.5f);
        view.setRotationX(rotation);
    }

    protected void onPostTransform(View page, float position) {
        super.onPostTransform(page, position);
        if (position <= -0.5f || position >= 0.5f) {
            page.setVisibility(4);
        } else {
            page.setVisibility(0);
        }
    }
}
