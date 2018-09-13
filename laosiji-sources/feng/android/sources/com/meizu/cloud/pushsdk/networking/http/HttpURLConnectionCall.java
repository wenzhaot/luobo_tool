package com.meizu.cloud.pushsdk.networking.http;

import com.feng.car.utils.PermissionsConstant;
import com.feng.library.okhttp.utils.OkHttpUtils.METHOD;
import com.meizu.cloud.pushsdk.networking.common.ANLog;
import com.meizu.cloud.pushsdk.networking.http.Response.Builder;
import com.meizu.cloud.pushsdk.networking.okio.BufferedSink;
import com.meizu.cloud.pushsdk.networking.okio.BufferedSource;
import com.meizu.cloud.pushsdk.networking.okio.Okio;
import com.umeng.message.util.HttpRequest;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpURLConnectionCall implements Call {
    public Response execute(Request request) throws IOException {
        HttpURLConnection connection = openConnection(request);
        for (String headerName : request.headers().names()) {
            String value = request.header(headerName);
            ANLog.i("current header name " + headerName + " value " + value);
            connection.addRequestProperty(headerName, value);
        }
        setConnectionParametersForRequest(connection, request);
        int responseCode = connection.getResponseCode();
        return new Builder().code(responseCode).headers(request.headers()).message(connection.getResponseMessage()).request(request).body(createOkBody(connection)).build();
    }

    private static ResponseBody createOkBody(final HttpURLConnection urlConnection) throws IOException {
        if (!urlConnection.getDoInput()) {
            return null;
        }
        final BufferedSource body = Okio.buffer(Okio.source(isSuccessfulSend(urlConnection.getResponseCode()) ? urlConnection.getInputStream() : urlConnection.getErrorStream()));
        return new ResponseBody() {
            public MediaType contentType() {
                String contentTypeHeader = urlConnection.getContentType();
                return contentTypeHeader == null ? null : MediaType.parse(contentTypeHeader);
            }

            public long contentLength() {
                return HttpURLConnectionCall.stringToLong(urlConnection.getHeaderField(HttpRequest.HEADER_CONTENT_LENGTH));
            }

            public BufferedSource source() {
                return body;
            }
        };
    }

    protected static boolean isSuccessfulSend(int code) {
        return code >= 200 && code < 300;
    }

    private static long stringToLong(String s) {
        long j = -1;
        if (s == null) {
            return j;
        }
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException e) {
            return j;
        }
    }

    public void cancel() {
    }

    public boolean isExecuted() {
        return false;
    }

    public boolean isCanceled() {
        return false;
    }

    private HttpURLConnection openConnection(Request request) throws IOException {
        String url = request.url().toString();
        HttpURLConnection connection = createConnection(new URL(url));
        connection.setConnectTimeout(PermissionsConstant.CODE_FOR_DOWN_WRITE_PERMISSION_BASE);
        connection.setReadTimeout(PermissionsConstant.CODE_FOR_DOWN_WRITE_PERMISSION_BASE);
        connection.setUseCaches(false);
        connection.setDoInput(true);
        if (!request.isHttps() || url.startsWith("https://push.statics")) {
        }
        return connection;
    }

    protected HttpURLConnection createConnection(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setInstanceFollowRedirects(HttpURLConnection.getFollowRedirects());
        return connection;
    }

    static void setConnectionParametersForRequest(HttpURLConnection connection, Request request) throws IOException {
        switch (request.getmethod()) {
            case 0:
                connection.setRequestMethod(HttpRequest.METHOD_GET);
                return;
            case 1:
                connection.setRequestMethod(HttpRequest.METHOD_POST);
                addBodyIfExists(connection, request);
                return;
            case 2:
                connection.setRequestMethod("PUT");
                addBodyIfExists(connection, request);
                return;
            case 3:
                connection.setRequestMethod("DELETE");
                return;
            case 4:
                connection.setRequestMethod("HEAD");
                return;
            case 5:
                connection.setRequestMethod(METHOD.PATCH);
                addBodyIfExists(connection, request);
                return;
            default:
                throw new IllegalStateException("Unknown method type.");
        }
    }

    private static void addBodyIfExists(HttpURLConnection connection, Request request) throws IOException {
        RequestBody body = request.body();
        if (body != null) {
            connection.setDoOutput(true);
            connection.addRequestProperty("Content-Type", body.contentType().toString());
            BufferedSink fromSink = Okio.buffer(Okio.sink(connection.getOutputStream()));
            body.writeTo(fromSink);
            fromSink.close();
        }
    }
}
