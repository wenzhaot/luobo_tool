package com.meizu.cloud.pushsdk.handler.impl.schedule;

import android.content.Context;
import android.content.Intent;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.handler.AbstractAppLogicListener;
import com.meizu.cloud.pushsdk.handler.MessageV3;
import com.meizu.cloud.pushsdk.handler.impl.MessageV3Handler;
import com.meizu.cloud.pushsdk.notification.PushNotification;
import com.meizu.cloud.pushsdk.util.UxIPUtils;

public class ScheduleNotificationHandler extends MessageV3Handler {
    public ScheduleNotificationHandler(Context context, AbstractAppLogicListener abstractAppLogicListener) {
        super(context, abstractAppLogicListener);
    }

    protected MessageV3 getMessage(Intent intent) {
        return (MessageV3) intent.getParcelableExtra(PushConstants.EXTRA_APP_PUSH_SCHEDULE_NOTIFICATION_MESSAGE);
    }

    protected void unsafeSend(MessageV3 message, PushNotification pushNotification) {
        if (pushNotification != null) {
            pushNotification.show(message);
            sendArrivedMessage(message);
        }
    }

    protected int scheduleNotificationStatus(MessageV3 message) {
        return 0;
    }

    public boolean messageMatch(Intent intent) {
        DebugLogger.i("AbstractMessageHandler", "start ScheduleNotificationHandler match");
        return PushConstants.MZ_PUSH_ON_MESSAGE_ACTION.equals(intent.getAction()) && PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_SCHEDULE_NOTIFICATION.equals(getIntentMethod(intent));
    }

    protected void onBeforeEvent(MessageV3 message) {
        DebugLogger.e("AbstractMessageHandler", "ScheduleNotificationHandler dont repeat upload receiver push event");
    }

    protected void onAfterEvent(MessageV3 message) {
        UxIPUtils.onShowPushMessageEvent(context(), message.getUploadDataPackageName(), message.getDeviceId(), message.getTaskId(), message.getSeqId(), message.getPushTimestamp());
    }

    public int getProcessorType() {
        return 8192;
    }
}
