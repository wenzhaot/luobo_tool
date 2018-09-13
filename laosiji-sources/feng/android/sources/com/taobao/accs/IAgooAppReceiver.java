package com.taobao.accs;

import java.util.Map;

/* compiled from: Taobao */
public abstract class IAgooAppReceiver extends IAppReceiverV1 {
    public abstract String getAppkey();

    public void onUnbindApp(int i) {
    }

    public void onBindUser(String str, int i) {
    }

    public void onUnbindUser(int i) {
    }

    public String getService(String str) {
        return null;
    }

    public Map<String, String> getAllServices() {
        return null;
    }
}
