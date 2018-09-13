package com.umeng.qq.tencent;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import com.stub.StubApp;

public class Info {
    private AuthAgent authAgent = new AuthAgent(this.qqToken);
    private QQToken qqToken;

    private Info(String var1) {
        this.qqToken = new QQToken(var1);
    }

    public static Info get(String var0, Context var1) {
        try {
            PackageManager var2 = var1.getPackageManager();
            var2.getActivityInfo(new ComponentName(var1.getPackageName(), "com.tencent.tauth.AuthActivity"), 0);
            var2.getActivityInfo(new ComponentName(var1.getPackageName(), "com.tencent.connect.common.AssistActivity"), 0);
            return new Info(var0);
        } catch (NameNotFoundException e) {
            return null;
        }
    }

    public QQToken getQqToken() {
        return this.qqToken;
    }

    public int a(Activity var1, String var2, IUiListener var3) {
        return a(var1, var2, var3, "");
    }

    private int a(Activity var1, String var3, IUiListener var4, String var5) {
        String var6 = StubApp.getOrigApplicationContext(var1.getApplicationContext()).getPackageName();
        for (ApplicationInfo var11 : var1.getPackageManager().getInstalledApplications(128)) {
            if (var6.equals(var11.packageName)) {
                break;
            }
        }
        BaseApi.isOEM = false;
        return this.authAgent.doLogin(var1, var3, var4, false);
    }
}
