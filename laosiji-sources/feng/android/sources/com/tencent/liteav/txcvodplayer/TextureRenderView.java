package com.tencent.liteav.txcvodplayer;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.tencent.ijk.media.player.IMediaPlayer;
import com.tencent.ijk.media.player.ISurfaceTextureHolder;
import com.tencent.ijk.media.player.ISurfaceTextureHost;
import com.tencent.liteav.basic.log.TXCLog;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@TargetApi(14)
public class TextureRenderView extends TextureView implements a {
    private static final String TAG = "TextureRenderView";
    private b mMeasureHelper;
    private b mSurfaceCallback;

    private static final class a implements com.tencent.liteav.txcvodplayer.a.b {
        private TextureRenderView a;
        private SurfaceTexture b;
        private ISurfaceTextureHost c;
        private Surface d;

        public a(@NonNull TextureRenderView textureRenderView, @Nullable SurfaceTexture surfaceTexture, @NonNull ISurfaceTextureHost iSurfaceTextureHost) {
            this.a = textureRenderView;
            this.b = surfaceTexture;
            this.c = iSurfaceTextureHost;
        }

        @TargetApi(16)
        public void a(IMediaPlayer iMediaPlayer) {
            if (iMediaPlayer != null) {
                if (VERSION.SDK_INT < 16 || !(iMediaPlayer instanceof ISurfaceTextureHolder)) {
                    this.d = b();
                    iMediaPlayer.setSurface(this.d);
                    return;
                }
                ISurfaceTextureHolder iSurfaceTextureHolder = (ISurfaceTextureHolder) iMediaPlayer;
                this.a.mSurfaceCallback.a(false);
                if (this.a.getSurfaceTexture() != null) {
                    this.b = this.a.getSurfaceTexture();
                }
                try {
                    SurfaceTexture surfaceTexture = iSurfaceTextureHolder.getSurfaceTexture();
                    if (surfaceTexture != null) {
                        this.a.setSurfaceTexture(surfaceTexture);
                        this.a.mSurfaceCallback.a(surfaceTexture);
                    } else {
                        if (this.d != null) {
                            iMediaPlayer.setSurface(this.d);
                        }
                        iSurfaceTextureHolder.setSurfaceTexture(this.b);
                        iSurfaceTextureHolder.setSurfaceTextureHost(this.a.mSurfaceCallback);
                    }
                    this.d = iMediaPlayer.getSurface();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @NonNull
        public a a() {
            return this.a;
        }

        @Nullable
        public Surface b() {
            if (this.b == null) {
                return null;
            }
            if (this.d == null) {
                this.d = new Surface(this.b);
            }
            return this.d;
        }
    }

    private static final class b implements SurfaceTextureListener, ISurfaceTextureHost {
        private SurfaceTexture a;
        private boolean b;
        private int c;
        private int d;
        private boolean e = true;
        private boolean f = false;
        private boolean g = false;
        private WeakReference<TextureRenderView> h;
        private Map<com.tencent.liteav.txcvodplayer.a.a, Object> i = new ConcurrentHashMap();

        public b(@NonNull TextureRenderView textureRenderView) {
            this.h = new WeakReference(textureRenderView);
        }

        public void a(boolean z) {
            this.e = z;
        }

        public void a(SurfaceTexture surfaceTexture) {
            this.a = surfaceTexture;
        }

        public void a(@NonNull com.tencent.liteav.txcvodplayer.a.a aVar) {
            this.i.put(aVar, aVar);
            com.tencent.liteav.txcvodplayer.a.b bVar = null;
            if (this.a != null) {
                if (null == null) {
                    bVar = new a((TextureRenderView) this.h.get(), this.a, this);
                }
                aVar.a(bVar, this.c, this.d);
            }
            if (this.b) {
                if (bVar == null) {
                    bVar = new a((TextureRenderView) this.h.get(), this.a, this);
                }
                aVar.a(bVar, 0, this.c, this.d);
            }
        }

        public void b(@NonNull com.tencent.liteav.txcvodplayer.a.a aVar) {
            this.i.remove(aVar);
        }

        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
            this.a = surfaceTexture;
            this.b = false;
            this.c = 0;
            this.d = 0;
            com.tencent.liteav.txcvodplayer.a.b aVar = new a((TextureRenderView) this.h.get(), surfaceTexture, this);
            for (com.tencent.liteav.txcvodplayer.a.a a : this.i.keySet()) {
                a.a(aVar, 0, 0);
            }
        }

        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
            this.a = surfaceTexture;
            this.b = true;
            this.c = i;
            this.d = i2;
            com.tencent.liteav.txcvodplayer.a.b aVar = new a((TextureRenderView) this.h.get(), surfaceTexture, this);
            for (com.tencent.liteav.txcvodplayer.a.a a : this.i.keySet()) {
                a.a(aVar, 0, i, i2);
            }
        }

        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            this.a = surfaceTexture;
            this.b = false;
            this.c = 0;
            this.d = 0;
            com.tencent.liteav.txcvodplayer.a.b aVar = new a((TextureRenderView) this.h.get(), surfaceTexture, this);
            for (com.tencent.liteav.txcvodplayer.a.a a : this.i.keySet()) {
                a.a(aVar);
            }
            TXCLog.d(TextureRenderView.TAG, "onSurfaceTextureDestroyed: destroy: " + this.e);
            return this.e;
        }

        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        }

        public void releaseSurfaceTexture(SurfaceTexture surfaceTexture) {
            if (surfaceTexture == null) {
                TXCLog.d(TextureRenderView.TAG, "releaseSurfaceTexture: null");
            } else if (this.g) {
                if (surfaceTexture != this.a) {
                    TXCLog.d(TextureRenderView.TAG, "releaseSurfaceTexture: didDetachFromWindow(): release different SurfaceTexture");
                    surfaceTexture.release();
                } else if (this.e) {
                    TXCLog.d(TextureRenderView.TAG, "releaseSurfaceTexture: didDetachFromWindow(): already released by TextureView");
                } else {
                    TXCLog.d(TextureRenderView.TAG, "releaseSurfaceTexture: didDetachFromWindow(): release detached SurfaceTexture");
                    surfaceTexture.release();
                }
            } else if (this.f) {
                if (surfaceTexture != this.a) {
                    TXCLog.d(TextureRenderView.TAG, "releaseSurfaceTexture: willDetachFromWindow(): release different SurfaceTexture");
                    surfaceTexture.release();
                } else if (this.e) {
                    TXCLog.d(TextureRenderView.TAG, "releaseSurfaceTexture: willDetachFromWindow(): will released by TextureView");
                } else {
                    TXCLog.d(TextureRenderView.TAG, "releaseSurfaceTexture: willDetachFromWindow(): re-attach SurfaceTexture to TextureView");
                    a(true);
                }
            } else if (surfaceTexture != this.a) {
                TXCLog.d(TextureRenderView.TAG, "releaseSurfaceTexture: alive: release different SurfaceTexture");
                surfaceTexture.release();
            } else if (this.e) {
                TXCLog.d(TextureRenderView.TAG, "releaseSurfaceTexture: alive: will released by TextureView");
            } else {
                TXCLog.d(TextureRenderView.TAG, "releaseSurfaceTexture: alive: re-attach SurfaceTexture to TextureView");
                a(true);
            }
        }

        public void a() {
            TXCLog.d(TextureRenderView.TAG, "willDetachFromWindow()");
            this.f = true;
        }

        public void b() {
            TXCLog.d(TextureRenderView.TAG, "didDetachFromWindow()");
            this.g = true;
        }
    }

    public TextureRenderView(Context context) {
        super(context);
        initView(context);
    }

    public TextureRenderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }

    public TextureRenderView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView(context);
    }

    @TargetApi(21)
    public TextureRenderView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        initView(context);
    }

    private void initView(Context context) {
        this.mMeasureHelper = new b(this);
        this.mSurfaceCallback = new b(this);
        setSurfaceTextureListener(this.mSurfaceCallback);
    }

    public View getView() {
        return this;
    }

    public boolean shouldWaitForResize() {
        return false;
    }

    protected void onDetachedFromWindow() {
        try {
            this.mSurfaceCallback.a();
            super.onDetachedFromWindow();
            this.mSurfaceCallback.b();
        } catch (Exception e) {
        }
    }

    public void setVideoSize(int i, int i2) {
        if (i > 0 && i2 > 0) {
            this.mMeasureHelper.a(i, i2);
            requestLayout();
        }
    }

    public void setVideoSampleAspectRatio(int i, int i2) {
        if (i > 0 && i2 > 0) {
            this.mMeasureHelper.b(i, i2);
            requestLayout();
        }
    }

    public void setVideoRotation(int i) {
        this.mMeasureHelper.a(i);
        setRotation((float) i);
    }

    public void setAspectRatio(int i) {
        this.mMeasureHelper.b(i);
        requestLayout();
    }

    protected void onMeasure(int i, int i2) {
        this.mMeasureHelper.c(i, i2);
        setMeasuredDimension(this.mMeasureHelper.a(), this.mMeasureHelper.b());
    }

    public com.tencent.liteav.txcvodplayer.a.b getSurfaceHolder() {
        return new a(this, this.mSurfaceCallback.a, this.mSurfaceCallback);
    }

    public void addRenderCallback(com.tencent.liteav.txcvodplayer.a.a aVar) {
        this.mSurfaceCallback.a(aVar);
    }

    public void removeRenderCallback(com.tencent.liteav.txcvodplayer.a.a aVar) {
        this.mSurfaceCallback.b(aVar);
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName(TextureRenderView.class.getName());
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(TextureRenderView.class.getName());
    }
}
