package com.feng.car.video.player;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.feng.car.video.FengVideoPlayer;
import com.tencent.rtmp.ITXVodPlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXVodPlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

public class JCMediaManager implements ITXVodPlayListener {
    private static JCMediaManager JCMediaManager;
    private final int VIEW_HIDE_FLAG = 1;
    private final int VIEW_SHOW_FLAG = 0;
    private boolean isFullScreen = false;
    private boolean isStopPlay = true;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (JCVideoPlayerManager.getCurrentJcvd() != null) {
                        JCVideoPlayerManager.FIRST_FLOOR_JCVD.showNetWorkWorseLine();
                    }
                    JCMediaManager.this.netWorkWorseLineisShown = true;
                    JCMediaManager.this.mHandler.sendEmptyMessageDelayed(1, 5000);
                    return;
                case 1:
                    if (JCVideoPlayerManager.getCurrentJcvd() != null) {
                        ((FengVideoPlayer) JCVideoPlayerManager.FIRST_FLOOR_JCVD).hideNetWorkTipsLine();
                    }
                    JCMediaManager.this.netWorkWorseLineisShown = false;
                    return;
                default:
                    return;
            }
        }
    };
    private boolean mIsNeedVolume = false;
    private TXVodPlayer mTXVodPlayer;
    private boolean needShowNetWorkWorseLine = true;
    private boolean netWorkWorseLineisShown = false;

    public TXVodPlayer getPlayer() {
        return this.mTXVodPlayer;
    }

    public boolean isFullScreen() {
        return this.isFullScreen;
    }

    public void setFullScreen(boolean fullScreen) {
        this.isFullScreen = fullScreen;
    }

    public static JCMediaManager instance() {
        if (JCMediaManager == null) {
            JCMediaManager = new JCMediaManager();
        }
        return JCMediaManager;
    }

    public void setIsNeedVolume(boolean flag) {
        this.mIsNeedVolume = flag;
    }

    public boolean getIsNeedVolume() {
        return this.mIsNeedVolume;
    }

    public void setNeedShowNetWorkWorseLine(boolean needShowNetWorkWorseLine) {
        this.needShowNetWorkWorseLine = needShowNetWorkWorseLine;
    }

    public void onPlayEvent(TXVodPlayer txVodPlayer, int event, Bundle bundle) {
        switch (event) {
            case TXLiveConstants.PLAY_ERR_NET_DISCONNECT /*-2301*/:
                if (JCVideoPlayerManager.getCurrentJcvd() != null) {
                    JCVideoPlayerManager.getCurrentJcvd().onError(TXLiveConstants.PLAY_ERR_NET_DISCONNECT, TXLiveConstants.PLAY_ERR_NET_DISCONNECT);
                    return;
                }
                return;
            case 2004:
                if (JCVideoPlayerManager.getCurrentJcvd() != null) {
                    JCVideoPlayerManager.getCurrentJcvd().onPrepared();
                    JCVideoPlayerManager.getCurrentJcvd().hideLoadingProgress();
                    return;
                }
                return;
            case 2005:
                if (JCVideoPlayerManager.getCurrentJcvd() != null) {
                    JCVideoPlayerManager.getCurrentJcvd().setTextAndProgress();
                    return;
                }
                return;
            case 2006:
                if (JCVideoPlayerManager.getCurrentJcvd() != null) {
                    JCVideoPlayerManager.getCurrentJcvd().onAutoCompletion();
                    JCVideoPlayerManager.getCurrentJcvd().hideLoadingProgress();
                    return;
                }
                return;
            case TXLiveConstants.PLAY_EVT_PLAY_LOADING /*2007*/:
                if (JCVideoPlayerManager.getCurrentJcvd() != null) {
                    JCVideoPlayerManager.getCurrentJcvd().showLoadingProgress();
                    return;
                }
                return;
            case TXLiveConstants.PLAY_WARNING_RECONNECT /*2103*/:
                if (JCVideoPlayerManager.getCurrentJcvd() != null) {
                    JCVideoPlayerManager.getCurrentJcvd().onError(TXLiveConstants.PLAY_WARNING_RECONNECT, TXLiveConstants.PLAY_WARNING_RECONNECT);
                    return;
                }
                return;
            case TXLiveConstants.PLAY_WARNING_RECV_DATA_LAG /*2104*/:
                if (this.needShowNetWorkWorseLine && !this.netWorkWorseLineisShown) {
                    this.mHandler.sendEmptyMessage(0);
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void onNetStatus(TXVodPlayer txVodPlayer, Bundle bundle) {
    }

    public void prepare(Context context, String url, TXCloudVideoView tXCloudVideoView, boolean needHardwareDecode) {
        if (!TextUtils.isEmpty(url)) {
            if (this.mTXVodPlayer != null) {
                this.mTXVodPlayer.stopPlay(false);
            } else {
                this.mTXVodPlayer = new TXVodPlayer(context);
            }
            this.mTXVodPlayer.setRenderMode(1);
            this.mTXVodPlayer.setVodListener(this);
            this.mTXVodPlayer.setPlayerView(tXCloudVideoView);
            this.mTXVodPlayer.enableHardwareDecode(needHardwareDecode);
            this.mTXVodPlayer.startPlay(url);
            this.isStopPlay = false;
        }
    }

    public void releaseMediaPlayer() {
        JCVideoPlayer player = JCVideoPlayerManager.getCurrentJcvd();
        if (player != null) {
            player.updatePositionToDataBase();
            player.destroyTXCloudVideoView();
        }
        if (this.mTXVodPlayer != null) {
            this.mTXVodPlayer.stopPlay(false);
        }
        this.isStopPlay = true;
    }

    public boolean isStopPlay() {
        return this.isStopPlay;
    }
}
