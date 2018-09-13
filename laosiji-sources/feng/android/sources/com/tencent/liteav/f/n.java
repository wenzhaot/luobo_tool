package com.tencent.liteav.f;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaFormat;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.Surface;
import com.tencent.ijk.media.player.IjkMediaMeta;
import com.tencent.ijk.media.player.misc.IMediaFormat;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.c.e;
import com.tencent.liteav.f.a.a;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.TreeSet;

@TargetApi(16)
/* compiled from: TXVideoEncoder */
public class n {
    private String a = n.class.getName();
    private int b = 960;
    private int c = 544;
    private int d = 20;
    private int e = 3;
    private int f = 1843200;
    private long g = 0;
    private TreeSet<Long> h = new TreeSet();
    private Object i = new Object();
    private String j;
    private a k;
    private MediaCodec l;
    private BufferInfo m = new BufferInfo();
    private Surface n;
    private int o = 0;
    private Object p = new Object();
    private boolean q;

    public synchronized void a(a aVar) {
        this.k = aVar;
    }

    public void a(int i, int i2) {
        TXCLog.d(this.a, "setOutputSize: " + i + "*" + i2);
        this.b = i;
        this.c = i2;
    }

    public void a(int i) {
        TXCLog.d(this.a, "setBitRate: " + i);
        this.f = i;
    }

    public void b(int i) {
        TXCLog.d(this.a, "setFrameRate: " + i);
        this.d = i;
    }

    public void c(int i) {
        TXCLog.d(this.a, "setIFrameInterval: " + i);
        this.e = i;
    }

    private MediaFormat d() {
        MediaFormat createVideoFormat = MediaFormat.createVideoFormat("video/avc", this.b, this.c);
        createVideoFormat.setInteger("color-format", 2130708361);
        createVideoFormat.setInteger(IjkMediaMeta.IJKM_KEY_BITRATE, this.f);
        createVideoFormat.setInteger("frame-rate", this.d);
        createVideoFormat.setInteger("bitrate-mode", 0);
        createVideoFormat.setInteger("i-frame-interval", this.e);
        return createVideoFormat;
    }

    public int a() {
        TXCLog.d(this.a, "start");
        MediaFormat d = d();
        try {
            this.j = d.getString(IMediaFormat.KEY_MIME);
            this.l = MediaCodec.createEncoderByType(this.j);
            try {
                this.l.configure(d, null, null, 1);
                if (VERSION.SDK_INT >= 18) {
                    this.n = this.l.createInputSurface();
                    synchronized (this.p) {
                        this.p.notifyAll();
                    }
                }
                this.l.start();
                this.o = 0;
                synchronized (this.i) {
                    this.h.clear();
                }
                this.g = 0;
                return 0;
            } catch (Exception e) {
                TXCLog.w(this.a, "mMediaCodec configure error ");
                return -1;
            }
        } catch (IOException e2) {
            e2.printStackTrace();
            TXCLog.e(this.a, "can not create by Code Name \"" + this.j + "\"");
            return -1;
        }
    }

    public synchronized Surface b() {
        if (this.n == null) {
            synchronized (this.p) {
                try {
                    this.p.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return this.n;
    }

    public void a(e eVar) {
        synchronized (this.i) {
            this.h.add(Long.valueOf(eVar.e()));
        }
        if (eVar == null || eVar.f() == 4 || eVar.g() <= 0) {
            a(true, false);
        } else {
            a(false, false);
        }
    }

    private synchronized void a(boolean z, boolean z2) {
        if (z2) {
            e();
        } else if (this.l == null) {
            TXCLog.w(this.a, "mMediaCodec == null.mime:" + this.j);
        } else {
            if (z) {
                if (VERSION.SDK_INT >= 18) {
                    TXCLog.d(this.a, "sending EOS to encoder.mime:" + this.j);
                    try {
                        this.l.signalEndOfInputStream();
                    } catch (IllegalStateException e) {
                        e();
                    }
                } else {
                    TXCLog.d(this.a, "end encoder.mime:" + this.j);
                    e();
                }
            }
            ByteBuffer[] outputBuffers = this.l.getOutputBuffers();
            while (this.h.size() != 0) {
                int dequeueOutputBuffer = this.l.dequeueOutputBuffer(this.m, 10000);
                if (dequeueOutputBuffer == -1) {
                    if (!z) {
                        break;
                    }
                    TXCLog.d(this.a, "no output available, spinning to await EOS.mime:" + this.j);
                } else if (dequeueOutputBuffer == -3) {
                    outputBuffers = this.l.getOutputBuffers();
                } else if (dequeueOutputBuffer == -2) {
                    MediaFormat outputFormat = this.l.getOutputFormat();
                    TXCLog.d(this.a, "encoder output format changed: " + outputFormat + " mime:" + this.j);
                    if (this.k != null) {
                        this.k.a(outputFormat);
                    }
                } else if (dequeueOutputBuffer < 0) {
                    TXCLog.w(this.a, "unexpected result from encoder.dequeueOutputBuffer: " + dequeueOutputBuffer + " mime:" + this.j);
                } else {
                    ByteBuffer outputBuffer;
                    if (this.q) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("request-sync", 0);
                        if (VERSION.SDK_INT >= 19) {
                            this.l.setParameters(bundle);
                        }
                    }
                    if (VERSION.SDK_INT >= 21) {
                        outputBuffer = this.l.getOutputBuffer(dequeueOutputBuffer);
                    } else {
                        outputBuffer = outputBuffers[dequeueOutputBuffer];
                    }
                    if (outputBuffer == null) {
                        throw new RuntimeException("encoderOutputBuffer " + dequeueOutputBuffer + " was null.mime:" + this.j);
                    }
                    byte[] bArr = new byte[this.m.size];
                    outputBuffer.position(this.m.offset);
                    outputBuffer.limit(this.m.offset + this.m.size);
                    outputBuffer.get(bArr, 0, this.m.size);
                    if ((this.m.flags & 2) == 2) {
                        this.m.size = 0;
                    }
                    if (!(this.k == null || this.m.size == 0)) {
                        this.m.presentationTimeUs = c();
                        BufferInfo bufferInfo = new BufferInfo();
                        ByteBuffer wrap = ByteBuffer.wrap(bArr);
                        bufferInfo.set(this.m.offset, bArr.length, this.m.presentationTimeUs, this.m.flags);
                        this.k.a(this.j, wrap, this.m);
                        this.o++;
                    }
                    this.l.releaseOutputBuffer(dequeueOutputBuffer, false);
                    if ((this.m.flags & 4) != 0) {
                        if (z) {
                            TXCLog.d(this.a, "end of stream reached.mime:" + this.j);
                        } else {
                            TXCLog.w(this.a, "reached end of stream unexpectedly.mime:" + this.j);
                        }
                    }
                }
            }
            if (z) {
                TXCLog.d(this.a, "mFrameCount:" + this.o + ", mime:" + this.j);
                e();
            }
        }
    }

    public void a(boolean z) {
        TXCLog.d(this.a, "stop");
        synchronized (this.i) {
            if (!this.h.isEmpty()) {
                TXCLog.d(this.a, "video unused pts size. from " + this.h.first() + " to last " + this.h.last());
            }
        }
        a(true, z);
        this.n = null;
    }

    private void e() {
        if (this.k != null) {
            this.k.a(this.j);
        }
        if (this.l != null) {
            this.l.stop();
            this.l.release();
            this.l = null;
        }
    }

    protected long c() {
        synchronized (this.i) {
            if (this.h.isEmpty()) {
                this.g += 1000000 / ((long) this.d);
                TXCLog.w(this.a, "no input video pts found. create pts manually. pts = " + this.g);
                return this.g;
            }
            this.g = ((Long) this.h.pollFirst()).longValue();
            long j = this.g;
            return j;
        }
    }
}
