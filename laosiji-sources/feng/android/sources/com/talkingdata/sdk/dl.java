package com.talkingdata.sdk;

import com.feng.car.utils.HttpConstant;
import com.meizu.cloud.pushsdk.pushtracer.constant.Parameters;
import org.json.JSONObject;

/* compiled from: td */
public class dl extends dq {
    static dl a;

    private dl() {
    }

    public static synchronized dl a() {
        dl dlVar;
        synchronized (dl.class) {
            if (a == null) {
                a = new dl();
            }
            dlVar = a;
        }
        return dlVar;
    }

    public void setSessionId(String str) {
        a(Parameters.SESSION_ID, (Object) str);
    }

    public void setCurrentPageName(String str) {
        a(HttpConstant.PAGE, (Object) str);
    }

    public void setAccount(JSONObject jSONObject) {
        a("account", (Object) jSONObject);
    }

    public void setSubaccount(JSONObject jSONObject) {
        a("subaccount", (Object) jSONObject);
    }

    public void setDeepLink(String str) {
        try {
            a("deeplink", (Object) str);
            ar.setDeepLink(str);
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }

    public void setSessionStartTime(long j) {
        a("sessionStartTime", (Object) Long.valueOf(j));
    }

    public void setPushInfo(String str) {
        a("push", (Object) str);
    }

    public void setAntiCheatingstatus(int i) {
        a("antiCheating", (Object) Integer.valueOf(i));
    }
}
