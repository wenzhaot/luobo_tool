package com.feng.car.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.databinding.ActivitySettingAccountSecurityBinding;
import com.feng.car.operation.SuccessFailCallback;
import com.feng.car.operation.ThirdLoginOperation;
import com.feng.car.utils.AuthUtil;
import com.feng.car.utils.FengUtil;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONObject;

public class SettingAccountSecurityActivity extends BaseActivity<ActivitySettingAccountSecurityBinding> {
    UMAuthListener mAuthListener = new UMAuthListener() {
        public void onStart(SHARE_MEDIA share_media) {
        }

        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            if (data.containsKey("name")) {
                SettingAccountSecurityActivity.this.mConnectUName = (String) data.get("name");
                if (TextUtils.isEmpty(SettingAccountSecurityActivity.this.mConnectUName)) {
                    SettingAccountSecurityActivity.this.mConnectUName = "";
                }
            }
            if (data.containsKey("gender")) {
                SettingAccountSecurityActivity.this.mConnectUSex = ((String) data.get("gender")).equals("女") ? "2" : "1";
                if (TextUtils.isEmpty(SettingAccountSecurityActivity.this.mConnectUSex)) {
                    SettingAccountSecurityActivity.this.mConnectUSex = "1";
                }
            }
            if (data.containsKey("iconurl")) {
                SettingAccountSecurityActivity.this.mConnectULogo = (String) data.get("iconurl");
                if (TextUtils.isEmpty(SettingAccountSecurityActivity.this.mConnectULogo)) {
                    SettingAccountSecurityActivity.this.mConnectULogo = "";
                }
            }
            if (platform == SHARE_MEDIA.QQ) {
                SettingAccountSecurityActivity.this.mConnectProv = "qq";
                SettingAccountSecurityActivity.this.mConnectUid = (String) data.get("uid");
                if (data.containsKey("accessToken")) {
                    SettingAccountSecurityActivity.this.getQQUid((String) data.get("accessToken"));
                } else if (data.containsKey("access_token")) {
                    SettingAccountSecurityActivity.this.getQQUid((String) data.get("access_token"));
                }
            } else if (platform == SHARE_MEDIA.WEIXIN) {
                SettingAccountSecurityActivity.this.mConnectProv = "weixin";
                SettingAccountSecurityActivity.this.mConnectUid = (String) data.get("openid");
                SettingAccountSecurityActivity.this.mConnectWxUnionId = (String) data.get("unionid");
            } else if (platform == SHARE_MEDIA.SINA) {
                SettingAccountSecurityActivity.this.mConnectProv = "weibo";
                SettingAccountSecurityActivity.this.mConnectUid = (String) data.get("uid");
            }
            if (platform != SHARE_MEDIA.QQ) {
                SettingAccountSecurityActivity.this.addUserConnect();
            }
        }

        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            SettingAccountSecurityActivity.this.showSecondTypeToast((int) R.string.Authorize_fail);
        }

        public void onCancel(SHARE_MEDIA platform, int action) {
            SettingAccountSecurityActivity.this.showSecondTypeToast((int) R.string.Authorize_cancel);
        }
    };
    private String mConnectProv = "";
    private String mConnectULogo = "";
    private String mConnectUName = "";
    private String mConnectUSex = "";
    private String mConnectUid = "";
    private String mConnectWxUnionId = "";
    private ThirdLoginOperation mOperation;

    public int setBaseContentView() {
        return R.layout.activity_setting_account_security;
    }

    public void initView() {
        this.mOperation = new ThirdLoginOperation();
        initNormalTitleBar((int) R.string.account_and_security);
        ((ActivitySettingAccountSecurityBinding) this.mBaseBinding).tvAccountWeChat.setOnClickListener(this);
        ((ActivitySettingAccountSecurityBinding) this.mBaseBinding).tvAccountSina.setOnClickListener(this);
        ((ActivitySettingAccountSecurityBinding) this.mBaseBinding).tvAccountQQ.setOnClickListener(this);
        ((ActivitySettingAccountSecurityBinding) this.mBaseBinding).tvAccountBindPhone.setOnClickListener(this);
        if (FengApplication.getInstance().getUserInfo().connect.weixin != 1 || FengApplication.getInstance().getUserInfo().getLocalOpenShopState() == 1) {
            ((ActivitySettingAccountSecurityBinding) this.mBaseBinding).tvAccountWeChat.setVisibility(0);
        } else {
            ((ActivitySettingAccountSecurityBinding) this.mBaseBinding).tvAccountWeChat.setVisibility(8);
        }
    }

    private void changePhoneState() {
        if (TextUtils.isEmpty(FengApplication.getInstance().getUserInfo().phonenumber)) {
            ((ActivitySettingAccountSecurityBinding) this.mBaseBinding).tvPhoneNumber.setText(R.string.account_bind_not);
            ((ActivitySettingAccountSecurityBinding) this.mBaseBinding).tvAccountBindPhone.setText(R.string.account_bind_phone);
            return;
        }
        String phoneNumber = FengApplication.getInstance().getUserInfo().phonenumber;
        phoneNumber = phoneNumber.replace(phoneNumber.substring(3, 7), "****");
        FengUtil.changeSearchColorByHtml(((ActivitySettingAccountSecurityBinding) this.mBaseBinding).tvPhoneNumber, phoneNumber, "已绑定  " + phoneNumber, ContextCompat.getColor(this, R.color.color_ffb90a));
        ((ActivitySettingAccountSecurityBinding) this.mBaseBinding).tvAccountBindPhone.setText("更换号码");
    }

    private void changeThirdState(TextView textView, int bindState) {
        if (bindState == 1) {
            textView.setText(R.string.account_bind_release);
            textView.setBackgroundResource(R.drawable.color_selector_909090_pressed_404040_4dp);
            return;
        }
        textView.setText(R.string.account_bind);
        textView.setBackgroundResource(R.drawable.color_selector_404040_pressed_191919_4dp);
    }

    protected void onResume() {
        super.onResume();
        changePhoneState();
        changeThirdState(((ActivitySettingAccountSecurityBinding) this.mBaseBinding).tvAccountQQ, FengApplication.getInstance().getUserInfo().connect.qq);
        changeThirdState(((ActivitySettingAccountSecurityBinding) this.mBaseBinding).tvAccountWeChat, FengApplication.getInstance().getUserInfo().connect.weixin);
        changeThirdState(((ActivitySettingAccountSecurityBinding) this.mBaseBinding).tvAccountSina, FengApplication.getInstance().getUserInfo().connect.weibo);
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        UMShareAPI.get(this).onSaveInstanceState(outState);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_account_bind_phone /*2131624633*/:
                Intent intent;
                if (!FengApplication.getInstance().getSeverceState()) {
                    showSecondTypeToast((int) R.string.server_maintain);
                    return;
                } else if (TextUtils.isEmpty(FengApplication.getInstance().getUserInfo().phonenumber)) {
                    intent = new Intent(this, SettingAccountPhoneActivity.class);
                    intent.putExtra("feng_type", 0);
                    startActivity(intent);
                    return;
                } else {
                    intent = new Intent(this, SettingAccountPhoneActivity.class);
                    intent.putExtra("feng_type", 1);
                    startActivity(intent);
                    return;
                }
            case R.id.tv_account_WeChat /*2131624635*/:
                if (!FengApplication.getInstance().getSeverceState()) {
                    showSecondTypeToast((int) R.string.server_maintain);
                    return;
                } else if (FengApplication.getInstance().getUserInfo().connect.weixin == 1) {
                    deleteUserConnnect("weixin");
                    return;
                } else if (UMShareAPI.get(this).isInstall(this, SHARE_MEDIA.WEIXIN)) {
                    AuthUtil.getInfo(this, SHARE_MEDIA.WEIXIN, this.mAuthListener);
                    return;
                } else {
                    showThirdTypeToast((int) R.string.not_install_weixin_tips);
                    return;
                }
            case R.id.tv_account_Sina /*2131624637*/:
                if (!FengApplication.getInstance().getSeverceState()) {
                    showSecondTypeToast((int) R.string.server_maintain);
                    return;
                } else if (FengApplication.getInstance().getUserInfo().connect.weibo == 1) {
                    deleteUserConnnect("weibo");
                    return;
                } else {
                    AuthUtil.getInfo(this, SHARE_MEDIA.SINA, this.mAuthListener);
                    return;
                }
            case R.id.tv_account_QQ /*2131624639*/:
                if (!FengApplication.getInstance().getSeverceState()) {
                    showSecondTypeToast((int) R.string.server_maintain);
                    return;
                } else if (FengApplication.getInstance().getUserInfo().connect.qq == 1) {
                    deleteUserConnnect("qq");
                    return;
                } else {
                    AuthUtil.getInfo(this, SHARE_MEDIA.QQ, this.mAuthListener);
                    return;
                }
            default:
                return;
        }
    }

    private void getQQUid(String access_token) {
        FengApplication.getInstance().httpGetUpdateVersion("https://graph.qq.com/oauth2.0/me?access_token=" + access_token + "&unionid=1", new OkHttpResponseCallback() {
            public void onNetworkError() {
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                SettingAccountSecurityActivity.this.showSecondTypeToast((int) R.string.binding_failed);
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    Matcher matcher = Pattern.compile("\\{(.*)\\}").matcher(content);
                    while (matcher.find()) {
                        SettingAccountSecurityActivity.this.mConnectUid = new JSONObject(matcher.group()).getString("unionid");
                        SettingAccountSecurityActivity.this.addUserConnect();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    SettingAccountSecurityActivity.this.showSecondTypeToast((int) R.string.binding_failed);
                }
            }
        });
    }

    private void addUserConnect() {
        this.mOperation.jumpCheckAddUserOperation(this, this.mConnectProv, this.mConnectUid, this.mConnectWxUnionId, this.mConnectUName, this.mConnectUSex, this.mConnectULogo, new SuccessFailCallback() {
            public void onStart() {
                SettingAccountSecurityActivity.this.showProgress("", "绑定中...");
            }

            public void onSuccess() {
                SettingAccountSecurityActivity.this.changePhoneState();
                SettingAccountSecurityActivity.this.changeThirdState(((ActivitySettingAccountSecurityBinding) SettingAccountSecurityActivity.this.mBaseBinding).tvAccountQQ, FengApplication.getInstance().getUserInfo().connect.qq);
                SettingAccountSecurityActivity.this.changeThirdState(((ActivitySettingAccountSecurityBinding) SettingAccountSecurityActivity.this.mBaseBinding).tvAccountWeChat, FengApplication.getInstance().getUserInfo().connect.weixin);
                SettingAccountSecurityActivity.this.changeThirdState(((ActivitySettingAccountSecurityBinding) SettingAccountSecurityActivity.this.mBaseBinding).tvAccountSina, FengApplication.getInstance().getUserInfo().connect.weibo);
                SettingAccountSecurityActivity.this.hideProgress();
            }

            public void onFail() {
                SettingAccountSecurityActivity.this.hideProgress();
            }
        });
    }

    private void deleteUserConnnect(String connectProv) {
        this.mOperation.relieveConnnectOperation(this, connectProv, new SuccessFailCallback() {
            public void onStart() {
                SettingAccountSecurityActivity.this.showProgress("", "解绑中...");
            }

            public void onSuccess() {
                SettingAccountSecurityActivity.this.changePhoneState();
                SettingAccountSecurityActivity.this.changeThirdState(((ActivitySettingAccountSecurityBinding) SettingAccountSecurityActivity.this.mBaseBinding).tvAccountQQ, FengApplication.getInstance().getUserInfo().connect.qq);
                SettingAccountSecurityActivity.this.changeThirdState(((ActivitySettingAccountSecurityBinding) SettingAccountSecurityActivity.this.mBaseBinding).tvAccountWeChat, FengApplication.getInstance().getUserInfo().connect.weixin);
                SettingAccountSecurityActivity.this.changeThirdState(((ActivitySettingAccountSecurityBinding) SettingAccountSecurityActivity.this.mBaseBinding).tvAccountSina, FengApplication.getInstance().getUserInfo().connect.weibo);
                SettingAccountSecurityActivity.this.hideProgress();
            }

            public void onFail() {
                SettingAccountSecurityActivity.this.hideProgress();
            }
        });
    }
}
