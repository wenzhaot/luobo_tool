package com.qiniu.android.storage;

import android.os.Looper;
import android.util.Log;
import com.qiniu.android.utils.AndroidNetwork;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public final class UploadOptions {
    final UpCancellationSignal cancellationSignal;
    final boolean checkCrc;
    final String mimeType;
    final NetReadyHandler netReadyHandler;
    final Map<String, String> params;
    final UpProgressHandler progressHandler;

    public UploadOptions(Map<String, String> params, String mimeType, boolean checkCrc, UpProgressHandler progressHandler, UpCancellationSignal cancellationSignal) {
        this(params, mimeType, checkCrc, progressHandler, cancellationSignal, null);
    }

    public UploadOptions(Map<String, String> params, String mimeType, boolean checkCrc, UpProgressHandler progressHandler, UpCancellationSignal cancellationSignal, NetReadyHandler netReadyHandler) {
        this.params = filterParam(params);
        this.mimeType = mime(mimeType);
        this.checkCrc = checkCrc;
        if (progressHandler == null) {
            progressHandler = new UpProgressHandler() {
                public void progress(String key, double percent) {
                    Log.d("Qiniu.UploadProgress", "" + percent);
                }
            };
        }
        this.progressHandler = progressHandler;
        if (cancellationSignal == null) {
            cancellationSignal = new UpCancellationSignal() {
                public boolean isCancelled() {
                    return false;
                }
            };
        }
        this.cancellationSignal = cancellationSignal;
        if (netReadyHandler == null) {
            netReadyHandler = new NetReadyHandler() {
                public void waitReady() {
                    if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
                        int i = 0;
                        while (i < 6) {
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (!AndroidNetwork.isNetWorkReady()) {
                                i++;
                            } else {
                                return;
                            }
                        }
                    }
                }
            };
        }
        this.netReadyHandler = netReadyHandler;
    }

    private static Map<String, String> filterParam(Map<String, String> params) {
        Map<String, String> ret = new HashMap();
        if (params != null) {
            for (Entry<String, String> i : params.entrySet()) {
                if (!(!((String) i.getKey()).startsWith("x:") || i.getValue() == null || ((String) i.getValue()).equals(""))) {
                    ret.put(i.getKey(), i.getValue());
                }
            }
        }
        return ret;
    }

    static UploadOptions defaultOptions() {
        return new UploadOptions(null, null, false, null, null);
    }

    private static String mime(String mimeType) {
        if (mimeType == null || mimeType.equals("")) {
            return "application/octet-stream";
        }
        return mimeType;
    }
}
