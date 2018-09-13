package com.meizu.cloud.pushsdk;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Process;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.base.reflect.ReflectClass;
import com.meizu.cloud.pushsdk.base.reflect.ReflectResult;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.handler.impl.model.ControlMessage;
import com.meizu.cloud.pushsdk.pushtracer.QuickTracker;
import com.meizu.cloud.pushsdk.util.UxIPUtils;
import com.stub.StubApp;
import java.util.List;

public class NotificationService extends IntentService {
    private static final String TAG = "NotificationService";
    private Object newInstance;

    public NotificationService(String name) {
        super(name);
    }

    public NotificationService() {
        super(TAG);
    }

    public void onDestroy() {
        DebugLogger.i(TAG, "NotificationService destroy");
        this.newInstance = null;
        super.onDestroy();
    }

    protected void onHandleIntent(Intent intent) {
        Process.setThreadPriority(10);
        if (intent != null) {
            try {
                DebugLogger.i(TAG, "onHandleIntentaction " + intent.getAction());
                String commandType = intent.getStringExtra("command_type");
                DebugLogger.d(TAG, "-- command_type -- " + commandType);
                if (!TextUtils.isEmpty(commandType) && commandType.equals("reflect_receiver")) {
                    String controlMessage = intent.getStringExtra(PushConstants.MZ_PUSH_CONTROL_MESSAGE);
                    DebugLogger.i(TAG, "control message is " + controlMessage);
                    if (!TextUtils.isEmpty(controlMessage)) {
                        QuickTracker.init(this, new ControlMessage(controlMessage, null, null).getStatics().getPushExtra());
                    }
                    reflectReceiver(intent);
                }
            } catch (Exception e) {
                DebugLogger.e(TAG, "onHandleIntent error " + e.getMessage());
            }
        }
    }

    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public String getReceiver(String var1, String var2) {
        if (TextUtils.isEmpty(var1) || TextUtils.isEmpty(var2)) {
            return null;
        }
        Intent var5 = new Intent(var2);
        var5.setPackage(var1);
        List var4 = getPackageManager().queryBroadcastReceivers(var5, 0);
        if (var4 == null || var4.size() <= 0) {
            return null;
        }
        return ((ResolveInfo) var4.get(0)).activityInfo.name;
    }

    public void reflectReceiver(Intent intent) {
        String receiver = getReceiver(getPackageName(), intent.getAction());
        if (TextUtils.isEmpty(receiver)) {
            UxIPUtils.notificationEvent(this, intent, "reflectReceiver sendbroadcast", 2005);
            DebugLogger.i(TAG, " reflectReceiver error: receiver for: " + intent.getAction() + " not found, package: " + getPackageName());
            intent.setPackage(getPackageName());
            sendBroadcast(intent);
            return;
        }
        try {
            UxIPUtils.notificationEvent(this, intent, "reflectReceiver startservice", 2003);
            intent.setClassName(getPackageName(), receiver);
            ReflectResult<Object> receiverResult = ReflectClass.forName(receiver).constructor((Class[]) null).newInstance((Object[]) null);
            if (receiverResult.ok && receiverResult.value != null) {
                DebugLogger.i(TAG, "Reflect MzPushReceiver " + receiverResult.ok);
                ReflectClass.forObject(receiverResult.value).method("onReceive", Context.class, Intent.class).invoke(receiverResult.value, StubApp.getOrigApplicationContext(getApplicationContext()), intent);
            }
        } catch (Exception var11) {
            DebugLogger.i(TAG, "reflect e: " + var11);
            UxIPUtils.notificationEvent(this, intent, var11.getMessage(), 2004);
        }
    }
}
