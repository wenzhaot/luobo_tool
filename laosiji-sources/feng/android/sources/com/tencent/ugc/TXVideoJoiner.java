package com.tencent.ugc;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.util.SparseArray;
import com.stub.StubApp;
import com.tencent.liteav.b.k;
import com.tencent.liteav.basic.datareport.TXCDRApi;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.f.a.c;
import com.tencent.liteav.f.a.e;
import com.tencent.liteav.f.a.f;
import com.tencent.liteav.f.d;
import com.tencent.liteav.f.i;
import com.tencent.liteav.f.j;
import com.tencent.liteav.i.c.a;
import com.tencent.liteav.videoediter.ffmpeg.b;
import com.tencent.ugc.TXVideoEditConstants.TXAbsoluteRect;
import com.tencent.ugc.TXVideoEditConstants.TXJoinerResult;
import com.tencent.ugc.TXVideoEditConstants.TXPreviewParam;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TXVideoJoiner implements e, f {
    private static final int PICTURE_JOIN = 1;
    private static final int SEQ_JOIN = 2;
    private static final String TAG = TXVideoJoiner.class.getSimpleName();
    private int currentIndex = 0;
    private boolean isLastFile;
    private Context mContext;
    private SparseArray<Object> mDurationArray;
    private c mFrameEncodeListener = new c() {
        public void a(final float f) {
            if (TXVideoJoiner.this.mVideoJoinListener != null && TXVideoJoiner.this.mIsJoinStart) {
                TXVideoJoiner.this.mHandler.post(new Runnable() {
                    public void run() {
                        if (TXVideoJoiner.this.mVideoJoinListener != null && TXVideoJoiner.this.mIsJoinStart) {
                            TXVideoJoiner.this.mVideoJoinListener.onJoinProgress(f);
                        }
                    }
                });
            }
        }

        public void a(int i, String str) {
            if (TXVideoJoiner.this.mVideoJoinListener != null && TXVideoJoiner.this.mIsJoinStart) {
                int i2 = 0;
                if (i != 0) {
                    i2 = -1;
                }
                final TXJoinerResult tXJoinerResult = new TXJoinerResult();
                tXJoinerResult.retCode = i2;
                tXJoinerResult.descMsg = str;
                TXVideoJoiner.this.mHandler.post(new Runnable() {
                    public void run() {
                        TXVideoJoiner.this.cancel();
                        if (TXVideoJoiner.this.mVideoJoinListener != null) {
                            TXVideoJoiner.this.mVideoJoinListener.onJoinComplete(tXJoinerResult);
                        }
                    }
                });
            }
        }
    };
    private Handler mHandler;
    private volatile boolean mIsJoinStart;
    private volatile boolean mIsPreviewStart;
    private int mJoinType;
    private i mMediaGenerater;
    private j mMediaPreviewer;
    private long mMinDuration = -1;
    private d mOutputConfig;
    private b mQuickJointer;
    private a mTXCVideoJoinerListener = new a() {
        public void a(float f) {
            if (TXVideoJoiner.this.mVideoJoinListener != null) {
                TXVideoJoiner.this.mVideoJoinListener.onJoinProgress(f);
            }
        }

        public void a(com.tencent.liteav.i.a.d dVar) {
            if (TXVideoJoiner.this.mVideoJoinListener != null) {
                TXJoinerResult tXJoinerResult = new TXJoinerResult();
                tXJoinerResult.retCode = dVar.a;
                tXJoinerResult.descMsg = dVar.b;
                TXVideoJoiner.this.mVideoJoinListener.onJoinComplete(tXJoinerResult);
            }
            TXVideoJoiner.this.mIsJoinStart = false;
        }
    };
    private com.tencent.liteav.a.f mTXCombineVideo;
    private TXVideoPreviewListener mTXVideoPreviewListener;
    private int mTotalDuration;
    private TXVideoInfoReader mVideoInfoReader;
    private TXVideoJoinerListener mVideoJoinListener;
    private String mVideoOutputPath;
    private LinkedList<String> mVideoPathList;

    public interface TXVideoJoinerListener {
        void onJoinComplete(TXJoinerResult tXJoinerResult);

        void onJoinProgress(float f);
    }

    public interface TXVideoPreviewListener {
        void onPreviewFinished();

        void onPreviewProgress(int i);
    }

    public TXVideoJoiner(Context context) {
        TXCLog.init();
        this.mContext = StubApp.getOrigApplicationContext(context.getApplicationContext());
        TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.ax);
        TXCDRApi.initCrashReport(this.mContext);
        com.tencent.liteav.g.f.a().a(null, this.mContext);
        this.mDurationArray = new SparseArray();
        this.mVideoInfoReader = TXVideoInfoReader.getInstance();
        if (VERSION.SDK_INT >= 16) {
            this.mTXCombineVideo = new com.tencent.liteav.a.f(context);
        }
        this.mVideoPathList = new LinkedList();
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mMediaGenerater = new i(context);
        this.mMediaPreviewer = new j(context);
        this.mOutputConfig = d.a();
    }

    public void initWithPreview(TXPreviewParam tXPreviewParam) {
        com.tencent.liteav.i.a.f fVar = new com.tencent.liteav.i.a.f();
        fVar.a = tXPreviewParam.videoView;
        fVar.b = tXPreviewParam.renderMode;
        this.mMediaPreviewer.a(fVar);
    }

    public int setVideoPathList(List<String> list) {
        if (list == null || list.size() == 0) {
            TXCLog.w(TAG, "==== setVideoPathList ==== is empty");
            return 0;
        }
        boolean z;
        for (int i = 0; i < list.size(); i++) {
            String str = (String) list.get(i);
            k.a().a = str;
            int f = k.a().f();
            if (f != 0) {
                return f;
            }
            long duration = this.mVideoInfoReader.getDuration(str);
            this.mDurationArray.put(i, Long.valueOf(duration));
            this.mTotalDuration = (int) (((long) this.mTotalDuration) + duration);
            this.mVideoPathList.add(str);
            try {
                this.mMediaGenerater.b((String) list.get(i));
                this.mMediaPreviewer.b((String) list.get(i));
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (duration < this.mMinDuration) {
                this.mMinDuration = duration;
            }
            if (this.mMinDuration == -1) {
                this.mMinDuration = duration;
            }
        }
        this.mMediaGenerater.a((long) (this.mTotalDuration * 1000));
        if (this.mTXCombineVideo != null && VERSION.SDK_INT >= 16) {
            this.mTXCombineVideo.a(this.mMinDuration);
            this.mTXCombineVideo.a((List) list);
        }
        TXCLog.d(TAG, "==== setVideoPathList ==== totalDuration : " + this.mTotalDuration);
        if (this.mVideoPathList.size() == 1) {
            z = true;
        } else {
            z = false;
        }
        this.isLastFile = z;
        this.mMediaGenerater.a(0, (long) this.mTotalDuration);
        this.mMediaPreviewer.a(0, (long) this.mTotalDuration);
        return 0;
    }

    public void onPreviewCompletion() {
        try {
            this.currentIndex++;
            if (this.currentIndex < this.mVideoPathList.size()) {
                this.mMediaPreviewer.a((String) this.mVideoPathList.get(this.currentIndex));
                this.mMediaPreviewer.a((f) this);
                this.isLastFile = this.mVideoPathList.get(this.currentIndex) == this.mVideoPathList.getLast();
                TXCLog.d(TAG, "==== onPreviewCompletion ==== isLastFile :: " + this.isLastFile);
                this.mMediaPreviewer.a(this.isLastFile);
                this.mMediaPreviewer.a();
                return;
            }
            TXCLog.d(TAG, "==== onPreviewCompletion cancel ====");
            this.currentIndex = 0;
            if (this.mTXVideoPreviewListener != null && this.mIsPreviewStart) {
                this.mHandler.post(new Runnable() {
                    public void run() {
                        TXVideoJoiner.this.stopPlay();
                        TXVideoJoiner.this.mTXVideoPreviewListener.onPreviewFinished();
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onJoinDecodeCompletion() {
        try {
            this.currentIndex++;
            if (this.currentIndex < this.mVideoPathList.size()) {
                this.mMediaGenerater.a((String) this.mVideoPathList.get(this.currentIndex));
                this.mMediaGenerater.a((e) this);
                this.isLastFile = this.mVideoPathList.get(this.currentIndex) == this.mVideoPathList.getLast();
                TXCLog.d(TAG, "==== onJoinDecodeCompletion ==== isLastFile :: " + this.isLastFile);
                this.mMediaGenerater.a(this.isLastFile);
                this.mMediaGenerater.b();
                return;
            }
            this.currentIndex = 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startPlay() {
        if (this.mIsPreviewStart) {
            stopPlay();
        }
        this.mIsPreviewStart = true;
        this.mMediaPreviewer.b(1);
        this.currentIndex = 0;
        try {
            this.mMediaPreviewer.a((String) this.mVideoPathList.get(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.mMediaPreviewer.a((f) this);
        this.mMediaPreviewer.a();
    }

    public void pausePlay() {
        if (this.mIsPreviewStart) {
            this.mMediaPreviewer.c();
        }
    }

    public void resumePlay() {
        if (this.mIsPreviewStart) {
            this.mMediaPreviewer.a((f) this);
            this.mMediaPreviewer.d();
        }
    }

    public void stopPlay() {
        if (this.mIsPreviewStart) {
            TXCLog.d(TAG, "==== stopPlay ====");
            if (this.mMediaPreviewer != null) {
                this.mMediaPreviewer.b();
            }
            if (this.mHandler != null) {
                this.mHandler.removeCallbacksAndMessages(null);
            }
            this.mIsPreviewStart = false;
        }
    }

    public void setVideoJoinerListener(TXVideoJoinerListener tXVideoJoinerListener) {
        this.mVideoJoinListener = tXVideoJoinerListener;
    }

    public void joinVideo(int i, String str) {
        this.mVideoOutputPath = str;
        if (com.tencent.liteav.g.f.a().a(null, this.mContext) != 0) {
            final TXJoinerResult tXJoinerResult = new TXJoinerResult();
            tXJoinerResult.retCode = -5;
            tXJoinerResult.descMsg = "licence校验失败";
            if (this.mVideoJoinListener != null) {
                this.mHandler.post(new Runnable() {
                    public void run() {
                        TXVideoJoiner.this.cancel();
                        if (TXVideoJoiner.this.mVideoJoinListener != null) {
                            TXVideoJoiner.this.mVideoJoinListener.onJoinComplete(tXJoinerResult);
                        }
                    }
                });
                return;
            }
            return;
        }
        this.mJoinType = 2;
        if (this.mIsJoinStart) {
            cancel();
        }
        this.mIsJoinStart = true;
        if (!tryQuickJoinVideo(i, str)) {
            normalJoin(i, str);
        }
    }

    private boolean tryQuickJoinVideo(int i, String str) {
        boolean z = true;
        this.mQuickJointer = new b();
        this.mQuickJointer.a(this.mVideoPathList);
        this.mQuickJointer.a(str);
        boolean a = this.mQuickJointer.a();
        if (a) {
            int e = this.mQuickJointer.e();
            int f = this.mQuickJointer.f();
            int[] a2 = com.tencent.liteav.j.c.a(i, e, f);
            if (!(e == a2[0] && f == a2[1])) {
                z = false;
            }
        } else {
            z = a;
        }
        if (z) {
            this.mQuickJointer.a(new b.a() {
                public void a(b bVar, int i, String str) {
                    bVar.c();
                    bVar.d();
                    TXVideoJoiner.this.mQuickJointer = null;
                    final TXJoinerResult tXJoinerResult = new TXJoinerResult();
                    tXJoinerResult.retCode = i == 0 ? 0 : -1;
                    tXJoinerResult.descMsg = str;
                    if (TXVideoJoiner.this.mVideoJoinListener != null && TXVideoJoiner.this.mIsJoinStart) {
                        TXVideoJoiner.this.mHandler.post(new Runnable() {
                            public void run() {
                                TXVideoJoiner.this.cancel();
                                if (TXVideoJoiner.this.mVideoJoinListener != null) {
                                    TXVideoJoiner.this.mVideoJoinListener.onJoinComplete(tXJoinerResult);
                                }
                            }
                        });
                    }
                }

                public void a(b bVar, final float f) {
                    if (TXVideoJoiner.this.mVideoJoinListener != null && TXVideoJoiner.this.mIsJoinStart) {
                        TXVideoJoiner.this.mHandler.post(new Runnable() {
                            public void run() {
                                if (TXVideoJoiner.this.mVideoJoinListener != null) {
                                    TXVideoJoiner.this.mVideoJoinListener.onJoinProgress(f);
                                }
                            }
                        });
                    }
                }
            });
            this.mQuickJointer.b();
        }
        return z;
    }

    private void normalJoin(int i, String str) {
        String verify = verify();
        if (verify != null) {
            final TXJoinerResult tXJoinerResult = new TXJoinerResult();
            tXJoinerResult.retCode = -1;
            tXJoinerResult.descMsg = verify;
            if (this.mVideoJoinListener != null && this.mIsJoinStart) {
                this.mHandler.post(new Runnable() {
                    public void run() {
                        TXVideoJoiner.this.cancel();
                        if (TXVideoJoiner.this.mVideoJoinListener != null) {
                            TXVideoJoiner.this.mVideoJoinListener.onJoinComplete(tXJoinerResult);
                        }
                    }
                });
                return;
            }
            return;
        }
        boolean z;
        this.currentIndex = 0;
        try {
            this.mMediaGenerater.a((String) this.mVideoPathList.get(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.mOutputConfig.a(str);
        this.mOutputConfig.a(i);
        this.mMediaGenerater.c(3);
        this.mMediaGenerater.b(i);
        this.mMediaGenerater.a((e) this);
        if (this.mVideoPathList.get(this.currentIndex) == this.mVideoPathList.getLast()) {
            z = true;
        } else {
            z = false;
        }
        this.isLastFile = z;
        this.mMediaGenerater.a(this.isLastFile);
        this.mMediaGenerater.a(this.mFrameEncodeListener);
        this.mMediaGenerater.c();
    }

    private String verify() {
        if (VERSION.SDK_INT < 18) {
            return new String("暂不支持Android 4.3以下的系统");
        }
        if (this.mMediaGenerater.a()) {
            return null;
        }
        return new String("暂不支持非单、双声道的视频合成");
    }

    public void cancel() {
        if (this.mIsJoinStart) {
            TXCLog.d(TAG, "==== cancel ====");
            if (this.mQuickJointer != null) {
                this.mQuickJointer.c();
                this.mQuickJointer = null;
            }
            if (this.mMediaGenerater != null) {
                this.mMediaGenerater.b(true);
            }
            if (this.mTXCombineVideo != null && VERSION.SDK_INT >= 16) {
                this.mTXCombineVideo.a(null);
                this.mTXCombineVideo.b();
            }
            if (this.mHandler != null) {
                this.mHandler.removeCallbacksAndMessages(null);
            }
            this.mIsJoinStart = false;
        }
    }

    public void setTXVideoPreviewListener(TXVideoPreviewListener tXVideoPreviewListener) {
        this.mTXVideoPreviewListener = tXVideoPreviewListener;
    }

    public void setSplitScreenList(List<TXAbsoluteRect> list, int i, int i2) {
        if (VERSION.SDK_INT >= 16 && list != null && list.size() > 0) {
            List arrayList = new ArrayList();
            int i3 = 0;
            while (true) {
                int i4 = i3;
                if (i4 < list.size()) {
                    TXAbsoluteRect tXAbsoluteRect = (TXAbsoluteRect) list.get(i4);
                    com.tencent.liteav.i.a.a aVar = new com.tencent.liteav.i.a.a();
                    aVar.c = tXAbsoluteRect.width;
                    aVar.d = tXAbsoluteRect.height;
                    aVar.a = tXAbsoluteRect.x;
                    aVar.b = tXAbsoluteRect.y;
                    arrayList.add(aVar);
                    i3 = i4 + 1;
                } else {
                    this.mTXCombineVideo.a(arrayList, i, i2);
                    return;
                }
            }
        }
    }

    public void splitJoinVideo(int i, String str) {
        TXCLog.i(TAG, "splitJoinVideo called");
        this.mVideoOutputPath = str;
        TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.aX);
        if (com.tencent.liteav.g.f.a().a(null, this.mContext) != 0) {
            final TXJoinerResult tXJoinerResult = new TXJoinerResult();
            tXJoinerResult.retCode = -5;
            tXJoinerResult.descMsg = "licence校验失败";
            if (this.mVideoJoinListener != null) {
                this.mHandler.post(new Runnable() {
                    public void run() {
                        TXVideoJoiner.this.cancel();
                        if (TXVideoJoiner.this.mVideoJoinListener != null) {
                            TXVideoJoiner.this.mVideoJoinListener.onJoinComplete(tXJoinerResult);
                        }
                    }
                });
                return;
            }
            return;
        }
        this.mJoinType = 1;
        if (this.mIsJoinStart) {
            cancel();
        }
        this.mIsJoinStart = true;
        if (this.mTXCombineVideo != null && VERSION.SDK_INT >= 16) {
            this.mTXCombineVideo.a(str);
            this.mTXCombineVideo.a(i);
            this.mTXCombineVideo.a(this.mTXCVideoJoinerListener);
            this.mTXCombineVideo.a();
        }
    }
}
