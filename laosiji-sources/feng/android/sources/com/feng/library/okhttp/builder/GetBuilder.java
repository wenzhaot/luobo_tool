package com.feng.library.okhttp.builder;

import com.feng.library.okhttp.request.GetRequest;
import com.feng.library.okhttp.request.RequestCall;
import java.util.LinkedHashMap;
import java.util.Map;

public class GetBuilder extends OkHttpRequestBuilder implements HasParamsable {
    public RequestCall build() {
        if (this.params != null) {
            this.url = appendParams(this.url, this.params);
        }
        return new GetRequest(this.url, this.tag, this.params, this.headers).build();
    }

    protected String appendParams(String url, Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        sb.append(url + "?");
        if (!(params == null || params.isEmpty())) {
            for (String key : params.keySet()) {
                sb.append(key).append("=").append((String) params.get(key)).append("&");
            }
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }

    public GetBuilder url(String url) {
        this.url = url;
        return this;
    }

    public GetBuilder tag(Object tag) {
        this.tag = tag;
        return this;
    }

    public GetBuilder params(Map<String, String> params) {
        this.params = params;
        return this;
    }

    public GetBuilder addParams(String key, String val) {
        if (this.params == null) {
            this.params = new LinkedHashMap();
        }
        this.params.put(key, val);
        return this;
    }

    public GetBuilder headers(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public GetBuilder addHeader(String key, String val) {
        if (this.headers == null) {
            this.headers = new LinkedHashMap();
        }
        this.headers.put(key, val);
        return this;
    }
}
