package com.baidu.mapapi.search.core;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class d implements Creator<PlaneInfo> {
    d() {
    }

    /* renamed from: a */
    public PlaneInfo createFromParcel(Parcel parcel) {
        return new PlaneInfo(parcel);
    }

    /* renamed from: a */
    public PlaneInfo[] newArray(int i) {
        return new PlaneInfo[i];
    }
}
