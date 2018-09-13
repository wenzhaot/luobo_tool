package com.umeng.socialize.net;

import com.umeng.socialize.net.base.SocializeReseponse;
import org.json.JSONObject;

public class g extends SocializeReseponse {
    public int a;
    public String b;

    public g(Integer num, JSONObject jSONObject) {
        super(num, jSONObject);
        this.a = jSONObject.optInt("code", -2);
        this.b = jSONObject.optString("data", "");
    }
}
