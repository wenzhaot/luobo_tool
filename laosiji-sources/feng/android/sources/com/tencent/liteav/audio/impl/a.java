package com.tencent.liteav.audio.impl;

import com.tencent.ugc.TXRecordCommon;

/* compiled from: TXCAudioUtil */
public class a {
    private static int[] a = new int[]{96000, 88200, 64000, TXRecordCommon.AUDIO_SAMPLERATE_48000, TXRecordCommon.AUDIO_SAMPLERATE_44100, TXRecordCommon.AUDIO_SAMPLERATE_32000, 24000, 22050, TXRecordCommon.AUDIO_SAMPLERATE_16000, 12000, 11025, TXRecordCommon.AUDIO_SAMPLERATE_8000, 7350};

    public static String a(byte[] bArr) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (bArr == null || bArr.length <= 0) {
            return null;
        }
        for (byte b : bArr) {
            String toHexString = Integer.toHexString(b & 255);
            if (toHexString.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append("0x");
            stringBuilder.append(toHexString);
        }
        return stringBuilder.toString();
    }

    public static int a(int i) {
        if (i >= a.length || i < 0) {
            return 0;
        }
        return a[i];
    }
}
