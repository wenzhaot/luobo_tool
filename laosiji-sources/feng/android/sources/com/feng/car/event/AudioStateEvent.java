package com.feng.car.event;

import com.feng.car.FengApplication;

public class AudioStateEvent {
    public static int FINISH_STATE = 3;
    public static int NORMAL_STATE = 0;
    public static int PAUSE_STATE = 2;
    public static int PLAYING_START = 4;
    public static int PLAYING_STATE = 1;
    public String audioHash = "";
    public int audioState = NORMAL_STATE;
    public int progress;
    public int totalProgress;

    public AudioStateEvent(int state) {
        this.audioState = state;
        FengApplication.getInstance().getAudioState().audioState = state;
    }

    public AudioStateEvent(int state, int totalProgress, int progress) {
        this.audioState = state;
        this.totalProgress = totalProgress;
        this.progress = progress;
        if (totalProgress != 0) {
            FengApplication.getInstance().getAudioState().totalProgress = totalProgress;
        }
        FengApplication.getInstance().getAudioState().audioState = state;
        FengApplication.getInstance().getAudioState().progress = progress;
    }
}
