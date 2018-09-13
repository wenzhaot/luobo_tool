package com.facebook.animated.webp;

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
public class WebPImage implements AnimatedImage, AnimatedImageDecoder {
    private static volatile boolean sInitialized;
    @DoNotStrip
    private long mNativeContext;

    private static native WebPImage nativeCreateFromDirectByteBuffer(ByteBuffer byteBuffer);

    private static native WebPImage nativeCreateFromNativeMemory(long j, int i);

    private native void nativeDispose();

    private native void nativeFinalize();

    private native int nativeGetDuration();

    private native WebPFrame nativeGetFrame(int i);

    private native int nativeGetFrameCount();

    private native int[] nativeGetFrameDurations();

    private native int nativeGetHeight();

    private native int nativeGetLoopCount();

    private native int nativeGetSizeInBytes();

    private native int nativeGetWidth();

    private static synchronized void ensure() {
        synchronized (WebPImage.class) {
            if (!sInitialized) {
                try {
                    SoLoaderShim.loadLibrary("webp");
                } catch (UnsatisfiedLinkError e) {
                }
                SoLoaderShim.loadLibrary("webpimage");
                sInitialized = true;
            }
        }
    }

    @DoNotStrip
    WebPImage(long nativeContext) {
        this.mNativeContext = nativeContext;
    }

    protected void finalize() {
        nativeFinalize();
    }

    public void dispose() {
        nativeDispose();
    }

    public static WebPImage create(byte[] source) {
        ensure();
        Preconditions.checkNotNull(source);
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(source.length);
        byteBuffer.put(source);
        byteBuffer.rewind();
        return nativeCreateFromDirectByteBuffer(byteBuffer);
    }

    public static WebPImage create(long nativePtr, int sizeInBytes) {
        ensure();
        Preconditions.checkArgument(nativePtr != 0);
        return nativeCreateFromNativeMemory(nativePtr, sizeInBytes);
    }

    public AnimatedImage decode(long nativePtr, int sizeInBytes) {
        return create(nativePtr, sizeInBytes);
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

    public WebPFrame getFrame(int frameNumber) {
        return nativeGetFrame(frameNumber);
    }

    public int getSizeInBytes() {
        return nativeGetSizeInBytes();
    }

    public boolean doesRenderSupportScaling() {
        return true;
    }

    public AnimatedDrawableFrameInfo getFrameInfo(int frameNumber) {
        WebPFrame frame = getFrame(frameNumber);
        try {
            AnimatedDrawableFrameInfo animatedDrawableFrameInfo = new AnimatedDrawableFrameInfo(frameNumber, frame.getXOffset(), frame.getYOffset(), frame.getWidth(), frame.getHeight(), frame.isBlendWithPreviousFrame() ? BlendOperation.BLEND_WITH_PREVIOUS : BlendOperation.NO_BLEND, frame.shouldDisposeToBackgroundColor() ? DisposalMethod.DISPOSE_TO_BACKGROUND : DisposalMethod.DISPOSE_DO_NOT);
            return animatedDrawableFrameInfo;
        } finally {
            frame.dispose();
        }
    }
}
