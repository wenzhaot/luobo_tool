package com.tencent.liteav.txcvodplayer.a;

import com.tencent.ijk.media.player.IjkMediaMeta;

/* compiled from: TXCVodCacheInfo */
public class a {
    String a;
    String b;
    String c;

    a(String str, String str2, String str3) {
        this.a = str2;
        this.b = str;
        this.c = str3;
    }

    public String a() {
        if ("mp4".equals(this.c)) {
            return c();
        }
        return null;
    }

    public String b() {
        if (IjkMediaMeta.IJKM_KEY_M3U8.equals(this.c)) {
            return c();
        }
        return null;
    }

    public String c() {
        return this.a + "/" + this.b;
    }

    public String d() {
        return this.b;
    }
}
