package com.facebook.imagepipeline.animated.factory;

import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import com.facebook.common.time.MonotonicClock;
import com.facebook.imagepipeline.animated.base.AnimatedDrawable;
import com.facebook.imagepipeline.animated.base.AnimatedDrawableBackend;
import com.facebook.imagepipeline.animated.base.AnimatedDrawableCachingBackend;
import com.facebook.imagepipeline.animated.base.AnimatedDrawableDiagnostics;
import com.facebook.imagepipeline.animated.base.AnimatedDrawableOptions;
import com.facebook.imagepipeline.animated.base.AnimatedImage;
import com.facebook.imagepipeline.animated.base.AnimatedImageResult;
import com.facebook.imagepipeline.animated.impl.AnimatedDrawableBackendProvider;
import com.facebook.imagepipeline.animated.impl.AnimatedDrawableCachingBackendImplProvider;
import com.facebook.imagepipeline.animated.impl.AnimatedDrawableDiagnosticsImpl;
import com.facebook.imagepipeline.animated.impl.AnimatedDrawableDiagnosticsNoop;
import com.facebook.imagepipeline.animated.util.AnimatedDrawableUtil;
import com.facebook.imagepipeline.image.CloseableAnimatedImage;
import com.facebook.imagepipeline.image.CloseableImage;
import java.util.concurrent.ScheduledExecutorService;

public class AnimatedDrawableFactoryImpl implements AnimatedDrawableFactory {
    private final AnimatedDrawableBackendProvider mAnimatedDrawableBackendProvider;
    private final AnimatedDrawableCachingBackendImplProvider mAnimatedDrawableCachingBackendProvider;
    private final AnimatedDrawableUtil mAnimatedDrawableUtil;
    private final MonotonicClock mMonotonicClock = new MonotonicClock() {
        public long now() {
            return SystemClock.uptimeMillis();
        }
    };
    private final Resources mResources;
    private final ScheduledExecutorService mScheduledExecutorServiceForUiThread;

    public AnimatedDrawableFactoryImpl(AnimatedDrawableBackendProvider animatedDrawableBackendProvider, AnimatedDrawableCachingBackendImplProvider animatedDrawableCachingBackendProvider, AnimatedDrawableUtil animatedDrawableUtil, ScheduledExecutorService scheduledExecutorService, Resources resources) {
        this.mAnimatedDrawableBackendProvider = animatedDrawableBackendProvider;
        this.mAnimatedDrawableCachingBackendProvider = animatedDrawableCachingBackendProvider;
        this.mAnimatedDrawableUtil = animatedDrawableUtil;
        this.mScheduledExecutorServiceForUiThread = scheduledExecutorService;
        this.mResources = resources;
    }

    public Drawable create(CloseableImage closeableImage) {
        if (closeableImage instanceof CloseableAnimatedImage) {
            return create(((CloseableAnimatedImage) closeableImage).getImageResult(), AnimatedDrawableOptions.DEFAULTS);
        }
        throw new UnsupportedOperationException("Unrecognized image class: " + closeableImage);
    }

    private AnimatedDrawable create(AnimatedImageResult animatedImageResult, AnimatedDrawableOptions options) {
        AnimatedImage animatedImage = animatedImageResult.getImage();
        return createAnimatedDrawable(options, this.mAnimatedDrawableBackendProvider.get(animatedImageResult, new Rect(0, 0, animatedImage.getWidth(), animatedImage.getHeight())));
    }

    private AnimatedImageResult getImageIfCloseableAnimatedImage(CloseableImage image) {
        if (image instanceof CloseableAnimatedImage) {
            return ((CloseableAnimatedImage) image).getImageResult();
        }
        return null;
    }

    private AnimatedDrawable createAnimatedDrawable(AnimatedDrawableOptions options, AnimatedDrawableBackend animatedDrawableBackend) {
        AnimatedDrawableDiagnostics animatedDrawableDiagnostics;
        DisplayMetrics displayMetrics = this.mResources.getDisplayMetrics();
        AnimatedDrawableCachingBackend animatedDrawableCachingBackend = this.mAnimatedDrawableCachingBackendProvider.get(animatedDrawableBackend, options);
        if (options.enableDebugging) {
            animatedDrawableDiagnostics = new AnimatedDrawableDiagnosticsImpl(this.mAnimatedDrawableUtil, displayMetrics);
        } else {
            animatedDrawableDiagnostics = AnimatedDrawableDiagnosticsNoop.getInstance();
        }
        return new AnimatedDrawable(this.mScheduledExecutorServiceForUiThread, animatedDrawableCachingBackend, animatedDrawableDiagnostics, this.mMonotonicClock);
    }
}
