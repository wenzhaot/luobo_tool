package com.tencent.liteav.videoediter.a;

import android.annotation.TargetApi;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import com.tencent.ijk.media.player.misc.IMediaFormat;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.c.e;
import java.io.IOException;
import java.util.HashMap;

@TargetApi(16)
/* compiled from: TXMediaExtractor */
public class b {
    private MediaExtractor a;
    private MediaExtractor b;
    private HashMap<Integer, MediaFormat> c = new HashMap();
    private MediaFormat d;
    private MediaFormat e;
    private long f = 0;
    private boolean g = true;
    private boolean h = true;

    public synchronized void a(String str) throws IOException {
        synchronized (this) {
            f();
            this.a = new MediaExtractor();
            this.a.setDataSource(str);
            int trackCount = this.a.getTrackCount();
            for (int i = 0; i < trackCount; i++) {
                MediaFormat trackFormat = this.a.getTrackFormat(i);
                if (trackFormat != null) {
                    this.c.put(Integer.valueOf(i), trackFormat);
                    String string = trackFormat.getString(IMediaFormat.KEY_MIME);
                    if (string != null && string.startsWith("video")) {
                        this.d = trackFormat;
                        this.a.selectTrack(i);
                        this.g = false;
                    } else if (string != null && string.startsWith("audio")) {
                        this.e = trackFormat;
                        this.b = new MediaExtractor();
                        this.b.setDataSource(str);
                        this.b.selectTrack(i);
                        this.h = false;
                    }
                    TXCLog.d("TXMediaExtractor", "track index: " + i + ", format: " + trackFormat);
                    long j = trackFormat.getLong("durationUs");
                    if (this.f < j) {
                        this.f = j;
                    }
                }
            }
        }
    }

    public synchronized MediaFormat a() {
        return this.d;
    }

    public synchronized MediaFormat b() {
        return this.e;
    }

    public synchronized long c() {
        return this.f;
    }

    public synchronized void a(long j) {
        if (this.a != null) {
            this.a.seekTo(j, 0);
            this.g = false;
        }
        if (!(this.b == null || this.a == null)) {
            this.b.seekTo(this.a.getSampleTime(), 0);
            this.h = false;
        }
    }

    public synchronized long d() {
        long j;
        j = 0;
        if (this.a != null) {
            j = this.a.getSampleTime();
        }
        if (this.b != null && r0 > this.b.getSampleTime()) {
            j = this.b.getSampleTime();
        }
        return j;
    }

    public synchronized int a(e eVar) {
        int i = -1;
        synchronized (this) {
            if (eVar != null) {
                if (eVar.b() != null) {
                    int readSampleData;
                    while (true) {
                        MediaExtractor mediaExtractor;
                        if (this.g) {
                            mediaExtractor = this.b;
                        } else if (this.h) {
                            mediaExtractor = this.a;
                        } else if (this.a == null || this.b == null) {
                            mediaExtractor = null;
                        } else if (this.a.getSampleTime() > this.b.getSampleTime()) {
                            mediaExtractor = this.b;
                        } else {
                            mediaExtractor = this.a;
                        }
                        if (mediaExtractor == null) {
                            TXCLog.w("TXMediaExtractor", "extractor = null!");
                            if (!(eVar == null || eVar.b() == null)) {
                                eVar.d(0);
                                eVar.c(4);
                            }
                        } else {
                            readSampleData = mediaExtractor.readSampleData(eVar.b(), 0);
                            if (readSampleData < 0) {
                                if (mediaExtractor == this.a) {
                                    this.g = true;
                                } else {
                                    this.h = true;
                                }
                                if (this.g && this.h) {
                                    eVar.d(0);
                                    eVar.c(4);
                                    break;
                                }
                            } else {
                                long sampleTime = mediaExtractor.getSampleTime();
                                int sampleFlags = mediaExtractor.getSampleFlags();
                                i = mediaExtractor.getSampleTrackIndex();
                                if (i < this.c.size()) {
                                    MediaFormat mediaFormat = (MediaFormat) this.c.get(Integer.valueOf(i));
                                    if (mediaFormat != null) {
                                        eVar.a(mediaFormat.getString(IMediaFormat.KEY_MIME));
                                    }
                                }
                                eVar.a(sampleTime);
                                eVar.c(sampleFlags);
                                eVar.d(readSampleData);
                                eVar.b().position(0);
                                mediaExtractor.advance();
                            }
                        }
                    }
                    i = readSampleData;
                }
            }
            TXCLog.e("TXMediaExtractor", "frame input is invalid");
            if (!(eVar == null || eVar.b() == null)) {
                eVar.d(0);
                eVar.c(4);
            }
        }
        return i;
    }

    private synchronized void f() {
        if (this.a != null) {
            this.a.release();
            this.a = null;
        }
        if (this.b != null) {
            this.b.release();
            this.b = null;
        }
        this.c.clear();
        this.d = null;
        this.e = null;
        this.f = 0;
        this.g = true;
        this.h = true;
    }

    public synchronized void e() {
        f();
    }
}
