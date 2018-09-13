package com.meizu.cloud.pushsdk.handler.impl.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.meizu.cloud.pushinternal.DebugLogger;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UploadLogMessage implements Parcelable {
    public static final Creator<UploadLogMessage> CREATOR = new Creator<UploadLogMessage>() {
        public UploadLogMessage createFromParcel(Parcel in) {
            return new UploadLogMessage(in);
        }

        public UploadLogMessage[] newArray(int size) {
            return new UploadLogMessage[size];
        }
    };
    public static final String MAX_SIZE = "max_size";
    private static final String TAG = "UploadLogMessage";
    public static final String UPLOAD_FILES = "upload_files";
    public static final String WIFI_UPLOAD = "wifi_upload";
    private ControlMessage controlMessage;
    private List<String> fileList;
    private int maxSize;
    private String uploadMessage;
    private boolean wifiUpload;

    public UploadLogMessage(String uploadMessage, String control, String deviceId, String seqId) {
        this.uploadMessage = uploadMessage;
        try {
            JSONObject jsonObject = new JSONObject(uploadMessage);
            if (!jsonObject.isNull(MAX_SIZE)) {
                this.maxSize = jsonObject.getInt(MAX_SIZE);
            }
            if (!jsonObject.isNull(WIFI_UPLOAD)) {
                this.wifiUpload = jsonObject.getBoolean(WIFI_UPLOAD);
            }
            if (!jsonObject.isNull(UPLOAD_FILES)) {
                JSONArray jsonArray = jsonObject.getJSONArray(UPLOAD_FILES);
                this.fileList = new ArrayList();
                for (int i = 0; i < jsonArray.length(); i++) {
                    this.fileList.add(jsonArray.getString(i));
                }
            }
        } catch (JSONException e) {
            DebugLogger.e(TAG, "parse upload message error " + e.getMessage());
        }
        this.controlMessage = new ControlMessage(control, deviceId, seqId);
    }

    protected UploadLogMessage(Parcel in) {
        this.maxSize = in.readInt();
        this.wifiUpload = in.readByte() != (byte) 0;
        this.fileList = in.createStringArrayList();
        this.controlMessage = (ControlMessage) in.readParcelable(ControlMessage.class.getClassLoader());
        this.uploadMessage = in.readString();
    }

    public int getMaxSize() {
        return this.maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public boolean isWifiUpload() {
        return this.wifiUpload;
    }

    public void setWifiUpload(boolean wifiUpload) {
        this.wifiUpload = wifiUpload;
    }

    public List<String> getFileList() {
        return this.fileList;
    }

    public void setFileList(List<String> fileList) {
        this.fileList = fileList;
    }

    public ControlMessage getControlMessage() {
        return this.controlMessage;
    }

    public void setControlMessage(ControlMessage controlMessage) {
        this.controlMessage = controlMessage;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.maxSize);
        parcel.writeByte((byte) (this.wifiUpload ? 1 : 0));
        parcel.writeStringList(this.fileList);
        parcel.writeParcelable(this.controlMessage, i);
        parcel.writeString(this.uploadMessage);
    }

    public String toString() {
        return "UploadLogMessage{maxSize=" + this.maxSize + ", wifiUpload=" + this.wifiUpload + ", fileList=" + this.fileList + ", controlMessage=" + this.controlMessage + ", uploadMessage='" + this.uploadMessage + '\'' + '}';
    }
}
