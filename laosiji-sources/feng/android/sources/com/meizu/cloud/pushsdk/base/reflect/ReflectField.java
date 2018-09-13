package com.meizu.cloud.pushsdk.base.reflect;

import com.meizu.cloud.pushsdk.base.Logger;
import java.lang.reflect.Field;

public class ReflectField {
    private String TAG = "ReflectField";
    private String mFieldName;
    private ReflectClass mReflectClass;

    ReflectField(ReflectClass reflectClass, String fieldName) {
        this.mReflectClass = reflectClass;
        this.mFieldName = fieldName;
    }

    private Field getField() throws ClassNotFoundException, NoSuchFieldException {
        Field field = this.mReflectClass.getRealClass().getDeclaredField(this.mFieldName);
        field.setAccessible(true);
        return field;
    }

    public <T> ReflectResult<T> set(Object receiver, T value) {
        ReflectResult<T> result = new ReflectResult();
        try {
            getField().set(receiver, value);
            result.value = value;
            result.ok = true;
        } catch (Exception e) {
            Logger.get().e(this.TAG, "set", e);
        }
        return result;
    }

    public <T> ReflectResult<T> setStatic(T value) {
        try {
            return set(this.mReflectClass.getRealClass(), value);
        } catch (Exception e) {
            Logger.get().e(this.TAG, "setStatic", e);
            return new ReflectResult();
        }
    }

    public <T> ReflectResult<T> get(Object receiver) {
        ReflectResult<T> result = new ReflectResult();
        try {
            result.value = getField().get(receiver);
            result.ok = true;
        } catch (Exception e) {
            Logger.get().e(this.TAG, "get", e);
        }
        return result;
    }

    public <T> ReflectResult<T> getStatic() {
        try {
            return get(this.mReflectClass.getRealClass());
        } catch (Exception e) {
            Logger.get().e(this.TAG, "getStatic", e);
            return new ReflectResult();
        }
    }
}
