package com.feng.car.operation;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import com.feng.car.FengApplication;
import com.feng.car.activity.BaseActivity;
import com.feng.car.activity.GuideActivity;
import com.feng.car.activity.SettingAccountPhoneActivity;
import com.feng.car.activity.SettingAccountSecurityActivity;
import com.feng.car.adapter.CommonBottomDialogAdapter.OnDialogItemClickListener;
import com.feng.car.entity.DialogItemEntity;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.event.UserLoginEvent;
import com.feng.car.utils.FengConstant;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.HttpConstant;
import com.feng.car.utils.LogGatherReadUtil;
import com.feng.car.view.CommonDialog;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.feng.library.utils.SharedUtil;
import com.feng.library.utils.StringUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

public class ThirdLoginOperation {
    public void sendCodeOperation(final Context context, String phoneNum, int type, String connectprov, final SuccessFailCallback callback) {
        if (checkPhoneNum(context, phoneNum)) {
            Map<String, Object> map = new HashMap();
            map.put("type", String.valueOf(type));
            if (type == 6 && !TextUtils.isEmpty(connectprov)) {
                map.put(HttpConstant.CONNECTPROY, connectprov);
            }
            map.put(HttpConstant.PHONENUMBER, phoneNum);
            FengApplication.getInstance().httpRequest(HttpConstant.IDENTIFYINGCODE, map, new OkHttpResponseCallback() {
                public void onNetworkError() {
                    ((BaseActivity) context).showSecondTypeToast(2131231273);
                }

                public void onStart() {
                    if (callback != null) {
                        callback.onStart();
                    }
                }

                public void onFinish() {
                }

                public void onFailure(int statusCode, String content, Throwable error) {
                    ((BaseActivity) context).showSecondTypeToast(2131231273);
                    if (callback != null) {
                        callback.onFail();
                    }
                }

                public void onSuccess(int statusCode, String content) {
                    try {
                        int code = new JSONObject(content).getInt("code");
                        if (code != 1) {
                            if (callback != null) {
                                callback.onFail();
                            }
                            FengApplication.getInstance().checkCode(HttpConstant.IDENTIFYINGCODE, code);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        if (callback != null) {
                            callback.onFail();
                        }
                        ((BaseActivity) context).showSecondTypeToast(2131231273);
                        FengApplication.getInstance().upLoadTryCatchLog(HttpConstant.IDENTIFYINGCODE, content, e);
                    }
                }
            });
        }
    }

    public void updatePhoneOperation(final Context context, final String newPhoneNum, String oldPhoneNum, String code, final SuccessFailCallback callback) {
        if (!FengApplication.getInstance().getSeverceState()) {
            ((BaseActivity) context).showSecondTypeToast(2131231540);
        } else if (!checkPhoneNum(context, newPhoneNum)) {
        } else {
            if (StringUtil.isEmpty(code)) {
                ((BaseActivity) context).showThirdTypeToast(2131231390);
                return;
            }
            Map<String, Object> map = new HashMap();
            map.put("code", code);
            map.put("newphone", newPhoneNum);
            map.put("oldphone", oldPhoneNum);
            FengApplication.getInstance().httpRequest(HttpConstant.USER_UPDATE_PHONE, map, new OkHttpResponseCallback() {
                public void onNetworkError() {
                    ((BaseActivity) context).showSecondTypeToast(2131231273);
                }

                public void onStart() {
                }

                public void onFinish() {
                }

                public void onFailure(int statusCode, String content, Throwable error) {
                    ((BaseActivity) context).showSecondTypeToast(2131231273);
                }

                public void onSuccess(int statusCode, String content) {
                    try {
                        int code = new JSONObject(content).getInt("code");
                        if (code == 1) {
                            FengApplication.getInstance().getUserInfo().phonenumber = newPhoneNum;
                            SharedUtil.putString(context, UserInfo.MOBLIE_KEY, newPhoneNum);
                            if (callback != null) {
                                callback.onSuccess();
                            }
                            ((BaseActivity) context).showFirstTypeToast(2131231686);
                            return;
                        }
                        if (callback != null) {
                            callback.onFail();
                        }
                        FengApplication.getInstance().checkCode(HttpConstant.IDENTIFYINGCODE, code);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        if (callback != null) {
                            callback.onFail();
                        }
                        ((BaseActivity) context).showSecondTypeToast(2131231273);
                        FengApplication.getInstance().upLoadTryCatchLog(HttpConstant.IDENTIFYINGCODE, content, e);
                    }
                }
            });
        }
    }

    public void checkOldPhoneOperation(final Context context, String oldPhoneNum, String code, final SuccessFailCallback callback) {
        if (!checkPhoneNum(context, oldPhoneNum)) {
            return;
        }
        if (StringUtil.isEmpty(code)) {
            ((BaseActivity) context).showThirdTypeToast(2131231390);
            return;
        }
        Map<String, Object> map = new HashMap();
        map.put("code", code);
        map.put("oldphone", oldPhoneNum);
        FengApplication.getInstance().httpRequest(HttpConstant.VALIDATE_OLD_PHONE, map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                ((BaseActivity) context).showSecondTypeToast(2131231273);
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                ((BaseActivity) context).showSecondTypeToast(2131231273);
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    int code = new JSONObject(content).getInt("code");
                    if (code != 1) {
                        if (callback != null) {
                            callback.onFail();
                        }
                        FengApplication.getInstance().checkCode(HttpConstant.VALIDATE_OLD_PHONE, code);
                    } else if (callback != null) {
                        callback.onSuccess();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (callback != null) {
                        callback.onFail();
                    }
                    ((BaseActivity) context).showSecondTypeToast(2131231273);
                    FengApplication.getInstance().upLoadTryCatchLog(HttpConstant.VALIDATE_OLD_PHONE, content, e);
                }
            }
        });
    }

    public void loginOperation(final Context context, String phoneNum, String code, final SuccessFailCallback callback) {
        if (!FengApplication.getInstance().getSeverceState()) {
            ((BaseActivity) context).showSecondTypeToast(2131231540);
        } else if (!checkPhoneNum(context, phoneNum)) {
        } else {
            if (StringUtil.isEmpty(code)) {
                ((BaseActivity) context).showThirdTypeToast(2131231390);
                return;
            }
            Map<String, Object> map = new HashMap();
            map.put(HttpConstant.PHONENUMBER, phoneNum);
            map.put(HttpConstant.IDENTIFYING_CODE, code);
            String device = LogGatherReadUtil.getInstance().getUniqueId(context);
            if (TextUtils.isEmpty(device)) {
                device = Build.FINGERPRINT;
            }
            map.put(HttpConstant.VISITORDEVICE, device);
            FengApplication.getInstance().httpRequest(HttpConstant.LOGIN, map, new OkHttpResponseCallback() {
                public void onNetworkError() {
                    ((BaseActivity) context).showSecondTypeToast(2131231273);
                }

                public void onStart() {
                    if (callback != null) {
                        callback.onStart();
                    }
                }

                public void onFinish() {
                }

                public void onFailure(int statusCode, String content, Throwable error) {
                    if (callback != null) {
                        callback.onFail();
                    }
                    ((BaseActivity) context).showSecondTypeToast(2131231273);
                }

                public void onSuccess(int statusCode, String content) {
                    try {
                        JSONObject jsonResult = new JSONObject(content);
                        int code = jsonResult.getInt("code");
                        if (code == 1) {
                            JSONObject jsonBody = jsonResult.getJSONObject("body");
                            JSONObject json = jsonBody.getJSONObject(HttpConstant.USER);
                            UserInfo user = new UserInfo();
                            user.parser(json);
                            if (jsonBody.has("shop") && jsonBody.getJSONObject("shop").has("state")) {
                                SharedUtil.putInt(context, UserInfo.LOCAL_OPEN_SHOP_STATE, jsonBody.getJSONObject("shop").getInt("state"));
                            }
                            SharedUtil.putInt(context, "login_canpublishvideo", user.canpublishvideo);
                            FengUtil.saveLoginUserInfo(context, user);
                            if (user.iscomplete == 0) {
                                context.startActivity(new Intent(context, GuideActivity.class));
                            } else {
                                EventBus.getDefault().post(new UserLoginEvent(true));
                            }
                            if (callback != null) {
                                callback.onSuccess();
                                return;
                            }
                            return;
                        }
                        if (callback != null) {
                            callback.onFail();
                        }
                        FengApplication.getInstance().checkCode(HttpConstant.LOGIN, code);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        ((BaseActivity) context).showThirdTypeToast(2131231196);
                        if (callback != null) {
                            callback.onFail();
                        }
                        FengApplication.getInstance().upLoadTryCatchLog(HttpConstant.LOGIN, content, e);
                    }
                }
            });
        }
    }

    public void checkIsBindOperation(Context context, String connectprov, String connectuid, String wxConnectUnionid, String connectuname, String connectsex, String connectulogo, SuccessFailCallback callback) {
        if (TextUtils.isEmpty(connectprov) || TextUtils.isEmpty(connectuid)) {
            ((BaseActivity) context).showSecondTypeToast(2131231073);
            return;
        }
        Map<String, Object> map = new HashMap();
        map.put(HttpConstant.CONNECTPROY, connectprov);
        map.put(HttpConstant.CONNECTUID, connectuid);
        if (connectprov.equals(FengConstant.LOGIN_PLATFORM_WEIXIN) && !TextUtils.isEmpty(wxConnectUnionid)) {
            map.put(HttpConstant.CONNECTWXUNIONID, wxConnectUnionid);
        }
        map.put(HttpConstant.CONNECTNICKNAME, connectuname);
        map.put(HttpConstant.CONNECTLOGO, connectulogo);
        map.put(HttpConstant.CONNECTSEX, connectsex);
        final Context context2 = context;
        final SuccessFailCallback successFailCallback = callback;
        final String str = connectprov;
        final String str2 = connectuid;
        final String str3 = wxConnectUnionid;
        final String str4 = connectuname;
        final String str5 = connectsex;
        final String str6 = connectulogo;
        FengApplication.getInstance().httpRequest(HttpConstant.CON_LOGIN, map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                ((BaseActivity) context2).showSecondTypeToast(2131231273);
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                ((BaseActivity) context2).showSecondTypeToast(2131231273);
                if (successFailCallback != null) {
                    successFailCallback.onFail();
                }
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    int code = jsonResult.getInt("code");
                    if (code == 1) {
                        JSONObject jsonBody = jsonResult.getJSONObject("body");
                        JSONObject json = jsonBody.getJSONObject(HttpConstant.USER);
                        UserInfo user = new UserInfo();
                        user.parser(json);
                        if (str.equals(FengConstant.LOGIN_PLATFORM_QQ)) {
                            user.connect.qq = 1;
                        } else if (str.equals(FengConstant.LOGIN_PLATFORM_WEIBO)) {
                            user.connect.weibo = 1;
                        } else if (str.equals(FengConstant.LOGIN_PLATFORM_WEIXIN)) {
                            user.connect.weixin = 1;
                        }
                        if (jsonBody.has("shop")) {
                            JSONObject jsonShop = jsonBody.getJSONObject("shop");
                            if (jsonShop.has("state")) {
                                SharedUtil.putInt(context2, UserInfo.LOCAL_OPEN_SHOP_STATE, jsonShop.getInt("state"));
                            }
                            if (jsonShop.has("saletype") && !json.isNull("saletype")) {
                                SharedUtil.getInt(context2, UserInfo.LOCAL_OPEN_SHOP_SALE_TYPE, jsonShop.getInt("state"));
                            }
                        }
                        SharedUtil.putInt(context2, "login_canpublishvideo", user.canpublishvideo);
                        SharedUtil.putString(context2, FengConstant.LATEST_LOGIN_AUTH, str);
                        FengUtil.saveLoginUserInfo(context2, user);
                        EventBus.getDefault().post(new UserLoginEvent(true));
                        if (user.iscomplete == 0) {
                            context2.startActivity(new Intent(context2, GuideActivity.class));
                        }
                        if (successFailCallback != null) {
                            successFailCallback.onSuccess();
                        }
                    } else if (code != -60) {
                        FengApplication.getInstance().checkCode(HttpConstant.CON_LOGIN, code);
                        if (successFailCallback != null) {
                            successFailCallback.onFail();
                        }
                    } else if (!str.equals(FengConstant.LOGIN_PLATFORM_QQ)) {
                        ThirdLoginOperation.this.jumpCheckAddUserOperation(context2, str, str2, str3, str4, str5, str6, successFailCallback);
                    } else if (successFailCallback != null) {
                        successFailCallback.onFail(code);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (successFailCallback != null) {
                        successFailCallback.onFail();
                    }
                    FengApplication.getInstance().upLoadTryCatchLog(HttpConstant.CON_LOGIN, content, e);
                }
            }
        });
    }

    public void addUserOperation(Context context, String connectprov, String connectuid, String wxConnectUnionid, String phoneNum, String code, SuccessFailCallback callback) {
        if (!checkPhoneNum(context, phoneNum)) {
            return;
        }
        if (StringUtil.isEmpty(code)) {
            ((BaseActivity) context).showThirdTypeToast(2131231390);
            return;
        }
        Map<String, Object> map = new HashMap();
        map.put(HttpConstant.CONNECTPROY, connectprov);
        map.put(HttpConstant.CONNECTUID, connectuid);
        if (connectprov.equals(FengConstant.LOGIN_PLATFORM_WEIXIN) && !TextUtils.isEmpty(wxConnectUnionid)) {
            map.put(HttpConstant.CONNECTWXUNIONID, wxConnectUnionid);
        }
        map.put(HttpConstant.PHONENUMBER, phoneNum);
        map.put(HttpConstant.IDENTIFYING_CODE, code);
        doUserConnect(context, map, connectprov, callback);
    }

    public void jumpCheckAddUserOperation(Context context, String connectprov, String connectuid, String wxConnectUnionid, String connectuname, String connectsex, String connectulogo, SuccessFailCallback callback) {
        Map<String, Object> map = new HashMap();
        map.put(HttpConstant.CONNECTPROY, connectprov);
        map.put(HttpConstant.CONNECTUID, connectuid);
        if (connectprov.equals(FengConstant.LOGIN_PLATFORM_WEIXIN) && !TextUtils.isEmpty(wxConnectUnionid)) {
            map.put(HttpConstant.CONNECTWXUNIONID, wxConnectUnionid);
        }
        map.put(HttpConstant.CONNECTNICKNAME, connectuname);
        map.put(HttpConstant.CONNECTLOGO, connectulogo);
        map.put(HttpConstant.CONNECTSEX, connectsex);
        doUserConnect(context, map, connectprov, callback);
    }

    private void doUserConnect(Context context, Map<String, Object> map, String connectProv, SuccessFailCallback callback) {
        if (FengApplication.getInstance().getSeverceState()) {
            String device = LogGatherReadUtil.getInstance().getUniqueId(context);
            if (TextUtils.isEmpty(device)) {
                device = Build.FINGERPRINT;
            }
            map.put(HttpConstant.VISITORDEVICE, device);
            final Context context2 = context;
            final SuccessFailCallback successFailCallback = callback;
            final Map<String, Object> map2 = map;
            final String str = connectProv;
            FengApplication.getInstance().httpRequest(HttpConstant.ADD_USER_CONNECT, map, new OkHttpResponseCallback() {
                public void onNetworkError() {
                    ((BaseActivity) context2).showSecondTypeToast(2131231273);
                }

                public void onStart() {
                    if (successFailCallback != null) {
                        successFailCallback.onStart();
                    }
                }

                public void onFinish() {
                }

                public void onFailure(int statusCode, String content, Throwable error) {
                    ((BaseActivity) context2).showSecondTypeToast(2131231273);
                    if (successFailCallback != null) {
                        successFailCallback.onFail();
                    }
                }

                public void onSuccess(int statusCode, String content) {
                    try {
                        JSONObject jsonResult = new JSONObject(content);
                        int code = jsonResult.getInt("code");
                        if (code == 1) {
                            JSONObject jsonBody = jsonResult.getJSONObject("body");
                            JSONObject json = jsonBody.getJSONObject(HttpConstant.USER);
                            if (!FengApplication.getInstance().isLoginUser()) {
                                EventBus.getDefault().post(new UserLoginEvent(true));
                            }
                            UserInfo user = new UserInfo();
                            user.parser(json);
                            if (map2.containsKey(HttpConstant.PHONENUMBER) && TextUtils.isEmpty(user.phonenumber)) {
                                user.phonenumber = map2.get(HttpConstant.PHONENUMBER).toString();
                            }
                            if (str.equals(FengConstant.LOGIN_PLATFORM_QQ)) {
                                user.connect.qq = 1;
                            } else if (str.equals(FengConstant.LOGIN_PLATFORM_WEIBO)) {
                                user.connect.weibo = 1;
                            } else if (str.equals(FengConstant.LOGIN_PLATFORM_WEIXIN)) {
                                user.connect.weixin = 1;
                            }
                            if (FengApplication.getInstance().isLoginUser()) {
                                FengApplication.getInstance().getUserInfo().followcount.set(user.followcount.get());
                                FengApplication.getInstance().getUserInfo().connect.weibo = user.connect.weibo;
                                FengApplication.getInstance().getUserInfo().connect.qq = user.connect.qq;
                                FengApplication.getInstance().getUserInfo().connect.weixin = user.connect.weixin;
                                if (jsonBody.has("shop")) {
                                    JSONObject jsonShop = jsonBody.getJSONObject("shop");
                                    if (jsonShop.has("state")) {
                                        FengApplication.getInstance().getUserInfo().setLocalOpenShopState(context2, jsonShop.getInt("state"));
                                    }
                                    if (jsonShop.has("saletype") && !json.isNull("saletype")) {
                                        FengApplication.getInstance().getUserInfo().setLocalSaleType(context2, jsonShop.getInt("saletype"));
                                    }
                                }
                                SharedUtil.putInt(context2, "login_canpublishvideo", user.canpublishvideo);
                            }
                            FengUtil.saveLoginUserInfo(context2, user);
                            SharedUtil.putString(context2, FengConstant.LATEST_LOGIN_AUTH, str);
                            if (user.iscomplete == 0) {
                                context2.startActivity(new Intent(context2, GuideActivity.class));
                            } else {
                                SharedUtil.putString(context2, FengConstant.LOGIN_THIRD_INFO, "");
                                if (context2 instanceof SettingAccountSecurityActivity) {
                                    ((BaseActivity) context2).showFirstTypeToast(2131230868);
                                }
                            }
                            if (successFailCallback != null) {
                                successFailCallback.onSuccess();
                                return;
                            }
                            return;
                        }
                        if (successFailCallback != null) {
                            successFailCallback.onFail();
                        }
                        FengApplication.getInstance().checkCode(HttpConstant.ADD_USER_CONNECT, code);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        if (successFailCallback != null) {
                            successFailCallback.onFail();
                        }
                        ((BaseActivity) context2).showSecondTypeToast(2131230867);
                        FengApplication.getInstance().upLoadTryCatchLog(HttpConstant.ADD_USER_CONNECT, content, e);
                    }
                }
            });
            return;
        }
        ((BaseActivity) context).showSecondTypeToast(2131231540);
    }

    public void relieveConnnectOperation(final Context context, final String connectProv, final SuccessFailCallback callback) {
        if (FengApplication.getInstance().getSeverceState()) {
            List<DialogItemEntity> list = new ArrayList();
            list.add(new DialogItemEntity("解除绑定", true));
            CommonDialog.showCommonDialog(context, "", list, new OnDialogItemClickListener() {
                public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                    ThirdLoginOperation.this.doRelieveConnnectOperation(context, connectProv, callback);
                }
            });
            return;
        }
        ((BaseActivity) context).showSecondTypeToast(2131231540);
    }

    private void doRelieveConnnectOperation(final Context context, final String connectProv, final SuccessFailCallback callback) {
        Map<String, Object> map = new HashMap();
        map.put(HttpConstant.CONNECTPROY, connectProv);
        FengApplication.getInstance().httpRequest(HttpConstant.DEL_USER_CONNECT, map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                ((BaseActivity) context).showSecondTypeToast(2131231273);
            }

            public void onStart() {
                if (callback != null) {
                    callback.onStart();
                }
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                ((BaseActivity) context).showSecondTypeToast(2131231273);
                if (callback != null) {
                    callback.onFail();
                }
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    int code = new JSONObject(content).getInt("code");
                    if (code == 1) {
                        if (connectProv.equals(FengConstant.LOGIN_PLATFORM_QQ)) {
                            FengApplication.getInstance().getUserInfo().connect.qq = 0;
                        } else if (connectProv.equals(FengConstant.LOGIN_PLATFORM_WEIBO)) {
                            FengApplication.getInstance().getUserInfo().connect.weibo = 0;
                        } else {
                            FengApplication.getInstance().getUserInfo().connect.weixin = 0;
                        }
                        FengApplication.getInstance().saveUserInfo();
                        ((BaseActivity) context).showFirstTypeToast(2131231677);
                        if (callback != null) {
                            callback.onSuccess();
                            return;
                        }
                        return;
                    }
                    if (code == -63) {
                        ThirdLoginOperation.this.intentToBindPhone(context);
                    } else if (code == HttpConstant.USER_BIND_SHOP) {
                        ((BaseActivity) context).showSecondTypeToast("您已绑定商铺，不能解绑");
                    } else {
                        ((BaseActivity) context).showSecondTypeToast(2131231676);
                    }
                    if (callback != null) {
                        callback.onFail();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (callback != null) {
                        callback.onFail();
                    }
                    ((BaseActivity) context).showSecondTypeToast(2131231676);
                    FengApplication.getInstance().upLoadTryCatchLog(HttpConstant.DEL_USER_CONNECT, content, e);
                }
            }
        });
    }

    public void checkThirdInfo(final Context context, String connectprov, String wxConnectUnionid, String connectuname, String connectsex, String connectulogo, final SuccessFailCallback callback) {
        if (FengApplication.getInstance().getSeverceState()) {
            Map<String, Object> map = new HashMap();
            map.put(HttpConstant.CONNECTPROY, connectprov);
            map.put(HttpConstant.CONNECTWXUNIONID, wxConnectUnionid);
            map.put(HttpConstant.CONNECTNICKNAME, connectuname);
            map.put(HttpConstant.CONNECTLOGO, connectulogo);
            map.put(HttpConstant.CONNECTSEX, connectsex);
            FengApplication.getInstance().httpRequest(HttpConstant.USER_CHECKTHIRDINFO, map, new OkHttpResponseCallback() {
                public void onNetworkError() {
                    ((BaseActivity) context).showSecondTypeToast(2131231273);
                }

                public void onStart() {
                    if (callback != null) {
                        callback.onStart();
                    }
                }

                public void onFinish() {
                }

                public void onFailure(int statusCode, String content, Throwable error) {
                    ((BaseActivity) context).showSecondTypeToast(2131231273);
                    if (callback != null) {
                        callback.onFail();
                    }
                }

                public void onSuccess(int statusCode, String content) {
                    try {
                        int code = new JSONObject(content).getInt("code");
                        if (code == 1) {
                            if (callback != null) {
                                callback.onSuccess();
                            }
                        } else if (callback != null) {
                            callback.onFail(code);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (callback != null) {
                            callback.onFail();
                        }
                    }
                }
            });
            return;
        }
        ((BaseActivity) context).showSecondTypeToast(2131231540);
    }

    private void intentToBindPhone(final Context context) {
        if (FengApplication.getInstance().getSeverceState()) {
            List<DialogItemEntity> list = new ArrayList();
            list.add(new DialogItemEntity("绑定手机号", false));
            CommonDialog.showCommonDialog(context, "该社交账号是老司机目前上车的唯一方式，绑定手机号后就可以进行解绑操作", list, new OnDialogItemClickListener() {
                public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                    Intent intent = new Intent(context, SettingAccountPhoneActivity.class);
                    intent.putExtra(FengConstant.FENGTYPE, 0);
                    context.startActivity(intent);
                }
            });
            return;
        }
        ((BaseActivity) context).showSecondTypeToast(2131231540);
    }

    private boolean checkPhoneNum(Context context, String phoneNum) {
        if (StringUtil.isEmpty(phoneNum)) {
            ((BaseActivity) context).showThirdTypeToast(2131231394);
            return false;
        } else if (StringUtil.isNumber(phoneNum).booleanValue() && phoneNum.length() == 11) {
            return true;
        } else {
            ((BaseActivity) context).showThirdTypeToast(2131231388);
            return false;
        }
    }
}
