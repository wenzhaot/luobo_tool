package com.alibaba.sdk.android.httpdns;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class p {
    private boolean enabled = true;
    private String[] f;

    p(String str) {
        boolean z = true;
        int i = 0;
        try {
            JSONObject jSONObject = new JSONObject(str);
            g.e("Schedule center response:" + jSONObject.toString());
            if (jSONObject.has("service_status")) {
                if (jSONObject.getString("service_status").equals("disable")) {
                    z = false;
                }
                this.enabled = z;
            }
            if (jSONObject.has("service_ip")) {
                JSONArray jSONArray = jSONObject.getJSONArray("service_ip");
                this.f = new String[jSONArray.length()];
                while (true) {
                    int i2 = i;
                    if (i2 < jSONArray.length()) {
                        this.f[i2] = (String) jSONArray.get(i2);
                        i = i2 + 1;
                    } else {
                        return;
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String[] c() {
        return this.f;
    }

    public boolean isEnabled() {
        return this.enabled;
    }
}
