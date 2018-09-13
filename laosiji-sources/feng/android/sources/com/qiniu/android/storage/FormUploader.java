package com.qiniu.android.storage;

import android.util.Log;
import com.feng.car.utils.HttpConstant;
import com.qiniu.android.http.Client;
import com.qiniu.android.http.CompletionHandler;
import com.qiniu.android.http.PostArgs;
import com.qiniu.android.http.ProgressHandler;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.utils.AndroidNetwork;
import com.qiniu.android.utils.Crc32;
import com.qiniu.android.utils.StringMap;
import java.io.File;
import java.io.IOException;
import org.json.JSONObject;

final class FormUploader {
    FormUploader() {
    }

    static void upload(Client httpManager, Configuration config, byte[] data, String key, UpToken token, UpCompletionHandler completionHandler, UploadOptions options) {
        post(data, null, key, token, completionHandler, options, httpManager, config);
    }

    static void upload(Client client, Configuration config, File file, String key, UpToken token, UpCompletionHandler completionHandler, UploadOptions options) {
        post(null, file, key, token, completionHandler, options, client, config);
    }

    private static void post(byte[] data, File file, String k, UpToken token, UpCompletionHandler completionHandler, UploadOptions optionsIn, Client client, Configuration config) {
        final String key = k;
        StringMap params = new StringMap();
        final PostArgs args = new PostArgs();
        if (k != null) {
            params.put("key", key);
            args.fileName = key;
        } else {
            args.fileName = "?";
        }
        if (file != null) {
            args.fileName = file.getName();
        }
        params.put(HttpConstant.TOKEN, token.token);
        final UploadOptions options = optionsIn != null ? optionsIn : UploadOptions.defaultOptions();
        params.putFileds(options.params);
        long crc = 0;
        if (file != null) {
            try {
                crc = Crc32.file(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            crc = Crc32.bytes(data);
        }
        params.put("crc32", "" + crc);
        final ProgressHandler progress = new ProgressHandler() {
            public void onProgress(long bytesWritten, long totalSize) {
                double percent = ((double) bytesWritten) / ((double) totalSize);
                if (percent > 0.95d) {
                    percent = 0.95d;
                }
                options.progressHandler.progress(key, percent);
            }
        };
        args.data = data;
        args.file = file;
        args.mimeType = options.mimeType;
        args.params = params;
        final String upHost = config.zone.upHost(token.token, config.useHttps, null);
        Log.d("Qiniu.FormUploader", "upload use up host " + upHost);
        final UpCompletionHandler upCompletionHandler = completionHandler;
        final Configuration configuration = config;
        final UpToken upToken = token;
        final Client client2 = client;
        client.asyncMultipartPost(upHost, args, token, progress, new CompletionHandler() {
            public void complete(ResponseInfo info, JSONObject response) {
                if (info.isNetworkBroken() && !AndroidNetwork.isNetWorkReady()) {
                    options.netReadyHandler.waitReady();
                    if (!AndroidNetwork.isNetWorkReady()) {
                        upCompletionHandler.complete(key, info, response);
                        return;
                    }
                }
                if (info.isOK()) {
                    options.progressHandler.progress(key, 1.0d);
                    upCompletionHandler.complete(key, info, response);
                } else if (info.needRetry()) {
                    final String upHostRetry = configuration.zone.upHost(upToken.token, configuration.useHttps, upHost);
                    Log.d("Qiniu.FormUploader", "retry upload first time use up host " + upHostRetry);
                    client2.asyncMultipartPost(upHostRetry, args, upToken, progress, new CompletionHandler() {
                        public void complete(ResponseInfo info, JSONObject response) {
                            if (info.isOK()) {
                                options.progressHandler.progress(key, 1.0d);
                                upCompletionHandler.complete(key, info, response);
                            } else if (info.needRetry()) {
                                final String upHostRetry2 = configuration.zone.upHost(upToken.token, configuration.useHttps, upHostRetry);
                                Log.d("Qiniu.FormUploader", "retry upload second time use up host " + upHostRetry2);
                                client2.asyncMultipartPost(upHostRetry2, args, upToken, progress, new CompletionHandler() {
                                    public void complete(ResponseInfo info2, JSONObject response2) {
                                        if (info2.isOK()) {
                                            options.progressHandler.progress(key, 1.0d);
                                        } else if (info2.needRetry()) {
                                            configuration.zone.frozenDomain(upHostRetry2);
                                        }
                                        upCompletionHandler.complete(key, info2, response2);
                                    }
                                }, options.cancellationSignal);
                            } else {
                                upCompletionHandler.complete(key, info, response);
                            }
                        }
                    }, options.cancellationSignal);
                } else {
                    upCompletionHandler.complete(key, info, response);
                }
            }
        }, options.cancellationSignal);
    }

    public static ResponseInfo syncUpload(Client client, Configuration config, byte[] data, String key, UpToken token, UploadOptions options) {
        try {
            return syncUpload0(client, config, data, null, key, token, options);
        } catch (Exception e) {
            return ResponseInfo.create(null, 0, "", "", "", "", "", "", 0, 0, 0, e.getMessage(), token, data != null ? (long) data.length : 0);
        }
    }

    public static ResponseInfo syncUpload(Client client, Configuration config, File file, String key, UpToken token, UploadOptions options) {
        try {
            return syncUpload0(client, config, null, file, key, token, options);
        } catch (Exception e) {
            return ResponseInfo.create(null, 0, "", "", "", "", "", "", 0, 0, 0, e.getMessage(), token, file != null ? file.length() : 0);
        }
    }

    private static ResponseInfo syncUpload0(Client client, Configuration config, byte[] data, File file, String key, UpToken token, UploadOptions optionsIn) {
        StringMap params = new StringMap();
        PostArgs args = new PostArgs();
        if (key != null) {
            params.put("key", key);
            args.fileName = key;
        } else {
            args.fileName = "?";
        }
        if (file != null) {
            args.fileName = file.getName();
        }
        params.put(HttpConstant.TOKEN, token.token);
        UploadOptions options = optionsIn != null ? optionsIn : UploadOptions.defaultOptions();
        params.putFileds(options.params);
        long crc = 0;
        if (file != null) {
            try {
                crc = Crc32.file(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            crc = Crc32.bytes(data);
        }
        params.put("crc32", "" + crc);
        args.data = data;
        args.file = file;
        args.mimeType = options.mimeType;
        args.params = params;
        if (!config.zone.preQuery(token.token)) {
            return ResponseInfo.invalidToken("failed to get up host");
        }
        String upHost = config.zone.upHost(token.token, config.useHttps, null);
        Log.d("Qiniu.FormUploader", "sync upload use up host " + upHost);
        ResponseInfo info = client.syncMultipartPost(upHost, args, token);
        if (info.isOK() || !info.needRetry()) {
            return info;
        }
        if (info.isNetworkBroken() && !AndroidNetwork.isNetWorkReady()) {
            options.netReadyHandler.waitReady();
            if (!AndroidNetwork.isNetWorkReady()) {
                return info;
            }
        }
        String upHostRetry = config.zone.upHost(token.token, config.useHttps, upHost);
        Log.d("Qiniu.FormUploader", "sync upload retry first time use up host " + upHostRetry);
        info = client.syncMultipartPost(upHostRetry, args, token);
        if (!info.needRetry()) {
            return info;
        }
        if (info.isNetworkBroken() && !AndroidNetwork.isNetWorkReady()) {
            options.netReadyHandler.waitReady();
            if (!AndroidNetwork.isNetWorkReady()) {
                return info;
            }
        }
        String upHostRetry2 = config.zone.upHost(token.token, config.useHttps, upHostRetry);
        Log.d("Qiniu.FormUploader", "sync upload retry second time use up host " + upHostRetry2);
        info = client.syncMultipartPost(upHostRetry2, args, token);
        if (!info.needRetry()) {
            return info;
        }
        config.zone.frozenDomain(upHostRetry2);
        return info;
    }
}
