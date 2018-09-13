package com.tencent.liteav.d;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaFormat;
import android.opengl.EGLContext;
import android.view.Surface;
import com.tencent.liteav.b.i;
import com.tencent.liteav.b.k;
import com.tencent.liteav.basic.e.b;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.c.g;
import com.tencent.liteav.e.j;
import com.tencent.liteav.j.a;
import com.tencent.liteav.j.f;
import com.tencent.liteav.muxer.c;
import com.tencent.liteav.videoencoder.TXSVideoEncoderParam;
import com.tencent.liteav.videoencoder.d;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/* compiled from: BasicVideoGenerate */
public abstract class e {
    private h A = new h() {
        public void a(com.tencent.liteav.c.e eVar) {
            if (eVar != null) {
                a.d();
                f.a().c(eVar.e());
                if (eVar.p()) {
                    if (e.this.k.b()) {
                        if (e.this.k.j()) {
                            if (e.this.h != null) {
                                e.this.h.b();
                                TXCLog.d("BasicVideoGenerate", "Encount EOF TailWaterMarkListener signalEOSAndFlush");
                                return;
                            }
                        } else if (!e.this.l.l() || ((k.a().d() != 2 || e.this.d.c()) && (k.a().d() != 1 || e.this.c.h() || e.this.c.o()))) {
                            TXCLog.i("BasicVideoGenerate", "Encount EOF Audio didProcessFrame appendTailWaterMark");
                            e.this.k.a = e.this.u;
                            e.this.k.b = e.this.t;
                            e.this.k.d();
                            return;
                        } else {
                            TXCLog.w("BasicVideoGenerate", "Encount EOF Video Has No Audio Append BGM,Video is not end");
                            return;
                        }
                    } else if (k.a().d() == 2) {
                        return;
                    } else {
                        if (k.a().d() == 1 && !e.this.c.h()) {
                            return;
                        }
                        if (e.this.h != null) {
                            e.this.h.b();
                            TXCLog.d("BasicVideoGenerate", "signalEOSAndFlush");
                            return;
                        }
                    }
                }
                if (e.this.q != null) {
                    e.this.q.a(eVar);
                }
                if (e.this.g != null) {
                    e.this.g.i();
                }
                e.this.t = eVar;
            }
        }
    };
    private d B = new d() {
        public void onEncodeNAL(b bVar, int i) {
            if (i != 0) {
                TXCLog.i("BasicVideoGenerate", "mVideoEncodeListener, errCode = " + i);
                return;
            }
            a.f();
            if (e.this.l.e()) {
                TXCLog.i("BasicVideoGenerate", "mVideoEncodeListener, input is full, output is full");
            } else if (bVar == null || bVar.a == null) {
                TXCLog.i("BasicVideoGenerate", "===Video onEncodeComplete===");
                e.this.b();
                e.this.d();
            } else {
                com.tencent.liteav.c.e eVar;
                try {
                    eVar = (com.tencent.liteav.c.e) e.this.s.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    eVar = null;
                }
                if (eVar.p()) {
                    TXCLog.i("BasicVideoGenerate", "===Video onEncodeComplete===:" + eVar.p() + ", nal:" + bVar);
                    e.this.b();
                    e.this.d();
                    return;
                }
                synchronized (this) {
                    if (!(e.this.i == null || bVar == null || bVar.a == null)) {
                        if (e.this.r) {
                            a(bVar, eVar);
                        } else if (bVar.b == 0) {
                            MediaFormat a = com.tencent.liteav.basic.util.a.a(bVar.a, e.this.l.h.a, e.this.l.h.b);
                            if (a != null) {
                                e.this.i.a(a);
                                e.this.i.a();
                                e.this.r = true;
                            }
                            a(bVar, eVar);
                        }
                    }
                }
                e.this.a(eVar.t());
            }
        }

        private void a(b bVar, com.tencent.liteav.c.e eVar) {
            long a = f.a(eVar);
            f.a().f(a);
            int i = bVar.k == null ? bVar.b == 0 ? 1 : 0 : bVar.k.flags;
            if (e.this.i != null) {
                e.this.i.b(bVar.a, 0, bVar.a.length, a, i);
            }
        }

        public void onEncodeFormat(MediaFormat mediaFormat) {
            TXCLog.i("BasicVideoGenerate", "Video onEncodeFormat format:" + mediaFormat);
            if (!e.this.l.e() && e.this.i != null) {
                e.this.i.a(mediaFormat);
                if (!e.this.l.l()) {
                    TXCLog.i("muxer", "No Audio, Video Muxer start");
                    e.this.i.a();
                    e.this.r = true;
                } else if (e.this.i.d()) {
                    TXCLog.i("BasicVideoGenerate", "Has Audio, Video Muxer start");
                    e.this.i.a();
                    e.this.r = true;
                }
            }
        }
    };
    private o C = new o() {
        public void a(ByteBuffer byteBuffer, BufferInfo bufferInfo) {
            a.g();
            f.a().e(bufferInfo.presentationTimeUs);
            if (e.this.i != null) {
                e.this.i.a(byteBuffer, bufferInfo);
            }
        }

        public void a(MediaFormat mediaFormat) {
            TXCLog.i("BasicVideoGenerate", "Audio onEncodeFormat format:" + mediaFormat);
            if (!e.this.l.e() && e.this.i != null) {
                e.this.i.b(mediaFormat);
                if ((k.a().d() == 2 || e.this.c.i()) && e.this.i.c()) {
                    e.this.i.a();
                    e.this.r = true;
                }
            }
        }

        public void a() {
            TXCLog.i("BasicVideoGenerate", "===Audio onEncodeComplete===");
            e.this.b();
            e.this.d();
        }
    };
    private g D = new g() {
        public void a(int i) {
            boolean z = true;
            if (k.a().d() == 1 && e.this.c.h()) {
                d dVar = e.this.c;
                if (i > 5) {
                    z = false;
                }
                dVar.b(z);
            } else if (e.this.g != null) {
                com.tencent.liteav.e.b bVar = e.this.g;
                if (i > 5) {
                    z = false;
                }
                bVar.c(z);
            }
        }
    };
    protected Context a;
    protected boolean b = false;
    protected d c;
    protected m d;
    protected x e;
    protected com.tencent.liteav.e.k f;
    protected com.tencent.liteav.e.b g;
    protected com.tencent.liteav.videoencoder.b h;
    protected c i;
    protected com.tencent.liteav.b.c j;
    protected j k;
    protected i l;
    protected q m = new q() {
        public void a(com.tencent.liteav.c.e eVar) {
            if (e.this.g != null) {
                e.this.g.a(eVar);
            }
        }

        public void b(com.tencent.liteav.c.e eVar) {
            TXCLog.d("BasicVideoGenerate", "TailWaterMarkListener onDecodeVideoFrame  frame:" + eVar.e() + ", flag : " + eVar.f() + ", reverse time = " + eVar.u());
            try {
                e.this.s.put(eVar);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (e.this.e != null) {
                e.this.e.a(eVar);
            }
        }
    };
    private final String n = "BasicVideoGenerate";
    private f o;
    private Surface p;
    private a q;
    private boolean r = false;
    private LinkedBlockingQueue<com.tencent.liteav.c.e> s;
    private com.tencent.liteav.c.e t;
    private com.tencent.liteav.c.e u;
    private l v = new l() {
        public void a(EGLContext eGLContext) {
            TXCLog.d("BasicVideoGenerate", "OnContextListener onContext");
            if (e.this.p != null) {
                if (e.this.h != null) {
                    TXSVideoEncoderParam tXSVideoEncoderParam = new TXSVideoEncoderParam();
                    tXSVideoEncoderParam.width = e.this.l.h.a;
                    tXSVideoEncoderParam.height = e.this.l.h.b;
                    tXSVideoEncoderParam.fps = e.this.l.j();
                    tXSVideoEncoderParam.glContext = eGLContext;
                    tXSVideoEncoderParam.enableEGL14 = true;
                    tXSVideoEncoderParam.enableBlackList = false;
                    tXSVideoEncoderParam.appendSpsPps = false;
                    tXSVideoEncoderParam.annexb = true;
                    tXSVideoEncoderParam.fullIFrame = e.this.l.m;
                    tXSVideoEncoderParam.gop = e.this.l.k();
                    if (e.this.b) {
                        tXSVideoEncoderParam.encoderMode = 1;
                        tXSVideoEncoderParam.encoderProfile = 3;
                        tXSVideoEncoderParam.record = true;
                    } else {
                        tXSVideoEncoderParam.encoderMode = 3;
                        tXSVideoEncoderParam.encoderProfile = 1;
                    }
                    e.this.h.a(e.this.l.i());
                    e.this.h.a(e.this.B);
                    e.this.h.a(tXSVideoEncoderParam);
                }
                if (e.this.l.l()) {
                    e.this.q = new a();
                    e.this.q.a(e.this.D);
                    e.this.q.a(e.this.C);
                    n nVar = new n();
                    nVar.channelCount = e.this.l.b;
                    nVar.sampleRate = e.this.l.a;
                    nVar.maxInputSize = e.this.l.c;
                    nVar.audioBitrate = e.this.l.h();
                    TXCLog.i("BasicVideoGenerate", "AudioEncoder.start");
                    e.this.q.a(nVar);
                    if (e.this.g != null) {
                        e.this.g.e();
                    }
                }
                if (k.a().d() == 1 && e.this.c != null) {
                    e.this.c.a(e.this.p);
                    e.this.c.a(e.this.w);
                    e.this.c.l();
                } else if (k.a().d() == 2 && e.this.d != null) {
                    e.this.d.a(e.this.x);
                    e.this.d.d();
                }
                a.h();
                f.a().b();
            }
        }
    };
    private com.tencent.liteav.f.a.b w = new com.tencent.liteav.f.a.b() {
        public void a(com.tencent.liteav.c.e eVar) {
            a.b();
            f.a().a(eVar.e());
            if (e.this.g != null) {
                e.this.g.a(eVar);
            }
        }

        public void b(com.tencent.liteav.c.e eVar) {
            a.a();
            f.a().b(eVar.e());
            try {
                e.this.s.put(eVar);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (e.this.e != null) {
                e.this.e.a(eVar);
            }
        }
    };
    private com.tencent.liteav.f.a.i x = new com.tencent.liteav.f.a.i() {
        public void a(com.tencent.liteav.c.e eVar) {
            TXCLog.d("BasicVideoGenerate", "mPicDecListener, onDecodeBitmapFrame  frame:" + eVar.e() + ", flag : " + eVar.f());
            try {
                e.this.s.put(eVar);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (e.this.e != null) {
                e.this.e.b(eVar);
            }
        }
    };
    private j y = new j() {
        public void a(Surface surface) {
            TXCLog.i("BasicVideoGenerate", "IVideoRenderListener onSurfaceTextureAvailable");
            e.this.p = surface;
            if (e.this.f != null) {
                e.this.f.a();
                e.this.f.b();
            }
        }

        public void a(int i, int i2) {
            if (e.this.f != null) {
                g gVar = new g();
                gVar.a = i;
                gVar.b = i2;
                e.this.f.a(gVar);
            }
        }

        public void b(Surface surface) {
            TXCLog.i("BasicVideoGenerate", "IVideoRenderListener onSurfaceTextureDestroy");
            e.this.e();
            e.this.p = null;
            if (e.this.f != null) {
                e.this.f.c();
                e.this.f.d();
            }
            if (e.this.o != null) {
                e.this.o.a();
            }
        }

        public int a(int i, float[] fArr, com.tencent.liteav.c.e eVar) {
            a.e();
            if (e.this.o != null) {
                i = e.this.o.a(eVar, com.tencent.liteav.b.e.a().b(), eVar.r());
                eVar.l(i);
                eVar.m(0);
            }
            if (e.this.f != null) {
                e.this.f.a(fArr);
                e.this.f.a(i, eVar);
            }
            return 0;
        }

        public void a(boolean z) {
        }
    };
    private i z = new i() {
        public void a(int i, com.tencent.liteav.c.e eVar) {
            a.c();
            long a = f.a(eVar);
            f.a().d(a);
            if (eVar.p()) {
                if (e.this.k.b()) {
                    if (!e.this.k.j()) {
                        if (e.this.s != null) {
                            e.this.s.remove(eVar);
                        }
                        if (!e.this.l.l() || ((k.a().d() != 2 && (k.a().d() != 1 || e.this.c.h())) || (e.this.g != null && e.this.g.j()))) {
                            TXCLog.i("BasicVideoGenerate", "Encount EOF Video didProcessFrame appendTailWaterMark, mLastVideoFrame = " + e.this.u);
                            e.this.k.a = e.this.u;
                            e.this.k.b = e.this.t;
                            e.this.k.d();
                            TXCLog.i("BasicVideoGenerate", "mLastVideoFrame width, height = " + e.this.u.m() + ", " + e.this.u.n());
                            return;
                        }
                        TXCLog.w("BasicVideoGenerate", "Encount EOF Video Has No Audio Append BGM,BGM is not end");
                        return;
                    } else if (e.this.h != null) {
                        e.this.h.b();
                        TXCLog.d("BasicVideoGenerate", "TailWaterMarkListener signalEOSAndFlush");
                        return;
                    }
                } else if (e.this.h != null) {
                    e.this.h.b();
                    TXCLog.d("BasicVideoGenerate", "signalEOSAndFlush");
                    return;
                }
            }
            if (e.this.h != null) {
                e.this.h.b(i, eVar.m(), eVar.n(), a / 1000);
            }
            if (e.this.l.e()) {
                try {
                    e.this.s.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (k.a().d() == 1) {
                    e.this.c.p();
                }
            } else if (k.a().d() == 1) {
                if (!e.this.c.o()) {
                    e.this.c.p();
                } else if (e.this.k.b()) {
                    e.this.k.f();
                }
            } else if (k.a().d() == 2) {
                if (!e.this.d.c()) {
                    e.this.d.h();
                } else if (e.this.k.b()) {
                    e.this.k.f();
                }
            }
            e.this.u = eVar;
        }

        public int b(int i, com.tencent.liteav.c.e eVar) {
            return e.this.a(i, eVar.m(), eVar.n(), eVar.e());
        }
    };

    protected abstract int a(int i, int i2, int i3, long j);

    protected abstract void a(long j);

    protected abstract void d();

    protected abstract void e();

    public e(Context context) {
        this.a = context;
        this.e = new x();
        this.f = new com.tencent.liteav.e.k(context);
        this.f.a(this.z);
        this.s = new LinkedBlockingQueue();
        this.l = i.a();
        this.j = com.tencent.liteav.b.c.a();
        this.k = j.a();
        this.b = com.tencent.liteav.basic.util.a.f();
    }

    public void a(String str) {
        TXCLog.i("BasicVideoGenerate", "setVideoPath videoPath:" + str);
        try {
            if (this.c == null) {
                this.c = new s();
            }
            this.c.a(str);
            if (this.c.h()) {
                this.l.a(this.c.f());
            }
            this.l.b(this.c.g());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void a(List<Bitmap> list, int i) {
        this.d = new m();
        this.d.a(false);
        this.d.a((List) list, i);
        this.o = new f(this.a, this.d.a(), this.d.b());
    }

    public void a() {
        TXCLog.i("BasicVideoGenerate", "start");
        this.s.clear();
        f();
        if (this.l.l()) {
            this.g = new com.tencent.liteav.e.b();
            this.g.a();
            this.g.a(this.A);
            if (k.a().d() == 1) {
                this.g.b(this.c.h());
            } else {
                this.g.b(false);
            }
            this.g.c();
            MediaFormat m = this.l.m();
            if (m != null) {
                this.g.a(m);
            }
            if ((k.a().d() == 2 || !this.c.h()) && this.i != null) {
                this.i.b(m);
            }
        }
        g gVar = new g();
        if (k.a().d() == 1) {
            gVar.a = this.c.d();
            gVar.b = this.c.e();
            gVar.c = this.c.n();
        } else if (k.a().d() == 2) {
            gVar.a = this.d.a();
            gVar.b = this.d.b();
        }
        gVar = this.l.a(gVar);
        this.l.h = gVar;
        this.f.a(this.l.h);
        this.e.a(gVar);
        this.e.a(this.v);
        this.e.a(this.y);
        this.e.a();
    }

    public void b() {
        if (this.c != null) {
            this.c.a(null);
            this.c.m();
        }
        if (this.d != null) {
            this.d.a(null);
            this.d.e();
        }
        if (this.e != null) {
            this.e.a(null);
            this.e.a(null);
            this.e.b();
        }
        if (this.l.l() && this.g != null) {
            this.g.d();
            this.g.a(null);
            this.g.b();
            this.g = null;
        }
        if (this.h != null) {
            this.h.a(null);
            this.h.a();
        }
        if (this.q != null) {
            this.q.a(null);
            this.q.a(null);
            this.q.a();
        }
        TXCLog.i("BasicVideoGenerate", "stop muxer :" + this.r);
        this.r = false;
        if (this.i != null) {
            this.i.b();
            this.i = null;
        }
    }

    public void c() {
        if (this.c != null) {
            this.c.k();
        }
        this.c = null;
        if (this.d != null) {
            this.d.i();
        }
        this.d = null;
        if (this.e != null) {
            this.e.c();
        }
        this.e = null;
        if (this.f != null) {
            this.f.a(null);
        }
        this.f = null;
        this.h = null;
        if (this.q != null) {
            this.q.b();
        }
        this.q = null;
        this.C = null;
        this.A = null;
        this.C = null;
        this.v = null;
        this.w = null;
        this.D = null;
        this.x = null;
        this.m = null;
        this.z = null;
        this.B = null;
        this.y = null;
    }

    private void f() {
        long c;
        if (k.a().d() == 1) {
            c = this.c.c();
        } else if (k.a().d() == 2) {
            c = this.d.a(com.tencent.liteav.b.e.a().b()) * 1000;
        } else {
            c = 0;
        }
        TXCLog.d("BasicVideoGenerate", "calculateDuration durationUs:" + c);
        long b = this.j.b();
        long c2 = this.j.c();
        if (c2 - b > 0) {
            c = c2 - b;
            TXCLog.d("BasicVideoGenerate", "calculateDuration Cut durationUs:" + c);
            if (k.a().d() == 1) {
                this.c.a(b, c2);
            } else if (k.a().d() == 2) {
                this.d.a(b / 1000, c2 / 1000);
            }
        } else if (k.a().d() == 1) {
            this.c.a(0, c);
        } else if (k.a().d() == 2) {
            this.d.a(0, c / 1000);
        }
        this.l.k = c;
        this.l.l = c;
        if (com.tencent.liteav.e.g.a().c()) {
            this.l.k = com.tencent.liteav.e.g.a().b(this.l.k);
            this.l.l = this.l.k;
            TXCLog.d("BasicVideoGenerate", "calculateDuration Speed durationUs:" + this.l.k);
        }
        if (this.k.b()) {
            this.l.k += this.k.c();
            TXCLog.d("BasicVideoGenerate", "calculateDuration AddTailWaterMark durationUs:" + this.l.k);
        }
    }

    public void a(boolean z) {
        if (this.f != null) {
            this.f.b(z);
        }
    }
}
