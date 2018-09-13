package com.tencent.ijk.media.player;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.text.TextUtils;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import com.tencent.ijk.media.player.IjkMediaMeta.IjkStreamMeta;
import com.tencent.ijk.media.player.annotations.AccessedByNative;
import com.tencent.ijk.media.player.annotations.CalledByNative;
import com.tencent.ijk.media.player.misc.IAndroidIO;
import com.tencent.ijk.media.player.misc.IMediaDataSource;
import com.tencent.ijk.media.player.misc.IjkTrackInfo;
import com.tencent.ijk.media.player.pragma.DebugLog;
import com.umeng.message.proguard.l;
import com.umeng.socialize.net.dplus.CommonNetImpl;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.io.FileDescriptor;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

public final class IjkMediaPlayer extends AbstractMediaPlayer {
    public static final int FFP_PROPV_DECODER_AVCODEC = 1;
    public static final int FFP_PROPV_DECODER_MEDIACODEC = 2;
    public static final int FFP_PROPV_DECODER_UNKNOWN = 0;
    public static final int FFP_PROPV_DECODER_VIDEOTOOLBOX = 3;
    public static final int FFP_PROP_FLOAT_DROP_FRAME_RATE = 10007;
    public static final int FFP_PROP_FLOAT_PLAYBACK_RATE = 10003;
    public static final int FFP_PROP_INT64_ASYNC_STATISTIC_BUF_BACKWARDS = 20201;
    public static final int FFP_PROP_INT64_ASYNC_STATISTIC_BUF_CAPACITY = 20203;
    public static final int FFP_PROP_INT64_ASYNC_STATISTIC_BUF_FORWARDS = 20202;
    public static final int FFP_PROP_INT64_AUDIO_CACHED_BYTES = 20008;
    public static final int FFP_PROP_INT64_AUDIO_CACHED_DURATION = 20006;
    public static final int FFP_PROP_INT64_AUDIO_CACHED_PACKETS = 20010;
    public static final int FFP_PROP_INT64_AUDIO_DECODER = 20004;
    public static final int FFP_PROP_INT64_BIT_RATE = 20100;
    public static final int FFP_PROP_INT64_CACHE_STATISTIC_COUNT_BYTES = 20208;
    public static final int FFP_PROP_INT64_CACHE_STATISTIC_FILE_FORWARDS = 20206;
    public static final int FFP_PROP_INT64_CACHE_STATISTIC_FILE_POS = 20207;
    public static final int FFP_PROP_INT64_CACHE_STATISTIC_PHYSICAL_POS = 20205;
    public static final int FFP_PROP_INT64_LATEST_SEEK_LOAD_DURATION = 20300;
    public static final int FFP_PROP_INT64_LOGICAL_FILE_SIZE = 20209;
    public static final int FFP_PROP_INT64_SELECTED_AUDIO_STREAM = 20002;
    public static final int FFP_PROP_INT64_SELECTED_TIMEDTEXT_STREAM = 20011;
    public static final int FFP_PROP_INT64_SELECTED_VIDEO_STREAM = 20001;
    public static final int FFP_PROP_INT64_TCP_SPEED = 20200;
    public static final int FFP_PROP_INT64_TRAFFIC_STATISTIC_BYTE_COUNT = 20204;
    public static final int FFP_PROP_INT64_VIDEO_CACHED_BYTES = 20007;
    public static final int FFP_PROP_INT64_VIDEO_CACHED_DURATION = 20005;
    public static final int FFP_PROP_INT64_VIDEO_CACHED_PACKETS = 20009;
    public static final int FFP_PROP_INT64_VIDEO_DECODER = 20003;
    public static final int IJK_LOG_DEBUG = 3;
    public static final int IJK_LOG_DEFAULT = 1;
    public static final int IJK_LOG_ERROR = 6;
    public static final int IJK_LOG_FATAL = 7;
    public static final int IJK_LOG_INFO = 4;
    public static final int IJK_LOG_SILENT = 8;
    public static final int IJK_LOG_UNKNOWN = 0;
    public static final int IJK_LOG_VERBOSE = 2;
    public static final int IJK_LOG_WARN = 5;
    private static final int MEDIA_BUFFERING_UPDATE = 3;
    private static final int MEDIA_ERROR = 100;
    private static final int MEDIA_HEVC_VIDEO_DECODER_ERROR = 211;
    private static final int MEDIA_HLS_KEY_ERROR = 210;
    private static final int MEDIA_INFO = 200;
    private static final int MEDIA_NOP = 0;
    private static final int MEDIA_PLAYBACK_COMPLETE = 2;
    private static final int MEDIA_PREPARED = 1;
    private static final int MEDIA_SEEK_COMPLETE = 4;
    protected static final int MEDIA_SET_VIDEO_SAR = 10001;
    private static final int MEDIA_SET_VIDEO_SIZE = 5;
    private static final int MEDIA_TIMED_TEXT = 99;
    private static final int MEDIA_VIDEO_DECODER_ERROR = 212;
    public static final int OPT_CATEGORY_CODEC = 2;
    public static final int OPT_CATEGORY_FORMAT = 1;
    public static final int OPT_CATEGORY_PLAYER = 4;
    public static final int OPT_CATEGORY_SWS = 3;
    public static final int PROP_FLOAT_VIDEO_DECODE_FRAMES_PER_SECOND = 10001;
    public static final int PROP_FLOAT_VIDEO_OUTPUT_FRAMES_PER_SECOND = 10002;
    public static final int SDL_FCC_RV16 = 909203026;
    public static final int SDL_FCC_RV32 = 842225234;
    public static final int SDL_FCC_YV12 = 842094169;
    private static final String TAG = IjkMediaPlayer.class.getName();
    private static volatile boolean mIsLibLoaded = false;
    private static volatile boolean mIsNativeInitialized = false;
    private static final IjkLibLoader sLocalLibLoader = new IjkLibLoader() {
        public void loadLibrary(String str) throws UnsatisfiedLinkError, SecurityException {
            System.loadLibrary(str);
        }
    };
    private int mBitrateIndex;
    private String mDataSource;
    private EventHandler mEventHandler;
    @AccessedByNative
    private int mListenerContext;
    @AccessedByNative
    private long mNativeAndroidIO;
    @AccessedByNative
    private long mNativeMediaDataSource;
    @AccessedByNative
    private long mNativeMediaPlayer;
    @AccessedByNative
    private int mNativeSurfaceTexture;
    private OnControlMessageListener mOnControlMessageListener;
    private OnMediaCodecSelectListener mOnMediaCodecSelectListener;
    private OnNativeInvokeListener mOnNativeInvokeListener;
    private boolean mScreenOnWhilePlaying;
    private boolean mStayAwake;
    private Surface mSurface;
    private SurfaceHolder mSurfaceHolder;
    private int mVideoHeight;
    private int mVideoSarDen;
    private int mVideoSarNum;
    private int mVideoWidth;
    private WakeLock mWakeLock;

    public interface OnMediaCodecSelectListener {
        String onMediaCodecSelect(IMediaPlayer iMediaPlayer, String str, int i, int i2);
    }

    public static class DefaultMediaCodecSelector implements OnMediaCodecSelectListener {
        public static final DefaultMediaCodecSelector sInstance = new DefaultMediaCodecSelector();

        @TargetApi(16)
        public String onMediaCodecSelect(IMediaPlayer iMediaPlayer, String str, int i, int i2) {
            if (VERSION.SDK_INT < 16) {
                return null;
            }
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            Log.i(IjkMediaPlayer.TAG, String.format(Locale.US, "onSelectCodec: mime=%s, profile=%d, level=%d", new Object[]{str, Integer.valueOf(i), Integer.valueOf(i2)}));
            ArrayList arrayList = new ArrayList();
            int codecCount = MediaCodecList.getCodecCount();
            for (int i3 = 0; i3 < codecCount; i3++) {
                MediaCodecInfo codecInfoAt = MediaCodecList.getCodecInfoAt(i3);
                Log.d(IjkMediaPlayer.TAG, String.format(Locale.US, "  found codec: %s", new Object[]{codecInfoAt.getName()}));
                if (!codecInfoAt.isEncoder()) {
                    String[] supportedTypes = codecInfoAt.getSupportedTypes();
                    if (supportedTypes != null) {
                        for (Object obj : supportedTypes) {
                            if (!TextUtils.isEmpty(obj)) {
                                Log.d(IjkMediaPlayer.TAG, String.format(Locale.US, "    mime: %s", new Object[]{obj}));
                                if (obj.equalsIgnoreCase(str)) {
                                    IjkMediaCodecInfo ijkMediaCodecInfo = IjkMediaCodecInfo.setupCandidate(codecInfoAt, str);
                                    if (ijkMediaCodecInfo != null) {
                                        arrayList.add(ijkMediaCodecInfo);
                                        Log.i(IjkMediaPlayer.TAG, String.format(Locale.US, "candidate codec: %s rank=%d", new Object[]{codecInfoAt.getName(), Integer.valueOf(ijkMediaCodecInfo.mRank)}));
                                        ijkMediaCodecInfo.dumpProfileLevels(str);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (arrayList.isEmpty()) {
                return null;
            }
            IjkMediaCodecInfo ijkMediaCodecInfo2;
            IjkMediaCodecInfo ijkMediaCodecInfo3 = (IjkMediaCodecInfo) arrayList.get(0);
            Iterator it = arrayList.iterator();
            while (true) {
                ijkMediaCodecInfo2 = ijkMediaCodecInfo3;
                if (!it.hasNext()) {
                    break;
                }
                ijkMediaCodecInfo3 = (IjkMediaCodecInfo) it.next();
                if (ijkMediaCodecInfo3.mRank <= ijkMediaCodecInfo2.mRank) {
                    ijkMediaCodecInfo3 = ijkMediaCodecInfo2;
                }
            }
            if (ijkMediaCodecInfo2.mRank < 600) {
                Log.w(IjkMediaPlayer.TAG, String.format(Locale.US, "unaccetable codec: %s", new Object[]{ijkMediaCodecInfo2.mCodecInfo.getName()}));
                return null;
            }
            Log.i(IjkMediaPlayer.TAG, String.format(Locale.US, "selected codec: %s rank=%d", new Object[]{ijkMediaCodecInfo2.mCodecInfo.getName(), Integer.valueOf(ijkMediaCodecInfo2.mRank)}));
            return ijkMediaCodecInfo2.mCodecInfo.getName();
        }
    }

    private static class EventHandler extends Handler {
        private final WeakReference<IjkMediaPlayer> mWeakPlayer;

        public EventHandler(IjkMediaPlayer ijkMediaPlayer, Looper looper) {
            super(looper);
            this.mWeakPlayer = new WeakReference(ijkMediaPlayer);
        }

        public void handleMessage(Message message) {
            IjkMediaPlayer ijkMediaPlayer = (IjkMediaPlayer) this.mWeakPlayer.get();
            if (ijkMediaPlayer == null || ijkMediaPlayer.mNativeMediaPlayer == 0) {
                DebugLog.w(IjkMediaPlayer.TAG, "IjkMediaPlayer went away with unhandled events");
                return;
            }
            switch (message.what) {
                case 0:
                    return;
                case 1:
                    ijkMediaPlayer.notifyOnPrepared();
                    return;
                case 2:
                    ijkMediaPlayer.stayAwake(false);
                    ijkMediaPlayer.notifyOnCompletion();
                    return;
                case 3:
                    long j = (long) message.arg1;
                    if (j < 0) {
                        j = 0;
                    }
                    long duration = ijkMediaPlayer.getDuration();
                    if (duration > 0) {
                        j = (j * 100) / duration;
                    } else {
                        j = 0;
                    }
                    if (j >= 100) {
                        j = 100;
                    }
                    ijkMediaPlayer.notifyOnBufferingUpdate((int) j);
                    return;
                case 4:
                    ijkMediaPlayer.notifyOnSeekComplete();
                    return;
                case 5:
                    ijkMediaPlayer.mVideoWidth = message.arg1;
                    ijkMediaPlayer.mVideoHeight = message.arg2;
                    ijkMediaPlayer.access$200(ijkMediaPlayer.mVideoWidth, ijkMediaPlayer.mVideoHeight, ijkMediaPlayer.mVideoSarNum, ijkMediaPlayer.mVideoSarDen);
                    return;
                case 99:
                    if (message.obj == null) {
                        ijkMediaPlayer.notifyOnTimedText(null);
                        return;
                    } else {
                        ijkMediaPlayer.notifyOnTimedText(new IjkTimedText(new Rect(0, 0, 1, 1), (String) message.obj));
                        return;
                    }
                case 100:
                    DebugLog.e(IjkMediaPlayer.TAG, "Error (" + message.arg1 + MiPushClient.ACCEPT_TIME_SEPARATOR + message.arg2 + l.t);
                    if (!ijkMediaPlayer.notifyOnError(message.arg1, message.arg2)) {
                        ijkMediaPlayer.notifyOnCompletion();
                    }
                    ijkMediaPlayer.stayAwake(false);
                    return;
                case 200:
                    switch (message.arg1) {
                        case 3:
                            DebugLog.i(IjkMediaPlayer.TAG, "Info: MEDIA_INFO_VIDEO_RENDERING_START\n");
                            break;
                    }
                    ijkMediaPlayer.access$400(message.arg1, message.arg2);
                    return;
                case IjkMediaPlayer.MEDIA_HLS_KEY_ERROR /*210*/:
                    ijkMediaPlayer.notifyHLSKeyError();
                    return;
                case IjkMediaPlayer.MEDIA_HEVC_VIDEO_DECODER_ERROR /*211*/:
                    ijkMediaPlayer.notifyHevcVideoDecoderError();
                    return;
                case IjkMediaPlayer.MEDIA_VIDEO_DECODER_ERROR /*212*/:
                    ijkMediaPlayer.notifyVideoDecoderError();
                    return;
                case 10001:
                    ijkMediaPlayer.mVideoSarNum = message.arg1;
                    ijkMediaPlayer.mVideoSarDen = message.arg2;
                    ijkMediaPlayer.access$200(ijkMediaPlayer.mVideoWidth, ijkMediaPlayer.mVideoHeight, ijkMediaPlayer.mVideoSarNum, ijkMediaPlayer.mVideoSarDen);
                    return;
                default:
                    DebugLog.e(IjkMediaPlayer.TAG, "Unknown message type " + message.what);
                    return;
            }
        }
    }

    public interface OnControlMessageListener {
        String onControlResolveSegmentUrl(int i);
    }

    public interface OnNativeInvokeListener {
        public static final String ARG_ERROR = "error";
        public static final String ARG_FAMILIY = "family";
        public static final String ARG_FD = "fd";
        public static final String ARG_HTTP_CODE = "http_code";
        public static final String ARG_IP = "ip";
        public static final String ARG_OFFSET = "offset";
        public static final String ARG_PORT = "port";
        public static final String ARG_RETRY_COUNTER = "retry_counter";
        public static final String ARG_SEGMENT_INDEX = "segment_index";
        public static final String ARG_URL = "url";
        public static final int CTRL_DID_DNS_RESOLVE = 131106;
        public static final int CTRL_DID_TCP_OPEN = 131074;
        public static final int CTRL_WILL_CONCAT_RESOLVE_SEGMENT = 131079;
        public static final int CTRL_WILL_DNS_RESOLVE = 131105;
        public static final int CTRL_WILL_HTTP_OPEN = 131075;
        public static final int CTRL_WILL_LIVE_OPEN = 131077;
        public static final int CTRL_WILL_TCP_OPEN = 131073;
        public static final int EVENT_DID_HTTP_OPEN = 2;
        public static final int EVENT_DID_HTTP_SEEK = 4;
        public static final int EVENT_WILL_HTTP_OPEN = 1;
        public static final int EVENT_WILL_HTTP_SEEK = 3;

        boolean onNativeInvoke(int i, Bundle bundle);
    }

    private native String _getAudioCodecInfo();

    private static native String _getColorFormatName(int i);

    private native int _getLoopCount();

    private native Bundle _getMediaMeta();

    private native float _getPropertyFloat(int i, float f);

    private native long _getPropertyLong(int i, long j);

    private native String _getVideoCodecInfo();

    private native void _injectCacheNode(int i, long j, long j2, long j3, long j4);

    private native void _pause() throws IllegalStateException;

    private native void _release();

    private native void _reset();

    private native void _setAndroidIOCallback(IAndroidIO iAndroidIO) throws IllegalArgumentException, SecurityException, IllegalStateException;

    private native void _setDataSource(IMediaDataSource iMediaDataSource) throws IllegalArgumentException, SecurityException, IllegalStateException;

    private native void _setDataSource(String str, String[] strArr, String[] strArr2) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException;

    private native void _setDataSourceFd(int i) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException;

    private native void _setFrameAtTime(String str, long j, long j2, int i, int i2) throws IllegalArgumentException, IllegalStateException;

    private native void _setLoopCount(int i);

    private native void _setOption(int i, String str, long j);

    private native void _setOption(int i, String str, String str2);

    private native void _setPropertyFloat(int i, float f);

    private native void _setPropertyLong(int i, long j);

    private native void _setStreamSelected(int i, boolean z);

    private native void _setVideoSurface(Surface surface);

    private native void _start() throws IllegalStateException;

    private native void _stop() throws IllegalStateException;

    private native void native_finalize();

    private static native void native_init();

    private native void native_message_loop(Object obj);

    public static native void native_profileBegin(String str);

    public static native void native_profileEnd();

    public static native void native_setLogLevel(int i);

    private native void native_setup(Object obj);

    public native void _prepareAsync() throws IllegalStateException;

    public native void _set_bitrate_index(int i);

    public native int getAudioSessionId();

    public native long getCurrentPosition();

    public native long getDuration();

    public native boolean isPlaying();

    public native void seekTo(long j) throws IllegalStateException;

    public native void setVolume(float f, float f2);

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

    private static void initNativeOnce() {
        synchronized (IjkMediaPlayer.class) {
            if (!mIsNativeInitialized) {
                native_init();
                mIsNativeInitialized = true;
            }
        }
    }

    public IjkMediaPlayer() {
        this(sLocalLibLoader);
    }

    public IjkMediaPlayer(IjkLibLoader ijkLibLoader) {
        this.mWakeLock = null;
        initPlayer(ijkLibLoader);
    }

    private void initPlayer(IjkLibLoader ijkLibLoader) {
        loadLibrariesOnce(ijkLibLoader);
        initNativeOnce();
        Looper myLooper = Looper.myLooper();
        if (myLooper != null) {
            this.mEventHandler = new EventHandler(this, myLooper);
        } else {
            myLooper = Looper.getMainLooper();
            if (myLooper != null) {
                this.mEventHandler = new EventHandler(this, myLooper);
            } else {
                this.mEventHandler = null;
            }
        }
        native_setup(new WeakReference(this));
    }

    public void setDisplay(SurfaceHolder surfaceHolder) {
        Surface surface;
        this.mSurfaceHolder = surfaceHolder;
        if (surfaceHolder != null) {
            surface = surfaceHolder.getSurface();
        } else {
            surface = null;
        }
        _setVideoSurface(surface);
        updateSurfaceScreenOn();
    }

    public void setSurface(Surface surface) {
        if (this.mScreenOnWhilePlaying && surface != null) {
            DebugLog.w(TAG, "setScreenOnWhilePlaying(true) is ineffective for Surface");
        }
        this.mSurfaceHolder = null;
        _setVideoSurface(surface);
        updateSurfaceScreenOn();
        this.mSurface = surface;
    }

    public Surface getSurface() {
        return this.mSurface;
    }

    public void setDataSource(Context context, Uri uri) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        setDataSource(context, uri, null);
    }

    /* JADX WARNING: Removed duplicated region for block: B:33:0x0095  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x009e  */
    @android.annotation.TargetApi(14)
    public void setDataSource(android.content.Context r8, android.net.Uri r9, java.util.Map<java.lang.String, java.lang.String> r10) throws java.io.IOException, java.lang.IllegalArgumentException, java.lang.SecurityException, java.lang.IllegalStateException {
        /*
        r7 = this;
        r0 = r9.getScheme();
        r1 = "file";
        r1 = r1.equals(r0);
        if (r1 == 0) goto L_0x0015;
    L_0x000d:
        r0 = r9.getPath();
        r7.setDataSource(r0);
    L_0x0014:
        return;
    L_0x0015:
        r1 = "content";
        r0 = r1.equals(r0);
        if (r0 == 0) goto L_0x003e;
    L_0x001e:
        r0 = "settings";
        r1 = r9.getAuthority();
        r0 = r0.equals(r1);
        if (r0 == 0) goto L_0x003e;
    L_0x002b:
        r0 = android.media.RingtoneManager.getDefaultType(r9);
        r9 = android.media.RingtoneManager.getActualDefaultRingtoneUri(r8, r0);
        if (r9 != 0) goto L_0x003e;
    L_0x0035:
        r0 = new java.io.FileNotFoundException;
        r1 = "Failed to resolve default ringtone";
        r0.<init>(r1);
        throw r0;
    L_0x003e:
        r0 = 0;
        r1 = r8.getContentResolver();	 Catch:{ SecurityException -> 0x00a6, IOException -> 0x0091, all -> 0x0099 }
        r2 = "r";
        r6 = r1.openAssetFileDescriptor(r9, r2);	 Catch:{ SecurityException -> 0x00a6, IOException -> 0x0091, all -> 0x0099 }
        if (r6 != 0) goto L_0x0052;
    L_0x004c:
        if (r6 == 0) goto L_0x0014;
    L_0x004e:
        r6.close();
        goto L_0x0014;
    L_0x0052:
        r0 = r6.getDeclaredLength();	 Catch:{ SecurityException -> 0x007a, IOException -> 0x00a4, all -> 0x00a2 }
        r2 = 0;
        r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        if (r0 >= 0) goto L_0x0069;
    L_0x005c:
        r0 = r6.getFileDescriptor();	 Catch:{ SecurityException -> 0x007a, IOException -> 0x00a4, all -> 0x00a2 }
        r7.setDataSource(r0);	 Catch:{ SecurityException -> 0x007a, IOException -> 0x00a4, all -> 0x00a2 }
    L_0x0063:
        if (r6 == 0) goto L_0x0014;
    L_0x0065:
        r6.close();
        goto L_0x0014;
    L_0x0069:
        r1 = r6.getFileDescriptor();	 Catch:{ SecurityException -> 0x007a, IOException -> 0x00a4, all -> 0x00a2 }
        r2 = r6.getStartOffset();	 Catch:{ SecurityException -> 0x007a, IOException -> 0x00a4, all -> 0x00a2 }
        r4 = r6.getDeclaredLength();	 Catch:{ SecurityException -> 0x007a, IOException -> 0x00a4, all -> 0x00a2 }
        r0 = r7;
        r0.setDataSource(r1, r2, r4);	 Catch:{ SecurityException -> 0x007a, IOException -> 0x00a4, all -> 0x00a2 }
        goto L_0x0063;
    L_0x007a:
        r0 = move-exception;
        r0 = r6;
    L_0x007c:
        if (r0 == 0) goto L_0x0081;
    L_0x007e:
        r0.close();
    L_0x0081:
        r0 = TAG;
        r1 = "Couldn't open file on client side, trying server side";
        android.util.Log.d(r0, r1);
        r0 = r9.toString();
        r7.setDataSource(r0, r10);
        goto L_0x0014;
    L_0x0091:
        r1 = move-exception;
        r6 = r0;
    L_0x0093:
        if (r6 == 0) goto L_0x0081;
    L_0x0095:
        r6.close();
        goto L_0x0081;
    L_0x0099:
        r1 = move-exception;
        r6 = r0;
        r0 = r1;
    L_0x009c:
        if (r6 == 0) goto L_0x00a1;
    L_0x009e:
        r6.close();
    L_0x00a1:
        throw r0;
    L_0x00a2:
        r0 = move-exception;
        goto L_0x009c;
    L_0x00a4:
        r0 = move-exception;
        goto L_0x0093;
    L_0x00a6:
        r1 = move-exception;
        goto L_0x007c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.ijk.media.player.IjkMediaPlayer.setDataSource(android.content.Context, android.net.Uri, java.util.Map):void");
    }

    public void setDataSource(String str) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        this.mDataSource = str;
        _setDataSource(str, null, null);
    }

    public void setDataSource(String str, Map<String, String> map) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        if (!(map == null || map.isEmpty())) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Entry entry : map.entrySet()) {
                stringBuilder.append((String) entry.getKey());
                stringBuilder.append(":");
                if (!TextUtils.isEmpty((String) entry.getValue())) {
                    stringBuilder.append((String) entry.getValue());
                }
                stringBuilder.append("\r\n");
                setOption(1, "headers", stringBuilder.toString());
                setOption(1, "protocol_whitelist", "async,cache,crypto,file,http,https,ijkhttphook,ijkinject,ijklivehook,ijklongurl,ijksegment,ijktcphook,pipe,rtp,tcp,tls,udp,ijkurlhook,data,ijkhttpcache");
            }
        }
        setDataSource(str);
    }

    @TargetApi(13)
    public void setDataSource(FileDescriptor fileDescriptor) throws IOException, IllegalArgumentException, IllegalStateException {
        if (VERSION.SDK_INT < 12) {
            try {
                Field declaredField = fileDescriptor.getClass().getDeclaredField("descriptor");
                declaredField.setAccessible(true);
                _setDataSourceFd(declaredField.getInt(fileDescriptor));
                return;
            } catch (Throwable e) {
                throw new RuntimeException(e);
            } catch (Throwable e2) {
                throw new RuntimeException(e2);
            }
        }
        ParcelFileDescriptor dup = ParcelFileDescriptor.dup(fileDescriptor);
        try {
            _setDataSourceFd(dup.getFd());
        } finally {
            dup.close();
        }
    }

    private void setDataSource(FileDescriptor fileDescriptor, long j, long j2) throws IOException, IllegalArgumentException, IllegalStateException {
        setDataSource(fileDescriptor);
    }

    public void setDataSource(IMediaDataSource iMediaDataSource) throws IllegalArgumentException, SecurityException, IllegalStateException {
        _setDataSource(iMediaDataSource);
    }

    public void setAndroidIOCallback(IAndroidIO iAndroidIO) throws IllegalArgumentException, SecurityException, IllegalStateException {
        _setAndroidIOCallback(iAndroidIO);
    }

    public void injectCacheNode(int i, long j, long j2, long j3, long j4) {
        _injectCacheNode(i, j, j2, j3, j4);
    }

    public String getDataSource() {
        return this.mDataSource;
    }

    public void prepareAsync() throws IllegalStateException {
        _prepareAsync();
    }

    public void start() throws IllegalStateException {
        stayAwake(true);
        _start();
    }

    public void stop() throws IllegalStateException {
        stayAwake(false);
        _stop();
    }

    public void pause() throws IllegalStateException {
        stayAwake(false);
        _pause();
    }

    @SuppressLint({"Wakelock"})
    public void setWakeMode(Context context, int i) {
        boolean z;
        if (this.mWakeLock != null) {
            boolean z2;
            if (this.mWakeLock.isHeld()) {
                z2 = true;
                this.mWakeLock.release();
            } else {
                z2 = false;
            }
            this.mWakeLock = null;
            z = z2;
        } else {
            z = false;
        }
        this.mWakeLock = ((PowerManager) context.getSystemService("power")).newWakeLock(CommonNetImpl.FLAG_SHARE | i, IjkMediaPlayer.class.getName());
        this.mWakeLock.setReferenceCounted(false);
        if (z) {
            this.mWakeLock.acquire();
        }
    }

    public void setScreenOnWhilePlaying(boolean z) {
        if (this.mScreenOnWhilePlaying != z) {
            if (z && this.mSurfaceHolder == null) {
                DebugLog.w(TAG, "setScreenOnWhilePlaying(true) is ineffective without a SurfaceHolder");
            }
            this.mScreenOnWhilePlaying = z;
            updateSurfaceScreenOn();
        }
    }

    @SuppressLint({"Wakelock"})
    private void stayAwake(boolean z) {
        if (this.mWakeLock != null) {
            if (z && !this.mWakeLock.isHeld()) {
                this.mWakeLock.acquire();
            } else if (!z && this.mWakeLock.isHeld()) {
                this.mWakeLock.release();
            }
        }
        this.mStayAwake = z;
        updateSurfaceScreenOn();
    }

    private void updateSurfaceScreenOn() {
        if (this.mSurfaceHolder != null) {
            SurfaceHolder surfaceHolder = this.mSurfaceHolder;
            boolean z = this.mScreenOnWhilePlaying && this.mStayAwake;
            surfaceHolder.setKeepScreenOn(z);
        }
    }

    public IjkTrackInfo[] getTrackInfo() {
        Bundle mediaMeta = getMediaMeta();
        if (mediaMeta == null) {
            return null;
        }
        IjkMediaMeta parse = IjkMediaMeta.parse(mediaMeta);
        if (parse == null || parse.mStreams == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = parse.mStreams.iterator();
        while (it.hasNext()) {
            IjkStreamMeta ijkStreamMeta = (IjkStreamMeta) it.next();
            IjkTrackInfo ijkTrackInfo = new IjkTrackInfo(ijkStreamMeta);
            if (ijkStreamMeta.mType.equalsIgnoreCase("video")) {
                ijkTrackInfo.setTrackType(1);
            } else if (ijkStreamMeta.mType.equalsIgnoreCase("audio")) {
                ijkTrackInfo.setTrackType(2);
            } else if (ijkStreamMeta.mType.equalsIgnoreCase("timedtext")) {
                ijkTrackInfo.setTrackType(3);
            }
            arrayList.add(ijkTrackInfo);
        }
        return (IjkTrackInfo[]) arrayList.toArray(new IjkTrackInfo[arrayList.size()]);
    }

    public int getSelectedTrack(int i) {
        switch (i) {
            case 1:
                return (int) _getPropertyLong(FFP_PROP_INT64_SELECTED_VIDEO_STREAM, -1);
            case 2:
                return (int) _getPropertyLong(FFP_PROP_INT64_SELECTED_AUDIO_STREAM, -1);
            case 3:
                return (int) _getPropertyLong(FFP_PROP_INT64_SELECTED_TIMEDTEXT_STREAM, -1);
            default:
                return -1;
        }
    }

    public void selectTrack(int i) {
        _setStreamSelected(i, true);
    }

    public void deselectTrack(int i) {
        _setStreamSelected(i, false);
    }

    public int getVideoWidth() {
        return this.mVideoWidth;
    }

    public int getVideoHeight() {
        return this.mVideoHeight;
    }

    public int getVideoSarNum() {
        return this.mVideoSarNum;
    }

    public int getVideoSarDen() {
        return this.mVideoSarDen;
    }

    public void release() {
        stayAwake(false);
        updateSurfaceScreenOn();
        resetListeners();
        new Thread(new Runnable() {
            public void run() {
                IjkMediaPlayer.this._release();
            }
        }).start();
    }

    public void reset() {
        stayAwake(false);
        _reset();
        this.mEventHandler.removeCallbacksAndMessages(null);
        this.mVideoWidth = 0;
        this.mVideoHeight = 0;
    }

    public void setLooping(boolean z) {
        int i = z ? 0 : 1;
        setOption(4, "loop", (long) i);
        _setLoopCount(i);
    }

    public boolean isLooping() {
        if (_getLoopCount() != 1) {
            return true;
        }
        return false;
    }

    public void setSpeed(float f) {
        _setPropertyFloat(FFP_PROP_FLOAT_PLAYBACK_RATE, f);
    }

    public float getSpeed(float f) {
        return _getPropertyFloat(FFP_PROP_FLOAT_PLAYBACK_RATE, 0.0f);
    }

    public int getVideoDecoder() {
        return (int) _getPropertyLong(FFP_PROP_INT64_VIDEO_DECODER, 0);
    }

    public float getVideoOutputFramesPerSecond() {
        return _getPropertyFloat(10002, 0.0f);
    }

    public float getVideoDecodeFramesPerSecond() {
        return _getPropertyFloat(10001, 0.0f);
    }

    public long getVideoCachedDuration() {
        return _getPropertyLong(FFP_PROP_INT64_VIDEO_CACHED_DURATION, 0);
    }

    public long getAudioCachedDuration() {
        return _getPropertyLong(FFP_PROP_INT64_AUDIO_CACHED_DURATION, 0);
    }

    public long getVideoCachedBytes() {
        return _getPropertyLong(FFP_PROP_INT64_VIDEO_CACHED_BYTES, 0);
    }

    public long getAudioCachedBytes() {
        return _getPropertyLong(FFP_PROP_INT64_AUDIO_CACHED_BYTES, 0);
    }

    public long getVideoCachedPackets() {
        return _getPropertyLong(FFP_PROP_INT64_VIDEO_CACHED_PACKETS, 0);
    }

    public long getAudioCachedPackets() {
        return _getPropertyLong(FFP_PROP_INT64_AUDIO_CACHED_PACKETS, 0);
    }

    public long getAsyncStatisticBufBackwards() {
        return _getPropertyLong(FFP_PROP_INT64_ASYNC_STATISTIC_BUF_BACKWARDS, 0);
    }

    public long getAsyncStatisticBufForwards() {
        return _getPropertyLong(FFP_PROP_INT64_ASYNC_STATISTIC_BUF_FORWARDS, 0);
    }

    public long getAsyncStatisticBufCapacity() {
        return _getPropertyLong(FFP_PROP_INT64_ASYNC_STATISTIC_BUF_CAPACITY, 0);
    }

    public long getTrafficStatisticByteCount() {
        return _getPropertyLong(FFP_PROP_INT64_TRAFFIC_STATISTIC_BYTE_COUNT, 0);
    }

    public long getCacheStatisticPhysicalPos() {
        return _getPropertyLong(FFP_PROP_INT64_CACHE_STATISTIC_PHYSICAL_POS, 0);
    }

    public long getCacheStatisticFileForwards() {
        return _getPropertyLong(FFP_PROP_INT64_CACHE_STATISTIC_FILE_FORWARDS, 0);
    }

    public long getCacheStatisticFilePos() {
        return _getPropertyLong(FFP_PROP_INT64_CACHE_STATISTIC_FILE_POS, 0);
    }

    public long getCacheStatisticCountBytes() {
        return _getPropertyLong(FFP_PROP_INT64_CACHE_STATISTIC_COUNT_BYTES, 0);
    }

    public long getFileSize() {
        return _getPropertyLong(FFP_PROP_INT64_LOGICAL_FILE_SIZE, 0);
    }

    public long getBitRate() {
        return _getPropertyLong(FFP_PROP_INT64_BIT_RATE, 0);
    }

    public long getTcpSpeed() {
        return _getPropertyLong(FFP_PROP_INT64_TCP_SPEED, 0);
    }

    public long getSeekLoadDuration() {
        return _getPropertyLong(FFP_PROP_INT64_LATEST_SEEK_LOAD_DURATION, 0);
    }

    public float getDropFrameRate() {
        return _getPropertyFloat(FFP_PROP_FLOAT_DROP_FRAME_RATE, 0.0f);
    }

    public void setRate(float f) {
        setSpeed(f);
    }

    public MediaInfo getMediaInfo() {
        String[] split;
        MediaInfo mediaInfo = new MediaInfo();
        mediaInfo.mMediaPlayerName = "ijkplayer";
        Object _getVideoCodecInfo = _getVideoCodecInfo();
        if (!TextUtils.isEmpty(_getVideoCodecInfo)) {
            split = _getVideoCodecInfo.split(MiPushClient.ACCEPT_TIME_SEPARATOR);
            if (split.length >= 2) {
                mediaInfo.mVideoDecoder = split[0];
                mediaInfo.mVideoDecoderImpl = split[1];
            } else if (split.length >= 1) {
                mediaInfo.mVideoDecoder = split[0];
                mediaInfo.mVideoDecoderImpl = "";
            }
        }
        _getVideoCodecInfo = _getAudioCodecInfo();
        if (!TextUtils.isEmpty(_getVideoCodecInfo)) {
            split = _getVideoCodecInfo.split(MiPushClient.ACCEPT_TIME_SEPARATOR);
            if (split.length >= 2) {
                mediaInfo.mAudioDecoder = split[0];
                mediaInfo.mAudioDecoderImpl = split[1];
            } else if (split.length >= 1) {
                mediaInfo.mAudioDecoder = split[0];
                mediaInfo.mAudioDecoderImpl = "";
            }
        }
        try {
            mediaInfo.mMeta = IjkMediaMeta.parse(_getMediaMeta());
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return mediaInfo;
    }

    public void setLogEnabled(boolean z) {
    }

    public boolean isPlayable() {
        return true;
    }

    public void setOption(int i, String str, String str2) {
        _setOption(i, str, str2);
    }

    public void setOption(int i, String str, long j) {
        _setOption(i, str, j);
    }

    public Bundle getMediaMeta() {
        return _getMediaMeta();
    }

    public static String getColorFormatName(int i) {
        return _getColorFormatName(i);
    }

    public void setAudioStreamType(int i) {
    }

    public void setKeepInBackground(boolean z) {
    }

    protected void finalize() throws Throwable {
        super.finalize();
        native_finalize();
    }

    @CalledByNative
    private static void postEventFromNative(Object obj, int i, int i2, int i3, Object obj2) {
        if (obj != null) {
            IjkMediaPlayer ijkMediaPlayer = (IjkMediaPlayer) ((WeakReference) obj).get();
            if (ijkMediaPlayer != null) {
                if (i == 200 && i2 == 2) {
                    ijkMediaPlayer.start();
                }
                if (ijkMediaPlayer.mEventHandler != null) {
                    ijkMediaPlayer.mEventHandler.sendMessage(ijkMediaPlayer.mEventHandler.obtainMessage(i, i2, i3, obj2));
                }
            }
        }
    }

    public void setOnControlMessageListener(OnControlMessageListener onControlMessageListener) {
        this.mOnControlMessageListener = onControlMessageListener;
    }

    public void setOnNativeInvokeListener(OnNativeInvokeListener onNativeInvokeListener) {
        this.mOnNativeInvokeListener = onNativeInvokeListener;
    }

    @CalledByNative
    private static boolean onNativeInvoke(Object obj, int i, Bundle bundle) {
        DebugLog.ifmt(TAG, "onNativeInvoke %d", Integer.valueOf(i));
        if (obj == null || !(obj instanceof WeakReference)) {
            throw new IllegalStateException("<null weakThiz>.onNativeInvoke()");
        }
        IjkMediaPlayer ijkMediaPlayer = (IjkMediaPlayer) ((WeakReference) obj).get();
        if (ijkMediaPlayer == null) {
            throw new IllegalStateException("<null weakPlayer>.onNativeInvoke()");
        }
        OnNativeInvokeListener onNativeInvokeListener = ijkMediaPlayer.mOnNativeInvokeListener;
        if (onNativeInvokeListener != null && onNativeInvokeListener.onNativeInvoke(i, bundle)) {
            return true;
        }
        switch (i) {
            case OnNativeInvokeListener.CTRL_WILL_CONCAT_RESOLVE_SEGMENT /*131079*/:
                OnControlMessageListener onControlMessageListener = ijkMediaPlayer.mOnControlMessageListener;
                if (onControlMessageListener == null) {
                    return false;
                }
                int i2 = bundle.getInt(OnNativeInvokeListener.ARG_SEGMENT_INDEX, -1);
                if (i2 < 0) {
                    throw new InvalidParameterException("onNativeInvoke(invalid segment index)");
                }
                String onControlResolveSegmentUrl = onControlMessageListener.onControlResolveSegmentUrl(i2);
                if (onControlResolveSegmentUrl == null) {
                    throw new RuntimeException(new IOException("onNativeInvoke() = <NULL newUrl>"));
                }
                bundle.putString("url", onControlResolveSegmentUrl);
                return true;
            default:
                return false;
        }
    }

    public void setOnMediaCodecSelectListener(OnMediaCodecSelectListener onMediaCodecSelectListener) {
        this.mOnMediaCodecSelectListener = onMediaCodecSelectListener;
    }

    public void resetListeners() {
        super.resetListeners();
        this.mOnMediaCodecSelectListener = null;
    }

    @CalledByNative
    private static String onSelectCodec(Object obj, String str, int i, int i2) {
        if (obj == null || !(obj instanceof WeakReference)) {
            return null;
        }
        IjkMediaPlayer ijkMediaPlayer = (IjkMediaPlayer) ((WeakReference) obj).get();
        if (ijkMediaPlayer == null) {
            return null;
        }
        OnMediaCodecSelectListener onMediaCodecSelectListener = ijkMediaPlayer.mOnMediaCodecSelectListener;
        if (onMediaCodecSelectListener == null) {
            onMediaCodecSelectListener = DefaultMediaCodecSelector.sInstance;
        }
        return onMediaCodecSelectListener.onMediaCodecSelect(ijkMediaPlayer, str, i, i2);
    }

    public int getBitrateIndex() {
        return this.mBitrateIndex;
    }

    public void setBitrateIndex(int i) {
        this.mBitrateIndex = i;
        _set_bitrate_index(i);
    }

    public ArrayList<IjkBitrateItem> getSupportedBitrates() {
        return getMediaInfo().mMeta.mBitrateItems;
    }
}
