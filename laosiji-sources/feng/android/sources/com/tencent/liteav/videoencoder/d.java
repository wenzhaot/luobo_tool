package com.tencent.liteav.videoencoder;

import android.media.MediaFormat;
import com.tencent.liteav.basic.e.b;

/* compiled from: TXIVideoEncoderListener */
public interface d {
    void onEncodeFormat(MediaFormat mediaFormat);

    void onEncodeNAL(b bVar, int i);
}
