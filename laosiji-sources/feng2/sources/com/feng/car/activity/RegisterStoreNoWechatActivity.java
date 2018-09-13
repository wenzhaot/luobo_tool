package com.feng.car.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import anet.channel.util.ErrorConstant;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.databinding.OpenStoreHeaderBinding;
import com.feng.car.event.ChangeIntentToShopReStateEvent;
import com.feng.car.operation.SuccessFailCallback;
import com.feng.car.operation.ThirdLoginOperation;
import com.feng.car.utils.AuthUtil;
import com.feng.car.utils.JsonUtil;
import com.feng.library.utils.SharedUtil;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import java.util.HashMap;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;

public class RegisterStoreNoWechatActivity extends BaseActivity<OpenStoreHeaderBinding> {
    UMAuthListener mAuthListener = new UMAuthListener() {
        public void onStart(SHARE_MEDIA share_media) {
        }

        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            if (platform == SHARE_MEDIA.WEIXIN) {
                RegisterStoreNoWechatActivity.this.mConnectProv = "weixin";
                RegisterStoreNoWechatActivity.this.mConnectUid = (String) data.get("openid");
                RegisterStoreNoWechatActivity.this.mConnectWxUnionId = (String) data.get("unionid");
                if (data.containsKey("name")) {
                    RegisterStoreNoWechatActivity.this.mConnectUName = (String) data.get("name");
                    if (TextUtils.isEmpty(RegisterStoreNoWechatActivity.this.mConnectUName)) {
                        RegisterStoreNoWechatActivity.this.mConnectUName = "";
                    }
                }
                if (data.containsKey("gender")) {
                    RegisterStoreNoWechatActivity.this.mConnectUSex = ((String) data.get("gender")).equals("女") ? "2" : "1";
                    if (TextUtils.isEmpty(RegisterStoreNoWechatActivity.this.mConnectUSex)) {
                        RegisterStoreNoWechatActivity.this.mConnectUSex = "1";
                    }
                }
                if (data.containsKey("iconurl")) {
                    RegisterStoreNoWechatActivity.this.mConnectULogo = (String) data.get("iconurl");
                    if (TextUtils.isEmpty(RegisterStoreNoWechatActivity.this.mConnectULogo)) {
                        RegisterStoreNoWechatActivity.this.mConnectULogo = "";
                    }
                }
                Map<String, String> map = new HashMap();
                map.put("name", RegisterStoreNoWechatActivity.this.mConnectUName);
                map.put("sex", RegisterStoreNoWechatActivity.this.mConnectUSex);
                map.put("url", RegisterStoreNoWechatActivity.this.mConnectULogo);
                SharedUtil.putString(RegisterStoreNoWechatActivity.this, "login_third_info", JsonUtil.toJson(map));
                if (FengApplication.getInstance().isLoginUser()) {
                    RegisterStoreNoWechatActivity.this.checkThirdInfo();
                } else {
                    RegisterStoreNoWechatActivity.this.checkIsBind();
                }
            }
        }

        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            RegisterStoreNoWechatActivity.this.showSecondTypeToast((int) R.string.Authorize_fail);
        }

        public void onCancel(SHARE_MEDIA platform, int action) {
            RegisterStoreNoWechatActivity.this.showSecondTypeToast((int) R.string.Authorize_cancel);
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
        return R.layout.open_store_header;
    }

    public void initView() {
        initNormalTitleBar("");
        this.mOperation = new ThirdLoginOperation();
        ((OpenStoreHeaderBinding) this.mBaseBinding).btBindWeChat.setOnClickListener(this);
    }

    public void onSingleClick(View v) {
        super.onSingleClick(v);
        switch (v.getId()) {
            case R.id.bt_bindWeChat /*2131625347*/:
                if (!FengApplication.getInstance().getSeverceState()) {
                    showSecondTypeToast((int) R.string.server_maintain);
                    finish();
                }
                if (UMShareAPI.get(this).isInstall(this, SHARE_MEDIA.WEIXIN)) {
                    AuthUtil.getInfo(this, SHARE_MEDIA.WEIXIN, this.mAuthListener);
                    return;
                }
                showThirdTypeToast((int) R.string.not_install_weixin_tips);
                finish();
                return;
            default:
                return;
        }
    }

    private void checkThirdInfo() {
        this.mOperation.checkThirdInfo(this, this.mConnectProv, this.mConnectWxUnionId, this.mConnectUName, this.mConnectUSex, this.mConnectULogo, new SuccessFailCallback() {
            public void onSuccess() {
                EventBus.getDefault().post(new ChangeIntentToShopReStateEvent());
                RegisterStoreNoWechatActivity.this.finish();
            }

            public void onFail(int code) {
                if (code == ErrorConstant.ERROR_REQUEST_CANCEL) {
                    RegisterStoreNoWechatActivity.this.showSecondTypeToast("授权微信与当前账号不匹配或已绑定其他账号,\n请退出并使用微信重新登陆!");
                } else if (code == -60) {
                    RegisterStoreNoWechatActivity.this.addUserConnect();
                } else {
                    RegisterStoreNoWechatActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                }
            }
        });
    }

    private void addUserConnect() {
        this.mOperation.jumpCheckAddUserOperation(this, this.mConnectProv, this.mConnectUid, this.mConnectWxUnionId, this.mConnectUName, this.mConnectUSex, this.mConnectULogo, new SuccessFailCallback() {
            public void onStart() {
                RegisterStoreNoWechatActivity.this.showProgress("", "绑定中...");
            }

            public void onSuccess() {
                RegisterStoreNoWechatActivity.this.hideProgress();
                EventBus.getDefault().post(new ChangeIntentToShopReStateEvent());
                RegisterStoreNoWechatActivity.this.finish();
            }

            public void onFail() {
                RegisterStoreNoWechatActivity.this.hideProgress();
            }
        });
    }

    private void checkIsBind() {
        this.mOperation.checkIsBindOperation(this, this.mConnectProv, this.mConnectUid, this.mConnectWxUnionId, this.mConnectUName, this.mConnectUSex, this.mConnectULogo, new SuccessFailCallback() {
            public void onSuccess() {
                EventBus.getDefault().post(new ChangeIntentToShopReStateEvent());
                RegisterStoreNoWechatActivity.this.finish();
            }

            public void onFail(int code) {
                if (code == -60) {
                    Intent intent = new Intent(RegisterStoreNoWechatActivity.this, LoginActivity.class);
                    intent.putExtra("feng_type", 1);
                    intent.putExtra("connectprov", RegisterStoreNoWechatActivity.this.mConnectProv);
                    intent.putExtra("connectuid", RegisterStoreNoWechatActivity.this.mConnectUid);
                    if (RegisterStoreNoWechatActivity.this.mConnectProv.equals("weixin") && !TextUtils.isEmpty(RegisterStoreNoWechatActivity.this.mConnectWxUnionId)) {
                        intent.putExtra("connectwxunionid", RegisterStoreNoWechatActivity.this.mConnectWxUnionId);
                    }
                    RegisterStoreNoWechatActivity.this.startActivity(intent);
                }
            }
        });
    }
}
