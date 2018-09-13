package com.liulishuo.filedownloader.download;

public interface ProcessCallback {
    boolean isRetry(Exception exception);

    void onCompleted(DownloadRunnable downloadRunnable, long j, long j2);

    void onError(Exception exception);

    void onProgress(long j);

    void onRetry(Exception exception);

    void syncProgressFromCache();
}
