package com.meizu.cloud.pushsdk.base.reflect;

import java.util.HashMap;

public class ReflectClass {
    private static HashMap<String, Class<?>> mCachedClasses = new HashMap();
    private Class<?> mClass;
    private String mClassName;
    private Object mClassObject;

    private ReflectClass(String name) {
        this.mClassName = name;
    }

    private ReflectClass(Object object) {
        this.mClassObject = object;
    }

    public ReflectClass(Class<?> clz) {
        this.mClass = clz;
    }

    Class<?> getRealClass() throws ClassNotFoundException {
        if (this.mClass != null) {
            return this.mClass;
        }
        if (this.mClassObject != null) {
            return this.mClassObject.getClass();
        }
        Class<?> clz = (Class) mCachedClasses.get(this.mClassName);
        if (clz != null) {
            return clz;
        }
        clz = Class.forName(this.mClassName);
        mCachedClasses.put(this.mClassName, clz);
        return clz;
    }

    public static ReflectClass forName(String className) {
        return new ReflectClass(className);
    }

    public static ReflectClass forObject(Object classObject) {
        return new ReflectClass(classObject);
    }

    public ReflectMethod method(String methodName, Class<?>... types) {
        return new ReflectMethod(this, methodName, types);
    }

    public ReflectField field(String fieldName) {
        return new ReflectField(this, fieldName);
    }

    public ReflectConstructor constructor(Class<?>... types) {
        return new ReflectConstructor(this, types);
    }
}
