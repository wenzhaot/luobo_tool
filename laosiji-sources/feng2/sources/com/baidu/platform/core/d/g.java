package com.baidu.platform.core.d;

import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.search.route.IndoorRoutePlanOption;
import com.baidu.platform.base.e;
import com.baidu.platform.domain.c;

public class g extends e {
    g(IndoorRoutePlanOption indoorRoutePlanOption) {
        a(indoorRoutePlanOption);
    }

    private void a(IndoorRoutePlanOption indoorRoutePlanOption) {
        this.a.a("qt", "indoornavi");
        this.a.a("rp_format", "json");
        this.a.a("version", "1");
        String str = "";
        if (CoordUtil.ll2mc(indoorRoutePlanOption.mFrom.getLocation()) != null) {
            this.a.a("sn", (String.format("%f,%f", new Object[]{Double.valueOf(CoordUtil.ll2mc(indoorRoutePlanOption.mFrom.getLocation()).getLongitudeE6()), Double.valueOf(CoordUtil.ll2mc(indoorRoutePlanOption.mFrom.getLocation()).getLatitudeE6())}) + "|" + indoorRoutePlanOption.mFrom.getFloor()).replaceAll(" ", ""));
        }
        str = "";
        if (CoordUtil.ll2mc(indoorRoutePlanOption.mTo.getLocation()) != null) {
            this.a.a("en", (String.format("%f,%f", new Object[]{Double.valueOf(CoordUtil.ll2mc(indoorRoutePlanOption.mTo.getLocation()).getLongitudeE6()), Double.valueOf(CoordUtil.ll2mc(indoorRoutePlanOption.mTo.getLocation()).getLatitudeE6())}) + "|" + indoorRoutePlanOption.mTo.getFloor()).replaceAll(" ", ""));
        }
    }

    public String a(c cVar) {
        return cVar.l();
    }
}
