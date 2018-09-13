package com.umeng.socialize.net;

import com.umeng.socialize.net.utils.URequest.RequestMethod;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class h extends e {
    private static final String a = "https://api.weibo.com/2/users/show.json";

    public h(String str, String str2, String str3, String str4) {
        super(a);
        this.mMethod = RequestMethod.GET;
        this.mResponseClz = i.class;
        addStringParams("uid", str);
        addStringParams("appkey", str3);
        addStringParams("access_token", str2);
        addStringParams("oauth_sign", a(str4, a(), str2, str3));
        addStringParams("oauth_timestamp", a());
    }

    public Map<String, Object> buildParams() {
        Map<String, Object> hashMap = new HashMap();
        hashMap.putAll(this.mParams);
        return hashMap;
    }

    public String toGetUrl() {
        return generateGetURL(getBaseUrl(), buildParams());
    }

    public JSONObject toJson() {
        return null;
    }
}
