package com.facebook.imagepipeline.animated.base;

import android.graphics.Bitmap;
import com.facebook.common.references.CloseableReference;
import java.util.Collection;
import java.util.List;

public class AnimatedImageResultBuilder {
    private List<CloseableReference<Bitmap>> mDecodedFrames;
    private int mFrameForPreview;
    private final AnimatedImage mImage;
    private CloseableReference<Bitmap> mPreviewBitmap;

    AnimatedImageResultBuilder(AnimatedImage image) {
        this.mImage = image;
    }

    public AnimatedImage getImage() {
        return this.mImage;
    }

    public CloseableReference<Bitmap> getPreviewBitmap() {
        return CloseableReference.cloneOrNull(this.mPreviewBitmap);
    }

    public AnimatedImageResultBuilder setPreviewBitmap(CloseableReference<Bitmap> previewBitmap) {
        this.mPreviewBitmap = CloseableReference.cloneOrNull((CloseableReference) previewBitmap);
        return this;
    }

    public int getFrameForPreview() {
        return this.mFrameForPreview;
    }

    public AnimatedImageResultBuilder setFrameForPreview(int frameForPreview) {
        this.mFrameForPreview = frameForPreview;
        return this;
    }

    public List<CloseableReference<Bitmap>> getDecodedFrames() {
        return CloseableReference.cloneOrNull(this.mDecodedFrames);
    }

    public AnimatedImageResultBuilder setDecodedFrames(List<CloseableReference<Bitmap>> decodedFrames) {
        this.mDecodedFrames = CloseableReference.cloneOrNull((Collection) decodedFrames);
        return this;
    }

    public AnimatedImageResult build() {
        try {
            AnimatedImageResult animatedImageResult = new AnimatedImageResult(this);
            return animatedImageResult;
        } finally {
            CloseableReference.closeSafely(this.mPreviewBitmap);
            this.mPreviewBitmap = null;
            CloseableReference.closeSafely(this.mDecodedFrames);
            this.mDecodedFrames = null;
        }
    }
}
