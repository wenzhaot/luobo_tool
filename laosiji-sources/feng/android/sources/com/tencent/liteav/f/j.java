package com.tencent.liteav.f;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.MediaFormat;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.tencent.ijk.media.player.IjkMediaMeta;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.c.e;
import com.tencent.liteav.d.b.a;
import com.tencent.liteav.f.a.b;
import com.tencent.liteav.f.a.f;
import com.tencent.liteav.f.a.h;
import com.tencent.liteav.videoediter.audio.d;
import com.tencent.ugc.TXVideoEditConstants;
import java.io.IOException;

@TargetApi(16)
/* compiled from: TXJoinerPreviewer */
public class j implements SurfaceTextureListener, a, b {
    private Context a;
    private String b;
    private long c = -1;
    private long d = -1;
    private float e = -1.0f;
    private float f = -1.0f;
    private float g = 1.0f;
    private MediaFormat h;
    private MediaFormat i;
    private MediaFormat j;
    private int k;
    private FrameLayout l;
    private Surface m;
    private int n;
    private int o;
    private String p;
    private o q;
    private com.tencent.liteav.d.b r;
    private d s;
    private q t;
    private p u;
    private Handler v = new Handler(Looper.getMainLooper());
    private Runnable w = new Runnable() {
        public void run() {
            if (j.this.m == null) {
                j.this.v.postDelayed(j.this.w, 10);
            } else {
                j.this.g();
            }
        }
    };
    private Surface x;
    private boolean y = false;

    static {
        com.tencent.liteav.basic.util.a.d();
    }

    public void a(boolean z) {
        this.t.a(z);
    }

    public void a(f fVar) {
        this.t.a(fVar);
    }

    public j(Context context) {
        this.a = context;
        this.t = new q();
        this.t.a(new h() {
            public void a() {
                j.this.r.d();
            }
        });
        this.t.a((b) this);
        this.u = new p(context);
        this.r = new com.tencent.liteav.d.b();
        this.q = o.a();
    }

    public int a(String str) throws IOException {
        TXCLog.d("TXJoinerPreviewer", "setVideoPath: " + str);
        int a = this.t.a(str);
        this.p = str;
        if (a == TXVideoEditConstants.ERR_UNSUPPORT_LARGE_RESOLUTION || a == 0) {
            this.i = this.t.a();
            e();
        }
        return a;
    }

    private void e() {
        int i = 0;
        if (this.i != null) {
            int integer;
            int i2;
            if (this.j == null) {
                integer = this.i.getInteger("sample-rate");
                i2 = 1;
            } else {
                i2 = this.i.getInteger("sample-rate");
                integer = this.j.getInteger("sample-rate");
                if (i2 <= integer) {
                    i2 = integer;
                }
                integer = this.h.getInteger("sample-rate");
                if (i2 != integer) {
                    TXCLog.i("TXJoinerPreviewer", "initTargetAudioFormat: sample rate change : from " + integer + " to " + i2);
                    integer = i2;
                    i2 = 0;
                    i = 1;
                } else {
                    integer = i2;
                    i2 = 0;
                }
            }
            if (i2 != 0 || i != 0) {
                if (this.t != null && this.t.i() == 2) {
                    this.t.g();
                }
                this.h = MediaFormat.createAudioFormat("audio/mp4a-latm", integer, 1);
                if (this.i.containsKey(IjkMediaMeta.IJKM_KEY_BITRATE)) {
                    this.h.setInteger(IjkMediaMeta.IJKM_KEY_BITRATE, this.i.getInteger(IjkMediaMeta.IJKM_KEY_BITRATE));
                }
                if (this.s == null) {
                    f();
                } else {
                    this.s.a(this.h);
                }
                if (this.r != null) {
                    this.r.a(this.h);
                }
                if (this.t != null && this.t.i() == 3) {
                    this.t.h();
                }
            }
        }
    }

    public int b(String str) throws IOException {
        TXCLog.d("TXJoinerPreviewer", "addSourcePath: " + str);
        return this.t.a(str);
    }

    public void b(int i) {
        TXCLog.d("TXJoinerPreviewer", "setCurrentType: " + i);
        this.t.a(i);
    }

    public synchronized void a() {
        TXCLog.d("TXJoinerPreviewer", "startPreview");
        if (this.m == null) {
            this.v.postDelayed(this.w, 10);
        } else {
            g();
        }
    }

    private void f() {
        if (this.s == null) {
            this.s = new d();
            if (this.h != null) {
                this.s.a(this.h);
            }
            if (this.e != -1.0f) {
                this.s.c(this.e);
            }
            if (this.f != -1.0f) {
                this.s.b(this.f);
            }
            if (!(((float) this.c) == -1.0f || ((float) this.d) == -1.0f)) {
                this.s.a(this.c, this.d);
            }
            if (!TextUtils.isEmpty(this.b)) {
                this.s.a(this.b);
            }
            this.s.a(this.g);
        }
    }

    private synchronized void g() {
        TXCLog.i("TXJoinerPreviewer", "startPreviewImpl");
        f();
        this.r.a((a) this);
        this.r.c();
        if (this.y) {
            h();
        }
        b(false);
        if (this.x != null) {
            this.t.a(this.x);
        }
        this.t.b(true);
        this.t.c(true);
        this.t.k();
    }

    private void b(boolean z) {
        this.u.a(this.m);
        this.u.a(this.n, this.o);
        this.u.a(this.t);
        this.u.a(true);
        this.u.b(z);
        this.u.a();
        int i = this.t.i();
        if (i == 2) {
            this.t.f();
        }
        this.x = this.u.c(i == 4);
        this.y = true;
    }

    public synchronized void b() {
        TXCLog.d("TXJoinerPreviewer", "MediaComposer stop");
        this.v.removeCallbacks(this.w);
        this.r.a(null);
        this.r.d();
        h();
        this.u.a(this.k);
        if (this.s != null) {
            this.s.b();
            this.s = null;
        }
    }

    private void h() {
        this.t.f();
        this.u.b();
        this.u.a(null);
        this.u = new p(this.a);
        this.y = false;
    }

    public void a(com.tencent.liteav.i.a.f fVar) {
        if (this.l != null) {
            this.l.removeAllViews();
        }
        this.l = fVar.a;
        View textureView = new TextureView(this.a);
        textureView.setSurfaceTextureListener(this);
        textureView.setLayoutParams(new LayoutParams(-2, -2));
        this.l.addView(textureView);
        this.k = fVar.b;
        this.u.a(fVar.b);
    }

    public void a(int i) {
        this.t.c(i <= 5);
    }

    public void a(e eVar) {
        c(eVar);
        if (this.s != null) {
            try {
                eVar = this.s.a(eVar);
            } catch (IllegalArgumentException e) {
                this.s = null;
                f();
                eVar = this.s.a(eVar);
            }
            if (eVar == null) {
                return;
            }
        }
        if (this.g != 1.0f) {
            do {
                this.r.a(eVar);
                eVar = this.s.a();
            } while (eVar != null);
            return;
        }
        this.r.a(eVar);
    }

    private void c(e eVar) {
        a(com.tencent.liteav.j.d.a(eVar.e(), o.a().b()));
    }

    private void a(float f) {
        if (this.g != f) {
            Log.i("TXJoinerPreviewer", "setSpeedLevel: speed change , new speed =  " + f);
            this.g = f;
            if (this.s != null) {
                this.s.a(f);
            }
        }
    }

    public void b(e eVar) {
        this.u.a(eVar);
    }

    public void a(long j, long j2) {
        TXCLog.d("TXJoinerPreviewer", "setCutFromTime startTime=" + (j * 1000) + ",duration=" + (j2 * 1000));
        this.t.a(j * 1000, 1000 * j2);
    }

    public synchronized void c() {
        TXCLog.i("TXJoinerPreviewer", "===pausePlay===");
        this.r.a();
        this.t.g();
    }

    public synchronized void d() {
        TXCLog.i("TXJoinerPreviewer", "===resumePlay===");
        this.r.b();
        this.t.h();
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        this.m = new Surface(surfaceTexture);
        this.n = i;
        this.o = i2;
        this.u.a(this.n, this.o);
        this.u.a(this.m);
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
        this.n = i;
        this.o = i2;
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        this.m = null;
        return true;
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        if (this.m == null) {
            this.m = new Surface(surfaceTexture);
        }
    }
}
