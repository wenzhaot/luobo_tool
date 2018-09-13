package com.umeng.socialize.net;

import com.umeng.socialize.net.base.SocializeReseponse;
import com.umeng.socialize.utils.SocializeUtils;
import java.util.Map;
import org.json.JSONObject;

public class i extends SocializeReseponse {
    public Map<String, String> a;

    public i(Integer num, JSONObject jSONObject) {
        super(num, jSONObject);
        this.a = SocializeUtils.jsonToMap(jSONObject.toString());
    }
}
