package com.meizu.cloud.pushsdk.base;

public class Proxy<T> {
    protected T mInnerImpl;
    protected T mOuterImpl;

    protected Proxy(T innerImpl) {
        if (innerImpl == null) {
            throw new RuntimeException("proxy must be has a default implementation");
        }
        this.mInnerImpl = innerImpl;
    }

    public void setImpl(T impl) {
        this.mOuterImpl = impl;
    }

    protected T getImpl() {
        if (this.mOuterImpl != null) {
            return this.mOuterImpl;
        }
        return this.mInnerImpl;
    }
}
