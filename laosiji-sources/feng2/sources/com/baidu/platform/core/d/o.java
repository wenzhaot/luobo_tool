package com.baidu.platform.core.d;

import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.platform.base.e;
import com.baidu.platform.domain.c;

public class o extends e {
    public o(WalkingRoutePlanOption walkingRoutePlanOption) {
        a(walkingRoutePlanOption);
    }

    private void a(WalkingRoutePlanOption walkingRoutePlanOption) {
        this.a.a("qt", "walk2");
        this.a.a("sn", a(walkingRoutePlanOption.mFrom));
        this.a.a("en", a(walkingRoutePlanOption.mTo));
        if (walkingRoutePlanOption.mFrom != null) {
            this.a.a("sc", walkingRoutePlanOption.mFrom.getCity());
        }
        if (walkingRoutePlanOption.mTo != null) {
            this.a.a("ec", walkingRoutePlanOption.mTo.getCity());
        }
        this.a.a("ie", "utf-8");
        this.a.a("lrn", "20");
        this.a.a("version", "3");
        this.a.a("rp_format", "json");
        this.a.a("rp_filter", "mobile");
    }

    public String a(c cVar) {
        return cVar.k();
    }
}
