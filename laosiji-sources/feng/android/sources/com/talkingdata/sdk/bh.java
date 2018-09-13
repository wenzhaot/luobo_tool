package com.talkingdata.sdk;

import java.util.Comparator;
import org.json.JSONObject;

/* compiled from: td */
final class bh implements Comparator {
    bh() {
    }

    public int compare(JSONObject jSONObject, JSONObject jSONObject2) {
        try {
            return jSONObject2.getInt("level") - jSONObject.getInt("level");
        } catch (Throwable th) {
            return 0;
        }
    }
}
