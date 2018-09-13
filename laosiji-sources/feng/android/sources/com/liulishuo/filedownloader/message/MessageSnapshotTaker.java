package com.liulishuo.filedownloader.message;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.download.DownloadStatusCallback.ProcessParams;
import com.liulishuo.filedownloader.message.BlockCompleteMessage.BlockCompleteMessageImpl;
import com.liulishuo.filedownloader.message.LargeMessageSnapshot.CompletedFlowDirectlySnapshot;
import com.liulishuo.filedownloader.message.LargeMessageSnapshot.CompletedSnapshot;
import com.liulishuo.filedownloader.message.LargeMessageSnapshot.ConnectedMessageSnapshot;
import com.liulishuo.filedownloader.message.LargeMessageSnapshot.ErrorMessageSnapshot;
import com.liulishuo.filedownloader.message.LargeMessageSnapshot.PausedSnapshot;
import com.liulishuo.filedownloader.message.LargeMessageSnapshot.PendingMessageSnapshot;
import com.liulishuo.filedownloader.message.LargeMessageSnapshot.ProgressMessageSnapshot;
import com.liulishuo.filedownloader.message.LargeMessageSnapshot.RetryMessageSnapshot;
import com.liulishuo.filedownloader.message.LargeMessageSnapshot.WarnFlowDirectlySnapshot;
import com.liulishuo.filedownloader.message.LargeMessageSnapshot.WarnMessageSnapshot;
import com.liulishuo.filedownloader.message.MessageSnapshot.StartedMessageSnapshot;
import com.liulishuo.filedownloader.model.FileDownloadModel;
import com.liulishuo.filedownloader.util.FileDownloadLog;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import java.io.File;

public class MessageSnapshotTaker {
    public static MessageSnapshot take(byte status, FileDownloadModel model) {
        return take(status, model, null);
    }

    public static MessageSnapshot catchCanReusedOldFile(int id, File oldFile, boolean flowDirectly) {
        long totalBytes = oldFile.length();
        if (totalBytes > 2147483647L) {
            if (flowDirectly) {
                return new CompletedFlowDirectlySnapshot(id, true, totalBytes);
            }
            return new CompletedSnapshot(id, true, totalBytes);
        } else if (flowDirectly) {
            return new SmallMessageSnapshot.CompletedFlowDirectlySnapshot(id, true, (int) totalBytes);
        } else {
            return new SmallMessageSnapshot.CompletedSnapshot(id, true, (int) totalBytes);
        }
    }

    public static MessageSnapshot catchWarn(int id, long sofar, long total, boolean flowDirectly) {
        if (total > 2147483647L) {
            if (flowDirectly) {
                return new WarnFlowDirectlySnapshot(id, sofar, total);
            }
            return new WarnMessageSnapshot(id, sofar, total);
        } else if (flowDirectly) {
            return new SmallMessageSnapshot.WarnFlowDirectlySnapshot(id, (int) sofar, (int) total);
        } else {
            return new SmallMessageSnapshot.WarnMessageSnapshot(id, (int) sofar, (int) total);
        }
    }

    public static MessageSnapshot catchException(int id, long sofar, Throwable error) {
        if (sofar > 2147483647L) {
            return new ErrorMessageSnapshot(id, sofar, error);
        }
        return new SmallMessageSnapshot.ErrorMessageSnapshot(id, (int) sofar, error);
    }

    public static MessageSnapshot catchPause(BaseDownloadTask task) {
        if (task.isLargeFile()) {
            return new PausedSnapshot(task.getId(), task.getLargeFileSoFarBytes(), task.getLargeFileTotalBytes());
        }
        return new SmallMessageSnapshot.PausedSnapshot(task.getId(), task.getSmallFileSoFarBytes(), task.getSmallFileTotalBytes());
    }

    public static MessageSnapshot takeBlockCompleted(MessageSnapshot snapshot) {
        if (snapshot.getStatus() == (byte) -3) {
            return new BlockCompleteMessageImpl(snapshot);
        }
        throw new IllegalStateException(FileDownloadUtils.formatString("take block completed snapshot, must has already be completed. %d %d", Integer.valueOf(snapshot.getId()), Byte.valueOf(snapshot.getStatus())));
    }

    public static MessageSnapshot take(byte status, FileDownloadModel model, ProcessParams processParams) {
        int id = model.getId();
        if (status == (byte) -4) {
            throw new IllegalStateException(FileDownloadUtils.formatString("please use #catchWarn instead %d", Integer.valueOf(id)));
        }
        switch (status) {
            case (byte) -3:
                if (model.isLargeFile()) {
                    return new CompletedSnapshot(id, false, model.getTotal());
                }
                return new SmallMessageSnapshot.CompletedSnapshot(id, false, (int) model.getTotal());
            case (byte) -1:
                if (model.isLargeFile()) {
                    return new ErrorMessageSnapshot(id, model.getSoFar(), processParams.getException());
                }
                return new SmallMessageSnapshot.ErrorMessageSnapshot(id, (int) model.getSoFar(), processParams.getException());
            case (byte) 1:
                if (model.isLargeFile()) {
                    return new PendingMessageSnapshot(id, model.getSoFar(), model.getTotal());
                }
                return new SmallMessageSnapshot.PendingMessageSnapshot(id, (int) model.getSoFar(), (int) model.getTotal());
            case (byte) 2:
                String filename = model.isPathAsDirectory() ? model.getFilename() : null;
                if (model.isLargeFile()) {
                    return new ConnectedMessageSnapshot(id, processParams.isResuming(), model.getTotal(), model.getETag(), filename);
                }
                return new SmallMessageSnapshot.ConnectedMessageSnapshot(id, processParams.isResuming(), (int) model.getTotal(), model.getETag(), filename);
            case (byte) 3:
                if (model.isLargeFile()) {
                    return new ProgressMessageSnapshot(id, model.getSoFar());
                }
                return new SmallMessageSnapshot.ProgressMessageSnapshot(id, (int) model.getSoFar());
            case (byte) 5:
                if (model.isLargeFile()) {
                    return new RetryMessageSnapshot(id, model.getSoFar(), processParams.getException(), processParams.getRetryingTimes());
                }
                return new SmallMessageSnapshot.RetryMessageSnapshot(id, (int) model.getSoFar(), processParams.getException(), processParams.getRetryingTimes());
            case (byte) 6:
                return new StartedMessageSnapshot(id);
            default:
                Throwable throwable;
                String message = FileDownloadUtils.formatString("it can't takes a snapshot for the task(%s) when its status is %d,", model, Byte.valueOf(status));
                FileDownloadLog.w(MessageSnapshotTaker.class, "it can't takes a snapshot for the task(%s) when its status is %d,", model, Byte.valueOf(status));
                if (processParams.getException() != null) {
                    throwable = new IllegalStateException(message, processParams.getException());
                } else {
                    throwable = new IllegalStateException(message);
                }
                if (model.isLargeFile()) {
                    return new ErrorMessageSnapshot(id, model.getSoFar(), throwable);
                }
                return new SmallMessageSnapshot.ErrorMessageSnapshot(id, (int) model.getSoFar(), throwable);
        }
    }
}
