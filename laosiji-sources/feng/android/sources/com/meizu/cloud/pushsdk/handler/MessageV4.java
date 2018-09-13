package com.meizu.cloud.pushsdk.handler;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.notification.model.ActVideoSetting;
import org.json.JSONException;
import org.json.JSONObject;

public class MessageV4 extends MessageV3 {
    public static final Creator<MessageV3> CREATOR = new Creator<MessageV3>() {
        public MessageV4 createFromParcel(Parcel in) {
            return new MessageV4(in);
        }

        public MessageV4[] newArray(int size) {
            return new MessageV4[size];
        }
    };
    private static final String TAG = "MessageV4";
    private ActVideoSetting actVideoSetting;

    public MessageV4(Parcel in) {
        super(in);
        this.actVideoSetting = (ActVideoSetting) in.readParcelable(ActVideoSetting.class.getClassLoader());
    }

    public void writeToParcel(Parcel parcel, int flag) {
        super.writeToParcel(parcel, flag);
        parcel.writeParcelable(this.actVideoSetting, flag);
    }

    public ActVideoSetting getActVideoSetting() {
        return this.actVideoSetting;
    }

    public void setActVideoSetting(ActVideoSetting actVideoSetting) {
        this.actVideoSetting = actVideoSetting;
    }

    public String toString() {
        return "MessageV4{actVideoSetting=" + this.actVideoSetting + '}' + super.toString();
    }

    public static MessageV4 parse(MessageV3 messageV3) {
        MessageV4 messageV4 = new MessageV4();
        if (!TextUtils.isEmpty(messageV3.getNotificationMessage())) {
            try {
                JSONObject pushMessageObj = new JSONObject(messageV3.getNotificationMessage()).getJSONObject("data");
                if (!pushMessageObj.isNull(PushConstants.EXTRA)) {
                    JSONObject extraObj = pushMessageObj.getJSONObject(PushConstants.EXTRA);
                    if (!extraObj.isNull(ActVideoSetting.ACT_VIDEO_SETTING)) {
                        messageV4.setActVideoSetting(ActVideoSetting.parse(extraObj.getJSONObject(ActVideoSetting.ACT_VIDEO_SETTING)));
                    }
                }
            } catch (JSONException e) {
                DebugLogger.e(TAG, "parse messageV4 error " + e.getMessage());
            }
        }
        DebugLogger.i(TAG, "MessageV4 " + messageV4);
        return messageV4;
    }
}
