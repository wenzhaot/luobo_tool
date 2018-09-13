package com.umeng.qq.tencent;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import com.tencent.ijk.media.player.IMediaPlayer;
import com.umeng.socialize.utils.DeviceConfig;

public class Wifig {
    public static int a(String var0, String var1) {
        if (var0 == null && var1 == null) {
            return 0;
        }
        if (var0 != null && var1 == null) {
            return 1;
        }
        if (var0 == null && var1 != null) {
            return -1;
        }
        String[] var2 = var0.split("\\.");
        String[] var3 = var1.split("\\.");
        int var4 = 0;
        while (var4 < var2.length && var4 < var3.length) {
            try {
                int var5 = Integer.parseInt(var2[var4]);
                int var6 = Integer.parseInt(var3[var4]);
                if (var5 < var6) {
                    return -1;
                }
                if (var5 > var6) {
                    return 1;
                }
                var4++;
            } catch (NumberFormatException e) {
                return var0.compareTo(var1);
            }
        }
        if (var2.length <= var4) {
            return var3.length > var4 ? -1 : 0;
        } else {
            return 1;
        }
    }

    public static boolean a(Context var0, Intent var1) {
        if (var0 == null || var1 == null || var0.getPackageManager().queryIntentActivities(var1, 0).size() == 0) {
            return false;
        }
        return true;
    }

    public static String a(Context var0) {
        return var0.getApplicationInfo().loadLabel(var0.getPackageManager()).toString();
    }

    public static int c(Context var0, String var1) {
        return a(DeviceConfig.getAppVersion("com.tencent.mobileqq", var0), var1);
    }

    public static boolean b(Context var0) {
        PackageInfo var2 = null;
        try {
            var2 = var0.getPackageManager().getPackageInfo("com.tencent.mobileqq", 0);
        } catch (NameNotFoundException var7) {
            var7.printStackTrace();
        }
        if (var2 == null) {
            return false;
        }
        try {
            String[] var4 = var2.versionName.split("\\.");
            int var5 = Integer.parseInt(var4[0]);
            int var6 = Integer.parseInt(var4[1]);
            if (var5 > 4 || (var5 == 4 && var6 >= 1)) {
                return true;
            }
            return false;
        } catch (Exception var8) {
            var8.printStackTrace();
            return false;
        }
    }

    public static int a(String var0) {
        if ("shareToQQ".equals(var0)) {
            return 10103;
        }
        if ("shareToQzone".equals(var0)) {
            return 10104;
        }
        if ("addToQQFavorites".equals(var0)) {
            return 10105;
        }
        if ("sendToMyComputer".equals(var0)) {
            return 10106;
        }
        if ("shareToTroopBar".equals(var0)) {
            return 10107;
        }
        if ("action_login".equals(var0)) {
            return 11101;
        }
        return "action_request".equals(var0) ? IMediaPlayer.MEDIA_INFO_MEDIA_ACCURATE_SEEK_COMPLETE : -1;
    }

    public static String a(int var0) {
        if (var0 == 10103) {
            return "shareToQQ";
        }
        if (var0 == 10104) {
            return "shareToQzone";
        }
        if (var0 == 10105) {
            return "addToQQFavorites";
        }
        if (var0 == 10106) {
            return "sendToMyComputer";
        }
        if (var0 == 10107) {
            return "shareToTroopBar";
        }
        if (var0 == 11101) {
            return "action_login";
        }
        return var0 == IMediaPlayer.MEDIA_INFO_MEDIA_ACCURATE_SEEK_COMPLETE ? "action_request" : null;
    }
}
