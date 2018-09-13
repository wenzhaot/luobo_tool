package com.meizu.cloud.pushsdk.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.handler.impl.model.PlatformMessage;
import com.meizu.cloud.pushsdk.handler.impl.model.Statics;
import com.meizu.cloud.pushsdk.notification.MPushMessage;
import com.meizu.cloud.pushsdk.pushtracer.QuickTracker;
import com.meizu.cloud.pushsdk.pushtracer.constant.Parameters;
import com.meizu.cloud.pushsdk.pushtracer.event.PushEvent;
import com.meizu.cloud.pushsdk.pushtracer.event.PushEvent.Builder;
import com.meizu.cloud.pushsdk.pushtracer.utils.Util;
import com.umeng.commonsdk.proguard.g;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public class UxIPUtils {
    private static final String TAG = "UxIPUtils";

    public static void init(Context context) {
    }

    public static void notificationEvent(Context context, String pushInfo, int pushInfoType, String taskId, String imei) {
        if (!TextUtils.isEmpty(taskId)) {
            onRecordMessageFlow(context, context.getPackageName(), imei, taskId, "3.5.0-SNAPSHOT", pushInfo, pushInfoType);
        }
    }

    public static void notificationEvent(Context context, Intent intent, String pushInfo, int pushInfoType) {
        notificationEvent(context, intent, "3.5.0-SNAPSHOT", pushInfo, pushInfoType);
    }

    public static void notificationEvent(Context context, Intent intent, String pushsdkVersion, String pushInfo, int pushInfoType) {
        if (!TextUtils.isEmpty(getTaskId(intent))) {
            onRecordMessageFlow(context, context.getPackageName(), intent.getStringExtra(PushConstants.MZ_PUSH_MESSAGE_STATISTICS_IMEI_KEY), getTaskId(intent), pushsdkVersion, pushInfo, pushInfoType);
        }
    }

    public static String getTaskId(Intent intent) {
        String taskId = intent.getStringExtra(PushConstants.EXTRA_APP_PUSH_TASK_ID);
        if (!TextUtils.isEmpty(taskId)) {
            return taskId;
        }
        try {
            MPushMessage mPushMessage = (MPushMessage) intent.getSerializableExtra(PushConstants.MZ_PUSH_PRIVATE_MESSAGE);
            if (mPushMessage != null) {
                return mPushMessage.getTaskId();
            }
            return taskId;
        } catch (Exception e) {
            taskId = "no push platform task";
            DebugLogger.e(TAG, "paese MessageV2 error " + e.getMessage());
            return taskId;
        }
    }

    public static void onRecordMessageFlow(Context context, String packageName, String deviceId, String taskId, String pushsdkVersion, String pushInfo, int pushInfoType) {
        Map<String, String> propertiesMap = new HashMap();
        propertiesMap.put(Statics.TASK_ID, taskId);
        propertiesMap.put("deviceId", deviceId);
        propertiesMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        propertiesMap.put(g.n, packageName);
        propertiesMap.put("pushsdk_version", pushsdkVersion);
        propertiesMap.put("push_info", pushInfo);
        propertiesMap.put("push_info_type", String.valueOf(pushInfoType));
        onLogEvent(context, false, "notification_service_message", propertiesMap);
    }

    public static void onShowPushMessageEvent(Context context, String packageName, String platformExtra) {
        PlatformMessage platformMessage = buildPlatformMessage(platformExtra);
        onShowPushMessageEvent(context, packageName, platformMessage.getDeviceId(), platformMessage.getTaskId(), platformMessage.getSeqId(), platformMessage.getPushTimesTamp());
    }

    public static PlatformMessage buildPlatformMessage(String platformExtra) {
        PlatformMessage platformMessage = new PlatformMessage();
        if (TextUtils.isEmpty(platformExtra)) {
            DebugLogger.e(TAG, "the platformExtra is empty");
            return platformMessage;
        }
        try {
            JSONObject platformExtraJsonObj = new JSONObject(platformExtra);
            String taskId = null;
            String seqId = null;
            String pushTimestamp = null;
            String deviceId = null;
            if (platformExtraJsonObj.has("task_id")) {
                taskId = platformExtraJsonObj.getString("task_id");
            }
            if (platformExtraJsonObj.has(PlatformMessage.PLATFORM_SEQ_ID)) {
                seqId = platformExtraJsonObj.getString(PlatformMessage.PLATFORM_SEQ_ID);
            }
            if (platformExtraJsonObj.has(PlatformMessage.PLATFORM_PUSH_TIMESTAMP)) {
                pushTimestamp = platformExtraJsonObj.getString(PlatformMessage.PLATFORM_PUSH_TIMESTAMP);
            }
            if (platformExtraJsonObj.has("device_id")) {
                deviceId = platformExtraJsonObj.getString("device_id");
            }
            return PlatformMessage.builder().taskId(taskId).deviceId(deviceId).pushTimesTamp(pushTimestamp).seqId(seqId).build();
        } catch (Exception e) {
            DebugLogger.e(TAG, "the platformExtra parse error");
            return platformMessage;
        }
    }

    public static void onShowPushMessageEvent(Context context, String packageName, String deviceId, String taskId, String seqId, String pushTimestamp) {
        onLogEvent(context, true, packageName, deviceId, taskId, seqId, Parameters.SHOW_PUSH_MESSAGE, pushTimestamp);
    }

    public static void onDeletePushMessageEvent(Context context, String packageName, String deviceId, String taskId, String seqId, String pushTimestamp) {
        onLogEvent(context, true, packageName, deviceId, taskId, seqId, Parameters.DELETE_PUSH_MESSAGE, pushTimestamp);
    }

    public static void onReceivePushMessageEvent(Context context, String packageName, String deviceId, String taskId, String seqId, String pushTimestamp) {
        onLogEvent(context, false, packageName, deviceId, taskId, seqId, Parameters.RECEIVE_PUSH_EVNET, pushTimestamp);
    }

    public static void onReceiveThroughMessage(Context context, String packageName, String deviceId, String taskId, String seqId, String pushTimestamp) {
        onLogEvent(context, true, packageName, deviceId, taskId, seqId, Parameters.RECEIVE_PUSH_EVNET, pushTimestamp);
    }

    public static void onClickPushMessageEvent(Context context, String packageName, String deviceId, String taskId, String seqId, String pushTimestamp) {
        onLogEvent(context, true, packageName, deviceId, taskId, seqId, Parameters.CLICK_PUSH_MESSAGE, pushTimestamp);
    }

    public static void onInvalidPushMessage(Context context, String packageName, String deviceId, String taskId, String seqId, String pushTimestamp) {
        onLogEvent(context, true, packageName, deviceId, taskId, seqId, Parameters.INVALID_PUSH_MESSAGE, pushTimestamp);
    }

    public static void onShowInBoxPushMessage(Context context, String packageName, String deviceId, String taskId, String seqId, String pushTimestamp) {
        onLogEvent(context, false, packageName, deviceId, taskId, seqId, Parameters.SHOWINBOX_PUSH_MESSAGE, pushTimestamp);
    }

    public static void onNoShowPushMessage(Context context, String packageName, String deviceId, String taskId, String seqId, String pushTimestamp) {
        onLogEvent(context, false, packageName, deviceId, taskId, seqId, Parameters.NOSHOW_PUSH_MESSAGE, pushTimestamp);
    }

    public static void onReceiveServerMessage(Context context, String packageName, String deviceId, String taskId, String seqId, String pushTimestamp) {
        onLogEvent(context, false, packageName, deviceId, taskId, seqId, Parameters.RECEIVER_SERVER_MESSAGE, pushTimestamp);
    }

    public static void onLogEvent(Context context, boolean emitNow, String packageName, String deviceId, String taskId, String seqId, String eventName, String pushTimestamp) {
        Map<String, String> propertiesMap = new HashMap();
        propertiesMap.put("en", eventName);
        propertiesMap.put(Parameters.TASK_ID, taskId);
        propertiesMap.put("di", deviceId);
        String str = "ts";
        if (TextUtils.isEmpty(pushTimestamp)) {
            pushTimestamp = String.valueOf(System.currentTimeMillis() / 1000);
        }
        propertiesMap.put(str, pushTimestamp);
        propertiesMap.put(Parameters.PACKAGE_NAME, packageName);
        propertiesMap.put("pv", "3.5.0-SNAPSHOT");
        if (!TextUtils.isEmpty(seqId)) {
            propertiesMap.put(Parameters.SEQ_ID, seqId);
        }
        if (!remotePushTracker(context, propertiesMap)) {
            onLogEvent(context, emitNow, eventName, propertiesMap);
        }
    }

    public static void onLogEvent(Context context, boolean emitNow, String eventName, Map<String, String> propertiesMap) {
        DebugLogger.e(TAG, "onLogEvent eventName [" + eventName + "] properties = " + propertiesMap);
        if (!"notification_service_message".equals(eventName)) {
            QuickTracker.getAndroidTrackerClassic(context, null).access$101(((Builder) PushEvent.builder().eventName(eventName).timestamp(Long.valueOf((String) propertiesMap.get("ts")).longValue())).eventCreateTime(String.valueOf(System.currentTimeMillis() / 1000)).deviceId((String) propertiesMap.get("di")).packageName((String) propertiesMap.get(Parameters.PACKAGE_NAME)).pushsdkVersion((String) propertiesMap.get("pv")).taskId((String) propertiesMap.get(Parameters.TASK_ID)).seqId(TextUtils.isEmpty((CharSequence) propertiesMap.get(Parameters.SEQ_ID)) ? "" : (String) propertiesMap.get(Parameters.SEQ_ID)).messageSeq(String.valueOf(PushPreferencesUtils.getMessageSeqInCrease(context, (String) propertiesMap.get(Parameters.PACKAGE_NAME)))).build(), emitNow);
        }
    }

    private static boolean remotePushTracker(Context paramContext, Map<String, String> propertiesMap) {
        String componentName = null;
        String managerServicePackageName = null;
        List<ResolveInfo> localList = paramContext.getPackageManager().queryIntentServices(new Intent(PushConstants.MZ_PUSH_TRACKER_SERVICE_ACTION), 0);
        if (localList != null) {
            for (ResolveInfo info : localList) {
                if ("com.meizu.cloud".equals(info.serviceInfo.packageName)) {
                    managerServicePackageName = info.serviceInfo.packageName;
                    componentName = info.serviceInfo.name;
                    break;
                }
            }
            if (TextUtils.isEmpty(componentName) && localList.size() > 0) {
                managerServicePackageName = ((ResolveInfo) localList.get(0)).serviceInfo.packageName;
                componentName = ((ResolveInfo) localList.get(0)).serviceInfo.name;
            }
        }
        DebugLogger.i(TAG, "current process packageName " + managerServicePackageName);
        if (TextUtils.isEmpty(componentName)) {
            return false;
        }
        try {
            String trackerData = Util.mapToJSONObject(propertiesMap).toString();
            Intent intent = new Intent();
            intent.setPackage(managerServicePackageName);
            intent.setAction(PushConstants.MZ_PUSH_TRACKER_SERVICE_ACTION);
            intent.putExtra(PushConstants.EXTRA_PUSH_TRACKER_JSON_DATA, trackerData);
            paramContext.startService(intent);
            DebugLogger.i(TAG, "Start tracker data in mz_tracker process " + trackerData);
            return true;
        } catch (Exception e) {
            DebugLogger.e(TAG, "start RemoteService error " + e.getMessage());
            return false;
        }
    }
}
