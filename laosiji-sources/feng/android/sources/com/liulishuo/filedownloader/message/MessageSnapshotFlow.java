package com.liulishuo.filedownloader.message;

public class MessageSnapshotFlow {
    private volatile MessageSnapshotThreadPool flowThreadPool;
    private volatile MessageReceiver receiver;

    public interface MessageReceiver {
        void receive(MessageSnapshot messageSnapshot);
    }

    public static final class HolderClass {
        private static final MessageSnapshotFlow INSTANCE = new MessageSnapshotFlow();
    }

    public static MessageSnapshotFlow getImpl() {
        return HolderClass.INSTANCE;
    }

    public void setReceiver(MessageReceiver receiver) {
        this.receiver = receiver;
        if (receiver == null) {
            this.flowThreadPool = null;
        } else {
            this.flowThreadPool = new MessageSnapshotThreadPool(5, receiver);
        }
    }

    public void inflow(MessageSnapshot snapshot) {
        if (snapshot instanceof IFlowDirectly) {
            if (this.receiver != null) {
                this.receiver.receive(snapshot);
            }
        } else if (this.flowThreadPool != null) {
            this.flowThreadPool.execute(snapshot);
        }
    }
}
