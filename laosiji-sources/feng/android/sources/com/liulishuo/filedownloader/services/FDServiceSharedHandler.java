package com.liulishuo.filedownloader.services;

import android.app.Notification;
import android.content.Intent;
import android.os.IBinder;
import com.liulishuo.filedownloader.FileDownloadServiceProxy;
import com.liulishuo.filedownloader.i.IFileDownloadIPCCallback;
import com.liulishuo.filedownloader.i.IFileDownloadIPCService.Stub;
import com.liulishuo.filedownloader.model.FileDownloadHeader;
import java.lang.ref.WeakReference;

public class FDServiceSharedHandler extends Stub implements IFileDownloadServiceHandler {
    private final FileDownloadManager downloadManager;
    private final WeakReference<FileDownloadService> wService;

    public interface FileDownloadServiceSharedConnection {
        void onConnected(FDServiceSharedHandler fDServiceSharedHandler);

        void onDisconnected();
    }

    FDServiceSharedHandler(WeakReference<FileDownloadService> wService, FileDownloadManager manager) {
        this.wService = wService;
        this.downloadManager = manager;
    }

    public void registerCallback(IFileDownloadIPCCallback callback) {
    }

    public void unregisterCallback(IFileDownloadIPCCallback callback) {
    }

    public boolean checkDownloading(String url, String path) {
        return this.downloadManager.isDownloading(url, path);
    }

    public void start(String url, String path, boolean pathAsDirectory, int callbackProgressTimes, int callbackProgressMinIntervalMillis, int autoRetryTimes, boolean forceReDownload, FileDownloadHeader header, boolean isWifiRequired) {
        this.downloadManager.start(url, path, pathAsDirectory, callbackProgressTimes, callbackProgressMinIntervalMillis, autoRetryTimes, forceReDownload, header, isWifiRequired);
    }

    public boolean pause(int downloadId) {
        return this.downloadManager.pause(downloadId);
    }

    public void pauseAllTasks() {
        this.downloadManager.pauseAll();
    }

    public boolean setMaxNetworkThreadCount(int count) {
        return this.downloadManager.setMaxNetworkThreadCount(count);
    }

    public long getSofar(int downloadId) {
        return this.downloadManager.getSoFar(downloadId);
    }

    public long getTotal(int downloadId) {
        return this.downloadManager.getTotal(downloadId);
    }

    public byte getStatus(int downloadId) {
        return this.downloadManager.getStatus(downloadId);
    }

    public boolean isIdle() {
        return this.downloadManager.isIdle();
    }

    public void startForeground(int id, Notification notification) {
        if (this.wService != null && this.wService.get() != null) {
            ((FileDownloadService) this.wService.get()).startForeground(id, notification);
        }
    }

    public void stopForeground(boolean removeNotification) {
        if (this.wService != null && this.wService.get() != null) {
            ((FileDownloadService) this.wService.get()).stopForeground(removeNotification);
        }
    }

    public boolean clearTaskData(int id) {
        return this.downloadManager.clearTaskData(id);
    }

    public void clearAllTaskData() {
        this.downloadManager.clearAllTaskData();
    }

    public void onStartCommand(Intent intent, int flags, int startId) {
        FileDownloadServiceProxy.getConnectionListener().onConnected(this);
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onDestroy() {
        FileDownloadServiceProxy.getConnectionListener().onDisconnected();
    }
}
