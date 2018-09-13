package com.liulishuo.filedownloader.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.SparseArray;
import com.liulishuo.filedownloader.model.ConnectionModel;
import com.liulishuo.filedownloader.model.FileDownloadModel;
import com.liulishuo.filedownloader.util.FileDownloadHelper;
import com.liulishuo.filedownloader.util.FileDownloadHelper.DatabaseCustomMaker;
import com.liulishuo.filedownloader.util.FileDownloadLog;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SqliteDatabaseImpl implements FileDownloadDatabase {
    public static final String CONNECTION_TABLE_NAME = "filedownloaderConnection";
    public static final String TABLE_NAME = "filedownloader";
    private final SQLiteDatabase db = new SqliteDatabaseOpenHelper(FileDownloadHelper.getAppContext()).getWritableDatabase();

    public class Maintainer implements com.liulishuo.filedownloader.database.FileDownloadDatabase.Maintainer {
        private final SparseArray<List<ConnectionModel>> connectionModelListMap;
        private MaintainerIterator currentIterator;
        private final SparseArray<FileDownloadModel> downloaderModelMap;
        private final SparseArray<FileDownloadModel> needChangeIdList;

        Maintainer(SqliteDatabaseImpl this$0) {
            this(null, null);
        }

        Maintainer(SparseArray<FileDownloadModel> downloaderModelMap, SparseArray<List<ConnectionModel>> connectionModelListMap) {
            this.needChangeIdList = new SparseArray();
            this.downloaderModelMap = downloaderModelMap;
            this.connectionModelListMap = connectionModelListMap;
        }

        public Iterator<FileDownloadModel> iterator() {
            Iterator maintainerIterator = new MaintainerIterator();
            this.currentIterator = maintainerIterator;
            return maintainerIterator;
        }

        public void onFinishMaintain() {
            if (this.currentIterator != null) {
                this.currentIterator.onFinishMaintain();
            }
            int length = this.needChangeIdList.size();
            if (length >= 0) {
                List<ConnectionModel> connectionModelList;
                SqliteDatabaseImpl.this.db.beginTransaction();
                int i = 0;
                while (i < length) {
                    try {
                        int oldId = this.needChangeIdList.keyAt(i);
                        FileDownloadModel modelWithNewId = (FileDownloadModel) this.needChangeIdList.get(oldId);
                        SqliteDatabaseImpl.this.db.delete(SqliteDatabaseImpl.TABLE_NAME, "_id = ?", new String[]{String.valueOf(oldId)});
                        SqliteDatabaseImpl.this.db.insert(SqliteDatabaseImpl.TABLE_NAME, null, modelWithNewId.toContentValues());
                        if (modelWithNewId.getConnectionCount() > 1) {
                            connectionModelList = SqliteDatabaseImpl.this.findConnectionModel(oldId);
                            if (connectionModelList.size() > 0) {
                                SqliteDatabaseImpl.this.db.delete(SqliteDatabaseImpl.CONNECTION_TABLE_NAME, "id = ?", new String[]{String.valueOf(oldId)});
                                for (ConnectionModel connectionModel : connectionModelList) {
                                    connectionModel.setId(modelWithNewId.getId());
                                    SqliteDatabaseImpl.this.db.insert(SqliteDatabaseImpl.CONNECTION_TABLE_NAME, null, connectionModel.toContentValues());
                                }
                            }
                        }
                        i++;
                    } catch (Throwable th) {
                        SqliteDatabaseImpl.this.db.endTransaction();
                    }
                }
                if (!(this.downloaderModelMap == null || this.connectionModelListMap == null)) {
                    int size = this.downloaderModelMap.size();
                    for (i = 0; i < size; i++) {
                        int id = ((FileDownloadModel) this.downloaderModelMap.valueAt(i)).getId();
                        connectionModelList = SqliteDatabaseImpl.this.findConnectionModel(id);
                        if (connectionModelList != null && connectionModelList.size() > 0) {
                            this.connectionModelListMap.put(id, connectionModelList);
                        }
                    }
                }
                SqliteDatabaseImpl.this.db.setTransactionSuccessful();
                SqliteDatabaseImpl.this.db.endTransaction();
            }
        }

        public void onRemovedInvalidData(FileDownloadModel model) {
        }

        public void onRefreshedValidData(FileDownloadModel model) {
            if (this.downloaderModelMap != null) {
                this.downloaderModelMap.put(model.getId(), model);
            }
        }

        public void changeFileDownloadModelId(int oldId, FileDownloadModel modelWithNewId) {
            this.needChangeIdList.put(oldId, modelWithNewId);
        }
    }

    class MaintainerIterator implements Iterator<FileDownloadModel> {
        private final Cursor c;
        private int currentId;
        private final List<Integer> needRemoveId = new ArrayList();

        MaintainerIterator() {
            this.c = SqliteDatabaseImpl.this.db.rawQuery("SELECT * FROM filedownloader", null);
        }

        public boolean hasNext() {
            return this.c.moveToNext();
        }

        public FileDownloadModel next() {
            FileDownloadModel model = SqliteDatabaseImpl.createFromCursor(this.c);
            this.currentId = model.getId();
            return model;
        }

        public void remove() {
            this.needRemoveId.add(Integer.valueOf(this.currentId));
        }

        void onFinishMaintain() {
            this.c.close();
            if (!this.needRemoveId.isEmpty()) {
                String args = TextUtils.join(", ", this.needRemoveId);
                if (FileDownloadLog.NEED_LOG) {
                    FileDownloadLog.d(this, "delete %s", args);
                }
                SqliteDatabaseImpl.this.db.execSQL(FileDownloadUtils.formatString("DELETE FROM %s WHERE %s IN (%s);", SqliteDatabaseImpl.TABLE_NAME, "_id", args));
                SqliteDatabaseImpl.this.db.execSQL(FileDownloadUtils.formatString("DELETE FROM %s WHERE %s IN (%s);", SqliteDatabaseImpl.CONNECTION_TABLE_NAME, "id", args));
            }
        }
    }

    public static class Maker implements DatabaseCustomMaker {
        public FileDownloadDatabase customMake() {
            return new SqliteDatabaseImpl();
        }
    }

    public static Maker createMaker() {
        return new Maker();
    }

    public void onTaskStart(int id) {
    }

    public FileDownloadModel find(int id) {
        Cursor c = null;
        try {
            c = this.db.rawQuery(FileDownloadUtils.formatString("SELECT * FROM %s WHERE %s = ?", TABLE_NAME, "_id"), new String[]{Integer.toString(id)});
            if (c.moveToNext()) {
                FileDownloadModel createFromCursor = createFromCursor(c);
                return createFromCursor;
            }
            if (c != null) {
                c.close();
            }
            return null;
        } finally {
            if (c != null) {
                c.close();
            }
        }
    }

    public List<ConnectionModel> findConnectionModel(int id) {
        List<ConnectionModel> resultList = new ArrayList();
        Cursor c = null;
        try {
            c = this.db.rawQuery(FileDownloadUtils.formatString("SELECT * FROM %s WHERE %s = ?", CONNECTION_TABLE_NAME, "id"), new String[]{Integer.toString(id)});
            while (c.moveToNext()) {
                ConnectionModel model = new ConnectionModel();
                model.setId(id);
                model.setIndex(c.getInt(c.getColumnIndex(ConnectionModel.INDEX)));
                model.setStartOffset(c.getLong(c.getColumnIndex(ConnectionModel.START_OFFSET)));
                model.setCurrentOffset(c.getLong(c.getColumnIndex(ConnectionModel.CURRENT_OFFSET)));
                model.setEndOffset(c.getLong(c.getColumnIndex(ConnectionModel.END_OFFSET)));
                resultList.add(model);
            }
            return resultList;
        } finally {
            if (c != null) {
                c.close();
            }
        }
    }

    public void removeConnections(int id) {
        this.db.execSQL("DELETE FROM filedownloaderConnection WHERE id = " + id);
    }

    public void insertConnectionModel(ConnectionModel model) {
        this.db.insert(CONNECTION_TABLE_NAME, null, model.toContentValues());
    }

    public void updateConnectionModel(int id, int index, long currentOffset) {
        ContentValues values = new ContentValues();
        values.put(ConnectionModel.CURRENT_OFFSET, Long.valueOf(currentOffset));
        this.db.update(CONNECTION_TABLE_NAME, values, "id = ? AND connectionIndex = ?", new String[]{Integer.toString(id), Integer.toString(index)});
    }

    public void updateConnectionCount(int id, int count) {
        ContentValues values = new ContentValues();
        values.put(FileDownloadModel.CONNECTION_COUNT, Integer.valueOf(count));
        this.db.update(TABLE_NAME, values, "_id = ? ", new String[]{Integer.toString(id)});
    }

    public void insert(FileDownloadModel downloadModel) {
        this.db.insert(TABLE_NAME, null, downloadModel.toContentValues());
    }

    public void update(FileDownloadModel downloadModel) {
        if (downloadModel == null) {
            FileDownloadLog.w(this, "update but model == null!", new Object[0]);
        } else if (find(downloadModel.getId()) != null) {
            this.db.update(TABLE_NAME, downloadModel.toContentValues(), "_id = ? ", new String[]{String.valueOf(downloadModel.getId())});
        } else {
            insert(downloadModel);
        }
    }

    public boolean remove(int id) {
        if (this.db.delete(TABLE_NAME, "_id = ?", new String[]{String.valueOf(id)}) != 0) {
            return true;
        }
        return false;
    }

    public void clear() {
        this.db.delete(TABLE_NAME, null, null);
        this.db.delete(CONNECTION_TABLE_NAME, null, null);
    }

    public void updateOldEtagOverdue(int id, String newEtag, long sofar, long total, int connectionCount) {
        ContentValues values = new ContentValues();
        values.put(FileDownloadModel.SOFAR, Long.valueOf(sofar));
        values.put(FileDownloadModel.TOTAL, Long.valueOf(total));
        values.put(FileDownloadModel.ETAG, newEtag);
        values.put(FileDownloadModel.CONNECTION_COUNT, Integer.valueOf(connectionCount));
        update(id, values);
    }

    public void updateConnected(int id, long total, String etag, String filename) {
        ContentValues cv = new ContentValues();
        cv.put("status", Byte.valueOf((byte) 2));
        cv.put(FileDownloadModel.TOTAL, Long.valueOf(total));
        cv.put(FileDownloadModel.ETAG, etag);
        cv.put(FileDownloadModel.FILENAME, filename);
        update(id, cv);
    }

    public void updateProgress(int id, long sofarBytes) {
        ContentValues cv = new ContentValues();
        cv.put("status", Byte.valueOf((byte) 3));
        cv.put(FileDownloadModel.SOFAR, Long.valueOf(sofarBytes));
        update(id, cv);
    }

    public void updateError(int id, Throwable throwable, long sofar) {
        ContentValues cv = new ContentValues();
        cv.put(FileDownloadModel.ERR_MSG, throwable.toString());
        cv.put("status", Byte.valueOf((byte) -1));
        cv.put(FileDownloadModel.SOFAR, Long.valueOf(sofar));
        update(id, cv);
    }

    public void updateRetry(int id, Throwable throwable) {
        ContentValues cv = new ContentValues();
        cv.put(FileDownloadModel.ERR_MSG, throwable.toString());
        cv.put("status", Byte.valueOf((byte) 5));
        update(id, cv);
    }

    public void updateCompleted(int id, long total) {
        remove(id);
    }

    public void updatePause(int id, long sofar) {
        ContentValues cv = new ContentValues();
        cv.put("status", Byte.valueOf((byte) -2));
        cv.put(FileDownloadModel.SOFAR, Long.valueOf(sofar));
        update(id, cv);
    }

    public void updatePending(int id) {
    }

    public com.liulishuo.filedownloader.database.FileDownloadDatabase.Maintainer maintainer() {
        return new Maintainer(this);
    }

    public com.liulishuo.filedownloader.database.FileDownloadDatabase.Maintainer maintainer(SparseArray<FileDownloadModel> downloaderModelMap, SparseArray<List<ConnectionModel>> connectionModelListMap) {
        return new Maintainer(downloaderModelMap, connectionModelListMap);
    }

    private void update(int id, ContentValues cv) {
        this.db.update(TABLE_NAME, cv, "_id = ? ", new String[]{String.valueOf(id)});
    }

    private static FileDownloadModel createFromCursor(Cursor c) {
        boolean z = true;
        FileDownloadModel model = new FileDownloadModel();
        model.setId(c.getInt(c.getColumnIndex("_id")));
        model.setUrl(c.getString(c.getColumnIndex("url")));
        String string = c.getString(c.getColumnIndex("path"));
        if (c.getShort(c.getColumnIndex(FileDownloadModel.PATH_AS_DIRECTORY)) != (short) 1) {
            z = false;
        }
        model.setPath(string, z);
        model.setStatus((byte) c.getShort(c.getColumnIndex("status")));
        model.setSoFar(c.getLong(c.getColumnIndex(FileDownloadModel.SOFAR)));
        model.setTotal(c.getLong(c.getColumnIndex(FileDownloadModel.TOTAL)));
        model.setErrMsg(c.getString(c.getColumnIndex(FileDownloadModel.ERR_MSG)));
        model.setETag(c.getString(c.getColumnIndex(FileDownloadModel.ETAG)));
        model.setFilename(c.getString(c.getColumnIndex(FileDownloadModel.FILENAME)));
        model.setConnectionCount(c.getInt(c.getColumnIndex(FileDownloadModel.CONNECTION_COUNT)));
        return model;
    }
}
