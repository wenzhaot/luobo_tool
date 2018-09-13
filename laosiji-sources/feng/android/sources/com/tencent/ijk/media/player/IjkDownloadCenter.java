package com.tencent.ijk.media.player;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.tencent.ijk.media.player.annotations.CalledByNative;
import com.tencent.liteav.basic.util.a;
import java.lang.ref.WeakReference;

public final class IjkDownloadCenter {
    private static final int DC_PROP_LONG_DOWNLOAD_SIZE = 1003;
    private static final int DC_PROP_LONG_SIZE = 1002;
    private static final int DC_PROP_STRING_URL = 1001;
    private static final int MSG_ERROR = 500;
    private static final int MSG_FINISH = 600;
    private static final int MSG_PROGRESS = 900;
    private static final int MSG_RESUME = 100;
    private static final int MSG_STOP = 300;
    private static final String TAG = "IjkDownloadCenter";
    private static IjkDownloadCenter instance = null;
    private static volatile boolean mIsLibLoaded = false;
    private static final IjkLibLoader sLocalLibLoader = new IjkLibLoader() {
        public void loadLibrary(String str) throws UnsatisfiedLinkError, SecurityException {
            a.a(str);
        }
    };
    private EventHandler mEventHandler;
    private OnDownloadListener mListener;

    private static class EventHandler extends Handler {
        private final WeakReference<IjkDownloadCenter> mWeakCenter;

        public EventHandler(IjkDownloadCenter ijkDownloadCenter, Looper looper) {
            super(looper);
            this.mWeakCenter = new WeakReference(ijkDownloadCenter);
        }

        public void handleMessage(Message message) {
            IjkDownloadCenter ijkDownloadCenter = (IjkDownloadCenter) this.mWeakCenter.get();
            if (ijkDownloadCenter != null && ijkDownloadCenter.mListener != null) {
                NativeEvent nativeEvent = (NativeEvent) message.obj;
                IjkDownloadMedia ijkDownloadMedia = nativeEvent.media;
                if (ijkDownloadMedia != null) {
                    switch (message.what) {
                        case 100:
                            ijkDownloadCenter.mListener.downloadBegin(ijkDownloadCenter, ijkDownloadMedia);
                            return;
                        case 300:
                            ijkDownloadCenter.mListener.downloadEnd(ijkDownloadCenter, ijkDownloadMedia);
                            return;
                        case IjkDownloadCenter.MSG_ERROR /*500*/:
                            ijkDownloadCenter.mListener.downloadError(ijkDownloadCenter, ijkDownloadMedia, nativeEvent.error, nativeEvent.reason);
                            return;
                        case 600:
                            ijkDownloadCenter.mListener.downloadFinish(ijkDownloadCenter, ijkDownloadMedia);
                            return;
                        case 900:
                            ijkDownloadCenter.mListener.downloadProgress(ijkDownloadCenter, ijkDownloadMedia);
                            return;
                        default:
                            return;
                    }
                }
            }
        }
    }

    private static class NativeEvent {
        int error;
        IjkDownloadMedia media;
        String reason;

        private NativeEvent() {
        }

        /* synthetic */ NativeEvent(AnonymousClass1 anonymousClass1) {
            this();
        }
    }

    public interface OnDownloadListener {
        void downloadBegin(IjkDownloadCenter ijkDownloadCenter, IjkDownloadMedia ijkDownloadMedia);

        void downloadEnd(IjkDownloadCenter ijkDownloadCenter, IjkDownloadMedia ijkDownloadMedia);

        void downloadError(IjkDownloadCenter ijkDownloadCenter, IjkDownloadMedia ijkDownloadMedia, int i, String str);

        void downloadFinish(IjkDownloadCenter ijkDownloadCenter, IjkDownloadMedia ijkDownloadMedia);

        void downloadProgress(IjkDownloadCenter ijkDownloadCenter, IjkDownloadMedia ijkDownloadMedia);
    }

    private native int download_get_task_prop_long(int i, int i2);

    private native String download_get_task_prop_string(int i, int i2);

    private native void native_download_free();

    private native int native_download_hls_start(String str, String str2);

    private native void native_download_setup(Object obj);

    private native void native_download_stop(int i);

    protected void finalize() throws Throwable {
        try {
            native_download_free();
        } finally {
            super.finalize();
        }
    }

    public IjkDownloadCenter setListener(OnDownloadListener onDownloadListener) {
        this.mListener = onDownloadListener;
        return this;
    }

    public IjkDownloadCenter(IjkLibLoader ijkLibLoader) {
        loadLibrariesOnce(ijkLibLoader);
        Looper mainLooper = Looper.getMainLooper();
        if (mainLooper != null) {
            this.mEventHandler = new EventHandler(this, mainLooper);
        } else {
            this.mEventHandler = null;
        }
        native_download_setup(new WeakReference(this));
    }

    public IjkDownloadCenter() {
        this(sLocalLibLoader);
    }

    public static IjkDownloadCenter getInstance() {
        if (instance == null) {
            instance = new IjkDownloadCenter();
        }
        return instance;
    }

    public static void loadLibrariesOnce(IjkLibLoader ijkLibLoader) {
        synchronized (IjkMediaPlayer.class) {
            if (!mIsLibLoaded) {
                if (ijkLibLoader == null) {
                    ijkLibLoader = sLocalLibLoader;
                }
                ijkLibLoader.loadLibrary("txffmpeg");
                ijkLibLoader.loadLibrary("txsdl");
                ijkLibLoader.loadLibrary("txplayer");
                mIsLibLoaded = true;
            }
        }
    }

    public void stop(int i) {
        native_download_stop(i);
    }

    public int downloadHls(String str, String str2) {
        return native_download_hls_start(str, str2);
    }

    @CalledByNative
    private static void postEventFromNative(Object obj, int i, int i2, int i3, Object obj2) {
        if (obj != null) {
            IjkDownloadCenter ijkDownloadCenter = (IjkDownloadCenter) ((WeakReference) obj).get();
            if (ijkDownloadCenter != null && ijkDownloadCenter.mEventHandler != null) {
                NativeEvent nativeEvent = new NativeEvent();
                nativeEvent.media = ijkDownloadCenter.convertMedia(i2);
                nativeEvent.error = i3;
                if (obj2 != null) {
                    nativeEvent.reason = (String) obj2;
                }
                ijkDownloadCenter.mEventHandler.sendMessage(ijkDownloadCenter.mEventHandler.obtainMessage(i, i2, i3, nativeEvent));
            }
        }
    }

    IjkDownloadMedia convertMedia(int i) {
        String download_get_task_prop_string = download_get_task_prop_string(1001, i);
        if (download_get_task_prop_string == null) {
            return null;
        }
        IjkDownloadMedia ijkDownloadMedia = new IjkDownloadMedia();
        ijkDownloadMedia.size = download_get_task_prop_long(1002, i);
        ijkDownloadMedia.downloadSize = download_get_task_prop_long(1003, i);
        ijkDownloadMedia.url = download_get_task_prop_string;
        ijkDownloadMedia.tid = i;
        return ijkDownloadMedia;
    }
}
