package com.tencent.ugc;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import com.stub.StubApp;
import com.tencent.liteav.b.b;
import com.tencent.liteav.b.d;
import com.tencent.liteav.b.i;
import com.tencent.liteav.b.j;
import com.tencent.liteav.b.k;
import com.tencent.liteav.basic.datareport.TXCDRApi;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.d.aa;
import com.tencent.liteav.d.ab;
import com.tencent.liteav.d.ad;
import com.tencent.liteav.d.r;
import com.tencent.liteav.d.v;
import com.tencent.liteav.d.w;
import com.tencent.liteav.e.h;
import com.tencent.liteav.g.f;
import com.tencent.liteav.i.a.g;
import com.tencent.liteav.i.b.a;
import com.tencent.liteav.i.b.c;
import com.tencent.liteav.i.b.e;
import com.tencent.ugc.TXVideoEditConstants.TXAnimatedPaster;
import com.tencent.ugc.TXVideoEditConstants.TXGenerateResult;
import com.tencent.ugc.TXVideoEditConstants.TXPaster;
import com.tencent.ugc.TXVideoEditConstants.TXPreviewParam;
import com.tencent.ugc.TXVideoEditConstants.TXRect;
import com.tencent.ugc.TXVideoEditConstants.TXRepeat;
import com.tencent.ugc.TXVideoEditConstants.TXSpeed;
import com.tencent.ugc.TXVideoEditConstants.TXSubtitle;
import com.tencent.ugc.TXVideoEditConstants.TXThumbnail;
import java.util.ArrayList;
import java.util.List;

public class TXVideoEditer {
    private static final String TAG = "TXVideoEditer";
    private b mBgmConfig;
    private Context mContext;
    private volatile boolean mIsPreviewStart;
    private d mMotionFilterConfig;
    private a mTXCThumbnailListener = new a() {
        public void a(int i, long j, Bitmap bitmap) {
            if (TXVideoEditer.this.mTXThumbnailListener != null) {
                TXVideoEditer.this.mTXThumbnailListener.onThumbnail(i, j, bitmap);
            }
        }
    };
    private com.tencent.liteav.i.b.b mTXCVideoCustomProcessListener = new com.tencent.liteav.i.b.b() {
        public int a(int i, int i2, int i3, long j) {
            if (TXVideoEditer.this.mTXVideoCustomProcessListener != null) {
                return TXVideoEditer.this.mTXVideoCustomProcessListener.onTextureCustomProcess(i, i2, i3, j);
            }
            return 0;
        }

        public void a() {
            if (TXVideoEditer.this.mTXVideoCustomProcessListener != null) {
                TXVideoEditer.this.mTXVideoCustomProcessListener.onTextureDestroyed();
            }
        }
    };
    private c mTXCVideoGenerateListener = new c() {
        public void a(float f) {
            if (TXVideoEditer.this.mTXVideoGenerateListener != null) {
                TXVideoEditer.this.mTXVideoGenerateListener.onGenerateProgress(f);
            }
        }

        public void a(com.tencent.liteav.i.a.c cVar) {
            TXGenerateResult tXGenerateResult = new TXGenerateResult();
            tXGenerateResult.retCode = cVar.a;
            tXGenerateResult.descMsg = cVar.b;
            if (tXGenerateResult.retCode == 0) {
                int o = i.a().o();
                int p = i.a().p();
                TXCDRApi.txReportDAU(TXVideoEditer.this.mContext, com.tencent.liteav.basic.datareport.a.aY, o, "");
                TXCDRApi.txReportDAU(TXVideoEditer.this.mContext, com.tencent.liteav.basic.datareport.a.aZ, p, "");
            }
            if (TXVideoEditer.this.mTXVideoGenerateListener != null) {
                TXVideoEditer.this.mTXVideoGenerateListener.onGenerateComplete(tXGenerateResult);
            }
        }
    };
    private com.tencent.liteav.i.b.d mTXCVideoPreviewListener = new com.tencent.liteav.i.b.d() {
        public void a(int i) {
            if (TXVideoEditer.this.mTXVideoPreviewListener != null) {
                TXVideoEditer.this.mTXVideoPreviewListener.onPreviewProgress(i);
            }
        }

        public void a() {
            if (TXVideoEditer.this.mTXVideoPreviewListener != null) {
                TXVideoEditer.this.mTXVideoPreviewListener.onPreviewFinished();
            }
        }
    };
    private e mTXCVideoProcessListener = new e() {
        public void a(float f) {
            if (TXVideoEditer.this.mTXVideoProcessListener != null) {
                TXVideoEditer.this.mTXVideoProcessListener.onProcessProgress(f);
            }
        }

        public void a(com.tencent.liteav.i.a.c cVar) {
            if (TXVideoEditer.this.mTXVideoProcessListener != null) {
                TXGenerateResult tXGenerateResult = new TXGenerateResult();
                tXGenerateResult.retCode = cVar.a;
                tXGenerateResult.descMsg = cVar.b;
                TXVideoEditer.this.mTXVideoProcessListener.onProcessComplete(tXGenerateResult);
            }
        }
    };
    private TXThumbnailListener mTXThumbnailListener;
    private TXVideoCustomProcessListener mTXVideoCustomProcessListener;
    private TXVideoGenerateListener mTXVideoGenerateListener;
    private TXVideoPreviewListener mTXVideoPreviewListener;
    private TXVideoProcessListener mTXVideoProcessListener;
    private r mVideoAverageThumbnailGenerate;
    private v mVideoEditGenerate;
    private w mVideoEditerPreview;
    private i mVideoOutputConfig;
    private j mVideoPreProcessConfig;
    private aa mVideoProcessGenerate;
    private ab mVideoRecordGenerate;
    private k mVideoSourceConfig;
    private ad mVideoTimelistThumbnailGenerate;

    public interface TXPCMCallbackListener {
        TXAudioFrame onPCMCallback(TXAudioFrame tXAudioFrame);
    }

    public interface TXThumbnailListener {
        void onThumbnail(int i, long j, Bitmap bitmap);
    }

    public interface TXVideoCustomProcessListener {
        int onTextureCustomProcess(int i, int i2, int i3, long j);

        void onTextureDestroyed();
    }

    public interface TXVideoGenerateListener {
        void onGenerateComplete(TXGenerateResult tXGenerateResult);

        void onGenerateProgress(float f);
    }

    public interface TXVideoPreviewListener {
        void onPreviewFinished();

        void onPreviewProgress(int i);
    }

    public interface TXVideoProcessListener {
        void onProcessComplete(TXGenerateResult tXGenerateResult);

        void onProcessProgress(float f);
    }

    public TXVideoEditer(Context context) {
        TXCLog.init();
        this.mContext = StubApp.getOrigApplicationContext(context.getApplicationContext());
        TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.aw);
        TXCDRApi.initCrashReport(this.mContext);
        f.a().a(null, this.mContext);
        this.mVideoEditerPreview = new w(this.mContext);
        this.mVideoEditGenerate = new v(this.mContext);
        this.mVideoProcessGenerate = new aa(this.mContext);
        this.mVideoRecordGenerate = new ab(this.mContext);
        this.mVideoAverageThumbnailGenerate = new r(this.mContext);
        this.mVideoTimelistThumbnailGenerate = new ad(this.mContext);
        this.mVideoOutputConfig = i.a();
        this.mVideoSourceConfig = k.a();
        this.mVideoPreProcessConfig = j.a();
        this.mBgmConfig = b.a();
        this.mMotionFilterConfig = d.a();
    }

    public int setVideoPath(String str) {
        TXCLog.i(TAG, "=== setVideoPath === videoPath: " + str);
        this.mVideoSourceConfig.a = str;
        return this.mVideoSourceConfig.e();
    }

    public void setCustomVideoProcessListener(TXVideoCustomProcessListener tXVideoCustomProcessListener) {
        this.mTXVideoCustomProcessListener = tXVideoCustomProcessListener;
        if (this.mVideoEditerPreview != null) {
            this.mVideoEditerPreview.a(this.mTXCVideoCustomProcessListener);
        }
        if (this.mVideoEditGenerate != null) {
            this.mVideoEditGenerate.a(this.mTXCVideoCustomProcessListener);
        }
    }

    public void setSpecialRatio(float f) {
        com.tencent.liteav.c.d d = this.mVideoPreProcessConfig.d();
        if (d == null) {
            d = new com.tencent.liteav.c.d();
        }
        d.a(f);
        d.b(0.0f);
        this.mVideoPreProcessConfig.a(d);
    }

    public void setFilter(Bitmap bitmap) {
        com.tencent.liteav.c.d d = this.mVideoPreProcessConfig.d();
        float f = 0.5f;
        float f2 = 0.0f;
        if (d != null) {
            f = d.b();
            f2 = d.c();
        }
        setFilter(bitmap, f, null, f2, 1.0f);
    }

    public void setFilter(Bitmap bitmap, float f, Bitmap bitmap2, float f2, float f3) {
        TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.aP);
        this.mVideoPreProcessConfig.a(new com.tencent.liteav.c.d(f3, bitmap, f, bitmap2, f2));
    }

    public void setBeautyFilter(int i, int i2) {
        TXCLog.i(TAG, "==== setBeautyFilter ==== beautyLevel: " + i + ",whiteningLevel:" + i2);
        this.mVideoPreProcessConfig.a(new com.tencent.liteav.c.c(i, i2));
    }

    public int setPictureList(List<Bitmap> list, int i) {
        int i2 = 30;
        int i3 = 15;
        if (list == null || list.size() <= 0) {
            TXCLog.e(TAG, "setPictureList, bitmapList is empty!");
            return -1;
        }
        if (i <= 15) {
            TXCLog.i(TAG, "setPictureList, fps <= 15, set 15");
        } else {
            i3 = i;
        }
        if (i3 >= 30) {
            TXCLog.i(TAG, "setPictureList, fps >= 30, set 30");
        } else {
            i2 = i3;
        }
        TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.aW);
        this.mVideoSourceConfig.a(list, i2);
        this.mVideoEditerPreview.a((List) list, i2);
        return 0;
    }

    public long setPictureTransition(int i) {
        long a = this.mVideoEditerPreview.a(i);
        i.a().l = 1000 * a;
        return a;
    }

    public int setBGM(String str) {
        int i;
        TXCLog.i(TAG, "==== setBGM ==== path: " + str);
        if (TextUtils.isEmpty(str)) {
            i = 0;
        } else {
            i = k.a().a(str);
        }
        if (i != 0) {
            return i;
        }
        TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.aQ);
        this.mBgmConfig.a(str);
        this.mVideoEditerPreview.b(str);
        stopPlay();
        return 0;
    }

    public void setBGMLoop(boolean z) {
        TXCLog.i(TAG, "==== setBGMLoop ==== looping: " + z);
        this.mBgmConfig.e = z;
        this.mVideoEditerPreview.a(z);
    }

    public void setBGMAtVideoTime(long j) {
        TXCLog.i(TAG, "==== setBGMAtVideoTime ==== videoStartTime: " + j);
        this.mBgmConfig.d = j;
        this.mVideoEditerPreview.a(j);
    }

    public void setBGMStartTime(long j, long j2) {
        TXCLog.i(TAG, "==== setBGMStartTime ==== startTime: " + j + ", endTime: " + j2);
        this.mBgmConfig.b = j;
        this.mBgmConfig.c = j2;
        this.mVideoEditerPreview.a(j, j2);
    }

    public void setBGMVolume(float f) {
        TXCLog.i(TAG, "==== setBGMVolume ==== volume: " + f);
        this.mBgmConfig.g = f;
        this.mVideoEditerPreview.b(f);
    }

    public void setWaterMark(Bitmap bitmap, TXRect tXRect) {
        TXCLog.i(TAG, "==== setWaterMark ==== waterMark: " + bitmap + ", rect: " + tXRect);
        TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.aU);
        g gVar = new g();
        gVar.c = tXRect.width;
        gVar.a = tXRect.x;
        gVar.b = tXRect.y;
        this.mVideoPreProcessConfig.a(new com.tencent.liteav.c.j(bitmap, gVar));
    }

    public void setTailWaterMark(Bitmap bitmap, TXRect tXRect, int i) {
        TXCLog.i(TAG, "==== setTailWaterMark ==== tailWaterMark: " + bitmap + ", rect: " + tXRect + ", duration: " + i);
        TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.aV);
        g gVar = new g();
        gVar.c = tXRect.width;
        gVar.a = tXRect.x;
        gVar.b = tXRect.y;
        com.tencent.liteav.e.j.a().a(new com.tencent.liteav.c.i(bitmap, gVar, i));
    }

    public void setSubtitleList(List<TXSubtitle> list) {
        TXCLog.i(TAG, "==== setSubtitleList ==== subtitleList: " + list);
        if (list != null) {
            TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.aT);
            List arrayList = new ArrayList();
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 < list.size()) {
                    TXSubtitle tXSubtitle = (TXSubtitle) list.get(i2);
                    com.tencent.liteav.i.a.j jVar = new com.tencent.liteav.i.a.j();
                    g gVar = new g();
                    gVar.c = tXSubtitle.frame.width;
                    gVar.a = tXSubtitle.frame.x;
                    gVar.b = tXSubtitle.frame.y;
                    jVar.b = gVar;
                    jVar.a = tXSubtitle.titleImage;
                    jVar.c = tXSubtitle.startTime;
                    jVar.d = tXSubtitle.endTime;
                    arrayList.add(jVar);
                    i = i2 + 1;
                } else {
                    h.a().a(arrayList);
                    return;
                }
            }
        }
        h.a().a(null);
    }

    public void setAnimatedPasterList(List<TXAnimatedPaster> list) {
        TXCLog.i(TAG, "==== setAnimatedPasterList ==== animatedPasterList: " + list);
        if (list != null) {
            TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.aS);
            List arrayList = new ArrayList();
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 < list.size()) {
                    TXAnimatedPaster tXAnimatedPaster = (TXAnimatedPaster) list.get(i2);
                    com.tencent.liteav.i.a.b bVar = new com.tencent.liteav.i.a.b();
                    g gVar = new g();
                    gVar.c = tXAnimatedPaster.frame.width;
                    gVar.a = tXAnimatedPaster.frame.x;
                    gVar.b = tXAnimatedPaster.frame.y;
                    bVar.b = gVar;
                    bVar.a = tXAnimatedPaster.animatedPasterPathFolder;
                    bVar.c = tXAnimatedPaster.startTime;
                    bVar.d = tXAnimatedPaster.endTime;
                    arrayList.add(bVar);
                    i = i2 + 1;
                } else {
                    com.tencent.liteav.e.a.a().a(arrayList);
                    return;
                }
            }
        }
        com.tencent.liteav.e.a.a().a(null);
    }

    public void setPasterList(List<TXPaster> list) {
        TXCLog.i(TAG, "==== setPasterList ==== pasterList: " + list);
        if (list != null) {
            TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.aR);
            List arrayList = new ArrayList();
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 < list.size()) {
                    TXPaster tXPaster = (TXPaster) list.get(i2);
                    com.tencent.liteav.i.a.e eVar = new com.tencent.liteav.i.a.e();
                    g gVar = new g();
                    gVar.c = tXPaster.frame.width;
                    gVar.a = tXPaster.frame.x;
                    gVar.b = tXPaster.frame.y;
                    eVar.b = gVar;
                    eVar.a = tXPaster.pasterImage;
                    eVar.c = tXPaster.startTime;
                    eVar.d = tXPaster.endTime;
                    arrayList.add(eVar);
                    i = i2 + 1;
                } else {
                    com.tencent.liteav.e.f.a().a(arrayList);
                    return;
                }
            }
        }
        com.tencent.liteav.e.f.a().a(null);
    }

    public void setSpeedList(List<TXSpeed> list) {
        TXCLog.i(TAG, "==== setSpeedList ==== ");
        if (list != null) {
            TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.aL);
            List arrayList = new ArrayList();
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 < list.size()) {
                    TXSpeed tXSpeed = (TXSpeed) list.get(i2);
                    com.tencent.liteav.i.a.i iVar = new com.tencent.liteav.i.a.i();
                    iVar.a = tXSpeed.speedLevel;
                    iVar.b = tXSpeed.startTime;
                    iVar.c = tXSpeed.endTime;
                    arrayList.add(iVar);
                    i = i2 + 1;
                } else {
                    com.tencent.liteav.e.g.a().a(arrayList);
                    return;
                }
            }
        }
        com.tencent.liteav.e.g.a().a(null);
    }

    public void setRepeatPlay(List<TXRepeat> list) {
        TXCLog.i(TAG, "==== setRepeatPlay ==== ");
        if (list != null) {
            TXRepeat tXRepeat;
            TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.aM);
            List arrayList = new ArrayList();
            for (int i = 0; i < list.size(); i++) {
                tXRepeat = (TXRepeat) list.get(i);
                com.tencent.liteav.i.a.h hVar = new com.tencent.liteav.i.a.h();
                hVar.c = tXRepeat.repeatTimes;
                hVar.a = tXRepeat.startTime;
                hVar.b = tXRepeat.endTime;
                arrayList.add(hVar);
            }
            com.tencent.liteav.b.f.a().a(arrayList);
            tXRepeat = (TXRepeat) list.get(0);
            this.mVideoEditerPreview.c(tXRepeat.startTime * 1000, tXRepeat.endTime * 1000);
            return;
        }
        TXCLog.i(TAG, "==== cancel setRepeatPlay ==== ");
        com.tencent.liteav.b.f.a().a(null);
        this.mVideoEditerPreview.c(0, 0);
    }

    public void setReverse(boolean z) {
        TXCLog.i(TAG, "==== setReverse ====isReverse:" + z);
        if (z) {
            TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.aN);
        }
        this.mVideoEditerPreview.c();
        com.tencent.liteav.b.g.a().a(z);
    }

    public void startEffect(int i, long j) {
        TXCLog.i(TAG, "==== startEffect ==== type: " + i + ", startTime: " + j);
        TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.aO, i, "");
        com.tencent.liteav.c.f fVar = null;
        switch (i) {
            case 0:
                fVar = new com.tencent.liteav.c.f(2);
                break;
            case 1:
                fVar = new com.tencent.liteav.c.f(3);
                break;
            case 2:
                fVar = new com.tencent.liteav.c.f(0);
                break;
            case 3:
                fVar = new com.tencent.liteav.c.f(1);
                break;
            case 4:
                fVar = new com.tencent.liteav.c.f(4);
                break;
            case 5:
                fVar = new com.tencent.liteav.c.f(5);
                break;
            case 6:
                fVar = new com.tencent.liteav.c.f(6);
                break;
            case 7:
                fVar = new com.tencent.liteav.c.f(7);
                break;
            case 8:
                fVar = new com.tencent.liteav.c.f(8);
                break;
            case 9:
                fVar = new com.tencent.liteav.c.f(11);
                break;
            case 10:
                fVar = new com.tencent.liteav.c.f(10);
                break;
        }
        if (fVar != null) {
            if (com.tencent.liteav.b.g.a().b()) {
                fVar.c = j * 1000;
            } else {
                fVar.b = j * 1000;
            }
            this.mMotionFilterConfig.a(fVar);
        }
    }

    public void stopEffect(int i, long j) {
        TXCLog.i(TAG, "==== stopEffect ==== type: " + i + ", endTime: " + j);
        com.tencent.liteav.c.f b = this.mMotionFilterConfig.b();
        if (b == null) {
            return;
        }
        if (com.tencent.liteav.b.g.a().b()) {
            b.b = j * 1000;
        } else {
            b.c = j * 1000;
        }
    }

    public void deleteLastEffect() {
        TXCLog.i(TAG, "==== deleteLastEffect ====");
        this.mMotionFilterConfig.c();
    }

    public void deleteAllEffect() {
        TXCLog.i(TAG, "==== deleteAllEffect ====");
        this.mMotionFilterConfig.e();
    }

    public void setVideoProcessListener(TXVideoProcessListener tXVideoProcessListener) {
        this.mTXVideoProcessListener = tXVideoProcessListener;
        if (tXVideoProcessListener == null) {
            if (this.mVideoProcessGenerate != null) {
                this.mVideoProcessGenerate.a(null);
            }
            if (this.mVideoRecordGenerate != null) {
                this.mVideoRecordGenerate.a(null);
            }
            if (this.mVideoAverageThumbnailGenerate != null) {
                this.mVideoAverageThumbnailGenerate.a(null);
                return;
            }
            return;
        }
        if (this.mVideoProcessGenerate != null) {
            this.mVideoProcessGenerate.a(this.mTXCVideoProcessListener);
        }
        if (this.mVideoRecordGenerate != null) {
            this.mVideoRecordGenerate.a(this.mTXCVideoProcessListener);
        }
        if (this.mVideoAverageThumbnailGenerate != null) {
            this.mVideoAverageThumbnailGenerate.a(this.mTXCVideoProcessListener);
        }
    }

    public void getThumbnail(List<Long> list, int i, int i2, boolean z, TXThumbnailListener tXThumbnailListener) {
        if (list != null && list.size() != 0) {
            this.mTXThumbnailListener = tXThumbnailListener;
            com.tencent.liteav.b.h.a().i();
            if (this.mVideoTimelistThumbnailGenerate != null) {
                this.mVideoTimelistThumbnailGenerate.a(this.mTXCThumbnailListener);
                this.mVideoTimelistThumbnailGenerate.a(i);
                this.mVideoTimelistThumbnailGenerate.b(i2);
                this.mVideoTimelistThumbnailGenerate.b(z);
                this.mVideoTimelistThumbnailGenerate.a((List) list);
                this.mVideoTimelistThumbnailGenerate.a();
            }
        }
    }

    public void getThumbnail(int i, int i2, int i3, boolean z, TXThumbnailListener tXThumbnailListener) {
        com.tencent.liteav.b.h.a().i();
        this.mTXThumbnailListener = tXThumbnailListener;
        com.tencent.liteav.i.a.k kVar = new com.tencent.liteav.i.a.k();
        kVar.a = i;
        kVar.b = i2;
        kVar.c = i3;
        com.tencent.liteav.b.h.a().a(kVar);
        if (this.mVideoAverageThumbnailGenerate != null) {
            this.mVideoAverageThumbnailGenerate.a(this.mTXCThumbnailListener);
            this.mVideoAverageThumbnailGenerate.a(true);
            this.mVideoAverageThumbnailGenerate.b(z);
            this.mVideoAverageThumbnailGenerate.a();
        }
    }

    public void setThumbnail(TXThumbnail tXThumbnail) {
        com.tencent.liteav.i.a.k kVar = new com.tencent.liteav.i.a.k();
        kVar.a = tXThumbnail.count;
        kVar.b = tXThumbnail.width;
        kVar.c = tXThumbnail.height;
        com.tencent.liteav.b.h.a().a(kVar);
    }

    public void setThumbnailListener(TXThumbnailListener tXThumbnailListener) {
        this.mTXThumbnailListener = tXThumbnailListener;
        if (tXThumbnailListener == null) {
            if (this.mVideoProcessGenerate != null) {
                this.mVideoProcessGenerate.a(null);
            }
            if (this.mVideoRecordGenerate != null) {
                this.mVideoRecordGenerate.a(null);
            }
            if (this.mVideoAverageThumbnailGenerate != null) {
                this.mVideoAverageThumbnailGenerate.a(null);
            }
            if (this.mVideoTimelistThumbnailGenerate != null) {
                this.mVideoTimelistThumbnailGenerate.a(null);
                return;
            }
            return;
        }
        if (this.mVideoProcessGenerate != null) {
            this.mVideoProcessGenerate.a(this.mTXCThumbnailListener);
        }
        if (this.mVideoRecordGenerate != null) {
            this.mVideoRecordGenerate.a(this.mTXCThumbnailListener);
        }
        if (this.mVideoAverageThumbnailGenerate != null) {
            this.mVideoAverageThumbnailGenerate.a(this.mTXCThumbnailListener);
        }
        if (this.mVideoTimelistThumbnailGenerate != null) {
            this.mVideoTimelistThumbnailGenerate.a(this.mTXCThumbnailListener);
        }
    }

    public void processVideo() {
        TXCLog.i(TAG, "=== processVideo ===");
        if (f.a().a(null, this.mContext) != 0) {
            TXGenerateResult tXGenerateResult = new TXGenerateResult();
            tXGenerateResult.retCode = -5;
            tXGenerateResult.descMsg = "licence校验失败";
            if (this.mTXVideoProcessListener != null) {
                this.mTXVideoProcessListener.onProcessComplete(tXGenerateResult);
                return;
            }
            return;
        }
        TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.ba);
        this.mVideoOutputConfig.o = com.tencent.liteav.j.g.a(1);
        this.mVideoOutputConfig.j = 3;
        this.mVideoOutputConfig.m = true;
        boolean g = this.mVideoSourceConfig.g();
        TXCLog.i(TAG, "allFullFrame:" + g);
        this.mVideoOutputConfig.r = g;
        if (g) {
            if (this.mVideoRecordGenerate != null) {
                this.mVideoRecordGenerate.a();
            }
        } else if (this.mVideoProcessGenerate != null) {
            this.mVideoProcessGenerate.a();
        }
    }

    public void release() {
        TXCLog.i(TAG, "=== release ===");
        i.a().n();
        k.a().h();
        com.tencent.liteav.b.c.a().f();
        com.tencent.liteav.b.g.a().c();
        com.tencent.liteav.b.f.a().c();
        com.tencent.liteav.e.g.a().d();
        j.a().e();
        h.a().c();
        com.tencent.liteav.e.f.a().c();
        com.tencent.liteav.e.a.a().c();
        d.a().e();
        b.a().b();
        com.tencent.liteav.e.j.a().i();
        com.tencent.liteav.b.h.a().j();
        if (this.mVideoEditerPreview != null) {
            this.mVideoEditerPreview.f();
        }
        if (this.mVideoRecordGenerate != null) {
            this.mVideoRecordGenerate.c();
        }
        if (this.mVideoProcessGenerate != null) {
            this.mVideoProcessGenerate.c();
        }
        if (this.mVideoEditGenerate != null) {
            this.mVideoEditGenerate.c();
        }
        if (this.mVideoTimelistThumbnailGenerate != null) {
            this.mVideoTimelistThumbnailGenerate.c();
        }
        if (this.mVideoAverageThumbnailGenerate != null) {
            this.mVideoAverageThumbnailGenerate.c();
        }
        this.mTXCThumbnailListener = null;
        this.mTXCVideoCustomProcessListener = null;
        this.mTXCVideoGenerateListener = null;
        this.mTXCVideoPreviewListener = null;
        this.mTXCVideoProcessListener = null;
    }

    public void setTXVideoPreviewListener(TXVideoPreviewListener tXVideoPreviewListener) {
        this.mTXVideoPreviewListener = tXVideoPreviewListener;
        if (this.mVideoEditerPreview == null) {
            return;
        }
        if (tXVideoPreviewListener == null) {
            this.mVideoEditerPreview.a(null);
        } else {
            this.mVideoEditerPreview.a(this.mTXCVideoPreviewListener);
        }
    }

    public void initWithPreview(TXPreviewParam tXPreviewParam) {
        if (tXPreviewParam == null) {
            TXCLog.e(TAG, "=== initWithPreview === please set param not null");
            return;
        }
        TXCLog.i(TAG, "=== initWithPreview === rendeMode: " + tXPreviewParam.renderMode);
        com.tencent.liteav.i.a.f fVar = new com.tencent.liteav.i.a.f();
        fVar.b = tXPreviewParam.renderMode;
        fVar.a = tXPreviewParam.videoView;
        this.mVideoOutputConfig.s = fVar.b;
        if (this.mVideoEditerPreview != null) {
            this.mVideoEditerPreview.a(fVar);
        }
    }

    public void startPlayFromTime(long j, long j2) {
        TXCLog.i(TAG, "==== startPlayFromTime ==== startTime: " + j + ", endTime: " + j2);
        if (this.mIsPreviewStart) {
            stopPlay();
        }
        this.mIsPreviewStart = true;
        if (this.mVideoEditerPreview != null) {
            this.mVideoEditerPreview.b(j, j2);
            this.mVideoEditerPreview.b();
        }
    }

    public void pausePlay() {
        TXCLog.i(TAG, "==== pausePlay ====");
        if (this.mVideoEditerPreview != null) {
            this.mVideoEditerPreview.e();
        }
    }

    public void resumePlay() {
        TXCLog.i(TAG, "==== resumePlay ====");
        if (this.mVideoEditerPreview != null) {
            this.mVideoEditerPreview.d();
        }
    }

    public void stopPlay() {
        TXCLog.i(TAG, "==== stopPlay ====");
        if (this.mIsPreviewStart) {
            if (this.mVideoEditerPreview != null) {
                this.mVideoEditerPreview.c();
            }
            this.mIsPreviewStart = false;
        }
    }

    public void previewAtTime(long j) {
        TXCLog.i(TAG, "==== previewAtTime ==== timeMs: " + j);
        if (this.mVideoEditerPreview != null) {
            this.mVideoEditerPreview.b(j);
        }
    }

    public void setVideoGenerateListener(TXVideoGenerateListener tXVideoGenerateListener) {
        TXCLog.i(TAG, "=== setVideoGenerateListener === listener:" + tXVideoGenerateListener);
        this.mTXVideoGenerateListener = tXVideoGenerateListener;
        if (this.mVideoEditGenerate == null) {
            return;
        }
        if (tXVideoGenerateListener == null) {
            this.mVideoEditGenerate.a(null);
        } else {
            this.mVideoEditGenerate.a(this.mTXCVideoGenerateListener);
        }
    }

    public void setCutFromTime(long j, long j2) {
        TXCLog.i(TAG, "==== setPlayFromTime ==== startTime: " + j + ", endTime: " + j2);
        TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.aK);
        com.tencent.liteav.b.c.a().a(j * 1000, 1000 * j2);
    }

    public void setVideoBitrate(int i) {
        TXCLog.i(TAG, "==== setVideoBitrate ==== videoBitrate: " + i);
        this.mVideoOutputConfig.p = i;
    }

    public void setAudioBitrate(int i) {
        TXCLog.i(TAG, "==== setAudioBitrate ==== audioBitrate: " + i);
        this.mVideoOutputConfig.q = i * 1024;
    }

    public void generateVideo(int i, String str) {
        TXCLog.i(TAG, "==== generateVideo ==== videoCompressed: " + i + ", videoOutputPath: " + str);
        if (f.a().a(null, this.mContext) != 0) {
            TXGenerateResult tXGenerateResult = new TXGenerateResult();
            tXGenerateResult.retCode = -5;
            tXGenerateResult.descMsg = "licence校验失败";
            if (this.mTXVideoGenerateListener != null) {
                this.mTXVideoGenerateListener.onGenerateComplete(tXGenerateResult);
                return;
            }
            return;
        }
        this.mVideoOutputConfig.i = str;
        this.mVideoOutputConfig.j = i;
        this.mVideoOutputConfig.m = false;
        if (this.mVideoEditGenerate != null) {
            this.mVideoEditGenerate.a();
        }
    }

    public void cancel() {
        TXCLog.i(TAG, "==== cancel ====");
        if (this.mVideoAverageThumbnailGenerate != null) {
            this.mVideoAverageThumbnailGenerate.b();
        }
        if (this.mVideoTimelistThumbnailGenerate != null) {
            this.mVideoTimelistThumbnailGenerate.b();
        }
        if (this.mVideoRecordGenerate != null) {
            this.mVideoRecordGenerate.b();
        }
        if (this.mVideoProcessGenerate != null) {
            this.mVideoProcessGenerate.b();
        }
        if (this.mVideoEditGenerate != null) {
            this.mVideoEditGenerate.b();
        }
    }

    public void refreshOneFrame() {
        TXCLog.i(TAG, "==== refreshOneFrame ====");
        if (this.mVideoEditerPreview != null) {
            this.mVideoEditerPreview.a();
        }
    }

    public void setVideoVolume(float f) {
        TXCLog.i(TAG, "==== setVideoVolume ==== volume: " + f);
        this.mBgmConfig.f = f;
        if (this.mVideoEditerPreview != null) {
            this.mVideoEditerPreview.a(f);
        }
    }
}
