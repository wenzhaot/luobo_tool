package com.baidu.mapapi.search.core;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class a implements Creator<BusInfo> {
    a() {
    }

    /* renamed from: a */
    public BusInfo createFromParcel(Parcel parcel) {
        return new BusInfo(parcel);
    }

    /* renamed from: a */
    public BusInfo[] newArray(int i) {
        return new BusInfo[i];
    }
}
