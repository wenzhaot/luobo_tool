package com.tencent.liteav.audio;

import com.tencent.liteav.basic.e.a;

/* compiled from: TXIAudioPlayListener */
public interface d {
    void onPlayAudioInfoChanged(a aVar, a aVar2);

    void onPlayError(int i, String str);

    void onPlayJitterStateNotify(int i);

    void onPlayPcmData(byte[] bArr, long j);
}
