package com.baidu.mapapi.search.route;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class a implements Creator<BikingRouteLine> {
    a() {
    }

    /* renamed from: a */
    public BikingRouteLine createFromParcel(Parcel parcel) {
        return new BikingRouteLine(parcel);
    }

    /* renamed from: a */
    public BikingRouteLine[] newArray(int i) {
        return new BikingRouteLine[i];
    }
}
