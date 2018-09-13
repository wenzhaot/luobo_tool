package com.meizu.cloud.pushsdk.base.reflect;

import com.meizu.cloud.pushsdk.base.Logger;
import java.lang.reflect.Constructor;

public class ReflectConstructor {
    private String TAG = "ReflectConstructor";
    private ReflectClass mReflectClass;
    private Class<?>[] mTypes;

    ReflectConstructor(ReflectClass reflectClass, Class<?>... types) {
        this.mReflectClass = reflectClass;
        this.mTypes = types;
    }

    public <T> ReflectResult<T> newInstance(Object... args) {
        ReflectResult<T> result = new ReflectResult();
        try {
            Constructor<?> constructor = this.mReflectClass.getRealClass().getDeclaredConstructor(this.mTypes);
            constructor.setAccessible(true);
            result.value = constructor.newInstance(args);
            result.ok = true;
        } catch (Exception e) {
            Logger.get().e(this.TAG, "newInstance", e);
        }
        return result;
    }
}
