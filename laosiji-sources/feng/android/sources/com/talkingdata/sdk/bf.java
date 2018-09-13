package com.talkingdata.sdk;

import java.util.Comparator;
import org.json.JSONObject;

/* compiled from: td */
final class bf implements Comparator {
    bf() {
    }

    public int compare(JSONObject jSONObject, JSONObject jSONObject2) {
        try {
            return jSONObject.getInt("networkId") - jSONObject2.getInt("networkId");
        } catch (Throwable th) {
            return 0;
        }
    }
}
