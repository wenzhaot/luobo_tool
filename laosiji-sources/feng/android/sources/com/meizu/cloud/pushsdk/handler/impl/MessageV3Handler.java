package com.meizu.cloud.pushsdk.handler.impl;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.SystemClock;
import android.text.TextUtils;
import com.feng.car.utils.FengConstant;
import com.feng.library.utils.DateUtil;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.NotificationService;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.handler.AbstractAppLogicListener;
import com.meizu.cloud.pushsdk.handler.MessageV3;
import com.meizu.cloud.pushsdk.notification.PictureNotification;
import com.meizu.cloud.pushsdk.notification.PushNotification;
import com.meizu.cloud.pushsdk.notification.PushNotificationBuilder;
import com.meizu.cloud.pushsdk.notification.StandardNotificationV2;
import com.meizu.cloud.pushsdk.notification.android.AndroidExpandablePicNotification;
import com.meizu.cloud.pushsdk.notification.android.AndroidExpandableTextNotification;
import com.meizu.cloud.pushsdk.notification.android.AndroidStandardNotification;
import com.meizu.cloud.pushsdk.notification.android.AndroidVideoNotification;
import com.meizu.cloud.pushsdk.notification.flyme.ExpandablePicNotification;
import com.meizu.cloud.pushsdk.notification.flyme.ExpandableTextNotification;
import com.meizu.cloud.pushsdk.notification.flyme.StandardNotification;
import com.meizu.cloud.pushsdk.notification.model.styleenum.BaseStyleModel;
import com.meizu.cloud.pushsdk.notification.model.styleenum.InnerStyleLayout;
import com.meizu.cloud.pushsdk.util.MinSdkChecker;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;
import com.meizu.cloud.pushsdk.util.UxIPUtils;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageV3Handler extends AbstractMessageHandler<MessageV3> {
    public MessageV3Handler(Context context, AbstractAppLogicListener abstractAppLogicListener) {
        super(context, abstractAppLogicListener);
    }

    protected MessageV3 getMessage(Intent intent) {
        String pushMessage;
        if (PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_NOTIFICATION_SHOW_V3.equals(getIntentMethod(intent))) {
            pushMessage = intent.getStringExtra(PushConstants.MZ_PUSH_PRIVATE_MESSAGE);
        } else {
            pushMessage = intent.getStringExtra("message");
        }
        return MessageV3.parse(context().getPackageName(), getPushServiceDefaultPackageName(intent), getPushTimestamp(intent), getDeviceId(intent), getTaskId(intent), getSeqId(intent), pushMessage);
    }

    protected void unsafeSend(MessageV3 message, PushNotification pushNotification) {
        if (pushNotification != null) {
            pushNotification.show(message);
            sendArrivedMessage(message);
        }
    }

    protected PushNotification onCreateNotification(MessageV3 messageV3) {
        PushNotificationBuilder pushNotificationBuilder = new PushNotificationBuilder();
        appLogicListener().onUpdateNotificationBuilder(pushNotificationBuilder);
        PushNotification pushNotification = null;
        if (messageV3.getmNotificationStyle() != null) {
            int baseStyle = messageV3.getmNotificationStyle().getBaseStyle();
            int innerStyle;
            if (BaseStyleModel.FLYME.getCode() == baseStyle) {
                innerStyle = messageV3.getmNotificationStyle().getInnerStyle();
                if (InnerStyleLayout.EXPANDABLE_STANDARD.getCode() == innerStyle) {
                    DebugLogger.i("AbstractMessageHandler", "show Standard Notification with Expandable disable");
                    pushNotification = new StandardNotification(context(), pushNotificationBuilder);
                } else if (InnerStyleLayout.EXPANDABLE_TEXT.getCode() == innerStyle) {
                    DebugLogger.i("AbstractMessageHandler", "show Standard Notification with Expandable Text");
                    pushNotification = new ExpandableTextNotification(context(), pushNotificationBuilder);
                } else if (InnerStyleLayout.EXPANDABLE_PIC.getCode() == innerStyle) {
                    DebugLogger.i("AbstractMessageHandler", "show Standard Notification with Expandable Picture");
                    pushNotification = new ExpandablePicNotification(context(), pushNotificationBuilder);
                } else if (InnerStyleLayout.EXPANDABLE_VIDEO.getCode() == innerStyle) {
                    DebugLogger.i("AbstractMessageHandler", "show Flyme Video notification");
                    pushNotification = new AndroidVideoNotification(context(), pushNotificationBuilder);
                }
            } else if (BaseStyleModel.PURE_PICTURE.getCode() == baseStyle) {
                pushNotification = new PictureNotification(context(), pushNotificationBuilder);
                DebugLogger.i("AbstractMessageHandler", "show Pure Picture Notification");
            } else if (BaseStyleModel.ANDROID.getCode() == baseStyle) {
                innerStyle = messageV3.getmNotificationStyle().getInnerStyle();
                if (InnerStyleLayout.EXPANDABLE_STANDARD.getCode() == innerStyle) {
                    DebugLogger.i("AbstractMessageHandler", "show Android  Notification with Expandable disable");
                    pushNotification = new AndroidStandardNotification(context(), pushNotificationBuilder);
                } else if (InnerStyleLayout.EXPANDABLE_TEXT.getCode() == innerStyle) {
                    DebugLogger.i("AbstractMessageHandler", "show Android  Notification with Expandable Text");
                    pushNotification = new AndroidExpandableTextNotification(context(), pushNotificationBuilder);
                } else if (InnerStyleLayout.EXPANDABLE_PIC.getCode() == innerStyle) {
                    DebugLogger.i("AbstractMessageHandler", "show Android  Notification with Expandable Picture");
                    pushNotification = new AndroidExpandablePicNotification(context(), pushNotificationBuilder);
                } else if (InnerStyleLayout.EXPANDABLE_VIDEO.getCode() == innerStyle) {
                    DebugLogger.i("AbstractMessageHandler", "show Flyme Video notification");
                    pushNotification = new AndroidVideoNotification(context(), pushNotificationBuilder);
                }
            }
        }
        if (pushNotification != null) {
            return pushNotification;
        }
        DebugLogger.e("AbstractMessageHandler", "use standard v2 notification");
        return new StandardNotificationV2(context(), pushNotificationBuilder);
    }

    protected boolean canSendMessage(MessageV3 message) {
        String pk = message.getUriPackageName();
        if (TextUtils.isEmpty(pk)) {
            return true;
        }
        return MzSystemUtils.isPackageInstalled(context(), pk);
    }

    protected int scheduleNotificationStatus(MessageV3 message) {
        if (message.getmTimeDisplaySetting() == null) {
            return 0;
        }
        if (!message.getmTimeDisplaySetting().isTimeDisplay()) {
            return 0;
        }
        if (System.currentTimeMillis() > Long.valueOf(message.getmTimeDisplaySetting().getEndShowTime()).longValue()) {
            UxIPUtils.notificationEvent(context(), "schedule notification expire", (int) PushConstants.EXPIRE_NOTIFICATION, message.getTaskId(), message.getDeviceId());
            return 1;
        } else if (System.currentTimeMillis() > Long.valueOf(message.getmTimeDisplaySetting().getStartShowTime()).longValue()) {
            UxIPUtils.notificationEvent(context(), "schedule notification on time", (int) PushConstants.ONTIME_NOTIFICATION, message.getTaskId(), message.getDeviceId());
            return 2;
        } else {
            UxIPUtils.notificationEvent(context(), "schedule notification delay", (int) PushConstants.DELAY_NOTIFICATION, message.getTaskId(), message.getDeviceId());
            return 3;
        }
    }

    protected void scheduleShowNotification(MessageV3 message) {
        Context context = context();
        context();
        AlarmManager alarmManager = (AlarmManager) context.getSystemService("alarm");
        Intent intent = new Intent(context(), NotificationService.class);
        intent.setPackage(message.getPackageName());
        intent.addCategory(message.getPackageName());
        intent.setData(Uri.parse("custom://" + System.currentTimeMillis()));
        intent.putExtra("command_type", "reflect_receiver");
        intent.setAction(PushConstants.MZ_PUSH_ON_MESSAGE_ACTION);
        intent.putExtra(PushConstants.EXTRA_APP_PUSH_SCHEDULE_NOTIFICATION_MESSAGE, message);
        intent.putExtra(PushConstants.MZ_PUSH_MESSAGE_METHOD, PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_SCHEDULE_NOTIFICATION);
        int pendingFlag = FengConstant.MAXVIDEOSIZE;
        if (MinSdkChecker.isSupportSetDrawableSmallIcon()) {
            pendingFlag = 67108864;
        }
        PendingIntent alarmIntent = PendingIntent.getService(context(), 0, intent, pendingFlag);
        String time = message.getmTimeDisplaySetting().getStartShowTime();
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.dateFormatYMDHMS);
        String showTime = null;
        if (!TextUtils.isEmpty(time)) {
            showTime = sdf.format(new Date(Long.valueOf(time).longValue()));
        }
        long afterTime = Long.valueOf(time).longValue() - System.currentTimeMillis();
        DebugLogger.i("AbstractMessageHandler", "after " + (afterTime / 1000) + " seconds Notification AlarmManager execute At " + showTime);
        if (VERSION.SDK_INT >= 19) {
            DebugLogger.i("AbstractMessageHandler", "setAlarmManager setWindow ELAPSED_REALTIME_WAKEUP");
            alarmManager.setExact(2, SystemClock.elapsedRealtime() + afterTime, alarmIntent);
            return;
        }
        alarmManager.set(2, SystemClock.elapsedRealtime() + afterTime, alarmIntent);
    }

    public boolean messageMatch(Intent intent) {
        DebugLogger.i("AbstractMessageHandler", "start MessageV3Handler match");
        if (!canReceiveMessage(0, getPushServiceDefaultPackageName(intent))) {
            return false;
        }
        if (PushConstants.MZ_PUSH_ON_MESSAGE_ACTION.equals(intent.getAction()) && PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_NOTIFICATION_SHOW_V3.equals(getIntentMethod(intent))) {
            return true;
        }
        if (!TextUtils.isEmpty(getIntentMethod(intent))) {
            return false;
        }
        String cloudPushMessage = intent.getStringExtra("message");
        if (TextUtils.isEmpty(cloudPushMessage) || !isNotificationJson(cloudPushMessage)) {
            return false;
        }
        DebugLogger.e("AbstractMessageHandler", "old cloud notification message");
        return true;
    }

    public int getProcessorType() {
        return 4;
    }

    protected void onBeforeEvent(MessageV3 message) {
        UxIPUtils.onReceivePushMessageEvent(context(), message.getUploadDataPackageName(), message.getDeviceId(), message.getTaskId(), message.getSeqId(), message.getPushTimestamp());
    }

    protected void onAfterEvent(MessageV3 message) {
        UxIPUtils.onShowPushMessageEvent(context(), message.getUploadDataPackageName(), message.getDeviceId(), message.getTaskId(), message.getSeqId(), message.getPushTimestamp());
    }
}
