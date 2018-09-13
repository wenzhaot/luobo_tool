package com.liulishuo.filedownloader;

public class FileDownloadMonitor {
    private static IMonitor monitor;

    public interface IMonitor {
        void onRequestStart(int i, boolean z, FileDownloadListener fileDownloadListener);

        void onRequestStart(BaseDownloadTask baseDownloadTask);

        void onTaskBegin(BaseDownloadTask baseDownloadTask);

        void onTaskOver(BaseDownloadTask baseDownloadTask);

        void onTaskStarted(BaseDownloadTask baseDownloadTask);
    }

    public static void setGlobalMonitor(IMonitor monitor) {
        monitor = monitor;
    }

    public static void releaseGlobalMonitor() {
        monitor = null;
    }

    public static IMonitor getMonitor() {
        return monitor;
    }

    public static boolean isValid() {
        return getMonitor() != null;
    }
}