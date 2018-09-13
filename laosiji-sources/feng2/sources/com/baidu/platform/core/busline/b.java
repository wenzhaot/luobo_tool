package com.baidu.platform.core.busline;

import anet.channel.strategy.dispatch.DispatchConstants;
import com.baidu.mapapi.search.busline.BusLineSearchOption;
import com.baidu.platform.base.e;
import com.baidu.platform.domain.c;

public class b extends e {
    public b(BusLineSearchOption busLineSearchOption) {
        a(busLineSearchOption);
    }

    private void a(BusLineSearchOption busLineSearchOption) {
        this.a.a("qt", "bsl");
        this.a.a("rt_info", "1");
        this.a.a("ie", "utf-8");
        this.a.a("oue", "0");
        this.a.a("c", busLineSearchOption.mCity);
        this.a.a("uid", busLineSearchOption.mUid);
        this.a.a(DispatchConstants.TIMESTAMP, System.currentTimeMillis() + "");
    }

    public String a(c cVar) {
        return cVar.m();
    }
}
