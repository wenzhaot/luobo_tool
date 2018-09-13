package com.feng.car.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

public class AutoFrescoDraweeView extends SimpleDraweeView {
    public AutoFrescoDraweeView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public AutoFrescoDraweeView(Context context) {
        super(context);
    }

    public AutoFrescoDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoFrescoDraweeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @TargetApi(21)
    public AutoFrescoDraweeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setAutoImageURI(Uri uri) {
        setController((PipelineDraweeController) ((PipelineDraweeControllerBuilder) ((PipelineDraweeControllerBuilder) ((PipelineDraweeControllerBuilder) Fresco.newDraweeControllerBuilder().setOldController(getController())).setImageRequest(ImageRequestBuilder.newBuilderWithSource(uri).build())).setAutoPlayAnimations(true)).build());
    }

    public void loadFileImage(Uri uri, int width, int height) {
        setController((PipelineDraweeController) ((PipelineDraweeControllerBuilder) ((PipelineDraweeControllerBuilder) ((PipelineDraweeControllerBuilder) Fresco.newDraweeControllerBuilder().setOldController(getController())).setImageRequest(ImageRequestBuilder.newBuilderWithSource(uri).setResizeOptions(new ResizeOptions(width, height)).build())).setAutoPlayAnimations(true)).build());
    }

    public void setHeadUrl(String url) {
        setAutoImageURI(Uri.parse(url));
    }

    public void setDraweeImage(String url, int width, int height) {
        if (width > 150) {
            width = 150;
            height = 150;
        }
        setController((PipelineDraweeController) ((PipelineDraweeControllerBuilder) ((PipelineDraweeControllerBuilder) Fresco.newDraweeControllerBuilder().setOldController(getController())).setImageRequest(ImageRequestBuilder.newBuilderWithSource(Uri.parse(url)).setResizeOptions(new ResizeOptions(width, height)).setAutoRotateEnabled(true).build())).build());
    }
}
