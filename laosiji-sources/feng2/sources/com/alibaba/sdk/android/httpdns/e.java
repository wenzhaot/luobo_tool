package com.alibaba.sdk.android.httpdns;

import org.json.JSONObject;

class e {
    private int b;
    private String e;

    e(int i, String str) {
        this.b = i;
        this.e = new JSONObject(str).getString("code");
    }

    public String a() {
        return this.e;
    }
}
