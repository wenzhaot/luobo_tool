package com.meizu.cloud.pushsdk.platform.pushstrategy;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.networking.common.ANResponse;
import com.meizu.cloud.pushsdk.networking.error.ANError;
import com.meizu.cloud.pushsdk.platform.PlatformMessageSender;
import com.meizu.cloud.pushsdk.platform.PushIdEncryptUtils;
import com.meizu.cloud.pushsdk.platform.api.PushAPI;
import com.meizu.cloud.pushsdk.platform.message.RegisterStatus;
import com.meizu.cloud.pushsdk.pushtracer.emitter.classic.Executor;
import com.meizu.cloud.pushsdk.util.PushPreferencesUtils;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RegisterStatusStrategy extends Strategy<RegisterStatus> {
    protected int deviceIdRetry;
    protected Handler mainHandler;
    protected ScheduledExecutorService scheduledExecutorService;

    public RegisterStatusStrategy(Context context, String appId, String appKey, PushAPI pushAPI, ScheduledExecutorService executorService) {
        super(context, appId, appKey, pushAPI, executorService);
        this.deviceIdRetry = 0;
    }

    public RegisterStatusStrategy(Context context, PushAPI pushAPI, ScheduledExecutorService executorService) {
        this(context, null, null, pushAPI, executorService);
        this.scheduledExecutorService = (ScheduledExecutorService) Executor.getExecutor();
        this.mainHandler = new Handler(context.getMainLooper()) {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        RegisterStatusStrategy.this.process();
                        return;
                    default:
                        return;
                }
            }
        };
    }

    public RegisterStatusStrategy(Context context, PushAPI pushAPI, ScheduledExecutorService executorService, boolean enableRPC) {
        this(context, pushAPI, executorService);
        this.enableRPC = enableRPC;
    }

    public boolean matchCondition() {
        return (TextUtils.isEmpty(this.appId) || TextUtils.isEmpty(this.appKey)) ? false : true;
    }

    protected RegisterStatus feedBackErrorResponse() {
        RegisterStatus registerStatus = new RegisterStatus();
        registerStatus.setCode(Strategy.FEEDBACK_PARAMETER_ERROR_CODE);
        if (TextUtils.isEmpty(this.appId)) {
            registerStatus.setMessage("appId not empty");
        } else if (TextUtils.isEmpty(this.appKey)) {
            registerStatus.setMessage("appKey not empty");
        }
        return registerStatus;
    }

    public Intent sendRpcRequest() {
        Intent intent = new Intent();
        intent.putExtra(Strategy.APP_ID, this.appId);
        intent.putExtra(Strategy.APP_KEY, this.appKey);
        intent.putExtra(Strategy.STRATEGY_PACKAGE_NANME, this.context.getPackageName());
        intent.putExtra(Strategy.STRATEGY_TYPE, strategyType());
        return intent;
    }

    protected RegisterStatus localResponse() {
        return null;
    }

    public RegisterStatus netWorkRequest() {
        RegisterStatus registerStatus = new RegisterStatus();
        String pushId = PushPreferencesUtils.getPushId(this.context, this.strategyPackageNanme);
        int expireTime = PushPreferencesUtils.getPushIdExpireTime(this.context, this.strategyPackageNanme);
        if (retryRegister(pushId, expireTime)) {
            PushPreferencesUtils.putPushId(this.context, "", this.strategyPackageNanme);
            this.deviceId = getDeviceId();
            if (!TextUtils.isEmpty(this.deviceId) || this.deviceIdRetry >= 3) {
                this.deviceIdRetry = 0;
                ANResponse<String> registerStatusANResponse = this.pushAPI.register(this.appId, this.appKey, this.deviceId);
                if (registerStatusANResponse.isSuccess()) {
                    registerStatus = new RegisterStatus((String) registerStatusANResponse.getResult());
                    DebugLogger.e(Strategy.TAG, "registerStatus " + registerStatus);
                    if (!TextUtils.isEmpty(registerStatus.getPushId())) {
                        PushPreferencesUtils.putPushId(this.context, registerStatus.getPushId(), this.strategyPackageNanme);
                        PushPreferencesUtils.putPushIdExpireTime(this.context, (int) ((System.currentTimeMillis() / 1000) + ((long) registerStatus.getExpireTime())), this.strategyPackageNanme);
                    }
                } else {
                    ANError error = registerStatusANResponse.getError();
                    if (error.getResponse() != null) {
                        DebugLogger.e(Strategy.TAG, "status code=" + error.getErrorCode() + " data=" + error.getResponse());
                    }
                    registerStatus.setCode(String.valueOf(error.getErrorCode()));
                    registerStatus.setMessage(error.getErrorBody());
                    DebugLogger.e(Strategy.TAG, "registerStatus " + registerStatus);
                }
            } else {
                DebugLogger.i(Strategy.TAG, "after " + (this.deviceIdRetry * 10) + " seconds start register");
                executeAfterGetDeviceId((long) (this.deviceIdRetry * 10));
                this.deviceIdRetry++;
                registerStatus.setCode(Strategy.DEVICE_ERROR_CODE);
                registerStatus.setMessage("deviceId is empty");
            }
        } else {
            registerStatus.setCode("200");
            registerStatus.setMessage("already register PushId,dont register frequently");
            registerStatus.setPushId(pushId);
            registerStatus.setExpireTime((int) (((long) expireTime) - (System.currentTimeMillis() / 1000)));
        }
        return registerStatus;
    }

    public void sendReceiverMessage(RegisterStatus message) {
        PlatformMessageSender.sendRegisterStatus(this.context, !TextUtils.isEmpty(this.strategyPackageNanme) ? this.strategyPackageNanme : this.context.getPackageName(), message);
    }

    protected int strategyType() {
        return 2;
    }

    protected void executeAfterGetDeviceId(long delay) {
        this.scheduledExecutorService.schedule(new Runnable() {
            public void run() {
                RegisterStatusStrategy.this.getDeviceId();
                RegisterStatusStrategy.this.mainHandler.sendEmptyMessage(0);
            }
        }, delay, TimeUnit.SECONDS);
    }

    protected boolean retryRegister(String pushId, int expireTime) {
        String deviceId = getDeviceId();
        if (TextUtils.isEmpty(pushId) || TextUtils.isEmpty(deviceId)) {
            return true;
        }
        if ((pushId.startsWith(deviceId) || (!TextUtils.isEmpty(PushIdEncryptUtils.decryptPushId(pushId)) && PushIdEncryptUtils.decryptPushId(pushId).startsWith(deviceId))) && System.currentTimeMillis() / 1000 < ((long) expireTime)) {
            return false;
        }
        return true;
    }
}
