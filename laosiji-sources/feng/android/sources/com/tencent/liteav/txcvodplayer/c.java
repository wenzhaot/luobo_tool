package com.tencent.liteav.txcvodplayer;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.tencent.ijk.media.player.IMediaPlayer;
import com.tencent.ijk.media.player.ISurfaceTextureHolder;
import com.tencent.liteav.basic.log.TXCLog;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: SurfaceRenderView */
public class c extends SurfaceView implements a {
    private b a;
    private b b;

    /* compiled from: SurfaceRenderView */
    private static final class a implements com.tencent.liteav.txcvodplayer.a.b {
        private c a;
        private SurfaceHolder b;

        public a(@NonNull c cVar, @Nullable SurfaceHolder surfaceHolder) {
            this.a = cVar;
            this.b = surfaceHolder;
        }

        public void a(IMediaPlayer iMediaPlayer) {
            if (iMediaPlayer != null) {
                if (VERSION.SDK_INT >= 16 && (iMediaPlayer instanceof ISurfaceTextureHolder)) {
                    ((ISurfaceTextureHolder) iMediaPlayer).setSurfaceTexture(null);
                }
                iMediaPlayer.setDisplay(this.b);
            }
        }

        @NonNull
        public a a() {
            return this.a;
        }
    }

    /* compiled from: SurfaceRenderView */
    private static final class b implements Callback {
        private SurfaceHolder a;
        private boolean b;
        private int c;
        private int d;
        private int e;
        private WeakReference<c> f;
        private Map<com.tencent.liteav.txcvodplayer.a.a, Object> g = new ConcurrentHashMap();

        public b(@NonNull c cVar) {
            this.f = new WeakReference(cVar);
        }

        public void a(@NonNull com.tencent.liteav.txcvodplayer.a.a aVar) {
            this.g.put(aVar, aVar);
            com.tencent.liteav.txcvodplayer.a.b bVar = null;
            if (this.a != null) {
                if (null == null) {
                    bVar = new a((c) this.f.get(), this.a);
                }
                aVar.a(bVar, this.d, this.e);
            }
            if (this.b) {
                if (bVar == null) {
                    bVar = new a((c) this.f.get(), this.a);
                }
                aVar.a(bVar, this.c, this.d, this.e);
            }
        }

        public void b(@NonNull com.tencent.liteav.txcvodplayer.a.a aVar) {
            this.g.remove(aVar);
        }

        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            this.a = surfaceHolder;
            this.b = false;
            this.c = 0;
            this.d = 0;
            this.e = 0;
            com.tencent.liteav.txcvodplayer.a.b aVar = new a((c) this.f.get(), this.a);
            for (com.tencent.liteav.txcvodplayer.a.a a : this.g.keySet()) {
                a.a(aVar, 0, 0);
            }
        }

        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            this.a = null;
            this.b = false;
            this.c = 0;
            this.d = 0;
            this.e = 0;
            com.tencent.liteav.txcvodplayer.a.b aVar = new a((c) this.f.get(), this.a);
            for (com.tencent.liteav.txcvodplayer.a.a a : this.g.keySet()) {
                a.a(aVar);
            }
        }

        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
            this.a = surfaceHolder;
            this.b = true;
            this.c = i;
            this.d = i2;
            this.e = i3;
            com.tencent.liteav.txcvodplayer.a.b aVar = new a((c) this.f.get(), this.a);
            for (com.tencent.liteav.txcvodplayer.a.a a : this.g.keySet()) {
                a.a(aVar, i, i2, i3);
            }
        }
    }

    public c(Context context) {
        super(context);
        a(context);
    }

    private void a(Context context) {
        this.a = new b(this);
        this.b = new b(this);
        getHolder().addCallback(this.b);
        getHolder().setType(0);
    }

    public View getView() {
        return this;
    }

    public boolean shouldWaitForResize() {
        return true;
    }

    public void setVideoSize(int i, int i2) {
        if (i > 0 && i2 > 0) {
            this.a.a(i, i2);
            getHolder().setFixedSize(i, i2);
            requestLayout();
        }
    }

    public void setVideoSampleAspectRatio(int i, int i2) {
        if (i > 0 && i2 > 0) {
            this.a.b(i, i2);
            requestLayout();
        }
    }

    public void setVideoRotation(int i) {
        TXCLog.e("", "SurfaceView doesn't support rotation (" + i + ")!\n");
    }

    public void setAspectRatio(int i) {
        this.a.b(i);
        requestLayout();
    }

    protected void onMeasure(int i, int i2) {
        this.a.c(i, i2);
        setMeasuredDimension(this.a.a(), this.a.b());
    }

    public void addRenderCallback(com.tencent.liteav.txcvodplayer.a.a aVar) {
        this.b.a(aVar);
    }

    public void removeRenderCallback(com.tencent.liteav.txcvodplayer.a.a aVar) {
        this.b.b(aVar);
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName(c.class.getName());
    }

    @TargetApi(14)
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        if (VERSION.SDK_INT >= 14) {
            accessibilityNodeInfo.setClassName(c.class.getName());
        }
    }
}
