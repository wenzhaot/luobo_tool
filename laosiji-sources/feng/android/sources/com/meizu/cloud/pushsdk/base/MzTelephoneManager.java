package com.meizu.cloud.pushsdk.base;

import android.content.Context;
import android.telephony.TelephonyManager;
import com.feng.car.utils.HttpConstant;
import com.meizu.cloud.pushsdk.base.reflect.ReflectClass;
import com.meizu.cloud.pushsdk.base.reflect.ReflectResult;

public class MzTelephoneManager {
    private static final String CLASS_NAME = "android.telephony.MzTelephonyManager";
    private static final String METHOD_NAME = "getDeviceId";

    public static String getDeviceId(Context context) {
        ReflectResult<String> result = ReflectClass.forName(CLASS_NAME).method(METHOD_NAME, new Class[0]).invokeStatic(new Object[0]);
        if (result.ok) {
            return result.value;
        }
        return ((TelephonyManager) context.getSystemService(HttpConstant.PHONE)).getDeviceId();
    }
}
