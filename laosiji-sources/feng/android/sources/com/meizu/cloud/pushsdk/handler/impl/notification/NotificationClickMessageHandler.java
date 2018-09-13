package com.meizu.cloud.pushsdk.handler.impl.notification;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.handler.AbstractAppLogicListener;
import com.meizu.cloud.pushsdk.handler.MessageV3;
import com.meizu.cloud.pushsdk.handler.MzPushMessage;
import com.meizu.cloud.pushsdk.handler.impl.AbstractMessageHandler;
import com.meizu.cloud.pushsdk.handler.impl.model.PlatformMessage;
import com.meizu.cloud.pushsdk.notification.PushNotification;
import com.meizu.cloud.pushsdk.notification.model.NotifyOption;
import com.meizu.cloud.pushsdk.platform.api.PushPlatformManager;
import com.meizu.cloud.pushsdk.util.MinSdkChecker;
import com.meizu.cloud.pushsdk.util.PushPreferencesUtils;
import com.meizu.cloud.pushsdk.util.UxIPUtils;
import com.umeng.socialize.net.dplus.CommonNetImpl;
import java.util.Map.Entry;

public class NotificationClickMessageHandler extends AbstractMessageHandler<MessageV3> {
    public NotificationClickMessageHandler(Context context, AbstractAppLogicListener abstractAppLogicListener) {
        super(context, abstractAppLogicListener);
    }

    /* JADX WARNING: Failed to extract finally block: empty outs */
    protected com.meizu.cloud.pushsdk.handler.MessageV3 getMessage(android.content.Intent r9) {
        /*
        r8 = this;
        r3 = 0;
        r4 = "AbstractMessageHandler";
        r5 = "parse message V3";
        com.meizu.cloud.pushinternal.DebugLogger.e(r4, r5);	 Catch:{ Exception -> 0x003a }
        r4 = "pushMessage";
        r4 = r9.getParcelableExtra(r4);	 Catch:{ Exception -> 0x003a }
        r0 = r4;
        r0 = (com.meizu.cloud.pushsdk.handler.MessageV3) r0;	 Catch:{ Exception -> 0x003a }
        r3 = r0;
        if (r3 != 0) goto L_0x0039;
    L_0x0017:
        r4 = "AbstractMessageHandler";
        r5 = "parse MessageV2 to MessageV3";
        com.meizu.cloud.pushinternal.DebugLogger.e(r4, r5);
        r4 = "pushMessage";
        r2 = r9.getSerializableExtra(r4);
        r2 = (com.meizu.cloud.pushsdk.notification.MPushMessage) r2;
        r4 = r8.getPushServiceDefaultPackageName(r9);
        r5 = r8.getDeviceId(r9);
        r6 = r2.getTaskId();
        r3 = com.meizu.cloud.pushsdk.handler.MessageV3.parse(r4, r5, r6, r2);
    L_0x0039:
        return r3;
    L_0x003a:
        r1 = move-exception;
        r4 = "AbstractMessageHandler";
        r5 = "cannot get messageV3";
        com.meizu.cloud.pushinternal.DebugLogger.e(r4, r5);	 Catch:{ all -> 0x0069 }
        if (r3 != 0) goto L_0x0039;
    L_0x0046:
        r4 = "AbstractMessageHandler";
        r5 = "parse MessageV2 to MessageV3";
        com.meizu.cloud.pushinternal.DebugLogger.e(r4, r5);
        r4 = "pushMessage";
        r2 = r9.getSerializableExtra(r4);
        r2 = (com.meizu.cloud.pushsdk.notification.MPushMessage) r2;
        r4 = r8.getPushServiceDefaultPackageName(r9);
        r5 = r8.getDeviceId(r9);
        r6 = r2.getTaskId();
        r3 = com.meizu.cloud.pushsdk.handler.MessageV3.parse(r4, r5, r6, r2);
        goto L_0x0039;
    L_0x0069:
        r4 = move-exception;
        if (r3 != 0) goto L_0x008e;
    L_0x006c:
        r5 = "AbstractMessageHandler";
        r6 = "parse MessageV2 to MessageV3";
        com.meizu.cloud.pushinternal.DebugLogger.e(r5, r6);
        r5 = "pushMessage";
        r2 = r9.getSerializableExtra(r5);
        r2 = (com.meizu.cloud.pushsdk.notification.MPushMessage) r2;
        r5 = r8.getPushServiceDefaultPackageName(r9);
        r6 = r8.getDeviceId(r9);
        r7 = r2.getTaskId();
        r3 = com.meizu.cloud.pushsdk.handler.MessageV3.parse(r5, r6, r7, r2);
    L_0x008e:
        throw r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.cloud.pushsdk.handler.impl.notification.NotificationClickMessageHandler.getMessage(android.content.Intent):com.meizu.cloud.pushsdk.handler.MessageV3");
    }

    protected void unsafeSend(MessageV3 message, PushNotification pushNotification) {
        PushPreferencesUtils.putDiscardNotificationIdByPackageName(context(), message.getPackageName(), 0);
        Intent privateIntent = buildIntent(context(), message);
        if (privateIntent != null) {
            privateIntent.addFlags(CommonNetImpl.FLAG_AUTH);
            try {
                context().startActivity(privateIntent);
            } catch (Exception e) {
                DebugLogger.e("AbstractMessageHandler", "Click message StartActivity error " + e.getMessage());
            }
        }
        if (!(TextUtils.isEmpty(message.getTitle()) || TextUtils.isEmpty(message.getContent()) || appLogicListener() == null)) {
            appLogicListener().onNotificationClicked(context(), MzPushMessage.fromMessageV3(message));
        }
        if (MinSdkChecker.isSupportSetDrawableSmallIcon()) {
            NotifyOption notifyOption = NotifyOption.getNotifyOptionSetting(message);
            if (notifyOption != null) {
                DebugLogger.e("AbstractMessageHandler", "delete notifyId " + notifyOption.getNotifyId() + " notifyKey " + notifyOption.getNotifyKey());
                if (TextUtils.isEmpty(notifyOption.getNotifyKey())) {
                    PushPlatformManager.getInstance(context()).clearNotification(notifyOption.getNotifyId(), message.getUploadDataPackageName());
                    return;
                } else {
                    PushPlatformManager.getInstance(context()).clearNotifyKey(message.getUploadDataPackageName(), notifyOption.getNotifyKey());
                    return;
                }
            }
            return;
        }
        clearNotifyOption(message);
    }

    public boolean messageMatch(Intent intent) {
        DebugLogger.i("AbstractMessageHandler", "start NotificationClickMessageHandler match");
        return PushConstants.MZ_PUSH_ON_MESSAGE_ACTION.equals(intent.getAction()) && PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_PRIVATE.equals(getIntentMethod(intent));
    }

    public int getProcessorType() {
        return 64;
    }

    protected void onBeforeEvent(MessageV3 message) {
        UxIPUtils.onClickPushMessageEvent(context(), message.getUploadDataPackageName(), TextUtils.isEmpty(message.getDeviceId()) ? getDeviceId(null) : message.getDeviceId(), message.getTaskId(), message.getSeqId(), message.getPushTimestamp());
    }

    private Intent buildIntent(Context context, MessageV3 messageV3) {
        Intent intent = null;
        String openClassName = messageV3.getUriPackageName();
        if (TextUtils.isEmpty(openClassName)) {
            openClassName = messageV3.getUploadDataPackageName();
        }
        DebugLogger.i("AbstractMessageHandler", "openClassName is " + openClassName);
        if (messageV3.getClickType() == 0) {
            intent = context.getPackageManager().getLaunchIntentForPackage(openClassName);
            if (!(intent == null || messageV3.getParamsMap() == null)) {
                for (Entry<String, String> paramsEntry : messageV3.getParamsMap().entrySet()) {
                    DebugLogger.i("AbstractMessageHandler", " launcher activity key " + ((String) paramsEntry.getKey()) + " value " + ((String) paramsEntry.getValue()));
                    if (!(TextUtils.isEmpty((CharSequence) paramsEntry.getKey()) || TextUtils.isEmpty((CharSequence) paramsEntry.getValue()))) {
                        intent.putExtra((String) paramsEntry.getKey(), (String) paramsEntry.getValue());
                    }
                }
            }
        } else if (1 == messageV3.getClickType()) {
            intent = new Intent();
            if (messageV3.getParamsMap() != null) {
                for (Entry<String, String> paramsEntry2 : messageV3.getParamsMap().entrySet()) {
                    DebugLogger.i("AbstractMessageHandler", " key " + ((String) paramsEntry2.getKey()) + " value " + ((String) paramsEntry2.getValue()));
                    if (!(TextUtils.isEmpty((CharSequence) paramsEntry2.getKey()) || TextUtils.isEmpty((CharSequence) paramsEntry2.getValue()))) {
                        intent.putExtra((String) paramsEntry2.getKey(), (String) paramsEntry2.getValue());
                    }
                }
            }
            intent.setClassName(openClassName, messageV3.getActivity());
            DebugLogger.i("AbstractMessageHandler", intent.toUri(1));
        } else if (2 == messageV3.getClickType()) {
            intent = new Intent("android.intent.action.VIEW", Uri.parse(messageV3.getWebUrl()));
            String uriPackageName = messageV3.getUriPackageName();
            if (!TextUtils.isEmpty(uriPackageName)) {
                intent.setPackage(uriPackageName);
                DebugLogger.i("AbstractMessageHandler", "set uri package " + uriPackageName);
            }
        } else if (3 == messageV3.getClickType()) {
            DebugLogger.i("AbstractMessageHandler", "CLICK_TYPE_SELF_DEFINE_ACTION");
        }
        if (intent != null) {
            intent.putExtra(PushConstants.MZ_PUSH_PLATFROM_EXTRA, PlatformMessage.builder().taskId(messageV3.getTaskId()).build().toJson());
        }
        return intent;
    }
}
