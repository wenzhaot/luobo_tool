package com.baidu.platform.core.f;

import anetwork.channel.util.RequestConstant;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.baidu.platform.base.e;
import com.baidu.platform.comapi.util.CoordTrans;
import com.baidu.platform.domain.c;

public class d extends e {
    public d(SuggestionSearchOption suggestionSearchOption) {
        a(suggestionSearchOption);
    }

    private void a(SuggestionSearchOption suggestionSearchOption) {
        this.a.a("q", suggestionSearchOption.mKeyword);
        this.a.a("region", suggestionSearchOption.mCity);
        if (suggestionSearchOption.mLocation != null) {
            LatLng latLng = new LatLng(suggestionSearchOption.mLocation.latitude, suggestionSearchOption.mLocation.longitude);
            if (SDKInitializer.getCoordType() == CoordType.GCJ02) {
                latLng = CoordTrans.gcjToBaidu(latLng);
            }
            this.a.a("location", latLng.latitude + "," + latLng.longitude);
        }
        if (suggestionSearchOption.mCityLimit.booleanValue()) {
            this.a.a("city_limit", RequestConstant.TURE);
        } else {
            this.a.a("city_limit", RequestConstant.FALSE);
        }
        this.a.a("from", "android_map_sdk");
        this.a.a("output", "json");
    }

    public String a(c cVar) {
        return cVar.d();
    }
}
