package com.liulishuo.filedownloader;

import android.app.Notification;
import android.os.IBinder;
import android.os.RemoteException;
import com.liulishuo.filedownloader.i.IFileDownloadIPCCallback.Stub;
import com.liulishuo.filedownloader.i.IFileDownloadIPCService;
import com.liulishuo.filedownloader.message.MessageSnapshot;
import com.liulishuo.filedownloader.message.MessageSnapshotFlow;
import com.liulishuo.filedownloader.model.FileDownloadHeader;
import com.liulishuo.filedownloader.services.BaseFileServiceUIGuard;
import com.liulishuo.filedownloader.services.FileDownloadService.SeparateProcessService;
import com.liulishuo.filedownloader.util.DownloadServiceNotConnectedHelper;

class FileDownloadServiceUIGuard extends BaseFileServiceUIGuard<FileDownloadServiceCallback, IFileDownloadIPCService> {

    protected static class FileDownloadServiceCallback extends Stub {
        protected FileDownloadServiceCallback() {
        }

        public void callback(MessageSnapshot snapshot) throws RemoteException {
            MessageSnapshotFlow.getImpl().inflow(snapshot);
        }
    }

    FileDownloadServiceUIGuard() {
        super(SeparateProcessService.class);
    }

    protected FileDownloadServiceCallback createCallback() {
        return new FileDownloadServiceCallback();
    }

    protected IFileDownloadIPCService asInterface(IBinder service) {
        return IFileDownloadIPCService.Stub.asInterface(service);
    }

    protected void registerCallback(IFileDownloadIPCService service, FileDownloadServiceCallback fileDownloadServiceCallback) throws RemoteException {
        service.registerCallback(fileDownloadServiceCallback);
    }

    protected void unregisterCallback(IFileDownloadIPCService service, FileDownloadServiceCallback fileDownloadServiceCallback) throws RemoteException {
        service.unregisterCallback(fileDownloadServiceCallback);
    }

    public boolean start(String url, String path, boolean pathAsDirectory, int callbackProgressTimes, int callbackProgressMinIntervalMillis, int autoRetryTimes, boolean forceReDownload, FileDownloadHeader header, boolean isWifiRequired) {
        if (!isConnected()) {
            return DownloadServiceNotConnectedHelper.start(url, path, pathAsDirectory);
        }
        try {
            ((IFileDownloadIPCService) getService()).start(url, path, pathAsDirectory, callbackProgressTimes, callbackProgressMinIntervalMillis, autoRetryTimes, forceReDownload, header, isWifiRequired);
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean pause(int id) {
        if (!isConnected()) {
            return DownloadServiceNotConnectedHelper.pause(id);
        }
        try {
            return ((IFileDownloadIPCService) getService()).pause(id);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isDownloading(String url, String path) {
        if (!isConnected()) {
            return DownloadServiceNotConnectedHelper.isDownloading(url, path);
        }
        try {
            return ((IFileDownloadIPCService) getService()).checkDownloading(url, path);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public long getSofar(int id) {
        if (!isConnected()) {
            return DownloadServiceNotConnectedHelper.getSofar(id);
        }
        long val = 0;
        try {
            return ((IFileDownloadIPCService) getService()).getSofar(id);
        } catch (RemoteException e) {
            e.printStackTrace();
            return val;
        }
    }

    public long getTotal(int id) {
        if (!isConnected()) {
            return DownloadServiceNotConnectedHelper.getTotal(id);
        }
        long val = 0;
        try {
            return ((IFileDownloadIPCService) getService()).getTotal(id);
        } catch (RemoteException e) {
            e.printStackTrace();
            return val;
        }
    }

    public byte getStatus(int id) {
        if (!isConnected()) {
            return DownloadServiceNotConnectedHelper.getStatus(id);
        }
        byte status = (byte) 0;
        try {
            return ((IFileDownloadIPCService) getService()).getStatus(id);
        } catch (RemoteException e) {
            e.printStackTrace();
            return status;
        }
    }

    public void pauseAllTasks() {
        if (isConnected()) {
            try {
                ((IFileDownloadIPCService) getService()).pauseAllTasks();
                return;
            } catch (RemoteException e) {
                e.printStackTrace();
                return;
            }
        }
        DownloadServiceNotConnectedHelper.pauseAllTasks();
    }

    public boolean isIdle() {
        if (!isConnected()) {
            return DownloadServiceNotConnectedHelper.isIdle();
        }
        try {
            ((IFileDownloadIPCService) getService()).isIdle();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void startForeground(int notificationId, Notification notification) {
        if (isConnected()) {
            try {
                ((IFileDownloadIPCService) getService()).startForeground(notificationId, notification);
                return;
            } catch (RemoteException e) {
                e.printStackTrace();
                return;
            }
        }
        DownloadServiceNotConnectedHelper.startForeground(notificationId, notification);
    }

    public void stopForeground(boolean removeNotification) {
        if (isConnected()) {
            try {
                ((IFileDownloadIPCService) getService()).stopForeground(removeNotification);
                return;
            } catch (RemoteException e) {
                e.printStackTrace();
                return;
            }
        }
        DownloadServiceNotConnectedHelper.stopForeground(removeNotification);
    }

    public boolean setMaxNetworkThreadCount(int count) {
        if (!isConnected()) {
            return DownloadServiceNotConnectedHelper.setMaxNetworkThreadCount(count);
        }
        try {
            return ((IFileDownloadIPCService) getService()).setMaxNetworkThreadCount(count);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean clearTaskData(int id) {
        if (!isConnected()) {
            return DownloadServiceNotConnectedHelper.clearTaskData(id);
        }
        try {
            return ((IFileDownloadIPCService) getService()).clearTaskData(id);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void clearAllTaskData() {
        if (isConnected()) {
            try {
                ((IFileDownloadIPCService) getService()).clearAllTaskData();
                return;
            } catch (RemoteException e) {
                e.printStackTrace();
                return;
            }
        }
        DownloadServiceNotConnectedHelper.clearAllTaskData();
    }
}
