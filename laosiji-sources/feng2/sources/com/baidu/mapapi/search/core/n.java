package com.baidu.mapapi.search.core;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class n implements Creator<VehicleInfo> {
    n() {
    }

    /* renamed from: a */
    public VehicleInfo createFromParcel(Parcel parcel) {
        return new VehicleInfo(parcel);
    }

    /* renamed from: a */
    public VehicleInfo[] newArray(int i) {
        return new VehicleInfo[i];
    }
}
