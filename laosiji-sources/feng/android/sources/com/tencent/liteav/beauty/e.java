package com.tencent.liteav.beauty;

/* compiled from: TXIVideoPreprocessorListener */
public interface e {
    void didProcessFrame(int i, int i2, int i3, long j);

    void didProcessFrame(byte[] bArr, int i, int i2, int i3, long j);

    int willAddWatermark(int i, int i2, int i3);
}
