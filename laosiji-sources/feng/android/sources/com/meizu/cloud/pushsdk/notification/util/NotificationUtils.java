package com.meizu.cloud.pushsdk.notification.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.util.PushPreferencesUtils;
import com.umeng.message.entity.UMessage;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class NotificationUtils {
    private static final String TAG = "NotificationUtils";
    private static Object lock = new Object();
    private static Field mFlymeNotification;
    private static Field mInternalApp;
    private static Field mReplyIntent;

    static {
        mFlymeNotification = null;
        mInternalApp = null;
        try {
            mFlymeNotification = Notification.class.getDeclaredField("mFlymeNotification");
            mInternalApp = Class.forName("android.app.NotificationExt").getDeclaredField("internalApp");
            mInternalApp.setAccessible(true);
            mReplyIntent = Notification.class.getDeclaredField("replyIntent");
            mReplyIntent.setAccessible(true);
        } catch (NoSuchFieldException e) {
            DebugLogger.e(TAG, "init NotificationUtils error " + e.getMessage());
        } catch (ClassNotFoundException e2) {
            e2.printStackTrace();
        }
    }

    public static void setReplyIntent(Notification notification, PendingIntent pendingIntent) {
        if (mReplyIntent != null) {
            try {
                mReplyIntent.set(notification, pendingIntent);
            } catch (IllegalAccessException e) {
                DebugLogger.e(TAG, "setReplyIntent error " + e.getMessage());
            }
        }
    }

    public static void setInternalApp(Notification notification, boolean internalApp) {
        if (mFlymeNotification != null && mInternalApp != null) {
            try {
                mInternalApp.set(mFlymeNotification.get(notification), Integer.valueOf(internalApp ? 1 : 0));
            } catch (IllegalAccessException e) {
                DebugLogger.e(TAG, "setInternalApp error " + e.getMessage());
            }
        }
    }

    public static void clearNotification(Context context, String packageName, int notifyId) {
        synchronized (lock) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(UMessage.DISPLAY_TYPE_NOTIFICATION);
            if (notificationManager != null) {
                DebugLogger.i(TAG, "clear clearNotification notifyId " + notifyId);
                notificationManager.cancel(notifyId);
                Set<String> notifySet = PushPreferencesUtils.getNotifyIdByPackageName(context, packageName);
                if (notifySet != null) {
                    notifySet.remove(String.valueOf(notifyId));
                }
                PushPreferencesUtils.putNotifyIdByPackageName(context, packageName, notifySet);
            }
        }
    }

    public static void clearAllNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(UMessage.DISPLAY_TYPE_NOTIFICATION);
        if (notificationManager != null) {
            notificationManager.cancelAll();
        }
    }

    public static void clearNotification(Context context, String packageName) {
        synchronized (lock) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(UMessage.DISPLAY_TYPE_NOTIFICATION);
            if (!(notificationManager == null || TextUtils.isEmpty(packageName))) {
                Set<String> notifySet = PushPreferencesUtils.getNotifyIdByPackageName(context, packageName);
                if (notifySet != null) {
                    for (String notifyId : notifySet) {
                        DebugLogger.i(TAG, "clear notifyId " + notifyId + " notification");
                        notificationManager.cancel(Integer.parseInt(notifyId));
                    }
                    notifySet.clear();
                }
                PushPreferencesUtils.putNotifyIdByPackageName(context, packageName, notifySet);
            }
        }
    }

    public static void storeNotifyIdByPackageName(Context context, String packageName, int notifyId) {
        synchronized (lock) {
            Set<String> notifySet = PushPreferencesUtils.getNotifyIdByPackageName(context, packageName);
            if (notifySet == null) {
                notifySet = new HashSet();
            }
            notifySet.add(String.valueOf(notifyId));
            DebugLogger.i(TAG, "store notifyId " + notifyId);
            PushPreferencesUtils.putNotifyIdByPackageName(context, packageName, notifySet);
        }
    }

    public static void removeNotifyIdByPackageName(Context context, String packageName, int notifyId) {
        synchronized (lock) {
            Set<String> notifySet = PushPreferencesUtils.getNotifyIdByPackageName(context, packageName);
            if (notifySet != null) {
                notifySet.remove(String.valueOf(notifyId));
                DebugLogger.i(TAG, "remove notifyId " + notifyId);
                PushPreferencesUtils.putNotifyIdByPackageName(context, packageName, notifySet);
            }
        }
    }

    public static boolean removeNotifyKey(Context context, String packageName, String notifyKey) {
        boolean z;
        synchronized (lock) {
            if (TextUtils.isEmpty(notifyKey)) {
                z = false;
            } else {
                int notifyId = PushPreferencesUtils.getNotifyIdByNotifyKey(context, packageName, notifyKey);
                DebugLogger.e(TAG, "removeNotifyKey " + notifyKey + " notifyId " + notifyId);
                removeNotifyIdByPackageName(context, packageName, notifyId);
                z = PushPreferencesUtils.removeNotifyKey(context, packageName, notifyKey);
            }
        }
        return z;
    }
}
