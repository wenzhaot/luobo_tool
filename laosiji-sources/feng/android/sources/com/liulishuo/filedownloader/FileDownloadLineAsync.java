package com.liulishuo.filedownloader;

import android.app.Notification;

public class FileDownloadLineAsync {
    public boolean startForeground(final int id, final Notification notification) {
        if (FileDownloader.getImpl().isServiceConnected()) {
            FileDownloader.getImpl().startForeground(id, notification);
            return true;
        }
        FileDownloader.getImpl().bindService(new Runnable() {
            public void run() {
                FileDownloader.getImpl().startForeground(id, notification);
            }
        });
        return false;
    }
}
