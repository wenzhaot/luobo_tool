package com.liulishuo.filedownloader.services;

import android.text.TextUtils;
import com.liulishuo.filedownloader.IThreadPoolMonitor;
import com.liulishuo.filedownloader.database.FileDownloadDatabase;
import com.liulishuo.filedownloader.download.CustomComponentHolder;
import com.liulishuo.filedownloader.download.DownloadLaunchRunnable.Builder;
import com.liulishuo.filedownloader.model.ConnectionModel;
import com.liulishuo.filedownloader.model.FileDownloadHeader;
import com.liulishuo.filedownloader.model.FileDownloadModel;
import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.liulishuo.filedownloader.util.FileDownloadHelper;
import com.liulishuo.filedownloader.util.FileDownloadLog;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import java.util.List;

class FileDownloadManager implements IThreadPoolMonitor {
    private final FileDownloadDatabase mDatabase;
    private final FileDownloadThreadPool mThreadPool;

    FileDownloadManager() {
        CustomComponentHolder holder = CustomComponentHolder.getImpl();
        this.mDatabase = holder.getDatabaseInstance();
        this.mThreadPool = new FileDownloadThreadPool(holder.getMaxNetworkThreadCount());
    }

    public synchronized void start(String url, String path, boolean pathAsDirectory, int callbackProgressTimes, int callbackProgressMinIntervalMillis, int autoRetryTimes, boolean forceReDownload, FileDownloadHeader header, boolean isWifiRequired) {
        if (FileDownloadLog.NEED_LOG) {
            FileDownloadLog.d(this, "request start the task with url(%s) path(%s) isDirectory(%B)", url, path, Boolean.valueOf(pathAsDirectory));
        }
        int id = FileDownloadUtils.generateId(url, path, pathAsDirectory);
        FileDownloadModel model = this.mDatabase.find(id);
        List<ConnectionModel> dirConnectionModelList = null;
        if (!pathAsDirectory && model == null) {
            int dirCaseId = FileDownloadUtils.generateId(url, FileDownloadUtils.getParent(path), true);
            model = this.mDatabase.find(dirCaseId);
            if (model != null) {
                if (path.equals(model.getTargetFilePath())) {
                    if (FileDownloadLog.NEED_LOG) {
                        FileDownloadLog.d(this, "task[%d] find model by dirCaseId[%d]", Integer.valueOf(id), Integer.valueOf(dirCaseId));
                    }
                    dirConnectionModelList = this.mDatabase.findConnectionModel(dirCaseId);
                }
            }
        }
        if (!FileDownloadHelper.inspectAndInflowDownloading(id, model, this, true)) {
            String targetFilePath;
            if (model != null) {
                targetFilePath = model.getTargetFilePath();
            } else {
                targetFilePath = FileDownloadUtils.getTargetFilePath(path, pathAsDirectory, null);
            }
            if (!FileDownloadHelper.inspectAndInflowDownloaded(id, targetFilePath, forceReDownload, true)) {
                String tempFilePath;
                long sofar = model != null ? model.getSoFar() : 0;
                if (model != null) {
                    tempFilePath = model.getTempFilePath();
                } else {
                    tempFilePath = FileDownloadUtils.getTempPath(targetFilePath);
                }
                if (FileDownloadHelper.inspectAndInflowConflictPath(id, sofar, tempFilePath, targetFilePath, this)) {
                    if (FileDownloadLog.NEED_LOG) {
                        FileDownloadLog.d(this, "there is an another task with the same target-file-path %d %s", Integer.valueOf(id), targetFilePath);
                    }
                    if (model != null) {
                        this.mDatabase.remove(id);
                        this.mDatabase.removeConnections(id);
                    }
                } else {
                    boolean needUpdate2DB;
                    if (model == null || !(model.getStatus() == (byte) -2 || model.getStatus() == (byte) -1 || model.getStatus() == (byte) 1 || model.getStatus() == (byte) 6 || model.getStatus() == (byte) 2)) {
                        if (model == null) {
                            model = new FileDownloadModel();
                        }
                        model.setUrl(url);
                        model.setPath(path, pathAsDirectory);
                        model.setId(id);
                        model.setSoFar(0);
                        model.setTotal(0);
                        model.setStatus((byte) 1);
                        model.setConnectionCount(1);
                        needUpdate2DB = true;
                    } else if (model.getId() != id) {
                        this.mDatabase.remove(model.getId());
                        this.mDatabase.removeConnections(model.getId());
                        model.setId(id);
                        model.setPath(path, pathAsDirectory);
                        if (dirConnectionModelList != null) {
                            for (ConnectionModel connectionModel : dirConnectionModelList) {
                                connectionModel.setId(id);
                                this.mDatabase.insertConnectionModel(connectionModel);
                            }
                        }
                        needUpdate2DB = true;
                    } else {
                        if (TextUtils.equals(url, model.getUrl())) {
                            needUpdate2DB = false;
                        } else {
                            model.setUrl(url);
                            needUpdate2DB = true;
                        }
                    }
                    if (needUpdate2DB) {
                        this.mDatabase.update(model);
                    }
                    this.mThreadPool.execute(new Builder().setModel(model).setHeader(header).setThreadPoolMonitor(this).setMinIntervalMillis(Integer.valueOf(callbackProgressMinIntervalMillis)).setCallbackProgressMaxCount(Integer.valueOf(callbackProgressTimes)).setForceReDownload(Boolean.valueOf(forceReDownload)).setWifiRequired(Boolean.valueOf(isWifiRequired)).setMaxRetryTimes(Integer.valueOf(autoRetryTimes)).build());
                }
            } else if (FileDownloadLog.NEED_LOG) {
                FileDownloadLog.d(this, "has already completed downloading %d", Integer.valueOf(id));
            }
        } else if (FileDownloadLog.NEED_LOG) {
            FileDownloadLog.d(this, "has already started download %d", Integer.valueOf(id));
        }
    }

    public boolean isDownloading(String url, String path) {
        return isDownloading(FileDownloadUtils.generateId(url, path));
    }

    public boolean isDownloading(int id) {
        return isDownloading(this.mDatabase.find(id));
    }

    public boolean pause(int id) {
        if (FileDownloadLog.NEED_LOG) {
            FileDownloadLog.d(this, "request pause the task %d", Integer.valueOf(id));
        }
        FileDownloadModel model = this.mDatabase.find(id);
        if (model == null) {
            return false;
        }
        model.setStatus((byte) -2);
        this.mThreadPool.cancel(id);
        return true;
    }

    public void pauseAll() {
        List<Integer> list = this.mThreadPool.getAllExactRunningDownloadIds();
        if (FileDownloadLog.NEED_LOG) {
            FileDownloadLog.d(this, "pause all tasks %d", Integer.valueOf(list.size()));
        }
        for (Integer id : list) {
            pause(id.intValue());
        }
    }

    public long getSoFar(int id) {
        FileDownloadModel model = this.mDatabase.find(id);
        if (model == null) {
            return 0;
        }
        int connectionCount = model.getConnectionCount();
        if (connectionCount <= 1) {
            return model.getSoFar();
        }
        List<ConnectionModel> modelList = this.mDatabase.findConnectionModel(id);
        if (modelList == null || modelList.size() != connectionCount) {
            return 0;
        }
        return ConnectionModel.getTotalOffset(modelList);
    }

    public long getTotal(int id) {
        FileDownloadModel model = this.mDatabase.find(id);
        if (model == null) {
            return 0;
        }
        return model.getTotal();
    }

    public byte getStatus(int id) {
        FileDownloadModel model = this.mDatabase.find(id);
        if (model == null) {
            return (byte) 0;
        }
        return model.getStatus();
    }

    public boolean isIdle() {
        return this.mThreadPool.exactSize() <= 0;
    }

    public synchronized boolean setMaxNetworkThreadCount(int count) {
        return this.mThreadPool.setMaxNetworkThreadCount(count);
    }

    public boolean isDownloading(FileDownloadModel model) {
        if (model == null) {
            return false;
        }
        boolean isInPool = this.mThreadPool.isInThreadPool(model.getId());
        if (FileDownloadStatus.isOver(model.getStatus())) {
            if (isInPool) {
                return true;
            }
            return false;
        } else if (isInPool) {
            return true;
        } else {
            FileDownloadLog.e(this, "%d status is[%s](not finish) & but not in the pool", Integer.valueOf(model.getId()), Byte.valueOf(model.getStatus()));
            return false;
        }
    }

    public int findRunningTaskIdBySameTempPath(String tempFilePath, int excludeId) {
        return this.mThreadPool.findRunningTaskIdBySameTempPath(tempFilePath, excludeId);
    }

    public boolean clearTaskData(int id) {
        if (id == 0) {
            FileDownloadLog.w(this, "The task[%d] id is invalid, can't clear it.", Integer.valueOf(id));
            return false;
        } else if (isDownloading(id)) {
            FileDownloadLog.w(this, "The task[%d] is downloading, can't clear it.", Integer.valueOf(id));
            return false;
        } else {
            this.mDatabase.remove(id);
            this.mDatabase.removeConnections(id);
            return true;
        }
    }

    public void clearAllTaskData() {
        this.mDatabase.clear();
    }
}
