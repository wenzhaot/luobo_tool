package com.baidu.platform.core.e;

import com.baidu.mapapi.search.share.LocationShareURLOption;
import com.baidu.mapapi.search.share.OnGetShareUrlResultListener;
import com.baidu.mapapi.search.share.PoiDetailShareURLOption;
import com.baidu.mapapi.search.share.RouteShareURLOption;
import com.baidu.platform.base.SearchType;
import com.baidu.platform.base.a;
import com.baidu.platform.base.d;

public class g extends a implements a {
    OnGetShareUrlResultListener b = null;

    public void a() {
        this.a.lock();
        this.b = null;
        this.a.unlock();
    }

    public void a(OnGetShareUrlResultListener onGetShareUrlResultListener) {
        this.a.lock();
        this.b = onGetShareUrlResultListener;
        this.a.unlock();
    }

    public boolean a(LocationShareURLOption locationShareURLOption) {
        d fVar = new f();
        fVar.a(SearchType.LOCATION_SEARCH_SHARE);
        return a(new b(locationShareURLOption), (Object) this.b, fVar);
    }

    public boolean a(PoiDetailShareURLOption poiDetailShareURLOption) {
        d fVar = new f();
        fVar.a(SearchType.POI_DETAIL_SHARE);
        return a(new c(poiDetailShareURLOption), (Object) this.b, fVar);
    }

    public boolean a(RouteShareURLOption routeShareURLOption) {
        d dVar = new d();
        dVar.a(SearchType.ROUTE_PLAN_SHARE);
        return a(new e(routeShareURLOption), (Object) this.b, dVar);
    }
}
