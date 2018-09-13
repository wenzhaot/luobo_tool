package com.liulishuo.filedownloader;

import com.liulishuo.filedownloader.BaseDownloadTask.IRunningTask;
import com.liulishuo.filedownloader.message.MessageSnapshot;
import com.liulishuo.filedownloader.message.MessageSnapshotTaker;
import com.liulishuo.filedownloader.util.FileDownloadLog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FileDownloadList {
    private final ArrayList<IRunningTask> mList;

    private static final class HolderClass {
        private static final FileDownloadList INSTANCE = new FileDownloadList();

        private HolderClass() {
        }
    }

    public static FileDownloadList getImpl() {
        return HolderClass.INSTANCE;
    }

    private FileDownloadList() {
        this.mList = new ArrayList();
    }

    boolean isEmpty() {
        return this.mList.isEmpty();
    }

    int size() {
        return this.mList.size();
    }

    int count(int id) {
        int size = 0;
        synchronized (this.mList) {
            Iterator it = this.mList.iterator();
            while (it.hasNext()) {
                if (((IRunningTask) it.next()).is(id)) {
                    size++;
                }
            }
        }
        return size;
    }

    public IRunningTask get(int id) {
        synchronized (this.mList) {
            Iterator it = this.mList.iterator();
            while (it.hasNext()) {
                IRunningTask task = (IRunningTask) it.next();
                if (task.is(id)) {
                    return task;
                }
            }
            return null;
        }
    }

    List<IRunningTask> getReceiveServiceTaskList(int id) {
        List<IRunningTask> list = new ArrayList();
        synchronized (this.mList) {
            Iterator it = this.mList.iterator();
            while (it.hasNext()) {
                IRunningTask task = (IRunningTask) it.next();
                if (task.is(id) && !task.isOver()) {
                    byte status = task.getOrigin().getStatus();
                    if (!(status == (byte) 0 || status == (byte) 10)) {
                        list.add(task);
                    }
                }
            }
        }
        return list;
    }

    List<IRunningTask> getDownloadingList(int id) {
        List<IRunningTask> list = new ArrayList();
        synchronized (this.mList) {
            Iterator it = this.mList.iterator();
            while (it.hasNext()) {
                IRunningTask task = (IRunningTask) it.next();
                if (task.is(id) && !task.isOver()) {
                    list.add(task);
                }
            }
        }
        return list;
    }

    boolean isNotContains(IRunningTask download) {
        return this.mList.isEmpty() || !this.mList.contains(download);
    }

    List<IRunningTask> copy(FileDownloadListener listener) {
        List<IRunningTask> targetList = new ArrayList();
        synchronized (this.mList) {
            Iterator it = this.mList.iterator();
            while (it.hasNext()) {
                IRunningTask task = (IRunningTask) it.next();
                if (task.is(listener)) {
                    targetList.add(task);
                }
            }
        }
        return targetList;
    }

    List<IRunningTask> assembleTasksToStart(int attachKey, FileDownloadListener listener) {
        List<IRunningTask> targetList = new ArrayList();
        synchronized (this.mList) {
            Iterator it = this.mList.iterator();
            while (it.hasNext()) {
                IRunningTask task = (IRunningTask) it.next();
                if (task.getOrigin().getListener() == listener && !task.getOrigin().isAttached()) {
                    task.setAttachKeyByQueue(attachKey);
                    targetList.add(task);
                }
            }
        }
        return targetList;
    }

    IRunningTask[] copy() {
        IRunningTask[] iRunningTaskArr;
        synchronized (this.mList) {
            iRunningTaskArr = (IRunningTask[]) this.mList.toArray(new IRunningTask[this.mList.size()]);
        }
        return iRunningTaskArr;
    }

    void divertAndIgnoreDuplicate(List<IRunningTask> destination) {
        synchronized (this.mList) {
            Iterator it = this.mList.iterator();
            while (it.hasNext()) {
                IRunningTask iRunningTask = (IRunningTask) it.next();
                if (!destination.contains(iRunningTask)) {
                    destination.add(iRunningTask);
                }
            }
            this.mList.clear();
        }
    }

    public boolean remove(IRunningTask willRemoveDownload, MessageSnapshot snapshot) {
        boolean succeed;
        byte removeByStatus = snapshot.getStatus();
        synchronized (this.mList) {
            succeed = this.mList.remove(willRemoveDownload);
        }
        if (FileDownloadLog.NEED_LOG && this.mList.size() == 0) {
            FileDownloadLog.v(this, "remove %s left %d %d", willRemoveDownload, Byte.valueOf(removeByStatus), Integer.valueOf(this.mList.size()));
        }
        if (succeed) {
            IFileDownloadMessenger messenger = willRemoveDownload.getMessageHandler().getMessenger();
            switch (removeByStatus) {
                case (byte) -4:
                    messenger.notifyWarn(snapshot);
                    break;
                case (byte) -3:
                    messenger.notifyBlockComplete(MessageSnapshotTaker.takeBlockCompleted(snapshot));
                    break;
                case (byte) -2:
                    messenger.notifyPaused(snapshot);
                    break;
                case (byte) -1:
                    messenger.notifyError(snapshot);
                    break;
            }
        }
        FileDownloadLog.e(this, "remove error, not exist: %s %d", willRemoveDownload, Byte.valueOf(removeByStatus));
        return succeed;
    }

    void add(IRunningTask task) {
        if (!task.getOrigin().isAttached()) {
            task.setAttachKeyDefault();
        }
        if (task.getMessageHandler().getMessenger().notifyBegin()) {
            addUnchecked(task);
        }
    }

    void addUnchecked(IRunningTask task) {
        if (!task.isMarkedAdded2List()) {
            synchronized (this.mList) {
                if (this.mList.contains(task)) {
                    FileDownloadLog.w(this, "already has %s", task);
                } else {
                    task.markAdded2List();
                    this.mList.add(task);
                    if (FileDownloadLog.NEED_LOG) {
                        FileDownloadLog.v(this, "add list in all %s %d %d", task, Byte.valueOf(task.getOrigin().getStatus()), Integer.valueOf(this.mList.size()));
                    }
                }
            }
        }
    }
}
