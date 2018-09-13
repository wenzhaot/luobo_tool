package com.umeng.weixin.umengwx;

import android.os.Bundle;

public abstract class b {
    public int a;
    public String b;
    public String c;
    public String d;

    public abstract int a();

    public void a(Bundle bundle) {
        bundle.putInt("_wxapi_command_type", a());
        bundle.putInt("_wxapi_baseresp_errcode", this.a);
        bundle.putString("_wxapi_baseresp_errstr", this.b);
        bundle.putString("_wxapi_baseresp_transaction", this.c);
        bundle.putString("_wxapi_baseresp_openId", this.d);
    }

    public void b(Bundle bundle) {
        this.a = bundle.getInt("_wxapi_baseresp_errcode");
        this.b = bundle.getString("_wxapi_baseresp_errstr");
        this.c = bundle.getString("_wxapi_baseresp_transaction");
        this.d = bundle.getString("_wxapi_baseresp_openId");
    }

    public abstract boolean b();
}
