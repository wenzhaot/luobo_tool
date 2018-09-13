package com.meizu.cloud.pushsdk.platform.message;

import com.meizu.cloud.pushsdk.constants.PushConstants;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterStatus extends BasicPushStatus {
    private int expireTime;
    private String pushId;

    public RegisterStatus(String responseJson) {
        super(responseJson);
    }

    public void parseValueData(JSONObject jsonObject) throws JSONException {
        if (!jsonObject.isNull(PushConstants.KEY_PUSH_ID)) {
            setPushId(jsonObject.getString(PushConstants.KEY_PUSH_ID));
        }
        if (!jsonObject.isNull("expireTime")) {
            setExpireTime(jsonObject.getInt("expireTime"));
        }
    }

    public String getPushId() {
        return this.pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public int getExpireTime() {
        return this.expireTime;
    }

    public void setExpireTime(int expireTime) {
        this.expireTime = expireTime;
    }

    public String toString() {
        return super.toString() + "pushId='" + this.pushId + '\'' + ", Become invalid after " + this.expireTime + " seconds " + '}';
    }
}
