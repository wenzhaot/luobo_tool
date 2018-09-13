package com.tencent.liteav.f;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaFormat;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.view.Surface;
import com.tencent.ijk.media.player.IjkMediaMeta;
import com.tencent.ijk.media.player.misc.IMediaFormat;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.c.e;
import com.tencent.liteav.d.k;
import com.tencent.liteav.muxer.c;
import com.tencent.ugc.TXRecordCommon;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

@TargetApi(16)
/* compiled from: VideoSourceWriter */
public class r implements com.tencent.liteav.f.a.a {
    private boolean a = false;
    private n b;
    private f c;
    private c d;
    private int e = -1;
    private a f;
    private int g = TXRecordCommon.AUDIO_SAMPLERATE_44100;
    private int h = 98304;
    private int i = 1;
    private int j = 20;
    private int k = 3;
    private Object l = new Object();
    private int m = 960;
    private int n = 544;
    private int o = 5120000;
    private long p = 0;
    private a.c q;
    private String r;
    private float s = 1.0f;
    private long t = 0;
    private long u = 0;
    private long v = 0;

    /* compiled from: VideoSourceWriter */
    class a extends Handler {
        public a(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    r.this.c((e) message.obj);
                    return;
                case 2:
                    r.this.d((e) message.obj);
                    return;
                default:
                    return;
            }
        }
    }

    public r(Context context) {
        this.d = new c(context, 2);
    }

    public void a(a.c cVar) {
        this.q = cVar;
    }

    public void a(long j) {
        TXCLog.d("VideoSourceWriter", "setDuration: " + j);
        this.p = j;
    }

    @TargetApi(16)
    public void b(MediaFormat mediaFormat) {
        int i = 0;
        TXCLog.d("VideoSourceWriter", "setInputAudioFormat: " + mediaFormat);
        if (mediaFormat == null) {
            TXCLog.w("VideoSourceWriter", "input audio format is null");
            return;
        }
        int integer;
        f();
        int integer2 = mediaFormat.containsKey("sample-rate") ? mediaFormat.getInteger("sample-rate") : 0;
        if (mediaFormat.containsKey("channel-count")) {
            integer = mediaFormat.getInteger("channel-count");
        } else {
            integer = 0;
        }
        if (mediaFormat.containsKey(IjkMediaMeta.IJKM_KEY_BITRATE)) {
            i = mediaFormat.getInteger(IjkMediaMeta.IJKM_KEY_BITRATE);
        }
        if (integer2 > 0) {
            this.g = integer2;
        }
        if (integer > 0) {
            this.i = integer;
        }
        if (i > 0) {
            this.h = i;
        }
    }

    @TargetApi(16)
    public void c(MediaFormat mediaFormat) {
        int i = 0;
        TXCLog.d("VideoSourceWriter", "setInputVideoFormat: " + mediaFormat);
        if (mediaFormat == null) {
            TXCLog.w("VideoSourceWriter", "input video format is null");
            return;
        }
        int integer;
        int integer2;
        e();
        int integer3 = mediaFormat.containsKey("frame-rate") ? mediaFormat.getInteger("frame-rate") : 0;
        if (mediaFormat.containsKey("width")) {
            integer = mediaFormat.getInteger("width");
        } else {
            integer = 0;
        }
        if (mediaFormat.containsKey("height")) {
            integer2 = mediaFormat.getInteger("height");
        } else {
            integer2 = 0;
        }
        if (mediaFormat.containsKey(IjkMediaMeta.IJKM_KEY_BITRATE)) {
            i = mediaFormat.getInteger(IjkMediaMeta.IJKM_KEY_BITRATE);
        }
        if (integer3 > 0) {
            this.j = integer3;
        }
        if (integer > 0) {
            this.m = integer;
        }
        if (integer2 > 0) {
            this.n = integer2;
        }
        if (i > 0) {
            this.o = i;
        }
    }

    public void a(int i) {
        this.j = i;
    }

    public void b(String str) {
        this.r = str;
        try {
            File file = new File(str);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        TXCLog.d("VideoSourceWriter", "setTargetPath: " + str);
        this.d.a(str);
    }

    public void b(int i) {
        TXCLog.d("VideoSourceWriter", "setVideoCompressed: " + i);
        this.e = i;
    }

    private void e() {
        if (this.b == null) {
            this.b = new n();
        }
    }

    private void f() {
        if (this.c == null) {
            this.c = new f();
        }
    }

    private void c(int i) {
        if (this.m <= 0 || this.n <= 0) {
            TXCLog.w("VideoSourceWriter", "no input size. " + this.m + "*" + this.n);
            return;
        }
        if (i == 0) {
            this.o = 1228800;
        } else if (i == 1) {
            this.o = 1228800;
        } else if (i == 2) {
            this.o = 2457600;
        } else if (i == 3) {
            this.o = 3686400;
        }
        int[] a = com.tencent.liteav.j.c.a(i, this.m, this.n);
        this.m = a[0];
        this.n = a[1];
        TXCLog.d("VideoSourceWriter", "target size: " + this.m + "*" + this.n + ", bitrate:" + this.o);
    }

    public int a() {
        if (this.e >= 0) {
            c(this.e);
        }
        return this.m;
    }

    public int b() {
        if (this.e >= 0) {
            c(this.e);
        }
        return this.n;
    }

    public void c() {
        TXCLog.d("VideoSourceWriter", "start");
        if (this.b != null) {
            if (this.e >= 0) {
                c(this.e);
            }
            this.b.a(this.o);
            this.b.a(this.m, this.n);
            this.b.a((com.tencent.liteav.f.a.a) this);
            this.b.b(this.j);
            this.b.c(this.k);
            if (!(this.b.a() == 0 || this.q == null)) {
                this.q.a(-1, "start video encoder error!");
            }
        }
        if (this.c != null) {
            this.c.a((com.tencent.liteav.f.a.a) this);
            this.c.a(this.h);
            this.c.c(this.i);
            this.c.b(this.g);
            if (!(this.c.a() == 0 || this.q == null)) {
                this.q.a(-1, "start audio encoder error!");
            }
        }
        HandlerThread handlerThread = new HandlerThread("VideoSourceWriter thread");
        handlerThread.start();
        this.f = new a(handlerThread.getLooper());
        this.a = true;
    }

    public void a(boolean z) {
        if (this.a) {
            this.a = false;
            TXCLog.i("VideoSourceWriter", "stopInternal, isCancel = " + z);
            if (this.f != null) {
                this.f.removeMessages(1);
                this.f.removeMessages(2);
                this.f.getLooper().quit();
                this.f = null;
            }
            if (this.b != null) {
                this.b.a(z);
            }
            if (this.c != null) {
                this.c.a(z);
            }
        }
    }

    public Surface d() {
        if (this.b != null) {
            return this.b.b();
        }
        return null;
    }

    public void a(e eVar) {
        if (this.f != null && this.b != null) {
            synchronized (this.l) {
                if (this.f.sendMessage(Message.obtain(this.f, 1, eVar))) {
                    try {
                        this.l.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void b(e eVar) {
        if (this.f != null && this.c != null) {
            synchronized (this.l) {
                if (this.f.sendMessage(Message.obtain(this.f, 2, eVar))) {
                    try {
                        this.l.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void c(e eVar) {
        try {
            this.b.a(eVar);
            synchronized (this.l) {
                this.l.notifyAll();
            }
        } catch (Throwable th) {
            synchronized (this.l) {
                this.l.notifyAll();
            }
        }
    }

    private void d(e eVar) {
        try {
            this.c.a(eVar);
            synchronized (this.l) {
                this.l.notifyAll();
            }
        } catch (Throwable th) {
            synchronized (this.l) {
                this.l.notifyAll();
            }
        }
    }

    public void a(MediaFormat mediaFormat) {
        TXCLog.d("VideoSourceWriter", "onOutputFormatChanged: " + mediaFormat);
        String str = null;
        if (VERSION.SDK_INT >= 16) {
            str = mediaFormat.getString(IMediaFormat.KEY_MIME);
        }
        if (str != null && str.startsWith("video")) {
            this.d.a(mediaFormat);
        } else if (str != null && str.startsWith("audio")) {
            this.d.b(a(this.g, this.i, 2));
        }
        if (this.d.d() && this.d.c()) {
            int a = this.d.a();
            if (a < 0 && this.q != null) {
                str = "";
                switch (a) {
                    case -4:
                        str = "create MediaMuxer error!";
                        break;
                    case -3:
                        str = "audio track not set yet!";
                        break;
                    case -2:
                        str = "video track not set yet!";
                        break;
                    case -1:
                        str = "target path not set yet!";
                        break;
                }
                this.q.a(-1, str);
            }
        }
    }

    private int d(int i) {
        int[] iArr = new int[]{96000, 88200, 64000, TXRecordCommon.AUDIO_SAMPLERATE_48000, TXRecordCommon.AUDIO_SAMPLERATE_44100, TXRecordCommon.AUDIO_SAMPLERATE_32000, 24000, 22050, TXRecordCommon.AUDIO_SAMPLERATE_16000, 12000, 11025, TXRecordCommon.AUDIO_SAMPLERATE_8000, 7350};
        for (int i2 = 0; i2 < 13; i2++) {
            if (iArr[i2] == i) {
                return i2;
            }
        }
        return -1;
    }

    @TargetApi(16)
    private MediaFormat a(int i, int i2, int i3) {
        int d = d(i);
        ByteBuffer allocate = ByteBuffer.allocate(2);
        allocate.put(0, (byte) ((i3 << 3) | (d >> 1)));
        allocate.put(1, (byte) (((d & 1) << 7) | (i2 << 3)));
        MediaFormat createAudioFormat = MediaFormat.createAudioFormat("audio/mp4a-latm", i, i2);
        createAudioFormat.setInteger("channel-count", i2);
        createAudioFormat.setInteger("sample-rate", i);
        createAudioFormat.setByteBuffer("csd-0", allocate);
        return createAudioFormat;
    }

    public void a(String str, ByteBuffer byteBuffer, BufferInfo bufferInfo) {
        float f = 1.0f;
        if (str.startsWith("video")) {
            this.d.b(byteBuffer, bufferInfo);
            if (this.q != null) {
                float f2 = 0.0f;
                if (this.p > 0) {
                    f2 = (((float) bufferInfo.presentationTimeUs) * 1.0f) / ((float) this.p);
                }
                if (f2 <= 1.0f) {
                    f = f2;
                }
                this.q.a(f);
            }
        } else if (str.startsWith("audio")) {
            this.d.a(byteBuffer, bufferInfo);
        }
    }

    public void a(String str) {
        TXCLog.d("VideoSourceWriter", "onFinish");
        if (str == null || !str.startsWith("video")) {
            this.c.a(null);
            return;
        }
        this.b.a(null);
        this.d.b();
        if (this.d.e() == 1) {
            k.a().b();
        }
        if (this.q != null) {
            this.q.a(0, "");
        }
    }
}
