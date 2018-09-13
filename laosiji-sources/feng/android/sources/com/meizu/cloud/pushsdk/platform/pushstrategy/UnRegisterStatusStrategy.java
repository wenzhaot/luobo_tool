package com.meizu.cloud.pushsdk.platform.pushstrategy;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.networking.common.ANResponse;
import com.meizu.cloud.pushsdk.networking.error.ANError;
import com.meizu.cloud.pushsdk.platform.PlatformMessageSender;
import com.meizu.cloud.pushsdk.platform.api.PushAPI;
import com.meizu.cloud.pushsdk.platform.message.UnRegisterStatus;
import com.meizu.cloud.pushsdk.util.PushPreferencesUtils;
import java.util.concurrent.ScheduledExecutorService;

public class UnRegisterStatusStrategy extends Strategy<UnRegisterStatus> {
    public UnRegisterStatusStrategy(Context context, String appId, String appKey, PushAPI pushAPI, ScheduledExecutorService executorService) {
        super(context, appId, appKey, pushAPI, executorService);
    }

    public UnRegisterStatusStrategy(Context context, PushAPI pushAPI, ScheduledExecutorService executorService) {
        this(context, null, null, pushAPI, executorService);
    }

    public UnRegisterStatusStrategy(Context context, PushAPI pushAPI, ScheduledExecutorService executorService, boolean enableRPC) {
        this(context, pushAPI, executorService);
        this.enableRPC = enableRPC;
    }

    protected boolean matchCondition() {
        return (TextUtils.isEmpty(this.appId) || TextUtils.isEmpty(this.appKey)) ? false : true;
    }

    protected UnRegisterStatus feedBackErrorResponse() {
        UnRegisterStatus registerStatus = new UnRegisterStatus();
        registerStatus.setCode(Strategy.FEEDBACK_PARAMETER_ERROR_CODE);
        if (TextUtils.isEmpty(this.appId)) {
            registerStatus.setMessage("appId not empty");
        } else if (TextUtils.isEmpty(this.appKey)) {
            registerStatus.setMessage("appKey not empty");
        }
        return registerStatus;
    }

    protected Intent sendRpcRequest() {
        Intent intent = new Intent();
        intent.putExtra(Strategy.APP_ID, this.appId);
        intent.putExtra(Strategy.APP_KEY, this.appKey);
        intent.putExtra(Strategy.STRATEGY_PACKAGE_NANME, this.context.getPackageName());
        intent.putExtra(Strategy.STRATEGY_TYPE, strategyType());
        return intent;
    }

    protected UnRegisterStatus netWorkRequest() {
        UnRegisterStatus unRegisterStatus = new UnRegisterStatus();
        if (TextUtils.isEmpty(PushPreferencesUtils.getPushId(this.context, this.strategyPackageNanme))) {
            unRegisterStatus.setCode("200");
            unRegisterStatus.setMessage("already unRegister PushId,dont unRegister frequently");
            unRegisterStatus.setIsUnRegisterSuccess(true);
        } else {
            this.deviceId = getDeviceId();
            ANResponse<String> anResponse = this.pushAPI.unRegister(this.appId, this.appKey, this.deviceId);
            if (anResponse.isSuccess()) {
                unRegisterStatus = new UnRegisterStatus((String) anResponse.getResult());
                DebugLogger.e(Strategy.TAG, "network unRegisterStatus " + unRegisterStatus);
                if ("200".equals(unRegisterStatus.getCode())) {
                    PushPreferencesUtils.putPushId(this.context, "", this.strategyPackageNanme);
                }
            } else {
                ANError error = anResponse.getError();
                if (error.getResponse() != null) {
                    DebugLogger.e(Strategy.TAG, "status code=" + error.getErrorCode() + " data=" + error.getResponse());
                }
                unRegisterStatus.setCode(String.valueOf(error.getErrorCode()));
                unRegisterStatus.setMessage(error.getErrorBody());
                DebugLogger.e(Strategy.TAG, "unRegisterStatus " + unRegisterStatus);
            }
        }
        return unRegisterStatus;
    }

    protected UnRegisterStatus localResponse() {
        return null;
    }

    protected void sendReceiverMessage(UnRegisterStatus message) {
        PlatformMessageSender.sendUnRegisterStatus(this.context, !TextUtils.isEmpty(this.strategyPackageNanme) ? this.strategyPackageNanme : this.context.getPackageName(), message);
    }

    protected int strategyType() {
        return 32;
    }
}
