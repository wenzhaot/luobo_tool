package com.umeng.qq.tencent;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import com.stub.StubApp;
import com.tencent.connect.common.AssistActivity;
import com.tencent.ijk.media.player.IjkMediaMeta;
import com.umeng.commonsdk.proguard.g;
import com.umeng.socialize.net.dplus.CommonNetImpl;
import com.umeng.socialize.utils.ContextUtil;

public class BaseApi {
    public static String businessId = null;
    public static String installChannel = null;
    public static boolean isOEM = false;
    public static String registerChannel = null;
    protected Info a;
    protected QQToken b;

    public BaseApi(Info var1, QQToken var2) {
        this.a = var1;
        this.b = var2;
    }

    public BaseApi(QQToken var1) {
        this((Info) null, var1);
    }

    protected boolean a(Intent var1) {
        return var1 != null ? Wifig.a(ContextUtil.getContext(), var1) : false;
    }

    protected void a(Activity var1, Intent var2, int var3) {
        var2.putExtra("key_request_code", var3);
        var1.startActivityForResult(a(var1, var2), var3);
    }

    private Intent a(Activity var1, Intent var2) {
        Intent var3 = new Intent(StubApp.getOrigApplicationContext(var1.getApplicationContext()), AssistActivity.class);
        var3.putExtra("is_login", true);
        var3.putExtra(AssistActivity.EXTRA_INTENT, var2);
        return var3;
    }

    protected void a(Activity var1, int var2, Intent var3, Boolean var4) {
        Intent var5 = new Intent(StubApp.getOrigApplicationContext(var1.getApplicationContext()), AssistActivity.class);
        if (var4.booleanValue()) {
            var5.putExtra("is_qq_mobile_share", true);
        }
        var5.putExtra(AssistActivity.EXTRA_INTENT, var3);
        var1.startActivityForResult(var5, var2);
    }

    protected Intent b(String var1) {
        Intent var2 = new Intent();
        if (JsonUtil.e(ContextUtil.getContext())) {
            var2.setClassName("com.tencent.minihd.qq", var1);
            if (Wifig.a(ContextUtil.getContext(), var2)) {
                return var2;
            }
        }
        var2.setClassName("com.tencent.mobileqq", var1);
        if (Wifig.a(ContextUtil.getContext(), var2)) {
            return var2;
        }
        var2.setClassName("com.tencent.tim", var1);
        if (Wifig.a(ContextUtil.getContext(), var2)) {
            return var2;
        }
        return null;
    }

    protected Bundle a() {
        Bundle var1 = new Bundle();
        var1.putString(IjkMediaMeta.IJKM_KEY_FORMAT, "json");
        var1.putString("status_os", VERSION.RELEASE);
        var1.putString("status_machine", Build.MODEL);
        var1.putString("status_version", VERSION.SDK);
        var1.putString("sdkv", "3.1.0.lite");
        var1.putString("sdkp", g.al);
        if (this.b != null && this.b.isSessionValid()) {
            var1.putString("access_token", this.b.getAccessToken());
            var1.putString("oauth_consumer_key", this.b.getAppId());
            var1.putString("openid", this.b.getOpenId());
            var1.putString("appid_for_getting_config", this.b.getAppId());
        }
        SharedPreferences var2 = ContextUtil.getContext().getSharedPreferences("pfStore", 0);
        if (isOEM) {
            var1.putString(CommonNetImpl.PF, "desktop_m_qq-" + installChannel + "-" + "android" + "-" + registerChannel + "-" + businessId);
        } else {
            var1.putString(CommonNetImpl.PF, var2.getString(CommonNetImpl.PF, "openmobile_android"));
        }
        return var1;
    }
}
