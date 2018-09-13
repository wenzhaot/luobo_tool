package com.liulishuo.filedownloader;

public interface IDownloadSpeed {

    public interface Monitor {
        void end(long j);

        void reset();

        void start(long j);

        void update(long j);
    }

    public interface Lookup {
        int getSpeed();

        void setMinIntervalUpdateSpeed(int i);
    }
}
