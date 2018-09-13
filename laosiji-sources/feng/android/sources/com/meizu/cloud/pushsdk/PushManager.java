package com.meizu.cloud.pushsdk;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.platform.api.PushPlatformManager;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;
import com.meizu.cloud.pushsdk.util.PushPreferencesUtils;

public class PushManager {
    static final String KEY_PUSH_ID = "pushId";
    static final String PUSH_ID_PREFERENCE_NAME = "com.meizu.flyme.push";
    public static final String TAG = "3.5.0-SNAPSHOT";

    @Deprecated
    public static void register(Context context) {
        DebugLogger.initDebugLogger(context);
        String cloudVersionName = MzSystemUtils.getAppVersionName(context, "com.meizu.cloud");
        DebugLogger.i("3.5.0-SNAPSHOT", context.getPackageName() + " start register cloudVersion_name " + cloudVersionName);
        Intent pushServiceIntent = new Intent(PushConstants.MZ_PUSH_ON_START_PUSH_REGISTER);
        if ("com.meizu.cloud".equals(MzSystemUtils.getMzPushServicePackageName(context))) {
            pushServiceIntent.setClassName("com.meizu.cloud", "com.meizu.cloud.pushsdk.pushservice.MzPushService");
            pushServiceIntent.putExtra("sender", context.getPackageName());
        } else if (!TextUtils.isEmpty(cloudVersionName) && MzSystemUtils.compareVersion(cloudVersionName, "4.5.7")) {
            DebugLogger.e("3.5.0-SNAPSHOT", "flyme 4.x start register cloud versionName " + cloudVersionName);
            pushServiceIntent.setPackage("com.meizu.cloud");
            pushServiceIntent.putExtra("sender", context.getPackageName());
        } else if (TextUtils.isEmpty(cloudVersionName) || !cloudVersionName.startsWith("3")) {
            DebugLogger.e("3.5.0-SNAPSHOT", context.getPackageName() + " start register ");
            pushServiceIntent.setClassName(context.getPackageName(), "com.meizu.cloud.pushsdk.pushservice.MzPushService");
            pushServiceIntent.putExtra("sender", context.getPackageName());
        } else {
            DebugLogger.e("3.5.0-SNAPSHOT", "flyme 3.x start register cloud versionName " + cloudVersionName);
            pushServiceIntent.setAction(PushConstants.REQUEST_REGISTRATION_INTENT);
            pushServiceIntent.setPackage("com.meizu.cloud");
            pushServiceIntent.putExtra(PushConstants.EXTRA_APPLICATION_PENDING_INTENT, PendingIntent.getBroadcast(context, 0, new Intent(), 0));
            pushServiceIntent.putExtra("sender", context.getPackageName());
        }
        context.startService(pushServiceIntent);
    }

    @Deprecated
    public static void unRegister(Context context) {
        String cloudVersionName = MzSystemUtils.getAppVersionName(context, "com.meizu.cloud");
        DebugLogger.e("3.5.0-SNAPSHOT", context.getPackageName() + " start unRegister cloud versionName " + cloudVersionName);
        Intent pushServiceIntent = new Intent(PushConstants.MZ_PUSH_ON_STOP_PUSH_REGISTER);
        if ("com.meizu.cloud".equals(MzSystemUtils.getMzPushServicePackageName(context))) {
            pushServiceIntent.setClassName("com.meizu.cloud", "com.meizu.cloud.pushsdk.pushservice.MzPushService");
            pushServiceIntent.putExtra("sender", context.getPackageName());
        } else if (!TextUtils.isEmpty(cloudVersionName) && MzSystemUtils.compareVersion(cloudVersionName, "4.5.7")) {
            pushServiceIntent.setPackage("com.meizu.cloud");
            pushServiceIntent.putExtra("sender", context.getPackageName());
        } else if (TextUtils.isEmpty(cloudVersionName) || !cloudVersionName.startsWith("3")) {
            DebugLogger.e("3.5.0-SNAPSHOT", context.getPackageName() + " start unRegister ");
            pushServiceIntent.setClassName(context.getPackageName(), "com.meizu.cloud.pushsdk.pushservice.MzPushService");
            pushServiceIntent.putExtra("sender", context.getPackageName());
        } else {
            pushServiceIntent.setAction(PushConstants.REQUEST_UNREGISTRATION_INTENT);
            pushServiceIntent.setPackage("com.meizu.cloud");
            pushServiceIntent.putExtra(PushConstants.EXTRA_APPLICATION_PENDING_INTENT, PendingIntent.getBroadcast(context, 0, new Intent(), 0));
            pushServiceIntent.putExtra("sender", context.getPackageName());
        }
        context.startService(pushServiceIntent);
    }

    public static String getPushId(Context context) {
        int expireTime = PushPreferencesUtils.getPushIdExpireTime(context, context.getPackageName());
        if (expireTime == 0 || System.currentTimeMillis() / 1000 <= ((long) expireTime)) {
            return PushPreferencesUtils.getPushId(context, context.getPackageName());
        }
        return null;
    }

    public static void checkPush(Context context, String appId, String appKey, String pushId) {
        PushPlatformManager.getInstance(context).checkPush(appId, appKey, context.getPackageName(), pushId);
    }

    public static void switchPush(Context context, String appId, String appKey, String pushId, int pushType, boolean switcher) {
        PushPlatformManager.getInstance(context).switchPush(appId, appKey, context.getPackageName(), pushId, pushType, switcher);
    }

    public static void switchPush(Context context, String appId, String appKey, String pushId, boolean switcher) {
        PushPlatformManager.getInstance(context).switchPush(appId, appKey, context.getPackageName(), pushId, switcher);
    }

    public static void register(Context context, String appId, String appKey) {
        DebugLogger.initDebugLogger(context);
        PushPlatformManager.getInstance(context).register(appId, appKey, context.getPackageName());
    }

    public static void unRegister(Context context, String appId, String appKey) {
        PushPlatformManager.getInstance(context).unRegister(appId, appKey, context.getPackageName());
    }

    public static void subScribeTags(Context context, String appId, String appKey, String pushId, String tags) {
        PushPlatformManager.getInstance(context).subScribeTags(appId, appKey, context.getPackageName(), pushId, tags);
    }

    public static void unSubScribeTags(Context context, String appId, String appKey, String pushId, String tags) {
        PushPlatformManager.getInstance(context).unSubScribeTags(appId, appKey, context.getPackageName(), pushId, tags);
    }

    public static void unSubScribeAllTags(Context context, String appId, String appKey, String pushId) {
        PushPlatformManager.getInstance(context).unSubScribeAllTags(appId, appKey, context.getPackageName(), pushId);
    }

    public static void checkSubScribeTags(Context context, String appId, String appKey, String pushId) {
        PushPlatformManager.getInstance(context).checkSubScribeTags(appId, appKey, context.getPackageName(), pushId);
    }

    public static void subScribeAlias(Context context, String appId, String appKey, String pushId, String alias) {
        PushPlatformManager.getInstance(context).subScribeAlias(appId, appKey, context.getPackageName(), pushId, alias);
    }

    public static void unSubScribeAlias(Context context, String appId, String appKey, String pushId, String alias) {
        PushPlatformManager.getInstance(context).unSubScribeAlias(appId, appKey, context.getPackageName(), pushId, alias);
    }

    public static void checkSubScribeAlias(Context context, String appId, String appKey, String pushId) {
        PushPlatformManager.getInstance(context).checkSubScribeAlias(appId, appKey, context.getPackageName(), pushId);
    }

    public static void checkNotificationMessage(Context context) {
        String cloudVersionName = MzSystemUtils.getAppVersionName(context, "com.meizu.cloud");
        DebugLogger.i("3.5.0-SNAPSHOT", context.getPackageName() + " checkNotificationMessage cloudVersion_name " + cloudVersionName);
        if (!TextUtils.isEmpty(cloudVersionName) && cloudVersionName.startsWith("6")) {
            Intent pushServiceIntent = new Intent(PushConstants.MZ_PUSH_ON_GET_NOTIFICATION_MESSAGE);
            pushServiceIntent.putExtra(PushConstants.EXTRA_GET_NOTIFICATION_PACKAGE_NAME, context.getPackageName());
            pushServiceIntent.setClassName("com.meizu.cloud", "com.meizu.cloud.pushsdk.pushservice.MzPushService");
            context.startService(pushServiceIntent);
        }
    }

    public static void enableCacheRequest(Context context, boolean flag) {
        PushPlatformManager.getInstance(context).enableRemoteInvoker(flag);
    }

    public static void clearNotification(Context context) {
        PushPlatformManager.getInstance(context).clearAllNotification(context.getPackageName());
    }

    public static void clearNotification(Context context, int notifyId) {
        PushPlatformManager.getInstance(context).clearNotification(notifyId, context.getPackageName());
    }
}
