package com.meizu.cloud.pushsdk.handler.impl;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.handler.AbstractAppLogicListener;
import com.meizu.cloud.pushsdk.notification.PushNotification;
import com.meizu.cloud.pushsdk.util.PushPreferencesUtils;

public class UnRegisterMessageHandler extends AbstractMessageHandler<Boolean> {
    public UnRegisterMessageHandler(Context context, AbstractAppLogicListener abstractAppLogicListener) {
        super(context, abstractAppLogicListener);
    }

    protected Boolean getMessage(Intent intent) {
        boolean isSuccess = intent.getBooleanExtra(PushConstants.EXTRA_APP_IS_UNREGISTER_SUCCESS, false);
        String error = intent.getStringExtra(PushConstants.EXTRA_REGISTRATION_ERROR);
        String removed = intent.getStringExtra(PushConstants.EXTRA_UNREGISTERED);
        DebugLogger.i("AbstractMessageHandler", "processUnRegisterCallback 5.0:" + isSuccess + " 4.0:" + error + " 3.0:" + removed);
        if (!TextUtils.isEmpty(error) && !isSuccess && TextUtils.isEmpty(removed)) {
            return Boolean.valueOf(false);
        }
        PushPreferencesUtils.putPushId(context(), "", context().getPackageName());
        return Boolean.valueOf(true);
    }

    protected void unsafeSend(Boolean message, PushNotification pushNotification) {
        if (appLogicListener() != null) {
            appLogicListener().onUnRegister(context(), message.booleanValue());
        }
    }

    public boolean messageMatch(Intent intent) {
        DebugLogger.i("AbstractMessageHandler", "start UnRegisterMessageHandler match");
        return PushConstants.MZ_PUSH_ON_UNREGISTER_ACTION.equals(intent.getAction()) || (PushConstants.REQUEST_UNREGISTRATION_INTENT.equals(intent.getAction()) && TextUtils.isEmpty(intent.getStringExtra(PushConstants.EXTRA_UNREGISTERED)));
    }

    public int getProcessorType() {
        return 32;
    }
}
