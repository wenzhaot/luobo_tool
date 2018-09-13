package com.feng.car.view;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.feng.car.FengApplication;
import com.feng.car.activity.BaseActivity;
import com.feng.car.activity.ViewPointActivity;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.event.AudioStateEvent;
import com.feng.car.operation.SuccessFailCallback;
import com.feng.car.service.AudioPlayService;
import com.feng.car.service.AudioPlayService.MyBinder;
import com.feng.car.utils.ActivityManager;
import com.feng.car.utils.HttpConstant;
import com.feng.car.utils.JsonUtil;
import com.feng.car.utils.LogGatherReadUtil;
import com.feng.car.video.player.JCUtils;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.feng.library.utils.StringUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.HashMap;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

public class VoiceBoxView extends RelativeLayout {
    public static int STATE_LOADING = 2;
    public static int STATE_NORMAL = 0;
    public static int STATE_PLAYING = 1;
    public static int VBV_HIDE_AUTHOR = 2001;
    public static int VBV_SHOW_AUTHOR = 2002;
    private ServiceConnection connection;
    private int m24;
    private int m32;
    private int m5;
    private int m92;
    public AutoFrescoDraweeView mAfdPlay;
    private String mAudioUrl;
    private MyBinder mBinder;
    private Context mContext;
    private BaseActivity mCurrentActivity;
    private boolean mIsPlay;
    private ProgressBar mPbLoader;
    private Resources mResources;
    private SnsInfo mSnsInfo;
    private TextView mTvVoiceAuthor;
    private TextView mTvVoiceTime;
    private TextView mTvVoiceTips;
    private TextView mTvVoiceTitle;

    public VoiceBoxView(Context context) {
        this(context, null);
    }

    public VoiceBoxView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VoiceBoxView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mIsPlay = false;
        this.mContext = context;
        this.mResources = this.mContext.getResources();
        this.mCurrentActivity = ActivityManager.getInstance().getCurrentActivity();
        this.m5 = this.mResources.getDimensionPixelSize(2131296812);
        this.m24 = this.mResources.getDimensionPixelSize(2131296423);
        this.m32 = this.mResources.getDimensionPixelSize(2131296512);
        this.m92 = this.mResources.getDimensionPixelSize(2131296865);
        initView();
    }

    private void initView() {
        setBackgroundResource(2130838584);
        this.mAfdPlay = new AutoFrescoDraweeView(this.mContext);
        this.mAfdPlay.setId(2131623979);
        this.mAfdPlay.setAutoImageURI(Uri.parse("res://com.feng.car/2130838587"));
        this.mAfdPlay.setHierarchy(new GenericDraweeHierarchyBuilder(this.mResources).setPressedStateOverlay(this.mResources.getDrawable(2130838393)).setRoundingParams(RoundingParams.asCircle()).build());
        LayoutParams iVParams = new LayoutParams(this.m92, this.m92);
        iVParams.setMargins(this.m24, 0, 0, 0);
        iVParams.addRule(15);
        addView(this.mAfdPlay, iVParams);
        this.mPbLoader = new ProgressBar(this.mContext);
        this.mPbLoader.setId(2131623991);
        this.mPbLoader.setVisibility(8);
        LayoutParams loaderParams = new LayoutParams(this.m92 - this.m32, this.m92 - this.m32);
        loaderParams.setMargins(this.m24 + (this.m32 / 2), 0, 0, 0);
        loaderParams.addRule(15);
        addView(this.mPbLoader, loaderParams);
        this.mTvVoiceTitle = new TextView(this.mContext);
        this.mTvVoiceTitle.setId(2131624026);
        this.mTvVoiceTitle.setEllipsize(TruncateAt.END);
        this.mTvVoiceTitle.setMaxLines(1);
        this.mTvVoiceTitle.setTextAppearance(this.mContext, 2131362230);
        this.mTvVoiceTitle.setTextColor(ContextCompat.getColor(this.mContext, 2131558478));
        LayoutParams titleParams = new LayoutParams(-2, -2);
        titleParams.setMargins(this.m24, this.m5, this.m24, 0);
        titleParams.addRule(6, 2131623979);
        titleParams.addRule(1, 2131623979);
        addView(this.mTvVoiceTitle, titleParams);
        this.mTvVoiceAuthor = new TextView(this.mContext);
        this.mTvVoiceAuthor.setId(2131624023);
        this.mTvVoiceAuthor.setEllipsize(TruncateAt.END);
        this.mTvVoiceAuthor.setMaxLines(1);
        this.mTvVoiceAuthor.setTextAppearance(this.mContext, 2131362226);
        this.mTvVoiceAuthor.setTextColor(ContextCompat.getColor(this.mContext, 2131558448));
        LayoutParams authorParams = new LayoutParams(-2, -2);
        authorParams.setMargins(this.m24, 0, 0, this.m5);
        authorParams.addRule(8, 2131623979);
        authorParams.addRule(1, 2131623979);
        addView(this.mTvVoiceAuthor, authorParams);
        this.mTvVoiceTime = new TextView(this.mContext);
        this.mTvVoiceTime.setId(2131624024);
        this.mTvVoiceTime.setEllipsize(TruncateAt.END);
        this.mTvVoiceTime.setMaxLines(1);
        this.mTvVoiceTime.setTextAppearance(this.mContext, 2131362226);
        this.mTvVoiceTime.setTextColor(ContextCompat.getColor(this.mContext, 2131558448));
        LayoutParams timeParams = new LayoutParams(-2, -2);
        timeParams.setMargins(this.m24, 0, 0, this.m5);
        timeParams.addRule(8, 2131623979);
        timeParams.addRule(1, 2131624023);
        addView(this.mTvVoiceTime, timeParams);
        this.mTvVoiceTips = new TextView(this.mContext);
        this.mTvVoiceTips.setId(2131624025);
        this.mTvVoiceTips.setEllipsize(TruncateAt.END);
        this.mTvVoiceTips.setMaxLines(1);
        this.mTvVoiceTips.setTextAppearance(this.mContext, 2131362226);
        this.mTvVoiceTips.setTextColor(ContextCompat.getColor(this.mContext, 2131558549));
        LayoutParams progressParams = new LayoutParams(-2, -2);
        progressParams.setMargins(0, 0, this.m32, 0);
        progressParams.addRule(8, 2131624024);
        progressParams.addRule(11);
        addView(this.mTvVoiceTips, progressParams);
    }

    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus && this.mSnsInfo != null && this.mSnsInfo.audio != null && FengApplication.getInstance().getAudioState().audioHash.equals(this.mSnsInfo.audio.hash)) {
            onRegisteredEventBus();
            restoreBoxState();
        }
    }

    public void initVoiceBox(SnsInfo info, int showType, final String strExpended) {
        this.mSnsInfo = info;
        if (this.mSnsInfo != null && this.mSnsInfo.audio != null) {
            if (FengApplication.getInstance().getAudioState().audioHash.equals(this.mSnsInfo.audio.hash)) {
                onRegisteredEventBus();
            }
            restoreBoxState();
            setTime(this.mSnsInfo.audio.playtime);
            setTitle(this.mSnsInfo.discussinfo.title);
            if (showType == VBV_HIDE_AUTHOR) {
                hideAuthor();
            } else {
                setAuthor((String) this.mSnsInfo.user.name.get());
            }
            if (this.mCurrentActivity instanceof ViewPointActivity) {
                setClickable(false);
            } else {
                setOnClickListener(new OnSingleClickListener() {
                    public void onSingleClick(View v) {
                        if (!TextUtils.isEmpty(strExpended)) {
                            LogGatherReadUtil.getInstance().addLocationClick(VoiceBoxView.this.mSnsInfo.id, VoiceBoxView.this.mSnsInfo.resourceid, VoiceBoxView.this.mSnsInfo.snstype, strExpended);
                        }
                        VoiceBoxView.this.mSnsInfo.intentToViewPoint(VoiceBoxView.this.mContext, false);
                    }
                });
            }
            this.mAfdPlay.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    VoiceBoxView.this.readyToPlay();
                }
            });
        }
    }

    public void initVoiceBox(SnsInfo info, int showType) {
        initVoiceBox(info, showType, "");
    }

    private void readyToPlay() {
        this.mSnsInfo.checkSnsState(this.mContext, true, new SuccessFailCallback() {
            public void onSuccess() {
                super.onSuccess();
                if (AudioStateEvent.PLAYING_STATE == FengApplication.getInstance().getAudioState().audioState && FengApplication.getInstance().getAudioState().audioHash.equals(VoiceBoxView.this.mSnsInfo.audio.hash)) {
                    Intent intentTo = new Intent(VoiceBoxView.this.mContext, AudioPlayService.class);
                    intentTo.putExtra(AudioPlayService.ENTRANCE_TYPE_KEY, 1002);
                    VoiceBoxView.this.mContext.startService(intentTo);
                } else if (AudioStateEvent.PAUSE_STATE == FengApplication.getInstance().getAudioState().audioState && FengApplication.getInstance().getAudioState().audioHash.equals(VoiceBoxView.this.mSnsInfo.audio.hash)) {
                    VoiceBoxView.this.bindService();
                    if (VoiceBoxView.this.mBinder != null) {
                        VoiceBoxView.this.mBinder.playAudioPlayer();
                    }
                    VoiceBoxView.this.unbindService();
                } else {
                    VoiceBoxView.this.getAudioInfoHash();
                }
            }

            public void onFail() {
                super.onFail();
                ((BaseActivity) VoiceBoxView.this.mContext).showSecondTypeToast(2131231273);
            }
        });
    }

    private void getAudioInfoHash() {
        Map<String, Object> map = new HashMap();
        map.put(HttpConstant.AUDIOHASH, this.mSnsInfo.audio.hash);
        map.put(HttpConstant.AUDIOID, String.valueOf(this.mSnsInfo.audio.id));
        FengApplication.getInstance().httpRequest(HttpConstant.SNS_AUTO_DISCUSS_AUDIOPLAY, map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                ((BaseActivity) VoiceBoxView.this.mContext).showSecondTypeToast(2131231273);
            }

            public void onStart() {
                VoiceBoxView.this.setVoiceLocalState(VoiceBoxView.STATE_LOADING);
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                ((BaseActivity) VoiceBoxView.this.mContext).showSecondTypeToast(2131231273);
                VoiceBoxView.this.setVoiceLocalState(VoiceBoxView.STATE_NORMAL);
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    int code = jsonResult.getInt("code");
                    if (code == 1) {
                        VoiceBoxView.this.mAudioUrl = jsonResult.getJSONObject("body").getString(HttpConstant.AUDIOURL);
                        if (StringUtil.isEmpty(VoiceBoxView.this.mAudioUrl)) {
                            ((BaseActivity) VoiceBoxView.this.mContext).showSecondTypeToast(2131231456);
                            return;
                        } else {
                            VoiceBoxView.this.startPlay();
                            return;
                        }
                    }
                    VoiceBoxView.this.checkErrorCode(code);
                } catch (Exception e) {
                    e.printStackTrace();
                    FengApplication.getInstance().upLoadTryCatchLog(HttpConstant.AUDIOPAY_AUDIOPLAY, content, e);
                    ((BaseActivity) VoiceBoxView.this.mContext).showSecondTypeToast(2131231456);
                }
            }
        });
    }

    private void startPlay() {
        Intent intentTo = new Intent(this.mContext, AudioPlayService.class);
        intentTo.putExtra(AudioPlayService.ENTRANCE_TYPE_KEY, 1001);
        intentTo.putExtra(AudioPlayService.AUDIO_PLAY_INFO_JSON, JsonUtil.toJson(this.mSnsInfo));
        intentTo.putExtra(AudioPlayService.AUDIO_URL_KEY, this.mAudioUrl);
        this.mContext.startService(intentTo);
        onRegisteredEventBus();
    }

    private void checkErrorCode(int code) {
        if (code == -69) {
            ((BaseActivity) this.mContext).showThirdTypeToast(2131231455);
        } else if (code == -68) {
            ((BaseActivity) this.mContext).showSecondTypeToast(2131231457);
        } else {
            ((BaseActivity) this.mContext).showSecondTypeToast(2131231456);
        }
        setVoiceLocalState(STATE_NORMAL);
    }

    private void setVoiceLocalState(int localState) {
        this.mSnsInfo.voiceLocalStates = localState;
        restoreBoxState();
        this.mIsPlay = this.mSnsInfo.voiceLocalStates == STATE_PLAYING;
    }

    private void restoreBoxState() {
        if (this.mSnsInfo.voiceLocalStates == STATE_LOADING) {
            showAudioLoadingProgress();
            return;
        }
        hideAudioLoadingProgress();
        if (this.mSnsInfo.voiceLocalStates == STATE_PLAYING) {
            changeBoxStates(true);
        } else if (this.mSnsInfo.voiceLocalStates == STATE_NORMAL) {
            changeBoxStates(false);
            setTips("");
        }
    }

    private void changeBoxStates(boolean isPlaying) {
        if (this.mIsPlay != isPlaying) {
            this.mIsPlay = isPlaying;
            if (isPlaying) {
                this.mAfdPlay.setAutoImageURI(Uri.parse("res://com.feng.car/2130838585"));
            } else {
                this.mAfdPlay.setAutoImageURI(Uri.parse("res://com.feng.car/2130838587"));
            }
            setSelected(isPlaying);
            this.mAfdPlay.setSelected(isPlaying);
        }
    }

    private void showAudioLoadingProgress() {
        this.mPbLoader.setVisibility(0);
        this.mAfdPlay.setVisibility(4);
    }

    private void hideAudioLoadingProgress() {
        this.mPbLoader.setVisibility(8);
        this.mAfdPlay.setVisibility(0);
    }

    private void onRegisteredEventBus() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    private void unRegisterEventBus() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(AudioStateEvent event) {
        if (event != null) {
            if (FengApplication.getInstance().getAudioState().audioHash.equals(this.mSnsInfo.audio.hash)) {
                if (event.audioState == AudioStateEvent.PLAYING_STATE) {
                    setVoiceLocalState(STATE_PLAYING);
                    setProgress((long) event.progress, event.totalProgress);
                } else {
                    setVoiceLocalState(STATE_NORMAL);
                }
            } else if (event.audioState == AudioStateEvent.PLAYING_START) {
                setVoiceLocalState(STATE_NORMAL);
                unRegisterEventBus();
            }
            if (event.audioState == AudioStateEvent.FINISH_STATE) {
                setTips("");
            }
        }
    }

    private void bindService() {
        this.connection = new ServiceConnection() {
            public void onServiceDisconnected(ComponentName name) {
            }

            public void onServiceConnected(ComponentName name, IBinder service) {
                VoiceBoxView.this.mBinder = (MyBinder) service;
            }
        };
        this.mContext.bindService(new Intent(this.mContext, AudioPlayService.class), this.connection, 1);
    }

    private void unbindService() {
        if (this.connection != null) {
            this.mContext.unbindService(this.connection);
            this.connection = null;
        }
    }

    private void hideAuthor() {
        this.mTvVoiceAuthor.setVisibility(8);
    }

    private void setProgress(long progress, int totalProgress) {
        setTips("已播" + ((100 * progress) / ((long) totalProgress)) + "%");
    }

    private void setTime(long time) {
        this.mTvVoiceTime.setText("时长" + JCUtils.stringForTime(((int) time) * 1000));
    }

    private void setTips(String tips) {
        this.mTvVoiceTips.setText(tips);
    }

    private void setTitle(String title) {
        this.mTvVoiceTitle.setText(title);
    }

    private void setAuthor(String author) {
        this.mTvVoiceAuthor.setText(author);
    }
}
