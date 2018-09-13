package com.feng.library.okhttp.callback;

public abstract class OkHttpResponseCallback {
    public abstract void onFailure(int i, String str, Throwable th);

    public abstract void onFinish();

    public abstract void onNetworkError();

    public abstract void onStart();

    public abstract void onSuccess(int i, String str);
}
