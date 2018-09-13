package com.baidu.mapapi.map;

import android.view.View;
import android.view.View.OnClickListener;
import com.baidu.platform.comapi.map.ae;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;

class s implements OnClickListener {
    final /* synthetic */ TextureMapView a;

    s(TextureMapView textureMapView) {
        this.a = textureMapView;
    }

    public void onClick(View view) {
        ae E = this.a.b.b().E();
        E.a += 1.0f;
        BaiduMap.mapStatusReason |= 16;
        this.a.b.b().a(E, (int) GenericDraweeHierarchyBuilder.DEFAULT_FADE_DURATION);
    }
}
