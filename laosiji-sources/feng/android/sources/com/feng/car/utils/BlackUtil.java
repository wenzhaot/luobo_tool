package com.feng.car.utils;

import android.content.Context;
import com.feng.car.FengApplication;
import com.feng.car.activity.BaseActivity;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.event.UserInfoRefreshEvent;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.feng.library.utils.SharedUtil;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

public class BlackUtil {
    public static final String BLACK_KEY = "black_key";
    public static BlackUtil instance;
    private boolean isSuccess = false;
    public List<UserInfo> userInfoList = new ArrayList();

    public static BlackUtil getInstance() {
        if (instance == null) {
            instance = new BlackUtil();
        }
        return instance;
    }

    public void getBlackListSuccess() {
        this.isSuccess = true;
    }

    public boolean isGetBlackListSuccess() {
        return this.isSuccess;
    }

    public List<UserInfo> getBlackList() {
        return this.userInfoList;
    }

    public void addUserToBlack(UserInfo user) {
        if (user != null) {
            UserInfo info = new UserInfo();
            info.id = user.id;
            info.isblack.set(1);
            this.userInfoList.add(info);
        }
    }

    public void removeUserFromBlack(UserInfo user) {
        if (this.userInfoList.size() > 0) {
            int i = 0;
            while (i < this.userInfoList.size()) {
                UserInfo info = (UserInfo) this.userInfoList.get(i);
                if (info == null || info.id != user.id) {
                    i++;
                } else {
                    this.userInfoList.remove(i);
                    return;
                }
            }
        }
    }

    public boolean isInBlackList(UserInfo user) {
        if (this.userInfoList.size() > 0) {
            for (int i = 0; i < this.userInfoList.size(); i++) {
                UserInfo info = (UserInfo) this.userInfoList.get(i);
                if (info != null && user.id == info.id) {
                    return true;
                }
            }
        }
        return false;
    }

    public void clearBlackList(Context context) {
        this.userInfoList.clear();
        SharedUtil.putString(context, BLACK_KEY, "");
    }

    public void addAllBlackList(List<UserInfo> list, Context context) {
        this.userInfoList.clear();
        for (int i = 0; i < list.size(); i++) {
            UserInfo user = (UserInfo) list.get(i);
            if (user != null) {
                UserInfo info = new UserInfo();
                info.id = user.id;
                info.isblack.set(1);
                this.userInfoList.add(info);
            }
        }
        saveBlackToLocal(context);
    }

    public void saveBlackToLocal(Context context) {
        try {
            String json = new GsonBuilder().create().toJson(this.userInfoList);
            if (json != null) {
                SharedUtil.putString(context, BLACK_KEY, json);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getBlackFromLocal(Context context) {
        try {
            String json = SharedUtil.getString(context, BLACK_KEY);
            if (json != null) {
                this.userInfoList = (List) new GsonBuilder().create().fromJson(json, new TypeToken<UserInfo>() {
                }.getType());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addOrRemoveBlack(final Context context, final UserInfo info, final BlackExcuteCallBack callBack) {
        Map<String, Object> map = new HashMap();
        map.put("userid", String.valueOf(info.id));
        if (info.isblack.get() == 0) {
            map.put("type", String.valueOf(1));
        } else {
            map.put("type", String.valueOf(0));
        }
        FengApplication.getInstance().httpRequest(HttpConstant.USER_BLACKUSER, map, new OkHttpResponseCallback() {
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
                        if (info.isblack.get() == 0) {
                            if (info.isfollow.get() == 2) {
                                info.followcount.set(info.followcount.get() - 1);
                                info.fanscount.set(info.fanscount.get() - 1);
                                FengApplication.getInstance().addFollowState(info.id, false);
                            } else if (info.isfollow.get() == 1) {
                                info.fanscount.set(info.fanscount.get() - 1);
                                FengApplication.getInstance().addFollowState(info.id, false);
                            } else if (info.isfans == 1) {
                                info.followcount.set(info.followcount.get() - 1);
                                info.isfans = 0;
                            }
                            info.isblack.set(1);
                            BlackUtil.this.addUserToBlack(info);
                            if (callBack != null) {
                                callBack.onAddBlackSuccess();
                            }
                        } else {
                            info.isblack.set(0);
                            BlackUtil.this.removeUserFromBlack(info);
                            if (callBack != null) {
                                callBack.onRemoveBlackSuccess();
                            }
                        }
                        info.isfollow.set(0);
                        EventBus.getDefault().post(new UserInfoRefreshEvent(info));
                    } else if (code == -5) {
                        ((BaseActivity) context).showSecondTypeToast(2131230795);
                    } else {
                        FengApplication.getInstance().checkCode(HttpConstant.USER_BLACKUSER, code);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ((BaseActivity) context).showSecondTypeToast(2131231273);
                    FengApplication.getInstance().upLoadTryCatchLog(HttpConstant.USER_BLACKUSER, content, e);
                }
            }
        });
    }

    public void getBlackListData(final Context context, final BlackListRequestCallBack callback) {
        if (!FengApplication.getInstance().isLoginUser()) {
            return;
        }
        if (!FengApplication.getInstance().isLoginUser() || FengApplication.getInstance().getUserInfo().id != 0) {
            Map<String, Object> map = new HashMap();
            map.put(HttpConstant.PAGE, String.valueOf(1));
            if (FengApplication.getInstance().isLoginUser()) {
                map.put("userid", String.valueOf(FengApplication.getInstance().getUserInfo().id));
            }
            FengApplication.getInstance().httpRequest(HttpConstant.USER_BLACKLIST, map, new OkHttpResponseCallback() {
                public void onNetworkError() {
                    if (callback != null) {
                        callback.onBlackListFailure();
                    }
                }

                public void onStart() {
                }

                public void onFinish() {
                }

                public void onFailure(int statusCode, String content, Throwable error) {
                    if (callback != null) {
                        callback.onBlackListFailure();
                    }
                }

                public void onSuccess(int statusCode, String content) {
                    try {
                        JSONObject jsonResult = new JSONObject(content);
                        int code = jsonResult.getInt("code");
                        if (code == 1) {
                            JSONObject jsonUser = jsonResult.getJSONObject("body").getJSONObject(HttpConstant.USER);
                            BaseListModel<UserInfo> baseListModel = new BaseListModel();
                            baseListModel.parser(UserInfo.class, jsonUser);
                            if (baseListModel.list.size() > 0) {
                                BlackUtil.this.addAllBlackList(baseListModel.list, context);
                            }
                            if (!BlackUtil.this.isGetBlackListSuccess()) {
                                BlackUtil.this.getBlackListSuccess();
                            }
                            if (callback != null) {
                                callback.onBlackListSuccess();
                                return;
                            }
                            return;
                        }
                        FengApplication.getInstance().upLoadLog(true, "user/blacklist/  " + code);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        FengApplication.getInstance().upLoadTryCatchLog(HttpConstant.USER_BLACKLIST, content, e);
                    }
                }
            });
        }
    }
}
