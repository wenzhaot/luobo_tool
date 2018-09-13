package com.baidu.mapapi.search.core;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class g implements Creator<RouteNode> {
    g() {
    }

    /* renamed from: a */
    public RouteNode createFromParcel(Parcel parcel) {
        return new RouteNode(parcel);
    }

    /* renamed from: a */
    public RouteNode[] newArray(int i) {
        return new RouteNode[i];
    }
}
