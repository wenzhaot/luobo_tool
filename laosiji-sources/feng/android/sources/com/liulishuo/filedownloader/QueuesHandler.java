package com.liulishuo.filedownloader;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Message;
import android.util.SparseArray;
import com.liulishuo.filedownloader.BaseDownloadTask.FinishListener;
import com.liulishuo.filedownloader.BaseDownloadTask.IRunningTask;
import com.liulishuo.filedownloader.util.FileDownloadLog;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import java.lang.ref.WeakReference;
import java.util.List;

class QueuesHandler implements IQueuesHandler {
    static final int WHAT_FREEZE = 2;
    static final int WHAT_SERIAL_NEXT = 1;
    static final int WHAT_UNFREEZE = 3;
    private final SparseArray<Handler> mRunningSerialMap = new SparseArray();

    private static class SerialFinishListener implements FinishListener {
        private int nextIndex;
        private final WeakReference<SerialHandlerCallback> wSerialHandlerCallback;

        private SerialFinishListener(WeakReference<SerialHandlerCallback> wSerialHandlerCallback) {
            this.wSerialHandlerCallback = wSerialHandlerCallback;
        }

        public FinishListener setNextIndex(int index) {
            this.nextIndex = index;
            return this;
        }

        public void over(BaseDownloadTask task) {
            if (this.wSerialHandlerCallback != null && this.wSerialHandlerCallback.get() != null) {
                ((SerialHandlerCallback) this.wSerialHandlerCallback.get()).goNext(this.nextIndex);
            }
        }
    }

    private class SerialHandlerCallback implements Callback {
        private Handler mHandler;
        private List<IRunningTask> mList;
        private int mRunningIndex = 0;
        private SerialFinishListener mSerialFinishListener = new SerialFinishListener(new WeakReference(this));

        SerialHandlerCallback() {
        }

        public void setHandler(Handler handler) {
            this.mHandler = handler;
        }

        public void setList(List<IRunningTask> list) {
            this.mList = list;
        }

        public boolean handleMessage(Message msg) {
            if (msg.what == 1) {
                if (msg.arg1 >= this.mList.size()) {
                    synchronized (QueuesHandler.this.mRunningSerialMap) {
                        QueuesHandler.this.mRunningSerialMap.remove(((IRunningTask) this.mList.get(0)).getAttachKey());
                    }
                    if (!(this.mHandler == null || this.mHandler.getLooper() == null)) {
                        this.mHandler.getLooper().quit();
                        this.mHandler = null;
                        this.mList = null;
                        this.mSerialFinishListener = null;
                    }
                    if (FileDownloadLog.NEED_LOG) {
                        FileDownloadListener fileDownloadListener;
                        Class cls = SerialHandlerCallback.class;
                        String str = "final serial %s %d";
                        Object[] objArr = new Object[2];
                        if (this.mList == null) {
                            fileDownloadListener = null;
                        } else if (this.mList.get(0) == null) {
                            fileDownloadListener = null;
                        } else {
                            fileDownloadListener = ((IRunningTask) this.mList.get(0)).getOrigin().getListener();
                        }
                        objArr[0] = fileDownloadListener;
                        objArr[1] = Integer.valueOf(msg.arg1);
                        FileDownloadLog.d(cls, str, objArr);
                    }
                } else {
                    this.mRunningIndex = msg.arg1;
                    IRunningTask stackTopTask = (IRunningTask) this.mList.get(this.mRunningIndex);
                    synchronized (stackTopTask.getPauseLock()) {
                        if (stackTopTask.getOrigin().getStatus() != (byte) 0 || FileDownloadList.getImpl().isNotContains(stackTopTask)) {
                            if (FileDownloadLog.NEED_LOG) {
                                FileDownloadLog.d(SerialHandlerCallback.class, "direct go next by not contains %s %d", stackTopTask, Integer.valueOf(msg.arg1));
                            }
                            goNext(msg.arg1 + 1);
                        } else {
                            stackTopTask.getOrigin().addFinishListener(this.mSerialFinishListener.setNextIndex(this.mRunningIndex + 1));
                            stackTopTask.startTaskByQueue();
                        }
                    }
                }
            } else if (msg.what == 2) {
                freeze();
            } else if (msg.what == 3) {
                unfreeze();
            }
            return true;
        }

        public void freeze() {
            ((IRunningTask) this.mList.get(this.mRunningIndex)).getOrigin().removeFinishListener(this.mSerialFinishListener);
            this.mHandler.removeCallbacksAndMessages(null);
        }

        public void unfreeze() {
            goNext(this.mRunningIndex);
        }

        private void goNext(int nextIndex) {
            FileDownloadListener fileDownloadListener = null;
            if (this.mHandler == null || this.mList == null) {
                FileDownloadLog.w(this, "need go next %d, but params is not ready %s %s", Integer.valueOf(nextIndex), this.mHandler, this.mList);
                return;
            }
            Message nextMsg = this.mHandler.obtainMessage();
            nextMsg.what = 1;
            nextMsg.arg1 = nextIndex;
            if (FileDownloadLog.NEED_LOG) {
                Class cls = SerialHandlerCallback.class;
                String str = "start next %s %s";
                Object[] objArr = new Object[2];
                if (!(this.mList == null || this.mList.get(0) == null)) {
                    fileDownloadListener = ((IRunningTask) this.mList.get(0)).getOrigin().getListener();
                }
                objArr[0] = fileDownloadListener;
                objArr[1] = Integer.valueOf(nextMsg.arg1);
                FileDownloadLog.d(cls, str, objArr);
            }
            this.mHandler.sendMessage(nextMsg);
        }
    }

    QueuesHandler() {
    }

    public boolean startQueueParallel(FileDownloadListener listener) {
        int attachKey = listener.hashCode();
        List<IRunningTask> list = FileDownloadList.getImpl().assembleTasksToStart(attachKey, listener);
        if (onAssembledTasksToStart(attachKey, list, listener, false)) {
            return false;
        }
        for (IRunningTask task : list) {
            task.startTaskByQueue();
        }
        return true;
    }

    public boolean startQueueSerial(FileDownloadListener listener) {
        SerialHandlerCallback callback = new SerialHandlerCallback();
        int attachKey = callback.hashCode();
        List<IRunningTask> list = FileDownloadList.getImpl().assembleTasksToStart(attachKey, listener);
        if (onAssembledTasksToStart(attachKey, list, listener, true)) {
            return false;
        }
        HandlerThread serialThread = new HandlerThread(FileDownloadUtils.formatString("filedownloader serial thread %s-%d", listener, Integer.valueOf(attachKey)));
        serialThread.start();
        Handler serialHandler = new Handler(serialThread.getLooper(), callback);
        callback.setHandler(serialHandler);
        callback.setList(list);
        callback.goNext(0);
        synchronized (this.mRunningSerialMap) {
            this.mRunningSerialMap.put(attachKey, serialHandler);
        }
        return true;
    }

    public void freezeAllSerialQueues() {
        for (int i = 0; i < this.mRunningSerialMap.size(); i++) {
            freezeSerialHandler((Handler) this.mRunningSerialMap.get(this.mRunningSerialMap.keyAt(i)));
        }
    }

    public void unFreezeSerialQueues(List<Integer> attachKeyList) {
        for (Integer attachKey : attachKeyList) {
            unFreezeSerialHandler((Handler) this.mRunningSerialMap.get(attachKey.intValue()));
        }
    }

    public int serialQueueSize() {
        return this.mRunningSerialMap.size();
    }

    public boolean contain(int attachKey) {
        return this.mRunningSerialMap.get(attachKey) != null;
    }

    private boolean onAssembledTasksToStart(int attachKey, List<IRunningTask> list, FileDownloadListener listener, boolean isSerial) {
        if (FileDownloadMonitor.isValid()) {
            FileDownloadMonitor.getMonitor().onRequestStart(list.size(), true, listener);
        }
        if (FileDownloadLog.NEED_LOG) {
            FileDownloadLog.v(FileDownloader.class, "start list attachKey[%d] size[%d] listener[%s] isSerial[%B]", Integer.valueOf(attachKey), Integer.valueOf(list.size()), listener, Boolean.valueOf(isSerial));
        }
        if (list != null && !list.isEmpty()) {
            return false;
        }
        FileDownloadLog.w(FileDownloader.class, "Tasks with the listener can't start, because can't find any task with the provided listener, maybe tasks instance has been started in the past, so they are all are inUsing, if in this case, you can use [BaseDownloadTask#reuse] to reuse theme first then start again: [%s, %B]", listener, Boolean.valueOf(isSerial));
        return true;
    }

    private void freezeSerialHandler(Handler handler) {
        handler.sendEmptyMessage(2);
    }

    private void unFreezeSerialHandler(Handler handler) {
        handler.sendEmptyMessage(3);
    }
}
