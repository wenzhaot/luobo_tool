package com.taobao.accs.client;

import android.app.ActivityManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.text.TextUtils;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.stub.StubApp;
import com.taobao.accs.IProcessName;
import com.taobao.accs.utl.ALog;
import com.taobao.agoo.TaobaoConstants;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: Taobao */
public class a {
    public static final int SECURITY_OFF = 2;
    public static final int SECURITY_OPEN = 1;
    public static final int SECURITY_TAOBAO = 0;
    public static int a = 0;
    public static String b = null;
    public static String c;
    public static String d;
    public static String e;
    public static IProcessName f;
    public static AtomicInteger g = new AtomicInteger(-1);
    private static volatile a h;
    private static Context i;
    private ActivityManager j;
    private ConnectivityManager k;

    public static a a(Context context) {
        if (h == null) {
            synchronized (a.class) {
                if (h == null) {
                    h = new a(context);
                }
            }
        }
        return h;
    }

    private a(Context context) {
        if (context == null) {
            throw new RuntimeException("Context is null!!");
        } else if (i == null) {
            i = StubApp.getOrigApplicationContext(context.getApplicationContext());
        }
    }

    public ActivityManager a() {
        if (this.j == null) {
            this.j = (ActivityManager) i.getSystemService(PushConstants.INTENT_ACTIVITY_NAME);
        }
        return this.j;
    }

    public ConnectivityManager b() {
        if (this.k == null) {
            this.k = (ConnectivityManager) i.getSystemService("connectivity");
        }
        return this.k;
    }

    public static String a(String str) {
        String str2;
        if (TextUtils.isEmpty(b)) {
            str2 = str + TaobaoConstants.DEFAULT_INTENT_SERVICE_CLASS_NAME;
        } else {
            str2 = b;
        }
        ALog.d("AdapterGlobalClientInfo", "getAgooCustomServiceName", "name", str2);
        return str2;
    }

    public static boolean c() {
        return g.intValue() == 0;
    }
}
