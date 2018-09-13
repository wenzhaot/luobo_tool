package com.umeng.commonsdk.framework;

import android.content.Context;

public class UMWorkDispatch {
    public static void sendEvent(Context context, int i, UMLogDataProtocol uMLogDataProtocol, Object obj) {
        d.a(context, i, uMLogDataProtocol, obj);
    }

    public static boolean eventHasExist(int i) {
        return d.a(i);
    }

    public static void Quit() {
        d.a();
    }
}
