package com.tencent.liteav.videoediter.a;

import android.annotation.TargetApi;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import com.tencent.liteav.basic.log.TXCLog;
import com.umeng.message.proguard.l;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentLinkedQueue;

@TargetApi(18)
/* compiled from: TXCMP4Muxer */
public class a {
    private final boolean a = false;
    private MediaMuxer b;
    private String c = null;
    private MediaFormat d = null;
    private MediaFormat e = null;
    private int f = 1;
    private int g = 0;
    private int h = 0;
    private int i = 0;
    private boolean j = false;
    private boolean k = false;
    private ConcurrentLinkedQueue<a> l = new ConcurrentLinkedQueue();
    private ConcurrentLinkedQueue<a> m = new ConcurrentLinkedQueue();
    private long n = -1;
    private long o = -1;
    private long p = -1;

    /* compiled from: TXCMP4Muxer */
    private static class a {
        ByteBuffer a;
        BufferInfo b;

        public a(ByteBuffer byteBuffer, BufferInfo bufferInfo) {
            this.a = byteBuffer;
            this.b = bufferInfo;
        }

        public ByteBuffer a() {
            return this.a;
        }

        public BufferInfo b() {
            return this.b;
        }
    }

    public synchronized void a(MediaFormat mediaFormat) {
        TXCLog.d("TXCMP4HWMuxer", "addVideoTrack:" + mediaFormat);
        this.d = mediaFormat;
        this.g |= 1;
        this.l.clear();
    }

    public synchronized void b(MediaFormat mediaFormat) {
        TXCLog.d("TXCMP4HWMuxer", "addAudioTrack:" + mediaFormat);
        this.e = mediaFormat;
        this.g |= 2;
        this.m.clear();
    }

    public synchronized boolean a() {
        boolean z = true;
        synchronized (this) {
            if ((this.f & 1) != 0) {
                if ((this.g & 1) == 0) {
                    z = false;
                }
            }
        }
        return z;
    }

    public synchronized boolean b() {
        boolean z = true;
        synchronized (this) {
            if ((this.f & 2) != 0) {
                if ((this.g & 2) == 0) {
                    z = false;
                }
            }
        }
        return z;
    }

    public synchronized int c() {
        int i = 0;
        synchronized (this) {
            if (this.c == null || this.c.isEmpty()) {
                TXCLog.e("TXCMP4HWMuxer", "target path not set yet!");
                i = -1;
            } else if (!a()) {
                TXCLog.e("TXCMP4HWMuxer", "video track not set yet!");
                i = -2;
            } else if (!b()) {
                TXCLog.e("TXCMP4HWMuxer", "audio track not set yet!");
                i = -3;
            } else if (this.b != null) {
                TXCLog.w("TXCMP4HWMuxer", "start has been called. stop must be called before start");
            } else {
                TXCLog.d("TXCMP4HWMuxer", "start");
                try {
                    this.b = new MediaMuxer(this.c, 0);
                    if (this.d != null) {
                        try {
                            this.i = this.b.addTrack(this.d);
                        } catch (IllegalArgumentException e) {
                            TXCLog.e("TXCMP4HWMuxer", "addVideoTrack IllegalArgumentException: " + e);
                            i = -5;
                        } catch (IllegalStateException e2) {
                            TXCLog.e("TXCMP4HWMuxer", "addVideoTrack IllegalStateException: " + e2);
                            i = -6;
                        }
                    }
                    if (this.e != null) {
                        try {
                            this.h = this.b.addTrack(this.e);
                        } catch (IllegalArgumentException e3) {
                            TXCLog.e("TXCMP4HWMuxer", "addAudioTrack IllegalArgumentException: " + e3);
                            i = -7;
                        } catch (IllegalStateException e22) {
                            TXCLog.e("TXCMP4HWMuxer", "addAudioTrack IllegalStateException: " + e22);
                            i = -8;
                        }
                    }
                    this.b.start();
                    this.n = -1;
                    this.j = true;
                    this.k = false;
                    this.o = -1;
                    this.p = -1;
                } catch (IOException e4) {
                    e4.printStackTrace();
                    TXCLog.e("TXCMP4HWMuxer", "create MediaMuxer exception:" + e4);
                    i = -4;
                }
            }
        }
        return i;
    }

    public synchronized int d() {
        int i = 0;
        synchronized (this) {
            try {
                if (this.b != null) {
                    TXCLog.d("TXCMP4HWMuxer", "stop. start flag = " + this.j + ", video key frame set = " + this.k);
                    if (this.j && this.k) {
                        this.b.stop();
                    }
                    this.b.release();
                    this.j = false;
                    this.b = null;
                    this.g = 0;
                    this.k = false;
                    this.l.clear();
                    this.m.clear();
                    this.d = null;
                    this.e = null;
                    this.o = -1;
                    this.p = -1;
                }
            } catch (Exception e) {
                TXCLog.e("TXCMP4HWMuxer", "muxer stop/release exception: " + e);
                i = -1;
                this.j = false;
                this.b = null;
                this.g = 0;
                this.k = false;
                this.l.clear();
                this.m.clear();
                this.d = null;
                this.e = null;
                this.o = -1;
                this.p = -1;
            } catch (Throwable th) {
                throw th;
            }
        }
        return i;
    }

    public synchronized void a(String str) {
        this.c = str;
    }

    public synchronized void a(ByteBuffer byteBuffer, BufferInfo bufferInfo) {
        if (this.b == null) {
            a(true, byteBuffer, bufferInfo);
            TXCLog.w("TXCMP4HWMuxer", "cache frame before muexer ready. ptsUs: " + bufferInfo.presentationTimeUs);
        } else {
            if (this.n < 0) {
                this.n = e();
                f();
            }
            c(byteBuffer, bufferInfo);
        }
    }

    public synchronized void b(ByteBuffer byteBuffer, BufferInfo bufferInfo) {
        if (this.b == null || this.n < 0) {
            TXCLog.w("TXCMP4HWMuxer", "cache sample before muexer ready. ptsUs: " + bufferInfo.presentationTimeUs);
            a(false, byteBuffer, bufferInfo);
        } else {
            d(byteBuffer, bufferInfo);
        }
    }

    private void c(ByteBuffer byteBuffer, BufferInfo bufferInfo) {
        long j = bufferInfo.presentationTimeUs - this.n;
        if (j < 0) {
            TXCLog.e("TXCMP4HWMuxer", "drop frame. first frame offset timeus = " + this.n + ", current timeus = " + bufferInfo.presentationTimeUs);
        } else if (j < this.o) {
            TXCLog.e("TXCMP4HWMuxer", "drop frame. current frame's pts(" + j + ") must larger than pre frame's pts(" + this.o + l.t);
        } else {
            this.o = j;
            bufferInfo.presentationTimeUs = j;
            try {
                byteBuffer.position(bufferInfo.offset);
                byteBuffer.limit(bufferInfo.offset + bufferInfo.size);
                this.b.writeSampleData(this.i, byteBuffer, bufferInfo);
                if ((bufferInfo.flags & 1) != 0) {
                    this.k = true;
                }
            } catch (IllegalStateException e) {
                TXCLog.e("TXCMP4HWMuxer", "write frame IllegalStateException: " + e);
            }
        }
    }

    private void d(ByteBuffer byteBuffer, BufferInfo bufferInfo) {
        long j = bufferInfo.presentationTimeUs - this.n;
        if (this.n < 0 || j < 0) {
            TXCLog.w("TXCMP4HWMuxer", "drop sample. first frame offset timeus = " + this.n + ", current sample timeus = " + bufferInfo.presentationTimeUs);
        } else if (j < this.p) {
            TXCLog.e("TXCMP4HWMuxer", "drop sample. current sample's pts pts(" + j + ") must larger than pre frame's pts(" + this.p + l.t);
        } else {
            this.p = j;
            bufferInfo.presentationTimeUs = j;
            try {
                this.b.writeSampleData(this.h, byteBuffer, bufferInfo);
            } catch (IllegalStateException e) {
                TXCLog.e("TXCMP4HWMuxer", "write sample IllegalStateException: " + e);
            }
        }
    }

    private void a(boolean z, ByteBuffer byteBuffer, BufferInfo bufferInfo) {
        if (byteBuffer != null && bufferInfo != null) {
            ByteBuffer allocateDirect = ByteBuffer.allocateDirect(byteBuffer.capacity());
            byteBuffer.rewind();
            if (bufferInfo.size > 0) {
                byteBuffer.position(bufferInfo.offset);
                byteBuffer.limit(bufferInfo.size);
            }
            allocateDirect.rewind();
            allocateDirect.put(byteBuffer);
            BufferInfo bufferInfo2 = new BufferInfo();
            bufferInfo2.set(bufferInfo.offset, bufferInfo.size, bufferInfo.presentationTimeUs, bufferInfo.flags);
            a aVar = new a(allocateDirect, bufferInfo2);
            if (z) {
                if (this.l.size() < 200) {
                    this.l.add(aVar);
                } else {
                    TXCLog.e("TXCMP4HWMuxer", "drop video frame. video cache size is larger than 200");
                }
            } else if (this.m.size() < 300) {
                this.m.add(aVar);
            } else {
                TXCLog.e("TXCMP4HWMuxer", "drop audio frame. audio cache size is larger than 300");
            }
        }
    }

    private long e() {
        long j;
        if (this.l.size() > 0) {
            j = ((a) this.l.peek()).b().presentationTimeUs;
        } else {
            j = 0;
        }
        if (this.m.size() <= 0) {
            return j;
        }
        a aVar = (a) this.m.peek();
        if (aVar == null || aVar.b() == null) {
            return j;
        }
        long j2 = ((a) this.m.peek()).b().presentationTimeUs;
        if (j > j2) {
            return j2;
        }
        return j;
    }

    private void f() {
        a aVar;
        while (this.l.size() > 0) {
            aVar = (a) this.l.poll();
            c(aVar.a(), aVar.b());
        }
        while (this.m.size() > 0) {
            aVar = (a) this.m.poll();
            d(aVar.a(), aVar.b());
        }
    }
}
