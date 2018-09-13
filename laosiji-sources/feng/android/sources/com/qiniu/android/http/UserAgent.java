package com.qiniu.android.http;

import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.qiniu.android.common.Constants;
import com.qiniu.android.dns.NetworkInfo;
import com.qiniu.android.utils.StringUtils;
import com.umeng.message.proguard.f;
import com.umeng.message.proguard.l;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Random;

public final class UserAgent {
    private static UserAgent _instance = new UserAgent();
    public final String id = genId();
    public final String ua = getUserAgent(this.id);

    private UserAgent() {
    }

    public static UserAgent instance() {
        return _instance;
    }

    private static String genId() {
        return System.currentTimeMillis() + "" + new Random().nextInt(NetworkInfo.ISP_OTHER);
    }

    static String getUserAgent(String id) {
        return String.format("QiniuAndroid/%s (%s; %s; %s", new Object[]{Constants.VERSION, osVersion(), device(), id});
    }

    private static String osVersion() {
        try {
            String v = VERSION.RELEASE;
            if (v == null) {
                return "-";
            }
            return StringUtils.strip(v.trim());
        } catch (Throwable th) {
            return "-";
        }
    }

    private static String device() {
        try {
            String model = Build.MODEL.trim();
            String device = deviceName(Build.MANUFACTURER.trim(), model);
            if (TextUtils.isEmpty(device)) {
                device = deviceName(Build.BRAND.trim(), model);
            }
            StringBuilder stringBuilder = new StringBuilder();
            if (device == null) {
                device = "-";
            }
            return StringUtils.strip(stringBuilder.append(device).append("-").append(model).toString());
        } catch (Throwable th) {
            return "-";
        }
    }

    private static String deviceName(String manufacturer, String model) {
        String str = manufacturer.toLowerCase(Locale.getDefault());
        if (str.startsWith("unknown") || str.startsWith("alps") || str.startsWith("android") || str.startsWith("sprd") || str.startsWith("spreadtrum") || str.startsWith("rockchip") || str.startsWith("wondermedia") || str.startsWith("mtk") || str.startsWith("mt65") || str.startsWith("nvidia") || str.startsWith("brcm") || str.startsWith("marvell") || model.toLowerCase(Locale.getDefault()).contains(str)) {
            return null;
        }
        return manufacturer;
    }

    public String getUa(String part) {
        String _part = ("" + part).trim();
        return new String((this.ua + "; " + _part.substring(0, Math.min(16, _part.length())) + l.t).getBytes(Charset.forName(f.a)));
    }
}
