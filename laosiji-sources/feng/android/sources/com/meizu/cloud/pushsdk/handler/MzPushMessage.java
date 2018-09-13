package com.meizu.cloud.pushsdk.handler;

import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.notification.model.NotifyOption;
import com.meizu.cloud.pushsdk.pushtracer.utils.Util;
import java.io.Serializable;
import java.util.Map;

public class MzPushMessage implements Serializable {
    private static final String TAG = "MzPushMessage";
    private String content;
    private int notifyId;
    private int pushType;
    private String selfDefineContentString;
    private String taskId;
    private String title;

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

    public int getPushType() {
        return this.pushType;
    }

    public void setPushType(int pushType) {
        this.pushType = pushType;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getSelfDefineContentString() {
        return this.selfDefineContentString;
    }

    public void setSelfDefineContentString(String selfDefineContentString) {
        this.selfDefineContentString = selfDefineContentString;
    }

    public int getNotifyId() {
        return this.notifyId;
    }

    public void setNotifyId(int notifyId) {
        this.notifyId = notifyId;
    }

    public static MzPushMessage fromMessageV3(MessageV3 messageV3) {
        MzPushMessage mzPushMessage = new MzPushMessage();
        mzPushMessage.setTitle(messageV3.getTitle());
        mzPushMessage.setContent(messageV3.getContent());
        mzPushMessage.setTaskId(messageV3.getTaskId());
        mzPushMessage.setPushType(0);
        mzPushMessage.setNotifyId(NotifyOption.getNotifyId(messageV3));
        mzPushMessage.setSelfDefineContentString(selfDefineContentString(messageV3.getWebUrl(), messageV3.getParamsMap()));
        return mzPushMessage;
    }

    private static String selfDefineContentString(String uri, Map<String, String> map) {
        String selfJson = null;
        if (!TextUtils.isEmpty(uri)) {
            selfJson = uri;
        } else if (map != null) {
            selfJson = (String) map.get("sk");
            if (TextUtils.isEmpty(selfJson)) {
                selfJson = Util.mapToJSONObject(map).toString();
            }
        }
        DebugLogger.e(TAG, "self json " + selfJson);
        return selfJson;
    }

    public String toString() {
        return "MzPushMessage{title='" + this.title + '\'' + ", content='" + this.content + '\'' + ", pushType=" + this.pushType + ", taskId='" + this.taskId + '\'' + ", selfDefineContentString='" + this.selfDefineContentString + '\'' + ", notifyId=" + this.notifyId + '}';
    }
}
