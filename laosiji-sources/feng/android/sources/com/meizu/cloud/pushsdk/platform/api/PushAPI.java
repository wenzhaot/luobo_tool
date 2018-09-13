package com.meizu.cloud.pushsdk.platform.api;

import android.content.Context;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.networking.AndroidNetworking;
import com.meizu.cloud.pushsdk.networking.common.ANResponse;
import com.meizu.cloud.pushsdk.networking.interfaces.OkHttpResponseAndStringRequestListener;
import com.meizu.cloud.pushsdk.platform.SignUtils;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;
import com.taobao.accs.common.Constants;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class PushAPI {
    public static final String TAG = "PushAPI";
    public String API_SERVER = "https://api-push.meizu.com/garcia/api/client/";
    private String CHANGE_ALL_SWITCH_STATUS_URL_PRIX = (this.API_SERVER + "message/changeAllSwitch");
    private String CHANGE_REGISTER_SWITCH_STATUS_URL_PRIX = (this.API_SERVER + "message/changeRegisterSwitch");
    private String CHECK_REGISTER_SWITCH_STATUS_URL_PRIX = (this.API_SERVER + "message/getRegisterSwitch");
    private String CHECK_SUBSCRIBE_ALIAS_URL_PRIX = (this.API_SERVER + "message/getSubAlias");
    private String CHECK_SUBSCRIBE_TAGS_URL_PRIX = (this.API_SERVER + "message/getSubTags");
    private String REGISTER_URL_PRIX = (this.API_SERVER + "message/registerPush");
    private String SUBSCRIBE_ALIAS_URL_PRIX = (this.API_SERVER + "message/subscribeAlias");
    private String SUBSCRIBE_TAGS_URL_PRIX = (this.API_SERVER + "message/subscribeTags");
    private String UNREGISTER_URL_ADVANCE_PRIX = (this.API_SERVER + "advance/unRegisterPush");
    private String UNREGISTER_URL_PRIX = (this.API_SERVER + "message/unRegisterPush");
    private String UNSUBSCRIBE_ALIAS_URL_PRIX = (this.API_SERVER + "message/unSubscribeAlias");
    private String UNSUBSCRIBE_ALL_TAGS_URL_RPIX = (this.API_SERVER + "message/unSubAllTags");
    private String UNSUBSCRIBE_TAGS_URL_PRIX = (this.API_SERVER + "message/unSubscribeTags");
    private String UPLOAD_LOG_FILE_URL_PRIX = (this.API_SERVER + "log/upload");

    public PushAPI(Context mContext) {
        AndroidNetworking.enableLogging();
        if (MzSystemUtils.isInternational() || MzSystemUtils.isIndiaLocal()) {
            this.API_SERVER = "https://api-push.in.meizu.com/garcia/api/client/";
            this.REGISTER_URL_PRIX = this.API_SERVER + "message/registerPush";
            this.UNREGISTER_URL_PRIX = this.API_SERVER + "message/unRegisterPush";
            this.UNREGISTER_URL_ADVANCE_PRIX = this.API_SERVER + "advance/unRegisterPush";
            this.CHECK_REGISTER_SWITCH_STATUS_URL_PRIX = this.API_SERVER + "message/getRegisterSwitch";
            this.CHANGE_REGISTER_SWITCH_STATUS_URL_PRIX = this.API_SERVER + "message/changeRegisterSwitch";
            this.CHANGE_ALL_SWITCH_STATUS_URL_PRIX = this.API_SERVER + "message/changeAllSwitch";
            this.SUBSCRIBE_TAGS_URL_PRIX = this.API_SERVER + "message/subscribeTags";
            this.UNSUBSCRIBE_TAGS_URL_PRIX = this.API_SERVER + "message/unSubscribeTags";
            this.UNSUBSCRIBE_ALL_TAGS_URL_RPIX = this.API_SERVER + "message/unSubAllTags";
            this.CHECK_SUBSCRIBE_TAGS_URL_PRIX = this.API_SERVER + "message/getSubTags";
            this.SUBSCRIBE_ALIAS_URL_PRIX = this.API_SERVER + "message/subscribeAlias";
            this.UNSUBSCRIBE_ALIAS_URL_PRIX = this.API_SERVER + "message/unSubscribeAlias";
            this.CHECK_SUBSCRIBE_ALIAS_URL_PRIX = this.API_SERVER + "message/getSubAlias";
        }
    }

    public void register(String appId, String appKey, String deviceId, OkHttpResponseAndStringRequestListener okHttpResponseAndStringRequestListener) {
        HashMap<String, String> paramsMap = new LinkedHashMap();
        paramsMap.put("appId", appId);
        paramsMap.put("deviceId", deviceId);
        HashMap<String, String> requestMap = new LinkedHashMap();
        requestMap.putAll(paramsMap);
        requestMap.put(Constants.KEY_SECURITY_SIGN, SignUtils.getSignature(paramsMap, appKey));
        DebugLogger.i(TAG, "register post map " + requestMap);
        AndroidNetworking.post(this.REGISTER_URL_PRIX).addBodyParameter(requestMap).build().getAsOkHttpResponseAndString(okHttpResponseAndStringRequestListener);
    }

    public ANResponse register(String appId, String appKey, String deviceId) {
        HashMap<String, String> paramsMap = new LinkedHashMap();
        paramsMap.put("appId", appId);
        paramsMap.put("deviceId", deviceId);
        HashMap<String, String> requestMap = new LinkedHashMap();
        requestMap.putAll(paramsMap);
        requestMap.put(Constants.KEY_SECURITY_SIGN, SignUtils.getSignature(paramsMap, appKey));
        DebugLogger.i(TAG, "register post map " + requestMap);
        return AndroidNetworking.post(this.REGISTER_URL_PRIX).addBodyParameter(requestMap).build().executeForString();
    }

    public void unRegister(String appId, String appKey, String deviceId, OkHttpResponseAndStringRequestListener okHttpResponseAndStringRequestListener) {
        HashMap<String, String> paramsMap = new LinkedHashMap();
        paramsMap.put("appId", appId);
        paramsMap.put("deviceId", deviceId);
        HashMap requestMap = new LinkedHashMap();
        requestMap.putAll(paramsMap);
        requestMap.put(Constants.KEY_SECURITY_SIGN, SignUtils.getSignature(paramsMap, appKey));
        DebugLogger.i(TAG, "unregister post map " + requestMap);
        AndroidNetworking.get(this.UNREGISTER_URL_PRIX).addQueryParameter(requestMap).build().getAsOkHttpResponseAndString(okHttpResponseAndStringRequestListener);
    }

    public ANResponse unRegister(String appId, String appKey, String deviceId) {
        HashMap<String, String> paramsMap = new LinkedHashMap();
        paramsMap.put("appId", appId);
        paramsMap.put("deviceId", deviceId);
        HashMap requestMap = new LinkedHashMap();
        requestMap.putAll(paramsMap);
        requestMap.put(Constants.KEY_SECURITY_SIGN, SignUtils.getSignature(paramsMap, appKey));
        DebugLogger.i(TAG, "unregister post map " + requestMap);
        return AndroidNetworking.get(this.UNREGISTER_URL_PRIX).addQueryParameter(requestMap).build().executeForString();
    }

    public void unRegister(String packageName, String deviceId, OkHttpResponseAndStringRequestListener okHttpResponseAndStringRequestListener) {
        HashMap<String, String> paramsMap = new LinkedHashMap();
        paramsMap.put(Constants.KEY_PACKAGE_NAME, packageName);
        paramsMap.put("deviceId", deviceId);
        HashMap<String, String> requestMap = new LinkedHashMap();
        requestMap.putAll(paramsMap);
        requestMap.put(Constants.KEY_SECURITY_SIGN, SignUtils.getSignature(paramsMap, "4a2ca769d79f4856bb3bd982d30de790"));
        DebugLogger.i(TAG, "advance unregister post map " + requestMap);
        AndroidNetworking.post(this.UNREGISTER_URL_ADVANCE_PRIX).addBodyParameter(requestMap).build().getAsOkHttpResponseAndString(okHttpResponseAndStringRequestListener);
    }

    public void checkPush(String appId, String appKey, String pushId, OkHttpResponseAndStringRequestListener okHttpResponseAndStringRequestListener) {
        HashMap<String, String> paramsMap = new LinkedHashMap();
        paramsMap.put("appId", appId);
        paramsMap.put(PushConstants.KEY_PUSH_ID, pushId);
        HashMap requestMap = new LinkedHashMap();
        requestMap.putAll(paramsMap);
        requestMap.put(Constants.KEY_SECURITY_SIGN, SignUtils.getSignature(paramsMap, appKey));
        DebugLogger.i(TAG, "checkPush post map " + requestMap);
        AndroidNetworking.get(this.CHECK_REGISTER_SWITCH_STATUS_URL_PRIX).addQueryParameter(requestMap).build().getAsOkHttpResponseAndString(okHttpResponseAndStringRequestListener);
    }

    public ANResponse checkPush(String appId, String appKey, String pushId) {
        HashMap<String, String> paramsMap = new LinkedHashMap();
        paramsMap.put("appId", appId);
        paramsMap.put(PushConstants.KEY_PUSH_ID, pushId);
        HashMap requestMap = new LinkedHashMap();
        requestMap.putAll(paramsMap);
        requestMap.put(Constants.KEY_SECURITY_SIGN, SignUtils.getSignature(paramsMap, appKey));
        DebugLogger.i(TAG, "checkPush post map " + requestMap);
        return AndroidNetworking.get(this.CHECK_REGISTER_SWITCH_STATUS_URL_PRIX).addQueryParameter(requestMap).build().executeForString();
    }

    public void switchPush(String appId, String appKey, String pushId, int msgType, boolean switcher, OkHttpResponseAndStringRequestListener okHttpResponseAndStringRequestListener) {
        HashMap<String, String> paramsMap = new LinkedHashMap();
        paramsMap.put("appId", appId);
        paramsMap.put(PushConstants.KEY_PUSH_ID, pushId);
        paramsMap.put("msgType", String.valueOf(msgType));
        paramsMap.put("subSwitch", switcher ? PushConstants.PUSH_TYPE_THROUGH_MESSAGE : PushConstants.PUSH_TYPE_NOTIFY);
        HashMap<String, String> requestMap = new LinkedHashMap();
        requestMap.putAll(paramsMap);
        requestMap.put(Constants.KEY_SECURITY_SIGN, SignUtils.getSignature(paramsMap, appKey));
        DebugLogger.i(TAG, this.CHANGE_ALL_SWITCH_STATUS_URL_PRIX + " switchPush post map " + requestMap);
        AndroidNetworking.post(this.CHANGE_REGISTER_SWITCH_STATUS_URL_PRIX).addBodyParameter(requestMap).build().getAsOkHttpResponseAndString(okHttpResponseAndStringRequestListener);
    }

    public ANResponse switchPush(String appId, String appKey, String pushId, int msgType, boolean switcher) {
        HashMap<String, String> paramsMap = new LinkedHashMap();
        paramsMap.put("appId", appId);
        paramsMap.put(PushConstants.KEY_PUSH_ID, pushId);
        paramsMap.put("msgType", String.valueOf(msgType));
        paramsMap.put("subSwitch", switcher ? PushConstants.PUSH_TYPE_THROUGH_MESSAGE : PushConstants.PUSH_TYPE_NOTIFY);
        HashMap<String, String> requestMap = new LinkedHashMap();
        requestMap.putAll(paramsMap);
        requestMap.put(Constants.KEY_SECURITY_SIGN, SignUtils.getSignature(paramsMap, appKey));
        DebugLogger.i(TAG, this.CHANGE_ALL_SWITCH_STATUS_URL_PRIX + " switchPush post map " + requestMap);
        return AndroidNetworking.post(this.CHANGE_REGISTER_SWITCH_STATUS_URL_PRIX).addBodyParameter(requestMap).build().executeForString();
    }

    public void switchPush(String appId, String appKey, String pushId, boolean switcher, OkHttpResponseAndStringRequestListener okHttpResponseAndStringRequestListener) {
        HashMap<String, String> paramsMap = new LinkedHashMap();
        paramsMap.put("appId", appId);
        paramsMap.put(PushConstants.KEY_PUSH_ID, pushId);
        paramsMap.put("subSwitch", switcher ? PushConstants.PUSH_TYPE_THROUGH_MESSAGE : PushConstants.PUSH_TYPE_NOTIFY);
        HashMap<String, String> requestMap = new LinkedHashMap();
        requestMap.putAll(paramsMap);
        requestMap.put(Constants.KEY_SECURITY_SIGN, SignUtils.getSignature(paramsMap, appKey));
        DebugLogger.i(TAG, this.CHANGE_ALL_SWITCH_STATUS_URL_PRIX + " switchPush post map " + requestMap);
        AndroidNetworking.post(this.CHANGE_ALL_SWITCH_STATUS_URL_PRIX).addBodyParameter(requestMap).build().getAsOkHttpResponseAndString(okHttpResponseAndStringRequestListener);
    }

    public ANResponse switchPush(String appId, String appKey, String pushId, boolean switcher) {
        HashMap<String, String> paramsMap = new LinkedHashMap();
        paramsMap.put("appId", appId);
        paramsMap.put(PushConstants.KEY_PUSH_ID, pushId);
        paramsMap.put("subSwitch", switcher ? PushConstants.PUSH_TYPE_THROUGH_MESSAGE : PushConstants.PUSH_TYPE_NOTIFY);
        HashMap<String, String> requestMap = new LinkedHashMap();
        requestMap.putAll(paramsMap);
        requestMap.put(Constants.KEY_SECURITY_SIGN, SignUtils.getSignature(paramsMap, appKey));
        DebugLogger.i(TAG, this.CHANGE_ALL_SWITCH_STATUS_URL_PRIX + " switchPush post map " + requestMap);
        return AndroidNetworking.post(this.CHANGE_ALL_SWITCH_STATUS_URL_PRIX).addBodyParameter(requestMap).build().executeForString();
    }

    public void subScribeTags(String appId, String appKey, String pushId, String args, OkHttpResponseAndStringRequestListener okHttpResponseAndStringRequestListener) {
        HashMap<String, String> paramsMap = new LinkedHashMap();
        paramsMap.put("appId", appId);
        paramsMap.put(PushConstants.KEY_PUSH_ID, pushId);
        paramsMap.put("tags", args);
        HashMap<String, String> requestMap = new LinkedHashMap();
        requestMap.putAll(paramsMap);
        requestMap.put(Constants.KEY_SECURITY_SIGN, SignUtils.getSignature(paramsMap, appKey));
        DebugLogger.i(TAG, "subScribeTags post map " + requestMap);
        AndroidNetworking.post(this.SUBSCRIBE_TAGS_URL_PRIX).addBodyParameter(requestMap).build().getAsOkHttpResponseAndString(okHttpResponseAndStringRequestListener);
    }

    public ANResponse subScribeTags(String appId, String appKey, String pushId, String args) {
        HashMap<String, String> paramsMap = new LinkedHashMap();
        paramsMap.put("appId", appId);
        paramsMap.put(PushConstants.KEY_PUSH_ID, pushId);
        paramsMap.put("tags", args);
        HashMap<String, String> requestMap = new LinkedHashMap();
        requestMap.putAll(paramsMap);
        requestMap.put(Constants.KEY_SECURITY_SIGN, SignUtils.getSignature(paramsMap, appKey));
        DebugLogger.i(TAG, "subScribeTags post map " + requestMap);
        return AndroidNetworking.post(this.SUBSCRIBE_TAGS_URL_PRIX).addBodyParameter(requestMap).build().executeForString();
    }

    public void unSubScribeTags(String appId, String appKey, String pushId, String args, OkHttpResponseAndStringRequestListener okHttpResponseAndStringRequestListener) {
        HashMap<String, String> paramsMap = new LinkedHashMap();
        paramsMap.put("appId", appId);
        paramsMap.put(PushConstants.KEY_PUSH_ID, pushId);
        paramsMap.put("tags", args);
        HashMap<String, String> requestMap = new LinkedHashMap();
        requestMap.putAll(paramsMap);
        requestMap.put(Constants.KEY_SECURITY_SIGN, SignUtils.getSignature(paramsMap, appKey));
        DebugLogger.i(TAG, "subScribeTags post map " + requestMap);
        AndroidNetworking.post(this.UNSUBSCRIBE_TAGS_URL_PRIX).addBodyParameter(requestMap).build().getAsOkHttpResponseAndString(okHttpResponseAndStringRequestListener);
    }

    public ANResponse unSubScribeTags(String appId, String appKey, String pushId, String args) {
        HashMap<String, String> paramsMap = new LinkedHashMap();
        paramsMap.put("appId", appId);
        paramsMap.put(PushConstants.KEY_PUSH_ID, pushId);
        paramsMap.put("tags", args);
        HashMap<String, String> requestMap = new LinkedHashMap();
        requestMap.putAll(paramsMap);
        requestMap.put(Constants.KEY_SECURITY_SIGN, SignUtils.getSignature(paramsMap, appKey));
        DebugLogger.i(TAG, "subScribeTags post map " + requestMap);
        return AndroidNetworking.post(this.UNSUBSCRIBE_TAGS_URL_PRIX).addBodyParameter(requestMap).build().executeForString();
    }

    public void unSubAllScribeTags(String appId, String appKey, String pushId, OkHttpResponseAndStringRequestListener okHttpResponseAndStringRequestListener) {
        HashMap<String, String> paramsMap = new LinkedHashMap();
        paramsMap.put("appId", appId);
        paramsMap.put(PushConstants.KEY_PUSH_ID, pushId);
        HashMap<String, String> requestMap = new LinkedHashMap();
        requestMap.putAll(paramsMap);
        requestMap.put(Constants.KEY_SECURITY_SIGN, SignUtils.getSignature(paramsMap, appKey));
        DebugLogger.i(TAG, "subScribeAllTags post map " + requestMap);
        AndroidNetworking.post(this.UNSUBSCRIBE_ALL_TAGS_URL_RPIX).addBodyParameter(requestMap).build().getAsOkHttpResponseAndString(okHttpResponseAndStringRequestListener);
    }

    public ANResponse unSubAllScribeTags(String appId, String appKey, String pushId) {
        HashMap<String, String> paramsMap = new LinkedHashMap();
        paramsMap.put("appId", appId);
        paramsMap.put(PushConstants.KEY_PUSH_ID, pushId);
        HashMap<String, String> requestMap = new LinkedHashMap();
        requestMap.putAll(paramsMap);
        requestMap.put(Constants.KEY_SECURITY_SIGN, SignUtils.getSignature(paramsMap, appKey));
        DebugLogger.i(TAG, "subScribeAllTags post map " + requestMap);
        return AndroidNetworking.post(this.UNSUBSCRIBE_ALL_TAGS_URL_RPIX).addBodyParameter(requestMap).build().executeForString();
    }

    public void checkSubScribeTags(String appId, String appKey, String pushId, OkHttpResponseAndStringRequestListener okHttpResponseAndStringRequestListener) {
        Map<String, String> paramsMap = new LinkedHashMap();
        paramsMap.put("appId", appId);
        paramsMap.put(PushConstants.KEY_PUSH_ID, pushId);
        HashMap requestMap = new LinkedHashMap();
        requestMap.putAll(paramsMap);
        requestMap.put(Constants.KEY_SECURITY_SIGN, SignUtils.getSignature(paramsMap, appKey));
        DebugLogger.i(TAG, "checkPush post map " + requestMap);
        AndroidNetworking.get(this.CHECK_SUBSCRIBE_TAGS_URL_PRIX).addQueryParameter(requestMap).build().getAsOkHttpResponseAndString(okHttpResponseAndStringRequestListener);
    }

    public ANResponse checkSubScribeTags(String appId, String appKey, String pushId) {
        Map<String, String> paramsMap = new LinkedHashMap();
        paramsMap.put("appId", appId);
        paramsMap.put(PushConstants.KEY_PUSH_ID, pushId);
        HashMap requestMap = new LinkedHashMap();
        requestMap.putAll(paramsMap);
        requestMap.put(Constants.KEY_SECURITY_SIGN, SignUtils.getSignature(paramsMap, appKey));
        DebugLogger.i(TAG, "checkPush post map " + requestMap);
        return AndroidNetworking.get(this.CHECK_SUBSCRIBE_TAGS_URL_PRIX).addQueryParameter(requestMap).build().executeForString();
    }

    public void subScribeAlias(String appId, String appKey, String pushId, String alias, OkHttpResponseAndStringRequestListener okHttpResponseAndStringRequestListener) {
        HashMap<String, String> paramsMap = new LinkedHashMap();
        paramsMap.put("appId", appId);
        paramsMap.put(Constants.KEY_APP_KEY, appKey);
        paramsMap.put(PushConstants.KEY_PUSH_ID, pushId);
        paramsMap.put("alias", alias);
        HashMap<String, String> requestMap = new LinkedHashMap();
        requestMap.putAll(paramsMap);
        requestMap.put(Constants.KEY_SECURITY_SIGN, SignUtils.getSignature(paramsMap, appKey));
        DebugLogger.i(TAG, "subScribeTags post map " + requestMap);
        AndroidNetworking.post(this.SUBSCRIBE_ALIAS_URL_PRIX).addBodyParameter(requestMap).build().getAsOkHttpResponseAndString(okHttpResponseAndStringRequestListener);
    }

    public ANResponse subScribeAlias(String appId, String appKey, String pushId, String alias) {
        HashMap<String, String> paramsMap = new LinkedHashMap();
        paramsMap.put("appId", appId);
        paramsMap.put(Constants.KEY_APP_KEY, appKey);
        paramsMap.put(PushConstants.KEY_PUSH_ID, pushId);
        paramsMap.put("alias", alias);
        HashMap<String, String> requestMap = new LinkedHashMap();
        requestMap.putAll(paramsMap);
        requestMap.put(Constants.KEY_SECURITY_SIGN, SignUtils.getSignature(paramsMap, appKey));
        DebugLogger.i(TAG, "subScribeTags post map " + requestMap);
        return AndroidNetworking.post(this.SUBSCRIBE_ALIAS_URL_PRIX).addBodyParameter(requestMap).build().executeForString();
    }

    public void unSubScribeAlias(String appId, String appKey, String pushId, String alias, OkHttpResponseAndStringRequestListener okHttpResponseAndStringRequestListener) {
        HashMap<String, String> paramsMap = new LinkedHashMap();
        paramsMap.put("appId", appId);
        paramsMap.put(PushConstants.KEY_PUSH_ID, pushId);
        paramsMap.put("alias", alias);
        HashMap<String, String> requestMap = new LinkedHashMap();
        requestMap.putAll(paramsMap);
        requestMap.put(Constants.KEY_SECURITY_SIGN, SignUtils.getSignature(paramsMap, appKey));
        DebugLogger.i(TAG, "subScribeTags post map " + requestMap);
        AndroidNetworking.post(this.UNSUBSCRIBE_ALIAS_URL_PRIX).addBodyParameter(requestMap).build().getAsOkHttpResponseAndString(okHttpResponseAndStringRequestListener);
    }

    public ANResponse unSubScribeAlias(String appId, String appKey, String pushId, String alias) {
        HashMap<String, String> paramsMap = new LinkedHashMap();
        paramsMap.put("appId", appId);
        paramsMap.put(PushConstants.KEY_PUSH_ID, pushId);
        paramsMap.put("alias", alias);
        HashMap<String, String> requestMap = new LinkedHashMap();
        requestMap.putAll(paramsMap);
        requestMap.put(Constants.KEY_SECURITY_SIGN, SignUtils.getSignature(paramsMap, appKey));
        DebugLogger.i(TAG, "subScribeTags post map " + requestMap);
        return AndroidNetworking.post(this.UNSUBSCRIBE_ALIAS_URL_PRIX).addBodyParameter(requestMap).build().executeForString();
    }

    public void checkSubScribeAlias(String appId, String appKey, String pushId, OkHttpResponseAndStringRequestListener okHttpResponseAndStringRequestListener) {
        HashMap<String, String> paramsMap = new LinkedHashMap();
        paramsMap.put("appId", appId);
        paramsMap.put(PushConstants.KEY_PUSH_ID, pushId);
        HashMap requestMap = new LinkedHashMap();
        requestMap.putAll(paramsMap);
        requestMap.put(Constants.KEY_SECURITY_SIGN, SignUtils.getSignature(paramsMap, appKey));
        DebugLogger.i(TAG, "checkPush post map " + requestMap);
        AndroidNetworking.get(this.CHECK_SUBSCRIBE_ALIAS_URL_PRIX).addQueryParameter(requestMap).build().getAsOkHttpResponseAndString(okHttpResponseAndStringRequestListener);
    }

    public ANResponse checkSubScribeAlias(String appId, String appKey, String pushId) {
        HashMap<String, String> paramsMap = new LinkedHashMap();
        paramsMap.put("appId", appId);
        paramsMap.put(PushConstants.KEY_PUSH_ID, pushId);
        HashMap requestMap = new LinkedHashMap();
        requestMap.putAll(paramsMap);
        requestMap.put(Constants.KEY_SECURITY_SIGN, SignUtils.getSignature(paramsMap, appKey));
        DebugLogger.i(TAG, "checkPush post map " + requestMap);
        return AndroidNetworking.get(this.CHECK_SUBSCRIBE_ALIAS_URL_PRIX).addQueryParameter(requestMap).build().executeForString();
    }

    public ANResponse<String> uploadLogFile(String messageId, String deviceId, String errorMsg, File logFile) {
        HashMap<String, String> paramsMap = new LinkedHashMap();
        paramsMap.put("msgId", messageId);
        paramsMap.put("deviceId", deviceId);
        HashMap<String, String> requestMap = new LinkedHashMap();
        requestMap.putAll(paramsMap);
        requestMap.put(Constants.KEY_SECURITY_SIGN, SignUtils.getSignature(paramsMap, "4a2ca769d79f4856bb3bd982d30de790"));
        if (!TextUtils.isEmpty(errorMsg)) {
            requestMap.put("errorMsg", errorMsg);
        }
        DebugLogger.i(TAG, "uploadLogFile post map " + requestMap);
        return AndroidNetworking.upload(this.UPLOAD_LOG_FILE_URL_PRIX).addMultipartParameter(requestMap).addMultipartFile("logFile", logFile).build().executeForString();
    }
}
