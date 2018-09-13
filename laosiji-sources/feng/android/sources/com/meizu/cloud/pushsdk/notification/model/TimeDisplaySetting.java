package com.meizu.cloud.pushsdk.notification.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import org.json.JSONException;
import org.json.JSONObject;

public class TimeDisplaySetting implements Parcelable {
    public static final Creator<TimeDisplaySetting> CREATOR = new Creator<TimeDisplaySetting>() {
        public TimeDisplaySetting createFromParcel(Parcel in) {
            return new TimeDisplaySetting(in);
        }

        public TimeDisplaySetting[] newArray(int size) {
            return new TimeDisplaySetting[size];
        }
    };
    public static final String END_SHOW_TIME = "et";
    public static final String START_SHOW_TIME = "st";
    public static final String TAG = "time_display_setting";
    public static final String TIME_DISPLAY = "td";
    public static final String TIME_DISPLAY_SETTING = "ts";
    private String endShowTime;
    private boolean isTimeDisplay;
    private String startShowTime;

    public TimeDisplaySetting(Parcel in) {
        this.isTimeDisplay = in.readByte() != (byte) 0;
        this.startShowTime = in.readString();
        this.endShowTime = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (this.isTimeDisplay ? 1 : 0));
        parcel.writeString(this.startShowTime);
        parcel.writeString(this.endShowTime);
    }

    public boolean isTimeDisplay() {
        return this.isTimeDisplay;
    }

    public void setIsTimeDisplay(boolean isTimeDisplay) {
        this.isTimeDisplay = isTimeDisplay;
    }

    public String getStartShowTime() {
        return this.startShowTime;
    }

    public void setStartShowTime(String startShowTime) {
        this.startShowTime = startShowTime;
    }

    public String getEndShowTime() {
        return this.endShowTime;
    }

    public void setEndShowTime(String endShowTime) {
        this.endShowTime = endShowTime;
    }

    public static TimeDisplaySetting parse(String setting) {
        JSONObject timedisplayObj = null;
        if (!TextUtils.isEmpty(setting)) {
            try {
                timedisplayObj = new JSONObject(setting);
            } catch (JSONException e) {
                DebugLogger.e(TAG, "parse json string error " + e.getMessage());
            }
        }
        return parse(timedisplayObj);
    }

    public static TimeDisplaySetting parse(JSONObject timedisplayObj) {
        TimeDisplaySetting timeDisplaySetting = new TimeDisplaySetting();
        if (timedisplayObj != null) {
            try {
                if (!timedisplayObj.isNull(TIME_DISPLAY)) {
                    timeDisplaySetting.setIsTimeDisplay(timedisplayObj.getInt(TIME_DISPLAY) != 0);
                }
                if (!timedisplayObj.isNull("st")) {
                    timeDisplaySetting.setStartShowTime(timedisplayObj.getString("st"));
                }
                if (!timedisplayObj.isNull("et")) {
                    timeDisplaySetting.setEndShowTime(timedisplayObj.getString("et"));
                }
            } catch (JSONException e) {
                DebugLogger.e(TAG, "parse json obj error " + e.getMessage());
            }
        } else {
            DebugLogger.e(TAG, "no such tag time_display_setting");
        }
        return timeDisplaySetting;
    }

    public String toString() {
        return "TimeDisplaySetting{isTimeDisplay=" + this.isTimeDisplay + ", startShowTime='" + this.startShowTime + '\'' + ", endShowTime='" + this.endShowTime + '\'' + '}';
    }
}
