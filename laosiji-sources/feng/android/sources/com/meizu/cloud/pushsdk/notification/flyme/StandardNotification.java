package com.meizu.cloud.pushsdk.notification.flyme;

import android.app.Notification;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.RemoteViews;
import com.meizu.cloud.pushsdk.handler.MessageV3;
import com.meizu.cloud.pushsdk.notification.AbstractPushNotification;
import com.meizu.cloud.pushsdk.notification.PushNotificationBuilder;
import com.meizu.cloud.pushsdk.notification.util.RProxy;
import com.meizu.cloud.pushsdk.util.MinSdkChecker;

public class StandardNotification extends AbstractPushNotification {
    public StandardNotification(Context context, PushNotificationBuilder pushNotificationBuilder) {
        super(context, pushNotificationBuilder);
    }

    protected void buildContentView(Notification notification, MessageV3 messageV3) {
        if (MinSdkChecker.isSupportNotificationBuild()) {
            RemoteViews standardView = new RemoteViews(this.context.getPackageName(), RProxy.push_expandable_big_image_notification(this.context));
            standardView.setTextViewText(RProxy.push_big_notification_title(this.context), messageV3.getTitle());
            standardView.setTextViewText(RProxy.push_big_notification_content(this.context), messageV3.getContent());
            standardView.setLong(RProxy.push_big_notification_date(this.context), "setTime", System.currentTimeMillis());
            appLargeIconSetting(standardView, messageV3);
            standardView.setViewVisibility(RProxy.push_big_bigview_defaultView(this.context), 8);
            standardView.setViewVisibility(RProxy.push_big_bigtext_defaultView(this.context), 8);
            notification.contentView = standardView;
        }
    }

    protected void appLargeIconSetting(RemoteViews standardView, MessageV3 messageV3) {
        if (messageV3.getmAppIconSetting() == null || isOnMainThread()) {
            standardView.setImageViewBitmap(RProxy.push_big_notification_icon(this.context), getAppIcon(this.context, messageV3.getUploadDataPackageName()));
        } else if (messageV3.getmAppIconSetting().isDefaultLargeIcon()) {
            standardView.setImageViewBitmap(RProxy.push_big_notification_icon(this.context), getAppIcon(this.context, messageV3.getUploadDataPackageName()));
        } else {
            Bitmap bitmap = getBitmapFromURL(messageV3.getmAppIconSetting().getLargeIconUrl());
            if (bitmap != null) {
                standardView.setImageViewBitmap(RProxy.push_big_notification_icon(this.context), bitmap);
            } else {
                standardView.setImageViewBitmap(RProxy.push_big_notification_icon(this.context), getAppIcon(this.context, messageV3.getUploadDataPackageName()));
            }
        }
    }
}
