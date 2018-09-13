package com.facebook.drawee.backends.pipeline;

import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.Objects;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Supplier;
import com.facebook.common.logging.FLog;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawable.base.DrawableWithCaches;
import com.facebook.drawee.components.DeferredReleaser;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.drawable.OrientedDrawable;
import com.facebook.imagepipeline.animated.factory.AnimatedDrawableFactory;
import com.facebook.imagepipeline.cache.MemoryCache;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.CloseableStaticBitmap;
import com.facebook.imagepipeline.image.ImageInfo;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;

public class PipelineDraweeController extends AbstractDraweeController<CloseableReference<CloseableImage>, ImageInfo> {
    private static final Class<?> TAG = PipelineDraweeController.class;
    private static Experiment sExperiment;
    private final AnimatedDrawableFactory mAnimatedDrawableFactory;
    private CacheKey mCacheKey;
    private Supplier<DataSource<CloseableReference<CloseableImage>>> mDataSourceSupplier;
    @Nullable
    private MemoryCache<CacheKey, CloseableImage> mMemoryCache;
    private final Resources mResources;

    protected static class Experiment {
        private boolean mIsFastCheckEnabled;

        protected Experiment() {
        }

        public Experiment setFastCheckEnabled(boolean fastCheckEnabled) {
            this.mIsFastCheckEnabled = fastCheckEnabled;
            return this;
        }
    }

    public PipelineDraweeController(Resources resources, DeferredReleaser deferredReleaser, AnimatedDrawableFactory animatedDrawableFactory, Executor uiThreadExecutor, MemoryCache<CacheKey, CloseableImage> memoryCache, Supplier<DataSource<CloseableReference<CloseableImage>>> dataSourceSupplier, String id, CacheKey cacheKey, Object callerContext) {
        super(deferredReleaser, uiThreadExecutor, id, callerContext);
        this.mResources = resources;
        this.mAnimatedDrawableFactory = animatedDrawableFactory;
        this.mMemoryCache = memoryCache;
        this.mCacheKey = cacheKey;
        init(dataSourceSupplier);
    }

    public void initialize(Supplier<DataSource<CloseableReference<CloseableImage>>> dataSourceSupplier, String id, CacheKey cacheKey, Object callerContext) {
        super.initialize(id, callerContext);
        init(dataSourceSupplier);
        this.mCacheKey = cacheKey;
    }

    private void init(Supplier<DataSource<CloseableReference<CloseableImage>>> dataSourceSupplier) {
        this.mDataSourceSupplier = dataSourceSupplier;
    }

    protected Resources getResources() {
        return this.mResources;
    }

    protected DataSource<CloseableReference<CloseableImage>> getDataSource() {
        if (FLog.isLoggable(2)) {
            FLog.v(TAG, "controller %x: getDataSource", Integer.valueOf(System.identityHashCode(this)));
        }
        return (DataSource) this.mDataSourceSupplier.get();
    }

    protected Drawable createDrawable(CloseableReference<CloseableImage> image) {
        Preconditions.checkState(CloseableReference.isValid(image));
        CloseableImage closeableImage = (CloseableImage) image.get();
        if (closeableImage instanceof CloseableStaticBitmap) {
            CloseableStaticBitmap closeableStaticBitmap = (CloseableStaticBitmap) closeableImage;
            Drawable bitmapDrawable = new BitmapDrawable(this.mResources, closeableStaticBitmap.getUnderlyingBitmap());
            if (closeableStaticBitmap.getRotationAngle() == 0 || closeableStaticBitmap.getRotationAngle() == -1) {
                return bitmapDrawable;
            }
            return new OrientedDrawable(bitmapDrawable, closeableStaticBitmap.getRotationAngle());
        } else if (this.mAnimatedDrawableFactory != null) {
            return this.mAnimatedDrawableFactory.create(closeableImage);
        } else {
            throw new UnsupportedOperationException("Unrecognized image class: " + closeableImage);
        }
    }

    protected ImageInfo getImageInfo(CloseableReference<CloseableImage> image) {
        Preconditions.checkState(CloseableReference.isValid(image));
        return (ImageInfo) image.get();
    }

    protected int getImageHash(@Nullable CloseableReference<CloseableImage> image) {
        return image != null ? image.getValueHash() : 0;
    }

    protected void releaseImage(@Nullable CloseableReference<CloseableImage> image) {
        CloseableReference.closeSafely((CloseableReference) image);
    }

    protected void releaseDrawable(@Nullable Drawable drawable) {
        if (drawable instanceof DrawableWithCaches) {
            ((DrawableWithCaches) drawable).dropCaches();
        }
    }

    protected CloseableReference<CloseableImage> getCachedImage() {
        if (!getExperiment().mIsFastCheckEnabled) {
            return null;
        }
        if (this.mMemoryCache == null || this.mCacheKey == null) {
            return null;
        }
        CloseableReference<CloseableImage> closeableImage = this.mMemoryCache.get(this.mCacheKey);
        if (closeableImage == null || ((CloseableImage) closeableImage.get()).getQualityInfo().isOfFullQuality()) {
            return closeableImage;
        }
        closeableImage.close();
        return null;
    }

    public String toString() {
        return Objects.toStringHelper((Object) this).add("super", super.toString()).add("dataSourceSupplier", this.mDataSourceSupplier).toString();
    }

    protected static Experiment getExperiment() {
        if (sExperiment == null) {
            sExperiment = new Experiment();
        }
        return sExperiment;
    }
}