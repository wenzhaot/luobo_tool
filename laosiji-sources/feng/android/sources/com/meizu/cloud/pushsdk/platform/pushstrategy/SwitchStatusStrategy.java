package com.meizu.cloud.pushsdk.platform.pushstrategy;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.networking.common.ANResponse;
import com.meizu.cloud.pushsdk.networking.error.ANError;
import com.meizu.cloud.pushsdk.platform.PlatformMessageSender;
import com.meizu.cloud.pushsdk.platform.api.PushAPI;
import com.meizu.cloud.pushsdk.platform.message.PushSwitchStatus;
import com.meizu.cloud.pushsdk.util.PushPreferencesUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

public class SwitchStatusStrategy extends Strategy<PushSwitchStatus> {
    public static final int CHECK_PUSH = 2;
    public static final int SWITCH_ALL = 3;
    public static final int SWITCH_NOTIFICATION = 0;
    public static final int SWITCH_THROUGH_MESSAGE = 1;
    private String pushId;
    private Map<String, Boolean> pushStatusMap;
    private int switchType;
    boolean switcher;

    public SwitchStatusStrategy(Context context, String appId, String appKey, PushAPI pushAPI, ScheduledExecutorService executorService) {
        super(context, appId, appKey, pushAPI, executorService);
        this.switchType = 0;
        this.pushStatusMap = new HashMap();
    }

    public SwitchStatusStrategy(Context context, String appId, String appKey, String pushId, PushAPI pushAPI, ScheduledExecutorService executorService) {
        this(context, appId, appKey, pushAPI, executorService);
        this.pushId = pushId;
    }

    public SwitchStatusStrategy(Context context, PushAPI pushAPI, ScheduledExecutorService executorService) {
        this(context, null, null, null, pushAPI, executorService);
    }

    public SwitchStatusStrategy(Context context, PushAPI pushAPI, ScheduledExecutorService executorService, boolean enableRPC) {
        this(context, pushAPI, executorService);
        this.enableRPC = enableRPC;
    }

    public void setSwitcher(boolean switcher) {
        this.switcher = switcher;
    }

    public void setSwitchType(int switchType) {
        this.switchType = switchType;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    protected boolean matchCondition() {
        return (TextUtils.isEmpty(this.appId) || TextUtils.isEmpty(this.appKey) || TextUtils.isEmpty(this.pushId)) ? false : true;
    }

    protected PushSwitchStatus feedBackErrorResponse() {
        PushSwitchStatus pushSwitchStatus = new PushSwitchStatus();
        pushSwitchStatus.setCode(Strategy.FEEDBACK_PARAMETER_ERROR_CODE);
        if (TextUtils.isEmpty(this.appId)) {
            pushSwitchStatus.setMessage("appId not empty");
        } else if (TextUtils.isEmpty(this.appKey)) {
            pushSwitchStatus.setMessage("appKey not empty");
        } else if (TextUtils.isEmpty(this.pushId)) {
            pushSwitchStatus.setMessage("pushId not empty");
        }
        return pushSwitchStatus;
    }

    protected Intent sendRpcRequest() {
        Intent intent = null;
        if (this.switchType != 2) {
            intent = new Intent();
            intent.putExtra(Strategy.APP_ID, this.appId);
            intent.putExtra(Strategy.APP_KEY, this.appKey);
            intent.putExtra(Strategy.STRATEGY_PACKAGE_NANME, this.context.getPackageName());
            intent.putExtra(Strategy.PUSH_ID, this.pushId);
            intent.putExtra(Strategy.STRATEGY_TYPE, strategyType());
            intent.putExtra(Strategy.STRATEGY_CHILD_TYPE, this.switchType);
            intent.putExtra(Strategy.STRATEGY_PARAMS, this.switcher ? PushConstants.PUSH_TYPE_THROUGH_MESSAGE : PushConstants.PUSH_TYPE_NOTIFY);
        }
        return intent;
    }

    protected PushSwitchStatus netWorkRequest() {
        PushSwitchStatus pushSwitchStatus = new PushSwitchStatus();
        pushSwitchStatus.setPushId(this.pushId);
        pushSwitchStatus.setCode("200");
        pushSwitchStatus.setMessage("");
        ANResponse<String> anResponse = null;
        switch (this.switchType) {
            case 0:
                if (notificationSwitch() == this.switcher && !isSyncPushStatus()) {
                    pushSwitchStatus.setSwitchNotificationMessage(this.switcher);
                    pushSwitchStatus.setSwitchThroughMessage(throughMessageSwitch());
                    break;
                }
                changeSyncPushStatus(true);
                switchNotification(this.switcher);
                anResponse = this.pushAPI.switchPush(this.appId, this.appKey, this.pushId, this.switchType, this.switcher);
                break;
                break;
            case 1:
                if (throughMessageSwitch() == this.switcher && !isSyncPushStatus()) {
                    pushSwitchStatus.setSwitchNotificationMessage(notificationSwitch());
                    pushSwitchStatus.setSwitchThroughMessage(this.switcher);
                    break;
                }
                changeSyncPushStatus(true);
                switchThroughMessage(this.switcher);
                anResponse = this.pushAPI.switchPush(this.appId, this.appKey, this.pushId, this.switchType, this.switcher);
                break;
                break;
            case 2:
                pushSwitchStatus.setSwitchNotificationMessage(notificationSwitch());
                pushSwitchStatus.setSwitchThroughMessage(throughMessageSwitch());
                break;
            case 3:
                if (notificationSwitch() != this.switcher || throughMessageSwitch() != this.switcher || isSyncPushStatus()) {
                    changeSyncPushStatus(true);
                    switchAll(this.switcher);
                    anResponse = this.pushAPI.switchPush(this.appId, this.appKey, this.pushId, this.switcher);
                    break;
                }
                pushSwitchStatus.setSwitchNotificationMessage(this.switcher);
                pushSwitchStatus.setSwitchThroughMessage(this.switcher);
                break;
                break;
        }
        if (anResponse != null) {
            if (anResponse.isSuccess()) {
                pushSwitchStatus = new PushSwitchStatus((String) anResponse.getResult());
                DebugLogger.e(Strategy.TAG, "network pushSwitchStatus " + pushSwitchStatus);
                if ("200".equals(pushSwitchStatus.getCode())) {
                    changeSyncPushStatus(false);
                }
            } else {
                ANError error = anResponse.getError();
                if (error.getResponse() != null) {
                    DebugLogger.e(Strategy.TAG, "status code=" + error.getErrorCode() + " data=" + error.getResponse());
                }
                pushSwitchStatus.setCode(String.valueOf(error.getErrorCode()));
                pushSwitchStatus.setMessage(error.getErrorBody());
                DebugLogger.e(Strategy.TAG, "pushSwitchStatus " + pushSwitchStatus);
            }
        }
        DebugLogger.e(Strategy.TAG, "enableRPC " + this.enableRPC + " isSupportRemoteInvoke " + this.isSupportRemoteInvoke);
        if (this.enableRPC && !this.isSupportRemoteInvoke) {
            switch (this.switchType) {
                case 0:
                case 1:
                    PlatformMessageSender.switchPushMessageSetting(this.context, this.switchType, this.switcher, this.strategyPackageNanme);
                    break;
                case 3:
                    PlatformMessageSender.switchPushMessageSetting(this.context, 0, this.switcher, this.strategyPackageNanme);
                    PlatformMessageSender.switchPushMessageSetting(this.context, 1, this.switcher, this.strategyPackageNanme);
                    break;
            }
        }
        return pushSwitchStatus;
    }

    protected PushSwitchStatus localResponse() {
        switch (this.switchType) {
            case 0:
                switchNotification(this.switcher);
                return null;
            case 1:
                switchThroughMessage(this.switcher);
                return null;
            case 2:
                PushSwitchStatus pushSwitchStatus = new PushSwitchStatus();
                pushSwitchStatus.setPushId(this.pushId);
                pushSwitchStatus.setCode("200");
                pushSwitchStatus.setMessage("");
                pushSwitchStatus.setSwitchNotificationMessage(notificationSwitch());
                pushSwitchStatus.setSwitchThroughMessage(throughMessageSwitch());
                return pushSwitchStatus;
            case 3:
                switchAll(this.switcher);
                return null;
            default:
                return null;
        }
    }

    protected void sendReceiverMessage(PushSwitchStatus message) {
        PlatformMessageSender.sendPushStatus(this.context, !TextUtils.isEmpty(this.strategyPackageNanme) ? this.strategyPackageNanme : this.context.getPackageName(), message);
    }

    protected int strategyType() {
        return 16;
    }

    private void switchNotification(boolean switcher) {
        PushPreferencesUtils.setNotificationMessageSwitchStatus(this.context, !TextUtils.isEmpty(this.strategyPackageNanme) ? this.strategyPackageNanme : this.context.getPackageName(), switcher);
    }

    private void switchThroughMessage(boolean switcher) {
        PushPreferencesUtils.setThroughMessageSwitchStatus(this.context, !TextUtils.isEmpty(this.strategyPackageNanme) ? this.strategyPackageNanme : this.context.getPackageName(), switcher);
    }

    private void switchAll(boolean switcher) {
        PushPreferencesUtils.setNotificationMessageSwitchStatus(this.context, !TextUtils.isEmpty(this.strategyPackageNanme) ? this.strategyPackageNanme : this.context.getPackageName(), switcher);
        PushPreferencesUtils.setThroughMessageSwitchStatus(this.context, !TextUtils.isEmpty(this.strategyPackageNanme) ? this.strategyPackageNanme : this.context.getPackageName(), switcher);
    }

    private boolean notificationSwitch() {
        return PushPreferencesUtils.getNotificationMessageSwitchStatus(this.context, !TextUtils.isEmpty(this.strategyPackageNanme) ? this.strategyPackageNanme : this.context.getPackageName());
    }

    private boolean throughMessageSwitch() {
        return PushPreferencesUtils.getThroughMessageSwitchStatus(this.context, !TextUtils.isEmpty(this.strategyPackageNanme) ? this.strategyPackageNanme : this.context.getPackageName());
    }

    private void changeSyncPushStatus(boolean flag) {
        this.pushStatusMap.put(this.strategyPackageNanme + "_" + this.switchType, Boolean.valueOf(flag));
    }

    private boolean isSyncPushStatus() {
        Boolean pushStatus = (Boolean) this.pushStatusMap.get(this.strategyPackageNanme + "_" + this.switchType);
        return pushStatus != null ? pushStatus.booleanValue() : true;
    }
}
