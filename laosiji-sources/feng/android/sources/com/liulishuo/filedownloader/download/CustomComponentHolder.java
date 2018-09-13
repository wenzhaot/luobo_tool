package com.liulishuo.filedownloader.download;

import com.liulishuo.filedownloader.connection.FileDownloadConnection;
import com.liulishuo.filedownloader.database.FileDownloadDatabase;
import com.liulishuo.filedownloader.database.FileDownloadDatabase.Maintainer;
import com.liulishuo.filedownloader.model.FileDownloadModel;
import com.liulishuo.filedownloader.services.DownloadMgrInitialParams;
import com.liulishuo.filedownloader.services.DownloadMgrInitialParams.InitCustomMaker;
import com.liulishuo.filedownloader.stream.FileDownloadOutputStream;
import com.liulishuo.filedownloader.util.FileDownloadHelper;
import com.liulishuo.filedownloader.util.FileDownloadHelper.ConnectionCountAdapter;
import com.liulishuo.filedownloader.util.FileDownloadHelper.ConnectionCreator;
import com.liulishuo.filedownloader.util.FileDownloadHelper.IdGenerator;
import com.liulishuo.filedownloader.util.FileDownloadHelper.OutputStreamCreator;
import com.liulishuo.filedownloader.util.FileDownloadLog;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class CustomComponentHolder {
    private ConnectionCountAdapter connectionCountAdapter;
    private ConnectionCreator connectionCreator;
    private FileDownloadDatabase database;
    private IdGenerator idGenerator;
    private DownloadMgrInitialParams initialParams;
    private OutputStreamCreator outputStreamCreator;

    private static final class LazyLoader {
        private static final CustomComponentHolder INSTANCE = new CustomComponentHolder();

        private LazyLoader() {
        }
    }

    public static CustomComponentHolder getImpl() {
        return LazyLoader.INSTANCE;
    }

    public void setInitCustomMaker(InitCustomMaker initCustomMaker) {
        synchronized (this) {
            this.initialParams = new DownloadMgrInitialParams(initCustomMaker);
            this.connectionCreator = null;
            this.outputStreamCreator = null;
            this.database = null;
            this.idGenerator = null;
        }
    }

    public FileDownloadConnection createConnection(String url) throws IOException {
        return getConnectionCreator().create(url);
    }

    public FileDownloadOutputStream createOutputStream(File file) throws IOException {
        return getOutputStreamCreator().create(file);
    }

    public IdGenerator getIdGeneratorInstance() {
        if (this.idGenerator != null) {
            return this.idGenerator;
        }
        synchronized (this) {
            if (this.idGenerator == null) {
                this.idGenerator = getDownloadMgrInitialParams().createIdGenerator();
            }
        }
        return this.idGenerator;
    }

    public FileDownloadDatabase getDatabaseInstance() {
        if (this.database != null) {
            return this.database;
        }
        synchronized (this) {
            if (this.database == null) {
                this.database = getDownloadMgrInitialParams().createDatabase();
                maintainDatabase(this.database.maintainer());
            }
        }
        return this.database;
    }

    public int getMaxNetworkThreadCount() {
        return getDownloadMgrInitialParams().getMaxNetworkThreadCount();
    }

    public boolean isSupportSeek() {
        return getOutputStreamCreator().supportSeek();
    }

    public int determineConnectionCount(int downloadId, String url, String path, long totalLength) {
        return getConnectionCountAdapter().determineConnectionCount(downloadId, url, path, totalLength);
    }

    private ConnectionCountAdapter getConnectionCountAdapter() {
        if (this.connectionCountAdapter != null) {
            return this.connectionCountAdapter;
        }
        synchronized (this) {
            if (this.connectionCountAdapter == null) {
                this.connectionCountAdapter = getDownloadMgrInitialParams().createConnectionCountAdapter();
            }
        }
        return this.connectionCountAdapter;
    }

    private ConnectionCreator getConnectionCreator() {
        if (this.connectionCreator != null) {
            return this.connectionCreator;
        }
        synchronized (this) {
            if (this.connectionCreator == null) {
                this.connectionCreator = getDownloadMgrInitialParams().createConnectionCreator();
            }
        }
        return this.connectionCreator;
    }

    private OutputStreamCreator getOutputStreamCreator() {
        if (this.outputStreamCreator != null) {
            return this.outputStreamCreator;
        }
        synchronized (this) {
            if (this.outputStreamCreator == null) {
                this.outputStreamCreator = getDownloadMgrInitialParams().createOutputStreamCreator();
            }
        }
        return this.outputStreamCreator;
    }

    private DownloadMgrInitialParams getDownloadMgrInitialParams() {
        if (this.initialParams != null) {
            return this.initialParams;
        }
        synchronized (this) {
            if (this.initialParams == null) {
                this.initialParams = new DownloadMgrInitialParams();
            }
        }
        return this.initialParams;
    }

    private static void maintainDatabase(Maintainer maintainer) {
        Iterator<FileDownloadModel> iterator = maintainer.iterator();
        long refreshDataCount = 0;
        long removedDataCount = 0;
        long resetIdCount = 0;
        IdGenerator idGenerator = getImpl().getIdGeneratorInstance();
        long startTimestamp = System.currentTimeMillis();
        while (iterator.hasNext()) {
            try {
                boolean isInvalid = false;
                FileDownloadModel model = (FileDownloadModel) iterator.next();
                if (model.getStatus() == (byte) 3 || model.getStatus() == (byte) 2 || model.getStatus() == (byte) -1 || (model.getStatus() == (byte) 1 && model.getSoFar() > 0)) {
                    model.setStatus((byte) -2);
                }
                String targetFilePath = model.getTargetFilePath();
                if (targetFilePath == null) {
                    isInvalid = true;
                } else {
                    File targetFile = new File(targetFilePath);
                    if (model.getStatus() == (byte) -2) {
                        if (FileDownloadUtils.isBreakpointAvailable(model.getId(), model, model.getPath(), null)) {
                            File tempFile = new File(model.getTempFilePath());
                            if (!tempFile.exists() && targetFile.exists()) {
                                boolean successRename = targetFile.renameTo(tempFile);
                                if (FileDownloadLog.NEED_LOG) {
                                    FileDownloadLog.d(FileDownloadDatabase.class, "resume from the old no-temp-file architecture [%B], [%s]->[%s]", Boolean.valueOf(successRename), targetFile.getPath(), tempFile.getPath());
                                }
                            }
                        }
                    }
                    if (model.getStatus() == (byte) 1 && model.getSoFar() <= 0) {
                        isInvalid = true;
                    } else if (!FileDownloadUtils.isBreakpointAvailable(model.getId(), model)) {
                        isInvalid = true;
                    } else if (targetFile.exists()) {
                        isInvalid = true;
                    }
                }
                if (isInvalid) {
                    iterator.remove();
                    maintainer.onRemovedInvalidData(model);
                    removedDataCount++;
                } else {
                    int oldId = model.getId();
                    int newId = idGenerator.transOldId(oldId, model.getUrl(), model.getPath(), model.isPathAsDirectory());
                    if (newId != oldId) {
                        if (FileDownloadLog.NEED_LOG) {
                            FileDownloadLog.d(FileDownloadDatabase.class, "the id is changed on restoring from db: old[%d] -> new[%d]", Integer.valueOf(oldId), Integer.valueOf(newId));
                        }
                        model.setId(newId);
                        maintainer.changeFileDownloadModelId(oldId, model);
                        resetIdCount++;
                    }
                    maintainer.onRefreshedValidData(model);
                    refreshDataCount++;
                }
            } catch (Throwable th) {
                FileDownloadUtils.markConverted(FileDownloadHelper.getAppContext());
                maintainer.onFinishMaintain();
                if (FileDownloadLog.NEED_LOG) {
                    FileDownloadLog.d(FileDownloadDatabase.class, "refreshed data count: %d , delete data count: %d, reset id count: %d. consume %d", Long.valueOf(refreshDataCount), Long.valueOf(removedDataCount), Long.valueOf(resetIdCount), Long.valueOf(System.currentTimeMillis() - startTimestamp));
                }
            }
        }
        FileDownloadUtils.markConverted(FileDownloadHelper.getAppContext());
        maintainer.onFinishMaintain();
        if (FileDownloadLog.NEED_LOG) {
            FileDownloadLog.d(FileDownloadDatabase.class, "refreshed data count: %d , delete data count: %d, reset id count: %d. consume %d", Long.valueOf(refreshDataCount), Long.valueOf(removedDataCount), Long.valueOf(resetIdCount), Long.valueOf(System.currentTimeMillis() - startTimestamp));
        }
    }
}
