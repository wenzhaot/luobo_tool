package com.qiniu.android.storage;

import com.qiniu.android.collect.Config;
import com.qiniu.android.collect.UploadInfoCollector;
import com.qiniu.android.collect.UploadInfoCollector.RecordMsg;
import com.qiniu.android.common.Zone.QueryHandler;
import com.qiniu.android.http.Client;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration.Builder;
import com.qiniu.android.utils.AsyncRun;
import com.qiniu.android.utils.StringUtils;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.io.File;
import org.json.JSONObject;

public final class UploadManager {
    private final Client client;
    private final Configuration config;

    private static class WarpHandler implements UpCompletionHandler {
        final long before = System.currentTimeMillis();
        final UpCompletionHandler complete;
        final long size;

        WarpHandler(UpCompletionHandler complete, long size) {
            this.complete = complete;
            this.size = size;
        }

        public void complete(final String key, final ResponseInfo res, final JSONObject response) {
            if (Config.isRecord) {
                final long after = System.currentTimeMillis();
                UploadInfoCollector.handleUpload(res.upToken, new RecordMsg() {
                    public String toRecordMsg() {
                        return StringUtils.join(new String[]{res.statusCode + "", res.reqId, res.host, res.ip, res.port + "", (after - WarpHandler.this.before) + "", res.timeStamp + "", WarpHandler.this.size + "", "block", WarpHandler.this.size + ""}, MiPushClient.ACCEPT_TIME_SEPARATOR);
                    }
                });
            }
            AsyncRun.runInMain(new Runnable() {
                public void run() {
                    try {
                        WarpHandler.this.complete.complete(key, res, response);
                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                }
            });
        }
    }

    public UploadManager() {
        this(new Builder().build());
    }

    public UploadManager(Configuration config) {
        this.config = config;
        this.client = new Client(config.proxy, config.connectTimeout, config.responseTimeout, config.urlConverter, config.dns);
    }

    public UploadManager(Recorder recorder, KeyGenerator keyGen) {
        this(new Builder().recorder(recorder, keyGen).build());
    }

    public UploadManager(Recorder recorder) {
        this(recorder, null);
    }

    private static boolean areInvalidArg(String key, byte[] data, File f, String token, UpToken decodedToken, UpCompletionHandler complete) {
        if (complete == null) {
            throw new IllegalArgumentException("no UpCompletionHandler");
        }
        String message = null;
        if (f == null && data == null) {
            message = "no input data";
        } else if (token == null || token.equals("")) {
            message = "no token";
        }
        ResponseInfo info = null;
        if (message != null) {
            info = ResponseInfo.invalidArgument(message, decodedToken);
        } else if (decodedToken == UpToken.NULL || decodedToken == null) {
            info = ResponseInfo.invalidToken("invalid token");
        } else if ((f != null && f.length() == 0) || (data != null && data.length == 0)) {
            info = ResponseInfo.zeroSize(decodedToken);
        }
        if (info == null) {
            return false;
        }
        complete.complete(key, info, null);
        return true;
    }

    private static ResponseInfo areInvalidArg(String key, byte[] data, File f, String token, UpToken decodedToken) {
        String message = null;
        if (f == null && data == null) {
            message = "no input data";
        } else if (token == null || token.equals("")) {
            message = "no token";
        }
        if (message != null) {
            return ResponseInfo.invalidArgument(message, decodedToken);
        }
        if (decodedToken == UpToken.NULL || decodedToken == null) {
            return ResponseInfo.invalidToken("invalid token");
        }
        if ((f == null || f.length() != 0) && (data == null || data.length != 0)) {
            return null;
        }
        return ResponseInfo.zeroSize(decodedToken);
    }

    private static WarpHandler warpHandler(UpCompletionHandler complete, long size) {
        return new WarpHandler(complete, size);
    }

    public void put(byte[] data, String key, String token, UpCompletionHandler complete, UploadOptions options) {
        final UpToken decodedToken = UpToken.parse(token);
        if (!areInvalidArg(key, data, null, token, decodedToken, complete)) {
            final byte[] bArr = data;
            final String str = key;
            final UpCompletionHandler upCompletionHandler = complete;
            final UploadOptions uploadOptions = options;
            this.config.zone.preQuery(token, new QueryHandler() {
                public void onSuccess() {
                    FormUploader.upload(UploadManager.this.client, UploadManager.this.config, bArr, str, decodedToken, upCompletionHandler, uploadOptions);
                }

                public void onFailure(int reason) {
                    ResponseInfo info;
                    if (ResponseInfo.isStatusCodeForBrokenNetwork(reason)) {
                        info = ResponseInfo.networkError(reason, decodedToken);
                    } else {
                        info = ResponseInfo.invalidToken("invalid token");
                    }
                    upCompletionHandler.complete(str, info, null);
                }
            });
        }
    }

    public void put(String filePath, String key, String token, UpCompletionHandler completionHandler, UploadOptions options) {
        put(new File(filePath), key, token, completionHandler, options);
    }

    public void put(File file, String key, String token, UpCompletionHandler complete, UploadOptions options) {
        final UpToken decodedToken = UpToken.parse(token);
        if (!areInvalidArg(key, null, file, token, decodedToken, complete)) {
            final File file2 = file;
            final String str = key;
            final UpCompletionHandler upCompletionHandler = complete;
            final UploadOptions uploadOptions = options;
            this.config.zone.preQuery(token, new QueryHandler() {
                public void onSuccess() {
                    if (file2.length() <= ((long) UploadManager.this.config.putThreshold)) {
                        FormUploader.upload(UploadManager.this.client, UploadManager.this.config, file2, str, decodedToken, upCompletionHandler, uploadOptions);
                        return;
                    }
                    AsyncRun.runInMain(new ResumeUploader(UploadManager.this.client, UploadManager.this.config, file2, str, decodedToken, UploadManager.warpHandler(upCompletionHandler, file2 != null ? file2.length() : 0), uploadOptions, UploadManager.this.config.keyGen.gen(str, file2)));
                }

                public void onFailure(int reason) {
                    ResponseInfo info;
                    if (ResponseInfo.isStatusCodeForBrokenNetwork(reason)) {
                        info = ResponseInfo.networkError(reason, decodedToken);
                    } else {
                        info = ResponseInfo.invalidToken("invalid token");
                    }
                    upCompletionHandler.complete(str, info, null);
                }
            });
        }
    }

    public ResponseInfo syncPut(byte[] data, String key, String token, UploadOptions options) {
        UpToken decodedToken = UpToken.parse(token);
        ResponseInfo info = areInvalidArg(key, data, null, token, decodedToken);
        return info != null ? info : FormUploader.syncUpload(this.client, this.config, data, key, decodedToken, options);
    }

    public ResponseInfo syncPut(File file, String key, String token, UploadOptions options) {
        UpToken decodedToken = UpToken.parse(token);
        ResponseInfo info = areInvalidArg(key, null, file, token, decodedToken);
        return info != null ? info : FormUploader.syncUpload(this.client, this.config, file, key, decodedToken, options);
    }

    public ResponseInfo syncPut(String file, String key, String token, UploadOptions options) {
        return syncPut(new File(file), key, token, options);
    }
}
