package com.liulishuo.filedownloader.message;

import android.os.Parcel;
import com.liulishuo.filedownloader.message.MessageSnapshot.IWarnMessageSnapshot;

public abstract class SmallMessageSnapshot extends MessageSnapshot {

    public static class CompletedSnapshot extends SmallMessageSnapshot {
        private final boolean reusedDownloadedFile;
        private final int totalBytes;

        CompletedSnapshot(int id, boolean reusedDownloadedFile, int totalBytes) {
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
            dest.writeInt(this.totalBytes);
        }

        CompletedSnapshot(Parcel in) {
            super(in);
            this.reusedDownloadedFile = in.readByte() != (byte) 0;
            this.totalBytes = in.readInt();
        }

        public byte getStatus() {
            return (byte) -3;
        }

        public int getSmallTotalBytes() {
            return this.totalBytes;
        }

        public boolean isReusedDownloadedFile() {
            return this.reusedDownloadedFile;
        }
    }

    public static class CompletedFlowDirectlySnapshot extends CompletedSnapshot implements IFlowDirectly {
        CompletedFlowDirectlySnapshot(int id, boolean reusedDownloadedFile, int totalBytes) {
            super(id, reusedDownloadedFile, totalBytes);
        }

        CompletedFlowDirectlySnapshot(Parcel in) {
            super(in);
        }
    }

    public static class ConnectedMessageSnapshot extends SmallMessageSnapshot {
        private final String etag;
        private final String fileName;
        private final boolean resuming;
        private final int totalBytes;

        ConnectedMessageSnapshot(int id, boolean resuming, int totalBytes, String etag, String fileName) {
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
            dest.writeInt(this.totalBytes);
            dest.writeString(this.etag);
            dest.writeString(this.fileName);
        }

        ConnectedMessageSnapshot(Parcel in) {
            super(in);
            this.resuming = in.readByte() != (byte) 0;
            this.totalBytes = in.readInt();
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

        public int getSmallTotalBytes() {
            return this.totalBytes;
        }

        public String getEtag() {
            return this.etag;
        }
    }

    public static class ErrorMessageSnapshot extends SmallMessageSnapshot {
        private final int sofarBytes;
        private final Throwable throwable;

        ErrorMessageSnapshot(int id, int sofarBytes, Throwable throwable) {
            super(id);
            this.sofarBytes = sofarBytes;
            this.throwable = throwable;
        }

        public int getSmallSofarBytes() {
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
            dest.writeInt(this.sofarBytes);
            dest.writeSerializable(this.throwable);
        }

        ErrorMessageSnapshot(Parcel in) {
            super(in);
            this.sofarBytes = in.readInt();
            this.throwable = (Throwable) in.readSerializable();
        }
    }

    public static class PendingMessageSnapshot extends SmallMessageSnapshot {
        private final int sofarBytes;
        private final int totalBytes;

        PendingMessageSnapshot(PendingMessageSnapshot snapshot) {
            this(snapshot.getId(), snapshot.getSmallSofarBytes(), snapshot.getSmallTotalBytes());
        }

        PendingMessageSnapshot(int id, int sofarBytes, int totalBytes) {
            super(id);
            this.sofarBytes = sofarBytes;
            this.totalBytes = totalBytes;
        }

        PendingMessageSnapshot(Parcel in) {
            super(in);
            this.sofarBytes = in.readInt();
            this.totalBytes = in.readInt();
        }

        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(this.sofarBytes);
            dest.writeInt(this.totalBytes);
        }

        public byte getStatus() {
            return (byte) 1;
        }

        public int getSmallSofarBytes() {
            return this.sofarBytes;
        }

        public int getSmallTotalBytes() {
            return this.totalBytes;
        }
    }

    public static class PausedSnapshot extends PendingMessageSnapshot {
        PausedSnapshot(int id, int sofarBytes, int totalBytes) {
            super(id, sofarBytes, totalBytes);
        }

        public byte getStatus() {
            return (byte) -2;
        }
    }

    public static class ProgressMessageSnapshot extends SmallMessageSnapshot {
        private final int sofarBytes;

        ProgressMessageSnapshot(int id, int sofarBytes) {
            super(id);
            this.sofarBytes = sofarBytes;
        }

        public byte getStatus() {
            return (byte) 3;
        }

        public int getSmallSofarBytes() {
            return this.sofarBytes;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(this.sofarBytes);
        }

        ProgressMessageSnapshot(Parcel in) {
            super(in);
            this.sofarBytes = in.readInt();
        }
    }

    public static class RetryMessageSnapshot extends ErrorMessageSnapshot {
        private final int retryingTimes;

        RetryMessageSnapshot(int id, int sofarBytes, Throwable throwable, int retryingTimes) {
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
        WarnMessageSnapshot(int id, int sofarBytes, int totalBytes) {
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
        WarnFlowDirectlySnapshot(int id, int sofarBytes, int totalBytes) {
            super(id, sofarBytes, totalBytes);
        }

        WarnFlowDirectlySnapshot(Parcel in) {
            super(in);
        }
    }

    SmallMessageSnapshot(int id) {
        super(id);
        this.isLargeFile = false;
    }

    SmallMessageSnapshot(Parcel in) {
        super(in);
    }

    public long getLargeTotalBytes() {
        return (long) getSmallTotalBytes();
    }

    public long getLargeSofarBytes() {
        return (long) getSmallSofarBytes();
    }
}
