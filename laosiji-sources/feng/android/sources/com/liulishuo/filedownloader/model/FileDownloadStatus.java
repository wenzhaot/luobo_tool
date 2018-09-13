package com.liulishuo.filedownloader.model;

import com.liulishuo.filedownloader.BaseDownloadTask;

public class FileDownloadStatus {
    public static final byte INVALID_STATUS = (byte) 0;
    public static final byte blockComplete = (byte) 4;
    public static final byte completed = (byte) -3;
    public static final byte connected = (byte) 2;
    public static final byte error = (byte) -1;
    public static final byte paused = (byte) -2;
    public static final byte pending = (byte) 1;
    public static final byte progress = (byte) 3;
    public static final byte retry = (byte) 5;
    public static final byte started = (byte) 6;
    public static final byte toFileDownloadService = (byte) 11;
    public static final byte toLaunchPool = (byte) 10;
    public static final byte warn = (byte) -4;

    public static boolean isOver(int status) {
        return status < 0;
    }

    public static boolean isIng(int status) {
        return status > 0;
    }

    public static boolean isKeepAhead(int status, int nextStatus) {
        if ((status != 3 && status != 5 && status == nextStatus) || isOver(status)) {
            return false;
        }
        if (status >= 1 && status <= 6 && nextStatus >= 10 && nextStatus <= 11) {
            return false;
        }
        switch (status) {
            case 1:
                switch (nextStatus) {
                    case 0:
                        return false;
                    default:
                        return true;
                }
            case 2:
                switch (nextStatus) {
                    case 0:
                    case 1:
                    case 6:
                        return false;
                    default:
                        return true;
                }
            case 3:
                switch (nextStatus) {
                    case 0:
                    case 1:
                    case 2:
                    case 6:
                        return false;
                    default:
                        return true;
                }
            case 5:
                switch (nextStatus) {
                    case 1:
                    case 6:
                        return false;
                    default:
                        return true;
                }
            case 6:
                switch (nextStatus) {
                    case 0:
                    case 1:
                        return false;
                    default:
                        return true;
                }
            default:
                return true;
        }
    }

    public static boolean isKeepFlow(int status, int nextStatus) {
        if ((status != 3 && status != 5 && status == nextStatus) || isOver(status)) {
            return false;
        }
        if (nextStatus == -2) {
            return true;
        }
        if (nextStatus == -1) {
            return true;
        }
        switch (status) {
            case 0:
                switch (nextStatus) {
                    case 10:
                        return true;
                    default:
                        return false;
                }
            case 1:
                switch (nextStatus) {
                    case 6:
                        return true;
                    default:
                        return false;
                }
            case 2:
            case 3:
                switch (nextStatus) {
                    case -3:
                    case 3:
                    case 5:
                        return true;
                    default:
                        return false;
                }
            case 5:
            case 6:
                switch (nextStatus) {
                    case 2:
                    case 5:
                        return true;
                    default:
                        return false;
                }
            case 10:
                switch (nextStatus) {
                    case 11:
                        return true;
                    default:
                        return false;
                }
            case 11:
                switch (nextStatus) {
                    case -4:
                    case -3:
                    case 1:
                        return true;
                    default:
                        return false;
                }
            default:
                return false;
        }
    }

    public static boolean isMoreLikelyCompleted(BaseDownloadTask task) {
        return task.getStatus() == (byte) 0 || task.getStatus() == (byte) 3;
    }
}
