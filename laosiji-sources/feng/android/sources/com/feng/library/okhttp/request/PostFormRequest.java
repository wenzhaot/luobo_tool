package com.feng.library.okhttp.request;

import com.feng.library.okhttp.builder.PostFormBuilder.FileInput;
import com.feng.library.okhttp.callback.Callback;
import com.feng.library.okhttp.request.CountingRequestBody.Listener;
import com.feng.library.okhttp.utils.OkHttpUtils;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import okhttp3.FormBody.Builder;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class PostFormRequest extends OkHttpRequest {
    private List<FileInput> files;

    public PostFormRequest(String url, Object tag, Map<String, String> params, Map<String, String> headers, List<FileInput> files) {
        super(url, tag, params, headers);
        this.files = files;
    }

    protected RequestBody buildRequestBody() {
        if (this.files == null || this.files.isEmpty()) {
            Builder builder = new Builder();
            addParams(builder);
            return builder.build();
        }
        MultipartBody.Builder builder2 = new MultipartBody.Builder().setType(MultipartBody.FORM);
        addParams(builder2);
        for (int i = 0; i < this.files.size(); i++) {
            FileInput fileInput = (FileInput) this.files.get(i);
            builder2.addFormDataPart(fileInput.key, fileInput.filename, RequestBody.create(MediaType.parse(guessMimeType(fileInput.filename)), fileInput.file));
        }
        return builder2.build();
    }

    protected RequestBody wrapRequestBody(RequestBody requestBody, final Callback callback) {
        if (callback == null) {
            return requestBody;
        }
        return new CountingRequestBody(requestBody, new Listener() {
            public void onRequestProgress(long bytesWritten, long contentLength) {
                final long j = bytesWritten;
                final long j2 = contentLength;
                OkHttpUtils.getInstance().getDelivery().post(new Runnable() {
                    public void run() {
                        callback.inProgress((((float) j) * 1.0f) / ((float) j2));
                    }
                });
            }
        });
    }

    protected Request buildRequest(RequestBody requestBody) {
        return this.builder.post(requestBody).build();
    }

    private String guessMimeType(String path) {
        String contentTypeFor = URLConnection.getFileNameMap().getContentTypeFor(path);
        if (contentTypeFor == null) {
            return "application/octet-stream";
        }
        return contentTypeFor;
    }

    private void addParams(MultipartBody.Builder builder) {
        if (this.params != null && !this.params.isEmpty()) {
            for (String key : this.params.keySet()) {
                String[] strArr = new String[]{"Content-Disposition", "form-data; name=\"" + key + "\""};
                builder.addPart(Headers.of(strArr), RequestBody.create(null, (String) this.params.get((String) r2.next())));
            }
        }
    }

    private void addParams(Builder builder) {
        if (this.params != null) {
            for (String key : this.params.keySet()) {
                builder.add(key, (String) this.params.get(key));
            }
        }
    }
}
