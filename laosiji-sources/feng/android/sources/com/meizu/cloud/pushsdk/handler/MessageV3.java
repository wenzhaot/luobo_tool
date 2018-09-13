package com.meizu.cloud.pushsdk.handler;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.notification.MPushMessage;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import com.meizu.cloud.pushsdk.notification.model.AppIconSetting;
import com.meizu.cloud.pushsdk.notification.model.NotificationStyle;
import com.meizu.cloud.pushsdk.notification.model.TimeDisplaySetting;
import com.meizu.cloud.pushsdk.pushtracer.utils.Util;
import com.umeng.message.common.inter.ITagManager;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONException;
import org.json.JSONObject;

public class MessageV3 implements Parcelable {
    public static final Creator<MessageV3> CREATOR = new Creator<MessageV3>() {
        public MessageV3 createFromParcel(Parcel in) {
            return new MessageV3(in);
        }

        public MessageV3[] newArray(int size) {
            return new MessageV3[size];
        }
    };
    private static final String TAG = "Message_V3";
    private String activity;
    private int clickType;
    private String content;
    private String deviceId;
    private boolean isDiscard;
    private AdvanceSetting mAdvanceSetting;
    private AppIconSetting mAppIconSetting;
    private NotificationStyle mNotificationStyle;
    private TimeDisplaySetting mTimeDisplaySetting;
    private String notificationMessage;
    private String packageName;
    private Map<String, String> paramsMap = new HashMap();
    private String pushTimestamp;
    private String seqId;
    private String taskId;
    private String throughMessage;
    private String title;
    private String uploadDataPackageName;
    private String uriPackageName;
    private String webUrl;

    public enum CLICK_TYPE_DEFINE {
        CLICK_TYPE_LAUNCHER_ACTIVITY,
        CLICK_TYPE_ACTIVITY,
        CLICK_TYPE_WEB
    }

    public MessageV3(Parcel in) {
        this.taskId = in.readString();
        this.seqId = in.readString();
        this.deviceId = in.readString();
        this.title = in.readString();
        this.content = in.readString();
        this.packageName = in.readString();
        this.clickType = in.readInt();
        this.isDiscard = in.readByte() != (byte) 0;
        this.activity = in.readString();
        this.webUrl = in.readString();
        this.uriPackageName = in.readString();
        this.uploadDataPackageName = in.readString();
        this.pushTimestamp = in.readString();
        this.paramsMap = in.readHashMap(getClass().getClassLoader());
        this.throughMessage = in.readString();
        this.notificationMessage = in.readString();
        this.mAdvanceSetting = (AdvanceSetting) in.readParcelable(AdvanceSetting.class.getClassLoader());
        this.mAppIconSetting = (AppIconSetting) in.readParcelable(AppIconSetting.class.getClassLoader());
        this.mNotificationStyle = (NotificationStyle) in.readParcelable(NotificationStyle.class.getClassLoader());
        this.mTimeDisplaySetting = (TimeDisplaySetting) in.readParcelable(TimeDisplaySetting.class.getClassLoader());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flag) {
        parcel.writeString(this.taskId);
        parcel.writeString(this.seqId);
        parcel.writeString(this.deviceId);
        parcel.writeString(this.title);
        parcel.writeString(this.content);
        parcel.writeString(this.packageName);
        parcel.writeInt(this.clickType);
        parcel.writeByte((byte) (this.isDiscard ? 1 : 0));
        parcel.writeString(this.activity);
        parcel.writeString(this.webUrl);
        parcel.writeString(this.uriPackageName);
        parcel.writeString(this.uploadDataPackageName);
        parcel.writeString(this.pushTimestamp);
        parcel.writeMap(this.paramsMap);
        parcel.writeString(this.throughMessage);
        parcel.writeString(this.notificationMessage);
        parcel.writeParcelable(this.mAdvanceSetting, flag);
        parcel.writeParcelable(this.mAppIconSetting, flag);
        parcel.writeParcelable(this.mNotificationStyle, flag);
        parcel.writeParcelable(this.mTimeDisplaySetting, flag);
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public AdvanceSetting getmAdvanceSetting() {
        return this.mAdvanceSetting;
    }

    public void setmAdvanceSetting(AdvanceSetting mAdvanceSetting) {
        this.mAdvanceSetting = mAdvanceSetting;
    }

    public AppIconSetting getmAppIconSetting() {
        return this.mAppIconSetting;
    }

    public void setmAppIconSetting(AppIconSetting mAppIconSetting) {
        this.mAppIconSetting = mAppIconSetting;
    }

    public NotificationStyle getmNotificationStyle() {
        return this.mNotificationStyle;
    }

    public void setmNotificationStyle(NotificationStyle mNotificationStyle) {
        this.mNotificationStyle = mNotificationStyle;
    }

    public TimeDisplaySetting getmTimeDisplaySetting() {
        return this.mTimeDisplaySetting;
    }

    public void setmTimeDisplaySetting(TimeDisplaySetting mTimeDisplaySetting) {
        this.mTimeDisplaySetting = mTimeDisplaySetting;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getClickType() {
        return this.clickType;
    }

    public void setClickType(int clickType) {
        this.clickType = clickType;
    }

    public boolean isDiscard() {
        return this.isDiscard;
    }

    public void setIsDiscard(boolean isDiscard) {
        this.isDiscard = isDiscard;
    }

    public String getActivity() {
        return this.activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getWebUrl() {
        return this.webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getUriPackageName() {
        return this.uriPackageName;
    }

    public void setUriPackageName(String uriPackageName) {
        this.uriPackageName = uriPackageName;
    }

    public String getPushTimestamp() {
        return this.pushTimestamp;
    }

    public void setPushTimestamp(String pushTimestamp) {
        this.pushTimestamp = pushTimestamp;
    }

    public Map<String, String> getParamsMap() {
        return this.paramsMap;
    }

    public void setParamsMap(Map<String, String> paramsMap) {
        this.paramsMap = paramsMap;
    }

    public String getThroughMessage() {
        return this.throughMessage;
    }

    public void setThroughMessage(String throughMessage) {
        this.throughMessage = throughMessage;
    }

    public String getNotificationMessage() {
        return this.notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public String getSeqId() {
        return this.seqId;
    }

    public void setSeqId(String seqId) {
        this.seqId = seqId;
    }

    public String getUploadDataPackageName() {
        return this.uploadDataPackageName;
    }

    public void setUploadDataPackageName(String uploadDataPackageName) {
        this.uploadDataPackageName = uploadDataPackageName;
    }

    public static MessageV3 parse(String packageName, String deviceId, String taskId, MPushMessage pushMessage) {
        DebugLogger.e(TAG, "V2 message " + pushMessage);
        MessageV3 messageV3 = new MessageV3();
        messageV3.setPackageName(packageName);
        messageV3.setUploadDataPackageName(packageName);
        messageV3.setDeviceId(deviceId);
        messageV3.setTaskId(taskId);
        messageV3.setTitle(pushMessage.getTitle());
        messageV3.setContent(pushMessage.getContent());
        messageV3.setIsDiscard(ITagManager.STATUS_TRUE.equals(pushMessage.getIsDiscard()));
        messageV3.setClickType(Integer.valueOf(pushMessage.getClickType()).intValue());
        for (Entry<String, String> extra : pushMessage.getExtra().entrySet()) {
            String key = (String) extra.getKey();
            String value = (String) extra.getValue();
            if (PushConstants.INTENT_ACTIVITY_NAME.equals(key)) {
                messageV3.setActivity(value);
            }
            if ("url".equals(key)) {
                messageV3.setWebUrl(value);
            }
            if (PushConstants.URI_PACKAGE_NAME.equals(key)) {
                messageV3.setUriPackageName(value);
            }
            if (NotificationStyle.NOTIFICATION_STYLE.equals(key)) {
                messageV3.setmNotificationStyle(NotificationStyle.parse(value));
            }
            if ("as".equals(key)) {
                messageV3.setmAdvanceSetting(AdvanceSetting.parse(value));
            }
            if ("is".equals(key)) {
                messageV3.setmAppIconSetting(AppIconSetting.parse(value));
            }
            if ("ts".equals(key)) {
                messageV3.setmTimeDisplaySetting(TimeDisplaySetting.parse(value));
            }
        }
        messageV3.setParamsMap(pushMessage.getParams());
        String extraJson = Util.mapToJSONObject(pushMessage.getExtra()).toString();
        DebugLogger.e(TAG, "MessageV2 extra json is " + extraJson);
        if (!TextUtils.isEmpty(extraJson)) {
            messageV3.setNotificationMessage(extraJson);
        }
        DebugLogger.i(TAG, "parase V2 message to V3 message " + messageV3);
        return messageV3;
    }

    public static MessageV3 parse(String packageName, String uploadDataPackageName, String timestamp, String deviceId, String taskId, String seqId, String pushMessage) {
        MessageV3 messageV3 = parse(packageName, deviceId, taskId, seqId, pushMessage);
        messageV3.setUploadDataPackageName(uploadDataPackageName);
        messageV3.setPushTimestamp(timestamp);
        return messageV3;
    }

    public static MessageV3 parse(String packageName, String deviceId, String taskId, String seqId, String pushMessage) {
        MessageV3 messageV3 = parse(packageName, deviceId, taskId, pushMessage);
        messageV3.setSeqId(seqId);
        return messageV3;
    }

    public static MessageV3 parse(String packageName, String deviceId, String taskId, String pushMessage) {
        MessageV3 messageV3 = new MessageV3();
        messageV3.setNotificationMessage(pushMessage);
        messageV3.setTaskId(taskId);
        messageV3.setDeviceId(deviceId);
        messageV3.setPackageName(packageName);
        try {
            JSONObject pushMessageObj = new JSONObject(pushMessage).getJSONObject("data");
            if (!pushMessageObj.isNull("title")) {
                messageV3.setTitle(pushMessageObj.getString("title"));
            }
            if (!pushMessageObj.isNull("content")) {
                messageV3.setContent(pushMessageObj.getString("content"));
            }
            if (!pushMessageObj.isNull(PushConstants.IS_DISCARD)) {
                messageV3.setIsDiscard(pushMessageObj.getBoolean(PushConstants.IS_DISCARD));
            }
            if (!pushMessageObj.isNull(PushConstants.CLICK_TYPE)) {
                messageV3.setClickType(pushMessageObj.getInt(PushConstants.CLICK_TYPE));
            }
            if (!pushMessageObj.isNull(PushConstants.EXTRA)) {
                JSONObject extraObj = pushMessageObj.getJSONObject(PushConstants.EXTRA);
                if (!extraObj.isNull(NotificationStyle.NOTIFICATION_STYLE)) {
                    messageV3.setmNotificationStyle(NotificationStyle.parse(extraObj.getJSONObject(NotificationStyle.NOTIFICATION_STYLE)));
                }
                if (!extraObj.isNull("is")) {
                    messageV3.setmAppIconSetting(AppIconSetting.parse(extraObj.getJSONObject("is")));
                }
                if (!extraObj.isNull("as")) {
                    messageV3.setmAdvanceSetting(AdvanceSetting.parse(extraObj.getJSONObject("as")));
                }
                if (!extraObj.isNull("ts")) {
                    messageV3.setmTimeDisplaySetting(TimeDisplaySetting.parse(extraObj.getJSONObject("ts")));
                }
                if (!extraObj.isNull(PushConstants.INTENT_ACTIVITY_NAME)) {
                    messageV3.setActivity(extraObj.getString(PushConstants.INTENT_ACTIVITY_NAME));
                }
                if (!extraObj.isNull("url")) {
                    messageV3.setWebUrl(extraObj.getString("url"));
                }
                if (!extraObj.isNull("task_id") && TextUtils.isEmpty(taskId)) {
                    DebugLogger.e(TAG, "Flyme 4 notification message by through message or taskId is null");
                    messageV3.setTaskId(extraObj.getString("task_id"));
                }
                if (!extraObj.isNull(PushConstants.URI_PACKAGE_NAME)) {
                    messageV3.setUriPackageName(extraObj.getString(PushConstants.URI_PACKAGE_NAME));
                }
                if (!extraObj.isNull(PushConstants.PARAMS)) {
                    messageV3.setParamsMap(getParamsMap(extraObj.getJSONObject(PushConstants.PARAMS)));
                }
            }
        } catch (JSONException e) {
            DebugLogger.e(TAG, "parse message error " + e.getMessage());
        }
        return messageV3;
    }

    public static Map<String, String> getParamsMap(JSONObject paramsJsonObject) {
        Map<String, String> paramsMap = new HashMap();
        try {
            Iterator<String> extraIterator = paramsJsonObject.keys();
            while (extraIterator.hasNext()) {
                String key = (String) extraIterator.next();
                paramsMap.put(key, paramsJsonObject.getString(key));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return paramsMap;
    }

    public String toString() {
        return "MessageV3{taskId='" + this.taskId + '\'' + ", seqId='" + this.seqId + '\'' + ", deviceId='" + this.deviceId + '\'' + ", title='" + this.title + '\'' + ", content='" + this.content + '\'' + ", packageName='" + this.packageName + '\'' + ", clickType=" + this.clickType + ", isDiscard=" + this.isDiscard + ", activity='" + this.activity + '\'' + ", webUrl='" + this.webUrl + '\'' + ", uriPackageName='" + this.uriPackageName + '\'' + ", pushTimestamp='" + this.pushTimestamp + '\'' + ", uploadDataPackageName='" + this.uploadDataPackageName + '\'' + ", paramsMap=" + this.paramsMap + ", throughMessage='" + this.throughMessage + '\'' + ", notificationMessage='" + this.notificationMessage + '\'' + ", mAdvanceSetting=" + this.mAdvanceSetting + ", mAppIconSetting=" + this.mAppIconSetting + ", mNotificationStyle=" + this.mNotificationStyle + ", mTimeDisplaySetting=" + this.mTimeDisplaySetting + '}';
    }
}
