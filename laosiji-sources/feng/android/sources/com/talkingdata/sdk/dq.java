package com.talkingdata.sdk;

import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: td */
public abstract class dq {
    protected JSONObject b = new JSONObject();

    public Object a_() {
        return this.b;
    }

    protected void a(String str, Object obj) {
        if (obj != null) {
            try {
                if (!a(obj)) {
                    this.b.put(str, obj);
                }
            } catch (Throwable th) {
            }
        }
    }

    protected void a(String str, JSONObject jSONObject) {
        if (str != null) {
            try {
                if (jSONObject.has(str)) {
                    jSONObject.remove(str);
                }
            } catch (Throwable th) {
            }
        }
    }

    protected boolean a(Object obj) {
        if (obj instanceof JSONObject) {
            if (((JSONObject) obj).length() <= 0) {
                return true;
            }
        } else if ((obj instanceof JSONArray) && ((JSONArray) obj).length() <= 0) {
            return true;
        }
        return false;
    }
}
