package com.feng.car.video.player;

public interface JCMediaPlayerListener {
    void autoFullscreen(float f);

    void autoQuitFullscreen();

    boolean backToOtherListener();

    int getScreenType();

    int getState();

    String getUrl();

    void goBackThisListener();

    void onAutoCompletion();

    void onBufferingUpdate(int i);

    void onCompletion();

    void onError(int i, int i2);

    void onInfo(int i, int i2);

    void onPrepared();

    void onReleaseAllVideos();

    void onScrollChange();

    void onSeekComplete();

    void onVideoSizeChanged();
}
