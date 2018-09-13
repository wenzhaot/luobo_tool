package com.liulishuo.filedownloader.util;

import android.app.Notification;

public class DownloadServiceNotConnectedHelper {
    private static final String CAUSE = ", but the download service isn't connected yet.";
    private static final String TIPS = "\nYou can use FileDownloader#isServiceConnected() to check whether the service has been connected, \nbesides you can use following functions easier to control your code invoke after the service has been connected: \n1. FileDownloader#bindService(Runnable)\n2. FileDownloader#insureServiceBind()\n3. FileDownloader#insureServiceBindAsync()";

    public static boolean start(String url, String path, boolean pathAsDirectory) {
        log("request start the task([%s], [%s], [%B]) in the download service", url, path, Boolean.valueOf(pathAsDirectory));
        return false;
    }

    public static boolean pause(int id) {
        log("request pause the task[%d] in the download service", Integer.valueOf(id));
        return false;
    }

    public static boolean isDownloading(String url, String path) {
        log("request check the task([%s], [%s]) is downloading in the download service", url, path);
        return false;
    }

    public static long getSofar(int id) {
        log("request get the downloaded so far byte for the task[%d] in the download service", Integer.valueOf(id));
        return 0;
    }

    public static long getTotal(int id) {
        log("request get the total byte for the task[%d] in the download service", Integer.valueOf(id));
        return 0;
    }

    public static byte getStatus(int id) {
        log("request get the status for the task[%d] in the download service", Integer.valueOf(id));
        return (byte) 0;
    }

    public static void pauseAllTasks() {
        log("request pause all tasks in the download service", new Object[0]);
    }

    public static boolean isIdle() {
        log("request check the download service is idle", new Object[0]);
        return true;
    }

    public static void startForeground(int notificationId, Notification notification) {
        log("request set the download service as the foreground service([%d],[%s]),", Integer.valueOf(notificationId), notification);
    }

    public static void stopForeground(boolean removeNotification) {
        log("request cancel the foreground status[%B] for the download service", Boolean.valueOf(removeNotification));
    }

    public static boolean setMaxNetworkThreadCount(int count) {
        log("request set the max network thread count[%d] in the download service", Integer.valueOf(count));
        return false;
    }

    public static boolean clearTaskData(int id) {
        log("request clear the task[%d] data in the database", Integer.valueOf(id));
        return false;
    }

    public static boolean clearAllTaskData() {
        log("request clear all tasks data in the database", new Object[0]);
        return false;
    }

    private static void log(String message, Object... args) {
        FileDownloadLog.w(DownloadServiceNotConnectedHelper.class, message + CAUSE + TIPS, args);
    }
}
