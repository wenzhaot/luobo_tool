package com.tencent.liteav;

import android.content.Context;
import android.view.Surface;
import android.view.TextureView;
import com.stub.StubApp;
import com.tencent.liteav.basic.b.a;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.rtmp.TXLivePlayer.ITXAudioRawDataListener;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.tencent.ugc.TXRecordCommon.ITXVideoRecordListener;
import java.lang.ref.WeakReference;

/* compiled from: TXIPlayer */
public abstract class h {
    protected c b = null;
    protected Context c = null;
    protected TXCloudVideoView d = null;
    protected WeakReference<a> e;

    public abstract int a(String str, int i);

    public abstract int a(boolean z);

    public abstract void b(int i);

    public abstract void b(boolean z);

    public abstract void c(int i);

    public abstract int d(int i);

    public abstract int i();

    public abstract boolean k();

    public h(Context context) {
        if (context != null) {
            this.c = StubApp.getOrigApplicationContext(context.getApplicationContext());
        }
    }

    public c n() {
        return this.b;
    }

    public void a(c cVar) {
        this.b = cVar;
        if (this.b == null) {
            this.b = new c();
        }
    }

    public void a() {
        TXCLog.w("TXIPlayer", "pause not support");
    }

    public void b() {
        TXCLog.w("TXIPlayer", "resume not support");
    }

    public void a(Surface surface) {
    }

    public void a(int i) {
        TXCLog.w("TXIPlayer", "seek not support");
    }

    public void a(TXCloudVideoView tXCloudVideoView) {
        this.d = tXCloudVideoView;
    }

    public TextureView j() {
        return null;
    }

    public void a(a aVar) {
        this.e = new WeakReference(aVar);
    }

    public void a(ITXVideoRecordListener iTXVideoRecordListener) {
    }

    public void a(ITXAudioRawDataListener iTXAudioRawDataListener) {
    }

    public void c(boolean z) {
        TXCLog.w("TXIPlayer", "autoPlay not implement");
    }

    public void b(float f) {
        TXCLog.w("TXIPlayer", "rate not implement");
    }

    public void a(Context context, int i) {
    }

    public boolean a(byte[] bArr) {
        return false;
    }

    public void a(i iVar) {
    }

    public int a(String str) {
        return -1;
    }

    public void o() {
    }

    public boolean p() {
        return false;
    }
}
