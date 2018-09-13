package com.liulishuo.filedownloader.services;

import android.app.Notification;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import com.liulishuo.filedownloader.i.IFileDownloadIPCCallback;
import com.liulishuo.filedownloader.i.IFileDownloadIPCService.Stub;
import com.liulishuo.filedownloader.message.MessageSnapshot;
import com.liulishuo.filedownloader.message.MessageSnapshotFlow;
import com.liulishuo.filedownloader.message.MessageSnapshotFlow.MessageReceiver;
import com.liulishuo.filedownloader.model.FileDownloadHeader;
import com.liulishuo.filedownloader.util.FileDownloadLog;
import java.lang.ref.WeakReference;

public class FDServiceSeparateHandler extends Stub implements MessageReceiver, IFileDownloadServiceHandler {
    private final RemoteCallbackList<IFileDownloadIPCCallback> callbackList = new RemoteCallbackList();
    private final FileDownloadManager downloadManager;
    private final WeakReference<FileDownloadService> wService;

    private synchronized int callback(MessageSnapshot snapShot) {
        int n;
        n = this.callbackList.beginBroadcast();
        int i = 0;
        while (i < n) {
            try {
                ((IFileDownloadIPCCallback) this.callbackList.getBroadcastItem(i)).callback(snapShot);
                i++;
            } catch (RemoteException e) {
                FileDownloadLog.e(this, e, "callback error", new Object[0]);
            } finally {
                this.callbackList.finishBroadcast();
            }
        }
        this.callbackList.finishBroadcast();
        return n;
    }

    FDServiceSeparateHandler(WeakReference<FileDownloadService> wService, FileDownloadManager manager) {
        this.wService = wService;
        this.downloadManager = manager;
        MessageSnapshotFlow.getImpl().setReceiver(this);
    }

    public void registerCallback(IFileDownloadIPCCallback callback) throws RemoteException {
        this.callbackList.register(callback);
    }

    public void unregisterCallback(IFileDownloadIPCCallback callback) throws RemoteException {
        this.callbackList.unregister(callback);
    }

    public boolean checkDownloading(String url, String path) throws RemoteException {
        return this.downloadManager.isDownloading(url, path);
    }

    public void start(String url, String path, boolean pathAsDirectory, int callbackProgressTimes, int callbackProgressMinIntervalMillis, int autoRetryTimes, boolean forceReDownload, FileDownloadHeader header, boolean isWifiRequired) throws RemoteException {
        this.downloadManager.start(url, path, pathAsDirectory, callbackProgressTimes, callbackProgressMinIntervalMillis, autoRetryTimes, forceReDownload, header, isWifiRequired);
    }

    public boolean pause(int downloadId) throws RemoteException {
        return this.downloadManager.pause(downloadId);
    }

    public void pauseAllTasks() throws RemoteException {
        this.downloadManager.pauseAll();
    }

    public boolean setMaxNetworkThreadCount(int count) throws RemoteException {
        return this.downloadManager.setMaxNetworkThreadCount(count);
    }

    public long getSofar(int downloadId) throws RemoteException {
        return this.downloadManager.getSoFar(downloadId);
    }

    public long getTotal(int downloadId) throws RemoteException {
        return this.downloadManager.getTotal(downloadId);
    }

    public byte getStatus(int downloadId) throws RemoteException {
        return this.downloadManager.getStatus(downloadId);
    }

    public boolean isIdle() throws RemoteException {
        return this.downloadManager.isIdle();
    }

    public void startForeground(int id, Notification notification) throws RemoteException {
        if (this.wService != null && this.wService.get() != null) {
            ((FileDownloadService) this.wService.get()).startForeground(id, notification);
        }
    }

    public void stopForeground(boolean removeNotification) throws RemoteException {
        if (this.wService != null && this.wService.get() != null) {
            ((FileDownloadService) this.wService.get()).stopForeground(removeNotification);
        }
    }

    public boolean clearTaskData(int id) throws RemoteException {
        return this.downloadManager.clearTaskData(id);
    }

    public void clearAllTaskData() throws RemoteException {
        this.downloadManager.clearAllTaskData();
    }

    public void onStartCommand(Intent intent, int flags, int startId) {
    }

    public IBinder onBind(Intent intent) {
        return this;
    }

    public void onDestroy() {
        MessageSnapshotFlow.getImpl().setReceiver(null);
    }

    public void receive(MessageSnapshot snapShot) {
        callback(snapShot);
    }
}
