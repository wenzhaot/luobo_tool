package com.liulishuo.filedownloader.message;

interface IMessageSnapshot {
    String getEtag();

    String getFileName();

    int getId();

    long getLargeSofarBytes();

    long getLargeTotalBytes();

    int getRetryingTimes();

    int getSmallSofarBytes();

    int getSmallTotalBytes();

    byte getStatus();

    Throwable getThrowable();

    boolean isLargeFile();

    boolean isResuming();

    boolean isReusedDownloadedFile();
}
