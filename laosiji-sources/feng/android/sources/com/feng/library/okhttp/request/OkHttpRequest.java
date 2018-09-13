package com.feng.library.okhttp.request;

import com.feng.library.okhttp.callback.Callback;
import com.feng.library.okhttp.utils.Exceptions;
import java.util.Map;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;

public abstract class OkHttpRequest {
    protected Builder builder = new Builder();
    protected Map<String, String> headers;
    protected Map<String, String> params;
    protected Object tag;
    protected String url;

    protected abstract Request buildRequest(RequestBody requestBody);

    protected abstract RequestBody buildRequestBody();

    protected OkHttpRequest(String url, Object tag, Map<String, String> params, Map<String, String> headers) {
        this.url = url;
        this.tag = tag;
        this.params = params;
        this.headers = headers;
        if (url == null) {
            Exceptions.illegalArgument("url can not be null.", new Object[0]);
        }
        initBuilder();
    }

    private void initBuilder() {
        this.builder.url(this.url).tag(this.tag);
        appendHeaders();
    }

    protected RequestBody wrapRequestBody(RequestBody requestBody, Callback callback) {
        return requestBody;
    }

    public RequestCall build() {
        return new RequestCall(this);
    }

    public Request generateRequest(Callback callback) {
        return buildRequest(wrapRequestBody(buildRequestBody(), callback));
    }

    protected void appendHeaders() {
        Headers.Builder headerBuilder = new Headers.Builder();
        if (this.headers != null && !this.headers.isEmpty()) {
            for (String key : this.headers.keySet()) {
                try {
                    headerBuilder.add(key, (String) this.headers.get(key));
                } catch (Exception e) {
                    e.printStackTrace();
                    headerBuilder.add(key, "");
                }
            }
            this.builder.headers(headerBuilder.build());
        }
    }
}
