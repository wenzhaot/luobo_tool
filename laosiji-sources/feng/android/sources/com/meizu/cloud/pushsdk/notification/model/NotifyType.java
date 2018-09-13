package com.meizu.cloud.pushsdk.notification.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import org.json.JSONException;
import org.json.JSONObject;

public class NotifyType implements Parcelable {
    public static final Creator<NotifyType> CREATOR = new Creator<NotifyType>() {
        public NotifyType createFromParcel(Parcel in) {
            return new NotifyType(in);
        }

        public NotifyType[] newArray(int size) {
            return new NotifyType[size];
        }
    };
    public static final String LIGHTS = "l";
    public static final String NOTIFY_TYPE = "nt";
    public static final String SOUND = "s";
    public static final String TAG = "notify_type";
    public static final String VIBRATE = "v";
    boolean lights;
    boolean sound;
    boolean vibrate;

    public NotifyType(Parcel in) {
        boolean z;
        boolean z2 = true;
        if (in.readByte() != (byte) 0) {
            z = true;
        } else {
            z = false;
        }
        this.vibrate = z;
        if (in.readByte() != (byte) 0) {
            z = true;
        } else {
            z = false;
        }
        this.lights = z;
        if (in.readByte() == (byte) 0) {
            z2 = false;
        }
        this.sound = z2;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int i2;
        int i3 = 1;
        if (this.vibrate) {
            i2 = 1;
        } else {
            i2 = 0;
        }
        parcel.writeByte((byte) i2);
        if (this.lights) {
            i2 = 1;
        } else {
            i2 = 0;
        }
        parcel.writeByte((byte) i2);
        if (!this.sound) {
            i3 = 0;
        }
        parcel.writeByte((byte) i3);
    }

    public boolean isVibrate() {
        return this.vibrate;
    }

    public void setVibrate(boolean vibrate) {
        this.vibrate = vibrate;
    }

    public boolean isLights() {
        return this.lights;
    }

    public void setLights(boolean lights) {
        this.lights = lights;
    }

    public boolean isSound() {
        return this.sound;
    }

    public void setSound(boolean sound) {
        this.sound = sound;
    }

    public static NotifyType parse(String setting) {
        JSONObject notificationObj = null;
        if (!TextUtils.isEmpty(setting)) {
            try {
                notificationObj = new JSONObject(setting);
            } catch (JSONException e) {
                DebugLogger.e("notify_type", "parse json string error " + e.getMessage());
            }
        }
        return parse(notificationObj);
    }

    public static NotifyType parse(JSONObject notifyTypeObj) {
        boolean z = true;
        NotifyType notifyType = new NotifyType();
        if (notifyTypeObj != null) {
            try {
                if (!notifyTypeObj.isNull(VIBRATE)) {
                    notifyType.setVibrate(notifyTypeObj.getInt(VIBRATE) != 0);
                }
                if (!notifyTypeObj.isNull(LIGHTS)) {
                    boolean z2;
                    if (notifyTypeObj.getInt(LIGHTS) != 0) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    notifyType.setLights(z2);
                }
                if (!notifyTypeObj.isNull("s")) {
                    if (notifyTypeObj.getInt("s") == 0) {
                        z = false;
                    }
                    notifyType.setSound(z);
                }
            } catch (JSONException e) {
                DebugLogger.e("notify_type", "parse json obj error " + e.getMessage());
            }
        } else {
            DebugLogger.e("notify_type", "no such tag notify_type");
        }
        return notifyType;
    }

    public String toString() {
        return "NotifyType{vibrate=" + this.vibrate + ", lights=" + this.lights + ", sound=" + this.sound + '}';
    }
}
