package com.feng.library.okhttp.request;

import java.util.Map;
import okhttp3.Request;
import okhttp3.RequestBody;

public class GetRequest extends OkHttpRequest {
    public GetRequest(String url, Object tag, Map<String, String> params, Map<String, String> headers) {
        super(url, tag, params, headers);
    }

    protected RequestBody buildRequestBody() {
        return null;
    }

    protected Request buildRequest(RequestBody requestBody) {
        return this.builder.get().build();
    }
}
