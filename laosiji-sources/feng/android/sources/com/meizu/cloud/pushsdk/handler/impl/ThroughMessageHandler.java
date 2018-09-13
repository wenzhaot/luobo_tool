package com.meizu.cloud.pushsdk.handler.impl;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.handler.AbstractAppLogicListener;
import com.meizu.cloud.pushsdk.handler.MessageV3;
import com.meizu.cloud.pushsdk.handler.impl.model.PlatformMessage;
import com.meizu.cloud.pushsdk.notification.PushNotification;
import com.meizu.cloud.pushsdk.util.UxIPUtils;

public class ThroughMessageHandler extends AbstractMessageHandler<MessageV3> {
    public ThroughMessageHandler(Context context, AbstractAppLogicListener abstractAppLogicListener) {
        super(context, abstractAppLogicListener);
    }

    public boolean messageMatch(Intent intent) {
        DebugLogger.i("AbstractMessageHandler", "start ThroughMessageHandler match");
        if (!canReceiveMessage(1, getPushServiceDefaultPackageName(intent))) {
            return false;
        }
        if (PushConstants.MZ_PUSH_ON_MESSAGE_ACTION.equals(intent.getAction())) {
            if ("message".equals(getIntentMethod(intent))) {
                return true;
            }
            if (TextUtils.isEmpty(getIntentMethod(intent))) {
                String cloudPushMessage = intent.getStringExtra("message");
                if (!(TextUtils.isEmpty(cloudPushMessage) || isNotificationJson(cloudPushMessage))) {
                    return true;
                }
            }
        }
        if (PushConstants.C2DM_INTENT.equals(intent.getAction())) {
            return true;
        }
        return false;
    }

    public int getProcessorType() {
        return 8;
    }

    protected MessageV3 getMessage(Intent intent) {
        MessageV3 messageV3 = new MessageV3();
        if (PushConstants.C2DM_INTENT.equals(intent.getAction())) {
            appLogicListener().onMessage(context(), intent);
            return null;
        }
        messageV3.setThroughMessage(intent.getStringExtra("message"));
        messageV3.setTaskId(getTaskId(intent));
        messageV3.setDeviceId(getDeviceId(intent));
        messageV3.setSeqId(getSeqId(intent));
        messageV3.setPushTimestamp(getPushTimestamp(intent));
        messageV3.setUploadDataPackageName(getPushServiceDefaultPackageName(intent));
        return messageV3;
    }

    protected void unsafeSend(MessageV3 message, PushNotification pushNotification) {
        if (appLogicListener() != null && message != null && !TextUtils.isEmpty(message.getThroughMessage())) {
            appLogicListener().onMessage(context(), message.getThroughMessage());
            appLogicListener().onMessage(context(), message.getThroughMessage(), PlatformMessage.builder().taskId(message.getTaskId()).seqId(message.getSeqId()).pushTimesTamp(message.getPushTimestamp()).deviceId(message.getDeviceId()).build().toJson());
        }
    }

    protected void onBeforeEvent(MessageV3 message) {
        if (message != null && !TextUtils.isEmpty(message.getDeviceId()) && !TextUtils.isEmpty(message.getTaskId())) {
            String deskTopNotificationPkg = getDeskTopNotificationPkg(message.getThroughMessage());
            if (TextUtils.isEmpty(deskTopNotificationPkg)) {
                UxIPUtils.onReceiveThroughMessage(context(), message.getUploadDataPackageName(), message.getDeviceId(), message.getTaskId(), message.getSeqId(), message.getPushTimestamp());
            } else {
                UxIPUtils.onReceiveThroughMessage(context(), deskTopNotificationPkg, message.getDeviceId(), message.getTaskId(), message.getSeqId(), message.getPushTimestamp());
            }
        }
    }
}
