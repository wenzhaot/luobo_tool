package com.feng.library.okhttp.builder;

import com.feng.library.okhttp.request.PostFileRequest;
import com.feng.library.okhttp.request.RequestCall;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import okhttp3.MediaType;

public class PostFileBuilder extends OkHttpRequestBuilder {
    private File file;
    private MediaType mediaType;

    public OkHttpRequestBuilder file(File file) {
        this.file = file;
        return this;
    }

    public OkHttpRequestBuilder mediaType(MediaType mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    public RequestCall build() {
        return new PostFileRequest(this.url, this.tag, this.params, this.headers, this.file, this.mediaType).build();
    }

    public PostFileBuilder url(String url) {
        this.url = url;
        return this;
    }

    public PostFileBuilder tag(Object tag) {
        this.tag = tag;
        return this;
    }

    public PostFileBuilder headers(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public PostFileBuilder addHeader(String key, String val) {
        if (this.headers == null) {
            this.headers = new LinkedHashMap();
        }
        this.headers.put(key, val);
        return this;
    }
}
