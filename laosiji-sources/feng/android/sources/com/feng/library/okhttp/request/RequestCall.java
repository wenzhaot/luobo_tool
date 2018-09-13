package com.feng.library.okhttp.request;

import android.content.Context;
import com.feng.library.okhttp.callback.Callback;
import com.feng.library.okhttp.utils.OkHttpUtils;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class RequestCall {
    private Call call;
    private OkHttpClient clone;
    private long connTimeOut = OkHttpUtils.DEFAULT_MILLISECONDS;
    private OkHttpRequest okHttpRequest;
    private long readTimeOut = 0;
    private Request request;
    private long writeTimeOut = 0;

    public RequestCall(OkHttpRequest request) {
        this.okHttpRequest = request;
    }

    public RequestCall readTimeOut(long readTimeOut) {
        this.readTimeOut = readTimeOut;
        return this;
    }

    public RequestCall writeTimeOut(long writeTimeOut) {
        this.writeTimeOut = writeTimeOut;
        return this;
    }

    public RequestCall connTimeOut(long connTimeOut) {
        this.connTimeOut = connTimeOut;
        return this;
    }

    public Call buildCall(Context context, Callback callback) {
        long j = OkHttpUtils.DEFAULT_MILLISECONDS;
        this.request = generateRequest(callback);
        if (this.readTimeOut > 0 || this.writeTimeOut > 0 || this.connTimeOut > 0) {
            this.readTimeOut = this.readTimeOut > 0 ? this.readTimeOut : OkHttpUtils.DEFAULT_MILLISECONDS;
            this.writeTimeOut = this.writeTimeOut > 0 ? this.writeTimeOut : OkHttpUtils.DEFAULT_MILLISECONDS;
            if (this.connTimeOut > 0) {
                j = this.connTimeOut;
            }
            this.connTimeOut = j;
            this.clone = OkHttpUtils.getInstance(context).getOkHttpClient().newBuilder().readTimeout(this.readTimeOut, TimeUnit.MILLISECONDS).writeTimeout(this.writeTimeOut, TimeUnit.MILLISECONDS).connectTimeout(this.connTimeOut, TimeUnit.MILLISECONDS).retryOnConnectionFailure(true).build();
            this.call = this.clone.newCall(this.request);
        } else {
            this.call = OkHttpUtils.getInstance(context).getOkHttpClient().newCall(this.request);
        }
        return this.call;
    }

    private Request generateRequest(Callback callback) {
        return this.okHttpRequest.generateRequest(callback);
    }

    public void execute(Context context, Callback callback) {
        buildCall(context, callback);
        if (callback != null) {
            callback.onBefore(this.request);
        }
        OkHttpUtils.getInstance(context).execute(this, callback);
    }

    public Call getCall() {
        return this.call;
    }

    public Request getRequest() {
        return this.request;
    }

    public OkHttpRequest getOkHttpRequest() {
        return this.okHttpRequest;
    }

    public void cancel() {
        if (this.call != null) {
            this.call.cancel();
        }
    }
}
