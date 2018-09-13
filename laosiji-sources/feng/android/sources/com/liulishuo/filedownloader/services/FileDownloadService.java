package com.liulishuo.filedownloader.services;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.liulishuo.filedownloader.util.FileDownloadHelper;
import com.liulishuo.filedownloader.util.FileDownloadProperties;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import java.lang.ref.WeakReference;

@SuppressLint({"Registered"})
public class FileDownloadService extends Service {
    private IFileDownloadServiceHandler handler;

    public static class SeparateProcessService extends FileDownloadService {
    }

    public static class SharedMainProcessService extends FileDownloadService {
    }

    public void onCreate() {
        super.onCreate();
        FileDownloadHelper.holdContext(this);
        try {
            FileDownloadUtils.setMinProgressStep(FileDownloadProperties.getImpl().downloadMinProgressStep);
            FileDownloadUtils.setMinProgressTime(FileDownloadProperties.getImpl().downloadMinProgressTime);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        FileDownloadManager manager = new FileDownloadManager();
        if (FileDownloadProperties.getImpl().processNonSeparate) {
            this.handler = new FDServiceSharedHandler(new WeakReference(this), manager);
        } else {
            this.handler = new FDServiceSeparateHandler(new WeakReference(this), manager);
        }
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        this.handler.onStartCommand(intent, flags, startId);
        return 1;
    }

    public void onDestroy() {
        this.handler.onDestroy();
        super.onDestroy();
    }

    public IBinder onBind(Intent intent) {
        return this.handler.onBind(intent);
    }
}
