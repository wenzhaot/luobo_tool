package com.liulishuo.filedownloader;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import com.liulishuo.filedownloader.event.DownloadServiceConnectChangedEvent;
import com.liulishuo.filedownloader.event.DownloadServiceConnectChangedEvent.ConnectStatus;
import com.liulishuo.filedownloader.model.FileDownloadHeader;
import com.liulishuo.filedownloader.services.FDServiceSharedHandler;
import com.liulishuo.filedownloader.services.FDServiceSharedHandler.FileDownloadServiceSharedConnection;
import com.liulishuo.filedownloader.services.FileDownloadService.SharedMainProcessService;
import com.liulishuo.filedownloader.util.DownloadServiceNotConnectedHelper;
import java.util.ArrayList;
import java.util.List;

class FileDownloadServiceSharedTransmit implements IFileDownloadServiceProxy, FileDownloadServiceSharedConnection {
    private static final Class<?> SERVICE_CLASS = SharedMainProcessService.class;
    private final ArrayList<Runnable> connectedRunnableList = new ArrayList();
    private FDServiceSharedHandler handler;

    FileDownloadServiceSharedTransmit() {
    }

    public boolean start(String url, String path, boolean pathAsDirectory, int callbackProgressTimes, int callbackProgressMinIntervalMillis, int autoRetryTimes, boolean forceReDownload, FileDownloadHeader header, boolean isWifiRequired) {
        if (!isConnected()) {
            return DownloadServiceNotConnectedHelper.start(url, path, pathAsDirectory);
        }
        this.handler.start(url, path, pathAsDirectory, callbackProgressTimes, callbackProgressMinIntervalMillis, autoRetryTimes, forceReDownload, header, isWifiRequired);
        return true;
    }

    public boolean pause(int id) {
        if (isConnected()) {
            return this.handler.pause(id);
        }
        return DownloadServiceNotConnectedHelper.pause(id);
    }

    public boolean isDownloading(String url, String path) {
        if (isConnected()) {
            return this.handler.checkDownloading(url, path);
        }
        return DownloadServiceNotConnectedHelper.isDownloading(url, path);
    }

    public long getSofar(int id) {
        if (isConnected()) {
            return this.handler.getSofar(id);
        }
        return DownloadServiceNotConnectedHelper.getSofar(id);
    }

    public long getTotal(int id) {
        if (isConnected()) {
            return this.handler.getTotal(id);
        }
        return DownloadServiceNotConnectedHelper.getTotal(id);
    }

    public byte getStatus(int id) {
        if (isConnected()) {
            return this.handler.getStatus(id);
        }
        return DownloadServiceNotConnectedHelper.getStatus(id);
    }

    public void pauseAllTasks() {
        if (isConnected()) {
            this.handler.pauseAllTasks();
        } else {
            DownloadServiceNotConnectedHelper.pauseAllTasks();
        }
    }

    public boolean isIdle() {
        if (isConnected()) {
            return this.handler.isIdle();
        }
        return DownloadServiceNotConnectedHelper.isIdle();
    }

    public boolean isConnected() {
        return this.handler != null;
    }

    public void bindStartByContext(Context context) {
        bindStartByContext(context, null);
    }

    public void bindStartByContext(Context context, Runnable connectedRunnable) {
        if (!(connectedRunnable == null || this.connectedRunnableList.contains(connectedRunnable))) {
            this.connectedRunnableList.add(connectedRunnable);
        }
        context.startService(new Intent(context, SERVICE_CLASS));
    }

    public void unbindByContext(Context context) {
        context.stopService(new Intent(context, SERVICE_CLASS));
        this.handler = null;
    }

    public void startForeground(int notificationId, Notification notification) {
        if (isConnected()) {
            this.handler.startForeground(notificationId, notification);
        } else {
            DownloadServiceNotConnectedHelper.startForeground(notificationId, notification);
        }
    }

    public void stopForeground(boolean removeNotification) {
        if (isConnected()) {
            this.handler.stopForeground(removeNotification);
        } else {
            DownloadServiceNotConnectedHelper.stopForeground(removeNotification);
        }
    }

    public boolean setMaxNetworkThreadCount(int count) {
        if (isConnected()) {
            return this.handler.setMaxNetworkThreadCount(count);
        }
        return DownloadServiceNotConnectedHelper.setMaxNetworkThreadCount(count);
    }

    public boolean clearTaskData(int id) {
        if (isConnected()) {
            return this.handler.clearTaskData(id);
        }
        return DownloadServiceNotConnectedHelper.clearTaskData(id);
    }

    public void clearAllTaskData() {
        if (isConnected()) {
            this.handler.clearAllTaskData();
        } else {
            DownloadServiceNotConnectedHelper.clearAllTaskData();
        }
    }

    public void onConnected(FDServiceSharedHandler handler) {
        this.handler = handler;
        List<Runnable> runnableList = (List) this.connectedRunnableList.clone();
        this.connectedRunnableList.clear();
        for (Runnable runnable : runnableList) {
            runnable.run();
        }
        FileDownloadEventPool.getImpl().asyncPublishInNewThread(new DownloadServiceConnectChangedEvent(ConnectStatus.connected, SERVICE_CLASS));
    }

    public void onDisconnected() {
        this.handler = null;
        FileDownloadEventPool.getImpl().asyncPublishInNewThread(new DownloadServiceConnectChangedEvent(ConnectStatus.disconnected, SERVICE_CLASS));
    }
}
