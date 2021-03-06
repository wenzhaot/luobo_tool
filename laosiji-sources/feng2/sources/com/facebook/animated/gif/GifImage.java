package com.facebook.animated.gif;

import com.facebook.common.internal.DoNotStrip;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.soloader.SoLoaderShim;
import com.facebook.imagepipeline.animated.base.AnimatedDrawableFrameInfo;
import com.facebook.imagepipeline.animated.base.AnimatedDrawableFrameInfo.BlendOperation;
import com.facebook.imagepipeline.animated.base.AnimatedDrawableFrameInfo.DisposalMethod;
import com.facebook.imagepipeline.animated.base.AnimatedImage;
import com.facebook.imagepipeline.animated.factory.AnimatedImageDecoder;
import java.nio.ByteBuffer;
import javax.annotation.concurrent.ThreadSafe;

@DoNotStrip
@ThreadSafe
public class GifImage implements AnimatedImage, AnimatedImageDecoder {
    private static volatile boolean sInitialized;
    @DoNotStrip
    private long mNativeContext;

    private static native GifImage nativeCreateFromDirectByteBuffer(ByteBuffer byteBuffer);

    private static native GifImage nativeCreateFromNativeMemory(long j, int i);

    private native void nativeDispose();

    private native void nativeFinalize();

    private native int nativeGetDuration();

    private native GifFrame nativeGetFrame(int i);

    private native int nativeGetFrameCount();

    private native int[] nativeGetFrameDurations();

    private native int nativeGetHeight();

    private native int nativeGetLoopCount();

    private native int nativeGetSizeInBytes();

    private native int nativeGetWidth();

    private static synchronized void ensure() {
        synchronized (GifImage.class) {
            if (!sInitialized) {
                sInitialized = true;
                SoLoaderShim.loadLibrary("gifimage");
            }
        }
    }

    public static GifImage create(byte[] source) {
        ensure();
        Preconditions.checkNotNull(source);
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(source.length);
        byteBuffer.put(source);
        byteBuffer.rewind();
        return nativeCreateFromDirectByteBuffer(byteBuffer);
    }

    public static GifImage create(long nativePtr, int sizeInBytes) {
        ensure();
        Preconditions.checkArgument(nativePtr != 0);
        return nativeCreateFromNativeMemory(nativePtr, sizeInBytes);
    }

    public AnimatedImage decode(long nativePtr, int sizeInBytes) {
        return create(nativePtr, sizeInBytes);
    }

    @DoNotStrip
    GifImage(long nativeContext) {
        this.mNativeContext = nativeContext;
    }

    protected void finalize() {
        nativeFinalize();
    }

    public void dispose() {
        nativeDispose();
    }

    public int getWidth() {
        return nativeGetWidth();
    }

    public int getHeight() {
        return nativeGetHeight();
    }

    public int getFrameCount() {
        return nativeGetFrameCount();
    }

    public int getDuration() {
        return nativeGetDuration();
    }

    public int[] getFrameDurations() {
        return nativeGetFrameDurations();
    }

    public int getLoopCount() {
        return nativeGetLoopCount();
    }

    public GifFrame getFrame(int frameNumber) {
        return nativeGetFrame(frameNumber);
    }

    public boolean doesRenderSupportScaling() {
        return false;
    }

    public int getSizeInBytes() {
        return nativeGetSizeInBytes();
    }

    public AnimatedDrawableFrameInfo getFrameInfo(int frameNumber) {
        GifFrame frame = getFrame(frameNumber);
        try {
            AnimatedDrawableFrameInfo animatedDrawableFrameInfo = new AnimatedDrawableFrameInfo(frameNumber, frame.getXOffset(), frame.getYOffset(), frame.getWidth(), frame.getHeight(), BlendOperation.BLEND_WITH_PREVIOUS, fromGifDisposalMethod(frame.getDisposalMode()));
            return animatedDrawableFrameInfo;
        } finally {
            frame.dispose();
        }
    }

    private static DisposalMethod fromGifDisposalMethod(int disposalMode) {
        if (disposalMode == 0) {
            return DisposalMethod.DISPOSE_DO_NOT;
        }
        if (disposalMode == 1) {
            return DisposalMethod.DISPOSE_DO_NOT;
        }
        if (disposalMode == 2) {
            return DisposalMethod.DISPOSE_TO_BACKGROUND;
        }
        if (disposalMode == 3) {
            return DisposalMethod.DISPOSE_TO_PREVIOUS;
        }
        return DisposalMethod.DISPOSE_DO_NOT;
    }
}
