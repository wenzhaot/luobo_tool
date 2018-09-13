package com.talkingdata.sdk;

import com.meizu.cloud.pushsdk.constants.PushConstants;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: td */
public class ds extends dq {
    public static final String a = "accounts";

    public void b() {
        a(a, (Object) bn.d(ab.g));
    }

    public void c() {
        try {
            JSONObject jSONObject = (JSONObject) a_();
            if (jSONObject.has(a)) {
                JSONArray jSONArray = jSONObject.getJSONArray(a);
                for (int i = 0; i < jSONArray.length(); i++) {
                    if ("sim".equals(jSONArray.getJSONObject(i).getString("type"))) {
                        JSONArray a = ck.a().a("IMEI", ed.a().a, ed.a().b);
                        if (a != null) {
                            jSONArray.getJSONObject(i).put(PushConstants.EXTRA, bd.z(ab.g));
                            if (a.length() > 0) {
                                ed.a().a("IMEI", a);
                            }
                        } else {
                            JSONArray jSONArray2 = jSONArray.getJSONObject(i).getJSONArray(PushConstants.EXTRA);
                            if (jSONArray2 != null) {
                                for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
                                    a("imei", jSONArray2.getJSONObject(i2));
                                }
                            }
                        }
                    }
                }
            }
        } catch (Throwable th) {
        }
    }

    public void setUserAccount(dm dmVar) {
        if (dmVar != null && dmVar.a_() != null) {
            if (this.b.isNull(a)) {
                new JSONArray().put(dmVar.a_());
                a(a, dmVar.a_());
                return;
            }
            try {
                this.b.getJSONArray(a).put(dmVar.a_());
            } catch (Throwable e) {
                cs.postSDKError(e);
                e.printStackTrace();
            }
        }
    }
}
