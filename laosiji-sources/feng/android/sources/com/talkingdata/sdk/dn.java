package com.talkingdata.sdk;

import java.util.Map;
import org.json.JSONObject;

/* compiled from: td */
public class dn extends dq {
    public dn(String str, String str2) {
        a("domain", (Object) str);
        a("name", (Object) str2);
    }

    public void setData(Map map) {
        if (map != null) {
            a("data", (Object) new JSONObject(map));
        }
    }
}
