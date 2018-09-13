package com.umeng.weixin.umengwx;

import android.os.Bundle;

public class i extends b {
    private static final String j = "MicroMsg.SDK.SendAuth.Resp";
    private static final int k = 1024;
    public String e;
    public String f;
    public String g;
    public String h;
    public String i;

    public i(Bundle bundle) {
        b(bundle);
    }

    public int a() {
        return 1;
    }

    public void a(Bundle bundle) {
        super.a(bundle);
        bundle.putString("_wxapi_sendauth_resp_token", this.e);
        bundle.putString("_wxapi_sendauth_resp_state", this.f);
        bundle.putString("_wxapi_sendauth_resp_url", this.g);
        bundle.putString("_wxapi_sendauth_resp_lang", this.h);
        bundle.putString("_wxapi_sendauth_resp_country", this.i);
    }

    public void b(Bundle bundle) {
        super.b(bundle);
        this.e = bundle.getString("_wxapi_sendauth_resp_token");
        this.f = bundle.getString("_wxapi_sendauth_resp_state");
        this.g = bundle.getString("_wxapi_sendauth_resp_url");
        this.h = bundle.getString("_wxapi_sendauth_resp_lang");
        this.i = bundle.getString("_wxapi_sendauth_resp_country");
    }

    public boolean b() {
        return this.f == null || this.f.length() <= 1024;
    }
}
