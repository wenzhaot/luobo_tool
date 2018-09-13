package com.umeng.socialize.net;

import com.umeng.socialize.net.utils.URequest.PostStyle;
import com.umeng.socialize.net.utils.URequest.RequestMethod;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class c extends e {
    public c(String str, String str2, String str3) {
        super("https://api.weibo.com/oauth2/revokeoauth2");
        this.mMethod = RequestMethod.POST;
        this.mResponseClz = d.class;
        this.postStyle = PostStyle.APPLICATION;
        addStringParams("oauth_sign", a(str3, a(), str2, str));
        addStringParams("oauth_timestamp", a());
        addStringParams("aid", str3);
        addStringParams("access_token", str2);
    }

    public Map<String, Object> buildParams() {
        return null;
    }

    public Map<String, Object> getBodyPair() {
        Map<String, Object> hashMap = new HashMap();
        hashMap.putAll(this.mParams);
        return hashMap;
    }

    public String toGetUrl() {
        return null;
    }

    public JSONObject toJson() {
        return null;
    }
}
