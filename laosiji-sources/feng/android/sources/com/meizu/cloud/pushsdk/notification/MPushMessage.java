package com.meizu.cloud.pushsdk.notification;

import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class MPushMessage implements Serializable {
    private static final String TAG = "MPushMessage";
    private String clickType;
    private String content;
    private Map<String, String> extra = new HashMap();
    private String isDiscard;
    private String notifyType;
    private String packageName;
    private Map<String, String> params = new HashMap();
    private String pushType;
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

    public String getNotifyType() {
        return this.notifyType;
    }

    public void setNotifyType(String notifyType) {
        this.notifyType = notifyType;
    }

    public Map<String, String> getExtra() {
        return this.extra;
    }

    public void setExtra(Map<String, String> extra) {
        this.extra = extra;
    }

    public String getClickType() {
        return this.clickType;
    }

    public void setClickType(String clickType) {
        this.clickType = clickType;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getPushType() {
        return this.pushType;
    }

    public void setPushType(String pushType) {
        this.pushType = pushType;
    }

    public String getIsDiscard() {
        return this.isDiscard;
    }

    public void setIsDiscard(String isDiscard) {
        this.isDiscard = isDiscard;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Map<String, String> getParams() {
        return this.params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public String toString() {
        return "MPushMessage{taskId='" + this.taskId + '\'' + ", pushType='" + this.pushType + '\'' + ", packageName='" + this.packageName + '\'' + ", title='" + this.title + '\'' + ", content='" + this.content + '\'' + ", notifyType='" + this.notifyType + '\'' + ", clickType='" + this.clickType + '\'' + ", isDiscard='" + this.isDiscard + '\'' + ", extra=" + this.extra + ", params=" + this.params + '}';
    }

    public static MPushMessage parsePushMessage(String pushType, String pushMessage, String packageName, String taskId) {
        MPushMessage mPushMessage = new MPushMessage();
        try {
            mPushMessage.setTaskId(taskId);
            mPushMessage.setPushType(pushType);
            mPushMessage.setPackageName(packageName);
            JSONObject pushMessageObj = new JSONObject(pushMessage).getJSONObject("data");
            if (!pushMessageObj.isNull("content")) {
                mPushMessage.setContent(pushMessageObj.getString("content"));
            }
            if (!pushMessageObj.isNull(PushConstants.IS_DISCARD)) {
                mPushMessage.setIsDiscard(pushMessageObj.getString(PushConstants.IS_DISCARD));
            }
            if (!pushMessageObj.isNull("title")) {
                mPushMessage.setTitle(pushMessageObj.getString("title"));
            }
            if (!pushMessageObj.isNull(PushConstants.CLICK_TYPE)) {
                mPushMessage.setClickType(pushMessageObj.getString(PushConstants.CLICK_TYPE));
            }
            if (!pushMessageObj.isNull(PushConstants.EXTRA)) {
                JSONObject extraArray = pushMessageObj.getJSONObject(PushConstants.EXTRA);
                if (extraArray != null) {
                    if (!extraArray.isNull(PushConstants.PARAMS)) {
                        JSONObject parametersJsonObject = null;
                        try {
                            parametersJsonObject = extraArray.getJSONObject(PushConstants.PARAMS);
                            if (parametersJsonObject != null) {
                                mPushMessage.setParams(getParamsMap(parametersJsonObject));
                            }
                            extraArray.remove(PushConstants.PARAMS);
                        } catch (JSONException e) {
                            DebugLogger.i(TAG, "parameter parse error message " + e.getMessage());
                            if (parametersJsonObject != null) {
                                mPushMessage.setParams(getParamsMap(parametersJsonObject));
                            }
                            extraArray.remove(PushConstants.PARAMS);
                        } catch (Throwable th) {
                            if (parametersJsonObject != null) {
                                mPushMessage.setParams(getParamsMap(parametersJsonObject));
                            }
                            extraArray.remove(PushConstants.PARAMS);
                        }
                    }
                    mPushMessage.setExtra(getParamsMap(extraArray));
                }
            }
        } catch (JSONException e2) {
            DebugLogger.i(TAG, "parse push message error " + e2.getMessage());
        }
        DebugLogger.i(TAG, " parsePushMessage " + mPushMessage);
        return mPushMessage;
    }

    private static Map<String, String> getParamsMap(JSONObject paramsJsonObject) {
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
}
