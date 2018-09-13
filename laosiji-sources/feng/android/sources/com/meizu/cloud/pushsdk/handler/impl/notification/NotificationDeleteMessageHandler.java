package com.meizu.cloud.pushsdk.handler.impl.notification;

import android.content.Context;
import android.content.Intent;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.handler.AbstractAppLogicListener;
import com.meizu.cloud.pushsdk.handler.MessageV3;
import com.meizu.cloud.pushsdk.handler.MzPushMessage;
import com.meizu.cloud.pushsdk.handler.impl.AbstractMessageHandler;
import com.meizu.cloud.pushsdk.notification.PushNotification;
import com.meizu.cloud.pushsdk.util.UxIPUtils;

public class NotificationDeleteMessageHandler extends AbstractMessageHandler<MessageV3> {
    public NotificationDeleteMessageHandler(Context context, AbstractAppLogicListener abstractAppLogicListener) {
        super(context, abstractAppLogicListener);
    }

    protected MessageV3 getMessage(Intent intent) {
        return (MessageV3) intent.getParcelableExtra(PushConstants.MZ_PUSH_PRIVATE_MESSAGE);
    }

    protected void unsafeSend(MessageV3 message, PushNotification pushNotification) {
        if (appLogicListener() != null && message != null) {
            appLogicListener().onNotificationDeleted(context(), MzPushMessage.fromMessageV3(message));
            clearNotifyOption(message);
        }
    }

    public boolean messageMatch(Intent intent) {
        DebugLogger.i("AbstractMessageHandler", "start NotificationDeleteMessageHandler match");
        return PushConstants.MZ_PUSH_ON_MESSAGE_ACTION.equals(intent.getAction()) && PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_NOTIFICATION_DELETE.equals(getIntentMethod(intent));
    }

    public int getProcessorType() {
        return 128;
    }

    protected void onBeforeEvent(MessageV3 message) {
        UxIPUtils.onDeletePushMessageEvent(context(), message.getUploadDataPackageName(), message.getDeviceId(), message.getTaskId(), message.getSeqId(), message.getPushTimestamp());
    }
}
