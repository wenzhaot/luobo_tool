package com.baidu.platform.core.d;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.route.BikingRoutePlanOption;
import com.baidu.platform.base.e;
import com.baidu.platform.comapi.util.CoordTrans;
import com.baidu.platform.domain.c;

public class b extends e {
    public b(BikingRoutePlanOption bikingRoutePlanOption) {
        a(bikingRoutePlanOption);
    }

    private void a(BikingRoutePlanOption bikingRoutePlanOption) {
        this.a.a("mode", "riding");
        LatLng location = bikingRoutePlanOption.mFrom.getLocation();
        if (location != null) {
            if (SDKInitializer.getCoordType() == CoordType.GCJ02) {
                location = CoordTrans.gcjToBaidu(location);
            }
            this.a.a("origin", location.latitude + "," + location.longitude);
        } else {
            this.a.a("origin", bikingRoutePlanOption.mFrom.getName());
        }
        location = bikingRoutePlanOption.mTo.getLocation();
        if (location != null) {
            if (SDKInitializer.getCoordType() == CoordType.GCJ02) {
                location = CoordTrans.gcjToBaidu(location);
            }
            this.a.a("destination", location.latitude + "," + location.longitude);
        } else {
            this.a.a("destination", bikingRoutePlanOption.mTo.getName());
        }
        this.a.a("origin_region", bikingRoutePlanOption.mFrom.getCity());
        this.a.a("destination_region", bikingRoutePlanOption.mTo.getCity());
        if (bikingRoutePlanOption.mRidingType == 1) {
            this.a.a("riding_type", String.valueOf(bikingRoutePlanOption.mRidingType));
        }
        this.a.a("output", "json");
        this.a.a("from", "android_map_sdk");
    }

    public String a(c cVar) {
        return cVar.j();
    }
}
