package com.liulishuo.filedownloader.message;

import android.os.Parcel;
import com.liulishuo.filedownloader.message.MessageSnapshot.IWarnMessageSnapshot;

public abstract class LargeMessageSnapshot extends MessageSnapshot {

    public static class CompletedSnapshot extends LargeMessageSnapshot {
        private final boolean reusedDownloadedFile;
        private final long totalBytes;

        CompletedSnapshot(int id, boolean reusedDownloadedFile, long totalBytes) {
            super(id);
            this.reusedDownloadedFile = reusedDownloadedFile;
            this.totalBytes = totalBytes;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeByte(this.reusedDownloadedFile ? (byte) 1 : (byte) 0);
            dest.writeLong(this.totalBytes);
        }

        CompletedSnapshot(Parcel in) {
            super(in);
            this.reusedDownloadedFile = in.readByte() != (byte) 0;
            this.totalBytes = in.readLong();
        }

        public byte getStatus() {
            return (byte) -3;
        }

        public long getLargeTotalBytes() {
            return this.totalBytes;
        }

        public boolean isReusedDownloadedFile() {
            return this.reusedDownloadedFile;
        }
    }

    public static class CompletedFlowDirectlySnapshot extends CompletedSnapshot implements IFlowDirectly {
        CompletedFlowDirectlySnapshot(int id, boolean reusedDownloadedFile, long totalBytes) {
            super(id, reusedDownloadedFile, totalBytes);
        }

        CompletedFlowDirectlySnapshot(Parcel in) {
            super(in);
        }
    }

    public static class ConnectedMessageSnapshot extends LargeMessageSnapshot {
        private final String etag;
        private final String fileName;
        private final boolean resuming;
        private final long totalBytes;

        ConnectedMessageSnapshot(int id, boolean resuming, long totalBytes, String etag, String fileName) {
            super(id);
            this.resuming = resuming;
            this.totalBytes = totalBytes;
            this.etag = etag;
            this.fileName = fileName;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeByte(this.resuming ? (byte) 1 : (byte) 0);
            dest.writeLong(this.totalBytes);
            dest.writeString(this.etag);
            dest.writeString(this.fileName);
        }

        ConnectedMessageSnapshot(Parcel in) {
            super(in);
            this.resuming = in.readByte() != (byte) 0;
            this.totalBytes = in.readLong();
            this.etag = in.readString();
            this.fileName = in.readString();
        }

        public String getFileName() {
            return this.fileName;
        }

        public byte getStatus() {
            return (byte) 2;
        }

        public boolean isResuming() {
            return this.resuming;
        }

        public long getLargeTotalBytes() {
            return this.totalBytes;
        }

        public String getEtag() {
            return this.etag;
        }
    }

    public static class ErrorMessageSnapshot extends LargeMessageSnapshot {
        private final long sofarBytes;
        private final Throwable throwable;

        ErrorMessageSnapshot(int id, long sofarBytes, Throwable throwable) {
            super(id);
            this.sofarBytes = sofarBytes;
            this.throwable = throwable;
        }

        public long getLargeSofarBytes() {
            return this.sofarBytes;
        }

        public byte getStatus() {
            return (byte) -1;
        }

        public Throwable getThrowable() {
            return this.throwable;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeLong(this.sofarBytes);
            dest.writeSerializable(this.throwable);
        }

        ErrorMessageSnapshot(Parcel in) {
            super(in);
            this.sofarBytes = in.readLong();
            this.throwable = (Throwable) in.readSerializable();
        }
    }

    public static class PendingMessageSnapshot extends LargeMessageSnapshot {
        private final long sofarBytes;
        private final long totalBytes;

        PendingMessageSnapshot(PendingMessageSnapshot snapshot) {
            this(snapshot.getId(), snapshot.getLargeSofarBytes(), snapshot.getLargeTotalBytes());
        }

        PendingMessageSnapshot(int id, long sofarBytes, long totalBytes) {
            super(id);
            this.sofarBytes = sofarBytes;
            this.totalBytes = totalBytes;
        }

        public byte getStatus() {
            return (byte) 1;
        }

        public long getLargeSofarBytes() {
            return this.sofarBytes;
        }

        public long getLargeTotalBytes() {
            return this.totalBytes;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeLong(this.sofarBytes);
            dest.writeLong(this.totalBytes);
        }

        PendingMessageSnapshot(Parcel in) {
            super(in);
            this.sofarBytes = in.readLong();
            this.totalBytes = in.readLong();
        }
    }

    public static class PausedSnapshot extends PendingMessageSnapshot {
        PausedSnapshot(int id, long sofarBytes, long totalBytes) {
            super(id, sofarBytes, totalBytes);
        }

        public byte getStatus() {
            return (byte) -2;
        }
    }

    public static class ProgressMessageSnapshot extends LargeMessageSnapshot {
        private final long sofarBytes;

        ProgressMessageSnapshot(int id, long sofarBytes) {
            super(id);
            this.sofarBytes = sofarBytes;
        }

        public byte getStatus() {
            return (byte) 3;
        }

        public long getLargeSofarBytes() {
            return this.sofarBytes;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeLong(this.sofarBytes);
        }

        ProgressMessageSnapshot(Parcel in) {
            super(in);
            this.sofarBytes = in.readLong();
        }
    }

    public static class RetryMessageSnapshot extends ErrorMessageSnapshot {
        private final int retryingTimes;

        RetryMessageSnapshot(int id, long sofarBytes, Throwable throwable, int retryingTimes) {
            super(id, sofarBytes, throwable);
            this.retryingTimes = retryingTimes;
        }

        public int getRetryingTimes() {
            return this.retryingTimes;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(this.retryingTimes);
        }

        RetryMessageSnapshot(Parcel in) {
            super(in);
            this.retryingTimes = in.readInt();
        }

        public byte getStatus() {
            return (byte) 5;
        }
    }

    public static class WarnMessageSnapshot extends PendingMessageSnapshot implements IWarnMessageSnapshot {
        WarnMessageSnapshot(int id, long sofarBytes, long totalBytes) {
            super(id, sofarBytes, totalBytes);
        }

        WarnMessageSnapshot(Parcel in) {
            super(in);
        }

        public MessageSnapshot turnToPending() {
            return new PendingMessageSnapshot((PendingMessageSnapshot) this);
        }

        public byte getStatus() {
            return (byte) -4;
        }
    }

    public static class WarnFlowDirectlySnapshot extends WarnMessageSnapshot implements IFlowDirectly {
        WarnFlowDirectlySnapshot(int id, long sofarBytes, long totalBytes) {
            super(id, sofarBytes, totalBytes);
        }

        WarnFlowDirectlySnapshot(Parcel in) {
            super(in);
        }
    }

    LargeMessageSnapshot(int id) {
        super(id);
        this.isLargeFile = true;
    }

    LargeMessageSnapshot(Parcel in) {
        super(in);
    }

    public int getSmallSofarBytes() {
        if (getLargeSofarBytes() > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) getLargeSofarBytes();
    }

    public int getSmallTotalBytes() {
        if (getLargeTotalBytes() > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) getLargeTotalBytes();
    }
}
