package com.baidu.platform.core.c;

import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiBoundSearchOption;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiIndoorOption;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.platform.base.SearchType;
import com.baidu.platform.base.a;
import com.baidu.platform.base.d;

public class f extends a implements a {
    private OnGetPoiSearchResultListener b = null;

    public void a() {
        this.a.lock();
        this.b = null;
        this.a.unlock();
    }

    public void a(OnGetPoiSearchResultListener onGetPoiSearchResultListener) {
        this.a.lock();
        this.b = onGetPoiSearchResultListener;
        this.a.unlock();
    }

    public boolean a(PoiBoundSearchOption poiBoundSearchOption) {
        d gVar = new g(poiBoundSearchOption.mPageNum, poiBoundSearchOption.mPageCapacity);
        gVar.a(SearchType.POI_IN_BOUND_SEARCH);
        return a(new h(poiBoundSearchOption), (Object) this.b, gVar);
    }

    public boolean a(PoiCitySearchOption poiCitySearchOption) {
        d gVar = new g(poiCitySearchOption.mPageNum, poiCitySearchOption.mPageCapacity);
        gVar.a(SearchType.POI_IN_CITY_SEARCH);
        return a(new h(poiCitySearchOption), (Object) this.b, gVar);
    }

    public boolean a(PoiDetailSearchOption poiDetailSearchOption) {
        d dVar = new d();
        dVar.a(SearchType.POI_DETAIL_SEARCH);
        return a(new e(poiDetailSearchOption), (Object) this.b, dVar);
    }

    public boolean a(PoiIndoorOption poiIndoorOption) {
        d bVar = new b();
        bVar.a(SearchType.INDOOR_POI_SEARCH);
        return a(new c(poiIndoorOption), (Object) this.b, bVar);
    }

    public boolean a(PoiNearbySearchOption poiNearbySearchOption) {
        d gVar = new g(poiNearbySearchOption.mPageNum, poiNearbySearchOption.mPageCapacity);
        gVar.a(SearchType.POI_NEAR_BY_SEARCH);
        return a(new h(poiNearbySearchOption), (Object) this.b, gVar);
    }
}
