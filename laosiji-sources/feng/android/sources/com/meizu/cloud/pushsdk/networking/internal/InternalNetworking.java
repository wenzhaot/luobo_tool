package com.meizu.cloud.pushsdk.networking.internal;

import com.meizu.cloud.pushsdk.networking.common.ANRequest;
import com.meizu.cloud.pushsdk.networking.error.ANError;
import com.meizu.cloud.pushsdk.networking.http.Headers;
import com.meizu.cloud.pushsdk.networking.http.HttpURLConnectionCall;
import com.meizu.cloud.pushsdk.networking.http.Request;
import com.meizu.cloud.pushsdk.networking.http.Request.Builder;
import com.meizu.cloud.pushsdk.networking.http.Response;
import com.meizu.cloud.pushsdk.networking.utils.Utils;
import java.io.File;

public final class InternalNetworking {
    public static String sUserAgent = null;

    private InternalNetworking() {
    }

    public static Response performSimpleRequest(ANRequest request) throws ANError {
        try {
            Builder builder = new Builder().url(request.getUrl());
            addHeadersToRequestBuilder(builder, request);
            switch (request.getMethod()) {
                case 0:
                    builder = builder.get();
                    break;
                case 1:
                    builder = builder.post(request.getRequestBody());
                    break;
                case 2:
                    builder = builder.put(request.getRequestBody());
                    break;
                case 3:
                    builder = builder.delete(request.getRequestBody());
                    break;
                case 4:
                    builder = builder.head();
                    break;
                case 5:
                    builder = builder.patch(request.getRequestBody());
                    break;
            }
            Request okHttpRequest = builder.build();
            request.setCall(new HttpURLConnectionCall());
            return request.getCall().execute(okHttpRequest);
        } catch (Throwable ioe) {
            throw new ANError(ioe);
        }
    }

    public static Response performDownloadRequest(ANRequest request) throws ANError {
        try {
            Builder builder = new Builder().url(request.getUrl());
            addHeadersToRequestBuilder(builder, request);
            Request okHttpRequest = builder.get().build();
            request.setCall(new HttpURLConnectionCall());
            Response okHttpResponse = request.getCall().execute(okHttpRequest);
            Utils.saveFile(okHttpResponse, request.getDirPath(), request.getFileName());
            return okHttpResponse;
        } catch (Throwable ioe) {
            try {
                File destinationFile = new File(request.getDirPath() + File.separator + request.getFileName());
                if (destinationFile.exists()) {
                    destinationFile.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            throw new ANError(ioe);
        }
    }

    public static Response performUploadRequest(ANRequest request) throws ANError {
        try {
            Builder builder = new Builder().url(request.getUrl());
            addHeadersToRequestBuilder(builder, request);
            Request okHttpRequest = builder.post(new RequestProgressBody(request.getMultiPartRequestBody(), request.getUploadProgressListener())).build();
            request.setCall(new HttpURLConnectionCall());
            return request.getCall().execute(okHttpRequest);
        } catch (Throwable ioe) {
            throw new ANError(ioe);
        }
    }

    public static void addHeadersToRequestBuilder(Builder builder, ANRequest request) {
        if (request.getUserAgent() != null) {
            builder.addHeader("User-Agent", request.getUserAgent());
        } else if (sUserAgent != null) {
            request.setUserAgent(sUserAgent);
            builder.addHeader("User-Agent", sUserAgent);
        }
        Headers requestHeaders = request.getHeaders();
        if (requestHeaders != null) {
            builder.headers(requestHeaders);
            if (request.getUserAgent() != null && !requestHeaders.names().contains("User-Agent")) {
                builder.addHeader("User-Agent", request.getUserAgent());
            }
        }
    }

    public static void setUserAgent(String userAgent) {
        sUserAgent = userAgent;
    }
}
