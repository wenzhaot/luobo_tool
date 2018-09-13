package com.facebook.drawee.backends.pipeline;

import android.content.Context;
import android.net.Uri;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.controller.AbstractDraweeControllerBuilder;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.imagepipeline.cache.CacheKeyFactory;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import java.util.Set;
import javax.annotation.Nullable;

public class PipelineDraweeControllerBuilder extends AbstractDraweeControllerBuilder<PipelineDraweeControllerBuilder, ImageRequest, CloseableReference<CloseableImage>, ImageInfo> {
    private final ImagePipeline mImagePipeline;
    private final PipelineDraweeControllerFactory mPipelineDraweeControllerFactory;

    public PipelineDraweeControllerBuilder(Context context, PipelineDraweeControllerFactory pipelineDraweeControllerFactory, ImagePipeline imagePipeline, Set<ControllerListener> boundControllerListeners) {
        super(context, boundControllerListeners);
        this.mImagePipeline = imagePipeline;
        this.mPipelineDraweeControllerFactory = pipelineDraweeControllerFactory;
    }

    public PipelineDraweeControllerBuilder setUri(Uri uri) {
        return (PipelineDraweeControllerBuilder) super.setImageRequest(ImageRequest.fromUri(uri));
    }

    public PipelineDraweeControllerBuilder setUri(@Nullable String uriString) {
        return (PipelineDraweeControllerBuilder) super.setImageRequest(ImageRequest.fromUri(uriString));
    }

    protected PipelineDraweeController obtainController() {
        DraweeController oldController = getOldController();
        if (!(oldController instanceof PipelineDraweeController)) {
            return this.mPipelineDraweeControllerFactory.newController(obtainDataSourceSupplier(), AbstractDraweeControllerBuilder.generateUniqueControllerId(), getCacheKey(), getCallerContext());
        }
        PipelineDraweeController controller = (PipelineDraweeController) oldController;
        controller.initialize(obtainDataSourceSupplier(), AbstractDraweeControllerBuilder.generateUniqueControllerId(), getCacheKey(), getCallerContext());
        return controller;
    }

    private CacheKey getCacheKey() {
        ImageRequest imageRequest = (ImageRequest) getImageRequest();
        CacheKeyFactory cacheKeyFactory = this.mImagePipeline.getCacheKeyFactory();
        if (cacheKeyFactory == null || imageRequest == null) {
            return null;
        }
        if (imageRequest.getPostprocessor() != null) {
            return cacheKeyFactory.getPostprocessedBitmapCacheKey(imageRequest, getCallerContext());
        }
        return cacheKeyFactory.getBitmapCacheKey(imageRequest, getCallerContext());
    }

    protected DataSource<CloseableReference<CloseableImage>> getDataSourceForRequest(ImageRequest imageRequest, Object callerContext, boolean bitmapCacheOnly) {
        if (bitmapCacheOnly) {
            return this.mImagePipeline.fetchImageFromBitmapCache(imageRequest, callerContext);
        }
        return this.mImagePipeline.fetchDecodedImage(imageRequest, callerContext);
    }

    protected PipelineDraweeControllerBuilder getThis() {
        return this;
    }
}