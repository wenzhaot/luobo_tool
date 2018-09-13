package com.umeng.socialize.media;

import com.umeng.socialize.media.UMediaObject.MediaType;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.util.HashMap;
import java.util.Map;

public class UMVideo extends BaseMediaObject {
    private String f;
    private String g;
    private String h;
    private String i;
    private int j;

    public int getDuration() {
        return this.j;
    }

    public void setDuration(int i) {
        this.j = i;
    }

    public UMVideo(String str) {
        super(str);
    }

    public String getLowBandUrl() {
        return this.f;
    }

    public String getLowBandDataUrl() {
        return this.g;
    }

    public void setLowBandDataUrl(String str) {
        this.g = str;
    }

    public String getHighBandDataUrl() {
        return this.h;
    }

    public void setHighBandDataUrl(String str) {
        this.h = str;
    }

    public String getH5Url() {
        return this.i;
    }

    public void setH5Url(String str) {
        this.i = str;
    }

    public void setLowBandUrl(String str) {
        this.f = str;
    }

    public MediaType getMediaType() {
        return MediaType.VEDIO;
    }

    public final Map<String, Object> toUrlExtraParams() {
        Map<String, Object> hashMap = new HashMap();
        if (isUrlMedia()) {
            hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_FURL, this.a);
            hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_FTYPE, getMediaType());
        }
        return hashMap;
    }

    public byte[] toByte() {
        if (this.e != null) {
            return this.e.toByte();
        }
        return null;
    }

    public String toString() {
        return "UMVedio [media_url=" + this.a + ", qzone_title=" + this.b + ", qzone_thumb=" + "media_url=" + this.a + ", qzone_title=" + this.b + ", qzone_thumb=" + "]";
    }
}
