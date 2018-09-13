package com.umeng.qq.tencent;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.DisplayMetrics;
import com.umeng.message.common.inter.ITagManager;
import com.umeng.socialize.utils.DeviceConfig;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtil {
    public static Bundle a(String var0) {
        Bundle var1 = new Bundle();
        if (var0 == null) {
            return var1;
        }
        try {
            String[] var2 = var0.split("&");
            String[] var3 = var2;
            int var4 = var2.length;
            for (int var5 = 0; var5 < var4; var5++) {
                String[] var7 = var3[var5].split("=");
                if (var7.length == 2) {
                    var1.putString(URLDecoder.decode(var7[0]), URLDecoder.decode(var7[1]));
                }
            }
            return var1;
        } catch (Exception e) {
            return null;
        }
    }

    public static JSONObject d(String var0) throws JSONException {
        if (var0.equals("false")) {
            var0 = "{value : false}";
        }
        if (var0.equals(ITagManager.STATUS_TRUE)) {
            var0 = "{value : true}";
        }
        if (var0.contains("allback(")) {
            var0 = var0.replaceFirst("[\\s\\S]*allback\\(([\\s\\S]*)\\);[^\\)]*\\z", "$1").trim();
        }
        if (var0.contains("online[0]=")) {
            var0 = "{online:" + var0.charAt(var0.length() - 2) + "}";
        }
        return new JSONObject(var0);
    }

    public static boolean e(String var0) {
        return var0 == null || var0.length() == 0;
    }

    public static boolean a(Context var0) {
        PackageInfo var2;
        PackageManager var1 = var0.getPackageManager();
        if (e(var0)) {
            try {
                var2 = var1.getPackageInfo("com.tencent.minihd.qq", 0);
                return true;
            } catch (NameNotFoundException e) {
            }
        }
        try {
            if (Wifig.a(var1.getPackageInfo("com.tencent.mobileqq", 0).versionName, "4.1") < 0) {
                return false;
            }
            return true;
        } catch (NameNotFoundException e2) {
            try {
                var2 = var1.getPackageInfo("com.tencent.tim", 0);
                return true;
            } catch (NameNotFoundException e3) {
                return false;
            }
        }
    }

    public static final String b(Context var0) {
        if (var0 != null) {
            CharSequence var1 = var0.getPackageManager().getApplicationLabel(var0.getApplicationInfo());
            if (var1 != null) {
                return var1.toString();
            }
        }
        return null;
    }

    public static boolean h(String var0) {
        if (var0 == null) {
            return false;
        }
        File var1 = new File(var0);
        if (var1 == null || !var1.exists()) {
            return false;
        }
        return true;
    }

    public static byte[] i(String var0) {
        try {
            return var0.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static boolean e(Context var0) {
        double var1 = 0.0d;
        try {
            DisplayMetrics var3 = var0.getResources().getDisplayMetrics();
            var1 = Math.sqrt(Math.pow((double) (((float) var3.widthPixels) / var3.xdpi), 2.0d) + Math.pow((double) (((float) var3.heightPixels) / var3.ydpi), 2.0d));
        } catch (Throwable th) {
        }
        return var1 > 6.5d;
    }

    public static boolean f(Context var0, String var1) {
        boolean var2 = true;
        if (e(var0) && DeviceConfig.getAppVersion("com.tencent.minihd.qq", var0) != null) {
            var2 = false;
        }
        if (var2 && DeviceConfig.getAppVersion("com.tencent.tim", var0) != null) {
            var2 = false;
        }
        if (var2) {
            return Wifig.c(var0, var1) < 0;
        } else {
            return var2;
        }
    }
}
