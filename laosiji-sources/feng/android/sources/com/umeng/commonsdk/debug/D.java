package com.umeng.commonsdk.debug;

import android.util.Log;

public class D implements UInterface {
    public void log(String str, String str2) {
        Log.d(str, str2);
    }
}
