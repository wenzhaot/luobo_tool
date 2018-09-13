package android.databinding;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.io.Serializable;

public class ObservableFloat extends BaseObservable implements Parcelable, Serializable {
    public static final Creator<ObservableFloat> CREATOR = new Creator<ObservableFloat>() {
        public ObservableFloat createFromParcel(Parcel source) {
            return new ObservableFloat(source.readFloat());
        }

        public ObservableFloat[] newArray(int size) {
            return new ObservableFloat[size];
        }
    };
    static final long serialVersionUID = 1;
    private float mValue;

    public ObservableFloat(float value) {
        this.mValue = value;
    }

    public float get() {
        return this.mValue;
    }

    public void set(float value) {
        if (value != this.mValue) {
            this.mValue = value;
            notifyChange();
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.mValue);
    }
}
