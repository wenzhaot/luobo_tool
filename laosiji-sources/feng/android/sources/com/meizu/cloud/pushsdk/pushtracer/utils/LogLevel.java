package com.meizu.cloud.pushsdk.pushtracer.utils;

public enum LogLevel {
    OFF(0),
    ERROR(1),
    DEBUG(2),
    VERBOSE(3);
    
    private int level;

    private LogLevel(int c) {
        this.level = c;
    }

    public int getLevel() {
        return this.level;
    }
}
