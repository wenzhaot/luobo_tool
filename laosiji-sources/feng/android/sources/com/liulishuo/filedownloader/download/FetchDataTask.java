package com.liulishuo.filedownloader.download;

import android.os.SystemClock;
import com.liulishuo.filedownloader.connection.FileDownloadConnection;
import com.liulishuo.filedownloader.database.FileDownloadDatabase;
import com.liulishuo.filedownloader.exception.FileDownloadGiveUpRetryException;
import com.liulishuo.filedownloader.exception.FileDownloadNetworkPolicyException;
import com.liulishuo.filedownloader.stream.FileDownloadOutputStream;
import com.liulishuo.filedownloader.util.FileDownloadLog;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import java.io.IOException;
import java.io.InputStream;

public class FetchDataTask {
    static final int BUFFER_SIZE = 4096;
    private final ProcessCallback callback;
    private final FileDownloadConnection connection;
    private final int connectionIndex;
    private final long contentLength;
    long currentOffset;
    private final FileDownloadDatabase database;
    private final int downloadId;
    private final long endOffset;
    private final DownloadRunnable hostRunnable;
    private final boolean isWifiRequired;
    private volatile long lastSyncBytes;
    private volatile long lastSyncTimestamp;
    private FileDownloadOutputStream outputStream;
    private final String path;
    private volatile boolean paused;
    private final long startOffset;

    public static class Builder {
        ProcessCallback callback;
        FileDownloadConnection connection;
        Integer connectionIndex;
        ConnectionProfile connectionProfile;
        Integer downloadId;
        DownloadRunnable downloadRunnable;
        Boolean isWifiRequired;
        String path;

        public Builder setConnection(FileDownloadConnection connection) {
            this.connection = connection;
            return this;
        }

        public Builder setConnectionProfile(ConnectionProfile connectionProfile) {
            this.connectionProfile = connectionProfile;
            return this;
        }

        public Builder setCallback(ProcessCallback callback) {
            this.callback = callback;
            return this;
        }

        public Builder setPath(String path) {
            this.path = path;
            return this;
        }

        public Builder setWifiRequired(boolean wifiRequired) {
            this.isWifiRequired = Boolean.valueOf(wifiRequired);
            return this;
        }

        public Builder setHost(DownloadRunnable downloadRunnable) {
            this.downloadRunnable = downloadRunnable;
            return this;
        }

        public Builder setConnectionIndex(int connectionIndex) {
            this.connectionIndex = Integer.valueOf(connectionIndex);
            return this;
        }

        public Builder setDownloadId(int downloadId) {
            this.downloadId = Integer.valueOf(downloadId);
            return this;
        }

        public FetchDataTask build() throws IllegalArgumentException {
            if (this.isWifiRequired != null && this.connection != null && this.connectionProfile != null && this.callback != null && this.path != null && this.downloadId != null && this.connectionIndex != null) {
                return new FetchDataTask(this.connection, this.connectionProfile, this.downloadRunnable, this.downloadId.intValue(), this.connectionIndex.intValue(), this.isWifiRequired.booleanValue(), this.callback, this.path);
            }
            throw new IllegalArgumentException();
        }
    }

    public void pause() {
        this.paused = true;
    }

    private FetchDataTask(FileDownloadConnection connection, ConnectionProfile connectionProfile, DownloadRunnable host, int id, int connectionIndex, boolean isWifiRequired, ProcessCallback callback, String path) {
        this.lastSyncBytes = 0;
        this.lastSyncTimestamp = 0;
        this.callback = callback;
        this.path = path;
        this.connection = connection;
        this.isWifiRequired = isWifiRequired;
        this.hostRunnable = host;
        this.connectionIndex = connectionIndex;
        this.downloadId = id;
        this.database = CustomComponentHolder.getImpl().getDatabaseInstance();
        this.startOffset = connectionProfile.startOffset;
        this.endOffset = connectionProfile.endOffset;
        this.currentOffset = connectionProfile.currentOffset;
        this.contentLength = connectionProfile.contentLength;
    }

    public void run() throws IOException, IllegalAccessException, IllegalArgumentException, FileDownloadGiveUpRetryException {
        if (!this.paused) {
            long contentLength = FileDownloadUtils.findContentLength(this.connectionIndex, this.connection);
            if (contentLength == -1) {
                contentLength = FileDownloadUtils.findContentLengthFromContentRange(this.connection);
            }
            if (contentLength == 0) {
                throw new FileDownloadGiveUpRetryException(FileDownloadUtils.formatString("there isn't any content need to download on %d-%d with the content-length is 0", Integer.valueOf(this.downloadId), Integer.valueOf(this.connectionIndex)));
            } else if (this.contentLength <= 0 || contentLength == this.contentLength) {
                long fetchBeginOffset = this.currentOffset;
                InputStream inputStream = null;
                FileDownloadOutputStream outputStream = null;
                boolean isSupportSeek = CustomComponentHolder.getImpl().isSupportSeek();
                if (this.hostRunnable == null || isSupportSeek) {
                    try {
                        outputStream = FileDownloadUtils.createOutputStream(this.path);
                        this.outputStream = outputStream;
                        if (isSupportSeek) {
                            outputStream.seek(this.currentOffset);
                        }
                        if (FileDownloadLog.NEED_LOG) {
                            FileDownloadLog.d(this, "start fetch(%d): range [%d, %d), seek to[%d]", Integer.valueOf(this.connectionIndex), Long.valueOf(this.startOffset), Long.valueOf(this.endOffset), Long.valueOf(this.currentOffset));
                        }
                        inputStream = this.connection.getInputStream();
                        byte[] buff = new byte[4096];
                        if (this.paused) {
                            if (inputStream != null) {
                                try {
                                    inputStream.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (outputStream != null) {
                                try {
                                    sync();
                                } catch (Throwable th) {
                                    if (outputStream != null) {
                                        try {
                                            outputStream.close();
                                        } catch (IOException e2) {
                                            e2.printStackTrace();
                                        }
                                    }
                                }
                            }
                            if (outputStream != null) {
                                try {
                                    outputStream.close();
                                    return;
                                } catch (IOException e22) {
                                    e22.printStackTrace();
                                    return;
                                }
                            }
                            return;
                        }
                        while (true) {
                            int byteCount = inputStream.read(buff);
                            if (byteCount == -1) {
                                if (inputStream != null) {
                                    try {
                                        inputStream.close();
                                    } catch (IOException e222) {
                                        e222.printStackTrace();
                                    }
                                }
                                if (outputStream != null) {
                                    try {
                                        sync();
                                    } catch (Throwable th2) {
                                        if (outputStream != null) {
                                            try {
                                                outputStream.close();
                                            } catch (IOException e2222) {
                                                e2222.printStackTrace();
                                            }
                                        }
                                    }
                                }
                                if (outputStream != null) {
                                    try {
                                        outputStream.close();
                                    } catch (IOException e22222) {
                                        e22222.printStackTrace();
                                    }
                                }
                                long fetchedLength = this.currentOffset - fetchBeginOffset;
                                if (contentLength == -1 || contentLength == fetchedLength) {
                                    this.callback.onCompleted(this.hostRunnable, this.startOffset, this.endOffset);
                                    return;
                                } else {
                                    throw new FileDownloadGiveUpRetryException(FileDownloadUtils.formatString("fetched length[%d] != content length[%d], range[%d, %d) offset[%d] fetch begin offset[%d]", Long.valueOf(fetchedLength), Long.valueOf(contentLength), Long.valueOf(this.startOffset), Long.valueOf(this.endOffset), Long.valueOf(this.currentOffset), Long.valueOf(fetchBeginOffset)));
                                }
                            }
                            outputStream.write(buff, 0, byteCount);
                            this.currentOffset += (long) byteCount;
                            this.callback.onProgress((long) byteCount);
                            checkAndSync();
                            if (this.paused) {
                                if (inputStream != null) {
                                    try {
                                        inputStream.close();
                                    } catch (IOException e222222) {
                                        e222222.printStackTrace();
                                    }
                                }
                                if (outputStream != null) {
                                    try {
                                        sync();
                                    } catch (Throwable th3) {
                                        if (outputStream != null) {
                                            try {
                                                outputStream.close();
                                            } catch (IOException e2222222) {
                                                e2222222.printStackTrace();
                                            }
                                        }
                                    }
                                }
                                if (outputStream != null) {
                                    try {
                                        outputStream.close();
                                        return;
                                    } catch (IOException e22222222) {
                                        e22222222.printStackTrace();
                                        return;
                                    }
                                }
                                return;
                            } else if (this.isWifiRequired && FileDownloadUtils.isNetworkNotOnWifiType()) {
                                throw new FileDownloadNetworkPolicyException();
                            }
                        }
                    } catch (Throwable th4) {
                        if (outputStream != null) {
                            try {
                                outputStream.close();
                            } catch (IOException e222222222) {
                                e222222222.printStackTrace();
                            }
                        }
                    }
                } else {
                    throw new IllegalAccessException("can't using multi-download when the output stream can't support seek");
                }
            } else {
                String range;
                if (this.endOffset == -1) {
                    range = FileDownloadUtils.formatString("range[%d-)", Long.valueOf(this.currentOffset));
                } else {
                    range = FileDownloadUtils.formatString("range[%d-%d)", Long.valueOf(this.currentOffset), Long.valueOf(this.endOffset));
                }
                throw new FileDownloadGiveUpRetryException(FileDownloadUtils.formatString("require %s with contentLength(%d), but the backend response contentLength is %d on downloadId[%d]-connectionIndex[%d], please ask your backend dev to fix such problem.", range, Long.valueOf(this.contentLength), Long.valueOf(contentLength), Integer.valueOf(this.downloadId), Integer.valueOf(this.connectionIndex)));
            }
        }
    }

    private void checkAndSync() {
        long now = SystemClock.elapsedRealtime();
        if (FileDownloadUtils.isNeedSync(this.currentOffset - this.lastSyncBytes, now - this.lastSyncTimestamp)) {
            sync();
            this.lastSyncBytes = this.currentOffset;
            this.lastSyncTimestamp = now;
        }
    }

    private void sync() {
        boolean bufferPersistToDevice;
        long startTimestamp = SystemClock.uptimeMillis();
        try {
            this.outputStream.flushAndSync();
            bufferPersistToDevice = true;
        } catch (IOException e) {
            bufferPersistToDevice = false;
            if (FileDownloadLog.NEED_LOG) {
                FileDownloadLog.d(this, "Because of the system cannot guarantee that all the buffers have been synchronized with physical media, or write to filefailed, we just not flushAndSync process to database too %s", e);
            }
        }
        if (bufferPersistToDevice) {
            boolean isBelongMultiConnection;
            if (this.connectionIndex >= 0) {
                isBelongMultiConnection = true;
            } else {
                isBelongMultiConnection = false;
            }
            if (isBelongMultiConnection) {
                this.database.updateConnectionModel(this.downloadId, this.connectionIndex, this.currentOffset);
            } else {
                this.callback.syncProgressFromCache();
            }
            if (FileDownloadLog.NEED_LOG) {
                FileDownloadLog.d(this, "require flushAndSync id[%d] index[%d] offset[%d], consume[%d]", Integer.valueOf(this.downloadId), Integer.valueOf(this.connectionIndex), Long.valueOf(this.currentOffset), Long.valueOf(SystemClock.uptimeMillis() - startTimestamp));
            }
        }
    }
}
