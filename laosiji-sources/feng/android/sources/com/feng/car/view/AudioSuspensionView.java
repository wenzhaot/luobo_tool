package com.feng.car.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.feng.car.FengApplication;
import com.feng.car.event.AudioStateEvent;
import com.feng.car.event.RecyclerviewDirectionEvent;
import com.feng.car.service.AudioPlayService;
import com.feng.car.utils.UmengConstans;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.umeng.analytics.MobclickAgent;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class AudioSuspensionView extends RelativeLayout {
    private AutoFrescoDraweeView mAutoDraweeView;
    private Context mContext;
    private boolean mIsAlready;
    private boolean mIsShow;
    private CircleProgressView mProgressView;
    private Resources mResources;

    public AudioSuspensionView(Context context) {
        this(context, null);
    }

    public AudioSuspensionView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AudioSuspensionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mIsShow = true;
        this.mIsAlready = false;
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        this.mResources = this.mContext.getResources();
        this.mProgressView = new CircleProgressView(this.mContext);
        this.mProgressView.setId(2131623943);
        LayoutParams params_playing = new LayoutParams(this.mResources.getDimensionPixelSize(2131296280), this.mResources.getDimensionPixelSize(2131296280));
        params_playing.addRule(12);
        params_playing.addRule(11);
        addView(this.mProgressView, params_playing);
        this.mAutoDraweeView = new AutoFrescoDraweeView(this.mContext);
        this.mAutoDraweeView.setAutoImageURI(Uri.parse("res://com.feng.car/2130837628"));
        LayoutParams iVLayoutParams = new LayoutParams(this.mResources.getDimensionPixelSize(2131296601), this.mResources.getDimensionPixelSize(2131296601));
        iVLayoutParams.addRule(13, -1);
        addView(this.mAutoDraweeView, iVLayoutParams);
        setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                MobclickAgent.onEvent(AudioSuspensionView.this.mContext, UmengConstans.VOICE_CLICK);
                Intent intentTo = new Intent(AudioSuspensionView.this.mContext, AudioPlayService.class);
                intentTo.putExtra(AudioPlayService.ENTRANCE_TYPE_KEY, 1002);
                AudioSuspensionView.this.mContext.startService(intentTo);
            }
        });
    }

    private void setShow() {
        if (FengApplication.getInstance().getAudioState().audioState == AudioStateEvent.NORMAL_STATE) {
            setVisibility(8);
        } else {
            setVisibility(0);
        }
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
        if (event.audioState == AudioStateEvent.PLAYING_START) {
            setVisibility(0);
            this.mIsShow = true;
        }
        if (this.mIsShow) {
            setShow();
            if (event.audioState == AudioStateEvent.NORMAL_STATE) {
                this.mIsAlready = false;
            } else if (event.audioState == AudioStateEvent.PLAYING_STATE) {
                if (!this.mIsAlready) {
                    this.mAutoDraweeView.setAutoImageURI(Uri.parse("res://com.feng.car/2130837628"));
                    this.mIsAlready = true;
                }
                if (event.totalProgress != 0) {
                    this.mProgressView.setMaxProgress(event.totalProgress);
                }
                this.mProgressView.setProgress(event.progress);
            } else {
                this.mAutoDraweeView.setAutoImageURI(Uri.parse("res://com.feng.car/2130837627"));
                if (event.totalProgress != 0) {
                    this.mProgressView.setMaxProgress(event.totalProgress);
                }
                this.mProgressView.setProgress(event.progress);
                this.mIsAlready = false;
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RecyclerviewDirectionEvent event) {
        if (event.directionUp || FengApplication.getInstance().getAudioState().audioState == AudioStateEvent.NORMAL_STATE) {
            setVisibility(8);
            this.mIsShow = false;
            return;
        }
        setVisibility(0);
        this.mIsShow = true;
    }

    public void onResume() {
        onRegisteredEventBus();
        this.mIsShow = true;
        onEventMainThread(FengApplication.getInstance().getAudioState());
        EventBus.getDefault().post(new RecyclerviewDirectionEvent(false, true));
    }

    public void onPause() {
        unRegisterEventBus();
    }
}
