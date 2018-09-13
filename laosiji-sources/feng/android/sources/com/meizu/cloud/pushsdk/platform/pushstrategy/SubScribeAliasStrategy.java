package com.meizu.cloud.pushsdk.platform.pushstrategy;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.networking.common.ANResponse;
import com.meizu.cloud.pushsdk.networking.error.ANError;
import com.meizu.cloud.pushsdk.platform.PlatformMessageSender;
import com.meizu.cloud.pushsdk.platform.api.PushAPI;
import com.meizu.cloud.pushsdk.platform.message.SubAliasStatus;
import com.meizu.cloud.pushsdk.util.PushPreferencesUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

public class SubScribeAliasStrategy extends Strategy<SubAliasStatus> {
    public static final int CHECK_ALIAS = 2;
    public static final int SUB_ALIAS = 0;
    public static final int UNSUB_ALIAS = 1;
    private String alias;
    private Map<String, Boolean> aliasStatusMap;
    private String pushId;
    private int subAliasType;

    public SubScribeAliasStrategy(Context context, String appId, String appKey, PushAPI pushAPI, ScheduledExecutorService executorService) {
        super(context, appId, appKey, pushAPI, executorService);
        this.aliasStatusMap = new HashMap();
    }

    public SubScribeAliasStrategy(Context context, String appId, String appKey, String pushId, PushAPI pushAPI, ScheduledExecutorService executorService) {
        this(context, appId, appKey, pushAPI, executorService);
        this.pushId = pushId;
    }

    public SubScribeAliasStrategy(Context context, PushAPI pushAPI, ScheduledExecutorService executorService) {
        this(context, null, null, null, pushAPI, executorService);
    }

    public SubScribeAliasStrategy(Context context, PushAPI pushAPI, ScheduledExecutorService executorService, boolean enableRPC) {
        this(context, pushAPI, executorService);
        this.enableRPC = enableRPC;
    }

    public void setSubAliasType(int subAliasType) {
        this.subAliasType = subAliasType;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    protected boolean matchCondition() {
        return (TextUtils.isEmpty(this.appId) || TextUtils.isEmpty(this.appKey) || TextUtils.isEmpty(this.pushId)) ? false : true;
    }

    protected SubAliasStatus feedBackErrorResponse() {
        SubAliasStatus subAliasStatus = new SubAliasStatus();
        subAliasStatus.setCode(Strategy.FEEDBACK_PARAMETER_ERROR_CODE);
        if (TextUtils.isEmpty(this.appId)) {
            subAliasStatus.setMessage("appId not empty");
        } else if (TextUtils.isEmpty(this.appKey)) {
            subAliasStatus.setMessage("appKey not empty");
        } else if (TextUtils.isEmpty(this.pushId)) {
            subAliasStatus.setMessage("pushId not empty");
        }
        return subAliasStatus;
    }

    protected Intent sendRpcRequest() {
        if (this.subAliasType == 2) {
            return null;
        }
        Intent intent = new Intent();
        intent.putExtra(Strategy.APP_ID, this.appId);
        intent.putExtra(Strategy.APP_KEY, this.appKey);
        intent.putExtra(Strategy.STRATEGY_PACKAGE_NANME, this.context.getPackageName());
        intent.putExtra(Strategy.PUSH_ID, this.pushId);
        intent.putExtra(Strategy.STRATEGY_TYPE, strategyType());
        intent.putExtra(Strategy.STRATEGY_CHILD_TYPE, this.subAliasType);
        intent.putExtra(Strategy.STRATEGY_PARAMS, this.alias);
        return intent;
    }

    protected SubAliasStatus netWorkRequest() {
        SubAliasStatus subAliasStatus = new SubAliasStatus();
        subAliasStatus.setPushId(this.pushId);
        subAliasStatus.setMessage("");
        ANResponse<String> anResponse = null;
        switch (this.subAliasType) {
            case 0:
                if (this.alias.equals(localAlias()) && !isSyncAliasStatus()) {
                    subAliasStatus.setCode("200");
                    subAliasStatus.setAlias(this.alias);
                    break;
                }
                changeSyncAliasStatus(true);
                if (isCacheAlias()) {
                    storeAlias(this.alias);
                }
                anResponse = this.pushAPI.subScribeAlias(this.appId, this.appKey, this.pushId, this.alias);
                break;
                break;
            case 1:
                if (TextUtils.isEmpty(localAlias()) && !isSyncAliasStatus()) {
                    subAliasStatus.setCode("200");
                    subAliasStatus.setAlias("");
                    break;
                }
                changeSyncAliasStatus(true);
                if (isCacheAlias()) {
                    storeAlias("");
                }
                anResponse = this.pushAPI.unSubScribeAlias(this.appId, this.appKey, this.pushId, this.alias);
                break;
                break;
            case 2:
                subAliasStatus.setAlias(localAlias());
                subAliasStatus.setCode("200");
                break;
        }
        if (anResponse != null) {
            if (anResponse.isSuccess()) {
                subAliasStatus = new SubAliasStatus((String) anResponse.getResult());
                DebugLogger.e(Strategy.TAG, "network subAliasStatus " + subAliasStatus);
                if ("200".equals(subAliasStatus.getCode())) {
                    changeSyncAliasStatus(false);
                }
            } else {
                ANError error = anResponse.getError();
                if (error.getResponse() != null) {
                    DebugLogger.e(Strategy.TAG, "status code=" + error.getErrorCode() + " data=" + error.getResponse());
                }
                subAliasStatus.setCode(String.valueOf(error.getErrorCode()));
                subAliasStatus.setMessage(error.getErrorBody());
                DebugLogger.e(Strategy.TAG, "subAliasStatus " + subAliasStatus);
            }
        }
        return subAliasStatus;
    }

    protected SubAliasStatus localResponse() {
        switch (this.subAliasType) {
            case 2:
                SubAliasStatus subAliasStatus = new SubAliasStatus();
                subAliasStatus.setCode("200");
                subAliasStatus.setPushId(this.pushId);
                subAliasStatus.setAlias(localAlias());
                subAliasStatus.setMessage("check alias success");
                return subAliasStatus;
            default:
                return null;
        }
    }

    protected void sendReceiverMessage(SubAliasStatus message) {
        PlatformMessageSender.sendSubAlias(this.context, !TextUtils.isEmpty(this.strategyPackageNanme) ? this.strategyPackageNanme : this.context.getPackageName(), message);
    }

    protected int strategyType() {
        return 8;
    }

    private void storeAlias(String alias) {
        PushPreferencesUtils.setAlias(this.context, !TextUtils.isEmpty(this.strategyPackageNanme) ? this.strategyPackageNanme : this.context.getPackageName(), alias);
    }

    private String localAlias() {
        return PushPreferencesUtils.getAlias(this.context, !TextUtils.isEmpty(this.strategyPackageNanme) ? this.strategyPackageNanme : this.context.getPackageName());
    }

    private void changeSyncAliasStatus(boolean flag) {
        this.aliasStatusMap.put(this.strategyPackageNanme + "_" + this.subAliasType, Boolean.valueOf(flag));
    }

    private boolean isSyncAliasStatus() {
        Boolean aliasStatus = (Boolean) this.aliasStatusMap.get(this.strategyPackageNanme + "_" + this.subAliasType);
        return aliasStatus != null ? aliasStatus.booleanValue() : true;
    }

    private boolean isCacheAlias() {
        return !this.isSupportRemoteInvoke && "com.meizu.cloud".equals(this.strategyPackageNanme);
    }
}
