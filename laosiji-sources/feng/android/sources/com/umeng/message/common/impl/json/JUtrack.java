package com.umeng.message.common.impl.json;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.text.TextUtils;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.stub.StubApp;
import com.umeng.analytics.pro.b;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.commonsdk.framework.UMEnvelopeBuild;
import com.umeng.commonsdk.proguard.g;
import com.umeng.commonsdk.stateless.UMSLEnvelopeBuild;
import com.umeng.message.MessageSharedPrefs;
import com.umeng.message.MsgConstant;
import com.umeng.message.UTrack.ICallBack;
import com.umeng.message.common.UmengMessageDeviceConfig;
import com.umeng.message.common.inter.ITagManager;
import com.umeng.message.common.inter.IUtrack;
import com.umeng.message.entity.Alias;
import com.umeng.message.proguard.h;
import com.umeng.message.proguard.m;
import com.umeng.message.provider.a;
import com.umeng.message.util.HttpRequest;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class JUtrack implements IUtrack {
    private static final String a = JUtrack.class.getSimpleName();
    private Context b;

    public JUtrack(Context context) {
        this.b = StubApp.getOrigApplicationContext(context.getApplicationContext());
    }

    public void sendMsgLog(JSONObject jSONObject, String str, int i) throws Exception {
        UMSLEnvelopeBuild.mContext = this.b;
        UMSLEnvelopeBuild uMSLEnvelopeBuild = new UMSLEnvelopeBuild();
        JSONObject buildSLBaseHeader = uMSLEnvelopeBuild.buildSLBaseHeader(this.b);
        JSONObject jSONObject2 = (JSONObject) buildSLBaseHeader.opt("header");
        jSONObject2.put("din", UmengMessageDeviceConfig.getDINAes(this.b));
        jSONObject2.put(g.as, MsgConstant.SDK_VERSION);
        jSONObject2.put("push_switch", UmengMessageDeviceConfig.isNotificationEnabled(this.b));
        buildSLBaseHeader.put("header", jSONObject2);
        jSONObject2 = new JSONObject();
        jSONObject2.put("ts", jSONObject.getLong("ts"));
        jSONObject2.put("pa", jSONObject.getString("pa"));
        jSONObject2.put("device_token", MessageSharedPrefs.getInstance(this.b).getDeviceToken());
        jSONObject2.put("msg_id", jSONObject.getString("msg_id"));
        jSONObject2.put(MsgConstant.KEY_ACTION_TYPE, jSONObject.getInt(MsgConstant.KEY_ACTION_TYPE));
        JSONArray jSONArray = new JSONArray();
        jSONArray.put(jSONObject2);
        jSONObject2 = new JSONObject();
        jSONObject2.put("push", jSONArray);
        if (h.d(this.b)) {
            jSONObject2 = uMSLEnvelopeBuild.buildSLEnvelope(this.b, buildSLBaseHeader, jSONObject2, MsgConstant.UNPX_PUSH_LOGS);
            if (jSONObject2 != null && !jSONObject2.has(b.ao)) {
                m.a(this.b).a(str, i);
                if (i != 0) {
                    m.a(this.b).b(str);
                    return;
                }
                return;
            }
            return;
        }
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put("jsonHeader", buildSLBaseHeader);
        jSONObject3.put("jsonBody", jSONObject2);
        jSONObject3.put("msgId", str);
        jSONObject3.put("actionType", i);
        Intent intent = new Intent();
        intent.setPackage(this.b.getPackageName());
        intent.setAction(MsgConstant.MESSAGE_MESSAGE_SEND_ACTION);
        intent.putExtra(MsgConstant.KEY_UMPX_PATH, MsgConstant.UNPX_PUSH_LOGS);
        intent.putExtra(MsgConstant.KEY_SENDMESSAGE, jSONObject3.toString());
        this.b.startService(intent);
    }

    public void trackAppLaunch(JSONObject jSONObject) throws Exception {
        UMSLEnvelopeBuild.mContext = this.b;
        UMSLEnvelopeBuild uMSLEnvelopeBuild = new UMSLEnvelopeBuild();
        JSONObject buildSLBaseHeader = uMSLEnvelopeBuild.buildSLBaseHeader(this.b);
        JSONObject jSONObject2 = (JSONObject) buildSLBaseHeader.opt("header");
        jSONObject2.put("din", UmengMessageDeviceConfig.getDINAes(this.b));
        jSONObject2.put(g.as, MsgConstant.SDK_VERSION);
        jSONObject2.put("push_switch", UmengMessageDeviceConfig.isNotificationEnabled(this.b));
        buildSLBaseHeader.put("header", jSONObject2);
        jSONObject2 = new JSONObject();
        jSONObject2.put("device_token", MessageSharedPrefs.getInstance(this.b).getDeviceToken());
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put("push", jSONObject2);
        if (h.d(this.b)) {
            jSONObject2 = uMSLEnvelopeBuild.buildSLEnvelope(this.b, buildSLBaseHeader, jSONObject3, MsgConstant.UMPX_PUSH_LAUNCH);
            if (jSONObject2 != null && !jSONObject2.has(b.ao)) {
                m.a(this.b).a(System.currentTimeMillis());
                int parseInt = Integer.parseInt(UMEnvelopeBuild.imprintProperty(this.b, "launch_policy", "-1"));
                UMLog uMLog = UMConfigure.umDebugLog;
                UMLog.mutlInfo(a, 2, "launch_policy:" + parseInt);
                int parseInt2 = Integer.parseInt(UMEnvelopeBuild.imprintProperty(this.b, "tag_policy", "-1"));
                UMLog uMLog2 = UMConfigure.umDebugLog;
                UMLog.mutlInfo(a, 2, "tag_policy:" + parseInt2);
                if (parseInt > 0) {
                    MessageSharedPrefs.getInstance(this.b).setAppLaunchLogSendPolicy(parseInt);
                }
                if (parseInt2 > 0) {
                    MessageSharedPrefs.getInstance(this.b).setTagSendPolicy(parseInt2);
                    return;
                }
                return;
            }
            return;
        }
        jSONObject2 = new JSONObject();
        jSONObject2.put("jsonHeader", buildSLBaseHeader);
        jSONObject2.put("jsonBody", jSONObject3);
        Intent intent = new Intent();
        intent.setPackage(this.b.getPackageName());
        intent.setAction(MsgConstant.MESSAGE_MESSAGE_SEND_ACTION);
        intent.putExtra(MsgConstant.KEY_UMPX_PATH, MsgConstant.UMPX_PUSH_LAUNCH);
        intent.putExtra(MsgConstant.KEY_SENDMESSAGE, jSONObject2.toString());
        this.b.startService(intent);
    }

    public void trackRegister(JSONObject jSONObject) throws Exception {
        UMSLEnvelopeBuild.mContext = this.b;
        UMSLEnvelopeBuild uMSLEnvelopeBuild = new UMSLEnvelopeBuild();
        JSONObject buildSLBaseHeader = uMSLEnvelopeBuild.buildSLBaseHeader(this.b);
        JSONObject jSONObject2 = (JSONObject) buildSLBaseHeader.opt("header");
        jSONObject2.put("din", UmengMessageDeviceConfig.getDINAes(this.b));
        jSONObject2.put(g.as, MsgConstant.SDK_VERSION);
        jSONObject2.put("push_switch", UmengMessageDeviceConfig.isNotificationEnabled(this.b));
        buildSLBaseHeader.put("header", jSONObject2);
        jSONObject2 = new JSONObject();
        jSONObject2.put("device_token", MessageSharedPrefs.getInstance(this.b).getDeviceToken());
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put("push", jSONObject2);
        if (h.d(this.b)) {
            jSONObject2 = uMSLEnvelopeBuild.buildSLEnvelope(this.b, buildSLBaseHeader, jSONObject3, MsgConstant.UMPX_PUSH_REGISTER);
            if (jSONObject2 != null && !jSONObject2.has(b.ao)) {
                MessageSharedPrefs.getInstance(this.b).setHasResgister(true);
                if (TextUtils.isEmpty(MessageSharedPrefs.getInstance(this.b).getDeviceToken())) {
                    UMLog uMLog = UMConfigure.umDebugLog;
                    UMLog.mutlInfo(a, 0, "setRegisteredToUmeng: device token为空");
                }
            }
        }
    }

    public void trackLocation(JSONObject jSONObject) throws Exception {
        JSONObject sendRequest;
        try {
            sendRequest = sendRequest(jSONObject, MsgConstant.LBS_ENDPOINT);
        } catch (Throwable e) {
            if (e == null || e.getMessage() == null || !e.getMessage().contains(MsgConstant.HTTPSDNS_ERROR) || !UmengMessageDeviceConfig.isOnline(this.b)) {
                throw new Exception(e);
            }
            sendRequest = sendRequest(this.b, jSONObject, MsgConstant.LBS_ENDPOINT);
        } catch (Exception e2) {
            e2.printStackTrace();
            return;
        }
        if (sendRequest == null) {
            return;
        }
        if (TextUtils.equals(sendRequest.getString("success"), ITagManager.SUCCESS)) {
            UMLog uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(a, 2, "location track success");
        }
    }

    public void addAlias(String str, String str2, JSONObject jSONObject, ICallBack iCallBack) throws Exception {
        String optString = jSONObject.optString("fail", "");
        String optString2 = jSONObject.optString("success", "");
        UMLog uMLog = UMConfigure.umDebugLog;
        UMLog.mutlInfo(a, 2, "keyfail:" + optString + ",keysuccess:" + optString2);
        if (optString.equals("") && optString2.equals("")) {
            JSONObject sendRequest;
            try {
                sendRequest = sendRequest(jSONObject, MsgConstant.ALIAS_ENDPOINT);
            } catch (Throwable e) {
                if (e == null || e.getMessage() == null || !e.getMessage().contains(MsgConstant.HTTPSDNS_ERROR) || !UmengMessageDeviceConfig.isOnline(this.b)) {
                    throw new Exception(e);
                }
                sendRequest = sendRequest(this.b, jSONObject, MsgConstant.ALIAS_ENDPOINT);
            }
            if (sendRequest == null || !TextUtils.equals(sendRequest.optString("success", ""), ITagManager.SUCCESS)) {
                MessageSharedPrefs.getInstance(this.b).addAlias(str, str2, 0, 1, "网络请求失败alias:" + str + ",type:" + str2 + ",devicetoken:" + MessageSharedPrefs.getInstance(this.b).getDeviceToken());
                iCallBack.onMessage(false, "alias:" + str + "添加失败");
                return;
            }
            MessageSharedPrefs.getInstance(this.b).addAlias(str, str2, 0, 0, "");
            try {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("interval", sendRequest.optLong("interval", 0));
                jSONObject2.put("last_requestTime", System.currentTimeMillis());
                MessageSharedPrefs.getInstance(this.b).add_addAliasInterval(jSONObject2.toString());
            } catch (Exception e2) {
                if (e2 != null) {
                    e2.printStackTrace();
                }
            }
            iCallBack.onMessage(true, "alias:" + str + "添加成功");
            return;
        }
        if (!optString.equals("")) {
            iCallBack.onMessage(false, "alias:" + str + "添加失败");
            MessageSharedPrefs.getInstance(this.b).addAlias(str, str2, 0, 1, optString);
        }
        if (!optString2.equals("")) {
            iCallBack.onMessage(true, "alias:" + str + "已经添加");
            MessageSharedPrefs.getInstance(this.b).addAlias(str, str2, 0, 2, optString2);
        }
    }

    public void setAlias(String str, String str2, JSONObject jSONObject, ICallBack iCallBack) throws Exception {
        String optString = jSONObject.optString("fail", "");
        String optString2 = jSONObject.optString("success", "");
        UMLog uMLog = UMConfigure.umDebugLog;
        UMLog.mutlInfo(a, 2, "keyfail:" + optString + ",keysuccess:" + optString2);
        if (optString.equals("") && optString2.equals("")) {
            JSONObject sendRequest;
            try {
                sendRequest = sendRequest(jSONObject, MsgConstant.ALIAS_EXCLUSIVE_ENDPOINT);
            } catch (Throwable e) {
                if (e == null || e.getMessage() == null || !e.getMessage().contains(MsgConstant.HTTPSDNS_ERROR) || !UmengMessageDeviceConfig.isOnline(this.b)) {
                    throw new Exception(e);
                }
                sendRequest = sendRequest(this.b, jSONObject, MsgConstant.ALIAS_EXCLUSIVE_ENDPOINT);
            }
            if (sendRequest == null || !TextUtils.equals(sendRequest.optString("success", ""), ITagManager.SUCCESS)) {
                MessageSharedPrefs.getInstance(this.b).addAlias(str, str2, 1, 1, "网络请求失败alias:" + str + ",type:" + str2 + ",devicetoken:" + MessageSharedPrefs.getInstance(this.b).getDeviceToken());
                iCallBack.onMessage(false, "alias:" + str + "添加失败");
                return;
            }
            MessageSharedPrefs.getInstance(this.b).addAlias(str, str2, 1, 0, "");
            try {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("interval", sendRequest.optLong("interval", 0));
                jSONObject2.put("last_requestTime", System.currentTimeMillis());
                MessageSharedPrefs.getInstance(this.b).add_setAliasInterval(jSONObject2.toString());
            } catch (Exception e2) {
                if (e2 != null) {
                    e2.printStackTrace();
                }
            }
            iCallBack.onMessage(true, "alias:" + str + "添加成功");
            return;
        }
        if (!optString.equals("")) {
            iCallBack.onMessage(false, "alias:" + str + "添加失败");
            MessageSharedPrefs.getInstance(this.b).addAlias(str, str2, 1, 1, optString);
        }
        if (!optString2.equals("")) {
            iCallBack.onMessage(true, "alias:" + str + "已经添加");
            MessageSharedPrefs.getInstance(this.b).addAlias(str, str2, 1, 2, optString2);
        }
    }

    public void deleteAlias(String str, String str2, JSONObject jSONObject, ICallBack iCallBack) throws Exception {
        JSONObject sendRequest;
        try {
            sendRequest = sendRequest(jSONObject, MsgConstant.DELETE_ALIAS_ENDPOINT);
        } catch (Throwable e) {
            if (e == null || e.getMessage() == null || !e.getMessage().contains(MsgConstant.HTTPSDNS_ERROR) || !UmengMessageDeviceConfig.isOnline(this.b)) {
                throw new Exception(e);
            }
            sendRequest = sendRequest(this.b, jSONObject, MsgConstant.DELETE_ALIAS_ENDPOINT);
        }
        if (sendRequest != null && TextUtils.equals(sendRequest.getString("success"), ITagManager.SUCCESS)) {
            MessageSharedPrefs.getInstance(this.b).removeAlias(0, str, str2);
            MessageSharedPrefs.getInstance(this.b).removeAlias(1, str, str2);
            try {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("interval", sendRequest.optLong("interval", 0));
                jSONObject2.put("last_requestTime", System.currentTimeMillis());
                MessageSharedPrefs.getInstance(this.b).add_deleteAliasInterval(jSONObject2.toString());
            } catch (Exception e2) {
                if (e2 != null) {
                    e2.printStackTrace();
                }
            }
            iCallBack.onMessage(true, "alias:" + str + ",type:" + str2 + "删除成功");
        }
    }

    public static JSONObject sendRequest(JSONObject jSONObject, String str) throws Exception {
        String body = HttpRequest.post((CharSequence) str).acceptJson().contentType("application/json").send(jSONObject.toString()).body("UTF-8");
        UMLog uMLog = UMConfigure.umDebugLog;
        UMLog.mutlInfo(a, 2, "sendRequest() url=" + str + "\n request = " + jSONObject + "\n response = " + body);
        return new JSONObject(body);
    }

    public static JSONObject sendRequest(Context context, JSONObject jSONObject, String str) throws Exception {
        String host = new URL(str).getHost();
        String a = com.umeng.message.util.b.a(context, host);
        UMLog uMLog = UMConfigure.umDebugLog;
        UMLog.mutlInfo(a, 2, "ip:" + a);
        if (a == null) {
            return null;
        }
        URL url = new URL(str.replaceFirst(host, a));
        a = HttpRequest.post(url).acceptJson().contentType("application/json").header("Host", host).trustHosts().send(jSONObject.toString()).body("UTF-8");
        UMLog uMLog2 = UMConfigure.umDebugLog;
        UMLog.mutlInfo(a, 2, "dns-->sendRequest() url=" + url.toString() + "\n request = " + jSONObject + "\n response = " + a);
        return new JSONObject(a);
    }

    public void sendAliasFailLog(JSONObject jSONObject) {
        if (MessageSharedPrefs.getInstance(this.b).getDaRegisterSendPolicy() == 1) {
            UMLog uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(a, 2, "da_register_policy=1, skip sending da_register info.");
            return;
        }
        try {
            String[] strArr = new String[]{PushConstants.PUSH_TYPE_THROUGH_MESSAGE};
            String str = "";
            ContentResolver contentResolver = this.b.getContentResolver();
            a.a(this.b);
            Cursor query = contentResolver.query(a.d, new String[]{"message", "time"}, "error=?", strArr, null);
            if (query != null) {
                List<Alias> arrayList = new ArrayList();
                query.moveToFirst();
                while (!query.isAfterLast()) {
                    String string = query.getString(query.getColumnIndex("message"));
                    long j = query.getLong(query.getColumnIndex("time"));
                    Alias alias = new Alias();
                    alias.aliasMessage = string;
                    alias.aliasTime = j;
                    arrayList.add(alias);
                    query.moveToNext();
                }
                if (query != null) {
                    query.close();
                }
                for (Alias alias2 : arrayList) {
                    a(jSONObject, alias2.aliasMessage, alias2.aliasTime);
                }
            }
        } catch (Exception e) {
            if (e != null) {
                e.printStackTrace();
            }
        }
    }

    public void sendRegisterLog(JSONObject jSONObject) throws Exception {
        JSONObject sendRequest;
        try {
            sendRequest = sendRequest(jSONObject, MsgConstant.ALIAS_LOG);
        } catch (Throwable e) {
            if (e == null || e.getMessage() == null || !e.getMessage().contains(MsgConstant.HTTPSDNS_ERROR) || !UmengMessageDeviceConfig.isOnline(this.b)) {
                throw new Exception(e);
            }
            sendRequest = sendRequest(this.b, jSONObject, MsgConstant.ALIAS_LOG);
        }
        if (sendRequest != null && TextUtils.equals(sendRequest.optString("success", ""), ITagManager.SUCCESS)) {
            int parseInt = Integer.parseInt(sendRequest.getString("da_register_policy"));
            if (parseInt > 0) {
                MessageSharedPrefs.getInstance(this.b).setDaRegisterSendPolicy(parseInt);
            }
        }
    }

    private void a(JSONObject jSONObject, String str, long j) throws Exception {
        if (!str.equals("")) {
            JSONObject sendRequest;
            jSONObject.put(MsgConstant.KEY_ALIAS_FAIL_LOG, str);
            try {
                sendRequest = sendRequest(jSONObject, MsgConstant.ALIAS_LOG);
            } catch (Throwable e) {
                if (e == null || e.getMessage() == null || !e.getMessage().contains(MsgConstant.HTTPSDNS_ERROR) || !UmengMessageDeviceConfig.isOnline(this.b)) {
                    throw new Exception(e);
                }
                sendRequest = sendRequest(this.b, jSONObject, MsgConstant.ALIAS_LOG);
            }
            if (sendRequest != null && TextUtils.equals(sendRequest.optString("success", ""), ITagManager.SUCCESS)) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("error", "3");
                String[] strArr = new String[]{j + ""};
                ContentResolver contentResolver = this.b.getContentResolver();
                a.a(this.b);
                contentResolver.update(a.d, contentValues, "time=?", strArr);
                int parseInt = Integer.parseInt(sendRequest.optString("da_register_policy"));
                if (parseInt > 0) {
                    MessageSharedPrefs.getInstance(this.b).setDaRegisterSendPolicy(parseInt);
                }
            }
        }
    }
}
