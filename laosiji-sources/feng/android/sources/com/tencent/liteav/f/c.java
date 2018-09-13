package com.tencent.liteav.f;

import android.annotation.TargetApi;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMetadataRetriever;
import android.text.TextUtils;
import com.tencent.ijk.media.player.misc.IMediaFormat;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.c.e;
import com.tencent.liteav.videoediter.ffmpeg.a;
import com.tencent.ugc.TXVideoEditConstants;
import java.io.IOException;

@TargetApi(16)
/* compiled from: MediaExtractorWrapper */
public class c {
    private static final String a = c.class.getSimpleName();
    private static int h;
    private static int i;
    private a b;
    private MediaExtractor c;
    private MediaExtractor d;
    private MediaFormat e;
    private MediaFormat f;
    private long g;
    private int j;
    private long k;
    private String l;
    private boolean m;
    private int n;
    private int o;
    private int p;
    private int q;
    private int r;

    public c() {
        this.g = -1;
        this.m = false;
        this.b = new a();
    }

    public c(boolean z) {
        this.g = -1;
        this.m = z;
        this.b = new a();
    }

    public int a(String str) throws IOException {
        this.l = str;
        if (this.c != null) {
            this.c.release();
        }
        if (this.d != null) {
            this.d.release();
        }
        if (this.m) {
            this.d = new MediaExtractor();
            this.d.setDataSource(str);
        } else {
            this.d = new MediaExtractor();
            this.c = new MediaExtractor();
            this.c.setDataSource(str);
            this.d.setDataSource(str);
        }
        this.b.a(str);
        return q();
    }

    private int q() {
        int i;
        int trackCount = this.d.getTrackCount();
        TXCLog.i(a, " trackCount = " + trackCount);
        for (i = 0; i < trackCount; i++) {
            MediaFormat trackFormat = this.d.getTrackFormat(i);
            TXCLog.i(a, "prepareMediaFileInfo :" + trackFormat.toString());
            String string = trackFormat.getString(IMediaFormat.KEY_MIME);
            if (string.startsWith("video")) {
                h = i;
                this.e = trackFormat;
                if (this.c != null) {
                    this.c.selectTrack(i);
                }
            } else if (string.startsWith("audio")) {
                i = i;
                this.f = trackFormat;
                this.d.selectTrack(i);
            }
        }
        this.j = f();
        if (this.e != null) {
            int b = b();
            i = c();
            if (b <= i) {
                i = b;
            }
            if (i > 1080) {
                return TXVideoEditConstants.ERR_UNSUPPORT_LARGE_RESOLUTION;
            }
        }
        if (trackCount <= 1) {
            return -1001;
        }
        return 0;
    }

    public long a() {
        if (this.e == null) {
            return 0;
        }
        if (this.f == null) {
            try {
                if (this.k == 0) {
                    this.k = this.e.getLong("durationUs");
                    TXCLog.d(a, "mDuration = " + this.k);
                }
                return this.k;
            } catch (NullPointerException e) {
                TXCLog.d(a, "空指针异常");
                return 0;
            }
        }
        try {
            if (this.k == 0) {
                long j = this.e.getLong("durationUs");
                long j2 = this.f.getLong("durationUs");
                if (j <= j2) {
                    j = j2;
                }
                this.k = j;
                TXCLog.d(a, "mDuration = " + this.k);
            }
            return this.k;
        } catch (NullPointerException e2) {
            TXCLog.d(a, "空指针异常");
            return 0;
        }
    }

    public int b() {
        if (this.r != 0) {
            return this.r;
        }
        try {
            if (this.e == null) {
                return 0;
            }
            this.r = this.e.getInteger("width");
            return this.r;
        } catch (NullPointerException e) {
            return 0;
        }
    }

    public int c() {
        if (this.q != 0) {
            return this.q;
        }
        try {
            if (this.e == null) {
                return 0;
            }
            this.q = this.e.getInteger("height");
            return this.q;
        } catch (NullPointerException e) {
            return 0;
        }
    }

    public int d() {
        if (this.p != 0) {
            return this.p;
        }
        int i = 0;
        try {
            if (this.e != null) {
                i = this.e.getInteger("frame-rate");
            }
        } catch (NullPointerException e) {
            try {
                i = this.e.getInteger("video-framerate");
            } catch (NullPointerException e2) {
                i = 20;
            }
        }
        this.p = i;
        return this.p;
    }

    public int e() {
        return this.j;
    }

    public int f() {
        int i = 0;
        try {
            if (this.e != null) {
                i = this.e.getInteger("rotation-degrees");
            }
        } catch (NullPointerException e) {
            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
            mediaMetadataRetriever.setDataSource(this.l);
            Object extractMetadata = mediaMetadataRetriever.extractMetadata(24);
            if (TextUtils.isEmpty(extractMetadata)) {
                TXCLog.e(a, "getRotation error: rotation is empty,rotation have been reset to zero");
            } else {
                i = Integer.parseInt(extractMetadata);
            }
            mediaMetadataRetriever.release();
        }
        TXCLog.d(a, "mRotation=" + this.j + ",rotation=" + i);
        return i;
    }

    public int g() {
        if (this.o != 0) {
            return this.o;
        }
        try {
            if (this.f == null) {
                return 0;
            }
            this.o = this.f.getInteger("sample-rate");
            return this.o;
        } catch (NullPointerException e) {
            return 0;
        }
    }

    public int h() {
        if (this.n != 0) {
            return this.n;
        }
        try {
            if (this.f == null) {
                return 0;
            }
            this.n = this.f.getInteger("channel-count");
            return this.n;
        } catch (NullPointerException e) {
            return 0;
        }
    }

    public long i() {
        long j = 0;
        if (this.e == null) {
            return j;
        }
        try {
            return this.e.getLong("durationUs");
        } catch (Exception e) {
            return j;
        }
    }

    public e a(e eVar) {
        eVar.a(this.c.getSampleTime());
        int sampleTrackIndex = this.c.getSampleTrackIndex();
        eVar.a(sampleTrackIndex);
        eVar.c(this.c.getSampleFlags());
        eVar.d(this.c.readSampleData(eVar.b(), 0));
        eVar.b().position(0);
        eVar.f(d());
        eVar.e(e());
        eVar.g(g());
        eVar.h(h());
        eVar.j(b());
        eVar.k(c());
        eVar.a(false);
        if (this.g == -1 && sampleTrackIndex == l()) {
            this.g = eVar.e();
        }
        if (eVar.g() <= 0) {
            eVar.d(0);
            eVar.a(0);
            eVar.c(4);
        }
        return eVar;
    }

    public e b(e eVar) {
        eVar.a(this.d.getSampleTime());
        int sampleTrackIndex = this.d.getSampleTrackIndex();
        eVar.a(sampleTrackIndex);
        eVar.c(this.d.getSampleFlags());
        eVar.d(this.d.readSampleData(eVar.b(), 0));
        eVar.b().position(0);
        eVar.e(e());
        eVar.g(g());
        eVar.h(h());
        eVar.j(b());
        eVar.k(c());
        eVar.a(false);
        if (this.g == -1 && sampleTrackIndex == l()) {
            this.g = eVar.e();
        }
        if (eVar.g() <= 0) {
            eVar.d(0);
            eVar.a(0);
            eVar.c(4);
        }
        return eVar;
    }

    public MediaFormat j() {
        return this.e;
    }

    public MediaFormat k() {
        return this.f;
    }

    public int l() {
        return h;
    }

    public boolean c(e eVar) {
        if (eVar.f() == 4) {
            return true;
        }
        this.c.advance();
        return false;
    }

    public boolean d(e eVar) {
        if (eVar.f() == 4) {
            return true;
        }
        this.d.advance();
        return false;
    }

    public void a(long j) {
        this.c.seekTo(j, 0);
    }

    public void b(long j) {
        this.c.seekTo(j, 1);
    }

    public void c(long j) {
        this.d.seekTo(j, 0);
    }

    public void m() {
        if (this.c != null) {
            this.c.release();
        }
        if (this.d != null) {
            this.d.release();
        }
    }

    public long n() {
        return this.c.getSampleTime();
    }

    public long o() {
        return this.d.getSampleTime();
    }

    public long p() {
        return this.c.getSampleTime();
    }
}
