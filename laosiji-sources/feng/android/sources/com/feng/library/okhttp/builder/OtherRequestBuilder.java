package com.feng.library.okhttp.builder;

import com.feng.library.okhttp.request.OtherRequest;
import com.feng.library.okhttp.request.RequestCall;
import java.util.LinkedHashMap;
import java.util.Map;
import okhttp3.RequestBody;

public class OtherRequestBuilder extends OkHttpRequestBuilder {
    private String content;
    private String method;
    private RequestBody requestBody;

    public OtherRequestBuilder(String method) {
        this.method = method;
    }

    public RequestCall build() {
        return new OtherRequest(this.requestBody, this.content, this.method, this.url, this.tag, this.params, this.headers).build();
    }

    public OtherRequestBuilder requestBody(RequestBody requestBody) {
        this.requestBody = requestBody;
        return this;
    }

    public OtherRequestBuilder requestBody(String content) {
        this.content = content;
        return this;
    }

    public OtherRequestBuilder url(String url) {
        this.url = url;
        return this;
    }

    public OtherRequestBuilder tag(Object tag) {
        this.tag = tag;
        return this;
    }

    public OtherRequestBuilder headers(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public OtherRequestBuilder addHeader(String key, String val) {
        if (this.headers == null) {
            this.headers = new LinkedHashMap();
        }
        this.headers.put(key, val);
        return this;
    }
}
