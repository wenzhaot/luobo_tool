package com.meizu.cloud.pushsdk.handler.impl.platform;

import android.content.Context;
import android.content.Intent;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.handler.AbstractAppLogicListener;
import com.meizu.cloud.pushsdk.handler.impl.AbstractMessageHandler;
import com.meizu.cloud.pushsdk.notification.PushNotification;
import com.meizu.cloud.pushsdk.platform.message.PushSwitchStatus;

public class PushSwitchStatusHandler extends AbstractMessageHandler<PushSwitchStatus> {
    public PushSwitchStatusHandler(Context context, AbstractAppLogicListener abstractAppLogicListener) {
        super(context, abstractAppLogicListener);
    }

    protected PushSwitchStatus getMessage(Intent intent) {
        return (PushSwitchStatus) intent.getSerializableExtra(PushConstants.EXTRA_APP_PUSH_SWITCH_STATUS);
    }

    protected void unsafeSend(PushSwitchStatus message, PushNotification pushNotification) {
        if (appLogicListener() != null && message != null) {
            appLogicListener().onPushStatus(context(), message);
        }
    }

    public boolean messageMatch(Intent intent) {
        DebugLogger.i("AbstractMessageHandler", "start PushSwitchStatusHandler match");
        return PushConstants.MZ_PUSH_ON_MESSAGE_ACTION.equals(intent.getAction()) && PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_PUSH_STATUS.equals(getIntentMethod(intent));
    }

    public int getProcessorType() {
        return AbstractMessageHandler.MESSAGE_TYPE_PUSH_SWITCH_STATUS;
    }
}
