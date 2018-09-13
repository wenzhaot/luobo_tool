package com.meizu.cloud.pushsdk.handler.impl.platform;

import android.content.Context;
import android.content.Intent;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.handler.AbstractAppLogicListener;
import com.meizu.cloud.pushsdk.handler.impl.AbstractMessageHandler;
import com.meizu.cloud.pushsdk.notification.PushNotification;

public class ReceiveNotifyMessageHandler extends AbstractMessageHandler<String> {
    public ReceiveNotifyMessageHandler(Context context, AbstractAppLogicListener abstractAppLogicListener) {
        super(context, abstractAppLogicListener);
    }

    protected String getMessage(Intent intent) {
        return intent.getStringExtra(PushConstants.EXTRA_APP_PUSH_RESPONSE_NOTIFICATION_MESSAGE);
    }

    protected void unsafeSend(String message, PushNotification pushNotification) {
        if (appLogicListener() != null && message != null) {
            appLogicListener().onNotifyMessageArrived(context(), message);
        }
    }

    public boolean messageMatch(Intent intent) {
        DebugLogger.i("AbstractMessageHandler", "start ReceiveNotifyMessageHandler match");
        return PushConstants.MZ_PUSH_ON_MESSAGE_ACTION.equals(intent.getAction()) && PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_RESPONSE_NOTIFICATION_MESSAGE.equals(getIntentMethod(intent));
    }

    public int getProcessorType() {
        return 16384;
    }
}
