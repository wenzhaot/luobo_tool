package com.liulishuo.filedownloader.database;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Message;
import com.liulishuo.filedownloader.database.FileDownloadDatabase.Maintainer;
import com.liulishuo.filedownloader.model.ConnectionModel;
import com.liulishuo.filedownloader.model.FileDownloadModel;
import com.liulishuo.filedownloader.util.FileDownloadHelper.DatabaseCustomMaker;
import com.liulishuo.filedownloader.util.FileDownloadLog;
import com.liulishuo.filedownloader.util.FileDownloadProperties;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

public class RemitDatabase implements FileDownloadDatabase {
    private static final int WHAT_CLEAN_LOCK = 0;
    private final NoDatabaseImpl cachedDatabase = new NoDatabaseImpl();
    private final List<Integer> freeToDBIdList = new ArrayList();
    private Handler handler;
    private AtomicInteger handlingId = new AtomicInteger();
    private final long minInterval = FileDownloadProperties.getImpl().downloadMinProgressTime;
    private volatile Thread parkThread;
    private final SqliteDatabaseImpl realDatabase = new SqliteDatabaseImpl();

    public static class Maker implements DatabaseCustomMaker {
        public FileDownloadDatabase customMake() {
            return new RemitDatabase();
        }
    }

    public RemitDatabase() {
        HandlerThread thread = new HandlerThread(FileDownloadUtils.getThreadPoolName("RemitHandoverToDB"));
        thread.start();
        this.handler = new Handler(thread.getLooper(), new Callback() {
            public boolean handleMessage(Message msg) {
                int id = msg.what;
                if (id != 0) {
                    try {
                        RemitDatabase.this.handlingId.set(id);
                        RemitDatabase.this.syncCacheToDB(id);
                        RemitDatabase.this.freeToDBIdList.add(Integer.valueOf(id));
                        RemitDatabase.this.handlingId.set(0);
                        if (RemitDatabase.this.parkThread != null) {
                            LockSupport.unpark(RemitDatabase.this.parkThread);
                            RemitDatabase.this.parkThread = null;
                        }
                    } catch (Throwable th) {
                        RemitDatabase.this.handlingId.set(0);
                        if (RemitDatabase.this.parkThread != null) {
                            LockSupport.unpark(RemitDatabase.this.parkThread);
                            RemitDatabase.this.parkThread = null;
                        }
                    }
                } else if (RemitDatabase.this.parkThread != null) {
                    LockSupport.unpark(RemitDatabase.this.parkThread);
                    RemitDatabase.this.parkThread = null;
                }
                return false;
            }
        });
    }

    private void syncCacheToDB(int id) {
        if (FileDownloadLog.NEED_LOG) {
            FileDownloadLog.d(this, "sync cache to db %d", Integer.valueOf(id));
        }
        this.realDatabase.update(this.cachedDatabase.find(id));
        List<ConnectionModel> modelList = this.cachedDatabase.findConnectionModel(id);
        this.realDatabase.removeConnections(id);
        for (ConnectionModel connectionModel : modelList) {
            this.realDatabase.insertConnectionModel(connectionModel);
        }
    }

    private boolean isNoNeedUpdateToRealDB(int id) {
        return !this.freeToDBIdList.contains(Integer.valueOf(id));
    }

    public void onTaskStart(int id) {
        this.handler.sendEmptyMessageDelayed(id, this.minInterval);
    }

    public FileDownloadModel find(int id) {
        return this.cachedDatabase.find(id);
    }

    public List<ConnectionModel> findConnectionModel(int id) {
        return this.cachedDatabase.findConnectionModel(id);
    }

    public void removeConnections(int id) {
        this.cachedDatabase.removeConnections(id);
        if (!isNoNeedUpdateToRealDB(id)) {
            this.realDatabase.removeConnections(id);
        }
    }

    public void insertConnectionModel(ConnectionModel model) {
        this.cachedDatabase.insertConnectionModel(model);
        if (!isNoNeedUpdateToRealDB(model.getId())) {
            this.realDatabase.insertConnectionModel(model);
        }
    }

    public void updateConnectionModel(int id, int index, long currentOffset) {
        this.cachedDatabase.updateConnectionModel(id, index, currentOffset);
        if (!isNoNeedUpdateToRealDB(id)) {
            this.realDatabase.updateConnectionModel(id, index, currentOffset);
        }
    }

    public void updateProgress(int id, long sofarBytes) {
        this.cachedDatabase.updateProgress(id, sofarBytes);
        if (!isNoNeedUpdateToRealDB(id)) {
            this.realDatabase.updateProgress(id, sofarBytes);
        }
    }

    public void updateConnectionCount(int id, int count) {
        this.cachedDatabase.updateConnectionCount(id, count);
        if (!isNoNeedUpdateToRealDB(id)) {
            this.realDatabase.updateConnectionCount(id, count);
        }
    }

    public void insert(FileDownloadModel downloadModel) {
        this.cachedDatabase.insert(downloadModel);
        if (!isNoNeedUpdateToRealDB(downloadModel.getId())) {
            this.realDatabase.insert(downloadModel);
        }
    }

    public void update(FileDownloadModel downloadModel) {
        this.cachedDatabase.update(downloadModel);
        if (!isNoNeedUpdateToRealDB(downloadModel.getId())) {
            this.realDatabase.update(downloadModel);
        }
    }

    public boolean remove(int id) {
        this.realDatabase.remove(id);
        return this.cachedDatabase.remove(id);
    }

    public void clear() {
        this.cachedDatabase.clear();
        this.realDatabase.clear();
    }

    public void updateOldEtagOverdue(int id, String newEtag, long sofar, long total, int connectionCount) {
        this.cachedDatabase.updateOldEtagOverdue(id, newEtag, sofar, total, connectionCount);
        if (!isNoNeedUpdateToRealDB(id)) {
            this.realDatabase.updateOldEtagOverdue(id, newEtag, sofar, total, connectionCount);
        }
    }

    public void updateConnected(int id, long total, String etag, String filename) {
        this.cachedDatabase.updateConnected(id, total, etag, filename);
        if (!isNoNeedUpdateToRealDB(id)) {
            this.realDatabase.updateConnected(id, total, etag, filename);
        }
    }

    public void updatePending(int id) {
        this.cachedDatabase.updatePending(id);
        if (!isNoNeedUpdateToRealDB(id)) {
            this.realDatabase.updatePending(id);
        }
    }

    public void updateRetry(int id, Throwable throwable) {
        this.cachedDatabase.updateRetry(id, throwable);
        if (!isNoNeedUpdateToRealDB(id)) {
            this.realDatabase.updateRetry(id, throwable);
        }
    }

    private void ensureCacheToDB(int id) {
        this.handler.removeMessages(id);
        if (this.handlingId.get() == id) {
            this.parkThread = Thread.currentThread();
            this.handler.sendEmptyMessage(0);
            LockSupport.park();
            return;
        }
        syncCacheToDB(id);
    }

    public void updateError(int id, Throwable throwable, long sofar) {
        this.cachedDatabase.updateError(id, throwable, sofar);
        if (isNoNeedUpdateToRealDB(id)) {
            ensureCacheToDB(id);
        }
        this.realDatabase.updateError(id, throwable, sofar);
        this.freeToDBIdList.remove(Integer.valueOf(id));
    }

    public void updateCompleted(int id, long total) {
        this.cachedDatabase.updateCompleted(id, total);
        if (isNoNeedUpdateToRealDB(id)) {
            this.handler.removeMessages(id);
            if (this.handlingId.get() == id) {
                this.parkThread = Thread.currentThread();
                this.handler.sendEmptyMessage(0);
                LockSupport.park();
                this.realDatabase.updateCompleted(id, total);
            }
        } else {
            this.realDatabase.updateCompleted(id, total);
        }
        this.freeToDBIdList.remove(Integer.valueOf(id));
    }

    public void updatePause(int id, long sofar) {
        this.cachedDatabase.updatePause(id, sofar);
        if (isNoNeedUpdateToRealDB(id)) {
            ensureCacheToDB(id);
        }
        this.realDatabase.updatePause(id, sofar);
        this.freeToDBIdList.remove(Integer.valueOf(id));
    }

    public Maintainer maintainer() {
        return this.realDatabase.maintainer(this.cachedDatabase.downloaderModelMap, this.cachedDatabase.connectionModelListMap);
    }
}
