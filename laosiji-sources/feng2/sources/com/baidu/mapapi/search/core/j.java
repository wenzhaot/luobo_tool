package com.baidu.mapapi.search.core;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class j implements Creator<TaxiInfo> {
    j() {
    }

    /* renamed from: a */
    public TaxiInfo createFromParcel(Parcel parcel) {
        return new TaxiInfo(parcel);
    }

    /* renamed from: a */
    public TaxiInfo[] newArray(int i) {
        return new TaxiInfo[i];
    }
}
