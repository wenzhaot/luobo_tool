package com.liulishuo.filedownloader.database;

import android.util.SparseArray;
import com.liulishuo.filedownloader.model.ConnectionModel;
import com.liulishuo.filedownloader.model.FileDownloadModel;
import com.liulishuo.filedownloader.util.FileDownloadHelper.DatabaseCustomMaker;
import com.liulishuo.filedownloader.util.FileDownloadLog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NoDatabaseImpl implements FileDownloadDatabase {
    final SparseArray<List<ConnectionModel>> connectionModelListMap = new SparseArray();
    final SparseArray<FileDownloadModel> downloaderModelMap = new SparseArray();

    class Maintainer implements com.liulishuo.filedownloader.database.FileDownloadDatabase.Maintainer {
        Maintainer() {
        }

        public Iterator<FileDownloadModel> iterator() {
            return new MaintainerIterator();
        }

        public void onFinishMaintain() {
        }

        public void onRemovedInvalidData(FileDownloadModel model) {
        }

        public void onRefreshedValidData(FileDownloadModel model) {
        }

        public void changeFileDownloadModelId(int oldId, FileDownloadModel modelWithNewId) {
        }
    }

    class MaintainerIterator implements Iterator<FileDownloadModel> {
        MaintainerIterator() {
        }

        public boolean hasNext() {
            return false;
        }

        public FileDownloadModel next() {
            return null;
        }

        public void remove() {
        }
    }

    public static class Maker implements DatabaseCustomMaker {
        public FileDownloadDatabase customMake() {
            return new NoDatabaseImpl();
        }
    }

    public static Maker createMaker() {
        return new Maker();
    }

    public void onTaskStart(int id) {
    }

    public FileDownloadModel find(int id) {
        return (FileDownloadModel) this.downloaderModelMap.get(id);
    }

    public List<ConnectionModel> findConnectionModel(int id) {
        List<ConnectionModel> resultList = new ArrayList();
        List<ConnectionModel> processList = (List) this.connectionModelListMap.get(id);
        if (processList != null) {
            resultList.addAll(processList);
        }
        return resultList;
    }

    public void removeConnections(int id) {
        this.connectionModelListMap.remove(id);
    }

    public void insertConnectionModel(ConnectionModel model) {
        int id = model.getId();
        List<ConnectionModel> processList = (List) this.connectionModelListMap.get(id);
        if (processList == null) {
            processList = new ArrayList();
            this.connectionModelListMap.put(id, processList);
        }
        processList.add(model);
    }

    public void updateConnectionModel(int id, int index, long currentOffset) {
        List<ConnectionModel> processList = (List) this.connectionModelListMap.get(id);
        if (processList != null) {
            for (ConnectionModel connectionModel : processList) {
                if (connectionModel.getIndex() == index) {
                    connectionModel.setCurrentOffset(currentOffset);
                    return;
                }
            }
        }
    }

    public void updateConnectionCount(int id, int count) {
    }

    public void insert(FileDownloadModel downloadModel) {
        this.downloaderModelMap.put(downloadModel.getId(), downloadModel);
    }

    public void update(FileDownloadModel downloadModel) {
        if (downloadModel == null) {
            FileDownloadLog.w(this, "update but model == null!", new Object[0]);
        } else if (find(downloadModel.getId()) != null) {
            this.downloaderModelMap.remove(downloadModel.getId());
            this.downloaderModelMap.put(downloadModel.getId(), downloadModel);
        } else {
            insert(downloadModel);
        }
    }

    public boolean remove(int id) {
        this.downloaderModelMap.remove(id);
        return true;
    }

    public void clear() {
        this.downloaderModelMap.clear();
    }

    public void updateOldEtagOverdue(int id, String newEtag, long sofar, long total, int connectionCount) {
    }

    public void updateConnected(int id, long total, String etag, String filename) {
    }

    public void updateProgress(int id, long sofarBytes) {
    }

    public void updateError(int id, Throwable throwable, long sofar) {
    }

    public void updateRetry(int id, Throwable throwable) {
    }

    public void updateCompleted(int id, long total) {
        remove(id);
    }

    public void updatePause(int id, long sofar) {
    }

    public void updatePending(int id) {
    }

    public com.liulishuo.filedownloader.database.FileDownloadDatabase.Maintainer maintainer() {
        return new Maintainer();
    }
}
