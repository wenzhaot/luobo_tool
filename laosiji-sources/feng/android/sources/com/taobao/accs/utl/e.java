package com.taobao.accs.utl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: Taobao */
public class e {

    /* compiled from: Taobao */
    public static class a {
        JSONObject a = new JSONObject();

        public a a(String str, String str2) {
            if (!(str2 == null || str == null)) {
                try {
                    this.a.put(str, str2);
                } catch (JSONException e) {
                }
            }
            return this;
        }

        public a a(String str, Integer num) {
            if (num != null) {
                try {
                    this.a.put(str, num);
                } catch (JSONException e) {
                }
            }
            return this;
        }

        public a a(String str, JSONArray jSONArray) {
            if (jSONArray != null) {
                try {
                    this.a.put(str, jSONArray);
                } catch (JSONException e) {
                }
            }
            return this;
        }

        public JSONObject a() {
            return this.a;
        }
    }

    public static String a(JSONObject jSONObject, String str, String str2) throws JSONException {
        if (jSONObject != null && jSONObject.has(str)) {
            return jSONObject.getString(str);
        }
        return str2;
    }
}
