package com.tencent.liteav.txcvodplayer;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.feng.car.utils.CameraUtil;
import com.feng.car.utils.HttpConstant;
import com.feng.car.video.shortvideo.FileUtils;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.stub.StubApp;
import com.taobao.accs.flowcontrol.FlowControl;
import com.tencent.ijk.media.exo.IjkExoMediaPlayer;
import com.tencent.ijk.media.player.AndroidMediaPlayer;
import com.tencent.ijk.media.player.IMediaPlayer;
import com.tencent.ijk.media.player.IMediaPlayer.OnBufferingUpdateListener;
import com.tencent.ijk.media.player.IMediaPlayer.OnCompletionListener;
import com.tencent.ijk.media.player.IMediaPlayer.OnErrorListener;
import com.tencent.ijk.media.player.IMediaPlayer.OnHLSKeyErrorListener;
import com.tencent.ijk.media.player.IMediaPlayer.OnHevcVideoDecoderErrorListener;
import com.tencent.ijk.media.player.IMediaPlayer.OnInfoListener;
import com.tencent.ijk.media.player.IMediaPlayer.OnPreparedListener;
import com.tencent.ijk.media.player.IMediaPlayer.OnSeekCompleteListener;
import com.tencent.ijk.media.player.IMediaPlayer.OnTimedTextListener;
import com.tencent.ijk.media.player.IMediaPlayer.OnVideoDecoderErrorListener;
import com.tencent.ijk.media.player.IMediaPlayer.OnVideoSizeChangedListener;
import com.tencent.ijk.media.player.IjkBitrateItem;
import com.tencent.ijk.media.player.IjkLibLoader;
import com.tencent.ijk.media.player.IjkMediaMeta;
import com.tencent.ijk.media.player.IjkMediaPlayer;
import com.tencent.ijk.media.player.IjkMediaPlayer.OnNativeInvokeListener;
import com.tencent.ijk.media.player.IjkTimedText;
import com.tencent.ijk.media.player.MediaInfo;
import com.tencent.ijk.media.player.TextureMediaPlayer;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.txcvodplayer.a.b;
import com.tencent.rtmp.TXLiveConstants;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.ref.WeakReference;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

/* compiled from: TXCVodVideoView */
public class e extends FrameLayout {
    private String A;
    private float B = 1.0f;
    private com.tencent.liteav.txcvodplayer.a.a C;
    private b D = b.a();
    private int E;
    private long F;
    private int G;
    private int H;
    private long I;
    private boolean J = false;
    private int K = -1;
    private float L = 1.0f;
    private float M = 1.0f;
    private boolean N = false;
    private int O;
    private boolean P;
    private OnCompletionListener Q = new OnCompletionListener() {
        public void onCompletion(IMediaPlayer iMediaPlayer) {
            if (e.this.h.toString().endsWith(".m3u8") && e.this.c == 0 && Math.abs(e.this.getDuration() - e.this.getCurrentPosition()) > CameraUtil.MAX_DURATION_RECORD) {
                Log.w(e.this.g, "hls not end, try to continue");
                e.this.T.onError(iMediaPlayer, 1, 0);
            } else if (e.this.c != 1 || e.this.j != -1) {
                e.this.i = 5;
                e.this.j = 5;
                e.this.a((int) TXLiveConstants.PUSH_WARNING_SERVER_DISCONNECT, "播放完成", "play end");
            }
        }
    };
    private OnInfoListener R = new OnInfoListener() {
        public boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i2) {
            switch (i) {
                case 3:
                    TXCLog.d(e.this.g, "MEDIA_INFO_VIDEO_RENDERING_START:");
                    if (!e.this.P) {
                        e.this.a(3008, "点播显示首帧画面", "render start");
                        if (e.this.c == 1) {
                            new Thread(new Runnable() {
                                public void run() {
                                    try {
                                        e.this.A = InetAddress.getByName(e.this.h.getHost()).getHostAddress();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                        }
                    }
                    e.this.setRate(e.this.B);
                    if (e.this.c == 1) {
                        e.this.a(3016, "缓冲结束", "loading end");
                        if (e.this.j == 3 && e.this.P) {
                            e.this.a(3001, "播放开始", "playing");
                        }
                    }
                    e.this.P = true;
                    break;
                case 700:
                    TXCLog.d(e.this.g, "MEDIA_INFO_VIDEO_TRACK_LAGGING:");
                    break;
                case IMediaPlayer.MEDIA_INFO_BUFFERING_START /*701*/:
                    TXCLog.d(e.this.g, "MEDIA_INFO_BUFFERING_START:");
                    e.this.a(3003, "缓冲开始", "loading start");
                    break;
                case IMediaPlayer.MEDIA_INFO_BUFFERING_END /*702*/:
                    TXCLog.d(e.this.g, "MEDIA_INFO_BUFFERING_END: eof " + i2);
                    if (i2 == 0 || e.this.h == null || e.this.h.getPath() == null || !e.this.h.getPath().endsWith(IjkMediaMeta.IJKM_KEY_M3U8)) {
                        e.this.a(3016, "缓冲结束", "loading end");
                        if (e.this.j == 3) {
                            e.this.a(3001, "播放开始", "playing");
                            break;
                        }
                    }
                    break;
                case IMediaPlayer.MEDIA_INFO_NETWORK_BANDWIDTH /*703*/:
                    TXCLog.d(e.this.g, "MEDIA_INFO_NETWORK_BANDWIDTH: " + i2);
                    break;
                case 800:
                    TXCLog.d(e.this.g, "MEDIA_INFO_BAD_INTERLEAVING:");
                    break;
                case IMediaPlayer.MEDIA_INFO_NOT_SEEKABLE /*801*/:
                    TXCLog.d(e.this.g, "MEDIA_INFO_NOT_SEEKABLE:");
                    break;
                case IMediaPlayer.MEDIA_INFO_METADATA_UPDATE /*802*/:
                    TXCLog.d(e.this.g, "MEDIA_INFO_METADATA_UPDATE:");
                    break;
                case IMediaPlayer.MEDIA_INFO_UNSUPPORTED_SUBTITLE /*901*/:
                    TXCLog.d(e.this.g, "MEDIA_INFO_UNSUPPORTED_SUBTITLE:");
                    break;
                case IMediaPlayer.MEDIA_INFO_SUBTITLE_TIMED_OUT /*902*/:
                    TXCLog.d(e.this.g, "MEDIA_INFO_SUBTITLE_TIMED_OUT:");
                    break;
                case 10001:
                    TXCLog.d(e.this.g, "MEDIA_INFO_VIDEO_ROTATION_CHANGED: " + i2);
                    e.this.r = i2;
                    if (e.this.a && e.this.r > 0) {
                        e.this.q = e.this.r;
                        if (e.this.x != null) {
                            e.this.x.setVideoRotation(e.this.q);
                        }
                    }
                    e.this.a(3009, "视频角度 " + e.this.r, "rotation " + e.this.r);
                    break;
                case 10002:
                    TXCLog.d(e.this.g, "MEDIA_INFO_AUDIO_RENDERING_START:");
                    break;
                case IMediaPlayer.MEDIA_INFO_FIRST_VIDEO_PACKET /*10011*/:
                    e.this.a(3013, "收到视频数据", "first video packet");
                    break;
            }
            return true;
        }
    };
    private int S;
    private OnErrorListener T = new OnErrorListener() {
        public boolean onError(IMediaPlayer iMediaPlayer, int i, int i2) {
            TXCLog.e(e.this.g, "onError: " + i + MiPushClient.ACCEPT_TIME_SEPARATOR + i2);
            e.this.i = -1;
            e.this.j = -1;
            if (i == -1004 && i2 == -3003) {
                e.this.a(i2, "文件不存在", "file not exist");
                e.this.c();
            } else {
                if (e.this.I != ((long) e.this.getCurrentPosition())) {
                    e.this.S = 0;
                }
                e.this.I = (long) e.this.getCurrentPosition();
                if (((float) e.this.S = e.this.S + 1) >= e.this.w.a) {
                    e.this.a(-3002, "网络断开，播放错误", "disconnect");
                    e.this.c();
                } else if (e.this.ag != null) {
                    e.this.ag.sendEmptyMessageDelayed(102, (long) (e.this.w.b * 1000.0f));
                }
            }
            return true;
        }
    };
    private OnHevcVideoDecoderErrorListener U = new OnHevcVideoDecoderErrorListener() {
        public void onHevcVideoDecoderError(IMediaPlayer iMediaPlayer) {
            Log.d(e.this.g, "onHevcVideoDecoderError");
            e.this.a(-3005, "点播H265解码失败", "hevc decode fail");
        }
    };
    private OnVideoDecoderErrorListener V = new OnVideoDecoderErrorListener() {
        public void onVideoDecoderError(IMediaPlayer iMediaPlayer) {
            Log.d(e.this.g, "onVideoDecoderError");
            if (e.this.i != 4) {
                e.this.a(3010, "点播解码失败", "decode fail");
            }
            if (!e.this.P && e.this.w.d) {
                e.this.w.d = false;
                e.this.g();
            }
        }
    };
    private OnBufferingUpdateListener W = new OnBufferingUpdateListener() {
        public void onBufferingUpdate(IMediaPlayer iMediaPlayer, int i) {
            e.this.s = i;
        }
    };
    protected boolean a = true;
    private OnSeekCompleteListener aa = new OnSeekCompleteListener() {
        public void onSeekComplete(IMediaPlayer iMediaPlayer) {
            TXCLog.v(e.this.g, "seek complete");
            e.this.t = 0;
            e.this.J = false;
            if (e.this.K >= 0) {
                e.this.a(e.this.K);
            }
        }
    };
    private OnTimedTextListener ab = new OnTimedTextListener() {
        public void onTimedText(IMediaPlayer iMediaPlayer, IjkTimedText ijkTimedText) {
        }
    };
    private OnNativeInvokeListener ac = new OnNativeInvokeListener() {
        public boolean onNativeInvoke(int i, Bundle bundle) {
            switch (i) {
                case OnNativeInvokeListener.CTRL_DID_TCP_OPEN /*131074*/:
                    e.this.A = bundle.getString("ip");
                    e.this.a(3012, "CTRL_DID_TCP_OPEN", "tcp open");
                    return true;
                case OnNativeInvokeListener.CTRL_DID_DNS_RESOLVE /*131106*/:
                    e.this.a(3014, "PLAYER_EVENT_DNS_RESOLVED", "dns resolved");
                    return true;
                default:
                    return false;
            }
        }
    };
    private OnHLSKeyErrorListener ad = new OnHLSKeyErrorListener() {
        public void onHLSKeyError(IMediaPlayer iMediaPlayer) {
            Log.e(e.this.g, "onHLSKeyError");
            e.this.a(-3004, "HLS解密key获取失败", "hls key error");
            if (e.this.l != null) {
                e.this.l.stop();
                e.this.l.release();
                e.this.l = null;
            }
            e.this.i = -1;
            e.this.j = -1;
        }
    };
    private int ae = 0;
    private f af;
    private Handler ag;
    private boolean ah = false;
    protected boolean b = true;
    protected int c = 0;
    OnVideoSizeChangedListener d = new OnVideoSizeChangedListener() {
        public void onVideoSizeChanged(IMediaPlayer iMediaPlayer, int i, int i2, int i3, int i4) {
            Object obj = ((e.this.n == i2 || Math.abs(e.this.n - i2) <= 16) && (e.this.m == i || Math.abs(e.this.m - i) <= 16)) ? null : 1;
            e.this.m = iMediaPlayer.getVideoWidth();
            e.this.n = iMediaPlayer.getVideoHeight();
            e.this.y = iMediaPlayer.getVideoSarNum();
            e.this.z = iMediaPlayer.getVideoSarDen();
            if (!(e.this.m == 0 || e.this.n == 0)) {
                if (e.this.x != null) {
                    e.this.x.setVideoSize(e.this.m, e.this.n);
                    e.this.x.setVideoSampleAspectRatio(e.this.y, e.this.z);
                }
                e.this.requestLayout();
            }
            if (obj != null) {
                Message message = new Message();
                message.what = 101;
                message.arg1 = 3005;
                Bundle bundle = new Bundle();
                bundle.putString(HttpConstant.DESCRIPTION, "分辨率改变:" + e.this.m + "*" + e.this.n);
                bundle.putInt("EVT_PARAM1", e.this.m);
                bundle.putInt("EVT_PARAM2", e.this.n);
                message.setData(bundle);
                if (e.this.ag != null) {
                    e.this.ag.sendMessage(message);
                }
            }
        }
    };
    OnPreparedListener e = new OnPreparedListener() {
        public void onPrepared(IMediaPlayer iMediaPlayer) {
            if (e.this.i == 1) {
                e.this.a(3000, "点播准备完成", "prepared");
                if (!e.this.b) {
                    e.this.j = 4;
                }
                e.this.i = 2;
            }
            if (e.this.i == -1) {
                e.this.i = 3;
                e.this.j = 3;
            }
            if (e.this.ag != null) {
                e.this.ag.sendEmptyMessage(100);
                e.this.ag.sendEmptyMessage(103);
            }
            e.this.m = iMediaPlayer.getVideoWidth();
            e.this.n = iMediaPlayer.getVideoHeight();
            int h = e.this.t;
            if (h != 0) {
                e.this.a(h);
                e.this.b = true;
            }
            if (e.this.m == 0 || e.this.n == 0) {
                if (e.this.j == 3) {
                    e.this.b();
                }
            } else if (e.this.x != null) {
                e.this.x.setVideoSize(e.this.m, e.this.n);
                e.this.x.setVideoSampleAspectRatio(e.this.y, e.this.z);
                if ((!e.this.x.shouldWaitForResize() || (e.this.o == e.this.m && e.this.p == e.this.n)) && e.this.j == 3) {
                    e.this.b();
                }
            }
        }
    };
    com.tencent.liteav.txcvodplayer.a.a f = new com.tencent.liteav.txcvodplayer.a.a() {
        public void a(@NonNull a.b bVar, int i, int i2, int i3) {
            Object obj = null;
            if (bVar.a() != e.this.x) {
                TXCLog.e(e.this.g, "onSurfaceChanged: unmatched render callback\n");
                return;
            }
            TXCLog.d(e.this.g, "onSurfaceChanged");
            e.this.o = i2;
            e.this.p = i3;
            Object obj2 = e.this.j == 3 ? 1 : null;
            if (!e.this.x.shouldWaitForResize() || (e.this.m == i2 && e.this.n == i3)) {
                obj = 1;
            }
            if (e.this.l != null && obj2 != null && obj != null) {
                if (e.this.t != 0) {
                    e.this.a(e.this.t);
                }
                if (e.this.j == 3) {
                    e.this.b();
                }
            }
        }

        public void a(@NonNull a.b bVar, int i, int i2) {
            if (bVar.a() != e.this.x) {
                TXCLog.e(e.this.g, "onSurfaceCreated: unmatched render callback\n");
                return;
            }
            TXCLog.d(e.this.g, "onSurfaceCreated");
            e.this.k = bVar;
            if (e.this.l != null) {
                e.this.a(e.this.l, bVar);
            } else {
                e.this.f();
            }
        }

        public void a(@NonNull a.b bVar) {
            if (bVar.a() != e.this.x) {
                TXCLog.e(e.this.g, "onSurfaceDestroyed: unmatched render callback\n");
                return;
            }
            TXCLog.d(e.this.g, "onSurfaceDestroyed");
            e.this.k = null;
            if (e.this.l != null) {
                e.this.l.setSurface(null);
            }
            e.this.a();
        }
    };
    private String g = "TXCVodVideoView";
    private Uri h;
    private int i = 0;
    private int j = 0;
    private a.b k = null;
    private IMediaPlayer l = null;
    private int m;
    private int n;
    private int o;
    private int p;
    private int q;
    private int r;
    private int s;
    private int t;
    private int u;
    private Context v;
    private d w;
    private a x;
    private int y;
    private int z;

    /* compiled from: TXCVodVideoView */
    private static class a extends Handler {
        private final WeakReference<e> a;
        private final int b = 500;

        public a(e eVar, Looper looper) {
            super(looper);
            this.a = new WeakReference(eVar);
        }

        public void handleMessage(Message message) {
            e eVar = (e) this.a.get();
            if (eVar != null && eVar.af != null) {
                long j;
                long j2;
                long j3;
                switch (message.what) {
                    case 100:
                        j = 0;
                        j2 = 0;
                        j3 = 0;
                        IMediaPlayer unwrappedMediaPlayer = eVar.getUnwrappedMediaPlayer();
                        if (unwrappedMediaPlayer != null) {
                            float f;
                            if (unwrappedMediaPlayer instanceof IjkMediaPlayer) {
                                IjkMediaPlayer ijkMediaPlayer = (IjkMediaPlayer) unwrappedMediaPlayer;
                                float videoOutputFramesPerSecond = ijkMediaPlayer.getVideoOutputFramesPerSecond();
                                j = ijkMediaPlayer.getVideoCachedBytes() + ijkMediaPlayer.getAudioCachedBytes();
                                j2 = ijkMediaPlayer.getBitRate();
                                j3 = ijkMediaPlayer.getTcpSpeed();
                                f = videoOutputFramesPerSecond;
                            } else if (unwrappedMediaPlayer instanceof IjkExoMediaPlayer) {
                                IjkExoMediaPlayer ijkExoMediaPlayer = (IjkExoMediaPlayer) unwrappedMediaPlayer;
                                DecoderCounters videoDecoderCounters = ijkExoMediaPlayer.getVideoDecoderCounters();
                                if (videoDecoderCounters != null) {
                                    j2 = System.currentTimeMillis() - eVar.F;
                                    int B = videoDecoderCounters.renderedOutputBufferCount - eVar.E;
                                    eVar.F = System.currentTimeMillis();
                                    eVar.E = videoDecoderCounters.renderedOutputBufferCount;
                                    if (j2 < 3000 && j2 > 0 && B < 120 && B > 0) {
                                        eVar.G = (int) Math.ceil(((double) B) * (1000.0d / ((double) j2)));
                                    }
                                }
                                j2 = (long) ijkExoMediaPlayer.getObservedBitrate();
                                j3 = j2 / 8;
                                f = (float) eVar.G;
                            } else {
                                f = 0.0f;
                            }
                            Bundle bundle = new Bundle();
                            bundle.putFloat("fps", f);
                            bundle.putLong("cachedBytes", j);
                            bundle.putLong("bitRate", j2);
                            bundle.putLong("tcpSpeed", j3);
                            eVar.af.a(bundle);
                            removeMessages(100);
                            sendEmptyMessageDelayed(100, 500);
                            return;
                        }
                        return;
                    case 101:
                        int i = message.arg1;
                        switch (i) {
                        }
                        eVar.af.a(i, message.getData());
                        return;
                    case 102:
                        eVar.g();
                        eVar.a(3006, "点播网络重连", "reconnect");
                        return;
                    case 103:
                        j3 = (long) eVar.getCurrentPosition();
                        Bundle bundle2 = new Bundle();
                        j2 = (long) eVar.getBufferDuration();
                        j = (long) eVar.getDuration();
                        bundle2.putInt(TXLiveConstants.EVT_PLAY_PROGRESS, (int) (j3 / 1000));
                        bundle2.putInt(TXLiveConstants.EVT_PLAY_DURATION, (int) (j / 1000));
                        bundle2.putInt(TXLiveConstants.NET_STATUS_PLAYABLE_DURATION, (int) (j2 / 1000));
                        bundle2.putInt(TXLiveConstants.EVT_PLAY_PROGRESS_MS, (int) j3);
                        bundle2.putInt(TXLiveConstants.EVT_PLAY_DURATION_MS, (int) j);
                        bundle2.putInt(TXLiveConstants.EVT_PLAYABLE_DURATION_MS, (int) j2);
                        eVar.af.a(3007, bundle2);
                        if (eVar.l != null) {
                            removeMessages(103);
                            if (eVar.w.l <= 0) {
                                eVar.w.l = 500;
                            }
                            sendEmptyMessageDelayed(103, (long) eVar.w.l);
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        }
    }

    public int getMetaRotationDegree() {
        return this.r;
    }

    public e(Context context) {
        super(context);
        a(context);
    }

    private void a(Context context) {
        this.v = StubApp.getOrigApplicationContext(context.getApplicationContext());
        this.w = new d();
        i();
        this.m = 0;
        this.n = 0;
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();
        this.i = 0;
        this.j = 0;
        Looper mainLooper = Looper.getMainLooper();
        if (mainLooper != null) {
            this.ag = new a(this, mainLooper);
        } else {
            this.ag = null;
        }
    }

    public void setRenderView(a aVar) {
        View view;
        TXCLog.d(this.g, "setRenderView " + aVar);
        if (this.x != null) {
            if (this.l != null) {
                this.l.setDisplay(null);
            }
            view = this.x.getView();
            this.x.removeRenderCallback(this.f);
            this.x = null;
            if (view.getParent() == this) {
                removeView(view);
            }
        }
        if (aVar != null) {
            this.x = aVar;
            aVar.setAspectRatio(this.ae);
            if (this.m > 0 && this.n > 0) {
                aVar.setVideoSize(this.m, this.n);
            }
            if (this.y > 0 && this.z > 0) {
                aVar.setVideoSampleAspectRatio(this.y, this.z);
            }
            view = this.x.getView();
            view.setLayoutParams(new LayoutParams(-2, -2, 17));
            if (view.getParent() == null) {
                addView(view);
            }
            this.x.addRenderCallback(this.f);
            this.x.setVideoRotation(this.q);
        }
    }

    public void setRender(int i) {
        switch (i) {
            case 0:
                setRenderView(null);
                return;
            case 1:
                setRenderView(new c(this.v));
                return;
            case 2:
                a textureRenderView = new TextureRenderView(this.v);
                if (this.l != null) {
                    textureRenderView.getSurfaceHolder().a(this.l);
                    textureRenderView.setVideoSize(this.l.getVideoWidth(), this.l.getVideoHeight());
                    textureRenderView.setVideoSampleAspectRatio(this.l.getVideoSarNum(), this.l.getVideoSarDen());
                    textureRenderView.setAspectRatio(this.ae);
                }
                setRenderView(textureRenderView);
                return;
            default:
                TXCLog.e(this.g, String.format(Locale.getDefault(), "invalid render %d\n", new Object[]{Integer.valueOf(i)}));
                return;
        }
    }

    public void setTextureRenderView(TextureRenderView textureRenderView) {
        TXCLog.d(this.g, "setTextureRenderView " + textureRenderView);
        if (this.l != null) {
            textureRenderView.getSurfaceHolder().a(this.l);
            textureRenderView.setVideoSize(this.l.getVideoWidth(), this.l.getVideoHeight());
            textureRenderView.setVideoSampleAspectRatio(this.l.getVideoSarNum(), this.l.getVideoSarDen());
            textureRenderView.setAspectRatio(this.ae);
        }
        setRenderView(textureRenderView);
    }

    public void setRenderSurface(final Surface surface) {
        this.k = new a.b() {
            public void a(IMediaPlayer iMediaPlayer) {
                iMediaPlayer.setSurface(surface);
            }

            @NonNull
            public a a() {
                return e.this.x;
            }
        };
        if (this.l != null) {
            a(this.l, this.k);
        }
    }

    public void setVideoPath(String str) {
        setVideoURI(Uri.parse(str));
    }

    public void setVideoURI(Uri uri) {
        this.h = uri;
        this.t = 0;
        this.u = 0;
        this.H = 0;
        this.S = 0;
        this.A = null;
        TXCLog.d(this.g, "setVideoURI " + uri);
        f();
        requestLayout();
        invalidate();
    }

    @TargetApi(23)
    private boolean f() {
        long j = 0;
        TXCLog.d(this.g, "openVideo");
        if (this.h == null) {
            return false;
        }
        if (this.k == null && this.b) {
            return false;
        }
        a(false);
        ((AudioManager) this.v.getSystemService("audio")).requestAudioFocus(null, 3, 1);
        try {
            String uri = this.h.toString();
            if (!uri.startsWith("/") || new File(uri).exists()) {
                IMediaPlayer iMediaPlayer;
                String str;
                String str2;
                Object iMediaPlayer2;
                switch (this.c) {
                    case 1:
                        IjkExoMediaPlayer ijkExoMediaPlayer = new IjkExoMediaPlayer(this.v);
                        TXCLog.i(this.g, "exo media player " + ijkExoMediaPlayer);
                        str2 = uri;
                        iMediaPlayer2 = ijkExoMediaPlayer;
                        str = str2;
                        break;
                    case 2:
                        AndroidMediaPlayer androidMediaPlayer = new AndroidMediaPlayer();
                        TXCLog.i(this.g, "android media player " + androidMediaPlayer);
                        str2 = uri;
                        iMediaPlayer2 = androidMediaPlayer;
                        str = str2;
                        break;
                    default:
                        if (this.h != null) {
                            long j2;
                            IjkMediaPlayer ijkMediaPlayer = new IjkMediaPlayer(new IjkLibLoader() {
                                public void loadLibrary(String str) throws UnsatisfiedLinkError, SecurityException {
                                    com.tencent.liteav.basic.util.a.a(str);
                                }
                            });
                            IjkMediaPlayer.native_setLogLevel(3);
                            ijkMediaPlayer.setOnNativeInvokeListener(this.ac);
                            if (this.w.d) {
                                ijkMediaPlayer.setOption(4, "mediacodec", 1);
                                ijkMediaPlayer.setOption(4, "mediacodec-hevc", 1);
                            } else {
                                ijkMediaPlayer.setOption(4, "mediacodec", 0);
                            }
                            ijkMediaPlayer.setOption(4, "mediacodec-auto-rotate", 0);
                            ijkMediaPlayer.setOption(4, "mediacodec-handle-resolution-change", 0);
                            ijkMediaPlayer.setOption(4, "opensles", 0);
                            ijkMediaPlayer.setOption(4, "overlay-format", 842225234);
                            ijkMediaPlayer.setOption(4, "framedrop", 1);
                            ijkMediaPlayer.setOption(4, "soundtouch", 1);
                            ijkMediaPlayer.setOption(4, "max-fps", 30);
                            if (!this.b || this.j == 4) {
                                ijkMediaPlayer.setOption(4, "start-on-prepared", 0);
                            } else {
                                ijkMediaPlayer.setOption(4, "start-on-prepared", 1);
                            }
                            ijkMediaPlayer.setOption(4, "load-on-prepared", 1);
                            ijkMediaPlayer.setOption(1, "http-detect-range-support", 0);
                            ijkMediaPlayer.setOption(2, "skip_loop_filter", 0);
                            ijkMediaPlayer.setOption(2, "skip_frame", 0);
                            ijkMediaPlayer.setOption(1, "timeout", (long) ((int) ((this.w.c * 1000.0f) * 1000.0f)));
                            ijkMediaPlayer.setOption(1, "reconnect", 1);
                            ijkMediaPlayer.setOption(1, "analyzeduration", 90000000);
                            String str3 = "enable-accurate-seek";
                            if (this.w.i) {
                                j2 = 1;
                            } else {
                                j2 = 0;
                            }
                            ijkMediaPlayer.setOption(4, str3, j2);
                            str3 = "disable-bitrate-sync";
                            if (!this.w.j) {
                                j = 1;
                            }
                            ijkMediaPlayer.setOption(4, str3, j);
                            ijkMediaPlayer.setOption(1, "dns_cache_timeout", 0);
                            if (this.w.h != null) {
                                str = null;
                                Iterator it = this.w.h.keySet().iterator();
                                while (true) {
                                    str3 = str;
                                    if (it.hasNext()) {
                                        str = (String) it.next();
                                        if (str3 == null) {
                                            str = String.format("%s: %s", new Object[]{str, this.w.h.get(str)});
                                        } else {
                                            str = str3 + "\r\n" + String.format("%s: %s", new Object[]{str, this.w.h.get(str)});
                                        }
                                    } else {
                                        ijkMediaPlayer.setOption(1, "headers", str3);
                                    }
                                }
                            }
                            ijkMediaPlayer.setBitrateIndex(this.O);
                            IjkMediaPlayer.native_setLogLevel(5);
                            if (this.w.e != null && this.D.d(uri)) {
                                this.D.b(this.w.e);
                                this.D.a(this.w.f);
                                this.C = this.D.c(uri);
                                if (this.C != null) {
                                    if (this.C.a() != null) {
                                        ijkMediaPlayer.setOption(1, "cache_file_path", this.C.a());
                                        str = "ijkio:cache:ffio:" + this.h.toString();
                                        iMediaPlayer2 = ijkMediaPlayer;
                                    } else if (this.C.b() != null) {
                                        ijkMediaPlayer.setOption(1, "cache_db_path", this.C.b());
                                        str = "ijkhlscache:" + this.h.toString();
                                        iMediaPlayer2 = ijkMediaPlayer;
                                    }
                                }
                            }
                            str = uri;
                            iMediaPlayer2 = ijkMediaPlayer;
                        } else {
                            str = uri;
                            iMediaPlayer2 = null;
                        }
                        TXCLog.i(this.g, "ijk media player " + iMediaPlayer2);
                        break;
                }
                this.l = new TextureMediaPlayer(iMediaPlayer2);
                this.l.setDataSource(str);
                this.l.setOnPreparedListener(this.e);
                this.l.setOnVideoSizeChangedListener(this.d);
                this.l.setOnCompletionListener(this.Q);
                this.l.setOnErrorListener(this.T);
                this.l.setOnInfoListener(this.R);
                this.l.setOnBufferingUpdateListener(this.W);
                this.l.setOnSeekCompleteListener(this.aa);
                this.l.setOnTimedTextListener(this.ab);
                this.l.setOnHLSKeyErrorListener(this.ad);
                this.l.setOnHevcVideoDecoderErrorListener(this.U);
                this.l.setOnVideoDecoderErrorListener(this.V);
                this.s = 0;
                a(this.l, this.k);
                this.l.setAudioStreamType(3);
                this.l.setScreenOnWhilePlaying(true);
                this.l.prepareAsync();
                setMute(this.N);
                this.i = 1;
                return true;
            }
            throw new FileNotFoundException();
        } catch (FileNotFoundException e) {
            this.i = -1;
            this.j = -1;
            this.T.onError(this.l, -1004, -3003);
        } catch (Exception e2) {
            TXCLog.w(this.g, e2.toString());
            this.i = -1;
            this.j = -1;
            this.T.onError(this.l, 1, 0);
        }
    }

    private void a(IMediaPlayer iMediaPlayer, a.b bVar) {
        if (iMediaPlayer != null) {
            if (bVar == null) {
                iMediaPlayer.setDisplay(null);
                return;
            }
            TXCLog.d(this.g, "bindSurfaceHolder");
            bVar.a(iMediaPlayer);
        }
    }

    void a() {
        if (this.l != null) {
            this.l.setDisplay(null);
        }
    }

    void a(boolean z) {
        if (this.l != null) {
            TXCLog.d(this.g, "release player " + this.l);
            this.l.reset();
            this.l.release();
            this.l = null;
            this.i = 0;
            if (z) {
                this.j = 0;
                this.m = 0;
                this.n = 0;
            }
            ((AudioManager) this.v.getSystemService("audio")).abandonAudioFocus(null);
        }
    }

    public void b() {
        TXCLog.i(this.g, "start");
        if (h()) {
            if (this.c == 1 && this.i == 5) {
                this.H = 0;
                this.l.seekTo(0);
            }
            this.l.start();
            if (!(this.i == 3 || this.J)) {
                this.i = 3;
                a(3001, "播放开始", "plying");
            }
        }
        this.j = 3;
    }

    private void g() {
        TXCLog.d(this.g, "replay");
        if (this.c == 0) {
            if (this.t == 0 && this.l != null) {
                this.t = (int) this.l.getCurrentPosition();
                this.l.stop();
                this.l.release();
                this.l = null;
            }
            if (!f()) {
                a(false);
            }
        } else if (this.c == 1) {
            j();
        }
    }

    public void c() {
        if (this.l != null) {
            if (this.C != null) {
                if (getDuration() <= 0) {
                    this.D.a(this.C.d(), true);
                } else {
                    this.D.a(this.C.d(), false);
                }
                this.C = null;
            }
            this.l.stop();
            this.l.release();
            this.l = null;
            this.h = null;
            this.m = 0;
            this.n = 0;
            this.B = 1.0f;
            this.J = false;
            this.K = -1;
            this.i = 0;
            this.j = 0;
            this.P = false;
            this.O = 0;
            ((AudioManager) this.v.getSystemService("audio")).abandonAudioFocus(null);
        }
        if (this.ag != null) {
            this.ag.removeMessages(102);
        }
        TXCLog.i(this.g, "stop");
    }

    public void d() {
        this.j = 4;
        TXCLog.i(this.g, "pause");
        if (h() && this.l.isPlaying()) {
            this.l.pause();
            this.i = 4;
        }
    }

    public int getDuration() {
        if (this.l != null && this.u < 1) {
            this.u = (int) this.l.getDuration();
        }
        return this.u;
    }

    public int getCurrentPosition() {
        if (this.c == 0) {
            if (this.t != 0) {
                return this.t;
            }
            if (this.J && this.K >= 0) {
                return this.K;
            }
        }
        if (this.l == null) {
            return 0;
        }
        int currentPosition = (int) this.l.getCurrentPosition();
        if (currentPosition <= 1) {
            return Math.max(currentPosition, this.H);
        }
        this.H = currentPosition;
        return currentPosition;
    }

    public void a(int i) {
        int min;
        TXCLog.d(this.g, "seek to " + i);
        if (getUrlPathExtention().equals(IjkMediaMeta.IJKM_KEY_M3U8)) {
            min = Math.min(i, getDuration() + FlowControl.DELAY_MAX_BRUSH);
        } else {
            min = i;
        }
        if (min >= 0) {
            if (h()) {
                if (min > getDuration()) {
                    min = getDuration();
                }
                if (this.J) {
                    this.K = min;
                } else {
                    this.K = -1;
                    this.l.seekTo((long) min);
                }
                if (this.c == 0) {
                    this.J = true;
                    return;
                }
                return;
            }
            this.t = min;
        }
    }

    public void setMute(boolean z) {
        this.N = z;
        if (this.l != null) {
            if (z) {
                this.l.setVolume(0.0f, 0.0f);
            } else {
                this.l.setVolume(this.L, this.M);
            }
        }
    }

    public boolean e() {
        return h() && this.l.isPlaying();
    }

    public int getBufferDuration() {
        if (this.l == null) {
            return 0;
        }
        IMediaPlayer unwrappedMediaPlayer = getUnwrappedMediaPlayer();
        try {
            if (1 == this.c && (unwrappedMediaPlayer instanceof IjkExoMediaPlayer)) {
                this.s = ((IjkExoMediaPlayer) unwrappedMediaPlayer).getBufferedPercentage();
            }
        } catch (NoClassDefFoundError e) {
        }
        int duration = (this.s * getDuration()) / 100;
        if (duration < getCurrentPosition()) {
            duration = getCurrentPosition();
        }
        if (Math.abs(getDuration() - duration) < 1000) {
            return getDuration();
        }
        return duration;
    }

    private boolean h() {
        return (this.l == null || this.i == -1 || this.i == 0 || this.i == 1) ? false : true;
    }

    public void setConfig(d dVar) {
        if (dVar != null) {
            this.w = dVar;
            this.D.a(this.w.k);
        }
    }

    public void setRenderMode(int i) {
        this.ae = i;
        if (this.x != null) {
            this.x.setAspectRatio(this.ae);
        }
        if (this.x != null) {
            this.x.setVideoRotation(this.q);
        }
    }

    public void setVideoRotationDegree(int i) {
        switch (i) {
            case 0:
            case 90:
            case TXLiveConstants.RENDER_ROTATION_180 /*180*/:
            case 270:
                break;
            case 360:
                i = 0;
                break;
            default:
                TXCLog.e(this.g, "not support degree " + i);
                return;
        }
        this.q = i;
        if (this.x != null) {
            this.x.setVideoRotation(this.q);
        }
        if (this.x != null) {
            this.x.setAspectRatio(this.ae);
        }
    }

    private void i() {
        setRender(0);
    }

    public void setAutoPlay(boolean z) {
        this.b = z;
    }

    public void setRate(float f) {
        TXCLog.d(this.g, "setRate " + f);
        if (this.l != null) {
            this.l.setRate(f);
        }
        this.B = f;
    }

    public void setAutoRotate(boolean z) {
        this.a = z;
    }

    private void j() {
        IjkExoMediaPlayer ijkExoMediaPlayer = (IjkExoMediaPlayer) getUnwrappedMediaPlayer();
        if (1 == this.c && (ijkExoMediaPlayer instanceof IjkExoMediaPlayer)) {
            ijkExoMediaPlayer.getPlayer().prepare(ijkExoMediaPlayer.buildMediaSource(this.h, null), false, false);
            if (this.A == null) {
                ijkExoMediaPlayer.getPlayer().setPlayWhenReady(this.b);
            } else {
                ijkExoMediaPlayer.getPlayer().setPlayWhenReady(true);
            }
        }
    }

    private void a(int i, String str, String str2) {
        if ((i != -3005 && i != 3010) || !this.ah) {
            Message message = new Message();
            message.what = 101;
            Bundle bundle = new Bundle();
            message.arg1 = i;
            bundle.putString(HttpConstant.DESCRIPTION, str);
            message.setData(bundle);
            if (this.ag != null) {
                this.ag.sendMessage(message);
            }
            TXCLog.d(this.g, "sendSimpleEvent " + i + " " + str2);
            boolean z = i == -3005 || i == 3010;
            this.ah = z;
        }
    }

    public void setListener(f fVar) {
        this.af = fVar;
    }

    public int getVideoWidth() {
        return this.m;
    }

    public int getVideoHeight() {
        return this.n;
    }

    public String getServerIp() {
        return this.A;
    }

    public int getPlayerType() {
        return this.c;
    }

    public void setPlayerType(int i) {
        this.c = i;
    }

    @NonNull
    String getUrlPathExtention() {
        if (this.h == null || this.h.getPath() == null) {
            return "";
        }
        String path = this.h.getPath();
        return path.substring(path.lastIndexOf(FileUtils.FILE_EXTENSION_SEPARATOR) + 1, path.length());
    }

    public IMediaPlayer getUnwrappedMediaPlayer() {
        if (this.l instanceof TextureMediaPlayer) {
            return ((TextureMediaPlayer) this.l).getBackEndMediaPlayer();
        }
        return this.l;
    }

    public int getBitrateIndex() {
        if (this.l != null) {
            return this.l.getBitrateIndex();
        }
        return this.O;
    }

    public void setBitrateIndex(int i) {
        TXCLog.d(this.g, "setBitrateIndex " + i);
        if (this.O != i) {
            this.O = i;
            if (this.l == null) {
                return;
            }
            if (this.w.j) {
                this.l.setBitrateIndex(i);
            } else {
                g();
            }
        }
    }

    public ArrayList<IjkBitrateItem> getSupportedBitrates() {
        if (this.l != null) {
            return this.l.getSupportedBitrates();
        }
        return new ArrayList();
    }

    public MediaInfo getMediaInfo() {
        if (this.l == null) {
            return null;
        }
        return this.l.getMediaInfo();
    }
}
