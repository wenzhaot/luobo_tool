package com.talkingdata.sdk;

import java.util.Comparator;
import org.json.JSONObject;

/* compiled from: td */
final class be implements Comparator {
    be() {
    }

    public int compare(JSONObject jSONObject, JSONObject jSONObject2) {
        try {
            return jSONObject2.getInt("asuLevel") - jSONObject.getInt("asuLevel");
        } catch (Throwable th) {
            return 0;
        }
    }
}
