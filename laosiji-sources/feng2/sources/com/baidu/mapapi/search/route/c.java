package com.baidu.mapapi.search.route;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class c implements Creator<BikingRouteLine> {
    c() {
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
