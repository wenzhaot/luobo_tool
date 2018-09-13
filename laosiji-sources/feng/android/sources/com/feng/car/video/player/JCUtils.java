package com.feng.car.video.player;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences.Editor;
import android.provider.Settings.SettingNotFoundException;
import android.provider.Settings.System;
import android.view.WindowManager.LayoutParams;
import java.util.Formatter;
import java.util.Locale;

public class JCUtils {
    public static boolean mIsLockScreen = false;

    public static String stringForTime(int timeMs) {
        if (timeMs <= 0 || timeMs >= 86400000) {
            return "00:00";
        }
        int totalSeconds = timeMs / 1000;
        int seconds = totalSeconds % 60;
        int minutes = totalSeconds / 60;
        return new Formatter(new StringBuilder(), Locale.getDefault()).format("%02d:%02d", new Object[]{Integer.valueOf(minutes), Integer.valueOf(seconds)}).toString();
    }

    public static String stringFormateTimeFromSecond(int timeMs) {
        if (timeMs <= 0 || timeMs >= 86400000) {
            return "00:00";
        }
        int seconds = timeMs % 60;
        int minutes = timeMs / 60;
        return new Formatter(new StringBuilder(), Locale.getDefault()).format("%02d:%02d", new Object[]{Integer.valueOf(minutes), Integer.valueOf(seconds)}).toString();
    }

    public static Activity scanForActivity(Context context) {
        if (context == null) {
            return null;
        }
        if (context instanceof Activity) {
            return (Activity) context;
        }
        return context instanceof ContextWrapper ? scanForActivity(((ContextWrapper) context).getBaseContext()) : null;
    }

    public static Activity getAppCompActivity(Context context) {
        if (context == null) {
            return null;
        }
        return context instanceof Activity ? (Activity) context : null;
    }

    public static void saveProgress(Context context, String url, int progress) {
        Editor editor = context.getSharedPreferences("JCVD_PROGRESS", 0).edit();
        editor.putInt(url, progress);
        editor.commit();
    }

    public static int getScreenBrightness(Context context) {
        int nowBrightnessValue = 0;
        LayoutParams lp = getAppCompActivity(context).getWindow().getAttributes();
        if (lp.screenBrightness >= 0.0f) {
            return (int) (lp.screenBrightness * 255.0f);
        }
        try {
            return System.getInt(context.getContentResolver(), "screen_brightness");
        } catch (SettingNotFoundException e) {
            e.printStackTrace();
            return nowBrightnessValue;
        }
    }

    public static void setCurWindowBrightness(Context context, int brightness) {
        Activity activity = (Activity) context;
        LayoutParams lp = activity.getWindow().getAttributes();
        if (brightness < 1) {
            brightness = 1;
        }
        if (brightness > 255) {
            brightness = 255;
        }
        lp.screenBrightness = Float.valueOf((float) brightness).floatValue() * 0.003921569f;
        activity.getWindow().setAttributes(lp);
    }

    public static boolean isLockScreen() {
        return mIsLockScreen;
    }

    public static void lockScreen() {
        mIsLockScreen = true;
    }

    public static void unLockScreen() {
        mIsLockScreen = false;
    }
}
