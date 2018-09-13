package com.umeng.socialize.sina.auth;

import android.content.Context;
import android.os.Bundle;
import com.taobao.accs.common.Constants;
import com.umeng.socialize.sina.params.ShareRequestParam;
import com.umeng.socialize.sina.util.Utility;

public class AuthInfo {
    private String mAppKey = "";
    private String mKeyHash = "";
    private String mPackageName = "";
    private String mRedirectUrl = "";
    private String mScope = "";

    public AuthInfo(Context context, String str, String str2, String str3) {
        this.mAppKey = str;
        this.mRedirectUrl = str2;
        this.mScope = str3;
        this.mPackageName = context.getPackageName();
        this.mKeyHash = Utility.getSign(context, this.mPackageName);
    }

    public static AuthInfo parseBundleData(Context context, Bundle bundle) {
        return new AuthInfo(context, bundle.getString(Constants.KEY_APP_KEY), bundle.getString("redirectUri"), bundle.getString("scope"));
    }

    public String getAppKey() {
        return this.mAppKey;
    }

    public Bundle getAuthBundle() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_APP_KEY, this.mAppKey);
        bundle.putString("redirectUri", this.mRedirectUrl);
        bundle.putString("scope", this.mScope);
        bundle.putString(ShareRequestParam.REQ_PARAM_PACKAGENAME, this.mPackageName);
        bundle.putString(ShareRequestParam.REQ_PARAM_KEY_HASH, this.mKeyHash);
        return bundle;
    }

    public String getKeyHash() {
        return this.mKeyHash;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public String getRedirectUrl() {
        return this.mRedirectUrl;
    }

    public String getScope() {
        return this.mScope;
    }
}
