package com.liulishuo.filedownloader;

import android.app.Notification;
import android.os.Looper;
import java.io.File;

public class FileDownloadLine {

    interface ConnectSubscriber {
        void connected();

        Object getValue();
    }

    static class ConnectListener implements Runnable {
        private boolean mIsFinished = false;
        private final ConnectSubscriber mSubscriber;

        ConnectListener(ConnectSubscriber subscriber) {
            this.mSubscriber = subscriber;
        }

        public boolean isFinished() {
            return this.mIsFinished;
        }

        public void run() {
            synchronized (this) {
                this.mSubscriber.connected();
                this.mIsFinished = true;
                notifyAll();
            }
        }
    }

    public void startForeground(final int id, final Notification notification) {
        if (FileDownloader.getImpl().isServiceConnected()) {
            FileDownloader.getImpl().startForeground(id, notification);
        } else {
            wait(new ConnectSubscriber() {
                public void connected() {
                    FileDownloader.getImpl().startForeground(id, notification);
                }

                public Object getValue() {
                    return null;
                }
            });
        }
    }

    public long getSoFar(final int id) {
        if (FileDownloader.getImpl().isServiceConnected()) {
            return FileDownloader.getImpl().getSoFar(id);
        }
        ConnectSubscriber subscriber = new ConnectSubscriber() {
            private long mValue;

            public void connected() {
                this.mValue = FileDownloader.getImpl().getSoFar(id);
            }

            public Object getValue() {
                return Long.valueOf(this.mValue);
            }
        };
        wait(subscriber);
        return ((Long) subscriber.getValue()).longValue();
    }

    public long getTotal(final int id) {
        if (FileDownloader.getImpl().isServiceConnected()) {
            return FileDownloader.getImpl().getTotal(id);
        }
        ConnectSubscriber subscriber = new ConnectSubscriber() {
            private long mValue;

            public void connected() {
                this.mValue = FileDownloader.getImpl().getTotal(id);
            }

            public Object getValue() {
                return Long.valueOf(this.mValue);
            }
        };
        wait(subscriber);
        return ((Long) subscriber.getValue()).longValue();
    }

    public byte getStatus(final int id, final String path) {
        if (FileDownloader.getImpl().isServiceConnected()) {
            return FileDownloader.getImpl().getStatus(id, path);
        }
        if (path != null && new File(path).exists()) {
            return (byte) -3;
        }
        ConnectSubscriber subscriber = new ConnectSubscriber() {
            private byte mValue;

            public void connected() {
                this.mValue = FileDownloader.getImpl().getStatus(id, path);
            }

            public Object getValue() {
                return Byte.valueOf(this.mValue);
            }
        };
        wait(subscriber);
        return ((Byte) subscriber.getValue()).byteValue();
    }

    private void wait(ConnectSubscriber subscriber) {
        ConnectListener connectListener = new ConnectListener(subscriber);
        synchronized (connectListener) {
            FileDownloader.getImpl().bindService(connectListener);
            if (!connectListener.isFinished()) {
                if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
                    throw new IllegalThreadStateException("Sorry, FileDownloader can not block the main thread, because the system is also  callbacks ServiceConnection#onServiceConnected method in the main thread.");
                }
                try {
                    connectListener.wait(200000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
