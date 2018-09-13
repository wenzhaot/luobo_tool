package com.meizu.cloud.pushsdk;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.base.IntentReceiver;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.pushtracer.QuickTracker;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;
import com.meizu.cloud.pushsdk.util.UxIPUtils;

public class SystemReceiver extends IntentReceiver {
    private static final String TAG = "SystemReceiver";

    public void onReceive(Context context, Intent intent) {
        try {
            super.onReceive(context, intent);
        } catch (Exception e) {
            DebugLogger.e(TAG, "Event core error " + e.getMessage());
            String str = "SystemReceiver " + e.getMessage();
            UxIPUtils.onRecordMessageFlow(context, context.getPackageName(), null, null, "3.5.0-SNAPSHOT", str, 3000);
        }
    }

    public void onHandleIntent(Context context, Intent intent) {
        if (intent != null) {
            try {
                if ("com.meizu.cloud.pushservice.action.PUSH_SERVICE_START".equals(intent.getAction())) {
                    restartCloudService(context);
                    QuickTracker.init(context, false).restartEventTracking();
                }
            } catch (Exception e) {
                DebugLogger.e(TAG, "onHandleIntent Exception " + e.getMessage());
            }
        }
    }

    public void startPushService(Context paramContext, Intent paramIntent) {
        try {
            paramContext.startService(paramIntent);
        } catch (SecurityException localSecurityException) {
            DebugLogger.e(TAG, "start service error " + localSecurityException.getMessage());
        }
    }

    public void restartCloudService(Context context) {
        String cloudVersionName = MzSystemUtils.getAppVersionName(context, "com.meizu.cloud");
        DebugLogger.i(TAG, context.getPackageName() + " start register cloudVersion_name " + cloudVersionName);
        Intent pushServiceIntent = new Intent();
        if ("com.meizu.cloud".equals(MzSystemUtils.getMzPushServicePackageName(context))) {
            DebugLogger.e(TAG, "cloud pushService start");
            pushServiceIntent.setAction("com.meizu.pushservice.action.START");
            pushServiceIntent.setClassName("com.meizu.cloud", "com.meizu.cloud.pushsdk.pushservice.MzPushService");
        } else if (!TextUtils.isEmpty(cloudVersionName) && MzSystemUtils.compareVersion(cloudVersionName, "4.5.7")) {
            DebugLogger.e(TAG, "flyme 4.x start register cloud versionName " + cloudVersionName);
            pushServiceIntent.setPackage("com.meizu.cloud");
            pushServiceIntent.setAction(PushConstants.MZ_PUSH_ON_START_PUSH_REGISTER);
        } else if (TextUtils.isEmpty(cloudVersionName) || !cloudVersionName.startsWith("3")) {
            DebugLogger.e(TAG, context.getPackageName() + " start register ");
            pushServiceIntent.setClassName(context.getPackageName(), "com.meizu.cloud.pushsdk.pushservice.MzPushService");
            pushServiceIntent.setAction("com.meizu.pushservice.action.START");
        } else {
            DebugLogger.e(TAG, "flyme 3.x start register cloud versionName " + cloudVersionName);
            pushServiceIntent.setAction(PushConstants.REQUEST_REGISTRATION_INTENT);
            pushServiceIntent.setPackage("com.meizu.cloud");
        }
        startPushService(context, pushServiceIntent);
    }
}
