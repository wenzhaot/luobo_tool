package com.meizu.cloud.pushsdk.notification.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.handler.MessageV3;
import org.json.JSONException;
import org.json.JSONObject;

public class NotifyOption implements Parcelable {
    public static final Creator<NotifyOption> CREATOR = new Creator<NotifyOption>() {
        public NotifyOption createFromParcel(Parcel in) {
            return new NotifyOption(in);
        }

        public NotifyOption[] newArray(int size) {
            return new NotifyOption[size];
        }
    };
    private static final String NOTIFY_ID = "ni";
    private static final String NOTIFY_KEY = "nk";
    public static final String NOTIFY_OPTION = "no";
    public static final int NO_VALID_NOTIFY_ID = 0;
    private static final String TAG = "NotifyOption";
    private int notifyId = 0;
    private String notifyKey;

    protected NotifyOption(Parcel in) {
        this.notifyId = in.readInt();
        this.notifyKey = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public int getNotifyId() {
        return this.notifyId;
    }

    public void setNotifyId(int notifyId) {
        this.notifyId = notifyId;
    }

    public String getNotifyKey() {
        return this.notifyKey;
    }

    public void setNotifyKey(String notifyKey) {
        this.notifyKey = notifyKey;
    }

    public String toString() {
        return "NotifyOption{notifyId=" + this.notifyId + ", notifyKey='" + this.notifyKey + '\'' + '}';
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.notifyId);
        dest.writeString(this.notifyKey);
    }

    public static NotifyOption parse(String setting) {
        JSONObject notifyOptionObj = null;
        if (!TextUtils.isEmpty(setting)) {
            try {
                notifyOptionObj = new JSONObject(setting);
            } catch (JSONException e) {
                DebugLogger.e(TAG, "parse json string error " + e.getMessage());
            }
        }
        return parse(notifyOptionObj);
    }

    public static NotifyOption parse(JSONObject notifyTypeObj) {
        NotifyOption notifyOption = new NotifyOption();
        if (notifyTypeObj != null) {
            try {
                if (!notifyTypeObj.isNull(NOTIFY_ID)) {
                    notifyOption.setNotifyId(notifyTypeObj.getInt(NOTIFY_ID));
                }
                if (!notifyTypeObj.isNull(NOTIFY_KEY)) {
                    notifyOption.setNotifyKey(notifyTypeObj.getString(NOTIFY_KEY));
                }
            } catch (JSONException e) {
                DebugLogger.e(TAG, "parse json obj error " + e.getMessage());
            }
        } else {
            DebugLogger.e(TAG, "no such tag NotifyOption");
        }
        return notifyOption;
    }

    public static NotifyOption getNotifyOptionSetting(MessageV3 messageV3) {
        NotifyOption notifyOption = null;
        try {
            if (!TextUtils.isEmpty(messageV3.getNotificationMessage())) {
                notifyOption = parse(new JSONObject(messageV3.getNotificationMessage()).getJSONObject("data").getJSONObject(PushConstants.EXTRA).getJSONObject(NOTIFY_OPTION));
            }
        } catch (Exception e) {
            DebugLogger.e(TAG, "parse flyme NotifyOption setting error " + e.getMessage() + " so get from notificationMessage");
            notifyOption = getNotifyOptionSetting(messageV3.getNotificationMessage());
        }
        DebugLogger.i(TAG, "current notify option is " + notifyOption);
        return notifyOption;
    }

    private static NotifyOption getNotifyOptionSetting(String notificationMessage) {
        try {
            if (TextUtils.isEmpty(notificationMessage)) {
                return null;
            }
            return parse(new JSONObject(notificationMessage).getString(NOTIFY_OPTION));
        } catch (JSONException e) {
            DebugLogger.e(TAG, "parse notificationMessage error " + e.getMessage());
            return null;
        }
    }

    public static int getNotifyId(MessageV3 messageV3) {
        NotifyOption notifyOption = getNotifyOptionSetting(messageV3);
        if (notifyOption != null) {
            return notifyOption.getNotifyId();
        }
        return 0;
    }
}
