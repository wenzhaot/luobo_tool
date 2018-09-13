package com.feng.car.activity;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.hardware.SensorManager;
import android.telephony.TelephonyManager;
import com.feng.car.adapter.ArticleDetailAdapter;
import com.feng.car.adapter.MvvmBaseAdapter;
import com.feng.car.event.NetWorkConnectEvent;
import com.feng.car.listener.MyPhoneStateListener;
import com.feng.car.video.player.JCAutoFullscreenListener;
import com.feng.car.video.player.JCMediaManager;
import com.feng.car.video.player.JCUserActionStandard;
import com.feng.car.video.player.JCVideoPlayer;
import com.feng.car.video.player.JCVideoPlayerManager;
import com.feng.library.utils.StringUtil;
import java.lang.reflect.Field;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public abstract class VideoBaseActivity<VB extends ViewDataBinding> extends BaseActivity<VB> implements JCUserActionStandard {
    public TelephonyManager manager;
    public MyPhoneStateListener myPhoneStateListener = new MyPhoneStateListener();
    public JCAutoFullscreenListener sensorEventListener;
    public SensorManager sensorManager;

    public void initListener() {
        JCVideoPlayer.setJcUserAction(this);
        if (this instanceof VideoFinalPageActivity) {
            this.sensorManager = (SensorManager) getSystemService("sensor");
            this.sensorEventListener = new JCAutoFullscreenListener(this);
            this.sensorManager.registerListener(this.sensorEventListener, this.sensorManager.getDefaultSensor(1), 3);
        }
        this.manager = (TelephonyManager) getSystemService("phone");
        this.manager.listen(this.myPhoneStateListener, 32);
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    protected void onResume() {
        super.onResume();
        initListener();
    }

    protected void onPause() {
        if (!(!(this instanceof VideoFinalPageActivity) || this.sensorManager == null || this.sensorEventListener == null)) {
            this.sensorManager.unregisterListener(this.sensorEventListener);
        }
        this.manager.listen(this.myPhoneStateListener, 0);
        super.onPause();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onEvent(int type, String url, int screen, Object... objects) {
        switch (type) {
            case 7:
                this.mSwipeBackLayout.setCanScroll(false);
                onVideoEnterFullscreen();
                return;
            case 8:
                this.mSwipeBackLayout.setCanScroll(true);
                onVideoQuitFullscreen();
                return;
            case 13:
                this.mSwipeBackLayout.setCanScroll(false);
                return;
            case 14:
                this.mSwipeBackLayout.setCanScroll(true);
                return;
            default:
                return;
        }
    }

    public void onVideoEnterFullscreen() {
    }

    public void onVideoQuitFullscreen() {
    }

    public void videoPause() {
        if (JCVideoPlayerManager.getCurrentJcvd() != null && JCVideoPlayerManager.getCurrentJcvd().mCurrentState == 2) {
            JCVideoPlayerManager.getCurrentJcvd().mCurrentState = 5;
            JCVideoPlayerManager.getCurrentJcvd().setUiWitStateAndScreen(5);
            if (JCMediaManager.instance().getPlayer() != null) {
                JCMediaManager.instance().getPlayer().pause();
            }
        }
    }

    public void videoPlaying() {
        if (JCVideoPlayerManager.getCurrentJcvd() != null && JCVideoPlayerManager.getCurrentJcvd().mCurrentState == 5) {
            JCVideoPlayerManager.getCurrentJcvd().mCurrentState = 2;
            JCVideoPlayerManager.getCurrentJcvd().setUiWitStateAndScreen(2);
            if (JCMediaManager.instance().getPlayer() != null) {
                JCMediaManager.instance().getPlayer().resume();
            }
        }
    }

    public void on4GConnect() {
    }

    public void onWifiConnect() {
    }

    public void refreshAdapter() {
        for (Field field : getClass().getDeclaredFields()) {
            String type = field.getGenericType().toString();
            if (!StringUtil.isEmpty(type)) {
                if (type.equals("class com.feng.car.adapter.CommonPostAdapter")) {
                    field.setAccessible(true);
                    try {
                        ((MvvmBaseAdapter) field.get(this)).refreshData();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (type.equals("class com.feng.car.adapter.ArticleDetailAdapter")) {
                    field.setAccessible(true);
                    try {
                        ((ArticleDetailAdapter) field.get(this)).notifyDataSetChanged();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(NetWorkConnectEvent event) {
        if (event == null) {
            return;
        }
        if (this instanceof HomeActivity) {
            if (event.isWifi) {
                onWifiConnect();
            } else {
                on4GConnect();
            }
        } else if (this instanceof VideoFinalPageActivity) {
            if (event.isWifi) {
                onWifiConnect();
            } else {
                on4GConnect();
            }
        } else if (this instanceof ArticleDetailActivity) {
            refreshAdapter();
        }
    }
}
