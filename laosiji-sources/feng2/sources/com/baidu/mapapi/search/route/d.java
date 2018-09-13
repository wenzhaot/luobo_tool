package com.baidu.mapapi.search.route;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class d implements Creator<DrivingRouteLine> {
    d() {
    }

    /* renamed from: a */
    public DrivingRouteLine createFromParcel(Parcel parcel) {
        return new DrivingRouteLine(parcel);
    }

    /* renamed from: a */
    public DrivingRouteLine[] newArray(int i) {
        return new DrivingRouteLine[i];
    }
}
