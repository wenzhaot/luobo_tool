package android.databinding;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.io.Serializable;

public class ObservableShort extends BaseObservable implements Parcelable, Serializable {
    public static final Creator<ObservableShort> CREATOR = new Creator<ObservableShort>() {
        public ObservableShort createFromParcel(Parcel source) {
            return new ObservableShort((short) source.readInt());
        }

        public ObservableShort[] newArray(int size) {
            return new ObservableShort[size];
        }
    };
    static final long serialVersionUID = 1;
    private short mValue;

    public ObservableShort(short value) {
        this.mValue = value;
    }

    public short get() {
        return this.mValue;
    }

    public void set(short value) {
        if (value != this.mValue) {
            this.mValue = value;
            notifyChange();
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mValue);
    }
}
