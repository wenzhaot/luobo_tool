package com.feng.car.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.CommonBottomDialogAdapter.OnDialogItemClickListener;
import com.feng.car.databinding.ActivityLoginBinding;
import com.feng.car.entity.DialogItemEntity;
import com.feng.car.event.ClosePageEvent;
import com.feng.car.operation.SuccessFailCallback;
import com.feng.car.operation.ThirdLoginOperation;
import com.feng.car.utils.AuthUtil;
import com.feng.car.utils.JsonUtil;
import com.feng.car.view.CommonDialog;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.feng.library.utils.SharedUtil;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {
    public static final int STATE_FIRST = 0;
    public static final int STATE_SECOND = 1;
    private final int CHANGE_TIME = 2;
    private final int RESET_TIME = 1;
    private final int START_TIME = 0;
    private String mConnectProv = "";
    private String mConnectULogo = "";
    private String mConnectUName = "";
    private String mConnectUSex = "";
    private String mConnectUid = "";
    private String mConnectWxUnionId = "";
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    LoginActivity.this.mTime = 60;
                    LoginActivity.this.startTime();
                    sendEmptyMessage(2);
                    return;
                case 1:
                    LoginActivity.this.mTime = 60;
                    LoginActivity.this.resetVerifyText(LoginActivity.this.getString(R.string.send_code));
                    LoginActivity.this.mHandler.removeCallbacksAndMessages(null);
                    return;
                case 2:
                    if (LoginActivity.this.mTime <= 0) {
                        LoginActivity.this.resetVerifyText(LoginActivity.this.getString(R.string.again_send));
                        return;
                    }
                    ((ActivityLoginBinding) LoginActivity.this.mBaseBinding).tvLoginSendVerify.setText(LoginActivity.this.getString(R.string.resendLeftTime, new Object[]{Integer.valueOf(LoginActivity.this.mTime)}));
                    LoginActivity.this.mTime = LoginActivity.this.mTime - 1;
                    sendEmptyMessageDelayed(2, 1000);
                    return;
                default:
                    return;
            }
        }
    };
    private ThirdLoginOperation mOperation;
    UMAuthListener mPlatformInfoListener = new UMAuthListener() {
        public void onStart(SHARE_MEDIA share_media) {
        }

        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            if (data.containsKey("name")) {
                LoginActivity.this.mConnectUName = (String) data.get("name");
                if (TextUtils.isEmpty(LoginActivity.this.mConnectUName)) {
                    LoginActivity.this.mConnectUName = "";
                }
            }
            if (data.containsKey("gender")) {
                LoginActivity.this.mConnectUSex = ((String) data.get("gender")).equals("女") ? "2" : "1";
                if (TextUtils.isEmpty(LoginActivity.this.mConnectUSex)) {
                    LoginActivity.this.mConnectUSex = "1";
                }
            }
            if (data.containsKey("iconurl")) {
                LoginActivity.this.mConnectULogo = (String) data.get("iconurl");
                if (TextUtils.isEmpty(LoginActivity.this.mConnectULogo)) {
                    LoginActivity.this.mConnectULogo = "";
                }
            }
            Map<String, String> map = new HashMap();
            map.put("name", LoginActivity.this.mConnectUName);
            map.put("sex", LoginActivity.this.mConnectUSex);
            map.put("url", LoginActivity.this.mConnectULogo);
            SharedUtil.putString(LoginActivity.this, "login_third_info", JsonUtil.toJson(map));
            if (platform == SHARE_MEDIA.QQ) {
                LoginActivity.this.mConnectProv = "qq";
                LoginActivity.this.mConnectUid = (String) data.get("uid");
                if (data.containsKey("accessToken")) {
                    LoginActivity.this.getQQUid((String) data.get("accessToken"));
                } else if (data.containsKey("access_token")) {
                    LoginActivity.this.getQQUid((String) data.get("access_token"));
                }
            } else if (platform == SHARE_MEDIA.WEIXIN) {
                LoginActivity.this.mConnectProv = "weixin";
                LoginActivity.this.mConnectUid = (String) data.get("openid");
                LoginActivity.this.mConnectWxUnionId = (String) data.get("unionid");
            } else if (platform == SHARE_MEDIA.SINA) {
                LoginActivity.this.mConnectProv = "weibo";
                LoginActivity.this.mConnectUid = (String) data.get("uid");
            }
            if (platform != SHARE_MEDIA.QQ) {
                LoginActivity.this.checkIsBind();
            }
        }

        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            LoginActivity.this.showSecondTypeToast((int) R.string.Authorize_fail);
            t.printStackTrace();
        }

        public void onCancel(SHARE_MEDIA platform, int action) {
            LoginActivity.this.showSecondTypeToast((int) R.string.Authorize_cancel);
        }
    };
    private int mTime = 60;
    private int mType = 0;

    public int setBaseContentView() {
        return R.layout.activity_login;
    }

    public void getLocalIntentData() {
        Intent intent = getIntent();
        this.mType = intent.getIntExtra("feng_type", 0);
        this.mConnectProv = intent.getStringExtra("connectprov");
        this.mConnectUid = intent.getStringExtra("connectuid");
        if (intent.hasExtra("video_bd_login_flag")) {
            int flag = intent.getIntExtra("video_bd_login_flag", 0);
            if (flag == 1) {
                FengApplication.getInstance().setVideoBdLoginFlag(1);
            } else if (flag == 2) {
                FengApplication.getInstance().setVideoBdLoginFlag(2);
            }
        }
        if (intent.hasExtra("name")) {
            this.mConnectUName = intent.getStringExtra("name");
            this.mConnectUSex = intent.getStringExtra("sex");
            this.mConnectULogo = intent.getStringExtra("url");
        }
    }

    public void initView() {
        initNormalTitleBar("");
        if (this.mType == 0) {
            SharedUtil.putString(this, "login_third_info", "");
            changeLeftIcon(R.drawable.icon_close);
        } else {
            ((ActivityLoginBinding) this.mBaseBinding).etLoginNumber.setHint(R.string.account_phone_bind);
            ((ActivityLoginBinding) this.mBaseBinding).tvLogin.setText("验证");
        }
        this.mOperation = new ThirdLoginOperation();
        closeSwip();
        ((ActivityLoginBinding) this.mBaseBinding).tvLogin.setOnClickListener(this);
        ((ActivityLoginBinding) this.mBaseBinding).tvLoginSendVerify.setOnClickListener(this);
        if (this.mType == 0) {
            ((ActivityLoginBinding) this.mBaseBinding).ivLoginWeibo.setOnClickListener(this);
            ((ActivityLoginBinding) this.mBaseBinding).ivLoginWechat.setOnClickListener(this);
            ((ActivityLoginBinding) this.mBaseBinding).ivLoginQq.setOnClickListener(this);
            ((ActivityLoginBinding) this.mBaseBinding).ivLogin5x.setOnClickListener(this);
            String authPlatform = SharedUtil.getString(this, "latest_login_auth");
            if (authPlatform.equals("weibo")) {
                ((ActivityLoginBinding) this.mBaseBinding).tvLoginLatestUseWeibo.setVisibility(0);
                return;
            } else if (authPlatform.equals("weixin")) {
                ((ActivityLoginBinding) this.mBaseBinding).tvLoginLatestUseWechat.setVisibility(0);
                return;
            } else if (authPlatform.equals("qq")) {
                ((ActivityLoginBinding) this.mBaseBinding).tvLoginLatestUseQq.setVisibility(0);
                return;
            } else if (authPlatform.equals("5x")) {
                ((ActivityLoginBinding) this.mBaseBinding).tvLoginLatestUse5x.setVisibility(0);
                return;
            } else {
                return;
            }
        }
        ((ActivityLoginBinding) this.mBaseBinding).llLoginAuth.setVisibility(8);
        ((ActivityLoginBinding) this.mBaseBinding).llThirdLogin.setVisibility(8);
        ((ActivityLoginBinding) this.mBaseBinding).ivLoginLogo.setVisibility(8);
        ((ActivityLoginBinding) this.mBaseBinding).tvLoginWelcome.setVisibility(8);
        ((ActivityLoginBinding) this.mBaseBinding).tvPhoneCheck.setVisibility(0);
        ((ActivityLoginBinding) this.mBaseBinding).tvVerifyPhoneTip.setVisibility(0);
        if ((this.mConnectProv.equals("qq") && FengApplication.getInstance().getQQSkipPhoneBind()) || ((this.mConnectProv.equals("weixin") && FengApplication.getInstance().getWeiXinSkipPhoneBind()) || (this.mConnectProv.equals("weibo") && FengApplication.getInstance().getWeiBoSkipPhoneBind()))) {
            ((ActivityLoginBinding) this.mBaseBinding).tvSkipVerifyPhone.setVisibility(0);
            ((ActivityLoginBinding) this.mBaseBinding).tvSkipVerifyPhone.setOnClickListener(this);
        }
    }

    public void onSingleClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login_send_verify /*2131624387*/:
                sendCode();
                return;
            case R.id.tv_login /*2131624388*/:
                if (this.mType == 0) {
                    login();
                    return;
                } else if (this.mType == 1) {
                    addUserConnect();
                    return;
                } else {
                    return;
                }
            case R.id.tv_skip_verifyPhone /*2131624389*/:
                showSkipVerifyDialog();
                MobclickAgent.onEvent(this, "login_NoProving");
                return;
            case R.id.iv_login_5x /*2131624393*/:
                startActivity(new Intent(this, LoginBy5X.class));
                return;
            case R.id.iv_login_wechat /*2131624396*/:
                if (UMShareAPI.get(this).isInstall(this, SHARE_MEDIA.WEIXIN)) {
                    AuthUtil.getInfo(this, SHARE_MEDIA.WEIXIN, this.mPlatformInfoListener);
                    MobclickAgent.onEvent(this, "login_wechat");
                    return;
                }
                showThirdTypeToast((int) R.string.not_install_weixin_tips);
                return;
            case R.id.iv_login_weibo /*2131624399*/:
                AuthUtil.getInfo(this, SHARE_MEDIA.SINA, this.mPlatformInfoListener);
                MobclickAgent.onEvent(this, "login_weibo");
                return;
            case R.id.iv_login_qq /*2131624401*/:
                if (UMShareAPI.get(this).isInstall(this, SHARE_MEDIA.QQ)) {
                    AuthUtil.getInfo(this, SHARE_MEDIA.QQ, this.mPlatformInfoListener);
                    MobclickAgent.onEvent(this, "login_QQ");
                    return;
                }
                showThirdTypeToast((int) R.string.not_install_qq_tips);
                return;
            default:
                return;
        }
    }

    private void showSkipVerifyDialog() {
        List<DialogItemEntity> list = new ArrayList();
        list.add(new DialogItemEntity(getString(R.string.thinkAgain), false));
        list.add(new DialogItemEntity(getString(R.string.skipAnyway), false));
        CommonDialog.showCommonDialog(this, "如果您使用的手机号在5X兴趣社区中注册过\n跳过此步后就会产生新的账号", "", list, new OnDialogItemClickListener() {
            public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                if (position == 0) {
                    MobclickAgent.onEvent(LoginActivity.this, "login_NoProving_think");
                } else if (position == 1) {
                    LoginActivity.this.notifyServerCreateNewUser();
                    MobclickAgent.onEvent(LoginActivity.this, "login_NoProving_skip");
                }
            }
        }, false);
    }

    private void login() {
        this.mOperation.loginOperation(this, ((ActivityLoginBinding) this.mBaseBinding).etLoginNumber.getText().toString(), ((ActivityLoginBinding) this.mBaseBinding).etLoginVerify.getText().toString().trim(), new SuccessFailCallback() {
            public void onStart() {
                LoginActivity.this.showProgress("", "请稍候...");
            }

            public void onSuccess() {
                LoginActivity.this.hideProgress();
                LoginActivity.this.finish();
            }

            public void onFail() {
                LoginActivity.this.hideProgress();
            }
        });
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        UMShareAPI.get(this).onSaveInstanceState(outState);
    }

    private void resetVerifyText(String strText) {
        ((ActivityLoginBinding) this.mBaseBinding).tvLoginSendVerify.setEnabled(true);
        ((ActivityLoginBinding) this.mBaseBinding).tvLoginSendVerify.setText(strText);
        ((ActivityLoginBinding) this.mBaseBinding).tvLoginSendVerify.setBackgroundResource(R.drawable.border_404040_pressed_191919_4dp);
        ((ActivityLoginBinding) this.mBaseBinding).tvLoginSendVerify.setTextColor(this.mResources.getColorStateList(R.color.selector_404040_pressed_191919));
    }

    private void startTime() {
        ((ActivityLoginBinding) this.mBaseBinding).tvLoginSendVerify.setEnabled(false);
        ((ActivityLoginBinding) this.mBaseBinding).tvLoginSendVerify.setTextColor(ContextCompat.getColor(this, R.color.color_38_000000));
        ((ActivityLoginBinding) this.mBaseBinding).tvLoginSendVerify.setBackgroundResource(R.drawable.bg_border_ffffff_black38_4dp);
    }

    private void sendCode() {
        int codeType = 5;
        if (this.mType == 0) {
            codeType = 5;
        } else if (this.mType == 1) {
            codeType = 6;
        }
        this.mOperation.sendCodeOperation(this, ((ActivityLoginBinding) this.mBaseBinding).etLoginNumber.getText().toString(), codeType, this.mConnectProv, new SuccessFailCallback() {
            public void onStart() {
                LoginActivity.this.mHandler.sendEmptyMessage(0);
            }

            public void onFail() {
                LoginActivity.this.mHandler.sendEmptyMessage(1);
            }
        });
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
                LoginActivity.this.showSecondTypeToast((int) R.string.Authorize_fail);
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    Matcher matcher = Pattern.compile("\\{(.*)\\}").matcher(content);
                    while (matcher.find()) {
                        LoginActivity.this.mConnectUid = new JSONObject(matcher.group()).getString("unionid");
                        LoginActivity.this.checkIsBind();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    LoginActivity.this.showSecondTypeToast((int) R.string.Authorize_fail);
                }
            }
        });
    }

    private void checkIsBind() {
        this.mOperation.checkIsBindOperation(this, this.mConnectProv, this.mConnectUid, this.mConnectWxUnionId, this.mConnectUName, this.mConnectUSex, this.mConnectULogo, new SuccessFailCallback() {
            public void onSuccess() {
                LoginActivity.this.finish();
            }

            public void onFail(int code) {
                if (code == -60) {
                    Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                    intent.putExtra("feng_type", 1);
                    intent.putExtra("connectprov", LoginActivity.this.mConnectProv);
                    intent.putExtra("connectuid", LoginActivity.this.mConnectUid);
                    intent.putExtra("name", LoginActivity.this.mConnectUName);
                    intent.putExtra("sex", LoginActivity.this.mConnectUSex);
                    intent.putExtra("url", LoginActivity.this.mConnectULogo);
                    if (LoginActivity.this.mConnectProv.equals("weixin") && !TextUtils.isEmpty(LoginActivity.this.mConnectWxUnionId)) {
                        intent.putExtra("connectwxunionid", LoginActivity.this.mConnectWxUnionId);
                    }
                    LoginActivity.this.startActivity(intent);
                }
            }
        });
    }

    private void addUserConnect() {
        this.mOperation.addUserOperation(this, this.mConnectProv, this.mConnectUid, this.mConnectWxUnionId, ((ActivityLoginBinding) this.mBaseBinding).etLoginNumber.getText().toString(), ((ActivityLoginBinding) this.mBaseBinding).etLoginVerify.getText().toString().trim(), new SuccessFailCallback() {
            public void onStart() {
                LoginActivity.this.showProgress("", "绑定中...");
            }

            public void onSuccess() {
                LoginActivity.this.hideProgress();
                EventBus.getDefault().post(new ClosePageEvent());
                LoginActivity.this.finish();
            }

            public void onFail() {
                LoginActivity.this.hideProgress();
            }
        });
    }

    private void notifyServerCreateNewUser() {
        this.mOperation.jumpCheckAddUserOperation(this, this.mConnectProv, this.mConnectUid, this.mConnectWxUnionId, this.mConnectUName, this.mConnectUSex, this.mConnectULogo, new SuccessFailCallback() {
            public void onStart() {
                LoginActivity.this.showProgress("", "绑定中...");
            }

            public void onSuccess() {
                LoginActivity.this.hideProgress();
                EventBus.getDefault().post(new ClosePageEvent());
                LoginActivity.this.finish();
            }

            public void onFail() {
                LoginActivity.this.hideProgress();
            }
        });
    }

    public String getLogCurrentPage() {
        if (this.mType == 0) {
            return "app_login";
        }
        return "app_third_check_bind";
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ClosePageEvent event) {
        finish();
    }
}
