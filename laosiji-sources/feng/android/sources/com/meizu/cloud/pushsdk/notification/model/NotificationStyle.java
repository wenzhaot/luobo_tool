package com.meizu.cloud.pushsdk.notification.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import org.json.JSONException;
import org.json.JSONObject;

public class NotificationStyle implements Parcelable {
    public static final String BANNER_IMAGE_URL = "bi";
    public static final String BASE_STYLE = "bs";
    public static final Creator<NotificationStyle> CREATOR = new Creator<NotificationStyle>() {
        public NotificationStyle createFromParcel(Parcel in) {
            return new NotificationStyle(in);
        }

        public NotificationStyle[] newArray(int size) {
            return new NotificationStyle[size];
        }
    };
    public static final String EXPANDABLE_IMAGE_URL = "ei";
    public static final String EXPANDABLE_TEXT = "et";
    public static final String INNER_STYLE = "is";
    public static final String NOTIFICATION_STYLE = "ns";
    public static final String TAG = "notification_style";
    private String bannerImageUrl;
    private int baseStyle = 0;
    private String expandableImageUrl;
    private String expandableText;
    private int innerStyle = 0;

    public NotificationStyle(Parcel in) {
        this.baseStyle = in.readInt();
        this.innerStyle = in.readInt();
        this.expandableText = in.readString();
        this.expandableImageUrl = in.readString();
        this.bannerImageUrl = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.baseStyle);
        parcel.writeInt(this.innerStyle);
        parcel.writeString(this.expandableText);
        parcel.writeString(this.expandableImageUrl);
        parcel.writeString(this.bannerImageUrl);
    }

    public int getBaseStyle() {
        return this.baseStyle;
    }

    public void setBaseStyle(int baseStyle) {
        this.baseStyle = baseStyle;
    }

    public int getInnerStyle() {
        return this.innerStyle;
    }

    public void setInnerStyle(int innerStyle) {
        this.innerStyle = innerStyle;
    }

    public String getExpandableText() {
        return this.expandableText;
    }

    public void setExpandableText(String expandableText) {
        this.expandableText = expandableText;
    }

    public String getExpandableImageUrl() {
        return this.expandableImageUrl;
    }

    public void setExpandableImageUrl(String expandableImageUrl) {
        this.expandableImageUrl = expandableImageUrl;
    }

    public String getBannerImageUrl() {
        return this.bannerImageUrl;
    }

    public void setBannerImageUrl(String bannerImageUrl) {
        this.bannerImageUrl = bannerImageUrl;
    }

    public static NotificationStyle parse(String setting) {
        JSONObject notificationObj = null;
        if (!TextUtils.isEmpty(setting)) {
            try {
                notificationObj = new JSONObject(setting);
            } catch (JSONException e) {
                DebugLogger.e(TAG, "parse json string error " + e.getMessage());
            }
        }
        return parse(notificationObj);
    }

    public static NotificationStyle parse(JSONObject notificationObj) {
        NotificationStyle notificationStyle = new NotificationStyle();
        if (notificationObj != null) {
            try {
                if (!notificationObj.isNull(BASE_STYLE)) {
                    notificationStyle.setBaseStyle(notificationObj.getInt(BASE_STYLE));
                }
                if (!notificationObj.isNull("is")) {
                    notificationStyle.setInnerStyle(notificationObj.getInt("is"));
                }
                if (!notificationObj.isNull("et")) {
                    notificationStyle.setExpandableText(notificationObj.getString("et"));
                }
                if (!notificationObj.isNull("ei")) {
                    notificationStyle.setExpandableImageUrl(notificationObj.getString("ei"));
                }
                if (!notificationObj.isNull(BANNER_IMAGE_URL)) {
                    notificationStyle.setBannerImageUrl(notificationObj.getString(BANNER_IMAGE_URL));
                }
            } catch (JSONException e) {
                DebugLogger.e(TAG, "parse json obj error " + e.getMessage());
            }
        } else {
            DebugLogger.e(TAG, "no such tag notification_style");
        }
        return notificationStyle;
    }

    public String toString() {
        return "NotificationStyle{baseStyle=" + this.baseStyle + ", innerStyle=" + this.innerStyle + ", expandableText='" + this.expandableText + '\'' + ", expandableImageUrl='" + this.expandableImageUrl + '\'' + ", bannerImageUrl='" + this.bannerImageUrl + '\'' + '}';
    }
}
