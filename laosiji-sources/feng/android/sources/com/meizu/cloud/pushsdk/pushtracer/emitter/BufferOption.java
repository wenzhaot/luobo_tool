package com.meizu.cloud.pushsdk.pushtracer.emitter;

public enum BufferOption {
    Single(1),
    DefaultGroup(3),
    HeavyGroup(25);
    
    private int code;

    private BufferOption(int c) {
        this.code = c;
    }

    public int getCode() {
        return this.code;
    }
}
