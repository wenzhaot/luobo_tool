package okhttp3;

import com.umeng.message.util.HttpRequest;
import javax.annotation.Nullable;

public final class MultipartBody$Part {
    final RequestBody body;
    @Nullable
    final Headers headers;

    public static MultipartBody$Part create(RequestBody body) {
        return create(null, body);
    }

    public static MultipartBody$Part create(@Nullable Headers headers, RequestBody body) {
        if (body == null) {
            throw new NullPointerException("body == null");
        } else if (headers != null && headers.get("Content-Type") != null) {
            throw new IllegalArgumentException("Unexpected header: Content-Type");
        } else if (headers == null || headers.get(HttpRequest.HEADER_CONTENT_LENGTH) == null) {
            return new MultipartBody$Part(headers, body);
        } else {
            throw new IllegalArgumentException("Unexpected header: Content-Length");
        }
    }

    public static MultipartBody$Part createFormData(String name, String value) {
        return createFormData(name, null, RequestBody.create(null, value));
    }

    public static MultipartBody$Part createFormData(String name, @Nullable String filename, RequestBody body) {
        if (name == null) {
            throw new NullPointerException("name == null");
        }
        StringBuilder disposition = new StringBuilder("form-data; name=");
        MultipartBody.appendQuotedString(disposition, name);
        if (filename != null) {
            disposition.append("; filename=");
            MultipartBody.appendQuotedString(disposition, filename);
        }
        return create(Headers.of("Content-Disposition", disposition.toString()), body);
    }

    private MultipartBody$Part(@Nullable Headers headers, RequestBody body) {
        this.headers = headers;
        this.body = body;
    }

    @Nullable
    public Headers headers() {
        return this.headers;
    }

    public RequestBody body() {
        return this.body;
    }
}
