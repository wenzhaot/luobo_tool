package com.meizu.cloud.pushsdk.handler.impl.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.meizu.cloud.pushinternal.DebugLogger;
import org.json.JSONException;
import org.json.JSONObject;

public class Statics implements Parcelable {
    public static final Creator<Statics> CREATOR = new Creator<Statics>() {
        public Statics createFromParcel(Parcel in) {
            return new Statics(in);
        }

        public Statics[] newArray(int size) {
            return new Statics[size];
        }
    };
    public static final String PUSH_EXTRA = "pushExtra";
    public static final String TAG = "statics";
    public static final String TASK_ID = "taskId";
    public static final String TIME = "time";
    private String deviceId;
    private boolean pushExtra = false;
    private String seqId;
    private String taskId;
    private String time;

    protected Statics(Parcel in) {
        boolean z = false;
        this.taskId = in.readString();
        this.time = in.readString();
        if (in.readByte() != (byte) 0) {
            z = true;
        }
        this.pushExtra = z;
        this.deviceId = in.readString();
        this.seqId = in.readString();
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean getPushExtra() {
        return this.pushExtra;
    }

    public void setPushExtra(boolean pushExtra) {
        this.pushExtra = pushExtra;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getSeqId() {
        return this.seqId;
    }

    public void setSeqId(String seqId) {
        this.seqId = seqId;
    }

    public static Statics parse(JSONObject staticsObj) {
        Statics statics = new Statics();
        if (staticsObj != null) {
            try {
                if (!staticsObj.isNull(TASK_ID)) {
                    statics.setTaskId(staticsObj.getString(TASK_ID));
                }
                if (!staticsObj.isNull("time")) {
                    statics.setTime(staticsObj.getString("time"));
                }
                if (!staticsObj.isNull(PUSH_EXTRA)) {
                    statics.setPushExtra(staticsObj.getInt(PUSH_EXTRA) != 0);
                }
            } catch (JSONException e) {
                DebugLogger.e(TAG, " parse statics message error " + e.getMessage());
            }
        } else {
            DebugLogger.e(TAG, "no control statics can parse ");
        }
        return statics;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.taskId);
        parcel.writeString(this.time);
        parcel.writeByte((byte) (this.pushExtra ? 1 : 0));
        parcel.writeString(this.deviceId);
        parcel.writeString(this.seqId);
    }

    public String toString() {
        return "Statics{taskId='" + this.taskId + '\'' + ", time='" + this.time + '\'' + ", pushExtra=" + this.pushExtra + ", deviceId='" + this.deviceId + '\'' + ", seqId='" + this.seqId + '\'' + '}';
    }
}
