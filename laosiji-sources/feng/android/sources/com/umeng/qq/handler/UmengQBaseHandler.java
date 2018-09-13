package com.umeng.qq.handler;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.umeng.qq.tencent.Tencent;
import com.umeng.socialize.PlatformConfig.APPIDPlatform;
import com.umeng.socialize.PlatformConfig.Platform;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.handler.UMSSOHandler;
import com.umeng.socialize.net.dplus.CommonNetImpl;
import com.umeng.socialize.utils.SLog;
import com.umeng.socialize.utils.UmengText.QQ;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class UmengQBaseHandler extends UMSSOHandler {
    protected static final String FIGUREURL_QQ_2 = "figureurl_qq_2";
    protected static final String IS_YELLOW_VIP = "is_yellow_vip";
    protected static final String IS_YELLOW_YEAR_VIP = "is_yellow_year_vip";
    protected static final String LEVEL = "level";
    protected static final String MSG = "msg";
    protected static final String NICKNAME = "nickname";
    protected static final String RET = "ret";
    private static final String TAG = "UmengQBaseHandler";
    protected static final String VIP = "vip";
    protected static final String YELLOW_VIP_LEVEL = "yellow_vip_level";
    protected String VERSION = "6.9.2";
    public APPIDPlatform config = null;
    protected UMAuthListener mAuthListener;
    protected ProgressDialog mProgressDialog = null;
    protected UMShareListener mShareListener;
    protected Tencent mTencent;

    public void onCreate(Context context, Platform p) {
        super.onCreate(context, p);
        this.config = (APPIDPlatform) p;
        this.mTencent = Tencent.createInstance(this.config.appId, context);
        if (this.mTencent == null) {
            SLog.E(QQ.QQ_TENCENT_ERROR);
        }
    }

    public String getVersion() {
        return this.VERSION;
    }

    protected Bundle parseOauthData(Object response) {
        Bundle bundle = new Bundle();
        if (response != null) {
            String jsonStr = response.toString().trim();
            if (!TextUtils.isEmpty(jsonStr)) {
                JSONObject json = null;
                try {
                    json = new JSONObject(jsonStr);
                } catch (JSONException e) {
                    SLog.error(e);
                }
                if (json != null) {
                    bundle.putString("auth_time", json.optString("auth_time", ""));
                    bundle.putString("pay_token", json.optString("pay_token", ""));
                    bundle.putString(CommonNetImpl.PF, json.optString(CommonNetImpl.PF, ""));
                    bundle.putString(RET, String.valueOf(json.optInt(RET, -1)));
                    bundle.putString("sendinstall", json.optString("sendinstall", ""));
                    bundle.putString("page_type", json.optString("page_type", ""));
                    bundle.putString("appid", json.optString("appid", ""));
                    bundle.putString("openid", json.optString("openid", ""));
                    bundle.putString("uid", json.optString("openid", ""));
                    bundle.putString("expires_in", json.optString("expires_in", ""));
                    bundle.putString("pfkey", json.optString("pfkey", ""));
                    bundle.putString("access_token", json.optString("access_token", ""));
                    bundle.putString("accessToken", json.optString("access_token", ""));
                }
            }
        }
        return bundle;
    }
}
