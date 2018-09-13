package com.meizu.cloud.pushsdk.handler.impl;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.handler.AbstractAppLogicListener;
import com.meizu.cloud.pushsdk.handler.MessageHandler;
import com.meizu.cloud.pushsdk.handler.MessageV3;
import com.meizu.cloud.pushsdk.handler.MzPushMessage;
import com.meizu.cloud.pushsdk.notification.PushNotification;
import com.meizu.cloud.pushsdk.notification.model.NotifyOption;
import com.meizu.cloud.pushsdk.notification.util.NotificationUtils;
import com.meizu.cloud.pushsdk.platform.PlatformMessageSender;
import com.meizu.cloud.pushsdk.util.MinSdkChecker;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;
import com.meizu.cloud.pushsdk.util.PushPreferencesUtils;
import com.stub.StubApp;
import com.taobao.accs.common.Constants;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public abstract class AbstractMessageHandler<T> implements MessageHandler {
    public static final int MESSAGE_TYPE_NOTIFICATION_ARRIVED = 131072;
    public static final int MESSAGE_TYPE_NOTIFICATION_CLICK = 64;
    public static final int MESSAGE_TYPE_NOTIFICATION_DELETE = 128;
    public static final int MESSAGE_TYPE_NOTIFICATION_STATE = 32768;
    public static final int MESSAGE_TYPE_PUSH_REGISTER_STATUS = 512;
    public static final int MESSAGE_TYPE_PUSH_SERVICE_V2 = 2;
    public static final int MESSAGE_TYPE_PUSH_SERVICE_V3 = 4;
    public static final int MESSAGE_TYPE_PUSH_SUBALIAS_STATUS = 4096;
    public static final int MESSAGE_TYPE_PUSH_SUBTAGS_STATUS = 2048;
    public static final int MESSAGE_TYPE_PUSH_SWITCH_STATUS = 256;
    public static final int MESSAGE_TYPE_PUSH_UNREGISTER_STATUS = 1024;
    public static final int MESSAGE_TYPE_RECEIVE_NOTIFY_MESSAGE = 16384;
    public static final int MESSAGE_TYPE_REGISTER = 16;
    public static final int MESSAGE_TYPE_SCHEDULE_NOTIFICATION = 8192;
    public static final int MESSAGE_TYPE_THROUGH = 8;
    public static final int MESSAGE_TYPE_UNREGISTER = 32;
    public static final int MESSAGE_TYPE_UPLOAD_FILE_LOG = 65536;
    public static final int SCHEDULE_OFF = 0;
    public static final int SCHEDULE_ON_DELAY = 3;
    public static final int SCHEDULE_ON_EXPIRE = 1;
    public static final int SCHEDULE_ON_TIME = 2;
    protected static final String TAG = "AbstractMessageHandler";
    private AbstractAppLogicListener abstractAppLogicListener;
    private Context context;
    private Map<Integer, String> messageHandlerMap;

    protected abstract T getMessage(Intent intent);

    protected abstract void unsafeSend(T t, PushNotification pushNotification);

    protected AbstractMessageHandler(Context context) {
        this(context, null);
    }

    protected AbstractMessageHandler(Context context, AbstractAppLogicListener abstractAppLogicListener) {
        if (context == null) {
            throw new IllegalArgumentException("Context must not be null.");
        }
        this.context = StubApp.getOrigApplicationContext(context.getApplicationContext());
        this.abstractAppLogicListener = abstractAppLogicListener;
        this.messageHandlerMap = new HashMap();
        this.messageHandlerMap.put(Integer.valueOf(2), "MESSAGE_TYPE_PUSH_SERVICE_V2");
        this.messageHandlerMap.put(Integer.valueOf(4), "MESSAGE_TYPE_PUSH_SERVICE_V3");
        this.messageHandlerMap.put(Integer.valueOf(16), "MESSAGE_TYPE_REGISTER");
        this.messageHandlerMap.put(Integer.valueOf(32), "MESSAGE_TYPE_UNREGISTER");
        this.messageHandlerMap.put(Integer.valueOf(8), "MESSAGE_TYPE_THROUGH");
        this.messageHandlerMap.put(Integer.valueOf(64), "MESSAGE_TYPE_NOTIFICATION_CLICK");
        this.messageHandlerMap.put(Integer.valueOf(128), "MESSAGE_TYPE_NOTIFICATION_DELETE");
        this.messageHandlerMap.put(Integer.valueOf(MESSAGE_TYPE_PUSH_SWITCH_STATUS), "MESSAGE_TYPE_PUSH_SWITCH_STATUS");
        this.messageHandlerMap.put(Integer.valueOf(512), "MESSAGE_TYPE_PUSH_REGISTER_STATUS");
        this.messageHandlerMap.put(Integer.valueOf(2048), "MESSAGE_TYPE_PUSH_SUBTAGS_STATUS");
        this.messageHandlerMap.put(Integer.valueOf(1024), "MESSAGE_TYPE_PUSH_UNREGISTER_STATUS");
        this.messageHandlerMap.put(Integer.valueOf(4096), "MESSAGE_TYPE_PUSH_SUBALIAS_STATUS");
        this.messageHandlerMap.put(Integer.valueOf(8192), "MESSAGE_TYPE_SCHEDULE_NOTIFICATION");
        this.messageHandlerMap.put(Integer.valueOf(16384), "MESSAGE_TYPE_RECEIVE_NOTIFY_MESSAGE");
        this.messageHandlerMap.put(Integer.valueOf(32768), "MESSAGE_TYPE_NOTIFICATION_STATE");
        this.messageHandlerMap.put(Integer.valueOf(65536), "MESSAGE_TYPE_UPLOAD_FILE_LOG");
        this.messageHandlerMap.put(Integer.valueOf(131072), "MESSAGE_TYPE_NOTIFICATION_ARRIVED");
    }

    protected PushNotification onCreateNotification(T t) {
        return null;
    }

    protected void onBeforeEvent(T t) {
    }

    protected void onAfterEvent(T t) {
    }

    protected int scheduleNotificationStatus(T t) {
        return 0;
    }

    protected void scheduleShowNotification(T t) {
    }

    protected boolean canSendMessage(T t) {
        return true;
    }

    protected String getDeviceId(Intent intent) {
        String deviceId = null;
        if (intent != null) {
            deviceId = intent.getStringExtra(PushConstants.MZ_PUSH_MESSAGE_STATISTICS_IMEI_KEY);
        }
        if (!TextUtils.isEmpty(deviceId)) {
            return deviceId;
        }
        deviceId = MzSystemUtils.getDeviceId(context());
        DebugLogger.e(TAG, "force get deviceId " + deviceId);
        return deviceId;
    }

    protected String getTaskId(Intent intent) {
        return intent.getStringExtra(PushConstants.EXTRA_APP_PUSH_TASK_ID);
    }

    protected String getSeqId(Intent intent) {
        return intent.getStringExtra(PushConstants.EXTRA_APP_PUSH_SEQ_ID);
    }

    protected String getPushServiceDefaultPackageName(Intent intent) {
        String defaultPackageName = intent.getStringExtra(PushConstants.EXTRA_APP_PUSH_SERVICE_DEFAULT_PACKAGE_NAME);
        if (TextUtils.isEmpty(defaultPackageName)) {
            return context().getPackageName();
        }
        return defaultPackageName;
    }

    protected String getPushTimestamp(Intent intent) {
        String pushTimestamp = intent.getStringExtra(PushConstants.EXTRA_APP_PUSH_TASK_TIMES_TAMP);
        DebugLogger.e(TAG, "receive push timestamp from pushservice " + pushTimestamp);
        if (TextUtils.isEmpty(pushTimestamp)) {
            return String.valueOf(System.currentTimeMillis() / 1000);
        }
        return pushTimestamp;
    }

    public boolean sendMessage(Intent intent) {
        boolean flag = false;
        if (messageMatch(intent)) {
            DebugLogger.e(TAG, "current message Type " + getMessageHandlerType(getProcessorType()));
            T message = getMessage(intent);
            DebugLogger.e(TAG, "current Handler message " + message);
            onBeforeEvent(message);
            boolean isCustomSend = false;
            switch (scheduleNotificationStatus(message)) {
                case 0:
                    DebugLogger.e(TAG, "schedule send message off, send message directly");
                    isCustomSend = true;
                    flag = true;
                    break;
                case 1:
                    DebugLogger.e(TAG, "expire notification, dont show message");
                    flag = false;
                    break;
                case 2:
                    DebugLogger.e(TAG, "notification on time ,show message");
                    isCustomSend = true;
                    flag = true;
                    break;
                case 3:
                    DebugLogger.e(TAG, "schedule notification");
                    scheduleShowNotification(message);
                    flag = true;
                    break;
            }
            boolean canSend = canSendMessage(message);
            DebugLogger.e(TAG, "can send message " + canSend);
            if (flag && isCustomSend && canSend) {
                unsafeSend(message, onCreateNotification(message));
                onAfterEvent(message);
                DebugLogger.e(TAG, "send message end ");
            }
        }
        return flag;
    }

    public AbstractAppLogicListener appLogicListener() {
        return this.abstractAppLogicListener;
    }

    public Context context() {
        return this.context;
    }

    public String getIntentMethod(Intent intent) {
        return intent.getStringExtra(PushConstants.MZ_PUSH_MESSAGE_METHOD);
    }

    public boolean isNotificationJson(String notificationJson) {
        try {
            return context().getPackageName().equals(new JSONObject(notificationJson).getString("appId"));
        } catch (Exception e) {
            DebugLogger.e(TAG, "parse notification error");
            return false;
        }
    }

    public String getDeskTopNotificationPkg(String deskTopNotificationJson) {
        String pkg = "";
        try {
            JSONObject launcherObj = new JSONObject(deskTopNotificationJson).getJSONObject("launcher");
            if (!launcherObj.has(Constants.KEY_ELECTION_PKG) || TextUtils.isEmpty(launcherObj.getString(Constants.KEY_ELECTION_PKG))) {
                return pkg;
            }
            return launcherObj.getString(Constants.KEY_ELECTION_PKG);
        } catch (Exception e) {
            DebugLogger.e(TAG, "parse desk top json error");
            return pkg;
        }
    }

    private String getMessageHandlerType(int type) {
        return (String) this.messageHandlerMap.get(Integer.valueOf(type));
    }

    protected boolean canReceiveMessage(int pushType, String destPackageName) {
        boolean flag = true;
        if (pushType == 0) {
            flag = PushPreferencesUtils.getNotificationMessageSwitchStatus(context(), destPackageName);
        } else if (pushType == 1) {
            flag = PushPreferencesUtils.getThroughMessageSwitchStatus(context(), destPackageName);
        }
        DebugLogger.e(TAG, destPackageName + (pushType == 0 ? " canNotificationMessage " : " canThroughMessage ") + flag);
        return flag;
    }

    protected void clearNotifyOption(MessageV3 message) {
        NotifyOption notifyOption = NotifyOption.getNotifyOptionSetting(message);
        if (notifyOption != null) {
            DebugLogger.e(TAG, "delete notifyKey " + notifyOption.getNotifyKey() + " notifyId " + notifyOption.getNotifyId());
            if (TextUtils.isEmpty(notifyOption.getNotifyKey())) {
                NotificationUtils.removeNotifyIdByPackageName(context(), message.getUploadDataPackageName(), notifyOption.getNotifyId());
            } else {
                NotificationUtils.removeNotifyKey(context(), message.getUploadDataPackageName(), notifyOption.getNotifyKey());
            }
        }
    }

    protected void sendArrivedMessage(MessageV3 message) {
        if (!MinSdkChecker.isSupportSetDrawableSmallIcon()) {
            appLogicListener().onNotificationArrived(context(), MzPushMessage.fromMessageV3(message));
        } else if (MzSystemUtils.isRunningProcess(context(), message.getUploadDataPackageName())) {
            DebugLogger.i(TAG, "send notification arrived message to " + message.getUploadDataPackageName());
            Intent arrivedIntent = new Intent();
            arrivedIntent.putExtra(PushConstants.MZ_PUSH_PRIVATE_MESSAGE, message);
            arrivedIntent.putExtra(PushConstants.MZ_PUSH_MESSAGE_METHOD, PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_NOTIFICATION_ARRIVED);
            PlatformMessageSender.sendMessageFromBroadcast(context(), arrivedIntent, PushConstants.MZ_PUSH_ON_MESSAGE_ACTION, message.getUploadDataPackageName());
        }
    }
}
