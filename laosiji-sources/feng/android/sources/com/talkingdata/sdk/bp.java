package com.talkingdata.sdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/* compiled from: td */
final class bp implements InvocationHandler {
    final /* synthetic */ bk val$callback;
    final /* synthetic */ Object val$real;

    bp(bk bkVar, Object obj) {
        this.val$callback = bkVar;
        this.val$real = obj;
    }

    public Object invoke(Object obj, Method method, Object[] objArr) {
        this.val$callback.beforeMethodInvoke(obj, method, objArr);
        Object invoke = method.invoke(this.val$real, objArr);
        this.val$callback.afterMethodInvoked(obj, method, objArr, invoke);
        return invoke;
    }
}
