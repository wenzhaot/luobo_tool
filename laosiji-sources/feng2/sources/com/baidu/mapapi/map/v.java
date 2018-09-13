package com.baidu.mapapi.map;

import android.view.View;
import android.view.View.OnClickListener;
import com.baidu.platform.comapi.map.ae;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;

class v implements OnClickListener {
    final /* synthetic */ WearMapView a;

    v(WearMapView wearMapView) {
        this.a = wearMapView;
    }

    public void onClick(View view) {
        ae E = this.a.d.a().E();
        E.a -= 1.0f;
        this.a.d.a().a(E, (int) GenericDraweeHierarchyBuilder.DEFAULT_FADE_DURATION);
    }
}
