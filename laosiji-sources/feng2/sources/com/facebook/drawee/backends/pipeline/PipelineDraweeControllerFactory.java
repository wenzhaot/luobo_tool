package com.facebook.drawee.backends.pipeline;

import android.content.res.Resources;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.Supplier;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.components.DeferredReleaser;
import com.facebook.imagepipeline.animated.factory.AnimatedDrawableFactory;
import com.facebook.imagepipeline.cache.MemoryCache;
import com.facebook.imagepipeline.image.CloseableImage;
import java.util.concurrent.Executor;

public class PipelineDraweeControllerFactory {
    private AnimatedDrawableFactory mAnimatedDrawableFactory;
    private DeferredReleaser mDeferredReleaser;
    private MemoryCache<CacheKey, CloseableImage> mMemoryCache;
    private Resources mResources;
    private Executor mUiThreadExecutor;

    public PipelineDraweeControllerFactory(Resources resources, DeferredReleaser deferredReleaser, AnimatedDrawableFactory animatedDrawableFactory, Executor uiThreadExecutor, MemoryCache<CacheKey, CloseableImage> memoryCache) {
        this.mResources = resources;
        this.mDeferredReleaser = deferredReleaser;
        this.mAnimatedDrawableFactory = animatedDrawableFactory;
        this.mUiThreadExecutor = uiThreadExecutor;
        this.mMemoryCache = memoryCache;
    }

    public PipelineDraweeController newController(Supplier<DataSource<CloseableReference<CloseableImage>>> dataSourceSupplier, String id, CacheKey cacheKey, Object callerContext) {
        return new PipelineDraweeController(this.mResources, this.mDeferredReleaser, this.mAnimatedDrawableFactory, this.mUiThreadExecutor, this.mMemoryCache, dataSourceSupplier, id, cacheKey, callerContext);
    }
}
