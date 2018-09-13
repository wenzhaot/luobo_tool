package com.tencent.tauth;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.stub.StubApp;
import com.tencent.connect.common.AssistActivity;
import com.umeng.qq.tencent.IUiListener;
import com.umeng.qq.tencent.JsonUtil;
import com.umeng.qq.tencent.UIListenerManager;
import com.umeng.qq.tencent.Wifig;
import com.umeng.socialize.utils.DeviceConfig;

public class AuthActivity extends Activity {
    public static final String ACTION_KEY = "action";
    public static final String ACTION_SHARE_PRIZE = "sharePrize";
    private static int a = 0;

    protected native void onCreate(Bundle bundle);

    static {
        StubApp.interface11(8078);
    }

    private void a(Uri var1) {
        if (var1 == null || var1.toString() == null || var1.toString().equals("")) {
            finish();
            return;
        }
        String var2 = var1.toString();
        Bundle var4 = JsonUtil.a(var2.substring(var2.indexOf("#") + 1));
        if (var4 == null) {
            finish();
            return;
        }
        String var5 = var4.getString(ACTION_KEY);
        Intent var6;
        if (var5 == null) {
            finish();
        } else if (var5.equals("shareToQQ") || var5.equals("shareToQzone") || var5.equals("sendToMyComputer") || var5.equals("shareToTroopBar")) {
            if (var5.equals("shareToQzone") && DeviceConfig.getAppVersion("com.tencent.mobileqq", this) != null && Wifig.c(this, "5.2.0") < 0) {
                a++;
                if (a == 2) {
                    a = 0;
                    finish();
                    return;
                }
            }
            var6 = new Intent(this, AssistActivity.class);
            var6.putExtras(var4);
            var6.setFlags(603979776);
            startActivity(var6);
            finish();
        } else if (var5.equals("addToQQFavorites")) {
            var6 = getIntent();
            var6.putExtras(var4);
            var6.putExtra("key_action", "action_share");
            IUiListener var7 = UIListenerManager.getInstance().getListnerWithAction(var5);
            if (var7 != null) {
                UIListenerManager.getInstance().handleDataToListener(var6, var7);
            }
            finish();
        } else if (var5.equals(ACTION_SHARE_PRIZE)) {
            var6 = getPackageManager().getLaunchIntentForPackage(getPackageName());
            String var9 = "";
            try {
                var9 = JsonUtil.d(var4.getString("response")).getString("activityid");
            } catch (Exception e) {
            }
            if (!TextUtils.isEmpty(var9)) {
                var6.putExtra(ACTION_SHARE_PRIZE, true);
                Bundle var10 = new Bundle();
                var10.putString("activityid", var9);
                var6.putExtras(var10);
            }
            startActivity(var6);
            finish();
        } else {
            finish();
        }
    }
}
