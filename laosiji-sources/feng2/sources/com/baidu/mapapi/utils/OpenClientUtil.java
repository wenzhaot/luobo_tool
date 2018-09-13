package com.baidu.mapapi.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class OpenClientUtil {
    public static int getBaiduMapVersion(Context context) {
        if (context == null) {
            return 0;
        }
        String str = "";
        try {
            str = context.getPackageManager().getPackageInfo("com.baidu.BaiduMap", 0).versionName;
            return (str == null || str.length() <= 0) ? 0 : Integer.valueOf(str.trim().replace(".", "").trim()).intValue();
        } catch (Exception e) {
            return 0;
        }
    }

    public static void getLatestBaiduMapApp(Context context) {
        if (context != null) {
            String b = a.b(context);
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setData(Uri.parse("http://map.baidu.com/zt/client/index/?fr=sdk_[" + b + "]"));
            context.startActivity(intent);
        }
    }
}
