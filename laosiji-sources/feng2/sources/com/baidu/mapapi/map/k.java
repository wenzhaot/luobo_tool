package com.baidu.mapapi.map;

import android.view.View;
import android.view.View.OnClickListener;
import com.baidu.platform.comapi.map.ae;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;

class k implements OnClickListener {
    final /* synthetic */ MapView a;

    k(MapView mapView) {
        this.a = mapView;
    }

    public void onClick(View view) {
        ae E = this.a.c.a().E();
        E.a += 1.0f;
        BaiduMap.mapStatusReason |= 16;
        this.a.c.a().a(E, (int) GenericDraweeHierarchyBuilder.DEFAULT_FADE_DURATION);
    }
}
