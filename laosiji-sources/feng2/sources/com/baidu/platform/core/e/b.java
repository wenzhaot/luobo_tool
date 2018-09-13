package com.baidu.platform.core.e;

import anet.channel.strategy.dispatch.DispatchConstants;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.inner.Point;
import com.baidu.mapapi.search.share.LocationShareURLOption;
import com.baidu.platform.base.e;
import com.baidu.platform.domain.c;

public class b extends e {
    public b(LocationShareURLOption locationShareURLOption) {
        a(locationShareURLOption);
    }

    private void a(LocationShareURLOption locationShareURLOption) {
        this.a.a("qt", "cs");
        Point ll2point = CoordUtil.ll2point(locationShareURLOption.mLocation);
        this.a.a("geo", ll2point.x + "|" + ll2point.y);
        this.a.a(DispatchConstants.TIMESTAMP, locationShareURLOption.mName);
        this.a.a("cnt", locationShareURLOption.mSnippet);
        b(false);
        a(false);
    }

    public String a(c cVar) {
        return cVar.q();
    }
}
