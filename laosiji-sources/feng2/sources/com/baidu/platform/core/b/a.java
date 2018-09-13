package com.baidu.platform.core.b;

import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.platform.base.SearchType;
import com.baidu.platform.base.d;
import com.baidu.platform.base.e;

public class a extends com.baidu.platform.base.a implements d {
    OnGetGeoCoderResultListener b = null;

    public void a() {
        this.a.lock();
        this.b = null;
        this.a.unlock();
    }

    public void a(OnGetGeoCoderResultListener onGetGeoCoderResultListener) {
        this.a.lock();
        this.b = onGetGeoCoderResultListener;
        this.a.unlock();
    }

    public boolean a(GeoCodeOption geoCodeOption) {
        d bVar = new b();
        e cVar = new c(geoCodeOption);
        bVar.a(SearchType.GEO_CODER);
        return a(cVar, (Object) this.b, bVar);
    }

    public boolean a(ReverseGeoCodeOption reverseGeoCodeOption) {
        d eVar = new e();
        e fVar = new f(reverseGeoCodeOption);
        eVar.a(SearchType.REVERSE_GEO_CODER);
        return a(fVar, (Object) this.b, eVar);
    }
}
