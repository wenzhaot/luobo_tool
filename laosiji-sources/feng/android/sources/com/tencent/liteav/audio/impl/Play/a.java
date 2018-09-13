package com.tencent.liteav.audio.impl.Play;

import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaFormat;
import com.tencent.liteav.audio.TXEAudioDef;
import com.tencent.liteav.audio.d;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/* compiled from: TXCAudioHWDecoder */
public class a implements Runnable {
    private static final String a = ("AudioCenter:" + a.class.getSimpleName());
    private WeakReference<d> b = null;
    private MediaCodec c = null;
    private BufferInfo d;
    private MediaFormat e;
    private long f = 0;
    private volatile boolean g = false;
    private Vector<com.tencent.liteav.basic.e.a> h;
    private List i;
    private Thread j = null;

    public void a(WeakReference<d> weakReference) {
        if (this.g) {
            b();
        }
        this.b = weakReference;
        this.f = 0;
        this.h = new Vector();
        this.i = new ArrayList();
        this.g = true;
        this.j = new Thread(this);
        this.j.setName(a);
        this.j.start();
    }

    public void a(com.tencent.liteav.basic.e.a aVar) {
        if (this.g) {
            synchronized (this.h) {
                if (this.h != null) {
                    this.h.add(aVar);
                }
            }
        }
    }

    public long a() {
        if (this.e == null) {
            return 0;
        }
        float integer = (float) this.e.getInteger("sample-rate");
        if (integer != 0.0f) {
            return (long) (((((float) this.i.size()) * 1024.0f) * 1000.0f) / integer);
        }
        return 0;
    }

    public void b() {
        this.g = false;
        if (this.j != null) {
            try {
                this.j.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.j = null;
        }
    }

    public void run() {
        int i;
        Object e;
        while (this.g) {
            boolean isEmpty;
            synchronized (this.h) {
                isEmpty = this.h.isEmpty();
            }
            if (isEmpty) {
                try {
                    Thread.sleep(10);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } else {
                ByteBuffer[] byteBufferArr;
                com.tencent.liteav.basic.e.a aVar;
                if (this.c != null) {
                    try {
                        ByteBuffer[] inputBuffers = this.c.getInputBuffers();
                        try {
                            int dequeueInputBuffer = this.c.dequeueInputBuffer(10000);
                            if (dequeueInputBuffer >= 0) {
                                byteBufferArr = inputBuffers;
                                i = dequeueInputBuffer;
                            } else {
                                return;
                            }
                        } catch (Exception e3) {
                            e = e3;
                            i = 1;
                        }
                    } catch (Exception e4) {
                        e = e4;
                        i = 0;
                    }
                } else {
                    i = -1;
                    byteBufferArr = null;
                }
                synchronized (this.h) {
                    aVar = (com.tencent.liteav.basic.e.a) this.h.remove(0);
                }
                if (aVar.d == com.tencent.liteav.basic.a.a.k) {
                    b(aVar);
                } else if (aVar.d == com.tencent.liteav.basic.a.a.l) {
                    this.i.add(new Long(aVar.e));
                    a(aVar, byteBufferArr, i);
                } else {
                    TXCLog.e(a, "not support audio format");
                }
            }
        }
        if (this.c != null) {
            this.c.stop();
            this.c.release();
            this.c = null;
            return;
        }
        return;
        TXCLog.e(a, "Exception. step: " + i + ", error: " + e);
    }

    /* JADX WARNING: Removed duplicated region for block: B:55:0x01b9  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x00c8  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x00dc  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x00c8  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x01b9  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x00dc  */
    private int b(com.tencent.liteav.basic.e.a r10) {
        /*
        r9 = this;
        r7 = 2;
        r8 = 0;
        r3 = 1;
        r1 = 0;
        r0 = r10.f;
        r0 = r0.length;
        if (r0 == r7) goto L_0x0025;
    L_0x0009:
        r0 = a;
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r4 = "aac seq header len not equal to 2 , with len ";
        r2 = r2.append(r4);
        r4 = r10.f;
        r4 = r4.length;
        r2 = r2.append(r4);
        r2 = r2.toString();
        com.tencent.liteav.basic.log.TXCLog.w(r0, r2);
    L_0x0025:
        r0 = r10.f;
        if (r0 == 0) goto L_0x0210;
    L_0x0029:
        r0 = r10.f;
        r0 = r0[r1];
        r0 = r0 & 248;
        r0 = r0 >> 3;
        r0 = r10.f;
        r0 = r0[r1];
        r0 = r0 & 7;
        r0 = r0 << 1;
        r2 = r10.f;
        r2 = r2[r3];
        r2 = r2 & 128;
        r2 = r2 >> 7;
        r0 = r0 | r2;
        r2 = com.tencent.liteav.audio.impl.a.a(r0);
        r0 = r10.f;
        r0 = r0[r3];
        r0 = r0 & 120;
        r4 = r0 >> 3;
        r0 = "audio/mp4a-latm";
        r0 = android.media.MediaFormat.createAudioFormat(r0, r2, r4);
        r9.e = r0;
        r0 = r9.e;
        r5 = "bitrate";
        r6 = 64000; // 0xfa00 float:8.9683E-41 double:3.162E-319;
        r0.setInteger(r5, r6);
        r0 = r9.e;
        r5 = "is-adts";
        r0.setInteger(r5, r1);
        r0 = r9.e;
        r5 = "aac-profile";
        r0.setInteger(r5, r7);
        r0 = a;
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r6 = "audio decoder media format: ";
        r5 = r5.append(r6);
        r6 = r9.e;
        r5 = r5.append(r6);
        r5 = r5.toString();
        com.tencent.liteav.basic.log.TXCLog.i(r0, r5);
        r0 = r9.b;
        if (r0 == 0) goto L_0x00ab;
    L_0x0091:
        r0 = r9.b;
        r0 = r0.get();
        r0 = (com.tencent.liteav.audio.d) r0;
        r5 = new com.tencent.liteav.basic.e.a;
        r5.<init>();
        r6 = com.tencent.liteav.basic.a.a.h;
        r5.c = r6;
        r5.b = r4;
        r5.a = r2;
        if (r0 == 0) goto L_0x00ab;
    L_0x00a8:
        r0.onPlayAudioInfoChanged(r5, r5);
    L_0x00ab:
        r0 = r9.c;
        if (r0 == 0) goto L_0x00b9;
    L_0x00af:
        r0 = r9.c;	 Catch:{ Exception -> 0x00f0 }
        r0.stop();	 Catch:{ Exception -> 0x00f0 }
        r0 = r9.c;	 Catch:{ Exception -> 0x021b }
        r0.release();	 Catch:{ Exception -> 0x021b }
    L_0x00b9:
        r0 = "audio/mp4a-latm";
        r0 = android.media.MediaCodec.createDecoderByType(r0);	 Catch:{ IOException -> 0x0117 }
        r9.c = r0;	 Catch:{ IOException -> 0x0117 }
    L_0x00c2:
        r0 = android.os.Build.VERSION.SDK_INT;
        r2 = 21;
        if (r0 < r2) goto L_0x01b9;
    L_0x00c8:
        r0 = r1;
    L_0x00c9:
        r2 = r9.c;	 Catch:{ CodecException -> 0x0139 }
        r4 = r9.e;	 Catch:{ CodecException -> 0x0139 }
        r5 = 0;
        r6 = 0;
        r7 = 0;
        r2.configure(r4, r5, r6, r7);	 Catch:{ CodecException -> 0x0139 }
        r2 = r9.c;	 Catch:{ CodecException -> 0x0217 }
        r2.start();	 Catch:{ CodecException -> 0x0217 }
    L_0x00d8:
        r0 = r9.c;
        if (r0 == 0) goto L_0x00ed;
    L_0x00dc:
        r0 = r9.c;
        r0 = r0.getInputBuffers();
        r1 = r9.c;
        r2 = 10000; // 0x2710 float:1.4013E-41 double:4.9407E-320;
        r1 = r1.dequeueInputBuffer(r2);
        r9.a(r10, r0, r1);
    L_0x00ed:
        r0 = com.tencent.liteav.audio.TXEAudioDef.TXE_AUDIO_PLAY_ERR_OK;
    L_0x00ef:
        return r0;
    L_0x00f0:
        r0 = move-exception;
        r2 = r1;
    L_0x00f2:
        r4 = a;
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r6 = "hw audio decoder release error: ";
        r5 = r5.append(r6);
        r2 = r5.append(r2);
        r5 = ". error: ";
        r2 = r2.append(r5);
        r0 = r2.append(r0);
        r0 = r0.toString();
        com.tencent.liteav.basic.log.TXCLog.e(r4, r0);
        goto L_0x00b9;
    L_0x0117:
        r0 = move-exception;
        r0.printStackTrace();
        r2 = a;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = "createDecoderByType exception: ";
        r4 = r4.append(r5);
        r0 = r0.getMessage();
        r0 = r4.append(r0);
        r0 = r0.toString();
        com.tencent.liteav.basic.log.TXCLog.e(r2, r0);
        goto L_0x00c2;
    L_0x0139:
        r2 = move-exception;
        r4 = r1;
    L_0x013b:
        r5 = a;
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r7 = "CodecException: ";
        r6 = r6.append(r7);
        r6 = r6.append(r2);
        r7 = ". step: ";
        r6 = r6.append(r7);
        r4 = r6.append(r4);
        r6 = ", mediaformat: ";
        r4 = r4.append(r6);
        r6 = r9.e;
        r4 = r4.append(r6);
        r4 = r4.toString();
        com.tencent.liteav.basic.log.TXCLog.e(r5, r4);
        r0 = r0 + 1;
        if (r0 <= r3) goto L_0x0183;
    L_0x0170:
        r0 = a;
        r1 = "decoder start error!";
        com.tencent.liteav.basic.log.TXCLog.e(r0, r1);
        r0 = r9.c;
        r0.release();
        r9.c = r8;
        r0 = com.tencent.liteav.audio.TXEAudioDef.TXE_AUDIO_PLAY_ERR_INVALID_STATE;
        goto L_0x00ef;
    L_0x0183:
        r4 = r2.isRecoverable();
        if (r4 == 0) goto L_0x0193;
    L_0x0189:
        r2 = r9.c;	 Catch:{ Exception -> 0x0190 }
        r2.stop();	 Catch:{ Exception -> 0x0190 }
        goto L_0x00c9;
    L_0x0190:
        r2 = move-exception;
        goto L_0x00c9;
    L_0x0193:
        r2 = r2.isTransient();
        if (r2 == 0) goto L_0x01a6;
    L_0x0199:
        r4 = 20;
        java.lang.Thread.sleep(r4);	 Catch:{ InterruptedException -> 0x01a0 }
        goto L_0x00c9;
    L_0x01a0:
        r2 = move-exception;
        r2.printStackTrace();
        goto L_0x00c9;
    L_0x01a6:
        r0 = a;
        r1 = "decoder cath unrecoverable error!";
        com.tencent.liteav.basic.log.TXCLog.e(r0, r1);
        r0 = r9.c;
        r0.release();
        r9.c = r8;
        r0 = com.tencent.liteav.audio.TXEAudioDef.TXE_AUDIO_PLAY_ERR_INVALID_STATE;
        goto L_0x00ef;
    L_0x01b9:
        r0 = r1;
    L_0x01ba:
        r2 = r9.c;	 Catch:{ IllegalStateException -> 0x0214 }
        r4 = r9.e;	 Catch:{ IllegalStateException -> 0x0214 }
        r5 = 0;
        r6 = 0;
        r7 = 0;
        r2.configure(r4, r5, r6, r7);	 Catch:{ IllegalStateException -> 0x0214 }
        r2 = r9.c;	 Catch:{ IllegalStateException -> 0x01cb }
        r2.start();	 Catch:{ IllegalStateException -> 0x01cb }
        goto L_0x00d8;
    L_0x01cb:
        r2 = move-exception;
        r4 = r3;
    L_0x01cd:
        r5 = a;
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r7 = "CodecException1: ";
        r6 = r6.append(r7);
        r2 = r6.append(r2);
        r6 = ". step: ";
        r2 = r2.append(r6);
        r2 = r2.append(r4);
        r2 = r2.toString();
        com.tencent.liteav.basic.log.TXCLog.e(r5, r2);
        r0 = r0 + 1;
        if (r0 <= r3) goto L_0x0208;
    L_0x01f5:
        r0 = a;
        r1 = "decoder start error!";
        com.tencent.liteav.basic.log.TXCLog.e(r0, r1);
        r0 = r9.c;
        r0.release();
        r9.c = r8;
        r0 = com.tencent.liteav.audio.TXEAudioDef.TXE_AUDIO_PLAY_ERR_INVALID_STATE;
        goto L_0x00ef;
    L_0x0208:
        r2 = r9.c;	 Catch:{ Exception -> 0x020e }
        r2.reset();	 Catch:{ Exception -> 0x020e }
        goto L_0x01ba;
    L_0x020e:
        r2 = move-exception;
        goto L_0x01ba;
    L_0x0210:
        r0 = com.tencent.liteav.audio.TXEAudioDef.TXE_AUDIO_COMMON_ERR_INVALID_PARAMS;
        goto L_0x00ef;
    L_0x0214:
        r2 = move-exception;
        r4 = r1;
        goto L_0x01cd;
    L_0x0217:
        r2 = move-exception;
        r4 = r3;
        goto L_0x013b;
    L_0x021b:
        r0 = move-exception;
        r2 = r3;
        goto L_0x00f2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.audio.impl.Play.a.b(com.tencent.liteav.basic.e.a):int");
    }

    private int a(com.tencent.liteav.basic.e.a aVar, ByteBuffer[] byteBufferArr, int i) {
        ByteBuffer byteBuffer;
        if (i >= 0) {
            try {
                if (aVar.f != null) {
                    byteBuffer = byteBufferArr[i];
                    byteBuffer.clear();
                    byteBuffer.put(aVar.f);
                }
                if (aVar == null || aVar.f.length <= 0) {
                    this.c.queueInputBuffer(i, 0, 0, c(), 4);
                } else {
                    this.c.queueInputBuffer(i, 0, aVar.f.length, c(), 0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (i == -1) {
            return -1;
        }
        ByteBuffer[] outputBuffers = this.c.getOutputBuffers();
        if (this.d == null) {
            this.d = new BufferInfo();
        }
        while (true) {
            ByteBuffer[] outputBuffers2;
            int dequeueOutputBuffer = this.c.dequeueOutputBuffer(this.d, 10000);
            if (dequeueOutputBuffer == -3) {
                outputBuffers2 = this.c.getOutputBuffers();
            } else {
                d dVar;
                if (dequeueOutputBuffer == -2) {
                    this.e = this.c.getOutputFormat();
                    if (this.b != null) {
                        dVar = (d) this.b.get();
                        com.tencent.liteav.basic.e.a aVar2 = new com.tencent.liteav.basic.e.a();
                        aVar2.c = com.tencent.liteav.basic.a.a.h;
                        aVar2.b = this.e.getInteger("channel-count");
                        aVar2.a = this.e.getInteger("sample-rate");
                        if (dVar != null) {
                            dVar.onPlayAudioInfoChanged(aVar2, aVar2);
                        }
                        outputBuffers2 = outputBuffers;
                    }
                } else if (dequeueOutputBuffer >= 0) {
                    byteBuffer = outputBuffers[dequeueOutputBuffer];
                    byteBuffer.position(this.d.offset);
                    byteBuffer.limit(this.d.offset + this.d.size);
                    byte[] bArr = new byte[this.d.size];
                    byteBuffer.get(bArr);
                    long longValue = ((Long) this.i.get(0)).longValue();
                    this.i.remove(0);
                    if (this.b != null) {
                        dVar = (d) this.b.get();
                        if (dVar != null) {
                            dVar.onPlayPcmData(bArr, longValue);
                        }
                    }
                    this.c.releaseOutputBuffer(dequeueOutputBuffer, false);
                }
                outputBuffers2 = outputBuffers;
            }
            if (dequeueOutputBuffer < 0) {
                break;
            }
            outputBuffers = outputBuffers2;
        }
        return TXEAudioDef.TXE_AUDIO_PLAY_ERR_OK;
    }

    private long c() {
        long timeTick = TXCTimeUtil.getTimeTick();
        if (timeTick < this.f) {
            timeTick += this.f - timeTick;
        }
        this.f = timeTick;
        return timeTick;
    }
}
