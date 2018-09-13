package com.meizu.cloud.pushsdk.platform.message;

import com.meizu.cloud.pushsdk.constants.PushConstants;
import org.json.JSONException;
import org.json.JSONObject;

public class SubAliasStatus extends BasicPushStatus {
    private String alias;
    private String pushId;

    public SubAliasStatus(String json) {
        super(json);
    }

    public void parseValueData(JSONObject jsonObject) throws JSONException {
        if (!jsonObject.isNull(PushConstants.KEY_PUSH_ID)) {
            setPushId(jsonObject.getString(PushConstants.KEY_PUSH_ID));
        }
        if (!jsonObject.isNull("alias")) {
            setAlias(jsonObject.getString("alias"));
        }
    }

    public String getPushId() {
        return this.pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String toString() {
        return super.toString() + " SubAliasStatus{pushId='" + this.pushId + '\'' + ", alias='" + this.alias + '\'' + '}';
    }
}
