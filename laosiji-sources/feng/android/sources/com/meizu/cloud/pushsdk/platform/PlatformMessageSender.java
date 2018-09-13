package com.meizu.cloud.pushsdk.platform;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.handler.MessageV3;
import com.meizu.cloud.pushsdk.handler.impl.model.PlatformMessage;
import com.meizu.cloud.pushsdk.platform.message.BasicPushStatus;
import com.meizu.cloud.pushsdk.platform.message.PushSwitchStatus;
import com.meizu.cloud.pushsdk.platform.message.RegisterStatus;
import com.meizu.cloud.pushsdk.platform.message.SubAliasStatus;
import com.meizu.cloud.pushsdk.platform.message.SubTagsStatus;
import com.meizu.cloud.pushsdk.platform.message.UnRegisterStatus;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;
import com.meizu.cloud.pushsdk.util.UxIPUtils;
import java.util.List;

public class PlatformMessageSender {
    public static final String TAG = "PlatformMessageSender";

    private interface OnUpdateIntent {
        BasicPushStatus getBasicStatus();

        String getBasicStatusExtra();

        String getMethod();
    }

    public static void sendPushStatus(Context context, String packageName, final PushSwitchStatus pushSwitchStatus) {
        sendPlatformStatus(context, packageName, new OnUpdateIntent() {
            public String getMethod() {
                return PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_PUSH_STATUS;
            }

            public BasicPushStatus getBasicStatus() {
                return pushSwitchStatus;
            }

            public String getBasicStatusExtra() {
                return PushConstants.EXTRA_APP_PUSH_SWITCH_STATUS;
            }
        });
    }

    public static void sendRegisterStatus(Context context, String packageName, final RegisterStatus registerStatus) {
        sendPlatformStatus(context, packageName, new OnUpdateIntent() {
            public String getMethod() {
                return PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_REGISTER_STATUS;
            }

            public BasicPushStatus getBasicStatus() {
                return registerStatus;
            }

            public String getBasicStatusExtra() {
                return PushConstants.EXTRA_APP_PUSH_REGISTER_STATUS;
            }
        });
    }

    public static void sendUnRegisterStatus(Context context, String packageName, final UnRegisterStatus unRegisterStatus) {
        sendPlatformStatus(context, packageName, new OnUpdateIntent() {
            public String getMethod() {
                return PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_UNREGISTER_STATUS;
            }

            public BasicPushStatus getBasicStatus() {
                return unRegisterStatus;
            }

            public String getBasicStatusExtra() {
                return PushConstants.EXTRA_APP_PUSH_UNREGISTER_STATUS;
            }
        });
    }

    public static void sendSubTags(Context context, String packageName, final SubTagsStatus subTagsStatus) {
        sendPlatformStatus(context, packageName, new OnUpdateIntent() {
            public String getMethod() {
                return PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_SUBTAGS_STATUS;
            }

            public BasicPushStatus getBasicStatus() {
                return subTagsStatus;
            }

            public String getBasicStatusExtra() {
                return PushConstants.EXTRA_APP_PUSH_SUBTAGS_STATUS;
            }
        });
    }

    public static void sendSubAlias(Context context, String packageName, final SubAliasStatus subAliasStatus) {
        sendPlatformStatus(context, packageName, new OnUpdateIntent() {
            public String getMethod() {
                return PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_SUBALIAS_STATUS;
            }

            public BasicPushStatus getBasicStatus() {
                return subAliasStatus;
            }

            public String getBasicStatusExtra() {
                return PushConstants.EXTRA_APP_PUSH_SUBALIAS_STATUS;
            }
        });
    }

    private static void sendPlatformStatus(Context context, String packageName, OnUpdateIntent onUpdateIntent) {
        Intent intent = new Intent();
        intent.addCategory(packageName);
        intent.setPackage(packageName);
        intent.putExtra(PushConstants.MZ_PUSH_MESSAGE_METHOD, onUpdateIntent.getMethod());
        intent.putExtra(onUpdateIntent.getBasicStatusExtra(), onUpdateIntent.getBasicStatus());
        sendMessageFromBroadcast(context, intent, PushConstants.MZ_PUSH_ON_MESSAGE_ACTION, packageName);
        sendMessageFromBroadcast(context, new Intent("com.meizu.cloud.pushservice.action.PUSH_SERVICE_START"), null, packageName);
    }

    public static void switchPushMessageSetting(Context context, int switchType, boolean switcher, String desPackageName) {
        String cloudVersionName = MzSystemUtils.getAppVersionName(context, "com.meizu.cloud");
        DebugLogger.i(TAG, context.getPackageName() + " switchPushMessageSetting cloudVersion_name " + cloudVersionName);
        if (!TextUtils.isEmpty(cloudVersionName) && cloudVersionName.startsWith("6")) {
            Intent pushServiceIntent = new Intent(PushConstants.MZ_PUSH_ON_MESSAGE_SWITCH_SETTING);
            pushServiceIntent.putExtra(PushConstants.EXTRA_APP_PUSH_SWITCH_SETTING_TYPE, switchType);
            pushServiceIntent.putExtra(PushConstants.EXTRA_APP_PUSH_SWITCH_SETTING_STATUS, switcher);
            pushServiceIntent.putExtra(PushConstants.EXTRA_APP_PUSH_SWITCH_SETTING_PACKAGE_NAME, desPackageName);
            pushServiceIntent.setClassName("com.meizu.cloud", "com.meizu.cloud.pushsdk.pushservice.MzPushService");
            context.startService(pushServiceIntent);
        }
    }

    public static void launchStartActivity(Context context, String packageName, String pushMessage, String platformExtra) {
        PlatformMessage platformMessage = UxIPUtils.buildPlatformMessage(platformExtra);
        MessageV3 messageV3 = MessageV3.parse(packageName, packageName, platformMessage.getPushTimesTamp(), platformMessage.getDeviceId(), platformMessage.getTaskId(), platformMessage.getSeqId(), pushMessage);
        Intent serviceClickIntent = new Intent();
        serviceClickIntent.setData(Uri.parse("custom://" + System.currentTimeMillis()));
        serviceClickIntent.putExtra(PushConstants.MZ_PUSH_PRIVATE_MESSAGE, messageV3);
        serviceClickIntent.putExtra(PushConstants.MZ_PUSH_MESSAGE_METHOD, PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_PRIVATE);
        serviceClickIntent.setAction(PushConstants.MZ_PUSH_ON_MESSAGE_ACTION);
        if (!TextUtils.isEmpty(packageName)) {
            serviceClickIntent.setPackage(packageName);
            serviceClickIntent.setClassName(packageName, "com.meizu.cloud.pushsdk.NotificationService");
        }
        serviceClickIntent.putExtra("command_type", "reflect_receiver");
        DebugLogger.i(TAG, "start notification service " + messageV3);
        try {
            context.startService(serviceClickIntent);
        } catch (Exception e) {
            DebugLogger.e(TAG, "launchStartActivity error " + e.getMessage());
        }
    }

    public static void sendMessageFromBroadcast(Context paramContext, Intent paramIntent, String action, String packageName) {
        if (!TextUtils.isEmpty(action)) {
            paramIntent.setAction(action);
        }
        if (!TextUtils.isEmpty(packageName)) {
            paramIntent.setPackage(packageName);
        }
        String name = findReceiver(paramContext, action, packageName);
        if (!TextUtils.isEmpty(name)) {
            paramIntent.setClassName(packageName, name);
        }
        paramContext.sendBroadcast(paramIntent);
    }

    public static String findReceiver(Context paramContext, String action, String packageName) {
        if (TextUtils.isEmpty(action) || TextUtils.isEmpty(packageName)) {
            return null;
        }
        Intent localIntent = new Intent(action);
        localIntent.setPackage(packageName);
        List localList = paramContext.getPackageManager().queryBroadcastReceivers(localIntent, 0);
        if (localList == null || localList.size() <= 0) {
            return null;
        }
        return ((ResolveInfo) localList.get(0)).activityInfo.name;
    }
}
