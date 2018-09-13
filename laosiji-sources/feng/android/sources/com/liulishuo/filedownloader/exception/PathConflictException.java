package com.liulishuo.filedownloader.exception;

import com.liulishuo.filedownloader.util.FileDownloadUtils;

public class PathConflictException extends IllegalAccessException {
    private final int mAnotherSamePathTaskId;
    private final String mDownloadingConflictPath;
    private final String mTargetFilePath;

    public PathConflictException(int anotherSamePathTaskId, String conflictPath, String targetFilePath) {
        super(FileDownloadUtils.formatString("There is an another running task(%d) with the same downloading path(%s), because of they are with the same target-file-path(%s), so if the current task is started, the path of the file is sure to be written by multiple tasks, it is wrong, then you receive this exception to avoid such conflict.", Integer.valueOf(anotherSamePathTaskId), conflictPath, targetFilePath));
        this.mAnotherSamePathTaskId = anotherSamePathTaskId;
        this.mDownloadingConflictPath = conflictPath;
        this.mTargetFilePath = targetFilePath;
    }

    public String getDownloadingConflictPath() {
        return this.mDownloadingConflictPath;
    }

    public String getTargetFilePath() {
        return this.mTargetFilePath;
    }

    public int getAnotherSamePathTaskId() {
        return this.mAnotherSamePathTaskId;
    }
}
