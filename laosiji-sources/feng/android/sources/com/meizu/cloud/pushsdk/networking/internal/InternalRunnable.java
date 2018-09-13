package com.meizu.cloud.pushsdk.networking.internal;

import com.feng.car.utils.FengConstant;
import com.meizu.cloud.pushsdk.networking.common.ANLog;
import com.meizu.cloud.pushsdk.networking.common.ANRequest;
import com.meizu.cloud.pushsdk.networking.common.ANResponse;
import com.meizu.cloud.pushsdk.networking.common.Priority;
import com.meizu.cloud.pushsdk.networking.common.ResponseType;
import com.meizu.cloud.pushsdk.networking.core.Core;
import com.meizu.cloud.pushsdk.networking.error.ANError;
import com.meizu.cloud.pushsdk.networking.http.Response;
import com.meizu.cloud.pushsdk.networking.utils.SourceCloseUtil;
import com.meizu.cloud.pushsdk.networking.utils.Utils;

public class InternalRunnable implements Runnable {
    private final Priority priority;
    public final ANRequest request;
    public final int sequence;

    public InternalRunnable(ANRequest request) {
        this.request = request;
        this.sequence = request.getSequenceNumber();
        this.priority = request.getPriority();
    }

    public void run() {
        ANLog.d("execution started : " + this.request.toString());
        switch (this.request.getRequestType()) {
            case 0:
                executeSimpleRequest();
                break;
            case 1:
                executeDownloadRequest();
                break;
            case 2:
                executeUploadRequest();
                break;
        }
        ANLog.d("execution done : " + this.request.toString());
    }

    private void executeSimpleRequest() {
        Response okHttpResponse = null;
        try {
            okHttpResponse = InternalNetworking.performSimpleRequest(this.request);
            if (okHttpResponse == null) {
                deliverError(this.request, Utils.getErrorForConnection(new ANError()));
            } else if (this.request.getResponseAs() == ResponseType.OK_HTTP_RESPONSE) {
                this.request.deliverOkHttpResponse(okHttpResponse);
                SourceCloseUtil.close(okHttpResponse, this.request);
            } else if (okHttpResponse.code() >= FengConstant.SINGLE_IMAGE_MAX_WIDTH) {
                deliverError(this.request, Utils.getErrorForServerResponse(new ANError(okHttpResponse), this.request, okHttpResponse.code()));
                SourceCloseUtil.close(okHttpResponse, this.request);
            } else {
                ANResponse response = this.request.parseResponse(okHttpResponse);
                if (response.isSuccess()) {
                    response.setOkHttpResponse(okHttpResponse);
                    this.request.deliverResponse(response);
                    SourceCloseUtil.close(okHttpResponse, this.request);
                    return;
                }
                deliverError(this.request, response.getError());
                SourceCloseUtil.close(okHttpResponse, this.request);
            }
        } catch (Throwable e) {
            deliverError(this.request, Utils.getErrorForConnection(new ANError(e)));
        } finally {
            SourceCloseUtil.close(okHttpResponse, this.request);
        }
    }

    private void executeDownloadRequest() {
        try {
            Response okHttpResponse = InternalNetworking.performDownloadRequest(this.request);
            if (okHttpResponse == null) {
                deliverError(this.request, Utils.getErrorForConnection(new ANError()));
            } else if (okHttpResponse.code() >= FengConstant.SINGLE_IMAGE_MAX_WIDTH) {
                deliverError(this.request, Utils.getErrorForServerResponse(new ANError(okHttpResponse), this.request, okHttpResponse.code()));
            } else {
                this.request.updateDownloadCompletion();
            }
        } catch (Throwable e) {
            deliverError(this.request, Utils.getErrorForConnection(new ANError(e)));
        }
    }

    private void executeUploadRequest() {
        Response okHttpResponse = null;
        try {
            okHttpResponse = InternalNetworking.performUploadRequest(this.request);
            if (okHttpResponse == null) {
                deliverError(this.request, Utils.getErrorForConnection(new ANError()));
            } else if (this.request.getResponseAs() == ResponseType.OK_HTTP_RESPONSE) {
                this.request.deliverOkHttpResponse(okHttpResponse);
                SourceCloseUtil.close(okHttpResponse, this.request);
            } else if (okHttpResponse.code() >= FengConstant.SINGLE_IMAGE_MAX_WIDTH) {
                deliverError(this.request, Utils.getErrorForServerResponse(new ANError(okHttpResponse), this.request, okHttpResponse.code()));
                SourceCloseUtil.close(okHttpResponse, this.request);
            } else {
                ANResponse response = this.request.parseResponse(okHttpResponse);
                if (response.isSuccess()) {
                    response.setOkHttpResponse(okHttpResponse);
                    this.request.deliverResponse(response);
                    SourceCloseUtil.close(okHttpResponse, this.request);
                    return;
                }
                deliverError(this.request, response.getError());
                SourceCloseUtil.close(okHttpResponse, this.request);
            }
        } catch (Throwable e) {
            deliverError(this.request, Utils.getErrorForConnection(new ANError(e)));
        } finally {
            SourceCloseUtil.close(okHttpResponse, this.request);
        }
    }

    public Priority getPriority() {
        return this.priority;
    }

    private void deliverError(final ANRequest request, final ANError anError) {
        Core.getInstance().getExecutorSupplier().forMainThreadTasks().execute(new Runnable() {
            public void run() {
                request.deliverError(anError);
                request.finish();
            }
        });
    }
}
