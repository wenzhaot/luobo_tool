package com.feng.car.video.player;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Build.VERSION;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import com.feng.car.activity.BaseActivity;
import com.feng.car.entity.thread.MediaInfo;
import com.feng.car.video.VideoUtil;
import com.feng.car.video.download.VideoDownloadManager;
import com.github.jdsjlzx.R;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.umeng.analytics.pro.j.a;

public abstract class JCVideoPlayer extends FrameLayout implements OnClickListener, OnSeekBarChangeListener, OnTouchListener {
    public static final int BLUE_DEFINITION_PLAYING = 4;
    public static long CLICK_QUIT_FULLSCREEN_TIME = 0;
    public static final int CURRENT_STATE_AUTO_COMPLETE = 6;
    public static final int CURRENT_STATE_ERROR = 7;
    public static final int CURRENT_STATE_NORMAL = 0;
    public static final int CURRENT_STATE_PAUSE = 5;
    public static final int CURRENT_STATE_PLAYING = 2;
    public static final int CURRENT_STATE_PLAYING_BUFFERING_START = 3;
    public static final int CURRENT_STATE_PREPARING = 1;
    public static final int FULLSCREEN_ID = 33797;
    public static int FULLSCREEN_ORIENTATION = 6;
    public static final int FULL_SCREEN_NORMAL_DELAY = 800;
    public static final int HIGH_DEFINITION_PLAYING = 2;
    protected static JCUserAction JC_USER_EVENT = null;
    public static final int NORMAL_DEFINITION_PLAYING = 1;
    public static int NORMAL_ORIENTATION = 1;
    public static final int SCREEN_LAYOUT_LIST = 1;
    public static final int SCREEN_LAYOUT_NORMAL = 0;
    public static final int SCREEN_WINDOW_FULLSCREEN = 2;
    public static final int SCREEN_WINDOW_TINY = 3;
    public static final int SUPER_DEFINITION_PLAYING = 3;
    public static final int THRESHOLD = 80;
    public static long lastAutoFullscreenTime = 0;
    public ViewGroup bottomContainer;
    public int currentScreen = -1;
    public TextView currentTimeTextView;
    public ImageView fullscreenButton;
    public boolean isDoubleClick = false;
    public boolean isVideoFinalPage = false;
    private long lastDownTime;
    protected AudioManager mAudioManager;
    protected boolean mChangePosition;
    protected boolean mChangeVolume;
    public Context mContext;
    protected int mCurrentPlaying = 3;
    public int mCurrentPosition = -1;
    public int mCurrentState = -1;
    protected String mCurrentUrl = "";
    protected int mDownPosition;
    protected float mDownX;
    protected float mDownY;
    public int mDuration;
    protected int mGestureDownBrightness;
    protected int mGestureDownVolume;
    protected Handler mHandler;
    public String mHash;
    public boolean mIsLanscape = false;
    public boolean mIsLateralVideo = true;
    public boolean mIsVideoCache;
    public boolean mIsVideoFinalPage = false;
    public MediaInfo mMediaInfo;
    public int mResourceId = 0;
    public int mResourceType = 2;
    protected int mSeekTimePosition;
    public int mSnsId;
    public String mTitle;
    public int mTotalTime;
    protected boolean mTouchingProgressBar;
    public VideoCloseClickListener mVideoCloseClickListener;
    public int mVideoId;
    public int normalScreenHeight;
    public Object[] objects = null;
    public TextView playTimeText;
    public SeekBar progressBar;
    public TextView snsTitle;
    public ImageView startButton;
    public TXCloudVideoView textureViewContainer;
    public ViewGroup topContainer;
    public TextView totalTimeTextView;

    public abstract int getLayoutId();

    public void setNormalScreenHeight(int h) {
        this.normalScreenHeight = h;
    }

    public void setTitle(String mTitle, boolean isShowTitle) {
        this.mTitle = mTitle;
        if (isShowTitle) {
            this.snsTitle.setText(mTitle);
            this.snsTitle.setVisibility(0);
            return;
        }
        this.snsTitle.setVisibility(8);
    }

    public void changeDefination(int definition) {
        VideoUtil.IGNORE_AUDIO_LOSS = true;
        this.mCurrentPlaying = definition;
        if (this.mCurrentPlaying == 1) {
            this.mCurrentUrl = this.mMediaInfo.getSDUrl();
        } else if (this.mCurrentPlaying == 2) {
            this.mCurrentUrl = this.mMediaInfo.getHDUrl();
        } else if (this.mCurrentPlaying == 3) {
            this.mCurrentUrl = this.mMediaInfo.getFHDUrl();
        } else if (this.mCurrentPlaying == 4) {
            this.mCurrentUrl = this.mMediaInfo.getBDUrl();
        }
    }

    public void setState(MediaInfo mediaInfo, boolean flag, int current, int state) {
        this.mIsVideoCache = flag;
        this.mMediaInfo = mediaInfo;
        this.mCurrentPlaying = current;
        this.mCurrentState = state;
        this.mCurrentPosition = this.mMediaInfo.position;
        if (this.mCurrentPlaying == 1) {
            this.mCurrentUrl = this.mMediaInfo.getSDUrl();
        } else if (this.mCurrentPlaying == 2) {
            this.mCurrentUrl = this.mMediaInfo.getHDUrl();
        } else if (this.mCurrentPlaying == 3) {
            this.mCurrentUrl = this.mMediaInfo.getFHDUrl();
        } else if (this.mCurrentPlaying == 4) {
            this.mCurrentUrl = this.mMediaInfo.getBDUrl();
        }
        aftersetState(state);
    }

    public void aftersetState(int state) {
    }

    public void afterBackPress() {
    }

    public void afterOnCompletion() {
    }

    private void changeBrightness(float deltaY) {
        int brightness = this.mGestureDownBrightness + (((int) deltaY) / 3);
        if (brightness < 0) {
            brightness = 0;
        } else if (brightness > 255) {
            brightness = 255;
        }
        JCUtils.setCurWindowBrightness(getContext(), brightness);
        showBrightnessDialog((brightness * 100) / 255);
    }

    public void showBrightnessDialog(int volumePercent) {
    }

    public void dismissBrightnessDialog() {
    }

    private void changeVolume(float deltaY) {
        int max = this.mAudioManager.getStreamMaxVolume(3);
        this.mAudioManager.setStreamVolume(3, this.mGestureDownVolume + ((int) (((((float) max) * deltaY) * 3.0f) / ((float) getHeight()))), 0);
        showVolumeDialog((int) (((float) ((this.mGestureDownVolume * 100) / max)) + (((deltaY * 3.0f) * 100.0f) / ((float) getHeight()))));
    }

    public void showVolumeDialog(int volumePercent) {
    }

    public JCVideoPlayer(Context context) {
        super(context);
        init(context);
    }

    public JCVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        View.inflate(context, getLayoutId(), this);
        this.mContext = context;
        this.startButton = (ImageView) findViewById(R.id.start);
        this.fullscreenButton = (ImageView) findViewById(2131623961);
        this.progressBar = (SeekBar) findViewById(2131623994);
        this.currentTimeTextView = (TextView) findViewById(2131623953);
        this.playTimeText = (TextView) findViewById(2131623992);
        this.totalTimeTextView = (TextView) findViewById(2131624017);
        this.bottomContainer = (ViewGroup) findViewById(2131623981);
        this.textureViewContainer = (TXCloudVideoView) findViewById(2131624008);
        this.topContainer = (ViewGroup) findViewById(2131623982);
        this.snsTitle = (TextView) findViewById(2131624005);
        this.startButton.setOnClickListener(this);
        this.fullscreenButton.setOnClickListener(this);
        this.progressBar.setOnSeekBarChangeListener(this);
        this.bottomContainer.setOnClickListener(this);
        if (this.bottomContainer.getBackground() != null) {
            this.bottomContainer.getBackground().mutate().setAlpha(178);
        }
        this.textureViewContainer.setOnClickListener(this);
        this.textureViewContainer.setOnTouchListener(this);
        this.mAudioManager = (AudioManager) getContext().getSystemService("audio");
        this.mHandler = new Handler();
    }

    public void setUp(String url, int screen, Object... objects) {
        if (TextUtils.isEmpty(this.mCurrentUrl) || !TextUtils.equals(this.mCurrentUrl, url)) {
            this.mCurrentUrl = url;
            this.objects = objects;
            this.currentScreen = screen;
            setUiWitStateAndScreen(0);
        }
    }

    public void onClick(View v) {
        int i = v.getId();
        if (i == 2131623961) {
            if (this.mCurrentState != 6) {
                if (this.currentScreen == 2) {
                    backPress();
                    return;
                }
                JCVideoPlayerManager.mCurrentDirection = JCVideoPlayerManager.CURRENT_VIDEO_STATE_HAND_LANDSCAPE;
                startWindowFullscreen(false);
            }
        } else if (i == 2131624008 && this.mCurrentState == 7) {
            prepareMediaPlayer();
        }
    }

    public void prepareMediaPlayer() {
        JCVideoPlayerManager.completeAll();
        setUiWitStateAndScreen(1);
        JCVideoPlayerManager.setFirstFloor(this);
    }

    public void onDoubleClick() {
    }

    public boolean onTouch(View v, MotionEvent event) {
        if (JCUtils.isLockScreen()) {
            return true;
        }
        float x = event.getX();
        float y = event.getY();
        if (v.getId() == 2131624008) {
            switch (event.getAction()) {
                case 0:
                    if (this.currentScreen == 2) {
                        long currentDownTime = System.currentTimeMillis();
                        if (currentDownTime - this.lastDownTime < 500) {
                            onDoubleClick();
                            this.isDoubleClick = true;
                        }
                        this.lastDownTime = currentDownTime;
                    }
                    if (!this.isDoubleClick) {
                        this.mTouchingProgressBar = true;
                        this.mDownX = x;
                        this.mDownY = y;
                        this.mChangeVolume = false;
                        this.mChangePosition = false;
                        onEvent(13);
                        break;
                    }
                    break;
                case 1:
                    this.mTouchingProgressBar = false;
                    dismissProgressDialog();
                    dismissVolumeDialog();
                    dismissBrightnessDialog();
                    if (!this.isDoubleClick) {
                        if (this.mChangePosition) {
                            onEvent(12);
                            try {
                                JCMediaManager.instance().getPlayer().seek(this.mSeekTimePosition);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            int duration = getDuration();
                            int i = this.mSeekTimePosition * 100;
                            if (duration == 0) {
                                duration = 1;
                            }
                            this.progressBar.setProgress(i / duration);
                        }
                        if (this.mChangeVolume) {
                            onEvent(11);
                        }
                        if (this.currentScreen != 2) {
                            onEvent(14);
                        }
                    }
                    this.isDoubleClick = false;
                    break;
                case 2:
                    if (!this.isDoubleClick) {
                        float deltaX = x - this.mDownX;
                        float deltaY = y - this.mDownY;
                        float absDeltaX = Math.abs(deltaX);
                        float absDeltaY = Math.abs(deltaY);
                        if (this.currentScreen == 2 && this.mDownX < ((float) (getWidth() - 30))) {
                            if (!(this.mChangePosition || this.mChangeVolume || (absDeltaX <= 80.0f && absDeltaY <= 80.0f))) {
                                if (absDeltaX < 80.0f) {
                                    this.mChangeVolume = true;
                                    this.mGestureDownVolume = this.mAudioManager.getStreamVolume(3);
                                    this.mGestureDownBrightness = JCUtils.getScreenBrightness(getContext());
                                } else if (this.mCurrentState != 7) {
                                    this.mChangePosition = true;
                                    this.mDownPosition = getCurrentPositionWhenPlaying();
                                }
                            }
                            if (this.mChangePosition) {
                                int totalTimeDuration = getDuration();
                                this.mSeekTimePosition = this.mDownPosition + (((int) ((((float) totalTimeDuration) * deltaX) / ((float) getWidth()))) / 5);
                                if (this.mSeekTimePosition > totalTimeDuration) {
                                    this.mSeekTimePosition = totalTimeDuration;
                                } else if (this.mSeekTimePosition <= 0) {
                                    this.mSeekTimePosition = 0;
                                }
                                showProgressDialog(deltaX, JCUtils.stringFormateTimeFromSecond(this.mSeekTimePosition), this.mSeekTimePosition, JCUtils.stringFormateTimeFromSecond(totalTimeDuration), totalTimeDuration);
                            }
                            if (this.mChangeVolume) {
                                deltaY = -deltaY;
                                if (this.mDownX < ((float) (getWidth() / 2))) {
                                    changeBrightness(deltaY);
                                    break;
                                }
                                changeVolume(deltaY);
                                break;
                            }
                        }
                    }
                    break;
                case 3:
                    this.isDoubleClick = false;
                    if (this.currentScreen != 2) {
                        onEvent(14);
                        break;
                    }
                    break;
            }
        }
        return true;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.currentScreen == 2 || this.currentScreen == 3) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public void addTextureView() {
        JCMediaManager.instance().getPlayer().setPlayerView(this.textureViewContainer);
    }

    public void setUiWitStateAndScreen(int state) {
        this.mCurrentState = state;
        switch (this.mCurrentState) {
            case 0:
                if (isCurrentJcvd()) {
                    JCMediaManager.instance().releaseMediaPlayer();
                    return;
                }
                return;
            case 1:
                resetProgressAndTime();
                return;
            case 6:
                this.progressBar.setProgress(100);
                this.currentTimeTextView.setText(this.totalTimeTextView.getText() + "/");
                return;
            default:
                return;
        }
    }

    public void onPrepared() {
        VideoUtil.IGNORE_AUDIO_LOSS = false;
        if (this.mCurrentState == 1) {
            try {
                if (this.mCurrentPosition != -1) {
                    JCMediaManager.instance().getPlayer().seek(this.mCurrentPosition);
                    updatePosition(this.mCurrentPosition);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            setUiWitStateAndScreen(2);
        }
    }

    public void onAutoCompletion() {
        onEvent(6);
        dismissVolumeDialog();
        dismissProgressDialog();
        dismissBrightnessDialog();
        this.mCurrentState = 6;
        int progress = 0;
        if (this.mDuration != 0) {
            progress = (this.mCurrentPosition * 100) / this.mDuration;
        }
        if (!VideoUtil.IGNORE_AUDIO_LOSS && progress >= 99) {
            updatePosition(0);
            this.mDuration = 0;
        }
        afterOnCompletion();
        updatePositionToDataBase();
        JCUtils.saveProgress(getContext(), this.mCurrentUrl, 0);
    }

    public void updatePosition(int position) {
        this.mCurrentPosition = position;
    }

    public void updateMedaInfo() {
    }

    public void onCompletion() {
        updateMedaInfo();
        setUiWitStateAndScreen(0);
    }

    public void playOnThisJcvd() {
    }

    public void clearFloatScreen() {
        JCUtils.getAppCompActivity(getContext()).setRequestedOrientation(NORMAL_ORIENTATION);
        showSupportActionBar(getContext());
        JCVideoPlayer secJcvd = JCVideoPlayerManager.getCurrentJcvd();
        ((ViewGroup) JCUtils.scanForActivity(getContext()).findViewById(16908290)).removeView(secJcvd);
        secJcvd.destroyTXCloudVideoView();
    }

    public void onError(int what, int extra) {
        if (isCurrentJcvd()) {
            JCMediaManager.instance().releaseMediaPlayer();
        }
        setUiWitStateAndScreen(7);
    }

    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
        for (ViewParent vpdown = getParent(); vpdown != null; vpdown = vpdown.getParent()) {
            vpdown.requestDisallowInterceptTouchEvent(true);
        }
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        onEvent(5);
        for (ViewParent vpup = getParent(); vpup != null; vpup = vpup.getParent()) {
            vpup.requestDisallowInterceptTouchEvent(false);
        }
        if (this.mCurrentState == 2 || this.mCurrentState == 5) {
            try {
                JCMediaManager.instance().getPlayer().seek((seekBar.getProgress() * getDuration()) / 100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean backPress() {
        if (JCUtils.isLockScreen()) {
            return true;
        }
        if (System.currentTimeMillis() - CLICK_QUIT_FULLSCREEN_TIME < 800) {
            return false;
        }
        JCVideoPlayerManager.mCurrentDirection = JCVideoPlayerManager.CURRENT_VIDEO_STATE_VERTICAL;
        LayoutParams params;
        if (this.mIsLanscape) {
            CLICK_QUIT_FULLSCREEN_TIME = System.currentTimeMillis();
            JCUtils.getAppCompActivity(this.mContext).setRequestedOrientation(NORMAL_ORIENTATION);
            showSupportActionBar(this.mContext);
            this.mIsLanscape = false;
            params = getLayoutParams();
            params.width = -1;
            params.height = this.normalScreenHeight;
            setLayoutParams(params);
            JCMediaManager.instance().setFullScreen(false);
            this.currentScreen = 0;
            changeNormalScreenUI();
            onEvent(8);
            return true;
        } else if (!JCMediaManager.instance().isFullScreen()) {
            return false;
        } else {
            CLICK_QUIT_FULLSCREEN_TIME = System.currentTimeMillis();
            showSupportActionBar(this.mContext);
            params = getLayoutParams();
            params.width = -1;
            params.height = this.normalScreenHeight;
            setLayoutParams(params);
            JCMediaManager.instance().setFullScreen(false);
            this.currentScreen = 0;
            changeNormalScreenUI();
            onEvent(8);
            return true;
        }
    }

    public void autoFullscreen(float x) {
        if (!this.mIsLanscape) {
            if (x > 0.0f) {
                JCUtils.getAppCompActivity(getContext()).setRequestedOrientation(0);
            } else {
                JCUtils.getAppCompActivity(getContext()).setRequestedOrientation(8);
            }
            startWindowFullscreen(true);
        }
    }

    public void updateTime() {
    }

    public void changeFullScreenUI() {
    }

    public void changeNormalScreenUI() {
    }

    public void startWindowFullscreen(boolean isAutoRotate) {
        ((BaseActivity) this.mContext).hideProgress();
        ((BaseActivity) this.mContext).hideToastDialog();
        onEvent(7);
        boolean isShow = false;
        if (isAutoRotate) {
            isShow = true;
            this.mIsLanscape = true;
            ((Activity) this.mContext).setRequestedOrientation(FULLSCREEN_ORIENTATION);
        } else if (this.mIsLateralVideo) {
            isShow = true;
            this.mIsLanscape = true;
            ((Activity) this.mContext).setRequestedOrientation(FULLSCREEN_ORIENTATION);
        }
        this.currentScreen = 2;
        setUiWitStateAndScreen(this.mCurrentState);
        lastAutoFullscreenTime = System.currentTimeMillis();
        JCMediaManager.instance().setFullScreen(true);
        hideSupportActionBar(this.mContext);
        setTitle(this.mTitle, isShow);
        changeFullScreenUI();
        LayoutParams layoutParams = getLayoutParams();
        layoutParams.width = -1;
        layoutParams.height = -1;
        setLayoutParams(layoutParams);
    }

    public void setSnsInfo(int snsid, int videoid, int resourceId, int resourceType, String hash, int totaltime) {
        this.mSnsId = snsid;
        this.mVideoId = videoid;
        this.mResourceId = resourceId;
        this.mResourceType = resourceType;
        this.mHash = hash;
        this.mTotalTime = totaltime;
    }

    public int getCurrentPositionWhenPlaying() {
        if ((this.mCurrentState == 2 || this.mCurrentState == 5 || this.mCurrentState == 3) && JCMediaManager.instance().getPlayer() != null) {
            return (int) JCMediaManager.instance().getPlayer().getCurrentPlaybackTime();
        }
        return -1;
    }

    public int getDuration() {
        if (JCMediaManager.instance().getPlayer() != null) {
            this.mDuration = (int) JCMediaManager.instance().getPlayer().getDuration();
        }
        return this.mDuration;
    }

    public void updatePositionToDataBase() {
        if (this.mMediaInfo != null && getDuration() > 1) {
            if (!this.mMediaInfo.isComplete || this.mMediaInfo.position != 0) {
                VideoDownloadManager.newInstance().updateVideoPosition(this.mMediaInfo, getDuration());
            }
        }
    }

    public void setTextAndProgress() {
        if (JCMediaManager.instance().getPlayer() != null) {
            int progress;
            int secProgress;
            int position = getCurrentPositionWhenPlaying();
            int duration = getDuration();
            if (duration == 0) {
                progress = 0;
            } else {
                progress = (position * 100) / duration;
            }
            if (!(position == -1 || position == 0)) {
                updatePosition(position);
            }
            int secPosition = (int) JCMediaManager.instance().getPlayer().getBufferDuration();
            if (duration == 0) {
                secProgress = 0;
            } else {
                secProgress = (secPosition * 100) / duration;
            }
            setProgressAndTime(progress, secProgress, position, duration);
        }
    }

    public void setProgressAndTime(int progress, int secProgress, int currentTime, int totalTime) {
        if (!(this.mTouchingProgressBar || progress == 0)) {
            this.progressBar.setProgress(progress);
        }
        if (secProgress > 95) {
            secProgress = 100;
        }
        if (secProgress != 0) {
            this.progressBar.setSecondaryProgress(secProgress);
        }
        if (currentTime != 0) {
            this.currentTimeTextView.setText(JCUtils.stringFormateTimeFromSecond(currentTime) + "/");
        }
        this.totalTimeTextView.setText(JCUtils.stringFormateTimeFromSecond(totalTime));
    }

    public void resetProgressAndTime() {
        this.progressBar.setProgress(0);
        this.progressBar.setSecondaryProgress(0);
        this.currentTimeTextView.setText(JCUtils.stringFormateTimeFromSecond(0) + "/");
        this.totalTimeTextView.setText(JCUtils.stringFormateTimeFromSecond(0));
    }

    public boolean isCurrentJcvd() {
        return JCVideoPlayerManager.getCurrentJcvd() != null && JCVideoPlayerManager.getCurrentJcvd() == this;
    }

    public static void releaseAllVideos() {
        if (System.currentTimeMillis() - CLICK_QUIT_FULLSCREEN_TIME > 800) {
            JCVideoPlayerManager.completeAll();
            JCMediaManager.instance().releaseMediaPlayer();
        }
    }

    public static void setJcUserAction(JCUserAction jcUserEvent) {
        JC_USER_EVENT = jcUserEvent;
    }

    public void onEvent(int type) {
        if (JC_USER_EVENT != null && isCurrentJcvd()) {
            JC_USER_EVENT.onEvent(type, this.mCurrentUrl, this.currentScreen, this.objects);
        }
    }

    public void hideSupportActionBar(final Context context) {
        JCUtils.getAppCompActivity(context).getWindow().setFlags(1024, 1024);
        try {
            ((Activity) context).runOnUiThread(new Runnable() {
                public void run() {
                    if (VERSION.SDK_INT >= 19) {
                        ((Activity) context).getWindow().getDecorView().setSystemUiVisibility(a.f);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showSupportActionBar(Context context) {
        JCUtils.getAppCompActivity(context).getWindow().clearFlags(1024);
        try {
            ((Activity) context).getWindow().getDecorView().setSystemUiVisibility(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showProgressDialog(float deltaX, String seekTime, int seekTimePosition, String totalTime, int totalTimeDuration) {
    }

    public void dismissProgressDialog() {
    }

    public void dismissVolumeDialog() {
    }

    public void showLoadingProgress() {
    }

    public void hideLoadingProgress() {
    }

    public void setVideoCloseListener(VideoCloseClickListener l) {
        this.mVideoCloseClickListener = l;
    }

    public void destroyTXCloudVideoView() {
        if (this.textureViewContainer != null) {
            this.textureViewContainer.onDestroy();
        }
    }
}
