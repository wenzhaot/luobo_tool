package com.umeng.analytics;

import android.content.Context;
import com.stub.StubApp;
import com.umeng.analytics.pro.j;
import com.umeng.commonsdk.framework.UMLogDataProtocol;
import org.json.JSONObject;

public class CoreProtocol implements UMLogDataProtocol {
    private static Context a = null;

    private static class a {
        private static final CoreProtocol a = new CoreProtocol();

        private a() {
        }
    }

    private CoreProtocol() {
    }

    public static CoreProtocol getInstance(Context context) {
        if (a == null && context != null) {
            a = StubApp.getOrigApplicationContext(context.getApplicationContext());
        }
        return a.a;
    }

    public void workEvent(Object obj, int i) {
        j.a(a).a(obj, i);
    }

    public void removeCacheData(Object obj) {
        j.a(a).a(obj);
    }

    public JSONObject setupReportData(long j) {
        return j.a(a).b(j);
    }
}
