package com.tencent.liteav.d;

import android.media.MediaCodec.BufferInfo;
import android.media.MediaFormat;
import java.nio.ByteBuffer;

/* compiled from: TXIAudioEncoderListener */
public interface o {
    void a();

    void a(MediaFormat mediaFormat);

    void a(ByteBuffer byteBuffer, BufferInfo bufferInfo);
}
