package com.tencent.ijk.media.player;

import android.annotation.TargetApi;
import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.SurfaceHolder;
import java.util.ArrayList;

@TargetApi(14)
public class TextureMediaPlayer extends MediaPlayerProxy implements IMediaPlayer, ISurfaceTextureHolder {
    private IMediaPlayer mBackEndMediaPlayer;
    private boolean mReuseSurfaceTexture;
    private Surface mSurface;
    private SurfaceTexture mSurfaceTexture;
    private ISurfaceTextureHost mSurfaceTextureHost;

    public TextureMediaPlayer(IMediaPlayer iMediaPlayer) {
        super(iMediaPlayer);
        this.mBackEndMediaPlayer = iMediaPlayer;
    }

    public void releaseSurfaceTexture() {
        if (this.mSurfaceTexture != null && !this.mReuseSurfaceTexture) {
            if (this.mSurfaceTextureHost != null) {
                this.mSurfaceTextureHost.releaseSurfaceTexture(this.mSurfaceTexture);
            } else {
                this.mSurfaceTexture.release();
            }
            this.mSurfaceTexture = null;
        }
    }

    public void reset() {
        super.reset();
        releaseSurfaceTexture();
    }

    public void release() {
        super.release();
        releaseSurfaceTexture();
    }

    public void setDisplay(SurfaceHolder surfaceHolder) {
        if (this.mSurfaceTexture == null) {
            super.setDisplay(surfaceHolder);
        }
    }

    public void setSurface(Surface surface) {
        if (this.mSurfaceTexture == null) {
            super.setSurface(surface);
        }
        this.mSurface = surface;
    }

    public Surface getSurface() {
        return super.getSurface();
    }

    public void setSurfaceTexture(SurfaceTexture surfaceTexture) {
        if (this.mSurfaceTexture != surfaceTexture) {
            releaseSurfaceTexture();
            this.mSurfaceTexture = surfaceTexture;
            if (surfaceTexture == null) {
                this.mSurface = null;
                super.setSurface(null);
                return;
            }
            if (this.mSurface == null) {
                this.mSurface = new Surface(surfaceTexture);
            }
            super.setSurface(this.mSurface);
        }
    }

    public SurfaceTexture getSurfaceTexture() {
        return this.mSurfaceTexture;
    }

    public void setSurfaceTextureHost(ISurfaceTextureHost iSurfaceTextureHost) {
        this.mSurfaceTextureHost = iSurfaceTextureHost;
    }

    public void setReuseSurfaceTexture(boolean z) {
        this.mReuseSurfaceTexture = z;
    }

    public IMediaPlayer getBackEndMediaPlayer() {
        return this.mBackEndMediaPlayer;
    }

    public int getBitrateIndex() {
        return this.mBackEndMediaPlayer.getBitrateIndex();
    }

    public void setBitrateIndex(int i) {
        this.mBackEndMediaPlayer.setBitrateIndex(i);
    }

    public ArrayList<IjkBitrateItem> getSupportedBitrates() {
        return this.mBackEndMediaPlayer.getSupportedBitrates();
    }
}
