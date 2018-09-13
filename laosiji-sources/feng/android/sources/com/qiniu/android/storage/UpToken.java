package com.qiniu.android.storage;

import com.qiniu.android.utils.UrlSafeBase64;
import org.json.JSONException;
import org.json.JSONObject;

public final class UpToken {
    public static UpToken NULL = new UpToken("", "", "");
    public final String accessKey;
    private String returnUrl = null;
    public final String token;

    private UpToken(String returnUrl, String token, String accessKey) {
        this.returnUrl = returnUrl;
        this.token = token;
        this.accessKey = accessKey;
    }

    public static UpToken parse(String token) {
        try {
            String[] t = token.split(":");
            if (t.length != 3) {
                return NULL;
            }
            try {
                JSONObject obj = new JSONObject(new String(UrlSafeBase64.decode(t[2])));
                if (obj.optString("scope").equals("")) {
                    return NULL;
                }
                if (obj.optInt("deadline") == 0) {
                    return NULL;
                }
                return new UpToken(obj.optString("returnUrl"), token, t[0]);
            } catch (JSONException e) {
                return NULL;
            }
        } catch (Exception e2) {
            return NULL;
        }
    }

    public String toString() {
        return this.token;
    }

    public boolean hasReturnUrl() {
        return !this.returnUrl.equals("");
    }
}
