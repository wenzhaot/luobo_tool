package com.facebook.imagepipeline.animated.factory;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import com.facebook.common.executors.DefaultSerialExecutorService;
import com.facebook.common.executors.SerialExecutorService;
import com.facebook.common.executors.UiThreadImmediateExecutorService;
import com.facebook.common.internal.DoNotStrip;
import com.facebook.common.time.MonotonicClock;
import com.facebook.common.time.RealtimeSinceBootClock;
import com.facebook.imagepipeline.animated.base.AnimatedDrawableBackend;
import com.facebook.imagepipeline.animated.base.AnimatedDrawableOptions;
import com.facebook.imagepipeline.animated.base.AnimatedImageResult;
import com.facebook.imagepipeline.animated.impl.AnimatedDrawableBackendImpl;
import com.facebook.imagepipeline.animated.impl.AnimatedDrawableBackendProvider;
import com.facebook.imagepipeline.animated.impl.AnimatedDrawableCachingBackendImpl;
import com.facebook.imagepipeline.animated.impl.AnimatedDrawableCachingBackendImplProvider;
import com.facebook.imagepipeline.animated.util.AnimatedDrawableUtil;
import com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory;
import com.facebook.imagepipeline.core.ExecutorSupplier;
import java.util.concurrent.ScheduledExecutorService;
import javax.annotation.concurrent.NotThreadSafe;

@DoNotStrip
@NotThreadSafe
public class AnimatedFactoryImpl implements AnimatedFactory {
    private AnimatedDrawableBackendProvider mAnimatedDrawableBackendProvider;
    private AnimatedDrawableFactory mAnimatedDrawableFactory;
    private AnimatedDrawableUtil mAnimatedDrawableUtil;
    private AnimatedImageFactory mAnimatedImageFactory;
    private ExecutorSupplier mExecutorSupplier;
    private PlatformBitmapFactory mPlatformBitmapFactory;

    @DoNotStrip
    public AnimatedFactoryImpl(PlatformBitmapFactory platformBitmapFactory, ExecutorSupplier executorSupplier) {
        this.mPlatformBitmapFactory = platformBitmapFactory;
        this.mExecutorSupplier = executorSupplier;
    }

    private AnimatedDrawableFactory buildAnimatedDrawableFactory(SerialExecutorService serialExecutorService, ActivityManager activityManager, AnimatedDrawableUtil animatedDrawableUtil, AnimatedDrawableBackendProvider animatedDrawableBackendProvider, ScheduledExecutorService scheduledExecutorService, MonotonicClock monotonicClock, Resources resources) {
        final SerialExecutorService serialExecutorService2 = serialExecutorService;
        final ActivityManager activityManager2 = activityManager;
        final AnimatedDrawableUtil animatedDrawableUtil2 = animatedDrawableUtil;
        final MonotonicClock monotonicClock2 = monotonicClock;
        return createAnimatedDrawableFactory(animatedDrawableBackendProvider, new AnimatedDrawableCachingBackendImplProvider() {
            public AnimatedDrawableCachingBackendImpl get(AnimatedDrawableBackend animatedDrawableBackend, AnimatedDrawableOptions options) {
                return new AnimatedDrawableCachingBackendImpl(serialExecutorService2, activityManager2, animatedDrawableUtil2, monotonicClock2, animatedDrawableBackend, options);
            }
        }, animatedDrawableUtil, scheduledExecutorService, resources);
    }

    private AnimatedDrawableBackendProvider getAnimatedDrawableBackendProvider() {
        if (this.mAnimatedDrawableBackendProvider == null) {
            this.mAnimatedDrawableBackendProvider = new AnimatedDrawableBackendProvider() {
                public AnimatedDrawableBackend get(AnimatedImageResult animatedImageResult, Rect bounds) {
                    return new AnimatedDrawableBackendImpl(AnimatedFactoryImpl.this.getAnimatedDrawableUtil(), animatedImageResult, bounds);
                }
            };
        }
        return this.mAnimatedDrawableBackendProvider;
    }

    public AnimatedDrawableFactory getAnimatedDrawableFactory(Context context) {
        if (this.mAnimatedDrawableFactory == null) {
            this.mAnimatedDrawableFactory = buildAnimatedDrawableFactory(new DefaultSerialExecutorService(this.mExecutorSupplier.forDecode()), (ActivityManager) context.getSystemService("activity"), getAnimatedDrawableUtil(), getAnimatedDrawableBackendProvider(), UiThreadImmediateExecutorService.getInstance(), RealtimeSinceBootClock.get(), context.getResources());
        }
        return this.mAnimatedDrawableFactory;
    }

    private AnimatedDrawableUtil getAnimatedDrawableUtil() {
        if (this.mAnimatedDrawableUtil == null) {
            this.mAnimatedDrawableUtil = new AnimatedDrawableUtil();
        }
        return this.mAnimatedDrawableUtil;
    }

    private AnimatedImageFactory buildAnimatedImageFactory() {
        return new AnimatedImageFactoryImpl(new AnimatedDrawableBackendProvider() {
            public AnimatedDrawableBackend get(AnimatedImageResult imageResult, Rect bounds) {
                return new AnimatedDrawableBackendImpl(AnimatedFactoryImpl.this.getAnimatedDrawableUtil(), imageResult, bounds);
            }
        }, this.mPlatformBitmapFactory);
    }

    public AnimatedImageFactory getAnimatedImageFactory() {
        if (this.mAnimatedImageFactory == null) {
            this.mAnimatedImageFactory = buildAnimatedImageFactory();
        }
        return this.mAnimatedImageFactory;
    }

    protected AnimatedDrawableFactory createAnimatedDrawableFactory(AnimatedDrawableBackendProvider animatedDrawableBackendProvider, AnimatedDrawableCachingBackendImplProvider animatedDrawableCachingBackendImplProvider, AnimatedDrawableUtil animatedDrawableUtil, ScheduledExecutorService scheduledExecutorService, Resources resources) {
        return new AnimatedDrawableFactoryImpl(animatedDrawableBackendProvider, animatedDrawableCachingBackendImplProvider, animatedDrawableUtil, scheduledExecutorService, resources);
    }
}
