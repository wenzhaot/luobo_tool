package com.liulishuo.filedownloader.download;

import com.liulishuo.filedownloader.connection.FileDownloadConnection;
import com.liulishuo.filedownloader.util.FileDownloadProperties;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import java.net.ProtocolException;

public class ConnectionProfile {
    static final int RANGE_INFINITE = -1;
    final long contentLength;
    final long currentOffset;
    final long endOffset;
    private final boolean isForceNoRange;
    private final boolean isTrialConnect;
    final long startOffset;

    public static class ConnectionProfileBuild {
        public static ConnectionProfile buildTrialConnectionProfile() {
            return new ConnectionProfile();
        }

        public static ConnectionProfile buildTrialConnectionProfileNoRange() {
            return new ConnectionProfile(0, 0, 0, 0, true);
        }

        public static ConnectionProfile buildBeginToEndConnectionProfile(long contentLength) {
            return new ConnectionProfile(0, 0, -1, contentLength, null);
        }

        public static ConnectionProfile buildToEndConnectionProfile(long startOffset, long currentOffset, long contentLength) {
            return new ConnectionProfile(startOffset, currentOffset, -1, contentLength, null);
        }

        public static ConnectionProfile buildConnectionProfile(long startOffset, long currentOffset, long endOffset, long contentLength) {
            return new ConnectionProfile(startOffset, currentOffset, endOffset, contentLength, null);
        }
    }

    private ConnectionProfile() {
        this.startOffset = 0;
        this.currentOffset = 0;
        this.endOffset = 0;
        this.contentLength = 0;
        this.isForceNoRange = false;
        this.isTrialConnect = true;
    }

    private ConnectionProfile(long startOffset, long currentOffset, long endOffset, long contentLength) {
        this(startOffset, currentOffset, endOffset, contentLength, false);
    }

    private ConnectionProfile(long startOffset, long currentOffset, long endOffset, long contentLength, boolean isForceNoRange) {
        if (!(startOffset == 0 && endOffset == 0) && isForceNoRange) {
            throw new IllegalArgumentException();
        }
        this.startOffset = startOffset;
        this.currentOffset = currentOffset;
        this.endOffset = endOffset;
        this.contentLength = contentLength;
        this.isForceNoRange = isForceNoRange;
        this.isTrialConnect = false;
    }

    public void processProfile(FileDownloadConnection connection) throws ProtocolException {
        if (!this.isForceNoRange) {
            String range;
            if (this.isTrialConnect && FileDownloadProperties.getImpl().trialConnectionHeadMethod) {
                connection.setRequestMethod("HEAD");
            }
            if (this.endOffset == -1) {
                range = FileDownloadUtils.formatString("bytes=%d-", Long.valueOf(this.currentOffset));
            } else {
                range = FileDownloadUtils.formatString("bytes=%d-%d", Long.valueOf(this.currentOffset), Long.valueOf(this.endOffset));
            }
            connection.addHeader("Range", range);
        }
    }

    public String toString() {
        return FileDownloadUtils.formatString("range[%d, %d) current offset[%d]", Long.valueOf(this.startOffset), Long.valueOf(this.endOffset), Long.valueOf(this.currentOffset));
    }
}
