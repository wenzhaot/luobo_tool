package com.talkingdata.sdk;

import java.lang.reflect.Method;

/* compiled from: td */
public interface bk {
    void afterMethodInvoked(Object obj, Method method, Object[] objArr, Object obj2);

    void beforeMethodInvoke(Object obj, Method method, Object[] objArr);
}
