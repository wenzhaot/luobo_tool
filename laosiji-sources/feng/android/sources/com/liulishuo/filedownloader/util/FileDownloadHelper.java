package com.liulishuo.filedownloader.util;

import android.annotation.SuppressLint;
import android.content.Context;
import com.liulishuo.filedownloader.IThreadPoolMonitor;
import com.liulishuo.filedownloader.connection.FileDownloadConnection;
import com.liulishuo.filedownloader.database.FileDownloadDatabase;
import com.liulishuo.filedownloader.exception.PathConflictException;
import com.liulishuo.filedownloader.message.MessageSnapshotFlow;
import com.liulishuo.filedownloader.message.MessageSnapshotTaker;
import com.liulishuo.filedownloader.model.FileDownloadModel;
import com.liulishuo.filedownloader.stream.FileDownloadOutputStream;
import java.io.File;
import java.io.IOException;

public class FileDownloadHelper {
    @SuppressLint({"StaticFieldLeak"})
    private static Context APP_CONTEXT;

    public interface ConnectionCountAdapter {
        int determineConnectionCount(int i, String str, String str2, long j);
    }

    public interface ConnectionCreator {
        FileDownloadConnection create(String str) throws IOException;
    }

    public interface DatabaseCustomMaker {
        FileDownloadDatabase customMake();
    }

    public interface IdGenerator {
        int generateId(String str, String str2, boolean z);

        int transOldId(int i, String str, String str2, boolean z);
    }

    public interface OutputStreamCreator {
        FileDownloadOutputStream create(File file) throws IOException;

        boolean supportSeek();
    }

    public static void holdContext(Context context) {
        APP_CONTEXT = context;
    }

    public static Context getAppContext() {
        return APP_CONTEXT;
    }

    public static boolean inspectAndInflowDownloaded(int id, String path, boolean forceReDownload, boolean flowDirectly) {
        if (forceReDownload || path == null) {
            return false;
        }
        File file = new File(path);
        if (!file.exists()) {
            return false;
        }
        MessageSnapshotFlow.getImpl().inflow(MessageSnapshotTaker.catchCanReusedOldFile(id, file, flowDirectly));
        return true;
    }

    public static boolean inspectAndInflowDownloading(int id, FileDownloadModel model, IThreadPoolMonitor monitor, boolean flowDirectly) {
        if (!monitor.isDownloading(model)) {
            return false;
        }
        MessageSnapshotFlow.getImpl().inflow(MessageSnapshotTaker.catchWarn(id, model.getSoFar(), model.getTotal(), flowDirectly));
        return true;
    }

    public static boolean inspectAndInflowConflictPath(int id, long sofar, String tempFilePath, String targetFilePath, IThreadPoolMonitor monitor) {
        if (!(targetFilePath == null || tempFilePath == null)) {
            int anotherSameTempPathTaskId = monitor.findRunningTaskIdBySameTempPath(tempFilePath, id);
            if (anotherSameTempPathTaskId != 0) {
                MessageSnapshotFlow.getImpl().inflow(MessageSnapshotTaker.catchException(id, sofar, new PathConflictException(anotherSameTempPathTaskId, tempFilePath, targetFilePath)));
                return true;
            }
        }
        return false;
    }
}
