package com.meizu.cloud.pushsdk.handler.impl;

import android.content.Context;
import android.content.Intent;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.handler.AbstractAppLogicListener;
import com.meizu.cloud.pushsdk.handler.MessageV3;
import com.meizu.cloud.pushsdk.handler.MzPushMessage;
import com.meizu.cloud.pushsdk.notification.MPushMessage;
import com.meizu.cloud.pushsdk.notification.PushNotification;
import com.meizu.cloud.pushsdk.util.UxIPUtils;

public class MessageV2Handler extends MessageV3Handler {
    public MessageV2Handler(Context context, AbstractAppLogicListener abstractAppLogicListener) {
        super(context, abstractAppLogicListener);
    }

    public boolean messageMatch(Intent intent) {
        DebugLogger.i("AbstractMessageHandler", "start MessageV2Handler match");
        if (canReceiveMessage(0, getPushServiceDefaultPackageName(intent)) && PushConstants.MZ_PUSH_ON_MESSAGE_ACTION.equals(intent.getAction()) && PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_NOTIFICATION_SHOW.equals(getIntentMethod(intent))) {
            return true;
        }
        return false;
    }

    public int getProcessorType() {
        return 2;
    }

    protected MessageV3 getMessage(Intent intent) {
        MPushMessage mPushMessage = (MPushMessage) intent.getSerializableExtra(PushConstants.MZ_PUSH_PRIVATE_MESSAGE);
        return MessageV3.parse(getPushServiceDefaultPackageName(intent), getDeviceId(intent), mPushMessage.getTaskId(), mPushMessage);
    }

    protected void unsafeSend(MessageV3 message, PushNotification pushNotification) {
        if (pushNotification != null) {
            pushNotification.show(message);
            appLogicListener().onNotificationArrived(context(), MzPushMessage.fromMessageV3(message));
        }
    }

    protected void onBeforeEvent(MessageV3 message) {
        UxIPUtils.onReceivePushMessageEvent(context(), message.getUploadDataPackageName(), message.getDeviceId(), message.getTaskId(), message.getSeqId(), message.getPushTimestamp());
    }

    protected void onAfterEvent(MessageV3 message) {
        UxIPUtils.onShowPushMessageEvent(context(), message.getUploadDataPackageName(), message.getDeviceId(), message.getTaskId(), message.getSeqId(), message.getPushTimestamp());
    }
}
