package com.meizu.cloud.pushsdk.platform.message;

import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class BasicPushStatus implements Serializable {
    public static final String SUCCESS_CODE = "200";
    public static final String TAG = "BasicPushStatus";
    public String code;
    public String message;

    public abstract void parseValueData(JSONObject jSONObject) throws JSONException;

    public BasicPushStatus(String json) {
        JSONObject jsonObject = parse(json);
        if (jsonObject != null && "200".equals(this.code) && !jsonObject.isNull("value")) {
            try {
                parseValueData(jsonObject.getJSONObject("value"));
            } catch (JSONException e) {
                DebugLogger.e(TAG, "parse value data error " + e.getMessage() + " json " + json);
            }
        }
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    protected JSONObject parse(String message) {
        JSONException e;
        JSONObject jsonObject = null;
        if (TextUtils.isEmpty(message)) {
            return null;
        }
        try {
            JSONObject jsonObject2 = new JSONObject(message);
            if (jsonObject2 != null) {
                try {
                    if (!jsonObject2.isNull("code")) {
                        setCode(jsonObject2.getString("code"));
                    }
                    if (!jsonObject2.isNull("message")) {
                        setMessage(jsonObject2.getString("message"));
                    }
                } catch (JSONException e2) {
                    e = e2;
                    jsonObject = jsonObject2;
                    DebugLogger.e(TAG, "covert json error " + e.getMessage());
                    return jsonObject;
                }
            }
            return jsonObject2;
        } catch (JSONException e3) {
            e = e3;
            DebugLogger.e(TAG, "covert json error " + e.getMessage());
            return jsonObject;
        }
    }

    public String toString() {
        return "BasicPushStatus{code='" + this.code + '\'' + ", message='" + this.message + '\'' + '}';
    }
}
