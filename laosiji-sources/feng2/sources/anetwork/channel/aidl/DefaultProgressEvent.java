package anetwork.channel.aidl;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import anetwork.channel.NetworkEvent.ProgressEvent;

/* compiled from: Taobao */
public class DefaultProgressEvent implements Parcelable, ProgressEvent {
    public static final Creator<DefaultProgressEvent> CREATOR = new Creator<DefaultProgressEvent>() {
        public DefaultProgressEvent createFromParcel(Parcel parcel) {
            return DefaultProgressEvent.readFromParcel(parcel);
        }

        public DefaultProgressEvent[] newArray(int i) {
            return new DefaultProgressEvent[i];
        }
    };
    private static final String TAG = "anet.DefaultProgressEvent";
    Object context;
    String desc;
    int index;
    byte[] out;
    int size;
    int total;

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String str) {
        this.desc = str;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int i) {
        this.size = i;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int i) {
        this.total = i;
    }

    public Object getContext() {
        return this.context;
    }

    public void setContext(Object obj) {
        this.context = obj;
    }

    public byte[] getBytedata() {
        return this.out;
    }

    public void setBytedata(byte[] bArr) {
        this.out = bArr;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int i) {
        this.index = i;
    }

    public String toString() {
        return "DefaultProgressEvent [index=" + this.index + ", size=" + this.size + ", total=" + this.total + ", desc=" + this.desc + "]";
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.index);
        parcel.writeInt(this.size);
        parcel.writeInt(this.total);
        parcel.writeString(this.desc);
        parcel.writeInt(this.out != null ? this.out.length : 0);
        parcel.writeByteArray(this.out);
    }

    public static DefaultProgressEvent readFromParcel(Parcel parcel) {
        DefaultProgressEvent defaultProgressEvent = new DefaultProgressEvent();
        try {
            defaultProgressEvent.index = parcel.readInt();
            defaultProgressEvent.size = parcel.readInt();
            defaultProgressEvent.total = parcel.readInt();
            defaultProgressEvent.desc = parcel.readString();
            int readInt = parcel.readInt();
            if (readInt > 0) {
                byte[] bArr = new byte[readInt];
                parcel.readByteArray(bArr);
                defaultProgressEvent.out = bArr;
            }
        } catch (Exception e) {
        }
        return defaultProgressEvent;
    }
}
