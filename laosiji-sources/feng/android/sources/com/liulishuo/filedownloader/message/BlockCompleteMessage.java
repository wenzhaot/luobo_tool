package com.liulishuo.filedownloader.message;

import com.liulishuo.filedownloader.util.FileDownloadUtils;

public interface BlockCompleteMessage {

    public static class BlockCompleteMessageImpl extends MessageSnapshot implements BlockCompleteMessage {
        private final MessageSnapshot mCompletedSnapshot;

        public BlockCompleteMessageImpl(MessageSnapshot snapshot) {
            super(snapshot.getId());
            if (snapshot.getStatus() != (byte) -3) {
                throw new IllegalArgumentException(FileDownloadUtils.formatString("can't create the block complete message for id[%d], status[%d]", Integer.valueOf(snapshot.getId()), Byte.valueOf(snapshot.getStatus())));
            } else {
                this.mCompletedSnapshot = snapshot;
            }
        }

        public MessageSnapshot transmitToCompleted() {
            return this.mCompletedSnapshot;
        }

        public byte getStatus() {
            return (byte) 4;
        }
    }

    MessageSnapshot transmitToCompleted();
}
