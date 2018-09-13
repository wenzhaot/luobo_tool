package com.feng.car.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.view.Window;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.databinding.LoginBy5xBinding;
import com.feng.car.databinding.LoginBy5xExplainBinding;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.event.ClosePageEvent;
import com.feng.car.event.UserLoginEvent;
import com.feng.car.utils.ApiSecurityUtils;
import com.feng.car.utils.FengUtil;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.feng.library.utils.SharedUtil;
import com.feng.library.utils.StringUtil;
import java.util.HashMap;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginBy5X extends BaseActivity<LoginBy5xBinding> {
    private LoginBy5xExplainBinding mLoginBy5xExplainBinding;
    private Dialog mProgressDialog;

    public int setBaseContentView() {
        return R.layout.login_by_5x;
    }

    public void initView() {
        initNormalTitleBar("");
        ((LoginBy5xBinding) this.mBaseBinding).tvLogin.setOnClickListener(this);
        ((LoginBy5xBinding) this.mBaseBinding).ivAsk5x.setOnClickListener(this);
    }

    public void onSingleClick(View v) {
        super.onSingleClick(v);
        switch (v.getId()) {
            case R.id.tv_login /*2131624388*/:
                login();
                return;
            case R.id.iv_ask_5x /*2131625255*/:
                showProgressDialog();
                return;
            case R.id.iv_5x_btn /*2131625256*/:
                if (this.mProgressDialog != null && this.mProgressDialog.isShowing()) {
                    this.mProgressDialog.dismiss();
                    return;
                }
                return;
            default:
                return;
        }
    }

    private void showProgressDialog() {
        if (this.mLoginBy5xExplainBinding == null) {
            this.mLoginBy5xExplainBinding = LoginBy5xExplainBinding.inflate(this.mInflater);
        }
        if (this.mProgressDialog == null) {
            this.mProgressDialog = new Dialog(this, R.style.ArticleShareDialog);
            this.mProgressDialog.setCanceledOnTouchOutside(true);
            this.mProgressDialog.setCancelable(false);
            Window window = this.mProgressDialog.getWindow();
            window.setGravity(17);
            window.setWindowAnimations(R.style.shareAnimation);
            window.setContentView(this.mLoginBy5xExplainBinding.getRoot());
            window.setLayout(-1, -1);
        }
        this.mProgressDialog.show();
        this.mLoginBy5xExplainBinding.iv5xBtn.setOnClickListener(this);
    }

    private void login() {
        String mobile = ((LoginBy5xBinding) this.mBaseBinding).etLoginAccount.getText().toString().trim();
        String password = ApiSecurityUtils.encryptUserPwd(((LoginBy5xBinding) this.mBaseBinding).etPwd.getText().toString().trim());
        boolean isPhone = true;
        if (StringUtil.isEmpty(mobile)) {
            showThirdTypeToast((int) R.string.please_input_phone_number_or_email);
            return;
        }
        if (mobile.length() != 11) {
            if (StringUtil.isEmail(mobile).booleanValue()) {
                isPhone = false;
            } else {
                showThirdTypeToast((int) R.string.please_input_right_phone_number_or_email);
                return;
            }
        } else if (!StringUtil.isNumber(mobile).booleanValue()) {
            if (StringUtil.isEmail(mobile).booleanValue()) {
                isPhone = false;
            } else {
                showThirdTypeToast((int) R.string.please_input_right_phone_number_or_email);
                return;
            }
        }
        if (StringUtil.isEmpty(password)) {
            showThirdTypeToast((int) R.string.please_input_password);
            return;
        }
        Map<String, Object> map = new HashMap();
        if (isPhone) {
            map.put("phonenumber", mobile);
        } else {
            map.put(NotificationCompat.CATEGORY_EMAIL, mobile);
        }
        map.put("userpwd", password);
        showProgress("", "请稍候...");
        FengApplication.getInstance().httpRequest("user/login/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                LoginBy5X.this.showSecondTypeToast((int) R.string.net_abnormal);
                LoginBy5X.this.hideProgress();
            }

            public void onStart() {
            }

            public void onFinish() {
                LoginBy5X.this.hideProgress();
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                LoginBy5X.this.showSecondTypeToast((int) R.string.net_abnormal);
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    int code = jsonResult.getInt("code");
                    if (code == 1) {
                        JSONObject jsonBody = jsonResult.getJSONObject("body");
                        JSONObject json = jsonBody.getJSONObject("user");
                        UserInfo user = new UserInfo();
                        user.parser(json);
                        user.connect.login_5x = 1;
                        SharedUtil.putString(LoginBy5X.this, "latest_login_auth", "5x");
                        if (jsonBody.has("shop") && jsonBody.getJSONObject("shop").has("state")) {
                            SharedUtil.putInt(LoginBy5X.this, "local_open_shop_state", jsonBody.getJSONObject("shop").getInt("state"));
                        }
                        SharedUtil.putInt(LoginBy5X.this, "login_canpublishvideo", user.canpublishvideo);
                        FengUtil.saveLoginUserInfo(LoginBy5X.this, user);
                        if (user.iscomplete == 0) {
                            LoginBy5X.this.startActivity(new Intent(LoginBy5X.this, GuideActivity.class));
                        }
                        EventBus.getDefault().post(new UserLoginEvent(true));
                        EventBus.getDefault().post(new ClosePageEvent());
                        LoginBy5X.this.finish();
                        return;
                    }
                    FengApplication.getInstance().checkCode("user/login/", code);
                } catch (JSONException e) {
                    e.printStackTrace();
                    LoginBy5X.this.showThirdTypeToast((int) R.string.login_failed);
                    LoginBy5X.this.hideProgress();
                    FengApplication.getInstance().upLoadTryCatchLog("user/login/", content, e);
                }
            }
        });
    }
}
