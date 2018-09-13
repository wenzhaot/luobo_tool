package com.umeng.commonsdk.framework;

import android.content.Context;
import com.stub.StubApp;
import com.umeng.commonsdk.framework.UMLogDataProtocol.UMBusinessType;
import com.umeng.commonsdk.statistics.b;
import com.umeng.commonsdk.statistics.common.e;
import com.umeng.commonsdk.statistics.idtracking.ImprintHandler;
import org.json.JSONObject;

/* compiled from: UMEnvelopeBuildImpl */
public class a {
    public static long a(Context context) {
        if (context == null) {
            return 0;
        }
        return b.i(StubApp.getOrigApplicationContext(context.getApplicationContext()));
    }

    public static boolean a(Context context, UMBusinessType uMBusinessType) {
        boolean z = false;
        if (context != null) {
            Context origApplicationContext = StubApp.getOrigApplicationContext(context.getApplicationContext());
            boolean b = b.b(origApplicationContext);
            int c = b.c(origApplicationContext);
            if (b && !b.a(origApplicationContext, uMBusinessType)) {
                z = true;
            }
            if (b && c > 0) {
                c.b();
            }
        }
        return z;
    }

    public static JSONObject a(Context context, JSONObject jSONObject, JSONObject jSONObject2) {
        e.b("--->>> buildEnvelopeFile Enter.");
        return new b().a(StubApp.getOrigApplicationContext(context.getApplicationContext()), jSONObject, jSONObject2);
    }

    public static String a(Context context, String str, String str2) {
        return context == null ? str2 : ImprintHandler.getImprintService(StubApp.getOrigApplicationContext(context.getApplicationContext())).b().a(str, str2);
    }

    public static long b(Context context) {
        if (context == null) {
            return 0;
        }
        return b.a(StubApp.getOrigApplicationContext(context.getApplicationContext()));
    }
}
