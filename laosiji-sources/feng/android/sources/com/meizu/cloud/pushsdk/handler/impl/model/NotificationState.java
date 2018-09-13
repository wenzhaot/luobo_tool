package com.meizu.cloud.pushsdk.handler.impl.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.meizu.cloud.pushsdk.handler.MessageV3;

public class NotificationState implements Parcelable {
    public static final Creator<NotificationState> CREATOR = new Creator<NotificationState>() {
        public NotificationState createFromParcel(Parcel in) {
            return new NotificationState(in);
        }

        public NotificationState[] newArray(int size) {
            return new NotificationState[size];
        }
    };
    public static final int STATE_NOTIFICATION_SHOW_ACCESS_DENY = -2;
    public static final int STATE_NOTIFICATION_SHOW_FLOAT = 1;
    public static final int STATE_NOTIFICATION_SHOW_INBOX = -1;
    public static final int STATE_NOTIFICATION_SHOW_NORMAL = 0;
    private MessageV3 messageV3;
    private int notificationId;
    private String notificationPkg;
    private int state;

    public NotificationState(MessageV3 messageV3) {
        this.messageV3 = messageV3;
    }

    protected NotificationState(Parcel in) {
        this.messageV3 = (MessageV3) in.readParcelable(MessageV3.class.getClassLoader());
        this.notificationPkg = in.readString();
        this.notificationId = in.readInt();
        this.state = in.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.messageV3, i);
        parcel.writeString(this.notificationPkg);
        parcel.writeInt(this.notificationId);
        parcel.writeInt(this.state);
    }

    public MessageV3 getMessageV3() {
        return this.messageV3;
    }

    public void setMessageV3(MessageV3 messageV3) {
        this.messageV3 = messageV3;
    }

    public String getNotificationPkg() {
        return this.notificationPkg;
    }

    public void setNotificationPkg(String notificationPkg) {
        this.notificationPkg = notificationPkg;
    }

    public int getNotificationId() {
        return this.notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String toString() {
        return "NotificationState{messageV3=" + this.messageV3 + ", notificationPkg='" + this.notificationPkg + '\'' + ", notificationId='" + this.notificationId + '\'' + ", state='" + this.state + '\'' + '}';
    }
}
