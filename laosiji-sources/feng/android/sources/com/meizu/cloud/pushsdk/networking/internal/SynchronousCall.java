package com.meizu.cloud.pushsdk.networking.internal;

import com.feng.car.utils.FengConstant;
import com.meizu.cloud.pushsdk.networking.common.ANRequest;
import com.meizu.cloud.pushsdk.networking.common.ANResponse;
import com.meizu.cloud.pushsdk.networking.common.ResponseType;
import com.meizu.cloud.pushsdk.networking.error.ANError;
import com.meizu.cloud.pushsdk.networking.http.Response;
import com.meizu.cloud.pushsdk.networking.utils.SourceCloseUtil;
import com.meizu.cloud.pushsdk.networking.utils.Utils;

public final class SynchronousCall {
    private SynchronousCall() {
    }

    public static <T> ANResponse<T> execute(ANRequest request) {
        switch (request.getRequestType()) {
            case 0:
                return executeSimpleRequest(request);
            case 1:
                return executeDownloadRequest(request);
            case 2:
                return executeUploadRequest(request);
            default:
                return new ANResponse(new ANError());
        }
    }

    private static <T> ANResponse<T> executeSimpleRequest(ANRequest request) {
        ANResponse<T> response;
        Response okHttpResponse = null;
        try {
            okHttpResponse = InternalNetworking.performSimpleRequest(request);
            if (okHttpResponse == null) {
                response = new ANResponse(Utils.getErrorForConnection(new ANError()));
            } else if (request.getResponseAs() == ResponseType.OK_HTTP_RESPONSE) {
                response = new ANResponse((Object) okHttpResponse);
                response.setOkHttpResponse(okHttpResponse);
                SourceCloseUtil.close(okHttpResponse, request);
            } else if (okHttpResponse.code() >= FengConstant.SINGLE_IMAGE_MAX_WIDTH) {
                response = new ANResponse(Utils.getErrorForServerResponse(new ANError(okHttpResponse), request, okHttpResponse.code()));
                response.setOkHttpResponse(okHttpResponse);
                SourceCloseUtil.close(okHttpResponse, request);
            } else {
                response = request.parseResponse(okHttpResponse);
                response.setOkHttpResponse(okHttpResponse);
                SourceCloseUtil.close(okHttpResponse, request);
            }
        } catch (Throwable se) {
            response = new ANResponse(Utils.getErrorForConnection(new ANError(se)));
        } catch (Exception e) {
            response = new ANResponse(Utils.getErrorForNetworkOnMainThreadOrConnection(e));
        } finally {
            SourceCloseUtil.close(okHttpResponse, request);
        }
        return response;
    }

    private static <T> ANResponse<T> executeDownloadRequest(ANRequest request) {
        try {
            Response okHttpResponse = InternalNetworking.performDownloadRequest(request);
            if (okHttpResponse == null) {
                return new ANResponse(Utils.getErrorForConnection(new ANError()));
            }
            ANResponse<T> response;
            if (okHttpResponse.code() >= FengConstant.SINGLE_IMAGE_MAX_WIDTH) {
                response = new ANResponse(Utils.getErrorForServerResponse(new ANError(okHttpResponse), request, okHttpResponse.code()));
                response.setOkHttpResponse(okHttpResponse);
                return response;
            }
            response = new ANResponse((Object) "success");
            response.setOkHttpResponse(okHttpResponse);
            return response;
        } catch (Throwable se) {
            return new ANResponse(Utils.getErrorForConnection(new ANError(se)));
        } catch (Exception e) {
            return new ANResponse(Utils.getErrorForNetworkOnMainThreadOrConnection(e));
        }
    }

    private static <T> ANResponse<T> executeUploadRequest(ANRequest request) {
        ANResponse<T> response;
        Response okHttpResponse = null;
        try {
            okHttpResponse = InternalNetworking.performUploadRequest(request);
            if (okHttpResponse == null) {
                response = new ANResponse(Utils.getErrorForConnection(new ANError()));
            } else if (request.getResponseAs() == ResponseType.OK_HTTP_RESPONSE) {
                response = new ANResponse((Object) okHttpResponse);
                response.setOkHttpResponse(okHttpResponse);
                SourceCloseUtil.close(okHttpResponse, request);
            } else if (okHttpResponse.code() >= FengConstant.SINGLE_IMAGE_MAX_WIDTH) {
                response = new ANResponse(Utils.getErrorForServerResponse(new ANError(okHttpResponse), request, okHttpResponse.code()));
                response.setOkHttpResponse(okHttpResponse);
                SourceCloseUtil.close(okHttpResponse, request);
            } else {
                response = request.parseResponse(okHttpResponse);
                response.setOkHttpResponse(okHttpResponse);
                SourceCloseUtil.close(okHttpResponse, request);
            }
        } catch (ANError se) {
            response = new ANResponse(Utils.getErrorForConnection(se));
        } catch (Exception e) {
            response = new ANResponse(Utils.getErrorForNetworkOnMainThreadOrConnection(e));
        } finally {
            SourceCloseUtil.close(okHttpResponse, request);
        }
        return response;
    }
}
