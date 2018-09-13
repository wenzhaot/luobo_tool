package com.tencent.liteav.muxer;

import android.media.MediaCodec.BufferInfo;
import android.media.MediaFormat;
import java.nio.ByteBuffer;

/* compiled from: ITXCMP4Muxer */
public interface a {
    int a();

    void a(int i);

    void a(MediaFormat mediaFormat);

    void a(String str);

    void a(ByteBuffer byteBuffer, BufferInfo bufferInfo);

    void a(byte[] bArr, int i, int i2, long j, int i3);

    int b();

    void b(MediaFormat mediaFormat);

    void b(ByteBuffer byteBuffer, BufferInfo bufferInfo);

    void b(byte[] bArr, int i, int i2, long j, int i3);

    boolean c();

    boolean d();
}
