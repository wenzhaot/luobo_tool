package com.tencent.liteav.videoencoder;

import android.opengl.GLES20;
import com.tencent.liteav.basic.c.d;
import com.tencent.liteav.basic.c.d.a;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.beauty.b.v;
import java.lang.ref.WeakReference;

public class TXCSWVideoEncoder extends c {
    private static final boolean DEBUG = false;
    private static final String TAG = TXCSWVideoEncoder.class.getSimpleName();
    private int mBitrate = 0;
    private long mNativeX264Encoder = 0;
    private long mPTS = 0;
    private int mPopIdx = 0;
    private int mPushIdx = 0;
    private d mRawFrameFilter;
    private int mRendIdx = 0;
    private d mResizeFilter;

    private static native void nativeClassInit();

    private native int nativeEncode(long j, int i, int i2, int i3, long j2);

    private native int nativeEncodeSync(long j, int i, int i2, int i3, long j2);

    private native long nativeGetRealFPS(long j);

    private native long nativeInit(WeakReference<TXCSWVideoEncoder> weakReference);

    private native void nativeRelease(long j);

    private native void nativeSetBitrate(long j, int i);

    private native void nativeSignalEOSAndFlush(long j);

    private native int nativeStart(long j, TXSVideoEncoderParam tXSVideoEncoderParam);

    private native void nativeStop(long j);

    private native long nativegetRealBitrate(long j);

    public int start(TXSVideoEncoderParam tXSVideoEncoderParam) {
        super.start(tXSVideoEncoderParam);
        int i = ((tXSVideoEncoderParam.width + 7) / 8) * 8;
        int i2 = ((tXSVideoEncoderParam.height + 1) / 2) * 2;
        if (!(i == tXSVideoEncoderParam.width && i2 == tXSVideoEncoderParam.height)) {
            TXCLog.w(TAG, "Encode Resolution not supportted, transforming...");
            TXCLog.w(TAG, tXSVideoEncoderParam.width + "x" + tXSVideoEncoderParam.height + "-> " + i + "x" + i2);
        }
        tXSVideoEncoderParam.width = i;
        tXSVideoEncoderParam.height = i2;
        this.mOutputWidth = i;
        this.mOutputHeight = i2;
        this.mInputWidth = i;
        this.mInputHeight = i2;
        this.mNativeX264Encoder = nativeInit(new WeakReference(this));
        nativeSetBitrate(this.mNativeX264Encoder, this.mBitrate);
        nativeStart(this.mNativeX264Encoder, tXSVideoEncoderParam);
        return 0;
    }

    public void stop() {
        this.mGLContextExternal = null;
        nativeStop(this.mNativeX264Encoder);
        nativeRelease(this.mNativeX264Encoder);
        this.mNativeX264Encoder = 0;
        if (this.mRawFrameFilter != null) {
            this.mRawFrameFilter.e();
            this.mRawFrameFilter = null;
        }
        if (this.mResizeFilter != null) {
            this.mResizeFilter.e();
            this.mResizeFilter = null;
        }
        super.stop();
    }

    public void setFPS(int i) {
    }

    public void setBitrate(int i) {
        this.mBitrate = i;
        nativeSetBitrate(this.mNativeX264Encoder, i);
    }

    public long getRealFPS() {
        return nativeGetRealFPS(this.mNativeX264Encoder);
    }

    public long getRealBitrate() {
        return nativegetRealBitrate(this.mNativeX264Encoder);
    }

    public long pushVideoFrame(int i, int i2, int i3, long j) {
        return pushVideoFrameInternal(i, i2, i3, j, false);
    }

    public long pushVideoFrameSync(int i, int i2, int i3, long j) {
        return pushVideoFrameInternal(i, i2, i3, j, true);
    }

    public void signalEOSAndFlush() {
        nativeSignalEOSAndFlush(this.mNativeX264Encoder);
    }

    private static void postEventFromNative(WeakReference<TXCSWVideoEncoder> weakReference, byte[] bArr, int i, long j, long j2, long j3, long j4, long j5, long j6, int i2) {
        TXCSWVideoEncoder tXCSWVideoEncoder = (TXCSWVideoEncoder) weakReference.get();
        if (tXCSWVideoEncoder != null) {
            tXCSWVideoEncoder.callDelegate(bArr, i, j, j2, j3, j4, j5, j6, i2, null, null);
            if (bArr != null) {
            }
        }
    }

    private long pushVideoFrameInternal(int i, int i2, int i3, long j, final boolean z) {
        if (this.mGLContextExternal != null) {
            if (!(this.mInputWidth == i2 && this.mInputHeight == i3)) {
                this.mInputWidth = i2;
                this.mInputHeight = i3;
                if (this.mResizeFilter == null) {
                    this.mResizeFilter = new d();
                    this.mResizeFilter.c();
                    this.mResizeFilter.a(true);
                }
                this.mResizeFilter.a(this.mOutputWidth, this.mOutputHeight);
            }
            GLES20.glViewport(0, 0, this.mOutputWidth, this.mOutputHeight);
            if (this.mResizeFilter != null) {
                this.mResizeFilter.a(i);
            }
            if (this.mResizeFilter != null) {
                i = this.mResizeFilter.l();
            }
            int[] iArr = new int[1];
            this.mPTS = j;
            if (this.mRawFrameFilter == null) {
                this.mRawFrameFilter = new v(1);
                this.mRawFrameFilter.a(true);
                if (this.mRawFrameFilter.c()) {
                    this.mRawFrameFilter.a(this.mOutputWidth, this.mOutputHeight);
                    this.mRawFrameFilter.a(new a() {
                        public void a(int i) {
                            if (z) {
                                TXCSWVideoEncoder.this.nativeEncodeSync(TXCSWVideoEncoder.this.mNativeX264Encoder, i, TXCSWVideoEncoder.this.mOutputWidth, TXCSWVideoEncoder.this.mOutputHeight, TXCSWVideoEncoder.this.mPTS);
                            } else {
                                TXCSWVideoEncoder.this.nativeEncode(TXCSWVideoEncoder.this.mNativeX264Encoder, i, TXCSWVideoEncoder.this.mOutputWidth, TXCSWVideoEncoder.this.mOutputHeight, TXCSWVideoEncoder.this.mPTS);
                            }
                        }
                    });
                } else {
                    this.mRawFrameFilter = null;
                    return 10000004;
                }
            }
            if (this.mRawFrameFilter == null) {
                return 10000004;
            }
            GLES20.glViewport(0, 0, this.mOutputWidth, this.mOutputHeight);
            this.mRawFrameFilter.a(i);
            int i4 = iArr[0];
            if (i4 != 0) {
                callDelegate(i4);
            }
        }
        return 0;
    }

    static {
        com.tencent.liteav.basic.util.a.d();
        nativeClassInit();
    }
}
