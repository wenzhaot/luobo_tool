package com.liulishuo.filedownloader.services;

import com.liulishuo.filedownloader.connection.DefaultConnectionCountAdapter;
import com.liulishuo.filedownloader.connection.FileDownloadUrlConnection;
import com.liulishuo.filedownloader.database.FileDownloadDatabase;
import com.liulishuo.filedownloader.database.RemitDatabase;
import com.liulishuo.filedownloader.stream.FileDownloadRandomAccessFile.Creator;
import com.liulishuo.filedownloader.util.FileDownloadHelper.ConnectionCountAdapter;
import com.liulishuo.filedownloader.util.FileDownloadHelper.ConnectionCreator;
import com.liulishuo.filedownloader.util.FileDownloadHelper.DatabaseCustomMaker;
import com.liulishuo.filedownloader.util.FileDownloadHelper.IdGenerator;
import com.liulishuo.filedownloader.util.FileDownloadHelper.OutputStreamCreator;
import com.liulishuo.filedownloader.util.FileDownloadLog;
import com.liulishuo.filedownloader.util.FileDownloadProperties;
import com.liulishuo.filedownloader.util.FileDownloadUtils;

public class DownloadMgrInitialParams {
    private final InitCustomMaker mMaker;

    public static class InitCustomMaker {
        ConnectionCountAdapter mConnectionCountAdapter;
        ConnectionCreator mConnectionCreator;
        DatabaseCustomMaker mDatabaseCustomMaker;
        IdGenerator mIdGenerator;
        Integer mMaxNetworkThreadCount;
        OutputStreamCreator mOutputStreamCreator;

        public InitCustomMaker idGenerator(IdGenerator idGenerator) {
            this.mIdGenerator = idGenerator;
            return this;
        }

        public InitCustomMaker connectionCountAdapter(ConnectionCountAdapter adapter) {
            this.mConnectionCountAdapter = adapter;
            return this;
        }

        public InitCustomMaker database(DatabaseCustomMaker maker) {
            this.mDatabaseCustomMaker = maker;
            return this;
        }

        public InitCustomMaker maxNetworkThreadCount(int maxNetworkThreadCount) {
            if (maxNetworkThreadCount > 0) {
                this.mMaxNetworkThreadCount = Integer.valueOf(maxNetworkThreadCount);
            }
            return this;
        }

        public InitCustomMaker outputStreamCreator(OutputStreamCreator creator) {
            this.mOutputStreamCreator = creator;
            if (this.mOutputStreamCreator == null || this.mOutputStreamCreator.supportSeek() || FileDownloadProperties.getImpl().fileNonPreAllocation) {
                return this;
            }
            throw new IllegalArgumentException("Since the provided FileDownloadOutputStream does not support the seek function, if FileDownloader pre-allocates file size at the beginning of the download, it will can not be resumed from the breakpoint. If you need to ensure that the resumption is available, please add and set the value of 'file.non-pre-allocation' field to 'true' in the 'filedownloader.properties' file which is in your application assets folder manually for resolving this problem.");
        }

        public InitCustomMaker connectionCreator(ConnectionCreator creator) {
            this.mConnectionCreator = creator;
            return this;
        }

        public void commit() {
        }

        public String toString() {
            return FileDownloadUtils.formatString("component: database[%s], maxNetworkCount[%s], outputStream[%s], connection[%s], connectionCountAdapter[%s]", this.mDatabaseCustomMaker, this.mMaxNetworkThreadCount, this.mOutputStreamCreator, this.mConnectionCreator, this.mConnectionCountAdapter);
        }
    }

    public DownloadMgrInitialParams() {
        this.mMaker = null;
    }

    public DownloadMgrInitialParams(InitCustomMaker maker) {
        this.mMaker = maker;
    }

    public int getMaxNetworkThreadCount() {
        if (this.mMaker == null) {
            return getDefaultMaxNetworkThreadCount();
        }
        Integer customizeMaxNetworkThreadCount = this.mMaker.mMaxNetworkThreadCount;
        if (customizeMaxNetworkThreadCount == null) {
            return getDefaultMaxNetworkThreadCount();
        }
        if (FileDownloadLog.NEED_LOG) {
            FileDownloadLog.d(this, "initial FileDownloader manager with the customize maxNetworkThreadCount: %d", customizeMaxNetworkThreadCount);
        }
        return FileDownloadProperties.getValidNetworkThreadCount(customizeMaxNetworkThreadCount.intValue());
    }

    public FileDownloadDatabase createDatabase() {
        if (this.mMaker == null || this.mMaker.mDatabaseCustomMaker == null) {
            return createDefaultDatabase();
        }
        FileDownloadDatabase customDatabase = this.mMaker.mDatabaseCustomMaker.customMake();
        if (customDatabase == null) {
            return createDefaultDatabase();
        }
        if (!FileDownloadLog.NEED_LOG) {
            return customDatabase;
        }
        FileDownloadLog.d(this, "initial FileDownloader manager with the customize database: %s", customDatabase);
        return customDatabase;
    }

    public OutputStreamCreator createOutputStreamCreator() {
        if (this.mMaker == null) {
            return createDefaultOutputStreamCreator();
        }
        OutputStreamCreator outputStreamCreator = this.mMaker.mOutputStreamCreator;
        if (outputStreamCreator == null) {
            return createDefaultOutputStreamCreator();
        }
        if (!FileDownloadLog.NEED_LOG) {
            return outputStreamCreator;
        }
        FileDownloadLog.d(this, "initial FileDownloader manager with the customize output stream: %s", outputStreamCreator);
        return outputStreamCreator;
    }

    public ConnectionCreator createConnectionCreator() {
        if (this.mMaker == null) {
            return createDefaultConnectionCreator();
        }
        ConnectionCreator connectionCreator = this.mMaker.mConnectionCreator;
        if (connectionCreator == null) {
            return createDefaultConnectionCreator();
        }
        if (!FileDownloadLog.NEED_LOG) {
            return connectionCreator;
        }
        FileDownloadLog.d(this, "initial FileDownloader manager with the customize connection creator: %s", connectionCreator);
        return connectionCreator;
    }

    public ConnectionCountAdapter createConnectionCountAdapter() {
        if (this.mMaker == null) {
            return createDefaultConnectionCountAdapter();
        }
        ConnectionCountAdapter adapter = this.mMaker.mConnectionCountAdapter;
        if (adapter == null) {
            return createDefaultConnectionCountAdapter();
        }
        if (!FileDownloadLog.NEED_LOG) {
            return adapter;
        }
        FileDownloadLog.d(this, "initial FileDownloader manager with the customize connection count adapter: %s", adapter);
        return adapter;
    }

    public IdGenerator createIdGenerator() {
        if (this.mMaker == null) {
            return createDefaultIdGenerator();
        }
        IdGenerator idGenerator = this.mMaker.mIdGenerator;
        if (idGenerator == null) {
            return createDefaultIdGenerator();
        }
        if (!FileDownloadLog.NEED_LOG) {
            return idGenerator;
        }
        FileDownloadLog.d(this, "initial FileDownloader manager with the customize id generator: %s", idGenerator);
        return idGenerator;
    }

    private IdGenerator createDefaultIdGenerator() {
        return new DefaultIdGenerator();
    }

    private int getDefaultMaxNetworkThreadCount() {
        return FileDownloadProperties.getImpl().downloadMaxNetworkThreadCount;
    }

    private FileDownloadDatabase createDefaultDatabase() {
        return new RemitDatabase();
    }

    private OutputStreamCreator createDefaultOutputStreamCreator() {
        return new Creator();
    }

    private ConnectionCreator createDefaultConnectionCreator() {
        return new FileDownloadUrlConnection.Creator();
    }

    private ConnectionCountAdapter createDefaultConnectionCountAdapter() {
        return new DefaultConnectionCountAdapter();
    }
}
