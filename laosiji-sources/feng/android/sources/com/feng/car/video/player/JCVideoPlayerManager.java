package com.feng.car.video.player;

public class JCVideoPlayerManager {
    public static int CURRENT_VIDEO_STATE_AUTO_LANDSCAPE = 1;
    public static int CURRENT_VIDEO_STATE_HAND_LANDSCAPE = 3;
    public static int CURRENT_VIDEO_STATE_HAND_QUTI = 2;
    public static int CURRENT_VIDEO_STATE_VERTICAL = 0;
    public static JCVideoPlayer FIRST_FLOOR_JCVD;
    public static int lastDirection = CURRENT_VIDEO_STATE_VERTICAL;
    public static int mCurrentDirection = CURRENT_VIDEO_STATE_VERTICAL;
    public static boolean needStartFullScreen = false;

    public static void setFirstFloor(JCVideoPlayer jcVideoPlayer) {
        FIRST_FLOOR_JCVD = jcVideoPlayer;
    }

    public static JCVideoPlayer getFirstFloor() {
        return FIRST_FLOOR_JCVD;
    }

    public static JCVideoPlayer getCurrentJcvd() {
        return getFirstFloor();
    }

    public static void completeAll() {
        if (FIRST_FLOOR_JCVD != null) {
            FIRST_FLOOR_JCVD.onCompletion();
            FIRST_FLOOR_JCVD = null;
        }
    }
}
