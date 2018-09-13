package com.meizu.cloud.pushsdk.handler.impl.fileupload;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.handler.AbstractAppLogicListener;
import com.meizu.cloud.pushsdk.handler.impl.AbstractMessageHandler;
import com.meizu.cloud.pushsdk.handler.impl.model.ControlMessage;
import com.meizu.cloud.pushsdk.handler.impl.model.UploadLogMessage;
import com.meizu.cloud.pushsdk.networking.common.ANResponse;
import com.meizu.cloud.pushsdk.notification.PushNotification;
import com.meizu.cloud.pushsdk.platform.api.PushPlatformManager;
import com.meizu.cloud.pushsdk.util.Connectivity;
import com.meizu.cloud.pushsdk.util.UxIPUtils;
import java.io.File;

public class LogUploadMessageHandler extends AbstractMessageHandler<UploadLogMessage> {
    public LogUploadMessageHandler(Context context, AbstractAppLogicListener abstractAppLogicListener) {
        super(context, abstractAppLogicListener);
    }

    protected UploadLogMessage getMessage(Intent intent) {
        String control = intent.getStringExtra(PushConstants.MZ_PUSH_CONTROL_MESSAGE);
        String seqId = intent.getStringExtra(PushConstants.EXTRA_APP_PUSH_SEQ_ID);
        return new UploadLogMessage(intent.getStringExtra(PushConstants.MZ_PUSH_PRIVATE_MESSAGE), control, intent.getStringExtra(PushConstants.MZ_PUSH_MESSAGE_STATISTICS_IMEI_KEY), seqId);
    }

    protected void unsafeSend(UploadLogMessage message, PushNotification pushNotification) {
        File zipFile;
        DebugLogger.flush();
        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        String messageId = message.getControlMessage().getStatics().getTaskId();
        String uploadFile = root + "/Android/data/pushSdktmp/" + messageId + "_" + message.getControlMessage().getStatics().getDeviceId() + ".zip";
        String errorMessage = null;
        try {
            new ZipTask(uploadFile).zip(message.getFileList());
            zipFile = new File(uploadFile);
        } catch (Exception e) {
            errorMessage = e.getMessage();
            zipFile = null;
            DebugLogger.e("AbstractMessageHandler", "zip error message " + errorMessage);
        }
        if (zipFile != null && zipFile.length() / 1024 > ((long) message.getMaxSize())) {
            errorMessage = "the upload file exceeds the max size";
        } else if (message.isWifiUpload() && !Connectivity.isConnectedWifi(context())) {
            errorMessage = "current network not allowed upload log file";
        }
        ANResponse<String> response = PushPlatformManager.getInstance(context()).uploadLogFile(message.getControlMessage().getStatics().getTaskId(), message.getControlMessage().getStatics().getDeviceId(), errorMessage, zipFile);
        if (response == null || !response.isSuccess()) {
            DebugLogger.i("AbstractMessageHandler", "upload error code " + response.getError() + ((String) response.getResult()));
            return;
        }
        if (zipFile != null) {
            zipFile.delete();
        }
        DebugLogger.e("AbstractMessageHandler", "upload success " + ((String) response.getResult()));
    }

    public boolean messageMatch(Intent intent) {
        DebugLogger.i("AbstractMessageHandler", "start LogUploadMessageHandler match");
        String control = intent.getStringExtra(PushConstants.MZ_PUSH_CONTROL_MESSAGE);
        int pushType = 0;
        if (!TextUtils.isEmpty(control)) {
            ControlMessage controlMessage = ControlMessage.parse(control);
            if (controlMessage.getControl() != null) {
                pushType = controlMessage.getControl().getPushType();
            }
        }
        return PushConstants.MZ_PUSH_ON_MESSAGE_ACTION.equals(intent.getAction()) && PushConstants.PUSH_TYPE_UPLOAD_LOG.equals(String.valueOf(pushType));
    }

    public int getProcessorType() {
        return 65536;
    }

    protected void onBeforeEvent(UploadLogMessage message) {
        UxIPUtils.onReceivePushMessageEvent(context(), context().getPackageName(), message.getControlMessage().getStatics().getDeviceId(), message.getControlMessage().getStatics().getTaskId(), message.getControlMessage().getStatics().getSeqId(), message.getControlMessage().getStatics().getTime());
    }
}
