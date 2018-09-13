package com.qiniu.android.utils;

import android.app.Application;
import android.content.Context;
import com.stub.StubApp;

public final class ContextGetter {
    public static Context applicationContext() {
        try {
            return StubApp.getOrigApplicationContext(getApplicationUsingReflection().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Application getApplicationUsingReflection() throws Exception {
        return (Application) Class.forName("android.app.ActivityThread").getMethod("currentApplication", new Class[0]).invoke(null, (Object[]) null);
    }
}
