package com.tencent.liteav.audio.impl.Play;

import android.content.Context;
import android.media.AudioTrack;
import com.tencent.liteav.audio.TXEAudioDef;
import com.tencent.liteav.basic.log.TXCLog;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/* compiled from: TXCMultAudioTrackPlayer */
public class d {
    static d a = new d();
    private static final String b = ("AudioCenter:" + d.class.getSimpleName());
    private a c;
    private boolean d = false;
    private volatile boolean e = false;
    private Context f = null;
    private int g = TXEAudioDef.TXE_AUDIO_MODE_SPEAKER;
    private volatile boolean h = false;
    private int i = com.tencent.liteav.basic.a.a.e;
    private int j = com.tencent.liteav.basic.a.a.g;
    private int k = com.tencent.liteav.basic.a.a.h;

    /* compiled from: TXCMultAudioTrackPlayer */
    class a extends Thread {
        volatile boolean b = false;

        public a(String str) {
            super(str);
        }

        public void a() {
            this.b = true;
        }

        public void b() {
            this.b = false;
        }
    }

    private d() {
    }

    public static d a() {
        return a;
    }

    public void b() {
        TXCLog.w(b, "mult-track-player start!");
        if (this.e) {
            TXCLog.e(b, "mult-track-player can not start because of has started!");
        } else if (this.i == 0 || this.j == 0) {
            TXCLog.e(b, "strat mult-track-player failed with invalid audio info , samplerate:" + this.i + ", channels:" + this.j);
        } else {
            this.e = true;
            if (this.c == null) {
                this.c = new a("AUDIO_TRACK") {
                    public void run() {
                        int i = 3;
                        a();
                        try {
                            int i2;
                            if (d.this.j == 1) {
                                i2 = 2;
                            } else {
                                i2 = 3;
                            }
                            if (d.this.k != 8) {
                                i = 2;
                            }
                            AudioTrack audioTrack = new AudioTrack(3, d.this.i, i2, i, AudioTrack.getMinBufferSize(d.this.i, i2, i), 1);
                            TXCLog.i(d.b, "create audio track, samplerate:" + d.this.i + ", channels:" + d.this.j + ", bits:" + d.this.k);
                            try {
                                audioTrack.play();
                                d.this.h = true;
                                d.this.a(d.this.f, d.this.g);
                                int i3 = 100;
                                int i4 = 0;
                                while (this.b) {
                                    byte[] nativeGetMixedTracksData = TXCAudioBasePlayController.nativeGetMixedTracksData(d.this.j * 2048);
                                    if (nativeGetMixedTracksData == null || nativeGetMixedTracksData.length <= 0) {
                                        try {
                                            AnonymousClass1.sleep(5);
                                        } catch (InterruptedException e) {
                                        }
                                    } else {
                                        if (d.this.d) {
                                            Arrays.fill(nativeGetMixedTracksData, (byte) 0);
                                        }
                                        if (i3 != 0 && i4 < 800) {
                                            short[] sArr = new short[(nativeGetMixedTracksData.length / 2)];
                                            ByteBuffer.wrap(nativeGetMixedTracksData).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(sArr);
                                            for (i2 = 0; i2 < sArr.length; i2++) {
                                                sArr[i2] = (short) (sArr[i2] / i3);
                                            }
                                            ByteBuffer.wrap(nativeGetMixedTracksData).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().put(sArr);
                                            i4 += nativeGetMixedTracksData.length / ((d.this.i * 2) / 1000);
                                            i3 = (i3 * (800 - i4)) / 800;
                                        }
                                        audioTrack.write(nativeGetMixedTracksData, 0, nativeGetMixedTracksData.length);
                                    }
                                }
                                try {
                                    audioTrack.pause();
                                    audioTrack.flush();
                                    audioTrack.stop();
                                    audioTrack.release();
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                }
                                TXCLog.e(d.b, "mult-player thread stop finish!");
                            } catch (Exception e22) {
                                e22.printStackTrace();
                            }
                        } catch (Exception e222) {
                            e222.printStackTrace();
                        }
                    }
                };
                this.c.start();
            }
            TXCLog.w(b, "mult-track-player thread start finish!");
        }
    }

    public void c() {
        TXCLog.w(b, "mult-track-player stop!");
        if (this.e) {
            if (this.c != null) {
                this.c.b();
                this.c = null;
            }
            this.g = TXEAudioDef.TXE_AUDIO_MODE_SPEAKER;
            this.f = null;
            this.h = false;
            this.e = false;
            TXCLog.w(b, "mult-track-player stop finish!");
            return;
        }
        TXCLog.w(b, "mult-track-player can not stop because of not started yet!");
    }

    public synchronized void a(Context context, int i) {
        this.f = context;
        this.g = i;
        if (this.h) {
            com.tencent.liteav.audio.a.a(context, i);
        }
    }
}
