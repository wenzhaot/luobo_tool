package com.liulishuo.filedownloader;

import com.liulishuo.filedownloader.BaseDownloadTask.IRunningTask;
import com.liulishuo.filedownloader.message.MessageSnapshot;
import com.liulishuo.filedownloader.message.MessageSnapshotFlow.MessageReceiver;
import com.liulishuo.filedownloader.util.FileDownloadLog;
import java.util.List;

public class MessageSnapshotGate implements MessageReceiver {
    private boolean transmitMessage(List<IRunningTask> taskList, MessageSnapshot snapshot) {
        if (taskList.size() > 1 && snapshot.getStatus() == (byte) -3) {
            for (IRunningTask task : taskList) {
                synchronized (task.getPauseLock()) {
                    if (task.getMessageHandler().updateMoreLikelyCompleted(snapshot)) {
                        FileDownloadLog.d(this, "updateMoreLikelyCompleted", new Object[0]);
                        return true;
                    }
                }
            }
        }
        for (IRunningTask task2 : taskList) {
            synchronized (task2.getPauseLock()) {
                if (task2.getMessageHandler().updateKeepFlow(snapshot)) {
                    FileDownloadLog.d(this, "updateKeepFlow", new Object[0]);
                    return true;
                }
            }
        }
        if ((byte) -4 == snapshot.getStatus()) {
            for (IRunningTask task22 : taskList) {
                synchronized (task22.getPauseLock()) {
                    if (task22.getMessageHandler().updateSameFilePathTaskRunning(snapshot)) {
                        FileDownloadLog.d(this, "updateSampleFilePathTaskRunning", new Object[0]);
                        return true;
                    }
                }
            }
        }
        if (taskList.size() != 1) {
            return false;
        }
        boolean updateKeepAhead;
        IRunningTask onlyTask = (IRunningTask) taskList.get(0);
        synchronized (onlyTask.getPauseLock()) {
            FileDownloadLog.d(this, "updateKeepAhead", new Object[0]);
            updateKeepAhead = onlyTask.getMessageHandler().updateKeepAhead(snapshot);
        }
        return updateKeepAhead;
    }

    public void receive(MessageSnapshot snapshot) {
        synchronized (Integer.toString(snapshot.getId()).intern()) {
            List<IRunningTask> taskList = FileDownloadList.getImpl().getReceiveServiceTaskList(snapshot.getId());
            if (taskList.size() > 0) {
                BaseDownloadTask topOriginTask = ((IRunningTask) taskList.get(0)).getOrigin();
                if (FileDownloadLog.NEED_LOG) {
                    FileDownloadLog.d(this, "~~~callback %s old[%s] new[%s] %d", Integer.valueOf(snapshot.getId()), Byte.valueOf(topOriginTask.getStatus()), Byte.valueOf(snapshot.getStatus()), Integer.valueOf(taskList.size()));
                }
                if (!transmitMessage(taskList, snapshot)) {
                    StringBuilder log = new StringBuilder("The event isn't consumed, id:" + snapshot.getId() + " status:" + snapshot.getStatus() + " task-count:" + taskList.size());
                    for (IRunningTask task : taskList) {
                        log.append(" | ").append(task.getOrigin().getStatus());
                    }
                    FileDownloadLog.i(this, log.toString(), new Object[0]);
                }
            } else {
                FileDownloadLog.i(this, "Receive the event %d, but there isn't any running task in the upper layer", Byte.valueOf(snapshot.getStatus()));
            }
        }
    }
}
