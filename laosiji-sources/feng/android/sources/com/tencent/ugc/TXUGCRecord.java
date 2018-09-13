package com.tencent.ugc;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.media.AudioRecord;
import android.media.MediaFormat;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import com.feng.car.utils.FengConstant;
import com.stub.StubApp;
import com.tencent.liteav.audio.TXCAudioUGCRecorder;
import com.tencent.liteav.audio.TXCUGCBGMPlayer;
import com.tencent.liteav.audio.TXEAudioDef;
import com.tencent.liteav.audio.e;
import com.tencent.liteav.basic.b.a;
import com.tencent.liteav.basic.datareport.TXCDRApi;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.tencent.liteav.muxer.c;
import com.tencent.liteav.renderer.f;
import com.tencent.liteav.videoediter.ffmpeg.b;
import com.tencent.liteav.videoencoder.TXSVideoEncoderParam;
import com.tencent.liteav.videoencoder.d;
import com.tencent.rtmp.TXLog;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.tencent.ugc.TXRecordCommon.ITXBGMNotify;
import com.tencent.ugc.TXRecordCommon.ITXSnapshotListener;
import com.tencent.ugc.TXRecordCommon.ITXVideoRecordListener;
import com.tencent.ugc.TXRecordCommon.TXRecordResult;
import com.tencent.ugc.TXRecordCommon.TXUGCCustomConfig;
import com.tencent.ugc.TXRecordCommon.TXUGCSimpleConfig;
import com.tencent.ugc.TXUGCPartsManager.IPartsManagerListener;
import com.tencent.ugc.TXVideoEditConstants.TXRect;
import com.umeng.message.proguard.l;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLContext;

public class TXUGCRecord implements e, a, com.tencent.liteav.beauty.e, com.tencent.liteav.renderer.e, f, d, IPartsManagerListener {
    private static final int DEFAULT_CHANNEL = 1;
    public static float ENCODE_SPEED_FAST = 1.25f;
    public static float ENCODE_SPEED_FASTEST = 2.0f;
    public static float ENCODE_SPEED_SLOW = 0.8f;
    public static float ENCODE_SPEED_SLOWEST = 0.5f;
    private static final String OUTPUT_DIR_NAME = "TXUGC";
    private static final String OUTPUT_TEMP_DIR_NAME = "TXUGCParts";
    private static final String OUTPUT_VIDEO_COVER_NAME = "TXUGCCover.jpg";
    private static final String OUTPUT_VIDEO_NAME = "TXUGCRecord.mp4";
    public static float PLAY_SPEED_FAST = 0.8f;
    public static float PLAY_SPEED_FASTEST = 0.5f;
    public static float PLAY_SPEED_SLOW = 1.25f;
    public static float PLAY_SPEED_SLOWEST = 2.0f;
    private static final int STATE_NO_PERMISSION = -1;
    private static final int STATE_RECORDING = 1;
    private static final int STATE_RECORD_INIT = 1;
    private static final int STATE_RECORD_PAUSE = 3;
    private static final int STATE_RECORD_RECORDING = 2;
    private static final int STATE_SUCCESS = 0;
    private static final String TAG = "TXUGCRecord";
    private static TXUGCRecord instance;
    private int encodeIndex;
    private boolean isReachedMaxDuration = false;
    private boolean mBGMDeletePart = false;
    private String mBGMPath;
    private long mBgmEndTime;
    private CopyOnWriteArrayList<Long> mBgmPartBytesList;
    private long mBgmStartTime;
    private com.tencent.liteav.capturer.a mCameraCapture = null;
    private int mCameraOrientationReadyChange = -1;
    private int mCameraResolution = 5;
    private boolean mCapturing = false;
    private com.tencent.liteav.d mConfig = new com.tencent.liteav.d();
    private Context mContext;
    private String mCoverCurTempPath = null;
    private String mCoverPath = null;
    private int mCropHeight;
    private int mCropWidth;
    private long mCurrentRecordDuration;
    private VideoCustomProcessListener mCustomProcessListener;
    private int mDisplayType;
    private int mFps = 20;
    private int mGop = 3;
    private boolean mInitCompelete = false;
    private c mMP4Muxer = null;
    private Handler mMainHandler;
    private int mMaxDuration;
    private int mMinDuration;
    ITXBGMNotify mOldBGMNotify = null;
    com.tencent.liteav.audio.f mOldBGMNotifyProxy = null;
    private long mPauseTotalTimeMs = 0;
    private int mRecordRetCode;
    private int mRecordSpeed = 2;
    private long mRecordStartTime = 0;
    private volatile int mRecordState = 1;
    private boolean mRecording = false;
    private int mRenderMode = 0;
    private int mRenderRotationReadyChange = -1;
    private String mSaveDir = null;
    private boolean mSnapshotRunning = false;
    private float mSpecialRadio = 0.5f;
    private boolean mStartMuxer = false;
    private boolean mStartPreview = false;
    private TXCloudVideoView mTXCloudVideoView;
    private b mTXFFQuickJoiner;
    private com.tencent.liteav.videoediter.a.c mTXMultiMediaComposer = null;
    private TXUGCPartsManager mTXUGCPartsManager;
    private boolean mUseSWEncoder = false;
    private com.tencent.liteav.videoencoder.b mVideoEncoder = null;
    private String mVideoFileCurTempPath = null;
    private String mVideoFilePath = null;
    private String mVideoFileTempDir = null;
    private int mVideoHeight = 0;
    private com.tencent.liteav.beauty.d mVideoPreprocessor = null;
    private ITXVideoRecordListener mVideoRecordListener;
    private com.tencent.liteav.renderer.b mVideoView;
    private int mVideoWidth = 0;
    private int mVoiceEnvironment = -1;
    private int mVoiceKind = -1;
    private boolean needCompose = false;

    public interface VideoCustomProcessListener {
        void onDetectFacePoints(float[] fArr);

        int onTextureCustomProcess(int i, int i2, int i3);

        void onTextureDestroyed();
    }

    public static synchronized TXUGCRecord getInstance(Context context) {
        TXUGCRecord tXUGCRecord;
        synchronized (TXUGCRecord.class) {
            if (instance == null) {
                instance = new TXUGCRecord(context);
            }
            tXUGCRecord = instance;
        }
        return tXUGCRecord;
    }

    protected TXUGCRecord(Context context) {
        TXCLog.init();
        if (context != null) {
            this.mContext = StubApp.getOrigApplicationContext(context.getApplicationContext());
            this.mMainHandler = new Handler(this.mContext.getMainLooper());
            this.mTXUGCPartsManager = new TXUGCPartsManager();
            this.mBgmPartBytesList = new CopyOnWriteArrayList();
            com.tencent.liteav.g.f.a().a(null, this.mContext);
        }
        this.mUseSWEncoder = com.tencent.liteav.basic.util.a.f();
    }

    public void setVideoRecordListener(ITXVideoRecordListener iTXVideoRecordListener) {
        this.mVideoRecordListener = iTXVideoRecordListener;
    }

    public void onDeleteLastPart() {
        if (this.mBgmPartBytesList.size() != 0) {
            this.mBgmPartBytesList.remove(this.mBgmPartBytesList.size() - 1);
            this.mBGMDeletePart = true;
        }
    }

    public void onDeleteAllParts() {
        this.mBgmPartBytesList.clear();
        this.mBGMDeletePart = false;
    }

    public int startCameraSimplePreview(TXUGCSimpleConfig tXUGCSimpleConfig, TXCloudVideoView tXCloudVideoView) {
        if (tXCloudVideoView == null || tXUGCSimpleConfig == null) {
            TXCLog.e(TAG, "startCameraPreview: invalid param");
            return -1;
        }
        this.mConfig.t = tXUGCSimpleConfig.needEdit;
        this.mConfig.a = tXUGCSimpleConfig.videoQuality;
        this.mConfig.n = tXUGCSimpleConfig.isFront;
        this.mMinDuration = tXUGCSimpleConfig.minDuration;
        this.mMaxDuration = tXUGCSimpleConfig.maxDuration;
        startCameraPreviewInternal(tXCloudVideoView, this.mConfig);
        return 0;
    }

    public int startCameraCustomPreview(TXUGCCustomConfig tXUGCCustomConfig, TXCloudVideoView tXCloudVideoView) {
        this.mConfig.t = tXUGCCustomConfig.needEdit;
        if (tXCloudVideoView == null || tXUGCCustomConfig == null) {
            TXCLog.e(TAG, "startCameraPreview: invalid param");
            return -1;
        }
        this.mConfig.a = -1;
        if (tXUGCCustomConfig.videoBitrate < 600) {
            tXUGCCustomConfig.videoBitrate = 600;
        }
        if (tXUGCCustomConfig.needEdit) {
            this.mConfig.d = 10000;
        } else {
            this.mConfig.d = tXUGCCustomConfig.videoBitrate;
        }
        TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bh, this.mConfig.d, "");
        if (tXUGCCustomConfig.videoFps < 15) {
            tXUGCCustomConfig.videoFps = 15;
        } else if (tXUGCCustomConfig.videoFps > 30) {
            tXUGCCustomConfig.videoFps = 30;
        }
        this.mConfig.c = tXUGCCustomConfig.videoFps;
        TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bi, this.mConfig.c, "");
        if (tXUGCCustomConfig.videoGop < 1) {
            tXUGCCustomConfig.videoGop = 1;
        } else if (tXUGCCustomConfig.videoGop > 10) {
            tXUGCCustomConfig.videoGop = 10;
        }
        if (tXUGCCustomConfig.needEdit) {
            this.mConfig.e = 0;
        } else {
            this.mConfig.e = tXUGCCustomConfig.videoGop;
        }
        TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bj, this.mConfig.e, "");
        if (tXUGCCustomConfig.audioSampleRate == TXRecordCommon.AUDIO_SAMPLERATE_8000 || tXUGCCustomConfig.audioSampleRate == TXRecordCommon.AUDIO_SAMPLERATE_16000 || tXUGCCustomConfig.audioSampleRate == TXRecordCommon.AUDIO_SAMPLERATE_32000 || tXUGCCustomConfig.audioSampleRate == TXRecordCommon.AUDIO_SAMPLERATE_44100 || tXUGCCustomConfig.audioSampleRate == TXRecordCommon.AUDIO_SAMPLERATE_48000) {
            this.mConfig.s = tXUGCCustomConfig.audioSampleRate;
        } else {
            this.mConfig.s = TXRecordCommon.AUDIO_SAMPLERATE_48000;
        }
        this.mConfig.b = tXUGCCustomConfig.videoResolution;
        this.mConfig.n = tXUGCCustomConfig.isFront;
        this.mConfig.p = tXUGCCustomConfig.enableHighResolutionCapture;
        this.mMinDuration = tXUGCCustomConfig.minDuration;
        this.mMaxDuration = tXUGCCustomConfig.maxDuration;
        this.mConfig.t = tXUGCCustomConfig.needEdit;
        startCameraPreviewInternal(tXCloudVideoView, this.mConfig);
        return 0;
    }

    public void setVideoResolution(int i) {
        if (this.mRecordState != 1) {
            TXCLog.e(TAG, "setVideoResolution err, state not init");
        } else if (this.mTXCloudVideoView == null) {
            TXCLog.e(TAG, "setVideoResolution, mTXCloudVideoView is null");
        } else if (this.mConfig.b == i) {
            TXCLog.i(TAG, "setVideoResolution, resolution not change");
        } else {
            this.mConfig.a = -1;
            this.mConfig.b = i;
            stopCameraPreview();
            startCameraPreviewInternal(this.mTXCloudVideoView, this.mConfig);
        }
    }

    public void setVideoBitrate(int i) {
        if (this.mRecordState != 1) {
            TXCLog.e(TAG, "setVideoBitrate err, state not init");
            return;
        }
        this.mConfig.a = -1;
        this.mConfig.d = i;
    }

    public void stopCameraPreview() {
        try {
            TXCLog.i(TAG, "ugcRecord, stopCameraPreview");
            this.mStartPreview = false;
            synchronized (this) {
                this.mCapturing = false;
                if (this.mCameraCapture != null) {
                    this.mCameraCapture.b();
                }
            }
            if (this.mVideoView != null) {
                this.mVideoView.a(new Runnable() {
                    public void run() {
                        if (TXUGCRecord.this.mVideoPreprocessor != null) {
                            TXUGCRecord.this.mVideoPreprocessor.a();
                        }
                    }
                });
                this.mVideoView.b(false);
                this.mVideoView = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TXUGCPartsManager getPartsManager() {
        return this.mTXUGCPartsManager;
    }

    public void setMute(boolean z) {
        TXCAudioUGCRecorder.getInstance().setMute(z);
    }

    public int startRecord() {
        if (VERSION.SDK_INT < 18) {
            TXCLog.e(TAG, "API level is too low (record need 18, current is " + VERSION.SDK_INT + l.t);
            return -3;
        } else if (this.mRecording) {
            TXCLog.e(TAG, "startRecord: there is existing uncompleted record task");
            return -1;
        } else {
            try {
                TXCDRApi.initCrashReport(this.mContext);
                this.mSaveDir = getDefaultDir();
                this.mVideoFileTempDir = this.mSaveDir + File.separator + OUTPUT_TEMP_DIR_NAME;
                File file = new File(this.mVideoFileTempDir);
                if (!file.exists()) {
                    file.mkdir();
                }
                this.mVideoFilePath = this.mSaveDir + File.separator + OUTPUT_VIDEO_NAME;
                this.mVideoFileCurTempPath = this.mVideoFileTempDir + File.separator + String.format("temp_TXUGC_%s.mp4", new Object[]{getTimeString()});
                file = new File(this.mVideoFilePath);
                if (file.exists()) {
                    file.delete();
                }
                this.mCoverPath = this.mSaveDir + File.separator + OUTPUT_VIDEO_COVER_NAME;
                this.mCoverCurTempPath = this.mCoverPath;
                file = new File(this.mCoverPath);
                if (file.exists()) {
                    file.delete();
                }
                this.encodeIndex = 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return startRecordInternal();
        }
    }

    public int startRecord(String str, String str2) {
        if (VERSION.SDK_INT < 18) {
            TXCLog.e(TAG, "API level is too low (record need 18, current is " + VERSION.SDK_INT + l.t);
            return -3;
        } else if (this.mRecording) {
            TXCLog.e(TAG, "startRecord: there is existing uncompleted record task");
            return -1;
        } else if (TextUtils.isEmpty(str)) {
            TXCLog.e(TAG, "startRecord: init videoRecord failed, videoFilePath is empty");
            return -2;
        } else {
            this.mVideoFilePath = str;
            File file = new File(str);
            if (file.exists()) {
                file.delete();
            }
            this.mVideoFileTempDir = getDefaultDir() + File.separator + OUTPUT_TEMP_DIR_NAME;
            file = new File(this.mVideoFileTempDir);
            if (!file.exists()) {
                file.mkdir();
            }
            this.mVideoFileCurTempPath = this.mVideoFileTempDir + File.separator + String.format("temp_TXUGC_%s.mp4", new Object[]{getTimeString()});
            this.mCoverPath = str2;
            this.mCoverCurTempPath = str2;
            return startRecordInternal();
        }
    }

    public int startRecord(String str, String str2, String str3) {
        if (VERSION.SDK_INT < 18) {
            TXCLog.e(TAG, "API level is too low (record need 18, current is " + VERSION.SDK_INT + l.t);
            return -3;
        } else if (this.mRecording) {
            TXCLog.e(TAG, "startRecord: there is existing uncompleted record task");
            return -1;
        } else if (TextUtils.isEmpty(str)) {
            TXCLog.e(TAG, "startRecord: init videoRecord failed, videoFilePath is empty");
            return -2;
        } else {
            this.mVideoFilePath = str;
            File file = new File(str);
            if (file.exists()) {
                file.delete();
            }
            if (TextUtils.isEmpty(str2)) {
                this.mVideoFileTempDir = getDefaultDir() + File.separator + OUTPUT_TEMP_DIR_NAME;
            } else {
                this.mVideoFileTempDir = str2;
            }
            file = new File(this.mVideoFileTempDir);
            if (!file.exists()) {
                file.mkdir();
            }
            this.mVideoFileCurTempPath = this.mVideoFileTempDir + File.separator + String.format("temp_TXUGC_%s.mp4", new Object[]{getTimeString()});
            this.mCoverPath = str3;
            this.mCoverCurTempPath = str3;
            return startRecordInternal();
        }
    }

    private int startRecordInternal() {
        if (!this.mInitCompelete) {
            TXCLog.i(TAG, "startRecordInternal, mInitCompelete = " + this.mInitCompelete);
            return -4;
        } else if (com.tencent.liteav.g.f.a().a(null, this.mContext) != 0) {
            return -5;
        } else {
            boolean z;
            TXCAudioUGCRecorder.getInstance().setAECType(TXEAudioDef.TXE_AEC_NONE, this.mContext);
            TXCAudioUGCRecorder.getInstance().setListener(this);
            TXCAudioUGCRecorder.getInstance().setChannels(1);
            TXCAudioUGCRecorder.getInstance().setSampleRate(this.mConfig.s);
            TXCAudioUGCRecorder.getInstance().startRecord(this.mContext);
            switch (this.mRecordSpeed) {
                case 0:
                    TXCAudioUGCRecorder.getInstance().setSpeedRate(ENCODE_SPEED_SLOWEST);
                    break;
                case 1:
                    TXCAudioUGCRecorder.getInstance().setSpeedRate(ENCODE_SPEED_SLOW);
                    break;
                case 2:
                    TXCAudioUGCRecorder.getInstance().setSpeedRate(1.0f);
                    break;
                case 3:
                    TXCAudioUGCRecorder.getInstance().setSpeedRate(ENCODE_SPEED_FAST);
                    break;
                case 4:
                    TXCAudioUGCRecorder.getInstance().setSpeedRate(ENCODE_SPEED_FASTEST);
                    break;
            }
            if (this.mVideoEncoder != null) {
                this.mVideoWidth = 0;
                this.mVideoHeight = 0;
            }
            if (this.mConfig.f >= 1280 || this.mConfig.g >= 1280) {
                z = false;
            } else {
                z = true;
            }
            this.mUseSWEncoder = z;
            if (this.mMP4Muxer == null) {
                int i;
                Context context = this.mContext;
                if (this.mUseSWEncoder) {
                    i = 0;
                } else {
                    i = 2;
                }
                this.mMP4Muxer = new c(context, i);
            }
            TXCLog.i(TAG, "startRecord");
            this.mMP4Muxer.a(this.mRecordSpeed);
            this.mMP4Muxer.a(this.mVideoFileCurTempPath);
            addAudioTrack();
            TXCDRApi.txReportDAU(StubApp.getOrigApplicationContext(this.mContext.getApplicationContext()), com.tencent.liteav.basic.datareport.a.au);
            this.mRecordState = 2;
            this.mRecording = true;
            this.mRecordStartTime = 0;
            this.mPauseTotalTimeMs = 0;
            if (this.mBGMDeletePart) {
                TXCAudioUGCRecorder.getInstance().clearCache();
            }
            TXCAudioUGCRecorder.getInstance().resume();
            return 0;
        }
    }

    public int stopRecord() {
        TXCLog.i(TAG, "stopRecord called, mRecording = " + this.mRecording + ", needCompose = " + this.needCompose);
        TXCAudioUGCRecorder.getInstance().setChangerType(-1, -1);
        TXCAudioUGCRecorder.getInstance().setReverbType(0);
        if (this.mRecording) {
            this.needCompose = true;
            return stopRecordForClip();
        }
        int composeRecord = composeRecord();
        if (composeRecord == 0) {
            return 0;
        }
        callbackRecordFail(composeRecord);
        return 0;
    }

    private int composeRecord() {
        if (this.mTXFFQuickJoiner == null) {
            this.mTXFFQuickJoiner = new b();
        }
        this.mTXFFQuickJoiner.a(new b.a() {
            public void a(b bVar, int i, String str) {
                if (i == 0) {
                    TXUGCRecord.this.callbackRecordSuccess();
                } else if (i == 1) {
                    TXUGCRecord.this.callbackRecordFail(-7);
                    TXLog.e(TXUGCRecord.TAG, "composeRecord, quick joiner result cancel");
                } else if (i == -1) {
                    TXUGCRecord.this.callbackRecordFail(-8);
                    TXLog.e(TXUGCRecord.TAG, "composeRecord, quick joiner result verify fail");
                } else if (i == -2) {
                    TXUGCRecord.this.callbackRecordFail(-9);
                    TXLog.e(TXUGCRecord.TAG, "composeRecord, quick joiner result err");
                }
                bVar.a(null);
                bVar.c();
                bVar.d();
                TXUGCRecord.this.mTXFFQuickJoiner = null;
                TXUGCRecord.this.mRecordState = 1;
            }

            public void a(b bVar, float f) {
                TXCLog.i(TXUGCRecord.TAG, "joiner progress " + f);
            }
        });
        if (this.mTXFFQuickJoiner.a(this.mTXUGCPartsManager.getPartsPathList()) != 0) {
            TXLog.e(TAG, "composeRecord, quick joiner set src path err");
            return -4;
        } else if (this.mTXFFQuickJoiner.a(this.mVideoFilePath) != 0) {
            TXLog.e(TAG, "composeRecord, quick joiner set dst path err, mVideoFilePath = " + this.mVideoFilePath);
            return -5;
        } else if (this.mTXFFQuickJoiner.b() == 0) {
            return 0;
        } else {
            TXLog.e(TAG, "composeRecord, quick joiner start fail");
            return -6;
        }
    }

    public void release() {
        TXCAudioUGCRecorder.getInstance().stopRecord();
        this.mTXCloudVideoView = null;
        this.mRecordState = 1;
        this.mRenderMode = 0;
        if (this.mVideoPreprocessor != null) {
            this.mVideoPreprocessor.a(null);
        }
    }

    private int stopRecordForClip() {
        if (this.mRecording) {
            int b;
            this.mRecording = false;
            TXCAudioUGCRecorder.getInstance().pause();
            if (this.mVideoEncoder != null) {
                this.mVideoEncoder.b();
            }
            this.mStartMuxer = false;
            stopEncoder(this.mVideoEncoder);
            this.mVideoEncoder = null;
            synchronized (this) {
                if (this.mMP4Muxer != null) {
                    b = this.mMP4Muxer.b();
                    this.mMP4Muxer = null;
                } else {
                    b = 0;
                }
            }
            File file = new File(this.mVideoFileCurTempPath);
            if (b != 0) {
                if (file.exists()) {
                    asyncDeleteFile(this.mVideoFileCurTempPath);
                    this.mVideoFileCurTempPath = null;
                }
                TXCLog.e(TAG, "stopRecordForClip, maybe mMP4Muxer not write data");
                if (!TextUtils.isEmpty(this.mBGMPath)) {
                    this.mBGMDeletePart = true;
                }
                if (!(this.needCompose || this.isReachedMaxDuration)) {
                    return -3;
                }
            }
            if (TextUtils.isEmpty(this.mVideoFileCurTempPath) || !file.exists() || file.length() == 0) {
                TXCLog.e(TAG, "stopRecordForClip, file err ---> path = " + this.mVideoFileCurTempPath);
                if (!TextUtils.isEmpty(this.mBGMPath)) {
                    this.mBGMDeletePart = true;
                }
                if (!(this.needCompose || this.isReachedMaxDuration)) {
                    return -3;
                }
            }
            TXCLog.i(TAG, "stopRecordForClip, tempVideoFile exist, path = " + this.mVideoFileCurTempPath + ", length = " + file.length());
            PartInfo partInfo = new PartInfo();
            partInfo.setPath(this.mVideoFileCurTempPath);
            partInfo.setDuration(this.mCurrentRecordDuration);
            this.mTXUGCPartsManager.addClipInfo(partInfo);
            if (!TextUtils.isEmpty(this.mBGMPath)) {
                long curPosition = TXCUGCBGMPlayer.getInstance().getCurPosition() - TXCAudioUGCRecorder.getInstance().getPcmCacheLen();
                TXCLog.i(TAG, "stopRecordForClip, bgmCurProgress = " + curPosition + ", bgm player position = " + TXCUGCBGMPlayer.getInstance().getCurPosition() + ", record bgm cache = " + TXCAudioUGCRecorder.getInstance().getPcmCacheLen());
                this.mBgmPartBytesList.add(Long.valueOf(curPosition));
                this.mBGMDeletePart = false;
            }
            callbackEventPause();
            String str = this.mCoverCurTempPath;
            if (!TextUtils.isEmpty(this.mCoverCurTempPath)) {
                this.mCoverCurTempPath = null;
            }
            asyncGenCoverAndDetermineCompose(str);
            return 0;
        }
        TXCLog.e(TAG, "stopRecordForClip: there is no existing uncompleted record task");
        return -2;
    }

    private void asyncGenCoverAndDetermineCompose(final String str) {
        AsyncTask.execute(new Runnable() {
            public void run() {
                try {
                    if (!(TextUtils.isEmpty(TXUGCRecord.this.mVideoFileCurTempPath) || TextUtils.isEmpty(str))) {
                        com.tencent.liteav.basic.util.a.a(TXUGCRecord.this.mVideoFileCurTempPath, str);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                TXUGCRecord.this.mMainHandler.post(new Runnable() {
                    public void run() {
                        TXCLog.i(TXUGCRecord.TAG, "stopRecordForClip, isReachedMaxDuration = " + TXUGCRecord.this.isReachedMaxDuration + ", needCompose = " + TXUGCRecord.this.needCompose);
                        int access$900;
                        if (TXUGCRecord.this.isReachedMaxDuration) {
                            TXUGCRecord.this.mRecordRetCode = 2;
                            access$900 = TXUGCRecord.this.composeRecord();
                            if (access$900 != 0) {
                                TXUGCRecord.this.callbackRecordFail(access$900);
                            }
                        } else if (TXUGCRecord.this.needCompose) {
                            TXUGCRecord.this.mRecordRetCode = 0;
                            TXUGCRecord.this.needCompose = false;
                            access$900 = TXUGCRecord.this.composeRecord();
                            if (access$900 != 0) {
                                TXUGCRecord.this.callbackRecordFail(access$900);
                            }
                        }
                    }
                });
            }
        });
    }

    private void callbackRecordFail(int i) {
        TXRecordResult tXRecordResult = new TXRecordResult();
        tXRecordResult.retCode = i;
        tXRecordResult.descMsg = "record video failed";
        if (this.mVideoRecordListener != null) {
            this.mVideoRecordListener.onRecordComplete(tXRecordResult);
        }
    }

    private void callbackRecordSuccess() {
        TXRecordResult tXRecordResult = new TXRecordResult();
        if (((long) this.mTXUGCPartsManager.getDuration()) < ((long) this.mMinDuration)) {
            this.mRecordRetCode = 1;
        }
        tXRecordResult.retCode = this.mRecordRetCode;
        tXRecordResult.descMsg = "record success";
        tXRecordResult.videoPath = this.mVideoFilePath;
        tXRecordResult.coverPath = this.mCoverPath;
        if (this.mVideoRecordListener != null) {
            this.mVideoRecordListener.onRecordComplete(tXRecordResult);
        }
    }

    private String getDefaultDir() {
        File file;
        if ("mounted".equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            String str = Environment.getExternalStorageDirectory() + File.separator + OUTPUT_DIR_NAME;
            file = new File(str);
            if (file.exists()) {
                return str;
            }
            file.mkdir();
            return str;
        }
        file = this.mContext.getFilesDir();
        if (file != null) {
            return file.getPath();
        }
        return null;
    }

    private String getTimeString() {
        return new SimpleDateFormat("yyyyMMdd_HHmmssSSS").format(new Date(System.currentTimeMillis()));
    }

    private void asyncDeleteFile(final String str) {
        if (str != null && !str.isEmpty()) {
            try {
                new AsyncTask() {
                    protected Object doInBackground(Object[] objArr) {
                        File file = new File(str);
                        if (file.isFile() && file.exists()) {
                            file.delete();
                        }
                        return null;
                    }
                }.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, new Object[0]);
            } catch (Exception e) {
                TXCLog.d(TAG, "asyncDeleteFile, exception = " + e);
            }
        }
    }

    public int pauseRecord() {
        if (this.mRecording) {
            TXCLog.i(TAG, "pauseRecord called");
            this.mRecordState = 3;
            return stopRecordForClip();
        }
        TXCLog.e(TAG, "pauseRecord: there is no existing uncompleted record task");
        return -2;
    }

    private void callbackEventPause() {
        this.mMainHandler.post(new Runnable() {
            public void run() {
                if (TXUGCRecord.this.mVideoRecordListener != null) {
                    TXUGCRecord.this.mVideoRecordListener.onRecordEvent(1, null);
                }
            }
        });
    }

    public int resumeRecord() {
        if (this.mRecording) {
            TXCLog.e(TAG, "resumeRecord: there is existing uncompleted record task");
            return -1;
        }
        TXCLog.i(TAG, "resumeRecord called");
        this.mVideoFileCurTempPath = this.mVideoFileTempDir + File.separator + String.format("temp_TXUGC_%s.mp4", new Object[]{getTimeString()});
        int startRecordInternal = startRecordInternal();
        callbackEventResume();
        return startRecordInternal;
    }

    private void callbackEventResume() {
        if (this.mVideoRecordListener != null) {
            this.mVideoRecordListener.onRecordEvent(2, null);
        }
    }

    private void changeRecordSpeed() {
        switch (this.mRecordSpeed) {
            case 0:
                TXCUGCBGMPlayer.getInstance().setSpeedRate(PLAY_SPEED_SLOWEST);
                TXCAudioUGCRecorder.getInstance().setSpeedRate(ENCODE_SPEED_SLOWEST);
                TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bk, this.mRecordSpeed, "SLOWEST");
                return;
            case 1:
                TXCUGCBGMPlayer.getInstance().setSpeedRate(PLAY_SPEED_SLOW);
                TXCAudioUGCRecorder.getInstance().setSpeedRate(ENCODE_SPEED_SLOW);
                TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bk, this.mRecordSpeed, "SLOW");
                return;
            case 2:
                TXCUGCBGMPlayer.getInstance().setSpeedRate(1.0f);
                TXCAudioUGCRecorder.getInstance().setSpeedRate(1.0f);
                TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bk, this.mRecordSpeed, "NORMAL");
                return;
            case 3:
                TXCUGCBGMPlayer.getInstance().setSpeedRate(PLAY_SPEED_FAST);
                TXCAudioUGCRecorder.getInstance().setSpeedRate(ENCODE_SPEED_FAST);
                TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bk, this.mRecordSpeed, "FAST");
                return;
            case 4:
                TXCUGCBGMPlayer.getInstance().setSpeedRate(PLAY_SPEED_FASTEST);
                TXCAudioUGCRecorder.getInstance().setSpeedRate(ENCODE_SPEED_FASTEST);
                TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bb);
                TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bk, this.mRecordSpeed, "FASTEST");
                return;
            default:
                return;
        }
    }

    public boolean setMicVolume(float f) {
        TXCAudioUGCRecorder.getInstance().setVolume(f);
        return true;
    }

    public boolean switchCamera(final boolean z) {
        this.mConfig.n = z;
        if (this.mVideoView != null) {
            this.mVideoView.a(new Runnable() {
                public void run() {
                    if (TXUGCRecord.this.mCameraCapture != null) {
                        TXUGCRecord.this.mCameraCapture.b();
                        TXUGCRecord.this.mVideoView.a(false);
                        TXUGCRecord.this.mCameraCapture.a(TXUGCRecord.this.mVideoView.getSurfaceTexture());
                        if (TXUGCRecord.this.mCameraCapture.b(z) == 0) {
                            TXUGCRecord.this.mCapturing = true;
                        } else {
                            TXUGCRecord.this.mCapturing = false;
                            TXUGCRecord.this.callbackEventCameraCannotUse();
                        }
                        TXUGCRecord.this.mVideoView.b();
                    }
                }
            });
        }
        return true;
    }

    public void setAspectRatio(int i) {
        this.mDisplayType = i;
        if (i == 0) {
            this.mCropWidth = this.mConfig.f;
            this.mCropHeight = this.mConfig.g;
            TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bd);
        } else if (i == 1) {
            this.mCropHeight = (((int) ((((float) this.mConfig.f) * 4.0f) / 3.0f)) / 16) * 16;
            this.mCropWidth = this.mConfig.f;
            TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bc);
        } else if (i == 2) {
            this.mCropHeight = this.mConfig.f;
            this.mCropWidth = this.mConfig.f;
            TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bb);
        }
    }

    public void snapshot(final ITXSnapshotListener iTXSnapshotListener) {
        if (!this.mSnapshotRunning && iTXSnapshotListener != null) {
            this.mSnapshotRunning = true;
            if (this.mVideoView != null) {
                this.mVideoView.a(new com.tencent.liteav.renderer.b.a() {
                    public void a(Bitmap bitmap) {
                        iTXSnapshotListener.onSnapshot(bitmap);
                    }
                });
            }
            this.mSnapshotRunning = false;
        }
    }

    public void setRecordSpeed(int i) {
        this.mRecordSpeed = i;
        if (!TextUtils.isEmpty(this.mBGMPath)) {
            changeRecordSpeed();
        }
    }

    public void setVideoProcessListener(VideoCustomProcessListener videoCustomProcessListener) {
        this.mCustomProcessListener = videoCustomProcessListener;
    }

    public void setReverb(int i) {
        TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bn, i, "");
        TXCAudioUGCRecorder.getInstance().setReverbType(i);
        TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.az);
    }

    public void setVoiceChangerType(int i) {
        switch (i) {
            case 1:
                this.mVoiceKind = 6;
                this.mVoiceEnvironment = -1;
                break;
            case 2:
                this.mVoiceKind = 4;
                this.mVoiceEnvironment = -1;
                break;
            case 3:
                this.mVoiceKind = 5;
                this.mVoiceEnvironment = -1;
                break;
            case 4:
                this.mVoiceKind = -1;
                this.mVoiceEnvironment = 9;
                break;
            case 6:
                this.mVoiceKind = -1;
                this.mVoiceEnvironment = 5;
                break;
            case 7:
                this.mVoiceKind = 13;
                this.mVoiceEnvironment = 1;
                break;
            case 8:
                this.mVoiceKind = 13;
                this.mVoiceEnvironment = -1;
                break;
            case 9:
                this.mVoiceKind = 10;
                this.mVoiceEnvironment = 4;
                break;
            case 10:
                this.mVoiceKind = 10;
                this.mVoiceEnvironment = 20;
                break;
            case 11:
                this.mVoiceKind = -1;
                this.mVoiceEnvironment = 2;
                break;
            default:
                this.mVoiceKind = -1;
                this.mVoiceEnvironment = -1;
                break;
        }
        TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bm, i, "");
        TXCAudioUGCRecorder.getInstance().setChangerType(this.mVoiceKind, this.mVoiceEnvironment);
    }

    public int setBGM(String str) {
        if (TextUtils.isEmpty(str)) {
            TXCLog.e(TAG, "setBGM, path is empty");
            stopBGM();
            TXCUGCBGMPlayer.getInstance().setOnPlayListener(null);
            return 0;
        }
        this.mBGMPath = str;
        TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bl);
        if (this.mOldBGMNotifyProxy == null) {
            this.mOldBGMNotifyProxy = new com.tencent.liteav.audio.f() {
                public void a() {
                    if (TXUGCRecord.this.mOldBGMNotify != null) {
                        TXUGCRecord.this.mOldBGMNotify.onBGMStart();
                    }
                }

                public void a(int i) {
                    if (TXUGCRecord.this.mOldBGMNotify != null) {
                        TXUGCRecord.this.mOldBGMNotify.onBGMComplete(0);
                    }
                    TXUGCRecord.this.mMainHandler.post(new Runnable() {
                        public void run() {
                            if (TXUGCRecord.this.mRecording) {
                                TXCUGCBGMPlayer.getInstance().stopPlay();
                                TXCUGCBGMPlayer.getInstance().playFromTime(TXUGCRecord.this.mBgmStartTime, TXUGCRecord.this.mBgmEndTime);
                                TXCUGCBGMPlayer.getInstance().startPlay(TXUGCRecord.this.mBGMPath);
                            }
                        }
                    });
                }

                public void a(long j, long j2) {
                    if (TXUGCRecord.this.mOldBGMNotify != null) {
                        TXUGCRecord.this.mOldBGMNotify.onBGMProgress(j, j2);
                    }
                }
            };
        }
        TXCUGCBGMPlayer.getInstance().setOnPlayListener(this.mOldBGMNotifyProxy);
        return (int) TXCUGCBGMPlayer.getDurationMS(str);
    }

    public void setBGMNofify(ITXBGMNotify iTXBGMNotify) {
        if (iTXBGMNotify == null) {
            this.mOldBGMNotify = null;
        } else {
            this.mOldBGMNotify = iTXBGMNotify;
        }
    }

    public boolean playBGMFromTime(int i, int i2) {
        if (TextUtils.isEmpty(this.mBGMPath)) {
            TXCLog.e(TAG, "playBGMFromTime, path is empty");
            return false;
        } else if (i < 0 || i2 < 0) {
            TXCLog.e(TAG, "playBGMFromTime, time is negative number");
            return false;
        } else if (i >= i2) {
            TXCLog.e(TAG, "playBGMFromTime, start time is bigger than end time");
            return false;
        } else {
            this.mBgmStartTime = (long) i;
            this.mBgmEndTime = (long) i2;
            this.mBGMDeletePart = false;
            this.mTXUGCPartsManager.setPartsManagerObserver(this);
            changeRecordSpeed();
            TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.aA);
            if (TXCAudioUGCRecorder.getInstance().isRecording()) {
                TXCAudioUGCRecorder.getInstance().enableBGMRecord(true);
                TXCAudioUGCRecorder.getInstance().setChannels(1);
                TXCAudioUGCRecorder.getInstance().startRecord(this.mContext);
            }
            TXCUGCBGMPlayer.getInstance().playFromTime((long) i, (long) i2);
            TXCUGCBGMPlayer.getInstance().startPlay(this.mBGMPath);
            return true;
        }
    }

    public boolean stopBGM() {
        this.mBGMPath = null;
        this.mTXUGCPartsManager.removePartsManagerObserver(this);
        TXCUGCBGMPlayer.getInstance().stopPlay();
        TXCAudioUGCRecorder.getInstance().enableBGMRecord(false);
        TXCUGCBGMPlayer.getInstance().setOnPlayListener(null);
        return true;
    }

    public boolean pauseBGM() {
        TXCUGCBGMPlayer.getInstance().pause();
        return true;
    }

    public boolean resumeBGM() {
        if (TextUtils.isEmpty(this.mBGMPath)) {
            TXCLog.e(TAG, "resumeBGM, mBGMPath is empty");
            return false;
        }
        changeRecordSpeed();
        if (this.mBGMDeletePart) {
            long j = 0;
            if (this.mBgmPartBytesList.size() > 0) {
                j = ((Long) this.mBgmPartBytesList.get(this.mBgmPartBytesList.size() - 1)).longValue();
            }
            TXCLog.i(TAG, "resumeBGM, curBGMBytesProgress = " + j);
            TXCUGCBGMPlayer.getInstance().seekBytes(j);
            TXCAudioUGCRecorder.getInstance().clearCache();
        }
        TXCUGCBGMPlayer.getInstance().resume();
        return true;
    }

    public boolean seekBGM(int i, int i2) {
        TXCUGCBGMPlayer.getInstance().playFromTime((long) i, (long) i2);
        return true;
    }

    public boolean setBGMVolume(float f) {
        TXCUGCBGMPlayer.getInstance().setVolume(f);
        return true;
    }

    public int getMusicDuration(String str) {
        return (int) TXCUGCBGMPlayer.getDurationMS(str);
    }

    public void setWatermark(Bitmap bitmap, TXRect tXRect) {
        if (this.mVideoPreprocessor != null && bitmap != null && tXRect != null) {
            this.mVideoPreprocessor.a(bitmap, tXRect.x, tXRect.y, tXRect.width);
        }
    }

    public void setMotionTmpl(String str) {
        if (this.mVideoPreprocessor != null) {
            this.mVideoPreprocessor.a(str);
        }
    }

    public void setMotionMute(boolean z) {
        if (this.mVideoPreprocessor != null) {
            this.mVideoPreprocessor.b(z);
        }
    }

    @TargetApi(18)
    public void setGreenScreenFile(String str, boolean z) {
        if (VERSION.SDK_INT >= 18) {
            TXCLog.e(TAG, "setGreenScreenFile, sdk version not support : 3");
        }
    }

    public void setFaceVLevel(int i) {
        TXCLog.e(TAG, "setFaceVLevel, sdk version not support : 3");
    }

    public void setFaceShortLevel(int i) {
        TXCLog.e(TAG, "setFaceShortLevel, sdk version not support : 3");
    }

    public void setChinLevel(int i) {
        TXCLog.e(TAG, "setChinLevel, sdk version not support : 3");
    }

    public void setNoseSlimLevel(int i) {
        TXCLog.e(TAG, "setNoseSlimLevel, sdk version not support : 3");
    }

    public void setEyeScaleLevel(float f) {
        TXCLog.e(TAG, "setEyeScaleLevel, sdk version not support : 3");
    }

    public void setFaceScaleLevel(float f) {
        TXCLog.e(TAG, "setFaceScaleLevel, sdk version not support : 3");
    }

    public void setBeautyStyle(int i) {
        if (this.mVideoPreprocessor != null) {
            this.mVideoPreprocessor.b(i);
        }
    }

    public void setBeautyDepth(int i, int i2, int i3, int i4) {
        if (this.mVideoPreprocessor != null) {
            this.mVideoPreprocessor.b(i);
            this.mVideoPreprocessor.c(i2);
            this.mVideoPreprocessor.d(i3);
            this.mVideoPreprocessor.e(i4);
        }
    }

    public void setFilter(Bitmap bitmap) {
        setFilter(bitmap, this.mSpecialRadio, null, 0.0f, 1.0f);
    }

    public void setFilter(Bitmap bitmap, float f, Bitmap bitmap2, float f2, float f3) {
        if (this.mVideoPreprocessor != null) {
            this.mVideoPreprocessor.a(f3, bitmap, f, bitmap2, f2);
        }
    }

    public void setSpecialRatio(float f) {
        this.mSpecialRadio = f;
        if (this.mVideoPreprocessor != null) {
            this.mVideoPreprocessor.a(f);
        }
    }

    private void setSharpenLevel(int i) {
        if (this.mVideoPreprocessor != null) {
            this.mVideoPreprocessor.f(i);
        }
    }

    public boolean toggleTorch(boolean z) {
        if (this.mCameraCapture != null) {
            this.mCameraCapture.a(z);
        }
        return true;
    }

    public int getMaxZoom() {
        return this.mCameraCapture.a();
    }

    public boolean setZoom(int i) {
        if (this.mCameraCapture != null) {
            return this.mCameraCapture.c(i);
        }
        return false;
    }

    public void setVideoRenderMode(int i) {
        if (i == 1) {
            this.mRenderMode = 1;
        } else {
            this.mRenderMode = 0;
        }
    }

    private int startCameraPreviewInternal(TXCloudVideoView tXCloudVideoView, com.tencent.liteav.d dVar) {
        TXCLog.i(TAG, "ugcRecord, startCameraPreviewInternal");
        this.mStartPreview = true;
        if (this.mTXCloudVideoView != null) {
            this.mTXCloudVideoView.removeVideoView();
        }
        this.mTXCloudVideoView = tXCloudVideoView;
        initConfig();
        calcVideoEncInfo();
        initModules();
        this.mInitCompelete = false;
        this.mVideoView.setRendMode(this.mRenderMode);
        this.mVideoView.setListener(this);
        return 0;
    }

    public void setHomeOrientation(int i) {
        this.mCameraOrientationReadyChange = i;
        resetRotation();
    }

    public void setRenderRotation(int i) {
        this.mRenderRotationReadyChange = i;
        resetRotation();
    }

    private void resetRotation() {
        if (this.mVideoView != null) {
            this.mVideoView.a(new Runnable() {
                public void run() {
                    if (TXUGCRecord.this.mRenderRotationReadyChange != -1) {
                        TXUGCRecord.this.mConfig.r = TXUGCRecord.this.mRenderRotationReadyChange;
                        TXUGCRecord.this.mRenderRotationReadyChange = -1;
                    }
                    if (TXUGCRecord.this.mCameraOrientationReadyChange != -1) {
                        TXUGCRecord.this.mConfig.q = TXUGCRecord.this.mCameraOrientationReadyChange;
                        TXUGCRecord.this.mCameraCapture.d(TXUGCRecord.this.mConfig.q);
                        TXUGCRecord.this.mCameraOrientationReadyChange = -1;
                    }
                }
            });
            return;
        }
        this.mConfig.r = this.mRenderRotationReadyChange;
        this.mConfig.q = this.mCameraOrientationReadyChange;
    }

    @TargetApi(16)
    private void addAudioTrack() {
        MediaFormat a = com.tencent.liteav.basic.util.a.a(TXCAudioUGCRecorder.getInstance().getSampleRate(), TXCAudioUGCRecorder.getInstance().getChannels(), 2);
        if (this.mMP4Muxer != null) {
            this.mMP4Muxer.b(a);
        }
    }

    private void initModules() {
        this.mVideoView = this.mTXCloudVideoView.getGLSurfaceView();
        if (this.mVideoView == null) {
            this.mVideoView = new com.tencent.liteav.renderer.b(this.mTXCloudVideoView.getContext());
            this.mTXCloudVideoView.addVideoView(this.mVideoView);
        }
        if (this.mCameraCapture == null) {
            this.mCameraCapture = new com.tencent.liteav.capturer.a();
            this.mCameraCapture.a(this.mConfig.p ? 7 : this.mCameraResolution);
            this.mCameraCapture.b(this.mConfig.c);
        }
        if (this.mVideoPreprocessor == null) {
            this.mVideoPreprocessor = new com.tencent.liteav.beauty.d(this.mContext, true);
        }
        this.mVideoPreprocessor.a((com.tencent.liteav.beauty.e) this);
        this.mVideoEncoder = null;
    }

    /* JADX WARNING: Missing block: B:21:?, code:
            return true;
     */
    /* JADX WARNING: Missing block: B:23:?, code:
            return false;
     */
    private boolean startCapture(android.graphics.SurfaceTexture r6) {
        /*
        r5 = this;
        r0 = 1;
        r1 = 0;
        monitor-enter(r5);
        r2 = "TXUGCRecord";
        r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00af }
        r3.<init>();	 Catch:{ all -> 0x00af }
        r4 = "startCapture, mCapturing = ";
        r3 = r3.append(r4);	 Catch:{ all -> 0x00af }
        r4 = r5.mCapturing;	 Catch:{ all -> 0x00af }
        r3 = r3.append(r4);	 Catch:{ all -> 0x00af }
        r4 = ", mCameraCapture = ";
        r3 = r3.append(r4);	 Catch:{ all -> 0x00af }
        r4 = r5.mCameraCapture;	 Catch:{ all -> 0x00af }
        r3 = r3.append(r4);	 Catch:{ all -> 0x00af }
        r3 = r3.toString();	 Catch:{ all -> 0x00af }
        com.tencent.liteav.basic.log.TXCLog.i(r2, r3);	 Catch:{ all -> 0x00af }
        if (r6 == 0) goto L_0x00ac;
    L_0x002e:
        r2 = r5.mCapturing;	 Catch:{ all -> 0x00af }
        if (r2 != 0) goto L_0x00ac;
    L_0x0032:
        r2 = r5.mCameraCapture;	 Catch:{ all -> 0x00af }
        r2.a(r6);	 Catch:{ all -> 0x00af }
        r2 = r5.mCameraCapture;	 Catch:{ all -> 0x00af }
        r3 = r5.mConfig;	 Catch:{ all -> 0x00af }
        r3 = r3.c;	 Catch:{ all -> 0x00af }
        r2.b(r3);	 Catch:{ all -> 0x00af }
        r2 = "TXUGCRecord";
        r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00af }
        r3.<init>();	 Catch:{ all -> 0x00af }
        r4 = "startCapture, setHomeOriention = ";
        r3 = r3.append(r4);	 Catch:{ all -> 0x00af }
        r4 = r5.mConfig;	 Catch:{ all -> 0x00af }
        r4 = r4.q;	 Catch:{ all -> 0x00af }
        r3 = r3.append(r4);	 Catch:{ all -> 0x00af }
        r3 = r3.toString();	 Catch:{ all -> 0x00af }
        com.tencent.liteav.basic.log.TXCLog.i(r2, r3);	 Catch:{ all -> 0x00af }
        r2 = r5.mCameraCapture;	 Catch:{ all -> 0x00af }
        r3 = r5.mConfig;	 Catch:{ all -> 0x00af }
        r3 = r3.q;	 Catch:{ all -> 0x00af }
        r2.d(r3);	 Catch:{ all -> 0x00af }
        r2 = r5.mCameraCapture;	 Catch:{ all -> 0x00af }
        r3 = r5.mConfig;	 Catch:{ all -> 0x00af }
        r3 = r3.n;	 Catch:{ all -> 0x00af }
        r2 = r2.b(r3);	 Catch:{ all -> 0x00af }
        if (r2 != 0) goto L_0x009a;
    L_0x0073:
        r1 = 1;
        r5.mCapturing = r1;	 Catch:{ all -> 0x00af }
        r1 = r5.mVideoView;	 Catch:{ all -> 0x00af }
        if (r1 == 0) goto L_0x0098;
    L_0x007a:
        r1 = r5.mVideoView;	 Catch:{ all -> 0x00af }
        r2 = r5.mConfig;	 Catch:{ all -> 0x00af }
        r2 = r2.c;	 Catch:{ all -> 0x00af }
        r1.setFPS(r2);	 Catch:{ all -> 0x00af }
        r1 = r5.mVideoView;	 Catch:{ all -> 0x00af }
        r1.setTextureListener(r5);	 Catch:{ all -> 0x00af }
        r1 = r5.mVideoView;	 Catch:{ all -> 0x00af }
        r1.setNotifyListener(r5);	 Catch:{ all -> 0x00af }
        r1 = r5.mVideoView;	 Catch:{ all -> 0x00af }
        r1.b();	 Catch:{ all -> 0x00af }
        r1 = r5.mVideoView;	 Catch:{ all -> 0x00af }
        r2 = 1;
        r1.c(r2);	 Catch:{ all -> 0x00af }
    L_0x0098:
        monitor-exit(r5);	 Catch:{ all -> 0x00af }
    L_0x0099:
        return r0;
    L_0x009a:
        r0 = 0;
        r5.mCapturing = r0;	 Catch:{ all -> 0x00af }
        r0 = "TXUGCRecord";
        r2 = "startCapture fail!";
        com.tencent.rtmp.TXLog.e(r0, r2);	 Catch:{ all -> 0x00af }
        r5.onRecordError();	 Catch:{ all -> 0x00af }
        monitor-exit(r5);	 Catch:{ all -> 0x00af }
        r0 = r1;
        goto L_0x0099;
    L_0x00ac:
        monitor-exit(r5);	 Catch:{ all -> 0x00af }
        r0 = r1;
        goto L_0x0099;
    L_0x00af:
        r0 = move-exception;
        monitor-exit(r5);	 Catch:{ all -> 0x00af }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.ugc.TXUGCRecord.startCapture(android.graphics.SurfaceTexture):boolean");
    }

    private void stopEncoder(final com.tencent.liteav.videoencoder.b bVar) {
        if (this.mVideoView != null) {
            this.mVideoView.a(new Runnable() {
                public void run() {
                    try {
                        if (bVar != null) {
                            bVar.a();
                            bVar.a(null);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void startEncoder(int i, int i2) {
        TXCLog.i(TAG, "New encode size width = " + i + " height = " + i2 + ", mVideoView = " + this.mVideoView);
        stopEncoder(this.mVideoEncoder);
        this.mVideoEncoder = null;
        EGLContext eglGetCurrentContext = ((EGL10) EGLContext.getEGL()).eglGetCurrentContext();
        this.mVideoWidth = i;
        this.mVideoHeight = i2;
        TXSVideoEncoderParam tXSVideoEncoderParam = new TXSVideoEncoderParam();
        tXSVideoEncoderParam.width = i;
        tXSVideoEncoderParam.height = i2;
        tXSVideoEncoderParam.fps = this.mConfig.c;
        tXSVideoEncoderParam.fullIFrame = this.mConfig.t;
        tXSVideoEncoderParam.glContext = eglGetCurrentContext;
        tXSVideoEncoderParam.annexb = true;
        tXSVideoEncoderParam.appendSpsPps = false;
        if (this.mUseSWEncoder) {
            this.mVideoEncoder = new com.tencent.liteav.videoencoder.b(2);
            tXSVideoEncoderParam.encoderMode = 1;
            tXSVideoEncoderParam.encoderProfile = 3;
        } else {
            this.mVideoEncoder = new com.tencent.liteav.videoencoder.b(1);
            tXSVideoEncoderParam.encoderMode = 3;
            tXSVideoEncoderParam.encoderProfile = 1;
        }
        tXSVideoEncoderParam.record = true;
        if (this.mConfig.t) {
            this.mVideoEncoder.a(24000);
        } else {
            this.mVideoEncoder.a(this.mConfig.d);
        }
        tXSVideoEncoderParam.realTime = false;
        tXSVideoEncoderParam.enableBlackList = false;
        this.mVideoEncoder.a((d) this);
        this.mVideoEncoder.a(tXSVideoEncoderParam);
    }

    private void encodeFrame(int i, int i2, int i3) {
        if (!(this.mVideoEncoder != null && this.mVideoWidth == i2 && this.mVideoHeight == i3)) {
            startEncoder(i2, i3);
        }
        long timeTick = TXCTimeUtil.getTimeTick();
        if (this.mRecordSpeed == 2) {
            this.mVideoEncoder.a(i, i2, i3, timeTick);
        } else if (this.mRecordSpeed == 3) {
            this.encodeIndex++;
            if (this.encodeIndex % 2 == 0) {
                this.mVideoEncoder.a(i, i2, i3, timeTick);
            }
        } else if (this.mRecordSpeed == 4) {
            this.encodeIndex++;
            if (this.encodeIndex % 4 == 0) {
                this.mVideoEncoder.a(i, i2, i3, timeTick);
            }
        } else if (this.mRecordSpeed == 1) {
            this.mVideoEncoder.a(i, i2, i3, timeTick);
        } else if (this.mRecordSpeed == 0) {
            this.mVideoEncoder.a(i, i2, i3, timeTick);
        }
    }

    private void onRecordError() {
        if (this.mVideoRecordListener != null && this.mRecording) {
            this.mMainHandler.post(new Runnable() {
                public void run() {
                    TXUGCRecord.this.stopRecordForClip();
                }
            });
            TXCUGCBGMPlayer.getInstance().pause();
            this.mRecording = false;
            this.mMainHandler.post(new Runnable() {
                public void run() {
                    TXRecordResult tXRecordResult = new TXRecordResult();
                    tXRecordResult.descMsg = "record video failed";
                    tXRecordResult.retCode = -1;
                    if (TXUGCRecord.this.mVideoRecordListener != null) {
                        TXUGCRecord.this.mVideoRecordListener.onRecordComplete(tXRecordResult);
                    }
                }
            });
        }
    }

    private void initConfig() {
        if (this.mConfig.a < 0) {
            switch (this.mConfig.b) {
                case 0:
                    this.mConfig.f = 360;
                    this.mConfig.g = FengConstant.IMAGEMIDDLEWIDTH;
                    this.mCameraResolution = 4;
                    TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.be, 360, "360x640");
                    break;
                case 1:
                    this.mConfig.f = 540;
                    this.mConfig.g = 960;
                    this.mCameraResolution = 5;
                    TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bf, 540, "540x960");
                    break;
                case 2:
                    this.mConfig.f = 720;
                    this.mConfig.g = 1280;
                    this.mCameraResolution = 6;
                    TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bg, 720, "720x1280");
                    break;
                default:
                    this.mConfig.f = 540;
                    this.mConfig.g = 960;
                    this.mCameraResolution = 5;
                    TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bf, 720, "720x1280");
                    break;
            }
        }
        switch (this.mConfig.a) {
            case 0:
                this.mConfig.b = 0;
                this.mConfig.f = 360;
                this.mConfig.g = FengConstant.IMAGEMIDDLEWIDTH;
                this.mConfig.d = 2400;
                this.mCameraResolution = 4;
                TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.be);
                TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bh, 2400, "");
                break;
            case 1:
                this.mConfig.b = 1;
                this.mConfig.f = 540;
                this.mConfig.g = 960;
                this.mConfig.d = 6500;
                this.mCameraResolution = 5;
                TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bf);
                TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bh, 6500, "");
                break;
            case 2:
                this.mConfig.b = 2;
                this.mConfig.f = 720;
                this.mConfig.g = 1280;
                this.mConfig.d = 9600;
                this.mCameraResolution = 6;
                TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bg);
                TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bh, 9600, "");
                break;
            default:
                this.mConfig.b = 1;
                this.mConfig.f = 540;
                this.mConfig.g = 960;
                this.mConfig.d = 6500;
                this.mCameraResolution = 5;
                TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bf);
                TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bh, 6500, "");
                break;
        }
        this.mConfig.c = this.mFps;
        TXCDRApi.txReportDAU(this.mContext, com.tencent.liteav.basic.datareport.a.bi, this.mFps, "");
        TXCLog.w(TAG, "record:camera init record param, width:" + this.mConfig.f + ",height:" + this.mConfig.g + ",bitrate:" + this.mConfig.d + ",fps:" + this.mConfig.c);
    }

    private void calcVideoEncInfo() {
        if (this.mConfig.g != 0) {
            double d = ((double) this.mConfig.f) / ((double) this.mConfig.g);
            this.mConfig.f = ((this.mConfig.f + 15) / 16) * 16;
            this.mConfig.g = ((this.mConfig.g + 15) / 16) * 16;
            double d2 = ((double) this.mConfig.f) / ((double) this.mConfig.g);
            double d3 = ((double) (this.mConfig.f + 16)) / ((double) this.mConfig.g);
            double d4 = ((double) (this.mConfig.f - 16)) / ((double) this.mConfig.g);
            com.tencent.liteav.d dVar = this.mConfig;
            int i = Math.abs(d2 - d) < Math.abs(d3 - d) ? Math.abs(d2 - d) < Math.abs(d4 - d) ? this.mConfig.f : this.mConfig.f - 16 : Math.abs(d3 - d) < Math.abs(d4 - d) ? this.mConfig.f + 16 : this.mConfig.f - 16;
            dVar.f = i;
        }
    }

    private boolean onRecordProgress(long j) {
        TXCLog.i(TAG, "onRecordProgress = " + j);
        if (this.mRecordSpeed != 2) {
            if (this.mRecordSpeed == 3) {
                j = (long) (((float) j) / ENCODE_SPEED_FAST);
            } else if (this.mRecordSpeed == 4) {
                j = (long) (((float) j) / ENCODE_SPEED_FASTEST);
            } else if (this.mRecordSpeed == 1) {
                j = (long) (((float) j) / ENCODE_SPEED_SLOW);
            } else if (this.mRecordSpeed == 0) {
                j = (long) (((float) j) / ENCODE_SPEED_SLOWEST);
            }
        }
        this.mCurrentRecordDuration = j;
        final long duration = ((long) this.mTXUGCPartsManager.getDuration()) + this.mCurrentRecordDuration;
        this.mMainHandler.post(new Runnable() {
            public void run() {
                if (TXUGCRecord.this.mVideoRecordListener != null) {
                    TXUGCRecord.this.mVideoRecordListener.onRecordProgress(duration);
                }
            }
        });
        if (duration >= ((long) this.mMaxDuration)) {
            this.isReachedMaxDuration = true;
            this.mMainHandler.post(new Runnable() {
                public void run() {
                    TXUGCRecord.this.stopRecordForClip();
                }
            });
            return false;
        }
        this.isReachedMaxDuration = false;
        return true;
    }

    private int getSreenRotation() {
        if (this.mContext == null || this.mContext.getResources().getConfiguration().orientation != 2) {
            return 0;
        }
        return 90;
    }

    private int getRecordState() {
        int minBufferSize = AudioRecord.getMinBufferSize(TXRecordCommon.AUDIO_SAMPLERATE_44100, 16, 2);
        AudioRecord audioRecord = new AudioRecord(0, TXRecordCommon.AUDIO_SAMPLERATE_44100, 16, 2, minBufferSize * 100);
        short[] sArr = new short[minBufferSize];
        try {
            audioRecord.startRecording();
            if (audioRecord.getRecordingState() != 3) {
                if (audioRecord != null) {
                    audioRecord.stop();
                    audioRecord.release();
                    TXCLog.i("CheckAudioPermission", "录音机被占用");
                }
                return 1;
            } else if (audioRecord.read(sArr, 0, sArr.length) <= 0) {
                if (audioRecord != null) {
                    audioRecord.stop();
                    audioRecord.release();
                }
                TXCLog.i("CheckAudioPermission", "录音的结果为空");
                return -1;
            } else if (audioRecord == null) {
                return 0;
            } else {
                audioRecord.stop();
                audioRecord.release();
                return 0;
            }
        } catch (Exception e) {
            if (audioRecord != null) {
                audioRecord.release();
                TXCLog.i("CheckAudioPermission", "无法进入录音初始状态");
            }
            return -1;
        }
    }

    private void callbackEventCameraCannotUse() {
        this.mMainHandler.post(new Runnable() {
            public void run() {
                if (TXUGCRecord.this.mVideoRecordListener != null) {
                    TXUGCRecord.this.mVideoRecordListener.onRecordEvent(3, null);
                }
            }
        });
    }

    private void callbackEventMicCannotUse() {
        this.mMainHandler.post(new Runnable() {
            public void run() {
                if (TXUGCRecord.this.mVideoRecordListener != null) {
                    TXUGCRecord.this.mVideoRecordListener.onRecordEvent(4, null);
                }
            }
        });
    }

    public void onRecordRawPcmData(byte[] bArr, long j, int i, int i2, int i3, boolean z) {
    }

    public void onRecordPcmData(byte[] bArr, long j, int i, int i2, int i3) {
    }

    public void onRecordEncData(byte[] bArr, long j, int i, int i2, int i3) {
        if (this.mMP4Muxer != null && this.mRecording) {
            byte[] bArr2 = bArr;
            this.mMP4Muxer.a(bArr2, 0, bArr.length, (j - this.mPauseTotalTimeMs) * 1000, 0);
        }
    }

    public void onRecordError(int i, String str) {
        if (i == TXEAudioDef.TXE_AUDIO_RECORD_ERR_NO_MIC_PERMIT) {
            TXLog.e(TAG, "onRecordError, audio no mic permit");
            onRecordError();
        }
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture) {
        TXCLog.i(TAG, "ugcRecord, onSurfaceTextureAvailable, surfaceTexture = " + surfaceTexture + ", mCapturing = " + this.mCapturing + ", mStartPreview = " + this.mStartPreview);
        if (this.mStartPreview && surfaceTexture != null) {
            if (!startCapture(surfaceTexture)) {
                callbackEventCameraCannotUse();
            } else if (TXCAudioUGCRecorder.getInstance().isRecording()) {
                this.mInitCompelete = true;
                return;
            } else if (getRecordState() == -1) {
                callbackEventMicCannotUse();
            }
            this.mInitCompelete = true;
        }
    }

    public void onSurfaceTextureDestroy(SurfaceTexture surfaceTexture) {
        TXCLog.i(TAG, "ugcRecord, onSurfaceTextureDestroy, surfaceTexture = " + surfaceTexture + ", mCapturing = " + this.mCapturing);
        if (this.mCustomProcessListener != null) {
            this.mCustomProcessListener.onTextureDestroyed();
        }
        if (this.mVideoPreprocessor != null) {
            this.mVideoPreprocessor.a();
        }
        if (this.mVideoEncoder != null) {
            this.mVideoEncoder.a();
            this.mVideoEncoder.a(null);
            this.mVideoEncoder = null;
        }
    }

    public void onNotifyEvent(int i, Bundle bundle) {
    }

    public int willAddWatermark(int i, int i2, int i3) {
        int onTextureCustomProcess;
        if (this.mCustomProcessListener != null) {
            onTextureCustomProcess = this.mCustomProcessListener.onTextureCustomProcess(i, i2, i3);
        } else {
            onTextureCustomProcess = i;
        }
        if (this.mVideoView != null) {
            this.mVideoView.a(onTextureCustomProcess, this.mVideoView.getWidth(), this.mVideoView.getHeight(), false, this.mConfig.r, i2, i3);
        }
        return onTextureCustomProcess;
    }

    public void didProcessFrame(int i, int i2, int i3, long j) {
        if (this.mRecording) {
            encodeFrame(i, i2, i3);
        }
    }

    public void didProcessFrame(byte[] bArr, int i, int i2, int i3, long j) {
    }

    public void didDetectFacePoints(float[] fArr) {
        if (this.mCustomProcessListener != null) {
            this.mCustomProcessListener.onDetectFacePoints(fArr);
        }
    }

    /* JADX WARNING: Missing block: B:26:?, code:
            return;
     */
    public void onEncodeNAL(com.tencent.liteav.basic.e.b r4, int r5) {
        /*
        r3 = this;
        if (r5 != 0) goto L_0x004d;
    L_0x0002:
        monitor-enter(r3);
        r0 = r3.mMP4Muxer;	 Catch:{ all -> 0x001a }
        if (r0 != 0) goto L_0x0009;
    L_0x0007:
        monitor-exit(r3);	 Catch:{ all -> 0x001a }
    L_0x0008:
        return;
    L_0x0009:
        if (r4 == 0) goto L_0x0018;
    L_0x000b:
        r0 = r4.a;	 Catch:{ all -> 0x001a }
        if (r0 == 0) goto L_0x0018;
    L_0x000f:
        r0 = r3.mStartMuxer;	 Catch:{ all -> 0x001a }
        if (r0 == 0) goto L_0x001d;
    L_0x0013:
        r0 = r4.a;	 Catch:{ all -> 0x001a }
        r3.recordVideoData(r4, r0);	 Catch:{ all -> 0x001a }
    L_0x0018:
        monitor-exit(r3);	 Catch:{ all -> 0x001a }
        goto L_0x0008;
    L_0x001a:
        r0 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x001a }
        throw r0;
    L_0x001d:
        r0 = r4.b;	 Catch:{ all -> 0x001a }
        if (r0 != 0) goto L_0x0018;
    L_0x0021:
        r0 = r4.a;	 Catch:{ all -> 0x001a }
        r1 = r3.mVideoWidth;	 Catch:{ all -> 0x001a }
        r2 = r3.mVideoHeight;	 Catch:{ all -> 0x001a }
        r0 = com.tencent.liteav.basic.util.a.a(r0, r1, r2);	 Catch:{ all -> 0x001a }
        if (r0 == 0) goto L_0x0047;
    L_0x002d:
        r1 = r3.mMP4Muxer;	 Catch:{ all -> 0x001a }
        r1.a(r0);	 Catch:{ all -> 0x001a }
        r0 = r3.mMP4Muxer;	 Catch:{ all -> 0x001a }
        r0.a();	 Catch:{ all -> 0x001a }
        r0 = 1;
        r3.mStartMuxer = r0;	 Catch:{ all -> 0x001a }
        r0 = 0;
        r3.mRecordStartTime = r0;	 Catch:{ all -> 0x001a }
        r0 = "TXUGCRecord";
        r1 = "onEncodeNAL, mMP4Muxer.start(), mStartMuxer = true";
        com.tencent.rtmp.TXLog.i(r0, r1);	 Catch:{ all -> 0x001a }
    L_0x0047:
        r0 = r4.a;	 Catch:{ all -> 0x001a }
        r3.recordVideoData(r4, r0);	 Catch:{ all -> 0x001a }
        goto L_0x0018;
    L_0x004d:
        r0 = "TXUGCRecord";
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "onEncodeNAL error: ";
        r1 = r1.append(r2);
        r1 = r1.append(r5);
        r1 = r1.toString();
        com.tencent.liteav.basic.log.TXCLog.e(r0, r1);
        goto L_0x0008;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.ugc.TXUGCRecord.onEncodeNAL(com.tencent.liteav.basic.e.b, int):void");
    }

    public void onEncodeFormat(MediaFormat mediaFormat) {
        synchronized (this) {
            Log.i(TAG, "onEncodeFormat: " + mediaFormat.toString());
            if (this.mMP4Muxer != null) {
                this.mMP4Muxer.a(mediaFormat);
                if (!this.mStartMuxer) {
                    this.mMP4Muxer.a();
                    this.mStartMuxer = true;
                    TXLog.i(TAG, "onEncodeFormat, mMP4Muxer.start(), mStartMuxer = true");
                }
            }
        }
    }

    private void recordVideoData(com.tencent.liteav.basic.e.b bVar, byte[] bArr) {
        if (this.mRecordStartTime == 0) {
            this.mRecordStartTime = bVar.g;
        }
        bVar.g -= this.mPauseTotalTimeMs;
        int i = bVar.k == null ? bVar.b == 0 ? 1 : 0 : bVar.k.flags;
        if (onRecordProgress(bVar.g - this.mRecordStartTime)) {
            this.mMP4Muxer.b(bArr, 0, bArr.length, bVar.g * 1000, i);
        }
    }

    public int onTextureProcess(int i, float[] fArr) {
        if (this.mVideoPreprocessor != null) {
            int i2 = this.mConfig.f;
            int i3 = this.mConfig.g;
            int i4 = this.mCropWidth;
            int i5 = this.mCropHeight;
            if (this.mConfig.q == 2 || this.mConfig.q == 0) {
                i2 = this.mConfig.g;
                i3 = this.mConfig.f;
                i4 = this.mCropHeight;
                i5 = this.mCropWidth;
            }
            if (this.mDisplayType != 0) {
                this.mVideoPreprocessor.a(com.tencent.liteav.basic.util.a.a(this.mCameraCapture.d(), this.mCameraCapture.e(), this.mCropHeight, this.mCropWidth));
                this.mVideoPreprocessor.a(i4, i5);
                this.mVideoView.setRendMode(1);
            } else {
                this.mVideoPreprocessor.a(com.tencent.liteav.basic.util.a.a(this.mCameraCapture.d(), this.mCameraCapture.e(), this.mConfig.g, this.mConfig.f));
                this.mVideoPreprocessor.a(i2, i3);
                this.mVideoView.setRendMode(this.mRenderMode);
            }
            this.mVideoPreprocessor.a(false);
            this.mVideoPreprocessor.a(this.mCameraCapture.c());
            this.mVideoPreprocessor.a(fArr);
            this.mVideoPreprocessor.a(i, this.mCameraCapture.d(), this.mCameraCapture.e(), this.mCameraCapture.c(), 4, 0);
        }
        return 0;
    }
}
