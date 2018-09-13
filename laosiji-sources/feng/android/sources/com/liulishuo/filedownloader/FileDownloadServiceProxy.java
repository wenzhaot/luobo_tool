package com.liulishuo.filedownloader;

import android.app.Notification;
import android.content.Context;
import com.liulishuo.filedownloader.model.FileDownloadHeader;
import com.liulishuo.filedownloader.services.FDServiceSharedHandler.FileDownloadServiceSharedConnection;
import com.liulishuo.filedownloader.util.FileDownloadProperties;

public class FileDownloadServiceProxy implements IFileDownloadServiceProxy {
    private final IFileDownloadServiceProxy handler;

    private static final class HolderClass {
        private static final FileDownloadServiceProxy INSTANCE = new FileDownloadServiceProxy();

        private HolderClass() {
        }
    }

    public static FileDownloadServiceProxy getImpl() {
        return HolderClass.INSTANCE;
    }

    public static FileDownloadServiceSharedConnection getConnectionListener() {
        if (getImpl().handler instanceof FileDownloadServiceSharedTransmit) {
            return (FileDownloadServiceSharedConnection) getImpl().handler;
        }
        return null;
    }

    private FileDownloadServiceProxy() {
        this.handler = FileDownloadProperties.getImpl().processNonSeparate ? new FileDownloadServiceSharedTransmit() : new FileDownloadServiceUIGuard();
    }

    public boolean start(String url, String path, boolean pathAsDirectory, int callbackProgressTimes, int callbackProgressMinIntervalMillis, int autoRetryTimes, boolean forceReDownload, FileDownloadHeader header, boolean isWifiRequired) {
        return this.handler.start(url, path, pathAsDirectory, callbackProgressTimes, callbackProgressMinIntervalMillis, autoRetryTimes, forceReDownload, header, isWifiRequired);
    }

    public boolean pause(int id) {
        return this.handler.pause(id);
    }

    public boolean isDownloading(String url, String path) {
        return this.handler.isDownloading(url, path);
    }

    public long getSofar(int id) {
        return this.handler.getSofar(id);
    }

    public long getTotal(int id) {
        return this.handler.getTotal(id);
    }

    public byte getStatus(int id) {
        return this.handler.getStatus(id);
    }

    public void pauseAllTasks() {
        this.handler.pauseAllTasks();
    }

    public boolean isIdle() {
        return this.handler.isIdle();
    }

    public boolean isConnected() {
        return this.handler.isConnected();
    }

    public void bindStartByContext(Context context) {
        this.handler.bindStartByContext(context);
    }

    public void bindStartByContext(Context context, Runnable connectedRunnable) {
        this.handler.bindStartByContext(context, connectedRunnable);
    }

    public void unbindByContext(Context context) {
        this.handler.unbindByContext(context);
    }

    public void startForeground(int notificationId, Notification notification) {
        this.handler.startForeground(notificationId, notification);
    }

    public void stopForeground(boolean removeNotification) {
        this.handler.stopForeground(removeNotification);
    }

    public boolean setMaxNetworkThreadCount(int count) {
        return this.handler.setMaxNetworkThreadCount(count);
    }

    public boolean clearTaskData(int id) {
        return this.handler.clearTaskData(id);
    }

    public void clearAllTaskData() {
        this.handler.clearAllTaskData();
    }
}
