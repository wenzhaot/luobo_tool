package com.umeng.analytics.pro;

import com.umeng.analytics.AnalyticsConfig;
import java.lang.Thread.UncaughtExceptionHandler;

/* compiled from: CrashHandler */
public class k implements UncaughtExceptionHandler {
    private UncaughtExceptionHandler a;
    private o b;

    public k() {
        if (Thread.getDefaultUncaughtExceptionHandler() != this) {
            this.a = Thread.getDefaultUncaughtExceptionHandler();
            Thread.setDefaultUncaughtExceptionHandler(this);
        }
    }

    public void a(o oVar) {
        this.b = oVar;
    }

    public void uncaughtException(Thread thread, Throwable th) {
        a(th);
        if (this.a != null && this.a != Thread.getDefaultUncaughtExceptionHandler()) {
            this.a.uncaughtException(thread, th);
        }
    }

    private void a(Throwable th) {
        if (AnalyticsConfig.CATCH_EXCEPTION) {
            this.b.a(th);
        } else {
            this.b.a(null);
        }
    }
}
