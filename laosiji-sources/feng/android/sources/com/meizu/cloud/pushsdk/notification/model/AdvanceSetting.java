package com.meizu.cloud.pushsdk.notification.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import org.json.JSONException;
import org.json.JSONObject;

public class AdvanceSetting implements Parcelable {
    public static final String ADVANCE_SETTING = "as";
    public static final String CLEAR_NOTIFICATION = "cn";
    public static final Creator<AdvanceSetting> CREATOR = new Creator<AdvanceSetting>() {
        public AdvanceSetting createFromParcel(Parcel in) {
            return new AdvanceSetting(in);
        }

        public AdvanceSetting[] newArray(int size) {
            return new AdvanceSetting[size];
        }
    };
    public static final String HEAD_UP_NOTIFICATION = "hn";
    public static final String NETWORK_TYPE = "it";
    public static final String NOTIFY_TYPE = "nt";
    public static final String TAG = "advance_setting";
    private boolean clearNotification = true;
    private boolean headUpNotification = true;
    private int netWorkType = 1;
    private NotifyType notifyType;

    public AdvanceSetting(Parcel in) {
        boolean z;
        boolean z2 = true;
        this.netWorkType = in.readInt();
        this.notifyType = (NotifyType) in.readParcelable(NotifyType.class.getClassLoader());
        if (in.readByte() != (byte) 0) {
            z = true;
        } else {
            z = false;
        }
        this.clearNotification = z;
        if (in.readByte() == (byte) 0) {
            z2 = false;
        }
        this.headUpNotification = z2;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flag) {
        int i;
        int i2 = 1;
        parcel.writeInt(this.netWorkType);
        parcel.writeParcelable(this.notifyType, flag);
        if (this.clearNotification) {
            i = 1;
        } else {
            i = 0;
        }
        parcel.writeByte((byte) i);
        if (!this.headUpNotification) {
            i2 = 0;
        }
        parcel.writeByte((byte) i2);
    }

    public int getNetWorkType() {
        return this.netWorkType;
    }

    public void setNetWorkType(int netWorkType) {
        this.netWorkType = netWorkType;
    }

    public NotifyType getNotifyType() {
        return this.notifyType;
    }

    public void setNotifyType(NotifyType notifyType) {
        this.notifyType = notifyType;
    }

    public boolean isClearNotification() {
        return this.clearNotification;
    }

    public void setClearNotification(boolean clearNotification) {
        this.clearNotification = clearNotification;
    }

    public boolean isHeadUpNotification() {
        return this.headUpNotification;
    }

    public void setHeadUpNotification(boolean headUpNotification) {
        this.headUpNotification = headUpNotification;
    }

    public static AdvanceSetting parse(String setting) {
        JSONObject advanceObj = null;
        if (!TextUtils.isEmpty(setting)) {
            try {
                advanceObj = new JSONObject(setting);
            } catch (JSONException e) {
                DebugLogger.e(TAG, "parse json string error " + e.getMessage());
            }
        }
        return parse(advanceObj);
    }

    public static AdvanceSetting parse(JSONObject advanceObj) {
        boolean z = true;
        AdvanceSetting advanceSetting = new AdvanceSetting();
        if (advanceObj != null) {
            try {
                if (!advanceObj.isNull(NETWORK_TYPE)) {
                    advanceSetting.setNetWorkType(advanceObj.getInt(NETWORK_TYPE));
                }
                if (!advanceObj.isNull("nt")) {
                    advanceSetting.setNotifyType(NotifyType.parse(advanceObj.getJSONObject("nt")));
                }
                if (!advanceObj.isNull(CLEAR_NOTIFICATION)) {
                    advanceSetting.setClearNotification(advanceObj.getInt(CLEAR_NOTIFICATION) != 0);
                }
                if (!advanceObj.isNull(HEAD_UP_NOTIFICATION)) {
                    if (advanceObj.getInt(HEAD_UP_NOTIFICATION) == 0) {
                        z = false;
                    }
                    advanceSetting.setHeadUpNotification(z);
                }
            } catch (JSONException e) {
                DebugLogger.e(TAG, "parse json obj error " + e.getMessage());
            }
        } else {
            DebugLogger.e(TAG, "no such tag advance_setting");
        }
        return advanceSetting;
    }

    public String toString() {
        return "AdvanceSetting{netWorkType=" + this.netWorkType + ", notifyType=" + this.notifyType + ", clearNotification=" + this.clearNotification + ", headUpNotification=" + this.headUpNotification + '}';
    }
}
