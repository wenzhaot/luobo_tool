package com.feng.car.operation;

public abstract class OperationCallback {
    public abstract void onFailure(int i, String str, Throwable th);

    public abstract void onFinish();

    public abstract void onNetworkError();

    public abstract void onStart();

    public abstract void onSuccess(String str);
}
