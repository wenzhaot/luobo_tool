package com.meizu.cloud.pushsdk.notification;

import android.app.Notification.BigTextStyle;
import android.app.Notification.Builder;
import android.content.Context;
import com.meizu.cloud.pushsdk.handler.MessageV3;
import com.meizu.cloud.pushsdk.util.MinSdkChecker;

public class StandardNotificationV2 extends AbstractPushNotification {
    public StandardNotificationV2(Context context, PushNotificationBuilder pushNotificationBuilder) {
        super(context, pushNotificationBuilder);
    }

    protected void buildExpandableContent(Builder builder, MessageV3 messageV3) {
        if (MinSdkChecker.isSupportNotificationBuild()) {
            BigTextStyle notiStyle = new BigTextStyle();
            notiStyle.setBigContentTitle(messageV3.getTitle());
            notiStyle.setSummaryText(messageV3.getContent());
            notiStyle.bigText(messageV3.getContent());
            builder.setStyle(notiStyle);
        }
    }
}
