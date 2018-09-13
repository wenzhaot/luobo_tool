package com.liulishuo.filedownloader;

import com.liulishuo.filedownloader.IDownloadSpeed.Lookup;
import com.liulishuo.filedownloader.message.MessageSnapshot;

public interface ITaskHunter extends Lookup {

    public interface IStarter {
        boolean equalListener(FileDownloadListener fileDownloadListener);

        void start();
    }

    public interface IMessageHandler {
        IFileDownloadMessenger getMessenger();

        MessageSnapshot prepareErrorMessage(Throwable th);

        boolean updateKeepAhead(MessageSnapshot messageSnapshot);

        boolean updateKeepFlow(MessageSnapshot messageSnapshot);

        boolean updateMoreLikelyCompleted(MessageSnapshot messageSnapshot);

        boolean updateSameFilePathTaskRunning(MessageSnapshot messageSnapshot);
    }

    void free();

    Throwable getErrorCause();

    String getEtag();

    int getRetryingTimes();

    long getSofarBytes();

    byte getStatus();

    long getTotalBytes();

    void intoLaunchPool();

    boolean isLargeFile();

    boolean isResuming();

    boolean isReusedOldFile();

    boolean pause();

    void reset();
}
