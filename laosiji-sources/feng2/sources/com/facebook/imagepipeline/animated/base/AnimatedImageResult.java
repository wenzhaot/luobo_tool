package com.facebook.imagepipeline.animated.base;

import android.graphics.Bitmap;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.references.CloseableReference;
import java.util.List;
import javax.annotation.Nullable;

public class AnimatedImageResult {
    @Nullable
    private List<CloseableReference<Bitmap>> mDecodedFrames;
    private final int mFrameForPreview;
    private final AnimatedImage mImage;
    @Nullable
    private CloseableReference<Bitmap> mPreviewBitmap;

    AnimatedImageResult(AnimatedImageResultBuilder builder) {
        this.mImage = (AnimatedImage) Preconditions.checkNotNull(builder.getImage());
        this.mFrameForPreview = builder.getFrameForPreview();
        this.mPreviewBitmap = builder.getPreviewBitmap();
        this.mDecodedFrames = builder.getDecodedFrames();
    }

    private AnimatedImageResult(AnimatedImage image) {
        this.mImage = (AnimatedImage) Preconditions.checkNotNull(image);
        this.mFrameForPreview = 0;
    }

    public static AnimatedImageResult forAnimatedImage(AnimatedImage image) {
        return new AnimatedImageResult(image);
    }

    public static AnimatedImageResultBuilder newBuilder(AnimatedImage image) {
        return new AnimatedImageResultBuilder(image);
    }

    public AnimatedImage getImage() {
        return this.mImage;
    }

    public int getFrameForPreview() {
        return this.mFrameForPreview;
    }

    @Nullable
    public synchronized CloseableReference<Bitmap> getDecodedFrame(int index) {
        CloseableReference<Bitmap> cloneOrNull;
        if (this.mDecodedFrames != null) {
            cloneOrNull = CloseableReference.cloneOrNull((CloseableReference) this.mDecodedFrames.get(index));
        } else {
            cloneOrNull = null;
        }
        return cloneOrNull;
    }

    public synchronized boolean hasDecodedFrame(int index) {
        boolean z;
        z = (this.mDecodedFrames == null || this.mDecodedFrames.get(index) == null) ? false : true;
        return z;
    }

    public synchronized CloseableReference<Bitmap> getPreviewBitmap() {
        return CloseableReference.cloneOrNull(this.mPreviewBitmap);
    }

    public synchronized void dispose() {
        CloseableReference.closeSafely(this.mPreviewBitmap);
        this.mPreviewBitmap = null;
        CloseableReference.closeSafely(this.mDecodedFrames);
        this.mDecodedFrames = null;
    }
}
