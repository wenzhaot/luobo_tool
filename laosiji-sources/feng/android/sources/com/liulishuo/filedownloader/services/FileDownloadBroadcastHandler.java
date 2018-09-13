package com.liulishuo.filedownloader.services;

import android.content.Intent;
import com.liulishuo.filedownloader.model.FileDownloadModel;
import com.liulishuo.filedownloader.util.FileDownloadHelper;
import com.liulishuo.filedownloader.util.FileDownloadUtils;

public class FileDownloadBroadcastHandler {
    public static final String ACTION_COMPLETED = "filedownloader.intent.action.completed";
    public static final String KEY_MODEL = "model";

    public static FileDownloadModel parseIntent(Intent intent) {
        if (ACTION_COMPLETED.equals(intent.getAction())) {
            return (FileDownloadModel) intent.getParcelableExtra("model");
        }
        throw new IllegalArgumentException(FileDownloadUtils.formatString("can't recognize the intent with action %s, on the current version we only support action [%s]", intent.getAction(), ACTION_COMPLETED));
    }

    public static void sendCompletedBroadcast(FileDownloadModel model) {
        if (model == null) {
            throw new IllegalArgumentException();
        } else if (model.getStatus() != (byte) -3) {
            throw new IllegalStateException();
        } else {
            Intent intent = new Intent(ACTION_COMPLETED);
            intent.putExtra("model", model);
            FileDownloadHelper.getAppContext().sendBroadcast(intent);
        }
    }
}
