package com.umeng.message.common.impl.json;

import android.content.Context;
import android.text.TextUtils;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.message.MessageSharedPrefs;
import com.umeng.message.MsgConstant;
import com.umeng.message.common.UmengMessageDeviceConfig;
import com.umeng.message.common.inter.ITagManager;
import com.umeng.message.common.inter.ITagManager.Result;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

public class JTagManager implements ITagManager {
    private static final String a = JTagManager.class.getSimpleName();
    private Context b;

    public JTagManager(Context context) {
        this.b = context;
    }

    public Result addTags(JSONObject jSONObject, String... strArr) throws Exception {
        JSONObject sendRequest;
        String str = MsgConstant.TAG_ENDPOINT + "/add";
        try {
            sendRequest = JUtrack.sendRequest(jSONObject, str);
        } catch (Throwable e) {
            if (e == null || e.getMessage() == null || !e.getMessage().contains(MsgConstant.HTTPSDNS_ERROR) || !UmengMessageDeviceConfig.isOnline(this.b)) {
                throw new Exception(e);
            }
            UMLog uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(a, 2, "添加tag UnknownHostException");
            sendRequest = JUtrack.sendRequest(this.b, jSONObject, str);
        }
        Result result = new Result(sendRequest, false);
        if (TextUtils.equals(result.status, ITagManager.SUCCESS)) {
            MessageSharedPrefs.getInstance(this.b).addTags(strArr);
            MessageSharedPrefs.getInstance(this.b).setTagRemain(result.remain);
            MessageSharedPrefs.getInstance(this.b).add_addTagsInterval(result.toString());
        }
        return result;
    }

    public Result addWeightedTags(JSONObject jSONObject, Hashtable<String, Integer> hashtable) throws Exception {
        JSONObject sendRequest;
        UMLog uMLog;
        String str = MsgConstant.WEIGHTED_TAG_ENDPOINT + "/incr";
        try {
            sendRequest = JUtrack.sendRequest(jSONObject, str);
        } catch (Throwable e) {
            if (e == null || e.getMessage() == null || !e.getMessage().contains(MsgConstant.HTTPSDNS_ERROR) || !UmengMessageDeviceConfig.isOnline(this.b)) {
                throw new Exception(e);
            }
            uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(a, 2, "添加加权标签 UnknownHostException");
            sendRequest = JUtrack.sendRequest(this.b, jSONObject, str);
        }
        uMLog = UMConfigure.umDebugLog;
        UMLog.mutlInfo(a, 2, sendRequest.toString());
        Result result = new Result(sendRequest, true);
        MessageSharedPrefs.getInstance(this.b).setAddWeightedTagsInterval(result.toString());
        return result;
    }

    public Result update(JSONObject jSONObject, String... strArr) throws Exception {
        JSONObject sendRequest;
        String str = MsgConstant.TAG_ENDPOINT + "/update";
        try {
            sendRequest = JUtrack.sendRequest(jSONObject, str);
        } catch (Throwable e) {
            if (e == null || e.getMessage() == null || !e.getMessage().contains(MsgConstant.HTTPSDNS_ERROR) || !UmengMessageDeviceConfig.isOnline(this.b)) {
                throw new Exception(e);
            }
            sendRequest = JUtrack.sendRequest(this.b, jSONObject, str);
        }
        Result result = new Result(sendRequest, false);
        if (TextUtils.equals(result.status, ITagManager.SUCCESS)) {
            MessageSharedPrefs.getInstance(this.b).resetTags();
            MessageSharedPrefs.getInstance(this.b).addTags(strArr);
            MessageSharedPrefs.getInstance(this.b).setTagRemain(result.remain);
        }
        return result;
    }

    public Result deleteTags(JSONObject jSONObject, String... strArr) throws Exception {
        JSONObject sendRequest;
        String str = MsgConstant.TAG_ENDPOINT + "/delete";
        try {
            sendRequest = JUtrack.sendRequest(jSONObject, str);
        } catch (Throwable e) {
            if (e == null || e.getMessage() == null || !e.getMessage().contains(MsgConstant.HTTPSDNS_ERROR) || !UmengMessageDeviceConfig.isOnline(this.b)) {
                throw new Exception(e);
            }
            sendRequest = JUtrack.sendRequest(this.b, jSONObject, str);
        }
        Result result = new Result(sendRequest, false);
        if (TextUtils.equals(result.status, ITagManager.SUCCESS)) {
            MessageSharedPrefs.getInstance(this.b).removeTags(strArr);
            MessageSharedPrefs.getInstance(this.b).setTagRemain(result.remain);
            MessageSharedPrefs.getInstance(this.b).add_deleteTagsInterval(result.toString());
        }
        return result;
    }

    public Result deleteWeightedTags(JSONObject jSONObject, String... strArr) throws Exception {
        JSONObject sendRequest;
        String str = MsgConstant.WEIGHTED_TAG_ENDPOINT + "/delete";
        try {
            sendRequest = JUtrack.sendRequest(jSONObject, str);
        } catch (Throwable e) {
            if (e == null || e.getMessage() == null || !e.getMessage().contains(MsgConstant.HTTPSDNS_ERROR) || !UmengMessageDeviceConfig.isOnline(this.b)) {
                throw new Exception(e);
            }
            sendRequest = JUtrack.sendRequest(this.b, jSONObject, str);
        }
        Result result = new Result(sendRequest, true);
        MessageSharedPrefs.getInstance(this.b).setDeleteWeightedTagsInterval(result.toString());
        return result;
    }

    public Result reset(JSONObject jSONObject) throws Exception {
        JSONObject sendRequest;
        String str = MsgConstant.TAG_ENDPOINT + "/reset";
        try {
            sendRequest = JUtrack.sendRequest(jSONObject, str);
        } catch (Throwable e) {
            if (e == null || e.getMessage() == null || !e.getMessage().contains(MsgConstant.HTTPSDNS_ERROR) || !UmengMessageDeviceConfig.isOnline(this.b)) {
                throw new Exception(e);
            }
            sendRequest = JUtrack.sendRequest(this.b, jSONObject, str);
        }
        Result result = new Result(sendRequest, false);
        if (TextUtils.equals(result.status, ITagManager.SUCCESS)) {
            MessageSharedPrefs.getInstance(this.b).resetTags();
        }
        return result;
    }

    public List<String> getTags(JSONObject jSONObject) throws Exception {
        JSONObject sendRequest;
        String str = MsgConstant.TAG_ENDPOINT + "/get";
        try {
            sendRequest = JUtrack.sendRequest(jSONObject, str);
        } catch (Throwable e) {
            if (e == null || e.getMessage() == null || !e.getMessage().contains(MsgConstant.HTTPSDNS_ERROR) || !UmengMessageDeviceConfig.isOnline(this.b)) {
                throw new Exception(e);
            }
            sendRequest = JUtrack.sendRequest(this.b, jSONObject, str);
        }
        Result result = new Result(sendRequest, false);
        if (!TextUtils.equals(result.status, ITagManager.SUCCESS) || sendRequest.getString("tags") == null) {
            return null;
        }
        UMLog uMLog = UMConfigure.umDebugLog;
        UMLog.mutlInfo(a, 2, sendRequest.getString("tags"));
        MessageSharedPrefs.getInstance(this.b).add_getTagsInteral(result.toString());
        return Arrays.asList(sendRequest.getString("tags").split(MiPushClient.ACCEPT_TIME_SEPARATOR));
    }

    public Hashtable<String, Integer> getWeightedTags(JSONObject jSONObject) throws Exception {
        JSONObject sendRequest;
        String str = MsgConstant.WEIGHTED_TAG_ENDPOINT + "/list";
        try {
            sendRequest = JUtrack.sendRequest(jSONObject, str);
        } catch (Throwable e) {
            if (e == null || e.getMessage() == null || !e.getMessage().contains(MsgConstant.HTTPSDNS_ERROR) || !UmengMessageDeviceConfig.isOnline(this.b)) {
                throw new Exception(e);
            }
            sendRequest = JUtrack.sendRequest(this.b, jSONObject, str);
        }
        MessageSharedPrefs.getInstance(this.b).setListWeightedTagsInterval(new Result(sendRequest, true).toString());
        Hashtable<String, Integer> hashtable = new Hashtable();
        JSONObject optJSONObject = sendRequest.optJSONObject("data").optJSONObject("tags");
        if (optJSONObject != null) {
            Iterator keys = optJSONObject.keys();
            while (keys.hasNext()) {
                str = (String) keys.next();
                hashtable.put(str, Integer.valueOf(optJSONObject.getInt(str)));
            }
        }
        return hashtable;
    }
}
