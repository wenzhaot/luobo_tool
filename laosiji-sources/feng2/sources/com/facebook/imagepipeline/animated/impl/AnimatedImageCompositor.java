package com.facebook.imagepipeline.animated.impl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.animated.base.AnimatedDrawableBackend;
import com.facebook.imagepipeline.animated.base.AnimatedDrawableFrameInfo;
import com.facebook.imagepipeline.animated.base.AnimatedDrawableFrameInfo.BlendOperation;
import com.facebook.imagepipeline.animated.base.AnimatedDrawableFrameInfo.DisposalMethod;

public class AnimatedImageCompositor {
    private final AnimatedDrawableBackend mAnimatedDrawableBackend;
    private final Callback mCallback;
    private final Paint mTransparentFillPaint = new Paint();

    public interface Callback {
        CloseableReference<Bitmap> getCachedBitmap(int i);

        void onIntermediateResult(int i, Bitmap bitmap);
    }

    private enum FrameNeededResult {
        REQUIRED,
        NOT_REQUIRED,
        SKIP,
        ABORT
    }

    public AnimatedImageCompositor(AnimatedDrawableBackend animatedDrawableBackend, Callback callback) {
        this.mAnimatedDrawableBackend = animatedDrawableBackend;
        this.mCallback = callback;
        this.mTransparentFillPaint.setColor(0);
        this.mTransparentFillPaint.setStyle(Style.FILL);
        this.mTransparentFillPaint.setXfermode(new PorterDuffXfermode(Mode.SRC));
    }

    public void renderFrame(int frameNumber, Bitmap bitmap) {
        int nextIndex;
        AnimatedDrawableFrameInfo frameInfo;
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(0, Mode.SRC);
        if (isKeyFrame(frameNumber)) {
            nextIndex = frameNumber;
        } else {
            nextIndex = prepareCanvasWithClosestCachedFrame(frameNumber - 1, canvas);
        }
        for (int index = nextIndex; index < frameNumber; index++) {
            frameInfo = this.mAnimatedDrawableBackend.getFrameInfo(index);
            DisposalMethod disposalMethod = frameInfo.disposalMethod;
            if (disposalMethod != DisposalMethod.DISPOSE_TO_PREVIOUS) {
                if (frameInfo.blendOperation == BlendOperation.NO_BLEND) {
                    disposeToBackground(canvas, frameInfo);
                }
                this.mAnimatedDrawableBackend.renderFrame(index, canvas);
                this.mCallback.onIntermediateResult(index, bitmap);
                if (disposalMethod == DisposalMethod.DISPOSE_TO_BACKGROUND) {
                    disposeToBackground(canvas, frameInfo);
                }
            }
        }
        frameInfo = this.mAnimatedDrawableBackend.getFrameInfo(frameNumber);
        if (frameInfo.blendOperation == BlendOperation.NO_BLEND) {
            disposeToBackground(canvas, frameInfo);
        }
        this.mAnimatedDrawableBackend.renderFrame(frameNumber, canvas);
    }

    private int prepareCanvasWithClosestCachedFrame(int previousFrameNumber, Canvas canvas) {
        for (int index = previousFrameNumber; index >= 0; index--) {
            switch (isFrameNeededForRendering(index)) {
                case REQUIRED:
                    AnimatedDrawableFrameInfo frameInfo = this.mAnimatedDrawableBackend.getFrameInfo(index);
                    CloseableReference<Bitmap> startBitmap = this.mCallback.getCachedBitmap(index);
                    if (startBitmap == null) {
                        if (!isKeyFrame(index)) {
                            break;
                        }
                        return index;
                    }
                    try {
                        canvas.drawBitmap((Bitmap) startBitmap.get(), 0.0f, 0.0f, null);
                        if (frameInfo.disposalMethod == DisposalMethod.DISPOSE_TO_BACKGROUND) {
                            disposeToBackground(canvas, frameInfo);
                        }
                        index++;
                        startBitmap.close();
                        return index;
                    } catch (Throwable th) {
                        startBitmap.close();
                    }
                case NOT_REQUIRED:
                    return index + 1;
                case ABORT:
                    return index;
                default:
                    break;
            }
        }
        return 0;
    }

    private void disposeToBackground(Canvas canvas, AnimatedDrawableFrameInfo frameInfo) {
        canvas.drawRect((float) frameInfo.xOffset, (float) frameInfo.yOffset, (float) (frameInfo.xOffset + frameInfo.width), (float) (frameInfo.yOffset + frameInfo.height), this.mTransparentFillPaint);
    }

    private FrameNeededResult isFrameNeededForRendering(int index) {
        AnimatedDrawableFrameInfo frameInfo = this.mAnimatedDrawableBackend.getFrameInfo(index);
        DisposalMethod disposalMethod = frameInfo.disposalMethod;
        if (disposalMethod == DisposalMethod.DISPOSE_DO_NOT) {
            return FrameNeededResult.REQUIRED;
        }
        if (disposalMethod == DisposalMethod.DISPOSE_TO_BACKGROUND) {
            if (isFullFrame(frameInfo)) {
                return FrameNeededResult.NOT_REQUIRED;
            }
            return FrameNeededResult.REQUIRED;
        } else if (disposalMethod == DisposalMethod.DISPOSE_TO_PREVIOUS) {
            return FrameNeededResult.SKIP;
        } else {
            return FrameNeededResult.ABORT;
        }
    }

    private boolean isKeyFrame(int index) {
        if (index == 0) {
            return true;
        }
        AnimatedDrawableFrameInfo currFrameInfo = this.mAnimatedDrawableBackend.getFrameInfo(index);
        AnimatedDrawableFrameInfo prevFrameInfo = this.mAnimatedDrawableBackend.getFrameInfo(index - 1);
        if (currFrameInfo.blendOperation == BlendOperation.NO_BLEND && isFullFrame(currFrameInfo)) {
            return true;
        }
        if (prevFrameInfo.disposalMethod == DisposalMethod.DISPOSE_TO_BACKGROUND && isFullFrame(prevFrameInfo)) {
            return true;
        }
        return false;
    }

    private boolean isFullFrame(AnimatedDrawableFrameInfo frameInfo) {
        return frameInfo.xOffset == 0 && frameInfo.yOffset == 0 && frameInfo.width == this.mAnimatedDrawableBackend.getRenderedWidth() && frameInfo.height == this.mAnimatedDrawableBackend.getRenderedHeight();
    }
}
