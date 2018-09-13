package com.liulishuo.filedownloader.notification;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.BaseDownloadTask.IRunningTask;
import com.liulishuo.filedownloader.FileDownloadList;
import com.liulishuo.filedownloader.FileDownloadListener;

public abstract class FileDownloadNotificationListener extends FileDownloadListener {
    private final FileDownloadNotificationHelper helper;

    protected abstract BaseNotificationItem create(BaseDownloadTask baseDownloadTask);

    public FileDownloadNotificationListener(FileDownloadNotificationHelper helper) {
        if (helper == null) {
            throw new IllegalArgumentException("helper must not be null!");
        }
        this.helper = helper;
    }

    public FileDownloadNotificationHelper getHelper() {
        return this.helper;
    }

    public void addNotificationItem(int downloadId) {
        if (downloadId != 0) {
            IRunningTask task = FileDownloadList.getImpl().get(downloadId);
            if (task != null) {
                addNotificationItem(task.getOrigin());
            }
        }
    }

    public void addNotificationItem(BaseDownloadTask task) {
        if (!disableNotification(task)) {
            BaseNotificationItem n = create(task);
            if (n != null) {
                this.helper.add(n);
            }
        }
    }

    public void destroyNotification(BaseDownloadTask task) {
        if (!disableNotification(task)) {
            this.helper.showIndeterminate(task.getId(), task.getStatus());
            BaseNotificationItem n = this.helper.remove(task.getId());
            if (!interceptCancel(task, n) && n != null) {
                n.cancel();
            }
        }
    }

    public void showIndeterminate(BaseDownloadTask task) {
        if (!disableNotification(task)) {
            this.helper.showIndeterminate(task.getId(), task.getStatus());
        }
    }

    public void showProgress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
        if (!disableNotification(task)) {
            this.helper.showProgress(task.getId(), task.getSmallFileSoFarBytes(), task.getSmallFileTotalBytes());
        }
    }

    protected boolean interceptCancel(BaseDownloadTask task, BaseNotificationItem notificationItem) {
        return false;
    }

    protected boolean disableNotification(BaseDownloadTask task) {
        return false;
    }

    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
        addNotificationItem(task);
        showIndeterminate(task);
    }

    protected void started(BaseDownloadTask task) {
        super.started(task);
        showIndeterminate(task);
    }

    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
        showProgress(task, soFarBytes, totalBytes);
    }

    protected void retry(BaseDownloadTask task, Throwable ex, int retryingTimes, int soFarBytes) {
        super.retry(task, ex, retryingTimes, soFarBytes);
        showIndeterminate(task);
    }

    protected void blockComplete(BaseDownloadTask task) {
    }

    protected void completed(BaseDownloadTask task) {
        destroyNotification(task);
    }

    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
        destroyNotification(task);
    }

    protected void error(BaseDownloadTask task, Throwable e) {
        destroyNotification(task);
    }

    protected void warn(BaseDownloadTask task) {
    }
}
