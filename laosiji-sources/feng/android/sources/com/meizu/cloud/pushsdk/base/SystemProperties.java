package com.meizu.cloud.pushsdk.base;

import com.meizu.cloud.pushsdk.base.reflect.ReflectClass;
import com.meizu.cloud.pushsdk.base.reflect.ReflectResult;

public class SystemProperties {
    private static final String CLASS_NAME = "android.os.SystemProperties";
    private static final String METHOD_NAME = "get";

    public static String get(String name) {
        ReflectResult<String> result = ReflectClass.forName(CLASS_NAME).method(METHOD_NAME, String.class).invokeStatic(name);
        if (result.ok) {
            return (String) result.value;
        }
        return null;
    }
}
