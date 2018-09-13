package com.meizu.cloud.pushsdk.notification.android;

import android.app.Notification;
import android.app.Notification.BigTextStyle;
import android.app.Notification.Builder;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.handler.MessageV3;
import com.meizu.cloud.pushsdk.handler.MessageV4;
import com.meizu.cloud.pushsdk.networking.AndroidNetworking;
import com.meizu.cloud.pushsdk.notification.PushNotificationBuilder;
import com.meizu.cloud.pushsdk.notification.util.FileUtil;
import com.meizu.cloud.pushsdk.notification.util.ZipExtractTask;
import com.meizu.cloud.pushsdk.pushtracer.emitter.classic.Executor;
import com.meizu.cloud.pushsdk.util.Connectivity;
import com.meizu.cloud.pushsdk.util.MinSdkChecker;
import java.io.File;

public class AndroidVideoNotification extends AndroidStandardNotification {
    public AndroidVideoNotification(Context context, PushNotificationBuilder pushNotificationBuilder) {
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

    protected void buildContentView(Notification notification, MessageV3 messageV3) {
        super.buildContentView(notification, messageV3);
        MessageV4 messageV4 = MessageV4.parse(messageV3);
        if (messageV4.getActVideoSetting() == null || (messageV4.getActVideoSetting().isWifiDisplay() && !Connectivity.isConnectedWifi(this.context))) {
            DebugLogger.e("AbstractPushNotification", "only wifi can download act");
            return;
        }
        final String baseActDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/pushSdkAct/" + messageV3.getUploadDataPackageName();
        String actFileName = String.valueOf(System.currentTimeMillis());
        String actUrl = messageV4.getActVideoSetting().getActUrl();
        if (!TextUtils.isEmpty(actUrl) && AndroidNetworking.download(actUrl, baseActDir, actFileName).build().executeForDownload().isSuccess()) {
            DebugLogger.i("AbstractPushNotification", "down load " + actUrl + " success");
            String actDir = baseActDir + File.separator + "ACT-" + actFileName;
            boolean isUzipSuccess = new ZipExtractTask(baseActDir + File.separator + actFileName, actDir).doUnzipSync();
            DebugLogger.i("AbstractPushNotification", "zip file " + isUzipSuccess);
            if (isUzipSuccess) {
                Bundle bigBundle = new Bundle();
                bigBundle.putString("path", actDir);
                Bundle mainBundle = new Bundle();
                mainBundle.putBundle("big", bigBundle);
                if (MinSdkChecker.isSupportVideoNotification()) {
                    notification.extras.putBundle("flyme.active", mainBundle);
                }
            }
        }
        Executor.execute(new Runnable() {
            public void run() {
                for (File file : FileUtil.listFile(baseActDir, String.valueOf(System.currentTimeMillis() - 86400000))) {
                    FileUtil.deleteDirectory(file.getPath());
                    DebugLogger.i("AbstractPushNotification", "Delete file directory " + file.getName() + "\n");
                }
            }
        });
    }
}
