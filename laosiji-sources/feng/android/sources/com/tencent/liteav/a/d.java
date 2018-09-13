package com.tencent.liteav.a;

import android.content.Context;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaFormat;
import android.opengl.EGLContext;
import android.text.TextUtils;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.c.e;
import com.tencent.liteav.d.a;
import com.tencent.liteav.d.g;
import com.tencent.liteav.d.n;
import com.tencent.liteav.d.o;
import com.tencent.liteav.muxer.c;
import com.tencent.liteav.videoencoder.TXSVideoEncoderParam;
import com.tencent.liteav.videoencoder.b;
import com.tencent.ugc.TXRecordCommon;
import java.nio.ByteBuffer;
import java.util.concurrent.LinkedBlockingQueue;

/* compiled from: TXCombineEncAndMuxer */
public class d {
    protected boolean a = false;
    protected b b;
    protected c c;
    private final String d = "TXCombineEncAndMuxer";
    private Context e;
    private a f;
    private int g = 1;
    private int h = 98304;
    private int i = 10000;
    private int j = TXRecordCommon.AUDIO_SAMPLERATE_48000;
    private String k;
    private boolean l = false;
    private long m;
    private int n = 13000;
    private int o;
    private int p;
    private LinkedBlockingQueue<e> q;
    private boolean r;
    private boolean s;
    private a.b t;
    private g u = new g() {
        public void a(int i) {
        }
    };
    private com.tencent.liteav.videoencoder.d v = new com.tencent.liteav.videoencoder.d() {
        public void onEncodeNAL(com.tencent.liteav.basic.e.b bVar, int i) {
            if (i != 0) {
                TXCLog.i("TXCombineEncAndMuxer", "mVideoEncodeListener, errCode = " + i);
            } else if (bVar == null || bVar.a == null) {
                TXCLog.i("TXCombineEncAndMuxer", "===Video onEncodeComplete===");
                d.this.r = true;
                if (d.this.s) {
                    TXCLog.i("TXCombineEncAndMuxer", "===Video onEncodeComplete=== mAudioEncEnd is true");
                    d.this.a();
                    d.this.b();
                }
            } else {
                e eVar;
                try {
                    TXCLog.i("TXCombineEncAndMuxer", "onEncodeNAL, before take mVideoQueue size = " + d.this.q.size());
                    eVar = (e) d.this.q.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    eVar = null;
                }
                synchronized (this) {
                    if (!(d.this.c == null || bVar == null || bVar.a == null)) {
                        if (d.this.l) {
                            a(bVar, eVar);
                        } else if (bVar.b == 0) {
                            MediaFormat a = com.tencent.liteav.basic.util.a.a(bVar.a, d.this.o, d.this.p);
                            if (a != null) {
                                d.this.c.a(a);
                                d.this.c.a();
                                d.this.l = true;
                            }
                            a(bVar, eVar);
                        }
                    }
                }
                d.this.d(eVar);
            }
        }

        private void a(com.tencent.liteav.basic.e.b bVar, e eVar) {
            long e = eVar.e();
            TXCLog.i("TXCombineEncAndMuxer", "Muxer writeVideoData :" + e);
            int i = bVar.k == null ? bVar.b == 0 ? 1 : 0 : bVar.k.flags;
            if (d.this.c != null) {
                d.this.c.b(bVar.a, 0, bVar.a.length, e, i);
            }
        }

        public void onEncodeFormat(MediaFormat mediaFormat) {
            TXCLog.i("TXCombineEncAndMuxer", "Video onEncodeFormat format:" + mediaFormat);
            if (d.this.c != null) {
                d.this.c.a(mediaFormat);
                if (d.this.c.d()) {
                    TXCLog.i("TXCombineEncAndMuxer", "Has Audio, Video Muxer start");
                    d.this.c.a();
                    d.this.l = true;
                }
            }
        }
    };
    private o w = new o() {
        public void a(ByteBuffer byteBuffer, BufferInfo bufferInfo) {
            TXCLog.i("TXCombineEncAndMuxer", "Audio onEncodeAAC");
            if (d.this.c != null) {
                TXCLog.i("TXCombineEncAndMuxer", "Muxer writeAudioData :" + bufferInfo.presentationTimeUs);
                d.this.c.a(byteBuffer, bufferInfo);
            }
        }

        public void a(MediaFormat mediaFormat) {
            TXCLog.i("TXCombineEncAndMuxer", "Audio onEncodeFormat format:" + mediaFormat);
            if (d.this.c != null) {
                d.this.c.b(mediaFormat);
                if (d.this.c.c()) {
                    d.this.c.a();
                    d.this.l = true;
                }
            }
        }

        public void a() {
            TXCLog.i("TXCombineEncAndMuxer", "===Audio onEncodeComplete===");
            d.this.s = true;
            if (d.this.r) {
                TXCLog.i("TXCombineEncAndMuxer", "===Audio onEncodeComplete=== mVideoEncEnd is true");
                d.this.a();
                d.this.b();
            }
        }
    };

    public d(Context context) {
        this.e = context;
        this.q = new LinkedBlockingQueue();
    }

    public void a(a.b bVar) {
        this.t = bVar;
    }

    public void a(int i, int i2) {
        int i3 = 1;
        int i4 = 0;
        this.o = i;
        this.p = i2;
        this.r = false;
        this.s = false;
        boolean z = this.o < 1280 && this.p < 1280;
        this.a = z;
        if (this.a) {
            i3 = 2;
        }
        this.b = new b(i3);
        Context context = this.e;
        if (!this.a) {
            i4 = 2;
        }
        this.c = new c(context, i4);
        if (!TextUtils.isEmpty(this.k)) {
            this.c.a(this.k);
        }
    }

    public void a(String str) {
        this.k = str;
        if (this.c != null) {
            this.c.a(this.k);
        }
    }

    public void a(long j) {
        this.m = j;
    }

    public void a(int i) {
        this.i = i;
    }

    public void b(int i) {
        this.j = i;
    }

    public void a(EGLContext eGLContext) {
        TXCLog.d("TXCombineEncAndMuxer", "OnContextListener onContext");
        TXSVideoEncoderParam tXSVideoEncoderParam = new TXSVideoEncoderParam();
        tXSVideoEncoderParam.width = this.o;
        tXSVideoEncoderParam.height = this.p;
        tXSVideoEncoderParam.fps = 20;
        tXSVideoEncoderParam.glContext = eGLContext;
        tXSVideoEncoderParam.enableEGL14 = true;
        tXSVideoEncoderParam.enableBlackList = false;
        tXSVideoEncoderParam.appendSpsPps = false;
        tXSVideoEncoderParam.annexb = true;
        tXSVideoEncoderParam.fullIFrame = false;
        tXSVideoEncoderParam.gop = 3;
        if (this.a) {
            tXSVideoEncoderParam.encoderMode = 1;
            tXSVideoEncoderParam.encoderProfile = 3;
            tXSVideoEncoderParam.record = true;
        } else {
            tXSVideoEncoderParam.encoderMode = 3;
            tXSVideoEncoderParam.encoderProfile = 1;
        }
        this.b.a(this.n);
        this.b.a(this.v);
        this.b.a(tXSVideoEncoderParam);
        this.f = new a();
        this.f.a(this.u);
        this.f.a(this.w);
        n nVar = new n();
        nVar.channelCount = this.g;
        nVar.sampleRate = this.j;
        nVar.maxInputSize = this.i;
        nVar.audioBitrate = this.h;
        TXCLog.i("TXCombineEncAndMuxer", "AudioEncoder.start");
        this.f.a(nVar);
    }

    public void a(int i, int i2, int i3, e eVar) {
        if (this.b != null) {
            this.q.add(eVar);
            this.b.b(i, i2, i3, eVar.e() / 1000);
        }
    }

    public void a(e eVar) {
        if (this.f != null) {
            this.f.a(eVar);
        }
    }

    public void b(e eVar) {
        this.q.add(eVar);
        this.b.b();
    }

    public void c(e eVar) {
        if (this.f != null) {
            this.f.a(eVar);
        }
    }

    public void a() {
        TXCLog.i("TXCombineEncAndMuxer", "stopEncAndMuxer()");
        if (this.b != null) {
            this.b.a();
            this.b = null;
        }
        if (this.f != null) {
            this.f.a();
            this.f = null;
        }
        if (this.c != null) {
            this.c.b();
            this.c = null;
            this.l = false;
        }
        this.k = null;
        this.q.clear();
    }

    private void d(e eVar) {
        if (this.t != null) {
            this.t.a(((float) eVar.e()) / (((float) this.m) * 1000.0f));
        }
    }

    private void b() {
        if (this.t != null) {
            this.t.a(0, "");
        }
    }
}
