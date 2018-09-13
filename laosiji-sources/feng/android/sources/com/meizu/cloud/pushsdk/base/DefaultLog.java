package com.meizu.cloud.pushsdk.base;

import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

class DefaultLog implements ICacheLog {
    private int mCacheCounter = 10;
    private long mCacheDuration = 60;
    private List<LogInfo> mCachedList = Collections.synchronizedList(new ArrayList());
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
    private boolean mDebugMode = false;
    private Handler mDelayHandler = new Handler(Looper.getMainLooper());
    private String mPath = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/pushSdk/defaultLog");
    private String mPid = String.valueOf(Process.myPid());
    private EncryptionWriter mWriter = new EncryptionWriter();

    class LogInfo {
        String header;
        String msg;
        String tag;

        public LogInfo(String level, String tag, String msg) {
            StringBuffer buffer = new StringBuffer(DefaultLog.this.mDateFormat.format(new Date()));
            buffer.append(" ");
            buffer.append(DefaultLog.this.mPid);
            buffer.append("-");
            buffer.append(String.valueOf(Thread.currentThread().getId()));
            buffer.append(" ");
            buffer.append(level);
            buffer.append("/");
            this.header = buffer.toString();
            this.tag = tag;
            this.msg = msg;
        }
    }

    private void startDelayTimer() {
        if (this.mCachedList.size() == 0) {
            this.mDelayHandler.postDelayed(new Runnable() {
                public void run() {
                    DefaultLog.this.flush(true);
                }
            }, this.mCacheDuration * 1000);
        }
    }

    private void checkLogCount() {
        if (this.mCachedList.size() == this.mCacheCounter) {
            flush(true);
        }
    }

    public void d(String tag, String msg) {
        if (this.mDebugMode) {
            Log.d(tag, msg);
        }
        synchronized (this.mCachedList) {
            startDelayTimer();
            addLogInfo(new LogInfo("D", tag, msg));
            checkLogCount();
        }
    }

    public void i(String tag, String msg) {
        if (this.mDebugMode) {
            Log.i(tag, msg);
        }
        synchronized (this.mCachedList) {
            startDelayTimer();
            addLogInfo(new LogInfo("I", tag, msg));
            checkLogCount();
        }
    }

    public void w(String tag, String msg) {
        if (this.mDebugMode) {
            Log.w(tag, msg);
        }
        synchronized (this.mCachedList) {
            startDelayTimer();
            addLogInfo(new LogInfo("W", tag, msg));
            checkLogCount();
        }
    }

    public void e(String tag, String msg) {
        if (this.mDebugMode) {
            Log.e(tag, msg);
        }
        synchronized (this.mCachedList) {
            startDelayTimer();
            addLogInfo(new LogInfo("E", tag, msg));
            checkLogCount();
        }
    }

    public void e(String tag, String msg, Throwable tr) {
        if (this.mDebugMode) {
            Log.e(tag, msg, tr);
        }
        synchronized (this.mCachedList) {
            startDelayTimer();
            addLogInfo(new LogInfo("E", tag, msg + "\n" + Log.getStackTraceString(tr)));
            checkLogCount();
        }
    }

    public void setCacheDuration(long seconds) {
        this.mCacheDuration = seconds;
    }

    public void setCacheCount(int counter) {
        this.mCacheCounter = counter;
    }

    public void setFilePath(String path) {
        this.mPath = path;
    }

    public void flush(boolean async) {
        Runnable task = new Runnable() {
            public void run() {
                List<LogInfo> tmp = new ArrayList();
                synchronized (DefaultLog.this.mCachedList) {
                    try {
                        DefaultLog.this.mDelayHandler.removeCallbacksAndMessages(null);
                        tmp.addAll(DefaultLog.this.mCachedList);
                        DefaultLog.this.mCachedList.clear();
                    } catch (Throwable th) {
                        while (true) {
                            throw th;
                        }
                    }
                }
                try {
                    DefaultLog.this.mWriter.open(DefaultLog.this.mPath);
                    for (LogInfo logInfo : tmp) {
                        DefaultLog.this.mWriter.write(logInfo.header, logInfo.tag, logInfo.msg);
                    }
                    try {
                        DefaultLog.this.mWriter.close();
                    } catch (Exception e) {
                    }
                } catch (Exception e2) {
                    try {
                        DefaultLog.this.mWriter.close();
                    } catch (Exception e3) {
                    }
                } catch (Throwable th2) {
                    try {
                        DefaultLog.this.mWriter.close();
                    } catch (Exception e4) {
                    }
                    throw th2;
                }
            }
        };
        if (async) {
            ExecutorProxy.get().execute(task);
        } else {
            task.run();
        }
    }

    public void setDebugMode(boolean debug) {
        this.mDebugMode = debug;
    }

    public boolean isDebugMode() {
        return this.mDebugMode;
    }

    private void addLogInfo(LogInfo logInfo) {
        try {
            this.mCachedList.add(logInfo);
        } catch (Exception e) {
            Log.e("Logger", "add logInfo error " + e.getMessage());
        }
    }
}
