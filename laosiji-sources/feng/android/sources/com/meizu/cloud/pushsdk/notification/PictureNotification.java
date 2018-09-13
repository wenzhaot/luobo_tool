package com.meizu.cloud.pushsdk.notification;

import android.app.Notification;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.RemoteViews;
import com.meizu.cloud.pushsdk.handler.MessageV3;
import com.meizu.cloud.pushsdk.notification.flyme.StandardNotification;
import com.meizu.cloud.pushsdk.notification.model.styleenum.InnerStyleLayout;
import com.meizu.cloud.pushsdk.notification.util.RProxy;
import com.meizu.cloud.pushsdk.util.MinSdkChecker;

public class PictureNotification extends StandardNotification {
    private static final String TAG = "PictureNotification";

    public PictureNotification(Context context, PushNotificationBuilder pushNotificationBuilder) {
        super(context, pushNotificationBuilder);
    }

    protected void buildBigContentView(Notification notification, MessageV3 messageV3) {
        if (MinSdkChecker.isSupportNotificationBuild()) {
            Bitmap bannerBitmap = getBitmapFromURL(messageV3.getmNotificationStyle().getBannerImageUrl());
            if (!isOnMainThread() && bannerBitmap != null) {
                RemoteViews pureBannerPicView = new RemoteViews(this.context.getPackageName(), RProxy.push_pure_pic_notification(this.context));
                pureBannerPicView.setImageViewBitmap(RProxy.push_pure_bigview_banner(this.context), bannerBitmap);
                pureBannerPicView.setViewVisibility(RProxy.push_pure_bigview_expanded(this.context), 8);
                pureBannerPicView.setViewVisibility(RProxy.push_pure_bigview_banner(this.context), 0);
                notification.contentView = pureBannerPicView;
                if (messageV3.getmNotificationStyle().getInnerStyle() == InnerStyleLayout.EXPANDABLE_PIC.getCode()) {
                    Bitmap bigBitmap = getBitmapFromURL(messageV3.getmNotificationStyle().getExpandableImageUrl());
                    if (!isOnMainThread() && bigBitmap != null) {
                        RemoteViews pureBigPicView = new RemoteViews(this.context.getPackageName(), RProxy.push_pure_pic_notification(this.context));
                        pureBigPicView.setImageViewBitmap(RProxy.push_pure_bigview_expanded(this.context), bigBitmap);
                        pureBigPicView.setViewVisibility(RProxy.push_pure_bigview_expanded(this.context), 0);
                        pureBigPicView.setViewVisibility(RProxy.push_pure_bigview_banner(this.context), 8);
                        notification.bigContentView = pureBigPicView;
                    }
                }
            }
        }
    }
}
