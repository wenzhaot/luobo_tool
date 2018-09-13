package org.android.agoo.huawei;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import com.taobao.accs.utl.UtilityImpl;
import com.taobao.agoo.BaseNotifyClickActivity;
import org.android.agoo.common.AgooConstants;

public class HuaWeiRegister {
    private static final String TAG = "accs.HuaWeiRegister";
    private static String phoneBrand = Build.BRAND;

    private static boolean checkDevice(Context context) {
        return phoneBrand.equalsIgnoreCase(AgooConstants.MESSAGE_SYSTEM_SOURCE_HUAWEI) || phoneBrand.equalsIgnoreCase("honor");
    }

    public static void register(Context context) {
        try {
            if (!UtilityImpl.isMainProcess(context)) {
                Log.e(TAG, "register not in main process, return");
            } else if (checkDevice(context)) {
                BaseNotifyClickActivity.addNotifyListener(new HuaweiMsgParseImpl());
                new Thread(new 1(context)).start();
            } else {
                Log.e(TAG, "checkDevice false");
            }
        } catch (Throwable th) {
            Log.e(TAG, th.getMessage());
        }
    }
}
