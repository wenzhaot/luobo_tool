package com.feng.library.okhttp.builder;

import com.feng.library.okhttp.request.PostStringRequest;
import com.feng.library.okhttp.request.RequestCall;
import java.util.LinkedHashMap;
import java.util.Map;
import okhttp3.MediaType;

public class PostStringBuilder extends OkHttpRequestBuilder {
    private String content;
    private MediaType mediaType;

    public PostStringBuilder content(String content) {
        this.content = content;
        return this;
    }

    public PostStringBuilder mediaType(MediaType mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    public RequestCall build() {
        return new PostStringRequest(this.url, this.tag, this.params, this.headers, this.content, this.mediaType).build();
    }

    public PostStringBuilder url(String url) {
        this.url = url;
        return this;
    }

    public PostStringBuilder tag(Object tag) {
        this.tag = tag;
        return this;
    }

    public PostStringBuilder headers(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public PostStringBuilder addHeader(String key, String val) {
        if (this.headers == null) {
            this.headers = new LinkedHashMap();
        }
        this.headers.put(key, val);
        return this;
    }
}
