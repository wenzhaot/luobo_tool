package com.meizu.cloud.pushsdk.handler.impl.notification;

import android.content.Context;
import android.content.Intent;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.handler.AbstractAppLogicListener;
import com.meizu.cloud.pushsdk.handler.MessageV3;
import com.meizu.cloud.pushsdk.handler.impl.AbstractMessageHandler;
import com.meizu.cloud.pushsdk.handler.impl.model.NotificationState;
import com.meizu.cloud.pushsdk.notification.PushNotification;
import com.meizu.cloud.pushsdk.notification.util.NotificationUtils;
import com.meizu.cloud.pushsdk.util.UxIPUtils;

public class NotificationStateMessageHandler extends AbstractMessageHandler<NotificationState> {
    public NotificationStateMessageHandler(Context context, AbstractAppLogicListener abstractAppLogicListener) {
        super(context, abstractAppLogicListener);
    }

    protected NotificationState getMessage(Intent intent) {
        String uploadPackageName = intent.getStringExtra(PushConstants.NOTIFICATION_EXTRA_SHOW_PACKAGE_NAME);
        String taskId = intent.getStringExtra(PushConstants.NOTIFICATION_EXTRA_TASK_ID);
        String seqId = intent.getStringExtra(PushConstants.NOTIFICATION_EXTRA_SEQ_ID);
        String deviceId = intent.getStringExtra(PushConstants.NOTIFICATION_EXTRA_DEVICE_ID);
        String pushTimestamp = intent.getStringExtra(PushConstants.NOTIFICATION_EXTRA_PUSH_TIMESTAMP);
        String notificationMessage = intent.getStringExtra(PushConstants.MZ_PUSH_NOTIFICATION_STATE_MESSAGE);
        DebugLogger.i("AbstractMessageHandler", "current taskId " + taskId + " seqId " + seqId + " deviceId " + deviceId + " packageName " + uploadPackageName);
        NotificationState notificationState = new NotificationState(MessageV3.parse(context().getPackageName(), uploadPackageName, pushTimestamp, deviceId, taskId, seqId, notificationMessage));
        String pkg = intent.getStringExtra("flyme:notification_pkg");
        int id = intent.getIntExtra("flyme:notification_id", 0);
        int state = intent.getIntExtra("flyme:notification_state", 0);
        notificationState.setNotificationId(id);
        notificationState.setNotificationPkg(pkg);
        notificationState.setState(state);
        return notificationState;
    }

    protected void unsafeSend(NotificationState message, PushNotification pushNotification) {
        DebugLogger.e("AbstractMessageHandler", "store notification id " + message.getNotificationId());
        NotificationUtils.storeNotifyIdByPackageName(context(), message.getMessageV3().getUploadDataPackageName(), message.getNotificationId());
    }

    public boolean messageMatch(Intent intent) {
        DebugLogger.i("AbstractMessageHandler", "start NotificationStateMessageHandler match");
        return PushConstants.MZ_PUSH_ON_MESSAGE_ACTION.equals(intent.getAction()) && PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_NOTIFICATION_STATE.equals(getIntentMethod(intent));
    }

    public int getProcessorType() {
        return 32768;
    }

    protected void onBeforeEvent(NotificationState message) {
        switch (message.getState()) {
            case -2:
                DebugLogger.e("AbstractMessageHandler", "notification STATE_NOTIFICATION_SHOW_ACCESS_DENY");
                UxIPUtils.onNoShowPushMessage(context(), message.getMessageV3().getUploadDataPackageName(), message.getMessageV3().getDeviceId(), message.getMessageV3().getTaskId(), message.getMessageV3().getSeqId(), message.getMessageV3().getPushTimestamp());
                return;
            case -1:
                DebugLogger.e("AbstractMessageHandler", "notification STATE_NOTIFICATION_SHOW_INBOX");
                UxIPUtils.onShowInBoxPushMessage(context(), message.getMessageV3().getUploadDataPackageName(), message.getMessageV3().getDeviceId(), message.getMessageV3().getTaskId(), message.getMessageV3().getSeqId(), message.getMessageV3().getPushTimestamp());
                return;
            case 0:
                DebugLogger.e("AbstractMessageHandler", "notification STATE_NOTIFICATION_SHOW_NORMAL");
                return;
            case 1:
                DebugLogger.e("AbstractMessageHandler", "notification STATE_NOTIFICATION_SHOW_FLOAT");
                return;
            default:
                return;
        }
    }
}
