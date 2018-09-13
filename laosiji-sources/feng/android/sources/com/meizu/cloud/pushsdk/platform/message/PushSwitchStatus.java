package com.meizu.cloud.pushsdk.platform.message;

import com.meizu.cloud.pushsdk.constants.PushConstants;
import org.json.JSONException;
import org.json.JSONObject;

public class PushSwitchStatus extends BasicPushStatus {
    private String pushId;
    private boolean switchNotificationMessage;
    private boolean switchThroughMessage;

    public PushSwitchStatus(String pushStatus) {
        super(pushStatus);
    }

    public void parseValueData(JSONObject jsonObject) throws JSONException {
        boolean z = true;
        if (!jsonObject.isNull(PushConstants.KEY_PUSH_ID)) {
            setPushId(jsonObject.getString(PushConstants.KEY_PUSH_ID));
        }
        if (!jsonObject.isNull("barTypeSwitch")) {
            setSwitchNotificationMessage(jsonObject.getInt("barTypeSwitch") == 1);
        }
        if (!jsonObject.isNull("directTypeSwitch")) {
            if (jsonObject.getInt("directTypeSwitch") != 1) {
                z = false;
            }
            setSwitchThroughMessage(z);
        }
    }

    public boolean isSwitchNotificationMessage() {
        return this.switchNotificationMessage;
    }

    public void setSwitchNotificationMessage(boolean switchNotificationMessage) {
        this.switchNotificationMessage = switchNotificationMessage;
    }

    public boolean isSwitchThroughMessage() {
        return this.switchThroughMessage;
    }

    public void setSwitchThroughMessage(boolean switchThroughMessage) {
        this.switchThroughMessage = switchThroughMessage;
    }

    public String getPushId() {
        return this.pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public String toString() {
        return super.toString() + "PushSwitchStatus{switchNotificationMessage=" + this.switchNotificationMessage + ", switchThroughMessage=" + this.switchThroughMessage + ", pushId='" + this.pushId + '\'' + '}';
    }
}
