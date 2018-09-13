package com.tencent.liteav.f;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaFormat;
import android.os.Build.VERSION;
import android.view.Surface;
import com.tencent.ijk.media.player.misc.IMediaFormat;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.c.e;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;

@TargetApi(19)
/* compiled from: TXHWAudioDecoder */
public class h implements b {
    private static final String a = h.class.getSimpleName();
    private MediaCodec b;
    private ByteBuffer[] c;
    private ByteBuffer[] d;
    private AtomicBoolean e = new AtomicBoolean(false);
    private long f = 1000;

    public void a(MediaFormat mediaFormat) {
        if (mediaFormat == null) {
            TXCLog.e(a, "create AudioDecoder error format:" + mediaFormat);
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
            TXCLog.e(a, "configure AudioDecoder error");
        } else {
            this.b.configure(mediaFormat, null, null, 0);
        }
    }

    public void a() {
        if (this.b == null) {
            TXCLog.e(a, "start AudioDecoder error");
            return;
        }
        this.b.start();
        this.d = this.b.getInputBuffers();
        this.c = this.b.getOutputBuffers();
        this.e.getAndSet(true);
    }

    public void b() {
        if (this.b == null) {
            TXCLog.e(a, "stop AudioDecoder error");
            return;
        }
        try {
            this.b.stop();
            this.b.release();
        } catch (IllegalStateException e) {
            TXCLog.e(a, "audio decoder stop exception: " + e);
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
            TXCLog.e(a, "audio dequeueInputBuffer exception: " + e);
        }
        if (i < 0) {
            return null;
        }
        ByteBuffer inputBuffer;
        if (VERSION.SDK_INT >= 21) {
            inputBuffer = this.b.getInputBuffer(i);
        } else {
            inputBuffer = this.d[i];
        }
        return new e(inputBuffer, 0, 0, i, 0, 0);
    }

    public void a(e eVar) {
        if (this.e.get()) {
            this.b.queueInputBuffer(eVar.d(), 0, eVar.g(), eVar.e(), eVar.f());
        }
    }

    public e d() {
        e eVar = null;
        if (this.e.get()) {
            BufferInfo bufferInfo = new BufferInfo();
            int dequeueOutputBuffer = this.b.dequeueOutputBuffer(bufferInfo, this.f);
            if (!(dequeueOutputBuffer == -1 || dequeueOutputBuffer == -3 || dequeueOutputBuffer == -2 || dequeueOutputBuffer < 0 || dequeueOutputBuffer < 0)) {
                eVar = new e();
                eVar.a(1);
                eVar.a(bufferInfo.presentationTimeUs);
                eVar.c(bufferInfo.flags);
                eVar.d(bufferInfo.size);
                ByteBuffer allocateDirect = ByteBuffer.allocateDirect(bufferInfo.size);
                if (VERSION.SDK_INT >= 21) {
                    allocateDirect.put(this.b.getOutputBuffer(dequeueOutputBuffer));
                } else {
                    ByteBuffer byteBuffer = this.b.getOutputBuffers()[dequeueOutputBuffer];
                    byteBuffer.rewind();
                    byteBuffer.limit(bufferInfo.size);
                    allocateDirect.put(byteBuffer);
                }
                allocateDirect.position(0);
                if (eVar.g() >= 0) {
                    allocateDirect.limit(eVar.g());
                }
                eVar.a(allocateDirect);
                this.b.releaseOutputBuffer(dequeueOutputBuffer, false);
            }
        }
        return eVar;
    }
}
