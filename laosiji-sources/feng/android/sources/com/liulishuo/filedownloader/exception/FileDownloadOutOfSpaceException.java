package com.liulishuo.filedownloader.exception;

import android.annotation.TargetApi;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import java.io.IOException;

public class FileDownloadOutOfSpaceException extends IOException {
    private long breakpointBytes;
    private long freeSpaceBytes;
    private long requiredSpaceBytes;

    @TargetApi(9)
    public FileDownloadOutOfSpaceException(long freeSpaceBytes, long requiredSpaceBytes, long breakpointBytes, Throwable cause) {
        super(FileDownloadUtils.formatString("The file is too large to store, breakpoint in bytes:  %d, required space in bytes: %d, but free space in bytes: %d", Long.valueOf(breakpointBytes), Long.valueOf(requiredSpaceBytes), Long.valueOf(freeSpaceBytes)), cause);
        init(freeSpaceBytes, requiredSpaceBytes, breakpointBytes);
    }

    public FileDownloadOutOfSpaceException(long freeSpaceBytes, long requiredSpaceBytes, long breakpointBytes) {
        super(FileDownloadUtils.formatString("The file is too large to store, breakpoint in bytes:  %d, required space in bytes: %d, but free space in bytes: %d", Long.valueOf(breakpointBytes), Long.valueOf(requiredSpaceBytes), Long.valueOf(freeSpaceBytes)));
        init(freeSpaceBytes, requiredSpaceBytes, breakpointBytes);
    }

    private void init(long freeSpaceBytes, long requiredSpaceBytes, long breakpointBytes) {
        this.freeSpaceBytes = freeSpaceBytes;
        this.requiredSpaceBytes = requiredSpaceBytes;
        this.breakpointBytes = breakpointBytes;
    }

    public long getFreeSpaceBytes() {
        return this.freeSpaceBytes;
    }

    public long getRequiredSpaceBytes() {
        return this.requiredSpaceBytes;
    }

    public long getBreakpointBytes() {
        return this.breakpointBytes;
    }
}
