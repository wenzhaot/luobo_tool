package com.liulishuo.filedownloader.message;

import com.liulishuo.filedownloader.message.MessageSnapshotFlow.MessageReceiver;
import com.liulishuo.filedownloader.util.FileDownloadExecutors;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class MessageSnapshotThreadPool {
    private final List<FlowSingleExecutor> executorList = new ArrayList();
    private final MessageReceiver receiver;

    public class FlowSingleExecutor {
        private final List<Integer> enQueueTaskIdList = new ArrayList();
        private final Executor mExecutor;

        public FlowSingleExecutor(int index) {
            this.mExecutor = FileDownloadExecutors.newDefaultThreadPool(1, "Flow-" + index);
        }

        public void enqueue(int id) {
            this.enQueueTaskIdList.add(Integer.valueOf(id));
        }

        public void execute(final MessageSnapshot snapshot) {
            this.mExecutor.execute(new Runnable() {
                public void run() {
                    MessageSnapshotThreadPool.this.receiver.receive(snapshot);
                    FlowSingleExecutor.this.enQueueTaskIdList.remove(Integer.valueOf(snapshot.getId()));
                }
            });
        }
    }

    MessageSnapshotThreadPool(int poolCount, MessageReceiver receiver) {
        this.receiver = receiver;
        for (int i = 0; i < poolCount; i++) {
            this.executorList.add(new FlowSingleExecutor(i));
        }
    }

    public void execute(MessageSnapshot snapshot) {
        FlowSingleExecutor targetPool = null;
        try {
            synchronized (this.executorList) {
                int id = snapshot.getId();
                for (FlowSingleExecutor executor : this.executorList) {
                    if (executor.enQueueTaskIdList.contains(Integer.valueOf(id))) {
                        targetPool = executor;
                        break;
                    }
                }
                if (targetPool == null) {
                    int leastTaskCount = 0;
                    for (FlowSingleExecutor executor2 : this.executorList) {
                        if (executor2.enQueueTaskIdList.size() <= 0) {
                            targetPool = executor2;
                            break;
                        }
                        if (leastTaskCount != 0) {
                            if (executor2.enQueueTaskIdList.size() >= leastTaskCount) {
                            }
                        }
                        leastTaskCount = executor2.enQueueTaskIdList.size();
                        targetPool = executor2;
                    }
                }
                targetPool.enqueue(id);
            }
            targetPool.execute(snapshot);
        } catch (Throwable th) {
            targetPool.execute(snapshot);
        }
    }
}
