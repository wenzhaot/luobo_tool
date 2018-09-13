package com.umeng.commonsdk.framework;

import android.content.Context;
import com.stub.StubApp;
import java.util.HashMap;

public class UMModuleRegister {
    public static final String ANALYTICS = "analytics";
    public static final String INNER = "internal";
    private static final int INNER_EVENT_VALUE_HIGH = 36864;
    private static final int INNER_EVENT_VALUE_LOW = 32769;
    public static final String PUSH = "push";
    private static final int PUSH_EVENT_VALUE_HIGH = 20480;
    private static final int PUSH_EVENT_VALUE_LOW = 16385;
    public static final String SHARE = "share";
    private static final int SHARE_EVENT_VALUE_HIGH = 28672;
    private static final int SHARE_EVENT_VALUE_LOW = 24577;
    private static Context mModuleAppContext = null;
    private static HashMap<String, UMLogDataProtocol> mModuleMap = null;

    public static String eventType2ModuleName(int i) {
        String str = "analytics";
        if (i >= 16385 && i <= PUSH_EVENT_VALUE_HIGH) {
            str = "push";
        }
        if (i >= 24577 && i <= SHARE_EVENT_VALUE_HIGH) {
            str = "share";
        }
        if (i < 32769 || i > INNER_EVENT_VALUE_HIGH) {
            return str;
        }
        return INNER;
    }

    public static boolean registerCallback(int i, UMLogDataProtocol uMLogDataProtocol) {
        if (mModuleMap == null) {
            mModuleMap = new HashMap();
        }
        String eventType2ModuleName = eventType2ModuleName(i);
        if (mModuleMap.containsKey(eventType2ModuleName)) {
            return true;
        }
        if (!getAppContext().getPackageName().equals(b.a(StubApp.getOrigApplicationContext(getAppContext().getApplicationContext())))) {
            return false;
        }
        mModuleMap.put(eventType2ModuleName, uMLogDataProtocol);
        return true;
    }

    public static void registerAppContext(Context context) {
        if (mModuleAppContext == null) {
            mModuleAppContext = StubApp.getOrigApplicationContext(context.getApplicationContext());
        }
    }

    public static UMLogDataProtocol getCallbackFromModuleName(String str) {
        if (mModuleMap.containsKey(str)) {
            return (UMLogDataProtocol) mModuleMap.get(str);
        }
        return null;
    }

    public static Context getAppContext() {
        return mModuleAppContext;
    }
}
