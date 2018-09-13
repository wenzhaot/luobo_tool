package com.feng.car.operation;

import android.content.Context;
import android.content.Intent;
import com.feng.car.FengApplication;
import com.feng.car.activity.BaseActivity;
import com.feng.car.activity.LoginActivity;
import com.feng.car.activity.PersonalHomePageNewActivity;
import com.feng.car.activity.PostInitActivity;
import com.feng.car.activity.WebActivity;
import com.feng.car.adapter.CommonBottomDialogAdapter.OnDialogItemClickListener;
import com.feng.car.entity.DialogItemEntity;
import com.feng.car.entity.circle.CircleInfo;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.event.UserInfoRefreshEvent;
import com.feng.car.utils.BlackUtil;
import com.feng.car.utils.BlackUtil.BlackExcuteCallBack;
import com.feng.car.utils.FengConstant;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.HttpConstant;
import com.feng.car.utils.JsonUtil;
import com.feng.car.view.CommonDialog;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

public class UserOperation {
    private UserInfo mUserInfo;

    public UserOperation(UserInfo info) {
        this.mUserInfo = info;
    }

    public void intentToPersonalHome(Context context, int type) {
        Intent intent = new Intent(context, PersonalHomePageNewActivity.class);
        intent.putExtra("userid", this.mUserInfo.id);
        intent.putExtra(FengConstant.FENGTYPE, type);
        context.startActivity(intent);
    }

    public void intentToSendPost(Context context, CircleInfo circleInfo) {
        if (FengApplication.getInstance().getSeverceState()) {
            checkUserState(context, circleInfo);
        } else {
            ((BaseActivity) context).showSecondTypeToast(2131231540);
        }
    }

    public void followOperation(final Context context, final OperationCallback callback) {
        List<DialogItemEntity> list = new ArrayList();
        if (BlackUtil.getInstance().isInBlackList(this.mUserInfo)) {
            list.add(new DialogItemEntity(context.getString(2131231477), false));
            CommonDialog.showCommonDialog(context, context.getString(2131231478), list, new OnDialogItemClickListener() {
                public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                    UserOperation.this.removeBlackAndAddFollow(context, callback);
                }
            });
        } else if (this.mUserInfo.isfollow.get() == 1 || this.mUserInfo.isfollow.get() == 2) {
            list.add(new DialogItemEntity(context.getString(2131230812), false));
            CommonDialog.showCommonDialog(context, context.getString(2131231681), list, new OnDialogItemClickListener() {
                public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                    UserOperation.this.doFollow(context, callback);
                }
            });
        } else {
            doFollow(context, callback);
        }
    }

    private void removeBlackAndAddFollow(final Context context, final OperationCallback callback) {
        Map<String, Object> map = new HashMap();
        map.put("userid", String.valueOf(this.mUserInfo.id));
        FengApplication.getInstance().httpRequest(HttpConstant.USER_DELANDFOLLOW, map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                if (callback != null) {
                    callback.onNetworkError();
                } else {
                    ((BaseActivity) context).showSecondTypeToast(2131231273);
                }
            }

            public void onStart() {
                if (callback != null) {
                    callback.onStart();
                }
            }

            public void onFinish() {
                if (callback != null) {
                    callback.onFinish();
                }
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (callback != null) {
                    callback.onFailure(statusCode, content, error);
                } else {
                    ((BaseActivity) context).showSecondTypeToast(2131231273);
                }
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject resultJson = new JSONObject(content);
                    if (callback != null) {
                        callback.onSuccess(content);
                        return;
                    }
                    int code = resultJson.getInt("code");
                    if (code == 1) {
                        UserOperation.this.mUserInfo.isblack.set(0);
                        UserOperation.this.mUserInfo.isfollow.set(1);
                        BlackUtil.getInstance().removeUserFromBlack(UserOperation.this.mUserInfo);
                        ((BaseActivity) context).showFirstTypeToast(2131231544);
                        FengApplication.getInstance().addFollowState(UserOperation.this.mUserInfo.id, true);
                        EventBus.getDefault().post(new UserInfoRefreshEvent(UserOperation.this.mUserInfo));
                    } else if (code == -5) {
                        ((BaseActivity) context).showSecondTypeToast(2131230795);
                    } else {
                        FengApplication.getInstance().checkCode(HttpConstant.USER_FOLLOWUSER, code);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ((BaseActivity) context).showSecondTypeToast(2131231273);
                    FengApplication.getInstance().upLoadTryCatchLog(HttpConstant.USER_FOLLOWUSER, content, e);
                }
            }
        });
    }

    private void doFollow(final Context context, final OperationCallback callback) {
        int type;
        Map<String, Object> map = new HashMap();
        map.put("userid", String.valueOf(this.mUserInfo.id));
        if (this.mUserInfo.isfollow.get() == 0) {
            type = 1;
        } else {
            type = 0;
        }
        map.put("type", String.valueOf(type));
        FengApplication.getInstance().httpRequest(HttpConstant.USER_FOLLOWUSER, map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                if (callback != null) {
                    callback.onNetworkError();
                } else {
                    ((BaseActivity) context).showSecondTypeToast(2131231273);
                }
            }

            public void onStart() {
                if (callback != null) {
                    callback.onStart();
                }
            }

            public void onFinish() {
                if (callback != null) {
                    callback.onFinish();
                }
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (callback != null) {
                    callback.onFailure(statusCode, content, error);
                } else {
                    ((BaseActivity) context).showSecondTypeToast(2131231273);
                }
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject resultJson = new JSONObject(content);
                    if (callback != null) {
                        callback.onSuccess(content);
                        return;
                    }
                    int code = resultJson.getInt("code");
                    if (code == 1) {
                        int isFollow = resultJson.getJSONObject("body").getInt(HttpConstant.ISFOLLOW);
                        if (isFollow == 0) {
                            UserOperation.this.mUserInfo.fanscount.set(UserOperation.this.mUserInfo.fanscount.get() - 1);
                            ((BaseActivity) context).showFirstTypeToast(2131231543);
                            FengApplication.getInstance().addFollowState(UserOperation.this.mUserInfo.id, false);
                        } else {
                            UserOperation.this.mUserInfo.fanscount.set(UserOperation.this.mUserInfo.fanscount.get() + 1);
                            ((BaseActivity) context).showFirstTypeToast(2131231544);
                            FengApplication.getInstance().addFollowState(UserOperation.this.mUserInfo.id, true);
                        }
                        UserOperation.this.mUserInfo.isfollow.set(isFollow);
                        EventBus.getDefault().post(new UserInfoRefreshEvent(UserOperation.this.mUserInfo));
                    } else if (code == -5) {
                        ((BaseActivity) context).showSecondTypeToast(2131230795);
                    } else {
                        FengApplication.getInstance().checkCode(HttpConstant.USER_FOLLOWUSER, code);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ((BaseActivity) context).showSecondTypeToast(2131231273);
                    FengApplication.getInstance().upLoadTryCatchLog(HttpConstant.USER_FOLLOWUSER, content, e);
                }
            }
        });
    }

    public void reportOperation(Context context) {
        if (FengApplication.getInstance().isLoginUser()) {
            Intent intent = new Intent(context, WebActivity.class);
            intent.putExtra("title", "举报");
            intent.putExtra("url", FengUtil.getReportUrl(context, 4, this.mUserInfo.id, this.mUserInfo.id));
            context.startActivity(intent);
            return;
        }
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    public void updateInfoOperation(Map<String, Object> map, final OperationCallback callback) {
        FengApplication.getInstance().httpRequest(HttpConstant.USER_UPDATE, map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                if (callback != null) {
                    callback.onNetworkError();
                }
            }

            public void onStart() {
                if (callback != null) {
                    callback.onStart();
                }
            }

            public void onFinish() {
                if (callback != null) {
                    callback.onFinish();
                }
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (callback != null) {
                    callback.onFailure(statusCode, content, error);
                }
            }

            public void onSuccess(int statusCode, String content) {
                if (callback != null) {
                    callback.onSuccess(content);
                }
            }
        });
    }

    public void blackOperation(final Context context, final BlackExcuteCallBack callBack) {
        if (!FengApplication.getInstance().isLoginUser()) {
            context.startActivity(new Intent(context, LoginActivity.class));
        } else if (this.mUserInfo.isblack.get() == 0) {
            List<DialogItemEntity> listData = new ArrayList();
            listData.add(new DialogItemEntity("加入黑名单", false));
            CommonDialog.showCommonDialog(context, context.getString(2131230798), listData, new OnDialogItemClickListener() {
                public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                    BlackUtil.getInstance().addOrRemoveBlack(context, UserOperation.this.mUserInfo, callBack);
                }
            });
        } else {
            BlackUtil.getInstance().addOrRemoveBlack(context, this.mUserInfo, callBack);
        }
    }

    private void checkUserState(Context context, CircleInfo circleInfo) {
        Intent intent = new Intent(context, PostInitActivity.class);
        if (circleInfo != null) {
            intent.putExtra(FengConstant.SEND_POST_FORM_CIRCLE, JsonUtil.toJson((Object) circleInfo));
        }
        context.startActivity(intent);
    }

    public void checkShopState(final Context context, final SuccessFailCallback callback) {
        FengApplication.getInstance().httpRequest(HttpConstant.SHOP_STATE, new HashMap(), new OkHttpResponseCallback() {
            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonObject = new JSONObject(content);
                    int code = jsonObject.getInt("code");
                    if (code == 1) {
                        JSONObject body = jsonObject.getJSONObject("body");
                        if (body.has("state")) {
                            FengApplication.getInstance().getUserInfo().setLocalOpenShopState(context, body.getInt("state"));
                        }
                        if (body.has("saletype")) {
                            FengApplication.getInstance().getUserInfo().setLocalSaleType(context, body.getInt("saletype"));
                            return;
                        }
                        return;
                    }
                    FengApplication.getInstance().upLoadLog(true, "user/state/  " + code);
                } catch (Exception e) {
                    FengApplication.getInstance().upLoadTryCatchLog(HttpConstant.USER_STATE, content, e);
                }
            }

            public void onNetworkError() {
                if (callback != null) {
                    callback.onSuccess();
                }
            }

            public void onStart() {
            }

            public void onFinish() {
                if (callback != null) {
                    callback.onSuccess();
                }
            }

            public void onFailure(int statusCode, String content, Throwable error) {
            }
        });
    }
}
