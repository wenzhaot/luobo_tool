package com.tencent.liteav.a;

import android.content.Context;
import android.opengl.EGLContext;
import android.view.Surface;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.beauty.d;
import com.tencent.liteav.c.e;
import com.tencent.liteav.c.g;
import com.tencent.liteav.d.j;
import com.tencent.liteav.d.l;
import com.tencent.ugc.TXRecordCommon;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/* compiled from: TXCombineDecAndRender */
public class c {
    private final j A = new j() {
        public void a(Surface surface) {
            TXCLog.i("TXCombineDecAndRender", "mVideoRenderCallback2 onSurfaceTextureAvailable");
            c.this.d.a(surface);
            c.this.p = new d(c.this.b, true);
        }

        public void a(int i, int i2) {
        }

        public void b(Surface surface) {
            TXCLog.i("TXCombineDecAndRender", "mVideoRenderCallback2 onSurfaceTextureDestroy");
            if (c.this.p != null) {
                c.this.p.a();
                c.this.p = null;
            }
        }

        public int a(int i, float[] fArr, e eVar) {
            int a;
            if (c.this.l) {
                TXCLog.i("TXCombineDecAndRender", "mVideoRenderCallback2 mDecodeVideoEnd, ignore");
            } else if (c.this.a(eVar, false)) {
                TXCLog.i("TXCombineDecAndRender", "mVideoRenderCallback2 onTextureProcess, end frame");
            } else if (c.this.w == null) {
                TXCLog.i("TXCombineDecAndRender", "mVideoRenderCallback2 onTextureProcess, mCurRenderFrame is null, frame pts = " + eVar.e());
                c.this.w = eVar;
                c.this.p.a(fArr);
                a = c.this.p.a(i, eVar.m(), eVar.n(), eVar.h(), 4, 0);
                c.this.v = a;
                com.tencent.liteav.j.e.a(a, eVar.m(), eVar.n());
                TXCLog.i("", "");
            } else {
                c.this.p.a(fArr);
                a = c.this.p.a(i, eVar.m(), eVar.n(), eVar.h(), 4, 0);
                TXCLog.i("TXCombineDecAndRender", "mVideoRenderCallback2 onTextureProcess, mCurRenderFrame is first pts = " + c.this.w.e() + ", process frame pts = " + eVar.e());
                c.this.a(c.this.v, c.this.w, a, eVar);
            }
            return 0;
        }

        public void a(boolean z) {
        }
    };
    private l B = new l() {
        public void a(EGLContext eGLContext) {
            TXCLog.d("TXCombineDecAndRender", "OnContextListener onContext");
            if (c.this.y != null) {
                c.this.y.a(eGLContext);
            }
            c.this.c.h();
            c.this.c.a(c.this.C);
            c.this.d.h();
            c.this.d.a(c.this.D);
            c.this.j.start();
        }
    };
    private com.tencent.liteav.a.a.a C = new com.tencent.liteav.a.a.a() {
        public void b(e eVar) {
            if (!c.this.n) {
                TXCLog.i("TXCombineDecAndRender", "Video1 frame put one:" + eVar.e() + ",VideoBlockingQueue size:" + c.this.f.size());
                try {
                    c.this.f.put(eVar);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (c.this.e != null) {
                    c.this.e.a(eVar, 0);
                }
            }
        }

        public void a(e eVar) {
            if (!c.this.n) {
                if (c.this.g.size() > 3) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                TXCLog.i("TXCombineDecAndRender", "Audio1 frame put one:" + eVar.e() + ", flag = " + eVar.f() + ", AudioBlockingQueue size:" + c.this.g.size());
                try {
                    c.this.g.put(eVar);
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
                c.this.e();
            }
        }
    };
    private com.tencent.liteav.a.a.a D = new com.tencent.liteav.a.a.a() {
        public void b(e eVar) {
            if (!c.this.n) {
                TXCLog.i("TXCombineDecAndRender", "Video2 frame put one:" + eVar.e() + ", flag = " + eVar.f() + ",VideoBlockingQueue2 size:" + c.this.h.size());
                try {
                    c.this.h.put(eVar);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (c.this.e != null) {
                    c.this.e.a(eVar, 1);
                }
            }
        }

        public void a(e eVar) {
            if (!c.this.n) {
                if (c.this.i.size() > 3) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                TXCLog.i("TXCombineDecAndRender", "Audio2 frame put one:" + eVar.e() + ", flag = " + eVar.f() + ",AudioBlockingQueue2 size:" + c.this.i.size());
                try {
                    c.this.i.put(eVar);
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
                c.this.e();
            }
        }
    };
    private final String a = "TXCombineDecAndRender";
    private Context b;
    private h c;
    private h d;
    private i e;
    private ArrayBlockingQueue<e> f;
    private ArrayBlockingQueue<e> g;
    private ArrayBlockingQueue<e> h;
    private ArrayBlockingQueue<e> i;
    private a j;
    private boolean k;
    private boolean l;
    private boolean m;
    private boolean n;
    private d o;
    private d p;
    private e q;
    private b r;
    private int s;
    private int t;
    private LinkedBlockingQueue<e> u;
    private int v = -1;
    private e w;
    private boolean x;
    private com.tencent.liteav.a.a.c y;
    private final j z = new j() {
        public void a(Surface surface) {
            TXCLog.i("TXCombineDecAndRender", "mVideoRenderCallback onSurfaceTextureAvailable");
            c.this.c.a(surface);
            c.this.o = new d(c.this.b, true);
        }

        public void a(int i, int i2) {
        }

        public void b(Surface surface) {
            TXCLog.i("TXCombineDecAndRender", "mVideoRenderCallback onSurfaceTextureDestroy");
            if (c.this.o != null) {
                c.this.o.a();
                c.this.o = null;
            }
            if (c.this.q != null) {
                c.this.q.a();
            }
        }

        public int a(int i, float[] fArr, e eVar) {
            int a;
            if (c.this.l) {
                TXCLog.i("TXCombineDecAndRender", "mVideoRenderCallback mDecodeVideoEnd, ignore");
            } else if (c.this.a(eVar, false)) {
                TXCLog.i("TXCombineDecAndRender", "mVideoRenderCallback onTextureProcess, end frame");
            } else if (c.this.w == null) {
                TXCLog.i("TXCombineDecAndRender", "mVideoRenderCallback onTextureProcess, mCurRenderFrame is null, frame pts = " + eVar.e());
                c.this.w = eVar;
                c.this.o.a(fArr);
                a = c.this.o.a(i, eVar.m(), eVar.n(), eVar.h(), 4, 0);
                c.this.v = a;
                com.tencent.liteav.j.e.a(a, eVar.m(), eVar.n());
                TXCLog.i("", "");
            } else {
                c.this.o.a(fArr);
                a = c.this.o.a(i, eVar.m(), eVar.n(), eVar.h(), 4, 0);
                TXCLog.i("TXCombineDecAndRender", "mVideoRenderCallback onTextureProcess, mCurRenderFrame is second pts = " + c.this.w.e() + ", process frame pts = " + eVar.e());
                c.this.a(a, eVar, c.this.v, c.this.w);
            }
            return 0;
        }

        public void a(boolean z) {
        }
    };

    /* compiled from: TXCombineDecAndRender */
    class a extends Thread {
        a() {
        }

        public void run() {
            setName("DecodeThread");
            try {
                TXCLog.i("TXCombineDecAndRender", "===DecodeThread Start===");
                while (!c.this.k && !c.this.n) {
                    c.this.c.j();
                    c.this.d.j();
                }
                c.this.f.clear();
                c.this.h.clear();
                TXCLog.i("TXCombineDecAndRender", "===DecodeThread Exit===");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public c(Context context) {
        this.b = context;
        this.c = new h();
        this.d = new h();
        this.e = new i(2);
        this.q = new e(this.b);
        this.r = new b();
        this.f = new ArrayBlockingQueue(1);
        this.h = new ArrayBlockingQueue(1);
        this.g = new ArrayBlockingQueue(10);
        this.i = new ArrayBlockingQueue(10);
        this.u = new LinkedBlockingQueue();
        this.c.a(this.f);
        this.d.a(this.h);
        this.c.b(this.g);
        this.d.b(this.i);
    }

    public void a(com.tencent.liteav.a.a.c cVar) {
        this.y = cVar;
    }

    public int a(List<String> list) {
        if (list == null || list.size() < 2) {
            return -1;
        }
        this.c.a((String) list.get(0));
        this.d.a((String) list.get(1));
        this.x = this.c.d() <= this.d.d();
        this.r.a(this.c.a());
        this.r.b(this.d.a());
        this.r.a(a());
        this.r.a();
        return 0;
    }

    public void a(List<com.tencent.liteav.i.a.a> list, int i, int i2) {
        this.q.a(list, i, i2);
        this.s = i;
        this.t = i2;
    }

    public int a() {
        int f = this.c.f();
        int f2 = this.d.f();
        if (f < f2) {
            f = f2;
        }
        return f > 0 ? f : TXRecordCommon.AUDIO_SAMPLERATE_48000;
    }

    public int b() {
        int g = this.c.g();
        int g2 = this.d.g();
        if (g < g2) {
            g = g2;
        }
        return g >= 0 ? g : 10000;
    }

    public void c() {
        g gVar = new g();
        g gVar2 = new g();
        gVar.a = this.c.b();
        gVar.b = this.c.c();
        gVar2.a = this.d.b();
        gVar2.b = this.d.c();
        this.e.a(gVar, 0);
        this.e.a(gVar2, 1);
        this.e.a(this.z, 0);
        this.e.a(this.A, 1);
        this.e.a(this.B);
        this.e.a();
        this.n = false;
        this.k = false;
        this.l = false;
        this.m = false;
        this.j = new a();
    }

    public void d() {
        this.n = true;
        if (this.j != null && this.j.isAlive()) {
            try {
                this.j.join(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (this.c != null) {
            this.c.i();
        }
        if (this.d != null) {
            this.d.i();
        }
        if (this.r != null) {
            this.r.b();
        }
        if (this.e != null) {
            this.e.b();
        }
        this.g.clear();
        this.i.clear();
        this.w = null;
    }

    private void a(int i, e eVar, int i2, e eVar2) {
        int a;
        if (this.x) {
            if (eVar2.e() < eVar.e()) {
                TXCLog.i("TXCombineDecAndRender", "prepareToCombine, mFirstFpsSmall true, secondFrame pts < first pts, drop second, current second queue size = " + this.h.size());
                this.w = eVar;
                this.v = i;
                this.h.remove();
                return;
            }
            TXCLog.i("TXCombineDecAndRender", "prepareToCombine, mFirstFpsSmall true, secondFrame pts >= first pts, to combine");
            a = this.q.a(i, i2, eVar, eVar2);
            if (this.y != null) {
                this.y.a(a, this.s, this.t, eVar);
            }
            this.f.remove();
            this.h.remove();
            TXCLog.i("TXCombineDecAndRender", "prepareToCombine, after combine, remain queue queue2 size = " + this.f.size() + ", " + this.h.size());
            this.v = -1;
            this.w = null;
        } else if (eVar2.e() > eVar.e()) {
            TXCLog.i("TXCombineDecAndRender", "mFirstFpsSmall false, secondFrame pts > first pts, drop first, current first queue size = " + this.f.size());
            this.w = eVar2;
            this.v = i2;
            this.f.remove();
        } else {
            TXCLog.i("TXCombineDecAndRender", "mFirstFpsSmall false, secondFrame pts <= first pts, to combine");
            a = this.q.a(i, i2, eVar, eVar2);
            if (this.y != null) {
                this.y.a(a, this.s, this.t, eVar);
            }
            this.f.remove();
            this.h.remove();
            this.v = -1;
            this.w = null;
        }
    }

    private void e() {
        if (this.g.isEmpty()) {
            TXCLog.i("TXCombineDecAndRender", "combineAudioFrame, mAudioBlockingQueue is empty, ignore");
            return;
        }
        e eVar = (e) this.g.peek();
        if (a(eVar, true)) {
            TXCLog.i("TXCombineDecAndRender", "combineAudioFrame, frame1 is end");
        } else if (this.i.isEmpty()) {
            TXCLog.i("TXCombineDecAndRender", "combineAudioFrame, mAudioBlockingQueue2 is empty, ignore");
        } else {
            e eVar2 = (e) this.i.peek();
            if (a(eVar2, true)) {
                TXCLog.i("TXCombineDecAndRender", "combineAudioFrame, frame2 is end");
                return;
            }
            try {
                eVar = (e) this.g.take();
                eVar2 = (e) this.i.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            TXCLog.i("TXCombineDecAndRender", "===combineAudioFrame===after take, size1:" + this.g.size() + ",size2:" + this.i.size());
            eVar = this.r.a(eVar, eVar2);
            if (this.y != null) {
                this.y.a(eVar);
            }
        }
    }

    private boolean a(e eVar, boolean z) {
        if (!eVar.p()) {
            return false;
        }
        if (this.y == null) {
            return true;
        }
        if (z) {
            TXCLog.i("TXCombineDecAndRender", "===judgeDecodeComplete=== audio end");
            this.m = true;
            this.g.clear();
            this.i.clear();
            this.y.c(eVar);
        } else {
            TXCLog.i("TXCombineDecAndRender", "===judgeDecodeComplete=== video end");
            this.l = true;
            this.y.b(eVar);
        }
        if (!this.m || !this.l) {
            return true;
        }
        TXCLog.i("TXCombineDecAndRender", "judgeDecodeComplete, video and audio both end");
        this.k = true;
        d();
        return true;
    }
}
