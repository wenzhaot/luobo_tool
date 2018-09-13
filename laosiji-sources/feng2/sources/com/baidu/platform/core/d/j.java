package com.baidu.platform.core.d;

import com.baidu.mapapi.search.route.BikingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.IndoorRoutePlanOption;
import com.baidu.mapapi.search.route.MassTransitRoutePlanOption;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.platform.base.SearchType;
import com.baidu.platform.base.a;
import com.baidu.platform.base.d;

public class j extends a implements e {
    private OnGetRoutePlanResultListener b = null;

    public void a() {
        this.a.lock();
        this.b = null;
        this.a.unlock();
    }

    public void a(OnGetRoutePlanResultListener onGetRoutePlanResultListener) {
        this.a.lock();
        this.b = onGetRoutePlanResultListener;
        this.a.unlock();
    }

    public boolean a(BikingRoutePlanOption bikingRoutePlanOption) {
        d aVar = new a();
        aVar.a(SearchType.BIKE_ROUTE);
        return a(new b(bikingRoutePlanOption), (Object) this.b, aVar);
    }

    public boolean a(DrivingRoutePlanOption drivingRoutePlanOption) {
        d cVar = new c();
        cVar.a(SearchType.DRIVE_ROUTE);
        return a(new d(drivingRoutePlanOption), (Object) this.b, cVar);
    }

    public boolean a(IndoorRoutePlanOption indoorRoutePlanOption) {
        d fVar = new f();
        fVar.a(SearchType.INDOOR_ROUTE);
        return a(new g(indoorRoutePlanOption), (Object) this.b, fVar);
    }

    public boolean a(MassTransitRoutePlanOption massTransitRoutePlanOption) {
        d hVar = new h();
        hVar.a(SearchType.MASS_TRANSIT_ROUTE);
        return a(new i(massTransitRoutePlanOption), (Object) this.b, hVar);
    }

    public boolean a(TransitRoutePlanOption transitRoutePlanOption) {
        d lVar = new l();
        lVar.a(SearchType.TRANSIT_ROUTE);
        return a(new m(transitRoutePlanOption), (Object) this.b, lVar);
    }

    public boolean a(WalkingRoutePlanOption walkingRoutePlanOption) {
        d nVar = new n();
        nVar.a(SearchType.WALK_ROUTE);
        return a(new o(walkingRoutePlanOption), (Object) this.b, nVar);
    }
}
