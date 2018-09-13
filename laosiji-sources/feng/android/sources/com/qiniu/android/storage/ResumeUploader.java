package com.qiniu.android.storage;

import com.feng.car.utils.FengConstant;
import com.qiniu.android.http.Client;
import com.qiniu.android.http.CompletionHandler;
import com.qiniu.android.http.ProgressHandler;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.utils.AndroidNetwork;
import com.qiniu.android.utils.Crc32;
import com.qiniu.android.utils.StringMap;
import com.qiniu.android.utils.StringUtils;
import com.qiniu.android.utils.UrlSafeBase64;
import com.tencent.ijk.media.player.IMediaPlayer;
import com.tencent.ijk.media.player.IjkMediaPlayer.OnNativeInvokeListener;
import com.umeng.message.util.HttpRequest;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;
import java.util.Map.Entry;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

final class ResumeUploader implements Runnable {
    private final byte[] chunkBuffer;
    private final Client client;
    private final UpCompletionHandler completionHandler;
    private final Configuration config;
    private final String[] contexts;
    private long crc32;
    private File f;
    private RandomAccessFile file = null;
    private final StringMap headers;
    private final String key;
    private final long modifyTime;
    private final UploadOptions options;
    private final String recorderKey;
    private UpToken token;
    private final long totalSize;

    ResumeUploader(Client client, Configuration config, File f, String key, UpToken token, final UpCompletionHandler completionHandler, UploadOptions options, String recorderKey) {
        this.client = client;
        this.config = config;
        this.f = f;
        this.recorderKey = recorderKey;
        this.totalSize = f.length();
        this.key = key;
        this.headers = new StringMap().put(HttpRequest.HEADER_AUTHORIZATION, "UpToken " + token.token);
        this.completionHandler = new UpCompletionHandler() {
            public void complete(String key, ResponseInfo info, JSONObject response) {
                if (ResumeUploader.this.file != null) {
                    try {
                        ResumeUploader.this.file.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                completionHandler.complete(key, info, response);
            }
        };
        if (options == null) {
            options = UploadOptions.defaultOptions();
        }
        this.options = options;
        this.chunkBuffer = new byte[config.chunkSize];
        this.contexts = new String[((int) (((this.totalSize + 4194304) - 1) / 4194304))];
        this.modifyTime = f.lastModified();
        this.token = token;
    }

    private static boolean isChunkOK(ResponseInfo info, JSONObject response) {
        return info.statusCode == 200 && info.error == null && (info.hasReqId() || isChunkResOK(response));
    }

    private static boolean isChunkResOK(JSONObject response) {
        try {
            response.getString("ctx");
            response.getLong("crc32");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isNotChunkToQiniu(ResponseInfo info, JSONObject response) {
        return info.statusCode < 500 && info.statusCode >= 200 && !info.hasReqId() && !isChunkResOK(response);
    }

    public void run() {
        long offset = recoveryFromRecord();
        try {
            this.file = new RandomAccessFile(this.f, "r");
            nextTask(offset, 0, this.config.zone.upHost(this.token.token, this.config.useHttps, null));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            this.completionHandler.complete(this.key, ResponseInfo.fileError(e, this.token), null);
        }
    }

    private void makeBlock(String upHost, long offset, int blockSize, int chunkSize, ProgressHandler progress, CompletionHandler _completionHandler, UpCancellationSignal c) {
        String path = String.format(Locale.ENGLISH, "/mkblk/%d", new Object[]{Integer.valueOf(blockSize)});
        try {
            this.file.seek(offset);
            this.file.read(this.chunkBuffer, 0, chunkSize);
            this.crc32 = Crc32.bytes(this.chunkBuffer, 0, chunkSize);
            post(String.format("%s%s", new Object[]{upHost, path}), this.chunkBuffer, 0, chunkSize, progress, _completionHandler, c);
        } catch (IOException e) {
            this.completionHandler.complete(this.key, ResponseInfo.fileError(e, this.token), null);
        }
    }

    private void putChunk(String upHost, long offset, int chunkSize, String context, ProgressHandler progress, CompletionHandler _completionHandler, UpCancellationSignal c) {
        int chunkOffset = (int) (offset % 4194304);
        String path = String.format(Locale.ENGLISH, "/bput/%s/%d", new Object[]{context, Integer.valueOf(chunkOffset)});
        try {
            this.file.seek(offset);
            this.file.read(this.chunkBuffer, 0, chunkSize);
            this.crc32 = Crc32.bytes(this.chunkBuffer, 0, chunkSize);
            post(String.format("%s%s", new Object[]{upHost, path}), this.chunkBuffer, 0, chunkSize, progress, _completionHandler, c);
        } catch (IOException e) {
            this.completionHandler.complete(this.key, ResponseInfo.fileError(e, this.token), null);
        }
    }

    private void makeFile(String upHost, CompletionHandler _completionHandler, UpCancellationSignal c) {
        String mime = String.format(Locale.ENGLISH, "/mimeType/%s/fname/%s", new Object[]{UrlSafeBase64.encodeToString(this.options.mimeType), UrlSafeBase64.encodeToString(this.f.getName())});
        String keyStr = "";
        if (this.key != null) {
            keyStr = String.format("/key/%s", new Object[]{UrlSafeBase64.encodeToString(this.key)});
        }
        String paramStr = "";
        if (this.options.params.size() != 0) {
            String[] str = new String[this.options.params.size()];
            int j = 0;
            for (Entry<String, String> i : this.options.params.entrySet()) {
                int j2 = j + 1;
                str[j] = String.format(Locale.ENGLISH, "%s/%s", new Object[]{i.getKey(), UrlSafeBase64.encodeToString((String) i.getValue())});
                j = j2;
            }
            paramStr = "/" + StringUtils.join(str, "/");
        }
        String path = String.format(Locale.ENGLISH, "/mkfile/%d%s%s%s", new Object[]{Long.valueOf(this.totalSize), mime, keyStr, paramStr});
        byte[] data = StringUtils.join(this.contexts, MiPushClient.ACCEPT_TIME_SEPARATOR).getBytes();
        post(String.format("%s%s", new Object[]{upHost, path}), data, 0, data.length, null, _completionHandler, c);
    }

    private void post(String upHost, byte[] data, int offset, int dataSize, ProgressHandler progress, CompletionHandler completion, UpCancellationSignal c) {
        this.client.asyncPost(upHost, data, offset, dataSize, this.headers, this.token, this.totalSize, progress, completion, c);
    }

    private long calcPutSize(long offset) {
        long left = this.totalSize - offset;
        return left < ((long) this.config.chunkSize) ? left : (long) this.config.chunkSize;
    }

    private long calcBlockSize(long offset) {
        long left = this.totalSize - offset;
        return left < 4194304 ? left : 4194304;
    }

    private boolean isCancelled() {
        return this.options.cancellationSignal.isCancelled();
    }

    private void nextTask(long offset, int retried, String upHost) {
        final long j;
        if (isCancelled()) {
            this.completionHandler.complete(this.key, ResponseInfo.cancelled(this.token), null);
        } else if (offset == this.totalSize) {
            final int i = retried;
            final String str = upHost;
            j = offset;
            makeFile(upHost, new CompletionHandler() {
                public void complete(ResponseInfo info, JSONObject response) {
                    if (info.isNetworkBroken() && !AndroidNetwork.isNetWorkReady()) {
                        ResumeUploader.this.options.netReadyHandler.waitReady();
                        if (!AndroidNetwork.isNetWorkReady()) {
                            ResumeUploader.this.completionHandler.complete(ResumeUploader.this.key, info, response);
                            return;
                        }
                    }
                    if (info.isOK()) {
                        ResumeUploader.this.removeRecord();
                        ResumeUploader.this.options.progressHandler.progress(ResumeUploader.this.key, 1.0d);
                        ResumeUploader.this.completionHandler.complete(ResumeUploader.this.key, info, response);
                        return;
                    }
                    if (info.needRetry() && i < ResumeUploader.this.config.retryMax + 1) {
                        String upHostRetry = ResumeUploader.this.config.zone.upHost(ResumeUploader.this.token.token, ResumeUploader.this.config.useHttps, str);
                        if (upHostRetry != null) {
                            ResumeUploader.this.nextTask(j, i + 1, upHostRetry);
                            return;
                        }
                    }
                    ResumeUploader.this.completionHandler.complete(ResumeUploader.this.key, info, response);
                }
            }, this.options.cancellationSignal);
        } else {
            final int chunkSize = (int) calcPutSize(offset);
            final long j2 = offset;
            ProgressHandler progress = new ProgressHandler() {
                public void onProgress(long bytesWritten, long totalSize) {
                    double percent = ((double) (j2 + bytesWritten)) / ((double) totalSize);
                    if (percent > 0.95d) {
                        percent = 0.95d;
                    }
                    ResumeUploader.this.options.progressHandler.progress(ResumeUploader.this.key, percent);
                }
            };
            final String str2 = upHost;
            final int i2 = retried;
            j = offset;
            CompletionHandler complete = new CompletionHandler() {
                public void complete(ResponseInfo info, JSONObject response) {
                    if (info.isNetworkBroken() && !AndroidNetwork.isNetWorkReady()) {
                        ResumeUploader.this.options.netReadyHandler.waitReady();
                        if (!AndroidNetwork.isNetWorkReady()) {
                            ResumeUploader.this.completionHandler.complete(ResumeUploader.this.key, info, response);
                            return;
                        }
                    }
                    if (info.isCancelled()) {
                        ResumeUploader.this.completionHandler.complete(ResumeUploader.this.key, info, response);
                    } else if (ResumeUploader.isChunkOK(info, response)) {
                        String context = null;
                        if (response != null || i2 >= ResumeUploader.this.config.retryMax) {
                            long crc = 0;
                            try {
                                context = response.getString("ctx");
                                crc = response.getLong("crc32");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if ((context == null || crc != ResumeUploader.this.crc32) && i2 < ResumeUploader.this.config.retryMax) {
                                ResumeUploader.this.nextTask(j, i2 + 1, ResumeUploader.this.config.zone.upHost(ResumeUploader.this.token.token, ResumeUploader.this.config.useHttps, str2));
                                return;
                            }
                            ResumeUploader.this.contexts[(int) (j / 4194304)] = context;
                            ResumeUploader.this.record(j + ((long) chunkSize));
                            ResumeUploader.this.nextTask(j + ((long) chunkSize), i2, str2);
                            return;
                        }
                        ResumeUploader.this.nextTask(j, i2 + 1, ResumeUploader.this.config.zone.upHost(ResumeUploader.this.token.token, ResumeUploader.this.config.useHttps, str2));
                    } else {
                        String upHostRetry = ResumeUploader.this.config.zone.upHost(ResumeUploader.this.token.token, ResumeUploader.this.config.useHttps, str2);
                        if (info.statusCode == IMediaPlayer.MEDIA_INFO_BUFFERING_START && i2 < ResumeUploader.this.config.retryMax) {
                            ResumeUploader.this.nextTask((j / 4194304) * 4194304, i2 + 1, str2);
                        } else if (upHostRetry == null || (!(ResumeUploader.isNotChunkToQiniu(info, response) || info.needRetry()) || i2 >= ResumeUploader.this.config.retryMax)) {
                            ResumeUploader.this.completionHandler.complete(ResumeUploader.this.key, info, response);
                        } else {
                            ResumeUploader.this.nextTask(j, i2 + 1, upHostRetry);
                        }
                    }
                }
            };
            if (offset % 4194304 == 0) {
                makeBlock(upHost, offset, (int) calcBlockSize(offset), chunkSize, progress, complete, this.options.cancellationSignal);
                return;
            }
            putChunk(upHost, offset, chunkSize, this.contexts[(int) (offset / 4194304)], progress, complete, this.options.cancellationSignal);
        }
    }

    private long recoveryFromRecord() {
        if (this.config.recorder == null) {
            return 0;
        }
        byte[] data = this.config.recorder.get(this.recorderKey);
        if (data == null) {
            return 0;
        }
        try {
            JSONObject obj = new JSONObject(new String(data));
            long offset = obj.optLong(OnNativeInvokeListener.ARG_OFFSET, 0);
            long modify = obj.optLong("modify_time", 0);
            long fSize = obj.optLong(FengConstant.SIZE, 0);
            JSONArray array = obj.optJSONArray("contexts");
            if (offset == 0 || modify != this.modifyTime || fSize != this.totalSize || array == null || array.length() == 0) {
                return 0;
            }
            for (int i = 0; i < array.length(); i++) {
                this.contexts[i] = array.optString(i);
            }
            return offset;
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private void removeRecord() {
        if (this.config.recorder != null) {
            this.config.recorder.del(this.recorderKey);
        }
    }

    private void record(long offset) {
        if (this.config.recorder != null && offset != 0) {
            this.config.recorder.set(this.recorderKey, String.format(Locale.ENGLISH, "{\"size\":%d,\"offset\":%d, \"modify_time\":%d, \"contexts\":[%s]}", new Object[]{Long.valueOf(this.totalSize), Long.valueOf(offset), Long.valueOf(this.modifyTime), StringUtils.jsonJoin(this.contexts)}).getBytes());
        }
    }

    private URI newURI(URI uri, String path) {
        try {
            return new URI(uri.getScheme(), null, uri.getHost(), uri.getPort(), path, null, null);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return uri;
        }
    }
}
