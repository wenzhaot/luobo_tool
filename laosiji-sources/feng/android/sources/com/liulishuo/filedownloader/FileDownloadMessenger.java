package com.liulishuo.filedownloader;

import com.liulishuo.filedownloader.BaseDownloadTask.IRunningTask;
import com.liulishuo.filedownloader.BaseDownloadTask.LifeCycleCallback;
import com.liulishuo.filedownloader.ITaskHunter.IMessageHandler;
import com.liulishuo.filedownloader.message.BlockCompleteMessage;
import com.liulishuo.filedownloader.message.MessageSnapshot;
import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.liulishuo.filedownloader.util.FileDownloadLog;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

class FileDownloadMessenger implements IFileDownloadMessenger {
    private boolean mIsDiscard = false;
    private LifeCycleCallback mLifeCycleCallback;
    private IRunningTask mTask;
    private Queue<MessageSnapshot> parcelQueue;

    FileDownloadMessenger(IRunningTask task, LifeCycleCallback callback) {
        init(task, callback);
    }

    private void init(IRunningTask task, LifeCycleCallback callback) {
        this.mTask = task;
        this.mLifeCycleCallback = callback;
        this.parcelQueue = new LinkedBlockingQueue();
    }

    public boolean notifyBegin() {
        if (FileDownloadLog.NEED_LOG) {
            FileDownloadLog.d(this, "notify begin %s", this.mTask);
        }
        if (this.mTask == null) {
            FileDownloadLog.w(this, "can't begin the task, the holder fo the messenger is nil, %d", Integer.valueOf(this.parcelQueue.size()));
            return false;
        }
        this.mLifeCycleCallback.onBegin();
        return true;
    }

    public void notifyPending(MessageSnapshot snapshot) {
        if (FileDownloadLog.NEED_LOG) {
            FileDownloadLog.d(this, "notify pending %s", this.mTask);
        }
        this.mLifeCycleCallback.onIng();
        process(snapshot);
    }

    public void notifyStarted(MessageSnapshot snapshot) {
        if (FileDownloadLog.NEED_LOG) {
            FileDownloadLog.d(this, "notify started %s", this.mTask);
        }
        this.mLifeCycleCallback.onIng();
        process(snapshot);
    }

    public void notifyConnected(MessageSnapshot snapshot) {
        if (FileDownloadLog.NEED_LOG) {
            FileDownloadLog.d(this, "notify connected %s", this.mTask);
        }
        this.mLifeCycleCallback.onIng();
        process(snapshot);
    }

    public void notifyProgress(MessageSnapshot snapshot) {
        BaseDownloadTask originTask = this.mTask.getOrigin();
        if (FileDownloadLog.NEED_LOG) {
            FileDownloadLog.d(this, "notify progress %s %d %d", originTask, Long.valueOf(originTask.getLargeFileSoFarBytes()), Long.valueOf(originTask.getLargeFileTotalBytes()));
        }
        if (originTask.getCallbackProgressTimes() > 0) {
            this.mLifeCycleCallback.onIng();
            process(snapshot);
        } else if (FileDownloadLog.NEED_LOG) {
            FileDownloadLog.d(this, "notify progress but client not request notify %s", this.mTask);
        }
    }

    public void notifyBlockComplete(MessageSnapshot snapshot) {
        if (FileDownloadLog.NEED_LOG) {
            FileDownloadLog.d(this, "notify block completed %s %s", this.mTask, Thread.currentThread().getName());
        }
        this.mLifeCycleCallback.onIng();
        process(snapshot);
    }

    public void notifyRetry(MessageSnapshot snapshot) {
        if (FileDownloadLog.NEED_LOG) {
            BaseDownloadTask originTask = this.mTask.getOrigin();
            FileDownloadLog.d(this, "notify retry %s %d %d %s", this.mTask, Integer.valueOf(originTask.getAutoRetryTimes()), Integer.valueOf(originTask.getRetryingTimes()), originTask.getErrorCause());
        }
        this.mLifeCycleCallback.onIng();
        process(snapshot);
    }

    public void notifyWarn(MessageSnapshot snapshot) {
        if (FileDownloadLog.NEED_LOG) {
            FileDownloadLog.d(this, "notify warn %s", this.mTask);
        }
        this.mLifeCycleCallback.onOver();
        process(snapshot);
    }

    public void notifyError(MessageSnapshot snapshot) {
        if (FileDownloadLog.NEED_LOG) {
            FileDownloadLog.d(this, "notify error %s %s", this.mTask, this.mTask.getOrigin().getErrorCause());
        }
        this.mLifeCycleCallback.onOver();
        process(snapshot);
    }

    public void notifyPaused(MessageSnapshot snapshot) {
        if (FileDownloadLog.NEED_LOG) {
            FileDownloadLog.d(this, "notify paused %s", this.mTask);
        }
        this.mLifeCycleCallback.onOver();
        process(snapshot);
    }

    public void notifyCompleted(MessageSnapshot snapshot) {
        if (FileDownloadLog.NEED_LOG) {
            FileDownloadLog.d(this, "notify completed %s", this.mTask);
        }
        this.mLifeCycleCallback.onOver();
        process(snapshot);
    }

    private void process(MessageSnapshot snapshot) {
        if (this.mTask == null) {
            if (FileDownloadLog.NEED_LOG) {
                FileDownloadLog.d(this, "occur this case, it would be the host task of this messenger has been over(paused/warn/completed/error) on the other thread before receiving the snapshot(id[%d], status[%d])", Integer.valueOf(snapshot.getId()), Byte.valueOf(snapshot.getStatus()));
            }
        } else if (this.mIsDiscard || this.mTask.getOrigin().getListener() == null) {
            if ((FileDownloadMonitor.isValid() || this.mTask.isContainFinishListener()) && snapshot.getStatus() == (byte) 4) {
                this.mLifeCycleCallback.onOver();
            }
            inspectAndHandleOverStatus(snapshot.getStatus());
        } else {
            this.parcelQueue.offer(snapshot);
            FileDownloadMessageStation.getImpl().requestEnqueue(this);
        }
    }

    private void inspectAndHandleOverStatus(int status) {
        if (FileDownloadStatus.isOver(status)) {
            if (!this.parcelQueue.isEmpty()) {
                MessageSnapshot queueTopTask = (MessageSnapshot) this.parcelQueue.peek();
                FileDownloadLog.w(this, "the messenger[%s](with id[%d]) has already accomplished all his job, but there still are some messages in parcel queue[%d] queue-top-status[%d]", this, Integer.valueOf(queueTopTask.getId()), Integer.valueOf(this.parcelQueue.size()), Byte.valueOf(queueTopTask.getStatus()));
            }
            this.mTask = null;
        }
    }

    public void handoverMessage() {
        if (!this.mIsDiscard) {
            MessageSnapshot message = (MessageSnapshot) this.parcelQueue.poll();
            int currentStatus = message.getStatus();
            IRunningTask task = this.mTask;
            if (task == null) {
                throw new IllegalArgumentException(FileDownloadUtils.formatString("can't handover the message, no master to receive this message(status[%d]) size[%d]", Integer.valueOf(currentStatus), Integer.valueOf(this.parcelQueue.size())));
            }
            BaseDownloadTask originTask = task.getOrigin();
            FileDownloadListener listener = originTask.getListener();
            IMessageHandler messageHandler = task.getMessageHandler();
            inspectAndHandleOverStatus(currentStatus);
            if (listener != null && !listener.isInvalid()) {
                if (currentStatus == 4) {
                    try {
                        listener.blockComplete(originTask);
                        notifyCompleted(((BlockCompleteMessage) message).transmitToCompleted());
                        return;
                    } catch (Throwable throwable) {
                        notifyError(messageHandler.prepareErrorMessage(throwable));
                        return;
                    }
                }
                FileDownloadLargeFileListener largeFileListener = null;
                if (listener instanceof FileDownloadLargeFileListener) {
                    largeFileListener = (FileDownloadLargeFileListener) listener;
                }
                switch (currentStatus) {
                    case -4:
                        listener.warn(originTask);
                        return;
                    case -3:
                        listener.completed(originTask);
                        return;
                    case -2:
                        if (largeFileListener != null) {
                            largeFileListener.paused(originTask, message.getLargeSofarBytes(), message.getLargeTotalBytes());
                            return;
                        }
                        listener.paused(originTask, message.getSmallSofarBytes(), message.getSmallTotalBytes());
                        return;
                    case -1:
                        listener.error(originTask, message.getThrowable());
                        return;
                    case 1:
                        if (largeFileListener != null) {
                            largeFileListener.pending(originTask, message.getLargeSofarBytes(), message.getLargeTotalBytes());
                            return;
                        } else {
                            listener.pending(originTask, message.getSmallSofarBytes(), message.getSmallTotalBytes());
                            return;
                        }
                    case 2:
                        if (largeFileListener != null) {
                            largeFileListener.connected(originTask, message.getEtag(), message.isResuming(), originTask.getLargeFileSoFarBytes(), message.getLargeTotalBytes());
                            return;
                        }
                        listener.connected(originTask, message.getEtag(), message.isResuming(), originTask.getSmallFileSoFarBytes(), message.getSmallTotalBytes());
                        return;
                    case 3:
                        if (largeFileListener != null) {
                            largeFileListener.progress(originTask, message.getLargeSofarBytes(), originTask.getLargeFileTotalBytes());
                            return;
                        }
                        listener.progress(originTask, message.getSmallSofarBytes(), originTask.getSmallFileTotalBytes());
                        return;
                    case 5:
                        if (largeFileListener != null) {
                            largeFileListener.retry(originTask, message.getThrowable(), message.getRetryingTimes(), message.getLargeSofarBytes());
                            return;
                        }
                        listener.retry(originTask, message.getThrowable(), message.getRetryingTimes(), message.getSmallSofarBytes());
                        return;
                    case 6:
                        listener.started(originTask);
                        return;
                    default:
                        return;
                }
            }
        }
    }

    public boolean handoverDirectly() {
        return this.mTask.getOrigin().isSyncCallback();
    }

    public void reAppointment(IRunningTask task, LifeCycleCallback callback) {
        if (this.mTask != null) {
            throw new IllegalStateException(FileDownloadUtils.formatString("the messenger is working, can't re-appointment for %s", task));
        } else {
            init(task, callback);
        }
    }

    public boolean isBlockingCompleted() {
        return ((MessageSnapshot) this.parcelQueue.peek()).getStatus() == (byte) 4;
    }

    public void discard() {
        this.mIsDiscard = true;
    }

    public String toString() {
        int i;
        String str = "%d:%s";
        Object[] objArr = new Object[2];
        if (this.mTask == null) {
            i = -1;
        } else {
            i = this.mTask.getOrigin().getId();
        }
        objArr[0] = Integer.valueOf(i);
        objArr[1] = super.toString();
        return FileDownloadUtils.formatString(str, objArr);
    }
}
