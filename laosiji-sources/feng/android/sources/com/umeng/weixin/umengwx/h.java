package com.umeng.weixin.umengwx;

import android.os.Bundle;

public class h extends a {
    private static final String e = "MicroMsg.SDK.SendAuth.Req";
    private static final int f = 1024;
    public String c;
    public String d;

    public h(Bundle bundle) {
        b(bundle);
    }

    public int a() {
        return 1;
    }

    public void a(Bundle bundle) {
        super.a(bundle);
        bundle.putString("_wxapi_sendauth_req_scope", this.c);
        bundle.putString("_wxapi_sendauth_req_state", this.d);
    }

    public void b(Bundle bundle) {
        super.b(bundle);
        this.c = bundle.getString("_wxapi_sendauth_req_scope");
        this.d = bundle.getString("_wxapi_sendauth_req_state");
    }

    public boolean b() {
        return (this.c == null || this.c.length() == 0 || this.c.length() > 1024) ? false : this.d == null || this.d.length() <= 1024;
    }
}
