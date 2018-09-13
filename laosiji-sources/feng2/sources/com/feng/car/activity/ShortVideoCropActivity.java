package com.feng.car.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.View;
import com.feng.car.R;
import com.feng.car.databinding.ActivityShortvideoCropBinding;
import com.feng.car.event.ClosePageEvent;
import com.feng.car.utils.FengConstant;
import com.feng.car.video.VideoUtil;
import com.feng.car.video.shortvideo.VideoCropTimeline$ProgressChangedListener;
import com.feng.library.utils.StringUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.stub.StubApp;
import com.tencent.ugc.TXVideoEditConstants.TXGenerateResult;
import com.tencent.ugc.TXVideoEditConstants.TXPreviewParam;
import com.tencent.ugc.TXVideoEditConstants.TXVideoInfo;
import com.tencent.ugc.TXVideoEditer;
import com.tencent.ugc.TXVideoEditer.TXThumbnailListener;
import com.tencent.ugc.TXVideoEditer.TXVideoGenerateListener;
import com.tencent.ugc.TXVideoEditer.TXVideoPreviewListener;
import com.tencent.ugc.TXVideoInfoReader;
import java.lang.ref.WeakReference;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ShortVideoCropActivity extends BaseActivity<ActivityShortvideoCropBinding> implements TXVideoPreviewListener, TXVideoGenerateListener, TXThumbnailListener {
    private boolean isFirst = true;
    private int lastEndTime;
    private int lastStartTime;
    private int mCount;
    private int mCurrentState = 0;
    private int mEndTime = FengConstant.MAX_VIDEO_CROP;
    private TXPhoneStateListener mPhoneListener;
    private int mStartTime = 0;
    private TXVideoEditer mTXVideoEditer;
    private TXVideoInfo mTxVideoInfo;
    private String mVideoOutputPath;
    private String mVideoPath;

    static class TXPhoneStateListener extends PhoneStateListener {
        WeakReference<ShortVideoCropActivity> mEditer;

        public TXPhoneStateListener(ShortVideoCropActivity editer) {
            this.mEditer = new WeakReference(editer);
        }

        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            ShortVideoCropActivity activity = (ShortVideoCropActivity) this.mEditer.get();
            if (activity != null) {
                switch (state) {
                    case 1:
                    case 2:
                        if (activity.mCurrentState == 8) {
                            activity.stopGenerate();
                        }
                        activity.stopPlay();
                        return;
                    default:
                        return;
                }
            }
        }
    }

    public int setBaseContentView() {
        return R.layout.activity_shortvideo_crop;
    }

    public void initView() {
        closeSwip();
        hideDefaultTitleBar();
        getWindow().addFlags(128);
        this.mVideoPath = getIntent().getStringExtra("key_video_editer_path");
        if (StringUtil.isEmpty(this.mVideoPath)) {
            finish();
        } else {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        ShortVideoCropActivity.this.startInitPlayer();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    private void startInitPlayer() {
        this.mCurrentState = 0;
        this.mTXVideoEditer = new TXVideoEditer(this);
        if (this.mTXVideoEditer.setVideoPath(this.mVideoPath) != 0) {
            finish();
            return;
        }
        this.mTXVideoEditer.setTXVideoPreviewListener(this);
        runOnUiThread(new Runnable() {
            public void run() {
                ShortVideoCropActivity.this.initPlayerLayout();
                ((ActivityShortvideoCropBinding) ShortVideoCropActivity.this.mBaseBinding).parentLine.setVisibility(0);
                ShortVideoCropActivity.this.startPlay((long) (ShortVideoCropActivity.this.mStartTime * 1000), (long) (ShortVideoCropActivity.this.mEndTime * 1000));
            }
        });
        startInitVideoTimeLine();
        initListener();
    }

    private void startInitVideoTimeLine() {
        this.mTxVideoInfo = TXVideoInfoReader.getInstance().getVideoFileInfo(this.mVideoPath);
        if (this.mTxVideoInfo != null) {
            this.mCount = ((ActivityShortvideoCropBinding) this.mBaseBinding).videoCropTimeline.getBitmapCount(this, this.mTxVideoInfo.duration);
            this.mTXVideoEditer.getThumbnail(this.mCount, 100, 100, true, this);
        }
    }

    public void onThumbnail(int i, long l, final Bitmap bitmap) {
        runOnUiThread(new Runnable() {
            public void run() {
                ((ActivityShortvideoCropBinding) ShortVideoCropActivity.this.mBaseBinding).videoCropTimeline.addBitmap(bitmap);
            }
        });
    }

    private void initListener() {
        ((ActivityShortvideoCropBinding) this.mBaseBinding).videoCropTimeline.setProgressChangedListener(new VideoCropTimeline$ProgressChangedListener() {
            public void onProgressChanged(float startX, float distance) {
                if (ShortVideoCropActivity.this.isFirst) {
                    ShortVideoCropActivity.this.isFirst = false;
                    return;
                }
                ((ActivityShortvideoCropBinding) ShortVideoCropActivity.this.mBaseBinding).videoCropTimeline.resetProgress();
                ShortVideoCropActivity.this.mStartTime = (int) (((ActivityShortvideoCropBinding) ShortVideoCropActivity.this.mBaseBinding).videoCropTimeline.getSecondPerPx() * startX);
                ShortVideoCropActivity.this.mEndTime = (int) (((ActivityShortvideoCropBinding) ShortVideoCropActivity.this.mBaseBinding).videoCropTimeline.getSecondPerPx() * (startX + distance));
                if (ShortVideoCropActivity.this.lastStartTime == 0 && ShortVideoCropActivity.this.lastEndTime == 0) {
                    ShortVideoCropActivity.this.lastStartTime = ShortVideoCropActivity.this.mStartTime;
                    ShortVideoCropActivity.this.lastEndTime = ShortVideoCropActivity.this.mEndTime;
                } else if (ShortVideoCropActivity.this.lastStartTime != ShortVideoCropActivity.this.mStartTime || ShortVideoCropActivity.this.lastEndTime != ShortVideoCropActivity.this.mEndTime) {
                    ShortVideoCropActivity.this.lastStartTime = ShortVideoCropActivity.this.mStartTime;
                    ShortVideoCropActivity.this.lastEndTime = ShortVideoCropActivity.this.mEndTime;
                    ShortVideoCropActivity.this.pausePlay();
                    ShortVideoCropActivity.this.mCurrentState = 4;
                    ShortVideoCropActivity.this.mTXVideoEditer.previewAtTime((long) (ShortVideoCropActivity.this.mStartTime * 1000));
                }
            }

            public void onStartProgressChanged(float startX, float distance) {
                ((ActivityShortvideoCropBinding) ShortVideoCropActivity.this.mBaseBinding).videoCropTimeline.resetProgress();
                ShortVideoCropActivity.this.mStartTime = (int) (((ActivityShortvideoCropBinding) ShortVideoCropActivity.this.mBaseBinding).videoCropTimeline.getSecondPerPx() * startX);
                ShortVideoCropActivity.this.mEndTime = (int) (((ActivityShortvideoCropBinding) ShortVideoCropActivity.this.mBaseBinding).videoCropTimeline.getSecondPerPx() * (startX + distance));
                if (ShortVideoCropActivity.this.lastStartTime == 0 && ShortVideoCropActivity.this.lastEndTime == 0) {
                    ShortVideoCropActivity.this.lastStartTime = ShortVideoCropActivity.this.mStartTime;
                    ShortVideoCropActivity.this.lastEndTime = ShortVideoCropActivity.this.mEndTime;
                } else if (ShortVideoCropActivity.this.lastStartTime != ShortVideoCropActivity.this.mStartTime || ShortVideoCropActivity.this.lastEndTime != ShortVideoCropActivity.this.mEndTime) {
                    ShortVideoCropActivity.this.lastStartTime = ShortVideoCropActivity.this.mStartTime;
                    ShortVideoCropActivity.this.lastEndTime = ShortVideoCropActivity.this.mEndTime;
                    ShortVideoCropActivity.this.pausePlay();
                    ShortVideoCropActivity.this.mCurrentState = 4;
                    ShortVideoCropActivity.this.mTXVideoEditer.previewAtTime((long) (ShortVideoCropActivity.this.mStartTime * 1000));
                }
            }

            public void onEndProgressChanged(float startX, float distance) {
                ((ActivityShortvideoCropBinding) ShortVideoCropActivity.this.mBaseBinding).videoCropTimeline.resetProgress();
                ShortVideoCropActivity.this.mStartTime = (int) (((ActivityShortvideoCropBinding) ShortVideoCropActivity.this.mBaseBinding).videoCropTimeline.getSecondPerPx() * startX);
                ShortVideoCropActivity.this.mEndTime = (int) (((ActivityShortvideoCropBinding) ShortVideoCropActivity.this.mBaseBinding).videoCropTimeline.getSecondPerPx() * (startX + distance));
                if (ShortVideoCropActivity.this.lastStartTime == 0 && ShortVideoCropActivity.this.lastEndTime == 0) {
                    ShortVideoCropActivity.this.lastStartTime = ShortVideoCropActivity.this.mStartTime;
                    ShortVideoCropActivity.this.lastEndTime = ShortVideoCropActivity.this.mEndTime;
                } else if (ShortVideoCropActivity.this.lastStartTime != ShortVideoCropActivity.this.mStartTime || ShortVideoCropActivity.this.lastEndTime != ShortVideoCropActivity.this.mEndTime) {
                    ShortVideoCropActivity.this.lastStartTime = ShortVideoCropActivity.this.mStartTime;
                    ShortVideoCropActivity.this.lastEndTime = ShortVideoCropActivity.this.mEndTime;
                    ShortVideoCropActivity.this.pausePlay();
                    ShortVideoCropActivity.this.mCurrentState = 4;
                    ShortVideoCropActivity.this.mTXVideoEditer.previewAtTime((long) (ShortVideoCropActivity.this.mEndTime * 1000));
                }
            }
        });
        ((ActivityShortvideoCropBinding) this.mBaseBinding).cancelText.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                ShortVideoCropActivity.this.finish();
            }
        });
        ((ActivityShortvideoCropBinding) this.mBaseBinding).playButton.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (ShortVideoCropActivity.this.mCurrentState == 0 || ShortVideoCropActivity.this.mCurrentState == 4) {
                    ShortVideoCropActivity.this.startPlay((long) (ShortVideoCropActivity.this.mStartTime * 1000), (long) (ShortVideoCropActivity.this.mEndTime * 1000));
                } else if (ShortVideoCropActivity.this.mCurrentState == 2 || ShortVideoCropActivity.this.mCurrentState == 1) {
                    ShortVideoCropActivity.this.pausePlay();
                } else if (ShortVideoCropActivity.this.mCurrentState == 3) {
                    ShortVideoCropActivity.this.resumePlay();
                }
            }
        });
        ((ActivityShortvideoCropBinding) this.mBaseBinding).editerLayoutPlayer.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (ShortVideoCropActivity.this.mCurrentState == 0 || ShortVideoCropActivity.this.mCurrentState == 4) {
                    ShortVideoCropActivity.this.startPlay((long) (ShortVideoCropActivity.this.mStartTime * 1000), (long) (ShortVideoCropActivity.this.mEndTime * 1000));
                } else if (ShortVideoCropActivity.this.mCurrentState == 2 || ShortVideoCropActivity.this.mCurrentState == 1) {
                    ShortVideoCropActivity.this.pausePlay();
                } else if (ShortVideoCropActivity.this.mCurrentState == 3) {
                    ShortVideoCropActivity.this.resumePlay();
                }
            }
        });
        ((ActivityShortvideoCropBinding) this.mBaseBinding).completText.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                ShortVideoCropActivity.this.startGenerateVideo();
            }
        });
        ((ActivityShortvideoCropBinding) this.mBaseBinding).progressLine.setOnClickListener(this);
        initPhoneListener();
    }

    private void startGenerateVideo() {
        showCropProgressDialog();
        startPlay((long) (this.mStartTime * 1000), (long) (this.mEndTime * 1000));
        stopPlay();
        this.mTXVideoEditer.cancel();
        this.mCurrentState = 8;
        ((ActivityShortvideoCropBinding) this.mBaseBinding).completText.setEnabled(false);
        ((ActivityShortvideoCropBinding) this.mBaseBinding).completText.setClickable(false);
        this.mVideoOutputPath = VideoUtil.generateVideoPath();
        makeupTime();
        this.mTXVideoEditer.setCutFromTime((long) (this.mStartTime * 1000), (long) (this.mEndTime * 1000));
        this.mTXVideoEditer.setVideoGenerateListener(this);
        this.mTXVideoEditer.generateVideo(3, this.mVideoOutputPath);
    }

    private void stopGenerate() {
        if (this.mCurrentState == 8) {
            ((ActivityShortvideoCropBinding) this.mBaseBinding).completText.setEnabled(true);
            ((ActivityShortvideoCropBinding) this.mBaseBinding).completText.setClickable(true);
            this.mCurrentState = 0;
            if (this.mTXVideoEditer != null) {
                this.mTXVideoEditer.cancel();
            }
        }
        hideCropProgressDialog();
    }

    private void makeupTime() {
        if (this.mEndTime - this.mStartTime < 10) {
            int time = 10 - (this.mEndTime - this.mStartTime);
            if (this.mStartTime == 0) {
                this.mEndTime += time;
            } else if (this.mEndTime == FengConstant.MAX_VIDEO_CROP) {
                this.mStartTime -= time;
            } else if (time == 1) {
                this.mStartTime -= time;
            } else {
                this.mStartTime -= time / 2;
                this.mEndTime += time / 2;
            }
        }
    }

    private void initPlayerLayout() {
        TXPreviewParam param = new TXPreviewParam();
        param.videoView = ((ActivityShortvideoCropBinding) this.mBaseBinding).editerFlVideo;
        param.renderMode = 2;
        this.mTXVideoEditer.initWithPreview(param);
    }

    public void startPlay(long startTime, long endTime) {
        if (this.mCurrentState == 0 || this.mCurrentState == 4 || this.mCurrentState == 6) {
            ((ActivityShortvideoCropBinding) this.mBaseBinding).videoCropTimeline.resetProgress();
            this.mTXVideoEditer.startPlayFromTime(startTime, endTime);
            this.mCurrentState = 1;
            ((ActivityShortvideoCropBinding) this.mBaseBinding).playButton.setVisibility(8);
        }
    }

    public void resumePlay() {
        if (this.mCurrentState == 3) {
            this.mTXVideoEditer.resumePlay();
            this.mCurrentState = 1;
            ((ActivityShortvideoCropBinding) this.mBaseBinding).playButton.setVisibility(8);
        }
    }

    public void pausePlay() {
        if (this.mCurrentState == 2 || this.mCurrentState == 1) {
            this.mTXVideoEditer.pausePlay();
            this.mCurrentState = 3;
            ((ActivityShortvideoCropBinding) this.mBaseBinding).playButton.setVisibility(0);
        }
    }

    public void stopPlay() {
        if (this.mCurrentState == 2 || this.mCurrentState == 1 || this.mCurrentState == 6 || this.mCurrentState == 3) {
            this.mTXVideoEditer.stopPlay();
            this.mCurrentState = 4;
            ((ActivityShortvideoCropBinding) this.mBaseBinding).playButton.setVisibility(0);
        }
    }

    protected void onPause() {
        super.onPause();
        pausePlay();
    }

    protected void onDestroy() {
        super.onDestroy();
        release();
    }

    private void release() {
        if (this.mPhoneListener != null) {
            ((TelephonyManager) StubApp.getOrigApplicationContext(getApplicationContext()).getSystemService("phone")).listen(this.mPhoneListener, 0);
        }
        if (this.mTXVideoEditer != null) {
            stopPlay();
            this.mTXVideoEditer.setVideoGenerateListener(null);
            this.mTXVideoEditer.release();
            this.mTXVideoEditer = null;
        }
    }

    public void onPreviewProgress(final int i) {
        if (this.mCurrentState == 1) {
            runOnUiThread(new Runnable() {
                public void run() {
                    ((ActivityShortvideoCropBinding) ShortVideoCropActivity.this.mBaseBinding).videoCropTimeline.updateProgressBar(((((float) i) * 1.0f) / 1000000.0f) - ((float) ShortVideoCropActivity.this.mStartTime));
                }
            });
        }
    }

    public void onPreviewFinished() {
        stopPlay();
        runOnUiThread(new Runnable() {
            public void run() {
                ((ActivityShortvideoCropBinding) ShortVideoCropActivity.this.mBaseBinding).videoCropTimeline.resetProgress();
            }
        });
    }

    public void onGenerateProgress(final float v) {
        runOnUiThread(new Runnable() {
            public void run() {
                ShortVideoCropActivity.this.initProgressText(v, false);
            }
        });
    }

    public void onGenerateComplete(final TXGenerateResult txGenerateResult) {
        runOnUiThread(new Runnable() {
            public void run() {
                ShortVideoCropActivity.this.hideCropProgressDialog();
                if (txGenerateResult.retCode == 0) {
                    Intent intent = new Intent(ShortVideoCropActivity.this, ShortVideoSelectImageActivity.class);
                    intent.putExtra("key_video_editer_path", ShortVideoCropActivity.this.mVideoOutputPath);
                    ShortVideoCropActivity.this.startActivity(intent);
                    ShortVideoCropActivity.this.release();
                    ShortVideoCropActivity.this.finish();
                } else {
                    ShortVideoCropActivity.this.showSecondTypeToast("裁剪失败：" + txGenerateResult.descMsg);
                }
                ((ActivityShortvideoCropBinding) ShortVideoCropActivity.this.mBaseBinding).completText.setEnabled(true);
                ((ActivityShortvideoCropBinding) ShortVideoCropActivity.this.mBaseBinding).completText.setClickable(true);
                ShortVideoCropActivity.this.mCurrentState = 0;
            }
        });
    }

    private void initProgressText(float v, boolean isProcessVideo) {
        float progress = v * 100.0f;
        if (isProcessVideo) {
            ((ActivityShortvideoCropBinding) this.mBaseBinding).progress.setPercent(progress);
        } else {
            ((ActivityShortvideoCropBinding) this.mBaseBinding).progress.setPercent(progress);
        }
    }

    private void showCropProgressDialog() {
        ((ActivityShortvideoCropBinding) this.mBaseBinding).progress.setPercent(0.0f);
        ((ActivityShortvideoCropBinding) this.mBaseBinding).progressLine.setVisibility(0);
    }

    private void hideCropProgressDialog() {
        ((ActivityShortvideoCropBinding) this.mBaseBinding).progressLine.setVisibility(8);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4 && this.mCurrentState == 8) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ClosePageEvent event) {
        finish();
    }

    private void initPhoneListener() {
        if (this.mPhoneListener == null) {
            this.mPhoneListener = new TXPhoneStateListener(this);
            ((TelephonyManager) StubApp.getOrigApplicationContext(getApplicationContext()).getSystemService("phone")).listen(this.mPhoneListener, 32);
        }
    }
}
