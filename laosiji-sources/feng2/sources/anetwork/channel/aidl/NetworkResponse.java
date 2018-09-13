package anetwork.channel.aidl;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import anet.channel.util.ALog;
import anet.channel.util.ErrorConstant;
import anetwork.channel.Response;
import anetwork.channel.statist.StatisticData;
import java.util.List;
import java.util.Map;

/* compiled from: Taobao */
public class NetworkResponse implements Parcelable, Response {
    public static final Creator<NetworkResponse> CREATOR = new Creator<NetworkResponse>() {
        public NetworkResponse createFromParcel(Parcel parcel) {
            return NetworkResponse.readFromParcel(parcel);
        }

        public NetworkResponse[] newArray(int i) {
            return new NetworkResponse[i];
        }
    };
    private static final String TAG = "anet.NetworkResponse";
    byte[] bytedata;
    private Map<String, List<String>> connHeadFields;
    private String desc;
    private Throwable error;
    private StatisticData statisticData;
    int statusCode;

    public void setStatusCode(int i) {
        this.statusCode = i;
        this.desc = ErrorConstant.getErrMsg(i);
    }

    public byte[] getBytedata() {
        return this.bytedata;
    }

    public void setBytedata(byte[] bArr) {
        this.bytedata = bArr;
    }

    public void setConnHeadFields(Map<String, List<String>> map) {
        this.connHeadFields = map;
    }

    public Map<String, List<String>> getConnHeadFields() {
        return this.connHeadFields;
    }

    public void setDesc(String str) {
        this.desc = str;
    }

    public String getDesc() {
        return this.desc;
    }

    public boolean isHttpSuccess() {
        return true;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("NetworkResponse [");
        stringBuilder.append("statusCode=").append(this.statusCode);
        stringBuilder.append(", desc=").append(this.desc);
        stringBuilder.append(", connHeadFields=").append(this.connHeadFields);
        stringBuilder.append(", bytedata=").append(this.bytedata != null ? new String(this.bytedata) : "");
        stringBuilder.append(", error=").append(this.error);
        stringBuilder.append(", statisticData=").append(this.statisticData).append("]");
        return stringBuilder.toString();
    }

    public NetworkResponse(int i) {
        this(i, null, null);
    }

    public NetworkResponse(int i, byte[] bArr, Map<String, List<String>> map) {
        this.statusCode = i;
        this.desc = ErrorConstant.getErrMsg(i);
        this.bytedata = bArr;
        this.connHeadFields = map;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public Throwable getError() {
        return this.error;
    }

    public void setError(Throwable th) {
        this.error = th;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.statusCode);
        parcel.writeString(this.desc);
        int i2 = 0;
        if (this.bytedata != null) {
            i2 = this.bytedata.length;
        }
        parcel.writeInt(i2);
        if (i2 > 0) {
            parcel.writeByteArray(this.bytedata);
        }
        parcel.writeMap(this.connHeadFields);
        if (this.statisticData != null) {
            parcel.writeSerializable(this.statisticData);
        }
    }

    public static NetworkResponse readFromParcel(Parcel parcel) {
        NetworkResponse networkResponse = new NetworkResponse();
        try {
            networkResponse.statusCode = parcel.readInt();
            networkResponse.desc = parcel.readString();
            int readInt = parcel.readInt();
            if (readInt > 0) {
                networkResponse.bytedata = new byte[readInt];
                parcel.readByteArray(networkResponse.bytedata);
            }
            networkResponse.connHeadFields = parcel.readHashMap(NetworkResponse.class.getClassLoader());
            try {
                networkResponse.statisticData = (StatisticData) parcel.readSerializable();
            } catch (Throwable th) {
                ALog.i(TAG, "[readFromParcel] source.readSerializable() error", null, new Object[0]);
            }
        } catch (Throwable e) {
            ALog.w(TAG, "[readFromParcel]", null, e, new Object[0]);
        }
        return networkResponse;
    }

    public void setStatisticData(StatisticData statisticData) {
        this.statisticData = statisticData;
    }

    public StatisticData getStatisticData() {
        return this.statisticData;
    }
}
