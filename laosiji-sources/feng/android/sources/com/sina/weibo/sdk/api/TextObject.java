package com.sina.weibo.sdk.api;

import android.os.Parcel;
import android.os.Parcelable.Creator;

public class TextObject extends BaseMediaObject {
    public static final Creator<TextObject> CREATOR = new Creator() {
        public TextObject createFromParcel(Parcel parcel) {
            return new TextObject(parcel);
        }

        public TextObject[] newArray(int i) {
            return new TextObject[i];
        }
    };
    public String text;

    public TextObject(Parcel parcel) {
        this.text = parcel.readString();
    }

    public boolean checkArgs() {
        return (this.text == null || this.text.length() == 0 || this.text.length() > 1024) ? false : true;
    }

    public int describeContents() {
        return 0;
    }

    public int getObjType() {
        return 1;
    }

    protected BaseMediaObject toExtraMediaObject(String str) {
        return this;
    }

    protected String toExtraMediaString() {
        return "";
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.text);
    }
}
