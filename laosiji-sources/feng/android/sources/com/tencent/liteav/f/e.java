package com.tencent.liteav.f;

import android.annotation.TargetApi;
import android.media.MediaFormat;
import android.view.Surface;
import com.tencent.ijk.media.player.misc.IMediaFormat;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.videoediter.ffmpeg.c;
import java.util.concurrent.atomic.AtomicBoolean;

@TargetApi(16)
/* compiled from: TXAudioDecoderWrapper */
public class e implements b {
    private static final String[] c = new String[]{"Xiaomi - MI 3"};
    private AtomicBoolean a = new AtomicBoolean(false);
    private b b;
    private boolean d;

    private boolean b(MediaFormat mediaFormat) {
        String string = mediaFormat.getString(IMediaFormat.KEY_MIME);
        TXCLog.i("TXAudioDecoderWrapper", " mime type = " + string);
        if (string == null || !c.a(string)) {
            TXCLog.i("TXAudioDecoderWrapper", "isUseSw: use hw decoder!");
            return false;
        }
        TXCLog.i("TXAudioDecoderWrapper", "isUseSw: support mime type! use sw decoder!");
        return true;
    }

    public void a(MediaFormat mediaFormat) {
        if (mediaFormat == null) {
            this.a.set(false);
            return;
        }
        this.a.set(true);
        TXCLog.i("TXAudioDecoderWrapper", "createDecoderByFormat: " + mediaFormat.toString());
        this.d = b(mediaFormat);
        if (this.d) {
            this.b = new c();
        } else {
            this.b = new h();
        }
        this.b.a(mediaFormat);
    }

    public boolean e() {
        return this.d;
    }

    public void a(MediaFormat mediaFormat, Surface surface) {
        if (mediaFormat == null) {
            this.a.set(false);
            return;
        }
        this.a.set(true);
        this.b.a(mediaFormat, surface);
    }

    public void a() {
        if (this.a.get()) {
            this.b.a();
        }
    }

    public void b() {
        if (this.a.get()) {
            this.b.b();
        }
    }

    public com.tencent.liteav.c.e c() {
        if (this.a.get()) {
            return this.b.c();
        }
        return null;
    }

    public void a(com.tencent.liteav.c.e eVar) {
        if (this.a.get()) {
            this.b.a(eVar);
        }
    }

    public com.tencent.liteav.c.e d() {
        if (this.a.get()) {
            return this.b.d();
        }
        return null;
    }

    public com.tencent.liteav.c.e a(com.tencent.liteav.c.e eVar, com.tencent.liteav.c.e eVar2) {
        if (!this.a.get()) {
            return null;
        }
        eVar2.k(eVar.n());
        eVar2.j(eVar.m());
        eVar2.f(eVar.i());
        eVar2.e(eVar.h());
        eVar2.i(eVar.l());
        eVar2.h(eVar.k());
        eVar2.g(eVar.j());
        return eVar2;
    }

    public com.tencent.liteav.c.e b(com.tencent.liteav.c.e eVar) {
        if (!this.a.get()) {
            return null;
        }
        eVar.c(4);
        TXCLog.d("TXAudioDecoderWrapper", "------appendEndFrame----------");
        return eVar;
    }
}
