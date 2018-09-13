package com.umeng.commonsdk.statistics.idtracking;

import android.content.Context;
import com.umeng.commonsdk.proguard.b;
import com.umeng.commonsdk.statistics.AnalyticsConstants;
import com.umeng.commonsdk.statistics.common.DeviceConfig;

/* compiled from: MacTracker */
public class g extends a {
    private static final String a = "mac";
    private Context b;

    public g(Context context) {
        super("mac");
        this.b = context;
    }

    public String f() {
        String str = null;
        try {
            return DeviceConfig.getMac(this.b);
        } catch (Throwable e) {
            if (AnalyticsConstants.UM_DEBUG) {
                e.printStackTrace();
            }
            b.a(this.b, e);
            return str;
        }
    }
}
