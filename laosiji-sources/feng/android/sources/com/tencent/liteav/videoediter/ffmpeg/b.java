package com.tencent.liteav.videoediter.ffmpeg;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.text.TextUtils;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.videoediter.ffmpeg.jni.TXFFQuickJointerJNI;
import java.io.File;
import java.io.IOException;
import java.util.List;

/* compiled from: TXQuickJoiner */
public class b implements com.tencent.liteav.videoediter.ffmpeg.jni.TXFFQuickJointerJNI.a {
    private volatile boolean a;
    private volatile boolean b;
    private volatile boolean c;
    private volatile boolean d;
    private TXFFQuickJointerJNI e = new TXFFQuickJointerJNI();
    private Handler f;
    private Handler g;
    private HandlerThread h;
    private volatile a i;

    /* compiled from: TXQuickJoiner */
    public interface a {
        void a(b bVar, float f);

        void a(b bVar, int i, String str);
    }

    public b() {
        this.e.setOnJoinerCallback(this);
        this.f = new Handler(Looper.getMainLooper());
        this.d = false;
    }

    private void g() {
        if (this.h == null || !this.h.isAlive() || this.h.isInterrupted()) {
            this.h = new HandlerThread("Quick Jointer Thread");
            this.h.start();
            this.g = new Handler(this.h.getLooper());
        }
    }

    private void h() {
        if (this.h != null) {
            this.g.post(new Runnable() {
                public void run() {
                    b.this.h.quit();
                    b.this.h = null;
                    b.this.g.removeCallbacksAndMessages(null);
                    b.this.g = null;
                }
            });
        }
    }

    public int a(String str) {
        if (this.c) {
            TXCLog.e("TXFFQuickJointerWrapper", "quick jointer is started, you must stop first!");
            return -1;
        } else if (TextUtils.isEmpty(str)) {
            TXCLog.e("TXFFQuickJointerWrapper", "quick jointer setDstPath empty！！！");
            return -1;
        } else {
            File file = new File(str);
            if (file.exists()) {
                file.delete();
            }
            try {
                if (file.createNewFile()) {
                    this.e.setDstPath(str);
                    this.a = !TextUtils.isEmpty(str);
                    return 0;
                }
                this.a = false;
                return -1;
            } catch (IOException e) {
                e.printStackTrace();
                this.a = false;
                return -1;
            }
        }
    }

    public boolean a() {
        return this.e.verify() == 0;
    }

    public int a(List<String> list) {
        if (this.c) {
            TXCLog.e("TXFFQuickJointerWrapper", "qucik jointer is started, you must stop frist!");
            return -1;
        }
        this.e.setSrcPaths(list);
        this.b = true;
        return 0;
    }

    public int b() {
        if (!this.b || !this.a) {
            return -1;
        }
        if (this.c) {
            TXCLog.e("TXFFQuickJointerWrapper", "qucik jointer is started, you must stop frist!");
            return -1;
        }
        g();
        this.g.post(new Runnable() {
            public void run() {
                b.this.c = true;
                if (b.this.e.verify() != 0) {
                    b.this.a(-1, "不符合快速合成的要求");
                    return;
                }
                int start = b.this.e.start();
                if (b.this.c) {
                    if (start < 0) {
                        b.this.a(-2, "合成失败");
                    } else {
                        b.this.a(0, "合成成功");
                    }
                    b.this.c = false;
                    return;
                }
                b.this.a(1, "取消合成");
            }
        });
        return 0;
    }

    public int c() {
        if (!this.c) {
            return -1;
        }
        this.e.stop();
        h();
        this.c = false;
        return 0;
    }

    public void d() {
        if (!this.d) {
            c();
            this.e.setOnJoinerCallback(null);
            this.e.destroy();
            this.i = null;
            this.e = null;
            this.d = true;
        }
    }

    public int e() {
        if (this.e != null) {
            return this.e.getVideoWidth();
        }
        return 0;
    }

    public int f() {
        if (this.e != null) {
            return this.e.getVideoHeight();
        }
        return 0;
    }

    private void a(final int i, final String str) {
        if (this.i != null) {
            this.f.post(new Runnable() {
                public void run() {
                    if (b.this.i != null) {
                        b.this.i.a(b.this, i, str);
                    }
                }
            });
        }
    }

    public void a(a aVar) {
        this.i = aVar;
    }

    public void a(final float f) {
        if (this.i != null) {
            this.f.post(new Runnable() {
                public void run() {
                    if (b.this.i != null) {
                        b.this.i.a(b.this, f);
                    }
                }
            });
        }
    }
}
