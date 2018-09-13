package com.alibaba.sdk.android.httpdns;

public class f extends Throwable {
    private int b;

    public f(int i, String str) {
        super(str);
        this.b = i;
    }

    public int getErrorCode() {
        return this.b;
    }
}
