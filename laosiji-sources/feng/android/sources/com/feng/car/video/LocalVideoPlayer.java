package com.feng.car.video;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.drawee.view.SimpleDraweeView;
import com.feng.car.entity.thread.MediaInfo;
import com.feng.car.utils.VideoManager;
import com.feng.car.video.player.JCMediaManager;
import com.feng.car.video.player.JCUtils;
import com.feng.car.video.player.JCVideoPlayer;
import com.github.jdsjlzx.listener.OnSingleClickListener;

public class LocalVideoPlayer extends JCVideoPlayer {
    public RelativeLayout coverView;
    public TextView definition;
    public ImageView exit_line;
    public ImageView play_icon;
    public ImageView replay_line;
    public SimpleDraweeView thumbImageView;

    public LocalVideoPlayer(Context context) {
        super(context);
    }

    public LocalVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void init(Context context) {
        super.init(context);
        this.thumbImageView = (SimpleDraweeView) findViewById(2131624014);
        this.definition = (TextView) findViewById(2131625187);
        this.replay_line = (ImageView) findViewById(2131625197);
        this.exit_line = (ImageView) findViewById(2131625198);
        this.coverView = (RelativeLayout) findViewById(2131625148);
        this.coverView.getBackground().mutate().setAlpha(170);
        this.play_icon = (ImageView) findViewById(2131625188);
        this.replay_line.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                LocalVideoPlayer.this.replay();
            }
        });
        this.play_icon.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                LocalVideoPlayer.this.playVideo();
            }
        });
        hideUnwantedView();
    }

    private void hideUnwantedView() {
        this.topContainer.setVisibility(8);
        this.progressBar.setVisibility(8);
        this.fullscreenButton.setVisibility(4);
        this.definition.setVisibility(8);
        this.currentTimeTextView.setVisibility(8);
        this.exit_line.setVisibility(8);
    }

    public void setLocalMediaInfo(MediaInfo info) {
        this.mMediaInfo = info;
        this.thumbImageView.setImageURI(Uri.parse(this.mMediaInfo.image.url));
        setUp(this.mMediaInfo.getSDUrl(), 1, "");
    }

    public void playVideo() {
        if (this.coverView.isShown()) {
            this.coverView.setVisibility(8);
        }
        if (TextUtils.isEmpty(this.mCurrentUrl)) {
            Toast.makeText(getContext(), getResources().getString(2131231302), 0).show();
        } else if (this.mCurrentState == 0 || this.mCurrentState == 7) {
            if (this.mMediaInfo != null) {
                updatePosition(VideoManager.newInstance().getPosition(this.mMediaInfo));
            } else {
                updatePosition(this.mCurrentPosition);
            }
            prepareMediaPlayer();
            JCMediaManager.instance().prepare(this.mContext, this.mCurrentUrl, this.textureViewContainer, false);
            onEvent(this.mCurrentState != 7 ? 0 : 1);
            if (this.mMediaInfo != null) {
                this.mMediaInfo.play_state = 2;
                this.mMediaInfo.isComplete = false;
            }
            this.play_icon.setImageResource(2130838579);
            this.startButton.setVisibility(8);
        } else if (this.mCurrentState == 2) {
            onEvent(3);
            if (JCMediaManager.instance().getPlayer() != null) {
                JCMediaManager.instance().getPlayer().pause();
            }
            setUiWitStateAndScreen(5);
            updateStartImage();
        } else if (this.mCurrentState == 5) {
            onEvent(4);
            if (JCMediaManager.instance().getPlayer() != null) {
                JCMediaManager.instance().getPlayer().resume();
            }
            setUiWitStateAndScreen(2);
            updateStartImage();
        } else if (this.mCurrentState == 6) {
            onEvent(2);
            prepareMediaPlayer();
            this.play_icon.setImageResource(2130838581);
        }
    }

    private void replay() {
        VideoUtil.IGNORE_AUDIO_LOSS = true;
        if (this.coverView.isShown()) {
            this.coverView.setVisibility(8);
        }
        JCUtils.scanForActivity(this.mContext).getWindow().addFlags(128);
        this.mCurrentPosition = 0;
        JCMediaManager.instance().prepare(this.mContext, this.mCurrentUrl, this.textureViewContainer, false);
        setUiWitStateAndScreen(1);
        this.play_icon.setImageResource(2130838579);
    }

    public void afterOnCompletion() {
        super.afterOnCompletion();
        this.coverView.setVisibility(0);
        this.play_icon.setImageResource(2130838581);
    }

    public void updateStartImage() {
        if (this.mCurrentState == 2) {
            this.play_icon.setImageResource(2130838579);
        } else if (this.mCurrentState == 7) {
            this.play_icon.setImageResource(2130838581);
        } else {
            this.play_icon.setImageResource(2130838581);
        }
    }

    public int getLayoutId() {
        return 2130903305;
    }
}
