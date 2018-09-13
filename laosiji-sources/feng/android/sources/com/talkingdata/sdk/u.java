package com.talkingdata.sdk;

import android.app.Activity;
import android.content.Context;
import java.lang.reflect.Method;

/* compiled from: td */
class u implements bk {
    final /* synthetic */ zz this$0;
    final /* synthetic */ Context val$ctx;

    u(zz zzVar, Context context) {
        this.this$0 = zzVar;
        this.val$ctx = context;
    }

    public void afterMethodInvoked(Object obj, Method method, Object[] objArr, Object obj2) {
    }

    public void beforeMethodInvoke(Object obj, Method method, Object[] objArr) {
        String name = method.getName();
        if (!(this.val$ctx instanceof Activity)) {
            return;
        }
        if (name.equalsIgnoreCase("activityPaused")) {
            ag.b((Activity) this.val$ctx, a.TRACKING);
        } else if (name.equalsIgnoreCase("activityIdle")) {
            ag.a((Activity) this.val$ctx, a.TRACKING);
        }
    }
}
