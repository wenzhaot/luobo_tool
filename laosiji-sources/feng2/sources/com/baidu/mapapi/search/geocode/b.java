package com.baidu.mapapi.search.geocode;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class b implements Creator<ReverseGeoCodeResult> {
    b() {
    }

    /* renamed from: a */
    public ReverseGeoCodeResult createFromParcel(Parcel parcel) {
        return new ReverseGeoCodeResult(parcel);
    }

    /* renamed from: a */
    public ReverseGeoCodeResult[] newArray(int i) {
        return new ReverseGeoCodeResult[i];
    }
}
