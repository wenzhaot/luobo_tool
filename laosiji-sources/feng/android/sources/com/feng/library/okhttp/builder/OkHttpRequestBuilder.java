package com.feng.library.okhttp.builder;

import com.feng.library.okhttp.request.RequestCall;
import java.util.Map;

public abstract class OkHttpRequestBuilder {
    protected Map<String, String> headers;
    protected Map<String, String> params;
    protected Object tag;
    protected String url;

    public abstract OkHttpRequestBuilder addHeader(String str, String str2);

    public abstract RequestCall build();

    public abstract OkHttpRequestBuilder headers(Map<String, String> map);

    public abstract OkHttpRequestBuilder tag(Object obj);

    public abstract OkHttpRequestBuilder url(String str);
}
