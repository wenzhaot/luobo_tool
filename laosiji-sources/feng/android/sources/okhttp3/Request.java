package okhttp3;

import java.util.List;
import javax.annotation.Nullable;

public final class Request {
    @Nullable
    final RequestBody body;
    private volatile CacheControl cacheControl;
    final Headers headers;
    final String method;
    final Object tag;
    final HttpUrl url;

    Request(Builder builder) {
        Object obj;
        this.url = builder.url;
        this.method = builder.method;
        this.headers = builder.headers.build();
        this.body = builder.body;
        if (builder.tag != null) {
            obj = builder.tag;
        } else {
            Request obj2 = this;
        }
        this.tag = obj2;
    }

    public HttpUrl url() {
        return this.url;
    }

    public String method() {
        return this.method;
    }

    public Headers headers() {
        return this.headers;
    }

    @Nullable
    public String header(String name) {
        return this.headers.get(name);
    }

    public List<String> headers(String name) {
        return this.headers.values(name);
    }

    @Nullable
    public RequestBody body() {
        return this.body;
    }

    public Object tag() {
        return this.tag;
    }

    public Builder newBuilder() {
        return new Builder(this);
    }

    public CacheControl cacheControl() {
        CacheControl result = this.cacheControl;
        if (result != null) {
            return result;
        }
        result = CacheControl.parse(this.headers);
        this.cacheControl = result;
        return result;
    }

    public boolean isHttps() {
        return this.url.isHttps();
    }

    public String toString() {
        return "Request{method=" + this.method + ", url=" + this.url + ", tag=" + (this.tag != this ? this.tag : null) + '}';
    }
}
