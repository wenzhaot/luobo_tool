package com.meizu.cloud.pushsdk.notification.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import org.json.JSONException;
import org.json.JSONObject;

public class AppIconSetting implements Parcelable {
    public static final String APP_ICON_SETTING = "is";
    public static final Creator<AppIconSetting> CREATOR = new Creator<AppIconSetting>() {
        public AppIconSetting createFromParcel(Parcel in) {
            return new AppIconSetting(in);
        }

        public AppIconSetting[] newArray(int size) {
            return new AppIconSetting[size];
        }
    };
    public static final String DEFAULT_LARGE_ICON = "di";
    public static final String LARGE_ICON_URL = "li";
    public static final String TAG = "app_icon_setting";
    private boolean defaultLargeIcon = true;
    private String largeIconUrl;

    public AppIconSetting(Parcel in) {
        boolean z = true;
        if (in.readByte() == (byte) 0) {
            z = false;
        }
        this.defaultLargeIcon = z;
        this.largeIconUrl = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (this.defaultLargeIcon ? 1 : 0));
        parcel.writeString(this.largeIconUrl);
    }

    public String getLargeIconUrl() {
        return this.largeIconUrl;
    }

    public void setLargeIconUrl(String largeIconUrl) {
        this.largeIconUrl = largeIconUrl;
    }

    public boolean isDefaultLargeIcon() {
        return this.defaultLargeIcon;
    }

    public void setDefaultLargeIcon(boolean defaultLargeIcon) {
        this.defaultLargeIcon = defaultLargeIcon;
    }

    public static AppIconSetting parse(String setting) {
        JSONObject appIconSetingObj = null;
        if (!TextUtils.isEmpty(setting)) {
            try {
                appIconSetingObj = new JSONObject(setting);
            } catch (JSONException e) {
                DebugLogger.e(TAG, "parse json string error " + e.getMessage());
            }
        }
        return parse(appIconSetingObj);
    }

    public static AppIconSetting parse(JSONObject appIconSetingObj) {
        AppIconSetting appIconSetting = new AppIconSetting();
        if (appIconSetingObj != null) {
            try {
                if (!appIconSetingObj.isNull("di")) {
                    appIconSetting.setDefaultLargeIcon(appIconSetingObj.getInt("di") != 0);
                }
                if (!appIconSetingObj.isNull(LARGE_ICON_URL)) {
                    appIconSetting.setLargeIconUrl(appIconSetingObj.getString(LARGE_ICON_URL));
                }
            } catch (JSONException e) {
                DebugLogger.e(TAG, "parse json obj error " + e.getMessage());
            }
        } else {
            DebugLogger.e(TAG, "no such tag app_icon_setting");
        }
        return appIconSetting;
    }

    public String toString() {
        return "AppIconSetting{defaultLargeIcon=" + this.defaultLargeIcon + ", largeIconUrl='" + this.largeIconUrl + '\'' + '}';
    }
}
