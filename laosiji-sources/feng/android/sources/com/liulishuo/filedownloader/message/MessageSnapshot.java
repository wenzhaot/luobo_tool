package com.liulishuo.filedownloader.message;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.liulishuo.filedownloader.message.SmallMessageSnapshot.CompletedSnapshot;
import com.liulishuo.filedownloader.message.SmallMessageSnapshot.ConnectedMessageSnapshot;
import com.liulishuo.filedownloader.message.SmallMessageSnapshot.ErrorMessageSnapshot;
import com.liulishuo.filedownloader.message.SmallMessageSnapshot.PendingMessageSnapshot;
import com.liulishuo.filedownloader.message.SmallMessageSnapshot.ProgressMessageSnapshot;
import com.liulishuo.filedownloader.message.SmallMessageSnapshot.RetryMessageSnapshot;
import com.liulishuo.filedownloader.message.SmallMessageSnapshot.WarnMessageSnapshot;
import com.liulishuo.filedownloader.util.FileDownloadUtils;

public abstract class MessageSnapshot implements IMessageSnapshot, Parcelable {
    public static final Creator<MessageSnapshot> CREATOR = new Creator<MessageSnapshot>() {
        public MessageSnapshot createFromParcel(Parcel source) {
            MessageSnapshot snapshot;
            boolean largeFile = true;
            if (source.readByte() != (byte) 1) {
                largeFile = false;
            }
            byte status = source.readByte();
            switch (status) {
                case (byte) -4:
                    if (!largeFile) {
                        snapshot = new WarnMessageSnapshot(source);
                        break;
                    }
                    snapshot = new LargeMessageSnapshot.WarnMessageSnapshot(source);
                    break;
                case (byte) -3:
                    if (!largeFile) {
                        snapshot = new CompletedSnapshot(source);
                        break;
                    }
                    snapshot = new LargeMessageSnapshot.CompletedSnapshot(source);
                    break;
                case (byte) -1:
                    if (!largeFile) {
                        snapshot = new ErrorMessageSnapshot(source);
                        break;
                    }
                    snapshot = new LargeMessageSnapshot.ErrorMessageSnapshot(source);
                    break;
                case (byte) 1:
                    if (!largeFile) {
                        snapshot = new PendingMessageSnapshot(source);
                        break;
                    }
                    snapshot = new LargeMessageSnapshot.PendingMessageSnapshot(source);
                    break;
                case (byte) 2:
                    if (!largeFile) {
                        snapshot = new ConnectedMessageSnapshot(source);
                        break;
                    }
                    snapshot = new LargeMessageSnapshot.ConnectedMessageSnapshot(source);
                    break;
                case (byte) 3:
                    if (!largeFile) {
                        snapshot = new ProgressMessageSnapshot(source);
                        break;
                    }
                    snapshot = new LargeMessageSnapshot.ProgressMessageSnapshot(source);
                    break;
                case (byte) 5:
                    if (!largeFile) {
                        snapshot = new RetryMessageSnapshot(source);
                        break;
                    }
                    snapshot = new LargeMessageSnapshot.RetryMessageSnapshot(source);
                    break;
                case (byte) 6:
                    snapshot = new StartedMessageSnapshot(source);
                    break;
                default:
                    snapshot = null;
                    break;
            }
            if (snapshot != null) {
                snapshot.isLargeFile = largeFile;
                return snapshot;
            }
            throw new IllegalStateException("Can't restore the snapshot because unknown status: " + status);
        }

        public MessageSnapshot[] newArray(int size) {
            return new MessageSnapshot[size];
        }
    };
    private final int id;
    protected boolean isLargeFile;

    public interface IWarnMessageSnapshot {
        MessageSnapshot turnToPending();
    }

    public static class NoFieldException extends IllegalStateException {
        NoFieldException(String methodName, MessageSnapshot snapshot) {
            super(FileDownloadUtils.formatString("There isn't a field for '%s' in this message %d %d %s", methodName, Integer.valueOf(snapshot.getId()), Byte.valueOf(snapshot.getStatus()), snapshot.getClass().getName()));
        }
    }

    public static class StartedMessageSnapshot extends MessageSnapshot {
        StartedMessageSnapshot(int id) {
            super(id);
        }

        StartedMessageSnapshot(Parcel in) {
            super(in);
        }

        public byte getStatus() {
            return (byte) 6;
        }
    }

    MessageSnapshot(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public Throwable getThrowable() {
        throw new NoFieldException("getThrowable", this);
    }

    public int getRetryingTimes() {
        throw new NoFieldException("getRetryingTimes", this);
    }

    public boolean isResuming() {
        throw new NoFieldException("isResuming", this);
    }

    public String getEtag() {
        throw new NoFieldException("getEtag", this);
    }

    public long getLargeSofarBytes() {
        throw new NoFieldException("getLargeSofarBytes", this);
    }

    public long getLargeTotalBytes() {
        throw new NoFieldException("getLargeTotalBytes", this);
    }

    public int getSmallSofarBytes() {
        throw new NoFieldException("getSmallSofarBytes", this);
    }

    public int getSmallTotalBytes() {
        throw new NoFieldException("getSmallTotalBytes", this);
    }

    public boolean isReusedDownloadedFile() {
        throw new NoFieldException("isReusedDownloadedFile", this);
    }

    public String getFileName() {
        throw new NoFieldException("getFileName", this);
    }

    public boolean isLargeFile() {
        return this.isLargeFile;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (this.isLargeFile ? 1 : 0));
        dest.writeByte(getStatus());
        dest.writeInt(this.id);
    }

    MessageSnapshot(Parcel in) {
        this.id = in.readInt();
    }
}
