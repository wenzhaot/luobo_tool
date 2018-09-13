package com.tencent.liteav.a;

import android.annotation.TargetApi;
import android.media.MediaFormat;
import android.util.Log;
import com.tencent.liteav.c.e;
import com.tencent.liteav.videoediter.audio.TXSkpResample;
import com.tencent.liteav.videoediter.audio.f;

@TargetApi(16)
/* compiled from: TXCombineAudioMixer */
public class b {
    private volatile float a = 1.0f;
    private volatile float b = 1.0f;
    private int c;
    private MediaFormat d;
    private MediaFormat e;
    private f f;
    private TXSkpResample g;
    private f h;
    private TXSkpResample i;
    private short[] j = null;

    public void a(int i) {
        this.c = i;
    }

    public void a(MediaFormat mediaFormat) {
        this.d = mediaFormat;
    }

    public void b(MediaFormat mediaFormat) {
        this.e = mediaFormat;
    }

    public void a() {
        if (this.d == null || this.e == null) {
            Object obj;
            String str = "TXCombineAudioMixer";
            StringBuilder append = new StringBuilder().append("AudioFormat1 :");
            if (this.d == null) {
                obj = "not set!!!";
            } else {
                obj = this.d;
            }
            append = append.append(obj).append(",AudioFormat2:");
            if (this.e == null) {
                obj = "not set!!!";
            } else {
                obj = this.e;
            }
            Log.e(str, append.append(obj).toString());
        } else if (this.c == 0) {
            Log.e("TXCombineAudioMixer", "Target Audio SampleRate is not set!!!");
        } else {
            int integer;
            if (this.d.containsKey("channel-count")) {
                integer = this.d.getInteger("channel-count");
                if (integer != 1) {
                    this.f = new f();
                    this.f.a(integer, 1);
                }
            }
            if (this.d.containsKey("sample-rate")) {
                integer = this.d.getInteger("sample-rate");
                if (integer != this.c) {
                    this.g = new TXSkpResample();
                    this.g.init(integer, this.c);
                }
            }
            if (this.e.containsKey("channel-count")) {
                integer = this.e.getInteger("channel-count");
                if (integer != 1) {
                    this.h = new f();
                    this.h.a(integer, 1);
                }
            }
            if (this.e.containsKey("sample-rate")) {
                integer = this.e.getInteger("sample-rate");
                if (integer != this.c) {
                    this.i = new TXSkpResample();
                    this.i.init(integer, this.c);
                }
            }
        }
    }

    public void b() {
        this.j = null;
    }

    public e a(e eVar, e eVar2) {
        short[] a = com.tencent.liteav.videoediter.audio.b.a(eVar.b(), eVar.g());
        if (this.f != null) {
            a = this.f.a(a);
        }
        if (this.g != null) {
            a = this.g.doResample(a);
        }
        short[] a2 = com.tencent.liteav.videoediter.audio.b.a(eVar2.b(), eVar2.g());
        if (this.h != null) {
            a2 = this.h.a(a2);
        }
        if (this.i != null) {
            a2 = this.i.doResample(a2);
        }
        short[] b;
        if (a.length == a2.length) {
            a = b(a, a2);
            eVar.a(com.tencent.liteav.videoediter.audio.b.a(eVar.b(), a));
            eVar.d(a.length * 2);
            return eVar;
        } else if (a.length > a2.length) {
            a = a(this.j, a);
            b = b(a, a2);
            this.j = a(a, a2.length, a.length - a2.length);
            eVar2.a(com.tencent.liteav.videoediter.audio.b.a(eVar2.b(), b));
            eVar2.d(b.length * 2);
            return eVar2;
        } else {
            a2 = a(this.j, a2);
            b = b(a2, a);
            this.j = a(a2, a.length, a2.length - a.length);
            eVar.a(com.tencent.liteav.videoediter.audio.b.a(eVar.b(), b));
            eVar.d(b.length * 2);
            return eVar;
        }
    }

    private short[] a(short[] sArr, short[] sArr2) {
        if (sArr == null || sArr.length == 0) {
            return sArr2;
        }
        Object obj = new short[(sArr.length + sArr2.length)];
        System.arraycopy(sArr, 0, obj, 0, sArr.length);
        System.arraycopy(sArr2, 0, obj, sArr.length, sArr2.length);
        return obj;
    }

    private short[] a(short[] sArr, int i, int i2) {
        Object obj = new short[i2];
        System.arraycopy(sArr, i, obj, 0, i2);
        return obj;
    }

    private short[] b(short[] sArr, short[] sArr2) {
        int length;
        short[] sArr3;
        if (sArr.length > sArr2.length) {
            length = sArr2.length;
            sArr3 = sArr2;
        } else {
            length = sArr.length;
            sArr3 = sArr;
        }
        for (int i = 0; i < length; i++) {
            short s;
            int i2 = (int) ((((float) sArr[i]) * this.b) + (((float) sArr2[i]) * this.a));
            if (i2 > 32767) {
                s = Short.MAX_VALUE;
            } else if (i2 < -32768) {
                s = Short.MIN_VALUE;
            } else {
                s = (short) i2;
            }
            sArr3[i] = s;
        }
        return sArr3;
    }
}
