package com.liulishuo.filedownloader.connection;

import com.liulishuo.filedownloader.util.FileDownloadHelper.ConnectionCountAdapter;

public class DefaultConnectionCountAdapter implements ConnectionCountAdapter {
    private static final long FOUR_CONNECTION_UPPER_LIMIT = 104857600;
    private static final long ONE_CONNECTION_UPPER_LIMIT = 1048576;
    private static final long THREE_CONNECTION_UPPER_LIMIT = 52428800;
    private static final long TWO_CONNECTION_UPPER_LIMIT = 5242880;

    public int determineConnectionCount(int downloadId, String url, String path, long totalLength) {
        if (totalLength < ONE_CONNECTION_UPPER_LIMIT) {
            return 1;
        }
        if (totalLength < TWO_CONNECTION_UPPER_LIMIT) {
            return 2;
        }
        if (totalLength < THREE_CONNECTION_UPPER_LIMIT) {
            return 3;
        }
        if (totalLength < FOUR_CONNECTION_UPPER_LIMIT) {
            return 4;
        }
        return 5;
    }
}
