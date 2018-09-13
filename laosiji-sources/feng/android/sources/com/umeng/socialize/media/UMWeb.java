package com.umeng.socialize.media;

import com.umeng.socialize.media.UMediaObject.MediaType;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.util.HashMap;
import java.util.Map;

public class UMWeb extends BaseMediaObject {
    public UMWeb(String str) {
        super(str);
    }

    public UMWeb(String str, String str2, String str3, UMImage uMImage) {
        this.a = str;
        setThumb(uMImage);
        this.d = str3;
        setTitle(str2);
    }

    public MediaType getMediaType() {
        return MediaType.WEBPAGE;
    }

    public Map<String, Object> toUrlExtraParams() {
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
        return "UMWEB [media_url=" + this.a + ", title=" + this.b + "" + "media_url=" + this.a + ", des=" + this.d + ", qzone_thumb=" + "]";
    }
}
