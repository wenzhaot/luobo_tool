package com.meizu.cloud.pushsdk.platform.message;

import org.json.JSONException;
import org.json.JSONObject;

public class UnRegisterStatus extends BasicPushStatus {
    private boolean isUnRegisterSuccess;

    public UnRegisterStatus(String json) {
        super(json);
    }

    public void parseValueData(JSONObject jsonObject) throws JSONException {
        if (!jsonObject.isNull("result")) {
            setIsUnRegisterSuccess(jsonObject.getBoolean("result"));
        }
    }

    public boolean isUnRegisterSuccess() {
        return this.isUnRegisterSuccess;
    }

    public void setIsUnRegisterSuccess(boolean isUnRegisterSuccess) {
        this.isUnRegisterSuccess = isUnRegisterSuccess;
    }

    public String toString() {
        return super.toString() + " UnRegisterStatus{isUnRegisterSuccess=" + this.isUnRegisterSuccess + '}';
    }
}
