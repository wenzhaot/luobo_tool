package com.meizu.cloud.pushsdk.notification;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.PowerManager;
import android.text.TextUtils;
import com.feng.car.utils.FengConstant;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.handler.MessageV3;
import com.meizu.cloud.pushsdk.networking.AndroidNetworking;
import com.meizu.cloud.pushsdk.networking.common.ANResponse;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import com.meizu.cloud.pushsdk.notification.model.NotifyOption;
import com.meizu.cloud.pushsdk.notification.util.NotificationUtils;
import com.meizu.cloud.pushsdk.notification.util.RProxy;
import com.meizu.cloud.pushsdk.util.MinSdkChecker;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;
import com.meizu.cloud.pushsdk.util.PushPreferencesUtils;
import com.umeng.message.entity.UMessage;
import org.json.JSONObject;

public abstract class AbstractPushNotification implements PushNotification {
    protected static final String TAG = "AbstractPushNotification";
    protected Context context;
    protected Handler handler;
    private NotificationManager notificationManager;
    protected PushNotificationBuilder pushNotificationBuilder;

    protected AbstractPushNotification(Context context, PushNotificationBuilder pushNotificationBuilder) {
        this.pushNotificationBuilder = pushNotificationBuilder;
        this.context = context;
        this.handler = new Handler(context.getMainLooper());
        this.notificationManager = (NotificationManager) context.getSystemService(UMessage.DISPLAY_TYPE_NOTIFICATION);
    }

    protected void buildExpandableContent(Builder builder, MessageV3 messageV3) {
    }

    protected void buildContentView(Notification notification, MessageV3 messageV3) {
    }

    protected void buildBigContentView(Notification notification, MessageV3 messageV3) {
    }

    protected void appIconSettingBuilder(Builder builder, MessageV3 messageV3) {
    }

    protected Notification construtNotificationFinal(MessageV3 messageV3, PendingIntent clickIntent, PendingIntent deleteIntent) {
        Notification notification;
        Builder builder = new Builder(this.context);
        basicSettingBuilder(builder, messageV3, clickIntent, deleteIntent);
        advanceSettingBuilder(builder, messageV3);
        appIconSettingBuilder(builder, messageV3);
        buildExpandableContent(builder, messageV3);
        if (MinSdkChecker.isSupportNotificationBuild()) {
            notification = builder.build();
        } else {
            notification = builder.getNotification();
        }
        buildContentView(notification, messageV3);
        buildBigContentView(notification, messageV3);
        return notification;
    }

    protected PendingIntent createClickIntent(MessageV3 messageV3) {
        Intent serviceClickIntent = new Intent();
        serviceClickIntent.setData(Uri.parse("custom://" + System.currentTimeMillis()));
        serviceClickIntent.putExtra(PushConstants.MZ_PUSH_PRIVATE_MESSAGE, messageV3);
        serviceClickIntent.putExtra(PushConstants.MZ_PUSH_MESSAGE_METHOD, PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_PRIVATE);
        serviceClickIntent.setClassName(messageV3.getUploadDataPackageName(), MzSystemUtils.findReceiver(this.context, PushConstants.MZ_PUSH_ON_MESSAGE_ACTION, messageV3.getUploadDataPackageName()));
        serviceClickIntent.setAction(PushConstants.MZ_PUSH_ON_MESSAGE_ACTION);
        return PendingIntent.getBroadcast(this.context, 0, serviceClickIntent, FengConstant.MAXVIDEOSIZE);
    }

    protected PendingIntent createDeleteIntent(MessageV3 messageV3) {
        Intent serviceDeleteIntent = new Intent();
        serviceDeleteIntent.setData(Uri.parse("custom://" + System.currentTimeMillis()));
        serviceDeleteIntent.putExtra(PushConstants.MZ_PUSH_PRIVATE_MESSAGE, messageV3);
        serviceDeleteIntent.putExtra(PushConstants.MZ_PUSH_MESSAGE_METHOD, PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_NOTIFICATION_DELETE);
        serviceDeleteIntent.setClassName(messageV3.getPackageName(), MzSystemUtils.findReceiver(this.context, PushConstants.MZ_PUSH_ON_MESSAGE_ACTION, messageV3.getPackageName()));
        serviceDeleteIntent.setAction(PushConstants.MZ_PUSH_ON_MESSAGE_ACTION);
        return PendingIntent.getBroadcast(this.context, 0, serviceDeleteIntent, FengConstant.MAXVIDEOSIZE);
    }

    protected PendingIntent createStateIntent(MessageV3 messageV3) {
        Intent serviceStateIntent = new Intent();
        serviceStateIntent.setData(Uri.parse("custom://" + System.currentTimeMillis()));
        serviceStateIntent.putExtra(PushConstants.MZ_PUSH_NOTIFICATION_STATE_MESSAGE, messageV3.getNotificationMessage());
        serviceStateIntent.putExtra(PushConstants.NOTIFICATION_EXTRA_TASK_ID, messageV3.getTaskId());
        serviceStateIntent.putExtra(PushConstants.NOTIFICATION_EXTRA_SEQ_ID, messageV3.getSeqId());
        serviceStateIntent.putExtra(PushConstants.NOTIFICATION_EXTRA_DEVICE_ID, messageV3.getDeviceId());
        serviceStateIntent.putExtra(PushConstants.NOTIFICATION_EXTRA_PUSH_TIMESTAMP, messageV3.getPushTimestamp());
        serviceStateIntent.putExtra(PushConstants.NOTIFICATION_EXTRA_SHOW_PACKAGE_NAME, messageV3.getUploadDataPackageName());
        serviceStateIntent.putExtra(PushConstants.MZ_PUSH_MESSAGE_METHOD, PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_NOTIFICATION_STATE);
        serviceStateIntent.setClassName(messageV3.getPackageName(), MzSystemUtils.findReceiver(this.context, PushConstants.MZ_PUSH_ON_MESSAGE_ACTION, messageV3.getPackageName()));
        serviceStateIntent.setAction(PushConstants.MZ_PUSH_ON_MESSAGE_ACTION);
        return PendingIntent.getBroadcast(this.context, 0, serviceStateIntent, FengConstant.MAXVIDEOSIZE);
    }

    protected void basicSettingBuilder(Builder builder, MessageV3 messageV3, PendingIntent clickIntent, PendingIntent deleteIntent) {
        builder.setContentTitle(messageV3.getTitle());
        builder.setContentText(messageV3.getContent());
        builder.setTicker(messageV3.getTitle());
        builder.setAutoCancel(true);
        if (MinSdkChecker.isSupportSendNotification()) {
            builder.setVisibility(1);
        }
        if (MinSdkChecker.isSupportSetDrawableSmallIcon()) {
            Icon smallIcon = loadSmallIcon(messageV3.getUploadDataPackageName());
            if (smallIcon != null) {
                builder.setSmallIcon(smallIcon);
            } else {
                DebugLogger.e(TAG, "cannot get " + messageV3.getUploadDataPackageName() + " smallIcon");
                builder.setSmallIcon(RProxy.stat_sys_third_app_notify(this.context));
            }
        } else {
            int stat_sys_third_app_notify = (this.pushNotificationBuilder == null || this.pushNotificationBuilder.getmStatusbarIcon() == 0) ? RProxy.stat_sys_third_app_notify(this.context) : this.pushNotificationBuilder.getmStatusbarIcon();
            builder.setSmallIcon(stat_sys_third_app_notify);
        }
        builder.setContentIntent(clickIntent);
        builder.setDeleteIntent(deleteIntent);
    }

    protected void advanceSettingBuilder(Builder builder, MessageV3 messageV3) {
        AdvanceSetting advanceSetting = messageV3.getmAdvanceSetting();
        if (advanceSetting != null) {
            if (advanceSetting.getNotifyType() != null) {
                boolean vibrate = advanceSetting.getNotifyType().isVibrate();
                boolean lights = advanceSetting.getNotifyType().isLights();
                boolean sound = advanceSetting.getNotifyType().isSound();
                if (vibrate || lights || sound) {
                    int defaultType = 0;
                    if (vibrate) {
                        defaultType = 0 | 2;
                    }
                    if (lights) {
                        defaultType |= 4;
                    }
                    if (sound) {
                        defaultType |= 1;
                    }
                    DebugLogger.e(TAG, "current notification type is " + defaultType);
                    builder.setDefaults(defaultType);
                }
            }
            builder.setOngoing(!advanceSetting.isClearNotification());
            if (advanceSetting.isHeadUpNotification() && MinSdkChecker.isSupportNotificationBuild()) {
                builder.setPriority(2);
            }
        }
    }

    public Bitmap getBitmapFromURL(String src) {
        ANResponse<Bitmap> response = AndroidNetworking.get(src).build().executeForBitmap();
        if (!response.isSuccess() || response.getResult() == null) {
            DebugLogger.i(TAG, "ANRequest On other Thread down load largeIcon " + src + "image fail");
            return null;
        }
        DebugLogger.i(TAG, "ANRequest On other Thread down load largeIcon " + src + "image " + (response.getResult() != null ? "success" : "fail"));
        return (Bitmap) response.getResult();
    }

    @TargetApi(23)
    private Icon loadSmallIcon(String despackageName) {
        try {
            int titleId = this.context.getPackageManager().getResourcesForApplication(despackageName).getIdentifier(PushConstants.MZ_PUSH_NOTIFICATION_SMALL_ICON, "drawable", despackageName);
            if (titleId == 0) {
                return null;
            }
            DebugLogger.i(TAG, "get " + despackageName + " smallIcon success resId " + titleId);
            return Icon.createWithResource(despackageName, titleId);
        } catch (Exception e) {
            DebugLogger.e(TAG, "cannot load smallIcon form package " + despackageName + " Error message " + e.getMessage());
            return null;
        }
    }

    public Bitmap getAppIcon(Context context, String packageName) {
        try {
            return ((BitmapDrawable) context.getPackageManager().getApplicationIcon(packageName)).getBitmap();
        } catch (NameNotFoundException e) {
            DebugLogger.i(TAG, "getappicon error " + e.getMessage());
            return ((BitmapDrawable) context.getApplicationInfo().loadIcon(context.getPackageManager())).getBitmap();
        }
    }

    protected boolean isOnMainThread() {
        return Thread.currentThread() == this.context.getMainLooper().getThread();
    }

    protected boolean isScreenOnAndUnlock() {
        PowerManager powerManager = (PowerManager) this.context.getSystemService("power");
        if (VERSION.SDK_INT >= 20 ? powerManager.isInteractive() : powerManager.isScreenOn()) {
            if (!((KeyguardManager) this.context.getSystemService("keyguard")).inKeyguardRestrictedInputMode()) {
                return true;
            }
        }
        return false;
    }

    protected String getFlymeGreenNotificationSetting(MessageV3 messageV3) {
        String flymeNotificationSetting = null;
        try {
            if (!TextUtils.isEmpty(messageV3.getNotificationMessage())) {
                flymeNotificationSetting = new JSONObject(messageV3.getNotificationMessage()).getJSONObject("data").getJSONObject(PushConstants.EXTRA).getString("fns");
            }
        } catch (Exception e) {
            DebugLogger.e(TAG, "parse flyme notifification setting error " + e.getMessage());
        }
        DebugLogger.i(TAG, "current FlymeGreen notification setting is " + flymeNotificationSetting);
        return flymeNotificationSetting;
    }

    @SuppressLint({"NewApi"})
    public void show(MessageV3 messageV3) {
        Notification notification = construtNotificationFinal(messageV3, createClickIntent(messageV3), createDeleteIntent(messageV3));
        NotificationUtils.setInternalApp(notification, true);
        NotificationUtils.setReplyIntent(notification, createStateIntent(messageV3));
        notification.extras.putString(PushConstants.EXTRA_ORIGINAL_NOTIFICATION_PACKAGE_NAME, messageV3.getUploadDataPackageName());
        notification.extras.putString(PushConstants.EXTRA_FLYME_GREEN_NOTIFICATION_SETTING, getFlymeGreenNotificationSetting(messageV3));
        notification.extras.putString(PushConstants.NOTIFICATION_EXTRA_TASK_ID, messageV3.getTaskId());
        notification.extras.putString(PushConstants.NOTIFICATION_EXTRA_SEQ_ID, messageV3.getSeqId());
        notification.extras.putString(PushConstants.NOTIFICATION_EXTRA_DEVICE_ID, messageV3.getDeviceId());
        notification.extras.putString(PushConstants.NOTIFICATION_EXTRA_PUSH_TIMESTAMP, messageV3.getPushTimestamp());
        int notifyId = Math.abs((int) System.currentTimeMillis());
        NotifyOption notifyOption = NotifyOption.getNotifyOptionSetting(messageV3);
        if (!(notifyOption == null || notifyOption.getNotifyId() == 0)) {
            notifyId = notifyOption.getNotifyId();
            DebugLogger.e(TAG, "server notify id " + notifyId);
            if (!TextUtils.isEmpty(notifyOption.getNotifyKey())) {
                int preferenceNotifyId = PushPreferencesUtils.getNotifyIdByNotifyKey(this.context, messageV3.getUploadDataPackageName(), notifyOption.getNotifyKey());
                DebugLogger.e(TAG, "notifyKey " + notifyOption.getNotifyKey() + " preference notifyId is " + preferenceNotifyId);
                if (preferenceNotifyId != 0) {
                    DebugLogger.e(TAG, "use preference notifyId " + preferenceNotifyId + " and cancel it");
                    this.notificationManager.cancel(preferenceNotifyId);
                }
                DebugLogger.e(TAG, "store new notifyId " + notifyId + " by notifyKey " + notifyOption.getNotifyKey());
                PushPreferencesUtils.putNotifyIdByNotifyKey(this.context, messageV3.getUploadDataPackageName(), notifyOption.getNotifyKey(), notifyId);
            }
        }
        DebugLogger.e(TAG, "current notify id " + notifyId);
        if (messageV3.isDiscard()) {
            if (PushPreferencesUtils.getDiscardNotificationId(this.context, messageV3.getPackageName()) == 0) {
                PushPreferencesUtils.putDiscardNotificationIdByPackageName(this.context, messageV3.getPackageName(), notifyId);
                DebugLogger.i(TAG, "no notification show so put notification id " + notifyId);
            }
            if (!TextUtils.isEmpty(messageV3.getTaskId())) {
                if (PushPreferencesUtils.getDiscardNotificationTaskId(this.context, messageV3.getPackageName()) == 0) {
                    PushPreferencesUtils.putDiscardNotificationTaskId(this.context, messageV3.getPackageName(), Integer.valueOf(messageV3.getTaskId()).intValue());
                } else if (Integer.valueOf(messageV3.getTaskId()).intValue() < PushPreferencesUtils.getDiscardNotificationTaskId(this.context, messageV3.getPackageName())) {
                    DebugLogger.i(TAG, "current package " + messageV3.getPackageName() + " taskid " + messageV3.getTaskId() + " dont show notification");
                    return;
                } else {
                    PushPreferencesUtils.putDiscardNotificationTaskId(this.context, messageV3.getPackageName(), Integer.valueOf(messageV3.getTaskId()).intValue());
                    notifyId = PushPreferencesUtils.getDiscardNotificationId(this.context, messageV3.getPackageName());
                }
            }
            DebugLogger.i(TAG, "current package " + messageV3.getPackageName() + " notificationId=" + notifyId + " taskId=" + messageV3.getTaskId());
        }
        this.notificationManager.notify(notifyId, notification);
        dismissFloatNotification(this.notificationManager, notifyId, messageV3);
    }

    protected void dismissFloatNotification(final NotificationManager notificationManager, final int notificationId, MessageV3 messageV3) {
        AdvanceSetting advanceSetting = messageV3.getmAdvanceSetting();
        if (advanceSetting != null) {
            boolean isFloatNotification = advanceSetting.isHeadUpNotification();
            boolean isClearNotification = advanceSetting.isClearNotification();
            if (isFloatNotification && !isClearNotification) {
                messageV3.getmAdvanceSetting().setHeadUpNotification(false);
                messageV3.getmAdvanceSetting().getNotifyType().setSound(false);
                messageV3.getmAdvanceSetting().getNotifyType().setVibrate(false);
                final Notification notification = construtNotificationFinal(messageV3, createClickIntent(messageV3), createDeleteIntent(messageV3));
                this.handler.postDelayed(new Runnable() {
                    public void run() {
                        notificationManager.notify(notificationId, notification);
                    }
                }, 5000);
            }
        }
    }
}
