package com.meizu.cloud.pushsdk.platform.pushstrategy;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.platform.PlatformMessageSender;
import com.meizu.cloud.pushsdk.platform.api.PushAPI;
import com.meizu.cloud.pushsdk.platform.message.BasicPushStatus;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

public abstract class Strategy<T extends BasicPushStatus> {
    public static final String APP_ID = "app_id";
    public static final String APP_KEY = "app_key";
    public static final String DEVICE_ERROR_CODE = "20000";
    public static final String FEEDBACK_PARAMETER_ERROR_CODE = "20001";
    public static final String PUSH_ID = "push_id";
    public static final String STRATEGY_CHILD_TYPE = "strategy_child_type";
    public static final int STRATEGY_ClEAR_NOTIFICATION = 64;
    public static final String STRATEGY_PACKAGE_NANME = "strategy_package_name";
    public static final String STRATEGY_PARAMS = "strategy_params";
    public static final int STRATEGY_REGISTER = 2;
    public static final int STRATEGY_SUBALIAS = 8;
    public static final int STRATEGY_SUBTAGS = 4;
    public static final int STRATEGY_SWITCH = 16;
    public static final String STRATEGY_TYPE = "strategy_type";
    public static final int STRATEGY_UNREGISTER = 32;
    public static final String SUCCESS_CODE = "200";
    public static final String TAG = "Strategy";
    protected String appId;
    protected String appKey;
    protected Context context;
    protected volatile String deviceId;
    protected boolean enableRPC = true;
    protected ScheduledExecutorService executorService;
    protected boolean isSupportRemoteInvoke = true;
    private String managerServicePackageName = null;
    protected PushAPI pushAPI;
    protected String strategyPackageNanme;

    protected abstract T feedBackErrorResponse();

    protected abstract T localResponse();

    protected abstract boolean matchCondition();

    protected abstract T netWorkRequest();

    protected abstract void sendReceiverMessage(T t);

    protected abstract Intent sendRpcRequest();

    protected abstract int strategyType();

    public Strategy(Context context, String appId, String appKey, PushAPI pushAPI, ScheduledExecutorService executorService) {
        this.executorService = executorService;
        this.context = context;
        this.appId = appId;
        this.appKey = appKey;
        this.pushAPI = pushAPI;
    }

    public void setExecutorService(ScheduledExecutorService executorService) {
        this.executorService = executorService;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public void setStrategyPackageNanme(String strategyPackageNanme) {
        this.strategyPackageNanme = strategyPackageNanme;
    }

    public void setSupportRemoteInvoke(boolean isSupportRemoteInvoke) {
        this.isSupportRemoteInvoke = isSupportRemoteInvoke;
    }

    protected boolean supportServiceRpc() {
        return this.enableRPC && this.isSupportRemoteInvoke && !TextUtils.isEmpty(findService(this.context, PushConstants.MZ_PUSH_MANAGER_SERVICE_ACTION));
    }

    private boolean supportAllResponse() {
        return this.enableRPC && !this.context.getPackageName().equals(this.managerServicePackageName);
    }

    private boolean isServiceCode(int code) {
        return code >= 110000 && code <= 200000;
    }

    protected boolean isRegisterStatus() {
        return 2 == strategyType() || 32 == strategyType();
    }

    public boolean process() {
        if (this.executorService == null) {
            return processMainThread();
        }
        this.executorService.execute(new Runnable() {
            public void run() {
                Strategy.this.processMainThread();
            }
        });
        return true;
    }

    public boolean processMainThread() {
        boolean flag = true;
        T basicPushStatus = null;
        if (!matchCondition()) {
            DebugLogger.e(TAG, "Missing required parameters");
            basicPushStatus = feedBackErrorResponse();
            sendReceiverMessage(basicPushStatus);
        } else if (supportServiceRpc()) {
            DebugLogger.i(TAG, "send message to remote service");
            if (!isRegisterStatus()) {
                basicPushStatus = localResponse();
                if (basicPushStatus != null) {
                    DebugLogger.e(TAG, "local response " + basicPushStatus);
                    sendReceiverMessage(basicPushStatus);
                }
            }
            Intent intent = sendRpcRequest();
            if (intent != null) {
                sendIntentMessage(intent);
            }
            PlatformMessageSender.sendMessageFromBroadcast(this.context, new Intent("com.meizu.cloud.pushservice.action.PUSH_SERVICE_START"), null, this.context.getPackageName());
        } else {
            basicPushStatus = netWorkRequest();
            DebugLogger.i(TAG, "real response status " + basicPushStatus);
            if (basicPushStatus != null) {
                if (isRegisterStatus() && DEVICE_ERROR_CODE.equals(basicPushStatus.getCode())) {
                    return true;
                }
                if (supportAllResponse()) {
                    DebugLogger.e(TAG, "response all request in local app");
                    sendReceiverMessage(basicPushStatus);
                } else {
                    String code = basicPushStatus.getCode();
                    if (TextUtils.isEmpty(code)) {
                        code = PushConstants.PUSH_TYPE_NOTIFY;
                    }
                    if ("200".equals(basicPushStatus.getCode())) {
                        sendReceiverMessage(basicPushStatus);
                    }
                    int serviceCode = Integer.valueOf(code).intValue();
                    if (isServiceCode(serviceCode)) {
                        DebugLogger.e(TAG, "service error so notify pushManager invoker code=" + serviceCode + " message " + basicPushStatus.getMessage());
                        sendReceiverMessage(basicPushStatus);
                    }
                }
            }
        }
        if (basicPushStatus != null) {
            DebugLogger.e(TAG, "current status code " + basicPushStatus.getCode());
            flag = !isServerError(basicPushStatus);
        }
        return flag;
    }

    private boolean isServerError(T basicPushStatus) {
        int errorCode = Integer.valueOf(basicPushStatus.getCode()).intValue();
        return (errorCode > 200 && errorCode < 600) || ((errorCode > 1000 && errorCode < 2000) || errorCode == 0);
    }

    protected String getDeviceId() {
        if (TextUtils.isEmpty(this.deviceId)) {
            this.deviceId = MzSystemUtils.getDeviceId(this.context);
            DebugLogger.e(TAG, "deviceId " + this.deviceId);
        }
        return this.deviceId;
    }

    protected String findService(Context paramContext, String action) {
        String componentName = null;
        if (!TextUtils.isEmpty(action)) {
            List<ResolveInfo> localList = paramContext.getPackageManager().queryIntentServices(new Intent(action), 0);
            if (localList != null) {
                for (ResolveInfo info : localList) {
                    if ("com.meizu.cloud".equals(info.serviceInfo.packageName)) {
                        this.managerServicePackageName = info.serviceInfo.packageName;
                        componentName = info.serviceInfo.name;
                        break;
                    }
                }
                if (TextUtils.isEmpty(componentName) && localList.size() > 0) {
                    this.managerServicePackageName = ((ResolveInfo) localList.get(0)).serviceInfo.packageName;
                    componentName = ((ResolveInfo) localList.get(0)).serviceInfo.name;
                }
            }
        }
        DebugLogger.i(TAG, "current process packageName " + this.managerServicePackageName);
        return componentName;
    }

    protected void sendIntentMessage(Intent intent) {
        try {
            intent.setPackage(this.managerServicePackageName);
            intent.setAction(PushConstants.MZ_PUSH_MANAGER_SERVICE_ACTION);
            this.context.startService(intent);
        } catch (Exception e) {
            DebugLogger.e(TAG, "start RemoteService error " + e.getMessage());
        }
    }
}
