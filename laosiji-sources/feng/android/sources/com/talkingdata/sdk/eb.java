package com.talkingdata.sdk;

import com.taobao.accs.utl.UtilityImpl;

/* compiled from: td */
public enum eb {
    WIFI(UtilityImpl.NET_TYPE_WIFI),
    CELLULAR("cellular"),
    BLUETOOTH("bluetooth");
    
    private String d;

    private eb(String str) {
        this.d = str;
    }

    public String a() {
        return this.d;
    }
}
