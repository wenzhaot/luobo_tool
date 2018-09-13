package com.tencent.liteav.f;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.text.TextUtils;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.videoediter.ffmpeg.a;

/* compiled from: TXMediaRetriever */
public class k {
    private MediaMetadataRetriever a = new MediaMetadataRetriever();
    private a b = new a();

    public void a(String str) {
        try {
            this.a.setDataSource(str);
        } catch (IllegalArgumentException e) {
            TXCLog.e("MediaMetadataRetrieverW", "set data source error , path = " + str);
            e.printStackTrace();
        }
        this.b.a(str);
    }

    public long a() {
        Object extractMetadata = this.a.extractMetadata(9);
        if (!TextUtils.isEmpty(extractMetadata)) {
            return Long.parseLong(extractMetadata);
        }
        TXCLog.e("MediaMetadataRetrieverW", "getDuration error: duration is empty,use ff to get!");
        return c() > b() ? c() : b();
    }

    public long b() {
        return this.b.g();
    }

    public long c() {
        return this.b.e();
    }

    public int d() {
        Object extractMetadata = this.a.extractMetadata(19);
        if (!TextUtils.isEmpty(extractMetadata)) {
            return Integer.parseInt(extractMetadata);
        }
        TXCLog.e("MediaMetadataRetrieverW", "getHeight error: height is empty,use ff to get!");
        return this.b.b();
    }

    public int e() {
        Object extractMetadata = this.a.extractMetadata(18);
        if (!TextUtils.isEmpty(extractMetadata)) {
            return Integer.parseInt(extractMetadata);
        }
        TXCLog.e("MediaMetadataRetrieverW", "getHeight error: height is empty,use ff to get!");
        return this.b.a();
    }

    public float f() {
        return this.b.c();
    }

    public long g() {
        return this.b.d();
    }

    public int h() {
        return this.b.f();
    }

    public Bitmap a(long j) {
        return this.a.getFrameAtTime(j, 3);
    }

    public Bitmap i() {
        return this.a.getFrameAtTime();
    }

    public void j() {
        this.a.release();
    }
}
