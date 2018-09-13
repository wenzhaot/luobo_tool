package com.baidu.mapapi.search.geocode;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class a implements Creator<GeoCodeResult> {
    a() {
    }

    /* renamed from: a */
    public GeoCodeResult createFromParcel(Parcel parcel) {
        return new GeoCodeResult(parcel);
    }

    /* renamed from: a */
    public GeoCodeResult[] newArray(int i) {
        return new GeoCodeResult[i];
    }
}
