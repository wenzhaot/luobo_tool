package com.facebook.imagepipeline.animated.factory;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Build.VERSION;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.animated.base.AnimatedDrawableBackend;
import com.facebook.imagepipeline.animated.base.AnimatedImage;
import com.facebook.imagepipeline.animated.base.AnimatedImageResult;
import com.facebook.imagepipeline.animated.impl.AnimatedDrawableBackendProvider;
import com.facebook.imagepipeline.animated.impl.AnimatedImageCompositor;
import com.facebook.imagepipeline.animated.impl.AnimatedImageCompositor.Callback;
import com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.image.CloseableAnimatedImage;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.memory.PooledByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class AnimatedImageFactoryImpl implements AnimatedImageFactory {
    static AnimatedImageDecoder sGifAnimatedImageDecoder;
    static AnimatedImageDecoder sWebpAnimatedImageDecoder;
    private final AnimatedDrawableBackendProvider mAnimatedDrawableBackendProvider;
    private final PlatformBitmapFactory mBitmapFactory;

    static {
        sGifAnimatedImageDecoder = null;
        sWebpAnimatedImageDecoder = null;
        sGifAnimatedImageDecoder = loadIfPresent("com.facebook.animated.gif.GifImage");
        sWebpAnimatedImageDecoder = loadIfPresent("com.facebook.animated.webp.WebPImage");
    }

    private static AnimatedImageDecoder loadIfPresent(String className) {
        try {
            return (AnimatedImageDecoder) Class.forName(className).newInstance();
        } catch (Throwable th) {
            return null;
        }
    }

    public AnimatedImageFactoryImpl(AnimatedDrawableBackendProvider animatedDrawableBackendProvider, PlatformBitmapFactory bitmapFactory) {
        this.mAnimatedDrawableBackendProvider = animatedDrawableBackendProvider;
        this.mBitmapFactory = bitmapFactory;
    }

    public CloseableImage decodeGif(EncodedImage encodedImage, ImageDecodeOptions options, Config bitmapConfig) {
        if (sGifAnimatedImageDecoder == null) {
            throw new UnsupportedOperationException("To encode animated gif please add the dependency to the animated-gif module");
        }
        CloseableReference bytesRef = encodedImage.getByteBufferRef();
        Preconditions.checkNotNull(bytesRef);
        try {
            Preconditions.checkState(!options.forceOldAnimationCode);
            PooledByteBuffer input = (PooledByteBuffer) bytesRef.get();
            CloseableImage closeableImage = getCloseableImage(options, sGifAnimatedImageDecoder.decode(input.getNativePtr(), input.size()), bitmapConfig);
            return closeableImage;
        } finally {
            CloseableReference.closeSafely(bytesRef);
        }
    }

    public CloseableImage decodeWebP(EncodedImage encodedImage, ImageDecodeOptions options, Config bitmapConfig) {
        if (sWebpAnimatedImageDecoder == null) {
            throw new UnsupportedOperationException("To encode animated webp please add the dependency to the animated-webp module");
        }
        CloseableReference bytesRef = encodedImage.getByteBufferRef();
        Preconditions.checkNotNull(bytesRef);
        try {
            Preconditions.checkArgument(!options.forceOldAnimationCode);
            PooledByteBuffer input = (PooledByteBuffer) bytesRef.get();
            CloseableImage closeableImage = getCloseableImage(options, sWebpAnimatedImageDecoder.decode(input.getNativePtr(), input.size()), bitmapConfig);
            return closeableImage;
        } finally {
            CloseableReference.closeSafely(bytesRef);
        }
    }

    private CloseableAnimatedImage getCloseableImage(ImageDecodeOptions options, AnimatedImage image, Config bitmapConfig) {
        Iterable decodedFrames = null;
        CloseableReference previewBitmap = null;
        try {
            int frameForPreview = options.useLastFrameForPreview ? image.getFrameCount() - 1 : 0;
            if (options.decodeAllFrames) {
                decodedFrames = decodeAllFrames(image, bitmapConfig);
                previewBitmap = CloseableReference.cloneOrNull((CloseableReference) decodedFrames.get(frameForPreview));
            }
            if (options.decodePreviewFrame && previewBitmap == null) {
                previewBitmap = createPreviewBitmap(image, bitmapConfig, frameForPreview);
            }
            CloseableAnimatedImage closeableAnimatedImage = new CloseableAnimatedImage(AnimatedImageResult.newBuilder(image).setPreviewBitmap(previewBitmap).setFrameForPreview(frameForPreview).setDecodedFrames(decodedFrames).build());
            return closeableAnimatedImage;
        } finally {
            CloseableReference.closeSafely(previewBitmap);
            CloseableReference.closeSafely(decodedFrames);
        }
    }

    private CloseableReference<Bitmap> createPreviewBitmap(AnimatedImage image, Config bitmapConfig, int frameForPreview) {
        CloseableReference<Bitmap> bitmap = createBitmap(image.getWidth(), image.getHeight(), bitmapConfig);
        new AnimatedImageCompositor(this.mAnimatedDrawableBackendProvider.get(AnimatedImageResult.forAnimatedImage(image), null), new Callback() {
            public void onIntermediateResult(int frameNumber, Bitmap bitmap) {
            }

            public CloseableReference<Bitmap> getCachedBitmap(int frameNumber) {
                return null;
            }
        }).renderFrame(frameForPreview, (Bitmap) bitmap.get());
        return bitmap;
    }

    private List<CloseableReference<Bitmap>> decodeAllFrames(AnimatedImage image, Config bitmapConfig) {
        final List<CloseableReference<Bitmap>> bitmaps = new ArrayList();
        AnimatedDrawableBackend drawableBackend = this.mAnimatedDrawableBackendProvider.get(AnimatedImageResult.forAnimatedImage(image), null);
        AnimatedImageCompositor animatedImageCompositor = new AnimatedImageCompositor(drawableBackend, new Callback() {
            public void onIntermediateResult(int frameNumber, Bitmap bitmap) {
            }

            public CloseableReference<Bitmap> getCachedBitmap(int frameNumber) {
                return CloseableReference.cloneOrNull((CloseableReference) bitmaps.get(frameNumber));
            }
        });
        for (int i = 0; i < drawableBackend.getFrameCount(); i++) {
            CloseableReference<Bitmap> bitmap = createBitmap(drawableBackend.getWidth(), drawableBackend.getHeight(), bitmapConfig);
            animatedImageCompositor.renderFrame(i, (Bitmap) bitmap.get());
            bitmaps.add(bitmap);
        }
        return bitmaps;
    }

    @SuppressLint({"NewApi"})
    private CloseableReference<Bitmap> createBitmap(int width, int height, Config bitmapConfig) {
        CloseableReference<Bitmap> bitmap = this.mBitmapFactory.createBitmap(width, height, bitmapConfig);
        ((Bitmap) bitmap.get()).eraseColor(0);
        if (VERSION.SDK_INT >= 12) {
            ((Bitmap) bitmap.get()).setHasAlpha(true);
        }
        return bitmap;
    }
}
