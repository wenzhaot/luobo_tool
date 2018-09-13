package com.umeng.socialize.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.util.List;

public class CommonUtil {
    public static boolean checkPath(String str) {
        try {
            if (Class.forName(str) == null) {
                return false;
            }
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static boolean checkAndroidManifest(Context context, String str) {
        try {
            context.getPackageManager().getActivityInfo(new ComponentName(context.getPackageName(), str), 0);
            return true;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    public static boolean checkPermission(Context context, String str) {
        return context.getPackageManager().checkPermission(str, context.getPackageName()) == 0;
    }

    public static boolean checkIntentFilterData(Context context, String str) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addCategory("android.intent.category.BROWSABLE");
        intent.setData(Uri.parse(SocializeProtocolConstants.PROTOCOL_KEY_TENCENT + str + ":"));
        List<ResolveInfo> queryIntentActivities = context.getPackageManager().queryIntentActivities(intent, 64);
        if (queryIntentActivities.size() <= 0) {
            return false;
        }
        for (ResolveInfo resolveInfo : queryIntentActivities) {
            if (resolveInfo.activityInfo != null && resolveInfo.activityInfo.packageName.equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkResource(Context context, String str, String str2) {
        if (context.getResources().getIdentifier(str, str2, context.getPackageName()) <= 0) {
            return false;
        }
        return true;
    }

    public static boolean checkMetaData(Context context, String str) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo == null || applicationInfo.metaData.get(str) == null) {
                return false;
            }
            return true;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    public static String checkKeyHash(Context context) {
        return "";
    }

    public static String checkMD5Sign(Context context) {
        return "";
    }

    public static String checkSHA1(Context context) {
        return "";
    }
}
