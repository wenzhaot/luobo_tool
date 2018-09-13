package com.umeng.qq.tencent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import com.huawei.android.pushagent.PushReceiver.KEY_TYPE;
import com.umeng.qq.handler.QQConstant;

public class QQShare extends BaseApi {
    public QQShare(Context var1, QQToken var2) {
        super(var2);
    }

    public void shareToQQ(Activity var1, Bundle var2, IUiListener var3) {
        if (JsonUtil.f(var1, "4.5.0")) {
            var3.onError(new UiError(-6, "低版本手Q不支持该项功能!", (String) null));
        } else if (JsonUtil.a((Context) var1)) {
            b(var1, var2, var3);
        }
    }

    private void b(Activity var1, Bundle var2, IUiListener var3) {
        String var4 = var2.getString("imageUrl");
        c(var1, var2, var3);
    }

    private void c(Activity var1, Bundle var2, IUiListener var3) {
        StringBuffer var4 = new StringBuffer("mqqapi://share/to_fri?src_type=app&version=1&file_type=news");
        String var5 = var2.getString("imageUrl");
        String var6 = var2.getString("title");
        String var7 = var2.getString("summary");
        String var8 = var2.getString(QQConstant.SHARE_TO_QQ_TARGET_URL);
        String var9 = var2.getString(QQConstant.SHARE_TO_QQ_AUDIO_URL);
        int var10 = var2.getInt(QQConstant.SHARE_TO_QQ_KEY_TYPE, 1);
        int var11 = var2.getInt(QQConstant.SHARE_TO_QQ_EXT_INT, 0);
        String var12 = var2.getString("share_qq_ext_str");
        String var13 = JsonUtil.b(var1);
        if (var13 == null) {
            var13 = var2.getString(QQConstant.SHARE_TO_QQ_APP_NAME);
        }
        String var14 = var2.getString(QQConstant.SHARE_TO_QQ_IMAGE_LOCAL_URL);
        String var15 = this.b.getAppId();
        String var16 = this.b.getOpenId();
        if (!TextUtils.isEmpty(var5)) {
            var4.append("&image_url=" + Base64.encodeToString(JsonUtil.i(var5), 2));
        }
        if (!TextUtils.isEmpty(var14)) {
            var4.append("&file_data=" + Base64.encodeToString(JsonUtil.i(var14), 2));
        }
        if (!TextUtils.isEmpty(var6)) {
            var4.append("&title=" + Base64.encodeToString(JsonUtil.i(var6), 2));
        }
        if (!TextUtils.isEmpty(var7)) {
            var4.append("&description=" + Base64.encodeToString(JsonUtil.i(var7), 2));
        }
        if (!TextUtils.isEmpty(var15)) {
            var4.append("&share_id=" + var15);
        }
        if (!TextUtils.isEmpty(var8)) {
            var4.append("&url=" + Base64.encodeToString(JsonUtil.i(var8), 2));
        }
        if (!TextUtils.isEmpty(var13)) {
            String var17 = var13;
            if (var13.length() > 20) {
                var17 = var13.substring(0, 20) + "...";
            }
            var4.append("&app_name=" + Base64.encodeToString(JsonUtil.i(var17), 2));
        }
        if (!TextUtils.isEmpty(var16)) {
            var4.append("&open_id=" + Base64.encodeToString(JsonUtil.i(var16), 2));
        }
        if (!TextUtils.isEmpty(var9)) {
            var4.append("&audioUrl=" + Base64.encodeToString(JsonUtil.i(var9), 2));
        }
        var4.append("&req_type=" + Base64.encodeToString(JsonUtil.i(String.valueOf(var10)), 2));
        if (!TextUtils.isEmpty(var12)) {
            var4.append("&share_qq_ext_str=" + Base64.encodeToString(JsonUtil.i(var12), 2));
        }
        var4.append("&cflag=" + Base64.encodeToString(JsonUtil.i(String.valueOf(var11)), 2));
        Intent var19 = new Intent("android.intent.action.VIEW");
        var19.setData(Uri.parse(var4.toString()));
        var19.putExtra(KEY_TYPE.PKGNAME, var1.getPackageName());
        if (!JsonUtil.f(var1, "4.6.0")) {
            if (UIListenerManager.getInstance().setListnerWithAction("shareToQQ", var3) != null) {
            }
            if (a(var19)) {
                a(var1, 10103, var19, Boolean.valueOf(true));
            }
        } else if (a(var19)) {
            UIListenerManager.getInstance().setListenerWithRequestcode(11103, var3);
            a(var1, var19, 11103);
        }
    }
}
