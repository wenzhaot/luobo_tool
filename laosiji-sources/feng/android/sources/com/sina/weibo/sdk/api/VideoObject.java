package com.sina.weibo.sdk.api;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class VideoObject extends BaseMediaObject {
    public static final Creator<VideoObject> CREATOR = new Creator() {
        public VideoObject createFromParcel(Parcel parcel) {
            return new VideoObject(parcel);
        }

        public VideoObject[] newArray(int i) {
            return new VideoObject[i];
        }
    };
    public static final String EXTRA_KEY_DEFAULTTEXT = "extra_key_defaulttext";
    public String dataHdUrl;
    public String dataUrl;
    public String defaultText;
    public int duration;
    public String h5Url;

    public VideoObject(Parcel parcel) {
        super(parcel);
        this.h5Url = parcel.readString();
        this.dataUrl = parcel.readString();
        this.dataHdUrl = parcel.readString();
        this.duration = parcel.readInt();
    }

    public boolean checkArgs() {
        return !super.checkArgs() ? false : (this.dataUrl == null || this.dataUrl.length() <= 512) ? (this.dataHdUrl == null || this.dataHdUrl.length() <= 512) && this.duration > 0 : false;
    }

    public int getObjType() {
        return 4;
    }

    protected BaseMediaObject toExtraMediaObject(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                this.defaultText = new JSONObject(str).optString("extra_key_defaulttext");
            } catch (JSONException e) {
            }
        }
        return this;
    }

    protected String toExtraMediaString() {
        try {
            JSONObject jSONObject = new JSONObject();
            if (!TextUtils.isEmpty(this.defaultText)) {
                jSONObject.put("extra_key_defaulttext", this.defaultText);
            }
            return jSONObject.toString();
        } catch (JSONException e) {
            return "";
        }
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.h5Url);
        parcel.writeString(this.dataUrl);
        parcel.writeString(this.dataHdUrl);
        parcel.writeInt(this.duration);
    }
}
