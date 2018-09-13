package com.tencent.liteav.videoediter.ffmpeg.jni;

import com.tencent.liteav.basic.log.TXCLog;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;

public class TXFFAudioDecoderJNI {
    private static final String TAG = "TXFFAudioDecoderJNI";
    private long handle = init();
    private AtomicBoolean isInitSuccess = new AtomicBoolean(false);

    private native int configureInput(long j, int i, byte[] bArr, int i2, int i3);

    private native void configureOutput(long j, int i, int i2);

    private native FFDecodedFrame decode(long j, byte[] bArr, long j2, int i);

    private native long init();

    private native void release(long j);

    public synchronized int configureInput(int i, ByteBuffer byteBuffer, int i2, int i3, int i4) {
        int configureInput;
        configureInput = configureInput(this.handle, i, getBuffer(byteBuffer, i2), i3, i4);
        if (configureInput == 0) {
            TXCLog.i(TAG, "init native decoder success!");
            this.isInitSuccess.set(true);
        } else {
            TXCLog.e(TAG, "init native decoder error!");
            this.isInitSuccess.set(true);
        }
        return configureInput;
    }

    public synchronized void configureOutput(int i, int i2) {
        configureOutput(this.handle, i, i2);
    }

    public synchronized FFDecodedFrame decode(byte[] bArr, long j, int i) {
        FFDecodedFrame decode;
        if (this.isInitSuccess.get()) {
            decode = decode(this.handle, bArr, j, i);
        } else {
            TXCLog.e(TAG, "decoder not init yet!");
            decode = null;
        }
        return decode;
    }

    public synchronized void release() {
        if (this.isInitSuccess.get()) {
            TXCLog.i(TAG, "release decoder!");
            release(this.handle);
            this.isInitSuccess.set(false);
        } else {
            TXCLog.w(TAG, "decoder not init yet!");
        }
    }

    private byte[] getBuffer(ByteBuffer byteBuffer, int i) {
        if (byteBuffer == null) {
            return null;
        }
        byte[] bArr = new byte[i];
        byteBuffer.position(0);
        byteBuffer.get(bArr);
        return bArr;
    }
}
