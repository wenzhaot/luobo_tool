package com.feng.car.activity;

import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.view.View;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.feng.car.R;
import com.feng.car.databinding.ActivityWatchvideoBinding;
import com.feng.car.entity.model.ImageVideoInfo;
import com.feng.car.entity.thread.MediaInfo;
import com.feng.car.event.MediaSelectChangeEvent;
import com.feng.car.event.TitleNextChangeEvent;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.LocalMediaDataUtil;
import com.feng.car.video.player.JCVideoPlayer;
import com.feng.car.video.player.JCVideoPlayerManager;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class WatchVideoActivity extends VideoBaseActivity<ActivityWatchvideoBinding> {
    private AudioManager audioManager;
    public OnAudioFocusChangeListener onAudioFocusChangeListener = new OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange) {
            }
        }
    };

    protected void onResume() {
        super.onResume();
        ((ActivityWatchvideoBinding) this.mBaseBinding).videoPlayer.playVideo();
        this.audioManager.requestAudioFocus(this.onAudioFocusChangeListener, 3, 2);
    }

    protected void onPause() {
        super.onPause();
        if (((ActivityWatchvideoBinding) this.mBaseBinding).videoPlayer.mCurrentState == 2) {
            ((ActivityWatchvideoBinding) this.mBaseBinding).videoPlayer.playVideo();
        }
        this.audioManager.abandonAudioFocus(this.onAudioFocusChangeListener);
    }

    public int setBaseContentView() {
        return R.layout.activity_watchvideo;
    }

    public void initView() {
        ((ActivityWatchvideoBinding) this.mBaseBinding).getRoot().setBackgroundResource(R.drawable.bg_white);
        final ImageVideoInfo info = LocalMediaDataUtil.getInstance().getVideoData();
        if (info == null) {
            showSecondTypeToast((int) R.string.get_videoinfo_failed);
            return;
        }
        boolean z;
        ((ActivityWatchvideoBinding) this.mBaseBinding).setInfo(info);
        ((ActivityWatchvideoBinding) this.mBaseBinding).tvSelectIndex.setVisibility(0);
        this.mRootBinding.titleLine.ivTitleLeft.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                WatchVideoActivity.this.finish();
                JCVideoPlayer.releaseAllVideos();
            }
        });
        initTitleBarRightTextWithBg(R.string.next, new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (WatchVideoActivity.this.mRootBinding.titleLine.tvRightText.isSelected()) {
                    EventBus.getDefault().post(new MediaSelectChangeEvent(2, null));
                    WatchVideoActivity.this.finish();
                }
            }
        });
        TextView textView = this.mRootBinding.titleLine.tvRightText;
        if (LocalMediaDataUtil.getInstance().getSelectData().size() > 0) {
            z = true;
        } else {
            z = false;
        }
        textView.setSelected(z);
        ((ActivityWatchvideoBinding) this.mBaseBinding).tvSelectIndex.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                EventBus.getDefault().post(new MediaSelectChangeEvent(info));
            }
        });
        try {
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(this, Uri.parse(info.url));
            String width = mmr.extractMetadata(18);
            String height = mmr.extractMetadata(19);
            if (Integer.valueOf(mmr.extractMetadata(24)).intValue() == 90) {
                info.width = Integer.valueOf(height).intValue();
                info.hight = Integer.valueOf(width).intValue();
            } else {
                info.width = Integer.valueOf(width).intValue();
                info.hight = Integer.valueOf(height).intValue();
            }
            mmr.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JCVideoPlayerManager.setFirstFloor(((ActivityWatchvideoBinding) this.mBaseBinding).videoPlayer);
        LayoutParams p = new LayoutParams(-1, (FengUtil.getScreenWidth(this) * 9) / 16);
        p.addRule(13);
        ((ActivityWatchvideoBinding) this.mBaseBinding).videoPlayer.setLayoutParams(p);
        MediaInfo mediaInfo = new MediaInfo();
        mediaInfo.videourl.sdinfo.url = info.url;
        mediaInfo.image.url = "file://" + info.url;
        mediaInfo.image.width = info.width;
        mediaInfo.image.height = info.hight;
        ((ActivityWatchvideoBinding) this.mBaseBinding).videoPlayer.setLocalMediaInfo(mediaInfo);
        this.audioManager = (AudioManager) getSystemService("audio");
    }

    public void onBackPressed() {
        finish();
        getWindow().clearFlags(128);
        JCVideoPlayer.releaseAllVideos();
        JCVideoPlayerManager.setFirstFloor(null);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(TitleNextChangeEvent event) {
        this.mRootBinding.titleLine.tvRightText.setSelected(event.isSelected);
    }
}
