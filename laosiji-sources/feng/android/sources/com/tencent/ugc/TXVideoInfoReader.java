package com.tencent.ugc;

import android.graphics.Bitmap;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.f.k;
import com.tencent.ugc.TXVideoEditConstants.TXVideoInfo;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicInteger;

public class TXVideoInfoReader {
    private static final int RETRY_MAX_COUNT = 3;
    private static TXVideoInfoReader sInstance;
    private String TAG = TXVideoInfoReader.class.getSimpleName();
    private int mCount;
    private a mGenerateImageThread;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private long mImageVideoDuration;
    private volatile WeakReference<OnSampleProgrocess> mListener;
    private AtomicInteger mRetryGeneThreadTimes = new AtomicInteger(0);
    private String mVideoPath;

    public interface OnSampleProgrocess {
        void sampleProcess(int i, Bitmap bitmap);
    }

    class a extends Thread {
        private k b;
        private String c;
        private long d;
        private volatile Bitmap e;
        private int f;

        public a(String str) {
            this.f = TXVideoInfoReader.this.mListener.hashCode();
            this.c = str;
        }

        /* JADX WARNING: Removed duplicated region for block: B:28:0x012f  */
        public void run() {
            /*
            r11 = this;
            r4 = 1;
            r1 = 0;
            r0 = new com.tencent.liteav.f.k;
            r0.<init>();
            r11.b = r0;
            r0 = r11.b;
            r2 = r11.c;
            r0.a(r2);
            r0 = r11.b;
            r2 = r0.a();
            r6 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
            r2 = r2 * r6;
            r11.d = r2;
            r2 = r11.d;
            r0 = com.tencent.ugc.TXVideoInfoReader.this;
            r0 = r0.mCount;
            r6 = (long) r0;
            r6 = r2 / r6;
            r0 = com.tencent.ugc.TXVideoInfoReader.this;
            r0 = r0.TAG;
            r2 = "run duration = %s ";
            r3 = new java.lang.Object[r4];
            r8 = r11.d;
            r5 = java.lang.Long.valueOf(r8);
            r3[r1] = r5;
            r2 = java.lang.String.format(r2, r3);
            com.tencent.liteav.basic.log.TXCLog.i(r0, r2);
            r0 = com.tencent.ugc.TXVideoInfoReader.this;
            r0 = r0.TAG;
            r2 = "run count = %s ";
            r3 = new java.lang.Object[r4];
            r5 = com.tencent.ugc.TXVideoInfoReader.this;
            r5 = r5.mCount;
            r5 = java.lang.Integer.valueOf(r5);
            r3[r1] = r5;
            r2 = java.lang.String.format(r2, r3);
            com.tencent.liteav.basic.log.TXCLog.i(r0, r2);
            r0 = r1;
        L_0x005f:
            r2 = com.tencent.ugc.TXVideoInfoReader.this;
            r2 = r2.mCount;
            if (r0 >= r2) goto L_0x00f2;
        L_0x0067:
            r2 = java.lang.Thread.currentThread();
            r2 = r2.isInterrupted();
            if (r2 != 0) goto L_0x00f2;
        L_0x0071:
            r2 = (long) r0;
            r2 = r2 * r6;
            r8 = r11.d;
            r5 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1));
            if (r5 <= 0) goto L_0x007b;
        L_0x0079:
            r2 = r11.d;
        L_0x007b:
            r5 = com.tencent.ugc.TXVideoInfoReader.this;
            r5 = r5.TAG;
            r8 = "current frame time = %s";
            r9 = new java.lang.Object[r4];
            r10 = java.lang.Long.valueOf(r2);
            r9[r1] = r10;
            r8 = java.lang.String.format(r8, r9);
            com.tencent.liteav.basic.log.TXCLog.i(r5, r8);
            r5 = r11.b;
            r3 = r5.a(r2);
            r2 = com.tencent.ugc.TXVideoInfoReader.this;
            r5 = r2.TAG;
            r8 = "the %s of bitmap is null ? %s";
            r2 = 2;
            r9 = new java.lang.Object[r2];
            r2 = java.lang.Integer.valueOf(r0);
            r9[r1] = r2;
            if (r3 != 0) goto L_0x00fb;
        L_0x00ad:
            r2 = r4;
        L_0x00ae:
            r2 = java.lang.Boolean.valueOf(r2);
            r9[r4] = r2;
            r2 = java.lang.String.format(r8, r9);
            com.tencent.liteav.basic.log.TXCLog.i(r5, r2);
            if (r3 != 0) goto L_0x0174;
        L_0x00bd:
            r2 = com.tencent.ugc.TXVideoInfoReader.this;
            r2 = r2.TAG;
            r5 = "getSampleImages failed!!!";
            com.tencent.liteav.basic.log.TXCLog.d(r2, r5);
            if (r0 != 0) goto L_0x00fd;
        L_0x00cb:
            r0 = com.tencent.ugc.TXVideoInfoReader.this;
            r0 = r0.mRetryGeneThreadTimes;
            r0 = r0.get();
            r1 = 3;
            if (r0 >= r1) goto L_0x00f2;
        L_0x00d8:
            r0 = com.tencent.ugc.TXVideoInfoReader.this;
            r0 = r0.TAG;
            r1 = "retry to get sample images";
            com.tencent.liteav.basic.log.TXCLog.d(r0, r1);
            r0 = com.tencent.ugc.TXVideoInfoReader.this;
            r0 = r0.mHandler;
            r1 = new com.tencent.ugc.TXVideoInfoReader$a$1;
            r1.<init>();
            r0.post(r1);
        L_0x00f2:
            r0 = 0;
            r11.e = r0;
            r0 = r11.b;
            r0.j();
            return;
        L_0x00fb:
            r2 = r1;
            goto L_0x00ae;
        L_0x00fd:
            r2 = r11.e;
            if (r2 == 0) goto L_0x0174;
        L_0x0101:
            r2 = r11.e;
            r2 = r2.isRecycled();
            if (r2 != 0) goto L_0x0174;
        L_0x0109:
            r2 = com.tencent.ugc.TXVideoInfoReader.this;
            r2 = r2.TAG;
            r3 = "copy last image";
            com.tencent.liteav.basic.log.TXCLog.d(r2, r3);
            r2 = r11.e;
            r3 = r11.e;
            r3 = r3.getConfig();
            r2 = r2.copy(r3, r4);
        L_0x0121:
            r11.e = r2;
            r3 = com.tencent.ugc.TXVideoInfoReader.this;
            r3 = r3.mRetryGeneThreadTimes;
            r3 = r3.get();
            if (r3 == 0) goto L_0x0138;
        L_0x012f:
            r3 = com.tencent.ugc.TXVideoInfoReader.this;
            r3 = r3.mRetryGeneThreadTimes;
            r3.getAndSet(r1);
        L_0x0138:
            r3 = com.tencent.ugc.TXVideoInfoReader.this;
            r3 = r3.mListener;
            if (r3 == 0) goto L_0x0170;
        L_0x0140:
            r3 = com.tencent.ugc.TXVideoInfoReader.this;
            r3 = r3.mListener;
            r3 = r3.get();
            if (r3 == 0) goto L_0x0170;
        L_0x014c:
            r3 = com.tencent.ugc.TXVideoInfoReader.this;
            r3 = r3.mCount;
            if (r3 <= 0) goto L_0x0170;
        L_0x0154:
            r3 = com.tencent.ugc.TXVideoInfoReader.this;
            r3 = r3.mListener;
            r3 = r3.hashCode();
            r5 = r11.f;
            if (r3 != r5) goto L_0x0170;
        L_0x0162:
            r3 = com.tencent.ugc.TXVideoInfoReader.this;
            r3 = r3.mHandler;
            r5 = new com.tencent.ugc.TXVideoInfoReader$a$2;
            r5.<init>(r0, r2);
            r3.post(r5);
        L_0x0170:
            r0 = r0 + 1;
            goto L_0x005f;
        L_0x0174:
            r2 = r3;
            goto L_0x0121;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.tencent.ugc.TXVideoInfoReader.a.run():void");
        }
    }

    long getDuration(String str) {
        try {
            k kVar = new k();
            if (TextUtils.isEmpty(str) || !new File(str).exists()) {
                return 0;
            }
            kVar.a(str);
            long a = kVar.a();
            kVar.j();
            return a;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private TXVideoInfoReader() {
    }

    public static TXVideoInfoReader getInstance() {
        if (sInstance == null) {
            sInstance = new TXVideoInfoReader();
        }
        return sInstance;
    }

    public TXVideoInfo getVideoFileInfo(String str) {
        if (VERSION.SDK_INT < 18) {
            return null;
        }
        File file = new File(str);
        if (!file.exists()) {
            return null;
        }
        TXVideoInfo tXVideoInfo = new TXVideoInfo();
        k kVar = new k();
        kVar.a(str);
        tXVideoInfo.duration = kVar.a();
        TXCLog.i(this.TAG, "getVideoFileInfo: duration = " + tXVideoInfo.duration);
        tXVideoInfo.coverImage = kVar.i();
        tXVideoInfo.width = kVar.e();
        tXVideoInfo.height = kVar.d();
        tXVideoInfo.fps = kVar.f();
        tXVideoInfo.bitrate = (int) (kVar.g() / 1024);
        tXVideoInfo.audioSampleRate = kVar.h();
        kVar.j();
        tXVideoInfo.fileSize = file.length();
        return tXVideoInfo;
    }

    public Bitmap getSampleImage(long j, String str) {
        if (TextUtils.isEmpty(str)) {
            TXCLog.w(this.TAG, "videoPath is null");
            return null;
        } else if (new File(str).exists()) {
            k kVar = new k();
            kVar.a(str);
            this.mImageVideoDuration = kVar.a() * 1000;
            long j2 = j * 1000;
            if (j2 > this.mImageVideoDuration) {
                j2 = this.mImageVideoDuration;
            }
            if (this.mImageVideoDuration <= 0) {
                TXCLog.w(this.TAG, "video duration is 0");
                kVar.j();
                return null;
            }
            Bitmap a = kVar.a(j2);
            if (a == null) {
                TXCLog.d(this.TAG, "getSampleImages failed!!!");
                kVar.j();
                return a;
            }
            TXCLog.d(this.TAG, "getSampleImages bmp  = " + a + ",time=" + j2 + ",duration=" + this.mImageVideoDuration);
            kVar.j();
            return a;
        } else {
            TXCLog.w(this.TAG, "videoPath is not exist");
            return null;
        }
    }

    public void getSampleImages(int i, String str, OnSampleProgrocess onSampleProgrocess) {
        this.mCount = i;
        this.mListener = new WeakReference(onSampleProgrocess);
        if (!TextUtils.isEmpty(str) && new File(str).exists()) {
            cancelThread();
            this.mGenerateImageThread = new a(str);
            this.mGenerateImageThread.start();
            TXCLog.i(this.TAG, "getSampleImages: thread start");
        }
    }

    public void cancel() {
        cancelThread();
        this.mHandler.removeCallbacksAndMessages(null);
        if (this.mListener != null) {
            this.mListener.clear();
            this.mListener = null;
        }
    }

    private void cancelThread() {
        if (this.mGenerateImageThread != null && this.mGenerateImageThread.isAlive() && !this.mGenerateImageThread.isInterrupted()) {
            TXCLog.i(this.TAG, "cancelThread: thread cancel");
            this.mGenerateImageThread.interrupt();
            this.mGenerateImageThread = null;
        }
    }
}
