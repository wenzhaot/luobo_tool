package com.talkingdata.sdk;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;

@TargetApi(14)
/* compiled from: td */
public class d implements ActivityLifecycleCallbacks {
    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    public void onActivityDestroyed(Activity activity) {
    }

    public void onActivityPaused(Activity activity) {
        try {
            ab.B = false;
            ag.b(activity, a.TRACKING);
        } catch (Throwable th) {
        }
    }

    public void onActivityResumed(Activity activity) {
        ab.D = true;
        ab.B = true;
        try {
            ag.a(activity, a.TRACKING);
            ab.m = true;
        } catch (Throwable th) {
        }
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    public void onActivityStarted(Activity activity) {
    }

    public void onActivityStopped(Activity activity) {
    }
}
