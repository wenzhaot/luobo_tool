package com.meizu.cloud.pushsdk.notification;

public class PushNotificationBuilder {
    protected int mLargIcon;
    protected int mNotificationDefaults;
    protected int mNotificationFlags;
    protected String mNotificationsound;
    protected int mStatusbarIcon;
    protected long[] mVibratePattern;

    public int getmNotificationDefaults() {
        return this.mNotificationDefaults;
    }

    public void setmNotificationDefaults(int mNotificationDefaults) {
        this.mNotificationDefaults = mNotificationDefaults;
    }

    public int getmNotificationFlags() {
        return this.mNotificationFlags;
    }

    public void setmNotificationFlags(int mNotificationFlags) {
        this.mNotificationFlags = mNotificationFlags;
    }

    public int getmStatusbarIcon() {
        return this.mStatusbarIcon;
    }

    public void setmStatusbarIcon(int mStatusbarIcon) {
        this.mStatusbarIcon = mStatusbarIcon;
    }

    public int getmLargIcon() {
        return this.mLargIcon;
    }

    public void setmLargIcon(int mLargIcon) {
        this.mLargIcon = mLargIcon;
    }

    public String getmNotificationsound() {
        return this.mNotificationsound;
    }

    public void setmNotificationsound(String mNotificationsound) {
        this.mNotificationsound = mNotificationsound;
    }

    public long[] getmVibratePattern() {
        return this.mVibratePattern;
    }

    public void setmVibratePattern(long[] mVibratePattern) {
        this.mVibratePattern = mVibratePattern;
    }
}
