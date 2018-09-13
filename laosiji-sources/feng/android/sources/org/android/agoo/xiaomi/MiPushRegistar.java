package org.android.agoo.xiaomi;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import com.taobao.accs.utl.UtilityImpl;
import com.taobao.agoo.BaseNotifyClickActivity;
import com.xiaomi.mipush.sdk.MiPushClient;

public class MiPushRegistar {
    private static final String PACKAGE_XIAOMI = "com.xiaomi.xmsf";
    private static final String TAG = "accs.MiPushRegistar";
    private static final String XIAOMI = "Xiaomi".toLowerCase();
    private static String phoneBrand = Build.BRAND;

    public static boolean checkDevice(Context context) {
        boolean z = false;
        try {
            PackageManager packageManager = context.getPackageManager();
            if (TextUtils.equals(XIAOMI, phoneBrand.toLowerCase())) {
                PackageInfo packageInfo = packageManager.getPackageInfo(PACKAGE_XIAOMI, 4);
                if (packageInfo != null && packageInfo.versionCode >= 105) {
                    z = true;
                }
            }
        } catch (Throwable th) {
            Log.e(TAG, "checkDevice error: " + th);
        }
        Log.e(TAG, "checkDevice result: " + z);
        return z;
    }

    public static void register(Context context, String str, String str2) {
        try {
            if (!UtilityImpl.isMainProcess(context)) {
                Log.e(TAG, "register not in main process, return");
            } else if (checkDevice(context)) {
                Log.e(TAG, "register begin");
                BaseNotifyClickActivity.addNotifyListener(new XiaoMiNotifyListener(null));
                new Thread(new 1(str, str2, context)).start();
            }
        } catch (Throwable th) {
            Log.d(TAG, "register error: " + th.getMessage());
        }
    }

    private static void registerMiPush(String str, String str2, Context context) {
        try {
            MiPushClient.registerPush(context, str, str2);
        } catch (Throwable th) {
            Log.e(TAG, "registerMiPush handleRegistrar error=" + th);
        }
    }

    public static void unregister(Context context) {
        try {
            new Thread(new 2(context)).start();
        } catch (Throwable th) {
            Log.e(TAG, "MiPush unregister error=" + th);
        }
    }
}
