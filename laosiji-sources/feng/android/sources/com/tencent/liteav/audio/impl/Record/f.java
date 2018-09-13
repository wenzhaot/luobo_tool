package com.tencent.liteav.audio.impl.Record;

import android.content.Context;
import android.media.AudioManager;
import android.media.AudioRecord;
import com.tencent.liteav.audio.TXEAudioDef;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.tencent.ugc.TXRecordCommon;
import java.lang.ref.WeakReference;

/* compiled from: TXCAudioSysRecord */
public class f implements Runnable {
    private static final String a = ("AudioCenter:" + f.class.getSimpleName());
    private static f b = null;
    private Context c;
    private int d = TXRecordCommon.AUDIO_SAMPLERATE_48000;
    private int e = 1;
    private int f = 16;
    private int g = TXEAudioDef.TXE_AEC_NONE;
    private AudioRecord h;
    private byte[] i = null;
    private WeakReference<g> j;
    private Thread k = null;
    private boolean l = false;

    public static f a() {
        if (b == null) {
            synchronized (f.class) {
                if (b == null) {
                    b = new f();
                }
            }
        }
        return b;
    }

    private f() {
    }

    public synchronized void a(g gVar) {
        if (gVar == null) {
            this.j = null;
        } else {
            this.j = new WeakReference(gVar);
        }
    }

    public void a(Context context, int i, int i2, int i3, int i4) {
        b();
        this.c = context;
        this.d = i;
        this.e = i2;
        this.f = i3;
        this.g = i4;
        this.l = true;
        this.k = new Thread(this, "AudioSysRecord Thread");
        this.k.start();
    }

    public void b() {
        this.l = false;
        long currentTimeMillis = System.currentTimeMillis();
        if (!(this.k == null || !this.k.isAlive() || Thread.currentThread().getId() == this.k.getId())) {
            try {
                this.k.join();
            } catch (Exception e) {
                e.printStackTrace();
                TXCLog.e(a, "record stop Exception: " + e.getMessage());
            }
        }
        TXCLog.i(a, "stop record cost time(MS): " + (System.currentTimeMillis() - currentTimeMillis));
        this.k = null;
    }

    public synchronized boolean c() {
        return this.l;
    }

    private void d() {
        int i;
        int i2;
        int i3 = this.d;
        int i4 = this.e;
        int i5 = this.f;
        int i6 = this.g;
        TXCLog.i(a, String.format("audio record sampleRate = %d, channels = %d, bits = %d, aectype = %d", new Object[]{Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5), Integer.valueOf(i6)}));
        if (i4 == 1) {
            i = 2;
        } else {
            i = 3;
        }
        if (i5 == 8) {
            i2 = 3;
        } else {
            i2 = 2;
        }
        int minBufferSize = AudioRecord.getMinBufferSize(i3, i, i2);
        try {
            if (i6 == TXEAudioDef.TXE_AEC_SYSTEM) {
                TXCLog.i(a, "audio record type: system aec");
                if (this.c != null) {
                    ((AudioManager) this.c.getSystemService("audio")).setMode(3);
                }
                this.h = new AudioRecord(7, i3, i, i2, minBufferSize * 2);
                if (this.c != null) {
                    ((AudioManager) this.c.getSystemService("audio")).setMode(0);
                }
            } else {
                TXCLog.i(a, "audio record type: system normal");
                this.h = new AudioRecord(1, i3, i, i2, minBufferSize * 2);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        if (this.h == null || this.h.getState() != 1) {
            TXCLog.e(a, "audio record: initialize the mic failed.");
            e();
            a(TXEAudioDef.TXE_AUDIO_RECORD_ERR_NO_MIC_PERMIT, "open mic failed!");
            return;
        }
        i6 = ((i4 * 1024) * i5) / 8;
        if (i6 > minBufferSize) {
            this.i = new byte[minBufferSize];
        } else {
            this.i = new byte[i6];
        }
        TXCLog.i(a, String.format("audio record: mic open rate=%dHZ, channels=%d, bits=%d, buffer=%d/%d, state=%d", new Object[]{Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5), Integer.valueOf(minBufferSize), Integer.valueOf(this.i.length), Integer.valueOf(this.h.getState())}));
        if (this.h != null) {
            try {
                this.h.startRecording();
            } catch (Exception e2) {
                e2.printStackTrace();
                TXCLog.e(a, "mic startRecording failed.");
                a(TXEAudioDef.TXE_AUDIO_RECORD_ERR_NO_MIC_PERMIT, "start recording failed!");
            }
        }
    }

    private void e() {
        if (this.h != null) {
            TXCLog.i(a, "stop mic");
            try {
                this.h.setRecordPositionUpdateListener(null);
                this.h.stop();
                this.h.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.h = null;
        this.i = null;
    }

    private void a(byte[] bArr, int i, long j) {
        g gVar = null;
        synchronized (this) {
            if (this.j != null) {
                gVar = (g) this.j.get();
            }
        }
        if (gVar != null) {
            gVar.onAudioRecordPCM(bArr, i, j);
        } else {
            TXCLog.e(a, "onRecordPcmData:no callback");
        }
    }

    private void a(int i, String str) {
        g gVar = null;
        synchronized (this) {
            if (this.j != null) {
                gVar = (g) this.j.get();
            }
        }
        if (gVar != null) {
            gVar.onAudioRecordError(i, str);
        } else {
            TXCLog.e(a, "onRecordError:no callback");
        }
    }

    private void f() {
        g gVar = null;
        synchronized (this) {
            if (this.j != null) {
                gVar = (g) this.j.get();
            }
        }
        if (gVar != null) {
            gVar.onAudioRecordStart();
        } else {
            TXCLog.e(a, "onRecordStart:no callback");
        }
    }

    private void g() {
        g gVar = null;
        synchronized (this) {
            if (this.j != null) {
                gVar = (g) this.j.get();
            }
        }
        if (gVar != null) {
            gVar.onAudioRecordStop();
        } else {
            TXCLog.e(a, "onRecordStop:no callback");
        }
    }

    public void run() {
        if (this.l) {
            f();
            d();
            int i = 0;
            int i2 = 0;
            while (this.l && !Thread.interrupted() && this.h != null && i2 <= 5) {
                System.currentTimeMillis();
                int read = this.h.read(this.i, i, this.i.length - i);
                if (read == this.i.length - i) {
                    a(this.i, this.i.length, TXCTimeUtil.getTimeTick());
                    i = 0;
                    i2 = 0;
                } else if (read <= 0) {
                    TXCLog.e(a, "read pcm eror, len =" + read);
                    i2++;
                } else {
                    i += read;
                }
            }
            e();
            if (i2 > 5) {
                a(TXEAudioDef.TXE_AUDIO_RECORD_ERR_NO_MIC_PERMIT, "read data failed!");
                return;
            } else {
                g();
                return;
            }
        }
        TXCLog.w(a, "audio record: abandom start audio sys record thread!");
    }
}
