package com.umeng.socialize.net.dplus.cache;

public abstract class IReader<T> {
    private String a;
    public T result;

    public abstract void create(String str);

    public IReader(String str) {
        this.a = str;
    }

    public String getLogFileName() {
        return this.a;
    }

    public static double formatSize(long j) {
        return (((double) j) / 1024.0d) / 1024.0d;
    }
}
