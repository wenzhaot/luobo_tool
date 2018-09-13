package com.tencent.rtmp;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Surface;
import android.view.TextureView;
import com.meizu.cloud.pushsdk.handler.impl.AbstractMessageHandler;
import com.stub.StubApp;
import com.tencent.liteav.basic.b.a;
import com.tencent.liteav.basic.datareport.TXCDRApi;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCCommonUtil;
import com.tencent.liteav.c;
import com.tencent.liteav.e;
import com.tencent.liteav.h;
import com.tencent.liteav.i;
import com.tencent.liteav.j;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.tencent.ugc.TXRecordCommon.ITXVideoRecordListener;
import com.umeng.message.proguard.l;

public class TXLivePlayer implements a {
    public static final int PLAY_TYPE_LIVE_FLV = 1;
    public static final int PLAY_TYPE_LIVE_RTMP = 0;
    public static final int PLAY_TYPE_LIVE_RTMP_ACC = 5;
    public static final int PLAY_TYPE_LOCAL_VIDEO = 6;
    public static final int PLAY_TYPE_VOD_FLV = 2;
    public static final int PLAY_TYPE_VOD_HLS = 3;
    public static final int PLAY_TYPE_VOD_MP4 = 4;
    public static final String TAG = "TXLivePlayer";
    private ITXAudioRawDataListener mAudioRawDataListener;
    private int mAudioRoute = 0;
    private boolean mAutoPlay = true;
    private TXLivePlayConfig mConfig;
    private Context mContext;
    private boolean mEnableHWDec = false;
    private boolean mIsNeedClearLastImg = true;
    private boolean mIsShiftPlaying;
    private ITXLivePlayListener mListener;
    private String mLivePlayUrl;
    private boolean mMute = false;
    private String mPlayUrl = "";
    private h mPlayer;
    private long mProgressStartTime;
    private float mRate = 1.0f;
    private int mRenderMode;
    private int mRenderRotation;
    private boolean mSnapshotRunning = false;
    private Surface mSurface;
    private TXCloudVideoView mTXCloudVideoView;
    private e mTimeShiftUtil;
    private ITXVideoRawDataListener mVideoRawDataListener = null;

    public interface ITXAudioRawDataListener {
        void onAudioInfoChanged(int i, int i2, int i3);

        void onPcmDataAvailable(byte[] bArr, long j);
    }

    public interface ITXSnapshotListener {
        void onSnapshot(Bitmap bitmap);
    }

    public interface ITXVideoRawDataListener {
        void onVideoRawDataAvailable(byte[] bArr, int i, int i2, int i3);
    }

    public TXLivePlayer(Context context) {
        TXCLog.init();
        this.mListener = null;
        this.mContext = StubApp.getOrigApplicationContext(context.getApplicationContext());
    }

    public void setConfig(TXLivePlayConfig tXLivePlayConfig) {
        TXCLog.d(TAG, "liteav_api setConfig " + this);
        this.mConfig = tXLivePlayConfig;
        if (this.mConfig == null) {
            this.mConfig = new TXLivePlayConfig();
        }
        if (this.mPlayer != null) {
            c n = this.mPlayer.n();
            if (n == null) {
                n = new c();
            }
            n.a = this.mConfig.mCacheTime;
            n.g = this.mConfig.mAutoAdjustCacheTime;
            n.c = this.mConfig.mMinAutoAdjustCacheTime;
            n.b = this.mConfig.mMaxAutoAdjustCacheTime;
            n.d = this.mConfig.mVideoBlockThreshold;
            n.e = this.mConfig.mConnectRetryCount;
            n.f = this.mConfig.mConnectRetryInterval;
            n.h = this.mConfig.mEnableAec;
            n.j = this.mConfig.mEnableNearestIP;
            n.l = this.mConfig.mRtmpChannelType;
            n.i = this.mEnableHWDec;
            n.m = this.mConfig.mCacheFolderPath;
            n.n = this.mConfig.mMaxCacheItems;
            n.k = this.mConfig.mEnableMessage;
            n.p = this.mConfig.mHeaders;
            this.mPlayer.a(n);
        }
    }

    public void setPlayerView(TXCloudVideoView tXCloudVideoView) {
        TXCLog.d(TAG, "liteav_api setPlayerView old view : " + this.mTXCloudVideoView + ", new view : " + tXCloudVideoView + ", " + this);
        this.mTXCloudVideoView = tXCloudVideoView;
        if (this.mPlayer != null) {
            this.mPlayer.a(tXCloudVideoView);
        }
    }

    public void setSurface(Surface surface) {
        TXCLog.d(TAG, "liteav_api setSurface old : " + this.mSurface + ", new : " + surface + ", " + this);
        this.mSurface = surface;
        if (this.mSurface != null) {
            enableHardwareDecode(true);
        }
        if (this.mPlayer != null) {
            this.mPlayer.a(this.mSurface);
        }
    }

    public int startPlay(String str, int i) {
        TXCLog.d(TAG, "liteav_api startPlay " + this);
        if (TextUtils.isEmpty(str)) {
            TXCLog.e(TAG, "start play error when url is empty " + this);
            return -1;
        }
        if (!TextUtils.isEmpty(this.mPlayUrl) && isPlaying()) {
            if (this.mPlayUrl.equalsIgnoreCase(str)) {
                TXCLog.e(TAG, "start play error when new url is the same with old url  " + this);
                return -1;
            }
            TXCLog.w(TAG, " stop old play when new url is not the same with old url  " + this);
            if (this.mPlayer != null) {
                this.mPlayer.a(false);
            }
            this.mPlayUrl = "";
        }
        TXCDRApi.initCrashReport(this.mContext);
        TXCLog.d(TAG, "===========================================================================================================================================================");
        TXCLog.d(TAG, "===========================================================================================================================================================");
        TXCLog.d(TAG, "=====  StartPlay url = " + str + " playType = " + i + " SDKVersion = " + TXCCommonUtil.getSDKID() + l.u + TXCCommonUtil.getSDKVersionStr() + "    ======");
        TXCLog.d(TAG, "===========================================================================================================================================================");
        TXCLog.d(TAG, "===========================================================================================================================================================");
        this.mPlayer = j.a(this.mContext, i);
        if (this.mPlayer == null) {
            return -2;
        }
        this.mPlayUrl = checkPlayUrl(str, i);
        setConfig(this.mConfig);
        if (this.mTXCloudVideoView != null) {
            this.mTXCloudVideoView.clearLog();
            this.mTXCloudVideoView.setVisibility(0);
        }
        this.mPlayer.a(this.mTXCloudVideoView);
        this.mPlayer.a((a) this);
        this.mPlayer.c(this.mAutoPlay);
        if (this.mSurface != null) {
            this.mPlayer.a(this.mSurface);
        }
        this.mPlayer.a(this.mPlayUrl, i);
        this.mPlayer.b(this.mMute);
        this.mPlayer.b(this.mRate);
        this.mPlayer.c(this.mRenderRotation);
        this.mPlayer.b(this.mRenderMode);
        setAudioRoute(this.mAudioRoute);
        this.mPlayer.a(this.mAudioRawDataListener);
        setVideoRawDataListener(this.mVideoRawDataListener);
        if (this.mPlayer.p()) {
            this.mLivePlayUrl = this.mPlayUrl;
            this.mProgressStartTime = this.mTimeShiftUtil != null ? this.mTimeShiftUtil.a() : 0;
            if (this.mProgressStartTime > 0) {
                this.mPlayer.o();
            }
        }
        return 0;
    }

    public int switchStream(String str) {
        if (this.mPlayer != null) {
            return this.mPlayer.a(str);
        }
        return -1;
    }

    public int stopPlay(boolean z) {
        TXCLog.d(TAG, "liteav_api stopPlay " + z + ", " + this);
        if (z && this.mTXCloudVideoView != null) {
            this.mTXCloudVideoView.setVisibility(8);
        }
        if (this.mPlayer != null) {
            this.mPlayer.a(z);
        }
        this.mPlayUrl = "";
        this.mProgressStartTime = 0;
        this.mTimeShiftUtil = null;
        this.mIsShiftPlaying = false;
        return 0;
    }

    public boolean isPlaying() {
        if (this.mPlayer != null) {
            return this.mPlayer.k();
        }
        return false;
    }

    public void pause() {
        TXCLog.d(TAG, "liteav_api pause " + this);
        if (this.mPlayer != null) {
            TXCLog.w(TAG, "pause play");
            this.mPlayer.a();
        }
    }

    public void resume() {
        TXCLog.d(TAG, "liteav_api resume " + this);
        if (this.mPlayer != null) {
            TXCLog.w(TAG, "resume play");
            this.mPlayer.b();
            setAudioRoute(this.mAudioRoute);
        }
    }

    public void seek(int i) {
        TXCLog.d(TAG, "liteav_api ");
        if (this.mPlayer == null) {
            return;
        }
        if (this.mPlayer.p() || this.mIsShiftPlaying) {
            Object a = this.mTimeShiftUtil != null ? this.mTimeShiftUtil.a((long) i) : "";
            if (!TextUtils.isEmpty(a)) {
                this.mIsShiftPlaying = startPlay(a, 3) == 0;
                if (this.mIsShiftPlaying) {
                    this.mProgressStartTime = (long) (i * 1000);
                    return;
                }
                return;
            }
            return;
        }
        this.mPlayer.a(i);
    }

    public int prepareLiveSeek() {
        TXCLog.d(TAG, "liteav_api prepareLiveSeek " + this);
        if (this.mTimeShiftUtil == null) {
            this.mTimeShiftUtil = new e(this.mContext);
        }
        if (this.mTimeShiftUtil != null) {
            return this.mTimeShiftUtil.a(this.mPlayUrl, new e.a() {
                public void onLiveTime(long j) {
                    TXLivePlayer.this.mProgressStartTime = j;
                    if (TXLivePlayer.this.mPlayer != null) {
                        TXLivePlayer.this.mPlayer.o();
                    }
                }
            });
        }
        return -1;
    }

    public int resumeLive() {
        TXCLog.d(TAG, "liteav_api resumeLive " + this);
        if (!this.mIsShiftPlaying) {
            return -1;
        }
        this.mIsShiftPlaying = false;
        return startPlay(this.mLivePlayUrl, 1);
    }

    public void setPlayListener(ITXLivePlayListener iTXLivePlayListener) {
        TXCLog.d(TAG, "liteav_api setPlayListener " + this);
        this.mListener = iTXLivePlayListener;
    }

    public void setVideoRecordListener(ITXVideoRecordListener iTXVideoRecordListener) {
        TXCLog.d(TAG, "liteav_api setVideoRecordListener");
        if (this.mPlayer != null) {
            this.mPlayer.a(iTXVideoRecordListener);
        }
    }

    public int startRecord(int i) {
        TXCLog.d(TAG, "liteav_api startRecord " + this);
        if (VERSION.SDK_INT < 18) {
            TXCLog.e(TAG, "API levl is too low (record need 18, current is" + VERSION.SDK_INT + l.t);
            return -3;
        } else if (!isPlaying()) {
            TXCLog.e(TAG, "startRecord: there is no playing stream");
            return -1;
        } else if (this.mPlayer != null) {
            return this.mPlayer.d(i);
        } else {
            return -1;
        }
    }

    public int stopRecord() {
        TXCLog.d(TAG, "liteav_api stopRecord " + this);
        if (this.mPlayer != null) {
            return this.mPlayer.i();
        }
        return -1;
    }

    public void setRenderMode(int i) {
        TXCLog.d(TAG, "liteav_api setRenderMode " + i);
        this.mRenderMode = i;
        if (this.mPlayer != null) {
            this.mPlayer.b(i);
        }
    }

    public void setRenderRotation(int i) {
        TXCLog.d(TAG, "liteav_api setRenderRotation " + i);
        this.mRenderRotation = i;
        if (this.mPlayer != null) {
            this.mPlayer.c(i);
        }
    }

    public boolean enableHardwareDecode(boolean z) {
        TXCLog.d(TAG, "liteav_api enableHardwareDecode " + z);
        if (!z && this.mSurface != null) {
            return false;
        }
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
        if (this.mPlayer != null) {
            c n = this.mPlayer.n();
            if (n == null) {
                n = new c();
            }
            n.i = this.mEnableHWDec;
            this.mPlayer.a(n);
        }
        return true;
    }

    public void setMute(boolean z) {
        TXCLog.d(TAG, "liteav_api setMute " + z);
        this.mMute = z;
        if (this.mPlayer != null) {
            this.mPlayer.b(z);
        }
    }

    public void setAutoPlay(boolean z) {
        TXCLog.d(TAG, "liteav_api setAutoPlay " + z);
        this.mAutoPlay = z;
    }

    public void setRate(float f) {
        TXCLog.d(TAG, "liteav_api setRate " + f);
        this.mRate = f;
        if (this.mPlayer != null) {
            this.mPlayer.b(f);
        }
    }

    public void setAudioRoute(int i) {
        TXCLog.d(TAG, "liteav_api setAudioRoute " + i);
        this.mAudioRoute = i;
        if (this.mPlayer != null) {
            this.mPlayer.a(this.mContext, this.mAudioRoute);
        }
    }

    public void onNotifyEvent(int i, Bundle bundle) {
        if (i == 15001) {
            if (this.mTXCloudVideoView != null) {
                this.mTXCloudVideoView.setLogText(bundle, null, 0);
            }
            if (this.mListener != null) {
                this.mListener.onNetStatus(bundle);
            }
        } else if (i == 2005) {
            long j = ((long) bundle.getInt(TXLiveConstants.EVT_PLAY_PROGRESS_MS)) + this.mProgressStartTime;
            if (j > 0) {
                bundle.putInt(TXLiveConstants.EVT_PLAY_PROGRESS, (int) (j / 1000));
                bundle.putInt(TXLiveConstants.EVT_PLAY_PROGRESS_MS, (int) j);
                if (this.mListener != null) {
                    this.mListener.onPlayEvent(i, bundle);
                }
            }
        } else {
            if (this.mTXCloudVideoView != null) {
                this.mTXCloudVideoView.setLogText(null, bundle, i);
            }
            if (this.mListener != null) {
                this.mListener.onPlayEvent(i, bundle);
            }
        }
    }

    public boolean addVideoRawData(byte[] bArr) {
        TXCLog.d(TAG, "liteav_api addVideoRawData " + bArr);
        if (this.mPlayUrl == null || this.mPlayUrl.isEmpty()) {
            return false;
        }
        if (this.mEnableHWDec) {
            TXLog.e(TAG, "can not addVideoRawData because of hw decode has set!");
            return false;
        } else if (this.mPlayer == null) {
            TXCLog.e(TAG, "player hasn't created or not instanceof live player");
            return false;
        } else {
            this.mPlayer.a(bArr);
            return true;
        }
    }

    public void setVideoRawDataListener(final ITXVideoRawDataListener iTXVideoRawDataListener) {
        TXCLog.d(TAG, "liteav_api setVideoRawDataListener " + iTXVideoRawDataListener);
        this.mVideoRawDataListener = iTXVideoRawDataListener;
        if (this.mPlayer != null) {
            if (iTXVideoRawDataListener != null) {
                this.mPlayer.a(new i() {
                    public void onVideoRawDataAvailable(byte[] bArr, int i, int i2, int i3) {
                        iTXVideoRawDataListener.onVideoRawDataAvailable(bArr, i, i2, i3);
                    }
                });
            } else {
                this.mPlayer.a(null);
            }
        }
    }

    public void snapshot(ITXSnapshotListener iTXSnapshotListener) {
        TXCLog.d(TAG, "liteav_api snapshot " + iTXSnapshotListener);
        if (!this.mSnapshotRunning && iTXSnapshotListener != null) {
            TextureView j;
            this.mSnapshotRunning = true;
            if (this.mPlayer != null) {
                j = this.mPlayer.j();
            } else {
                j = null;
            }
            if (j != null) {
                Bitmap bitmap;
                try {
                    bitmap = j.getBitmap();
                } catch (OutOfMemoryError e) {
                    bitmap = null;
                }
                if (bitmap != null) {
                    int i = 0;
                    Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, i, bitmap.getWidth(), bitmap.getHeight(), j.getTransform(null), true);
                    bitmap.recycle();
                    bitmap = createBitmap;
                }
                postBitmapToMainThread(iTXSnapshotListener, bitmap);
                return;
            }
            this.mSnapshotRunning = false;
        }
    }

    public void setAudioRawDataListener(ITXAudioRawDataListener iTXAudioRawDataListener) {
        TXCLog.d(TAG, "liteav_api setAudioRawDataListener " + iTXAudioRawDataListener);
        this.mAudioRawDataListener = iTXAudioRawDataListener;
        if (this.mPlayer != null) {
            this.mPlayer.a(iTXAudioRawDataListener);
        }
    }

    private boolean isAVCDecBlacklistDevices() {
        if (Build.MANUFACTURER.equalsIgnoreCase("HUAWEI") && Build.MODEL.equalsIgnoreCase("Che2-TL00")) {
            return true;
        }
        return false;
    }

    private String checkPlayUrl(String str, int i) {
        int i2 = 0;
        if (i != 6) {
            try {
                byte[] bytes = str.getBytes("UTF-8");
                StringBuilder stringBuilder = new StringBuilder(bytes.length);
                while (true) {
                    int i3 = i2;
                    if (i3 >= bytes.length) {
                        break;
                    }
                    i2 = bytes[i3] < (byte) 0 ? bytes[i3] + AbstractMessageHandler.MESSAGE_TYPE_PUSH_SWITCH_STATUS : bytes[i3];
                    if (i2 <= 32 || i2 >= 127 || i2 == 34 || i2 == 37 || i2 == 60 || i2 == 62 || i2 == 91 || i2 == 125 || i2 == 92 || i2 == 93 || i2 == 94 || i2 == 96 || i2 == 123 || i2 == 124) {
                        stringBuilder.append(String.format("%%%02X", new Object[]{Integer.valueOf(i2)}));
                    } else {
                        stringBuilder.append((char) i2);
                    }
                    i2 = i3 + 1;
                }
                str = stringBuilder.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return str.trim();
    }

    private void postBitmapToMainThread(final ITXSnapshotListener iTXSnapshotListener, final Bitmap bitmap) {
        if (iTXSnapshotListener != null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    if (iTXSnapshotListener != null) {
                        iTXSnapshotListener.onSnapshot(bitmap);
                    }
                    TXLivePlayer.this.mSnapshotRunning = false;
                }
            });
        }
    }
}
