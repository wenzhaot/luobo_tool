package com.feng.car.listener;

import android.telephony.PhoneStateListener;
import com.feng.car.video.FengVideoPlayer;
import com.feng.car.video.player.JCVideoPlayerManager;

public class MyPhoneStateListener extends PhoneStateListener {
    public void onCallStateChanged(int state, String incomingNumber) {
        super.onCallStateChanged(state, incomingNumber);
        switch (state) {
            case 1:
                if (JCVideoPlayerManager.getCurrentJcvd() != null && JCVideoPlayerManager.getCurrentJcvd().mCurrentState == 2) {
                    ((FengVideoPlayer) JCVideoPlayerManager.getCurrentJcvd()).playVideo();
                    return;
                }
                return;
            default:
                return;
        }
    }
}
