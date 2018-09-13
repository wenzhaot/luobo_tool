package com.feng.car.activity;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.databinding.ActivityAudioPlayDetailBinding;
import com.feng.car.databinding.ArticleShareDialogBinding;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.event.AudioStateEvent;
import com.feng.car.listener.FengUMShareListener;
import com.feng.car.operation.SuccessFailCallback;
import com.feng.car.service.AudioPlayService;
import com.feng.car.service.AudioPlayService.MyBinder;
import com.feng.car.utils.FengUtil;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.bean.SHARE_MEDIA;
import java.util.HashMap;
import java.util.Map;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

public class AudioPlayDetailActivity extends BaseActivity<ActivityAudioPlayDetailBinding> {
    private ServiceConnection connection = new ServiceConnection() {
        public void onServiceDisconnected(ComponentName name) {
        }

        public void onServiceConnected(ComponentName name, IBinder service) {
            AudioPlayDetailActivity.this.mBinder = (MyBinder) service;
            AudioPlayDetailActivity.this.mAudioSnsInfo = AudioPlayDetailActivity.this.mBinder.getAudioSnsInfo();
            if (AudioPlayDetailActivity.this.mAudioSnsInfo != null) {
                ((ActivityAudioPlayDetailBinding) AudioPlayDetailActivity.this.mBaseBinding).setSnsInfo(AudioPlayDetailActivity.this.mAudioSnsInfo);
                ((ActivityAudioPlayDetailBinding) AudioPlayDetailActivity.this.mBaseBinding).setUserInfo(AudioPlayDetailActivity.this.mAudioSnsInfo.user);
                AudioPlayDetailActivity.this.mViewPointShareListener = new FengUMShareListener(AudioPlayDetailActivity.this, AudioPlayDetailActivity.this.mAudioSnsInfo);
                String imageUrl = FengUtil.getUniformScaleUrl(AudioPlayDetailActivity.this.mAudioSnsInfo.discussinfo.image, 640, 0.56f);
                ((ActivityAudioPlayDetailBinding) AudioPlayDetailActivity.this.mBaseBinding).tvAudioDetailTitle.setText(AudioPlayDetailActivity.this.mAudioSnsInfo.discussinfo.title);
                ((ActivityAudioPlayDetailBinding) AudioPlayDetailActivity.this.mBaseBinding).userImage.setHeadUrl(FengUtil.getHeadImageUrl(AudioPlayDetailActivity.this.mAudioSnsInfo.user.getHeadImageInfo()));
                ((ActivityAudioPlayDetailBinding) AudioPlayDetailActivity.this.mBaseBinding).afdAudioDetailCover.setAspectRatio(1.78f);
                ((ActivityAudioPlayDetailBinding) AudioPlayDetailActivity.this.mBaseBinding).afdAudioDetailCover.setImageURI(imageUrl);
                ((ActivityAudioPlayDetailBinding) AudioPlayDetailActivity.this.mBaseBinding).seekAudioDetailBar.setMax(AudioPlayDetailActivity.this.mBinder.getTotalDuration());
                ((ActivityAudioPlayDetailBinding) AudioPlayDetailActivity.this.mBaseBinding).seekAudioDetailBar.setProgress(AudioPlayDetailActivity.this.mBinder.getCurrentPosition());
                ((ActivityAudioPlayDetailBinding) AudioPlayDetailActivity.this.mBaseBinding).tvAudioCurrentTime.setText(AudioPlayDetailActivity.this.formatTime((long) AudioPlayDetailActivity.this.mBinder.getCurrentPosition()));
                ((ActivityAudioPlayDetailBinding) AudioPlayDetailActivity.this.mBaseBinding).tvAudioTotalTime.setText(AudioPlayDetailActivity.this.formatTime((long) AudioPlayDetailActivity.this.mBinder.getTotalDuration()));
                AudioPlayDetailActivity.this.isAudioPlaying = AudioPlayDetailActivity.this.mBinder.isPlaying();
                if (AudioPlayDetailActivity.this.isAudioPlaying) {
                    ((ActivityAudioPlayDetailBinding) AudioPlayDetailActivity.this.mBaseBinding).ivAudioPlayOrPause.setImageResource(R.drawable.audio_detail_pause_btn_selector);
                } else {
                    ((ActivityAudioPlayDetailBinding) AudioPlayDetailActivity.this.mBaseBinding).ivAudioPlayOrPause.setImageResource(R.drawable.audio_detail_play_btn_selector);
                }
                if (AudioPlayDetailActivity.this.mAudioSnsInfo.ispraise.get() == 1) {
                    ((ActivityAudioPlayDetailBinding) AudioPlayDetailActivity.this.mBaseBinding).ivAudioPraise.setImageResource(R.drawable.audio_detail_praise_already_btn_selector);
                } else {
                    ((ActivityAudioPlayDetailBinding) AudioPlayDetailActivity.this.mBaseBinding).ivAudioPraise.setImageResource(R.drawable.audio_detail_praise_btn_selector);
                }
            }
        }
    };
    private boolean isAudioPlaying = true;
    private SnsInfo mAudioSnsInfo;
    private MyBinder mBinder;
    private Drawable mCancelFollowDrawable;
    private Drawable mFollowDrawable;
    private boolean mIsFinsh = false;
    private boolean mIsStopService = false;
    private float mPressedDownY;
    private Dialog mShareDialog;
    private FengUMShareListener mViewPointShareListener;
    private ArticleShareDialogBinding mViewPointVoiceShareBinding;

    public int setBaseContentView() {
        return R.layout.activity_audio_play_detail;
    }

    public void initView() {
        if (getIntent() != null) {
            this.mIsFinsh = getIntent().getBooleanExtra("is_finish_page", false);
        }
        hideDefaultTitleBar();
        closeSwip();
        initData();
        ((ActivityAudioPlayDetailBinding) this.mBaseBinding).ivAudioPlayOrPause.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (AudioPlayDetailActivity.this.isAudioPlaying) {
                    AudioPlayDetailActivity.this.isAudioPlaying = false;
                    ((ActivityAudioPlayDetailBinding) AudioPlayDetailActivity.this.mBaseBinding).ivAudioPlayOrPause.setImageResource(R.drawable.audio_detail_play_btn_selector);
                    AudioPlayDetailActivity.this.mBinder.pauseAudioPlayer();
                    return;
                }
                AudioPlayDetailActivity.this.isAudioPlaying = true;
                ((ActivityAudioPlayDetailBinding) AudioPlayDetailActivity.this.mBaseBinding).ivAudioPlayOrPause.setImageResource(R.drawable.audio_detail_pause_btn_selector);
                AudioPlayDetailActivity.this.mBinder.playAudioPlayer();
            }
        });
        ((ActivityAudioPlayDetailBinding) this.mBaseBinding).tvAudioDetailQuitListening.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                MobclickAgent.onEvent(AudioPlayDetailActivity.this, "voice_close");
                AudioPlayDetailActivity.this.mBinder.stopAudioPlayer(false, true);
                AudioPlayDetailActivity.this.mIsStopService = true;
                AudioPlayDetailActivity.this.finishActivity();
            }
        });
        ((ActivityAudioPlayDetailBinding) this.mBaseBinding).ivFollow.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                MobclickAgent.onEvent(AudioPlayDetailActivity.this, "voice_follow");
                AudioPlayDetailActivity.this.mAudioSnsInfo.user.followOperation(AudioPlayDetailActivity.this, null);
            }
        });
        ((ActivityAudioPlayDetailBinding) this.mBaseBinding).ivAudioPraise.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                MobclickAgent.onEvent(AudioPlayDetailActivity.this, "voice_like");
                ((ActivityAudioPlayDetailBinding) AudioPlayDetailActivity.this.mBaseBinding).ivAudioPraise.setEnabled(false);
                AudioPlayDetailActivity.this.mAudioSnsInfo.praiseOperation(AudioPlayDetailActivity.this, true, new SuccessFailCallback() {
                    public void onSuccess() {
                        ((ActivityAudioPlayDetailBinding) AudioPlayDetailActivity.this.mBaseBinding).ivAudioPraise.setEnabled(true);
                    }

                    public void onFail() {
                        ((ActivityAudioPlayDetailBinding) AudioPlayDetailActivity.this.mBaseBinding).ivAudioPraise.setEnabled(true);
                    }
                });
            }
        });
        ((ActivityAudioPlayDetailBinding) this.mBaseBinding).ivAudioShare.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                MobclickAgent.onEvent(AudioPlayDetailActivity.this, "voice_share");
                AudioPlayDetailActivity.this.showViewPointVoiceShareDialog();
            }
        });
        ((ActivityAudioPlayDetailBinding) this.mBaseBinding).seekAudioDetailBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    MobclickAgent.onEvent(AudioPlayDetailActivity.this, "voice_guage");
                    ((ActivityAudioPlayDetailBinding) AudioPlayDetailActivity.this.mBaseBinding).tvAudioCurrentTime.setText(AudioPlayDetailActivity.this.formatTime((long) progress));
                    AudioPlayDetailActivity.this.mBinder.seekToPosition(progress);
                    if (!AudioPlayDetailActivity.this.isAudioPlaying) {
                        ((ActivityAudioPlayDetailBinding) AudioPlayDetailActivity.this.mBaseBinding).ivAudioPlayOrPause.performClick();
                    }
                }
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        ((ActivityAudioPlayDetailBinding) this.mBaseBinding).rlAudioHeadInfoContainer.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                float x = event.getX();
                float y = event.getY();
                switch (event.getAction()) {
                    case 0:
                        AudioPlayDetailActivity.this.mPressedDownY = y;
                        break;
                    case 2:
                        float deltaY = y - AudioPlayDetailActivity.this.mPressedDownY;
                        if (Math.abs(deltaY) >= 200.0f && deltaY > 0.0f) {
                            AudioPlayDetailActivity.this.finishActivity();
                            break;
                        }
                }
                return true;
            }
        });
        ((ActivityAudioPlayDetailBinding) this.mBaseBinding).llAudioDownArrowContainer.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                AudioPlayDetailActivity.this.finishActivity();
            }
        });
        ((ActivityAudioPlayDetailBinding) this.mBaseBinding).userImage.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (AudioPlayDetailActivity.this.mAudioSnsInfo != null) {
                    AudioPlayDetailActivity.this.mAudioSnsInfo.user.intentToPersonalHome(AudioPlayDetailActivity.this);
                    AudioPlayDetailActivity.this.finishActivity();
                }
            }
        });
        ((ActivityAudioPlayDetailBinding) this.mBaseBinding).afdAudioDetailCover.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                AudioPlayDetailActivity.this.handleIntent();
            }
        });
        ((ActivityAudioPlayDetailBinding) this.mBaseBinding).tvAudioDetailTitle.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                AudioPlayDetailActivity.this.handleIntent();
            }
        });
    }

    private void handleIntent() {
        if (this.mIsFinsh) {
            finishActivity();
        } else if (this.mAudioSnsInfo != null) {
            this.mAudioSnsInfo.intentToViewPoint(this, false);
            finishActivity();
        }
    }

    private void initData() {
        bindService();
    }

    private void setUserData() {
        ((ActivityAudioPlayDetailBinding) this.mBaseBinding).setUserInfo(this.mAudioSnsInfo.user);
        ((ActivityAudioPlayDetailBinding) this.mBaseBinding).userImage.setHeadUrl(FengUtil.getHeadImageUrl(this.mAudioSnsInfo.user.getHeadImageInfo()));
    }

    private void bindService() {
        bindService(new Intent(this, AudioPlayService.class), this.connection, 1);
    }

    private void unbindService() {
        unbindService(this.connection);
        if (this.mIsStopService) {
            stopService(new Intent(this, AudioPlayService.class));
        }
    }

    public void onBackPressed() {
        finishActivity();
    }

    private void finishActivity() {
        finish();
        overridePendingTransition(0, R.anim.bottom_out_anim);
    }

    protected void onDestroy() {
        super.onDestroy();
        unbindService();
        if (this.mShareDialog != null && this.mShareDialog.isShowing()) {
            this.mShareDialog.dismiss();
            this.mShareDialog = null;
        }
    }

    private String formatTime(long milliSecs) {
        StringBuffer sb = new StringBuffer();
        long m = milliSecs / 60000;
        sb.append(m < 10 ? "0" + m : Long.valueOf(m));
        sb.append(":");
        long s = (milliSecs % 60000) / 1000;
        sb.append(s < 10 ? "0" + s : Long.valueOf(s));
        return sb.toString();
    }

    private void loadUserInfoData() {
        String userName = "";
        int userId = this.mAudioSnsInfo.user.id;
        userName = (String) this.mAudioSnsInfo.user.name.get();
        Map<String, Object> map = new HashMap();
        if (userId != 0) {
            map.put("userid", String.valueOf(userId));
        } else {
            map.put("nickname", userName);
        }
        FengApplication.getInstance().httpRequest("user/ywf/info/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                AudioPlayDetailActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                AudioPlayDetailActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonObject = new JSONObject(content);
                    int code = jsonObject.getInt("code");
                    if (code == 1) {
                        JSONObject userJson = jsonObject.getJSONObject("body").getJSONObject("user");
                        UserInfo userInfo = new UserInfo();
                        userInfo.parser(userJson);
                        AudioPlayDetailActivity.this.mAudioSnsInfo.user = userInfo;
                        AudioPlayDetailActivity.this.setUserData();
                    } else if (code != -4) {
                        FengApplication.getInstance().checkCode("user/ywf/info/", code);
                    }
                } catch (JSONException e) {
                    FengApplication.getInstance().upLoadTryCatchLog("user/ywf/info/", content, e);
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(AudioStateEvent event) {
        if (event.audioState == AudioStateEvent.PLAYING_STATE) {
            int currentPosition = event.progress;
            ((ActivityAudioPlayDetailBinding) this.mBaseBinding).seekAudioDetailBar.setProgress(currentPosition);
            ((ActivityAudioPlayDetailBinding) this.mBaseBinding).tvAudioCurrentTime.setText(formatTime((long) currentPosition));
        } else if (event.audioState == AudioStateEvent.PAUSE_STATE) {
            this.isAudioPlaying = false;
            ((ActivityAudioPlayDetailBinding) this.mBaseBinding).ivAudioPlayOrPause.setImageResource(R.drawable.audio_detail_play_btn_selector);
        } else if (event.audioState == AudioStateEvent.FINISH_STATE) {
            this.isAudioPlaying = false;
            ((ActivityAudioPlayDetailBinding) this.mBaseBinding).ivAudioPlayOrPause.setImageResource(R.drawable.audio_detail_play_btn_selector);
            ((ActivityAudioPlayDetailBinding) this.mBaseBinding).tvAudioCurrentTime.setText(formatTime(0));
            new Thread(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(100);
                        AudioPlayDetailActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                ((ActivityAudioPlayDetailBinding) AudioPlayDetailActivity.this.mBaseBinding).seekAudioDetailBar.setProgress(0);
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    private void showViewPointVoiceShareDialog() {
        if (this.mViewPointVoiceShareBinding == null) {
            this.mViewPointVoiceShareBinding = ArticleShareDialogBinding.inflate(LayoutInflater.from(this));
            this.mViewPointVoiceShareBinding.setUserInfo(this.mAudioSnsInfo.user);
            this.mViewPointVoiceShareBinding.setMSnsInfo(this.mAudioSnsInfo);
            this.mViewPointVoiceShareBinding.friendsShare.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    AudioPlayDetailActivity.this.mAudioSnsInfo.socialShare(AudioPlayDetailActivity.this, SHARE_MEDIA.WEIXIN_CIRCLE, AudioPlayDetailActivity.this.mViewPointShareListener, 4);
                }
            });
            this.mViewPointVoiceShareBinding.weixinShare.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    AudioPlayDetailActivity.this.mAudioSnsInfo.socialShare(AudioPlayDetailActivity.this, SHARE_MEDIA.WEIXIN, AudioPlayDetailActivity.this.mViewPointShareListener, 4);
                }
            });
            this.mViewPointVoiceShareBinding.weiboShare.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    AudioPlayDetailActivity.this.mAudioSnsInfo.socialShare(AudioPlayDetailActivity.this, SHARE_MEDIA.SINA, AudioPlayDetailActivity.this.mViewPointShareListener, 4);
                }
            });
            this.mViewPointVoiceShareBinding.qqShare.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    AudioPlayDetailActivity.this.mAudioSnsInfo.socialShare(AudioPlayDetailActivity.this, SHARE_MEDIA.QQ, AudioPlayDetailActivity.this.mViewPointShareListener, 4);
                }
            });
            this.mViewPointVoiceShareBinding.qzoneShare.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    AudioPlayDetailActivity.this.mAudioSnsInfo.socialShare(AudioPlayDetailActivity.this, SHARE_MEDIA.QZONE, AudioPlayDetailActivity.this.mViewPointShareListener, 4);
                }
            });
            this.mViewPointVoiceShareBinding.copyLink.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    AudioPlayDetailActivity.this.mAudioSnsInfo.copySnsUrl(AudioPlayDetailActivity.this, 1);
                    AudioPlayDetailActivity.this.hideVoiceShareDialog();
                }
            });
            this.mViewPointVoiceShareBinding.edit.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    AudioPlayDetailActivity.this.mAudioSnsInfo.editOperation(AudioPlayDetailActivity.this);
                    AudioPlayDetailActivity.this.hideVoiceShareDialog();
                }
            });
            this.mViewPointVoiceShareBinding.delete.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    AudioPlayDetailActivity.this.mAudioSnsInfo.deleteOperation(AudioPlayDetailActivity.this, null);
                    AudioPlayDetailActivity.this.hideVoiceShareDialog();
                }
            });
            this.mViewPointVoiceShareBinding.follow.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    AudioPlayDetailActivity.this.mAudioSnsInfo.user.followOperation(AudioPlayDetailActivity.this, null);
                    AudioPlayDetailActivity.this.hideVoiceShareDialog();
                }
            });
            this.mViewPointVoiceShareBinding.report.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    AudioPlayDetailActivity.this.mAudioSnsInfo.reportOperation(AudioPlayDetailActivity.this);
                    AudioPlayDetailActivity.this.hideVoiceShareDialog();
                }
            });
            this.mViewPointVoiceShareBinding.backHome.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    AudioPlayDetailActivity.this.startActivity(new Intent(AudioPlayDetailActivity.this, HomeActivity.class));
                    AudioPlayDetailActivity.this.hideVoiceShareDialog();
                }
            });
            this.mViewPointVoiceShareBinding.cancel.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    AudioPlayDetailActivity.this.hideVoiceShareDialog();
                }
            });
            this.mViewPointVoiceShareBinding.collectText.setVisibility(8);
            if (this.mAudioSnsInfo.snstype == 9) {
                if (this.mAudioSnsInfo.user.getIsMy() == 1) {
                    this.mViewPointVoiceShareBinding.follow.setVisibility(8);
                    this.mViewPointVoiceShareBinding.edit.setVisibility(8);
                    this.mViewPointVoiceShareBinding.delete.setVisibility(8);
                    this.mViewPointVoiceShareBinding.report.setVisibility(8);
                    this.mViewPointVoiceShareBinding.placeView.setVisibility(0);
                    this.mViewPointVoiceShareBinding.placeView1.setVisibility(0);
                    this.mViewPointVoiceShareBinding.placeView2.setVisibility(0);
                } else {
                    this.mViewPointVoiceShareBinding.follow.setVisibility(0);
                    this.mViewPointVoiceShareBinding.edit.setVisibility(8);
                    this.mViewPointVoiceShareBinding.delete.setVisibility(8);
                    this.mViewPointVoiceShareBinding.report.setVisibility(8);
                    this.mViewPointVoiceShareBinding.placeView.setVisibility(0);
                    this.mViewPointVoiceShareBinding.placeView1.setVisibility(0);
                    if (this.mAudioSnsInfo.user.isfollow.get() == 0) {
                        this.mViewPointVoiceShareBinding.follow.setText(R.string.follow);
                    } else {
                        this.mViewPointVoiceShareBinding.follow.setText(R.string.cancel_attention);
                    }
                }
            } else if (this.mAudioSnsInfo.user.getIsMy() == 1) {
                this.mViewPointVoiceShareBinding.follow.setVisibility(8);
                this.mViewPointVoiceShareBinding.edit.setVisibility(8);
                this.mViewPointVoiceShareBinding.delete.setVisibility(0);
                this.mViewPointVoiceShareBinding.report.setVisibility(8);
                this.mViewPointVoiceShareBinding.placeView.setVisibility(0);
                this.mViewPointVoiceShareBinding.placeView1.setVisibility(0);
            } else {
                this.mViewPointVoiceShareBinding.follow.setVisibility(0);
                this.mViewPointVoiceShareBinding.edit.setVisibility(8);
                this.mViewPointVoiceShareBinding.delete.setVisibility(8);
                this.mViewPointVoiceShareBinding.report.setVisibility(0);
                this.mViewPointVoiceShareBinding.placeView.setVisibility(0);
                if (this.mAudioSnsInfo.user.isfollow.get() == 0) {
                    this.mViewPointVoiceShareBinding.follow.setText(R.string.follow);
                } else {
                    this.mViewPointVoiceShareBinding.follow.setText(R.string.cancel_attention);
                }
            }
        }
        if (this.mShareDialog == null) {
            this.mShareDialog = new Dialog(this, R.style.ArticleShareDialog);
            this.mShareDialog.setCanceledOnTouchOutside(true);
            this.mShareDialog.setCancelable(true);
            Window window = this.mShareDialog.getWindow();
            window.setGravity(80);
            window.setWindowAnimations(R.style.shareAnimation);
            window.setContentView(this.mViewPointVoiceShareBinding.getRoot());
            window.setLayout(-1, -2);
            this.mFollowDrawable = getResources().getDrawable(R.drawable.article_follow_selector);
            this.mCancelFollowDrawable = getResources().getDrawable(R.drawable.article_follow_cancel_selector);
            this.mFollowDrawable.setBounds(0, 0, this.mFollowDrawable.getMinimumWidth(), this.mFollowDrawable.getMinimumHeight());
            this.mCancelFollowDrawable.setBounds(0, 0, this.mCancelFollowDrawable.getMinimumWidth(), this.mCancelFollowDrawable.getMinimumHeight());
            if (this.mAudioSnsInfo.user.isfollow.get() == 0) {
                this.mViewPointVoiceShareBinding.follow.setCompoundDrawables(null, this.mFollowDrawable, null, null);
                this.mViewPointVoiceShareBinding.follow.setText(R.string.follow);
            } else {
                this.mViewPointVoiceShareBinding.follow.setCompoundDrawables(null, this.mCancelFollowDrawable, null, null);
                this.mViewPointVoiceShareBinding.follow.setText(R.string.cancel_attention);
            }
        }
        this.mShareDialog.show();
    }

    private void hideVoiceShareDialog() {
        if (this.mShareDialog != null) {
            this.mShareDialog.dismiss();
        }
    }

    public void loginSuccess() {
        super.loginSuccess();
        loadUserInfoData();
    }
}
