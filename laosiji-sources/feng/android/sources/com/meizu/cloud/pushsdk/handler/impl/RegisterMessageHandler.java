package com.meizu.cloud.pushsdk.handler.impl;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.handler.AbstractAppLogicListener;
import com.meizu.cloud.pushsdk.notification.PushNotification;
import com.meizu.cloud.pushsdk.util.PushPreferencesUtils;

public class RegisterMessageHandler extends AbstractMessageHandler<String> {
    public RegisterMessageHandler(Context context, AbstractAppLogicListener abstractAppLogicListener) {
        super(context, abstractAppLogicListener);
    }

    protected String getMessage(Intent intent) {
        String pushId = intent.getStringExtra("registration_id");
        PushPreferencesUtils.putPushId(context(), pushId, context().getPackageName());
        PushPreferencesUtils.putPushIdExpireTime(context(), 0, context().getPackageName());
        return pushId;
    }

    protected void unsafeSend(String message, PushNotification pushNotification) {
        if (appLogicListener() != null) {
            appLogicListener().onRegister(context(), message);
        }
    }

    public boolean messageMatch(Intent intent) {
        DebugLogger.i("AbstractMessageHandler", "start RegisterMessageHandler match");
        return PushConstants.MZ_PUSH_ON_REGISTER_ACTION.equals(intent.getAction()) || (PushConstants.REGISTRATION_CALLBACK_INTENT.equals(intent.getAction()) && !TextUtils.isEmpty(intent.getStringExtra("registration_id")));
    }

    public int getProcessorType() {
        return 16;
    }
}
