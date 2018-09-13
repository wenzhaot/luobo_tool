package com.liulishuo.filedownloader;

public class FileDownloadSampleListener extends FileDownloadListener {
    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
    }

    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
    }

    protected void blockComplete(BaseDownloadTask task) {
    }

    protected void completed(BaseDownloadTask task) {
    }

    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
    }

    protected void error(BaseDownloadTask task, Throwable e) {
    }

    protected void warn(BaseDownloadTask task) {
    }
}
