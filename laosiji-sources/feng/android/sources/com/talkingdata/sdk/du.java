package com.talkingdata.sdk;

import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: td */
public class du extends dq {
    public du() {
        a("tid", (Object) au.a(ab.g));
        a("serialNo", au.h(ab.g) == null ? "" : au.h(ab.g));
    }

    public void b() {
        JSONArray z;
        JSONArray a = ck.a().a("AdID");
        if (a != null) {
            a("adId", (Object) au.g(ab.g));
            if (a.length() > 0) {
                ed.a().a("AdID", a);
            }
        } else {
            a("adId", this.b);
        }
        a = ck.a().a("IMEI", ed.a().a, ed.a().b);
        if (a != null) {
            try {
                z = bd.z(ab.g);
                JSONArray jSONArray = new JSONArray();
                if (z != null && z.length() > 0) {
                    JSONObject jSONObject = z.getJSONObject(0);
                    if (jSONObject.has("imei")) {
                        jSONArray.put(jSONObject.get("imei"));
                    }
                    if (z.length() == 2) {
                        jSONArray.put(z.getJSONObject(1).get("imei"));
                    }
                }
                a("imeis", (Object) jSONArray);
            } catch (Exception e) {
            }
            if (a.length() > 0) {
                ed.a().a("IMEI", a);
            }
        } else {
            a("imeis", this.b);
        }
        a = ck.a().a("MacAddress");
        if (a != null) {
            z = new JSONArray();
            z.put(au.f(ab.g));
            a("wifiMacs", (Object) z);
            if (a.length() > 0) {
                ed.a().a("MacAddress", a);
            }
        } else {
            a("wifiMacs", this.b);
        }
        a = ck.a().a("AndroidId");
        if (a != null) {
            a("androidId", (Object) au.b(ab.g));
            if (a.length() > 0) {
                ed.a().a("AndroidId", a);
                return;
            }
            return;
        }
        a("androidId", this.b);
    }
}
