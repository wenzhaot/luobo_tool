package com.meizu.cloud.pushsdk.notification.flyme;

import android.app.Notification;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.RemoteViews;
import com.meizu.cloud.pushsdk.handler.MessageV3;
import com.meizu.cloud.pushsdk.notification.PushNotificationBuilder;
import com.meizu.cloud.pushsdk.notification.util.RProxy;
import com.meizu.cloud.pushsdk.util.MinSdkChecker;

public class ExpandablePicNotification extends StandardNotification {
    private static final String TAG = "ExpandablePicNotification";

    public ExpandablePicNotification(Context context, PushNotificationBuilder pushNotificationBuilder) {
        super(context, pushNotificationBuilder);
    }

    protected void buildBigContentView(Notification notification, MessageV3 messageV3) {
        if (MinSdkChecker.isSupportNotificationBuild()) {
            RemoteViews expandedView = new RemoteViews(this.context.getPackageName(), RProxy.push_expandable_big_image_notification(this.context));
            expandedView.setTextViewText(RProxy.push_big_notification_title(this.context), messageV3.getTitle());
            expandedView.setTextViewText(RProxy.push_big_notification_content(this.context), messageV3.getContent());
            expandedView.setLong(RProxy.push_big_notification_date(this.context), "setTime", System.currentTimeMillis());
            appLargeIconSetting(expandedView, messageV3);
            largeExpandableImageSetting(expandedView, messageV3);
            notification.bigContentView = expandedView;
        }
    }

    private void largeExpandableImageSetting(RemoteViews expandedView, MessageV3 messageV3) {
        if (messageV3.getmNotificationStyle() != null && !isOnMainThread()) {
            if (TextUtils.isEmpty(messageV3.getmNotificationStyle().getExpandableImageUrl())) {
                expandedView.setViewVisibility(RProxy.push_big_bigview_defaultView(this.context), 8);
                return;
            }
            Bitmap bitmap = getBitmapFromURL(messageV3.getmNotificationStyle().getExpandableImageUrl());
            if (bitmap != null) {
                expandedView.setViewVisibility(RProxy.push_big_bigview_defaultView(this.context), 0);
                expandedView.setImageViewBitmap(RProxy.push_big_bigview_defaultView(this.context), bitmap);
                return;
            }
            expandedView.setViewVisibility(RProxy.push_big_bigview_defaultView(this.context), 8);
        }
    }
}
