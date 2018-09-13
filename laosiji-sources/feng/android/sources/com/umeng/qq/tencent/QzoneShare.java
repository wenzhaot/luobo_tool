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
import java.net.URLEncoder;
import java.util.ArrayList;

public class QzoneShare extends BaseApi {
    public QzoneShare(Context var1, QQToken var2) {
        super(var2);
    }

    public void shareToQzone(Activity var1, Bundle var2, IUiListener var3) {
        String type = var2.getString(QQConstant.SHARE_UMENG_TYPE);
        if (type == null || !type.equals(QQConstant.SHARE_SHUO)) {
            b(var1, var2, var3);
        } else {
            publishQzone(var1, var2, var3);
        }
    }

    private void b(Activity var1, Bundle var2, IUiListener var3) {
        StringBuffer var4 = new StringBuffer("mqqapi://share/to_qzone?src_type=app&version=1&file_type=news");
        ArrayList var5 = var2.getStringArrayList("imageUrl");
        String var6 = var2.getString("title");
        String var7 = var2.getString("summary");
        String var8 = var2.getString(QQConstant.SHARE_TO_QQ_TARGET_URL);
        String var9 = var2.getString(QQConstant.SHARE_TO_QQ_AUDIO_URL);
        int var10 = var2.getInt(QQConstant.SHARE_TO_QQ_KEY_TYPE, 1);
        String var11 = var2.getString(QQConstant.SHARE_TO_QQ_APP_NAME);
        int var12 = var2.getInt(QQConstant.SHARE_TO_QQ_EXT_INT, 0);
        String var13 = var2.getString("share_qq_ext_str");
        String var14 = this.b.getAppId();
        String var15 = this.b.getOpenId();
        if (var5 != null) {
            StringBuffer var16 = new StringBuffer();
            int var17 = var5.size() > 9 ? 9 : var5.size();
            for (int var18 = 0; var18 < var17; var18++) {
                var16.append(URLEncoder.encode((String) var5.get(var18)));
                if (var18 != var17 - 1) {
                    var16.append(";");
                }
            }
            var4.append("&image_url=" + Base64.encodeToString(JsonUtil.i(var16.toString()), 2));
        }
        if (!TextUtils.isEmpty(var6)) {
            var4.append("&title=" + Base64.encodeToString(JsonUtil.i(var6), 2));
        }
        if (!TextUtils.isEmpty(var7)) {
            var4.append("&description=" + Base64.encodeToString(JsonUtil.i(var7), 2));
        }
        if (!TextUtils.isEmpty(var14)) {
            var4.append("&share_id=" + var14);
        }
        if (!TextUtils.isEmpty(var8)) {
            var4.append("&url=" + Base64.encodeToString(JsonUtil.i(var8), 2));
        }
        if (!TextUtils.isEmpty(var11)) {
            var4.append("&app_name=" + Base64.encodeToString(JsonUtil.i(var11), 2));
        }
        if (!JsonUtil.e(var15)) {
            var4.append("&open_id=" + Base64.encodeToString(JsonUtil.i(var15), 2));
        }
        if (!JsonUtil.e(var9)) {
            var4.append("&audioUrl=" + Base64.encodeToString(JsonUtil.i(var9), 2));
        }
        var4.append("&req_type=" + Base64.encodeToString(JsonUtil.i(String.valueOf(var10)), 2));
        if (!JsonUtil.e(var13)) {
            var4.append("&share_qq_ext_str=" + Base64.encodeToString(JsonUtil.i(var13), 2));
        }
        var4.append("&cflag=" + Base64.encodeToString(JsonUtil.i(String.valueOf(var12)), 2));
        Intent var19 = new Intent("android.intent.action.VIEW");
        var19.setData(Uri.parse(var4.toString()));
        var19.putExtra(KEY_TYPE.PKGNAME, var1.getPackageName());
        if (!JsonUtil.f(var1, "4.6.0")) {
            UIListenerManager.getInstance().setListnerWithAction("shareToQzone", var3);
            if (a(var19)) {
                a(var1, 10104, var19, Boolean.valueOf(false));
            }
        } else if (a(var19)) {
            UIListenerManager.getInstance().setListenerWithRequestcode(11104, var3);
            a(var1, var19, 11104);
        }
    }

    private void publishQzone(Activity var1, Bundle var2, IUiListener var3) {
        StringBuffer var4 = new StringBuffer("mqqapi://qzone/publish?src_type=app&version=1&file_type=news");
        ArrayList var5 = var2.getStringArrayList("imageUrl");
        String var6 = var2.getString("summary");
        int var7 = var2.getInt(QQConstant.SHARE_TO_QQ_KEY_TYPE, 3);
        String var8 = var2.getString(QQConstant.SHARE_TO_QQ_APP_NAME);
        String var9 = var2.getString("videoPath");
        int var10 = var2.getInt("videoDuration");
        long var11 = var2.getLong("videoSize");
        String var13 = this.b.getAppId();
        String var14 = this.b.getOpenId();
        if (3 == var7 && var5 != null) {
            StringBuffer var16 = new StringBuffer();
            int var17 = var5.size();
            for (int var18 = 0; var18 < var17; var18++) {
                var16.append(URLEncoder.encode((String) var5.get(var18)));
                if (var18 != var17 - 1) {
                    var16.append(";");
                }
            }
            var4.append("&image_url=" + Base64.encodeToString(JsonUtil.i(var16.toString()), 2));
        }
        if (4 == var7) {
            var4.append("&videoPath=" + Base64.encodeToString(JsonUtil.i(var9), 2));
            var4.append("&videoDuration=" + Base64.encodeToString(JsonUtil.i(String.valueOf(var10)), 2));
            var4.append("&videoSize=" + Base64.encodeToString(JsonUtil.i(String.valueOf(var11)), 2));
        }
        if (!TextUtils.isEmpty(var6)) {
            var4.append("&description=" + Base64.encodeToString(JsonUtil.i(var6), 2));
        }
        if (!TextUtils.isEmpty(var13)) {
            var4.append("&share_id=" + var13);
        }
        if (!TextUtils.isEmpty(var8)) {
            var4.append("&app_name=" + Base64.encodeToString(JsonUtil.i(var8), 2));
        }
        if (!JsonUtil.e(var14)) {
            var4.append("&open_id=" + Base64.encodeToString(JsonUtil.i(var14), 2));
        }
        var4.append("&req_type=" + Base64.encodeToString(JsonUtil.i(String.valueOf(var7)), 2));
        Intent var19 = new Intent("android.intent.action.VIEW");
        var19.setData(Uri.parse(var4.toString()));
        var19.putExtra(KEY_TYPE.PKGNAME, var1.getPackageName());
        if (a(var19)) {
            a(var1, 10104, var19, Boolean.valueOf(false));
        }
    }
}
