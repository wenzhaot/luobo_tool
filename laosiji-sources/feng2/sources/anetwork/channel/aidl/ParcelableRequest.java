package anetwork.channel.aidl;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import anet.channel.request.BodyEntry;
import anet.channel.strategy.dispatch.DispatchConstants;
import anet.channel.util.ALog;
import anetwork.channel.Header;
import anetwork.channel.Param;
import anetwork.channel.Request;
import anetwork.channel.entity.BasicHeader;
import anetwork.channel.entity.StringParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: Taobao */
public class ParcelableRequest implements Parcelable {
    public static final Creator<ParcelableRequest> CREATOR = new Creator<ParcelableRequest>() {
        public ParcelableRequest createFromParcel(Parcel parcel) {
            return ParcelableRequest.readFromParcel(parcel);
        }

        public ParcelableRequest[] newArray(int i) {
            return new ParcelableRequest[i];
        }
    };
    private static final String TAG = "anet.ParcelableRequest";
    private String bizId;
    private BodyEntry bodyEntry;
    private String charset;
    private int connectTimeout;
    private Map<String, String> extProperties;
    private List<Header> headers = new ArrayList();
    private boolean isRedirect;
    private String method;
    private List<Param> params = new ArrayList();
    private int readTimeout;
    public long reqStartTime;
    private Request request;
    private int retryTime;
    private String seqNo;
    private String url;

    public ParcelableRequest(Request request) {
        this.request = request;
        if (request != null) {
            if (request.getURI() != null) {
                this.url = request.getURI().toString();
            } else if (request.getURL() != null) {
                this.url = request.getURL().toString();
            }
            this.retryTime = request.getRetryTime();
            this.charset = request.getCharset();
            this.isRedirect = request.getFollowRedirects();
            this.headers = request.getHeaders();
            this.method = request.getMethod();
            this.params = request.getParams();
            this.bodyEntry = request.getBodyEntry();
            this.connectTimeout = request.getConnectTimeout();
            this.readTimeout = request.getReadTimeout();
            this.bizId = request.getBizId();
            this.seqNo = request.getSeqNo();
            this.extProperties = request.getExtProperties();
        }
        this.reqStartTime = System.currentTimeMillis();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        if (this.request != null) {
            try {
                int i2;
                parcel.writeInt(this.request.getRetryTime());
                parcel.writeString(this.url.toString());
                parcel.writeString(this.request.getCharset());
                parcel.writeBooleanArray(new boolean[]{this.request.getFollowRedirects()});
                parcel.writeString(this.request.getMethod());
                List arrayList = new ArrayList();
                if (this.request.getHeaders() != null) {
                    for (i2 = 0; i2 < this.request.getHeaders().size(); i2++) {
                        if (this.request.getHeaders().get(i2) != null) {
                            arrayList.add(((Header) this.request.getHeaders().get(i2)).getName() + DispatchConstants.SIGN_SPLIT_SYMBOL + ((Header) this.request.getHeaders().get(i2)).getValue());
                        }
                    }
                }
                parcel.writeList(arrayList);
                arrayList = this.request.getParams();
                List arrayList2 = new ArrayList();
                if (arrayList != null) {
                    for (i2 = 0; i2 < arrayList.size(); i2++) {
                        Param param = (Param) arrayList.get(i2);
                        if (param != null) {
                            arrayList2.add(param.getKey() + DispatchConstants.SIGN_SPLIT_SYMBOL + param.getValue());
                        }
                    }
                }
                parcel.writeList(arrayList2);
                parcel.writeParcelable(this.bodyEntry, 0);
                parcel.writeLong(this.reqStartTime);
                parcel.writeInt(this.request.getConnectTimeout());
                parcel.writeInt(this.request.getReadTimeout());
                parcel.writeString(this.request.getBizId());
                parcel.writeString(this.request.getSeqNo());
                Map extProperties = this.request.getExtProperties();
                parcel.writeInt(extProperties == null ? 0 : 1);
                if (extProperties != null) {
                    parcel.writeMap(extProperties);
                }
            } catch (Throwable th) {
                ALog.w(TAG, "[writeToParcel]", null, th, new Object[0]);
            }
        }
    }

    public static ParcelableRequest readFromParcel(Parcel parcel) {
        ParcelableRequest parcelableRequest = new ParcelableRequest();
        try {
            int i;
            String str;
            int indexOf;
            parcelableRequest.retryTime = parcel.readInt();
            parcelableRequest.url = parcel.readString();
            parcelableRequest.charset = parcel.readString();
            boolean[] zArr = new boolean[1];
            parcel.readBooleanArray(zArr);
            parcelableRequest.isRedirect = zArr[0];
            parcelableRequest.method = parcel.readString();
            ArrayList arrayList = new ArrayList();
            parcel.readList(arrayList, ParcelableRequest.class.getClassLoader());
            if (arrayList != null) {
                for (i = 0; i < arrayList.size(); i++) {
                    str = (String) arrayList.get(i);
                    if (str != null) {
                        indexOf = str.indexOf(DispatchConstants.SIGN_SPLIT_SYMBOL);
                        if (!(indexOf == -1 || indexOf == str.length() - 1)) {
                            parcelableRequest.headers.add(new BasicHeader(str.substring(0, indexOf), str.substring(indexOf + 1)));
                        }
                    }
                }
            }
            List readArrayList = parcel.readArrayList(ParcelableRequest.class.getClassLoader());
            if (readArrayList != null) {
                for (i = 0; i < readArrayList.size(); i++) {
                    str = (String) readArrayList.get(i);
                    if (str != null) {
                        indexOf = str.indexOf(DispatchConstants.SIGN_SPLIT_SYMBOL);
                        if (!(indexOf == -1 || indexOf == str.length() - 1)) {
                            parcelableRequest.params.add(new StringParam(str.substring(0, indexOf), str.substring(indexOf + 1)));
                        }
                    }
                }
            }
            parcelableRequest.bodyEntry = (BodyEntry) parcel.readParcelable(ParcelableRequest.class.getClassLoader());
            parcelableRequest.reqStartTime = parcel.readLong();
            parcelableRequest.connectTimeout = parcel.readInt();
            parcelableRequest.readTimeout = parcel.readInt();
            parcelableRequest.bizId = parcel.readString();
            parcelableRequest.seqNo = parcel.readString();
            if (parcel.readInt() != 0) {
                parcelableRequest.extProperties = parcel.readHashMap(ParcelableRequest.class.getClassLoader());
            }
        } catch (Throwable th) {
            ALog.w(TAG, "[readFromParcel]", null, th, new Object[0]);
        }
        return parcelableRequest;
    }

    public String getCharset() {
        return this.charset;
    }

    public String getMethod() {
        return this.method;
    }

    public String getURL() {
        return this.url;
    }

    public boolean getFollowRedirects() {
        return this.isRedirect;
    }

    public BodyEntry getBodyEntry() {
        return this.bodyEntry;
    }

    public int getRetryTime() {
        return this.retryTime;
    }

    public List<Param> getParams() {
        return this.params;
    }

    public List<Header> getHeaders() {
        return this.headers;
    }

    public int getConnectTimeout() {
        return this.connectTimeout;
    }

    public int getReadTimeout() {
        return this.readTimeout;
    }

    public String getBizId() {
        return this.bizId;
    }

    public String getSeqNo() {
        return this.seqNo;
    }

    public String getExtProperty(String str) {
        if (this.extProperties == null) {
            return null;
        }
        return (String) this.extProperties.get(str);
    }
}
