package com.tencent.ijk.media.exo;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Surface;
import android.view.SurfaceHolder;
import com.feng.car.video.shortvideo.FileUtils;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer.EventListener;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.SimpleExoPlayer.VideoListener;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.DefaultSsChunkSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.upstream.DataSource.Factory;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Util;
import com.stub.StubApp;
import com.tencent.ijk.media.exo.demo.EventLogger;
import com.tencent.ijk.media.player.AbstractMediaPlayer;
import com.tencent.ijk.media.player.IMediaPlayer;
import com.tencent.ijk.media.player.IjkBitrateItem;
import com.tencent.ijk.media.player.MediaInfo;
import com.tencent.ijk.media.player.misc.IjkTrackInfo;
import java.io.FileDescriptor;
import java.util.ArrayList;
import java.util.Map;

public class IjkExoMediaPlayer extends AbstractMediaPlayer implements EventListener {
    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
    private EventLogger eventLogger;
    private Context mAppContext;
    private Uri mDataSource;
    private SimplePlayerListener mSimpleListener = new SimplePlayerListener();
    private Surface mSurface;
    private int mVideoHeight;
    private int mVideoWidth;
    private Handler mainHandler;
    private Factory mediaDataSourceFactory;
    private SimpleExoPlayer player;
    private DefaultTrackSelector trackSelector;

    private class SimplePlayerListener implements VideoListener {
        private SimplePlayerListener() {
        }

        public void onVideoSizeChanged(int i, int i2, int i3, float f) {
            IjkExoMediaPlayer.this.mVideoWidth = i;
            IjkExoMediaPlayer.this.mVideoHeight = i2;
            IjkExoMediaPlayer.this.access$200(i, i2, 1, 1);
            if (i3 > 0) {
                IjkExoMediaPlayer.this.access$400(10001, i3);
            }
        }

        public void onRenderedFirstFrame() {
            IjkExoMediaPlayer.this.access$400(3, 0);
        }
    }

    public IjkExoMediaPlayer(Context context) {
        this.mAppContext = StubApp.getOrigApplicationContext(context.getApplicationContext());
        this.mediaDataSourceFactory = buildDataSourceFactory(true);
        this.mainHandler = new Handler();
        DefaultRenderersFactory defaultRenderersFactory = new DefaultRenderersFactory(context);
        this.trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(BANDWIDTH_METER));
        this.player = ExoPlayerFactory.newSimpleInstance(defaultRenderersFactory, this.trackSelector);
        this.player.addListener(this);
        this.eventLogger = new EventLogger(this.trackSelector);
        this.player.addListener(this.eventLogger);
        this.player.setAudioDebugListener(this.eventLogger);
        this.player.setVideoDebugListener(this.eventLogger);
        this.player.setMetadataOutput(this.eventLogger);
        this.player.setVideoListener(this.mSimpleListener);
    }

    public SimpleExoPlayer getPlayer() {
        return this.player;
    }

    public void setDisplay(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            setSurface(null);
        } else {
            setSurface(surfaceHolder.getSurface());
        }
    }

    public void setSurface(Surface surface) {
        this.mSurface = surface;
        if (this.player != null) {
            this.player.setVideoSurface(surface);
        }
    }

    public Surface getSurface() {
        return this.mSurface;
    }

    public void setDataSource(Context context, Uri uri) {
        this.mDataSource = uri;
    }

    public void setDataSource(Context context, Uri uri, Map<String, String> map) {
        setDataSource(context, uri);
    }

    public void setDataSource(String str) {
        setDataSource(this.mAppContext, Uri.parse(str));
    }

    public void setDataSource(FileDescriptor fileDescriptor) {
        throw new UnsupportedOperationException("no support");
    }

    public String getDataSource() {
        return this.mDataSource.toString();
    }

    public void prepareAsync() {
        this.player.prepare(buildMediaSource(this.mDataSource, null));
        this.player.setPlayWhenReady(false);
    }

    public void start() {
        if (this.player != null) {
            this.player.setPlayWhenReady(true);
        }
    }

    public void stop() throws IllegalStateException {
        if (this.player != null) {
            this.player.release();
        }
    }

    public void pause() throws IllegalStateException {
        if (this.player != null) {
            this.player.setPlayWhenReady(false);
        }
    }

    public void setWakeMode(Context context, int i) {
    }

    public void setScreenOnWhilePlaying(boolean z) {
    }

    public IjkTrackInfo[] getTrackInfo() {
        return null;
    }

    public int getVideoWidth() {
        return this.mVideoWidth;
    }

    public int getVideoHeight() {
        return this.mVideoHeight;
    }

    public boolean isPlaying() {
        if (this.player == null) {
            return false;
        }
        switch (this.player.getPlaybackState()) {
            case 2:
            case 3:
                return this.player.getPlayWhenReady();
            default:
                return false;
        }
    }

    public void seekTo(long j) throws IllegalStateException {
        if (this.player != null) {
            this.player.seekTo(j);
        }
    }

    public long getCurrentPosition() {
        if (this.player == null) {
            return 0;
        }
        return this.player.getCurrentPosition();
    }

    public long getDuration() {
        if (this.player == null) {
            return 0;
        }
        return this.player.getDuration();
    }

    public int getVideoSarNum() {
        return 1;
    }

    public int getVideoSarDen() {
        return 1;
    }

    public void reset() {
        if (this.player != null) {
            this.player.release();
            this.player.removeListener(this.eventLogger);
            this.player = null;
        }
        this.mSurface = null;
        this.mDataSource = null;
        this.mVideoWidth = 0;
        this.mVideoHeight = 0;
    }

    public void setLooping(boolean z) {
        throw new UnsupportedOperationException("no support");
    }

    public boolean isLooping() {
        return false;
    }

    public void setRate(float f) {
        this.player.setPlaybackParameters(new PlaybackParameters(f, f));
    }

    public void setVolume(float f, float f2) {
        this.player.setVolume((f + f2) / 2.0f);
    }

    public int getAudioSessionId() {
        return 0;
    }

    public MediaInfo getMediaInfo() {
        return null;
    }

    public void setLogEnabled(boolean z) {
    }

    public boolean isPlayable() {
        return true;
    }

    public void setAudioStreamType(int i) {
    }

    public void setKeepInBackground(boolean z) {
    }

    public void release() {
        if (this.player != null) {
            reset();
            this.eventLogger = null;
        }
    }

    public int getBufferedPercentage() {
        if (this.player == null) {
            return 0;
        }
        return this.player.getBufferedPercentage();
    }

    public Format getVideoFormat() {
        if (this.player == null) {
            return null;
        }
        return this.player.getVideoFormat();
    }

    public int getObservedBitrate() {
        return this.eventLogger.getObservedBitrate();
    }

    public DecoderCounters getVideoDecoderCounters() {
        return this.player.getVideoDecoderCounters();
    }

    public MediaSource buildMediaSource(Uri uri, String str) {
        int inferContentType;
        if (TextUtils.isEmpty(str)) {
            inferContentType = Util.inferContentType(uri);
        } else {
            inferContentType = Util.inferContentType(FileUtils.FILE_EXTENSION_SEPARATOR + str);
        }
        switch (inferContentType) {
            case 0:
                return new DashMediaSource(uri, buildDataSourceFactory(false), new DefaultDashChunkSource.Factory(this.mediaDataSourceFactory), this.mainHandler, this.eventLogger);
            case 1:
                return new SsMediaSource(uri, buildDataSourceFactory(false), new DefaultSsChunkSource.Factory(this.mediaDataSourceFactory), this.mainHandler, this.eventLogger);
            case 2:
                return new HlsMediaSource(uri, this.mediaDataSourceFactory, this.mainHandler, this.eventLogger);
            case 3:
                return new ExtractorMediaSource(uri, this.mediaDataSourceFactory, new DefaultExtractorsFactory(), this.mainHandler, this.eventLogger);
            default:
                throw new IllegalStateException("Unsupported type: " + inferContentType);
        }
    }

    private Factory buildDataSourceFactory(boolean z) {
        DefaultBandwidthMeter defaultBandwidthMeter = z ? BANDWIDTH_METER : null;
        return new DefaultDataSourceFactory(this.mAppContext, defaultBandwidthMeter, buildHttpDataSourceFactory(defaultBandwidthMeter));
    }

    public HttpDataSource.Factory buildHttpDataSourceFactory(DefaultBandwidthMeter defaultBandwidthMeter) {
        return new DefaultHttpDataSourceFactory(Util.getUserAgent(this.mAppContext, "ExoPlayerDemo"), defaultBandwidthMeter);
    }

    public void onTimelineChanged(Timeline timeline, Object obj) {
    }

    public void onTracksChanged(TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {
    }

    public void onLoadingChanged(boolean z) {
    }

    public void onPlayerStateChanged(boolean z, int i) {
        switch (i) {
            case 1:
                notifyOnCompletion();
                return;
            case 2:
                access$400(IMediaPlayer.MEDIA_INFO_BUFFERING_START, this.player.getBufferedPercentage());
                return;
            case 3:
                notifyOnPrepared();
                return;
            case 4:
                notifyOnCompletion();
                return;
            default:
                return;
        }
    }

    public void onPlayerError(ExoPlaybackException exoPlaybackException) {
        notifyOnError(1, 1);
    }

    public void onPositionDiscontinuity() {
    }

    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
    }

    public int getBitrateIndex() {
        return 0;
    }

    public void setBitrateIndex(int i) {
    }

    public ArrayList<IjkBitrateItem> getSupportedBitrates() {
        return new ArrayList();
    }
}
