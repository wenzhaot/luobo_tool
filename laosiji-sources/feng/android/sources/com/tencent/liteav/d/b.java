package com.tencent.liteav.d;

import android.media.AudioTrack;
import android.media.MediaFormat;
import android.os.Build.VERSION;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.c.e;
import java.lang.ref.WeakReference;
import java.util.concurrent.LinkedBlockingDeque;

/* compiled from: AudioTrackRender */
public class b {
    private volatile AudioTrack a;
    private volatile e b;
    private LinkedBlockingDeque<e> c = new LinkedBlockingDeque();
    private b d;
    private volatile a e;
    private int f;
    private int g;

    /* compiled from: AudioTrackRender */
    public interface a {
        void a(int i);
    }

    /* compiled from: AudioTrackRender */
    private static class b extends Thread {
        private WeakReference<b> a;

        public b(b bVar) {
            super("PlayPCMThread for Video Editer");
            this.a = new WeakReference(bVar);
        }

        public void run() {
            super.run();
            while (!isInterrupted()) {
                try {
                    e b = b();
                    if (b != null) {
                        a(b);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
        }

        private void a(e eVar) {
            c();
            ((b) this.a.get()).b(eVar);
        }

        private e b() throws InterruptedException {
            c();
            return (e) ((b) this.a.get()).c.peek();
        }

        private void c() {
            if (this.a.get() == null) {
                throw new RuntimeException("can't reach the object: AudioTrackRender");
            }
        }

        public void a() {
            interrupt();
            this.a.clear();
            this.a = null;
        }
    }

    private void b(e eVar) {
        if (this.b == null) {
            this.b = eVar;
        }
        if (eVar.f() == 4) {
            e();
            return;
        }
        byte[] array = eVar.b().array();
        int remaining = eVar.b().remaining();
        if (remaining != 0) {
            try {
                if (this.a != null && this.a.getPlayState() == 3) {
                    this.a.write(array, eVar.b().arrayOffset(), remaining);
                    this.c.remove();
                    if (this.e != null) {
                        this.e.a(this.c.size());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.b = eVar;
    }

    public void a(a aVar) {
        this.e = aVar;
    }

    public void a() {
        try {
            if (this.a != null) {
                this.a.pause();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void b() {
        try {
            if (this.a != null && this.a.getPlayState() != 3) {
                this.a.play();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void c() {
        a(this.g, this.f);
    }

    public void a(MediaFormat mediaFormat) {
        if (mediaFormat == null) {
            e();
        } else if (VERSION.SDK_INT >= 16) {
            int integer = mediaFormat.getInteger("sample-rate");
            int integer2 = mediaFormat.getInteger("channel-count");
            if (!(this.f == integer && this.g == integer2)) {
                e();
            }
            this.f = integer;
            this.g = integer2;
            TXCLog.i("AudioTrackRender", "setAudioFormat sampleRate=" + integer + ",channelCount=" + integer2);
        }
    }

    public void a(e eVar) {
        if (this.d == null || !this.d.isAlive() || this.d.isInterrupted()) {
            this.d = new b(this);
            this.d.start();
        }
        this.c.add(eVar);
        if (this.e != null) {
            this.e.a(this.c.size());
        }
    }

    private boolean a(int i, int i2) {
        int i3 = 4;
        if (i != 1) {
            if (i == 2 || i == 3) {
                i3 = 12;
            } else if (i == 4 || i == 5) {
                i3 = 204;
            } else if (i == 6 || i == 7) {
                i3 = 252;
            } else if (i == 8) {
                i3 = 6396;
            } else {
                i3 = 0;
            }
        }
        if (this.a != null) {
            return false;
        }
        int minBufferSize = AudioTrack.getMinBufferSize(i2, i3, 2);
        try {
            this.a = new AudioTrack(3, i2, i3, 2, minBufferSize, 1);
            this.a.play();
            return false;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            TXCLog.e("AudioTrackRender", "new AudioTrack IllegalArgumentException: " + e + ", sampleRate: " + i2 + ", channelType: " + i3 + ", minBufferLen: " + minBufferSize);
            this.a = null;
            return true;
        } catch (IllegalStateException e2) {
            e2.printStackTrace();
            TXCLog.e("AudioTrackRender", "new AudioTrack IllegalArgumentException: " + e2 + ", sampleRate: " + i2 + ", channelType: " + i3 + ", minBufferLen: " + minBufferSize);
            if (this.a != null) {
                this.a.release();
            }
            this.a = null;
            return true;
        }
    }

    private void e() {
        try {
            if (this.a != null) {
                this.a.stop();
                this.a.release();
            }
            this.a = null;
        } catch (Exception e) {
            this.a = null;
            TXCLog.e("AudioTrackRender", "audio track stop exception: " + e);
        }
    }

    public void d() {
        this.c.clear();
        if (this.d != null) {
            this.d.a();
            this.d = null;
        }
        TXCLog.d("AudioTrackRender", "mPlayPCMThread:" + this.d);
        this.b = null;
        e();
    }
}
