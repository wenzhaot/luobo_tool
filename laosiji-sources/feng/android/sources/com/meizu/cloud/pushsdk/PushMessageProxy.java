package com.meizu.cloud.pushsdk;

import android.content.Context;
import android.content.Intent;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.handler.AbstractAppLogicListener;
import com.meizu.cloud.pushsdk.handler.MessageHandler;
import com.meizu.cloud.pushsdk.handler.MzPushMessage;
import com.meizu.cloud.pushsdk.handler.impl.MessageV2Handler;
import com.meizu.cloud.pushsdk.handler.impl.MessageV3Handler;
import com.meizu.cloud.pushsdk.handler.impl.RegisterMessageHandler;
import com.meizu.cloud.pushsdk.handler.impl.ThroughMessageHandler;
import com.meizu.cloud.pushsdk.handler.impl.UnRegisterMessageHandler;
import com.meizu.cloud.pushsdk.handler.impl.fileupload.LogUploadMessageHandler;
import com.meizu.cloud.pushsdk.handler.impl.notification.NotificationArrivedHandler;
import com.meizu.cloud.pushsdk.handler.impl.notification.NotificationClickMessageHandler;
import com.meizu.cloud.pushsdk.handler.impl.notification.NotificationDeleteMessageHandler;
import com.meizu.cloud.pushsdk.handler.impl.notification.NotificationStateMessageHandler;
import com.meizu.cloud.pushsdk.handler.impl.platform.PushSwitchStatusHandler;
import com.meizu.cloud.pushsdk.handler.impl.platform.ReceiveNotifyMessageHandler;
import com.meizu.cloud.pushsdk.handler.impl.platform.RegisterStatusHandler;
import com.meizu.cloud.pushsdk.handler.impl.platform.SubScribeAliasStatusHandler;
import com.meizu.cloud.pushsdk.handler.impl.platform.SubScribeTagsStatusHandler;
import com.meizu.cloud.pushsdk.handler.impl.platform.UnRegisterStatusHandler;
import com.meizu.cloud.pushsdk.handler.impl.schedule.ScheduleNotificationHandler;
import com.meizu.cloud.pushsdk.notification.PushNotificationBuilder;
import com.meizu.cloud.pushsdk.platform.message.PushSwitchStatus;
import com.meizu.cloud.pushsdk.platform.message.RegisterStatus;
import com.meizu.cloud.pushsdk.platform.message.SubAliasStatus;
import com.meizu.cloud.pushsdk.platform.message.SubTagsStatus;
import com.meizu.cloud.pushsdk.platform.message.UnRegisterStatus;
import com.stub.StubApp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class PushMessageProxy {
    private static final String TAG = "PushMessageProxy";
    static volatile PushMessageProxy singleton = null;
    private Context context;
    private Map<Integer, MessageHandler> managerHashMap;
    private Map<String, AbstractAppLogicListener> messageListenerMap;

    public class DefaultPushMessageListener extends AbstractAppLogicListener {
        public void onMessage(Context context, Intent intent) {
            for (Entry<String, AbstractAppLogicListener> entry : PushMessageProxy.this.messageListenerMap.entrySet()) {
                AbstractAppLogicListener abstractAppLogicListener = (AbstractAppLogicListener) entry.getValue();
                if (abstractAppLogicListener != null) {
                    abstractAppLogicListener.onMessage(context, intent);
                }
            }
        }

        public void onRegister(Context context, String pushId) {
            for (Entry<String, AbstractAppLogicListener> entry : PushMessageProxy.this.messageListenerMap.entrySet()) {
                AbstractAppLogicListener abstractAppLogicListener = (AbstractAppLogicListener) entry.getValue();
                if (abstractAppLogicListener != null) {
                    abstractAppLogicListener.onRegister(context, pushId);
                }
            }
        }

        public void onMessage(Context context, String message) {
            for (Entry<String, AbstractAppLogicListener> entry : PushMessageProxy.this.messageListenerMap.entrySet()) {
                AbstractAppLogicListener abstractAppLogicListener = (AbstractAppLogicListener) entry.getValue();
                if (abstractAppLogicListener != null) {
                    abstractAppLogicListener.onMessage(context, message);
                }
            }
        }

        public void onMessage(Context context, String message, String platformExtra) {
            for (Entry<String, AbstractAppLogicListener> entry : PushMessageProxy.this.messageListenerMap.entrySet()) {
                AbstractAppLogicListener abstractAppLogicListener = (AbstractAppLogicListener) entry.getValue();
                if (abstractAppLogicListener != null) {
                    abstractAppLogicListener.onMessage(context, message, platformExtra);
                }
            }
        }

        public void onUnRegister(Context context, boolean success) {
            for (Entry<String, AbstractAppLogicListener> entry : PushMessageProxy.this.messageListenerMap.entrySet()) {
                AbstractAppLogicListener abstractAppLogicListener = (AbstractAppLogicListener) entry.getValue();
                if (abstractAppLogicListener != null) {
                    abstractAppLogicListener.onUnRegister(context, success);
                }
            }
        }

        public void onUpdateNotificationBuilder(PushNotificationBuilder pushNotificationBuilder) {
            for (Entry<String, AbstractAppLogicListener> entry : PushMessageProxy.this.messageListenerMap.entrySet()) {
                AbstractAppLogicListener abstractAppLogicListener = (AbstractAppLogicListener) entry.getValue();
                if (abstractAppLogicListener != null) {
                    abstractAppLogicListener.onUpdateNotificationBuilder(pushNotificationBuilder);
                }
            }
        }

        public void onPushStatus(Context context, PushSwitchStatus pushSwitchStatus) {
            for (Entry<String, AbstractAppLogicListener> entry : PushMessageProxy.this.messageListenerMap.entrySet()) {
                AbstractAppLogicListener abstractAppLogicListener = (AbstractAppLogicListener) entry.getValue();
                if (abstractAppLogicListener != null) {
                    abstractAppLogicListener.onPushStatus(context, pushSwitchStatus);
                }
            }
        }

        public void onRegisterStatus(Context context, RegisterStatus registerStatus) {
            for (Entry<String, AbstractAppLogicListener> entry : PushMessageProxy.this.messageListenerMap.entrySet()) {
                AbstractAppLogicListener abstractAppLogicListener = (AbstractAppLogicListener) entry.getValue();
                if (abstractAppLogicListener != null) {
                    abstractAppLogicListener.onRegisterStatus(context, registerStatus);
                }
            }
        }

        public void onUnRegisterStatus(Context context, UnRegisterStatus unRegisterStatus) {
            for (Entry<String, AbstractAppLogicListener> entry : PushMessageProxy.this.messageListenerMap.entrySet()) {
                AbstractAppLogicListener abstractAppLogicListener = (AbstractAppLogicListener) entry.getValue();
                if (abstractAppLogicListener != null) {
                    abstractAppLogicListener.onUnRegisterStatus(context, unRegisterStatus);
                }
            }
        }

        public void onSubTagsStatus(Context context, SubTagsStatus subTagsStatus) {
            for (Entry<String, AbstractAppLogicListener> entry : PushMessageProxy.this.messageListenerMap.entrySet()) {
                AbstractAppLogicListener abstractAppLogicListener = (AbstractAppLogicListener) entry.getValue();
                if (abstractAppLogicListener != null) {
                    abstractAppLogicListener.onSubTagsStatus(context, subTagsStatus);
                }
            }
        }

        public void onSubAliasStatus(Context context, SubAliasStatus subAliasStatus) {
            for (Entry<String, AbstractAppLogicListener> entry : PushMessageProxy.this.messageListenerMap.entrySet()) {
                AbstractAppLogicListener abstractAppLogicListener = (AbstractAppLogicListener) entry.getValue();
                if (abstractAppLogicListener != null) {
                    abstractAppLogicListener.onSubAliasStatus(context, subAliasStatus);
                }
            }
        }

        public void onNotificationClicked(Context context, MzPushMessage mzPushMessage) {
            for (Entry<String, AbstractAppLogicListener> entry : PushMessageProxy.this.messageListenerMap.entrySet()) {
                AbstractAppLogicListener abstractAppLogicListener = (AbstractAppLogicListener) entry.getValue();
                if (abstractAppLogicListener != null) {
                    abstractAppLogicListener.onNotificationClicked(context, mzPushMessage);
                }
            }
        }

        public void onNotificationArrived(Context context, MzPushMessage mzPushMessage) {
            for (Entry<String, AbstractAppLogicListener> entry : PushMessageProxy.this.messageListenerMap.entrySet()) {
                AbstractAppLogicListener abstractAppLogicListener = (AbstractAppLogicListener) entry.getValue();
                if (abstractAppLogicListener != null) {
                    abstractAppLogicListener.onNotificationArrived(context, mzPushMessage);
                }
            }
        }

        public void onNotificationDeleted(Context context, MzPushMessage mzPushMessage) {
            for (Entry<String, AbstractAppLogicListener> entry : PushMessageProxy.this.messageListenerMap.entrySet()) {
                AbstractAppLogicListener abstractAppLogicListener = (AbstractAppLogicListener) entry.getValue();
                if (abstractAppLogicListener != null) {
                    abstractAppLogicListener.onNotificationDeleted(context, mzPushMessage);
                }
            }
        }

        public void onNotifyMessageArrived(Context context, String message) {
            for (Entry<String, AbstractAppLogicListener> entry : PushMessageProxy.this.messageListenerMap.entrySet()) {
                AbstractAppLogicListener abstractAppLogicListener = (AbstractAppLogicListener) entry.getValue();
                if (abstractAppLogicListener != null) {
                    abstractAppLogicListener.onNotifyMessageArrived(context, message);
                }
            }
        }
    }

    public PushMessageProxy(Context context) {
        this(context, null);
    }

    public PushMessageProxy(Context context, List<MessageHandler> messageManagerList) {
        this(context, messageManagerList, null);
    }

    public PushMessageProxy(Context context, List<MessageHandler> messageManagerList, AbstractAppLogicListener receiverLisener) {
        this.managerHashMap = new HashMap();
        this.messageListenerMap = null;
        if (context == null) {
            throw new IllegalArgumentException("Context must not be null.");
        }
        this.context = StubApp.getOrigApplicationContext(context.getApplicationContext());
        this.messageListenerMap = new HashMap();
        AbstractAppLogicListener abstractAppLogicListener = new DefaultPushMessageListener();
        if (messageManagerList == null) {
            addHandler(new MessageV3Handler(context, abstractAppLogicListener));
            addHandler(new MessageV2Handler(context, abstractAppLogicListener));
            addHandler(new ThroughMessageHandler(context, abstractAppLogicListener));
            addHandler(new NotificationClickMessageHandler(context, abstractAppLogicListener));
            addHandler(new RegisterMessageHandler(context, abstractAppLogicListener));
            addHandler(new UnRegisterMessageHandler(context, abstractAppLogicListener));
            addHandler(new NotificationDeleteMessageHandler(context, abstractAppLogicListener));
            addHandler(new PushSwitchStatusHandler(context, abstractAppLogicListener));
            addHandler(new RegisterStatusHandler(context, abstractAppLogicListener));
            addHandler(new UnRegisterStatusHandler(context, abstractAppLogicListener));
            addHandler(new SubScribeAliasStatusHandler(context, abstractAppLogicListener));
            addHandler(new SubScribeTagsStatusHandler(context, abstractAppLogicListener));
            addHandler(new ScheduleNotificationHandler(context, abstractAppLogicListener));
            addHandler(new ReceiveNotifyMessageHandler(context, abstractAppLogicListener));
            addHandler(new NotificationStateMessageHandler(context, abstractAppLogicListener));
            addHandler(new LogUploadMessageHandler(context, abstractAppLogicListener));
            addHandler(new NotificationArrivedHandler(context, abstractAppLogicListener));
            return;
        }
        addHandler((List) messageManagerList);
    }

    public static PushMessageProxy with(Context context) {
        if (singleton == null) {
            synchronized (PushMessageProxy.class) {
                if (singleton == null) {
                    DebugLogger.i(TAG, "PushMessageProxy init");
                    singleton = new PushMessageProxy(context);
                }
            }
        }
        return singleton;
    }

    public PushMessageProxy receiverListener(String tag, AbstractAppLogicListener abstractAppLogicListener) {
        this.messageListenerMap.put(tag, abstractAppLogicListener);
        return this;
    }

    public PushMessageProxy unReceiverListener(String tag) {
        this.messageListenerMap.put(tag, null);
        return this;
    }

    public PushMessageProxy addHandler(MessageHandler messageManager) {
        this.managerHashMap.put(Integer.valueOf(messageManager.getProcessorType()), messageManager);
        return this;
    }

    public PushMessageProxy addHandler(List<MessageHandler> messageManagerList) {
        if (messageManagerList == null) {
            throw new IllegalArgumentException("messageManagerList must not be null.");
        }
        for (MessageHandler messageManager : messageManagerList) {
            addHandler(messageManager);
        }
        return this;
    }

    public void processMessage(Intent intent) {
        DebugLogger.e(TAG, "is onMainThread " + isOnMainThread());
        try {
            DebugLogger.i(TAG, "receive action " + intent.getAction() + " method " + intent.getStringExtra(PushConstants.MZ_PUSH_MESSAGE_METHOD));
            if (intent != null) {
                for (Entry<Integer, MessageHandler> iterator : this.managerHashMap.entrySet()) {
                    if (((MessageHandler) iterator.getValue()).sendMessage(intent)) {
                        return;
                    }
                }
            }
        } catch (Exception e) {
            DebugLogger.e(TAG, "processMessage error " + e.getMessage());
        }
    }

    protected boolean isOnMainThread() {
        return Thread.currentThread() == this.context.getMainLooper().getThread();
    }
}
