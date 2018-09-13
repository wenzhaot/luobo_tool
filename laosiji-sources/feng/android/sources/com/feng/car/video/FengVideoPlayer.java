package com.feng.car.video;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.drawee.drawable.ScalingUtils.ScaleType;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.feng.car.FengApplication;
import com.feng.car.activity.ArticleDetailActivity;
import com.feng.car.activity.BaseActivity;
import com.feng.car.activity.LoginActivity;
import com.feng.car.activity.VideoFinalPageActivity;
import com.feng.car.entity.thread.MediaInfo;
import com.feng.car.utils.FengConstant;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.HttpConstant;
import com.feng.car.utils.JsonUtil;
import com.feng.car.utils.LogConstans;
import com.feng.car.utils.LogGatherReadUtil;
import com.feng.car.utils.VideoManager;
import com.feng.car.video.download.VideoDownloadManager;
import com.feng.car.video.player.JCMediaManager;
import com.feng.car.video.player.JCUtils;
import com.feng.car.video.player.JCVideoPlayer;
import com.feng.car.video.player.JCVideoPlayerManager;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.feng.library.utils.DateUtil;
import com.feng.library.utils.SharedUtil;
import com.feng.library.utils.StringUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.uuzuche.lib_zxing.R;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class FengVideoPlayer extends JCVideoPlayer {
    protected Timer DISMISS_CONTROL_VIEW_TIMER;
    private RelativeLayout blueLine;
    private TextView blue_text;
    public ProgressBar bottomProgressBar;
    private View brightness_content;
    private int color_b6b6b6;
    private int color_ffb90a;
    private int color_ffffff;
    private RelativeLayout coverView;
    public TextView definitionText;
    private LinearLayout definition_line;
    public ImageView exit_line;
    private LinearLayout flowTipsLine;
    SimpleDateFormat formatter = new SimpleDateFormat(DateUtil.dateFormatHM);
    private TextView high_text;
    private boolean isFastClick = false;
    private long lastToFinalPageTime;
    private long lastUpTime = 0;
    public ProgressBar loadingProgressBar;
    public ImageView lockImage;
    private ImageView loginIcon;
    private int mAdId = 0;
    protected ProgressBar mDialogBrightnessProgressBar;
    protected ImageView mDialogIcon;
    protected TextView mDialogSeekTime;
    protected TextView mDialogTotalTime;
    protected ProgressBar mDialogVolumeProgressBar;
    protected DismissControlViewTimerTask mDismissControlViewTimerTask;
    private boolean mIsAD = false;
    private boolean mIsFeedList = false;
    public boolean mIsForcePlayHighUrl = false;
    private int mIsSpeed = 0;
    private boolean mIsWatchVideo = false;
    private String mKeySole;
    private String mLastJson;
    private String mLastUrl;
    private Map<String, String> mLogMap = new HashMap();
    private OptionListener mOptionListener;
    private String mPlaykey = "";
    private int mSeat = 0;
    private String mStrExpended = "";
    public ImageView networkTipsClose;
    public LinearLayout networkTipsLine;
    private TextView normal_text;
    public ImageView option;
    public RelativeLayout optionTipsLine;
    public TextView playCount;
    public TextView playTimeText;
    public ImageView play_icon;
    public RelativeLayout playingBottomLine;
    private LinearLayout progress_content;
    private View progress_view;
    public ImageView replay_line;
    private TextView sizeText;
    private TextView super_text;
    public SimpleDraweeView thumbImageView;
    private TextView timeText;
    public TextView tips;
    public ImageView video_close;
    private LinearLayout volume_content;

    private class DismissControlViewTimerTask extends TimerTask {
        private DismissControlViewTimerTask() {
        }

        /* synthetic */ DismissControlViewTimerTask(FengVideoPlayer x0, AnonymousClass1 x1) {
            this();
        }

        public void run() {
            if (FengVideoPlayer.this.mCurrentState != 0 && FengVideoPlayer.this.mCurrentState != 7 && FengVideoPlayer.this.mCurrentState != 6 && FengVideoPlayer.this.getContext() != null && (FengVideoPlayer.this.getContext() instanceof Activity)) {
                ((Activity) FengVideoPlayer.this.getContext()).runOnUiThread(new Runnable() {
                    public void run() {
                        FengVideoPlayer.this.bottomContainer.setVisibility(8);
                        FengVideoPlayer.this.topContainer.setVisibility(8);
                        FengVideoPlayer.this.startButton.setVisibility(8);
                        FengVideoPlayer.this.definition_line.setVisibility(8);
                        FengVideoPlayer.this.lockImage.setVisibility(8);
                        FengVideoPlayer.this.bottomProgressBar.setVisibility(0);
                    }
                });
            }
        }
    }

    public void setForcePlayHigh(boolean b) {
        this.mIsForcePlayHighUrl = b;
    }

    public void updateTime() {
        super.updateTime();
        this.timeText.setText(this.formatter.format(new Date(System.currentTimeMillis())));
    }

    public void setSnsInfo(int snsid, int resourceid, int resourcetype) {
        this.mSnsId = snsid;
        this.mResourceId = resourceid;
        this.mResourceType = resourcetype;
        this.mIsFeedList = false;
        this.mIsAD = false;
    }

    public void setSnsInfo(int snsid, int resourceid, int resourcetype, String strExpended, boolean isFeedList) {
        this.mSnsId = snsid;
        this.mResourceId = resourceid;
        this.mResourceType = resourcetype;
        this.mIsFeedList = isFeedList;
        this.mStrExpended = strExpended;
        this.mIsAD = false;
    }

    public void setSnsInfo(int snsid, int resourceid, int resourcetype, int nAdId, int nSeat, String strExpended) {
        this.mSnsId = snsid;
        this.mResourceId = resourceid;
        this.mResourceType = resourcetype;
        this.mIsFeedList = false;
        this.mStrExpended = strExpended;
        this.mAdId = nAdId;
        this.mSeat = nSeat;
        this.mIsAD = true;
    }

    public String getKeySole() {
        return this.mKeySole;
    }

    public void setKeySole(int keySole) {
        this.mKeySole = String.valueOf(keySole);
    }

    public FengVideoPlayer(Context context) {
        super(context);
    }

    public FengVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void init(Context context) {
        super.init(context);
        this.color_ffb90a = this.mContext.getResources().getColor(2131558549);
        this.color_ffffff = this.mContext.getResources().getColor(2131558558);
        this.color_b6b6b6 = this.mContext.getResources().getColor(2131558493);
        this.thumbImageView = (SimpleDraweeView) findViewById(2131624014);
        this.loadingProgressBar = (ProgressBar) findViewById(2131625189);
        this.bottomProgressBar = (ProgressBar) findViewById(2131625183);
        this.video_close = (ImageView) findViewById(2131625199);
        this.option = (ImageView) findViewById(2131625200);
        this.play_icon = (ImageView) findViewById(2131625188);
        this.definitionText = (TextView) findViewById(2131625187);
        this.definition_line = (LinearLayout) findViewById(2131625190);
        this.definition_line.getBackground().mutate().setAlpha(178);
        this.blueLine = (RelativeLayout) findViewById(2131625191);
        this.loginIcon = (ImageView) findViewById(2131625193);
        this.blue_text = (TextView) findViewById(2131625192);
        this.super_text = (TextView) findViewById(2131625194);
        this.high_text = (TextView) findViewById(2131625195);
        this.normal_text = (TextView) findViewById(2131625196);
        this.coverView = (RelativeLayout) findViewById(2131625148);
        this.replay_line = (ImageView) findViewById(2131625197);
        this.exit_line = (ImageView) findViewById(2131625198);
        this.coverView.getBackground().mutate().setAlpha(170);
        this.playingBottomLine = (RelativeLayout) findViewById(2131625184);
        this.playTimeText = (TextView) findViewById(2131623992);
        this.playCount = (TextView) findViewById(2131625185);
        this.optionTipsLine = (RelativeLayout) findViewById(2131625206);
        this.snsTitle = (TextView) findViewById(2131624005);
        this.flowTipsLine = (LinearLayout) findViewById(2131625202);
        this.sizeText = (TextView) findViewById(2131625203);
        this.networkTipsLine = (LinearLayout) findViewById(2131625204);
        this.tips = (TextView) findViewById(2131624696);
        this.networkTipsClose = (ImageView) findViewById(2131625205);
        this.thumbImageView.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (FengVideoPlayer.this.mIsVideoFinalPage) {
                    FengVideoPlayer.this.onClickUiToggle();
                } else {
                    FengVideoPlayer.this.startToVideoFinalPage();
                }
            }
        });
        this.lockImage = (ImageView) findViewById(2131625201);
        this.timeText = (TextView) findViewById(2131624741);
        this.lockImage.setOnClickListener(this);
        this.video_close.setOnClickListener(this);
        this.option.setOnClickListener(this);
        this.play_icon.setOnClickListener(this);
        this.definitionText.setOnClickListener(this);
        this.blue_text.setOnClickListener(this);
        this.super_text.setOnClickListener(this);
        this.high_text.setOnClickListener(this);
        this.normal_text.setOnClickListener(this);
        this.coverView.setOnClickListener(this);
        this.replay_line.setOnClickListener(this);
        this.exit_line.setOnClickListener(this);
    }

    public void updatePosition(int position) {
        super.updatePosition(position);
        if (this.mMediaInfo != null) {
            this.mMediaInfo.position = position;
            VideoManager.newInstance().updatePosition(this.mMediaInfo);
        }
    }

    public void setMediaInfo(MediaInfo info) {
        if (info != null) {
            GenericDraweeHierarchy hierarchy;
            if (this.mIsVideoFinalPage) {
                this.playingBottomLine.setVisibility(8);
                this.startButton.setVisibility(8);
            }
            this.mMediaInfo = info;
            this.mCurrentPosition = this.mMediaInfo.position;
            this.mVideoId = this.mMediaInfo.id;
            this.mHash = this.mMediaInfo.key;
            this.mTotalTime = this.mMediaInfo.playtime;
            setTag(this.mMediaInfo.key);
            GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(getResources());
            if (this.mIsVideoFinalPage) {
                hierarchy = builder.setActualImageScaleType(ScaleType.FIT_CENTER).setFadeDuration(0).build();
            } else {
                hierarchy = builder.setActualImageScaleType(ScaleType.CENTER_CROP).setFadeDuration(0).build();
            }
            this.thumbImageView.setHierarchy(hierarchy);
            this.thumbImageView.setImageURI(Uri.parse(this.mMediaInfo.image.url));
            if (info.image.width >= info.image.height) {
                this.mIsLateralVideo = true;
            } else {
                this.mIsLateralVideo = false;
            }
            this.mIsVideoCache = VideoDownloadManager.newInstance().hasDownloadVideo(this.mMediaInfo);
            if (this.mIsVideoCache) {
                setUp(this.mMediaInfo.path, 1, "");
                this.definitionText.setText(2131231193);
                this.definitionText.setTextColor(this.color_b6b6b6);
                this.definitionText.setClickable(false);
            } else {
                changeDefinitionSelectText();
                if (this.mIsForcePlayHighUrl) {
                    setUp(this.mMediaInfo.getSDUrl(), 1, "");
                    this.definitionText.setText(this.mMediaInfo.getSDName());
                    this.mCurrentPlaying = 1;
                } else {
                    int local_definition = VideoUtil.getLocalDefinition(this.mContext);
                    if (local_definition == 1) {
                        setUp(this.mMediaInfo.getSDUrl(), 1, "");
                        this.definitionText.setText(this.mMediaInfo.getSDName());
                        this.mCurrentPlaying = 1;
                    } else if (local_definition == 2) {
                        if (StringUtil.isEmpty(this.mMediaInfo.getHDUrl())) {
                            setUp(this.mMediaInfo.getSDUrl(), 1, "");
                            this.definitionText.setText(this.mMediaInfo.getSDName());
                            this.mCurrentPlaying = 1;
                        } else {
                            setUp(this.mMediaInfo.getHDUrl(), 1, "");
                            this.definitionText.setText(this.mMediaInfo.getHDName());
                            this.mCurrentPlaying = 2;
                        }
                    } else if (local_definition == 3) {
                        if (!StringUtil.isEmpty(this.mMediaInfo.getFHDUrl())) {
                            setUp(this.mMediaInfo.getFHDUrl(), 1, "");
                            this.definitionText.setText(this.mMediaInfo.getFHDName());
                            this.mCurrentPlaying = 3;
                        } else if (StringUtil.isEmpty(this.mMediaInfo.getHDUrl())) {
                            setUp(this.mMediaInfo.getSDUrl(), 1, "");
                            this.definitionText.setText(this.mMediaInfo.getSDName());
                            this.mCurrentPlaying = 1;
                        } else {
                            setUp(this.mMediaInfo.getHDUrl(), 1, "");
                            this.definitionText.setText(this.mMediaInfo.getHDName());
                            this.mCurrentPlaying = 2;
                        }
                    } else if (local_definition == 4) {
                        if (FengApplication.getInstance().isLoginUser()) {
                            if (!StringUtil.isEmpty(this.mMediaInfo.getBDUrl())) {
                                setUp(this.mMediaInfo.getBDUrl(), 1, "");
                                this.definitionText.setText(this.mMediaInfo.getBDName());
                                this.mCurrentPlaying = 4;
                            } else if (!StringUtil.isEmpty(this.mMediaInfo.getFHDUrl())) {
                                setUp(this.mMediaInfo.getFHDUrl(), 1, "");
                                this.definitionText.setText(this.mMediaInfo.getFHDName());
                                this.mCurrentPlaying = 3;
                            } else if (StringUtil.isEmpty(this.mMediaInfo.getHDUrl())) {
                                setUp(this.mMediaInfo.getSDUrl(), 1, "");
                                this.definitionText.setText(this.mMediaInfo.getSDName());
                                this.mCurrentPlaying = 1;
                            } else {
                                setUp(this.mMediaInfo.getHDUrl(), 1, "");
                                this.definitionText.setText(this.mMediaInfo.getHDName());
                                this.mCurrentPlaying = 2;
                            }
                        } else if (!StringUtil.isEmpty(this.mMediaInfo.getFHDUrl())) {
                            setUp(this.mMediaInfo.getFHDUrl(), 1, "");
                            this.definitionText.setText(this.mMediaInfo.getFHDName());
                            this.mCurrentPlaying = 3;
                        } else if (StringUtil.isEmpty(this.mMediaInfo.getHDUrl())) {
                            setUp(this.mMediaInfo.getSDUrl(), 1, "");
                            this.definitionText.setText(this.mMediaInfo.getSDName());
                            this.mCurrentPlaying = 1;
                        } else {
                            setUp(this.mMediaInfo.getHDUrl(), 1, "");
                            this.definitionText.setText(this.mMediaInfo.getHDName());
                            this.mCurrentPlaying = 2;
                        }
                    }
                    if (FengUtil.isWifiConnectivity(this.mContext) || this.mIsVideoCache) {
                        hideFlowTipsLine();
                        if (!this.mIsVideoFinalPage) {
                            this.startButton.setVisibility(0);
                        }
                    } else {
                        showFlowTipsLine();
                    }
                }
                this.mMediaInfo.defination = this.mCurrentPlaying;
            }
            StringBuffer sb = new StringBuffer("播放");
            sb.append(FengUtil.numberFormat(this.mMediaInfo.playcount));
            sb.append("次");
            this.playCount.setText(sb.toString());
            if (this.mMediaInfo.playtime == 0) {
                this.playTimeText.setVisibility(8);
            } else {
                this.playTimeText.setVisibility(0);
                this.playTimeText.setText(JCUtils.stringForTime(this.mMediaInfo.playtime));
            }
            if (!this.mIsVideoFinalPage) {
                if (this.mMediaInfo.isComplete) {
                    this.coverView.setVisibility(0);
                    this.exit_line.setVisibility(8);
                    this.startButton.setVisibility(8);
                    this.playingBottomLine.setVisibility(8);
                    return;
                }
                this.coverView.setVisibility(8);
                this.exit_line.setVisibility(0);
                if (this.mCurrentState != 2) {
                }
            }
        }
    }

    public void setVideoFinalPage(boolean videoFinalPage) {
        this.mIsVideoFinalPage = videoFinalPage;
    }

    public void setIsWatchVideo(boolean b) {
        this.mIsWatchVideo = b;
    }

    public void changeFullScreenUI() {
        super.changeFullScreenUI();
        this.video_close.setImageResource(2130838274);
        this.fullscreenButton.setImageResource(2130837889);
        this.video_close.setVisibility(0);
        this.option.setVisibility(8);
        this.timeText.setVisibility(0);
        updateTime();
        this.play_icon.setVisibility(0);
        this.definitionText.setVisibility(0);
        this.lockImage.setVisibility(0);
        if (this.coverView.isShown()) {
            this.exit_line.setVisibility(0);
        }
        if (!SharedUtil.getBoolean(this.mContext, FengConstant.VIDEO_FULLSCREEN_TIPS_KEY, false)) {
            if (getResources().getConfiguration().orientation == 1) {
                ImageView volumeTipsImage = (ImageView) findViewById(2131625208);
                ImageView brightTipsImage = (ImageView) findViewById(2131625209);
                ((ImageView) findViewById(2131625207)).setImageResource(2130838410);
                volumeTipsImage.setImageResource(2130838589);
                brightTipsImage.setImageResource(2130837764);
            }
            this.optionTipsLine.setVisibility(0);
            if (this.mCurrentState == 2) {
                playVideo();
                uploadPercentLog(false);
            }
            SharedUtil.putBoolean(this.mContext, FengConstant.VIDEO_FULLSCREEN_TIPS_KEY, true);
            this.optionTipsLine.setOnTouchListener(new OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    FengVideoPlayer.this.optionTipsLine.setVisibility(8);
                    FengVideoPlayer.this.onEvent(4);
                    if (JCMediaManager.instance().getPlayer() != null) {
                        JCMediaManager.instance().getPlayer().resume();
                    }
                    FengVideoPlayer.this.setUiWitStateAndScreen(2);
                    if (FengVideoPlayer.this.mMediaInfo != null) {
                        FengVideoPlayer.this.mMediaInfo.play_state = 1;
                    }
                    FengVideoPlayer.this.uploadPercentLog(true);
                    return false;
                }
            });
        }
    }

    public void changeNormalScreenUI() {
        super.changeNormalScreenUI();
        this.fullscreenButton.setImageResource(2130837891);
        this.video_close.setImageResource(2130837975);
        this.timeText.setVisibility(8);
        this.video_close.setVisibility(0);
        if (!this.mIsWatchVideo) {
            this.option.setVisibility(0);
        }
        this.play_icon.setVisibility(0);
        this.definitionText.setVisibility(8);
        this.lockImage.setVisibility(8);
        if (this.coverView.isShown()) {
            this.exit_line.setVisibility(8);
        }
        if (this.optionTipsLine.isShown()) {
            this.optionTipsLine.setVisibility(8);
        }
    }

    public void setUp(String url, int screen, Object... objects) {
        super.setUp(url, screen, objects);
        if (objects.length != 0) {
            if (this.currentScreen == 2) {
                this.fullscreenButton.setImageResource(2130837889);
                this.video_close.setVisibility(0);
                this.option.setVisibility(8);
                this.timeText.setVisibility(0);
                this.play_icon.setVisibility(0);
                this.definitionText.setVisibility(0);
                this.lockImage.setVisibility(0);
            } else if (this.currentScreen == 0 || this.currentScreen == 1) {
                this.fullscreenButton.setImageResource(2130837891);
                if (this.mIsVideoFinalPage) {
                    this.video_close.setVisibility(0);
                    if (!this.mIsWatchVideo) {
                        this.option.setVisibility(0);
                    }
                    this.play_icon.setVisibility(0);
                } else {
                    this.video_close.setVisibility(8);
                    this.option.setVisibility(8);
                    this.timeText.setVisibility(8);
                    this.play_icon.setVisibility(8);
                }
                this.definitionText.setVisibility(8);
                this.lockImage.setVisibility(8);
            }
        }
    }

    private void changeDefinitionSelectText() {
        boolean is4G = false;
        if (FengUtil.isNetworkAvailable(this.mContext) && !FengUtil.isWifiConnectivity(this.mContext)) {
            is4G = true;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.mMediaInfo.getSDName());
        if (is4G) {
            sb.append("（");
            sb.append(FengUtil.formatVideoSize(this.mMediaInfo.getSDSize()));
            sb.append("）");
        }
        this.normal_text.setText(sb.toString());
        if (StringUtil.isEmpty(this.mMediaInfo.getHDUrl())) {
            this.high_text.setVisibility(8);
        } else {
            sb = new StringBuilder(this.mMediaInfo.getHDName());
            if (is4G) {
                sb.append("（");
                sb.append(FengUtil.formatVideoSize(this.mMediaInfo.getHDSize()));
                sb.append("）");
            }
            this.high_text.setText(sb.toString());
        }
        if (StringUtil.isEmpty(this.mMediaInfo.getFHDUrl())) {
            this.super_text.setVisibility(8);
        } else {
            sb = new StringBuilder(this.mMediaInfo.getFHDName());
            if (is4G) {
                sb.append("（");
                sb.append(FengUtil.formatVideoSize(this.mMediaInfo.getFHDSize()));
                sb.append("）");
            }
            this.super_text.setText(sb.toString());
        }
        if (StringUtil.isEmpty(this.mMediaInfo.getBDUrl())) {
            this.blueLine.setVisibility(8);
            return;
        }
        sb = new StringBuilder(this.mMediaInfo.getBDName());
        if (is4G) {
            sb.append("（");
            sb.append(FengUtil.formatVideoSize(this.mMediaInfo.getBDSize()));
            sb.append("）");
        }
        this.blue_text.setText(sb.toString());
    }

    public int getLayoutId() {
        return 2130903287;
    }

    public void setUiWitStateAndScreen(int state) {
        super.setUiWitStateAndScreen(state);
        switch (this.mCurrentState) {
            case 0:
                changeUiToNormal();
                return;
            case 1:
                changeUiToPreparingShow();
                startDismissControlViewTimer();
                return;
            case 2:
                changeUiToPlayingShow();
                startDismissControlViewTimer();
                JCUtils.scanForActivity(this.mContext).getWindow().addFlags(128);
                return;
            case 3:
                changeUiToPlayingBufferingShow();
                return;
            case 5:
                changeUiToPauseShow();
                cancelDismissControlViewTimer();
                return;
            case 6:
                changeUiToCompleteShow();
                cancelDismissControlViewTimer();
                this.bottomProgressBar.setProgress(100);
                return;
            case 7:
                changeUiToError();
                return;
            default:
                return;
        }
    }

    public boolean onTouch(View v, MotionEvent event) {
        int id = v.getId();
        if (id != 2131624008) {
            if (id == 2131623994) {
                switch (event.getAction()) {
                    case 0:
                        cancelDismissControlViewTimer();
                        break;
                    case 1:
                        startDismissControlViewTimer();
                        break;
                }
            }
        }
        switch (event.getAction()) {
            case 0:
                if (System.currentTimeMillis() - this.lastUpTime < 200) {
                    this.isFastClick = true;
                    break;
                }
                break;
            case 1:
                startDismissControlViewTimer();
                if (this.mChangePosition) {
                    int duration = getDuration();
                    int i = this.mSeekTimePosition * 100;
                    if (duration == 0) {
                        duration = 1;
                    }
                    this.bottomProgressBar.setProgress(i / duration);
                }
                if (!(!this.isVideoFinalPage || this.mChangePosition || this.mChangeVolume)) {
                    onEvent(102);
                    if (this.flowTipsLine.isShown()) {
                        this.flowTipsLine.setVisibility(8);
                        changeDefination(2);
                    } else if (this.isFastClick) {
                        this.isFastClick = false;
                    } else {
                        postDelayed(new Runnable() {
                            public void run() {
                                if (!FengVideoPlayer.this.isFastClick) {
                                    FengVideoPlayer.this.onClickUiToggle();
                                }
                            }
                        }, 120);
                    }
                }
                this.lastUpTime = System.currentTimeMillis();
                break;
        }
        return super.onTouch(v, event);
    }

    public void setTextAndProgress() {
        super.setTextAndProgress();
        int position = getCurrentPositionWhenPlaying();
        int duration = getDuration();
        int i = position * 100;
        if (duration == 0) {
            duration = 1;
        }
        int progress = i / duration;
        if (progress != 0) {
            this.bottomProgressBar.setProgress(progress);
        }
    }

    public void resetProgressAndTime() {
        super.resetProgressAndTime();
        this.bottomProgressBar.setProgress(0);
        this.bottomProgressBar.setSecondaryProgress(0);
    }

    private void startToVideoFinalPage() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - this.lastToFinalPageTime >= 1000) {
            this.lastToFinalPageTime = currentTime;
            if (this.mMediaInfo != null) {
                VideoManager.newInstance().updateVideoInfo(this.mMediaInfo);
                if (this.mIsFeedList) {
                    LogGatherReadUtil.getInstance().addLocationClick(this.mSnsId, this.mResourceId, this.mResourceType, this.mStrExpended);
                } else if (this.mIsAD) {
                    LogGatherReadUtil.getInstance().addAdClickLog(this.mAdId, this.mSeat, this.mStrExpended);
                }
                Intent intent = new Intent(this.mContext, VideoFinalPageActivity.class);
                intent.putExtra(FengConstant.MEDIA, this.mMediaInfo.key);
                intent.putExtra(HttpConstant.SNS_ID, this.mSnsId);
                intent.putExtra("resourceid", this.mResourceId);
                intent.putExtra(HttpConstant.RESOURCETYPE, this.mResourceType);
                intent.putExtra(FengConstant.FROM_KEY, false);
                if (this.flowTipsLine.isShown()) {
                    intent.putExtra(FengConstant.FORCE_PLAY_HIGH, true);
                }
                if (this.mContext instanceof ArticleDetailActivity) {
                    intent.putExtra(FengConstant.FROM_ARTICLE_FINAL, 1);
                }
                int[] location = new int[2];
                getLocationOnScreen(location);
                intent.putExtra("location_y", location[1] - getResources().getDimensionPixelSize(2131296859));
                this.mContext.startActivity(intent);
                ((Activity) this.mContext).overridePendingTransition(0, 0);
            }
        }
    }

    public void setOptionListener(OptionListener l) {
        this.mOptionListener = l;
    }

    private void changeNormalDefinitionColor() {
        this.blue_text.setBackgroundResource(R.color.transparent);
        this.super_text.setBackgroundResource(R.color.transparent);
        this.high_text.setBackgroundResource(R.color.transparent);
        this.normal_text.setBackgroundResource(2130838039);
        this.blue_text.setTextColor(this.color_ffffff);
        this.super_text.setTextColor(this.color_ffffff);
        this.high_text.setTextColor(this.color_ffffff);
        this.normal_text.setTextColor(this.color_ffb90a);
        this.definitionText.setText(this.mMediaInfo.getSDName());
    }

    private void changeHighDefinitionColor() {
        this.blue_text.setBackgroundResource(R.color.transparent);
        this.super_text.setBackgroundResource(R.color.transparent);
        this.high_text.setBackgroundResource(2130838039);
        this.normal_text.setBackgroundResource(R.color.transparent);
        this.blue_text.setTextColor(this.color_ffffff);
        this.super_text.setTextColor(this.color_ffffff);
        this.high_text.setTextColor(this.color_ffb90a);
        this.normal_text.setTextColor(this.color_ffffff);
        this.definitionText.setText(this.mMediaInfo.getHDName());
    }

    private void changeSuperDefinitionColor() {
        this.blue_text.setBackgroundResource(R.color.transparent);
        this.super_text.setBackgroundResource(2130838039);
        this.high_text.setBackgroundResource(R.color.transparent);
        this.normal_text.setBackgroundResource(R.color.transparent);
        this.blue_text.setTextColor(this.color_ffffff);
        this.super_text.setTextColor(this.color_ffb90a);
        this.high_text.setTextColor(this.color_ffffff);
        this.normal_text.setTextColor(this.color_ffffff);
        this.definitionText.setText(this.mMediaInfo.getFHDName());
    }

    private void changeBlueDefinitionColor() {
        this.blue_text.setBackgroundResource(2130838039);
        this.super_text.setBackgroundResource(R.color.transparent);
        this.high_text.setBackgroundResource(R.color.transparent);
        this.normal_text.setBackgroundResource(R.color.transparent);
        this.blue_text.setTextColor(this.color_ffb90a);
        this.super_text.setTextColor(this.color_ffffff);
        this.high_text.setTextColor(this.color_ffffff);
        this.normal_text.setTextColor(this.color_ffffff);
        this.definitionText.setText(this.mMediaInfo.getBDName());
    }

    private void changeDefinitionColor() {
        if (this.mCurrentPlaying == 1) {
            changeNormalDefinitionColor();
        } else if (this.mCurrentPlaying == 2) {
            changeHighDefinitionColor();
        } else if (this.mCurrentPlaying == 3) {
            changeSuperDefinitionColor();
        } else if (this.mCurrentPlaying == 4) {
            changeBlueDefinitionColor();
        }
        if (!FengApplication.getInstance().isLoginUser()) {
            this.loginIcon.setVisibility(0);
        }
    }

    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case com.github.jdsjlzx.R.id.start /*2131624007*/:
                if (!FengUtil.isNetworkAvailable(this.mContext)) {
                    ((BaseActivity) this.mContext).showThirdTypeToast(2131230912);
                    return;
                } else if (this.mIsVideoFinalPage || this.currentScreen == 2) {
                    playVideo();
                    return;
                } else {
                    startToVideoFinalPage();
                    return;
                }
            case 2131624008:
                startDismissControlViewTimer();
                return;
            case 2131625187:
                if (!this.mIsVideoCache) {
                    if (this.definition_line.isShown()) {
                        this.definition_line.setVisibility(8);
                        return;
                    }
                    changeDefinitionColor();
                    this.definition_line.setVisibility(0);
                    return;
                }
                return;
            case 2131625188:
                if (this.mCurrentState == 2) {
                    this.mMediaInfo.play_state = 1;
                }
                playVideo();
                updatePositionToDataBase();
                return;
            case 2131625192:
                if (!FengApplication.getInstance().isLoginUser()) {
                    JCVideoPlayerManager.needStartFullScreen = true;
                    JCVideoPlayerManager.lastDirection = JCVideoPlayerManager.mCurrentDirection;
                    backPress();
                    Intent intent1 = new Intent(this.mContext, LoginActivity.class);
                    intent1.putExtra("video_bd_login_flag", 1);
                    this.mContext.startActivity(intent1);
                } else if (this.mCurrentPlaying != 4) {
                    changeDefination(4);
                }
                hideAllView();
                return;
            case 2131625194:
                if (this.mCurrentPlaying != 3) {
                    changeDefination(3);
                }
                hideAllView();
                return;
            case 2131625195:
                if (this.mCurrentPlaying != 2) {
                    changeDefination(2);
                }
                hideAllView();
                return;
            case 2131625196:
                if (this.mCurrentPlaying != 1) {
                    changeDefination(1);
                }
                hideAllView();
                return;
            case 2131625197:
                replay();
                return;
            case 2131625198:
                VideoUtil.IS_EXIT = true;
                backPress();
                return;
            case 2131625199:
                if (this.currentScreen == 2) {
                    backPress();
                    return;
                } else if (this.mVideoCloseClickListener != null) {
                    this.mVideoCloseClickListener.onPortraitCloseClick();
                    return;
                } else {
                    return;
                }
            case 2131625200:
                if (this.mOptionListener != null) {
                    this.mOptionListener.onOptionClick();
                    return;
                }
                return;
            case 2131625201:
                if (getResources().getConfiguration().orientation == 1) {
                    if (JCUtils.isLockScreen()) {
                        JCUtils.unLockScreen();
                        this.lockImage.setImageResource(2130838559);
                        onClickUiToggle();
                        return;
                    }
                    onClickUiToggle();
                    this.lockImage.setImageResource(2130838336);
                    JCUtils.lockScreen();
                    this.lockImage.setVisibility(0);
                    startDismissControlViewTimer();
                    return;
                } else if (JCUtils.isLockScreen()) {
                    JCUtils.unLockScreen();
                    this.lockImage.setImageResource(2130838559);
                    onClickUiToggle();
                    JCUtils.getAppCompActivity(getContext()).setRequestedOrientation(FULLSCREEN_ORIENTATION);
                    return;
                } else {
                    onClickUiToggle();
                    this.lockImage.setImageResource(2130838336);
                    JCUtils.lockScreen();
                    this.lockImage.setVisibility(0);
                    startDismissControlViewTimer();
                    int angle = ((WindowManager) this.mContext.getSystemService("window")).getDefaultDisplay().getRotation();
                    if (angle == 1) {
                        JCUtils.getAppCompActivity(getContext()).setRequestedOrientation(0);
                        return;
                    } else if (angle == 3) {
                        JCUtils.getAppCompActivity(getContext()).setRequestedOrientation(8);
                        return;
                    } else {
                        return;
                    }
                }
            default:
                return;
        }
    }

    public void hideAllView() {
        this.bottomContainer.setVisibility(8);
        this.topContainer.setVisibility(8);
        this.definition_line.setVisibility(8);
        this.bottomProgressBar.setVisibility(0);
        this.lockImage.setVisibility(8);
    }

    private void playUrl() {
        if (this.mMediaInfo == null) {
            return;
        }
        if (this.mIsVideoCache) {
            JCMediaManager.instance().prepare(this.mContext, this.mCurrentUrl, this.textureViewContainer, this.mMediaInfo.downloadDefination == 4);
        } else {
            JCMediaManager.instance().prepare(this.mContext, this.mCurrentUrl, this.textureViewContainer, this.mCurrentUrl.equals(this.mMediaInfo.getBDUrl()));
        }
    }

    public void playVideo() {
        if (this.coverView.isShown()) {
            this.coverView.setVisibility(8);
            this.exit_line.setVisibility(0);
        }
        if (TextUtils.isEmpty(this.mCurrentUrl) && this.isVideoFinalPage) {
            Toast.makeText(getContext(), getResources().getString(2131231302), 0).show();
        } else if (this.mCurrentState == 0 || this.mCurrentState == 7 || JCMediaManager.instance().isStopPlay()) {
            int i;
            if (this.mMediaInfo != null) {
                updatePosition(VideoManager.newInstance().getPosition(this.mMediaInfo));
            } else {
                updatePosition(this.mCurrentPosition);
            }
            prepareMediaPlayer();
            playUrl();
            if (this.mCurrentState != 7) {
                i = 0;
            } else {
                i = 1;
            }
            onEvent(i);
            if (this.mMediaInfo != null) {
                this.mMediaInfo.play_state = 2;
                this.mMediaInfo.isComplete = false;
            }
            hideFlowTipsLine();
            addVideoPlayCount();
            uploadPercentLog(true);
        } else if (this.mCurrentState == 2) {
            onEvent(3);
            if (JCMediaManager.instance().getPlayer() != null) {
                JCMediaManager.instance().getPlayer().pause();
            }
            setUiWitStateAndScreen(5);
            uploadPercentLog(false);
            JCUtils.scanForActivity(this.mContext).getWindow().clearFlags(128);
        } else if (this.mCurrentState == 5) {
            JCUtils.scanForActivity(this.mContext).getWindow().addFlags(128);
            onEvent(4);
            if (JCMediaManager.instance().getPlayer() != null) {
                JCMediaManager.instance().getPlayer().resume();
            }
            setUiWitStateAndScreen(2);
            if (this.mMediaInfo != null) {
                this.mMediaInfo.play_state = 2;
            }
            hideFlowTipsLine();
            addVideoPlayCount();
            uploadPercentLog(true);
        } else if (this.mCurrentState == 6) {
            onEvent(2);
            prepareMediaPlayer();
            playUrl();
            uploadPercentLog(true);
        }
    }

    private void replay() {
        VideoUtil.IGNORE_AUDIO_LOSS = true;
        addVideoPlayCount();
        JCUtils.scanForActivity(this.mContext).getWindow().addFlags(128);
        updatePosition(0);
        playUrl();
        setUiWitStateAndScreen(1);
        if (this.currentScreen == 2) {
            if (JCVideoPlayerManager.getFirstFloor() != null) {
                ((FengVideoPlayer) JCVideoPlayerManager.getFirstFloor()).mMediaInfo.isComplete = false;
            }
        } else if (this.mMediaInfo != null) {
            this.mMediaInfo.isComplete = false;
        }
        updateMedaInfo();
        if (this.coverView.isShown()) {
            this.coverView.setVisibility(8);
            this.exit_line.setVisibility(0);
        }
        uploadPercentLog(true);
    }

    public void aftersetState(int state) {
        super.aftersetState(state);
        if (this.mCurrentPlaying == 1) {
            this.definitionText.setText(this.mMediaInfo.getSDName());
        } else if (this.mCurrentPlaying == 2) {
            this.definitionText.setText(this.mMediaInfo.getHDName());
        } else if (this.mCurrentPlaying == 3) {
            this.definitionText.setText(this.mMediaInfo.getFHDName());
        } else if (this.mCurrentPlaying == 4) {
            this.definitionText.setText(this.mMediaInfo.getBDName());
        }
        if (this.mCurrentState == 2) {
            this.play_icon.setImageResource(2130838578);
        } else if (this.mCurrentState == 7) {
            this.play_icon.setImageResource(2130838580);
        } else {
            this.play_icon.setImageResource(2130838580);
        }
        this.thumbImageView.setImageURI(Uri.parse(this.mMediaInfo.image.url));
        changeDefinitionSelectText();
        if (this.mIsVideoCache) {
            this.definitionText.setTextColor(this.color_b6b6b6);
            this.definitionText.setClickable(false);
            this.definitionText.setText(2131231193);
        }
        this.isVideoFinalPage = true;
        this.mCurrentState = state;
        this.video_close.setImageResource(2130838274);
        if (!SharedUtil.getBoolean(this.mContext, FengConstant.VIDEO_FULLSCREEN_TIPS_KEY, false)) {
            if (getResources().getConfiguration().orientation == 1) {
                ImageView volumeTipsImage = (ImageView) findViewById(2131625208);
                ImageView brightTipsImage = (ImageView) findViewById(2131625209);
                ((ImageView) findViewById(2131625207)).setImageResource(2130838410);
                volumeTipsImage.setImageResource(2130838589);
                brightTipsImage.setImageResource(2130837764);
            }
            this.optionTipsLine.setVisibility(0);
            if (this.mCurrentState == 2) {
                playVideo();
                uploadPercentLog(false);
            }
            SharedUtil.putBoolean(this.mContext, FengConstant.VIDEO_FULLSCREEN_TIPS_KEY, true);
            this.optionTipsLine.setOnTouchListener(new OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    FengVideoPlayer.this.optionTipsLine.setVisibility(8);
                    FengVideoPlayer.this.onEvent(4);
                    if (JCMediaManager.instance().getPlayer() != null) {
                        JCMediaManager.instance().getPlayer().resume();
                    }
                    FengVideoPlayer.this.setUiWitStateAndScreen(2);
                    if (FengVideoPlayer.this.mMediaInfo != null) {
                        FengVideoPlayer.this.mMediaInfo.play_state = 1;
                    }
                    FengVideoPlayer.this.uploadPercentLog(true);
                    return false;
                }
            });
        }
    }

    private void saveDefinationData() {
        VideoUtil.saveDefinition(this.mContext, this.mCurrentPlaying);
    }

    public void show4GToast() {
        if (!this.mIsVideoCache && FengUtil.isNetworkAvailable(this.mContext) && !FengUtil.isWifiConnectivity(this.mContext)) {
            if (this.mCurrentPlaying == 1) {
                ((BaseActivity) this.mContext).showThirdTypeToast("您正在使用运营商网络播放" + this.mMediaInfo.getSDName() + "视频");
            } else if (this.mCurrentPlaying == 2) {
                ((BaseActivity) this.mContext).showThirdTypeToast("您正在使用运营商网络播放" + this.mMediaInfo.getHDName() + "视频");
            } else if (this.mCurrentPlaying == 3) {
                ((BaseActivity) this.mContext).showThirdTypeToast("您正在使用运营商网络播放" + this.mMediaInfo.getFHDName() + "视频");
            } else if (this.mCurrentPlaying == 4) {
                ((BaseActivity) this.mContext).showThirdTypeToast("您正在使用运营商网络播放" + this.mMediaInfo.getBDName() + "视频");
            }
        }
    }

    public void changeDefination(int definition) {
        super.changeDefination(definition);
        show4GToast();
        changeDefinitionColor();
        if (this.currentScreen == 2) {
            saveDefinationData();
        }
        if (JCVideoPlayerManager.getCurrentJcvd() == null) {
            JCVideoPlayerManager.setFirstFloor(this);
        }
        playUrl();
        this.mCurrentState = 1;
        showLoadingProgress();
        if (this.mMediaInfo != null) {
            this.mMediaInfo.play_state = 2;
            this.mMediaInfo.defination = definition;
            this.mMediaInfo.hasChangeDefinition = true;
        }
    }

    public void afterBackPress() {
        super.afterBackPress();
        if (this.coverView.isShown()) {
            this.coverView.setVisibility(8);
        }
    }

    public void onCompletion() {
        super.onCompletion();
        uploadPercentLog(false);
    }

    public void afterOnCompletion() {
        super.afterOnCompletion();
        uploadPercentLog(false);
        if (this.mMediaInfo != null) {
            this.mMediaInfo.isComplete = true;
            this.mMediaInfo.position = getDuration();
            updatePositionToDataBase();
        }
        this.lockImage.setImageResource(2130838559);
        this.lockImage.setVisibility(8);
        JCUtils.unLockScreen();
        this.mCurrentState = 6;
        if (this.currentScreen == 2) {
            this.topContainer.setVisibility(0);
            this.coverView.setVisibility(0);
            this.playingBottomLine.setVisibility(8);
            this.bottomContainer.setVisibility(8);
            this.exit_line.setVisibility(0);
            this.thumbImageView.setImageURI(Uri.parse(this.mMediaInfo.image.url));
            this.thumbImageView.setVisibility(0);
        } else {
            setUiWitStateAndScreen(0);
            this.thumbImageView.setImageURI(Uri.parse(this.mMediaInfo.image.url));
            this.thumbImageView.setVisibility(0);
            this.bottomContainer.setVisibility(8);
            this.startButton.setVisibility(8);
            if (this.mIsVideoFinalPage) {
                this.coverView.setVisibility(0);
                this.exit_line.setVisibility(8);
            } else {
                this.coverView.setVisibility(0);
                this.playingBottomLine.setVisibility(8);
                this.exit_line.setVisibility(8);
            }
            this.playTimeText.setText(JCUtils.stringForTime(this.mDuration));
        }
        JCUtils.scanForActivity(this.mContext).getWindow().clearFlags(128);
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
        super.onStartTrackingTouch(seekBar);
        cancelDismissControlViewTimer();
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        super.onStopTrackingTouch(seekBar);
        startDismissControlViewTimer();
        this.mIsSpeed = 1;
    }

    public void onClickUiToggle() {
        if (JCUtils.isLockScreen()) {
            if (this.lockImage.isShown()) {
                this.lockImage.setVisibility(8);
            } else if (this.currentScreen == 2) {
                this.lockImage.setVisibility(0);
                startDismissControlViewTimer();
            }
        } else if (this.mCurrentState == 1) {
            if (this.bottomContainer.getVisibility() == 0) {
                changeUiToPreparingClear();
            } else {
                changeUiToPreparingShow();
            }
        } else if (this.mCurrentState == 2) {
            if (this.bottomContainer.getVisibility() == 0) {
                changeUiToPlayingClear();
            } else {
                changeUiToPlayingShow();
            }
        } else if (this.mCurrentState == 5) {
            if (this.bottomContainer.getVisibility() == 0) {
                changeUiToPauseClear();
            } else {
                changeUiToPauseShow();
            }
        } else if (this.mCurrentState == 6) {
            if (this.bottomContainer.getVisibility() == 0) {
                changeUiToCompleteClear();
            } else {
                changeUiToCompleteShow();
            }
        } else if (this.mCurrentState != 3) {
            changeUiToNormal();
        } else if (this.bottomContainer.getVisibility() == 0) {
            changeUiToPlayingBufferingClear();
        } else {
            changeUiToPlayingBufferingShow();
        }
    }

    public void changeUiToNormal() {
        switch (this.currentScreen) {
            case 0:
            case 1:
                setAllControlsVisible(0, 8, 0, 4, 0, 0, 4);
                updateStartImage();
                return;
            case 2:
                setAllControlsVisible(0, 4, 0, 4, 0, 0, 4);
                updateStartImage();
                return;
            default:
                return;
        }
    }

    public void changeUiToPreparingShow() {
        switch (this.currentScreen) {
            case 0:
            case 1:
                setAllControlsVisible(0, 4, 4, 0, 0, 0, 4);
                return;
            case 2:
                setAllControlsVisible(0, 4, 4, 0, 0, 0, 4);
                return;
            default:
                return;
        }
    }

    public void changeUiToPreparingClear() {
        switch (this.currentScreen) {
            case 0:
            case 1:
                setAllControlsVisible(0, 4, 4, 0, 0, 0, 4);
                return;
            case 2:
                setAllControlsVisible(0, 4, 4, 0, 0, 0, 4);
                return;
            default:
                return;
        }
    }

    public void onPrepared() {
        super.onPrepared();
        if (this.currentScreen == 2) {
            setAllControlsVisible(0, 0, 4, 4, 4, 4, 4);
        } else {
            setAllControlsVisible(4, 4, 4, 4, 4, 4, 0);
        }
        startDismissControlViewTimer();
        updatePositionToDataBase();
    }

    public void changeUiToPlayingShow() {
        switch (this.currentScreen) {
            case 0:
            case 1:
                if (this.mIsVideoFinalPage) {
                    setAllControlsVisible(0, 0, 4, 4, 4, 4, 4);
                } else {
                    setAllControlsVisible(0, 8, 4, 4, 4, 4, 4);
                }
                updateStartImage();
                return;
            case 2:
                setAllControlsVisible(0, 0, 4, 4, 4, 4, 4);
                updateStartImage();
                return;
            default:
                return;
        }
    }

    public void changeUiToPlayingClear() {
        switch (this.currentScreen) {
            case 0:
            case 1:
                setAllControlsVisible(8, 8, 4, 4, 4, 4, 0);
                return;
            case 2:
                setAllControlsVisible(4, 4, 4, 4, 4, 4, 0);
                return;
            default:
                return;
        }
    }

    public void changeUiToPauseShow() {
        switch (this.currentScreen) {
            case 0:
            case 1:
                if (this.mIsVideoFinalPage) {
                    setAllControlsVisible(0, 0, 4, 4, 4, 4, 4);
                } else {
                    setAllControlsVisible(0, 8, 4, 4, 4, 4, 4);
                }
                updateStartImage();
                return;
            case 2:
                setAllControlsVisible(0, 0, 4, 4, 4, 4, 4);
                updateStartImage();
                return;
            default:
                return;
        }
    }

    public void changeUiToPauseClear() {
        switch (this.currentScreen) {
            case 0:
            case 1:
                setAllControlsVisible(4, 4, 4, 4, 4, 4, 4);
                return;
            case 2:
                setAllControlsVisible(4, 4, 4, 4, 4, 4, 4);
                return;
            default:
                return;
        }
    }

    public void changeUiToPlayingBufferingShow() {
        switch (this.currentScreen) {
            case 0:
            case 1:
                setAllControlsVisible(0, 0, 4, 0, 4, 4, 4);
                return;
            case 2:
                setAllControlsVisible(0, 0, 4, 0, 4, 4, 4);
                return;
            default:
                return;
        }
    }

    public void changeUiToPlayingBufferingClear() {
        switch (this.currentScreen) {
            case 0:
            case 1:
                setAllControlsVisible(4, 4, 4, 0, 4, 4, 0);
                updateStartImage();
                return;
            case 2:
                setAllControlsVisible(4, 4, 4, 0, 4, 4, 0);
                updateStartImage();
                return;
            default:
                return;
        }
    }

    public void changeUiToCompleteShow() {
        switch (this.currentScreen) {
            case 0:
            case 1:
                if (this.mIsVideoFinalPage) {
                    this.bottomContainer.setVisibility(8);
                } else {
                    setAllControlsVisible(0, 0, 0, 4, 0, 4, 4);
                }
                updateStartImage();
                return;
            case 2:
                setAllControlsVisible(0, 0, 0, 4, 0, 4, 4);
                updateStartImage();
                return;
            default:
                return;
        }
    }

    public void changeUiToCompleteClear() {
        switch (this.currentScreen) {
            case 0:
            case 1:
                setAllControlsVisible(4, 4, 0, 4, 0, 4, 0);
                updateStartImage();
                return;
            case 2:
                setAllControlsVisible(4, 4, 0, 4, 0, 4, 0);
                updateStartImage();
                return;
            default:
                return;
        }
    }

    public void changeUiToError() {
        switch (this.currentScreen) {
            case 0:
            case 1:
                setAllControlsVisible(4, 4, 0, 4, 4, 0, 4);
                updateStartImage();
                this.startButton.setVisibility(0);
                this.startButton.setImageResource(2130838297);
                return;
            case 2:
                setAllControlsVisible(4, 4, 0, 4, 4, 0, 4);
                updateStartImage();
                this.startButton.setVisibility(0);
                this.startButton.setImageResource(2130838297);
                return;
            default:
                return;
        }
    }

    public void setAllControlsVisible(int topCon, int bottomCon, int startBtn, int loadingPro, int thumbImg, int coverImg, int bottomPro) {
        this.bottomContainer.setVisibility(bottomCon);
        this.topContainer.setVisibility(topCon);
        if (this.currentScreen == 2) {
            this.lockImage.setVisibility(topCon);
        }
        if (this.currentScreen == 2) {
            if (this.mCurrentState == 6) {
                this.play_icon.setVisibility(8);
            } else {
                this.play_icon.setVisibility(bottomCon);
            }
        } else if (this.mIsVideoFinalPage) {
            if (this.mMediaInfo == null) {
                this.play_icon.setVisibility(bottomCon);
            } else if (this.mMediaInfo.isComplete) {
                this.play_icon.setVisibility(8);
            } else {
                this.play_icon.setVisibility(topCon);
            }
        } else if (this.mMediaInfo == null || this.mMediaInfo.isComplete || this.mCurrentState == 2) {
        }
        this.loadingProgressBar.setVisibility(loadingPro);
        if (!this.mIsVideoFinalPage) {
            this.playingBottomLine.setVisibility(thumbImg);
            if (this.flowTipsLine.isShown() || this.coverView.isShown() || this.loadingProgressBar.isShown()) {
                this.startButton.setVisibility(8);
            } else {
                this.startButton.setVisibility(thumbImg);
            }
        }
        this.thumbImageView.setVisibility(thumbImg);
        if (bottomCon != 0) {
            this.definition_line.setVisibility(bottomCon);
        }
        this.bottomProgressBar.setVisibility(bottomPro);
    }

    public void updateStartImage() {
        if (this.currentScreen == 2) {
            if (this.mCurrentState == 2) {
                this.play_icon.setImageResource(2130838578);
            } else if (this.mCurrentState == 7) {
                this.play_icon.setImageResource(2130838580);
            } else {
                this.play_icon.setImageResource(2130838580);
            }
        } else if (this.mCurrentState == 2) {
            this.play_icon.setImageResource(2130838579);
        } else if (this.mCurrentState == 7) {
            this.play_icon.setImageResource(2130838581);
        } else {
            this.play_icon.setImageResource(2130838581);
        }
    }

    public void showProgressDialog(float deltaX, String seekTime, int seekTimePosition, String totalTime, int totalTimeDuration) {
        super.showProgressDialog(deltaX, seekTime, seekTimePosition, totalTime, totalTimeDuration);
        if (this.progress_view == null) {
            this.progress_view = findViewById(2131625174);
            this.progress_content = (LinearLayout) findViewById(2131625175);
            this.progress_content.getBackground().mutate().setAlpha(178);
            this.mDialogSeekTime = (TextView) findViewById(2131625177);
            this.mDialogTotalTime = (TextView) findViewById(2131625178);
            this.mDialogIcon = (ImageView) findViewById(2131625176);
        }
        if (!this.progress_view.isShown()) {
            this.progress_view.setVisibility(0);
        }
        this.mDialogSeekTime.setText(seekTime + " / ");
        this.mDialogTotalTime.setText(totalTime);
        if (deltaX > 0.0f) {
            this.mDialogIcon.setBackgroundResource(2130837885);
        } else {
            this.mDialogIcon.setBackgroundResource(2130837652);
        }
        this.mIsSpeed = 1;
    }

    public void dismissProgressDialog() {
        super.dismissProgressDialog();
        if (this.progress_view != null && this.progress_view.isShown()) {
            this.progress_view.setVisibility(8);
        }
    }

    public void showVolumeDialog(int volumePercent) {
        super.showVolumeDialog(volumePercent);
        if (this.volume_content == null) {
            this.volume_content = (LinearLayout) findViewById(2131625179);
            this.volume_content.getBackground().mutate().setAlpha(178);
            this.mDialogVolumeProgressBar = (ProgressBar) findViewById(2131625180);
        }
        if (!this.volume_content.isShown()) {
            this.volume_content.setVisibility(0);
        }
        this.mDialogVolumeProgressBar.setProgress(volumePercent);
    }

    public void dismissVolumeDialog() {
        super.dismissVolumeDialog();
        if (this.volume_content != null && this.volume_content.isShown()) {
            this.volume_content.setVisibility(8);
        }
    }

    public void showBrightnessDialog(int volumePercent) {
        super.showBrightnessDialog(volumePercent);
        if (this.brightness_content == null) {
            this.brightness_content = findViewById(2131625181);
            this.brightness_content.getBackground().mutate().setAlpha(178);
            this.mDialogBrightnessProgressBar = (ProgressBar) findViewById(2131625182);
        }
        if (!this.brightness_content.isShown()) {
            this.brightness_content.setVisibility(0);
        }
        this.mDialogBrightnessProgressBar.setProgress(volumePercent);
    }

    public void dismissBrightnessDialog() {
        super.dismissBrightnessDialog();
        if (this.brightness_content != null && this.brightness_content.isShown()) {
            this.brightness_content.setVisibility(8);
        }
    }

    public void startDismissControlViewTimer() {
        cancelDismissControlViewTimer();
        this.DISMISS_CONTROL_VIEW_TIMER = new Timer();
        this.mDismissControlViewTimerTask = new DismissControlViewTimerTask(this, null);
        this.DISMISS_CONTROL_VIEW_TIMER.schedule(this.mDismissControlViewTimerTask, 5000);
    }

    public void cancelDismissControlViewTimer() {
        if (this.DISMISS_CONTROL_VIEW_TIMER != null) {
            this.DISMISS_CONTROL_VIEW_TIMER.cancel();
        }
        if (this.mDismissControlViewTimerTask != null) {
            this.mDismissControlViewTimerTask.cancel();
        }
    }

    public void showLoadingProgress() {
        this.loadingProgressBar.setVisibility(0);
        this.startButton.setVisibility(8);
    }

    public void hideLoadingProgress() {
        if (this.loadingProgressBar.isShown()) {
            this.loadingProgressBar.setVisibility(8);
        }
    }

    public void updateMedaInfo() {
        super.updateMedaInfo();
        if (this.mMediaInfo != null) {
            this.mMediaInfo.position = this.mCurrentPosition;
            this.mMediaInfo.defination = this.mCurrentPlaying;
            VideoManager.newInstance().updateVideoInfo(this.mMediaInfo);
        }
    }

    public void addVideoPlayCount() {
        if (this.mVideoId != 0) {
            Map<String, Object> map = new HashMap();
            map.put(HttpConstant.VIDEORESOURCEID, String.valueOf(this.mVideoId));
            map.put(HttpConstant.RESOURCETYPE, String.valueOf(this.mResourceType));
            FengApplication.getInstance().httpRequest(HttpConstant.ADD_PLAY_COUNT, map, new OkHttpResponseCallback() {
                public void onNetworkError() {
                }

                public void onStart() {
                }

                public void onFinish() {
                }

                public void onFailure(int statusCode, String content, Throwable error) {
                }

                public void onSuccess(int statusCode, String content) {
                }
            });
        }
    }

    public void uploadPercentLog(boolean isPlaying) {
        StringBuffer sb = new StringBuffer();
        if (this.mCurrentState == 6) {
            sb.append("100%");
        } else if (this.mDuration != 0) {
            sb.append((int) (((((float) this.mCurrentPosition) * 1.0f) / ((float) this.mDuration)) * 100.0f));
            sb.append("%");
        } else {
            sb.append("0%");
        }
        if (isPlaying) {
            this.mLogMap.put(LogConstans.ISSPEED, String.valueOf(0));
            this.mLogMap.put(LogConstans.PLAYTYPE, String.valueOf(0));
            this.mPlaykey = UUID.randomUUID().toString();
        } else {
            this.mLogMap.put(LogConstans.ISSPEED, String.valueOf(this.mIsSpeed));
            this.mLogMap.put(LogConstans.PLAYTYPE, String.valueOf(1));
        }
        if (this.mTotalTime == 0) {
            this.mTotalTime = getDuration();
        }
        this.mLogMap.put(LogConstans.VIDEOTOTALTIME, String.valueOf(this.mTotalTime / 1000));
        this.mLogMap.put(LogConstans.PLAYKEY, this.mPlaykey);
        this.mLogMap.put(LogConstans.VIDEOHASH, this.mHash);
        String json = JsonUtil.toJson(this.mLogMap);
        if (json != null && !json.equals(this.mLastJson)) {
            this.mIsSpeed = 0;
            this.mLastJson = json;
            if (sb.length() > 0) {
                LogGatherReadUtil.getInstance().addPercentageLog(this.mSnsId, this.mResourceId, this.mResourceType, 1, sb.toString(), this.mLastJson);
            }
        }
    }

    public void showFlowTipsLine() {
        if (this.mCurrentState == 2) {
            playVideo();
            uploadPercentLog(false);
        }
        if (this.mMediaInfo != null) {
            this.thumbImageView.setImageURI(Uri.parse(this.mMediaInfo.image.url));
        }
        this.thumbImageView.setVisibility(0);
        this.flowTipsLine.setVisibility(0);
        this.startButton.setVisibility(8);
        if (this.mIsVideoFinalPage) {
            this.topContainer.setVisibility(0);
        } else {
            this.topContainer.setVisibility(8);
        }
        this.bottomContainer.setVisibility(8);
        this.lockImage.setVisibility(8);
        StringBuilder sb = new StringBuilder(FengUtil.formatVideoSize(this.mMediaInfo.getSDSize()));
        sb.append(" 流量");
        SpannableStringBuilder builder = new SpannableStringBuilder(sb.toString());
        ForegroundColorSpan colorffffffSpan = new ForegroundColorSpan(this.color_ffffff);
        builder.setSpan(new ForegroundColorSpan(this.color_ffb90a), 0, sb.length() - 2, 33);
        builder.setSpan(colorffffffSpan, sb.length() - 2, sb.length(), 18);
        this.sizeText.setText(builder);
        this.mLastUrl = this.mCurrentUrl;
        this.flowTipsLine.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (FengVideoPlayer.this.mIsVideoFinalPage || FengVideoPlayer.this.mIsWatchVideo || FengVideoPlayer.this.currentScreen == 2) {
                    VideoUtil.hasClick4GPlay = true;
                    FengVideoPlayer.this.topContainer.setVisibility(8);
                    if (FengVideoPlayer.this.mCurrentPlaying != 1) {
                        FengVideoPlayer.this.mLastUrl = FengVideoPlayer.this.mCurrentUrl;
                        FengVideoPlayer.this.changeDefination(1);
                    } else {
                        FengVideoPlayer.this.show4GToast();
                        if (FengVideoPlayer.this.mCurrentState != 2) {
                            FengVideoPlayer.this.playVideo();
                        }
                    }
                    FengVideoPlayer.this.hideFlowTipsLine();
                    return;
                }
                FengVideoPlayer.this.startToVideoFinalPage();
            }
        });
    }

    public void hideFlowTipsLine() {
        this.flowTipsLine.setVisibility(8);
    }

    public void showNetWorkWorseLine() {
        if (!this.mIsVideoCache && this.mCurrentPlaying != 1) {
            String str = "网络环境不佳，建议您切换到标清画质";
            this.networkTipsLine.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    FengVideoPlayer.this.changeDefination(1);
                    FengVideoPlayer.this.hideNetWorkTipsLine();
                }
            });
            SpannableStringBuilder builder = new SpannableStringBuilder(str);
            ForegroundColorSpan color54Span = new ForegroundColorSpan(this.color_ffffff);
            ForegroundColorSpan color87Span = new ForegroundColorSpan(this.color_ffb90a);
            builder.setSpan(color54Span, 0, str.length() - 4, 33);
            builder.setSpan(color87Span, str.length() - 4, str.length(), 18);
            this.tips.setText(builder);
            this.networkTipsLine.setVisibility(0);
            this.networkTipsClose.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    JCMediaManager.instance().setNeedShowNetWorkWorseLine(false);
                    FengVideoPlayer.this.hideNetWorkTipsLine();
                }
            });
        }
    }

    public void hideNetWorkTipsLine() {
        this.networkTipsLine.setVisibility(8);
        JCMediaManager.instance().setNeedShowNetWorkWorseLine(false);
    }

    public void backToWifiPlay() {
        hideFlowTipsLine();
        if (!StringUtil.isEmpty(this.mLastUrl)) {
            if (this.mLastUrl.equals(this.mMediaInfo.getHDUrl())) {
                changeDefination(2);
            } else if (this.mLastUrl.equals(this.mMediaInfo.getFHDUrl())) {
                changeDefination(3);
            } else if (this.mLastUrl.equals(this.mMediaInfo.getBDUrl())) {
                changeDefination(4);
            } else if (this.mCurrentState != 2) {
                playVideo();
            }
        }
    }

    public void onDoubleClick() {
        super.onDoubleClick();
        playVideo();
    }
}
