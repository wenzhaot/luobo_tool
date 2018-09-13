package com.meizu.cloud.pushsdk.util;

import android.content.Context;
import android.content.SharedPreferences;
import com.feng.car.video.shortvideo.FileUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.util.Set;

public class PushPreferencesUtils {
    public static final String MZ_PUSH_PREFERENCE = "mz_push_preference";
    private static final String MZ_PUSH_PREFIX_MESSAGE_SEQ = ".message_seq";
    private static final String MZ_PUSH_PREFIX_NOTIFICATION_ID = ".notification_id";
    private static final String MZ_PUSH_PREFIX_NOTIFY_ID = ".notify_id";
    private static final String MZ_PUSH_PREFIX_PUSH_TASK_ID = ".notification_push_task_id";

    private static SharedPreferences getSharePerferenceByName(Context context, String name) {
        return context.getSharedPreferences(name, 0);
    }

    public static void putStringByKey(Context context, String preferenceName, String key, String value) {
        getSharePerferenceByName(context, preferenceName).edit().putString(key, value).apply();
    }

    public static String getStringBykey(Context context, String preferenceName, String key) {
        return getSharePerferenceByName(context, preferenceName).getString(key, "");
    }

    public static Set<String> getStringSetBykey(Context context, String preferenceName, String key) {
        return getSharePerferenceByName(context, preferenceName).getStringSet(key, null);
    }

    public static void putStringSetByKey(Context context, String preferenceName, String key, Set<String> stringSet) {
        getSharePerferenceByName(context, preferenceName).edit().putStringSet(key, stringSet).commit();
    }

    public static void putIntBykey(Context context, String preferenceName, String key, int value) {
        getSharePerferenceByName(context, preferenceName).edit().putInt(key, value).apply();
    }

    public static int getIntBykey(Context context, String preferenceName, String key) {
        return getSharePerferenceByName(context, preferenceName).getInt(key, 0);
    }

    public static void putBooleanByKey(Context context, String preferenceName, String key, boolean value) {
        getSharePerferenceByName(context, preferenceName).edit().putBoolean(key, value).apply();
    }

    public static boolean getBooleanByKey(Context context, String preferenceName, String key) {
        return getSharePerferenceByName(context, preferenceName).getBoolean(key, true);
    }

    public static boolean remove(Context context, String preferenceName, String key) {
        return getSharePerferenceByName(context, preferenceName).edit().remove(key).commit();
    }

    public static String getPushId(Context context, String pkg) {
        return getStringBykey(context, PushConstants.PUSH_ID_PREFERENCE_NAME, pkg + "_" + PushConstants.KEY_PUSH_ID);
    }

    public static void putPushId(Context context, String pushId, String pkg) {
        putStringByKey(context, PushConstants.PUSH_ID_PREFERENCE_NAME, pkg + "_" + PushConstants.KEY_PUSH_ID, pushId);
    }

    public static void putPushIdExpireTime(Context context, int expireTime, String pkg) {
        putIntBykey(context, PushConstants.PUSH_ID_PREFERENCE_NAME, pkg + "_" + PushConstants.KEY_PUSH_ID_EXPIRE_TIME, expireTime);
    }

    public static int getPushIdExpireTime(Context context, String pkg) {
        return getIntBykey(context, PushConstants.PUSH_ID_PREFERENCE_NAME, pkg + "_" + PushConstants.KEY_PUSH_ID_EXPIRE_TIME);
    }

    public static String getDeviceId(Context context) {
        return getSharePerferenceByName(context, MZ_PUSH_PREFERENCE).getString(PushConstants.MZ_PUSH_MESSAGE_STATISTICS_IMEI_KEY, null);
    }

    public static void putDeviceId(Context context, String deviceId) {
        putStringByKey(context, MZ_PUSH_PREFERENCE, PushConstants.MZ_PUSH_MESSAGE_STATISTICS_IMEI_KEY, deviceId);
    }

    public static void putDiscardNotificationIdByPackageName(Context context, String pkg, int notificationId) {
        putIntBykey(context, MZ_PUSH_PREFERENCE, pkg + MZ_PUSH_PREFIX_NOTIFICATION_ID, notificationId);
    }

    public static int getDiscardNotificationId(Context context, String pkg) {
        return getSharePerferenceByName(context, MZ_PUSH_PREFERENCE).getInt(pkg + MZ_PUSH_PREFIX_NOTIFICATION_ID, 0);
    }

    public static void putDiscardNotificationTaskId(Context context, String pkg, int taskId) {
        putIntBykey(context, MZ_PUSH_PREFERENCE, pkg + MZ_PUSH_PREFIX_PUSH_TASK_ID, taskId);
    }

    public static int getDiscardNotificationTaskId(Context context, String pkg) {
        return getSharePerferenceByName(context, MZ_PUSH_PREFERENCE).getInt(pkg + MZ_PUSH_PREFIX_PUSH_TASK_ID, 0);
    }

    public static void setNotificationMessageSwitchStatus(Context context, String desPackageName, boolean switcher) {
        putBooleanByKey(context, MZ_PUSH_PREFERENCE, "switch_notification_message_" + desPackageName, switcher);
    }

    public static boolean getNotificationMessageSwitchStatus(Context context, String desPackageName) {
        return getBooleanByKey(context, MZ_PUSH_PREFERENCE, "switch_notification_message_" + desPackageName);
    }

    public static void setAlias(Context context, String desPackageName, String alias) {
        putStringByKey(context, MZ_PUSH_PREFERENCE, "push_alias_" + desPackageName, alias);
    }

    public static String getAlias(Context context, String desPackageName) {
        return getStringBykey(context, MZ_PUSH_PREFERENCE, "push_alias_" + desPackageName);
    }

    public static void setThroughMessageSwitchStatus(Context context, String desPackageName, boolean switcher) {
        putBooleanByKey(context, MZ_PUSH_PREFERENCE, "switch_through_message_" + desPackageName, switcher);
    }

    public static boolean getThroughMessageSwitchStatus(Context context, String desPackageName) {
        return getBooleanByKey(context, MZ_PUSH_PREFERENCE, "switch_through_message_" + desPackageName);
    }

    public static void putMessageSeq(Context context, String packageName, int messageSeq) {
        putIntBykey(context, MZ_PUSH_PREFERENCE, packageName + MZ_PUSH_PREFIX_MESSAGE_SEQ, messageSeq);
    }

    public static int getMessageSeqInCrease(Context context, String packageName) {
        int messageSeq = getIntBykey(context, MZ_PUSH_PREFERENCE, packageName + MZ_PUSH_PREFIX_MESSAGE_SEQ) + 1;
        putMessageSeq(context, packageName, messageSeq);
        DebugLogger.e(MZ_PUSH_PREFERENCE, "current messageSeq " + messageSeq);
        return messageSeq;
    }

    public static void putNotifyIdByPackageName(Context context, String packageName, Set<String> notify) {
        putStringSetByKey(context, MZ_PUSH_PREFERENCE, packageName + MZ_PUSH_PREFIX_NOTIFY_ID, notify);
    }

    public static Set<String> getNotifyIdByPackageName(Context context, String packageName) {
        return getStringSetBykey(context, MZ_PUSH_PREFERENCE, packageName + MZ_PUSH_PREFIX_NOTIFY_ID);
    }

    public static void putNotifyIdByNotifyKey(Context context, String packageName, String notifyKey, int notifyId) {
        putIntBykey(context, MZ_PUSH_PREFERENCE, packageName + FileUtils.FILE_EXTENSION_SEPARATOR + notifyKey, notifyId);
    }

    public static int getNotifyIdByNotifyKey(Context context, String packageName, String notifyKey) {
        return getIntBykey(context, MZ_PUSH_PREFERENCE, packageName + FileUtils.FILE_EXTENSION_SEPARATOR + notifyKey);
    }

    public static boolean removeNotifyKey(Context context, String packageName, String notifyKey) {
        return remove(context, MZ_PUSH_PREFERENCE, packageName + FileUtils.FILE_EXTENSION_SEPARATOR + notifyKey);
    }
}
