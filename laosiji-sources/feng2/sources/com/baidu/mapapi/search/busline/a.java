package com.baidu.mapapi.search.busline;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class a implements Creator<BusLineResult> {
    a() {
    }

    /* renamed from: a */
    public BusLineResult createFromParcel(Parcel parcel) {
        return new BusLineResult(parcel);
    }

    /* renamed from: a */
    public BusLineResult[] newArray(int i) {
        return new BusLineResult[i];
    }
}
