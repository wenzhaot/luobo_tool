package com.umeng.commonsdk.debug;

import android.util.Log;

public class W implements UInterface {
    public void log(String str, String str2) {
        Log.w(str, str2);
    }
}
