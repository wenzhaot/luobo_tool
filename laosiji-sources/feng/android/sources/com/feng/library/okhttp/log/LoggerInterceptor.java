package com.feng.library.okhttp.log;

import android.text.TextUtils;
import android.util.Log;
import java.io.IOException;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Interceptor.Chain;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

public class LoggerInterceptor implements Interceptor {
    public static final String TAG = "OkHttpUtils";
    private boolean showResponse;
    private String tag;

    public LoggerInterceptor(String tag, boolean showResponse) {
        if (TextUtils.isEmpty(tag)) {
            tag = TAG;
        }
        this.showResponse = showResponse;
        this.tag = tag;
    }

    public LoggerInterceptor(String tag) {
        this(tag, false);
    }

    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        logForRequest(request);
        return logForResponse(chain.proceed(request));
    }

    private Response logForResponse(Response response) {
        try {
            Log.e(this.tag, "========response'log=======");
            Response clone = response.newBuilder().build();
            Log.e(this.tag, "url : " + clone.request().url());
            Log.e(this.tag, "code : " + clone.code());
            Log.e(this.tag, "protocol : " + clone.protocol());
            if (!TextUtils.isEmpty(clone.message())) {
                Log.e(this.tag, "message : " + clone.message());
            }
            if (this.showResponse) {
                ResponseBody body = clone.body();
                if (body != null) {
                    MediaType mediaType = body.contentType();
                    if (mediaType != null) {
                        Log.e(this.tag, "responseBody's contentType : " + mediaType.toString());
                        if (isText(mediaType)) {
                            String resp = body.string();
                            Log.e(this.tag, "responseBody's content : " + resp);
                            return response.newBuilder().body(ResponseBody.create(mediaType, resp)).build();
                        }
                        Log.e(this.tag, "responseBody's content :  maybe [file part] , too large too print , ignored!");
                    }
                }
            }
            Log.e(this.tag, "========response'log=======end");
            return response;
        } catch (Exception e) {
            return response;
        }
    }

    private void logForRequest(Request request) {
        try {
            String url = request.url().toString();
            Headers headers = request.headers();
            Log.e(this.tag, "========request'log=======");
            Log.e(this.tag, "method : " + request.method());
            Log.e(this.tag, "url : " + url);
            if (headers != null && headers.size() > 0) {
                Log.e(this.tag, "headers : " + headers.toString());
            }
            RequestBody requestBody = request.body();
            if (requestBody != null) {
                MediaType mediaType = requestBody.contentType();
                if (mediaType != null) {
                    Log.e(this.tag, "requestBody's contentType : " + mediaType.toString());
                    if (isText(mediaType)) {
                        Log.e(this.tag, "requestBody's content : " + bodyToString(request));
                    } else {
                        Log.e(this.tag, "requestBody's content :  maybe [file part] , too large too print , ignored!");
                    }
                }
            }
            Log.e(this.tag, "========request'log=======end");
        } catch (Exception e) {
        }
    }

    private boolean isText(MediaType mediaType) {
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        if (mediaType.subtype() == null || (!mediaType.subtype().equals("json") && !mediaType.subtype().equals("xml") && !mediaType.subtype().equals("html") && !mediaType.subtype().equals("webviewhtml"))) {
            return false;
        }
        return true;
    }

    private String bodyToString(Request request) {
        try {
            Request copy = request.newBuilder().build();
            Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (IOException e) {
            return "something error when show requestBody.";
        }
    }
}
