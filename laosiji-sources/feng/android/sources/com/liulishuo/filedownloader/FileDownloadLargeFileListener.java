package com.liulishuo.filedownloader;

public abstract class FileDownloadLargeFileListener extends FileDownloadListener {
    protected abstract void paused(BaseDownloadTask baseDownloadTask, long j, long j2);

    protected abstract void pending(BaseDownloadTask baseDownloadTask, long j, long j2);

    protected abstract void progress(BaseDownloadTask baseDownloadTask, long j, long j2);

    public FileDownloadLargeFileListener(int priority) {
        super(priority);
    }

    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
    }

    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, long soFarBytes, long totalBytes) {
    }

    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
    }

    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
    }

    protected void retry(BaseDownloadTask task, Throwable ex, int retryingTimes, long soFarBytes) {
    }

    protected void retry(BaseDownloadTask task, Throwable ex, int retryingTimes, int soFarBytes) {
    }

    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
    }
}
