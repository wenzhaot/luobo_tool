package com.tencent.liteav.d;

import android.os.HandlerThread;
import com.tencent.liteav.basic.log.TXCLog;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: VideoDecAndDemuxGenerateGivenTimes */
public class t extends d {
    private final String Z;

    public t() {
        this.Z = "VideoDecAndDemuxGenerateGivenTimes";
        this.W = 0;
        this.X = 0;
        this.Y = new AtomicBoolean(true);
        this.V = new ArrayList();
        this.D = new HandlerThread("video_handler_thread");
        this.D.start();
        this.C = new b(this.D.getLooper());
    }

    protected void l() {
        if (this.B.get() == 2) {
            TXCLog.e("VideoDecAndDemuxGenerateGivenTimes", "start ignore, state = " + this.B.get());
            return;
        }
        a(this.f.get());
        this.C.sendEmptyMessage(5);
        this.B.set(2);
    }

    protected void m() {
        if (this.B.get() == 1) {
            TXCLog.e("VideoDecAndDemuxGenerateGivenTimes", "stop ignore, mCurrentState = " + this.B.get());
        } else {
            this.C.sendEmptyMessage(7);
        }
    }

    public void a(boolean z) {
    }

    public synchronized void p() {
        this.Y.set(true);
    }

    public void k() {
        if (this.D != null) {
            this.D.quit();
        }
    }
}
