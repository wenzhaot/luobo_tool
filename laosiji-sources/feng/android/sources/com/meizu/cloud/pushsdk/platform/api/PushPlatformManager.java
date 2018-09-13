package com.meizu.cloud.pushsdk.platform.api;

import android.content.Context;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.networking.common.ANResponse;
import com.meizu.cloud.pushsdk.networking.error.ANError;
import com.meizu.cloud.pushsdk.networking.http.Response;
import com.meizu.cloud.pushsdk.networking.interfaces.OkHttpResponseAndStringRequestListener;
import com.meizu.cloud.pushsdk.platform.message.StrategyMessage;
import com.meizu.cloud.pushsdk.platform.pushstrategy.NotificationClearStrategy;
import com.meizu.cloud.pushsdk.platform.pushstrategy.RegisterStatusStrategy;
import com.meizu.cloud.pushsdk.platform.pushstrategy.SubScribeAliasStrategy;
import com.meizu.cloud.pushsdk.platform.pushstrategy.SubScribeTagStrategy;
import com.meizu.cloud.pushsdk.platform.pushstrategy.SwitchStatusStrategy;
import com.meizu.cloud.pushsdk.platform.pushstrategy.UnRegisterStatusStrategy;
import com.meizu.cloud.pushsdk.pushtracer.emitter.classic.Executor;
import com.meizu.cloud.pushsdk.util.PushPreferencesUtils;
import com.stub.StubApp;
import java.io.File;
import java.util.concurrent.ScheduledExecutorService;

public class PushPlatformManager {
    private static final String TAG = "PushPlatformManager";
    private static PushPlatformManager mInstance;
    private ScheduledExecutorService executorService;
    private Context mContext;
    private NotificationClearStrategy notificationClearStrategy;
    private PushAPI pushAPI;
    private RegisterStatusStrategy registerStatusStrategy;
    private SubScribeAliasStrategy subScribeAliasStrategy;
    private SubScribeTagStrategy subScribeTagStrategy;
    private SwitchStatusStrategy switchStatusStrategy;
    private UnRegisterStatusStrategy unRegisterStatusStrategy;

    public PushPlatformManager(Context context, boolean isAsync) {
        this(context, isAsync, true);
    }

    public PushPlatformManager(Context context, boolean isAsync, boolean enableRPC) {
        this.mContext = StubApp.getOrigApplicationContext(context.getApplicationContext());
        this.pushAPI = new PushAPI(this.mContext);
        if (isAsync) {
            this.executorService = (ScheduledExecutorService) Executor.getExecutor();
        }
        this.registerStatusStrategy = new RegisterStatusStrategy(this.mContext, this.pushAPI, this.executorService, enableRPC);
        this.unRegisterStatusStrategy = new UnRegisterStatusStrategy(this.mContext, this.pushAPI, this.executorService, enableRPC);
        this.switchStatusStrategy = new SwitchStatusStrategy(this.mContext, this.pushAPI, this.executorService, enableRPC);
        this.subScribeTagStrategy = new SubScribeTagStrategy(this.mContext, this.pushAPI, this.executorService, enableRPC);
        this.subScribeAliasStrategy = new SubScribeAliasStrategy(this.mContext, this.pushAPI, this.executorService, enableRPC);
        this.notificationClearStrategy = new NotificationClearStrategy(this.mContext, this.executorService, enableRPC);
    }

    public static PushPlatformManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (PushPlatformManager.class) {
                if (mInstance == null) {
                    mInstance = new PushPlatformManager(context, true);
                }
            }
        }
        return mInstance;
    }

    public void enableRemoteInvoker(boolean isRemoteInvoker) {
        this.registerStatusStrategy.setSupportRemoteInvoke(isRemoteInvoker);
        this.unRegisterStatusStrategy.setSupportRemoteInvoke(isRemoteInvoker);
        this.switchStatusStrategy.setSupportRemoteInvoke(isRemoteInvoker);
        this.subScribeAliasStrategy.setSupportRemoteInvoke(isRemoteInvoker);
        this.subScribeTagStrategy.setSupportRemoteInvoke(isRemoteInvoker);
    }

    public boolean dispatcherStrategyMessage(StrategyMessage strategyMessage) {
        boolean z = false;
        if (strategyMessage == null) {
            return true;
        }
        switch (strategyMessage.getStrategyType()) {
            case 2:
                return register(strategyMessage.getAppId(), strategyMessage.getAppKey(), strategyMessage.getPackageName());
            case 4:
                if (strategyMessage.getStrategyChildType() == 0) {
                    return subScribeTags(strategyMessage.getAppId(), strategyMessage.getAppKey(), strategyMessage.getPackageName(), strategyMessage.getPushId(), strategyMessage.getParams());
                } else if (3 == strategyMessage.getStrategyChildType()) {
                    return checkSubScribeTags(strategyMessage.getAppId(), strategyMessage.getAppKey(), strategyMessage.getPackageName(), strategyMessage.getPushId());
                } else {
                    if (1 == strategyMessage.getStrategyChildType()) {
                        return unSubScribeTags(strategyMessage.getAppId(), strategyMessage.getAppKey(), strategyMessage.getPackageName(), strategyMessage.getPushId(), strategyMessage.getParams());
                    } else if (2 == strategyMessage.getStrategyChildType()) {
                        return unSubScribeAllTags(strategyMessage.getAppId(), strategyMessage.getAppKey(), strategyMessage.getPackageName(), strategyMessage.getPushId());
                    } else {
                        return true;
                    }
                }
            case 8:
                if (strategyMessage.getStrategyChildType() == 0) {
                    return subScribeAlias(strategyMessage.getAppId(), strategyMessage.getAppKey(), strategyMessage.getPackageName(), strategyMessage.getPushId(), strategyMessage.getParams());
                } else if (1 == strategyMessage.getStrategyChildType()) {
                    return unSubScribeAlias(strategyMessage.getAppId(), strategyMessage.getAppKey(), strategyMessage.getPackageName(), strategyMessage.getPushId(), strategyMessage.getParams());
                } else if (2 == strategyMessage.getStrategyChildType()) {
                    return checkSubScribeAlias(strategyMessage.getAppId(), strategyMessage.getAppKey(), strategyMessage.getPackageName(), strategyMessage.getPushId());
                } else {
                    return true;
                }
            case 16:
                String appId;
                String appKey;
                String packageName;
                String pushId;
                boolean z2;
                if (strategyMessage.getStrategyChildType() == 0) {
                    appId = strategyMessage.getAppId();
                    appKey = strategyMessage.getAppKey();
                    packageName = strategyMessage.getPackageName();
                    pushId = strategyMessage.getPushId();
                    if (PushConstants.PUSH_TYPE_THROUGH_MESSAGE.equals(strategyMessage.getParams())) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    return switchPush(appId, appKey, packageName, pushId, 0, z2);
                } else if (1 == strategyMessage.getStrategyChildType()) {
                    appId = strategyMessage.getAppId();
                    appKey = strategyMessage.getAppKey();
                    packageName = strategyMessage.getPackageName();
                    pushId = strategyMessage.getPushId();
                    if (PushConstants.PUSH_TYPE_THROUGH_MESSAGE.equals(strategyMessage.getParams())) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    return switchPush(appId, appKey, packageName, pushId, 1, z2);
                } else if (3 == strategyMessage.getStrategyChildType()) {
                    appId = strategyMessage.getAppId();
                    appKey = strategyMessage.getAppKey();
                    packageName = strategyMessage.getPackageName();
                    pushId = strategyMessage.getPushId();
                    if (PushConstants.PUSH_TYPE_THROUGH_MESSAGE.equals(strategyMessage.getParams())) {
                        z = true;
                    }
                    return switchPush(appId, appKey, packageName, pushId, z);
                } else if (2 == strategyMessage.getStrategyChildType()) {
                    return checkPush(strategyMessage.getAppId(), strategyMessage.getAppKey(), strategyMessage.getPackageName(), strategyMessage.getPushId());
                } else {
                    return true;
                }
            case 32:
                return unRegister(strategyMessage.getAppId(), strategyMessage.getAppKey(), strategyMessage.getPackageName());
            case 64:
                if (strategyMessage.getStrategyChildType() == 0) {
                    return clearAllNotification(strategyMessage.getPackageName());
                }
                if (1 == strategyMessage.getStrategyChildType()) {
                    return clearNotification(Integer.valueOf(strategyMessage.getParams()).intValue(), strategyMessage.getPackageName());
                }
                if (2 == strategyMessage.getStrategyChildType()) {
                    return clearNotifyKey(strategyMessage.getPackageName(), strategyMessage.getParams());
                }
                return true;
            default:
                return true;
        }
    }

    public boolean register(String appId, String appKey, String packageName) {
        this.registerStatusStrategy.setAppId(appId);
        this.registerStatusStrategy.setAppKey(appKey);
        this.registerStatusStrategy.setStrategyPackageNanme(packageName);
        return this.registerStatusStrategy.process();
    }

    public boolean unRegister(String appId, String appKey, String packageName) {
        this.unRegisterStatusStrategy.setAppId(appId);
        this.unRegisterStatusStrategy.setAppKey(appKey);
        this.unRegisterStatusStrategy.setStrategyPackageNanme(packageName);
        return this.unRegisterStatusStrategy.process();
    }

    public void unRegisterAdvance(final String packageName, String deviceId) {
        this.pushAPI.unRegister(packageName, deviceId, new OkHttpResponseAndStringRequestListener() {
            public void onResponse(Response okHttpResponse, String response) {
                PushPreferencesUtils.putPushId(PushPlatformManager.this.mContext, "", PushPlatformManager.this.mContext.getPackageName());
                DebugLogger.e(PushPlatformManager.TAG, "unregisetr advance pakcage " + packageName + " result " + response);
            }

            public void onError(ANError anError) {
                DebugLogger.e(PushPlatformManager.TAG, "unregisetr advance pakcage " + packageName + " error " + anError.getErrorBody());
            }
        });
    }

    public boolean checkPush(String appId, String appKey, String packageName, String pushId) {
        this.switchStatusStrategy.setAppId(appId);
        this.switchStatusStrategy.setAppKey(appKey);
        this.switchStatusStrategy.setStrategyPackageNanme(packageName);
        this.switchStatusStrategy.setPushId(pushId);
        this.switchStatusStrategy.setSwitchType(2);
        return this.switchStatusStrategy.process();
    }

    public boolean switchPush(String appId, String appKey, String packageName, String pushId, int msgType, boolean switcher) {
        this.switchStatusStrategy.setAppId(appId);
        this.switchStatusStrategy.setAppKey(appKey);
        this.switchStatusStrategy.setStrategyPackageNanme(packageName);
        this.switchStatusStrategy.setPushId(pushId);
        this.switchStatusStrategy.setSwitchType(msgType);
        this.switchStatusStrategy.setSwitcher(switcher);
        return this.switchStatusStrategy.process();
    }

    public boolean switchPush(String appId, String appKey, String packageName, String pushId, boolean switcher) {
        this.switchStatusStrategy.setAppId(appId);
        this.switchStatusStrategy.setAppKey(appKey);
        this.switchStatusStrategy.setStrategyPackageNanme(packageName);
        this.switchStatusStrategy.setPushId(pushId);
        this.switchStatusStrategy.setSwitchType(3);
        this.switchStatusStrategy.setSwitcher(switcher);
        return this.switchStatusStrategy.process();
    }

    public boolean subScribeTags(String appId, String appKey, String packageName, String pushId, String tags) {
        this.subScribeTagStrategy.setAppId(appId);
        this.subScribeTagStrategy.setAppKey(appKey);
        this.subScribeTagStrategy.setStrategyPackageNanme(packageName);
        this.subScribeTagStrategy.setPushId(pushId);
        this.subScribeTagStrategy.setSubTagType(0);
        this.subScribeTagStrategy.setSubTags(tags);
        return this.subScribeTagStrategy.process();
    }

    public boolean unSubScribeTags(String appId, String appKey, String packageName, String pushId, String tags) {
        this.subScribeTagStrategy.setAppId(appId);
        this.subScribeTagStrategy.setAppKey(appKey);
        this.subScribeTagStrategy.setStrategyPackageNanme(packageName);
        this.subScribeTagStrategy.setPushId(pushId);
        this.subScribeTagStrategy.setSubTagType(1);
        this.subScribeTagStrategy.setSubTags(tags);
        return this.subScribeTagStrategy.process();
    }

    public boolean unSubScribeAllTags(String appId, String appKey, String packageName, String pushId) {
        this.subScribeTagStrategy.setAppId(appId);
        this.subScribeTagStrategy.setAppKey(appKey);
        this.subScribeTagStrategy.setStrategyPackageNanme(packageName);
        this.subScribeTagStrategy.setPushId(pushId);
        this.subScribeTagStrategy.setSubTagType(2);
        return this.subScribeTagStrategy.process();
    }

    public boolean checkSubScribeTags(String appId, String appKey, String packageName, String pushId) {
        this.subScribeTagStrategy.setAppId(appId);
        this.subScribeTagStrategy.setAppKey(appKey);
        this.subScribeTagStrategy.setStrategyPackageNanme(packageName);
        this.subScribeTagStrategy.setPushId(pushId);
        this.subScribeTagStrategy.setSubTagType(3);
        return this.subScribeTagStrategy.process();
    }

    public boolean subScribeAlias(String appId, String appKey, String packageName, String pushId, String alias) {
        this.subScribeAliasStrategy.setAppId(appId);
        this.subScribeAliasStrategy.setAppKey(appKey);
        this.subScribeAliasStrategy.setStrategyPackageNanme(packageName);
        this.subScribeAliasStrategy.setPushId(pushId);
        this.subScribeAliasStrategy.setSubAliasType(0);
        this.subScribeAliasStrategy.setAlias(alias);
        return this.subScribeAliasStrategy.process();
    }

    public boolean unSubScribeAlias(String appId, String appKey, String packageName, String pushId, String alias) {
        this.subScribeAliasStrategy.setAppId(appId);
        this.subScribeAliasStrategy.setAppKey(appKey);
        this.subScribeAliasStrategy.setStrategyPackageNanme(packageName);
        this.subScribeAliasStrategy.setPushId(pushId);
        this.subScribeAliasStrategy.setSubAliasType(1);
        this.subScribeAliasStrategy.setAlias(alias);
        return this.subScribeAliasStrategy.process();
    }

    public boolean checkSubScribeAlias(String appId, String appKey, String packageName, String pushId) {
        this.subScribeAliasStrategy.setAppId(appId);
        this.subScribeAliasStrategy.setAppKey(appKey);
        this.subScribeAliasStrategy.setStrategyPackageNanme(packageName);
        this.subScribeAliasStrategy.setPushId(pushId);
        this.subScribeAliasStrategy.setSubAliasType(2);
        return this.subScribeAliasStrategy.process();
    }

    public boolean clearNotification(int notifyId, String packageName) {
        this.notificationClearStrategy.setNotifyId(notifyId);
        this.notificationClearStrategy.setStrategyPackageNanme(packageName);
        this.notificationClearStrategy.setClearType(1);
        return this.notificationClearStrategy.process();
    }

    public boolean clearAllNotification(String packageName) {
        this.notificationClearStrategy.setClearType(0);
        this.notificationClearStrategy.setStrategyPackageNanme(packageName);
        return this.notificationClearStrategy.process();
    }

    public boolean clearNotifyKey(String packageName, String notifyKey) {
        this.notificationClearStrategy.setClearType(2);
        this.notificationClearStrategy.setNotifyKey(notifyKey);
        this.notificationClearStrategy.setStrategyPackageNanme(packageName);
        return this.notificationClearStrategy.process();
    }

    public ANResponse<String> uploadLogFile(String messageId, String deviceId, String errorMsg, File logFile) {
        return this.pushAPI.uploadLogFile(messageId, deviceId, errorMsg, logFile);
    }
}
