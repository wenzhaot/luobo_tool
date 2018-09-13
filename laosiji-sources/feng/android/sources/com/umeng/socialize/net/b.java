package com.umeng.socialize.net;

import com.umeng.socialize.net.base.SocializeReseponse;
import org.json.JSONObject;

public class b extends SocializeReseponse {
    public String a;
    public String b;
    public String c;

    public b(Integer num, JSONObject jSONObject) {
        super(num, jSONObject);
        if (jSONObject.has("error") || jSONObject.has("error_code")) {
            this.c = jSONObject.optString("error", "");
            return;
        }
        this.a = jSONObject.optString("aid", "");
        this.b = jSONObject.optString("sub", "");
    }
}
