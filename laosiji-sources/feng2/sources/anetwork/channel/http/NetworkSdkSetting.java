package anetwork.channel.http;

import android.app.Application;
import android.content.Context;
import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.SessionCenter;
import anet.channel.entity.ENV;
import anet.channel.util.ALog;
import anetwork.channel.config.NetworkConfigCenter;
import anetwork.channel.cookie.CookieManager;
import anetwork.channel.monitor.Monitor;
import com.stub.StubApp;
import java.io.Serializable;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: Taobao */
public class NetworkSdkSetting implements Serializable {
    public static ENV CURRENT_ENV = ENV.ONLINE;
    private static final String TAG = "ANet.NetworkSdkSetting";
    private static Context context;
    private static AtomicBoolean isInit = new AtomicBoolean(false);

    public static void init(Context context) {
        if (context != null) {
            try {
                if (isInit.compareAndSet(false, true)) {
                    context = context;
                    SessionCenter.init(context);
                    Monitor.init();
                    NetworkConfigCenter.init();
                    CookieManager.setup(context);
                }
            } catch (Throwable th) {
                ALog.e(TAG, "Network SDK initial failed!", null, th, new Object[0]);
            }
        }
    }

    public static void init(Application application, HashMap<String, Object> hashMap) {
        try {
            GlobalAppRuntimeInfo.setTtid((String) hashMap.get("ttid"));
            init(StubApp.getOrigApplicationContext(application.getApplicationContext()));
        } catch (Throwable e) {
            ALog.e(TAG, "Network SDK initial failed!", null, e, new Object[0]);
        }
    }

    public static void setTtid(String str) {
        GlobalAppRuntimeInfo.setTtid(str);
    }

    public static Context getContext() {
        return context;
    }
}
