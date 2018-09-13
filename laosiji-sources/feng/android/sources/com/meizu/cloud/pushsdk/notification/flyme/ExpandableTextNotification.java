package com.meizu.cloud.pushsdk.notification.flyme;

import android.app.Notification;
import android.content.Context;
import android.text.TextUtils;
import android.widget.RemoteViews;
import com.meizu.cloud.pushsdk.handler.MessageV3;
import com.meizu.cloud.pushsdk.notification.PushNotificationBuilder;
import com.meizu.cloud.pushsdk.notification.util.RProxy;
import com.meizu.cloud.pushsdk.util.MinSdkChecker;

public class ExpandableTextNotification extends StandardNotification {
    public ExpandableTextNotification(Context context, PushNotificationBuilder pushNotificationBuilder) {
        super(context, pushNotificationBuilder);
    }

    protected void buildBigContentView(Notification notification, MessageV3 messageV3) {
        if (MinSdkChecker.isSupportNotificationBuild()) {
            RemoteViews expandedView = new RemoteViews(this.context.getPackageName(), RProxy.push_expandable_big_text_notification(this.context));
            expandedView.setTextViewText(RProxy.push_big_notification_title(this.context), messageV3.getTitle());
            expandedView.setLong(RProxy.push_big_notification_date(this.context), "setTime", System.currentTimeMillis());
            appLargeIconSetting(expandedView, messageV3);
            if (!(messageV3.getmNotificationStyle() == null || TextUtils.isEmpty(messageV3.getmNotificationStyle().getExpandableText()))) {
                expandedView.setViewVisibility(RProxy.push_big_bigtext_defaultView(this.context), 0);
                expandedView.setTextViewText(RProxy.push_big_bigtext_defaultView(this.context), messageV3.getmNotificationStyle().getExpandableText());
            }
            notification.bigContentView = expandedView;
        }
    }
}