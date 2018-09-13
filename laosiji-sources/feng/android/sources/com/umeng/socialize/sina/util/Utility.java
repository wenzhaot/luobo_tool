package com.umeng.socialize.sina.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.umeng.socialize.net.a;
import com.umeng.socialize.net.b;
import com.umeng.socialize.net.base.SocializeClient;
import com.umeng.socialize.sina.helper.MD5;
import com.umeng.socialize.utils.SLog;
import com.umeng.socialize.utils.UmengText.SINA;
import java.util.UUID;

public class Utility {
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final String WEIBO_IDENTITY_ACTION = "com.sina.weibo.action.sdkidentity";
    private static String aid = "";

    public static String generateGUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String generateUA(Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Build.MANUFACTURER).append("-").append(Build.MODEL);
        stringBuilder.append("_");
        stringBuilder.append(VERSION.RELEASE);
        stringBuilder.append("_");
        stringBuilder.append("weibosdk");
        stringBuilder.append("_");
        stringBuilder.append("0031405000");
        stringBuilder.append("_android");
        return stringBuilder.toString();
    }

    public static String getAid(Context context, String str) {
        if (TextUtils.isEmpty(aid)) {
            b bVar = (b) new SocializeClient().execute(new a(str));
            if (bVar == null || !TextUtils.isEmpty(bVar.c)) {
                SLog.E(SINA.SINA_AID_ERROR);
            } else {
                aid = bVar.a;
            }
        }
        return aid;
    }

    public static String getSign(Context context, String str) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(str, 64);
            for (Signature toByteArray : packageInfo.signatures) {
                byte[] toByteArray2 = toByteArray.toByteArray();
                if (toByteArray2 != null) {
                    return MD5.hexdigest(toByteArray2);
                }
            }
            return null;
        } catch (NameNotFoundException e) {
            return null;
        }
    }
}
