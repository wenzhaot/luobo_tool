package com.umeng.debug.log;

import android.util.Log;

public class I implements UInterface {
    public void log(String str, String str2) {
        Log.i(str, str2);
    }
}
