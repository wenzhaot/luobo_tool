package com.feng.car.activity;

import android.text.TextUtils;
import android.view.View;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.databinding.WechatBindActicityBinding;
import com.feng.car.event.OpenActivityEvent;
import com.feng.car.operation.SuccessFailCallback;
import com.feng.car.operation.ThirdLoginOperation;
import com.feng.car.utils.AuthUtil;
import com.feng.car.utils.JsonUtil;
import com.feng.library.utils.SharedUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.stub.StubApp;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import java.util.HashMap;
import java.util.Map;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class WeChatBindActivity extends BaseActivity<WechatBindActicityBinding> {
    public static int BIND_FINISH = 1004;
    public static int BIND_TYPE = 1003;
    public static int LOGIN_TYPE = 1002;
    private String mConnectProv = "";
    private String mConnectULogo = "";
    private String mConnectUName = "";
    private String mConnectUSex = "";
    private String mConnectUid = "";
    private String mConnectWxUnionId = "";
    private ThirdLoginOperation mOperation = new ThirdLoginOperation();
    UMAuthListener mPlatformInfoListener = new UMAuthListener() {
        public void onStart(SHARE_MEDIA share_media) {
        }

        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            if (platform == SHARE_MEDIA.WEIXIN) {
                WeChatBindActivity.this.mConnectProv = "weixin";
                WeChatBindActivity.this.mConnectUid = (String) data.get("openid");
                WeChatBindActivity.this.mConnectWxUnionId = (String) data.get("unionid");
                if (data.containsKey("name")) {
                    WeChatBindActivity.this.mConnectUName = (String) data.get("name");
                    if (TextUtils.isEmpty(WeChatBindActivity.this.mConnectUName)) {
                        WeChatBindActivity.this.mConnectUName = "";
                    }
                }
                if (data.containsKey("gender")) {
                    WeChatBindActivity.this.mConnectUSex = ((String) data.get("gender")).equals("女") ? "2" : "1";
                    if (TextUtils.isEmpty(WeChatBindActivity.this.mConnectUSex)) {
                        WeChatBindActivity.this.mConnectUSex = "1";
                    }
                }
                if (data.containsKey("iconurl")) {
                    WeChatBindActivity.this.mConnectULogo = (String) data.get("iconurl");
                    if (TextUtils.isEmpty(WeChatBindActivity.this.mConnectULogo)) {
                        WeChatBindActivity.this.mConnectULogo = "";
                    }
                }
            }
            if (WeChatBindActivity.this.mType == WeChatBindActivity.LOGIN_TYPE) {
                Map<String, String> map = new HashMap();
                map.put("name", WeChatBindActivity.this.mConnectUName);
                map.put("sex", WeChatBindActivity.this.mConnectUSex);
                map.put("url", WeChatBindActivity.this.mConnectULogo);
                SharedUtil.putString(WeChatBindActivity.this, "login_third_info", JsonUtil.toJson(map));
                WeChatBindActivity.this.checkIsBind();
            } else if (WeChatBindActivity.this.mType == WeChatBindActivity.BIND_TYPE) {
                WeChatBindActivity.this.addUserConnect();
            }
        }

        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            WeChatBindActivity.this.showSecondTypeToast((int) R.string.Authorize_fail);
            t.printStackTrace();
        }

        public void onCancel(SHARE_MEDIA platform, int action) {
            WeChatBindActivity.this.showSecondTypeToast((int) R.string.Authorize_cancel);
        }
    };
    private int mType = BIND_TYPE;
    private String mUrl = "";

    static {
        StubApp.interface11(3283);
    }

    public int setBaseContentView() {
        return R.layout.wechat_bind_acticity;
    }

    public void initView() {
        this.mType = getIntent().getIntExtra("type", BIND_TYPE);
        this.mUrl = getIntent().getStringExtra("url");
        if (this.mType == BIND_FINISH) {
            toWebPage();
        }
        initNormalTitleBar(getString(R.string.bind_weChat_account));
        if (this.mType == LOGIN_TYPE) {
            ((WechatBindActicityBinding) this.mBaseBinding).ivBindWeChat.setImageResource(R.drawable.bind_wechat_login);
            ((WechatBindActicityBinding) this.mBaseBinding).btBindWeChat.setBackgroundResource(R.drawable.bind_wechat_login_selector);
        } else if (this.mType == BIND_TYPE) {
            ((WechatBindActicityBinding) this.mBaseBinding).ivBindWeChat.setImageResource(R.drawable.bind_wechat);
            ((WechatBindActicityBinding) this.mBaseBinding).btBindWeChat.setBackgroundResource(R.drawable.bind_wechat_selector);
        }
        ((WechatBindActicityBinding) this.mBaseBinding).btBindWeChat.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (!UMShareAPI.get(WeChatBindActivity.this).isInstall(WeChatBindActivity.this, SHARE_MEDIA.WEIXIN)) {
                    WeChatBindActivity.this.showThirdTypeToast((int) R.string.not_install_weixin_tips);
                } else if (WeChatBindActivity.this.mType == WeChatBindActivity.LOGIN_TYPE) {
                    AuthUtil.getInfo(WeChatBindActivity.this, SHARE_MEDIA.WEIXIN, WeChatBindActivity.this.mPlatformInfoListener);
                } else if (WeChatBindActivity.this.mType == WeChatBindActivity.BIND_TYPE) {
                    AuthUtil.getInfo(WeChatBindActivity.this, SHARE_MEDIA.WEIXIN, WeChatBindActivity.this.mPlatformInfoListener);
                }
            }
        });
    }

    private void addUserConnect() {
        this.mOperation.jumpCheckAddUserOperation(this, this.mConnectProv, this.mConnectUid, this.mConnectWxUnionId, this.mConnectUName, this.mConnectUSex, this.mConnectULogo, new SuccessFailCallback() {
            public void onStart() {
                WeChatBindActivity.this.showProgress("", "绑定中...");
            }

            public void onSuccess() {
                WeChatBindActivity.this.hideProgress();
                WeChatBindActivity.this.toWebPage();
            }

            public void onFail() {
                WeChatBindActivity.this.hideProgress();
            }
        });
    }

    private void checkIsBind() {
        this.mOperation.checkIsBindOperation(this, this.mConnectProv, this.mConnectUid, this.mConnectWxUnionId, this.mConnectUName, this.mConnectUSex, this.mConnectULogo, new SuccessFailCallback() {
            public void onSuccess() {
                WeChatBindActivity.this.toWebPage();
            }

            public void onFail(int code) {
            }
        });
    }

    private void toWebPage() {
        FengApplication.getInstance().handlerUrlSkip(this, this.mUrl, "", false, null);
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(OpenActivityEvent event) {
        if (!TextUtils.isEmpty(this.mUrl)) {
            toWebPage();
        }
    }
}
