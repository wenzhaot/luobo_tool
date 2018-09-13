package com.meizu.cloud.pushsdk.platform.pushstrategy;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.notification.util.NotificationUtils;
import com.meizu.cloud.pushsdk.platform.api.PushAPI;
import com.meizu.cloud.pushsdk.platform.message.BasicPushStatus;
import com.meizu.cloud.pushsdk.util.MinSdkChecker;
import java.util.concurrent.ScheduledExecutorService;

public class NotificationClearStrategy extends Strategy {
    public static final int CLEAR_ALL = 0;
    public static final int CLEAR_NOTIFY_ID = 1;
    public static final int CLEAR_NOTIFY_KEY = 2;
    private int clearType;
    private int notifyId;
    private String notifyKey;

    public NotificationClearStrategy(Context context, ScheduledExecutorService executorService, boolean enableRpc) {
        this(context, null, null, null, executorService);
        this.enableRPC = enableRpc;
    }

    public NotificationClearStrategy(Context context, String appId, String appKey, PushAPI pushAPI, ScheduledExecutorService executorService) {
        super(context, appId, appKey, pushAPI, executorService);
        this.isSupportRemoteInvoke = MinSdkChecker.isSupportSetDrawableSmallIcon();
    }

    public void setNotifyId(int notifyId) {
        this.notifyId = notifyId;
    }

    public void setClearType(int clearType) {
        this.clearType = clearType;
    }

    public void setNotifyKey(String notifyKey) {
        this.notifyKey = notifyKey;
    }

    protected boolean matchCondition() {
        if (this.clearType == 0) {
            return true;
        }
        if (this.notifyId != 0 && this.clearType == 1) {
            return true;
        }
        if (this.clearType != 2 || TextUtils.isEmpty(this.notifyKey)) {
            return false;
        }
        return true;
    }

    protected BasicPushStatus feedBackErrorResponse() {
        return null;
    }

    protected Intent sendRpcRequest() {
        Intent intent = new Intent();
        intent.putExtra(Strategy.STRATEGY_PACKAGE_NANME, this.context.getPackageName());
        intent.putExtra(Strategy.STRATEGY_TYPE, strategyType());
        intent.putExtra(Strategy.STRATEGY_CHILD_TYPE, this.clearType);
        if (this.clearType == 2) {
            intent.putExtra(Strategy.STRATEGY_PARAMS, this.notifyKey);
        } else if (this.clearType == 1) {
            intent.putExtra(Strategy.STRATEGY_PARAMS, "" + this.notifyId);
        }
        return intent;
    }

    protected BasicPushStatus netWorkRequest() {
        switch (this.clearType) {
            case 0:
                if (!MinSdkChecker.isSupportSetDrawableSmallIcon()) {
                    DebugLogger.e(Strategy.TAG, "android 6.0 blow so cancel all by context");
                    NotificationUtils.clearAllNotification(this.context);
                }
                NotificationUtils.clearNotification(this.context, this.strategyPackageNanme);
                break;
            case 1:
                NotificationUtils.clearNotification(this.context, this.strategyPackageNanme, this.notifyId);
                break;
            case 2:
                NotificationUtils.removeNotifyKey(this.context, this.strategyPackageNanme, this.notifyKey);
                break;
        }
        return null;
    }

    protected BasicPushStatus localResponse() {
        return null;
    }

    protected void sendReceiverMessage(BasicPushStatus message) {
    }

    protected int strategyType() {
        return 64;
    }
}
