package com.feng.car.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.feng.car.FengApplication;
import com.feng.car.activity.AudioPlayDetailActivity;
import com.feng.car.activity.BaseActivity;
import com.feng.car.activity.CameraActivity;
import com.feng.car.activity.VideoFinalPageActivity;
import com.feng.car.activity.ViewPointActivity;
import com.feng.car.activity.WatchVideoActivity;
import com.feng.car.activity.WebActivity;
import com.feng.car.entity.audio.AudioRecordInfo;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.event.AudioStateEvent;
import com.feng.car.event.RecyclerviewDirectionEvent;
import com.feng.car.event.SnsInfoRefreshEvent;
import com.feng.car.event.UserInfoRefreshEvent;
import com.feng.car.utils.ActivityManager;
import com.feng.car.utils.AudioRecordInfoManager;
import com.feng.car.utils.HttpConstant;
import com.feng.car.utils.JsonUtil;
import com.umeng.socialize.net.dplus.CommonNetImpl;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class AudioPlayService extends Service {
    public static final String AUDIO_PLAY_INFO_JSON = "AUDIO_PLAY_INFO_JSON";
    public static final String AUDIO_URL_KEY = "AUDIO_URL_KEY";
    public static final int ENTRANCE_NOT_SET_DATA_TYPE = 1002;
    public static final int ENTRANCE_SET_DATA_TYPE = 1001;
    public static final String ENTRANCE_TYPE_KEY = "ENTRANCE_TYPE_KEY";
    private boolean isInitializing = false;
    private boolean isPhoneCallRinging = false;
    private OnAudioFocusChangeListener mAudioFocusListener = new OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange) {
                case -3:
                case -2:
                    if (AudioPlayService.this.mBinder.isPlaying()) {
                        BaseActivity activity = ActivityManager.getInstance().getCurrentActivity();
                        if (activity != null && ((activity instanceof VideoFinalPageActivity) || (activity instanceof WebActivity) || (activity instanceof WatchVideoActivity) || (activity instanceof CameraActivity))) {
                            AudioPlayService.this.mPausedByTransientLossOfFocus = false;
                            AudioPlayService.this.mBinder.pauseAudioPlayer();
                            return;
                        } else if (AudioPlayService.this.isPhoneCallRinging) {
                            AudioPlayService.this.mPausedByTransientLossOfFocus = false;
                            AudioPlayService.this.mBinder.pauseAudioPlayer();
                            return;
                        } else {
                            return;
                        }
                    }
                    return;
                case -1:
                    if (AudioPlayService.this.mBinder.isPlaying()) {
                        AudioPlayService.this.mPausedByTransientLossOfFocus = false;
                        AudioPlayService.this.mBinder.pauseAudioPlayer();
                        return;
                    }
                    return;
                case 1:
                    if (!AudioPlayService.this.mBinder.isPlaying() && AudioPlayService.this.mPausedByTransientLossOfFocus) {
                        AudioPlayService.this.mPausedByTransientLossOfFocus = false;
                        AudioPlayService.this.mBinder.playAudioPlayer();
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    };
    private AudioManager mAudioManager;
    private MediaPlayer mAudioPlayer;
    private AudioRecordInfo mAudioRecordInfo;
    private SnsInfo mAudioSnsInfo;
    private String mAudioUrl = "";
    private MyBinder mBinder = new MyBinder();
    public Handler mHandler = new Handler();
    private AudioRecordInfo mLastAudioRecordInfo;
    private boolean mPausedByTransientLossOfFocus = true;
    private MyPhoneStateListener mPhoneStateListener = new MyPhoneStateListener(this, null);
    public Handler mPlayDelayHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            FengApplication.getInstance().getAudioState().audioHash = AudioPlayService.this.mAudioSnsInfo.audio.hash;
            AudioPlayService.this.mBinder.playAudioPlayer();
            EventBus.getDefault().post(new RecyclerviewDirectionEvent(false, true));
            AudioPlayService.this.mHandler.post(AudioPlayService.this.mTimerRunnable);
        }
    };
    private TelephonyManager mTelephonyManager;
    private Runnable mTimerRunnable = new Runnable() {
        public void run() {
            if (!AudioPlayService.this.isInitializing) {
                AudioPlayService.this.writeRecord();
            }
            AudioPlayService.this.mHandler.postDelayed(AudioPlayService.this.mTimerRunnable, 500);
        }
    };

    public class MyBinder extends Binder {
        private boolean mAlreadyStop = false;
        private boolean mIsNext = false;

        public MediaPlayer getMediaPlayer() {
            return AudioPlayService.this.mAudioPlayer;
        }

        public void initAudioPlayer() {
            try {
                AudioPlayService.this.mAudioPlayer.reset();
                AudioPlayService.this.mAudioPlayer.setAudioStreamType(3);
                AudioPlayService.this.mAudioPlayer.setDataSource(AudioPlayService.this, Uri.parse(AudioPlayService.this.mAudioUrl));
                AudioPlayService.this.mAudioPlayer.setOnCompletionListener(new OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                        if (!MyBinder.this.mAlreadyStop) {
                            if (MyBinder.this.mIsNext) {
                                MyBinder.this.initAudioPlayer();
                            } else {
                                MyBinder.this.stopAudioPlayer(false, false);
                            }
                        }
                    }
                });
                AudioPlayService.this.mAudioPlayer.prepareAsync();
                AudioPlayService.this.mAudioPlayer.setOnPreparedListener(new OnPreparedListener() {
                    public void onPrepared(MediaPlayer mp) {
                        MyBinder.this.mAlreadyStop = false;
                        MyBinder.this.mIsNext = false;
                        if (AudioPlayService.this.mAudioRecordInfo != null) {
                            MyBinder.this.seekToPosition(AudioPlayService.this.mAudioRecordInfo.voiceRecordPosition);
                        }
                        if (AudioPlayService.this.mAudioRecordInfo.voiceRecordPosition == 0) {
                            AudioPlayService.this.mPlayDelayHandler.sendEmptyMessage(1);
                        } else {
                            AudioPlayService.this.mPlayDelayHandler.sendEmptyMessageDelayed(1, 500);
                        }
                        AudioPlayService.this.isInitializing = false;
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public boolean isPlaying() {
            if (AudioPlayService.this.mAudioPlayer != null) {
                return AudioPlayService.this.mAudioPlayer.isPlaying();
            }
            return false;
        }

        public void playAudioPlayer() {
            AudioPlayService.this.mAudioManager.requestAudioFocus(AudioPlayService.this.mAudioFocusListener, 3, 2);
            if (AudioPlayService.this.mAudioPlayer != null) {
                if (!AudioPlayService.this.isInitializing) {
                    EventBus.getDefault().post(new AudioStateEvent(AudioStateEvent.PLAYING_START));
                    EventBus.getDefault().post(new AudioStateEvent(AudioStateEvent.PLAYING_STATE, AudioPlayService.this.mBinder.getTotalDuration(), AudioPlayService.this.mBinder.getCurrentPosition()));
                }
                AudioPlayService.this.mAudioPlayer.start();
                AudioPlayService.this.mHandler.post(AudioPlayService.this.mTimerRunnable);
                AudioPlayService.this.isInitializing = false;
            }
        }

        public void pauseAudioPlayer() {
            if (AudioPlayService.this.mAudioPlayer != null) {
                EventBus.getDefault().post(new AudioStateEvent(AudioStateEvent.PAUSE_STATE));
                AudioPlayService.this.mAudioPlayer.pause();
                AudioPlayService.this.mHandler.removeCallbacks(AudioPlayService.this.mTimerRunnable);
            }
            if (AudioPlayService.this.mAudioManager != null) {
                AudioPlayService.this.mAudioManager.abandonAudioFocus(AudioPlayService.this.mAudioFocusListener);
            }
        }

        private void setIsNext(boolean isNext) {
            this.mIsNext = isNext;
        }

        public void stopAudioPlayer(boolean isNeedReInit, boolean isSendNormalStatus) {
            if (AudioPlayService.this.mAudioPlayer != null) {
                try {
                    this.mAlreadyStop = true;
                    AudioPlayService.this.mHandler.removeCallbacks(AudioPlayService.this.mTimerRunnable);
                    if (!isNeedReInit) {
                        AudioRecordInfoManager.getInstance().removeRecord(AudioPlayService.this.mAudioRecordInfo.viewPointVoiceHash);
                    } else if (!AudioPlayService.this.isInitializing) {
                        AudioPlayService.this.writeRecord();
                    }
                    AudioPlayService.this.mAudioPlayer.pause();
                    AudioPlayService.this.mAudioPlayer.seekTo(0);
                    EventBus.getDefault().post(new AudioStateEvent(AudioStateEvent.PAUSE_STATE, AudioPlayService.this.mBinder.getTotalDuration(), 0));
                    EventBus.getDefault().post(new AudioStateEvent(AudioStateEvent.FINISH_STATE, AudioPlayService.this.mBinder.getTotalDuration(), 0));
                    if (isSendNormalStatus) {
                        EventBus.getDefault().post(new AudioStateEvent(AudioStateEvent.NORMAL_STATE));
                    }
                    if (isNeedReInit) {
                        AudioPlayService.this.mBinder.initAudioPlayer();
                    }
                    if (AudioPlayService.this.mAudioManager != null) {
                        AudioPlayService.this.mAudioManager.abandonAudioFocus(AudioPlayService.this.mAudioFocusListener);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void seekToPosition(int currentTime) {
            if (currentTime >= 0) {
                AudioPlayService.this.mAudioPlayer.seekTo(currentTime);
            }
        }

        public int getCurrentPosition() {
            if (AudioPlayService.this.mAudioPlayer != null) {
                return AudioPlayService.this.mAudioPlayer.getCurrentPosition();
            }
            return 0;
        }

        public int getTotalDuration() {
            if (AudioPlayService.this.mAudioPlayer != null) {
                return AudioPlayService.this.mAudioPlayer.getDuration();
            }
            return 0;
        }

        public SnsInfo getAudioSnsInfo() {
            return AudioPlayService.this.mAudioSnsInfo;
        }
    }

    private class MyPhoneStateListener extends PhoneStateListener {
        private MyPhoneStateListener() {
        }

        /* synthetic */ MyPhoneStateListener(AudioPlayService x0, AnonymousClass1 x1) {
            this();
        }

        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case 0:
                    AudioPlayService.this.isPhoneCallRinging = false;
                    return;
                case 1:
                    AudioPlayService.this.isPhoneCallRinging = true;
                    return;
                default:
                    return;
            }
        }
    }

    private void writeRecord() {
        try {
            int currentPosition = this.mBinder.getCurrentPosition();
            int totalProgress = this.mBinder.getTotalDuration();
            if (currentPosition >= totalProgress) {
                EventBus.getDefault().post(new AudioStateEvent(AudioStateEvent.NORMAL_STATE));
                this.mAudioRecordInfo.voiceRecordPosition = 0;
            } else {
                EventBus.getDefault().post(new AudioStateEvent(AudioStateEvent.PLAYING_STATE, totalProgress, currentPosition));
                this.mAudioRecordInfo.voiceRecordPosition = currentPosition;
            }
            AudioRecordInfoManager.getInstance().updatePosition(this.mAudioRecordInfo.viewPointVoiceHash, this.mAudioRecordInfo.voiceRecordPosition);
        } catch (Exception e) {
        }
    }

    public void onCreate() {
        super.onCreate();
        this.mAudioPlayer = new MediaPlayer();
        this.mAudioManager = (AudioManager) getSystemService("audio");
        EventBus.getDefault().register(this);
        this.mTelephonyManager = (TelephonyManager) getSystemService(HttpConstant.PHONE);
        this.mTelephonyManager.listen(this.mPhoneStateListener, 32);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            int entranceType = intent.getIntExtra(ENTRANCE_TYPE_KEY, 1002);
            if (entranceType == 1001) {
                this.isInitializing = true;
                String audioPlayJson = intent.getStringExtra(AUDIO_PLAY_INFO_JSON);
                this.mAudioUrl = intent.getStringExtra(AUDIO_URL_KEY);
                this.mAudioSnsInfo = (SnsInfo) JsonUtil.fromJson(audioPlayJson, SnsInfo.class);
                this.mAudioRecordInfo = new AudioRecordInfo(this.mAudioSnsInfo.audio.hash);
                AudioRecordInfo info = AudioRecordInfoManager.getInstance().getRecord(this.mAudioRecordInfo.viewPointVoiceHash);
                if (info != null) {
                    this.mAudioRecordInfo.voiceRecordPosition = info.voiceRecordPosition;
                } else {
                    AudioRecordInfoManager.getInstance().addRecord(this.mAudioRecordInfo);
                }
                if (this.mBinder.isPlaying()) {
                    this.mBinder.setIsNext(true);
                    this.mBinder.stopAudioPlayer(true, false);
                } else if (this.mLastAudioRecordInfo == null) {
                    this.mBinder.initAudioPlayer();
                } else if (TextUtils.equals(this.mLastAudioRecordInfo.viewPointVoiceHash, this.mAudioSnsInfo.audio.hash)) {
                    this.mBinder.playAudioPlayer();
                } else {
                    this.mBinder.initAudioPlayer();
                }
                if (this.mLastAudioRecordInfo == null) {
                    this.mLastAudioRecordInfo = new AudioRecordInfo(this.mAudioSnsInfo.audio.hash);
                } else {
                    this.mLastAudioRecordInfo.viewPointVoiceHash = this.mAudioSnsInfo.audio.hash;
                }
            } else if (entranceType == 1002) {
                BaseActivity currentActivity = ActivityManager.getInstance().getCurrentActivity();
                Intent intentTo = new Intent(this, AudioPlayDetailActivity.class);
                intentTo.addFlags(CommonNetImpl.FLAG_AUTH);
                if (currentActivity != null) {
                    if ((currentActivity instanceof ViewPointActivity) && this.mAudioSnsInfo != null && ((ViewPointActivity) currentActivity).getSnsId() == this.mAudioSnsInfo.id) {
                        intentTo.putExtra("is_finish_page", true);
                    }
                    currentActivity.startActivity(intentTo);
                    currentActivity.overridePendingTransition(2130968586, 0);
                } else {
                    startActivity(intentTo);
                }
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();
        try {
            if (this.mAudioPlayer != null) {
                this.mAudioPlayer.stop();
                this.mAudioPlayer.release();
                this.mAudioPlayer = null;
                this.mHandler.removeCallbacks(this.mTimerRunnable);
                if (!(this.mAudioManager == null || this.mAudioFocusListener == null)) {
                    this.mAudioManager.abandonAudioFocus(this.mAudioFocusListener);
                }
            }
            EventBus.getDefault().unregister(this);
            this.mTelephonyManager.listen(this.mPhoneStateListener, 0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public IBinder onBind(Intent intent) {
        return this.mBinder;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(UserInfoRefreshEvent event) {
        if (this.mAudioSnsInfo != null) {
            UserInfo userInfo = this.mAudioSnsInfo.user;
            if (event.userInfo != null && userInfo.id == event.userInfo.id && event.type == 2) {
                userInfo.isfollow.set(event.userInfo.isfollow.get());
                userInfo.fanscount.set(event.userInfo.fanscount.get());
                userInfo.followcount.set(event.userInfo.followcount.get());
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SnsInfoRefreshEvent snsInfoRefreshEvent) {
        if (snsInfoRefreshEvent != null && snsInfoRefreshEvent.refreshType == 2004 && this.mAudioSnsInfo != null && this.mAudioSnsInfo.id == snsInfoRefreshEvent.snsInfo.id) {
            this.mAudioSnsInfo.praisecount.set(snsInfoRefreshEvent.snsInfo.praisecount.get());
            this.mAudioSnsInfo.ispraise.set(snsInfoRefreshEvent.snsInfo.ispraise.get());
        }
    }
}
