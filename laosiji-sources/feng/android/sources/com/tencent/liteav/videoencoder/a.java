package com.tencent.liteav.videoencoder;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaCodecInfo;
import android.media.MediaCodecInfo.CodecCapabilities;
import android.media.MediaCodecInfo.CodecProfileLevel;
import android.media.MediaCodecInfo.EncoderCapabilities;
import android.media.MediaCodecList;
import android.media.MediaFormat;
import android.opengl.EGL14;
import android.opengl.EGLContext;
import android.opengl.GLES20;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.Surface;
import com.tencent.ijk.media.player.IjkMediaMeta;
import com.tencent.liteav.basic.c.c;
import com.tencent.liteav.basic.c.d;
import com.tencent.liteav.basic.c.g;
import com.tencent.liteav.basic.c.h;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.tencent.liteav.basic.util.b;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayDeque;
import javax.microedition.khronos.egl.EGL10;

/* compiled from: TXCHWVideoEncoder */
public class a extends c {
    private static final String a = a.class.getSimpleName();
    private boolean A;
    private boolean B;
    private ByteBuffer[] C;
    private byte[] D;
    private volatile long E;
    private long F;
    private long G;
    private int H;
    private int I;
    private boolean J;
    private int K;
    private int L;
    private int M;
    private int N;
    private int b;
    private long c;
    private long d;
    private long e;
    private long f;
    private int g;
    private boolean h;
    private boolean i;
    private long j;
    private long k;
    private long l;
    private long m;
    private long n;
    private boolean o;
    private long p;
    private long q;
    private MediaCodec r;
    private b s;
    private Runnable t;
    private Runnable u;
    private Runnable v;
    private ArrayDeque<Long> w;
    private Object x;
    private Surface y;
    private boolean z;

    public a() {
        this.b = 0;
        this.c = 0;
        this.d = 0;
        this.e = 0;
        this.f = 0;
        this.g = 0;
        this.h = false;
        this.i = true;
        this.j = 0;
        this.k = 0;
        this.l = 0;
        this.m = 0;
        this.n = 0;
        this.p = 0;
        this.q = 0;
        this.r = null;
        this.s = null;
        this.t = new Runnable() {
            public void run() {
                a.this.c();
            }
        };
        this.u = new Runnable() {
            public void run() {
                a.this.b(10);
            }
        };
        this.v = new Runnable() {
            public void run() {
                a.this.b(1);
            }
        };
        this.w = new ArrayDeque(10);
        this.y = null;
        this.z = true;
        this.A = true;
        this.B = false;
        this.C = null;
        this.D = null;
        this.E = 0;
        this.F = 0;
        this.G = 0;
        this.K = 0;
        this.L = 0;
        this.M = 0;
        this.N = -1;
        this.s = new b("HWVideoEncoder");
    }

    public int start(final TXSVideoEncoderParam tXSVideoEncoderParam) {
        super.start(tXSVideoEncoderParam);
        final boolean[] zArr = new boolean[1];
        if (VERSION.SDK_INT < 18) {
            zArr[0] = false;
        } else {
            synchronized (this) {
                this.s.a(new Runnable() {
                    public void run() {
                        if (a.this.mInit) {
                            a.this.c();
                        }
                        zArr[0] = a.this.a(tXSVideoEncoderParam);
                    }
                });
            }
        }
        if (!zArr[0]) {
            callDelegate(10000004);
        }
        if (zArr[0]) {
            return 0;
        }
        return 10000004;
    }

    public void stop() {
        this.A = true;
        synchronized (this) {
            this.s.a(new Runnable() {
                public void run() {
                    if (a.this.mInit) {
                        a.this.c();
                        a.this.s.a().removeCallbacksAndMessages(null);
                    }
                }
            });
        }
    }

    public void setFPS(final int i) {
        this.s.b(new Runnable() {
            public void run() {
                a.this.d(i);
            }
        });
    }

    public void setBitrate(final int i) {
        this.b = i;
        this.s.b(new Runnable() {
            public void run() {
                a.this.c(i);
            }
        });
    }

    public long getRealFPS() {
        return this.d;
    }

    public long getRealBitrate() {
        return this.c;
    }

    public long pushVideoFrame(int i, int i2, int i3, long j) {
        if (this.A) {
            return 10000004;
        }
        GLES20.glFinish();
        this.E = j;
        this.N = i;
        this.K++;
        if (this.J) {
            d();
        }
        this.s.b(this.u);
        return 0;
    }

    public long pushVideoFrameSync(int i, int i2, int i3, long j) {
        if (this.A) {
            return 10000004;
        }
        GLES20.glFinish();
        this.E = j;
        this.N = i;
        this.K++;
        if (this.J) {
            d();
        }
        this.s.a(this.v);
        return 0;
    }

    public void signalEOSAndFlush() {
        if (!this.A) {
            this.s.a(new Runnable() {
                public void run() {
                    if (a.this.r != null) {
                        try {
                            a.this.r.signalEndOfInputStream();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        do {
                        } while (a.this.a(10) >= 0);
                        a.this.c();
                    }
                }
            });
        }
    }

    @TargetApi(16)
    private MediaFormat a(int i, int i2, int i3, int i4, int i5) {
        if (i == 0 || i2 == 0 || i3 == 0 || i4 == 0) {
            return null;
        }
        MediaFormat createVideoFormat = MediaFormat.createVideoFormat("video/avc", i, i2);
        createVideoFormat.setInteger(IjkMediaMeta.IJKM_KEY_BITRATE, i3 * 1024);
        createVideoFormat.setInteger("frame-rate", i4);
        createVideoFormat.setInteger("color-format", 2130708361);
        createVideoFormat.setInteger("i-frame-interval", i5);
        return createVideoFormat;
    }

    @TargetApi(16)
    private MediaFormat a(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        int i8 = 0;
        MediaFormat a = a(i, i2, i3, i4, i5);
        if (a == null) {
            return null;
        }
        if (VERSION.SDK_INT >= 21) {
            MediaCodecInfo a2 = a("video/avc");
            if (a2 == null) {
                return a;
            }
            CodecCapabilities capabilitiesForType = a2.getCapabilitiesForType("video/avc");
            EncoderCapabilities encoderCapabilities = capabilitiesForType.getEncoderCapabilities();
            if (encoderCapabilities.isBitrateModeSupported(i6)) {
                a.setInteger("bitrate-mode", i6);
            } else if (encoderCapabilities.isBitrateModeSupported(2)) {
                a.setInteger("bitrate-mode", 2);
            }
            a.setInteger("complexity", ((Integer) encoderCapabilities.getComplexityRange().clamp(Integer.valueOf(5))).intValue());
            if (VERSION.SDK_INT >= 23) {
                CodecProfileLevel[] codecProfileLevelArr = capabilitiesForType.profileLevels;
                int length = codecProfileLevelArr.length;
                int i9 = 0;
                while (i8 < length) {
                    CodecProfileLevel codecProfileLevel = codecProfileLevelArr[i8];
                    if (codecProfileLevel.profile <= i7 && codecProfileLevel.profile > i9) {
                        i9 = codecProfileLevel.profile;
                        a.setInteger("profile", codecProfileLevel.profile);
                        a.setInteger("level", codecProfileLevel.level);
                    }
                    i8++;
                }
            }
        }
        return a;
    }

    @TargetApi(16)
    private static MediaCodecInfo a(String str) {
        int codecCount = MediaCodecList.getCodecCount();
        for (int i = 0; i < codecCount; i++) {
            MediaCodecInfo codecInfoAt = MediaCodecList.getCodecInfoAt(i);
            if (codecInfoAt.isEncoder()) {
                for (String equalsIgnoreCase : codecInfoAt.getSupportedTypes()) {
                    if (equalsIgnoreCase.equalsIgnoreCase(str)) {
                        return codecInfoAt;
                    }
                }
                continue;
            }
        }
        return null;
    }

    private void a(long j) {
        this.w.add(Long.valueOf(j));
    }

    private long a() {
        Long l = (Long) this.w.poll();
        return l == null ? 0 : l.longValue();
    }

    private boolean a(Surface surface, int i, int i2) {
        if (surface == null) {
            return false;
        }
        if (this.B) {
            EGLContext eGLContext = (EGLContext) this.mGLContextExternal;
            if (eGLContext == null) {
                eGLContext = EGL14.EGL_NO_CONTEXT;
            }
            this.x = c.a(null, eGLContext, surface, i, i2);
        } else {
            javax.microedition.khronos.egl.EGLContext eGLContext2 = (javax.microedition.khronos.egl.EGLContext) this.mGLContextExternal;
            if (eGLContext2 == null) {
                eGLContext2 = EGL10.EGL_NO_CONTEXT;
            }
            this.x = com.tencent.liteav.basic.c.b.a(null, eGLContext2, surface, i, i2);
        }
        if (this.x == null) {
            return false;
        }
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        this.mEncodeFilter = new d();
        this.mEncodeFilter.a(h.e, h.a(g.NORMAL, false, false));
        if (this.mEncodeFilter.c()) {
            GLES20.glViewport(0, 0, i, i2);
            return true;
        }
        this.mEncodeFilter = null;
        return false;
    }

    private void b() {
        if (this.mEncodeFilter != null) {
            this.mEncodeFilter.e();
            this.mEncodeFilter = null;
        }
        if (this.x instanceof com.tencent.liteav.basic.c.b) {
            ((com.tencent.liteav.basic.c.b) this.x).b();
            this.x = null;
        }
        if (this.x instanceof c) {
            ((c) this.x).b();
            this.x = null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:36:0x00d3  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00de  */
    @android.annotation.TargetApi(18)
    private boolean a(com.tencent.liteav.videoencoder.TXSVideoEncoderParam r13) {
        /*
        r12 = this;
        r8 = 2;
        r11 = 0;
        r10 = 0;
        r0 = 0;
        r9 = 1;
        r12.A = r10;
        r12.z = r10;
        r12.c = r0;
        r12.d = r0;
        r12.e = r0;
        r12.f = r0;
        r12.g = r10;
        r12.j = r0;
        r12.k = r0;
        r12.l = r0;
        r12.m = r0;
        r12.n = r0;
        r12.p = r0;
        r12.q = r0;
        r12.C = r11;
        r12.D = r11;
        r12.E = r0;
        r0 = -1;
        r12.H = r0;
        r0 = r13.width;
        r12.mOutputWidth = r0;
        r0 = r13.height;
        r12.mOutputHeight = r0;
        r0 = r13.gop;
        r12.I = r0;
        r0 = r13.fullIFrame;
        r12.J = r0;
        r0 = r13.syncOutput;
        r12.o = r0;
        r0 = r13.enableEGL14;
        r12.B = r0;
        r0 = r12.w;
        r0.clear();
        if (r13 == 0) goto L_0x005a;
    L_0x004a:
        r0 = r13.width;
        if (r0 == 0) goto L_0x005a;
    L_0x004e:
        r0 = r13.height;
        if (r0 == 0) goto L_0x005a;
    L_0x0052:
        r0 = r13.fps;
        if (r0 == 0) goto L_0x005a;
    L_0x0056:
        r0 = r13.gop;
        if (r0 != 0) goto L_0x005d;
    L_0x005a:
        r12.z = r9;
    L_0x005c:
        return r10;
    L_0x005d:
        r0 = r13.annexb;
        r12.h = r0;
        r0 = r13.appendSpsPps;
        r12.i = r0;
        r0 = r12.b;
        if (r0 != 0) goto L_0x0086;
    L_0x0069:
        r0 = r13.width;
        r1 = r13.width;
        r0 = r0 * r1;
        r0 = (double) r0;
        r2 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r0 = r0 * r2;
        r2 = r13.height;
        r3 = r13.height;
        r2 = r2 * r3;
        r2 = (double) r2;
        r0 = r0 + r2;
        r0 = java.lang.Math.sqrt(r0);
        r2 = 4608083138725491507; // 0x3ff3333333333333 float:4.172325E-8 double:1.2;
        r0 = r0 * r2;
        r0 = (int) r0;
        r12.b = r0;
    L_0x0086:
        r0 = r12.b;
        r0 = (long) r0;
        r12.j = r0;
        r0 = r13.fps;
        r12.g = r0;
        r0 = r13.encoderMode;
        switch(r0) {
            case 1: goto L_0x00ea;
            case 2: goto L_0x00ec;
            case 3: goto L_0x00ee;
            default: goto L_0x0094;
        };
    L_0x0094:
        r6 = r8;
    L_0x0095:
        r0 = com.tencent.liteav.basic.d.b.a();
        r0 = r0.b();
        if (r0 != r9) goto L_0x00a1;
    L_0x009f:
        r13.encoderProfile = r9;
    L_0x00a1:
        r0 = r13.encoderProfile;
        switch(r0) {
            case 1: goto L_0x00a6;
            case 2: goto L_0x00a6;
            case 3: goto L_0x00a6;
            default: goto L_0x00a6;
        };
    L_0x00a6:
        r7 = 1;
        r1 = r13.width;	 Catch:{ Exception -> 0x00bc }
        r2 = r13.height;	 Catch:{ Exception -> 0x00bc }
        r3 = r12.b;	 Catch:{ Exception -> 0x00bc }
        r4 = r13.fps;	 Catch:{ Exception -> 0x00bc }
        r5 = r13.gop;	 Catch:{ Exception -> 0x00bc }
        r0 = r12;
        r0 = r0.a(r1, r2, r3, r4, r5, r6, r7);	 Catch:{ Exception -> 0x00bc }
        if (r0 != 0) goto L_0x00f0;
    L_0x00b8:
        r0 = 1;
        r12.z = r0;	 Catch:{ Exception -> 0x00bc }
        goto L_0x005c;
    L_0x00bc:
        r0 = move-exception;
        r8 = r9;
    L_0x00be:
        r0.printStackTrace();
        r0 = 5;
        if (r8 < r0) goto L_0x00cd;
    L_0x00c4:
        r0 = r12.r;
        if (r0 == 0) goto L_0x00cd;
    L_0x00c8:
        r0 = r12.r;
        r0.stop();
    L_0x00cd:
        r12.r = r11;
        r0 = r12.y;
        if (r0 == 0) goto L_0x00d8;
    L_0x00d3:
        r0 = r12.y;
        r0.release();
    L_0x00d8:
        r12.y = r11;
    L_0x00da:
        r0 = r12.r;
        if (r0 == 0) goto L_0x00e6;
    L_0x00de:
        r0 = r12.C;
        if (r0 == 0) goto L_0x00e6;
    L_0x00e2:
        r0 = r12.y;
        if (r0 != 0) goto L_0x0148;
    L_0x00e6:
        r12.z = r9;
        goto L_0x005c;
    L_0x00ea:
        r6 = r8;
        goto L_0x0095;
    L_0x00ec:
        r6 = r9;
        goto L_0x0095;
    L_0x00ee:
        r6 = r10;
        goto L_0x0095;
    L_0x00f0:
        r1 = "video/avc";
        r1 = android.media.MediaCodec.createEncoderByType(r1);	 Catch:{ Exception -> 0x00bc }
        r12.r = r1;	 Catch:{ Exception -> 0x00bc }
        r1 = r12.r;	 Catch:{ Exception -> 0x011c }
        r2 = 0;
        r3 = 0;
        r4 = 1;
        r1.configure(r0, r2, r3, r4);	 Catch:{ Exception -> 0x011c }
    L_0x0101:
        r8 = 3;
        r0 = r12.r;	 Catch:{ Exception -> 0x011a }
        r0 = r0.createInputSurface();	 Catch:{ Exception -> 0x011a }
        r12.y = r0;	 Catch:{ Exception -> 0x011a }
        r8 = 4;
        r0 = r12.r;	 Catch:{ Exception -> 0x011a }
        r0.start();	 Catch:{ Exception -> 0x011a }
        r8 = 5;
        r0 = r12.r;	 Catch:{ Exception -> 0x011a }
        r0 = r0.getOutputBuffers();	 Catch:{ Exception -> 0x011a }
        r12.C = r0;	 Catch:{ Exception -> 0x011a }
        goto L_0x00da;
    L_0x011a:
        r0 = move-exception;
        goto L_0x00be;
    L_0x011c:
        r0 = move-exception;
        r6 = r0;
        r0 = r6 instanceof java.lang.IllegalArgumentException;	 Catch:{ Exception -> 0x011a }
        if (r0 != 0) goto L_0x012c;
    L_0x0122:
        r0 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Exception -> 0x011a }
        r1 = 21;
        if (r0 < r1) goto L_0x0147;
    L_0x0128:
        r0 = r6 instanceof android.media.MediaCodec.CodecException;	 Catch:{ Exception -> 0x011a }
        if (r0 == 0) goto L_0x0147;
    L_0x012c:
        r7 = r12.r;	 Catch:{ Exception -> 0x011a }
        r1 = r13.width;	 Catch:{ Exception -> 0x011a }
        r2 = r13.height;	 Catch:{ Exception -> 0x011a }
        r3 = r12.b;	 Catch:{ Exception -> 0x011a }
        r4 = r13.fps;	 Catch:{ Exception -> 0x011a }
        r5 = r13.gop;	 Catch:{ Exception -> 0x011a }
        r0 = r12;
        r0 = r0.a(r1, r2, r3, r4, r5);	 Catch:{ Exception -> 0x011a }
        r1 = 0;
        r2 = 0;
        r3 = 1;
        r7.configure(r0, r1, r2, r3);	 Catch:{ Exception -> 0x011a }
        r6.printStackTrace();	 Catch:{ Exception -> 0x011a }
        goto L_0x0101;
    L_0x0147:
        throw r6;	 Catch:{ Exception -> 0x011a }
    L_0x0148:
        r0 = r12.y;
        r1 = r13.width;
        r2 = r13.height;
        r0 = r12.a(r0, r1, r2);
        if (r0 != 0) goto L_0x0158;
    L_0x0154:
        r12.z = r9;
        goto L_0x005c;
    L_0x0158:
        r12.mInit = r9;
        r10 = r9;
        goto L_0x005c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.videoencoder.a.a(com.tencent.liteav.videoencoder.TXSVideoEncoderParam):boolean");
    }

    private int a(int i) {
        if (this.r == null) {
            return -1;
        }
        BufferInfo bufferInfo = new BufferInfo();
        try {
            int dequeueOutputBuffer = this.r.dequeueOutputBuffer(bufferInfo, (long) (i * 1000));
            if (dequeueOutputBuffer == -1) {
                return 0;
            }
            if (dequeueOutputBuffer == -3) {
                this.C = this.r.getOutputBuffers();
                return 1;
            } else if (dequeueOutputBuffer == -2) {
                callDelegate(this.r.getOutputFormat());
                return 1;
            } else if (dequeueOutputBuffer < 0) {
                return -1;
            } else {
                int i2;
                ByteBuffer byteBuffer = this.C[dequeueOutputBuffer];
                if (byteBuffer == null) {
                    i2 = -1;
                } else {
                    byte[] bArr;
                    Object bArr2;
                    Object obj = new byte[bufferInfo.size];
                    byteBuffer.position(bufferInfo.offset);
                    byteBuffer.limit(bufferInfo.offset + bufferInfo.size);
                    byteBuffer.get(obj, 0, bufferInfo.size);
                    int length = obj.length;
                    if (bufferInfo.size > 5 && obj[0] == (byte) 0 && obj[1] == (byte) 0 && obj[2] == (byte) 0 && obj[3] == (byte) 0 && obj[4] == (byte) 0 && obj[5] == (byte) 0) {
                        i2 = 3;
                        while (i2 < obj.length - 4) {
                            if (obj[i2] == (byte) 0 && obj[i2 + 1] == (byte) 0 && obj[i2 + 2] == (byte) 0 && obj[i2 + 3] == (byte) 1) {
                                int i3 = length - i2;
                                length = i2;
                                i2 = i3;
                                break;
                            }
                            i2++;
                        }
                        i2 = length;
                        length = 0;
                        Object obj2 = new byte[i2];
                        System.arraycopy(obj, length, obj2, 0, i2);
                        bArr2 = obj2;
                    } else {
                        bArr2 = obj;
                    }
                    if (bufferInfo.size != 0) {
                        int i4 = 1;
                        if ((bufferInfo.flags & 2) == 2) {
                            if (this.h) {
                                this.D = (byte[]) bArr2.clone();
                            } else {
                                this.D = a((byte[]) bArr2.clone());
                            }
                            i2 = 1;
                        } else {
                            byte[] bArr3;
                            if ((bufferInfo.flags & 1) == 1) {
                                i4 = 0;
                                this.H = -1;
                                if (this.h) {
                                    bArr3 = new byte[(this.D.length + bArr2.length)];
                                    System.arraycopy(this.D, 0, bArr3, 0, this.D.length);
                                    System.arraycopy(bArr2, 0, bArr3, this.D.length, bArr2.length);
                                } else {
                                    bArr2 = a(bArr2);
                                    bArr3 = new byte[(this.D.length + bArr2.length)];
                                    System.arraycopy(this.D, 0, bArr3, 0, this.D.length);
                                    System.arraycopy(bArr2, 0, bArr3, this.D.length, bArr2.length);
                                }
                            } else if (this.h) {
                                bArr3 = bArr2;
                            } else {
                                bArr3 = a(bArr2);
                            }
                            if (!this.J) {
                                i2 = this.H + 1;
                                this.H = i2;
                                if (i2 == this.g * this.I) {
                                    d();
                                }
                            }
                            long a = a();
                            long j = bufferInfo.presentationTimeUs / 1000;
                            if (this.G == 0) {
                                this.G = a;
                            }
                            if (this.F == 0) {
                                this.F = j;
                            }
                            long j2 = j + (this.G - this.F);
                            if (a <= this.n) {
                                a = this.n + 1;
                            }
                            if (a > j2) {
                                a = j2;
                            }
                            this.n = a;
                            a = TXCTimeUtil.getTimeTick();
                            if (i4 == 0) {
                                if (a > this.e + 1000) {
                                    this.c = (long) (((((double) this.p) * 8000.0d) / ((double) (a - this.e))) / 1024.0d);
                                    this.p = 0;
                                    this.e = a;
                                }
                                this.k++;
                                this.l = 0;
                            } else {
                                this.l++;
                            }
                            this.p += (long) bArr3.length;
                            if (a > this.f + 2000) {
                                this.d = (long) ((((double) this.q) * 1000.0d) / ((double) (a - this.f)));
                                this.q = 0;
                                this.f = a;
                            }
                            this.q++;
                            byteBuffer.position(bufferInfo.offset);
                            if (this.i) {
                                callDelegate(bArr3, i4, this.k, this.l, this.m, i4 == 0 ? 0 : this.l - 1, j2, j2, 0, byteBuffer, bufferInfo);
                            } else {
                                callDelegate(obj, i4, this.k, this.l, this.m, i4 == 0 ? 0 : this.l - 1, j2, j2, 0, byteBuffer, bufferInfo);
                            }
                            this.m++;
                            this.M++;
                            if ((bufferInfo.flags & 4) != 0) {
                                if (this.mListener != null) {
                                    this.mListener.onEncodeNAL(null, 0);
                                }
                                i2 = -2;
                            } else {
                                i2 = 1;
                            }
                        }
                    } else if ((bufferInfo.flags & 4) != 0) {
                        if (this.mListener != null) {
                            this.mListener.onEncodeNAL(null, 0);
                        }
                        i2 = -2;
                    } else {
                        i2 = -1;
                    }
                }
                try {
                    if (this.r == null) {
                        return i2;
                    }
                    this.r.releaseOutputBuffer(dequeueOutputBuffer, false);
                    return i2;
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                    return i2;
                }
            }
        } catch (IllegalStateException e2) {
            e2.printStackTrace();
            return -1;
        }
    }

    private byte[] a(byte[] bArr) {
        int length = bArr.length;
        byte[] bArr2 = new byte[(length + 20)];
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (i < length) {
            if (bArr[i] == (byte) 0 && bArr[i + 1] == (byte) 0 && bArr[i + 2] == (byte) 1) {
                i3 = a(i, i2, bArr2, bArr, i3);
                i += 3;
                i2 = i;
            } else if (bArr[i] == (byte) 0 && bArr[i + 1] == (byte) 0 && bArr[i + 2] == (byte) 0 && bArr[i + 3] == (byte) 1) {
                i3 = a(i, i2, bArr2, bArr, i3);
                i += 4;
                i2 = i;
            }
            if (i == length - 4 && (bArr[i + 1] != (byte) 0 || bArr[i + 2] != (byte) 0 || bArr[i + 3] != (byte) 1)) {
                i = length;
                break;
            }
            i++;
        }
        int a = a(i, i2, bArr2, bArr, i3);
        Object obj = new byte[a];
        System.arraycopy(bArr2, 0, obj, 0, a);
        return obj;
    }

    private int a(int i, int i2, byte[] bArr, byte[] bArr2, int i3) {
        if (i2 <= 0 || i <= i2) {
            return i3;
        }
        int i4 = i - i2;
        try {
            ByteBuffer wrap = ByteBuffer.wrap(new byte[4]);
            wrap.asIntBuffer().put(i4);
            wrap.order(ByteOrder.BIG_ENDIAN);
            System.arraycopy(wrap.array(), 0, bArr, i3, 4);
            System.arraycopy(bArr2, i2, bArr, i3 + 4, i4);
            return i3 + (i4 + 4);
        } catch (Exception e) {
            e.printStackTrace();
            TXCLog.e(a, "setNalData exception");
            return i3;
        }
    }

    @TargetApi(18)
    private void b(int i) {
        if (!this.z && this.x != null) {
            int a;
            a(this.E);
            this.mEncodeFilter.b(this.N);
            if (this.x instanceof c) {
                ((c) this.x).a(this.E * 1000000);
                ((c) this.x).c();
            }
            if (this.x instanceof com.tencent.liteav.basic.c.b) {
                ((com.tencent.liteav.basic.c.b) this.x).a();
            }
            do {
                a = a(i);
            } while (a > 0);
            if (a == -1 || a == -2) {
                if (a == -1) {
                    callDelegate(10000005);
                }
                this.z = true;
                c();
                return;
            }
            this.L++;
        }
    }

    private void c() {
        if (this.mInit) {
            this.z = true;
            this.A = true;
            b();
            try {
                this.r.stop();
                try {
                    this.r.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (IllegalStateException e2) {
                e2.printStackTrace();
                try {
                    this.r.release();
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            } catch (Throwable th) {
                try {
                    this.r.release();
                } catch (Exception e4) {
                    e4.printStackTrace();
                }
                throw th;
            }
            this.r = null;
            this.N = -1;
            this.c = 0;
            this.d = 0;
            this.e = 0;
            this.f = 0;
            this.g = 0;
            this.j = 0;
            this.k = 0;
            this.l = 0;
            this.m = 0;
            this.n = 0;
            this.p = 0;
            this.q = 0;
            this.mGLContextExternal = null;
            this.C = null;
            this.D = null;
            this.E = 0;
            this.mOutputWidth = 0;
            this.mOutputHeight = 0;
            this.mInit = false;
            this.mListener = null;
            this.w.clear();
        }
    }

    private void c(int i) {
        if (this.j != ((long) this.b)) {
            this.j = (long) this.b;
            if (VERSION.SDK_INT >= 19 && this.r != null) {
                Bundle bundle = new Bundle();
                bundle.putInt("video-bitrate", this.b * 1024);
                this.r.setParameters(bundle);
            }
        }
    }

    private void d() {
        if (VERSION.SDK_INT >= 19 && this.r != null) {
            Bundle bundle = new Bundle();
            bundle.putInt("request-sync", 0);
            this.r.setParameters(bundle);
        }
    }

    private void d(int i) {
    }
}
