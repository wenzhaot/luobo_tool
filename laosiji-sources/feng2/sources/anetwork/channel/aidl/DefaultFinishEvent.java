package anetwork.channel.aidl;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import anet.channel.util.ErrorConstant;
import anetwork.channel.NetworkEvent.FinishEvent;
import anetwork.channel.statist.StatisticData;

/* compiled from: Taobao */
public class DefaultFinishEvent implements Parcelable, FinishEvent {
    public static final Creator<DefaultFinishEvent> CREATOR = new Creator<DefaultFinishEvent>() {
        public DefaultFinishEvent createFromParcel(Parcel parcel) {
            return DefaultFinishEvent.readFromParcel(parcel);
        }

        public DefaultFinishEvent[] newArray(int i) {
            return new DefaultFinishEvent[i];
        }
    };
    private static final String TAG = "anet.DefaultFinishEvent";
    int code;
    Object context;
    String desc;
    StatisticData statisticData;

    public Object getContext() {
        return this.context;
    }

    public void setContext(Object obj) {
        this.context = obj;
    }

    public int getHttpCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setCode(int i) {
        this.code = i;
    }

    public void setDesc(String str) {
        this.desc = str;
    }

    public void setStatisticData(StatisticData statisticData) {
        this.statisticData = statisticData;
    }

    public StatisticData getStatisticData() {
        return this.statisticData;
    }

    public DefaultFinishEvent(int i) {
        this(i, null, null);
    }

    public DefaultFinishEvent(int i, String str, StatisticData statisticData) {
        this.code = i;
        if (str == null) {
            str = ErrorConstant.getErrMsg(i);
        }
        this.desc = str;
        this.statisticData = statisticData;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("DefaultFinishEvent [");
        stringBuilder.append("code=").append(this.code);
        stringBuilder.append(", desc=").append(this.desc);
        stringBuilder.append(", context=").append(this.context);
        stringBuilder.append(", statisticData=").append(this.statisticData);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.code);
        parcel.writeString(this.desc);
        if (this.statisticData != null) {
            parcel.writeSerializable(this.statisticData);
        }
    }

    static DefaultFinishEvent readFromParcel(Parcel parcel) {
        DefaultFinishEvent defaultFinishEvent = new DefaultFinishEvent();
        try {
            defaultFinishEvent.code = parcel.readInt();
            defaultFinishEvent.desc = parcel.readString();
            try {
                defaultFinishEvent.statisticData = (StatisticData) parcel.readSerializable();
            } catch (Throwable th) {
            }
        } catch (Throwable th2) {
        }
        return defaultFinishEvent;
    }
}
