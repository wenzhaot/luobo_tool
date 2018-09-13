package com.umeng.message.common.inter;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.util.Hashtable;
import java.util.List;
import org.json.JSONObject;

public interface ITagManager {
    public static final String FAIL = "fail";
    public static final String STATUS_FALSE = "false";
    public static final String STATUS_TRUE = "true";
    public static final String SUCCESS = "ok";

    public static class Result {
        private int a = 0;
        public String errors = "";
        public long interval = 0;
        public String jsonString = "";
        public long last_requestTime = 0;
        public String msg = "";
        public int remain = 0;
        public String status = "fail";

        public void setStatus(String str) {
            this.status = str;
        }

        public void setRemain(int i) {
            this.remain = i;
        }

        public void setInterval(long j) {
            this.interval = j;
        }

        public void setErrors(String str) {
            this.errors = str;
        }

        public void setLast_requestTime(long j) {
            this.last_requestTime = j;
        }

        public Result(JSONObject jSONObject, boolean z) {
            if (z) {
                this.a = jSONObject.optInt("code");
                this.msg = jSONObject.optString(SocializeProtocolConstants.PROTOCOL_KEY_MSG, "");
                this.status = jSONObject.optString("status", "false");
                this.interval = jSONObject.optLong("interval", 0);
                this.last_requestTime = jSONObject.optLong("timestamp", System.currentTimeMillis());
                JSONObject optJSONObject = jSONObject.optJSONObject("data");
                if (optJSONObject != null) {
                    this.remain = optJSONObject.optInt("remain", 0);
                }
            } else {
                try {
                    this.status = jSONObject.optString("success", "fail");
                    this.remain = jSONObject.optInt("remain", 0);
                    this.interval = jSONObject.optLong("interval", 0);
                    this.errors = jSONObject.optString("errors");
                    if (jSONObject.has("last_requestTime")) {
                        this.last_requestTime = jSONObject.optLong("last_requestTime", 0);
                    } else {
                        jSONObject.put("last_requestTime", System.currentTimeMillis());
                    }
                } catch (Exception e) {
                    UMLog uMLog = UMConfigure.umDebugLog;
                    UMLog.mutlInfo("com.umeng.message.common.inter.ITagManager.Result", 0, "Json error");
                    return;
                }
            }
            this.jsonString = jSONObject.toString();
        }

        public String toString() {
            return this.jsonString;
        }
    }

    Result addTags(JSONObject jSONObject, String... strArr) throws Exception;

    Result addWeightedTags(JSONObject jSONObject, Hashtable<String, Integer> hashtable) throws Exception;

    Result deleteTags(JSONObject jSONObject, String... strArr) throws Exception;

    Result deleteWeightedTags(JSONObject jSONObject, String... strArr) throws Exception;

    List<String> getTags(JSONObject jSONObject) throws Exception;

    Hashtable<String, Integer> getWeightedTags(JSONObject jSONObject) throws Exception;

    Result reset(JSONObject jSONObject) throws Exception;

    Result update(JSONObject jSONObject, String... strArr) throws Exception;
}
