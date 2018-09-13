package com.feng.car.video.player;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.provider.Settings.SettingNotFoundException;
import android.provider.Settings.System;

public class JCAutoFullscreenListener implements SensorEventListener {
    private Context mContext;

    public JCAutoFullscreenListener(Context context) {
        this.mContext = context;
    }

    private boolean isAcelerometerRotation() {
        try {
            if (System.getInt(this.mContext.getContentResolver(), "accelerometer_rotation") == 1) {
                return true;
            }
            return false;
        } catch (SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void onSensorChanged(SensorEvent event) {
        if (JCMediaManager.instance().getIsNeedVolume()) {
            float x = event.values[0];
            float y = event.values[1];
            if (isAcelerometerRotation() && !JCUtils.isLockScreen()) {
                if (((x <= -15.0f || x >= -8.0f) && (x >= 15.0f || x <= 8.0f)) || ((double) Math.abs(y)) >= 1.5d) {
                    if (Math.abs(y) <= 8.0f) {
                        return;
                    }
                    if (!JCMediaManager.instance().isFullScreen()) {
                        JCVideoPlayerManager.mCurrentDirection = JCVideoPlayerManager.CURRENT_VIDEO_STATE_VERTICAL;
                    } else if (System.currentTimeMillis() - JCVideoPlayer.CLICK_QUIT_FULLSCREEN_TIME > 1000 && JCVideoPlayerManager.mCurrentDirection != JCVideoPlayerManager.CURRENT_VIDEO_STATE_HAND_LANDSCAPE && JCVideoPlayerManager.getCurrentJcvd() != null) {
                        JCVideoPlayerManager.getCurrentJcvd().backPress();
                    }
                } else if (System.currentTimeMillis() - JCVideoPlayer.lastAutoFullscreenTime > 2000 && JCVideoPlayerManager.mCurrentDirection != JCVideoPlayerManager.CURRENT_VIDEO_STATE_HAND_QUTI && JCVideoPlayerManager.getCurrentJcvd() != null) {
                    JCVideoPlayerManager.getCurrentJcvd().autoFullscreen(x);
                    JCVideoPlayerManager.mCurrentDirection = JCVideoPlayerManager.CURRENT_VIDEO_STATE_AUTO_LANDSCAPE;
                }
            }
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
