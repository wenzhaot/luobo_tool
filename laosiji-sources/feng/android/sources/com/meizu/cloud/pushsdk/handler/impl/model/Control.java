package com.meizu.cloud.pushsdk.handler.impl.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import org.json.JSONException;
import org.json.JSONObject;

public class Control implements Parcelable {
    public static final String CACHED = "cached";
    public static final String CACHENUM = "cacheNum";
    public static final Creator<Control> CREATOR = new Creator<Control>() {
        public Control createFromParcel(Parcel in) {
            return new Control(in);
        }

        public Control[] newArray(int size) {
            return new Control[size];
        }
    };
    public static final String PUSH_TYPE = "pushType";
    public static final String TAG = "ctl";
    private int cacheNum;
    private int cached;
    private int pushType;

    protected Control(Parcel in) {
        this.pushType = in.readInt();
        this.cached = in.readInt();
        this.cacheNum = in.readInt();
    }

    public Control(JSONObject jsonObject) {
        parse(jsonObject);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.pushType);
        parcel.writeInt(this.cached);
        parcel.writeInt(this.cacheNum);
    }

    public int getPushType() {
        return this.pushType;
    }

    public void setPushType(int pushType) {
        this.pushType = pushType;
    }

    public int getCached() {
        return this.cached;
    }

    public void setCached(int cached) {
        this.cached = cached;
    }

    public int getCacheNum() {
        return this.cacheNum;
    }

    public void setCacheNum(int cacheNum) {
        this.cacheNum = cacheNum;
    }

    public static Control parse(String ctl) {
        JSONObject notificationObj = null;
        if (!TextUtils.isEmpty(ctl)) {
            try {
                notificationObj = new JSONObject(ctl);
            } catch (JSONException e) {
                DebugLogger.e(TAG, "parse json string error " + e.getMessage());
            }
        }
        return parse(notificationObj);
    }

    public static Control parse(JSONObject ctlobj) {
        Control control = new Control();
        if (ctlobj != null) {
            try {
                if (!ctlobj.isNull(PUSH_TYPE)) {
                    control.setPushType(ctlobj.getInt(PUSH_TYPE));
                }
                if (!ctlobj.isNull(CACHED)) {
                    control.setCached(ctlobj.getInt(CACHED));
                }
                if (!ctlobj.isNull(CACHENUM)) {
                    control.setCacheNum(ctlobj.getInt(CACHENUM));
                }
            } catch (JSONException e) {
                DebugLogger.e(TAG, " parse control message error " + e.getMessage());
            }
        } else {
            DebugLogger.e(TAG, "no control message can parse ");
        }
        return control;
    }

    public String toString() {
        return "Control{pushType=" + this.pushType + ", cached=" + this.cached + ", cacheNum=" + this.cacheNum + '}';
    }
}
