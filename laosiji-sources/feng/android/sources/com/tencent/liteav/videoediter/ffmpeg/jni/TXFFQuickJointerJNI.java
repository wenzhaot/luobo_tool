package com.tencent.liteav.videoediter.ffmpeg.jni;

import android.util.Log;
import com.tencent.liteav.basic.log.TXCLog;
import java.util.List;

public class TXFFQuickJointerJNI {
    private static final String TAG = "TXFFQuickJointerJNI";
    private long handle;
    private boolean isInitSuccess;
    private a mCallback;
    private int mTotalVideoNums;

    public interface a {
        void a(float f);
    }

    private native void destroy(long j);

    private native int getVideoHeight(long j);

    private native int getVideoWidth(long j);

    private native long init();

    private native void setDstPath(long j, String str);

    private native void setSrcPaths(long j, String[] strArr);

    private native int start(long j);

    private native int stop(long j);

    private native int verify(long j);

    public TXFFQuickJointerJNI() {
        this.handle = -1;
        this.handle = init();
        if (this.handle != -1) {
            this.isInitSuccess = true;
        }
    }

    public synchronized void destroy() {
        if (this.isInitSuccess) {
            destroy(this.handle);
            this.isInitSuccess = false;
            this.handle = -1;
        }
    }

    public synchronized int getVideoWidth() {
        int videoWidth;
        if (this.isInitSuccess) {
            videoWidth = getVideoWidth(this.handle);
        } else {
            videoWidth = 0;
        }
        return videoWidth;
    }

    public synchronized int getVideoHeight() {
        int videoHeight;
        if (this.isInitSuccess) {
            videoHeight = getVideoHeight(this.handle);
        } else {
            videoHeight = 0;
        }
        return videoHeight;
    }

    public synchronized void setSrcPaths(List<String> list) {
        if (this.isInitSuccess) {
            if (list == null || list.size() == 0) {
                TXCLog.e(TAG, "quick joiner path empty!!!");
            } else {
                this.mTotalVideoNums = list.size();
                String[] strArr = new String[list.size()];
                int i = 0;
                while (true) {
                    int i2 = i;
                    if (i2 >= list.size()) {
                        break;
                    }
                    strArr[i2] = (String) list.get(i2);
                    i = i2 + 1;
                }
                setSrcPaths(this.handle, strArr);
            }
        }
    }

    public synchronized void setDstPath(String str) {
        if (this.isInitSuccess) {
            setDstPath(this.handle, str);
        }
    }

    public synchronized int start() {
        int i = -1;
        synchronized (this) {
            if (this.isInitSuccess) {
                if (this.mTotalVideoNums == 0) {
                    TXCLog.e(TAG, "quick joiner path empty!!!");
                } else {
                    i = start(this.handle);
                }
            }
        }
        return i;
    }

    public synchronized int verify() {
        int verify;
        if (this.isInitSuccess) {
            verify = verify(this.handle);
        } else {
            verify = -1;
        }
        return verify;
    }

    public synchronized void stop() {
        if (this.isInitSuccess) {
            stop(this.handle);
        }
    }

    public void setOnJoinerCallback(a aVar) {
        this.mCallback = aVar;
    }

    public void callbackFromNative(int i) {
        Log.i(TAG, "callbackFromNative: index = " + i);
        if (this.mCallback != null) {
            this.mCallback.a(this.mTotalVideoNums > 0 ? ((float) (i + 1)) / ((float) this.mTotalVideoNums) : 0.0f);
        }
    }
}
