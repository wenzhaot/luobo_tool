package com.tencent.liteav;

import android.content.Context;
import android.os.Bundle;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import com.feng.car.utils.HttpConstant;
import com.tencent.ijk.media.player.IjkBitrateItem;
import com.tencent.ijk.media.player.MediaInfo;
import com.tencent.liteav.basic.b.a;
import com.tencent.liteav.basic.datareport.TXCDRApi;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.tencent.liteav.txcvodplayer.TextureRenderView;
import com.tencent.liteav.txcvodplayer.d;
import com.tencent.liteav.txcvodplayer.e;
import com.tencent.liteav.txcvodplayer.f;
import com.tencent.rtmp.TXBitrateItem;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXVodPlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: TXCVodPlayer */
public class g extends h {
    protected boolean a;
    private e f;
    private d g;
    private f h = null;
    private boolean i;
    private boolean j = true;
    private boolean k = true;
    private float l = 1.0f;
    private Surface m;
    private f n = new f() {
        public void a(int i, Bundle bundle) {
            int i2 = 1;
            int i3 = 0;
            Bundle bundle2 = new Bundle(bundle);
            switch (i) {
                case -3005:
                    i2 = TXLiveConstants.PLAY_ERR_HEVC_DECODE_FAIL;
                    if (!g.this.i) {
                        g.this.g.a(false);
                        break;
                    }
                    break;
                case -3004:
                    i2 = TXLiveConstants.PLAY_ERR_HLS_KEY;
                    break;
                case -3003:
                    i2 = TXLiveConstants.PLAY_ERR_FILE_NOT_FOUND;
                    break;
                case -3002:
                    i2 = TXLiveConstants.PLAY_ERR_NET_DISCONNECT;
                    break;
                case -3001:
                    i2 = TXLiveConstants.PLAY_ERR_NET_DISCONNECT;
                    break;
                case TXLiveConstants.PLAY_EVT_START_VIDEO_DECODER /*2008*/:
                    i2 = TXLiveConstants.PLAY_EVT_START_VIDEO_DECODER;
                    break;
                case 3000:
                    g.this.h.d();
                    i2 = TXLiveConstants.PLAY_EVT_VOD_PLAY_PREPARED;
                    break;
                case 3001:
                    g.this.h.d();
                    i2 = 2004;
                    break;
                case 3002:
                    i2 = 2006;
                    break;
                case 3003:
                    g.this.h.i();
                    i2 = TXLiveConstants.PLAY_EVT_PLAY_LOADING;
                    break;
                case TXLiveConstants.PUSH_WARNING_SERVER_DISCONNECT /*3004*/:
                    g.this.h.c();
                    if (!g.this.a) {
                        i2 = 2006;
                        break;
                    }
                    g.this.f.b();
                    TXCLog.d(TXVodPlayer.TAG, "loop play");
                    return;
                case 3005:
                    i2 = TXLiveConstants.PLAY_EVT_CHANGE_RESOLUTION;
                    break;
                case 3006:
                    i2 = TXLiveConstants.PLAY_WARNING_RECONNECT;
                    break;
                case 3007:
                    i2 = 2005;
                    g.this.h.a(bundle.getInt(TXLiveConstants.EVT_PLAY_DURATION, 0));
                    break;
                case 3008:
                    if (!g.this.i) {
                        g.this.i = true;
                        g.this.h.e();
                        Bundle bundle3 = new Bundle();
                        bundle3.putInt("EVT_ID", TXLiveConstants.PLAY_EVT_START_VIDEO_DECODER);
                        bundle3.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
                        MediaInfo mediaInfo = g.this.f.getMediaInfo();
                        if (!(mediaInfo == null || mediaInfo.mVideoDecoderImpl == null || !mediaInfo.mVideoDecoderImpl.contains("hevc"))) {
                            i3 = 1;
                        }
                        if (g.this.f.getPlayerType() == 0) {
                            if (i3 == 0) {
                                bundle3.putCharSequence(HttpConstant.DESCRIPTION, g.this.g.a() ? "启动硬解" : "启动软解");
                            } else {
                                bundle3.putCharSequence(HttpConstant.DESCRIPTION, g.this.g.a() ? "启动硬解265" : "启动软解265");
                            }
                            String str = "EVT_PARAM1";
                            if (!g.this.g.a()) {
                                i2 = 2;
                            }
                            bundle3.putInt(str, i2);
                            bundle3.putInt("hevc", i3);
                        } else {
                            bundle3.putCharSequence(HttpConstant.DESCRIPTION, "启动硬解");
                            bundle3.putInt("EVT_PARAM1", 2);
                        }
                        a(TXLiveConstants.PLAY_EVT_START_VIDEO_DECODER, bundle3);
                        i2 = 2003;
                        break;
                    }
                    return;
                case 3009:
                    bundle2.putInt("EVT_PARAM1", g.this.f.getMetaRotationDegree());
                    i2 = TXLiveConstants.PLAY_EVT_CHANGE_ROTATION;
                    break;
                case 3010:
                    i2 = TXLiveConstants.PLAY_WARNING_HW_ACCELERATION_FAIL;
                    if (!g.this.i) {
                        g.this.g.a(false);
                        break;
                    }
                    break;
                case 3012:
                    g.this.h.f();
                    return;
                case 3013:
                    g.this.h.h();
                    return;
                case 3014:
                    g.this.h.g();
                    return;
                case 3015:
                    return;
                case 3016:
                    i2 = TXLiveConstants.PLAY_EVT_VOD_LOADING_END;
                    break;
                default:
                    TXCLog.d(TXVodPlayer.TAG, "miss match event " + i);
                    return;
            }
            bundle2.putString(TXLiveConstants.EVT_DESCRIPTION, bundle.getString(HttpConstant.DESCRIPTION, ""));
            if (g.this.e != null) {
                a aVar = (a) g.this.e.get();
                if (aVar != null) {
                    aVar.onNotifyEvent(i2, bundle2);
                }
            }
        }

        public void a(Bundle bundle) {
            Bundle bundle2 = new Bundle();
            int[] a = com.tencent.liteav.basic.util.a.a();
            int intValue = Integer.valueOf(a[0]).intValue() / 10;
            bundle2.putCharSequence(TXLiveConstants.NET_STATUS_CPU_USAGE, intValue + "/" + (Integer.valueOf(a[1]).intValue() / 10) + "%");
            bundle2.putInt(TXLiveConstants.NET_STATUS_VIDEO_FPS, (int) bundle.getFloat("fps"));
            bundle2.putInt(TXLiveConstants.NET_STATUS_NET_SPEED, ((int) bundle.getLong("tcpSpeed")) / 1000);
            bundle2.putInt(TXLiveConstants.NET_STATUS_CODEC_CACHE, ((int) bundle.getLong("cachedBytes")) / 1000);
            bundle2.putInt(TXLiveConstants.NET_STATUS_VIDEO_WIDTH, g.this.f.getVideoWidth());
            bundle2.putInt(TXLiveConstants.NET_STATUS_VIDEO_HEIGHT, g.this.f.getVideoHeight());
            bundle2.putString(TXLiveConstants.NET_STATUS_SERVER_IP, g.this.f.getServerIp());
            if (g.this.e != null) {
                a aVar = (a) g.this.e.get();
                if (aVar != null) {
                    aVar.onNotifyEvent(15001, bundle2);
                }
            }
        }
    };

    public g(Context context) {
        super(context);
        this.f = new e(context);
        this.f.setListener(this.n);
    }

    public void a(c cVar) {
        super.a(cVar);
        if (this.g == null) {
            this.g = new d();
        }
        this.g.a((float) this.b.e);
        this.g.b((float) this.b.f);
        this.g.c((float) this.b.q);
        this.g.a(this.b.i);
        this.g.a(this.b.m);
        this.g.a(this.b.n);
        this.g.b(this.b.o);
        this.g.a(this.b.p);
        this.g.b(this.b.r);
        this.g.c(this.b.t);
        this.g.b(this.b.u);
        this.g.c(this.b.v);
        this.f.setConfig(this.g);
        this.k = cVar.s;
    }

    public int a(String str, int i) {
        if (this.d != null) {
            this.d.setVisibility(0);
            TextureView textureRenderView = new TextureRenderView(this.d.getContext());
            this.d.addVideoView(textureRenderView);
            this.f.setTextureRenderView(textureRenderView);
        } else if (this.m != null) {
            this.f.setRenderSurface(this.m);
        }
        this.h = new f(this.c);
        this.h.a(str);
        this.h.b();
        this.i = false;
        this.f.setPlayerType(this.g.b());
        this.f.setVideoPath(str);
        this.f.setAutoPlay(this.j);
        this.f.setRate(this.l);
        this.f.setAutoRotate(this.k);
        if (this.g != null) {
            this.f.b();
            if (this.g.b() == 1) {
                this.h.b(3);
            } else {
                this.h.b(1);
            }
        } else {
            this.f.b();
            this.h.b(1);
        }
        TXCLog.d(TXVodPlayer.TAG, "startPlay " + str);
        TXCDRApi.txReportDAU(this.c, com.tencent.liteav.basic.datareport.a.bo);
        return 0;
    }

    public int a(boolean z) {
        this.f.c();
        if (!(this.d == null || this.d.getVideoView() == null || !z)) {
            this.d.getVideoView().setVisibility(8);
        }
        if (this.h != null) {
            this.h.c();
        }
        return 0;
    }

    public void a(Surface surface) {
        this.m = surface;
        if (this.f != null) {
            this.f.setRenderSurface(this.m);
        }
    }

    public void a() {
        this.f.d();
    }

    public void b() {
        this.f.b();
    }

    public void a(int i) {
        this.f.a(i * 1000);
        if (this.i && this.h != null) {
            this.h.j();
        }
    }

    public void a(float f) {
        this.f.a((int) (1000.0f * f));
        if (this.i && this.h != null) {
            this.h.j();
        }
    }

    public float c() {
        if (this.f != null) {
            return ((float) this.f.getCurrentPosition()) / 1000.0f;
        }
        return 0.0f;
    }

    public float d() {
        if (this.f != null) {
            return ((float) this.f.getBufferDuration()) / 1000.0f;
        }
        return 0.0f;
    }

    public float e() {
        if (this.f != null) {
            return ((float) this.f.getDuration()) / 1000.0f;
        }
        return 0.0f;
    }

    public float f() {
        if (this.f != null) {
            return ((float) this.f.getBufferDuration()) / 1000.0f;
        }
        return 0.0f;
    }

    public int g() {
        if (this.f != null) {
            return this.f.getVideoWidth();
        }
        return 0;
    }

    public int h() {
        if (this.f != null) {
            return this.f.getVideoHeight();
        }
        return 0;
    }

    public void b(boolean z) {
        this.f.setMute(z);
    }

    public void b(int i) {
        if (i == 1) {
            this.f.setRenderMode(0);
        } else {
            this.f.setRenderMode(1);
        }
    }

    public void c(int i) {
        this.f.setVideoRotationDegree(360 - i);
    }

    public void a(TXCloudVideoView tXCloudVideoView) {
        if (!(this.d == null || this.d == tXCloudVideoView)) {
            View videoView = this.d.getVideoView();
            if (videoView != null) {
                this.d.removeView(videoView);
            }
        }
        super.a(tXCloudVideoView);
        if (this.d != null) {
            this.d.setVisibility(0);
            TextureView textureRenderView = new TextureRenderView(this.d.getContext());
            this.d.addVideoView(textureRenderView);
            this.f.setRenderView(textureRenderView);
        }
    }

    public void a(TextureRenderView textureRenderView) {
        if (this.f != null) {
            this.f.setRenderView(textureRenderView);
        }
    }

    public int d(int i) {
        return 0;
    }

    public int i() {
        return 0;
    }

    public TextureView j() {
        if (this.d != null) {
            return this.d.getVideoView();
        }
        return null;
    }

    public boolean k() {
        return this.f.e();
    }

    public void c(boolean z) {
        this.j = z;
        if (this.f != null) {
            this.f.setAutoPlay(z);
        }
    }

    public void b(float f) {
        this.l = f;
        if (this.f != null) {
            this.f.setRate(f);
        }
        if (this.i && this.h != null) {
            this.h.l();
        }
    }

    public int l() {
        if (this.f != null) {
            return this.f.getBitrateIndex();
        }
        return 0;
    }

    public void e(int i) {
        if (this.f != null) {
            this.f.setBitrateIndex(i);
        }
        if (this.i && this.h != null) {
            this.h.k();
        }
    }

    public ArrayList<TXBitrateItem> m() {
        ArrayList<TXBitrateItem> arrayList = new ArrayList();
        if (this.f != null) {
            ArrayList supportedBitrates = this.f.getSupportedBitrates();
            if (supportedBitrates != null) {
                Iterator it = supportedBitrates.iterator();
                while (it.hasNext()) {
                    IjkBitrateItem ijkBitrateItem = (IjkBitrateItem) it.next();
                    TXBitrateItem tXBitrateItem = new TXBitrateItem();
                    tXBitrateItem.index = ijkBitrateItem.index;
                    tXBitrateItem.width = ijkBitrateItem.width;
                    tXBitrateItem.height = ijkBitrateItem.height;
                    tXBitrateItem.bitrate = ijkBitrateItem.bitrate;
                    arrayList.add(tXBitrateItem);
                }
            }
        }
        return arrayList;
    }

    public void d(boolean z) {
        this.a = z;
    }

    public void e(boolean z) {
        TextureView j = j();
        if (j != null) {
            j.setScaleX(z ? -1.0f : 1.0f);
        }
        if (this.h != null) {
            this.h.a(z);
        }
    }
}
