package com.tencent.ijk.media.player;

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
import com.tencent.ijk.media.player.misc.IMediaDataSource;

public abstract class AbstractMediaPlayer implements IMediaPlayer {
    private OnBufferingUpdateListener mOnBufferingUpdateListener;
    private OnCompletionListener mOnCompletionListener;
    private OnErrorListener mOnErrorListener;
    private OnHLSKeyErrorListener mOnHLSKeyErrorListener;
    private OnHevcVideoDecoderErrorListener mOnHevcVideoDecoderErrorListener;
    private OnInfoListener mOnInfoListener;
    private OnPreparedListener mOnPreparedListener;
    private OnSeekCompleteListener mOnSeekCompleteListener;
    private OnTimedTextListener mOnTimedTextListener;
    private OnVideoDecoderErrorListener mOnVideoDecoderErrorListener;
    private OnVideoSizeChangedListener mOnVideoSizeChangedListener;

    public final void setOnPreparedListener(OnPreparedListener onPreparedListener) {
        this.mOnPreparedListener = onPreparedListener;
    }

    public final void setOnCompletionListener(OnCompletionListener onCompletionListener) {
        this.mOnCompletionListener = onCompletionListener;
    }

    public final void setOnBufferingUpdateListener(OnBufferingUpdateListener onBufferingUpdateListener) {
        this.mOnBufferingUpdateListener = onBufferingUpdateListener;
    }

    public final void setOnSeekCompleteListener(OnSeekCompleteListener onSeekCompleteListener) {
        this.mOnSeekCompleteListener = onSeekCompleteListener;
    }

    public final void setOnVideoSizeChangedListener(OnVideoSizeChangedListener onVideoSizeChangedListener) {
        this.mOnVideoSizeChangedListener = onVideoSizeChangedListener;
    }

    public final void setOnErrorListener(OnErrorListener onErrorListener) {
        this.mOnErrorListener = onErrorListener;
    }

    public final void setOnInfoListener(OnInfoListener onInfoListener) {
        this.mOnInfoListener = onInfoListener;
    }

    public final void setOnTimedTextListener(OnTimedTextListener onTimedTextListener) {
        this.mOnTimedTextListener = onTimedTextListener;
    }

    public final void setOnHLSKeyErrorListener(OnHLSKeyErrorListener onHLSKeyErrorListener) {
        this.mOnHLSKeyErrorListener = onHLSKeyErrorListener;
    }

    public final void setOnHevcVideoDecoderErrorListener(OnHevcVideoDecoderErrorListener onHevcVideoDecoderErrorListener) {
        this.mOnHevcVideoDecoderErrorListener = onHevcVideoDecoderErrorListener;
    }

    public final void setOnVideoDecoderErrorListener(OnVideoDecoderErrorListener onVideoDecoderErrorListener) {
        this.mOnVideoDecoderErrorListener = onVideoDecoderErrorListener;
    }

    public void resetListeners() {
        this.mOnPreparedListener = null;
        this.mOnBufferingUpdateListener = null;
        this.mOnCompletionListener = null;
        this.mOnSeekCompleteListener = null;
        this.mOnVideoSizeChangedListener = null;
        this.mOnErrorListener = null;
        this.mOnInfoListener = null;
        this.mOnTimedTextListener = null;
        this.mOnHLSKeyErrorListener = null;
        this.mOnHevcVideoDecoderErrorListener = null;
        this.mOnVideoDecoderErrorListener = null;
    }

    protected final void notifyOnPrepared() {
        if (this.mOnPreparedListener != null) {
            this.mOnPreparedListener.onPrepared(this);
        }
    }

    protected final void notifyOnCompletion() {
        if (this.mOnCompletionListener != null) {
            this.mOnCompletionListener.onCompletion(this);
        }
    }

    protected final void notifyOnBufferingUpdate(int i) {
        if (this.mOnBufferingUpdateListener != null) {
            this.mOnBufferingUpdateListener.onBufferingUpdate(this, i);
        }
    }

    protected final void notifyOnSeekComplete() {
        if (this.mOnSeekCompleteListener != null) {
            this.mOnSeekCompleteListener.onSeekComplete(this);
        }
    }

    protected final void notifyOnVideoSizeChanged(int i, int i2, int i3, int i4) {
        if (this.mOnVideoSizeChangedListener != null) {
            this.mOnVideoSizeChangedListener.onVideoSizeChanged(this, i, i2, i3, i4);
        }
    }

    protected final boolean notifyOnError(int i, int i2) {
        return this.mOnErrorListener != null && this.mOnErrorListener.onError(this, i, i2);
    }

    protected final boolean notifyOnInfo(int i, int i2) {
        return this.mOnInfoListener != null && this.mOnInfoListener.onInfo(this, i, i2);
    }

    protected final void notifyOnTimedText(IjkTimedText ijkTimedText) {
        if (this.mOnTimedTextListener != null) {
            this.mOnTimedTextListener.onTimedText(this, ijkTimedText);
        }
    }

    public void setDataSource(IMediaDataSource iMediaDataSource) {
        throw new UnsupportedOperationException();
    }

    protected final void notifyHLSKeyError() {
        if (this.mOnHLSKeyErrorListener != null) {
            this.mOnHLSKeyErrorListener.onHLSKeyError(this);
        }
    }

    protected final void notifyHevcVideoDecoderError() {
        if (this.mOnHevcVideoDecoderErrorListener != null) {
            this.mOnHevcVideoDecoderErrorListener.onHevcVideoDecoderError(this);
        }
    }

    protected final void notifyVideoDecoderError() {
        if (this.mOnVideoDecoderErrorListener != null) {
            this.mOnVideoDecoderErrorListener.onVideoDecoderError(this);
        }
    }
}
