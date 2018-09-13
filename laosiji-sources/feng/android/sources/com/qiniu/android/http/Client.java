package com.qiniu.android.http;

import com.qiniu.android.common.Constants;
import com.qiniu.android.http.CancellationHandler.CancellationException;
import com.qiniu.android.storage.UpCancellationSignal;
import com.qiniu.android.storage.UpToken;
import com.qiniu.android.utils.AsyncRun;
import com.qiniu.android.utils.StringMap;
import com.qiniu.android.utils.StringMap.Consumer;
import com.qiniu.android.utils.StringUtils;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Dns;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Interceptor.Chain;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;

public final class Client {
    public static final String ContentTypeHeader = "Content-Type";
    public static final String DefaultMime = "application/octet-stream";
    public static final String FormMime = "application/x-www-form-urlencoded";
    public static final String JsonMime = "application/json";
    private final UrlConverter converter;
    private OkHttpClient httpClient;

    private static class ResponseTag {
        public long duration;
        public String ip;

        private ResponseTag() {
            this.ip = "";
            this.duration = -1;
        }

        /* synthetic */ ResponseTag(AnonymousClass1 x0) {
            this();
        }
    }

    public Client() {
        this(null, 10, 30, null, null);
    }

    public Client(ProxyConfiguration proxy, int connectTimeout, int responseTimeout, UrlConverter converter, final Dns dns) {
        this.converter = converter;
        Builder builder = new Builder();
        if (proxy != null) {
            builder.proxy(proxy.proxy());
            if (!(proxy.user == null || proxy.password == null)) {
                builder.proxyAuthenticator(proxy.authenticator());
            }
        }
        if (dns != null) {
            builder.dns(new Dns() {
                public List<InetAddress> lookup(String hostname) throws UnknownHostException {
                    try {
                        return dns.lookup(hostname);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return Dns.SYSTEM.lookup(hostname);
                    }
                }
            });
        }
        builder.networkInterceptors().add(new Interceptor() {
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                long before = System.currentTimeMillis();
                Response response = chain.proceed(request);
                long after = System.currentTimeMillis();
                ResponseTag tag = (ResponseTag) request.tag();
                String ip = "";
                try {
                    ip = chain.connection().socket().getRemoteSocketAddress().toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                tag.ip = ip;
                tag.duration = after - before;
                return response;
            }
        });
        builder.connectTimeout((long) connectTimeout, TimeUnit.SECONDS);
        builder.readTimeout((long) responseTimeout, TimeUnit.SECONDS);
        builder.writeTimeout(0, TimeUnit.SECONDS);
        this.httpClient = builder.build();
    }

    private static String via(Response response) {
        String via = response.header("X-Via", "");
        if (!via.equals("")) {
            return via;
        }
        via = response.header("X-Px", "");
        if (!via.equals("")) {
            return via;
        }
        via = response.header("Fw-Via", "");
        if (via.equals("")) {
            return via;
        }
        return via;
    }

    private static String ctype(Response response) {
        MediaType mediaType = response.body().contentType();
        if (mediaType == null) {
            return "";
        }
        return mediaType.type() + "/" + mediaType.subtype();
    }

    private static JSONObject buildJsonResp(byte[] body) throws Exception {
        String str = new String(body, Constants.UTF_8);
        if (StringUtils.isNullOrEmpty(str)) {
            return new JSONObject();
        }
        return new JSONObject(str);
    }

    private static ResponseInfo buildResponseInfo(Response response, String ip, long duration, UpToken upToken, long totalSize) {
        int code = response.code();
        String reqId = response.header("X-Reqid");
        reqId = reqId == null ? null : reqId.trim().split(MiPushClient.ACCEPT_TIME_SEPARATOR)[0];
        byte[] body = null;
        String error = null;
        try {
            body = response.body().bytes();
        } catch (IOException e) {
            error = e.getMessage();
        }
        JSONObject json = null;
        if (ctype(response).equals("application/json") && body != null) {
            try {
                json = buildJsonResp(body);
                if (response.code() != 200) {
                    error = json.optString("error", new String(body, Constants.UTF_8));
                }
            } catch (Exception e2) {
                if (response.code() < 300) {
                    error = e2.getMessage();
                }
            }
        } else if (body == null) {
            error = "null body";
        } else {
            String str = new String(body);
        }
        HttpUrl u = response.request().url();
        return ResponseInfo.create(json, code, reqId, response.header("X-Log"), via(response), u.host(), u.encodedPath(), ip, u.port(), duration, getContentLength(response), error, upToken, totalSize);
    }

    private static long getContentLength(Response response) {
        try {
            RequestBody body = response.request().body();
            if (body == null) {
                return 0;
            }
            return body.contentLength();
        } catch (Throwable th) {
            return -1;
        }
    }

    private static void onRet(Response response, String ip, long duration, UpToken upToken, long totalSize, final CompletionHandler complete) {
        final ResponseInfo info = buildResponseInfo(response, ip, duration, upToken, totalSize);
        AsyncRun.runInMain(new Runnable() {
            public void run() {
                complete.complete(info, info.response);
            }
        });
    }

    public void asyncSend(final Request.Builder requestBuilder, StringMap headers, UpToken upToken, long totalSize, CompletionHandler complete) {
        if (headers != null) {
            headers.forEach(new Consumer() {
                public void accept(String key, Object value) {
                    requestBuilder.header(key, value.toString());
                }
            });
        }
        if (upToken != null) {
            requestBuilder.header("User-Agent", UserAgent.instance().getUa(upToken.accessKey));
        } else {
            requestBuilder.header("User-Agent", UserAgent.instance().getUa("pandora"));
        }
        final ResponseTag tag = new ResponseTag();
        final UpToken upToken2 = upToken;
        final long j = totalSize;
        final CompletionHandler completionHandler = complete;
        this.httpClient.newCall(requestBuilder.tag(tag).build()).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                int statusCode = -1;
                String msg = e.getMessage();
                if (e instanceof CancellationException) {
                    statusCode = -2;
                } else if (e instanceof UnknownHostException) {
                    statusCode = -1003;
                } else {
                    if (msg != null) {
                        if (msg.indexOf("Broken pipe") == 0) {
                            statusCode = ResponseInfo.NetworkConnectionLost;
                        }
                    }
                    if (e instanceof SocketTimeoutException) {
                        statusCode = -1001;
                    } else if (e instanceof ConnectException) {
                        statusCode = -1004;
                    }
                }
                HttpUrl u = call.request().url();
                completionHandler.complete(ResponseInfo.create(null, statusCode, "", "", "", u.host(), u.encodedPath(), "", u.port(), tag.duration, -1, e.getMessage(), upToken2, j), null);
            }

            public void onResponse(Call call, Response response) throws IOException {
                ResponseTag tag = (ResponseTag) response.request().tag();
                Client.onRet(response, tag.ip, tag.duration, upToken2, j, completionHandler);
            }
        });
    }

    public void asyncPost(String url, byte[] body, StringMap headers, UpToken upToken, long totalSize, ProgressHandler progressHandler, CompletionHandler completionHandler, UpCancellationSignal c) {
        asyncPost(url, body, 0, body.length, headers, upToken, totalSize, progressHandler, completionHandler, c);
    }

    public void asyncPost(String url, byte[] body, int offset, int size, StringMap headers, UpToken upToken, long totalSize, ProgressHandler progressHandler, CompletionHandler completionHandler, CancellationHandler c) {
        RequestBody rbody;
        RequestBody rbody2;
        if (this.converter != null) {
            url = this.converter.convert(url);
        }
        if (body == null || body.length <= 0) {
            rbody = RequestBody.create(null, new byte[0]);
        } else {
            MediaType t = MediaType.parse("application/octet-stream");
            if (headers != null) {
                Object ct = headers.get("Content-Type");
                if (ct != null) {
                    t = MediaType.parse(ct.toString());
                }
            }
            rbody = RequestBody.create(t, body, offset, size);
        }
        if (progressHandler == null && c == null) {
            rbody2 = rbody;
        } else {
            rbody2 = new CountingRequestBody(rbody, progressHandler, totalSize, c);
        }
        asyncSend(new Request.Builder().url(url).post(rbody2), headers, upToken, totalSize, completionHandler);
    }

    public void asyncMultipartPost(String url, PostArgs args, UpToken upToken, ProgressHandler progressHandler, CompletionHandler completionHandler, CancellationHandler c) {
        RequestBody file;
        long totalSize;
        if (args.file != null) {
            file = RequestBody.create(MediaType.parse(args.mimeType), args.file);
            totalSize = args.file.length();
        } else {
            file = RequestBody.create(MediaType.parse(args.mimeType), args.data);
            totalSize = (long) args.data.length;
        }
        asyncMultipartPost(url, args.params, upToken, totalSize, progressHandler, args.fileName, file, completionHandler, c);
    }

    private void asyncMultipartPost(String url, StringMap fields, UpToken upToken, long totalSize, ProgressHandler progressHandler, String fileName, RequestBody file, CompletionHandler completionHandler, CancellationHandler cancellationHandler) {
        RequestBody body;
        if (this.converter != null) {
            url = this.converter.convert(url);
        }
        final MultipartBody.Builder mb = new MultipartBody.Builder();
        mb.addFormDataPart("file", fileName, file);
        fields.forEach(new Consumer() {
            public void accept(String key, Object value) {
                mb.addFormDataPart(key, value.toString());
            }
        });
        mb.setType(MediaType.parse("multipart/form-data"));
        RequestBody body2 = mb.build();
        if (progressHandler == null && cancellationHandler == null) {
            body = body2;
        } else {
            body = new CountingRequestBody(body2, progressHandler, totalSize, cancellationHandler);
        }
        asyncSend(new Request.Builder().url(url).post(body), null, upToken, totalSize, completionHandler);
    }

    public void asyncGet(String url, StringMap headers, UpToken upToken, CompletionHandler completionHandler) {
        asyncSend(new Request.Builder().get().url(url), headers, upToken, 0, completionHandler);
    }

    public ResponseInfo syncGet(String url, StringMap headers) {
        return send(new Request.Builder().get().url(url), headers);
    }

    private ResponseInfo send(Request.Builder requestBuilder, StringMap headers) {
        if (headers != null) {
            final Request.Builder builder = requestBuilder;
            headers.forEach(new Consumer() {
                public void accept(String key, Object value) {
                    builder.header(key, value.toString());
                }
            });
        }
        requestBuilder.header("User-Agent", UserAgent.instance().getUa(""));
        long start = System.currentTimeMillis();
        ResponseTag responseTag = new ResponseTag();
        Request req = requestBuilder.tag(responseTag).build();
        try {
            return buildResponseInfo(this.httpClient.newCall(req).execute(), responseTag.ip, responseTag.duration, UpToken.NULL, 0);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseInfo.create(null, -1, "", "", "", req.url().host(), req.url().encodedPath(), responseTag.ip, req.url().port(), responseTag.duration, -1, e.getMessage(), UpToken.NULL, 0);
        }
    }

    public ResponseInfo syncMultipartPost(String url, PostArgs args, UpToken upToken) {
        RequestBody file;
        long totalSize;
        if (args.file != null) {
            file = RequestBody.create(MediaType.parse(args.mimeType), args.file);
            totalSize = args.file.length();
        } else {
            file = RequestBody.create(MediaType.parse(args.mimeType), args.data);
            totalSize = (long) args.data.length;
        }
        return syncMultipartPost(url, args.params, upToken, totalSize, args.fileName, file);
    }

    private ResponseInfo syncMultipartPost(String url, StringMap fields, UpToken upToken, long totalSize, String fileName, RequestBody file) {
        final MultipartBody.Builder mb = new MultipartBody.Builder();
        mb.addFormDataPart("file", fileName, file);
        fields.forEach(new Consumer() {
            public void accept(String key, Object value) {
                mb.addFormDataPart(key, value.toString());
            }
        });
        mb.setType(MediaType.parse("multipart/form-data"));
        return syncSend(new Request.Builder().url(url).post(mb.build()), null, upToken, totalSize);
    }

    public ResponseInfo syncSend(Request.Builder requestBuilder, StringMap headers, UpToken upToken, long totalSize) {
        if (headers != null) {
            final Request.Builder builder = requestBuilder;
            headers.forEach(new Consumer() {
                public void accept(String key, Object value) {
                    builder.header(key, value.toString());
                }
            });
        }
        requestBuilder.header("User-Agent", UserAgent.instance().getUa(upToken.accessKey));
        ResponseTag responseTag = new ResponseTag();
        Request req = null;
        try {
            req = requestBuilder.tag(responseTag).build();
            return buildResponseInfo(this.httpClient.newCall(req).execute(), responseTag.ip, responseTag.duration, upToken, totalSize);
        } catch (Exception e) {
            e.printStackTrace();
            int statusCode = -1;
            String msg = e.getMessage();
            if (e instanceof UnknownHostException) {
                statusCode = -1003;
            } else {
                if (msg != null) {
                    if (msg.indexOf("Broken pipe") == 0) {
                        statusCode = ResponseInfo.NetworkConnectionLost;
                    }
                }
                if (e instanceof SocketTimeoutException) {
                    statusCode = -1001;
                } else if (e instanceof ConnectException) {
                    statusCode = -1004;
                }
            }
            HttpUrl u = req.url();
            return ResponseInfo.create(null, statusCode, "", "", "", u.host(), u.encodedPath(), "", u.port(), 0, 0, e.getMessage(), upToken, totalSize);
        }
    }
}
