package com.tencent.liteav.f;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaFormat;
import android.os.Build.VERSION;
import com.tencent.ijk.media.player.IjkMediaMeta;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.c.e;
import com.tencent.liteav.f.a.a;
import com.tencent.ugc.TXRecordCommon;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicBoolean;

@TargetApi(16)
/* compiled from: TXAudioEncoder */
public class f {
    private static final String b = f.class.getName();
    private volatile AtomicBoolean a = new AtomicBoolean(false);
    private int c = 1;
    private int d = 98304;
    private int e = TXRecordCommon.AUDIO_SAMPLERATE_44100;
    private int f = 1024;
    private int g = 10000;
    private String h = "audio/mp4a-latm";
    private boolean i = true;
    private long j = -1;
    private TreeSet<Long> k = new TreeSet();
    private final Object l = new Object();
    private BufferInfo m = new BufferInfo();
    private MediaCodec n;
    private a o;
    private int p = 0;

    public synchronized void a(a aVar) {
        this.o = aVar;
    }

    public void a(int i) {
        TXCLog.d(b, "setBitRate: " + i);
        if (i > 0) {
            this.d = i;
        }
    }

    public void b(int i) {
        TXCLog.d(b, "setSampleRate: " + i);
        if (i > 0) {
            this.e = i;
        }
    }

    public void c(int i) {
        TXCLog.d(b, "setChannels: " + i);
        if (i > 0) {
            this.c = i;
        }
    }

    public int a() {
        String str = null;
        MediaCodecInfo a = a("audio/mp4a-latm");
        MediaFormat c = c();
        if (a != null) {
            str = a.getName();
        }
        if (str == null) {
            return -1;
        }
        try {
            this.n = MediaCodec.createByCodecName(str);
            this.n.configure(c, null, null, 1);
            this.n.start();
            this.p = 0;
            synchronized (this.l) {
                this.k.clear();
            }
            this.j = -1;
            this.a.set(true);
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private static MediaCodecInfo a(String str) {
        int codecCount = MediaCodecList.getCodecCount();
        for (int i = 0; i < codecCount; i++) {
            MediaCodecInfo codecInfoAt = MediaCodecList.getCodecInfoAt(i);
            if (codecInfoAt.isEncoder()) {
                for (String equalsIgnoreCase : codecInfoAt.getSupportedTypes()) {
                    if (equalsIgnoreCase.equalsIgnoreCase(str)) {
                        return codecInfoAt;
                    }
                }
                continue;
            }
        }
        return null;
    }

    private MediaFormat c() {
        MediaFormat createAudioFormat = MediaFormat.createAudioFormat(this.h, this.e, this.c);
        createAudioFormat.setInteger(IjkMediaMeta.IJKM_KEY_BITRATE, this.d);
        createAudioFormat.setInteger("aac-profile", 2);
        createAudioFormat.setInteger("max-input-size", this.g);
        TXCLog.d(b, "audio encodeVideo format: " + createAudioFormat);
        return createAudioFormat;
    }

    public void a(e eVar) {
        if (!this.a.get() || this.n == null) {
            TXCLog.e(b, "MediaCodec == null or MediaCodec isn't start yet! ");
            return;
        }
        synchronized (this.l) {
            this.k.add(Long.valueOf(eVar.e()));
        }
        b(eVar);
        if (eVar == null || eVar.f() == 4 || eVar.g() <= 0) {
            b(true);
        } else {
            b(false);
        }
    }

    protected synchronized void b(e eVar) {
        if (!this.a.get() || this.n == null) {
            TXCLog.e(b, "MediaCodec == null or MediaCodec isn't start yet! ");
        } else if (eVar != null) {
            int dequeueInputBuffer = this.n.dequeueInputBuffer(1000);
            if (dequeueInputBuffer >= 0) {
                ByteBuffer inputBuffer;
                if (VERSION.SDK_INT >= 21) {
                    inputBuffer = this.n.getInputBuffer(dequeueInputBuffer);
                } else {
                    inputBuffer = this.n.getInputBuffers()[dequeueInputBuffer];
                }
                inputBuffer.clear();
                int g = eVar.g();
                if (g <= 0 || eVar.f() == 4) {
                    TXCLog.d(b, "queueInputBuffer set end of streammime:" + this.h);
                    this.n.queueInputBuffer(dequeueInputBuffer, 0, 0, eVar.e(), 4);
                } else {
                    ByteBuffer duplicate = eVar.b().duplicate();
                    duplicate.rewind();
                    duplicate.limit(g);
                    inputBuffer.rewind();
                    if (g < inputBuffer.remaining()) {
                        inputBuffer.put(duplicate);
                        this.n.queueInputBuffer(dequeueInputBuffer, 0, g, eVar.e(), 0);
                    } else {
                        String str = "input size is larger than buffer capacity. should increate buffer capacity by setting MediaFormat.KEY_MAX_INPUT_SIZE while configure. mime = " + this.h;
                        TXCLog.e(b, str);
                        throw new IllegalArgumentException(str);
                    }
                }
            }
            TXCLog.w(b, "no buffer available.mime:" + this.h);
        }
    }

    private synchronized void b(boolean z) {
        if (this.n == null || !this.a.get()) {
            TXCLog.e(b, "MediaCodec == null or MediaCodec isn't start yet! ");
        } else {
            if (z) {
                TXCLog.d(b, "end encoder.mime:" + this.h);
            }
            ByteBuffer[] outputBuffers = this.n.getOutputBuffers();
            while (this.k.size() != 0) {
                int dequeueOutputBuffer = this.n.dequeueOutputBuffer(this.m, 10000);
                if (dequeueOutputBuffer == -1) {
                    if (!z) {
                        break;
                    }
                    TXCLog.d(b, "no output available, spinning to await EOS.mime:" + this.h);
                } else if (dequeueOutputBuffer == -3) {
                    outputBuffers = this.n.getOutputBuffers();
                } else if (dequeueOutputBuffer == -2) {
                    MediaFormat outputFormat = this.n.getOutputFormat();
                    if (this.o != null) {
                        this.o.a(outputFormat);
                    }
                } else if (dequeueOutputBuffer < 0) {
                    continue;
                } else {
                    ByteBuffer outputBuffer;
                    if (VERSION.SDK_INT >= 21) {
                        outputBuffer = this.n.getOutputBuffer(dequeueOutputBuffer);
                    } else {
                        outputBuffer = outputBuffers[dequeueOutputBuffer];
                    }
                    if (outputBuffer == null) {
                        throw new RuntimeException("encoderOutputBuffer " + dequeueOutputBuffer + " was null.mime:" + this.h);
                    }
                    byte[] bArr = new byte[this.m.size];
                    outputBuffer.position(this.m.offset);
                    outputBuffer.limit(this.m.offset + this.m.size);
                    outputBuffer.get(bArr, 0, this.m.size);
                    if ((this.m.flags & 2) == 2) {
                        this.m.size = 0;
                    }
                    if (!(this.o == null || this.m.size == 0)) {
                        this.m.presentationTimeUs = b();
                        BufferInfo bufferInfo = new BufferInfo();
                        ByteBuffer wrap = ByteBuffer.wrap(bArr);
                        bufferInfo.set(this.m.offset, bArr.length, this.m.presentationTimeUs, this.m.flags);
                        this.o.a(this.h, wrap, this.m);
                        this.p++;
                    }
                    this.n.releaseOutputBuffer(dequeueOutputBuffer, false);
                    if ((this.m.flags & 4) != 0) {
                        if (z) {
                            TXCLog.d(b, "end of stream reached.mime:" + this.h);
                        } else {
                            TXCLog.w(b, "reached end of stream unexpectedly.mime:" + this.h);
                        }
                    }
                }
            }
            if (z) {
                TXCLog.d(b, "mFrameCount:" + this.p + ", mime:" + this.h);
                d();
            }
        }
    }

    protected long b() {
        if (this.j == -1) {
            this.j = 0;
        } else {
            this.j += (1000000 * ((long) this.f)) / ((long) this.e);
        }
        TXCLog.w(b, "calculateCurrentFramePTS manually. pts = " + this.j);
        return this.j;
    }

    public void a(boolean z) {
        if (this.n == null || !this.a.get()) {
            TXCLog.e(b, "MediaCodec == null or MediaCodec isn't start yet! ");
            return;
        }
        TXCLog.d(b, "stop");
        if (z) {
            d();
        }
    }

    private void d() {
        if (this.o != null) {
            this.o.a(this.h);
        }
        try {
            this.a.set(false);
            if (this.n != null) {
                this.n.stop();
                this.n.release();
                this.n = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
