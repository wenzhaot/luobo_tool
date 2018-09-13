package com.tencent.ijk.media.exo.demo;

import android.os.SystemClock;
import android.util.Log;
import android.view.Surface;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer.EventListener;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.Timeline.Period;
import com.google.android.exoplayer2.Timeline.Window;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.drm.DefaultDrmSessionManager;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.Metadata.Entry;
import com.google.android.exoplayer2.metadata.MetadataRenderer.Output;
import com.google.android.exoplayer2.metadata.emsg.EventMessage;
import com.google.android.exoplayer2.metadata.id3.ApicFrame;
import com.google.android.exoplayer2.metadata.id3.CommentFrame;
import com.google.android.exoplayer2.metadata.id3.GeobFrame;
import com.google.android.exoplayer2.metadata.id3.Id3Frame;
import com.google.android.exoplayer2.metadata.id3.PrivFrame;
import com.google.android.exoplayer2.metadata.id3.TextInformationFrame;
import com.google.android.exoplayer2.metadata.id3.UrlLinkFrame;
import com.google.android.exoplayer2.source.AdaptiveMediaSourceEventListener;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector.MappedTrackInfo;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.video.VideoRendererEventListener;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

public class EventLogger implements EventListener, AudioRendererEventListener, DefaultDrmSessionManager.EventListener, Output, AdaptiveMediaSourceEventListener, ExtractorMediaSource.EventListener, VideoRendererEventListener {
    private static final int MAX_TIMELINE_ITEM_LINES = 3;
    private static final String TAG = "EventLogger";
    private static final NumberFormat TIME_FORMAT = NumberFormat.getInstance(Locale.US);
    private long mBytesLoaded = 0;
    private long mBytesLoadedSeconds = 0;
    private long mLastBytesLoadedTime = 0;
    private final Period period;
    private final long startTimeMs;
    private final MappingTrackSelector trackSelector;
    private final Window window;

    static {
        TIME_FORMAT.setMinimumFractionDigits(2);
        TIME_FORMAT.setMaximumFractionDigits(2);
        TIME_FORMAT.setGroupingUsed(false);
    }

    public EventLogger(MappingTrackSelector mappingTrackSelector) {
        this.trackSelector = mappingTrackSelector;
        this.window = new Window();
        this.period = new Period();
        this.startTimeMs = SystemClock.elapsedRealtime();
    }

    public void onLoadingChanged(boolean z) {
        Log.d(TAG, "loading [" + z + "]");
    }

    public void onPlayerStateChanged(boolean z, int i) {
        Log.d(TAG, "state [" + getSessionTimeString() + ", " + z + ", " + getStateString(i) + "]");
    }

    public void onPositionDiscontinuity() {
        Log.d(TAG, "positionDiscontinuity");
    }

    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
        Log.d(TAG, "playbackParameters " + String.format("[speed=%.2f, pitch=%.2f]", new Object[]{Float.valueOf(playbackParameters.speed), Float.valueOf(playbackParameters.pitch)}));
    }

    public void onTimelineChanged(Timeline timeline, Object obj) {
        int i = 0;
        int periodCount = timeline.getPeriodCount();
        int windowCount = timeline.getWindowCount();
        Log.d(TAG, "sourceInfo [periodCount=" + periodCount + ", windowCount=" + windowCount);
        for (int i2 = 0; i2 < Math.min(periodCount, 3); i2++) {
            timeline.getPeriod(i2, this.period);
            Log.d(TAG, "  period [" + getTimeString(this.period.getDurationMs()) + "]");
        }
        if (periodCount > 3) {
            Log.d(TAG, "  ...");
        }
        while (i < Math.min(windowCount, 3)) {
            timeline.getWindow(i, this.window);
            Log.d(TAG, "  window [" + getTimeString(this.window.getDurationMs()) + ", " + this.window.isSeekable + ", " + this.window.isDynamic + "]");
            i++;
        }
        if (windowCount > 3) {
            Log.d(TAG, "  ...");
        }
        Log.d(TAG, "]");
    }

    public void onPlayerError(ExoPlaybackException exoPlaybackException) {
        Log.e(TAG, "playerFailed [" + getSessionTimeString() + "]", exoPlaybackException);
    }

    public void onTracksChanged(TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {
        MappedTrackInfo currentMappedTrackInfo = this.trackSelector.getCurrentMappedTrackInfo();
        if (currentMappedTrackInfo == null) {
            Log.d(TAG, "Tracks []");
            return;
        }
        int i;
        int i2;
        Log.d(TAG, "Tracks [");
        for (i = 0; i < currentMappedTrackInfo.length; i++) {
            TrackGroupArray trackGroups = currentMappedTrackInfo.getTrackGroups(i);
            TrackSelection trackSelection = trackSelectionArray.get(i);
            if (trackGroups.length > 0) {
                Log.d(TAG, "  Renderer:" + i + " [");
                for (i2 = 0; i2 < trackGroups.length; i2++) {
                    TrackGroup trackGroup = trackGroups.get(i2);
                    Log.d(TAG, "    Group:" + i2 + ", adaptive_supported=" + getAdaptiveSupportString(trackGroup.length, currentMappedTrackInfo.getAdaptiveSupport(i, i2, false)) + " [");
                    for (int i3 = 0; i3 < trackGroup.length; i3++) {
                        String trackStatusString = getTrackStatusString(trackSelection, trackGroup, i3);
                        Log.d(TAG, "      " + trackStatusString + " Track:" + i3 + ", " + Format.toLogString(trackGroup.getFormat(i3)) + ", supported=" + getFormatSupportString(currentMappedTrackInfo.getTrackFormatSupport(i, i2, i3)));
                    }
                    Log.d(TAG, "    ]");
                }
                if (trackSelection != null) {
                    for (i2 = 0; i2 < trackSelection.length(); i2++) {
                        Metadata metadata = trackSelection.getFormat(i2).metadata;
                        if (metadata != null) {
                            Log.d(TAG, "    Metadata [");
                            printMetadata(metadata, "      ");
                            Log.d(TAG, "    ]");
                            break;
                        }
                    }
                }
                Log.d(TAG, "  ]");
            }
        }
        TrackGroupArray unassociatedTrackGroups = currentMappedTrackInfo.getUnassociatedTrackGroups();
        if (unassociatedTrackGroups.length > 0) {
            Log.d(TAG, "  Renderer:None [");
            for (i = 0; i < unassociatedTrackGroups.length; i++) {
                Log.d(TAG, "    Group:" + i + " [");
                TrackGroup trackGroup2 = unassociatedTrackGroups.get(i);
                for (i2 = 0; i2 < trackGroup2.length; i2++) {
                    String trackStatusString2 = getTrackStatusString(false);
                    Log.d(TAG, "      " + trackStatusString2 + " Track:" + i2 + ", " + Format.toLogString(trackGroup2.getFormat(i2)) + ", supported=" + getFormatSupportString(0));
                }
                Log.d(TAG, "    ]");
            }
            Log.d(TAG, "  ]");
        }
        Log.d(TAG, "]");
    }

    public void onMetadata(Metadata metadata) {
        Log.d(TAG, "onMetadata [");
        printMetadata(metadata, "  ");
        Log.d(TAG, "]");
    }

    public void onAudioEnabled(DecoderCounters decoderCounters) {
        Log.d(TAG, "audioEnabled [" + getSessionTimeString() + "]");
    }

    public void onAudioSessionId(int i) {
        Log.d(TAG, "audioSessionId [" + i + "]");
    }

    public void onAudioDecoderInitialized(String str, long j, long j2) {
        Log.d(TAG, "audioDecoderInitialized [" + getSessionTimeString() + ", " + str + "]");
    }

    public void onAudioInputFormatChanged(Format format) {
        Log.d(TAG, "audioFormatChanged [" + getSessionTimeString() + ", " + Format.toLogString(format) + "]");
    }

    public void onAudioDisabled(DecoderCounters decoderCounters) {
        Log.d(TAG, "audioDisabled [" + getSessionTimeString() + "]");
    }

    public void onAudioTrackUnderrun(int i, long j, long j2) {
        printInternalError("audioTrackUnderrun [" + i + ", " + j + ", " + j2 + "]", null);
    }

    public void onVideoEnabled(DecoderCounters decoderCounters) {
        Log.d(TAG, "videoEnabled [" + getSessionTimeString() + "]");
    }

    public void onVideoDecoderInitialized(String str, long j, long j2) {
        Log.d(TAG, "videoDecoderInitialized [" + getSessionTimeString() + ", " + str + "]");
    }

    public void onVideoInputFormatChanged(Format format) {
        Log.d(TAG, "videoFormatChanged [" + getSessionTimeString() + ", " + Format.toLogString(format) + "]");
    }

    public void onVideoDisabled(DecoderCounters decoderCounters) {
        Log.d(TAG, "videoDisabled [" + getSessionTimeString() + "]");
    }

    public void onDroppedFrames(int i, long j) {
        Log.d(TAG, "droppedFrames [" + getSessionTimeString() + ", " + i + "]");
    }

    public void onVideoSizeChanged(int i, int i2, int i3, float f) {
    }

    public void onRenderedFirstFrame(Surface surface) {
        Log.d(TAG, "renderedFirstFrame [" + surface + "]");
    }

    public void onDrmSessionManagerError(Exception exception) {
        printInternalError("drmSessionManagerError", exception);
    }

    public void onDrmKeysRestored() {
        Log.d(TAG, "drmKeysRestored [" + getSessionTimeString() + "]");
    }

    public void onDrmKeysRemoved() {
        Log.d(TAG, "drmKeysRemoved [" + getSessionTimeString() + "]");
    }

    public void onDrmKeysLoaded() {
        Log.d(TAG, "drmKeysLoaded [" + getSessionTimeString() + "]");
    }

    public void onLoadError(IOException iOException) {
        printInternalError("loadError", iOException);
    }

    public void onLoadStarted(DataSpec dataSpec, int i, int i2, Format format, int i3, Object obj, long j, long j2, long j3) {
        if (this.mLastBytesLoadedTime == 0) {
            this.mLastBytesLoadedTime = System.currentTimeMillis();
        }
    }

    public void onLoadError(DataSpec dataSpec, int i, int i2, Format format, int i3, Object obj, long j, long j2, long j3, long j4, long j5, IOException iOException, boolean z) {
        printInternalError("loadError", iOException);
    }

    public void onLoadCanceled(DataSpec dataSpec, int i, int i2, Format format, int i3, Object obj, long j, long j2, long j3, long j4, long j5) {
    }

    public void onLoadCompleted(DataSpec dataSpec, int i, int i2, Format format, int i3, Object obj, long j, long j2, long j3, long j4, long j5) {
        long currentTimeMillis = System.currentTimeMillis();
        if (this.mLastBytesLoadedTime != 0) {
            logBytesLoadedInSeconds(j5, (float) ((currentTimeMillis - this.mLastBytesLoadedTime) / 1000));
            this.mLastBytesLoadedTime = currentTimeMillis;
        }
    }

    private void logBytesLoadedInSeconds(long j, float f) {
        this.mBytesLoaded += j;
        this.mBytesLoadedSeconds = (long) (((float) this.mBytesLoadedSeconds) + f);
    }

    public int getObservedBitrate() {
        if (this.mBytesLoadedSeconds == 0) {
            return 0;
        }
        double d = ((double) (this.mBytesLoaded / this.mBytesLoadedSeconds)) * 8.0d;
        Log.d(TAG, " mBytesLoaded " + this.mBytesLoaded + " in " + this.mBytesLoadedSeconds + " seconds (" + ((int) d) + " b/s indicated ");
        return (int) d;
    }

    public void onUpstreamDiscarded(int i, long j, long j2) {
    }

    public void onDownstreamFormatChanged(int i, Format format, int i2, Object obj, long j) {
    }

    private void printInternalError(String str, Exception exception) {
        Log.e(TAG, "internalError [" + getSessionTimeString() + ", " + str + "]", exception);
    }

    private void printMetadata(Metadata metadata, String str) {
        for (int i = 0; i < metadata.length(); i++) {
            Entry entry = metadata.get(i);
            if (entry instanceof TextInformationFrame) {
                TextInformationFrame textInformationFrame = (TextInformationFrame) entry;
                Log.d(TAG, str + String.format("%s: value=%s", new Object[]{textInformationFrame.id, textInformationFrame.value}));
            } else if (entry instanceof UrlLinkFrame) {
                UrlLinkFrame urlLinkFrame = (UrlLinkFrame) entry;
                Log.d(TAG, str + String.format("%s: url=%s", new Object[]{urlLinkFrame.id, urlLinkFrame.url}));
            } else if (entry instanceof PrivFrame) {
                PrivFrame privFrame = (PrivFrame) entry;
                Log.d(TAG, str + String.format("%s: owner=%s", new Object[]{privFrame.id, privFrame.owner}));
            } else if (entry instanceof GeobFrame) {
                GeobFrame geobFrame = (GeobFrame) entry;
                Log.d(TAG, str + String.format("%s: mimeType=%s, filename=%s, description=%s", new Object[]{geobFrame.id, geobFrame.mimeType, geobFrame.filename, geobFrame.description}));
            } else if (entry instanceof ApicFrame) {
                ApicFrame apicFrame = (ApicFrame) entry;
                Log.d(TAG, str + String.format("%s: mimeType=%s, description=%s", new Object[]{apicFrame.id, apicFrame.mimeType, apicFrame.description}));
            } else if (entry instanceof CommentFrame) {
                CommentFrame commentFrame = (CommentFrame) entry;
                Log.d(TAG, str + String.format("%s: language=%s, description=%s", new Object[]{commentFrame.id, commentFrame.language, commentFrame.description}));
            } else if (entry instanceof Id3Frame) {
                Id3Frame id3Frame = (Id3Frame) entry;
                Log.d(TAG, str + String.format("%s", new Object[]{id3Frame.id}));
            } else if (entry instanceof EventMessage) {
                EventMessage eventMessage = (EventMessage) entry;
                Log.d(TAG, str + String.format("EMSG: scheme=%s, id=%d, value=%s", new Object[]{eventMessage.schemeIdUri, Long.valueOf(eventMessage.id), eventMessage.value}));
            }
        }
    }

    private String getSessionTimeString() {
        return getTimeString(SystemClock.elapsedRealtime() - this.startTimeMs);
    }

    private static String getTimeString(long j) {
        return j == -9223372036854775807L ? "?" : TIME_FORMAT.format((double) (((float) j) / 1000.0f));
    }

    private static String getStateString(int i) {
        switch (i) {
            case 1:
                return "I";
            case 2:
                return "B";
            case 3:
                return "R";
            case 4:
                return "E";
            default:
                return "?";
        }
    }

    private static String getFormatSupportString(int i) {
        switch (i) {
            case 0:
                return "NO";
            case 1:
                return "NO_UNSUPPORTED_TYPE";
            case 2:
                return "NO_EXCEEDS_CAPABILITIES";
            case 3:
                return "YES";
            default:
                return "?";
        }
    }

    private static String getAdaptiveSupportString(int i, int i2) {
        if (i < 2) {
            return "N/A";
        }
        switch (i2) {
            case 0:
                return "NO";
            case 4:
                return "YES_NOT_SEAMLESS";
            case 8:
                return "YES";
            default:
                return "?";
        }
    }

    private static String getTrackStatusString(TrackSelection trackSelection, TrackGroup trackGroup, int i) {
        boolean z = (trackSelection == null || trackSelection.getTrackGroup() != trackGroup || trackSelection.indexOf(i) == -1) ? false : true;
        return getTrackStatusString(z);
    }

    private static String getTrackStatusString(boolean z) {
        return z ? "[X]" : "[ ]";
    }
}
