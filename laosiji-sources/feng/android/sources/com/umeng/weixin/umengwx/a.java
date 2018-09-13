package com.umeng.weixin.umengwx;

import android.os.Bundle;

public abstract class a {
    public String a;
    public String b;

    public abstract int a();

    public void a(Bundle bundle) {
        bundle.putInt("_wxapi_command_type", a());
        bundle.putString("_wxapi_basereq_transaction", this.a);
        bundle.putString("_wxapi_basereq_openid", this.b);
    }

    public void b(Bundle bundle) {
        this.a = f.b(bundle, "_wxapi_basereq_transaction");
        this.b = f.b(bundle, "_wxapi_basereq_openid");
    }

    public abstract boolean b();
}
