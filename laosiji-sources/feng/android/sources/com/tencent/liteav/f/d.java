package com.tencent.liteav.f;

import com.tencent.ugc.TXRecordCommon;

/* compiled from: OutputConfig */
public class d {
    private static d a;
    private String b;
    private int c = 5000;
    private int d = 20;
    private int e = 3;
    private int f = 98304;
    private int g = 1;
    private int h = TXRecordCommon.AUDIO_SAMPLERATE_48000;
    private int i;

    public static d a() {
        if (a == null) {
            a = new d();
        }
        return a;
    }

    private d() {
    }

    public String b() {
        return this.b;
    }

    public void a(String str) {
        this.b = str;
    }

    public void a(int i) {
        this.i = i;
    }
}
