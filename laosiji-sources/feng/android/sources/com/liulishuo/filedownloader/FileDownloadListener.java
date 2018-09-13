package com.liulishuo.filedownloader;

import com.liulishuo.filedownloader.util.FileDownloadLog;

public abstract class FileDownloadListener {
    protected abstract void completed(BaseDownloadTask baseDownloadTask);

    protected abstract void error(BaseDownloadTask baseDownloadTask, Throwable th);

    protected abstract void paused(BaseDownloadTask baseDownloadTask, int i, int i2);

    protected abstract void pending(BaseDownloadTask baseDownloadTask, int i, int i2);

    protected abstract void progress(BaseDownloadTask baseDownloadTask, int i, int i2);

    protected abstract void warn(BaseDownloadTask baseDownloadTask);

    public FileDownloadListener(int priority) {
        FileDownloadLog.w(this, "not handle priority any more", new Object[0]);
    }

    protected boolean isInvalid() {
        return false;
    }

    protected void started(BaseDownloadTask task) {
    }

    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
    }

    protected void blockComplete(BaseDownloadTask task) throws Throwable {
    }

    protected void retry(BaseDownloadTask task, Throwable ex, int retryingTimes, int soFarBytes) {
    }
}
