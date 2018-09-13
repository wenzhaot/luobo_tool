package com.umeng.message;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.PowerManager;
import android.text.TextUtils;
import android.widget.RemoteViews;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.message.common.c;
import com.umeng.message.entity.UMessage;
import com.umeng.message.entity.UNotificationItem;
import com.umeng.message.proguard.h;
import com.umeng.socialize.net.dplus.CommonNetImpl;
import com.umeng.socialize.sina.params.ShareRequestParam;
import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import org.json.JSONObject;

public class UmengAdHandler implements UHandler {
    private static int a = 64;
    private static final String b = UmengAdHandler.class.getName();
    private static Date c = null;
    private static String d = "9999999999999";
    private static final String g = "umeng_push_notification_default_large_icon";
    private static final String h = "umeng_push_notification_default_small_icon";
    private static final String i = "umeng_push_notification_default_sound";
    private UMessage e = null;
    private int f;

    public void setPrevMessage(UMessage uMessage) {
        this.e = uMessage;
    }

    public void handleMessage(Context context, UMessage uMessage) {
        UMLog uMLog = UMConfigure.umDebugLog;
        UMLog.mutlInfo(b, 2, "handleMessage");
        dealWithNotificationMessage(context, uMessage);
    }

    public void dealWithNotificationMessage(Context context, UMessage uMessage) {
        boolean z = true;
        UMLog uMLog = UMConfigure.umDebugLog;
        UMLog.mutlInfo(b, 2, "notify: " + uMessage.getRaw().toString());
        if (MessageSharedPrefs.getInstance(context).hasMessageResourceDownloaded(uMessage.msg_id) || !startDownloadResourceService(context, uMessage)) {
            String substring;
            String lastMessageMsgID = MessageSharedPrefs.getInstance(context).getLastMessageMsgID();
            if ("".equals(lastMessageMsgID)) {
                lastMessageMsgID = "";
            } else {
                lastMessageMsgID = lastMessageMsgID.substring(7, 20);
            }
            if (uMessage.msg_id != null && 22 == uMessage.msg_id.length() && uMessage.msg_id.startsWith("u")) {
                MessageSharedPrefs.getInstance(context).setLastMessageMsgID(uMessage.msg_id);
                substring = uMessage.msg_id.substring(7, 20);
            } else {
                substring = d;
            }
            if (!"".equals(lastMessageMsgID)) {
                z = substring.compareToIgnoreCase(lastMessageMsgID) >= 0;
            }
            MessageSharedPrefs.getInstance(context).removeMessageResouceRecord(uMessage.msg_id);
            Notification notification = getNotification(context, uMessage);
            if (notification == null || notification.icon == 0) {
            }
            if (notification != null) {
                this.f = new Random(System.nanoTime()).nextInt();
                PendingIntent clickPendingIntent = getClickPendingIntent(context, uMessage);
                notification.deleteIntent = getDismissPendingIntent(context, uMessage);
                notification.contentIntent = clickPendingIntent;
                int notificationDefaults = getNotificationDefaults(context, uMessage);
                if ((notificationDefaults & 1) != 0) {
                    Uri sound = getSound(context, uMessage);
                    if (sound != null) {
                        notification.sound = getSound(context, uMessage);
                    }
                    if (sound != null) {
                        notificationDefaults ^= 1;
                    }
                }
                notification.defaults = notificationDefaults;
                a(context, notification, z, uMessage);
            }
        }
    }

    private void a(Context context, Notification notification, boolean z, UMessage uMessage) {
        try {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(UMessage.DISPLAY_TYPE_NOTIFICATION);
            int i = this.f;
            if (!(h.c(context) && h.b(context) && !PushAgent.getInstance(context).getNotificationOnForeground()) && (MessageSharedPrefs.getInstance(context).getDisplayNotificationNumber() != 1 || z)) {
                if (MessageSharedPrefs.getInstance(context).getDisplayNotificationNumber() > 0) {
                    while (MessageNotificationQueue.getInstance().size() >= MessageSharedPrefs.getInstance(context).getDisplayNotificationNumber()) {
                        UNotificationItem pollFirst = MessageNotificationQueue.getInstance().pollFirst();
                        notificationManager.cancel(pollFirst.id);
                        UTrack.getInstance(context).setClearPrevMessage(false);
                        UTrack.getInstance(context).trackMsgDismissed(pollFirst.message);
                    }
                    MessageNotificationQueue.getInstance().addLast(new UNotificationItem(i, uMessage));
                }
                notificationManager.notify(i, notification);
                return;
            }
            UTrack.getInstance(context).setClearPrevMessage(false);
            UTrack.getInstance(context).trackMsgDismissed(uMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint({"NewApi", "Wakelock"})
    private void a(Context context) {
        boolean z = false;
        try {
            UMLog uMLog;
            final PowerManager powerManager = (PowerManager) context.getSystemService("power");
            if (VERSION.SDK_INT >= 7) {
                z = new Object() {
                    boolean a() {
                        return powerManager.isScreenOn();
                    }
                }.a();
            } else {
                uMLog = UMConfigure.umDebugLog;
                UMLog.mutlInfo(b, 0, "android os version < 7, skip checking screen on status");
            }
            uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(b, 2, "屏幕是否处于开启状态:" + z);
            if (!z) {
                powerManager.newWakeLock(805306374, "MyLock").acquire(10000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dealWithCustomMessage(Context context, UMessage uMessage) {
    }

    public PendingIntent getClickPendingIntent(Context context, UMessage uMessage) {
        Intent intent = new Intent();
        intent.setClass(context, NotificationProxyBroadcastReceiver.class);
        intent.putExtra(NotificationProxyBroadcastReceiver.EXTRA_KEY_MSG, uMessage.getRaw().toString());
        intent.putExtra(NotificationProxyBroadcastReceiver.EXTRA_KEY_ACTION, 10);
        intent.putExtra(NotificationProxyBroadcastReceiver.EXTRA_KEY_MESSAGE_ID, uMessage.message_id);
        intent.putExtra(NotificationProxyBroadcastReceiver.EXTRA_KEY_NOTIFICATION_ID, this.f);
        intent.putExtra(NotificationProxyBroadcastReceiver.EXTRA_KEY_TASK_ID, uMessage.task_id);
        return PendingIntent.getBroadcast(context, (int) System.currentTimeMillis(), intent, CommonNetImpl.FLAG_AUTH);
    }

    public PendingIntent getDismissPendingIntent(Context context, UMessage uMessage) {
        Intent intent = new Intent();
        intent.setClass(context, NotificationProxyBroadcastReceiver.class);
        intent.putExtra(NotificationProxyBroadcastReceiver.EXTRA_KEY_MSG, uMessage.getRaw().toString());
        intent.putExtra(NotificationProxyBroadcastReceiver.EXTRA_KEY_ACTION, 11);
        intent.putExtra(NotificationProxyBroadcastReceiver.EXTRA_KEY_MESSAGE_ID, uMessage.message_id);
        intent.putExtra(NotificationProxyBroadcastReceiver.EXTRA_KEY_NOTIFICATION_ID, this.f);
        intent.putExtra(NotificationProxyBroadcastReceiver.EXTRA_KEY_TASK_ID, uMessage.task_id);
        return PendingIntent.getBroadcast(context, (int) (System.currentTimeMillis() + 1), intent, CommonNetImpl.FLAG_AUTH);
    }

    public boolean isInNoDisturbTime(Context context) {
        boolean z;
        int i = Calendar.getInstance().get(11);
        int i2 = Calendar.getInstance().get(12);
        boolean z2 = (i * 60) + i2 >= (PushAgent.getInstance(context).getNoDisturbStartHour() * 60) + PushAgent.getInstance(context).getNoDisturbStartMinute();
        boolean z3;
        if ((i * 60) + i2 <= (PushAgent.getInstance(context).getNoDisturbEndHour() * 60) + PushAgent.getInstance(context).getNoDisturbEndMinute()) {
            z3 = true;
        } else {
            z3 = false;
        }
        if ((PushAgent.getInstance(context).getNoDisturbEndHour() * 60) + PushAgent.getInstance(context).getNoDisturbEndMinute() >= (PushAgent.getInstance(context).getNoDisturbStartHour() * 60) + PushAgent.getInstance(context).getNoDisturbStartMinute()) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            if (z2 && z3) {
                return true;
            }
            return false;
        } else if (z2 || z3) {
            return true;
        } else {
            return false;
        }
    }

    public int getNotificationDefaults(Context context, UMessage uMessage) {
        long muteDuration = (1 * ((long) MessageSharedPrefs.getInstance(context).getMuteDuration())) * 1000;
        if (isInNoDisturbTime(context)) {
            return 0;
        }
        if (c != null && Calendar.getInstance().getTimeInMillis() - c.getTime() < muteDuration) {
            return 0;
        }
        int notificationPlayVibrate = MessageSharedPrefs.getInstance(context).getNotificationPlayVibrate();
        UMLog uMLog = UMConfigure.umDebugLog;
        UMLog.mutlInfo(b, 2, "playVibrate:" + notificationPlayVibrate);
        if (notificationPlayVibrate == 1) {
            notificationPlayVibrate = 2;
        } else if (notificationPlayVibrate == 2 || !uMessage.play_vibrate) {
            notificationPlayVibrate = 0;
        } else {
            notificationPlayVibrate = 2;
        }
        int notificationPlayLights = MessageSharedPrefs.getInstance(context).getNotificationPlayLights();
        UMLog uMLog2 = UMConfigure.umDebugLog;
        UMLog.mutlInfo(b, 2, "playLights:" + notificationPlayLights);
        if (notificationPlayLights == 1) {
            notificationPlayVibrate |= 4;
        } else if (notificationPlayLights != 2 && uMessage.play_lights) {
            notificationPlayVibrate |= 4;
        }
        notificationPlayLights = MessageSharedPrefs.getInstance(context).getNotificationPlaySound();
        uMLog2 = UMConfigure.umDebugLog;
        UMLog.mutlInfo(b, 2, "playSound:" + notificationPlayLights);
        if (notificationPlayLights == 1) {
            notificationPlayVibrate |= 1;
        } else if (notificationPlayLights != 2 && uMessage.play_sound) {
            notificationPlayVibrate |= 1;
        }
        c = Calendar.getInstance().getTime();
        if (uMessage.screen_on) {
            a(context);
        }
        return notificationPlayVibrate;
    }

    public boolean startDownloadResourceService(Context context, UMessage uMessage) {
        try {
            Intent intent = new Intent(context, UmengDownloadResourceService.class);
            intent.putExtra("body", uMessage.getRaw().toString());
            intent.putExtra("id", uMessage.message_id);
            intent.putExtra("task_id", uMessage.task_id);
            context.startService(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean a(Context context, Builder builder, UMessage uMessage) {
        int smallIconId = getSmallIconId(context, uMessage);
        Bitmap largeIcon = getLargeIcon(context, uMessage);
        if (smallIconId < 0) {
            return false;
        }
        builder.setSmallIcon(smallIconId);
        builder.setLargeIcon(largeIcon);
        return true;
    }

    public int getSmallIconId(Context context, UMessage uMessage) {
        int i;
        Exception e;
        int i2 = -1;
        try {
            if (!TextUtils.isEmpty(uMessage.icon)) {
                i2 = c.a(context).d(uMessage.icon);
            }
            if (i2 < 0) {
                i2 = c.a(context).d(h);
            }
            if (i2 < 0) {
                UMLog uMLog = UMConfigure.umDebugLog;
                UMLog.mutlInfo(b, 2, "没有自定义图标，改用应用图标");
                i = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).applicationInfo.icon;
            } else {
                i = i2;
            }
            if (i < 0) {
                try {
                    UMLog uMLog2 = UMConfigure.umDebugLog;
                    UMLog.mutlInfo(b, 0, "找不到适当的通知图标，请确保您为此通知指定了一个图标，或者应用已经定义了一个图标");
                } catch (Exception e2) {
                    e = e2;
                    e.printStackTrace();
                    return i;
                }
            }
        } catch (Exception e3) {
            Exception exception = e3;
            i = -1;
            e = exception;
            e.printStackTrace();
            return i;
        }
        return i;
    }

    public Bitmap getLargeIcon(Context context, UMessage uMessage) {
        try {
            Bitmap decodeFile;
            if (UMessage.DISPLAY_TYPE_NOTIFICATIONPULLAPP.equals(uMessage.display_type)) {
                try {
                    decodeFile = BitmapFactory.decodeFile(UmengDownloadResourceService.getMessageResourceFolder(context, uMessage) + new JSONObject(uMessage.custom).optString(ShareRequestParam.REQ_UPLOAD_PIC_PARAM_IMG).hashCode());
                } catch (Exception e) {
                    decodeFile = null;
                }
            } else if (uMessage.isLargeIconFromInternet()) {
                decodeFile = BitmapFactory.decodeFile(UmengDownloadResourceService.getMessageResourceFolder(context, uMessage) + uMessage.img.hashCode());
            } else {
                decodeFile = null;
            }
            if (decodeFile != null) {
                return decodeFile;
            }
            UMLog uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(b, 2, "bmp is null");
            int i = -1;
            if (!TextUtils.isEmpty(uMessage.largeIcon)) {
                i = c.a(context).d(uMessage.largeIcon);
            }
            if (i < 0) {
                i = c.a(context).d(g);
            }
            if (i > 0) {
                return BitmapFactory.decodeResource(context.getResources(), i);
            }
            return decodeFile;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public Uri getSound(Context context, UMessage uMessage) {
        try {
            String str;
            if (uMessage.isSoundFromInternet()) {
                str = UmengDownloadResourceService.getMessageResourceFolder(context, uMessage) + uMessage.sound.hashCode();
                if (!new File(str).exists()) {
                    str = null;
                }
            } else {
                str = null;
            }
            if (str == null) {
                int i = -1;
                if (!TextUtils.isEmpty(uMessage.sound)) {
                    i = c.a(context).j(uMessage.sound);
                }
                if (i < 0) {
                    i = c.a(context).j(i);
                }
                if (i > 0) {
                    str = "android.resource://" + context.getPackageName() + "/" + i;
                }
            }
            if (str != null) {
                return Uri.parse(str);
            }
            return null;
        } catch (Throwable th) {
            return null;
        }
    }

    public Notification getNotification(Context context, UMessage uMessage) {
        String str = uMessage.custom;
        if (str == null || str.equals("")) {
            return null;
        }
        try {
            int optInt = new JSONObject(str).optInt("t");
            Builder builder = new Builder(context);
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), c.a(context).f("upush_notification"));
            switch (optInt) {
                case 0:
                    remoteViews.setImageViewBitmap(c.a(context).c("notification_large_icon1"), getLargeIcon(context, uMessage));
                    remoteViews.setViewVisibility(c.a(context).c("upush_notification1"), 0);
                    remoteViews.setViewVisibility(c.a(context).c("upush_notification2"), 8);
                    remoteViews.setTextViewText(c.a(context).c("notification_title"), uMessage.title);
                    remoteViews.setTextViewText(c.a(context).c("notification_text"), uMessage.text);
                    builder.setContent(remoteViews).setTicker(uMessage.ticker).setSmallIcon(getSmallIconId(context, uMessage)).setAutoCancel(true);
                    return builder.getNotification();
                case 1:
                    remoteViews.setImageViewBitmap(c.a(context).c("notification_large_icon2"), getLargeIcon(context, uMessage));
                    remoteViews.setViewVisibility(c.a(context).c("upush_notification1"), 8);
                    remoteViews.setViewVisibility(c.a(context).c("upush_notification2"), 0);
                    builder.setContent(remoteViews).setTicker(uMessage.ticker).setSmallIcon(getSmallIconId(context, uMessage)).setAutoCancel(true);
                    return builder.getNotification();
                default:
                    return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
