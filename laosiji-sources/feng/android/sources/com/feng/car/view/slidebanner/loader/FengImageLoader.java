package com.feng.car.view.slidebanner.loader;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;
import com.facebook.drawee.drawable.ScalingUtils.ScaleType;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.feng.car.view.AutoFrescoDraweeView;

public class FengImageLoader extends ImageLoader {
    public void displayImage(Context context, Object path, ImageView imageView) {
        Uri uri = Uri.parse((String) path);
        if (imageView instanceof AutoFrescoDraweeView) {
            ((AutoFrescoDraweeView) imageView).setAutoImageURI(uri);
        } else {
            imageView.setImageURI(uri);
        }
    }

    public ImageView createImageView(Context context) {
        AutoFrescoDraweeView simpleDraweeView = new AutoFrescoDraweeView(context);
        simpleDraweeView.setHierarchy(new GenericDraweeHierarchyBuilder(context.getResources()).setActualImageScaleType(ScaleType.CENTER_CROP).setPlaceholderImage(2130837822, ScaleType.CENTER_CROP).setFailureImage(2130838408, ScaleType.CENTER_INSIDE).setProgressBarImage(2130838408, ScaleType.CENTER_INSIDE).setPressedStateOverlay(context.getResources().getDrawable(2130838387)).build());
        return simpleDraweeView;
    }
}
