package com.tencent.liteav.d;

import android.annotation.TargetApi;
import android.os.HandlerThread;
import com.tencent.liteav.b.f;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.i.a.h;

@TargetApi(16)
/* compiled from: VideoDecAndDemuxGenerate */
public class s extends d {
    private final String Z;

    public s() {
        this.Z = "VideoDecAndDemuxGenerate";
        this.D = new HandlerThread("video_handler_thread");
        this.D.start();
        this.C = new b(this.D.getLooper());
        this.F = new HandlerThread("audio_handler_thread");
        this.F.start();
        this.E = new a(this.F.getLooper());
    }

    public synchronized void l() {
        if (this.B.get() == 2 || this.B.get() == 3) {
            TXCLog.e("VideoDecAndDemuxGenerate", "start ignore, mCurrentState = " + this.B.get());
        } else {
            this.B.set(2);
            this.T.set(false);
            this.U.getAndSet(false);
            this.x.getAndSet(false);
            this.y.getAndSet(false);
            this.z.getAndSet(false);
            this.A.getAndSet(false);
            this.h = 0;
            h b = f.a().b();
            if (b == null) {
                b(0, 0);
            } else {
                b(b.a * 1000, b.b * 1000);
            }
            a(this.f.get());
            this.C.sendEmptyMessage(1);
            if (h()) {
                this.E.sendEmptyMessage(101);
            }
        }
    }

    public synchronized void m() {
        if (this.B.get() == 1) {
            TXCLog.e("VideoDecAndDemuxGenerate", "stop ignore, mCurrentState is STATE_INIT");
        } else {
            this.B.set(1);
            this.C.sendEmptyMessage(3);
            if (h()) {
                this.E.sendEmptyMessage(103);
            }
        }
    }

    public void a(boolean z) {
    }

    public void k() {
        if (this.D != null) {
            this.D.quit();
        }
        if (this.F != null) {
            this.F.quit();
        }
    }
}
