package com.umeng.qq.tencent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.umeng.socialize.utils.DeviceConfig;

public class Tencent {
    private static Tencent tencent;
    private Info info;

    private Tencent(String var1, Context var2) {
        this.info = Info.get(var1, var2);
    }

    public static synchronized Tencent createInstance(String var0, Context var1) {
        Tencent tencent;
        synchronized (Tencent.class) {
            if (tencent == null) {
                tencent = new Tencent(var0, var1);
            } else if (!var0.equals(tencent.getAppId())) {
                tencent.logout();
                tencent = new Tencent(var0, var1);
            }
            tencent = tencent;
        }
        return tencent;
    }

    public void shareToQQ(Activity var1, Bundle var2, IUiListener var3) {
        if (this.info != null) {
            new QQShare(var1, this.info.getQqToken()).shareToQQ(var1, var2, var3);
        }
    }

    public void shareToQzone(Activity var1, Bundle var2, IUiListener var3) {
        if (this.info != null) {
            new QzoneShare(var1, this.info.getQqToken()).shareToQzone(var1, var2, var3);
        }
    }

    public String getAppId() {
        return this.info.getQqToken().getAppId();
    }

    public void logout() {
        this.info.getQqToken().setAccessToken((String) null, PushConstants.PUSH_TYPE_NOTIFY);
        this.info.getQqToken().setOpenId((String) null);
    }

    public static boolean onActivityResultData(int var0, int var1, Intent var2, IUiListener var3) {
        return UIListenerManager.getInstance().onActivityResult(var0, var1, var2, var3);
    }

    public static void handleResultData(Intent var0, IUiListener var1) {
        UIListenerManager.getInstance().handleDataToListener(var0, var1);
    }

    public void setAccessToken(String var1, String var2) {
        this.info.getQqToken().setAccessToken(var1, var2);
    }

    public void setOpenId(String var1) {
        this.info.getQqToken().setOpenId(var1);
    }

    public boolean isSupportSSOLogin(Activity var1) {
        if ((!JsonUtil.e((Context) var1) || DeviceConfig.getAppVersion("com.tencent.minihd.qq", var1) == null) && TextUtils.isEmpty(DeviceConfig.getAppVersion("com.tencent.tim", var1))) {
            return TextUtils.isEmpty(DeviceConfig.getAppVersion("com.tencent.mobileqq", var1)) ? false : Wifig.b(var1);
        } else {
            return true;
        }
    }

    public int login(Activity var1, String var2, IUiListener var3) {
        return this.info.a(var1, var2, var3);
    }

    public QQToken getQQToken() {
        return this.info.getQqToken();
    }

    public void release() {
        tencent = null;
        this.info = null;
    }
}
