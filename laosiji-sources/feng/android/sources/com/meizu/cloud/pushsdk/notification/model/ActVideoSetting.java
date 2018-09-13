package com.meizu.cloud.pushsdk.notification.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import org.json.JSONException;
import org.json.JSONObject;

public class ActVideoSetting implements Parcelable {
    public static final String ACT_URL = "au";
    public static final String ACT_VIDEO_SETTING = "acts";
    public static final Creator<ActVideoSetting> CREATOR = new Creator<ActVideoSetting>() {
        public ActVideoSetting createFromParcel(Parcel in) {
            return new ActVideoSetting(in);
        }

        public ActVideoSetting[] newArray(int size) {
            return new ActVideoSetting[size];
        }
    };
    public static final String TAG = "ActVideoSetting";
    public static final String WIFI_DISPLAY = "wd";
    private String actUrl;
    private boolean wifiDisplay;

    protected ActVideoSetting(Parcel in) {
        this.wifiDisplay = in.readByte() != (byte) 0;
        this.actUrl = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (this.wifiDisplay ? 1 : 0));
        parcel.writeString(this.actUrl);
    }

    public boolean isWifiDisplay() {
        return this.wifiDisplay;
    }

    public void setWifiDisplay(boolean wifiDisplay) {
        this.wifiDisplay = wifiDisplay;
    }

    public String getActUrl() {
        return this.actUrl;
    }

    public void setActUrl(String actUrl) {
        this.actUrl = actUrl;
    }

    public String toString() {
        return "ActVideoSetting{wifiDisplay=" + this.wifiDisplay + ", actUrl='" + this.actUrl + '\'' + '}';
    }

    public static ActVideoSetting parse(String setting) {
        JSONObject actVideoObj = null;
        if (!TextUtils.isEmpty(setting)) {
            try {
                actVideoObj = new JSONObject(setting);
            } catch (JSONException e) {
                DebugLogger.e(TAG, "parse json string error " + e.getMessage());
            }
        }
        return parse(actVideoObj);
    }

    public static ActVideoSetting parse(JSONObject actVideoObj) {
        ActVideoSetting actVideoSetting = new ActVideoSetting();
        if (actVideoObj != null) {
            try {
                if (!actVideoObj.isNull(WIFI_DISPLAY)) {
                    actVideoSetting.setWifiDisplay(actVideoObj.getInt(WIFI_DISPLAY) != 0);
                }
                if (!actVideoObj.isNull(ACT_URL)) {
                    actVideoSetting.setActUrl(actVideoObj.getString(ACT_URL));
                }
            } catch (JSONException e) {
                DebugLogger.e(TAG, "parse json obj error " + e.getMessage());
            }
        } else {
            DebugLogger.e(TAG, "no such tag ActVideoSetting");
        }
        return actVideoSetting;
    }
}
