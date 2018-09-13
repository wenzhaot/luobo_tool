package com.tencent.rtmp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Surface;
import android.view.TextureView;
import com.feng.car.video.shortvideo.FileUtils;
import com.stub.StubApp;
import com.tencent.liteav.basic.b.a;
import com.tencent.liteav.basic.datareport.TXCDRApi;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCCommonUtil;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.tencent.liteav.c;
import com.tencent.liteav.g;
import com.tencent.liteav.h.b;
import com.tencent.liteav.txcvodplayer.TextureRenderView;
import com.tencent.rtmp.TXLivePlayer.ITXSnapshotListener;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.umeng.message.proguard.l;
import java.util.ArrayList;

public class TXVodPlayer implements a, b {
    public static final int PLAYER_TYPE_EXO = 1;
    public static final int PLAYER_TYPE_FFPLAY = 0;
    public static final String TAG = "TXVodPlayer";
    private boolean mAutoPlay = true;
    private int mBitrateIndex;
    private TXVodPlayConfig mConfig;
    private Context mContext;
    private boolean mEnableHWDec = false;
    private boolean mIsGetPlayInfo;
    private boolean mIsNeedClearLastImg = true;
    private ITXLivePlayListener mListener;
    private boolean mLoop;
    private boolean mMirror;
    private boolean mMute = false;
    private com.tencent.liteav.h.a mNetApi;
    private ITXVodPlayListener mNewListener;
    private String mPlayUrl = "";
    private g mPlayer;
    private float mRate = 1.0f;
    private int mRenderMode;
    private int mRenderRotation;
    private boolean mSnapshotRunning = false;
    private Surface mSurface;
    private TXCloudVideoView mTXCloudVideoView;
    private TextureRenderView mTextureView;
    private String mToken;

    public TXVodPlayer(Context context) {
        TXCLog.init();
        this.mListener = null;
        this.mNewListener = null;
        this.mContext = StubApp.getOrigApplicationContext(context.getApplicationContext());
    }

    public void setConfig(TXVodPlayConfig tXVodPlayConfig) {
        this.mConfig = tXVodPlayConfig;
        if (this.mConfig == null) {
            this.mConfig = new TXVodPlayConfig();
        }
        if (this.mPlayer != null) {
            c n = this.mPlayer.n();
            if (n == null) {
                n = new c();
            }
            n.e = this.mConfig.mConnectRetryCount;
            n.f = this.mConfig.mConnectRetryInterval;
            n.q = this.mConfig.mTimeout;
            n.i = this.mEnableHWDec;
            n.m = this.mConfig.mCacheFolderPath;
            n.n = this.mConfig.mMaxCacheItems;
            n.o = this.mConfig.mPlayerType;
            n.p = this.mConfig.mHeaders;
            n.r = this.mConfig.enableAccurateSeek;
            n.s = this.mConfig.autoRotate;
            n.t = this.mConfig.smoothSwitchBitrate;
            n.u = this.mConfig.cacheMp4ExtName;
            n.v = this.mConfig.progressInterval;
            this.mPlayer.a(n);
        }
    }

    public void setPlayerView(TXCloudVideoView tXCloudVideoView) {
        this.mTXCloudVideoView = tXCloudVideoView;
        if (this.mPlayer != null) {
            this.mPlayer.a(tXCloudVideoView);
        }
    }

    public void setPlayerView(TextureRenderView textureRenderView) {
        this.mTextureView = textureRenderView;
        if (this.mPlayer != null) {
            this.mPlayer.a(textureRenderView);
        }
    }

    public void setSurface(Surface surface) {
        this.mSurface = surface;
        if (this.mPlayer != null) {
            this.mPlayer.a(this.mSurface);
        }
    }

    public int startPlay(String str) {
        if (str == null || TextUtils.isEmpty(str)) {
            return -1;
        }
        TXCDRApi.initCrashReport(this.mContext);
        int i = this.mBitrateIndex;
        stopPlay(this.mIsNeedClearLastImg);
        this.mBitrateIndex = i;
        if (this.mToken != null) {
            String path = Uri.parse(str).getPath();
            if (path != null) {
                String[] split = path.split("/");
                if (split.length > 0) {
                    i = str.lastIndexOf(split[split.length - 1]);
                    str = str.substring(0, i) + "voddrm.token." + this.mToken + FileUtils.FILE_EXTENSION_SEPARATOR + str.substring(i);
                }
            }
        }
        this.mPlayUrl = checkPlayUrl(str);
        TXCLog.d(TAG, "===========================================================================================================================================================");
        TXCLog.d(TAG, "===========================================================================================================================================================");
        TXCLog.d(TAG, "=====  StartPlay url = " + this.mPlayUrl + " SDKVersion = " + TXCCommonUtil.getSDKID() + l.u + TXCCommonUtil.getSDKVersionStr() + "    ======");
        TXCLog.d(TAG, "===========================================================================================================================================================");
        TXCLog.d(TAG, "===========================================================================================================================================================");
        this.mPlayer = new g(this.mContext);
        updateConfig();
        if (this.mTXCloudVideoView != null) {
            this.mTXCloudVideoView.clearLog();
            this.mTXCloudVideoView.setVisibility(0);
            this.mPlayer.a(this.mTXCloudVideoView);
        } else if (this.mSurface != null) {
            this.mPlayer.a(this.mSurface);
        } else if (this.mTextureView != null) {
            this.mPlayer.a(this.mTextureView);
        }
        this.mPlayer.e(this.mBitrateIndex);
        this.mPlayer.a((a) this);
        this.mPlayer.c(this.mAutoPlay);
        this.mPlayer.a(this.mPlayUrl, 0);
        this.mPlayer.b(this.mMute);
        this.mPlayer.b(this.mRate);
        this.mPlayer.c(this.mRenderRotation);
        this.mPlayer.b(this.mRenderMode);
        this.mPlayer.d(this.mLoop);
        setMirror(this.mMirror);
        return 0;
    }

    public int startPlay(TXPlayerAuthBuilder tXPlayerAuthBuilder) {
        this.mNetApi = new com.tencent.liteav.h.a();
        this.mNetApi.a(tXPlayerAuthBuilder.isHttps);
        this.mNetApi.a((b) this);
        return this.mNetApi.a(tXPlayerAuthBuilder.appId, tXPlayerAuthBuilder.fileId, tXPlayerAuthBuilder.timeout, tXPlayerAuthBuilder.us, tXPlayerAuthBuilder.exper, tXPlayerAuthBuilder.sign);
    }

    /* JADX WARNING: Missing block: B:46:?, code:
            r9 = r3.toString();
     */
    private java.lang.String checkPlayUrl(java.lang.String r9) {
        /*
        r8 = this;
        r7 = 37;
        r0 = 0;
        r1 = "http";
        r1 = r9.startsWith(r1);
        if (r1 == 0) goto L_0x008b;
    L_0x000c:
        r1 = "UTF-8";
        r2 = r9.getBytes(r1);	 Catch:{ Exception -> 0x0087 }
        r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0087 }
        r1 = r2.length;	 Catch:{ Exception -> 0x0087 }
        r3.<init>(r1);	 Catch:{ Exception -> 0x0087 }
        r1 = r0;
    L_0x001a:
        r0 = r2.length;	 Catch:{ Exception -> 0x0087 }
        if (r1 >= r0) goto L_0x0090;
    L_0x001d:
        r0 = r2[r1];	 Catch:{ Exception -> 0x0087 }
        if (r0 >= 0) goto L_0x0067;
    L_0x0021:
        r0 = r2[r1];	 Catch:{ Exception -> 0x0087 }
        r0 = r0 + 256;
    L_0x0025:
        r4 = 32;
        if (r0 <= r4) goto L_0x005b;
    L_0x0029:
        r4 = 127; // 0x7f float:1.78E-43 double:6.27E-322;
        if (r0 >= r4) goto L_0x005b;
    L_0x002d:
        r4 = 34;
        if (r0 == r4) goto L_0x005b;
    L_0x0031:
        if (r0 == r7) goto L_0x005b;
    L_0x0033:
        r4 = 60;
        if (r0 == r4) goto L_0x005b;
    L_0x0037:
        r4 = 62;
        if (r0 == r4) goto L_0x005b;
    L_0x003b:
        r4 = 91;
        if (r0 == r4) goto L_0x005b;
    L_0x003f:
        r4 = 125; // 0x7d float:1.75E-43 double:6.2E-322;
        if (r0 == r4) goto L_0x005b;
    L_0x0043:
        r4 = 92;
        if (r0 == r4) goto L_0x005b;
    L_0x0047:
        r4 = 93;
        if (r0 == r4) goto L_0x005b;
    L_0x004b:
        r4 = 94;
        if (r0 == r4) goto L_0x005b;
    L_0x004f:
        r4 = 96;
        if (r0 == r4) goto L_0x005b;
    L_0x0053:
        r4 = 123; // 0x7b float:1.72E-43 double:6.1E-322;
        if (r0 == r4) goto L_0x005b;
    L_0x0057:
        r4 = 124; // 0x7c float:1.74E-43 double:6.13E-322;
        if (r0 != r4) goto L_0x0082;
    L_0x005b:
        if (r0 != r7) goto L_0x006a;
    L_0x005d:
        r0 = "TXVodPlayer";
        r1 = "传入URL已转码";
        com.tencent.liteav.basic.log.TXCLog.w(r0, r1);	 Catch:{ Exception -> 0x0087 }
    L_0x0066:
        return r9;
    L_0x0067:
        r0 = r2[r1];	 Catch:{ Exception -> 0x0087 }
        goto L_0x0025;
    L_0x006a:
        r4 = "%%%02X";
        r5 = 1;
        r5 = new java.lang.Object[r5];	 Catch:{ Exception -> 0x0087 }
        r6 = 0;
        r0 = java.lang.Integer.valueOf(r0);	 Catch:{ Exception -> 0x0087 }
        r5[r6] = r0;	 Catch:{ Exception -> 0x0087 }
        r0 = java.lang.String.format(r4, r5);	 Catch:{ Exception -> 0x0087 }
        r3.append(r0);	 Catch:{ Exception -> 0x0087 }
    L_0x007e:
        r0 = r1 + 1;
        r1 = r0;
        goto L_0x001a;
    L_0x0082:
        r0 = (char) r0;	 Catch:{ Exception -> 0x0087 }
        r3.append(r0);	 Catch:{ Exception -> 0x0087 }
        goto L_0x007e;
    L_0x0087:
        r0 = move-exception;
        r0.printStackTrace();
    L_0x008b:
        r9 = r9.trim();
        goto L_0x0066;
    L_0x0090:
        r9 = r3.toString();	 Catch:{ Exception -> 0x0087 }
        goto L_0x008b;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.rtmp.TXVodPlayer.checkPlayUrl(java.lang.String):java.lang.String");
    }

    public int stopPlay(boolean z) {
        if (z && this.mTXCloudVideoView != null) {
            this.mTXCloudVideoView.setVisibility(8);
        }
        if (this.mPlayer != null) {
            this.mPlayer.a(z);
        }
        this.mPlayUrl = "";
        if (this.mNetApi != null) {
            this.mNetApi.a(null);
            this.mNetApi = null;
        }
        this.mBitrateIndex = 0;
        this.mIsGetPlayInfo = false;
        return 0;
    }

    public boolean isPlaying() {
        if (this.mPlayer != null) {
            return this.mPlayer.k();
        }
        return false;
    }

    public void pause() {
        if (this.mPlayer != null) {
            this.mPlayer.a();
        }
    }

    public void resume() {
        if (this.mPlayer != null) {
            this.mPlayer.b();
        }
    }

    public void seek(int i) {
        if (this.mPlayer != null) {
            this.mPlayer.a(i);
        }
    }

    public void seek(float f) {
        if (this.mPlayer != null) {
            this.mPlayer.a(f);
        }
    }

    public float getCurrentPlaybackTime() {
        if (this.mPlayer != null) {
            return this.mPlayer.c();
        }
        return 0.0f;
    }

    public float getBufferDuration() {
        if (this.mPlayer != null) {
            return this.mPlayer.d();
        }
        return 0.0f;
    }

    public float getDuration() {
        if (this.mPlayer != null) {
            return this.mPlayer.e();
        }
        return 0.0f;
    }

    public float getPlayableDuration() {
        if (this.mPlayer != null) {
            return this.mPlayer.f();
        }
        return 0.0f;
    }

    public int getWidth() {
        if (this.mPlayer != null) {
            return this.mPlayer.g();
        }
        return 0;
    }

    public int getHeight() {
        if (this.mPlayer != null) {
            return this.mPlayer.h();
        }
        return 0;
    }

    @Deprecated
    public void setPlayListener(ITXLivePlayListener iTXLivePlayListener) {
        this.mListener = iTXLivePlayListener;
    }

    public void setVodListener(ITXVodPlayListener iTXVodPlayListener) {
        this.mNewListener = iTXVodPlayListener;
    }

    public void setRenderMode(int i) {
        this.mRenderMode = i;
        if (this.mPlayer != null) {
            this.mPlayer.b(i);
        }
    }

    public void setRenderRotation(int i) {
        this.mRenderRotation = i;
        if (this.mPlayer != null) {
            this.mPlayer.c(i);
        }
    }

    public boolean enableHardwareDecode(boolean z) {
        if (z) {
            if (VERSION.SDK_INT < 18) {
                TXCLog.e("HardwareDecode", "enableHardwareDecode failed, android system build.version = " + VERSION.SDK_INT + ", the minimum build.version should be 18(android 4.3 or later)");
                return false;
            } else if (isAVCDecBlacklistDevices()) {
                TXCLog.e("HardwareDecode", "enableHardwareDecode failed, MANUFACTURER = " + Build.MANUFACTURER + ", MODEL" + Build.MODEL);
                return false;
            }
        }
        this.mEnableHWDec = z;
        updateConfig();
        return true;
    }

    public void setMute(boolean z) {
        this.mMute = z;
        if (this.mPlayer != null) {
            this.mPlayer.b(z);
        }
    }

    public void setAutoPlay(boolean z) {
        this.mAutoPlay = z;
        if (this.mPlayer != null) {
            this.mPlayer.c(z);
        }
    }

    public void setRate(float f) {
        this.mRate = f;
        if (this.mPlayer != null) {
            this.mPlayer.b(f);
        }
    }

    public int getBitrateIndex() {
        if (this.mPlayer != null) {
            return this.mPlayer.l();
        }
        return 0;
    }

    public void setBitrateIndex(int i) {
        if (this.mPlayer != null) {
            this.mPlayer.e(i);
        }
        this.mBitrateIndex = i;
    }

    public ArrayList<TXBitrateItem> getSupportedBitrates() {
        if (this.mPlayer != null) {
            return this.mPlayer.m();
        }
        return new ArrayList();
    }

    public void snapshot(ITXSnapshotListener iTXSnapshotListener) {
        if (!this.mSnapshotRunning && iTXSnapshotListener != null) {
            TextureView j;
            this.mSnapshotRunning = true;
            if (this.mPlayer != null) {
                j = this.mPlayer.j();
            } else {
                j = null;
            }
            if (j != null) {
                Bitmap bitmap = j.getBitmap();
                if (bitmap != null) {
                    Matrix transform = j.getTransform(null);
                    if (this.mMirror) {
                        transform.postScale(-1.0f, 1.0f);
                    }
                    Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), transform, true);
                    bitmap.recycle();
                    bitmap = createBitmap;
                }
                postBitmapToMainThread(iTXSnapshotListener, bitmap);
                return;
            }
            this.mSnapshotRunning = false;
        }
    }

    public void setMirror(boolean z) {
        if (this.mPlayer != null) {
            this.mPlayer.e(z);
        }
        this.mMirror = z;
    }

    public void onNotifyEvent(int i, Bundle bundle) {
        if (i == 15001) {
            if (this.mTXCloudVideoView != null) {
                this.mTXCloudVideoView.setLogText(bundle, null, 0);
            }
            if (this.mListener != null) {
                this.mListener.onNetStatus(bundle);
            }
            if (this.mNewListener != null) {
                this.mNewListener.onNetStatus(this, bundle);
                return;
            }
            return;
        }
        if (this.mTXCloudVideoView != null) {
            this.mTXCloudVideoView.setLogText(null, bundle, i);
        }
        if (this.mListener != null) {
            this.mListener.onPlayEvent(i, bundle);
        }
        if (this.mNewListener != null) {
            this.mNewListener.onPlayEvent(this, i, bundle);
        }
    }

    private boolean isAVCDecBlacklistDevices() {
        if (Build.MANUFACTURER.equalsIgnoreCase("HUAWEI") && Build.MODEL.equalsIgnoreCase("Che2-TL00")) {
            return true;
        }
        return false;
    }

    private void postBitmapToMainThread(final ITXSnapshotListener iTXSnapshotListener, final Bitmap bitmap) {
        if (iTXSnapshotListener != null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    if (iTXSnapshotListener != null) {
                        iTXSnapshotListener.onSnapshot(bitmap);
                    }
                    TXVodPlayer.this.mSnapshotRunning = false;
                }
            });
        }
    }

    void updateConfig() {
        setConfig(this.mConfig);
    }

    public void onNetSuccess(com.tencent.liteav.h.a aVar) {
        if (aVar == this.mNetApi) {
            com.tencent.liteav.h.c a = aVar.a();
            if (!this.mIsGetPlayInfo) {
                startPlay(a.a());
            }
            this.mIsGetPlayInfo = false;
            Bundle bundle = new Bundle();
            bundle.putInt("EVT_ID", TXLiveConstants.PLAY_EVT_GET_PLAYINFO_SUCC);
            bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
            bundle.putString(TXLiveConstants.EVT_DESCRIPTION, "文件信息请求成功");
            bundle.putString(TXLiveConstants.EVT_PLAY_URL, a.a());
            bundle.putString(TXLiveConstants.EVT_PLAY_COVER_URL, a.b());
            bundle.putString(TXLiveConstants.EVT_PLAY_NAME, a.f());
            bundle.putString(TXLiveConstants.EVT_PLAY_DESCRIPTION, a.g());
            if (a.d() != null) {
                bundle.putInt(TXLiveConstants.EVT_PLAY_DURATION, a.d().c());
            }
            onNotifyEvent(TXLiveConstants.PLAY_EVT_GET_PLAYINFO_SUCC, bundle);
        }
    }

    public void onNetFailed(com.tencent.liteav.h.a aVar, String str, int i) {
        if (aVar == this.mNetApi) {
            this.mIsGetPlayInfo = false;
            Bundle bundle = new Bundle();
            bundle.putInt("EVT_ID", TXLiveConstants.PLAY_EVT_GET_PLAYINFO_SUCC);
            bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
            bundle.putString(TXLiveConstants.EVT_DESCRIPTION, str);
            bundle.putInt("EVT_PARAM1", i);
            onNotifyEvent(TXLiveConstants.PLAY_ERR_GET_PLAYINFO_FAIL, bundle);
        }
    }

    public void setToken(String str) {
        this.mToken = str;
    }

    public void setLoop(boolean z) {
        this.mLoop = z;
        if (this.mPlayer != null) {
            this.mPlayer.d(this.mLoop);
        }
    }

    public boolean isLoop() {
        return this.mLoop;
    }
}
