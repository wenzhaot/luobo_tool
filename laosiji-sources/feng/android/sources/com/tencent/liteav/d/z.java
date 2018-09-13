package com.tencent.liteav.d;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaFormat;
import android.os.Build.VERSION;
import android.view.Surface;
import com.tencent.ijk.media.player.misc.IMediaFormat;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.c.e;
import com.tencent.liteav.f.b;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;

@TargetApi(16)
/* compiled from: VideoMediaCodecDecoder */
public class z implements b {
    private static final String a = z.class.getSimpleName();
    private MediaCodec b;
    private ByteBuffer[] c;
    private ByteBuffer[] d;
    private AtomicBoolean e = new AtomicBoolean(false);
    private long f = 1000;

    public void a(MediaFormat mediaFormat) {
        if (mediaFormat == null) {
            TXCLog.e(a, "create VideoDecoder error format:" + mediaFormat);
            return;
        }
        try {
            this.b = MediaCodec.createDecoderByType(mediaFormat.getString(IMediaFormat.KEY_MIME));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void a(MediaFormat mediaFormat, Surface surface) {
        if (mediaFormat == null) {
            TXCLog.e(a, "configure VideoDecoder error");
            return;
        }
        TXCLog.d(a, "format: " + mediaFormat + ", surface: " + surface + ", mMediaCodec: " + this.b);
        mediaFormat.setInteger("rotation-degrees", 0);
        try {
            this.b.configure(mediaFormat, surface, null, 0);
        } catch (Exception e) {
            TXCLog.w(a, "mMediaCodec configure error ");
        }
    }

    public void a() {
        TXCLog.d(a, "start");
        if (this.b == null) {
            TXCLog.e(a, "start VideoDecoder error");
            return;
        }
        this.b.start();
        this.c = this.b.getInputBuffers();
        this.d = this.b.getOutputBuffers();
        this.e.getAndSet(true);
    }

    public void b() {
        TXCLog.d(a, "stop");
        if (this.b == null) {
            TXCLog.e(a, "stop VideoDecoder error");
            return;
        }
        try {
            this.b.stop();
            this.b.release();
        } catch (IllegalStateException e) {
            TXCLog.e(a, "video decoder stop exception: " + e);
        } finally {
            this.e.getAndSet(false);
        }
    }

    public e c() {
        if (!this.e.get()) {
            return null;
        }
        int i = -1;
        try {
            i = this.b.dequeueInputBuffer(this.f);
        } catch (Exception e) {
            TXCLog.e(a, "video dequeueInputBuffer exception: " + e);
        }
        if (i < 0) {
            return null;
        }
        ByteBuffer inputBuffer;
        if (VERSION.SDK_INT >= 21) {
            inputBuffer = this.b.getInputBuffer(i);
        } else {
            inputBuffer = this.c[i];
        }
        return new e(inputBuffer, 0, 0, i, 0, 0);
    }

    public void a(e eVar) {
        if (this.e.get()) {
            this.b.queueInputBuffer(eVar.d(), 0, eVar.g(), eVar.e(), eVar.f());
        }
    }

    public e d() {
        if (!this.e.get()) {
            return null;
        }
        BufferInfo bufferInfo = new BufferInfo();
        int dequeueOutputBuffer = this.b.dequeueOutputBuffer(bufferInfo, this.f);
        if (dequeueOutputBuffer == -1 || dequeueOutputBuffer == -3 || dequeueOutputBuffer == -2 || dequeueOutputBuffer < 0 || dequeueOutputBuffer < 0) {
            return null;
        }
        e eVar = new e(null, bufferInfo.size, bufferInfo.presentationTimeUs, dequeueOutputBuffer, bufferInfo.flags, 0);
        this.b.releaseOutputBuffer(dequeueOutputBuffer, true);
        return eVar;
    }

    public e a(e eVar, e eVar2) {
        if (!this.e.get()) {
            return null;
        }
        eVar2.k(eVar.n());
        eVar2.j(eVar.m());
        eVar2.e(eVar.h());
        eVar2.f(eVar.i());
        eVar2.i(eVar.l());
        eVar2.h(eVar.k());
        eVar2.g(eVar.j());
        eVar2.b(eVar.e());
        return eVar2;
    }

    public e b(e eVar) {
        if (!this.e.get()) {
            return null;
        }
        eVar.d(0);
        eVar.a(0);
        eVar.c(4);
        TXCLog.d(a, "------appendEndFrame----------");
        return eVar;
    }
}
