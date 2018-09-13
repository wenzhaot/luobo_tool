package com.meizu.cloud.pushsdk.handler.impl.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import org.json.JSONException;
import org.json.JSONObject;

public class ControlMessage implements Parcelable {
    public static final Creator<ControlMessage> CREATOR = new Creator<ControlMessage>() {
        public ControlMessage createFromParcel(Parcel in) {
            return new ControlMessage(in);
        }

        public ControlMessage[] newArray(int size) {
            return new ControlMessage[size];
        }
    };
    public static final String TAG = "ControlMessage";
    private Control control;
    private String controlMessage;
    private Statics statics;

    public ControlMessage(String controlMessage, String deviceId, String seqId) {
        this.controlMessage = controlMessage;
        if (TextUtils.isEmpty(controlMessage)) {
            this.control = new Control();
            this.statics = new Statics();
            return;
        }
        try {
            JSONObject controlObj = new JSONObject(controlMessage);
            if (controlObj != null) {
                this.control = Control.parse(controlObj.getJSONObject(Control.TAG));
                this.statics = Statics.parse(controlObj.getJSONObject(Statics.TAG));
                this.statics.setDeviceId(deviceId);
                this.statics.setSeqId(seqId);
            }
        } catch (JSONException e) {
            this.control = new Control();
            this.statics = new Statics();
            DebugLogger.e(TAG, "parse control message error " + e.getMessage());
        }
    }

    protected ControlMessage(Parcel in) {
        this.controlMessage = in.readString();
        this.control = (Control) in.readParcelable(Control.class.getClassLoader());
        this.statics = (Statics) in.readParcelable(Statics.class.getClassLoader());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.controlMessage);
        parcel.writeParcelable(this.control, i);
        parcel.writeParcelable(this.statics, i);
    }

    public String getControlMessage() {
        return this.controlMessage;
    }

    public void setControlMessage(String controlMessage) {
        this.controlMessage = controlMessage;
    }

    public Control getControl() {
        return this.control;
    }

    public void setControl(Control control) {
        this.control = control;
    }

    public Statics getStatics() {
        return this.statics;
    }

    public void setStatics(Statics statics) {
        this.statics = statics;
    }

    public static ControlMessage parse(String control) {
        ControlMessage controlMessage = new ControlMessage();
        try {
            JSONObject controlObj = new JSONObject(control);
            if (controlObj != null) {
                controlMessage.setControl(Control.parse(controlObj.getJSONObject(Control.TAG)));
                controlMessage.setStatics(Statics.parse(controlObj.getJSONObject(Statics.TAG)));
            }
        } catch (Exception e) {
            DebugLogger.e(TAG, "parse control message error " + e.getMessage());
            controlMessage.setStatics(new Statics());
            controlMessage.setControl(new Control());
        }
        return controlMessage;
    }

    public String toString() {
        return "ControlMessage{controlMessage='" + this.controlMessage + '\'' + ", control=" + this.control + ", statics=" + this.statics + '}';
    }
}
