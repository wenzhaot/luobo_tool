package anetwork.channel.aidl;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

/* compiled from: Taobao */
public class ParcelableObject implements Parcelable {
    public static final Creator<ParcelableObject> CREATOR = new Creator<ParcelableObject>() {
        public ParcelableObject createFromParcel(Parcel parcel) {
            return new ParcelableObject(parcel);
        }

        public ParcelableObject[] newArray(int i) {
            return new ParcelableObject[i];
        }
    };
    private Object object;

    public ParcelableObject(Object obj) {
        this.object = obj;
    }

    public Object getObject() {
        return this.object;
    }

    ParcelableObject(Parcel parcel) {
        readFromParcel(parcel);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
    }

    ParcelableObject readFromParcel(Parcel parcel) {
        return new ParcelableObject();
    }
}
