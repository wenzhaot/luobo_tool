package com.baidu.mapapi.search.route;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class h implements Creator<IndoorRouteResult> {
    h() {
    }

    /* renamed from: a */
    public IndoorRouteResult createFromParcel(Parcel parcel) {
        return new IndoorRouteResult(parcel);
    }

    /* renamed from: a */
    public IndoorRouteResult[] newArray(int i) {
        return new IndoorRouteResult[i];
    }
}
