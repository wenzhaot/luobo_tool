package com.meizu.cloud.pushsdk.base.reflect;

import com.meizu.cloud.pushsdk.base.Logger;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;

public class ReflectMethod {
    private static HashMap<String, Method> mCachedMethods = new HashMap();
    private String TAG = "ReflectMethod";
    private String mMethodName;
    private ReflectClass mReflectClass;
    private Class<?>[] mTypes;

    private class NULL {
        private NULL() {
        }
    }

    ReflectMethod(ReflectClass reflectClass, String name, Class<?>... types) {
        this.mReflectClass = reflectClass;
        this.mMethodName = name;
        this.mTypes = types;
    }

    private boolean match(Class<?>[] declaredTypes, Class<?>[] actualTypes) {
        if (declaredTypes.length != actualTypes.length) {
            return false;
        }
        int i = 0;
        while (i < actualTypes.length) {
            if (actualTypes[i] != NULL.class && !wrapper(declaredTypes[i]).isAssignableFrom(wrapper(actualTypes[i]))) {
                return false;
            }
            i++;
        }
        return true;
    }

    private boolean isSimilarSignature(Method possiblyMatchingMethod, String desiredMethodName, Class<?>[] desiredParamTypes) {
        return possiblyMatchingMethod.getName().equals(desiredMethodName) && match(possiblyMatchingMethod.getParameterTypes(), desiredParamTypes);
    }

    private Method similarMethod() throws NoSuchMethodException, ClassNotFoundException {
        Method method;
        int i = 0;
        Class<?> clz = this.mReflectClass.getRealClass();
        for (Method method2 : clz.getMethods()) {
            if (isSimilarSignature(method2, this.mMethodName, this.mTypes)) {
                return method2;
            }
        }
        Method[] declaredMethods = clz.getDeclaredMethods();
        int length = declaredMethods.length;
        while (i < length) {
            method2 = declaredMethods[i];
            if (isSimilarSignature(method2, this.mMethodName, this.mTypes)) {
                return method2;
            }
            i++;
        }
        throw new NoSuchMethodException("No similar method " + this.mMethodName + " with params " + Arrays.toString(this.mTypes) + " could be found on type " + clz);
    }

    private String getKey() throws ClassNotFoundException {
        StringBuffer buffer = new StringBuffer(this.mReflectClass.getRealClass().getName());
        buffer.append(this.mMethodName);
        for (Class<?> type : this.mTypes) {
            buffer.append(type.getName());
        }
        return buffer.toString();
    }

    public <T> ReflectResult<T> invoke(Object receiver, Object... args) {
        ReflectResult<T> result = new ReflectResult();
        try {
            String key = getKey();
            Method method = (Method) mCachedMethods.get(key);
            if (method == null) {
                if (this.mTypes.length == args.length) {
                    method = this.mReflectClass.getRealClass().getMethod(this.mMethodName, this.mTypes);
                } else {
                    if (args.length > 0) {
                        this.mTypes = new Class[args.length];
                        for (int i = 0; i < args.length; i++) {
                            this.mTypes[i] = args[i].getClass();
                        }
                    }
                    method = similarMethod();
                }
                mCachedMethods.put(key, method);
            }
            method.setAccessible(true);
            result.value = method.invoke(receiver, args);
            result.ok = true;
        } catch (Exception e) {
            Logger.get().e(this.TAG, "invoke", e);
        }
        return result;
    }

    public <T> ReflectResult<T> invokeStatic(Object... args) {
        try {
            return invoke(this.mReflectClass.getRealClass(), args);
        } catch (ClassNotFoundException e) {
            return new ReflectResult();
        }
    }

    private Class<?> wrapper(Class<?> type) {
        if (type == null) {
            return null;
        }
        if (!type.isPrimitive()) {
            return type;
        }
        if (Boolean.TYPE == type) {
            return Boolean.class;
        }
        if (Integer.TYPE == type) {
            return Integer.class;
        }
        if (Long.TYPE == type) {
            return Long.class;
        }
        if (Short.TYPE == type) {
            return Short.class;
        }
        if (Byte.TYPE == type) {
            return Byte.class;
        }
        if (Double.TYPE == type) {
            return Double.class;
        }
        if (Float.TYPE == type) {
            return Float.class;
        }
        if (Character.TYPE == type) {
            return Character.class;
        }
        if (Void.TYPE == type) {
            return Void.class;
        }
        return type;
    }
}
