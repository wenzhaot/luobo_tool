package com.meizu.cloud.pushsdk.networking.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.meizu.cloud.pushsdk.networking.interfaces.UploadProgressListener;
import com.meizu.cloud.pushsdk.networking.model.Progress;
import java.lang.ref.WeakReference;

public class UploadProgressHandler extends Handler {
    private final WeakReference<UploadProgressListener> mUploadProgressListenerWeakRef;

    public UploadProgressHandler(UploadProgressListener uploadProgressListener) {
        super(Looper.getMainLooper());
        this.mUploadProgressListenerWeakRef = new WeakReference(uploadProgressListener);
    }

    public void handleMessage(Message msg) {
        UploadProgressListener uploadProgressListener = (UploadProgressListener) this.mUploadProgressListenerWeakRef.get();
        switch (msg.what) {
            case 1:
                if (uploadProgressListener != null) {
                    Progress progress = msg.obj;
                    uploadProgressListener.onProgress(progress.currentBytes, progress.totalBytes);
                    return;
                }
                return;
            default:
                super.handleMessage(msg);
                return;
        }
    }
}