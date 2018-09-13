package com.meizu.cloud.pushsdk.networking.common;

import com.meizu.cloud.pushsdk.networking.error.ANError;
import com.meizu.cloud.pushsdk.networking.http.Response;

public class ANResponse<T> {
    private final ANError mANError;
    private final T mResult;
    private Response response;

    public static <T> ANResponse<T> success(T result) {
        return new ANResponse((Object) result);
    }

    public static <T> ANResponse<T> failed(ANError anError) {
        return new ANResponse(anError);
    }

    public ANResponse(T result) {
        this.mResult = result;
        this.mANError = null;
    }

    public ANResponse(ANError anError) {
        this.mResult = null;
        this.mANError = anError;
    }

    public T getResult() {
        return this.mResult;
    }

    public boolean isSuccess() {
        return this.mANError == null;
    }

    public ANError getError() {
        return this.mANError;
    }

    public void setOkHttpResponse(Response response) {
        this.response = response;
    }

    public Response getOkHttpResponse() {
        return this.response;
    }
}
