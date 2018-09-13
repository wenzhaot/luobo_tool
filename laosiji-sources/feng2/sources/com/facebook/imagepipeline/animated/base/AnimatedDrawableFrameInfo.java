package com.facebook.imagepipeline.animated.base;

public class AnimatedDrawableFrameInfo {
    public final BlendOperation blendOperation;
    public final DisposalMethod disposalMethod;
    public final int frameNumber;
    public final int height;
    public final int width;
    public final int xOffset;
    public final int yOffset;

    public enum BlendOperation {
        BLEND_WITH_PREVIOUS,
        NO_BLEND
    }

    public enum DisposalMethod {
        DISPOSE_DO_NOT,
        DISPOSE_TO_BACKGROUND,
        DISPOSE_TO_PREVIOUS
    }

    public AnimatedDrawableFrameInfo(int frameNumber, int xOffset, int yOffset, int width, int height, BlendOperation blendOperation, DisposalMethod disposalMethod) {
        this.frameNumber = frameNumber;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.width = width;
        this.height = height;
        this.blendOperation = blendOperation;
        this.disposalMethod = disposalMethod;
    }
}
