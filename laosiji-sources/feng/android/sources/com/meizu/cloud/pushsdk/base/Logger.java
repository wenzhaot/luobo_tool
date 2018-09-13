package com.meizu.cloud.pushsdk.base;

import android.content.Context;
import android.os.Environment;

public class Logger extends Proxy<ICacheLog> implements ICacheLog {
    private static Logger sInstance;
    private boolean mInitialized = false;

    protected Logger(ICacheLog innerImpl) {
        super(innerImpl);
    }

    public static Logger get() {
        if (sInstance == null) {
            synchronized (Logger.class) {
                if (sInstance == null) {
                    sInstance = new Logger(new DefaultLog());
                }
            }
        }
        return sInstance;
    }

    public void init(Context context) {
        if (!this.mInitialized) {
            this.mInitialized = true;
            if ((context.getApplicationInfo().flags & 2) != 0) {
                setDebugMode(true);
            } else {
                setDebugMode(false);
            }
            setFilePath(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/pushSdk/" + context.getPackageName());
        }
    }

    public void d(String tag, String msg) {
        ((ICacheLog) getImpl()).d(tag, msg);
    }

    public void i(String tag, String msg) {
        ((ICacheLog) getImpl()).i(tag, msg);
    }

    public void w(String tag, String msg) {
        ((ICacheLog) getImpl()).w(tag, msg);
    }

    public void e(String tag, String msg) {
        ((ICacheLog) getImpl()).e(tag, msg);
    }

    public void e(String tag, String msg, Throwable tr) {
        ((ICacheLog) getImpl()).e(tag, msg, tr);
    }

    public void setCacheDuration(long seconds) {
        ((ICacheLog) getImpl()).setCacheDuration(seconds);
    }

    public void setCacheCount(int counter) {
        ((ICacheLog) getImpl()).setCacheCount(counter);
    }

    public void setFilePath(String path) {
        ((ICacheLog) getImpl()).setFilePath(path);
    }

    public void flush(boolean async) {
        ((ICacheLog) getImpl()).flush(async);
    }

    public void setDebugMode(boolean debug) {
        ((ICacheLog) getImpl()).setDebugMode(debug);
    }

    public boolean isDebugMode() {
        return ((ICacheLog) getImpl()).isDebugMode();
    }
}
