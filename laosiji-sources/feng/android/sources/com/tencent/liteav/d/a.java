package com.tencent.liteav.d;

import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaFormat;
import android.os.Build.VERSION;
import com.tencent.ijk.media.player.IjkMediaMeta;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.b;
import com.tencent.liteav.c.e;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;
import java.util.concurrent.LinkedBlockingDeque;

/* compiled from: AudioMediaCodecEncoder */
public class a {
    private b a = new b("HWAudioEncoder");
    private o b;
    private g c;
    private boolean d;
    private int e;
    private int f;
    private LinkedBlockingDeque<e> g = new LinkedBlockingDeque();
    private TreeSet<Long> h;
    private MediaCodec i;
    private Long j;
    private final Object k = new Object();
    private int l;
    private n m;
    private ArrayList<e> n;
    private long o = -1;
    private MediaFormat p;
    private Runnable q = new Runnable() {
        public void run() {
            if (a.this.d) {
                a.this.h();
            }
        }
    };

    public void a(o oVar) {
        this.b = oVar;
    }

    public void a(final n nVar) {
        this.o = -1;
        if (this.n == null) {
            this.n = new ArrayList();
        } else {
            this.n.clear();
        }
        this.p = c(nVar);
        synchronized (this) {
            this.a.a(new Runnable() {
                public void run() {
                    if (!a.this.d) {
                        a.this.b(nVar);
                    }
                }
            });
        }
    }

    public void a() {
        synchronized (this) {
            this.a.a(new Runnable() {
                public void run() {
                    if (a.this.d) {
                        a.this.e();
                        a.this.a.a().removeCallbacksAndMessages(null);
                    }
                }
            });
        }
    }

    private void b(n nVar) {
        TXCLog.i("AudioMediaCodecEncoder", "startAudioInner");
        if (VERSION.SDK_INT >= 16) {
            MediaCodecInfo a = a("audio/mp4a-latm");
            if (a != null && this.p != null) {
                try {
                    this.i = MediaCodec.createByCodecName(a.getName());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                this.i.configure(this.p, null, null, 1);
                this.i.start();
                this.g.clear();
                this.d = true;
                this.a.a(this.q, 1);
                this.h = new TreeSet();
                this.j = Long.valueOf(0);
            }
        }
    }

    private void e() {
        TXCLog.i("AudioMediaCodecEncoder", "stopInner");
        if (VERSION.SDK_INT >= 16) {
            if (this.i != null) {
                this.i.stop();
                this.i.release();
            }
            this.d = false;
        }
    }

    public void b() {
        this.a = null;
    }

    public void a(e eVar) {
        int i = this.m.channelCount * 2048;
        if (eVar.g() <= i) {
            b(eVar);
            return;
        }
        synchronized (this.n) {
            this.n.add(eVar);
        }
        while (true) {
            short[] a = a(i / 2);
            if (a != null) {
                e a2 = a(a);
                if (a2 != null) {
                    b(a2);
                }
            } else {
                return;
            }
        }
    }

    public e c() {
        e eVar = null;
        synchronized (this.n) {
            if (this.n.size() > 0) {
                eVar = (e) this.n.remove(0);
            }
        }
        return eVar;
    }

    private short[] a(int i) {
        e c = c();
        if (c == null) {
            return null;
        }
        short[] c2 = c(c);
        if (c2 == null) {
            return null;
        }
        short[] copyOf = Arrays.copyOf(c2, i);
        int length = c2.length;
        if (length < i) {
            while (length < i) {
                e c3 = c();
                if (c3 == null) {
                    synchronized (this.n) {
                        this.n.add(c);
                    }
                    return null;
                }
                c2 = c(c3);
                if (c2.length + length > i) {
                    short[] a = a(copyOf, length, c2);
                    if (a != null) {
                        length += c2.length - a.length;
                        c3 = a(a);
                        synchronized (this.n) {
                            this.n.add(c3);
                        }
                    } else {
                        continue;
                    }
                } else {
                    a(copyOf, length, c2);
                    length += c2.length;
                }
            }
        } else if (length > i) {
            e a2 = a(Arrays.copyOfRange(c2, i, c2.length));
            synchronized (this.n) {
                this.n.add(a2);
            }
            return copyOf;
        } else if (length == i) {
            return c(c);
        }
        return copyOf;
    }

    private short[] c(e eVar) {
        return com.tencent.liteav.videoediter.audio.b.a(eVar.b(), eVar.g());
    }

    private e a(short[] sArr) {
        if (sArr == null || sArr.length == 0) {
            return null;
        }
        e eVar = new e();
        eVar.a(com.tencent.liteav.videoediter.audio.b.a(sArr));
        eVar.d(sArr.length * 2);
        return eVar;
    }

    private short[] a(short[] sArr, int i, short[] sArr2) {
        int i2 = 0;
        while (i2 < sArr2.length && i < sArr.length) {
            sArr[i] = sArr2[i2];
            i++;
            i2++;
        }
        if ((sArr2.length - i2) + 1 > 0) {
            return Arrays.copyOfRange(sArr2, i2, sArr2.length);
        }
        return null;
    }

    private Long f() {
        long j;
        if (this.l == 0) {
            j = this.o;
        } else {
            j = this.o + ((1024000000 * ((long) this.l)) / ((long) this.m.sampleRate));
        }
        this.l++;
        return Long.valueOf(j);
    }

    public void b(e eVar) {
        this.e++;
        try {
            this.g.put(eVar);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (this.k) {
            if (this.h != null) {
                int i = this.m.channelCount * 2048;
                if (this.o == -1) {
                    this.o = eVar.e();
                }
                this.h.add(Long.valueOf(f().longValue()));
            }
        }
        while (this.g.size() > 0 && this.d) {
            e g = g();
            if (g != null) {
                e eVar2;
                try {
                    eVar2 = (e) this.g.take();
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                    eVar2 = null;
                }
                if (eVar2 != null) {
                    a(g, eVar2);
                }
            } else if (this.c != null) {
                this.c.a(this.g.size());
            }
        }
    }

    private e g() {
        if (VERSION.SDK_INT < 16) {
            return null;
        }
        int dequeueInputBuffer = this.i.dequeueInputBuffer(10000);
        if (dequeueInputBuffer < 0) {
            return null;
        }
        ByteBuffer inputBuffer;
        if (VERSION.SDK_INT >= 21) {
            inputBuffer = this.i.getInputBuffer(dequeueInputBuffer);
        } else {
            inputBuffer = this.i.getInputBuffers()[dequeueInputBuffer];
        }
        inputBuffer.clear();
        return new e(inputBuffer, 0, 0, dequeueInputBuffer, 0, 0);
    }

    private void a(e eVar, e eVar2) {
        if (VERSION.SDK_INT >= 16) {
            int d = eVar.d();
            ByteBuffer b = eVar.b();
            if (eVar2.p()) {
                this.i.queueInputBuffer(d, 0, 0, eVar2.e(), 4);
                return;
            }
            ByteBuffer duplicate = eVar2.b().duplicate();
            duplicate.rewind();
            duplicate.limit(eVar2.g());
            b.rewind();
            if (eVar2.g() <= b.remaining()) {
                b.put(duplicate);
                this.i.queueInputBuffer(d, 0, eVar2.g(), eVar2.e(), 0);
                return;
            }
            String str = "input size is larger than buffer capacity. should increate buffer capacity by setting MediaFormat.KEY_MAX_INPUT_SIZE while configure. mime = ";
            TXCLog.e("AudioMediaCodecEncoder", str);
            throw new IllegalArgumentException(str);
        }
    }

    private void h() {
        if (VERSION.SDK_INT >= 16) {
            if (this.i == null) {
                TXCLog.e("AudioMediaCodecEncoder", "onDecodeOutput, mMediaCodec is null");
                if (this.a != null) {
                    this.a.a(this.q, 10);
                    return;
                }
                return;
            }
            BufferInfo bufferInfo = new BufferInfo();
            ByteBuffer[] outputBuffers = this.i.getOutputBuffers();
            int dequeueOutputBuffer = this.i.dequeueOutputBuffer(bufferInfo, 10000);
            if (dequeueOutputBuffer != -1) {
                if (dequeueOutputBuffer == -3) {
                    this.i.getOutputBuffers();
                } else if (dequeueOutputBuffer == -2) {
                    MediaFormat outputFormat = this.i.getOutputFormat();
                    if (this.b != null) {
                        this.b.a(outputFormat);
                    }
                } else if (dequeueOutputBuffer >= 0) {
                    ByteBuffer outputBuffer;
                    if (VERSION.SDK_INT >= 21) {
                        outputBuffer = this.i.getOutputBuffer(dequeueOutputBuffer);
                    } else {
                        outputBuffer = outputBuffers[dequeueOutputBuffer];
                    }
                    if (outputBuffer == null) {
                        throw new RuntimeException("encoderOutputBuffer " + dequeueOutputBuffer + " was null.mime:");
                    }
                    byte[] bArr = new byte[bufferInfo.size];
                    outputBuffer.position(bufferInfo.offset);
                    outputBuffer.limit(bufferInfo.offset + bufferInfo.size);
                    outputBuffer.get(bArr, 0, bufferInfo.size);
                    if ((bufferInfo.flags & 2) == 2) {
                        bufferInfo.size = 0;
                    }
                    if (!(this.b == null || bufferInfo.size == 0)) {
                        this.f++;
                        TXCLog.d("AudioMediaCodecEncoder", "mPopIdx:" + this.f + ", info flags = " + bufferInfo.flags + ", pts:" + bufferInfo.presentationTimeUs + ",info.size=" + bufferInfo.size);
                        bufferInfo.presentationTimeUs = d();
                        BufferInfo bufferInfo2 = new BufferInfo();
                        outputBuffer = ByteBuffer.wrap(bArr);
                        bufferInfo2.set(bufferInfo.offset, bArr.length, bufferInfo.presentationTimeUs, bufferInfo.flags);
                        this.b.a(outputBuffer, bufferInfo);
                    }
                    this.i.releaseOutputBuffer(dequeueOutputBuffer, false);
                    if ((bufferInfo.flags & 4) != 0) {
                        if (this.b != null) {
                            this.b.a();
                            return;
                        }
                        return;
                    }
                }
                if (this.a != null) {
                    this.a.b(this.q);
                }
            } else if (this.a != null) {
                this.a.b(this.q);
            }
        }
    }

    protected long d() {
        synchronized (this.k) {
            if (this.h.isEmpty()) {
                this.j = Long.valueOf(this.j.longValue() + 100);
                return this.j.longValue();
            }
            this.j = (Long) this.h.pollFirst();
            long longValue = this.j.longValue();
            return longValue;
        }
    }

    private MediaFormat c(n nVar) {
        this.m = nVar;
        if (VERSION.SDK_INT < 16) {
            return null;
        }
        MediaFormat createAudioFormat = MediaFormat.createAudioFormat("audio/mp4a-latm", nVar.sampleRate, nVar.channelCount);
        createAudioFormat.setInteger(IjkMediaMeta.IJKM_KEY_BITRATE, nVar.audioBitrate);
        createAudioFormat.setInteger("aac-profile", 2);
        int i = (nVar.channelCount * 1024) * 2;
        if (i <= 102400) {
            i = 102400;
        }
        createAudioFormat.setInteger("max-input-size", i);
        return createAudioFormat;
    }

    private static MediaCodecInfo a(String str) {
        if (VERSION.SDK_INT >= 16) {
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
        }
        return null;
    }

    public void a(g gVar) {
        TXCLog.i("AudioMediaCodecEncoder", "setPCMQueueCallback listener:" + gVar);
        this.c = gVar;
    }
}
