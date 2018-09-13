package com.feng.library.okhttp.callback;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public abstract class Callback<T> {
    public static Callback CALLBACK_DEFAULT = new 1();

    public abstract void onError(Call call, Exception exception);

    public abstract void onResponse(T t);

    public abstract T parseNetworkResponse(Response response) throws Exception;

    public void onBefore(Request request) {
    }

    public void onAfter() {
    }

    public void inProgress(float progress) {
    }
}
