package com.meizu.cloud.pushsdk.notification.android;

import android.app.Notification.BigTextStyle;
import android.app.Notification.Builder;
import android.content.Context;
import com.meizu.cloud.pushsdk.handler.MessageV3;
import com.meizu.cloud.pushsdk.notification.PushNotificationBuilder;
import com.meizu.cloud.pushsdk.util.MinSdkChecker;

public class AndroidExpandableTextNotification extends AndroidStandardNotification {
    public AndroidExpandableTextNotification(Context context, PushNotificationBuilder pushNotificationBuilder) {
        super(context, pushNotificationBuilder);
    }

    protected void buildExpandableContent(Builder builder, MessageV3 messageV3) {
        if (MinSdkChecker.isSupportNotificationBuild()) {
            BigTextStyle notiStyle = new BigTextStyle();
            notiStyle.setBigContentTitle(messageV3.getTitle());
            notiStyle.setSummaryText(messageV3.getContent());
            notiStyle.bigText(messageV3.getmNotificationStyle().getExpandableText());
            builder.setStyle(notiStyle);
        }
    }
}
